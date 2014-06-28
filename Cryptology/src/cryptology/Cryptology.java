package cryptology;

import java.util.Random;

public class Cryptology {

	// Weighted Letter Frequencies From A to Z:										  	  	  A		 B		 C	     D	    E	    F		 G		H	    I	    J		 K		L	     M	    N		 O		P	   Q	   R		S		T	   U	   V		W		X	   Y	   Z	
	
	public static final double[]	WEIGHTED_ENGLISH_LETTER_FREQUENCIES 				= { 8.167, 9.659, 12.441, 16.694, 29.396, 31.624, 33.639, 39.733, 46.699, 46.852, 47.624, 51.649, 54.055, 60.804, 68.311, 70.24, 70.335, 76.322, 82.649, 91.705, 94.463, 95.441, 97.801, 97.951, 99.925, 99.999 };
	public static final double[]	LETTER_FREQUENCIES_ENGLISH 							= { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074 };
	public static final char[]		ENGLISH_LETTERS										= { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	public Cryptology() {
		
	}
	
	public String stripNonLetter(String string) {
		
		// Extract words consisting of only letters, Exclude all digits, punctuation and whitespace
		// \\W means non-word character
		// \\d means digit
		// + means one or more than one such character
		String[] string_array = string.split("[\\W\\d]+");
		
		// Merge array into one upper case string
		String fin_string = "";
		for (String text : string_array) {
			fin_string += text;
		}
		return fin_string.toUpperCase();
	}
	
	public int findNearestWeightedFrequency(double searchValue) {
		
		// Don't do Binary Search! It does not guarantee find the CLOSEST value greater than the search value!
		
		int	indexFound = 0;
		for (int index = 0; index < WEIGHTED_ENGLISH_LETTER_FREQUENCIES.length; index++) {
			
			// Quit searching when a frequency greater or equal than the search value is found
			if (WEIGHTED_ENGLISH_LETTER_FREQUENCIES[index] >= searchValue) {
				
				indexFound = index;				
				break;
			}			
		}		
		return indexFound;
	}
	
	public char generateWeightedLetter() {
		Random random = new Random();
		char letter = 0;
		boolean letter_found = false;
		while (!letter_found) {
			for (int letter_index = 0; letter_index < ENGLISH_LETTERS.length; letter_index++) {
				if (random.nextInt(100000)*0.001 < LETTER_FREQUENCIES_ENGLISH[letter_index]) {
					letter = ENGLISH_LETTERS[letter_index];
					letter_found = true;
					break;
				}
			}
		}
		return letter;
	}
	
	/*
	 * Get Null Method
	 * 
	 * Randomly selects a letter from the English alphabet according to its weighted letter frequency.
	 * Nulls are used in regular cipher text to pad short rows.
	 * 
	 */
	
	public char getNull() {
		
		Random 	 random					= new Random();
		double	 randomCummulativeValue	= 0;
		char 	 randomCharacter		= ' ';
		
		// Randomly select a character from the cummulative ascending letter frequencies. nextDouble() returns a number from 0.0 (inclusive) to 1.0 (exclusive).
		// Multiplying it by 100.0 gives a value from 0.0 to 0.999999...
		randomCummulativeValue = random.nextDouble() * 100.0d;

		// Find the letter with the closest weighted frequency
		randomCharacter = ENGLISH_LETTERS[findNearestWeightedFrequency(randomCummulativeValue)];		
		return randomCharacter;
	}
}
