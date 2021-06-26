// concurrent integration with no mutex for answer

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define NUM_SLICES 1000
#define H 4.0/NUM_SLICES

double answer;

double f(double x) {
  return (16.0 - x*x) ;
}

double trapezoid(double a, double b) {
  return H*(f(a)+f(b))/2.0;
}

void *IntegratePart(void *i) {

  double a,b,area;

  a = (int)i * H ;
  b = a + H;
  area = trapezoid(a,b);

  // critical section with no mutex !!!!!
  answer=answer+area;

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
	for(t=0;t<NUM_SLICES;t++) {
		pthread_join( threads[t], NULL);
	}
  printf("%f\n",answer);
	return 0;
}