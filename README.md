# backdrop


About My Project.
I used an in-memory database (h2 database) for saving registered users. I used name of users to search for them in the database because I do not have a unique property 
such as email/username, ideally, an email or username is to be used when searching for a user. The name was only used to test the functionality 
of the features I implemented. I also used Junit for testing the functionalities. I couldn't use my paystack API public key to communicate with the paystack API, I had 
to use my secret key. This shouldn't have been pushed to GitHub alongside the project and instead put in a .env file. I only pushed it so that the whole functionality
could be tested without any issue.


Reasons why pure Levenshtein Distance algorithm is more effective in this scenario is because the values compared do
not contain transposition (swapping of two adjacent character in a string). They only contain small number of typographical
errors or derivation.
