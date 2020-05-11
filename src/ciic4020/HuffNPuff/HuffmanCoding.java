package ciic4020.HuffNPuff;

import IO.TextFileManager;
import ciic4020.bst.BTNode;
import ciic4020.hashtable.HashTableSC;
import ciic4020.hashtable.SimpleHashFunction;
import ciic4020.map.Map;
import ciic4020.sortedlist.SortedArrayList;
import ciic4020.sortedlist.SortedList;

public class HuffmanCoding {

	public static void main(String[] args) {
		
		//Hello yes, it is time to Huff, Puff, and *blow* away the unnecessary data.
		
		String TheHayHouse=LoadData("inputData/stringData.txt");
		Map<String,Integer> EachHayBale = compute_fd(TheHayHouse);
		process_results(EachHayBale, TheHayHouse, Encode(huffman_code(huffman_tree(EachHayBale)), TheHayHouse));
		
	}
	

	/**
	 * Uses TextFileManager to read and return the first line of the specified file
	 * @param filename The file you need the first line of
	 * @return The first line of the file you specified, or "" if an IOerror occurred, or if the file was not found.
	 */
	public static String LoadData(String filename) {
		try {return new TextFileManager(filename).toString();} 
		catch (Exception e) {return "";}
	}
	
	/**
	 * Comptues the Frequency Distribution of the characters in the string. Uses a strategy from FreqFinder, a previous project.
	 * @param OriginalData The string you want to compute the frequency distribution of
	 * @return A map, where the key is the character, and the data is its frequenecy.
	 */
	public static Map<String, Integer> compute_fd(String OriginalData){
		//Gee, it sure is boring around here...
	    //My boy, this is what all true heroes strive for.
		//I just wonder what Ganon's up to.
		
		//HERE IS THE MAP. WHERE DO YOU WISH TO GO? 
		Map<String, Integer> TheMap = new HashTableSC<String, Integer>(4, new SimpleHashFunction<String>());
		
		//THESE ARE THE FACES, OF EVIL!
		for (Character Face : OriginalData.toCharArray()) {
			
			//we already have it.
			if(TheMap.containsKey(Face.toString())) {TheMap.put(Face.toString(), TheMap.get(Face.toString()) + 1);}
			
			//We don't have it
			else {TheMap.put(Face.toString(),1);}
			
			//Real talk I love how flexible Java is with comments that I can put a comment between the if and else.
			//I hope this doesn't break convention or something.
			
		}
		
		//now lets copy it to the arraylist, and SQUADALA! WE'RE OFF!
		return TheMap;
	}
	
	/***
	 * Creates a Huffman Code Tree using the specified data.
	 * @param CharFreq A map of character frequency, of types <Character,Frequency>
	 * @return The root node of the tree with Frequency as its key, and the character as its value.
	 */
	public static BTNode<Integer, String> huffman_tree(Map<String,Integer> CharFreq) {
		int size = CharFreq.size();
		
		//Create sorted list of nodes
		SortedList<BTNode<Integer,String>> SL = FreqToSortedList(CharFreq);
		
		//now let's build the tree.
		for (int i = 0; i < size-1; i++) {
			BTNode<Integer,String> Node = new BTNode<Integer,String>(); //get us a new node.
			BTNode<Integer,String> LeftNode = SL.removeIndex(0); //get index 0 which *should* be the smallest one.
			BTNode<Integer,String> RightNode = SL.removeIndex(0); //Get index 0 again, which also shoudl be the smallest one.		
			
			Node.setLeftChild(LeftNode); //Set Left Node
			Node.setRightChild(RightNode); //Set Right Node
			
			Node.setKey(LeftNode.getKey()+RightNode.getKey()); //Set key
			Node.setValue(LeftNode.getValue()+RightNode.getValue()); //Set value
			
			SL.add(Node); //Re-add the node.
		}
		
		//Once we're done with this, there should only be one node left.
		return SL.removeIndex(0);
		
	}
			
	/**
	 * Takes a frequency distribution, and returns a sorted list of BTNodes, sorted by frequency.
	 * @param OrigFreq Original frequency map where characters are mapped to their frequency
	 * @return Sorted list of BTNodes, where their key is their frequency, and their value is the character.
	 */
	private static SortedList<BTNode<Integer,String>> FreqToSortedList(Map<String,Integer> OrigFreq){
		SortedList<BTNode<Integer,String>> TheList = new SortedArrayList<BTNode<Integer,String>>(OrigFreq.size());
		
		//go through the maps, and add each key's frequency (by using GET) and the key itself 
		for (String Key : OrigFreq.getKeys()) {TheList.add(new BTNode<Integer, String>(OrigFreq.get(Key), Key));}
				
		return TheList;
	}
	
	/***
	 * Uses a Huffman Tree to generate a map with all the characters mapped to their huffman code
	 * @param Root Root node of the Huffman Tree
	 * @return A map with all of the Characters in the tree mapped to their code
	 */
	public static Map<String,String> huffman_code(BTNode<Integer, String> Root){
		HashTableSC<String, String> TheMapToTheHayHouse = new HashTableSC<String, String>(5, new SimpleHashFunction<String>());
		AddToHuffmanCode("", TheMapToTheHayHouse, Root);
		return TheMapToTheHayHouse;
	}
	
	/***
	 * Recursive helper method to browse through the tree and add it to the key
	 * @param Prefix Continuously updated prefix to determine the string of the huffman code (IE, "" when only one layer deep, "0" when a second layer deep.)
	 * @param Code Code map that will eventually have all the characters mapped to a huffman code
	 * @param Root Root BTNode to start from.
	 */
	private static void AddToHuffmanCode(String Prefix,Map<String,String> Code, BTNode<Integer,String> Root) {
		
		//Add the right child
		Code.put(Root.getRightChild().getValue(), Prefix + "1");
		
		//Now let's check if the next left one is the last one.
		if(Root.getLeftChild().getLeftChild()==null) {
			//the left one is the last one. Add it.
			Code.put(Root.getLeftChild().getValue(), Prefix + "0");
		}
		else {
			//The left one isn't the last one. Get on with the next one.
			AddToHuffmanCode(Prefix+"0", Code, Root.getLeftChild());			
		}
			
		return;
		
	}
	
	/***
	 * Takes a Huffman code, and an input string, and returns an huffman-coded text.
	 * @param Code The code, where each character is mapped to its respective Huffman code.
	 * @param Input Input string that will be encoded
	 * @return The text, but encoded.
	 */
	public static String Encode(Map<String,String> Code, String Input) {
		
		String JustThePiggies ="";
		
		//Here's where the big bad wolf (our code) blows away all the unneccessary data, leaving just the piggies within the house.
		for (Character HayBale : Input.toCharArray()) {	JustThePiggies += Code.get(HayBale.toString());}
		
		return JustThePiggies;
	}

	/**
	 * Displays the results of the Huffman Coding procedure, including the frequency distribution (in non-ascending order)<br>
	 * The input text, the output (encoded) text, and how much smaller the output would be to store (assuming 1 byte per <br>
	 * character in the output, and 8 bytes per character (as per ASCII) in the input).
	 *  
	 * @param FD Frequency distribution map, where each character is mapped to its frequency
	 * @param Input Input text
	 * @param Output Output (encoded) text.
	 */
	public static void process_results(Map<String,Integer> FD, String Input, String Output) {
		
		//Get frequencies sorted
		SortedList<BTNode<Integer,String>> TheList = FreqToSortedList(FD);
		
		//Table Header
		System.out.println("C|Freq");
		
		//Display these cositas in reverse order.
		for (int i = TheList.size()-1; i > 0; i--) {System.out.println(TheList.get(i).getValue() + "|" + TheList.get(i).getKey());}
		
		//Display Original text
		System.out.println(Input);
		
		//Display the encoded text
		System.out.println(Output);

		//Display the compression percentage.
		
		//The calculation is the Output's Length (since each character is one byte) compared to ASCII's 8 bytes per character.
		System.out.println(Output.length()/(Input.length()*8.0)); 
		
		//And we're done/
	}
	
}
