/* A "Hello World" Promela model for SPIN. */

active proctype Hello() {
    printf("Hello process, my pid is: %d\n", _pid);
}

init {
    int lastpid;
    printf("init process, my pid is: %d\n", _pid);
    lastpid = run Hello();
    printf("next pid was: %d\n", lastpid);
    lastpid = run Hello();
    printf("last pid was: %d\n", lastpid);
}
