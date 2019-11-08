package ru.skkdc.lis.conclusion.template.impl;

import java.util.HashMap;
import java.util.Map;

import ru.skkdc.lis.conclusion.labinfo.LaboratoryInfo;
import ru.skkdc.lis.conclusion.labinfo.PatientInfo;
import ru.skkdc.lis.conclusion.labinfo.ResultInfo;
import ru.skkdc.lis.conclusion.labinfo.SpecimenInfo;

public class LaboratoryInfoTestImpl implements LaboratoryInfo{

	public static final String LABEL01 = "label01";
	public static final String LABEL02 = "label02";
	public static final String LABEL03 = "label03";
	
	private SpecimenInfoTestImpl specimenInfo = new SpecimenInfoTestImpl();
	private PatientInfoTestImpl patientInfo = new PatientInfoTestImpl();
	private Map<String, ResultInfo> resultInfo = new HashMap<>();
	
	
	
	public LaboratoryInfoTestImpl() {
		super();
		
		resultInfo.put(LABEL01, new ResultInfoTestImpl("testname01"));
		resultInfo.put(LABEL02, new ResultInfoTestImpl("testname02"));
		resultInfo.put(LABEL03, new ResultInfoTestImpl("testname03"));
	}

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
		return resultInfo;
	}
	
}
