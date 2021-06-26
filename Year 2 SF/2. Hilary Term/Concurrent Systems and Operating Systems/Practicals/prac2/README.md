This practical is worth 6% of your year-end mark.  It is an exercise in using condition variables and mutexes in a producer/consumer system. 

1. A single producer thread will continuously update a single global variable **pnum** with a sequence of different non-zero integer values. It will signal completion by setting that global variable to zero. Once set to zero, its value will remain zero.

2. In between each producer update, if the value of the global variable pnum satisfies a specific predicate (see below) then a consumer thread will read that value and add it into a global variable **csum** dedicated for its own use. It will also terminate when the global variable **pnum** set by the producer has become zero.

When both produce and consumer threads are done, the value of the consumer global variable **csum** will be output.

You are given four files:

1. `cond.c` - this provides three different functions that compute a condition on their integer input (DO NOT CHANGE).
2. `1to10.txt` - this contains numbers from 1 to 10 followed by a zero. It is a convenient way to get data into the main program.
3. `1to20.txt` - this contains numbers from 1 to 20 followed by a zero. It is a convenient way to get data into the main program.
4. `practical2.c` - the main program. It accepts a stream of integers separated by white space on standard input, and an optional command line argument that specifies which of the condition functions in cond.c should be used (cond1() is the default).

To compile, use `cc -o p2 practical2.c -pthread`

To run for 1..20 with the default condition (cond1()) :          `./p2 < 1to20.txt    or      ./p2 1 < 1to20.txt`

To run for 1..10 with  condition cond2():                             `./p2 2 < 1to10.txt`

To run for 1..20 with condition cond3():                              `./p2 3 < 1to20.txt`

If you just run `./p2` then you will get an endless stream of print statements from the consumer, busily multiplying 999 by infinity by the process of repeated addition !

Note that the number argument to select the condition needs to occur between `./p2` and `<` .

- The program as given launches both a producer and consumer thread, but they use no form of thread synchronisation. Your task is to use mutexes and condition variables to ensure the right answer is produced.

- Do not move, delete, or modify, any printf statement that outputs a string starting with "@" !

- You may add your own **printf** statements, but they should **not** produce lines that start with "@".

- You will need to declare extra global variables, and modify the program mainline, as well as some or all of  produceT, Produce, consumeT and Consume. You should be adding declarations and statements,

To submit, just upload your modified practical2.c file (no zip/tar/gzip/log files required).

