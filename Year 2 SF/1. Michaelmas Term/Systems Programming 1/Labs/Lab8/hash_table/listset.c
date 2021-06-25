
// include the header files with the declarations of listset
#include "listset.h"

// create a new, empty linked list set
struct listset *listset_new()
{
  struct listset *result = malloc(sizeof(struct listset));
  // assert(result != NULL);
  result->head = NULL;
  return result;
}

/* check to see if an item is in the set returns 1 if in the set, 0 if not */
int listset_lookup(struct listset *this, char *item)
{
  for (struct listnode *node = this->head; node != NULL; node = node->next)
  {
    if (!strcmp(node->str, item))
    {
      return 1;
    }
  }
  return 0;
}

// add an item, with number 'item' to the set
// has no effect if the item is already in the set.
// New items that are not already in the set should
// be added to the start of the list
void listset_add(struct listset *this, char *item)
{
  if (listset_lookup(this, item) == 0)
  {
    struct listnode *node = malloc(sizeof(struct listnode));
    node->str = item;
    node->next = this->head;
    this->head = node;
  }
}

// remove an item with number 'item' from the set
void listset_remove(struct listset *this, char *item)
{
  if (this->head != NULL)
  {
    // removing head
    if (!strcmp(item, this->head->str))
    {
      // when you have more than one element (a head) in the list
      if (this->head->next != NULL)
      {
        this->head = this->head->next;
        return;
      }
      // when head is the only element in the list
      else
      {
        this->head = NULL;
        return;
      }
    }
    // removing the rest of the elements
    for (struct listnode *node = this->head; node->next != NULL; node = node->next)
    {
      if (!strcmp(node->next->str, item))
      {
        node->next = node->next->next;
        return;
      }
    }
    // struct listnode *node = malloc(sizeof(struct listnode));
    // node = this->head;
    // struct listnode *prev_node = malloc(sizeof(struct listnode));
    // while (node->next != NULL)
    // {
    //   if (!strcmp(node->next->str, item))
    //   {
    //     node->next = node->next->next;
    //     break;
    //   }
    //   prev_node = node;
    //   node = node->next;
    // }
    // // printf("%s, head\n", this->head->str);
    // // printf("%s, prev_node\n", prev_node->str);
    // // printf("%s, node\n", node->str);
    //   if (!strcmp(node->str, item))
    //   {
    //     prev_node->next = NULL;
    //     return;
    //   }
  }
}

// place the union of src1 and src2 into dest
void listset_union(struct listset *dest, struct listset *src1, struct listset *src2)
{
  for (struct listnode *src1_node = src1->head; src1_node != NULL; src1_node = src1_node->next)
  {
    listset_add(dest, src1_node->str);
  }
  for (struct listnode *src2_node = src2->head; src2_node != NULL; src2_node = src2_node->next)
  {
    // if (!listset_lookup(dest, src2_node->str)) // listset_add takes care of this
    // {
      listset_add(dest, src2_node->str);
    // }
  }
  listset_reverse(dest);
  return;
  // listset_print(dest);
}

// place the intersection of src1 and src2 into dest
void listset_intersect(struct listset *dest, struct listset *src1, struct listset *src2)
{
  for (struct listnode *src1_node = src1->head; src1_node != NULL; src1_node = src1_node->next)
  {
    if (listset_lookup(src2, src1_node->str))
    {
      listset_add(dest, src1_node->str);
    }
  }
  listset_reverse(dest);
  return;
}

// return the number of items in the listset
int listset_cardinality(struct listset *this)
{
  if (this->head == NULL)
  {
    return 0;
  }
  else
  {
    struct listnode *node = malloc(sizeof(struct listnode));
    node = this->head;
    int count = 1;

    while (node->next != NULL)
    {
      node = node->next;
      count++;
    }
    return count;
  }
}

// print the elements of the list set
void listset_print(struct listset *this)
{
  struct listnode *p;

  for (p = this->head; p != NULL; p = p->next)
  {
    printf("%s, ", p->str);
  }
  printf("\n");
  // printf("%d cardinality\n", listset_cardinality(this));
}

void listset_reverse(struct listset *this)
{
  struct listnode * prev_node = NULL;
  struct listnode * next_node = NULL;

  for(  struct listnode * current_node = this->head;current_node != NULL;prev_node = current_node,current_node = next_node){
    // store the next ptr
    next_node = current_node->next;

    // reverse current node
    current_node->next = prev_node;
  } 

  this->head = prev_node;
  // printf("%d cardinality\n", listset_cardinality(this));
}


// Tests union and intersect explicity, even tests reverse implicitly
void test_union_intersection()
{
  struct listset *listset1 = listset_new();

  listset_add(listset1, "4");
  listset_add(listset1, "3");
  listset_add(listset1, "2");
  listset_add(listset1, "1");

  printf("listset1 - ");
  listset_print(listset1);
  printf("\n");

  struct listset *listset2 = listset_new();

  listset_add(listset2, "6");
  listset_add(listset2, "5");
  listset_add(listset2, "4");
  listset_add(listset2, "3");
  printf("listset2 - ");
  listset_print(listset2);
  printf("\n");

  struct listset *union_list = listset_new();
  listset_union(union_list, listset1, listset2);
  printf("Union - ");
  listset_print(union_list);
  printf("\n");

  struct listset *intersection_list = listset_new();
  listset_intersect(intersection_list, listset1, listset2);
  printf("Intersection - ");
  listset_print(intersection_list);
  printf("\n");
}