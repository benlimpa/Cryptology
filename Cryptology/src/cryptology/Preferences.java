package cryptology;

public class Preferences {
	
	// Global Preferences
	private int		blockSize;
	private boolean padding;
	private boolean details;
	
	// Substitution Preferences
	private int		shiftValue;
	
	// Transposition Preferences
	
	
	public int		getBlockSize()	{return blockSize;}
	public boolean	getPadding()	{return padding;}
	public boolean	getDetails()	{return details;}
	
	public void setBlockSize(int blockSize) {this.blockSize = blockSize;}
	public void setPadding(boolean padding) {this.padding = padding;}
	public void setDetails(boolean details) {this.details = details;}
	
}
