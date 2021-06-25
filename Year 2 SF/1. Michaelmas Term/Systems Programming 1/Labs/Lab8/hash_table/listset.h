#ifndef LIST_H
#define LIST_H
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
// #include <iostream> 

// a linked list node containing a string
struct listnode {
  char * str;
  struct listnode * next;
};

// a linked list data structure
struct listset {
  struct listnode * head;
};

// create a new, empty linked list set
struct listset * listset_new();

/* check to see if an item is in the set
   returns 1 if in the set, 0 if not */
int listset_lookup(struct listset * this, char * item);

// add an item, with number 'item' to the set
// has no effect if the item is already in the set.
// New items that are not already in the set should
// be added to the start of the list
void listset_add(struct listset * this, char * item);

// remove an item with number 'item' from the set
void listset_remove(struct listset * this, char * item);

// place the union of src1 and src2 into dest
void listset_union(struct listset * dest, struct listset * src1, struct listset * src2);

// place the intersection of src1 and src2 into dest
void listset_intersect(struct listset * dest, struct listset * src1, struct listset * src2);

// return the number of items in the listset
int listset_cardinality(struct listset * this);

// reverses the listset
void listset_reverse(struct listset * this);

// print the elements of the list set
void listset_print(struct listset * this);

void test_union_intersection();

#endif
