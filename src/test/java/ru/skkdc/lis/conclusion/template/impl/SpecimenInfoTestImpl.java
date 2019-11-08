package ru.skkdc.lis.conclusion.template.impl;

import ru.skkdc.lis.conclusion.labinfo.SpecimenInfo;

class SpecimenInfoTestImpl implements SpecimenInfo{

	@Override
	public String getBarcodeVal() {
		return "123456789012";
	}

	@Override
	public String getShortId() {
		return "1234";
	}
	
}