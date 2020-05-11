package ciic4020.bst.test;

import ciic4020.bst.LinkedBST;
import ciic4020.bst.utils.BinaryTreePrinter;
import ciic4020.bst.utils.IntegerComparator;

/**
 * Tester class for the LinkedBST implementation of the BinarySearchTree ADT
 * This classes uses Integer data; the same number for the key/value pair
 * @author Juan O. Lopez
 */
public class IntegerTreeTester {

	public static void main(String[] args) { 
		LinkedBST<Integer, Integer> t = new LinkedBST<Integer, Integer>(new IntegerComparator()); 
		
		// add nodes and data to the tree
		t.add(13, 13); // Root
		t.add(16, 16); // These values should be added to the right of the root
		t.add(15, 15);
		t.add(18, 18);
		System.out.println("\nAdded 13, 16, 15, 18\n");
		BinaryTreePrinter.print(t.root());
		
		t.add(7, 7); // These values should be added to the left of the root
		t.add(12, 12);
		t.add(4, 4);
		System.out.println("\nAdded 7, 12, 4\n");
		BinaryTreePrinter.print(t.root());
		
		t.add(3, 3);
		t.add(17, 17);
		t.add(5, 5);
		System.out.println("\nAdded 3, 17, 5\n");
		BinaryTreePrinter.print(t.root());
		
		/* Remove is trickier, so let's test one scenario at a time first */
		t.remove(3); // Remove a leaf
		System.out.println("\nRemoved 3 (a leaf)\n");
		BinaryTreePrinter.print(t.root());

		t.remove(4); // Only has a right child
		System.out.println("\nRemoved 4 (has a right child)\n");
		BinaryTreePrinter.print(t.root());
		t.remove(18); // Only has a left child
		System.out.println("\nRemoved 18 (has a left child)\n");
		BinaryTreePrinter.print(t.root());
		
		/* Now the fun part, removing nodes with 2 children. Keep in mind that the
		 * node with the value isn't actually removed; the predecessor/successor
		 * is removed instead.  So, to be thorough, we'll test the different
		 * scenarios for the predecessor/successor (leaf, 1 child).
		 * To do that, add some more values first. */
		t.add(14, 14);
		t.add(6, 6);
		System.out.println("\nAdded 14 and 6\n");
		BinaryTreePrinter.print(t.root());
		
		t.remove(16); // Its predecessor is 15, which has 14 as a left child
		System.out.println("\nRemoved 16 (predecessor has 1 child)\n");
		BinaryTreePrinter.print(t.root());
		t.remove(7); // Its predecessor is 6, which is a leaf
		System.out.println("\nRemoved 7 (predecessor is a leaf)\n");
		BinaryTreePrinter.print(t.root());
		
		/* Finally, test removing the root */
		t.remove(13);
		System.out.println("\nRemoved 13 (the root)\n");
		BinaryTreePrinter.print(t.root());
		
		/* Add a couple more values, just in case removing the root broke something */
		t.add(1, 1);
		t.add(2, 2);
		t.add(16, 16);
		System.out.println("\nAdded 1, 2, 16\n");
		BinaryTreePrinter.print(t.root());
		
		/* Test iterator */
		System.out.println("\nFinal values are:");
		for (Integer i : t)
			System.out.print(i + " ");
		System.out.print("\n");
		
		System.out.println("\nDONE!");
	}
	
}