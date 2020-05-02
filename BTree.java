import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class BTree {
	
	private BTreeNode root;
	private int order;
	private int degree;
	private RandomAccessFile raf;
	private File f;
	private int rootOffset;
	private int nodeSize;
	private int nextInsert;
	private Cache<BTreeNode> currentNodes;

	
	BTree(int degree, String fileName, int sequenceLength, boolean cache, int cacheSize){
		//This if statement will determine the optimal degree for a disk size of
		//		4096 if there is no degree specified
		//TODO possibly need to double check my logic
		if(degree == 0) {
			this.degree = 4099/32;
		}
		else {
			this.degree = degree;
		}
		this.order = degree*2;
		
		//Build the name of the file we are writing to
		String name = fileName + ".btree.data." + sequenceLength + "." + degree;
		
		//This was found by adding up the number of bytes for all of the data that belongs to a node
			this.nodeSize = 4 + 1 + 4 + 12 * (order - 1) + 4 * order;
		
		//This is offset by the number of bytes the tree metaData is(3 integers)
		this.rootOffset = 4 + 4 + 4;
		
		//This will be where to insert a new node
		this.nextInsert = rootOffset + nodeSize;
		
		if(cache) {
			currentNodes = new Cache<BTreeNode>(cacheSize);
		}
		
		BTreeNode r = new BTreeNode(order, true, 0);
		root = r;
		r.setOffset(rootOffset);
		
		try {
			f = new File(name);
			f.delete();
			f.createNewFile();
			raf = new RandomAccessFile(f, "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeTreeMetaData();
	}
	
	


	public void diskWrite(BTreeNode node){
		try {
			raf.seek(node.offset);
			raf.writeInt(node.offset);
			raf.writeInt(node.parent);
			raf.writeInt(node.numObjects);
			raf.writeBoolean(node.leaf);
			for(int i = 0; i < order; i++) {
				//Write child
				if(!node.leaf && i < (node.numObjects + 1)) {
					//Write all the children that are in the node
					raf.writeInt(node.children[i]);
				}
				else{
					//There are no more children so write 0
					raf.writeInt(0);
				}
				//write TreeObject
				if(i < node.numObjects) {
					raf.writeLong(node.key[i].getKey());
					raf.writeInt(node.key[i].getFrequency());
				}
				else{
					raf.writeLong(0);
					raf.writeInt(0);
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	public BTreeNode diskRead(int offset) {
		BTreeNode node =  null;
		//TODO need to implement a method in the cache class to tell whether something was in there or not
		if(currentNodes != null) {
			node = currentNodes.contains(offset);
			if(node != null) {
				return node;
			}
		}
		
		try {
			raf.seek(offset);
			node = new BTreeNode(0,false,0);
			node.offset = raf.readInt();
			node.parent = raf.readInt();
			node.numObjects = raf.readInt();
			node.leaf = raf.readBoolean();
			for(int i = 0; i < order; i++) {
				//read child
				node.children[i]=raf.readInt();
				//read TreeObject
				long k = raf.readLong();
				int freq = raf.readInt();
				TreeObject t = new TreeObject(k);
				t.setFrequency(freq);
				node.key[i] = t;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		currentNodes.addObject(node);
		
		return node;
	}
	
	public void writeTreeMetaData() {
		try {
			raf.seek(0);
			raf.writeInt(degree);
			raf.writeInt(rootOffset);
			raf.writeInt(nodeSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readTreeMetaData() {
		try {
			raf.seek(0);
			degree = raf.readInt();
			rootOffset = raf.readInt();
			nodeSize = raf.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		//This loop isnt complete I need the second half of the psuedocode for the
		//condition
		while( i <= x.getNumbObjects() && k < x.key[i].getKey())
		{
			i++;
		}
		if(i <= x.getNumbObjects() && k == x.key[i].getKey())
		{
			return x.key[i].getFrequency();
		}
		else if(x.leaf)
		{
			return -1;
		}
		else
		{
			//Disk read method (x.children[i])
			return search(x.children[i], k);
		}
	}
	
	public void splitChild(BTreeNode x, int i) {
		
	}
	
	public void insert(long k) {
		
	}
	
	public void insertNonfull(BTreeNode x, long k) {
		
	}
	
	public BTreeNode precedingChild(long key) {
		BTreeNode node = diskSearch(key);
		if (node == null || node.leaf){
			return null;
		}
		int i = 0;
		while (i < node.numObjects){
			if (key == node.key[i].getKey()){
				break;
			}
			i++;
		}
		BTreeNode result = diskRead(node.children[i]);
		if (result != null){
			return result;
		} else {
			return null;
		}
	}
	
	public BTreeNode successorChild(long key) {
		BTreeNode node = diskSearch(key);
		if (node == null || node.leaf){
			return null;
		}
		int i = 0;
		while (i < node.numObjects){
			if (key == node.key[i].getKey()){
				break;
			}
			i++;
		}
		BTreeNode result = diskRead(node.children[i + 1]);
		if (result != null){
			return result;
		} else {
			return null;
		}
		
	}
	
	public long findPredecessorKey(long k, BTreeNode x) {
		
		return 0;
	}

	public long findSuccessorKey(long k, BTreeNode x) {
	
		return 0;
	}

	/**
	* Searches the BTree starting at the supplied offset and returns the corresponding BTreeNode
	*
	* @param k the key to be searched for
	* @param offset the offset of the node where the search will be started
	* @return The BTreeNode containing the key, null if not found
	*/
	private BTreeNode diskSearch(long k, int offset){
		BTreeNode node = diskRead(offset);

		if (node.key[0].getKey() > k){
			return diskSearch(k, node.children[0]);

		} else if (node.key[node.numObjects - 1].getKey() < k){
			return diskSearch(k, node.children[node.numChildren - 1]);

		} else {
			for (int i = 0; i < node.numObjects - 1; i++){
				if (k == node.key[i].getKey()){
					return node;
				} else if (k > node.key[i].getKey() && k < node.key[i + 1].getKey() && !node.leaf){
					return diskSearch(k, node.children[i + 1]);
				}
			}
		}
		return null;
	}

	/**
	* Searches the BTree starting at the root and returns the corresponding BTreeNode
	*
	* @param k the key to be searched for
	* @return The BTreeNode containing the key, null if not found
	*/
	private BTreeNode diskSearch(long k){
		BTreeNode node = diskRead(rootOffset);

		if (node.key[0].getKey() > k){
			return diskSearch(k, node.children[0]);

		} else if (node.key[node.numObjects - 1].getKey() < k){
			return diskSearch(k, node.children[node.numChildren - 1]);

		} else {
			for (int i = 0; i < node.numObjects - 1; i++){
				if (k == node.key[i].getKey()){
					return node;
				} else if (k > node.key[i].getKey() && k < node.key[i + 1].getKey() && !node.leaf){
					return diskSearch(k, node.children[i + 1]);
				}
			}
		}
		return null;
	}
	
	//Is this the same as the split method??
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
	
	public void mergeNodes(BTreeNode x, BTreeNode y) {
		//check and handle overfill situation
		if(x.n+y.n>=x.order) {
			//i'm assuming that we are to move the 
		}
		//perform the merge
		
	}

}
