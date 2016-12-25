package application;

public class Node {

	private boolean matchPaired;
	private String exp;
	private int changedBit; // it contains index of changed bit.
	private String binaryRep;
	
	
	public Node() {
		matchPaired = false;
		exp = " ";
		changedBit = -1;
		binaryRep = " ";
	}
	
	public int getBit(int n, int digit) {		
	    return (n >> digit) & 1;
	}
	
	public boolean isMatchPaired() {
		return matchPaired;
	}
	public void setMatchPaired(boolean matchPaired) {
		this.matchPaired = matchPaired;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}

	public int getChangedBit() {
		return changedBit;
	}

	public void setChangedBit(int changedBit) {
		this.changedBit = changedBit;
	}

	public String getBinaryRep() {
		return binaryRep;
	}

	public void setBinaryRep(String binaryRep) {
		this.binaryRep = binaryRep;
	}
	
	
	
	
	
}
