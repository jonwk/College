// main program for a huffman coder

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "huff.h"



int main(int argc, char ** argv)
{
//  int char_freqs[NUM_CHARS];
  struct huffcoder * coder;
  int valid_command = 0;

  if ( argc < 3 ) {
    fprintf(stderr, "Usage: %s showcodes <trainfile>\n", argv[0]);
    fprintf(stderr, "     : %s encode    <trainfile> <infile> <outfile>\n", argv[0]);
    fprintf(stderr, "     : %s decode    <trainfile> <infile> <outfile>\n", argv[0]);
    exit(1);
  }

  if ( strcmp(argv[1], "showcodes") == 0 ) {
    valid_command = 1;
    if ( argc != 3 ) {
      fprintf(stderr, "Fatal: Exactly one filename must be passed for showcodes\n");
      exit(1);
    }
  }

  if ( (strcmp(argv[1], "encode") == 0) || (strcmp(argv[1], "decode") == 0) ) {
    valid_command = 1;
    if ( argc != 5 ) {
      fprintf(stderr,
	      "Fatal: Exactly three filenames must be passed for encode/decode\n");
      exit(1);
    }
  }

  if ( valid_command != 1 ) {
    fprintf(stderr, "Fatal: Unknown command %s\n", argv[1]);
  }

  // create a new huffcoder structure
  coder = huffcoder_new();
  
  // find character frequencies using the training file
  huffcoder_count(coder, argv[2]);

  // build the Huffman tree
  huffcoder_build_tree(coder);

  // from the Huffman tree fill the table with Huffman codes
  huffcoder_tree2table(coder);

  if ( strcmp(argv[1], "showcodes") == 0 ) {
    // print the Huffman codes
    huffcoder_print_codes(coder);
  }
  else if ( strcmp(argv[1], "encode") == 0 ) {
    huffcoder_encode(coder, argv[3], argv[4]);
  }
  else if ( strcmp(argv[1], "decode") == 0 ) {
    huffcoder_decode(coder, argv[3], argv[4]);
  }
  
  return 0;
}
