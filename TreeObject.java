import java.io.Serializable;

public class TreeObject {
	
	private long key;
	private int frequency;
	
	TreeObject(String sequence){
		key = convertCharToNum(sequence.charAt(0));
		for(int i = 1; i < sequence.length(); i++) {
			key = key * 4 + convertCharToNum(sequence.charAt(i));
		}
	}
	
	public TreeObject(long key){
		this.frequency = 1;
		this.key = key;
	}
	
	public TreeObject(long key, int frequency)
	{
		this.frequency = frequency;
		this.key = key;
	}
	
	public int getFrequency()
	{
		return frequency;
	}
	
	public void setFrequency(int i) {
		frequency = i;
	}
	
	public void increaseFrequency()
	{
		this.frequency++;
	}
	
	public void copy(TreeObject newValues) {
		this.key = newValues.key;
		this.frequency = newValues.frequency;
	}

	public void empty() {
		this.key = 0;
		this.frequency = 0;
	}
	
	public long getKey() {
		return key;
	}
	
	public static int convertCharToNum(char c) {
		c = Character.toLowerCase(c);
		int result;
		switch(c) {
		case 'a':
			result = 0b00;
			break;
		case 't':
			result = 0b11;
			break;
		case 'c':
			result = 0b01;
			break;
		case 'g':
			result = 0b10;
			break;
		default:
			result = -0b10;
			System.out.println("Invalid character");
			break;
		}
		return result;
	}
}
