# Darlene
# Project 0: Generate Wireless IoT Sensor Network


###

**Hand-in Instruction**: Please submit in blackboard a zip file called 
“LastnameOfStudent1_LastnameOfStudent2_ LastnameOfStudent2_401_501_Proj0.zip”. 
The zip file above should include the following files:
1. your program source codes 
2. readme file: stating clearly 
  
  a. whether your program can be compiled and executed successfully, 
  and including two test cases and their screen shot outputs.
 
  b. How to compile and test your programs.
  
  c. The contributions of each groupmember.

##
**Network Model**\
The IoT sensor network is represented as a graph G(V,E), where V = {1, 2, ..., N} 
is the set of N nodes, and E is the set of edges. 
The sensor nodes are randomly generated in an x by y area. 
All the sensor nodes have the same transmission range Tr. 
That is, if the distance between two nodes are less than or equal to Tr, 
then they can communicate directly and are connected by an edge

## Programming Objective: 
When executed, your program should prompt to ask 

1. the width x and length y of the sensor network (e.x., 50meter x 50meter)

2. number of sensor nodes: N

3. transmission range in meters: Tr

**4. Which graph traversal technique: \
(recursive DFS = 0, DFS using stack = 1; BFS using queue = 2)**

It then creates the adjacency list graph structure for the sensor network, and test if it is connected using BFS and DFS. 
If it is not connected, output the set of connected components.

**The code provided only implemented the recursive DFS. Please add BFS implementation using queue and DSF 
implementation using stack.**

Grading: successful implementation of each is 50 points.
