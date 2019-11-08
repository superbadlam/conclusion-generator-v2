package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamStringOrDoubleValue extends AbstractParamValue<Object> {

	private final Object value;
	
	
	public ParamStringOrDoubleValue(String value, Supplier<String> supplier,ParamFormatterType formatterType) {
		super(computeClass(value),supplier, formatterType);
		this.value=morphValue(value);
	}

	public ParamStringOrDoubleValue(String value, Supplier<String> supplier) {
		super(computeClass(value),supplier, ParamFormatterType.UNKNOWN);
		this.value=morphValue(value);
	}
	
	public ParamStringOrDoubleValue(String value,ParamFormatterType formatterType) {
		super(computeClass(value),null,formatterType);				
		this.value=morphValue(value);					
	}

	public ParamStringOrDoubleValue(String value) {
		super(computeClass(value),null,ParamFormatterType.UNKNOWN);				
		this.value=morphValue(value);					
	}
	
	private static Class<?> computeClass(String value) {
		return isNumber(value)? Double.class : String.class;
	}
	
	private static Object morphValue(String value) {
		return isNumber(value) ? Double.parseDouble(value) : value;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	private static boolean isNumber(String text) {
		if(text==null)
			return false;
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public ParamFormatterType getFormatterType() {
		return formatterType;
	}
	
}
