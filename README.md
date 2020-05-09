********************************************************************
CS321 Data Structures: Final Project  
Team Programming Project: Bioinformatics

Team Members:

Aidan Vanleuven  
David Marcial  
Justin Heck  
Mitchell Crocker  
Nathan Jones


********************************************************************

OVERVIEW:


INCLUDED FIlES:

* BTreeNode.java
* BTree.java
* TreeObject.java
* Cache.java
* GeneBankCreateBTree.java
* GeneBankSearch.java

COMPILING AND RUNNING:

To compile, execute the following command in the main directory:

$ javac *.java

Run the compiled class file with the command:
$ java GeneBankCreateBTree < cache > <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]

**Please remember to run GeneBankCreateBTree first for a BTree file is needed in order to run GeneBankSearch**

$ java GeneBankSearch <cache> <btree file> <query file> [<cache size>] [<debug leve>]

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

There are six different classes for this project. We have the main class which is GeneBankCreateBTree, that will create our B-Tree but in order for a actual B-Tree to be created we need to use our TreeObject, BTreeNode, BTree classes. We also have a Cache class that gets used if the user decides that they want to use a Cache when creating the B-Tree and also when using the GeneBankSearch class. 