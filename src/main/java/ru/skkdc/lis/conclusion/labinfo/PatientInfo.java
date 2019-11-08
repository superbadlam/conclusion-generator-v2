package ru.skkdc.lis.conclusion.labinfo;

import java.util.Date;

public interface PatientInfo {

	Date getBirthdate();
	Sex getSex();
	Faza getFaza();
	Contingent getContingent();
	Integer getBerem();
	Double getVes();
	Double getVolume();
	String getResultAddress();
}
