package ru.skkdc.lis.conclusion.template.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ru.skkdc.lis.conclusion.template.ParamFormatter;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;
import ru.skkdc.lis.conclusion.template.TextAlignment;

@XmlRootElement(name = "ParamFormatter")
@XmlAccessorType(XmlAccessType.PROPERTY)
class ParamFormatterImpl implements ParamFormatter {
	
	private ParamFormatterType paramtype;
	private TextAlignment alignment=TextAlignment.ALIGNMENT_LEFT;
	private int width=25;
	private int precision=-1;
	
	@XmlAttribute(name="paramtype")
	@Override
	public String getParamtype() {
		return paramtype.getTypeName();
	}
	public void setParamtype(String type) {
		this.paramtype = ParamFormatterType.create(type);
	}
	
	@XmlAttribute(name="alignment")
	@Override
	public String getAlignment() {
		return alignment.getAlignment();
	}
	public void setAlignment(String alignment) {
		this.alignment = TextAlignment.create(alignment);
	}
	
	@XmlAttribute
	@Override
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	@XmlAttribute(name = "precision")
	@Override
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	
	public ParamFormatterImpl(ParamFormatterType type, TextAlignment alignment, Integer width, int precision) {
		super();
		this.paramtype = type;
		this.alignment = alignment;
		this.width = width;
		this.precision = precision;
	}
	public ParamFormatterImpl(ParamFormatterType type, TextAlignment alignment, int width) {
		super();
		this.paramtype = type;
		this.alignment = alignment;
		this.width = width;
	}
	public ParamFormatterImpl() {
		super();
	}
	public ParamFormatterImpl(ParamFormatterType type) {
		super();
		this.paramtype = type;
	}
	public ParamFormatterImpl(ParamFormatterType type, TextAlignment alignment) {
		super();
		this.paramtype = type;
		this.alignment = alignment;
	}
	public ParamFormatterImpl(ParamFormatterType type, int width) {
		super();
		this.paramtype = type;
		this.width = width;
	}
	
}
