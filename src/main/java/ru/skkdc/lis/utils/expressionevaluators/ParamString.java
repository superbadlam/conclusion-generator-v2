package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamString extends AbstractParamValue<String> {

	private final String value;

	public ParamString(String value, Supplier<String> supplier,ParamFormatterType formatterType) {
		super(String.class, supplier,formatterType);
		this.value = value;
	}

	public ParamString(String value, Supplier<String> supplier) {
		super(String.class, supplier, ParamFormatterType.UNKNOWN);
		this.value = value;
	}
	
	public ParamString(String value, ParamFormatterType formatterType) {
		super(String.class, null,formatterType);
		this.value = value;
	}
	
	public ParamString(String value) {
		super(String.class, null, ParamFormatterType.UNKNOWN);
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}
