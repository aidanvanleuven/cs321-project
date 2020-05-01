import java.io.File;
import java.io.Serializable;

public class BTreeNode{

	/*
	 * In the pseudocode in the slides, these values are accessed
	 * directly in the BTree so I made them public in order to do
	 * that
	*/
	public int n;
	public TreeObject key[];
	public boolean leaf;
	public int children[];
	public int parent;
	
	
	//What is the value of n in the BTreeNode?? Is it the number of objects? 
	BTreeNode(int order, boolean leaf, int parent){
		n = 0;
		this.key = new TreeObject[order];
		this.leaf = leaf;
		this.children = new int[order];
		this.parent = parent;
	}
	
}
