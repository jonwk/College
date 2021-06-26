#define NUM_THREADS 6


int x; // Global variable !!

proctype PrintHello(){
    int y ; // (Thread) local variable

    y = x; // read that global
    printf("\n%d: Hello World, from butrfeld!\n",_pid);
    x = y+1; // write that global
}

init {
  x=0;
  run PrintHello(); // corresponds to pthread_create calls
  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  run PrintHello();
  (_nr_pr == 1);    // same overall effect as pthread_join calls
  printf("\nAll threads done by butrfeld, x = %d\n",x);
  assert(x==6)
}
