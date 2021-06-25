#ifndef HASH_H
#define HASH_H

#include "listset.h"


// a hash table data structure
struct hashtable {
  struct listset * table;
  int size;
};

// create a new, empty hashtable set
struct hashtable * hashtable_new(int size);

/* check to see if an item is in the set
   returns 1 if in the set, 0 if not */
int hashtable_lookup(struct hashtable * this, char * item);

// add an item, with number 'item' to the set
// has no effect if the item is already in the set.
// New items that are not already in the set should
// be added to the start of the list of the relevant
// hashtable entry
void hashtable_add(struct hashtable * this, char * item);

// remove an item with number 'item' from the hastable set
void hashtable_remove(struct hashtable * this, char * item);

// return the number of items in the hashtable set
int hashtable_cardinality(struct hashtable * this);

// print the elements of the hashtable set
void hashtable_print(struct hashtable * this);

#endif
