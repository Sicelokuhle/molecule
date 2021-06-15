# Sicelokuhle Siyabonga Shabalala
# SHBSIC001
# OS Assignment 2
# Due: 15 June 2021

JAVAC = javac
JV = java
CLN = RM

default: 
	$(JAVAC) molecule/*.java

clean:
	$(CLN) molecule/*.class

run:
	$(JV) molecule.RunSimulation 8 3