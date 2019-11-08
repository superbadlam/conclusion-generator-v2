package ru.skkdc.lis.conclusion.template.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import ru.skkdc.lis.conclusion.template.ParamFormatter;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;
import ru.skkdc.lis.conclusion.template.ProcessContext;
import ru.skkdc.lis.conclusion.template.Text;
import ru.skkdc.lis.conclusion.template.TextAlignment;
import ru.skkdc.lis.conclusion.template.helpers.AdjustmentManager;
import ru.skkdc.lis.conclusion.template.helpers.SubstituteManager;
import ru.skkdc.lis.utils.expressionevaluators.ParamValue;

@XmlRootElement(name = "Text")
@XmlAccessorType(XmlAccessType.FIELD)
class TextImpl extends AbstractElement implements Text {

	@XmlTransient
	private TextAlignment alignment = TextAlignment.ALIGNMENT_LEFT;

	@XmlAttribute
	@Override
	public String getAlignment() {
		return alignment.getAlignment();
	}

	public void setAlignment(String alignment) {
		this.alignment = TextAlignment.create(alignment);
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public List<String> process(ProcessContext context) {
		int width = context.getWidth();
		Map<String, String> params = adjustEachParam(context.getParams(),context.getParamFormatters());
		return AdjustmentManager.adjustText(SubstituteManager.substitute(text, params), alignment, width);
	}

	private Map<String, String> adjustEachParam(Map<String, ParamValue<?>> contextParams,	Map<String, ParamFormatter> contextFormatters) {
		HashMap<String, String> adjustedParams=new HashMap<>();
		
		if(contextParams!=null) {
			contextParams.keySet().forEach(key->{
				
				ParamFormatterType formatterType=contextParams.get(key).getFormatterType();
				if(contextFormatters!=null && contextFormatters.containsKey(formatterType.getTypeName())){
					ParamFormatter formatter=contextFormatters.get(formatterType.getTypeName());
					ParamValue<?> param = contextParams.get(key);
				   adjustedParams.put(key, adjustParam(param, formatter));
				}else {
					adjustedParams.put(key, contextParams.get(key).getText());
				}
				
			});
		}
		
		return adjustedParams;
	}

	private String adjustParam(ParamValue<?> param,  ParamFormatter formatter) {
		
		List<String> lines = AdjustmentManager.adjustText(
				param.getText(),
				TextAlignment.create(formatter.getAlignment()),
				formatter.getWidth(),
				formatter.getPrecision()
		);
		
		return lines.get(0);
	}

}
