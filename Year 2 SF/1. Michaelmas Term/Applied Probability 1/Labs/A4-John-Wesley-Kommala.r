# ------------------- Question 1 -------------------
probs<-c()

for(i in 1:200){
  # generating random value for lambda
  lambda<-rexp(1)
  # for 10000
  n<-10000
  # time it will take for that customer to leave the shop (using rexp())
  customer_time_spent<- rexp(n,rate=lam)
  # how many customers arrive during that time (using rpois()), when people leave
  arrivals<-rpois(customer_time_served,lambda = lam)
  # finding probability of values in arrivals <= 2
  probs[i]<-length(which(arrivals <= 2)) /length(arrivals)
  # cat(i,"prob",probs[i],"\n")
}
answer<-mean(probs)
cat("Question1,the mean probability that while a particular customer is shopping, maximum 2 other customer arrive over 200 iterations is",answer,"\n")


# ------------------- Question 2 -------------------
# For each value of a=1,…,5, and 
# Plot you answer for E[Y] verses a .

# gets E[Y] using 10,000 random samples of X, 
# where X~U(0,a) , Y = X if X < (a/2) else (a/2)
get_Ey <- function(a) {
  x<-runif(10000,min=0,max=a)
  for(i in 1:10000){
    if(x[i] < (a/2)){
      y[i]<-x[i]
    }
    else {
      y[i]<-(a/2)
    }
  }
  return(mean(y))
}

# gets the mean E[Y] over 200 iterations
get_mean_200_Ey <- function(a){
  a_200<-c()
  for(i in 1:200){
    a_200[i]<-get_Ey(a)
  }
  return(mean(a_200))
}


# generates y values corresponding to the given x values for the plot 
get_y_vals<-function(x_vals){
  x_len<-length(x_vals)
  y_vals<-c(x_len)
  for(i in 1:x_len){
    a<-x_vals[i]
    y_vals[i]<-get_mean_200_Ey(a)
  }
  return(y_vals)
}

x_values<-c(1,2,3,4,5)
y_values<-get_y_vals(x_values)
plot(x_vals, y_vals,xlab = "a",ylab = "E[Y]")
title("Question 2")