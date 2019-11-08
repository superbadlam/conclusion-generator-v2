package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamInteger extends AbstractParamValue<Integer> {

	private final Integer value;

	public ParamInteger(Integer value, Supplier<String> supplier,ParamFormatterType formatterType) {
		super(Integer.class, supplier, formatterType);
		this.value = value;
	}
	
	public ParamInteger(Integer value, Supplier<String> supplier) {
		super(Integer.class, supplier, ParamFormatterType.UNKNOWN);
		this.value = value;
	}
	
	public ParamInteger(Integer value,ParamFormatterType formatterType) {
		super(Integer.class, null, formatterType);
		this.value = value;
	}

	public ParamInteger(Integer value) {
		super(Integer.class, null, ParamFormatterType.UNKNOWN);
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

}
