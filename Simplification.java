package application;

public class Simplification {

	Node[] nodesPart1 = new Node[26];
	Node[] nodesPart2 = new Node[26];
	Node[] nodesPart3 = new Node[26];
	
	Node result = new Node();
	
	public String simplify(String sop){
		nodesPart1 = new Node[26];
		nodesPart2 = new Node[26];
		nodesPart3 = new Node[26];
		result = new Node();
		extractTerms(sop);
		detDecimal();
		//TruthTable.setResultFunction1(nodesPart1);
		scan(nodesPart1,nodesPart2);
		scan(nodesPart2,nodesPart3);
		unMatchedNode(nodesPart1);
		unMatchedNode(nodesPart2);
		unMatchedNode(nodesPart3);
		eliminateResult();
		return writeResult();
	}
	
	public int numberOfElements(Node[] nodesPart){
		for (int i = 0; i < nodesPart.length; i++) {
			if (nodesPart[i] == null)
				return i;
		}
		return -1;
	}
	public void extractTerms(String sop){
		String[] terms = sop.split("=");
		String[] minterms = terms[1].split("\\+");
		
		for (int i = 0; i < minterms.length; i++){
			boolean isThere = false;
			String m = minterms[i].trim();
			for (int j = 0; j < nodesPart1.length; j++) {
				if (nodesPart1[j] != null && nodesPart1[j].getExp().equals(m)){
					isThere = true;
					break;
				}
			}
			if (!isThere){
				nodesPart1[i] = new Node();
				nodesPart1[i].setExp(m);
			}
		}
	}
	public void detDecimal(){
		
		for (int i = 0; i < nodesPart1.length; i++){
			if (nodesPart1[i] != null) {
				String[] literals = nodesPart1[i].getExp().split("\\."); 
				String binary = "";
				
				for (int j = 0; j < literals.length; j++) { // Literals length aslýnda n;
					if (literals[j].length() == 2)
						binary += 0;
					else
						binary += 1;
				}
				
				nodesPart1[i].setBinaryRep(binary);
				nodesPart1[i].setExp(Integer.parseInt(binary,2)+"");
				
			}
		}
		
	}	
	
	public void scan(Node[] part, Node[] part2){
		int length = numberOfElements(part);
		int count = 0;
		for (int i = 0; i < length; i++) {
			for (int j = i+1; j < length; j++){
				
				if (compare(part[i], part[j])){
					/*System.out.println(part[i].getBinaryRep() + " ile " +
									   part[j].getBinaryRep() + " deðiþim 1 tane!"  
							                  + " ve deðiþen bit " +
									   part[i].getChangedBit());*/
					
					part[i].setMatchPaired(true);
					part[j].setMatchPaired(true);
					
					StringBuilder binaryRep = new StringBuilder(part[i].getBinaryRep());
					binaryRep.setCharAt(part[i].getChangedBit(), '_');

					part2[count] = new Node();
					part2[count].setBinaryRep(binaryRep.toString());
					part2[count].setExp(part[i].getExp() + "-" + part[j].getExp());
					
					count++;
				}
			}
		}
	}
	
	public boolean compare(Node inode, Node jnode){
		
		int change = 0;
				
		for (int i = 0; i < inode.getBinaryRep().length(); i++){
			if (inode.getBinaryRep().charAt(i) != jnode.getBinaryRep().charAt(i)){
				inode.setChangedBit(i); // index soldan saða artsýn diye!
				change++;
			}
		}
		return (change == 1);
		
	}
	public void writeArray(Node[] arr){
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null)
				System.out.println(i + " ini index " + 
								 "Exp: " + arr[i].getExp() + " " + 
								 "BinRep: " + arr[i].getBinaryRep() + " ");
		}
	}
	public void unMatchedNode(Node[] arr){
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null && !arr[i].isMatchPaired() ){
				if (!result.getBinaryRep().contains(arr[i].getBinaryRep())){
					result.setExp(result.getExp() + " " + arr[i].getExp());
					result.setBinaryRep(result.getBinaryRep() + " " +arr[i].getBinaryRep());
				}
			}
		}
	}
	
	public void eliminateResult(){
		
		result.setExp(result.getExp().trim());
		result.setBinaryRep(result.getBinaryRep().trim());
		
		String[] termArr = result.getExp().replaceAll("-", " ").split(" ");
		Integer[] numberCounter = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		for (int i = 0; i < termArr.length; i++) {
			int counter = 0;
			for (int j = 0; j < termArr.length; j++){
				if (termArr[i].equals(termArr[j])){
					if (i != j) 
						termArr[j] = "";
					counter++;
				}
			}
			if (!termArr[i].equals(""))
				numberCounter[Integer.parseInt(termArr[i])] = counter;
		}
		
		String[] resultTerms = result.getExp().split(" ");
		String[] binaries = result.getBinaryRep().split(" ");
		
		result.setExp("");
		result.setBinaryRep("");
		for (int i = 0; i < resultTerms.length; i++) {
			String[] split = resultTerms[i].split("-");
			boolean deleted = true;
			for (int j = 0; j < split.length; j++) {
				
				if (numberCounter[Integer.parseInt(split[j])] == 1){ //this term absolutely essential.
					result.setExp(result.getExp() + resultTerms[i] + " ");
					result.setBinaryRep(result.getBinaryRep() + binaries[i] + " ");
					deleted = false;
					break;
				}
				
			}
			
			if (deleted){
				for (int j = 0; j < split.length; j++) 
					numberCounter[Integer.parseInt(split[j])] -= 1; 
			}
			
		}
		
		
		
	}
	
	public String writeResult(){
		String res = result.getBinaryRep().trim();
		
		String[] terms = res.split(" ");
		int len = terms[0].length();
		
		res = "";
		for (int i = 0; i < terms.length; i++){
			for (int j = 0; j < len; j++){
				boolean found = false;
				if (terms[i].charAt(j) == '0'){
					res += (char)(j + 65) + "'";
					found = true;
				}
				else if (terms[i].charAt(j) == '1'){
					res += (char)(j + 65);
					found = true;
				}
				if (found && j != len-1)
					res += ".";
			}
			if (terms.length-1 != i)
				res += " + ";
		}
		
		if (res.charAt(res.length()-1) == '.'){
			res = res.substring(0, res.length()-1);
		}
		
		return res;
	}
	
}
