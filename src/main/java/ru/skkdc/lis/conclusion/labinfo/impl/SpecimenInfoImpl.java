package ru.skkdc.lis.conclusion.labinfo.impl;

import ru.skkdc.lis.conclusion.labinfo.SpecimenInfo;

public class SpecimenInfoImpl implements SpecimenInfo{
	
	public static class Builder{
		private String shortId;
		private String barcodeValue;
		public Builder setShortId(String shortId) {
			this.shortId = shortId;
			return this;
		}
		public Builder setBarcodeValue(String barcodeValue) {
			this.barcodeValue = barcodeValue;
			return this;
		}
		public SpecimenInfoImpl build() {
			return new SpecimenInfoImpl(this);
		}
		
		
	}
	
	private String shortId;
	private String barcodeValue;

	public SpecimenInfoImpl(String barcodeValue, String shortId) {
		super();
		this.barcodeValue=barcodeValue;
		this.shortId=shortId;
	}

	private SpecimenInfoImpl(Builder builder) {
		
		if(builder.barcodeValue==null)
			throw new IllegalArgumentException("barcodeValue can't be a null");
		if(builder.shortId==null)
			throw new IllegalArgumentException("shortId can't be a null");
		
		this.barcodeValue=builder.barcodeValue;
		this.shortId=builder.shortId;
	}

	@Override
	public String getBarcodeVal() {
		return barcodeValue;
	}

	@Override
	public String getShortId() {
		return shortId;
	}
	
}