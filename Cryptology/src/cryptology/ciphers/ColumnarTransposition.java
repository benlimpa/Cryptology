package cryptology.ciphers;

import java.util.Random;

import cryptology.Cryptology;

public class ColumnarTransposition extends Cryptology {

	private String				clearText;
	private String				cipherText1;
	private String				cipherText2;
	private char[][] 			transpositionMatrix1;
	private char[][] 			transpositionMatrix2;
	private int[] 				keyword1LetterOrder;
	private int[] 				keyword2LetterOrder;
	
	public static final int		COLUMNAR_TRANSPOSITION			= 1;
	public static final int		DOUBLE_COLUMNAR_TRANSPOSITION	= 2;
	public static final int		INITIALIZATION_CHARACTER		= ' ';		// ASCII 32 decimal
	public static final int		BLOCKED_OFF_CHARACTER			= '\0';		// ASCII 0 decimal
	public static final int		REGULAR_CIPHER_TEXT				= 1;
	public static final int		IRREGULAR_CIPHER_TEXT			= 2;

	/*
	 * Getters
	 * 
	 */
	
	public String getCipherText1() {
		
		return cipherText1;
	}
	
	public String getCipherText2() {
		
		return cipherText2;
	}
	
	public String getClearText() {
		
		return clearText;		
	}
	
	public char[][] getTranspositionMatrix1() {
		
		return transpositionMatrix1;
	}
	
	public char[][] getTranspositionMatrix2() {
		
		return transpositionMatrix2;
	}
	
	public int[] getKeyword1LetterOrder() {
		
		return keyword1LetterOrder;
	}
	
	public int[] getKeyword2LetterOrder() {
		
		return keyword2LetterOrder;
	}

	/*
	 * Columnar Transposition Cipher Encipher Method
	 * 
	 * Columnar Transposition uses a keyword. Keyword cannot be part of clear text.
	 * Select between regular (padded) and irregular (unpadded) cipher text.
	 */
	
	public void columnarTranspositionEncipher(String clearText, String keyword, boolean paddedCipherText) {
		
		cipherText1 						= "";
		int 	 columns 					= 0;
		int 	 rows						= 0;
		int		 rowsRemainder				= 0;
		int		 clearTextPosition 			= 0;
		int		 numberOfRandomCharacters	= 0;
		char 	 character					= ' ';
				
		// Write the clear text in rows on the keyword transposition matrix
		columns 		= keyword.length();
		rows			= clearText.length() / columns;
		rowsRemainder	= clearText.length() % columns;
		
		if (rowsRemainder > 0) {
			
			rows++;
		}

		// Initialize the keyword transposition matrix
		char[] 	 randomCharacters 	= new char[keyword.length()];
		transpositionMatrix1 		= new char[rows][columns];
		
		for (int row = 0; row < transpositionMatrix1.length; row++) {
			
			for (int col = 0; col < transpositionMatrix1[row].length; col++) {
				
				transpositionMatrix1[row][col] = INITIALIZATION_CHARACTER;
			}
		}
	
		// Write the clear text in rows, padding the last short row as needed if regular cipher text was selected
		for (int row = 0; row < transpositionMatrix1.length; row++) {
			
			for (int col = 0; col < transpositionMatrix1[row].length; col++) {
				
				if (clearTextPosition < clearText.length()) {
					
					transpositionMatrix1[row][col] = clearText.charAt(clearTextPosition);				
					clearTextPosition++;
				}
				else {
					
					// Regular cipher text: pad the clear text with a random letter
					if (paddedCipherText) {
						
						character = getNull();
						randomCharacters[numberOfRandomCharacters] = character;
						numberOfRandomCharacters++;
						transpositionMatrix1[row][col] = character;
					}
				}
			}
		}
			
		// Get the numerical values of the letters of keyword as offsets from A (65)
		int[] keywordNumericalValues = new int[keyword.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++) {
			
			keywordNumericalValues[index] = keyword.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword characters in ascending sequence. Parallel sort the keyword column order numbers.
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword column order into word order		
		keyword1LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++) {
			
			keyword1LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
			
		// Read the clear text in column order according to the sorted numerical values of each keyword character.
		// If this is an irregular cipher text, there may be an incomplete last row with one or more matrix cols still set to the initialization character.
		// If so, the initialization characters will be skipped.		
		for (int col = 0; col < keywordColumnOrder.length; col++) {
			
			for (int row = 0; row < rows; row++) {
				
				if (transpositionMatrix1[row][keywordColumnOrder[col]] != INITIALIZATION_CHARACTER) {
					
					cipherText1 = cipherText1 + transpositionMatrix1[row][keywordColumnOrder[col]];
				}
			}
		}
	}

	public void columnarTranspositionDecipher(String cipherText, String keyword)
	{
		clearText 							= "";
		int 	 columns 					= 0;
		int 	 rows						= 0;
		int		 rowsRemainder				= 0;
		int 	 cipherTextPosition			= 0;

		// Calculate the transposition matrix dimension
		columns 		= keyword.length();
		rows			= cipherText.length() / columns;
		rowsRemainder	= cipherText.length() % columns;
		
		if (rowsRemainder > 0) {
			
			rows++;
		}

		// Initialize the transposition matrix to the initialization character. If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row will be blocked off by initializing them to the block off character.
		transpositionMatrix1 = new char[rows][columns];

		for (int row = 0; row < rows; row++) {
			
			for (int col = 0; col < transpositionMatrix1[row].length; col++) {
				
				transpositionMatrix1[row][col] = INITIALIZATION_CHARACTER;
			}
		}
		
		if (rowsRemainder > 0) {
			
			for (int col = rowsRemainder; col < transpositionMatrix1[rows - 1].length; col++) {
				
				transpositionMatrix1[rows - 1][col] = BLOCKED_OFF_CHARACTER;
			}
		}
		
		// Get the numerical values of the letters of the keyword as offsets from A (65)
		int[] keywordNumericalValues = new int[keyword.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++)
		{
			keywordNumericalValues[index] = keyword.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword characters in ascending sequence. Parallel sort the keyword column order numbers.
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword column order into word order
		keyword1LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++) {
			
			keyword1LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Write the cipher text in col order on the transposition matrix. If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row are blocked off by initializing them to the block off character. Skip the blocked off cols.
		for (int col = 0; col < keywordColumnOrder.length; col++) {
			
			for (int row = 0; row < rows; row++) {
				
				if (transpositionMatrix1[row][keywordColumnOrder[col]] != BLOCKED_OFF_CHARACTER) {
					
					if (cipherTextPosition < cipherText.length()) {
						
						transpositionMatrix1[row][keywordColumnOrder[col]] = cipherText.charAt(cipherTextPosition);
						cipherTextPosition++;
					}
				}
			}
		}
		
		// Read the clear text in row order from the transposition matrix
		for (int row = 0; row < transpositionMatrix1.length; row++) {
			
			for (int col = 0; col < transpositionMatrix1[row].length; col++) {
				
				if (transpositionMatrix1[row][col] != BLOCKED_OFF_CHARACTER) {
					
					clearText = clearText + transpositionMatrix1[row][col];
				}
			}
		}
	}
	
	/*
	 * Double Columnar Transposition Cipher Encipher Method
	 * 
	 * Double Columnar Transposition uses two keywords. Keywords cannot be part of clear text.
	 * Select between regular (padded) and irregular (unpadded) cipher text.
	 * Only the first transposition would be padded. Padding both transpositions makes it impossible to decipher correctly.
	 */
	
	public void doubleColumnarTranspositionEncipher(String clearText, String keyword1, String keyword2, boolean paddedcipherText) {
		
		cipherText2 						= "";
		int 	 columns 					= 0;
		int 	 rows						= 0;
		int		 rowsRemainder				= 0;
		int 	 cipherTextLength			= 0;
		int 	 cipherTextPosition			= 0;
						
		/*
		 * First Columnar Transposition according to Keywowrd #1.
		 * Creates Transposition Matrix #1. Letter order is placed in keyword1LetterOrder. Intermediate cipher text is placed in cipherText1.
		 */
		
		columnarTranspositionEncipher(clearText, keyword1, paddedcipherText);
		
		/*
		 * Second Columnar Transposition according to Keyword #2.
		 * Creates Transposition Matrix #2. Letter order is placed in keyword2LetterOrder. Final cipher text is placed in cipherText2.
		 */

		// Get the length of the intermediate cipher text. Calculate the number of keyword #2 transposition matrix columns. 
		// Calculate the keyword #2 transposition matrix rows.
		
		cipherTextLength  	= cipherText1.length();
		columns 			= keyword2.length();
		rows				= cipherTextLength / columns;
		rowsRemainder		= cipherTextLength % columns;
		
		if (rowsRemainder > 0) {
			
			rows++;
		}
		
		// Initialize the keyword #2 transposition matrix	
		transpositionMatrix2 = new char[rows][columns];
		
		for (int row = 0; row < transpositionMatrix2.length; row++) {
			
			for (int col = 0; col < transpositionMatrix2[row].length; col++) {
				
				transpositionMatrix2[row][col] = INITIALIZATION_CHARACTER;
			}
		}
			
		// Read the intermediate cipher text in column order according to the column order of each keyword #1 character.
		// Write the intermediate cipher text in row order on the keyword #2 transposition matrix.
		// Only the first transposition would be padded. Padding both transpositions makes it impossible to decipher correctly.

		for (int row = 0; row < transpositionMatrix2.length; row++) {
			
			for (int col = 0; col < transpositionMatrix2[row].length; col++) {
				
				if (cipherTextPosition < cipherTextLength) {
					
					transpositionMatrix2[row][col] = cipherText1.charAt(cipherTextPosition);				
					cipherTextPosition++;
				}
			}
		}

		// Get the numerical values of the letters of keyword #2 as offsets from A (65)
		int[] keywordNumericalValues = new int[keyword2.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++) {
			
			keywordNumericalValues[index] = keyword2.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword #2 characters in ascending sequence. Parallel sort the keyword #2 column order numbers.
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword #2 column order into word order
		keyword2LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++) {
			
			keyword2LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Read the cipher text in column order according to the sorted numerical values of each keyword #2 character.
		// If this is an irregular cipher text, there may be an incomplete last row with one or more matrix cols still set to the initialization character.
		// If so, the initialization characters will be skipped. Write the final cipher text.
		
		for (int col = 0; col < keywordColumnOrder.length; col++) {
			
			for (int row = 0; row < rows; row++) {
				
				if (transpositionMatrix2[row][keywordColumnOrder[col]] != INITIALIZATION_CHARACTER) {
					
					cipherText2 = cipherText2 + transpositionMatrix2[row][keywordColumnOrder[col]];
				}
			}
		}
	}

	public void doubleColumnarTranspositionDecipher(String cipherText, String keyword1, String keyword2)
	{
		clearText 						= "";
		int 	 columns 				= 0;
		int 	 rows					= 0;
		int		 rowsRemainder			= 0;
		int 	 cipherTextLength		= cipherText.length();
		int 	 cipherTextPosition		= 0;

		/*
		 * First Columnar Transposition according to Keyword #2. Must decipher backwards (first keyword2 then keyword1).
		 * Creates Transposition Matrix #1. Letter order is placed in keyword1LetterOrder. Intermediate cipher text is placed in cipherText1.
		 */
		
		columnarTranspositionDecipher(cipherText, keyword2);
		
		/*
		 * Second Columnar Transposition accoding to Keyword #1.
		 * Creates Transposition Matrix #2. Letter order is placed in keyword2LetterOrder. Final cipher text is placed in cipherText2.
		 */

		// Get the intermediate cipher text length. Calculate the number of keyword #1 transposition matrix columns
		cipherTextLength 	= cipherText1.length();
		columns 			= keyword1.length();

		// Calculate the keyword #1 transposition matrix rows
		rows				= cipherTextLength / columns;
		rowsRemainder		= cipherTextLength % columns;
		
		if (rowsRemainder > 0) {
			
			rows++;
		}

		// Initialize the keyword #1 transposition matrix to the initialization character. If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row will be blocked off by initializing them to the block off character.
		
		transpositionMatrix2 = new char[rows][columns];

		for (int row = 0; row < rows; row++) {
			
			for (int col = 0; col < transpositionMatrix2[row].length; col++) {
				
				transpositionMatrix2[row][col] = INITIALIZATION_CHARACTER;
			}
		}
		
		if (rowsRemainder > 0) {
			
			for (int col = rowsRemainder; col < transpositionMatrix2[rows - 1].length; col++) {
				
				transpositionMatrix2[rows - 1][col] = BLOCKED_OFF_CHARACTER;
			}
		}
		
		// Get the numerical values of the letters of keyword #1 as offsets from A (65)
		int[] keywordNumericalValues = new int[keyword1.length()];

		for (int index = 0; index < keywordNumericalValues.length; index++) {
			
			keywordNumericalValues[index] = keyword1.charAt(index) - 65;
		}

		// Sort the numerical values of the keyword #1 characters in ascending sequence. Parallel sort the keyword #1 column order numbers.		
		int[] keywordColumnOrder = sortKeywordColumns(keywordNumericalValues);
		
		// Transform the keyword #1 column order into word order		
		keyword2LetterOrder = new int[keywordColumnOrder.length];
		
		for (int index = 0; index < keywordColumnOrder.length; index++) {
			
			keyword2LetterOrder[keywordColumnOrder[index]] = index + 1;
		}
		
		// Write the intermediate cipher text in col order on the transposition matrix.
		// If this is an irregular cipher text, there may be an incomplete last row.
		// if so, the unneeded cols on the last incomplete row are blocked off by initializing them to the block off character.
		// Skip the blocked off cols.
		
		cipherTextPosition = 0;

		for (int col = 0; col < keywordColumnOrder.length; col++) {
			
			for (int row = 0; row < rows; row++) {
				
				if (transpositionMatrix2[row][keywordColumnOrder[col]] != BLOCKED_OFF_CHARACTER) {
					
					if (cipherTextPosition < cipherText.length()) {
						
						transpositionMatrix2[row][keywordColumnOrder[col]] = cipherText1.charAt(cipherTextPosition);
						cipherTextPosition++;
					}
				}
			}
		}
		
		// Read the cipher text in row order from the keyword #1 transposition matrix. Write the clear text.
		for (int row = 0; row < transpositionMatrix2.length; row++) {
			
			for (int col = 0; col < transpositionMatrix2[row].length; col++) {
				
				if (transpositionMatrix2[row][col] != BLOCKED_OFF_CHARACTER) {
					
					clearText = clearText + transpositionMatrix2[row][col];
				}
			}
		}
	}
	
	/*
	 * Sort Keyword Columns Method
	 * 
	 * Insertion Sort algorithm. This is a stable Sort algorithm.
	 * It is called stable if it keeps elements with equal keys in the same relative order in the output as they were in the input.
	 * Other stable sorts: Bubble Sort, Merge Sort, Counting Sort. Selection Sort and most implementations of Quick Sort are not stable sorts.
	 * Sort the numerical values of the keyword characters and parallel sort the keyword column order #s in ascending sequence.
	 */
	
	public int[] sortKeywordColumns(int[] keywordNumericalValues)
	{
		int	minimum 		= 0;
		int	compare			= 0;
		int minimumIndex	= 0;

		int[] columnOrder	= new int[keywordNumericalValues.length];
		
		for (int index = 0; index < columnOrder.length; index++) {
			
			columnOrder[index] = index;
		}
		
		// Sort the array		
		for (int pass = 1; pass < keywordNumericalValues.length; pass++) {
			
			// Save the new minimum value and the minimum column index			
			minimum = keywordNumericalValues[pass];
			minimumIndex = pass;
			
			// The Compare and Swap Loop
			for (compare = pass - 1; compare >= 0 && minimum < keywordNumericalValues[compare]; compare--) {
				
				// Move the larger value down and save its column index
				keywordNumericalValues[compare + 1] = keywordNumericalValues[compare];
				columnOrder[compare + 1] = columnOrder[compare];
			}

			// Move the smaller value found to the top and save its column index
			keywordNumericalValues[compare + 1] = minimum;
			columnOrder[compare + 1] = minimumIndex;
		}
		
		return columnOrder;	
	}
}
