package cryptology.ciphers;

import cryptology.Cryptology;

public class Caesar extends Cryptology {
	
	public Caesar() {
	}
	
	//public void setShiftValue(int shift_value) {this.shift_value = shift_value;}

	public String encipher(String clearText, int shiftValue, boolean padText, int chunkSize) {
		
		/*
		 *  New Algorithm - Wrap values outside of character set back around instead
		 */
		
		String finCipherText = "";

		String finClearText = stripNonLetter(clearText);
		
		// Shift characters
		for (int i = 0; i < finClearText.length(); i++) {
			
			int character = (finClearText.charAt(i) + shiftValue);
			
			character -= 64;			// Shift down from ascii values (Shift from 65-90 to 1-26)
			character %= 26;			// Get remainder after dividing 26 to ensure that the value is within 1-26
			if (character <= 0) {		// (Special case if shift value is negative) OR (remainder is 0 so it must be z)
				
				character += 26;
			}
			character += 64;			// Shift back up to ascii values (Shift from 1-26 to)
			finCipherText += String.valueOf((char)character);		// Append to final string
		}
		
		// Pad Text
		if (padText) {
			
			int charsToGen = finCipherText.length() % chunkSize;
			String padChars = "";
			
			for (int i = 0; i < charsToGen; i++) {
				padChars += getNull();
			}
			
			finCipherText += padChars;
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
