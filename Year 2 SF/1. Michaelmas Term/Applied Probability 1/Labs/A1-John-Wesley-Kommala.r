# Q1.By generating 10,000 pairs of samples, find the probability that 2 randomly chosen integers are relatively prime (coprime). You are required to calculate the mean of results for 200 iterations.


library(numbers)

# generating 10,000 pairs of samples from 1 to 1 Billion, assuming that finding co primes past about 10 Billion is sparse
RandomSamples<- sample(1:1000000000,size = 10000)

# Initializing a boolean list to check if 2 randomly chosen integers are relatively prime (coprime) 200 times.
results<-rep(FALSE,200)


for (i in 1:200){
  # Choosing two random integers from the Sample generated initially
  int1<-sample(RandomSamples, 2)[1]
  int2<-sample(RandomSamples, 2)[2]
  
  # Storing the boolean value returned by the coprime function by checking the two randomly chosen integers
  results[i]<-coprime(int1,int2)
  
  # This is the code to see the values in the lists and variables
  # if(results[i]==TRUE){
  # print("coprime"int1)
  # cat(" \n coprimes", int1,int2)
  # }
}

# print(results)

# Here is the mean of the results from 200 iterations
print(mean(results))







