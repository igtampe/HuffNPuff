package ciic4020.bst.test;

import ciic4020.bst.LinkedBST;
import ciic4020.bst.utils.BinaryTreePrinter;
import ciic4020.bst.utils.StringComparator;

/**
 * Tester class for the LinkedBST implementation of the BinarySearchTree ADT
 * This classes uses String data; the same string for the key/value pair
 * @author Juan O. Lopez
 */
public class StringTreeTester {

	public static void main(String[] args) { 
		LinkedBST<String, String> t = new LinkedBST<String, String>(new StringComparator()); 
		
		// add nodes and data to the tree
		t.add("Eva", "Eva Luna"); 
		t.add("Maria", "Maria Lozada"); 
		t.add("Juana", "Juana Cubana"); 
		t.add("Lola", "Lola Mento"); 
		t.add("Pepote", "Pepote Guapote"); 
		t.add("Rosa", "Rosa Gonzalez"); 
		t.add("Manolo", "Manolo Cidre"); 
		t.add("Eligio", "Eligio Hernandez"); 
		t.add("Deborah", "Deborah Martorell"); 
		t.add("Andres", "Andres Garcia"); 
		t.add("Pergamino", "Pergamino Antiguo"); 
		t.add("Bienvenido", "Bienvenido Perfecto");
		t.add("Manolin", "Manolin Cito"); 
		t.add("Juan", "Juan Iquillo"); 
		t.add("Mariola", "Mariola Guardiola");
		t.add("Ana", "Ana Conda"); 
		t.add("Elegancia", "Elegancia Fina"); 
		
		BinaryTreePrinter.print(t.root());
		
		/* Test iterator */
		System.out.println("\nElements are:");
		for (String s : t) {
			System.out.println(s);
		}
		
		/* Other tests */
		System.out.println("\nDoes the tree contain Juan? " + t.contains("Juan"));
		System.out.println("Does the tree contain Carlos? " + t.contains("Carlos"));
		System.out.println("Size of the tree is: " + t.size());
		System.out.println("Is the tree empty? " + t.isEmpty());
		System.out.println("Clearing the tree...");
		t.clear();
		System.out.println("Is the tree empty? " + t.isEmpty());
	}
	
}