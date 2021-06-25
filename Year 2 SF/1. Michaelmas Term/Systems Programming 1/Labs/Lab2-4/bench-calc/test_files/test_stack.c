#include "stack.h"
#include <string.h>

int main(int argc, char ** argv) {
  struct double_stack * stack = double_stack_new(argc -1);
  memset(stack->items, 0, (argc -1) * sizeof(double));
  double element;
  for (int i=1; i<argc; i++) {
    element = atof(argv[i]);
    double_stack_push(stack, element);
    printf("Pushed %f to stack\n", element);
  }
  for (int i=argc-1; i>0; i--) {
    element = double_stack_pop(stack);
    printf("Popped %f from stack\n", element);
    if (atof(argv[i]) != element) exit(-1);
  }
  exit(0);
}
