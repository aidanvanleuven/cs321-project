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
	private Cache<Integer, BTreeNode> currentNodes;

	
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
			currentNodes = new Cache<Integer, BTreeNode>(cacheSize);
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
		
		if(currentNodes != null) {
			node = currentNodes.getObject(offset);
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
		
		currentNodes.addObject(node, node.offset);
		
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
	
	/**
	 * 
	 * @param x - BTreeNode 
	 * @param k - long value whose value we are searching for matching element for
	 * @return - int value count the frequency of the value in the tree
	 */
	 public int search(BTreeNode x, long k)
	{
		int i = 1;

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
			BTreeNode next = diskRead(x.children[i]);
			return search(next, k);
		}
	}
	
	public void splitChild(BTreeNode x, int i) {
		
	}
	
	/**
	 * This doc added because this method is used in another class and I just want to make sure
	 * when it gets implemented they use it in the same sort of way that I did. LMK if what I did
	 * is stupid
	 * @param k
	 */
	public void insert(long k) {
		BTreeNode duplicate = diskSearch(k);

		if (duplicate != null) {
			duplicate.key[0].increaseFrequency();
			diskWrite(duplicate);
			return;
		}

		BTreeNode r = this.root;

		if (r.numObjects == 2 * degree - 1) {
			BTreeNode newNode = new BTreeNode(0, false, 0);
			this.root = newNode;
			newNode.setNumObjects(0);
			//newNode.children[0] = r; //need to make r the child of newNode here
			r.setOffset(nextInsert);
			newNode.setOffset(0);
			splitNode(newNode, 0);
			insertNonfull(newNode, k);
		} else {
			insertNonfull(r, k);
		}
	}
	
	public void insertNonfull(BTreeNode x, long k) {
		
	}
	
	/**
	* Returns the predecessor child of the provided key
	*
	* @param key the key of which the predecessor child will be returned
	* @return the preceeding child node of the given key
	*/
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
	
	/**
	* Returns the successor child of the provided key
	*
	* @param key the key of which the successor child will be returned
	* @return the proceeding child node of the given key
	*/
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
	
	/**
	* Returns the predecessor of the provided key in the provided node
	*
	* @param k the key of which the predecessor will be returned
	* @param x the node in which k is stored
	* @return the preceding key, 0 if not found or invalid
	*/
	public long findPredecessorKey(long k, BTreeNode x) {
		int i = 0;
		Boolean found = false;

		while (i < x.numObjects){
			if (k == x.key[i].getKey()){
				found = true;
				break;
			}
			i++;
		}

		if (found){
			if (x.leaf){
				if (i - 1 >= 0){
					return x.key[i - 1].getKey();
				} else {
					return 0;
				}
			} else {
				BTreeNode result = diskRead(x.children[i]);
				return result.key[result.numObjects - 1].getKey();
			}
		} else {
			return 0;
		}
	}

	/**
	* Returns the successor of the provided key in the provided node
	*
	* @param k the key of which the successor will be returned
	* @param x the node in which k is stored
	* @return the proceeding key, 0 if not found or invalid
	*/
	public long findSuccessorKey(long k, BTreeNode x) {
		int i = 0;
		Boolean found = false;

		while (i < x.numObjects){
			if (k == x.key[i].getKey()){
				found = true;
				break;
			}
			i++;
		}

		if (found){
			if (x.leaf){
				if (i + 1 == x.numObjects){
					return 0;
				} else {
					return x.key[i + 1].getKey();
				}
			} else {
				BTreeNode result = diskRead(x.children[i + 1]);
				return result.key[0].getKey();
			}
		} else {
			return 0;
		}
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
	 * This method was deemed unessential to this particular
	 * project and so has not yet been implemented
	 * @param k is element to be removed from the node
	 * @param x is the node itself
	 */
	public void deleteKey(long k, BTreeNode x) {
	}
	
	public void mergeNodes(BTreeNode x, BTreeNode y) {
		//check and handle overfill situation
		if(x.n+y.n>=x.order) {
			//i'm assuming that we are to move the 
		}
		//perform the merge
		
	}

}
