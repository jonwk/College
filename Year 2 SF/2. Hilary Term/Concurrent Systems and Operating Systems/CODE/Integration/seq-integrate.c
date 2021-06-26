
#include <stdio.h>

#define NUM_SLICES 1000
#define H 4.0/NUM_SLICES // interatinh of interval [0.0,4.0]

double answer;

double f(double x) { return (16.0 - x*x) ; }

// expected answer:  42.6666...

double trapezoid(double a, double b) { return H*(f(a)+f(b))/2.0; }

int main (int argc, const char * argv[]) {
  answer   = 0.0;
  double a,b,area ;
  int i ;
  for (i=0;i<NUM_SLICES;i++) {
    a = (int)i * H ;
    b = a + H;
    area = trapezoid(a,b);
      answer = answer + area;
   }
  printf("\nAnswer is %f\n",answer);
	return 0;
}