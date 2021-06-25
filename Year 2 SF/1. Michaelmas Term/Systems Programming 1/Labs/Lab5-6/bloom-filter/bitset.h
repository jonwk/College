// header file for a bitset abstract datatype

#ifndef BITSET_H
#define BITSET_H

#include <stdint.h>
#include <stdlib.h>
#include <math.h>

struct bitset {
  uint64_t * bits;    // array where the 0/1 bits are stored
  int size_in_words;  // size of the array
  int universe_size;  // number of items that could be members of the set
};


// create a new, empty bit vector set with a universe of 'size' items
struct bitset * bitset_new(int size);

// get the size of the universe of items that could be stored in the set
int bitset_size(struct bitset * this);

// get the number of items that are stored in the set
int bitset_cardinality(struct bitset * this);

// check to see if an item is in the set
int bitset_lookup(struct bitset * this, int item);

// add an item, with number 'item' to the set
// has no effect if the item is already in the set
void bitset_add(struct bitset * this, int item);

// remove an item with number 'item' from the set
void bitset_remove(struct bitset * this, int item);

// place the union of src1 and src2 into dest;
// all of src1, src2, and dest must have the same size universe
void bitset_union(struct bitset * dest, struct bitset * src1,
		  struct bitset * src2);

// place the intersection of src1 and src2 into dest
// all of src1, src2, and dest must have the same size universe
void bitset_intersect(struct bitset * dest, struct bitset * src1,
                  struct bitset * src2);

#endif // BITSET_H
