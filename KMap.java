package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class KMap {

	
	boolean isOnePossibility;
	
	String sop;
	int numberOf1s = 0;
	String result;
	
	static int inputSize = 4;
	GridPane grid;
	KCell[][] kMap;
	
	KCell[] groupOf8s;
	KCell[] groupOf4s;
	KCell[] groupOf2s;
	KCell[] Of1s;
	
	KCell[] temp;
	
	KMap(){
		temp = new KCell[16];
		Of1s = new KCell[16];
		groupOf2s = new KCell[30];
		groupOf4s = new KCell[30];
		groupOf8s = new KCell[30];
		result = "";
	}
	
	void createKMap(){
		grid = new GridPane();
		
		StringBuilder showVariable = new StringBuilder("");
		for (int i = 0; i < inputSize; i++){
			if (i == Math.floor(Math.log(inputSize)/Math.log(2)))
				showVariable.append(" / ");
			showVariable.append((char)(i+65));
		}
		grid.add(new Label(showVariable.toString()), 0, 0);
		
		int boundOfY = (int) Math.floor(Math.pow(2, inputSize)/inputSize);	
		int boundOfX = (int) (Math.pow(2, inputSize)/boundOfY);

		for (int y = 1; y <= boundOfY; y++){
			if (inputSize != 2 && inputSize != 3)
				if (y == 3)
					grid.add(new Label("11"), 0, y);
				else if (y == 4)
					grid.add(new Label("10"), 0, y);
				else 
					grid.add(new Label("0" + Integer.toBinaryString(y-1)), 0, y);
			else
				grid.add(new Label(Integer.toBinaryString(y-1)), 0, y);
		}
		
		for (int x = 1; x <= boundOfX; x++){
			if (inputSize != 2)
				if (x == 3)
					grid.add(new Label("11"), x, 0);
				else if (x == 4)
					grid.add(new Label("10"), x, 0);
				else
					grid.add(new Label("0" + Integer.toBinaryString(x-1)), x, 0);
			else
				grid.add(new Label(Integer.toBinaryString(x-1)), x, 0);
		}
		
		kMap = new KCell[boundOfY][boundOfX];
		
		// Binary conversion of given expression
		String[] terms = sop.split("=");
		String[] minterms = terms[1].split("\\+");
		String k = "";
		for (int i = 0; i < minterms.length; i++) {
			minterms[i] = minterms[i].trim();
			String[] term = minterms[i].split("\\.");
			for (int j = 0; j < term.length; j++) {
				if (term[j].length() == 2)
					k += 0;
				else
					k += 1;
			}
			k += " ";
		}
		
		String[] binaryRep = k.split(" ");

		// Populate data to Karnaugh Map
		for (int i = 0; i < binaryRep.length; i++){
			int xx = giveCoord(binaryRep[i].substring((int) (Math.log(inputSize)/Math.log(2))));
			int yy = giveCoord(binaryRep[i].substring(0, (int) (Math.log(inputSize)/Math.log(2))));
			grid.add(new Button("1"), xx, yy);
			kMap[yy-1][xx-1] = new KCell(xx,yy,1,binaryRep[i]);
			numberOf1s++;
		}
		for (int y = 1; y <= boundOfY; y++){
			for (int x = 1; x <= boundOfX; x++) {
				if (getNodeFromGridPane(grid, x, y) == null){
					grid.add(new Button("0"), x, y);
					kMap[y-1][x-1] = new KCell(x,y,0,"");
				}
			}
		}
		
		
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(12);
	    grid.setVgap(12);
		
	    for (int y = 0; y < kMap.length; y++) {
			for (int x = 0; x < kMap[0].length; x++) {
				
				if (kMap[y][x].getKey() == 1){
					if (y-1 >= 0 && kMap[y-1][x].getKey() == 1) 
						kMap[y][x].setUp(kMap[y-1][x]);
					if (x+1 < kMap[0].length && kMap[y][x+1].getKey() == 1)
						kMap[y][x].setRight(kMap[y][x+1]);
					if (y+1 < kMap.length && kMap[y+1][x].getKey() == 1)
						kMap[y][x].setDown(kMap[y+1][x]);
					if (x-1 >= 0 && kMap[y][x-1].getKey() == 1)
						kMap[y][x].setLeft(kMap[y][x-1]);
					
					if (y == 0 && kMap[kMap.length-1][x].getKey() == 1)
						kMap[y][x].setUp(kMap[kMap.length-1][x]);
					if (x == kMap[0].length-1 && kMap[y][0].getKey() == 1)
						kMap[y][x].setRight(kMap[y][0]);
					if (y == kMap.length-1 && kMap[0][x].getKey() == 1)
						kMap[y][x].setDown(kMap[0][x]);
					if (x == 0 && kMap[y][kMap[0].length-1].getKey() == 1)
						kMap[y][x].setLeft(kMap[y][kMap[0].length-1]);
					
				}
				
			}
		}
	    
	}

	int up(KCell kCell){
		
		int arrCounter = emptySlot(temp);
		
		temp[arrCounter] = kCell;
		arrCounter++;
		
		int numberOfOne = 1;
		
		KCell travelUp = kCell.getUp();
		
		while (travelUp != null && travelUp != kCell){
			temp[arrCounter] = travelUp;
			arrCounter++;
			numberOfOne++;
			travelUp = travelUp.getUp();
		}
		
		return numberOfOne;
		
	}
	int right(KCell kCell){
		
		int arrCounter = emptySlot(temp);
		
		temp[arrCounter] = kCell;
		arrCounter++;
		
		int numberOfOne = 1;
		
		KCell travelUp = kCell.getRight();
		
		while (travelUp != null && travelUp != kCell){
			temp[arrCounter] = travelUp;
			arrCounter++;
			numberOfOne++;
			travelUp = travelUp.getRight();
		}
		
		return numberOfOne;
	}
	int down(KCell kCell){
		
		int arrCounter = emptySlot(temp);
		
		temp[arrCounter] = kCell;
		arrCounter++;
		
		int numberOfOne = 1;
		
		KCell travelUp = kCell.getDown();
		
		while (travelUp != null && travelUp != kCell){
			temp[arrCounter] = travelUp;
			arrCounter++;
			numberOfOne++;
			travelUp = travelUp.getDown();
		}
		
		return numberOfOne;
	}
	int left(KCell kCell){
		
		int arrCounter = emptySlot(temp);
		
		temp[arrCounter] = kCell;
		arrCounter++;
		
		int numberOfOne = 1;
		
		KCell travelUp = kCell.getLeft();
		
		while (travelUp != null && travelUp != kCell){
			temp[arrCounter] = travelUp;
			arrCounter++;
			numberOfOne++;
			travelUp = travelUp.getLeft();
		}
		
		return numberOfOne;
	}

	public GridPane simplification(){
		createKMap();
		findPossibility();
		return grid;
	}
	
	private void findPossibility() {
		isOnePossibility = false;
		int priority = 5;
		while (priority > 0){
			for (int y = 0; y < kMap.length; y++) {
				for (int x = 0; x < kMap[0].length; x++) {
					if (kMap[y][x].getKey() == 1 && !kMap[y][x].isLooped()){
						
						if (priority == 5){
							int adjacentNodes = 0;
							if (kMap[y][x].getUp() != null)
								adjacentNodes++;
							if (kMap[y][x].getRight() != null)
								adjacentNodes++;
							if (kMap[y][x].getDown() != null)
								adjacentNodes++;
							if (kMap[y][x].getLeft() != null)
								adjacentNodes++;
							
							if (adjacentNodes == 1){ // one possibility to get paired !
								scan(kMap[y][x],1,groupOf2s);
								isOnePossibility = true;
							}
						}
						
						else {
							if (priority == 4)
								scan(kMap[y][x],4,groupOf8s);
							else if (priority == 3)
								scan(kMap[y][x],2,groupOf4s);
							else if (priority == 2)
								scan(kMap[y][x],1,groupOf2s);
							else
								scan(kMap[y][x],0,Of1s);
						}
					}
				}
			}
			priority--;
		}

		colouring();
		
	}
	
	public String giveResult(){
		
		result = "";
		
		writeResult(groupOf8s,8);
		writeResult(groupOf4s,4);
		writeResult(groupOf2s,2);
		
		int len = emptySlot(Of1s);
		for(int i = 0; i < len; i++){
			for (int j = 0; j < inputSize; j++){
				if (Of1s[i].getBinaryRep().charAt(j) == '0')
					result += (char)(j+65) + "'"; 
				else
					result += (char)(j+65);
			}
			result += " ";
		}
		
		StringBuilder resultt = new StringBuilder(result);
		resultt.setCharAt(result.length()-1, ' ');
		result = resultt.toString();
		
		System.out.println(result);
		
		if (numberOf1s == 16)
			return "1";
		else
			return result;
		
	}
	public void writeResult(KCell[] whichArray,int value){
		
		int len = emptySlot(whichArray);
		
		if (len > 0){ // we need to compare
			int cofactor = 1;
			for (int i = 0; i < len; i++){
				
				String binaries = whichArray[i].getBinaryRep();
				int k = i+1;
				while (k < value * cofactor && k < len){
					binaries += whichArray[k].getBinaryRep();
					k++;
				}

				String deletedIndex = "";
				for (int j = 0; j < inputSize; j++){
					for (int t = j+inputSize; t < binaries.length(); t+=inputSize){
						if (binaries.charAt(j) != binaries.charAt(t)){
							deletedIndex += j;
							break;
						}
					}
				}	
				
				for (int j = 0; j < inputSize; j++){
					if (deletedIndex.indexOf(Integer.toString(j)) == -1){
						if (whichArray[i].getBinaryRep().charAt(j) == '0')
							result += (char)(j+65) + "'";
						else
							result += (char)(j+65);
						
					}
				}
				
				result += "+";
				i += value-1;
				cofactor++;
			}
		}
		
	}
	
	private void colouring() {
		
		int len = emptySlot(groupOf8s);
		
		for (int i = 0; i < len; i++) {
			
			Button temp =	(Button)getNodeFromGridPane(grid, groupOf8s[i].getLocation().getX(), 
							groupOf8s[i].getLocation().getY());

			if (groupOf8s[i].getIsOctet() != 1)
				temp.setStyle("-fx-base: #9400D3;");
			else
				temp.setStyle("-fx-base: #FF69B4;");
			
		}

		len = emptySlot(groupOf4s);
		
		for (int i = 0; i < len; i++) {
			
			Button temp =	(Button)getNodeFromGridPane(grid, groupOf4s[i].getLocation().getX(), 
							groupOf4s[i].getLocation().getY());

			if (groupOf4s[i].getIsQuad() != 1)
				temp.setStyle("-fx-base: #FFD700;");
			else
				temp.setStyle("-fx-base: #FFB6C1;");
			
		}
		
		len = emptySlot(groupOf2s);
		
		for (int i = 0; i < len; i++) {
			
			Button temp =	(Button)getNodeFromGridPane(grid, groupOf2s[i].getLocation().getX(), 
							groupOf2s[i].getLocation().getY());

			if (groupOf2s[i].getIsPair() != 1)
				temp.setStyle("-fx-base: #9ACD32;");
			else
				temp.setStyle("-fx-base: #F5DEB3;");
			
		}
		
		
		
		
	}

	private void scan(KCell kCell,int value,KCell[] whichArray) {
		
		if (value >= 2) {
			if (kCell.getUp() != null && kCell.getRight() != null) {
				if (up(kCell) == value && up(kCell.getRight()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];

			}
			if (kCell.getUp() != null && kCell.getLeft() != null) {
				if (up(kCell) == value && up(kCell.getLeft()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];

			}
			if (kCell.getRight() != null && kCell.getUp() != null) {
				if (right(kCell) == value && right(kCell.getUp()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];

			}
			if (kCell.getRight() != null && kCell.getDown() != null) {
				if (right(kCell) == value && right(kCell.getDown()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];

			}
			if (kCell.getDown() != null && kCell.getRight() != null) {
				if (down(kCell) == value && down(kCell.getRight()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];

			}
			if (kCell.getDown() != null && kCell.getLeft() != null) {
				if (down(kCell) == value && down(kCell.getLeft()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];

			}
			if (kCell.getLeft() != null && kCell.getUp() != null) {
				if (left(kCell) == value && left(kCell.getUp()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];

			}
			if (kCell.getLeft() != null && kCell.getDown() != null) {
				if (left(kCell) == value && left(kCell.getDown()) == value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];
			}
			
		}
		if (value == 2 || value == 1){
			int numberOfOnes = 0;
			if (kCell.getUp() != null){
				numberOfOnes = up(kCell);
				
				if (numberOfOnes == 3 && value == 1){
					temp[emptySlot(temp)-1] = null;
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				} 
				else if (numberOfOnes == 2*value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];
			}
			if (kCell.getRight() != null){
				numberOfOnes = right(kCell);
				if (numberOfOnes == 3 && value == 1){
					temp[emptySlot(temp)-1] = null;
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else if (numberOfOnes == 2*value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];
			}
			if (kCell.getDown() != null){
				numberOfOnes = down(kCell);
				if (numberOfOnes == 3 && value == 1){
					temp[emptySlot(temp)-1] = null;
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else if (numberOfOnes == 2*value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];
			}
			if (kCell.getLeft() != null){
				numberOfOnes = left(kCell);
				if (numberOfOnes == 3 && value == 1){
					temp[emptySlot(temp)-1] = null;
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else if (numberOfOnes == 2*value){
					assignArrFromTemp(whichArray);
					kCell.setLooped(true);
				}
				else
					temp = new KCell[30];
			}
		}
		else if (value == 0)
			if (kCell.getUp() == null && kCell.getRight() == null && kCell.getDown() == null && kCell.getLeft() == null){
				temp[0] = kCell;
				assignArrFromTemp(whichArray);
				kCell.setLooped(true);
			}
		
	}

	private void assignArrFromTemp(KCell[] whichArray) {

		int slot = emptySlot(whichArray);
		int tempLength = emptySlot(temp);
		
		boolean haveToAssign = false;
		if (slot == 0)
			haveToAssign = true; // there wont be any comparison because whicharray
								//has no elements to compare with temp
		
		for (int i = 0; i < tempLength; i++){
			
			haveToAssign = true;
			for (int j = 0; j < slot; j++){
				if (temp[i] == whichArray[j] || temp[i].isLooped()){
					haveToAssign = false;
					break;
				}
			}
			if (haveToAssign){ // false a girmedi demek ki true yani farklý çýk direkt.
				if(!isAvailableOtherGroup(temp[i]))
					break;
				else
					haveToAssign = false;
			}
				
		}
		
		if (haveToAssign) {
			for (int i = 0; i < tempLength; i++) {

				if (tempLength == 8)
					temp[i].setIsOctet(temp[i].getIsOctet() + 1);
				else if (tempLength == 4)
					temp[i].setIsQuad(temp[i].getIsQuad() + 1);
				else if (tempLength == 2)
					temp[i].setIsPair(temp[i].getIsPair() + 1);
				else
					temp[i].setIsAlone(temp[i].getIsAlone() + 1);
				
				temp[i].setLooped(true);
					
				whichArray[slot + i] = temp[i];
				temp[i] = null;

			} 
		}
		else
			temp = new KCell[30];
	
	}

	private boolean isAvailableOtherGroup(KCell kCell) {
		
		int slot = emptySlot(groupOf2s);
		for (int i = 0; i < slot; i++){
			if (kCell.getBinaryRep().equals(groupOf2s[i].getBinaryRep()))
				return true;
		}
		slot = emptySlot(groupOf4s);
		for (int i = 0; i < slot; i++){
			if (kCell.getBinaryRep().equals(groupOf4s[i].getBinaryRep()))
				return true;
		}
		slot = emptySlot(groupOf8s);
		for (int i = 0; i < slot; i++){
			if (kCell.getBinaryRep().equals(groupOf8s[i].getBinaryRep()))
				return true;
		}
		return false;
		
	}

	public String getSop() {
		return sop;
	}
	
	public void setSop(String sop) {
		this.sop = sop;
	}
	javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
	    for (javafx.scene.Node node : gridPane.getChildren()) {
	        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
	            return node;
	        }
	    }
	    return null;
	}
	
	public int giveCoord(String firstPoint){
		if (firstPoint.length() == 2){
			if (firstPoint.equals("00"))
				return 1;
			else if (firstPoint.equals("01"))
				return 2;
			else if (firstPoint.equals("11"))
				return 3;
			else
				return 4;
		}
		else {
			if (firstPoint.equals("0"))
				return 1;
			else 
				return 2;
		}
		
	}
	
	public int emptySlot(KCell[] kCell){
		for (int i = 0; i < kCell.length; i++) {
			if (kCell[i] == null)
				return i;
		}
		return -1;
	}
	
}
