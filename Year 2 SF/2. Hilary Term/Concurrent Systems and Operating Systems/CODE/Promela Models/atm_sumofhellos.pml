#define NUM_THREADS 6


int x; // Global variable !!

proctype PrintHello(){
    int y ; // (Thread) local variable

    atomic{
      y = x; // read that global
      printf("\n%d: Hello World, from John !\n",_pid);
      x = y+1; // write that global
    }
}

init {
  x=0;
  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  (_nr_pr == 1);
  printf("\nAll threads done by John, x = %d\n",x);
  assert(x ==6);
}
