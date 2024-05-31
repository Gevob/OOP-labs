package it.polito.po.test;

import static it.polito.po.test.OOPAssertions.*;
import static org.junit.Assert.*;
import org.junit.Test;

import hydraulic.*;

public class TestR1_Elements {
	
	@Test
	public void testEmptySystem(){
		HSystem s = new HSystem();
		
		Element[] elements = s.getElements();
		
		assertNotNull("Missing elements",elements);
		
		assertEquals("There should'nt be any elements at first", 0, elements.length);
	}

	
	@Test
	public void testGetElements(){
		HSystem s = new HSystem();
		
		Element el1 = new Source("Test");		
		Element el2 = new Source("Test 1");
		Element el3 = new Source("Test 2");	
		s.addElement(el1);
		s.addElement(el2);
		s.addElement(el3);
		
		Element[] elements = s.getElements();

		assertEquals("There should be three elements", 3, elements.length);

		assertArrayContainsAll("Missing elements",elements,el1,el2);
	}

	@Test
	public void testElementName(){
		Element el = new Source("Test");
		
		String name = el.getName();
		
		assertNotNull("Missing element name", name);
		assertEquals("Wrong name", "Test", name);
	}
}
