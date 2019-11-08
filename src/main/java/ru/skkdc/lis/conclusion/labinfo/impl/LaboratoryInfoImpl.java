package ru.skkdc.lis.conclusion.labinfo.impl;

import java.util.Map;

import ru.skkdc.lis.conclusion.labinfo.LaboratoryInfo;
import ru.skkdc.lis.conclusion.labinfo.PatientInfo;
import ru.skkdc.lis.conclusion.labinfo.ResultInfo;
import ru.skkdc.lis.conclusion.labinfo.SpecimenInfo;

public class LaboratoryInfoImpl implements LaboratoryInfo{

	public static class Builder {
		private SpecimenInfo specimenInfo; 
		private PatientInfo patientInfo;
		private Map<String, ResultInfo> results ;
		
		public Builder setSpecimenInfo(SpecimenInfo specimenInfo) {
			this.specimenInfo = specimenInfo;
			return this;
		}
		public Builder setPatientInfo(PatientInfo patientInfo) {
			this.patientInfo = patientInfo;
			return this;
		}
		public Builder setResults(Map<String, ResultInfo> results) {
			this.results = results;
			return this;
		}
		
		public LaboratoryInfoImpl build() {
			return new LaboratoryInfoImpl(this);
		}
		
	}
	
	
	
	private LaboratoryInfoImpl(Builder b) {
		super();
		
		if(b.specimenInfo==null)
			throw new IllegalArgumentException("specimenInfo не может быть null");
		if(b.patientInfo==null)
			throw new IllegalArgumentException("patientInfo не может быть null");
		if(b.results==null)
			throw new IllegalArgumentException("results не может быть null");		
		if(b.results.isEmpty())
			throw new IllegalArgumentException("results должен содержать хотя бы один елемент");
		
		specimenInfo=b.specimenInfo;
		patientInfo=b.patientInfo;
		results=b.results;
	}

	private SpecimenInfo specimenInfo; 
	private PatientInfo patientInfo;
	private Map<String, ResultInfo> results ; 
	

	@Override
	public SpecimenInfo getSpecimenInfo() {
		return specimenInfo;
	}

	@Override
	public PatientInfo getPatientInfo() {
		return patientInfo;
	}

	@Override
	public Map<String, ResultInfo> getResultInfo() {
		return results;
	}
	
}
