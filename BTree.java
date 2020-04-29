import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BTree {
	
	BTreeNode root;
	int order;
	int degree;
	String name;
	Cache<BTreeNode> currentNodes;
	
	BTree(int order, String treeName, int sequenceLength){
		this.order = order;
		this.degree = order/2;
		this.name = treeName + ".btree.data." + sequenceLength + "." + degree;
		this.root = new BTreeNode(order,true);
		root.path = name + "/";
		this.currentNodes = new Cache<BTreeNode>(15);
		diskWrite(root);
	}
	
	public void diskWrite(BTreeNode node){
		String path = "";
		if(node == root) {
			initializeDisk();
		}
		path = node.path;
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path + "/n"));
			out.writeObject(node);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void initializeDisk() {
		File file = new File(name);
		file.mkdir();
	}
	

	/*
	 * It looks like this is suppose to work on the child of
	 * a node... Slide 39 on lecture15
	 */
	public void diskRead(BTreeNode xc) {
		
	}
	
	/*
	 * The return type isn't super clear in the slides
	 * so I just put void for now but it will return
	 * something
	 */
	public void search(BTreeNode x, long k){
		
	}
	
	public void splitChild(BTreeNode x, int i) {
		
	}
	
	public void insert(long k) {
		
	}
	
	public void insertNonfull(BTreeNode x, long k) {
		
	}
	/*
	public BTreeNode precedingChild(long key) {
		
	}
	
	public BTreeNode successorChild(long key) {
		
	}
	
	public long findPredecessorKey(long k, BTreeNode x) {
		
	}

	public long findSuccessorKey(long k, BTreeNode x) {
	
	}
	*/
	
	public void moveKey(long k, BTreeNode from, BTreeNode to) {
		
	}
	
	/*
	 * These two functions might be the same, but they
	 * were both listed in the slides...
	 */
	public void deleteKey(long k, BTreeNode x) {
		
	}
	
	public void removeKey(long k, BTreeNode x) {
		
	}
	
	public void mergeNodes(BTreeNode x, BTreeNode y) {
		
	}

}
