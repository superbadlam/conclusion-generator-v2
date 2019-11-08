package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.labinfo.Sex;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamSex extends AbstractParamValue<Sex> {

	private final Sex value;
	
	public ParamSex(Sex value, Supplier<String> supplier) {
		super(Sex.class,supplier,ParamFormatterType.SEX);
		this.value=value;
	}

	public ParamSex(Sex value) {
		super(Sex.class,null,ParamFormatterType.SEX);
		this.value = value;
	}

	@Override
	public Sex getValue() {
		return value;
	}

	@Override
	public ParamFormatterType getFormatterType() {
		return ParamFormatterType.SEX;
	}
	
}
