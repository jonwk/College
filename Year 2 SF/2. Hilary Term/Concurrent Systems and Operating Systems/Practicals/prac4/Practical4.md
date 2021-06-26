# Practical 4 - changing the **xv6** Scheduler.

This folder contains sources of a tool `sim` that simulates **xv6** from the perspective of the scheduler. That scheduler implements a round-robin policy.

Your task is to modify the scheduler to implement a simple load-balancing policy.

## Task

### Key constraint
 
You are only allowed to modify file `proc.c`, and in there, only the code from just before the definition of `void scheduler(void)` downwards.

### Task Description

The simple load-balancing policy is, whenever more than one process is `RUNNABLE`, then:

* select one that has been scheduled so far to run least often
* if there are several such processes then choose them in round-robin order.

(see below for some discussion of expected behaviour)

## Submission

Your submission on Blackboard should consist **only** of the file `proc.c`.

## Using `sim`

### Compiling `sim`


To build/re-build the simulator, simply give the command `make` (or its Windows equivalent).

To remove old compiled material (objects files and executables),
use command `make clean`. It is often a good idea to do this first.

Alternatively issue the following sequence of commands to build the code:

```
cc -c -o swtch.o swtch.c
cc -c -o proc.o proc.c
cc -c -o main.o main.c
cc -o sim swtch.o proc.o main.o 
```

### Running `sim`

The simulator requires a configuration file on standard input. These files have a format described in the file `Scheduler.md`, also in this folder. Two example files, `25CPU.txt` and `5WAIT.txt` are provided.

To run the simulator with `25CPU.txt` simply enter `./sim < 25CPU.txt` at the command line. Its output is sent to standard output and so will appear in the terminal window.

The files `fun25CPU.log` and `run5WAIT.log` contain a record of the outputs expected when using `25CPU.txt` or `5WAIT.txt` respectively, with the given round-robin scheduler.


## Expected behaviour

We present a number of simple scenarios first,
and then we discuss the examples defined in `25CPU.txt` and `5WAIT.txt`.

For the simple scenarios before, we assume that:

* there are 10 processes, P0..P9 - this is the round-robin order
* we have just returned to scheduler from a call it made to `swtch` (see `swtch.c`), and we know the `pid` of the process that has just run.

### Scenario 1

* `swtch` returns from running P5
* P3, P5, P7 are runnable, P3 has run 2 times, P5 and P7 have run 6 times

#### Round-Robin scheduler (runs P7)

P7 is next in round-robin order so it runs next

#### Load-Balancing Scheduler (runs P3)

P3 has the lowest number of runs, and is the only such process

### Scenario 2

* `swtch` returns from running P5
* P3, P5, P7 are runnable, P3 and P7 have run 2 times, P5 has run 6 times

#### Round-Robin scheduler (runs P7)

P7 is next in round-robin order so it runs next

#### Load-Balancing Scheduler (runs P7)

Both P3 and P7 have the lowest number of runs, and as P7 is before P3 in round-robin order, it gets to run.

### Scenario 3

* `swtch` returns from running P5
* P3, P5, P7 are runnable, P3 and P7 have run 6 times, P5 has run 2 times

#### Round-Robin scheduler (runs P7)

P7 is next in round-robin order so it runs next

#### Load-Balancing Scheduler (runs P5)

P5 has the lowest number of runs, and is the only such process

### Scenario `25CPU.txt`

Five processes, each of which does five `CPU` actions and then `EXIT`s.

The processes each perform 5 `CPU` and then `EXIT` in strict round-robin order, with ***either*** scheduler
(round-robin, load-balancing give same outcome). 

### Scenario `5WAIT.txt`

Seven processes:

* *P0* : performs 10 `CPU` actions and then `EXIT`s.
* *P1-P5* : starts with a `WAIT` action, then does 9 `CPU` actions and `EXIT`s.
* *P6* : does 5 `WAKE` actions, for *P1-P5* in that order, then 5 `CPU` actions and `EXIT`s.

The behaviour of the round-robin scheduler can be seen in `run5WAIT.txt`.
Note that awakened processes have to wait their round-robin turn.

With the load-balancing scheduler, we expect to see *Pn* scheduled at least once,perhaps more,
immediately after the run of *P6* that performed `WAKE n`, or possibly after a run of P0 (a.k.a. `GOGO_0`). 




