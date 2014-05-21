import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Nick Dandakis
 *
 * Implements the functionality of a trie node.
 */
public class TrieNode {
	private String word;
	private HashMap<Character, TrieNode> children;
	
	/**
	 * Constructor of TrieNode object.
	 * 
	 * @param word the word string that this node holds, can be null
	 */
	public TrieNode(String word) {
		this.word = word;
		this.children = new HashMap<Character, TrieNode>();
	}

	/**
	 * Adds child TrieNode. 
	 * 
	 * @param key the key character
	 * @param child the child TrieNode object
	 */
	public void addChild(Character key, TrieNode child){
		children.put(key, child);
	}
	
	/**
	 * Removed child TrieNode.
	 * 
	 * @param key the key of TrieNode to remove
	 */
	public void removeChild(Character key){
		children.remove(key);
	}
	
	/**
	 * @return the value (word string) of this node
	 */
	public String getWord(){
		return this.word;
	}
	
	/**
	 * Inserts word string into trie data structure.
	 * 
	 * @param word string to be inserted into trie
	 */
	public void insert(String word){
		insert(word, word);
	}
	
	/**
	 * Inserts word string into trie data structure.
	 * 
	 * @param word string to be inserted into trie, immutable 
	 * @param scratch initially set to word and is recursively updated (pruned) while traversing (and updating) the trie
	 */
	public void insert(String word, String scratch){
		if(scratch.length() == 1){
			addChild(scratch.charAt(0), new TrieNode(word));
			return;
		} else if(!children.containsKey(scratch.charAt(0)))
			addChild(scratch.charAt(0), new TrieNode(null));
		
		children.get(scratch.charAt(0)).insert(word, scratch.substring(1));
	}
	
	/**
	 * Looks for word string in trie data structure.
	 * 
	 * @param word string to look for in trie data structure
	 * @return true if word found in trie, false otherwise
	 */
	public boolean lookup(String word){
		return lookup(word, word);
	}
	
	/**
	 * Looks for word string in trie data structure.
	 * 
	 * @param word string to look for in trie data structure, immutable
	 * @param scratch initially set to word and is recursively updated (pruned) while traversing the trie
	 * @return true if word found in trie, false otherwise
	 */
	public boolean lookup(String word, String scratch){
		if(scratch.length() == 1)
			if(children.containsKey(scratch.charAt(0))){
				if(children.get(scratch.charAt(0)).getWord() != null && children.get(scratch.charAt(0)).getWord().equals(word))
					return true;
				else 
					return false;
			} else
				return false;			
		else if(!children.containsKey(scratch.charAt(0)))
			return false;
		else
			return children.get(scratch.charAt(0)).lookup(word, scratch.substring(1));
	}	
	
	/**
	 * Finds all words that can be used as first compounds for word parameter.
	 * 
	 * @param word string find all first compounds for
	 * @param compounds the compounds found, clear after use
	 */
	public void populateFirstCompounds(String word, ArrayList<String> compounds){
		if(word.length() == 0) 
			return;
		else{ 
			if(children.containsKey(word.charAt(0))){
				if(children.get(word.charAt(0)).getWord() != null)
					compounds.add(children.get(word.charAt(0)).getWord());
				children.get(word.charAt(0)).populateFirstCompounds(word.substring(1), compounds);
			} else
				return;
		}
	}
	
}
