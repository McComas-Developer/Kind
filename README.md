# Kind

## Welcome Team Memebers!

### This will be our repository for CSCI 3033. The goal here is to build an Android app that lets people post positive things they had done or seen and see what others have done or seen. The goal is to spread the kindess :)


## Getting Started
In order to get started working on this project you will need Java and the JDK (obviously) as well as Android Studio (which I think might also give you the JDK)

Links: [Android Studio](https://developer.android.com/studio)

Once you have Android Studio you can open your terminal in a directory of your choice and run:

`git clone https://github.com/Elijah-Dangerfield/Santas-List.git`

This should download all of the files needed to the current directory. Once done you can open Android Studio -> `Open an Existing...` and then choose the directory. 

Once this is done make sure to run the project and confirm it worked :)

## Architecture 
![Image](https://miro.medium.com/max/1200/1*Tt_OwtZJ993YzswuRyPQiA.png)


## Workflow

### NO PUSHING TO MASTER PLS

Under `Repository` -> `Projects` -> `Alpha` you will find all of the cards for the jobs that need to be done. In order to begin 
some work you will need to checkout a branch in the name of the card followed by a description of the work to be done. 

ex: For card HomeScreen-1  if you are finishing the networking you might make a branch like this: <br />

`git checkout -b HomeScreen-1_finish_networking`

#### Once you create the branch and have started work, make sure to move the assocaited card to the "Doing" section

After you have completed your feature you will need to submit a pull request. You can do this by going to: <br />

`Repository` -> `Code`-> `Branches` -> `Your branch` and then click on "Pull request"

### !!!  BEFORE YOU SUBMIT PULL REQUEST  !!!
make sure you run:

`git merge master` 

while on your branch. This makes sure that your branch is up to date with master and can easily be merged in :)

The pull request will let the repo manager know that 
your code is ready to be merged into the master branch. After code review is completed the card will be moved to done and the branch will be merged into master and another card may be checked out. 


## Resources
#### Android Learning:
I HIGHLY Reccomend Standford Professor Marty Stepps videos. He has them in Kotlin and Java.
[Marty Stepp's Youtube Channel](https://www.youtube.com/user/martystepp/videos)

#### Git Learning:
So some peeps have already complied a bunch of resources [here](https://try.github.io)
