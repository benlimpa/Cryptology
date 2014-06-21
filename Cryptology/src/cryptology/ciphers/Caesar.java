package cryptology.ciphers;

import cryptology.Cryptology;

public class Caesar extends Cryptology {

	
	public Caesar() {
	}
	
	//public void setShiftValue(int shift_value) {this.shift_value = shift_value;}

	public String encipher(String clearText, int shiftValue) {
		
		String finCipherText = "";

		String finClearText = stripNonLetter(clearText);
		
		// Shift characters
		for (int i = 0; i < finClearText.length(); i++) {
			int character = (finClearText.charAt(i) + shiftValue);
			
			// Old Algorithm - DOES NOT SUPPORT SHIFT VALUES > 26 OR < 0
			/*
			if (character > 90) {
				//character = (char)(character - 26);
				character -= 26;
			}
			*/
			
			// New Algorithm - Wrap values outside of character set back around
			character -= 64;		// Shift down from ascii values
			character %= 26;		// Get remainder after dividing 26
			if (character <= 0)		// (Special case if shift value is negative) OR (remainder is 0 so it must be z)
				character += 26;
			character += 64;		// Shift back up to ascii values
			finCipherText += String.valueOf((char)character); // Append to final string
		}
		return finCipherText;
	}

	public String decipher(String cipherText, int shiftValue) {
		
		/*
		 *  New Algorithm - Wrap values outside of character set back around instead
		 */
		
		String finClearText = "";

		String finCipherText = stripNonLetter(cipherText);
		
		// Shift characters back
		for (int i = 0; i < finCipherText.length(); i++) {
			
			int character = (finCipherText.charAt(i) - shiftValue);
			
			character -= 64;			// Shift down from ascii values (Shift from 65-90 to 1-26)
			character %= 26;			// Get remainder after dividing 26 to ensure that the value is within 1-26
			if (character <= 0) {		// (Special case if shift value is negative) OR (remainder is 0 so it must be z)
				
				character += 26;
			}
			character += 64;			// Shift back up to ascii values (Shift from 1-26 to)
			finClearText += String.valueOf((char)character);		// Append to final string
		}
		return finClearText;
	}
}
