#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "listset.h"

// main function to test the listset ADT
int main(int argc, char ** argv) {
  struct listset * myset;

  if ( argc < 2 ) {
    fprintf(stderr, "Usage: ./listset <command> [<string>]\n");
    fprintf(stderr, "       where <command> is + (add following string)");
    fprintf(stderr, "                          - (remove following string)");
    fprintf(stderr, "                          = (print set)");
    exit(1);
  }

  // create a set and follow the commands
  myset = listset_new();
  int i = 1;
  while ( i < argc ) {
    if ( argv[i][0] == '+' ) {
      assert( i+1 < argc );
      listset_add(myset, argv[i+1]);
      i = i + 2;
    }
    else if (argv[i][0] == '-' ) {
      assert( i+1 < argc );
      listset_remove(myset, argv[i+1]);
      i = i + 2;
    }
    else if (argv[i][0] == '=' ) {
      listset_print(myset);
      i++;
    }
    else if (argv[i][0] == '#' ) {
      printf("cardinality: %d\n", listset_cardinality(myset));
      i++;
    }
    else {
      fprintf(stderr, "Error: Unknown command %s\n", argv[i]);
      exit(1);
    }
//    assert( 0 ); // we should never get here
  }
    
  return 0;
}
