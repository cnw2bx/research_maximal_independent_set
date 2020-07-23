import java.util.ArrayList;
import java.util.HashMap;

public class CamDaddy {
	
	public static ArrayList<String> binaryStringConstruct(int length){
		ArrayList<String> codeWords = new ArrayList<String>();
		for(int k = 0; k < Math.pow(2, length);k++) {
			String str = Integer.toBinaryString(k);
			for(int i = str.length(); i < length; i++)
				str = "0" + str;
			codeWords.add(str);
		}
		return codeWords;
	}
	
	//Constructs a table with key=deletion, value=possible codeword
	public static HashMap<String, ArrayList<String>> tableConstruct(int length) {
		ArrayList<String> posDeletions = binaryStringConstruct(length);
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		for(String del: posDeletions) {
			ArrayList<String> posParents = new ArrayList<String>();
			
			for(int i = 0; i < del.length(); i++) {
				String sideL = del.substring(0, i);
				String sideR = del.substring(i, del.length());
				String parent = sideL + "0"+ sideR;
				if(!posParents.contains(parent))
					posParents.add(parent);
				parent = sideL + "1" + sideR;
				if(!posParents.contains(parent))
					posParents.add(parent);
			}
			//edge cases 
			String parent = del + "0";
			if(!posParents.contains(parent))
				posParents.add(parent);
			parent = del + "1";
			if(!posParents.contains(parent))
				posParents.add(parent);
			map.put(del, posParents);
		}
		return map;
			
		}
	
	public static ArrayList<String> findCodeWords(int length) {
		HashMap<String, ArrayList<String>> table = tableConstruct(length);
		ArrayList<String> posDels = binaryStringConstruct(length);
		ArrayList<String> posDelsTemp = binaryStringConstruct(length);
		ArrayList<String> solution = new ArrayList<String>();
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<String> usedRoots = new ArrayList<String>();
		//System.out.println(table);
		
		
		for(String code:posDels) {
			
			if(table.get(code) != null && table.get(code).size() != 0) {
				for(int g=0; g < table.get(code).size(); g++) {
					temp.add(table.get(code).get(g));
				}
				//System.out.println(table);
				//System.out.println(temp);
				
				
				String word = table.get(code).get(0);
				solution.add(word);
				
			
				for(int j = 0; j < word.length(); j++) {
					String sideL = word.substring(0, j);
					String sideR = word.substring(j+1, word.length());
					String del = sideL + sideR;
					if(!usedRoots.contains(del)) {
						usedRoots.add(del);
					}
				}
			
			}
			
			//System.out.println(usedRoots);
			
			
			for(int i=0; i < usedRoots.size(); i++) {
				if(table.get(usedRoots.get(i)) != null) {
					for(int k=0; k < table.get(usedRoots.get(i)).size(); k++) {
						temp.add(table.get(usedRoots.get(i)).get(k));
					}
				}
				table.remove(usedRoots.get(i));
				posDelsTemp.remove(usedRoots.get(i));
			}
			
			
			for(String del: posDelsTemp) {
				for(int k=0; k < temp.size(); k++) {
					if(table.get(del).contains(temp.get(k))) {
						table.get(del).remove(temp.get(k));
					}
				}	
			}
			
	
		}
			
		//System.out.println(solution);
		//System.out.println(table);
		System.out.println("Number of solutions found: " + solution.size());
		return solution;
	
	}
	
	public static ArrayList<String> duplicationRoot(ArrayList<String> codeWords, int k) {
		ArrayList<String> dupRoot = new ArrayList<String>();
		
		for(String s: codeWords) {
			
			for(int i=0; i < s.length() - 2*k+1; i++) {
				
				String sub = s.substring(i, i + k);
				String subNext = s.substring(i+k, i+k+k);

				//System.out.println(sub);
				//System.out.println(subNext);
				if(sub.equals(subNext)) {
					
					s = s.substring(0, i + k) + s.substring(i+k+k, s.length());
					
				}
			
			}
			
			dupRoot.add(s);
			
		}
		
		return dupRoot;
	}
	
	


	public static void main(String[] args) {
		//System.out.println(binaryStringConstruct(3));
		//System.out.println(tableConstruct(3));
		//System.out.println(tableConstruct(2)); 
		int numBits = 7;
		int k = 3;
		
		HashMap<Integer, Integer> bits = new HashMap<Integer, Integer>();
		bits.put(1, 1);
		bits.put(2, 2);
		bits.put(3, 2);
		bits.put(4, 4);
		bits.put(5, 6);
		bits.put(6, 10);
		bits.put(7, 16);
		bits.put(8, 30);
		bits.put(9, 52);
		bits.put(10, 94);
		bits.put(11, 172);
		bits.put(12, 316);
		bits.put(13, 586);
		
		ArrayList<String> codeWords = findCodeWords(numBits - 1);
		//System.out.println(codeWords);
		//System.out.println("Solving for: " + numBits + " bits");
		System.out.println("Maximal Independent size: " + bits.get(numBits));
	
		
		ArrayList<String> almostIrreducible = duplicationRoot(codeWords, k);
		ArrayList<String> irreducible = duplicationRoot(almostIrreducible, k);
		//System.out.println(duplicationRoot(irreducible, k));
		
		for(int i=0; i < codeWords.size(); i++) {
			System.out.println(codeWords.get(i) + " : " + irreducible.get(i) + "\t size difference = " + (codeWords.get(i).length() - irreducible.get(i).length()));
		}

		
	}

}
