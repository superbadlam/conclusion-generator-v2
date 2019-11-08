package ru.skkdc.lis.conclusion.template.impl;

import java.util.Map;

import ru.skkdc.lis.conclusion.labinfo.LaboratoryInfo;
import ru.skkdc.lis.conclusion.template.Element;
import ru.skkdc.lis.conclusion.template.ParamFormatter;
import ru.skkdc.lis.conclusion.template.ProcessContext;
import ru.skkdc.lis.utils.expressionevaluators.ParamValue;

class ProcessContextImpl implements ProcessContext {
    private int width;
	private Map<String, ParamValue<?>> params;
	private Element parent;
	private LaboratoryInfo labContext;
	private Map<String, ParamFormatter> formatters;
	@Override
	public int getWidth() {
		return width;
	}
	@Override
	public void setWidth(int width) {
		this.width = width;
	}
	@Override
	public Map<String, ParamValue<?>> getParams() {
		return params;
	}
	@Override
	public void setParams(Map<String, ParamValue<?>> params) {
		this.params = params;
	}
	@Override
	public Element getParent() {
		return parent;
	}
	@Override
	public void setParent(Element parent) {
		this.parent = parent;
	}
	@Override
	public void setLaboratoryInfo(LaboratoryInfo labContext) {
		this.labContext=labContext;
	}
	@Override
	public LaboratoryInfo getLaboratoryInfo() {
		return labContext;
	}
	@Override
	public Map<String, ParamFormatter> getParamFormatters() {
		return formatters;
	}
	@Override
	public void setParamFormatters(Map<String, ParamFormatter> formatters) {
		this.formatters = formatters;
	}

}
