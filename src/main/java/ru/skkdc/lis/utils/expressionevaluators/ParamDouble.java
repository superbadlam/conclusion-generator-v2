package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamDouble extends AbstractParamValue<Double> {

	private final Double value;
	
	public ParamDouble(Double value, Supplier<String> supplier, ParamFormatterType formatterType) {
		super(Double.class,supplier, formatterType);
		this.value=value;
	}
	
	public ParamDouble(Double value, Supplier<String> supplier) {
		super(Double.class,supplier, ParamFormatterType.UNKNOWN);
		this.value=value;
	}	
	

	public ParamDouble(Double value,ParamFormatterType formatterType) {
		super(Double.class,null, formatterType);
		this.value = value;
	}

	public ParamDouble(Double value) {
		super(Double.class,null, ParamFormatterType.UNKNOWN);
		this.value = value;
	}	
	
	@Override
	public Double getValue() {
		return value;
	}
	
}
