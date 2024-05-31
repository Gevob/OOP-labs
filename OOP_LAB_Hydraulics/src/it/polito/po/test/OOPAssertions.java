package it.polito.po.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Assertions specific for the lab assignment testing layout generation.
 * 
 * This is a static class not meant to be instantiated, but just imported using
 * 
 * {@code import static it.polito.po.test.OOPAssertions.*;}
 * 
 * @author Marco Torchiano <marco.torchiano@polito.it>
 *
 */
public class OOPAssertions {

	protected OOPAssertions() { // this is a static class not meant to be instantiated!	
	}
	
	/**
	 * Asserts that an array contains all the given elements
	 * 
	 * @param msg the identifying message
	 * @param ary the array to be checked
	 * @param elements the elements to be matched
	 */
	public static void assertArrayContainsAll(String msg, Object ary[], Object... elements) {
		if(ary==null) fail(msg + " null array.");
		for(Object e : elements) {
			boolean found = false;
			for(Object x : ary) {
				if(e.equals(x)) {
					found = true;
					break;
				}
			}
			if(!found) fail(msg + " element " + e + " not found in array.");
		}
	}

	/**
	 * Asserts that the names appear in the given order inside the main string
	 * 
	 * @param string
	 * @param names
	 */
	public static void assertInOrder(String string,String... names) {
		string = string.replaceAll("[ \\t]+(?=(\n|\r|$))", "")  // extra spaces at end of line
			 .replaceAll("\\[\\s+|\\s+\\]", "");	  // extra spaces inside [..]
		int[] pos = new int[names.length];
		int prev = -1;
		for(int i=0; i<names.length; ++i) {
			String n = names[i].trim();
			pos[i] = string.indexOf("[" + n + "]");
			
			assertTrue("Missing [" + n + "] in layout", pos[i] >= 0);
			
			if(i>0) {
				assertTrue("Element [" + n + "] should follow element [" + names[i-1] + "]", pos[i] > prev);
			}
		}
	}
	
	/**
	 * Asserts that, inside a main string, the indentation level of the given names is the same
	 * 
	 * @param l the main string
	 * @param names the names whose indentation is to be checked
	 */
	public static void assertSameIndent(String l,String... names) {
		l = l.replaceAll("[ \\t]+(?=(\n|\r|$))", "")  // extra spaces at end of line
				 .replaceAll("\\[\\s+|\\s+\\]", "");	  // extra spaces inside [..]

		int[] starts = new int[l.replaceAll("[^\n]+", "").length()+1];
		starts[0] = 0;
		for(int i=1; i<starts.length; ++i) {
			starts[i] = l.indexOf("\n",starts[i-1])+1;
		}
		int[] indent = new int[names.length];
		int prev = -1;
		for(int i=0; i<names.length; ++i) {
			String n = names[i].trim();
			int pos = l.indexOf("[" + n + "]");

			assertTrue("Missing [" + n + "] in layout", pos >= 0);
			indent[i] = pos - maxLe(starts,pos);
			if(prev>=0) {
				assertTrue("Different indentation levels for [" + names[i-1] + "](" + indent[i-1] + ") and [" +
						 n + "](" + indent[i] + ")", indent[i] == prev);
			}
			prev = indent[i];
		}
	}
	
	private static int maxLe(int[] values, int upperBound) {
		int res=Integer.MIN_VALUE;
		for(int i=0; i<values.length; ++i) {
			if(values[i]<=upperBound && values[i]>res) {
				res = values[i];
			}
		}
		return res;
	}

}
