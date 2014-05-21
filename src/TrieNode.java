import java.util.ArrayList;
import java.util.HashMap;


public class TrieNode {
	private String value;
	private HashMap<Character, TrieNode> children;
	
	public TrieNode(String value) {
		this.value = value;
		this.children = new HashMap<Character, TrieNode>();
	}

	public void addChild(Character key, TrieNode child){
		children.put(key, child);
	}
	
	public void removeChild(TrieNode child){
		children.remove(child.getValue());
	}
	
	public String getValue(){
		return this.value;
	}
	
	public void insert(String word){
		insert(word, word);
	}
	
	public void insert(String word, String scratch){
		if(scratch.length() == 1){
			addChild(scratch.charAt(0), new TrieNode(word));
			return;
		} else if(!children.containsKey(scratch.charAt(0)))
			addChild(scratch.charAt(0), new TrieNode(null));
		
		children.get(scratch.charAt(0)).insert(word, scratch.substring(1));
	}
	
	public boolean lookup(String word){
		return lookup(word, word);
	}
	
	public boolean lookup(String word, String scratch){
		if(scratch.length() == 1)
			if(children.get(scratch.charAt(0)).getValue() != null && children.get(scratch.charAt(0)).getValue().equals(word))
				return true;
			else 
				return false;
		else if(!children.containsKey(scratch.charAt(0)))
			return false;
		else
			return children.get(scratch.charAt(0)).lookup(word, scratch.substring(1));
	}	
}
