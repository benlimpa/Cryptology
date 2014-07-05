package cryptology;

import java.util.Scanner;

import cryptology.ciphers.Caesar;

public class View {

	private Scanner			keyboard;
	
	private final String	EXIT		= "X";	
	private final String	ENCIPHER	= "E";
	private final String	DECIPHER	= "D";
	
	// Settings / User Preferences
	private boolean			padText		= true;
	private int				chunkSize	= 4;
	
	public View() {
		keyboard = new Scanner(System.in);
	}
	
	public void displayMenu() {
		
		final String[]	MENU_OPTION_INFO	= {	"What would you like to do?:",
												"Cryptography		- [G]",
												"Cryptanalysis		- [A]",
												"",
												"Settings		- [S]",
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
					String stripText = getText("text to be stripped");
					System.out.println("\nStripped String:  " + Cryptology.stripNonLetter(stripText));
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private void displaySettings() {
		
		final String[] SETTINGS_OPTION_INFO = {	"Chunk Size		- [C]",
												"Pad Text		- [P]"};
		
		final String CHUNK_SIZE				=	"C";
		final String PAD_TEXT				=	"P";
		
		String menuOption = "";
		
		while (!menuOption.equalsIgnoreCase(EXIT)) {
			
			// Display Title
			System.out.println("\n-------------");
			System.out.println("--Settings:--\n");
			
			// Display Options
			for (String info : SETTINGS_OPTION_INFO) {
				
				System.out.println(info);
			}
			
			// Allow user to select option
			System.out.print("\nE[X]it or choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase()) {
			
				case CHUNK_SIZE:
					System.out.println("Current Chunk Size: " + chunkSize);
					chunkSize = getInteger("New Chunk Size");
					System.out.println("Updated Chunk Size: " + chunkSize);
					break;
				case PAD_TEXT:
					System.out.println("Current: Pad Text: " + padText);
					padText = getBoolean("to Pad Text");
					System.out.println("Updated: Pad Text: " + padText);
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private void displayCryptography() {
		
		final String[]	CRYPTOGRAPHY_OPTION_INFO	= {	"Types of Ciphers:",
														"Substituion Cipher	- [S]",
														"Transposition Cipher	- [T]"};

		final String	SUBSTITUTION_CIPHER			=	"S";
		final String	TRANSPOSITON_CIPHER			=	"T";
		
		String			menuOption			=	"";
		
		while (!menuOption.equalsIgnoreCase(EXIT)) {
			
			// Display Title
			System.out.println("\n-----------------");
			System.out.println("--Cryptography:--\n");
			
			// Display Options
			for (String info : CRYPTOGRAPHY_OPTION_INFO) {
				
				System.out.println(info);
			}
			
			// Allow user to select option
			System.out.print("\nE[X]it or choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase()) {
			
				case SUBSTITUTION_CIPHER:
					displaySubstitutionCiphers();
					break;
				case TRANSPOSITON_CIPHER:
					displayTranspositionCiphers();
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private void displayCryptanalysis() {
		
	}
	
	private void displayTranspositionCiphers() {
		
	}

	private void displaySubstitutionCiphers() {
		
		final String[]	SUBSTITUTION_CIPHER_INFO	= { "Types of Substitution Ciphers:",
														"Caesar's Cipher - [C]"};
		
		final String	CAESAR_CIPHER				=	"C";
		
		String menuOption = "";
		
		while (!menuOption.equalsIgnoreCase(EXIT)) {
			
			// Display Title
			System.out.println("\n-------------------------");
			System.out.println("--Substitution Ciphers:--\n");
			
			// Display Options
			for (String info : SUBSTITUTION_CIPHER_INFO) {
				
				System.out.println(info);
			}
			
			// Allow user to select option
			System.out.print("\nE[X]it or choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase()) {
				case CAESAR_CIPHER:
					displayCaesarCipher();
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}

	private void displayCaesarCipher() {
		
		final String[]	CAESAR_CIPHER_INFO	= { "Options:",
												"Encipher - [E]",
												"Decipher - [D]"};
		
		String			menuOption = "";
		int				shiftValue;
		
		Caesar caesar = new Caesar();
		
		while (!menuOption.equalsIgnoreCase(EXIT)) {
			
			// Display Title
			System.out.println("\n-------------------");
			System.out.println("--Caesar's Cipher--\n");
			
			// Display Options
			for (String info : CAESAR_CIPHER_INFO) {
				System.out.println(info);
			}
			
			System.out.print("\nE[X]it or choose a menu option: ");
			menuOption = keyboard.nextLine();
			
			switch (menuOption.toUpperCase()) {
				
				case ENCIPHER:
					String	clearText		= getText("Clear Text");
							shiftValue		= getInteger("Shift Value");
					String	encipheredText	= caesar.encipher(clearText, shiftValue, padText, chunkSize);
					System.out.println("Enciphered Text:\n"+encipheredText);
					break;
				case DECIPHER:
					String	cipherText	= getText("Cipher Text");
							shiftValue	= getInteger("Shift Value");
					String	decipheredText = caesar.decipher(cipherText, shiftValue);
					System.out.println("Deciphered Text:\n"+decipheredText);
					break;
				case EXIT:break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private int getInteger(String label) {
		
		int value = 0;
		boolean valueFound;
		
		do {
			
			try {
				
				System.out.print("\nPlease enter \"" + label + "\" [Integer]:  ");
				value = keyboard.nextInt();
				keyboard.nextLine();
				valueFound = true;
			} catch (Exception e) {
				
				System.out.println("Invalid Input");
				keyboard.nextLine();
				valueFound = false;
			}
		} while (!valueFound);
		
		return value;
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
				
				System.out.println("\nWould you like \"" + label + "\"? [true/false]:  ");
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
