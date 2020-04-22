
public class TreeObject {
	
	private long key;
	
	TreeObject(String sequence){
		key = convertCharToNum(sequence.charAt(0));
		for(int i = 1; i < sequence.length(); i++) {
			key = key * 4 + convertCharToNum(sequence.charAt(i));
		}
	}
	
	TreeObject(long key){
		this.key = key;
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
