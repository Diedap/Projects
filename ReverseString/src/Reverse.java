import java.util.Scanner;

public class Reverse {

	public static void main(String[] args) {
	
		String name = "";
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Hello, please type a word to be reversed:");
		name = sc.next();
		
		String r = reverse (name);
		System.out.println (r);
	
	}
	
	public static String reverse (String s) {
		char[] letters = new char [s.length()];
		
		int letterIndex = 0;
		for (int i = s.length() - 1; i >= 0; i--) {
			letters[letterIndex] = s.charAt(i);
			letterIndex ++;
		}
		
		String reverse = "";
		for (int i = 0; i < s.length(); i++) {
			reverse = reverse + letters [i];
		}
		
		return reverse;
	}

}
