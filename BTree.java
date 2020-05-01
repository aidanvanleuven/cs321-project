
public class BTree {
	
	BTreeNode root;
	int order;
	
	BTree(int order){
		this.order = order;
		root = new BTreeNode(order,true);
		diskWrite(root);
	}
	
	public void diskWrite(BTreeNode node){
		
	}
	
	/*
	 * It looks like this is suppose to work on the child of
	 * a node... Slide 39 on lecture15
	 */
	public void diskRead(BTreeNode xc) {
		
	}
	
	/*
	 * 
	 * @param x - BTreeNode 
	 * @param k - long value whose value we are searching for matching element for
	 * @return - int value count the frequency of the value in the tree
	 */
	 public int search(BTreeNode x, long k)
	{
		int i = 1;
		
		
	}
	
	public void splitChild(BTreeNode x, int i) {
		
	}
	
	public void insert(long k) {
		
	}
	
	public void insertNonfull(BTreeNode x, long k) {
		
	}
	
	public BTreeNode precedingChild(long key) {
		
	}
	
	public BTreeNode successorChild(long key) {
		
	}
	
	public long findPredecessorKey(long k, BTreeNode x) {
		
	}

	public long findSuccessorKey(long k, BTreeNode x) {
	
	}
	
	public void moveKey(long k, BTreeNode from, BTreeNode to) {
		
	}
	
	/*
	 * @param k is element to be removed from the node
	 * @param x is the node itself
	 * @author NathanJones
	 */
	public void deleteKey(long k, BTreeNode x) {
		//
	}
	
	/*
	 * @param x is the first node to be merged
	 * @param y is the second node to be merged 
	 * @author NathanJones
	 */
	public void mergeNodes(BTreeNode x, BTreeNode y) {
		//check and handle overfill situation
		if(x.n+y.n>=x.order) {
			//i'm assuming that we are to move the 
		}
		//perform the merge
		
	}

}
