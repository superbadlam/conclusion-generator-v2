package ru.skkdc.lis.utils.expressionevaluators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.labinfo.Contingent;
import ru.skkdc.lis.conclusion.labinfo.Sex;
import ru.skkdc.lis.conclusion.labinfo.impl.ContingentImpl;

class BooleanExpressionEvaluatorTest {

	@Test
	void test01() {
		String expr="text.equals(\"some-textual-content\")";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void test02() {
		String expr="\"some-textual-content\".equals(text)";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testWithNoLogicalExpression02() {
		String expr="text";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertThrows(ExpressionEvaluatorException.class, ()->BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testWithNoLogicalExpression01() {
		String expr="text+1/123";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertThrows(ExpressionEvaluatorException.class, ()->BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareStringLikeStringAndInt() {
		String expr="text==1";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertThrows(ExpressionEvaluatorException.class, ()->BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareStringLikeStringAndString() {
		String expr="text==\"some-textual-content\"";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareStringLikeIntAndInt() {
		String expr="1==1";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testEvaluateTrue() {
		String expr="true";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("text", new ParamString("some-textual-content"));
		
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareIntegerLikeIntAndInt01() {
		String expr="var01==var02";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("var01", new ParamInteger(1));
		params.put("var02", new ParamInteger(2));
		
		assertFalse(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareIntegerLikeIntAndInt02() {
		String expr="var01.equals(var02)";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("var01", new ParamInteger(1));
		params.put("var02", new ParamInteger(2));
		
		assertFalse(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareSex01() {
		String expr="Sex.MALE.equals(sex)";
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("sex", new ParamSex(Sex.MALE));
		
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareSex02() {
		String expr="Sex.MALE==sex";
		
		Map<String, ParamValue<?>> params=new HashMap<>();
		params.put("sex", new ParamSex(Sex.MALE));
		
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
	@Test
	void testCompareContingent() {
		
		Contingent contingent01=new ContingentImpl("contingent-name", 1);
		Contingent contingent02=new ContingentImpl("contingent-name", 1);
		Contingent contingent03=new ContingentImpl("contingent-name", 3);
		Contingent contingent04=new ContingentImpl("contingent-name-04", 1);
		
		String expr="myContingent01.equals(myContingent02)";
		
		Map<String, ParamValue<?>> params=new HashMap<>();		
		params.put("myContingent01", new ParamContingent(contingent01));
		params.put("myContingent02", new ParamContingent(contingent01));
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
		
		params=new HashMap<>();		
		params.put("myContingent01", new ParamContingent(contingent01));
		params.put("myContingent02", new ParamContingent(contingent02));
		assertTrue(BooleanExpressionEvaluator.checkCondition(expr, params));
		
		params=new HashMap<>();		
		params.put("myContingent01", new ParamContingent(contingent01));
		params.put("myContingent02", new ParamContingent(contingent03));
		assertFalse(BooleanExpressionEvaluator.checkCondition(expr, params));
		
		params=new HashMap<>();		
		params.put("myContingent01", new ParamContingent(contingent01));
		params.put("myContingent02", new ParamContingent(contingent04));
		assertFalse(BooleanExpressionEvaluator.checkCondition(expr, params));
	}
	
}
