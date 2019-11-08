package ru.skkdc.lis.conclusion.template;

import java.util.Map;

import ru.skkdc.lis.conclusion.labinfo.LaboratoryInfo;
import ru.skkdc.lis.utils.expressionevaluators.ParamValue;

public interface ProcessContext {
	int getWidth();
	Map<String, ParamValue<?>> getParams();
	Element getParent();
	LaboratoryInfo getLaboratoryInfo();
	Map<String, ParamFormatter> getParamFormatters();
	
	void setWidth(int width);
	void setParams(Map<String, ParamValue<?>> params);
	void setParent(Element parent);
	void setLaboratoryInfo(LaboratoryInfo labContext);
	void setParamFormatters(Map<String, ParamFormatter> formatters);	
	
}