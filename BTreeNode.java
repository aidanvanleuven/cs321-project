import java.io.Serializable;

public class BTreeNode implements Serializable{
	
	/**
	 * Required by the interface
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * In the pseudocode in the slides, these values are accessed
	 * directly in the BTree so I made them public in order to do
	 * that
	*/
	public int n;
	public int order;
	public TreeObject key[];
	public boolean leaf;
	public BTreeNode children[];
	String path;
	public BTreeNode parent;
	
	
	//What is the value of n in the BTreeNode?? Is it the number of objects? 
	BTreeNode(int order, boolean leaf, BTreeNode parent){
		n = 0;
		this.order = order;
		this.key = new TreeObject[order - 1];
		this.leaf = leaf;
		this.children = new BTreeNode[order];
		this.parent = parent;
	}
	
	public void setChildPaths() {
		if(!leaf) {
			for(int i = 0; i < (n + 1); i++) {
				children[i].path = path + "/" + i;
			}
		}
	}
	
}
