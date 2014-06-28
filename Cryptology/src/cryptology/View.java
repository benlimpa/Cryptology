package cryptology;

import java.util.Scanner;

public class View {

	private Scanner	keyboard;
	
	public View() {
		
	}
	
	private String getText(String type) {
		
		String 	text 		= "";
		String  input 		= "";
		boolean done 		= false;
		
		System.out.println("\n" + "Enter each line of the " + type + " below and press Enter. Press Enter again when you are finished:" + "\n");
		
		while (!done) {
			
			System.out.print("> ");
			input = keyboard.nextLine();
			input = Cryptology.stripNonLetter(input);
			
			if (input.length() > 0) {
				
				text = text + input;
			}
			else {
				
				if (text.length() > 0) {
					
					System.out.println("\n" + "Text input finished.");
				}
				else {
					
					System.out.println("\n" + "Text input cancelled.");
				}
				
				done = true;
			}
		}
		text = text.toUpperCase();
		return text;
	}
	
	private boolean getBoolean(String label) {
		
		boolean value = false;
		boolean valueNotFound;
		
		do {
			
			try {
				
				System.out.println("\nWould you like \"" + label + "\" [true/false]:  ");
				value = keyboard.nextBoolean();
				keyboard.nextLine();
				valueNotFound = false;
			} catch (Exception e) {
				
				System.out.println("Invalid Input");
				keyboard.nextLine();
				valueNotFound = true;
			}
		} while (valueNotFound);
		
		return value;
	}
}
