#define NUM_THREADS 6


int x; // Global variable !!

mtype = { locked, unlocked };

mtype mutex = unlocked;
int mid = 0;
int init_id;

inline lock(m) { atomic{ (m==unlocked) -> m = locked; mid=_pid } }

inline unlock(m) {
  atomic{
    assert(mid==_pid);
    m = unlocked;
    mid=init_id
  }
}


proctype PrintHello(){
    int y ; // (Thread) local variable

    lock(mutex);
    y = x; // read that global
    printf("\n%d: Hello World, from butrfeld!\n",_pid);
    x = y+1; // write that global
    unlock(mutex);
}

init {
  init_id = _pid;
  mid = _pid;
  x=0;

  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  unlock(mutex); // should fail
  (_nr_pr == 1);
  printf("\nAll threads done by butrfeld, x = %d\n",x);
  assert(x ==6);
}
