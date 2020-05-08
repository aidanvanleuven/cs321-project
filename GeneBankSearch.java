import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneBankSearch {

	public static void main(String[] args) {
		boolean cache = false;
		File btree;
		File qFile;
		int cacheSize = 0;
		BTree tree;
		
		//Parse command line arguments
		if(args.length < 3 || args.length > 4) {
			printUsage();
			System.exit(1);
		}
		int tmp = Integer.parseInt(args[0]);
		if(tmp == 1) {
			cache = true;
		}
		if(args.length == 4) {
			cacheSize = Integer.parseInt(args[3]);
		}
		
		btree = new File(args[1]);
		
		qFile = new File(args[2]);
		
		tree = new BTree(btree, cache, cacheSize);
		tree.printTree();
		
		try {
			Scanner q = new Scanner(qFile);
			while(q.hasNextLine()) {
				String sequence = q.nextLine();
				long key = TreeObject.sequenceToLong(sequence);
				int frequency = tree.search(key);
				sequence = sequence.toLowerCase();
				System.out.println(sequence + ": " + frequency);
			}
			q.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void printUsage() {
		System.out.println("java GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>]");
	}
	
}
