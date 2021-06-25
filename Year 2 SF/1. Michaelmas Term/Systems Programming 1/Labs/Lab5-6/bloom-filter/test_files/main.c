// include the standard library header files that we use in this
// program, which are denoted by angle brackets < >
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

// include the header files that are not part of the standard library,
// but can be found in the current directory, which are denoted with
// quotation marks " "
#include "bitset.h"
#include "bloom.h"

// print the contents of the bitset
void bitset_print(struct bitset * this) {
  int i;
  int size = bitset_size(this);
  for ( i = 0; i < size; i++ ) {
    if ( bitset_lookup(this, i) == 1 ) {
      printf("%d ", i);
    }
  }
  printf("\n");
}

// print the contents of the bloom filter
void bloom_print(struct bloom * this) {
  bitset_print(this->bitset);
}

// add the characters from a string to a bitset
void add_chars_to_set(struct bitset * this, char * s)
{
  int i;
  for ( i = 0; s[i] != 0; i++ ) {
    unsigned char temp = s[i];
    bitset_add(this, temp);
  }
}

// remove the characters of a string from a bitset
void remove_chars_from_set(struct bitset * this, char * s)
{
  int i;
  for ( i = 0; s[i] != 0; i++ ) {
    unsigned char temp = s[i];
    bitset_remove(this, temp);
  }
}

// small routine to test a bitset
void test_bitset_create(char * string1)
{
  struct bitset * a = bitset_new(256);

  add_chars_to_set(a, string1);

  // print the contents of the sets
  bitset_print(a);
}

void test_bitset_union(char * string1, char * string2)
{
  struct bitset * a = bitset_new(256);
  struct bitset * b = bitset_new(256);
  struct bitset * c = bitset_new(256);

  add_chars_to_set(a, string1);
  add_chars_to_set(b, string2);

  // compute and print the union of sets
  bitset_union(c, a, b);
  bitset_print(c);
}

void test_bitset_intersect(char * string1, char * string2)
{
  struct bitset * a = bitset_new(256);
  struct bitset * b = bitset_new(256);
  struct bitset * c = bitset_new(256);

  add_chars_to_set(a, string1);
  add_chars_to_set(b, string2);

  // compute and print the intersection of sets
  bitset_intersect(c, a, b);
  bitset_print(c);
}

void test_bitset_remove(char * string1, char * string2)
{
  struct bitset * a = bitset_new(256);

  add_chars_to_set(a, string1);
  bitset_print(a);
  remove_chars_from_set(a, string2);
  bitset_print(a);
}

void test_bitset_cardinality(char * string1)
{
  struct bitset * a = bitset_new(256);

  add_chars_to_set(a, string1);
  // print the cardinality of the bitset
  int card = bitset_cardinality(a);
  printf("Cardinality is: %d\n", card);
}

void test_bloom_filter(char ** strings, int nstrings)
{
  struct bloom * filter = bloom_new(256);
  int i;

  for ( i = 0; i < nstrings; i++ ) {
    //fprintf(stderr, "%d: %s\n", i, strings[i]);
    bloom_add(filter, strings[i]);
  }
  bloom_print(filter);
}

void print_usage()
{
  fprintf(stderr, "Usage: prog bitset_create <filename>\n");
  fprintf(stderr, "Usage: prog bitset_union <filename>\n");
  fprintf(stderr, "Usage: prog bitset_intersect <filename>\n");
  fprintf(stderr, "Usage: prog bitset_remove <filename>\n");
  fprintf(stderr, "Or...: prog bloom <filename>\n");
  exit(1);
}


/* Open a file. Abort progam if file cannot be opened */
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

/* read lines of text from a file; return number of lines */
int read_strings(char * filename, char ** strings, int size)
{
  const int max_line = 1024;
  char line[max_line];
  int i = 0;
  FILE * file;
  char * eof;

  file = open_file(filename);

  /* read in the strings - one per line */
  eof = fgets(line, max_line, file);
  while ( eof != NULL && i < size ) {     /* eof == NULL => end of file */
    int length = strlen(line);
    strings[i] = malloc(sizeof(char)*(length+1));
    strcpy(strings[i], line);
    i++;
    eof = fgets(line, max_line, file);
  }

  fclose(file);
  return i;
}

const int max_string = 128;

int main(int argc, char ** argv)
{
  char * strings[max_string];
  int nstrings;
  char * filename;
  char * option;

  // check there are at least two parameters, including the program name
  if ( argc != 3 ) {
    print_usage();
  }

  filename = argv[2];
  nstrings = read_strings(filename, strings, max_string);

  option = argv[1];

  if ( strcmp(option, "bitset_create") == 0 ) {
    // there must be exactly one string to use as data
    assert( nstrings == 1 );
    test_bitset_create(strings[0]);
  }
  else if ( strcmp(option, "bitset_union") == 0 ) {
    // there must be exactly two strings to use as data
    assert( nstrings == 2 );
    test_bitset_union(strings[0], strings[1]);
  }
  else if ( strcmp(option, "bitset_intersect") == 0 ) {
    // there must be exactly two strings to use as data
    assert( nstrings == 2 );
    test_bitset_intersect(strings[0], strings[1]);
  }
  else if ( strcmp(option, "bitset_remove") == 0 ) {
    // there must be exactly two strings to use as data
    assert( nstrings == 2 );
    test_bitset_remove(strings[0], strings[1]);
  }
  else if ( strcmp(option, "bitset_cardinality") == 0 ) {
    // there must be exactly one string to use as data
    assert( nstrings == 1 );
    test_bitset_cardinality(strings[0]);
  }
  else if ( strcmp(option, "bloom") == 0 ) {
    assert( nstrings <= max_string );
    test_bloom_filter(strings, nstrings);
  }
  else {
    print_usage();
  }

  return 0;
}
