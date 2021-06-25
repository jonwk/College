# Contents 
```
bench-calc
    Readme.md
    Makefile
    stack.c
    postfix.c
    infix.c
    stack.h
    postfix.h
    infix.h
└── test_files
        main.c
        test_stack.c
    └── test_inputs
            test_postfix_1.txt
            ...
            test_infix_1.txt
            ...
    └── test_outputs
            test_postfix_1.txt
            ...
            test_infix_1.txt
            ...
```
  
  You have been provided with a Makefile and C main functions to test your code
  as well as test inputs and expected outputs.  **Do not modify the Makefile and
  the source code provided to you.  For the final submission the Makefile and
  main.c program will be overwritten.**

# Header Files
  Sample header files are given to you.  You can edit the header files however
  you must keep the same function signature to be able to run the automatic
  tests. **Do not delete the function declarations in the .h files.**  
  Include all your ".h" required for your code in their header file and include
  that header file in your .c.
  
## stack.h Snippet

```c
  #include <stdlib.h>
  #include <stdio.h>
  #include <assert.h>
  ...
  function declarations
  ...
```
## stack.c Snippet

```c
  #include "stack.h"
  ...
  function implementation
  ...
```

# Lab 2
  You should implement your stack functions for lab 2 in a file called stack.c.
```c
  /* create a new empty stack */
  struct double_stack * double_stack_new(int max_size);

  /* push a value onto the stack */
  void double_stack_push(struct double_stack * this, double value);
  
  /* pop a value from the stack */
  double double_stack_pop(struct double_stack * this);
```
  
  You can test your stack functions by using it in test_files/test_stack.c.  

  To compile test_stack.c along with stack.c run: 
  ```
  make test_stack
  ```
  If the compilation has been successful, then test_stack should now be present in bench-calc.
  Example of how to run test_stack:
  ```
  ./test_stack 3.4 6 -1
  ```
  test_stack.c will *not* be used for grading, hence you can modify it to test your stack functions.
  Here is an example of a simple main to understand commandline parameters:
  
  ```c
  int main(int argc, char ** argv) { 
   int i; 
   if ( argc == 1 ) { 
     printf("Please try adding some command-line parameters\n"); 
     printf("Usage: %s <param1> <param2> ...\n", argv[0]); 
     exit(1); 
   } 
   printf("The number of command line parameters is stored in argc: %d\n", argc); 
   printf("The value of argc counts the name of the program itself as a parameter\n"); 
   printf("The name of the program is stored in argv[0]: %s\n", argv[0]); 
   printf("The parameters are:\n");
   for ( i = 1; i < argc; i++ ) {
     printf("%s\n", argv[i]);
   }
   return 0; 
  } // end of main 
  ```

# Postfix and Infix labs
  You should implement your postfix and infix functions in postfix.c and infix.c respectively.

### Evaluation of Postfix and Infix Code
  Run the Makefile on macneill.

  Run the command make.
  ```
  make
  ```

  If the command has correctly been executed a new directory "build" should be created under "test_files" and the file "results.txt".  You should find a summary of the test results in "results.txt". results.txt will give you an indication of your grades for the lab.  We will run more test for the final evaluation of your code and your final grades.

### Manual Testing of Postfix and Infix Code

  If you want to run some manual tests, you can still invoke your program from the command line.  The compiler outputs are in test_files/build and is called calc.

### Submission of Postfix and Infix Code
  Submit only the your source code (stack.c, postfix.c and infix.c) and header
  files (stack.h, postfix.h, and infix.h). 

  The Makefile can be used to create the submission archive with:

  ```
  make submission_archive
  ```
  
