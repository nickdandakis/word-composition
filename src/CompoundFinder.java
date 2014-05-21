
public class CompoundFinder {
	static TrieNode trie;
	
	public static void main(String[] args) {
		trie = new TrieNode(null);
		
		String test1 = "Homework";
		String test2 = "House";
		String test3 = "Mountain";
		
		trie.insert(test1.toLowerCase());
		trie.insert(test2.toLowerCase());
		trie.insert(test3.toLowerCase());
		
//		System.out.println(trie.lookup("homework"));
//		System.out.println(trie.lookup("house"));
//		System.out.println(trie.lookup("ho"));
//		System.out.println(trie.lookup("mountain"));
//		System.out.println(trie.lookup("mount"));
	}

}
