// header file for Huffman coder

#ifndef HUFF_H
#define HUFF_H

#define NUM_CHARS 256

// node in a Huffman tree is either a compound char (internal node)
// or a simple char (leaf)
struct huffchar {
  int freq;
  int is_compound;
  int seqno;
  union {
    struct {
      struct huffchar * left;
      struct huffchar * right;
    } compound;
    unsigned char c;
  } u;
};


struct huffcoder {
  int freqs[NUM_CHARS];
  int code_lengths[NUM_CHARS];
  unsigned long long codes[NUM_CHARS];
  struct huffchar * tree;
};

// create a new huffcoder structure
struct huffcoder *  huffcoder_new();

// count the frequency of characters in a file; set chars with zero
// frequency to one
void huffcoder_count(struct huffcoder * this, char * filename);

// using the character frequencies build the tree of compound
// and simple characters that are used to compute the Huffman codes
void huffcoder_build_tree(struct huffcoder * this);

// using the Huffman tree, build a table of the Huffman codes
// with the huffcoder object
void huffcoder_tree2table(struct huffcoder * this);

// print the Huffman codes for each character in order
void huffcoder_print_codes(struct huffcoder * this);

// encode the input file and write the encoding to the output file
void huffcoder_encode(struct huffcoder * this, char * input_filename,
		      char * output_filename);

// decode the input file and write the decoding to the output file
void huffcoder_decode(struct huffcoder * this, char * input_filename,
		      char * output_filename);

#endif // HUFF_H
