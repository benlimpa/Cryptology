package cryptology;

public class Cryptology {

	// Weighted Letter Frequencies From A to Z:										  	  	  A		 B		 C	     D	    E	    F		 G		H	    I	    J		 K		L	     M	    N		 O		P	   Q	   R		S		T	   U	   V		W		X	   Y	   Z	
	
	public static final double[]	WEIGHTED_ENGLISH_LETTER_FREQUENCIES 				= { 8.167, 9.659, 12.441, 16.694, 29.396, 31.624, 33.639, 39.733, 46.699, 46.852, 47.624, 51.649, 54.055, 60.804, 68.311, 70.24, 70.335, 76.322, 82.649, 91.705, 94.463, 95.441, 97.801, 97.951, 99.925, 99.999 };
	
	public Cryptology() {
		
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
}
