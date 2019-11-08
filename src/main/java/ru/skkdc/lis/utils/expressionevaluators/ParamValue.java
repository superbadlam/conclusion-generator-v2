package ru.skkdc.lis.utils.expressionevaluators;

import ru.skkdc.lis.conclusion.template.ParamFormatterType;

public interface ParamValue<T> {
	String getText();
	Class<?> getType();
	T getValue();
	ParamFormatterType getFormatterType();
}
