package ru.skkdc.lis.conclusion.labinfo;

import java.util.Map;

public interface LaboratoryInfo {
	SpecimenInfo getSpecimenInfo();
	PatientInfo getPatientInfo();
	Map<String,ResultInfo> getResultInfo();
}
