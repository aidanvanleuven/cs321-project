import java.io.File;
import java.io.Serializable;

public class BTreeNode{

	/*
	 * In the pseudocode in the slides, these values are accessed
	 * directly in the BTree so I made them public in order to do
	 * that
	*/
	public int numObjects;
	public int order;
	public TreeObject key[];  //we might need to rename this
	public boolean leaf;
	public int children[];
	public int parent;
	
	
	//What is the value of n in the BTreeNode?? Is it the number of objects? 
	BTreeNode(int order, boolean leaf, int parent){
		this.numObjects = 0;
		this.order = order;
		this.key = new TreeObject[order];
		this.leaf = leaf;
		this.children = new int[order];
		this.parent = parent;
	}
	
	
	//might not need this
	public int getNumbObjects()
	{
		return this.numObjects;
	}
	//might not need this
	public void setNumObjects(int x)
	{
		this.numObjects = x;
	}
	
}
