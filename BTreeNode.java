import java.io.File;
import java.io.Serializable;

public class BTreeNode{

	/*
	 * In the pseudocode in the slides, these values are accessed
	 * directly in the BTree so I made them public in order to do
	 * that
	*/
	public int numObjects;
	public TreeObject key[];  //we might need to rename this
	public boolean leaf;
	public int children[];
	public int parent;
	public int offset;
	public int numChildren; //experimental
	 
	BTreeNode(int order, boolean leaf, int parent){
		this.numObjects = 0;
		this.numChildren = 0;
		this.key = new TreeObject[order];
		this.leaf = leaf;
		this.children = new int[order];
		this.parent = parent;
	}
	
	public void setOffset(int i) {
		this.offset = i;
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
	
	public boolean isLeaf() {
		return leaf;
	}
	
	public void updateLeaf() {
		for(int i:children) {
			if(i != 0) {
				leaf = false;
				return;
			}
		}
		leaf = true;
	}
	
	public void fillKeys() {
		for(int i = 0; i < key.length; i++) {
			if(key[i] == null) {
				key[i] = new TreeObject(0,0);
			}
		}
	}

	//Returns keys of the node
	@Override
	public String toString(){
		String result = "";
		for (int i = 0; i < numObjects && key[i] != null; i++){
			result += key[i].toString() + " ";
		}
		return result;
	}
	
}
