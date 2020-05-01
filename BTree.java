import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class BTree {
	
	private ZipFile myFile;
	private BTreeNode root;
	private int order;
	private int degree;
	private String name;
	private Cache<BTreeNode> currentNodes;
	
	BTree(int order){
		this.order = order;
		this.degree = order/2;
		this.name = treeName + ".btree.data." + sequenceLength + "." + degree;
		this.root = new BTreeNode(order,true,null);
		this.currentNodes = new Cache<BTreeNode>(15);
		try {
			myFile = new ZipFile(name);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initializeDisk();
		writeTreeMetaData();
		diskWrite(root);
	}


	public void diskWrite(BTreeNode node){
		try {
			String path;
			if(node == root) {
				path = "/n";
			}
			else{
				path = diskSearch(node.key[1].getKey());
			}
			FileOutputStream fs = new FileOutputStream(name);
			ZipOutputStream zs = new ZipOutputStream(fs);
			ObjectOutputStream os = new ObjectOutputStream(zs);
			ZipEntry current = myFile.getEntry(path);
			if(current == null) {
				current = new ZipEntry(path);
			}
			zs.putNextEntry(current);
			//need to write data to node
			os.writeObject(node);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String diskSearch(long key) {
		String path = "/";
		
		
		return path;
	}
	
	private void initializeDisk() {
		
		try {
			FileOutputStream fs;
			fs = new FileOutputStream(name);
			ZipOutputStream zs = new ZipOutputStream(fs);
			ZipEntry r = new ZipEntry("/");
			ZipEntry treeMeta = new ZipEntry("/tree");
			ZipEntry n = new ZipEntry("/n");
			zs.putNextEntry(r);
			zs.putNextEntry(treeMeta);
			zs.putNextEntry(n);
			zs.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void writeTreeMetaData() {
		try {
			FileOutputStream fs = new FileOutputStream(name);
			ZipOutputStream zs = new ZipOutputStream(fs);
			ZipEntry current = myFile.getEntry("/tree");
			if(current == null) {
				//need to make node
			}
			zs.putNextEntry(current);
			//need to write data to node
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	//Is this the same as the split method??
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
