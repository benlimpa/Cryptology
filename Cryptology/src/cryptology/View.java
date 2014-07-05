package cryptology;

import java.util.Scanner;

public class View {

	private Scanner			keyboard;
	
	private final String	EXIT = "X";	
	
	public View() {
		keyboard = new Scanner(System.in);
	}
	
	public void displayMenu() {
		
		final String[]	MENU_OPTION_INFO	= {	"What would you like to do?:",
												"Cryptography		- [G]",
												"Cryptanalysis		- [A]",
												"",
												"Various Utilities	- [U]"};
		
		final String CRYPTOGRAPHY			=	"G";
		final String CRYPTANALYSIS			=	"A";
		final String UTILITIES				=	"U";
		final String SETTINGS				=	"S";
		
		String menuOption 					= 	"";
		
		// Main Menu
		while (!menuOption.equalsIgnoreCase(EXIT)) { // Loop menu until user selects option or exits
			
			// Display Welcome Message
			System.out.println("\n---------------------------------------");
			System.out.println("--Welcome to the world of Cryptology!--\n");
			
			// Display Menu Options
			for (String info : MENU_OPTION_INFO) {
				
				System.out.println(info);
			}
			
			// Allow user to select option
			System.out.print("\nE[X]it or choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase()) {
				case CRYPTOGRAPHY:
					displayCryptography();
					break;
				case CRYPTANALYSIS:
					displayCryptanalysis();
					break;
				case UTILITIES:
					displayUtilities();
					break;
				case SETTINGS:
					displaySettings();
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
		
	private void displayUtilities() {
		
		final String	STRIP_NON_LETTER	= "S";
		
		String			menuOption				= "";
		
		while (!menuOption.equalsIgnoreCase(EXIT)) {
			
			// Display Title
			System.out.println("\n--------------");
			System.out.println("--Utilities:--\n");
			
			// Display Options
			System.out.println("Strip non-letter characters from text - [S]");
			
			// Allow user to select option
			System.out.print("\nE[X]it or choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase()) {
			
				case STRIP_NON_LETTER:
					System.out.print("Enter text to be stripped of non-letter characters:  ");
					System.out.println("\nStripped String:  " + Cryptology.stripNonLetter(keyboard.nextLine()));
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private void displaySettings() {
		
	}
	
	private void displayCryptography() {
		
	}
	
	private void displayCryptanalysis() {
		
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
	
	private String getKeyword(String clearText, String message, int minimumLength, int maximumLength, boolean nullKeywordOK) {
		
		boolean finished 	= false;
		String 	keyword 	= "";

		while (!finished) {
			
			System.out.print("\n" + message + " with a minimum of " + minimumLength + " and a maximum of " + maximumLength + " alphabetic characters: ");
		
			keyword = keyboard.nextLine();

			// Strip invalid characters (non-alphabetic)
			keyword = Cryptology.stripNonLetter(keyword);
			
			// Validate the keyword
			if (!clearText.contains(keyword) && (keyword.length() >= minimumLength && keyword.length() <= maximumLength) || nullKeywordOK) {
				
				finished = true;
			}
			else {
				
				System.out.println("\n" + "Keywords cannot be part of the clear text. Keywords must have at least " + minimumLength + " and no more than " + maximumLength + " alphabetic characters");
			}
		}

		keyword = keyword.toUpperCase();
		return keyword;
	}
}
