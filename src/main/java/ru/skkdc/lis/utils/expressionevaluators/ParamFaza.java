package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.labinfo.Faza;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public final class ParamFaza extends AbstractParamValue<Faza> {

	private final Faza value;

	public ParamFaza(Faza value, Supplier<String> supplier) {
		super(Faza.class, supplier, ParamFormatterType.FAZA);
		this.value = value;
	}

	public ParamFaza(Faza value) {
		super(Faza.class, null,ParamFormatterType.FAZA);
		this.value = value;
	}

	@Override
	public Faza getValue() {
		return value;
	}

	@Override
	public ParamFormatterType getFormatterType() {
		return ParamFormatterType.SEX;
	}

}
