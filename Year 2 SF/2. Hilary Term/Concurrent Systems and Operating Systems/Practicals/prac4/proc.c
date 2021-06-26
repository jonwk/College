// +++++++ ONLY MODIFY BELOW LINE 39 BELOW
#include "types.h"
#include "defs.h"
#include <stdio.h>
#include "proc.h"

#define NCPU 1

struct cpu cpus[NCPU];
int ncpu;

void printstate(enum procstate pstate)
{ // DO NOT MODIFY
  switch (pstate)
  {
  case UNUSED:
    printf("UNUSED");
    break;
  case EMBRYO:
    printf("EMBRYO");
    break;
  case SLEEPING:
    printf("SLEEPING");
    break;
  case RUNNABLE:
    printf("RUNNABLE");
    break;
  case RUNNING:
    printf("RUNNING");
    break;
  case ZOMBIE:
    printf("ZOMBIE");
    break;
  default:
    printf("????????");
  }
}

// Per-CPU process scheduler.
// Each CPU calls scheduler() after setting itself up.
// Scheduler never returns.  It loops, doing:
//  - choose a process to run
//  - swtch to start running that process
//  - eventually that process transfers control
//      via swtch back to the scheduler.

// local to scheduler in xv6, but here we need them to persist outside,
// because while xv6 runs scheduler as a "coroutine" via swtch,
// here swtch is just a regular procedure call.
struct proc *p;
struct cpu *c = cpus;

//  Add in any global variables and functions you need below.
// +++++++ ONLY MODIFY BELOW THIS LINE ++++++++++++++++++++++

int run_count_arr[NPROC];
int init;

void initialiseTimes()
{
  for (int i = 0; i < NPROC; i++)
  {
    run_count_arr[i] = 0;
  }
  init = 1;
}

void scheduler(void)
{
  int runnableFound; // DO NOT MODIFY/DELETE

  if (!init)
    initialiseTimes();

  c->proc = 0;

  runnableFound = 1; // force one pass over ptable

  int least_runnable = -1;

  while (runnableFound)
  { // DO NOT MODIFY
    // Enable interrupts on this processor.
    // sti();
    runnableFound = 0; // DO NOT MODIFY
    // Loop over process table looking for process to run.
    // acquire(&ptable.lock);

    least_runnable++;
    int temp_least_runnable = least_runnable;

    for (p = ptable.proc; p < &ptable.proc[NPROC]; p++)
    {

      if (p->state != RUNNABLE)
        continue;

      if ((&ptable.proc[temp_least_runnable])->state != RUNNABLE || run_count_arr[p->pid] < run_count_arr[temp_least_runnable])
        temp_least_runnable = p->pid;

      runnableFound = 1; // DO NOT MODIFY/DELETE/BYPASS

      // Switch to chosen process.  It is the process's job
      // to release ptable.lock and then reacquire it
      // before jumping back to us.
      c->proc = p;
      //switchuvm(p);
      p->state = RUNNING;

      swtch(p);
      // p->state should not be running on return here.
      //switchkvm();
      // Process is done running for now.
      // It should have changed its p->state before coming back.
      c->proc = 0;
    }
    least_runnable = temp_least_runnable;
    // release(&ptable.lock);
  }
  printf("No RUNNABLE process!\n");
}
