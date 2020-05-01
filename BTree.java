import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class BTree {
	
	public ZipFile myFile;
	private BTreeNode root;
	private int order;
	private int degree;
	private String name;
	private Cache<BTreeNode> currentNodes;
	private ObjectOutputStream os;
	private ZipOutputStream zs;
	
	BTree(int order, String treeName, int sequenceLength){
		this.order = order;
		this.degree = order/2;
		this.name = treeName + ".btree.data." + sequenceLength + "." + degree;
		this.root = new BTreeNode(order,true,0);
		this.currentNodes = new Cache<BTreeNode>(15);
		try {

			myFile = new ZipFile(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		diskWrite(root);
		writeTreeMetaData();
	}


	public void diskWrite(BTreeNode node){

		
	}
	


	
	private void writeTreeMetaData() {

		
	}
	


	public BTreeNode diskRead(int offset) {
		BTreeNode node =  null;
		
		return node;
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
		
		return 0;
	}
	
	public void splitChild(BTreeNode x, int i) {
		
	}
	
	public void insert(long k) {
		
	}
	
	public void insertNonfull(BTreeNode x, long k) {
		
	}
	
	public BTreeNode precedingChild(long key) {
		
		return null;
	}
	
	public BTreeNode successorChild(long key) {
		
		return null;
	}
	
	public long findPredecessorKey(long k, BTreeNode x) {
		
		return 0;
	}

	public long findSuccessorKey(long k, BTreeNode x) {
	
		return 0;
	}
	
	
	public void moveKey(long k, BTreeNode from, BTreeNode to) {
		
	}
	
	/*
	 * These two functions might be the same, but they
	 * were both listed in the slides...
	 */
	public void deleteKey(long k, BTreeNode x) {
		
	}
		
	public void mergeNodes(BTreeNode x, BTreeNode y) {
		
	}

}
