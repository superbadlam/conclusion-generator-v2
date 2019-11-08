package ru.skkdc.lis.conclusion.template.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import ru.skkdc.lis.conclusion.labinfo.ResultInfo;
import ru.skkdc.lis.conclusion.template.ConditionalBlock;
import ru.skkdc.lis.conclusion.template.Element;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;
import ru.skkdc.lis.conclusion.template.ProcessContext;
import ru.skkdc.lis.conclusion.template.helpers.SubstituteManager;
import ru.skkdc.lis.utils.expressionevaluators.BooleanExpressionEvaluator;
import ru.skkdc.lis.utils.expressionevaluators.ParamString;
import ru.skkdc.lis.utils.expressionevaluators.ParamStringOrDoubleValue;
import ru.skkdc.lis.utils.expressionevaluators.ParamValue;

@XmlRootElement(name = "ConditionalBlock")
@XmlAccessorType(XmlAccessType.FIELD)
class ConditionalBlockImpl extends AbstractElement implements ConditionalBlock {

	@XmlAttribute(name = "condition")
	private String condition;
    @XmlElementRef
    private List<AbstractElement> elementList;	

	@Override
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

    public List<AbstractElement> getElementList() {
        return elementList;
    }
    public void setElementList(List<AbstractElement> elements) {
        this.elementList = elements;
    }


	@Override
	public List<String> process(ProcessContext context) {

		ArrayList<String> lines = new ArrayList<>();

		if (elementList != null) {

			Map<String, ParamValue<?>> oldParam = context.getParams();
			Element oldParent = context.getParent();

			Map<String, ParamValue<?>> params = 
					createParams(context.getLaboratoryInfo().getResultInfo(), oldParam);

			context.setParams(params);
			context.setParent(this);

			if (BooleanExpressionEvaluator.checkCondition(condition, params))
				elementList.forEach(e -> lines.addAll(e.process(context)));

			context.setParent(oldParent);
			context.setParams(oldParam);
		}
		return lines;
	}

	private Map<String, ParamValue<?>> createParams(Map<String, ResultInfo> results, Map<String, ParamValue<?>> oldParam) {
		Map<String, ParamValue<?>> params = new HashMap<>();

		if (oldParam != null)
			params.putAll(oldParam);

		results.keySet().stream().forEach(label -> {
			params.put(label + "_" + SubstituteManager.TESTNAME, new ParamString(results.get(label).getTestName(),ParamFormatterType.TESTNAME));
			params.put(label + "_" + SubstituteManager.VALUE, new ParamStringOrDoubleValue(results.get(label).getValue(),ParamFormatterType.VAL));
			params.put(label + "_" + SubstituteManager.LNORM, new ParamStringOrDoubleValue(results.get(label).getLNorm(),ParamFormatterType.LNORM));
			params.put(label + "_" + SubstituteManager.UNORM, new ParamStringOrDoubleValue(results.get(label).getUNorm(),ParamFormatterType.UNORM));
			params.put(label + "_" + SubstituteManager.FLAG, new ParamString(results.get(label).getFlag(),ParamFormatterType.FLAG));
			params.put(label + "_" + SubstituteManager.UNITS, new ParamString(results.get(label).getUnits(),ParamFormatterType.UNITS));
		});
		return params;
	}

	

}
