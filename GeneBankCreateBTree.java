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
    public static int debugLevel = 0;
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
        (args[0]==1) ? code needed for future cache implementation
         */
        
        if(Integer.parseInt(args[1]) == 0)
        {
            degree = (size - 4)/ 32;
        }
        else
        {
            degree = Integer.parseInt(args[1]);
        }
        
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
        
        //GBK file argument
        try
        {
            gbk = new File(args[2]);
        }
        catch(NullPointerException e)  //Ideally I would like to use the filenotfound execption but its giving me an error, any help would be appreciated. DM
        {
            System.out.println("File does not exist " + gbk.toPath());
            
        }
        
        //fileName = new File(gbk + ".btree.data." + sequenceLength + "." + degree);
        String fileName = (args[2]);
        
        cache = false;
        
        
        tree = new BTree(degree,fileName,sequenceLength,cache, cacheSize);
        TreeObject object;
        
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader currentInput = new BufferedReader(new FileReader(gbk));
        String currentLine = currentInput.readLine();
        boolean start = false;
     
        while(currentLine != null)
        {
            Scanner lineScan = new Scanner(currentLine);
            String line = currentLine.replaceAll("\\s", "");
            String line1 = line.replaceAll("\\d", ""); 
            
            if(line1.equals("ORIGIN"))
            {
                start = true;
            }
            
            if(start == true)
            {
                for(int i = 0; i < line1.length(); i++)
                {
                    char character = line1.charAt(i);
                    
                    if (character == 'n' || character == 'N')
                    {
                        stringBuilder = new StringBuilder();
                    }
                    else if(character == 'A' || character == 'T' || character == 'C' || character == 'G')
                    {
                        stringBuilder.append(Character.toLowerCase(character));
                    } 
                    
                    if(sequenceLength == stringBuilder.length())
                    {
                    long key = TreeObject.convertCharToNum(character);
                    object = new TreeObject(key);
                    tree.insert(key);
                    }                   
                }  
            }   
            lineScan.close(); 
            currentLine=currentInput.readLine();
        }
        currentInput.close();
    }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found");
        }
    
        catch(IOException b)
        {
        
        }      
        //optional cache size
        if(args.length>=5)
        {
        	size = Integer.parseInt(args[4]);
        }
        
        //optional debug level
        if(args.length==6) 
        {
        	debugLevel = Integer.parseInt(args[5]);
        }
        
    } 
    
    public static void printUsage()
    {
        System.out.println("java GenebankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level]>");
    }
    
    
}