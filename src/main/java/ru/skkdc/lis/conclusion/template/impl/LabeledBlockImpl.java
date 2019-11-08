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
import ru.skkdc.lis.conclusion.template.Element;
import ru.skkdc.lis.conclusion.template.LabeledBlock;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;
import ru.skkdc.lis.conclusion.template.ProcessContext;
import ru.skkdc.lis.conclusion.template.helpers.SubstituteManager;
import ru.skkdc.lis.utils.expressionevaluators.ParamString;
import ru.skkdc.lis.utils.expressionevaluators.ParamValue;

@XmlRootElement(name = "LabeledBlock")
@XmlAccessorType(XmlAccessType.FIELD)
class LabeledBlockImpl extends AbstractElement implements LabeledBlock {	
	
	
	@XmlAttribute(name = "label")
    private String label;
    @XmlElementRef
    private List<AbstractElement> elementList;	


    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<AbstractElement> getElementList() {
        return elementList;
    }
    public void setElementList(List<AbstractElement> elements) {
        this.elementList = elements;
    }

    @Override
    public List<String> process(ProcessContext context) {
    	
    	ArrayList<String> lines=new ArrayList<>();
    	Map<String, ResultInfo> results = context.getLaboratoryInfo().getResultInfo();
    	
    	if(results.containsKey(label)) {
		
    		Map<String, ParamValue<?>> oldParam = context.getParams();
    		Element oldParent = context.getParent();
    		
    		Map<String, ParamValue<?>> params = new HashMap<>();
    		
    		if(oldParam!=null)
    			params.putAll(oldParam);
    		
    		params.put(SubstituteManager.TESTNAME, new ParamString(results.get(label).getTestName(),ParamFormatterType.TESTNAME));
    		params.put(SubstituteManager.VALUE,    new ParamString(results.get(label).getValue(),ParamFormatterType.VAL));
    		params.put(SubstituteManager.LNORM,    new ParamString(results.get(label).getLNorm(),ParamFormatterType.LNORM));
    		params.put(SubstituteManager.UNORM,    new ParamString(results.get(label).getUNorm(),ParamFormatterType.UNORM));
    		params.put(SubstituteManager.FLAG,     new ParamString(results.get(label).getFlag(),ParamFormatterType.FLAG));
    		params.put(SubstituteManager.UNITS,    new ParamString(results.get(label).getUnits(),ParamFormatterType.UNITS));
    		
    		context.setParams(params);
    		context.setParent(this);
    		
			if (elementList != null)
				elementList.forEach(e -> lines.addAll(e.process(context)));
						
			context.setParent(oldParent);
			context.setParams(oldParam);
    	}
    	
        return lines;
    }


}
