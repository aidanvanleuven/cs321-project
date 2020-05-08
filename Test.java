import java.util.Random;

public class Test {

	public static void main(String[] args) {
		int degree = 50;
		String fileName = "tom.gbk";
		int sequenceLength = 4;
		boolean cache = true;
		int cacheSize = 200;
		BTree tom = new BTree(degree,fileName,sequenceLength,cache,cacheSize);
		Random rand = new Random(1);
		long startTime = System.nanoTime();
		for(long k = 0; k < 1000; k++) {
			tom.insert(rand.nextInt(2000));
		}
		long endTime = System.nanoTime();
		System.out.println((double)(endTime - startTime)/1000000000);
		// tom.insert(TreeObject.sequenceToLong("ATTA"));
		// tom.insert(TreeObject.sequenceToLong("ATTA"));
		// tom.insert(TreeObject.sequenceToLong("ATCG"));
		// tom.insert(TreeObject.sequenceToLong("GTCA"));
		// tom.insert(TreeObject.sequenceToLong("ACTG"));
		// tom.insert(TreeObject.sequenceToLong("ACGT"));
		// tom.insert(TreeObject.sequenceToLong("ATCC"));
		// tom.insert(TreeObject.sequenceToLong("ATGC"));
		// tom.insert(TreeObject.sequenceToLong("ATGC"));
		// tom.insert(TreeObject.sequenceToLong("TGCA"));
		// tom.insert(TreeObject.sequenceToLong("TGCA"));
		// tom.insert(TreeObject.sequenceToLong("AAAA"));
		// tom.insert(TreeObject.sequenceToLong("AAAT"));
		// tom.insert(TreeObject.sequenceToLong("GAAT"));
		// tom.insert(TreeObject.sequenceToLong("CCAT"));
		// tom.insert(TreeObject.sequenceToLong("GCAT"));
		// tom.insert(TreeObject.sequenceToLong("ACAT"));
		// tom.printTree();
	}
}
