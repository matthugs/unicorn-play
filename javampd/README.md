MPD_Questions
=============
WTF MAVEN DOCUMENTATION IS AWFUL

Install Maven. For Linux systems that use apt:

sudo apt-get install maven

Now get and build this project:
clone this repo

git clone https://github.gatech.edu/emaysilles3/MPD_Questions

cd into the MPDQuestions folder. To compile and automatically download the javaMPD library:

mvn compile

for MORE DEBUG INFO THAN YOUR BODY CAN HANDLE

mvn compile -X

To run:

mvn exec:java

Similarly,

mvn exec:java -X

All is does right now is run HelloWorld

Maven Pulls 5.0.3, you need some things to keep up
==================================================

The documentation on the JavaMPD website is for 6.0.0 which doesn't exist yet. The developer was working on it hard but kinda stopped halfway. If you pull the current development branch and try to compile it, Maven is all like "I"M MAVEN AND I'M ANGRY AT ALL THESE COMPILER ISSUES."

So, you get 5.0.3 installed into ~/.m2/repositories/.... for free through mvn compile.

To get documentation for 5.0.3:

Download the 5.0.3 zip from the JavaMPD Github Releases.

Unzip, cd into the directory

mvn javadoc:javadoc

cd target/site/apidocs

open index.html. Ta-da!

You now have 5.0.3's javadocs. Have at it!