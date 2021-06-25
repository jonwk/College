#include "postfix.h"

int isNum(char * s){
  if (((s[0] >= '0' && s[0] <= '9' ) || (s[1] >= '0' && s[1] <= '9'))) return 1;
  else return 0;
}

// evaluate expression stored as an array of string tokens
double evaluate_postfix_expression(char ** args, int nargs) {
  // Write your code here
   struct double_stack * numstack = double_stack_new(nargs-1);
   memset(numstack->items, 0, (nargs-1) * sizeof(double));

   double num1, num2;

   for (int i=0; i<nargs; i++) {

    if(isNum(args[i])){
      double_stack_push(numstack,atof(args[i]));
    }
    else if(args[i][0]=='+'||args[i][0]=='-'||args[i][0]=='X'||args[i][0]=='/'||args[i][0]=='^'){
      num1 = double_stack_pop(numstack);
      num2 = double_stack_pop(numstack);
      switch(args[i][0]){
        case '+' : 
        printf("Adding %f and %f\n", num2,num1);
        double_stack_push(numstack,num2 + num1) ; 
        break;
        case '-' :
        printf("Subtracting %f and %f\n", num2,num1); 
        double_stack_push(numstack,num2 - num1) ; 
        break;
        case 'X' : 
        printf("Multiplying %f and %f\n", num2,num1);        
        double_stack_push(numstack,num2 * num1) ; 
        break;
        case '/' :
        printf("Dividing %f and %f\n", num2,num1);
        double_stack_push(numstack,num2 / num1) ; 
        break;
        case '^' : 
        printf("Powering %f and %f\n", num2,num1);
        double_stack_push(numstack,pow(num2, num1)) ; 
        break;
      }
    }
  }
  return double_stack_pop(numstack); 
}

