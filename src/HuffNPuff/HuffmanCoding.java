package HuffNPuff;

import java.text.DecimalFormat;

import IO.TextFileManager;
import ciic4020.bst.BTNode;
import ciic4020.hashtable.HashTableSC;
import ciic4020.hashtable.SimpleHashFunction;
import ciic4020.map.Map;
import ciic4020.sortedlist.SortedArrayList;
import ciic4020.sortedlist.SortedList;

/**
 * HuffmanCoding contains all the necessary methods to encode text using the Huffman Code algorithm 
 * @author igtampe
 *
 */
public class HuffmanCoding {

	/**
	 * Takes the data stored in InputData/StringData.txt and encodes it using Huffman Coding. Then displays relevant data, including:
	 * <ul>
	 * <li>A table displaying Character, Frequency, and Huffman Code</li>
	 * <li>The original text</li>
	 * <li>The Encoded text</li>
	 * <li>The percent of space saved by storing the text as a Huffman-Coded string, assuming 1bit per character in the output, and 8 bits per character in the input.</li>
	 * </ul>
	 * @param args The arguements do nothing.
	 */
	public static void main(String[] args) {
		
		//Hello yes, it is time to Huff, Puff, and *blow* away the unnecessary data.
			
		String TheHayHouse=LoadData("inputData/stringData.txt"); //The hayhouse includes the piggies, the only important data
		Map<Character,Integer> EachHayBale = compute_fd(TheHayHouse); //Each Hay Bale represents the frequency of each type of block that contains the important data
		Map<Character,String> TheBigBadWolf = huffman_code(huffman_tree(EachHayBale)); //The Big Bad Wolf represents the huffman code we'll need to blow away the haybales, leaving only the important data.
		String ThePiggies = Encode(TheBigBadWolf, TheHayHouse); //Encode blows away the extra data, leaving only the piggies (The encoded data).
		process_results(EachHayBale, TheBigBadWolf,TheHayHouse, ThePiggies);
		
		//There's only one piggy in the hay house, but just ignore that for me, please.
	}
	

	/**
	 * Uses TextFileManager to read and return the first line of the specified file.
	 * @param filename The file you need the first line of
	 * @return The first line of the file you specified, or "" if an IOerror occurred, or if the file was not found.
	 */
	public static String LoadData(String filename) {
		try {return new TextFileManager(filename).toString();} 
		catch (Exception e) {return "";}
	}
	
	/**
	 * Comptues the Frequency Distribution of the characters in the string. Uses the MapFD strategy from FreqFinder, though it has been modified to use HashtableSC, instead of the default Java Hashtable.
	 * @param OriginalData The string you want to compute the frequency distribution of
	 * @return A map, where the key is the character, and the data is its frequenecy.
	 */
	public static Map<Character, Integer> compute_fd(String OriginalData){
		//Gee, it sure is boring around here...
	    //My boy, this is what all true heroes strive for.
		//I just wonder what Ganon's up to.
		
		//HERE IS THE MAP. WHERE DO YOU WISH TO GO? 
		Map<Character, Integer> TheMap = new HashTableSC<Character, Integer>(4, new SimpleHashFunction<Character>());
		
		//THESE ARE THE FACES, OF EVIL!
		for (Character Face : OriginalData.toCharArray()) {
			
			//If we already have it.
			if(TheMap.containsKey(Face)) {TheMap.put(Face, TheMap.get(Face) + 1);}
			
			//If we don't have it
			else {TheMap.put(Face,1);}			
		}
		
		//now lets copy it to the arraylist, and SQUADALA! WE'RE OFF!
		return TheMap;
	}
	
	/***
	 * Creates a Huffman Code Tree using the specified character frequency map. The process is as follows: <br>
	 * 
	 * <ol>
	 * <li>Take the frequency data and make a sortedlist (Stored by frequency), where each entry is a node that holds its frequency, and it's character (as a string)</li>
	 * <li>Find the 2 least frequent symbols</li>
	 * <li>Create a new node, where the left is the least, and the right is the second least item we previously found. The key of this new node is the sum of the two frequencies, and its value is the two strings put together</li>
	 * <li>Repeat until there is only one symbole left, which will be the root of the tree.</li>
	 * </ol>
	 * @param CharFreq A map of character frequency, where each character is mapped to its frequency (as an integer)
	 * @return The root node of the tree with Frequency as its key, and the character as its value.
	 */
	public static BTNode<Integer, String> huffman_tree(Map<Character,Integer> CharFreq) {
	
		//Create sorted list of nodes
		SortedList<BTNode<Integer,String>> SL = FreqToSortedList(CharFreq);
		
		//now let's build the tree.
		for (int i = 0; i < CharFreq.size()-1; i++) {
			BTNode<Integer,String> Node = new BTNode<Integer,String>(); //get us a new node.
			BTNode<Integer,String> LeftNode = SL.removeIndex(0); //get index 0 which *should* be the smallest one.
			BTNode<Integer,String> RightNode = SL.removeIndex(0); //Get index 0 again, which also shoudl be the smallest one.		
			
			//Tie Breaker
			if((LeftNode.getKey().equals(RightNode.getKey()))&&(LeftNode.getValue().compareTo(RightNode.getValue())>0)) {
				
				//Set the smaller node on the left, which in this case is the node that was *supposed* to be the right
				Node.setLeftChild(RightNode); 
				Node.setRightChild(LeftNode);						
				
			}else {
				Node.setLeftChild(LeftNode); //Set Left Node
				Node.setRightChild(RightNode); //Set Right Node				
			}						
			
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
	private static SortedList<BTNode<Integer,String>> FreqToSortedList(Map<Character,Integer> OrigFreq){
		SortedList<BTNode<Integer,String>> TheList = new SortedArrayList<BTNode<Integer,String>>(OrigFreq.size());
		
		//go through the maps, and add each key's frequency (by using GET) and the key itself 
		for (Character Key : OrigFreq.getKeys()) {TheList.add(new BTNode<Integer, String>(OrigFreq.get(Key), Key.toString()));}
		return TheList;
		
	}
	
	/***
	 * Uses a Huffman Tree to generate a map with all the characters mapped to their huffman code. This is accomplished recursively using a private, helper function.
	 * It goes through the tree in in-order, by checking that the root isn't null, then checking the left node, and then checking the right node. <br><br>
	 * 
	 * The method takes with it a map which holds the codes, and a prefix, which depends on the direction it traveled to reach the node. 0 for left, and 1 for right.
	 * When a node is checked, if the string it holds is of length one, it assumes its an end node, and adds it as a huffman code to the map using the prefix, and one more bit to specify the direction it found it.
	 * Otherwise, it'll recursively call itself to check the left and right child nodes of the node it found.
	 * <br>
	 *   
	 * @param Root Root node of the Huffman Tree
	 * @return A map with all of the Characters in the tree mapped to their code
	 */
	public static Map<Character,String> huffman_code(BTNode<Integer, String> Root){
		HashTableSC<Character, String> TheMapToTheHayHouse = new HashTableSC<Character, String>(4, new SimpleHashFunction<Character>());
		AddToHuffmanCode("", TheMapToTheHayHouse, Root);
		return TheMapToTheHayHouse;
	}
	
	/***
	 * Recursive helper method to browse through the tree and add it to the key
	 * @param Prefix Continuously updated prefix to determine the string of the huffman code (IE, "" when only one layer deep, "0" when a second layer deep.)
	 * @param Code Code map that will eventually have all the characters mapped to a huffman code
	 * @param Root Root BTNode to start from.
	 */
	private static void AddToHuffmanCode(String Prefix,Map<Character,String> Code, BTNode<Integer,String> Root) {
		
		//If it's nothing, return
		if (Root==null) {return;}
		
		//if there is a left child...
		if ((Root.getLeftChild()!=null)) {
		
			//And it's value is only one string long (osea it's an end node), add it to the code.
			if((Root.getLeftChild().getValue().length()==1)) {Code.put(Root.getLeftChild().getValue().charAt(0), Prefix + "0");}
			
			//Otherwise, it's not and end node. Find the end nodes.
			else {AddToHuffmanCode(Prefix+"0", Code, Root.getLeftChild());}
			
		}
		
		
		//If there is a right child...
		if ((Root.getRightChild()!=null)) {
			
			//And it's value is only one string long (osea it's an end node), add it to the code.
			if((Root.getRightChild().getValue().length()==1)) {Code.put(Root.getRightChild().getValue().charAt(0), Prefix + "1");}
			
			//Otherwise, it's not and end node. Find the end nodes.
			else {AddToHuffmanCode(Prefix+"1", Code, Root.getRightChild());}
			
		}
				
	}
	
	/***
	 * Takes a Huffman code, and an input string, and returns an huffman-coded text.<br> 
	 * It goes through each character in the string, and builds a return string basing itself on whatever code is linked to the character in the provided code map.
	 * @param Code The code, where each character is mapped to its respective Huffman code.
	 * @param Input Input string that will be encoded
	 * @return The text, but encoded.
	 */
	public static String Encode(Map<Character,String> Code, String Input) {
		
		String JustThePiggies ="";
		
		//Here's where the big bad wolf (our code) blows away all the unneccessary data, leaving just the piggies within the house.
		for (Character HayBale : Input.toCharArray()) {	JustThePiggies += Code.get(HayBale);}
		
		return JustThePiggies;
	}

	/**
	 * Displays the results of the Huffman Coding procedure, including the frequency distribution (in non-ascending order)
	 * The code linked to each character, The input text, the output (encoded) text, and the amount of space saved by encoding it (assuming 1 bit per
	 * character in the output, and 8 bits per character (as per ASCII) in the input).
	 *  
	 * @param FD Frequency distribution map, where each character is mapped to its frequency
	 * @param Code The Huffman Code used to encode the text.
	 * @param Input Input text
	 * @param Output Output (encoded) text.
	 */
	public static void process_results(Map<Character,Integer> FD, Map<Character,String> Code, String Input, String Output) {
		
		//Get frequencies sorted
		SortedList<BTNode<Integer,String>> TheList = FreqToSortedList(FD);
		
		//Table Header
		System.out.println("Symbol\tFrequency\tCode");
		System.out.println("------\t---------\t----");
		
		//Display these cositas in reverse order.
		for (int i = TheList.size()-1; i > -1; i--) {System.out.println(TheList.get(i).getValue() + "\t" + TheList.get(i).getKey()+"\t\t" + Code.get(TheList.get(i).getValue().charAt(0)));}
		
		//Display Original text
		System.out.println();
		System.out.println("Original string:");
		System.out.println(Input);
		
		//Display the encoded text
		System.out.println("Encoded string:");
		System.out.println(Output);

		//Display the compression percentage.
		System.out.println();
		System.out.println("The original string requires "+Input.length()+" bytes.");

		//Make sure we format the bytes correctly
		int OutputBytes=(int) Math.ceil(Output.length()/8.0);
		System.out.println("The encoded string requires "+OutputBytes+" bytes.");
		
		//Prepare the percentage display
		String PercentageDisplay = new DecimalFormat("##.##").format((1-(Math.ceil(Output.length()/8.0)/(Input.length()+0.0)))*100);
		
		//And display the percentage
		System.out.println("Difference in space required is " + PercentageDisplay + "%.");
		
		//And we're done/
	}
	
}
