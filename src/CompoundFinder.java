import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CompoundFinder {
	static TrieNode trie;
	static ArrayList<String> words;
	
	public static void main(String[] args) {
		populateTrie();
		findCompounds();
	}
	
	public static void populateTrie(){
		trie = new TrieNode(null);
		words = new ArrayList<String>();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("/usr/share/dict/words")); // TODO UI 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // TODO make more meaningful
		}
		
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if(line.matches("[a-zA-Z][a-zA-Z]+")){
				if(line.length() < 6) // TODO UI
					trie.insert(line.toLowerCase());
				else if(line.length() == 6) // TODO UI
					words.add(line.toLowerCase());
			}			
		}
		
		System.out.println(words.size());
	}
	
	public static void findCompounds(){
		ArrayList<String> compounds = new ArrayList<String>();
		
		for(String word : words){
			trie.populateCompounds(word, compounds);
			
			if(compounds.size() != 0){
				for(String compound : compounds){
					if(trie.lookup(word.substring(compound.length()))){
						System.out.println(String.format("%s + %s = %s", compound, word.substring(compound.length()), word));
					}
				}
			}
			
			compounds.clear();
		}		
	}
	
	public static void testbed(){
		String test1 = "Ho";
		String test2 = "omework";
		String test3 = "Homework";
		
		trie.insert(test1.toLowerCase());
		trie.insert(test2.toLowerCase());
		trie.insert(test3.toLowerCase());
		
		ArrayList<String> compounds = new ArrayList<String>();
		trie.populateCompounds(test3.toLowerCase(), compounds);
		
		System.out.println(trie.lookup(test3.substring(compounds.get(0).length())));
		
//		System.out.println(trie.lookup("homework"));
//		System.out.println(trie.lookup("house"));
//		System.out.println(trie.lookup("ho"));
//		System.out.println(trie.lookup("mountain"));
//		System.out.println(trie.lookup("mount"));
	}

}
