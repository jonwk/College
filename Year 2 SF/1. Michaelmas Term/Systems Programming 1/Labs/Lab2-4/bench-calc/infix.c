// for each token in the input string {
//   if ( token is a number ) {
//     append token to the output string
//   }
//   else if (token is a left bracket) {
//     push bracket to stack
//   }
//   else if ( token in an operator ) {
//     while ( there is operator on top of stack with equal or higher precedence ) {
//       pop stack and append popped operator to output string
//     }
//     push token operator to stack
//   }
//   else if ( token is right bracket ) {
//     while ( top of stack != '(' ) {
//       pop operator from stack and append to output string
//     pop left bracket
//   }
// }
// pop remaining stack items and append each of them to the end of your reverse Polish notation expression.

#include "infix.h"

int isEmpty(struct double_stack *s)
{
  return s->top == -1;
}
int isNumber(char *in)
{
  return ((in[0] >= '0' && in[0] <= '9') || (in[1] >= '0' && in[1] <= '9'));
}
int isOperand(char op)
{
  return (op == '+' || op == '-' || op == 'X' || op == '/' || op == '^');
}
int precedence(char sign)
{
  switch (sign)
  {
  case '+':
  case '-':
    return 1;

  case 'X':
  case '*':
  case 'x':
  case '/':
    return 2;

  case '^':
    return 3;
  }
  return 0;
}
void pop_and_append(struct double_stack *st, char **str, int index)
{
  str[index] = malloc(sizeof(char) * 2);
  str[index][0] = (char)double_stack_pop(st);
  str[index][1] = '\0';
}
double evaluate_infix_expression(char **args, int nargs)
{
  struct double_stack *stack = double_stack_new(nargs - 1);
  memset(stack->items, 0, (nargs - 1) * sizeof(double));

  char **postfix_expression = malloc(sizeof(char *) * nargs);
  int pf_nargs = 0;

  for (int i = 0; i < nargs; i++)
  {
    if (isNumber(args[i]))
    {
      postfix_expression[pf_nargs++] = args[i];
    }
    else if (args[i][0] == '(')
    {
      double_stack_push(stack, args[i][0]);
    }
    else if (isOperand(args[i][0]))
    {
      while (precedence((char)stack->items[stack->top]) >= precedence(args[i][0]))
      {
        pop_and_append(stack, postfix_expression, pf_nargs++);
      }
      double_stack_push(stack, args[i][0]);
    }
    else if (args[i][0] == ')')
    {
      while ((char)(stack->items[stack->top]) != '(')
      {
        pop_and_append(stack, postfix_expression, pf_nargs++);
      }
      double_stack_pop(stack);
    }
  }
  while (!isEmpty(stack))
  {
    pop_and_append(stack, postfix_expression, pf_nargs++);
  }
  return evaluate_postfix_expression(postfix_expression, pf_nargs);
}