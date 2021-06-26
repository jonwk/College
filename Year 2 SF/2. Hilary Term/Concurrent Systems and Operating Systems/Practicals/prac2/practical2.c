#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include <time.h>
#include <unistd.h>
#include "cond.c"


int pnum;  // number updated when producer runs.
int csum;  // sum computed using pnum when consumer runs.

int (*pred)(int); // predicate indicating if pnum is to be consumed


pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
int isProducerActive ; // One global variable, used to check if producer is running,

int produceT() {
  scanf("%d",&pnum); // read a number from stdin
  return pnum;
}

void *Produce(void *a) {
  int p;

  p=1;
  while (p) {
    pthread_mutex_lock(&mutex);

    printf("@P-READY\n");
    p = produceT();

    pthread_cond_signal(&cond);
    printf("@PRODUCED %d\n",p);

    isProducerActive = 0;
    // waiting till the consumer is not active, 
    // consumer being active translates to the producer not being active
    while (isProducerActive == 0)
      pthread_cond_wait(&cond, &mutex);
    pthread_mutex_unlock(&mutex);
  }
  printf("@P-EXIT\n");
  pthread_exit(NULL);
}


int consumeT() {
  if ( pred(pnum) ) { csum += pnum; }
  return pnum;
}

void *Consume(void *a) {
  int p;

  p=1;
  while (p) {
    pthread_mutex_lock(&mutex);

    // waiting till the producer is not active, so that the consumer can start
    while (isProducerActive == 1)
      pthread_cond_wait(&cond, &mutex);

    printf("@C-READY\n");
    p = consumeT();
    printf("@CONSUMED %d\n",csum);

    pthread_cond_signal(&cond);
    isProducerActive = 1;
    pthread_mutex_unlock(&mutex);
  }
  printf("@C-EXIT\n");
  pthread_exit(NULL);
}


int main (int argc, const char * argv[]) {
  // the current number predicate
  static pthread_t prod,cons;
	long rc;

  pred = &cond1;
  if (argc>1) {
    if      (!strncmp(argv[1],"2",10)) { pred = &cond2; }
    else if (!strncmp(argv[1],"3",10)) { pred = &cond3; }
  }


  pnum = 999;
  csum=0;
  srand(time(0));

  printf("@P-CREATE\n");
 	rc = pthread_create(&prod,NULL,Produce,(void *)0);
	if (rc) {
			printf("@P-ERROR %ld\n",rc);
			exit(-1);
		}
  printf("@C-CREATE\n");
 	rc = pthread_create(&cons,NULL,Consume,(void *)0);
	if (rc) {
			printf("@C-ERROR %ld\n",rc);
			exit(-1);
		}

  printf("@P-JOIN\n");
  pthread_join( prod, NULL);
  printf("@C-JOIN\n");
  pthread_join( cons, NULL);


  printf("@CSUM=%d.\n",csum);

  return 0;
}


