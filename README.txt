Assignment # 2
==================

Files included:
=================
*PageRankAlgorithm.java – Page rank code in Java.
*PageRankAlgorithm$1.class and PageRankAlgorithm.class files – 2 executable files.
*testgraph.txt file - It is an adjacency matric file for Test graph. In given test graph, I changed the page labels from {A,B,C,...} to {1,2,3,...} just for implementation purpose. I have also the attached the Test graph with numberic lebels for reference.


Implementation of the Algorithm:
==================================

I started with simple Page rank algorithm where I implemented below equation initially:

M = dA+(1-d)B

where 	A=transition matrix 
	d=dampen factor


The adjacency matrix for the Test graph is below:
AdjMat - 6X6 for 6 vertices
A       B       C       D       E       F 
0.0	0.0	0.0	0.0	0.0	0.0	
1.0	0.0	1.0	1.0	1.0	0.0	
0.0	1.0	0.0	1.0	1.0	0.0	
0.0	1.0	1.0	0.0	1.0	0.0	
0.0	1.0	1.0	1.0	0.0	0.0	
0.0	0.0	0.0	0.0	1.0	0.0

I ran the test graph with above input, I got below transition matrix and Ranks:
Transtion Matrix:
0.0	0.0	0.0	0.0	0.0	0.0	
1.0	0.0	0.33	0.33	0.25	0.0	
0.0	0.33	0.0	0.33	0.25	0.0	
0.0	0.33	0.33	0.0	0.25	0.0	
0.0	0.33	0.33	0.33	0.0	0.0	
0.0	0.0	0.0	0.0	0.25	0.0

Ranked Documents
Rank 1-> Document 5 ::Score :0.0
Rank 2-> Document 4 ::Score :0.0
Rank 3-> Document 3 ::Score :0.0
Rank 4-> Document 2 ::Score :0.0
Rank 5-> Document 6 ::Score :0.0
Rank 6-> Document 1 ::Score :0.0


The problem in the Test graph is, node F does not has any out edge, i.e. OutDegree(F) = 0. Due to all zeros column(F) in transition matrix, page ranks eventually converaged to zero. This problem is also called Dangling nodes problem. 
To solve this problem, I updated my algorithm and added 1/N in transition matrix where columns were all zeros. After this change, my Adjacency matrix remained the same, but transition matrix updated to

Transtion Matrix:
0.0	0.0	0.0	0.0	0.0	0.17	
1.0	0.0	0.33	0.33	0.25	0.17	
0.0	0.33	0.0	0.33	0.25	0.17	
0.0	0.33	0.33	0.0	0.25	0.17	
0.0	0.33	0.33	0.33	0.0	0.17	
0.0	0.0	0.0	0.0	0.25	0.17

It added the 1/6=0.17 in the last column(F) which was all zeros earlier. The ranks came out are below:

Ranked Documents
Rank 1-> Document 2 ::Score :0.235
Rank 2-> Document 5 ::Score :0.2228
Rank 3-> Document 4 ::Score :0.2105
Rank 4-> Document 3 ::Score :0.2105
Rank 5-> Document 6 ::Score :0.0843
Rank 6-> Document 1 ::Score :0.0369



To run the Algorithm:
============================

-Requirements- 
*System should have java 7 installed.


-To run through cmd
*copy the pagerank folder(java file, class files and input file(4 files)) in one folder/location.
*Open command prompt from window button.
*in the command prompt, change directory to the folder where code and input file location.
	if copied the folder to d:\XYZ folder
	then, go to XYZ\pagerank in command line
	C:> d:
	then,
	d:> cd d:\XYZ\pagerank (it will change the current directory to the folder where code was copied
the main purpose: 1)all the file should be in one location. 2)command to run the code should run from the folder where the input and other files reside.

*Run the below command:
	java -cp . PageRankAlgorithm

It will ask for the input file, which is testgraph.txt for the given test graph, and number of pages in the graph which is 6 in test graph.
Enter the file name:
testgraph.txt
Enter Number of Pages:
6

Results:

Ranked Documents
Rank 1-> Document 2 ::Score :0.235
Rank 2-> Document 5 ::Score :0.2228
Rank 3-> Document 4 ::Score :0.2105
Rank 4-> Document 3 ::Score :0.2105
Rank 5-> Document 6 ::Score :0.0843
Rank 6-> Document 1 ::Score :0.0369


(If you need to re-compile the code to see if it is building correctly, use the below command on the same folder:
	javac PageRankAlgorithm

I also attached the snapshot of the command prompt with the commands for references.



References:
==================
*http://home.ie.cuhk.edu.hk/~wkshum/papers/pagerank.pdf
*http://www.math.cornell.edu/~mec/Winter2009/RalucaRemus/Lecture3/lecture3.html













 
	



