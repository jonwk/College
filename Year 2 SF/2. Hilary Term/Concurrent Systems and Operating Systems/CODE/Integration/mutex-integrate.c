// concurrent integration with mutex for answer

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define checkResults(string, val) {             \
if (val) {                                     \
printf("Failed with %d at %s", val, string); \
exit(1);                                     \
}                                              \
}

#define NUM_SLICES 1000
#define H 4.0/NUM_SLICES

double answer;

double f(double x) {
  return (16.0 - x*x) ;
}

double trapezoid(double a, double b) {
  return H*(f(a)+f(b))/2.0;
}

pthread_mutex_t  mutex = PTHREAD_MUTEX_INITIALIZER;

void *IntegratePart(void *i) {

  double a,b,area;
  int rc;

  a = (int)i * H ;
  b = a + H;
  area = trapezoid(a,b);

  // critical section with mutex
  rc = pthread_mutex_lock(&mutex);
	checkResults("pthread_mutex_lock()\n", rc);

  answer=answer+area;

  rc = pthread_mutex_unlock(&mutex);
	checkResults("pthread_mutex_lock()\n",rc);

	pthread_exit(NULL);
}

int main (int argc, const char * argv[]) {
	pthread_t threads[NUM_SLICES];
	long rc,t;
  answer = 0.0;
	for (t=0;t<NUM_SLICES;t++) {
 		rc = pthread_create(&threads[t],NULL,IntegratePart,(void *)t);
		if (rc) {
			printf("ERROR return code from pthread_create(): %ld\n",rc);
			exit(-1);
		}
	}
	// wait for threads to exit
	for(t=0;t<NUM_SLICES;t++) {
		pthread_join( threads[t], NULL);
	}
  //printf("\nAnswer is %f\n",answer);
  printf("%f\n",answer);
	return 0;
}