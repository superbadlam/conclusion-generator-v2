package ru.skkdc.lis.conclusion.labinfo.impl;

import java.util.Date;

import ru.skkdc.lis.conclusion.labinfo.Contingent;
import ru.skkdc.lis.conclusion.labinfo.Faza;
import ru.skkdc.lis.conclusion.labinfo.PatientInfo;
import ru.skkdc.lis.conclusion.labinfo.Sex;

public class PatientInfoImpl implements PatientInfo{
	
	
	public static class Builder{
		private Sex sex;
		private Faza faza;
		private Contingent contingent;
		private Integer berem;
		private Double ves;
		private Double volume;
		private Date birthdate;
		private String resultAddress;
		
		
		
		public Builder setResultAddress(String resultAddress) {
			this.resultAddress = resultAddress;
			return this;
		}
		public Builder setSex(int sexCode) {
			this.sex = Sex.get(sexCode);
			return this;
		}
		public Builder setFaza(long code, String name) {
			
			if(name==null)
				throw new IllegalArgumentException();
			
			this.faza = new FazaImpl(name, code);
			return this;
		}
		public Builder setContingent(long code, String name) {
			
			if(name==null)
				throw new IllegalArgumentException();
			
			this.contingent = new ContingentImpl(name, code);
			return this;
		}
		public Builder setBerem(Integer berem) {
			this.berem = berem;
			return this;
		}
		public Builder setVes(Double ves) {
			this.ves = ves;
			return this;
		}
		public Builder setVolume(Double volume) {
			this.volume = volume;
			return this;
		}
		public Builder setBirthdate(Date birthdate) {
			if(birthdate==null)
				throw new IllegalArgumentException();
			this.birthdate = birthdate;
			return this;
		}
		
		public PatientInfoImpl build() {
			return new PatientInfoImpl(this);
		}
		
	}
	
	private Sex sex;
	private Faza faza;
	private Contingent contingent;
	private Integer berem;
	private Double ves;
	private Double volume;
	private Date birthdate;
	private String resultAddress;
	
	
	
	private PatientInfoImpl(Builder builder) {
		super();
		
		if(builder.sex==null)
			throw new IllegalArgumentException("sex can't be a null");
		if(builder.birthdate==null)
			throw new IllegalArgumentException("birthdate can't be a null");

		this.berem=builder.berem;
		this.contingent=builder.contingent;
		this.faza=builder.faza;
		this.sex=builder.sex;
		this.ves=builder.ves;
		this.volume=builder.volume;
		this.birthdate=builder.birthdate;
		this.resultAddress=builder.resultAddress;
	}
	
	@Override
	public Sex getSex() {
		return sex;
	}
	@Override
	public Faza getFaza() {
		return faza;
	}
	@Override
	public Contingent getContingent() {
		return contingent;
	}
	@Override
	public Integer getBerem() {
		return berem;
	}
	@Override
	public Double getVes() {
		return ves;
	}
	@Override
	public Double getVolume() {
		return volume;
	}
	@Override
	public Date getBirthdate() {
		return birthdate;
	}

	@Override
	public String getResultAddress() {
		return resultAddress;
	}
	
}