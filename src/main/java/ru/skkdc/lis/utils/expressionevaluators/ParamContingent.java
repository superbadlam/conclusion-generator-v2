package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.labinfo.Contingent;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamContingent extends AbstractParamValue<Contingent> {

	private final Contingent value;

	public ParamContingent(Contingent value) {
		super(Contingent.class,null, ParamFormatterType.CONTINGENT);				
		this.value = value;
	}
	
	public ParamContingent(Contingent value, Supplier<String> supplier) {
		super(Contingent.class,supplier, ParamFormatterType.CONTINGENT);
		this.value=value;
	}

	@Override
	public Contingent getValue() {
		return value;
	}


}
