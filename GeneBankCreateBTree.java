import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Scanner;
/*
 * This class is invoked and creates 
 * @author davidMarcial nathanJones
 */
public class GeneBankCreateBTree
{
    public static BTree tree;
    public static File gbk;
    public static File fileName;
    public static int size = 4096;
    public static int degree = 0;
    public static final int MAX_SEQUENCE_LENGTH = 31;
    public static final int MIN_SEQUENCE_LENGTH = 1;
    public static int sequenceLength = 0;    
    public static boolean cache;
    public static int cacheSize = 0;
    public static void main (String args[])
    {
        try
        {
        
        if (args.length < 4 || args.length > 6)
        {
            System.out.println("Incorrect number of arguments! Please use the following usage.");
            printUsage();
        }
        
        /*
         *read cache argument and set appropriately
         */
        cache = (Integer.parseInt(args[0]) == 1) ? true:false;
        
        /*
         * read degree argument and set appropriately
         */
        if(Integer.parseInt(args[1]) == 0)
        {
            degree = (size - 4)/ 32;
        }
        else
        {
            degree = Integer.parseInt(args[1]);
        }
        
        /*
         * read and set stringLength argument
         */
        int stringLength = Integer.parseInt(args[3]);
        if(stringLength >= MIN_SEQUENCE_LENGTH && stringLength <= MAX_SEQUENCE_LENGTH)
        {
            sequenceLength = stringLength;
        }
        else
        {
            System.out.println("The integer for the sequence length must be between 1 and 31 inclusive");
            printUsage();
        }
        
        /*
         * read and set file argument
         */
        String fileName = (args[2]);
        try
        {
            gbk = new File(fileName);
        }
        catch(NullPointerException e)
        {
            System.out.println("File does not exist " + gbk.toPath());
        }
        
        /*
         * TODO optional cache size argument
         */
        if(args.length>=5)
        {
        	cacheSize = Integer.parseInt(args[4]);
        }
        
        /*
         * Build the tree
         */
        tree = new BTree(degree,fileName,sequenceLength,cache, cacheSize);
        BufferedReader currentInput = new BufferedReader(new FileReader(gbk));
        String currentLine = currentInput.readLine();
        boolean start = false;
        boolean completeSequence = false;
        boolean containedSlash = false;
        while(currentLine != null)
        {
        	
        	if(start && (currentLine.contains("N") || currentLine.contains("n"))) {
        		completeSequence = true;
        	}
            currentLine = currentLine.replaceAll("\\s","").replaceAll("\\d","").replaceAll("[Nn]","");
            
            if(containedSlash) 
            {
            	completeSequence = true;
            	currentLine.replace("//", "");
            	containedSlash = true;
            }
            
            if(start == true && completeSequence && currentLine.length()>0)
            {
            	while(currentLine.length() >= sequenceLength) {
            		String tmp = currentLine.substring(0, sequenceLength);
            		tree.insert(TreeObject.sequenceToLong(tmp));
            		currentLine = currentLine.substring(1);
            		System.out.println(tmp);
            	}
            	completeSequence = false;
            	if(containedSlash) {
            		start = false;
            	}
            	System.out.println(currentLine);
            }   
        
            if(currentLine.equals("ORIGI"))
            {
                start = true;
                currentLine = currentInput.readLine();
            }
            
            if(start) {
            	String curr = currentInput.readLine();
            	if(curr.contains("//")) {
            		curr = "";
            		containedSlash = true;
            	}
            	currentLine += curr;
            }
            else {
            	currentLine = currentInput.readLine();
            }
        }
        currentInput.close();
        
        /*
         * create dump file if requested
         */
        if(args.length==6) 
        {
        	if(Integer.parseInt(args[5])==1)
        	{
        		File f = new File(fileName + ".btree.dump." + sequenceLength + "." + degree);
        		RandomAccessFile dump = new RandomAccessFile(f, "rw");

        	}
        }
    }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    
        catch(IOException b)
        {
        
        }      

    } 
    
    public static void printUsage()
    {
        System.out.println("java GenebankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level]>");
    }
    
    
}