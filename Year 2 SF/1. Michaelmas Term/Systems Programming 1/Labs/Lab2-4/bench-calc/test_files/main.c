#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "stack.h"
#include "postfix.h"
#include "infix.h"

int main(int argc, char ** argv) {
  if ( argc == 1 ) {
    // command line contains only the name of the program
    printf("Error: No command line parameters provided\n");
    printf("Usage: %s postfix|infix <expression>\n", argv[0]);
    exit(1);
  }
  else if ( argc == 2 ) {
    // command line contains name of prog and one other parameter
    printf("Error: No expression to evaluate provided\n");
    printf("Usage: %s postfix|infix <expression>\n", argv[0]);
    exit(1);
  }
  else {
    // command line has enough parameters for an expression
    double result;
    if ( strcmp(argv[1], "postfix") == 0 ) {
      // pass the command line parameters, but with the first two removed
      result = evaluate_postfix_expression(argv+2, argc-2);
      printf("\nResult is %lf\n", result);
    }
    else if ( strcmp(argv[1], "infix") == 0 ) {
      // pass the command line parameters, but with the first two removed
      result = evaluate_infix_expression(argv+2, argc-2);
      printf("\nResult is %lf\n", result);
    }
    else {
      printf("Error: You must specify whether the expression is infix or postfix\n");
      printf("Usage: %s postfix|infix <expression>\n", argv[0]);
      exit(1);
    }

    return 0;
  }
}
