#include "types.h"
#include "defs.h"
#include "proc.h"
#include <stdio.h>
#include <string.h>

void initptable () {
  struct proc *p;

  for(p = ptable.proc; p < &ptable.proc[NPROC]; p++){
    p->sz  = 0;
    p->state = UNUSED;
    p->pid  = 0;
    p->parent = NULL;
    p->killed = 0;
    p->name[0] = 0;
  }
}

int readproc() { // returns 0 if not a proc, 1 otherwise
  int l_p; // ptable index
  uint l_sz;                     // Size of process memory (bytes)
  int l_state;        // Process state
  int l_parent_p;         // Parent process ptable index
  int l_killed;                  // If non-zero, have been killed
  char l_name[17];               // Process name (debugging)
  char stuff[6];

  int rc;
  struct proc *p;
  int prc;

  l_p=999; l_sz=999; l_state=999; l_parent_p=999; l_killed=999;

#define EXPECTED 7
// PROC 3 10 3 -1 0 proc1
  rc = scanf( "%s %d %u %d %d %d %s"
    , stuff, &l_p, &l_sz, &l_state, &l_parent_p, &l_killed, l_name );
  // printf("rc=%d, stuff=%s\n",rc,stuff);
  if(rc==EXPECTED && strcmp(stuff,"PROC")==0){
    prc=1;
    printf( "PROCESS %u ", l_sz);
    printstate(l_state);
    printf( " 0x%x 0x%x %d '%s'\n", l_p, l_parent_p, l_killed, l_name);
    p = &ptable.proc[l_p]; p->pid  = l_p; // VERY IMPORTANT!!
    p->sz  = l_sz;
    p->state = l_state;
    p->parent = l_parent_p == -1 ? NULL : &ptable.proc[l_parent_p];
    p->killed = l_killed;
    strcpy(p->name,l_name);
  } else {
    if(rc>=0) {printf("Expected %d items, found %d\n", EXPECTED, rc );}
    prc=0;
  }
  return prc;
}

void loadtables() {

  scanf("%u",&swtchLimit);
  while(readproc()){
    readactions();
  }
}

int main (int argc, const char * argv[]) {

  printf("\n\tWelcome to the xv6 Scheduler Simulator!\n\n");
  initptable();
  initpactions();
  loadtables();
  scheduler();
  return 0;

}
