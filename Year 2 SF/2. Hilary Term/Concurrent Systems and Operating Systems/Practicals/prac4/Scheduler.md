# Scheduler Enhancements

We define a simple tex-based file-format to initialise the process tables.

## Process behaviour

For each process setup, we represent its code as a sequence of actions,
each describing the overall behaviour of one instance of being in the `RUNNING` state.

The running of the scheduler is then determined by that, and continues until there is no `RUNNABLE` process,
or a pre-determined limit on the number of process switches has been reached.

### Process States

The only process states we consider are `UNUSED` (0), `SLEEPING` (2), `RUNNABLE` (3), `RUNNING` (4), and `ZOMBIE` (5).

### Process Actions

We take a very abstract view of a process run:

* `CPU` is a CPU-bound run of the process that ends with a timeout as it uses all of its time quantum.
* `WAIT` is a run of the process that ends because it calls an O/S service (e.g. I/O) that requires it to wait.
* `WAKE p` is a run of the process in which it sends a signal to another process `p` to wake it up.
* `FORK p` is a run of the process in which it forks a new process `p` (not implemented at present).
* `KILL` is a run of the process were it kills of another process `p`.
* `EXIT` is a run of the process in which it terminates itself.


## File structure

File top-level, where `0 <= l <= h <= NPROC-1`:

    swtchLimit
    <ptable[l]>
    <pactions.l>
    ...
    <ptable[h]>
    <pactions.h>

Process table entry:

    <ptable[p]> = PROC p sz pstate parent killed name
    
Process action entry:

    <pactions.p> = ACT p <act[0]> .. <act[n-1]> EXIT
    
Action:

    <act[i]> = EXIT // done, becomes ZOMBIE
             | CPU  // cpu-bound, stays RUNNABLE
             | WAIT // waiting, becomes SLEEPING
             | WAKE p // wakes process p, stays RUNNABLE
             | FORK p // forks process p, stays RUNNABLE
             | KILL p // kills process p, stays RUNNABLE

At present all are implemented except `FORK`.