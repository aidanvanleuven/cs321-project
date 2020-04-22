
public class BTreeNode {
	
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
	public BTreeNode parent;
	
	BTreeNode(int order, boolean leaf, BTreeNode parent){
		n = 0;
		this.order = order;
		this.key = new TreeObject[order - 1];
		this.leaf = leaf;
		this.children = new BTreeNode[order];
		this.parent = parent;
	}
	
}
