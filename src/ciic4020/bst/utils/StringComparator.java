package ciic4020.bst.utils;

public class StringComparator implements Comparator<String> {

	@Override
	public int compare(String v1, String v2) {
		return v1.compareTo(v2);
	}

}