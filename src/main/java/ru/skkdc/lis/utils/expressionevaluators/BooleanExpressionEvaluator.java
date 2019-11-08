package ru.skkdc.lis.utils.expressionevaluators;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BooleanExpressionEvaluator {
	
	private BooleanExpressionEvaluator() {
		super();
	}

	public static boolean checkCondition(String expr, Map<String,  ParamValue<?>> params) {
		
		if(expr==null)
			throw new IllegalArgumentException("expression can't be a null");
		
		boolean result;
		
		String[] paramNames = params.keySet().toArray(new String[0]);
		Class<?>[] paramTypes=params.values().stream().map(v->v.getType()).collect(Collectors.toList()).toArray(new Class<?>[0]);
		Object[] paramValue=params.values().stream().map(p->p.getValue()).collect(Collectors.toList()).toArray();
				
		ExpressionEvaluator ee = new ExpressionEvaluator();
		ee.setDefaultImports(new String[]{"ru.skkdc.lis.conclusion.labinfo.*"});
		ee.setParameters(paramNames, paramTypes);
		ee.setExpressionType(boolean.class);
		
		try {
			ee.cook(expr);
			result = (boolean) ee.evaluate(paramValue);
			log.trace("вычислили  выражение: expr='{}', result={}", expr, result);
		} catch (IllegalArgumentException | CompileException | InvocationTargetException e) {
			log.warn(String.format("не удалось вычислить выражение: expr='%s', params=%s",expr, params),e);
			throw new ExpressionEvaluatorException(String.format("не удалось вычислить выражение: expr='%s', cause=%s",expr, e.getMessage()),e);
		}
		return result;
	}
}
