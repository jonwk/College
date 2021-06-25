# Contents 
```
bloom-filter
    Readme.md
    Makefile
    bitset.c
    bloom.c
    bitset.h
    bloom.h
└── test_files
        main.c
    └── test_inputs
            test_bitset_create_1.txt
            ...
            test_bloom_create_1.txt
            ...
    └── test_outputs
            test_bitset_create_1.txt
            ...
            test_bloom_create_1.txt
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
  

# Lab 5
  You should implement your bitset functions in bitset.c and your bloom functions in bloom.c.

### Compile and Run Tests

  Use to only compile your code run:
  ```
  make filter
  ```
  If the compilation is successfull then the executable filter will be create in your current directory.

  

  To compile and run the tests, run the command make.
  ```
  make
  ```

  If the command has correctly been executed a new directory "build" should be created under "test_files" and the file "results.txt".  You should find a summary of the test results in "results.txt". results.txt will give you an indication of your grades for the lab.  We will run more test for the final evaluation of your code and your final grades.

### Manual Testing of Filter

  If you want to run some manual tests, you can still invoke your program from the command line. 
  E.g.:
  ```
  ./filter bitset_create test_files/test_inputs/test_bitset_create_1.txt
  ```

### Submission of Bitset and Bloom Code
  Submit only the your source code (bitset.c and bloom.c) and header
  files (bitset.h, and bloom.h) in a tarball.  To create the tarball use the Makefile as follows.

  The Makefile can be used to create the submission archive with:

  ```
  make submission_archive
  ```
  If the command runs successfully then filter_submission.tar will be created.  Submit filter_submission.tar on BlackBoard.
