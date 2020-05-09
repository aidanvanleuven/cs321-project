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
		this.key = newValues.getKey();
		this.frequency = newValues.getFrequency();
	}

	public void empty() {
		this.key = 0;
		this.frequency = 0;
	}
	
	public long getKey() {
		return key;
	}
	
	public void setKey(long k)
	{
		this.key = k;
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
			System.out.println("Invalid character "+ c);
			break;
		}
		return result;
	}
	
	public static long sequenceToLong(String s) {
		long k = convertCharToNum(s.charAt(0));
		for(int i = 1; i < s.length(); i++) {
			k = k * 4 + convertCharToNum(s.charAt(i));
		}
		return k;
	}
	
	public static String longToString(long k) {
		String result = "" + longToChar(k);
		while(k > 0) {
			k/=4;
			result = longToChar(k) + result;
		}

		return result;
	}
	
	public static char longToChar(long k) {
		long mask = 0b11;
		int result;
		result = (int) (k & mask);
		char r;
		switch(result) {
		case 0b00:
			r = 'a';
			break;
		case 0b11:
			r = 't';
			break;
		case 0b01:
			r = 'c';
			break;
		case 0b10:
			r = 'g';
			break;
		default:
			r = 'z';
			System.out.println("Invalid character");
			break;
		}
		return r;
	}
	
	@Override
	public String toString() {
		return longToString(key) + ",F:" + frequency;
	}
}
