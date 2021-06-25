# ------------------- Question 1 -------------------

# gets the percentage shown in question for k lamps that have lifetimes in an exponential distribution
get_P <- function(k) {
  avg_hrs <- 24000
  T_values<-rexp(n=10000,rate=1/avg_hrs)
  Total_T<-sum(T_values[1:k])
  return((T_values[1]/Total_T)*100)
}

# gets the mean percentage over 200 iterations
get_200_mean_percentage <-function(k){
  itr<-c(200)
  for(i in 1:200){
    itr[i]<-get_P(k)
  } 
  return(mean(itr))
}

# generates y values corresponding to the given x values for the plot
get_y_vals<-function(x_vals){
  x_len<-length(x_vals)
  y_vals<-c(x_len)
  for(i in 1:x_len){
    k<-x_vals[i]
    y_vals[i]<-get_200_mean_percentage(k)
  }
  return(y_vals)
}

x_values<-c(2,3,4,5)
y_values<-get_y_vals(x_values)

plot(x_values, y_values, xlab = "k",ylab = "percentage")
title("Question 1")

# ------------------- Question 2 -------------------

# gets P(a<X<b) for a given normal distribution
get_prob<-function(mean,sd,a,b){
  prob_a_X_b<-pnorm(b,mean=mean,sd=sd) - pnorm(a,mean=mean,sd=sd)
  return(prob_a_X_b)
}

# gets the value of σ^2 for which P(a<X<b) is maximum
# I generate random values for sd to be used in normal distribution
get_var_max_prob<-function(n,mean,a,b){
  sd_s<-runif(n,min=0,max=1000)
  variances<-c(n)
  probs<-c(n)
  for(i in 1:n){
    probs[i]<-get_prob(mean=mean,sd=sd_s[i],a=a,b=b)
    variances[i]<-(sd_s[i]**2)
  }
  max_prob_index<-which.max(probs)
  var_of_max_prob<-variances[max_prob_index]
  return(var_of_max_prob)
}


# gets the mean σ^2 over 200 iterations
get_200_mean_variance<-function(n,mean,a,b){
  max_var<-c()
  for(z in 1:200){
    max_var[z]<-get_var_max_prob(n=n,mean=mean,a=a,b=b)
    # cat(z,"variance",max_var[z],"\n")
  }
  return(mean(max_var))
}

a<-1
b<-2
mean<-0
n<-10000
mean_max_var<-get_200_mean_variance(n=n,mean=mean,a=a,b=b)
cat("Question 2, mean of the value of σ^2 for which P(a<X<b) is maximum over 200 iterations -",mean_max_var)