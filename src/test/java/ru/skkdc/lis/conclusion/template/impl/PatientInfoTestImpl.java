package ru.skkdc.lis.conclusion.template.impl;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import ru.skkdc.lis.conclusion.labinfo.Contingent;
import ru.skkdc.lis.conclusion.labinfo.Faza;
import ru.skkdc.lis.conclusion.labinfo.PatientInfo;
import ru.skkdc.lis.conclusion.labinfo.Sex;

class PatientInfoTestImpl implements PatientInfo{

	@Override
	public Sex getSex() {
		return Sex.MALE;
	}

	@Override
	public Faza getFaza() {
		return new Faza() {
			
			@Override
			public String getName() {
				return "фаза 1";
			}
			
			@Override
			public long getCode() {
				return 1;
			}
		};
	}

	@Override
	public Contingent getContingent() {
		return new Contingent() {
			
			@Override
			public String getName() {
				return "контингент 1";
			}
			
			@Override
			public long getCode() {
				return 1;
			}
		};
	}

	@Override
	public Integer getBerem() {
		return 25;
	}

	@Override
	public Double getVes() {
		return 80.0;
	}

	@Override
	public Date getBirthdate() {		
		return Date.from(LocalDate.of(2011, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public Double getVolume() {
		return 3.0;
	}

	@Override
	public String getResultAddress() {
		return "на руки пациенту";
	}

}