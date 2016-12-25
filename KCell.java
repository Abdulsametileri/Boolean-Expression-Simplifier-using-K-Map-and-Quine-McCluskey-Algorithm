package application;


public class KCell {

	Location location;
	
	private int key;
	private int isPair = 0;
	private int isQuad = 0;
	private int isOctet = 0;
	private int isAlone = 0;
	
	private boolean isStartTerm;
	
	private boolean isLooped;
	
	private KCell up;
	private KCell down;
	private KCell left;
	private KCell right;

	private String binaryRep;
	
	KCell(int x, int y, int key,String binaryRep){
		location = new Location(x,y);
		this.key = key;
		up = null; down = null; left = null; right = null;
		this.binaryRep = binaryRep;
		isLooped = false;
		isStartTerm = false;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public KCell getUp() {
		return up;
	}

	public void setUp(KCell up) {
		this.up = up;
	}

	public KCell getDown() {
		return down;
	}

	public void setDown(KCell down) {
		this.down = down;
	}

	public KCell getLeft() {
		return left;
	}

	public void setLeft(KCell left) {
		this.left = left;
	}

	public KCell getRight() {
		return right;
	}

	public void setRight(KCell right) {
		this.right = right;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getIsQuad() {
		return isQuad;
	}

	public void setIsQuad(int isQuad) {
		this.isQuad = isQuad;
	}

	public int getIsOctet() {
		return isOctet;
	}

	public void setIsOctet(int isOctet) {
		this.isOctet = isOctet;
	}

	public String getBinaryRep() {
		return binaryRep;
	}

	public void setBinaryRep(String binaryRep) {
		this.binaryRep = binaryRep;
	}

	public int getIsPair() {
		return isPair;
	}

	public void setIsPair(int isPair) {
		this.isPair = isPair;
	}

	public boolean isLooped() {
		return isLooped;
	}

	public void setLooped(boolean isLooped) {
		this.isLooped = isLooped;
	}

	public int getIsAlone() {
		return isAlone;
	}

	public void setIsAlone(int isAlone) {
		this.isAlone = isAlone;
	}

	public boolean isStartTerm() {
		return isStartTerm;
	}

	public void setStartTerm(boolean isStartTerm) {
		this.isStartTerm = isStartTerm;
	}

	
	
	
	
}
