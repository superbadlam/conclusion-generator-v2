package ru.skkdc.lis.utils.expressionevaluators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class ParamStringOrDoubleValueTest {

	@Test
	void testWhenValueIsNullAndSupplierIsNull() {
		ParamStringOrDoubleValue p=new ParamStringOrDoubleValue(null);
		assertEquals("", p.getText());
		assertEquals(String.class, p.getType());
		assertNull(p.getValue());
	}

	@Test
	void testWhenValueIsText() {
		ParamStringOrDoubleValue p=new ParamStringOrDoubleValue("qwerty");
		assertEquals("qwerty", p.getText());
		assertEquals(String.class, p.getType());
		assertTrue(p.getValue().equals("qwerty"));
	}

	@Test
	void testWhenValueIsDouble() {
		ParamStringOrDoubleValue p=new ParamStringOrDoubleValue("1000.11");
		assertEquals("1000.11", p.getText());
		assertEquals(Double.class, p.getType());
		assertTrue(p.getValue().equals(1000.11));
	}
	
	@Test
	void testWhenValueIsInteger() {
		ParamStringOrDoubleValue p=new ParamStringOrDoubleValue("1000");
		assertEquals("1000.0", p.getText());
		assertEquals(Double.class, p.getType());
		assertTrue(p.getValue().equals(1000.0));
	}
	
	@Test
	void testWithSupplier(){
		String sInt="1000";
		ParamStringOrDoubleValue p=new ParamStringOrDoubleValue(sInt,()->sInt);
		assertEquals("1000", p.getText());
		assertEquals(Double.class, p.getType());
		assertTrue(p.getValue().equals(1000.0));
	}
}
