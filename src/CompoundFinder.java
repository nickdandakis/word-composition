import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CompoundFinder {
	static TrieNode trie; 				// the glorious trie
	static ArrayList<String> words; 	// the words to find compounds for
	static int wordLength;				// the specific word length query words need to be
	static File dictionary;				// the dictionary file
	
	public static void main(String[] args) {
		userInput();
		populateTrie();
		findCompounds();
	}
	
	public static void userInput(){
		Scanner systemIn = new Scanner(System.in);
		
		System.out.println("## Mint Digital coding test - Nick Dandakis ##");
		System.out.println("Processes dictionary, looking for words that are composed of two concatenated smaller words.\n");
		
		// Grab word length and do some validation for minimum word length
		System.out.print("Word length: ");
		wordLength = Integer.valueOf(systemIn.next());
		while(wordLength < 4){
			System.out.println("Error: Minimum word length is 4!");
			System.out.print("Word length: ");
			wordLength = Integer.valueOf(systemIn.next());
		}		
		
		// Grab dictionary location and do some validation for existing file
		// I could've added a default location of /usr/share/dict/words, but I'll leave that as a TODO
		String dictionaryLocation;
		System.out.print("Dictionary location: ");
		dictionaryLocation = systemIn.next();
		dictionary = new File(dictionaryLocation);
		while(!dictionary.exists() || !dictionary.isFile()){
			System.out.println("Error: File does not exist or is not a file.");
			System.out.print("Dictionary location: ");
			dictionaryLocation = systemIn.next();			
			dictionary = new File(dictionaryLocation);
		}
		
		systemIn.close();
	}
	
	public static void populateTrie(){
		System.out.println("\nPopulating trie...");
		trie = new TrieNode(null);
		words = new ArrayList<String>();
		Scanner dictionaryScanner = null;
		try {
			dictionaryScanner = new Scanner(dictionary); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Scan all lines in dictionary file,
		// insert all words that are over two characters long and less than specified word length,
		// also add all words that are exactly specified word length long into ArrayList for processing
		while (dictionaryScanner.hasNextLine()) {
			String line = dictionaryScanner.nextLine();
			if(line.matches("[a-zA-Z][a-zA-Z]+")){
				if(line.length() < wordLength) 
					trie.insert(line.toLowerCase());
				else if(line.length() == wordLength)
					words.add(line.toLowerCase());
			}			
		}
		
		System.out.println("Trie populated!");
		dictionaryScanner.close();
	}
	
	public static void findCompounds(){
		System.out.println("\nProcessing...");
		ArrayList<String> compounds = new ArrayList<String>();
		int resultsCounter = 0;
		
		// For each word that is of specified word length,
		// find all words that can be used as the first compound,
		// then for each first compound word found, check if the rest of the word exists in dictionary
		for(String word : words){
			trie.populateFirstCompounds(word, compounds);
			if(compounds.size() != 0){
				for(String compound : compounds){
					if(trie.lookup(word.substring(compound.length()))){
						System.out.println(String.format("%s + %s = %s", compound, word.substring(compound.length()), word));
						resultsCounter++;
					}
				}
			}			
			compounds.clear();
		}		
		
		System.out.println(String.format("\nProcessing complete! Found %d compound words.", resultsCounter));
	}

}
