package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamLong extends AbstractParamValue<Long> {

	private final Long value;

	public ParamLong(Long value, Supplier<String> supplier,ParamFormatterType formatterType) {
		super(Long.class, supplier,formatterType);
		this.value = value;
	}
	
	public ParamLong(Long value, Supplier<String> supplier) {
		super(Long.class, supplier,ParamFormatterType.UNKNOWN);
		this.value = value;
	}
	

	public ParamLong(Long value, ParamFormatterType formatterType) {
		super(Long.class, null, formatterType);
		this.value = value;
	}
	
	public ParamLong(Long value) {
		super(Long.class, null, ParamFormatterType.UNKNOWN);
		this.value = value;
	}
	
	@Override
	public Long getValue() {
		return value;
	}

}
