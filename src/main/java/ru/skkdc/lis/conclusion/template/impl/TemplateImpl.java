package ru.skkdc.lis.conclusion.template.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ru.skkdc.lis.conclusion.labinfo.Contingent;
import ru.skkdc.lis.conclusion.labinfo.Faza;
import ru.skkdc.lis.conclusion.labinfo.LaboratoryInfo;
import ru.skkdc.lis.conclusion.labinfo.Sex;
import ru.skkdc.lis.conclusion.template.Element;
import ru.skkdc.lis.conclusion.template.ParamFormatter;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;
import ru.skkdc.lis.conclusion.template.ProcessContext;
import ru.skkdc.lis.conclusion.template.Template;
import ru.skkdc.lis.conclusion.template.helpers.SubstituteManager;
import ru.skkdc.lis.utils.expressionevaluators.ParamContingent;
import ru.skkdc.lis.utils.expressionevaluators.ParamDouble;
import ru.skkdc.lis.utils.expressionevaluators.ParamFaza;
import ru.skkdc.lis.utils.expressionevaluators.ParamInteger;
import ru.skkdc.lis.utils.expressionevaluators.ParamSex;
import ru.skkdc.lis.utils.expressionevaluators.ParamString;
import ru.skkdc.lis.utils.expressionevaluators.ParamValue;

@XmlType(propOrder = { "formatters", "elementList" })
@XmlRootElement(name = "Template")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateImpl implements Template {

	@XmlAttribute(name = "version", required = true)
	private String version = "1.1";
	@XmlAttribute(name = "name", required = true)
	private String name;
	@XmlAttribute(name = "width")
	private int width = 60;
	@XmlElementRef
    private List<AbstractElement> elementList;	
	@XmlElement(name = "ParamFormatter")
	private List<ParamFormatterImpl> formatters;

	public void setVersion(String version) {
		this.version = version;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getWidth() {
		return width;
	}

    public List<AbstractElement> getElementList() {
        return elementList;
    }
    public void setElementList(List<AbstractElement> elements) {
        this.elementList = elements;
    }

	public List<ParamFormatterImpl> getFormatters() {
		return formatters;
	}

	public void setFormatters(List<ParamFormatterImpl> formatters) {
		this.formatters = formatters;
	}

	@Override
	public String buildConclusion(LaboratoryInfo labContext, Consumer<Exception> warningsHandler) {
		if(warningsHandler==null)
			throw new IllegalArgumentException("warningsHandler can't be a null");
		return elementList != null ? processElements(labContext, warningsHandler) : "";
	}
	

	private String processElements(LaboratoryInfo labContext, Consumer<Exception> warningsConsumer) {
		final StringBuilder sb = new StringBuilder();
		ProcessContext context = buildContext(labContext);
		
		for (Element el : elementList) {
			try {
				List<String> lines=el.process(context);
				lines.forEach(line -> {
					sb.append(line);
					sb.append('\n');
				});
			}catch (Exception e) {
				warningsConsumer.accept(e);
			}
		}

		return sb.toString();
	}

	private ProcessContext buildContext(LaboratoryInfo labContext) {

		Map<String, ParamValue<?>> params = new HashMap<>();

		params.put(SubstituteManager.BARCODE, new ParamString(labContext.getSpecimenInfo().getBarcodeVal(),ParamFormatterType.BARCODE));
		params.put(SubstituteManager.SHORTID, new ParamString(labContext.getSpecimenInfo().getShortId(),ParamFormatterType.SHORTID));
		params.put(SubstituteManager.RESULT_ADDERSS, new ParamString(labContext.getPatientInfo().getResultAddress(),ParamFormatterType.RESULTADDRESS));		
		
		Sex sex=labContext.getPatientInfo().getSex();
		params.put(SubstituteManager.SEX, new ParamSex(sex,()->sex==null ? "" : sex.getName()));
		
		Faza faza=labContext.getPatientInfo().getFaza();
		params.put(SubstituteManager.FAZA, new ParamFaza(faza,()->faza==null ? "" : faza.getName()));
		
		Contingent contingent=labContext.getPatientInfo().getContingent();
		params.put(SubstituteManager.CONTINGENT, new ParamContingent(contingent,()-> contingent==null ? "" : contingent.getName()));
		
		params.put(SubstituteManager.BEREM, new ParamInteger(labContext.getPatientInfo().getBerem(),ParamFormatterType.BEREM));
		params.put(SubstituteManager.VES, new ParamDouble(labContext.getPatientInfo().getVes(),ParamFormatterType.VES));
		params.put(SubstituteManager.VOLUME, new ParamDouble(labContext.getPatientInfo().getVolume(),ParamFormatterType.VOLUME));

		ProcessContextImpl context = new ProcessContextImpl();
		context.setParams(params);
		context.setParent(null);
		context.setWidth(width);
		context.setLaboratoryInfo(labContext);
		context.setParamFormatters(formattersAsMap());
		return context;
	}

	private Map<String, ParamFormatter> formattersAsMap() {
		HashMap<String, ParamFormatter> fs = new HashMap<>();
		formatters.forEach(f -> fs.put(f.getParamtype(), f));
		return fs;
	}

	@Override
	public boolean isEmpty() {
		return (elementList==null || elementList.isEmpty());
	}
}
