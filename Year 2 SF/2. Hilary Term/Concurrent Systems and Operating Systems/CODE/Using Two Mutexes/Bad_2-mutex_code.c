// Example showing deadlock even with proper mutex usage.
// Copyright (2020) Andrew Butterfield, Trinity College Dublin

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

int resource1, resource2 ; // Two global resources

pthread_mutex_t m1 = PTHREAD_MUTEX_INITIALIZER ; // Protects resource1
pthread_mutex_t m2 = PTHREAD_MUTEX_INITIALIZER ; // Protects resource2


// Going Up: sets r1 = min(r1,r2), r2 = r1+r2
void *GoingUp(void *a) {
    
    int tmp;
    
    pthread_mutex_lock(&m1);
    printf("UP has m1, looking for m2 now\n");
    pthread_mutex_lock(&m2);
    printf("UP has m1 and m2\n");

    tmp = resource1 + resource2;
    if (resource2 < resource1) { resource1 = resource2 ;}
    resource2 = tmp;
    
    // printf("UP r1,r2 = %d,%d\n",resource1,resource2);
    pthread_mutex_unlock(&m2);
    pthread_mutex_unlock(&m1);

    pthread_exit(NULL);
}

// Going Down: sets r1 = max(r1,r2), r2 = max(r1,r2)-min(r1,r2)
void *GoingDown(void *a) {
    
    int tmp;
    
    pthread_mutex_lock(&m2);
    printf("DOWN has m2, looking for m1 now\n");
    pthread_mutex_lock(&m1);
    printf("DOWN has m2 and m1\n");
    
    
    if (resource2 < resource1) {
        // max is resource1
        resource2 = resource1 - resource2;
    } else {
        // max is resource 2
        tmp = resource2 - resource1;
        resource1 = resource2;
        resource2 = tmp;
    }
    // printf("DOWN r1,r2 = %d,%d\n",resource1,resource2);
    
    pthread_mutex_unlock(&m1);
    pthread_mutex_unlock(&m2);

    pthread_exit(NULL);
}




int main (int argc, const char * argv[]) {
	static pthread_t goingup,goingdown ;
	long rc;

    resource1 = 13; resource2 = 42;
    printf("r1,r2 = %d,%d\n", resource1, resource2);

    printf("Creating GoingUp:\n");
 	rc = pthread_create(&goingup,NULL,GoingUp,(void *)0);
	if (rc) {
			printf("ERROR return code from pthread_create(goingup): %ld\n",rc);
			exit(-1);
		}
    printf("Creating GoingDown:\n");
 	rc = pthread_create(&goingdown,NULL,GoingDown,(void *)0);
	if (rc) {
			printf("ERROR return code from pthread_create(goingdown): %ld\n",rc);
			exit(-1);
		}
    printf("Waiting to join threads....\n");
    pthread_join( goingdown, NULL);
    pthread_join( goingup, NULL);
    
    printf("R1,R2 = %d,%d\n", resource1, resource2);
    printf("All Done!\n");

	return 0;
}