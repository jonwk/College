# Q1.Using 10,000 random samples, estimate the number of required dice rolling to get each face at least once. 

# Initializing list to get the number of required dice rolling to get each face at least once 200 times.
Min_Dice_Rollings<-rep(0,200)

for (i in 1:200){
  # Creating a Sample Set which simulates the dice being rolled 10000 times
  dice_rolls <- sample(1:6, size = 10000, replace = TRUE)
  
  # Initializing a boolean list of 6 elements which represent all the faces of a die
  # The elements in this list will be turned into true when we get that face while we roll the die
  rolls<-rep(FALSE,6)
  
  # Initializing a variable that will be used to note the number of required dice rolling to get each face at least once
  Dice_Rollings<-0
  
  for(j in 1:10000){
    
    # Checking if we have rolled that face already
    if(rolls[dice_rolls[j]]==FALSE){
      # if we haven't rolled it already then we set it to true, denoting the fact that we have rolled it already for the further rolls
      rolls[dice_rolls[j]]<-TRUE
    }
    # Checking if we have rolled all the faces
    else if(all(rolls)==TRUE){
      # The moment we roll all he faces, the corresponding index will be noted into a variable and break out of the for loop
      Dice_Rollings<-(j-1)
      # cat("\nturn-",i,"\n",dice_rolls[1:(j-1)],"\nno.of turns",j-1)
      break
    }
  }
  # we note how many dice rollings it took for us this time into the list
  Min_Dice_Rollings[i]<-Dice_Rollings
}
# print(Min_Dice_Rollings)

# Here is the mean of the results from 200 iterations
print(mean(Min_Dice_Rollings))

# ----------------------------------------------------------------------------------------------------------------------------------

# Q2.  Assume that k cards, numbered from 1 to k, are place on a desk, faced down. You are going to guess the numbers on the cards. 
# You would randomly guess the cards by saying numbers between 1 to k. Obviously, you would use each number once to guess. 
# Therefore, a guess such 2,2, ., 2 is not acceptable!
# For k=6, 7, 8, 9, 10, and using 10,000 random samples, estimate the probability that none of the numbers that you guess are correct. 
# Plot the result of probabilities vs. k.

# mean_wrong_guesses:
#  - A function that takes in the value of K and returns the mean of the probability that none of the numbers that are guessed are correct over 200 iterations.. 
#  - Parameters: k
#  - Return Value: the mean of the probability that none of the numbers that are guessed are correct over 200 iterations. 

mean_wrong_guesses <- function(k) {
  # Initializing list to store the probabilities of having no correct guesses over 200 iterations.
  Wrong_Guesses_Probabilities<-rep(0,200)
  # Iterating over 200 times
  for(j in 1:200){
    
    # assuming all the 10000 guesses are wrong
    Wrong_Guesses<-10000
    
    for(i in 1:10000){
      # generating a sample set that simulates a randomly shuffled deck of cards
      cards<-sample(1:k,size=k,replace=FALSE)
      
      # generating a sample set the user guessing cards
      guesses<-sample(1:k,size=k,replace=FALSE)
      
      # Comparing guesses to cards
      for(l in 1:k){
        #checking if any card is same and breaking after the for loop when we find even 1 same card as we are looking to have all wrong guesses
        if(cards[l]==guesses[l]){
          # subtracting 1 from Wrong_guesses as we don't have all wrong guesses this time
          Wrong_Guesses<-Wrong_Guesses-1
          break 
        }
      }
    }
    # Probability = ( favorable outcomes)/(total trails or outcomes)
    Wrong_Guesses_Probabilities[j]<-Wrong_Guesses/10000
  }
  # cat("\nK-",k," mean-",mean(Wrong_Guesses_Probabilities),"\n")
  return(mean(Wrong_Guesses_Probabilities))
}  

# Generating a list of mean wrong guesses where k=6,7,8,9,10 which will be plotted along the y axis of the graph
list_wrong_means<-c(mean_wrong_guesses(k=6),mean_wrong_guesses(k=7),mean_wrong_guesses(k=8),mean_wrong_guesses(k=9),mean_wrong_guesses(k=10))

# Creating a list of k values that will be plotted along x axis of the graph
k_vals<-c(6,7,8,9,10)

# Plotting a graph with all the corresponding values and labels
plot(k_vals,list_wrong_means,xlab = "k cards",ylab ="mean")
# print('plotting done')
