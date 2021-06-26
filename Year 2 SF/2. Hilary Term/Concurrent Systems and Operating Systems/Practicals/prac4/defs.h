struct proc;

// proc.c
enum procstate;
void printstate(enum procstate pstate);
struct proc *p;
struct cpu *c;
void scheduler(void);

// swtch.S
void swtch(struct proc *p);
uint swtchLimit ;

int initpactions();
int readactions();
