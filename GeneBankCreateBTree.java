import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class GeneBankCreateBTree
{
    static BTree tree;
    static File file;
    static File file1;
    static int size = 4096;
    static int degree = 0;
    
    
    public static void main (String args[])
    {
        if (args.length < 3 || args.length >4)
        {
            System.out.println("Incorrect number of arguments");
            printUsage();
        }
        
        
        
        if(Integer.parseInt(args[0]) == 0)
        {
            degree = (size -4)/ 32;
        }
        else
        {
            degree = Integer.parseInt(args[0]);
        }
        
        
    }
    
    
    
    
    public static void printUsage()
    {
        System.out.println("java GenebankCreateBTree <degree> <gbk file> <sequence length>");
    }
    
    
}