package ru.skkdc.lis.conclusion.template.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.codehaus.janino.Java.AmbiguousName;
import org.codehaus.janino.Java.Rvalue;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.ScriptEvaluator;
import org.codehaus.janino.util.AbstractTraverser;
import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.labinfo.LaboratoryInfo;

class JaninoTest {

	@Test
	void testNotEnoughParamForEvaluateExpression() {
		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(new String[] { "a"}, new Class[] { int.class });
		ee.setExpressionType(int.class);


		assertThrows(CompileException.class, () -> {
			ee.cook("a * d ");
			ee.evaluate(new Object[] { 19, 23 });
		});
	}
	
	@Test
	void testTooMuchParamsForEvaluateExpression() {
		
		String strExpr="(((a +  2) * testName_val) * 6) - c";
		String[] paramNames=new String[] { "a", "b", "testName_val", "c"};
		Class<?>[] paramTypes=new Class[] { int.class, int.class, int.class, int.class };
		
		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(paramNames, paramTypes);
		ee.setExpressionType(int.class);
		
		try {
			Scanner scanner=new Scanner(null, new ByteArrayInputStream(strExpr.getBytes()));
			Parser parser = new Parser(scanner);
	        Rvalue rvalue = parser.parseExpression().toRvalueOrCompileException();

	        final Set<String> setParameterNames = new HashSet<>();
	        new AbstractTraverser<RuntimeException>() {
	            @Override public void
	            traverseAmbiguousName(AmbiguousName an) {
	                setParameterNames.add(an.identifiers[0]);
	            }
	        }.visitAtom(rvalue);
	        
	        
	        assertEquals(3, setParameterNames.size());
	        assertTrue(setParameterNames.contains("a"));
	        assertTrue(setParameterNames.contains("c"));
	        assertTrue(setParameterNames.contains("testName_val"));
	        assertFalse(setParameterNames.contains("b"));
	        
			ee.cook(strExpr);
			Integer result = (Integer) ee.evaluate(new Object[] { 0, 23, 1, 1 });
			assertEquals(11, result.intValue());
	        
		} catch (IOException | CompileException | InvocationTargetException e) {
			fail(e);
		}

	}

	@Test
	void testWrongParamsForEvaluateExpression() {
		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(new String[] { "a", "c" }, new Class[] { int.class, int.class });
		ee.setExpressionType(int.class);

		assertThrows(CompileException.class, () -> {
			ee.cook("a + b");
			ee.evaluate(new Object[] { 19, 23 });
		});
	}

	@Test
	void testNormalEvaluateExpression01() {

		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(new String[] { "a", "b" }, new Class[] { int.class, int.class });
		ee.setExpressionType(int.class);
		int result;
		try {
			ee.cook("a + b");
			result = (Integer) ee.evaluate(new Object[] { 19, 23 });
			assertEquals(19 + 23, result);
		} catch (CompileException | InvocationTargetException e) {
			fail(e);
		}

	}

	@Test
	void testNormalEvaluateExpression02() {

		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(new String[] { "a", "b" }, new Class[] { int.class, int.class });
		ee.setExpressionType(boolean.class);
		boolean result;
		try {
			ee.cook("0<(a + b)");
			result = (boolean) ee.evaluate(new Object[] { 19, 23 });
			assertEquals(true, result);
		} catch (CompileException | InvocationTargetException e) {
			fail(e);
		}
	}
	
	@Test
	void testNormalEvaluateExpression03() {

		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(new String[] { "a", "b" }, new Class[] { int.class, int.class });
		ee.setExpressionType(boolean.class);
		boolean result;
		try {
			ee.cook("a>0 && b>0");
			result = (boolean) ee.evaluate(new Object[] { 19, 23 });
			assertEquals(true, result);
		} catch (CompileException | InvocationTargetException e) {
			fail(e);
		}
	}

	@Test
	void testSyntaxErrorExpression() {

		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(new String[] { "a", "b" }, new Class[] { int.class, int.class });
		ee.setExpressionType(boolean.class);

		assertThrows(CompileException.class, () -> {
			ee.cook("(0<a) + b");
			ee.evaluate(new Object[] { 19, 23 });
		});

	}
	
	@Test
	void testNormalEvaluateScript01() {

        ScriptEvaluator se = new ScriptEvaluator();
        
        try {
			HashMap<String,String> map=new HashMap<>();
			map.put("AAA", "string_1");
			map.put("BBB", "string_2");
			map.put("CCC", "string_3");
			se.setParameters(new String[] {"p1"}, new Class[] {LaboratoryInfo.class});
			se.setReturnType(boolean.class);
			
			se.cook(
				      "import ru.skkdc.lis.conclusion.labinfo.*;\n" + 				    		  
				      "\n" + 
				      "LaboratoryInfo labInfo=p1;\n" + 
				      "static Double ves(LaboratoryInfo labInfo) {\n" + 
				      "	return labInfo.getPatientInfo().getVes();\n" + 
				      "}\n" + 
				      "\n" + 
				      "static Integer berem(LaboratoryInfo labInfo) {\n" + 
				      "	//if(labInfo.getPatientInfo().getBerem()<35)\n" + 
				      "		//throw new RuntimeException(\"nslgjdflgjld\");\n" + 
				      "	return labInfo.getPatientInfo().getBerem();\n" + 
				      "}\n" + 
				      "\n" + 
				      "return (ves(p1)<100 && berem(p1)<35);"
				);
			
			Object res=se.evaluate(new Object[] {new LaboratoryInfoTestImpl()});
			 // Print script return value.
	        System.out.println("Result = " + (
	            res instanceof Object[]
	            ? Arrays.toString((Object[]) res)
	            : String.valueOf(res)
	        ));
			
		} catch (CompileException | InvocationTargetException e) {
			fail(e);
		}
	}
	
	@Test
	void testMapToJaninoExpressionParams() {
		
		HashMap<String,Object> map=new HashMap<>();
		map.put("AAA", "string_1");
		map.put("BBB", 1);
		map.put("CCC", 1.0);
		map.put("DDD", new Date());
		
		String strExpr="AAA.equals(\"string_1\") && (BBB+CCC)==2";
		String[] paramNames = map.keySet().toArray(new String[0]);
		Class<?>[] paramTypes=map.values().stream().map(v->v.getClass()).collect(Collectors.toList()).toArray(new Class<?>[0]);
		Object[] paramValue=map.values().toArray(new Object[0]);
				
		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setParameters(paramNames, paramTypes);
		ee.setExpressionType(boolean.class);
		
		try {
			ee.cook(strExpr);
			boolean result = (boolean) ee.evaluate(paramValue);
			assertEquals(true, result);
	        
		} catch (CompileException | InvocationTargetException e) {
			fail(e);
		}
	}

}
