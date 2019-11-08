package ru.skkdc.lis.utils.expressionevaluators;

import java.util.function.Supplier;

import ru.skkdc.lis.conclusion.template.ParamFormatterType;

abstract class AbstractParamValue<T> implements ParamValue<T> {

	protected final Class<? extends T> type;
	protected final Supplier<String> supplier;
	protected ParamFormatterType formatterType;
	

	protected AbstractParamValue(Class<? extends T> type, Supplier<String> supplier, ParamFormatterType formatterType) {
		super();
		
		if(formatterType==null) 
			throw new IllegalArgumentException("formatterType can't be a null");
		
		if(type==null) 
			throw new IllegalArgumentException("type can't be a null");
		
		this.type = type;
		this.supplier = supplier;
		this.formatterType=formatterType;
	}

	@Override
	public String getText() {
		return supplier==null ? toString() : supplier.get();
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public String toString() {
		return getValue()==null ? "" : getValue().toString();
	}

	@Override
	public ParamFormatterType getFormatterType() {
		return formatterType;
	}


}