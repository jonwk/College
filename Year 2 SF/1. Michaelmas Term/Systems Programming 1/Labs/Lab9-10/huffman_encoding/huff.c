

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <ctype.h>

#include "huff.h"
#include "bitfile.h"
#include <limits.h>


// create a new huffcoder structure
struct huffcoder *  huffcoder_new()
{
	struct huffcoder *p;
	p= malloc(sizeof(struct huffcoder));
	p->tree = NULL;
	for(int i=0; i<NUM_CHARS; i++)
	{
		p->freqs[i] = 0;
	}
	return p;
}

// count the frequency of characters in a file; set chars with zero
// frequency to one


FILE * open_file(char filename[])
{
  FILE * file;

  file = fopen(filename, "r");
  if ( file == NULL ) {
    printf("FATAL: Error opening file %s. Aborting program.\n", filename);
    exit(1);
  }

  return file;
}

void huffcoder_count(struct huffcoder * this, char * filename)
{
	FILE * fp = open_file(filename);
	char temp = 0;
	while(temp != EOF)
	{
		temp = fgetc(fp);
		if(temp != EOF)
		{
			this->freqs[(unsigned int) temp]++;
		}
	}
	fclose(fp);
	for(int i=0; i<NUM_CHARS; i++)
	{
		if(this->freqs[i] == 0)
		{
			this->freqs[i] = 1;
		}
	}
}


// using the character frequencies build the tree of compound
// and simple characters that are used to compute the Huffman codes

//makes new node for char
struct huffchar * new_char_node(unsigned char c, int freq)
{
	struct huffchar * new;
	new = malloc(sizeof(struct huffchar));			//malloc space
	new->u.compound.left =0;
	new->u.compound.right =0;
	new->is_compound = 0;
	new->freq = freq;				//set freq to char freq
	new->seqno = c;					//set seqno to char seqno
	new->u.c = c;					//set char to char c
	//fprintf(stderr, "new char(%d)\n",(int)new->u.c);
	return new;
}

int get_lowest_index(struct huffchar * nodes[NUM_CHARS])
{
	unsigned int lowest_freq = UINT_MAX;
	int seq_no=0;
	int index = -1;
	for(int i=0; i<NUM_CHARS; i++)
	{
		if(nodes[i] != NULL)
		{
			int current_freq = nodes[i]->freq;
			int current_seq = nodes[i]->seqno;
			if(current_freq < lowest_freq)
			{
			index = i;
			seq_no = current_seq;
			lowest_freq = current_freq;
			}
			else if(current_freq == lowest_freq)
			{
				if(current_seq < seq_no)
				{
					index = i;
					seq_no = current_seq;
					lowest_freq = current_freq;
				}
			}
		}
	}
	//fprintf(stderr,"index %d %p\n",index, nodes[index]);
	return index;
}


struct huffchar * make_comp_node(struct huffchar * n1, struct huffchar * n2, int seq_no)
{
	if(!n1->is_compound)
	{
	//fprintf(stderr, "--n1 %d\n", (int)n1->u.c);
	}
	if(!n2->is_compound)
	{
	//fprintf(stderr, "--n2 %d\n", (int)n2->u.c);
	}
	struct huffchar * comp;
	comp = malloc(sizeof(struct huffchar));					//malloc space
	comp->freq = n1->freq + n2->freq;		//set freq
	comp->is_compound = 1;					//set is_compound to true
	comp->u.compound.left = n1;				//set left to be char with smallest freq
	comp->u.compound.right = n2; 			//set right to be one with more freq
	comp->seqno = seq_no;
	if(comp->seqno < 0){
	//fprintf(stderr, "warn %d \n",comp->seqno);
	}
	return comp;
}

//check for non null nodes
int num_of_nodes(struct huffchar * rootnodes[NUM_CHARS])
{
	int non_null=0;
	for(int i=0; i<NUM_CHARS; i++)
	{
		if(rootnodes[i] != NULL)
		{
			non_null++;
		}
	}
	return non_null;
}


void huffcoder_build_tree(struct huffcoder * this)
{
//make array(list of nodes to be joined)
//huffchar * rootnodes{NUM_CHAR]
	struct huffchar * rootnodes[NUM_CHARS];
//go through rootnodes and set each to equal a node representing the character
//eg. rootnode[i] = new_char_node((char)i, t->freq[i]);
	for(int i = 0; i<NUM_CHARS; i++)
	{
		rootnodes[i] = new_char_node((unsigned char)i, this->freqs[i]);
	}
	int current_seq_no = 256;
	int i1=0;
	int i2=0;
	while(num_of_nodes(rootnodes) > 1)
	{
		//find lowest 2 and take them out of list
		i1 = get_lowest_index(rootnodes);
		struct huffchar * lowest = rootnodes[i1];
		rootnodes[i1] = NULL;
		i2 = get_lowest_index(rootnodes);
		struct huffchar * low2 = rootnodes[i2];
		rootnodes[i2] = NULL;
		//printf("%d %d\n", i1, i2);
		struct huffchar * compound = make_comp_node(lowest, low2, current_seq_no);
		current_seq_no++;
		rootnodes[i1] = compound;
		
	}
	i1 = get_lowest_index(rootnodes);
	this->tree = rootnodes[i1];
}


// recursive function to convert the Huffman tree into a table of
// Huffman codes
void tree2table_recursive(struct huffcoder * this, struct huffchar * node,
		 int path, int depth)	
{
		//fprintf(stderr, "%p\n", node);
		//fprintf(stderr, "Test8 %d \n", node->is_compound);
		//fprintf(stderr, "Test10  c %d\n", node->freq);
		//fprintf(stderr, "Test11  d %d\n", node->seqno);
	if(node->is_compound)
	{
	//fprintf(stderr, "Test1 \n");
		//depth++;
		//fprintf(stderr, "Test3 \n");
		path = path << 1;
		//fprintf(stderr, "Test4 \n");
		//fprintf(stderr, "Test %p \n", node->u.compound.left);
		//fprintf(stderr,"going left\n");
		tree2table_recursive(this,node->u.compound.left, path, depth+1);
		//fprintf(stderr,"going right\n");
		//fprintf(stderr, "Test5 \n");
		path = path | 1;
		
		tree2table_recursive(this, node->u.compound.right, path, depth+1);
	}
	if(!node->is_compound)
	{
		//fprintf(stderr, "Test2  a\n");
		////fprintf(stderr, "Test2  b %c\n", node->u.c);
		//fprintf(stderr, "Test2  c %d\n", node->freq);
		//fprintf(stderr, "Test2  d %d\n", node->seqno);
		unsigned char index = node->u.c;
		//fprintf(stderr, "Test2 [%c] %d \n", index, (int)index);
		this->codes[(unsigned int)index]=path;
		this->code_lengths[(unsigned int)index]=depth;
	}
	//fprintf(stderr,"going back\n");
}

// using the Huffman tree, build a table of the Huffman codes
// with the huffcoder object
void huffcoder_tree2table(struct huffcoder * this)
{
	
	int path=0;
	int depth=0;
	fprintf(stderr, "Test: \n");
	tree2table_recursive(this, this->tree, path, depth);
}


// print the Huffman codes for each character in order
void huffcoder_print_codes(struct huffcoder * this)
{
  int i, j;
  char buffer[NUM_CHARS];

  for (int i = 0; i < NUM_CHARS; i++ ) {
    // put the code into a string
    for ( j = this->code_lengths[i]-1; j >= 0; j--) {
      buffer[(this->code_lengths[i]-1)-j] = ((this->codes[i] >> j) & 1) + '0';
    }
    // don't forget to add a zero to end of string
    buffer[this->code_lengths[i]] = '\0';

    // print the code
    //printf("char: %d, freq: %d, code: %s\n", this->codes[i], this->freqs[i], buffer);
    printf("char: %d, freq: %d, code: %s\n", i, this->freqs[i], buffer);
  }
}



// encode the input file and write the encoding to the output file
void huffcoder_encode(struct huffcoder * this, char * input_filename,
		      char * output_filename)
{
/*
FILE * in = fopen(input_filename, "r");
FILE * out = fopen(output_filename, "w");
unsigned long long bit;
	//int count = 0;
	while(!feof(in))
	{
		char c = fgetc(in);
		if (!feof(in)) {
			int length = this->code_lengths[(int)c];
			unsigned long long code = this->codes[(int)c];
			fputc(c, out);
			fputc(':', out);
			for(int i=length-1; i>=0; i--)
			{
				bit = code & (1 << i);
				if( bit != 0)
				{
					bit = 1;
					fputc('1', out);
				}
				else
				{
					bit = 0;
					fputc('0', out);
				}
			}
			fputc('\n', out);
		}
		
	}
		char c = 4;
		int length = this->code_lengths[(int)c];
		unsigned long long code = this->codes[(int)c];
		fputc('$', out);
		fputc(':', out);
		for(int i=length-1; i>=0; i--)
		{
			bit = code & (1 << i);
			if( bit != 0)
			{
				bit = 1;
				fputc('1', out);
			}
			else
			{
				bit = 0;
				fputc('0', out);
			}
		}
		fputc('\n', out);
fclose(in);
fclose(out);
*/
//*
	struct bitfile * newbitf = bitfile_new();
	FILE * input = fopen(input_filename, "r");
	newbitf= bitfile_open(output_filename, "w");
  
  	if ( input == NULL ) {		
    	printf("FATAL: Error opening file %s. Aborting program.\n", input_filename);
    	exit(1);
	}
	
	//open outfile
	
	unsigned long long bit;
	//int count = 0;
	while(!feof(input))
	{
		fprintf(stderr, "Test \n"); 
		char c = fgetc(input);
		if (!feof(input)) {
			int length = this->code_lengths[(int)c];
			unsigned long long code = this->codes[(int)c];
			for(int i=length-1; i>=0; i--)
			{
				//fprintf(stderr, "Test2, %lld, %d \n", code, length); 
				bit = code & (1 << i);
				if( bit != 0)
				{
					bit = 1;
				}
				else
				{
					bit = 0;
				}

				bitfile_write_bit(newbitf, bit);
				fprintf(stderr, "%d", bit); 
			}
			//fprintf(stderr, "COUNT: %c %d\n", c, count);
			//count++;
			//if (count > 100000) return;
		}
		
	}


	int length = this->code_lengths[4];
	unsigned long long code = this->codes[4];
	fprintf(stderr, "EOT: ");
	for(int i=length-1; i>=0; i--)
	{
		//fprintf(stderr, "Test2, %lld, %d \n", code, length); 
		bit = code & (1 << i);
		if( bit != 0)
		{
			bit = 1;
		}
		else
		{
			bit = 0;
		}

		bitfile_write_bit(newbitf, bit);
		fprintf(stderr, "%d", bit); 
	}
	fprintf(stderr, "\n");


	fclose(input);
	bitfile_close(newbitf);
/**/
}

// decode the input file and write the decoding to the output file
void huffcoder_decode(struct huffcoder * this, char * input_filename,
		      char * output_filename)
{
	struct bitfile * newbitf = bitfile_new();
	FILE * input = fopen(output_filename, "w");
	
  	newbitf= bitfile_open(input_filename, "r");	//write
  	if ( newbitf->file == NULL ) {		
    	printf("FATAL: Error opening file %s. Aborting program.\n", input_filename);
    	exit(1);
	}
	
	//open outfile
	
	unsigned char cout = 0;
	//unsigned long long bit;
	//while(newbitf->is_EOF==0)
	//{
		while(cout != 4){
		    struct huffchar * temp = this->tree;
			while(temp->is_compound == 1)			//while node is a compound
			{
				//fprintf(stderr, "Test2, %c \n", cout); 
				int currentbit = bitfile_read_bit(newbitf);
				//if bit is 0 go left
				if(currentbit == 0)
				{
					temp = temp->u.compound.left;
					//fprintf(stderr, "Test3, %c \n", cout); 
				}
				//else go right
				else
				{
					temp = temp->u.compound.right;
					//fprintf(stderr, "Test4, %c \n", cout); 
				}
			}
			cout = temp->u.c;
			fprintf(stderr, "Test2, %d , %c \n", cout, cout); 
			if(cout != 4)
			{
				fputc(cout, input);
			} else {
				fprintf(stderr, "HERE\n");
				exit(1);
			}
		}
		fprintf(stderr, "x");
	//}

	fclose(input);
	bitfile_close(newbitf);
	
	
}