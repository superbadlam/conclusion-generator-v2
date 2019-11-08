package ru.skkdc.lis.conclusion.template.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import ru.skkdc.lis.conclusion.labinfo.ResultInfo;

class ResultInfoTestImpl implements ResultInfo{

	private final DecimalFormat decFormatter;
	
	private double val = Math.random();
	private double lnorm = Math.random();
	private double unorm = Math.random();
	private String flag = ((val<lnorm || val>unorm) ? " " : "!");
	private String testName;
	
	ResultInfoTestImpl(String testName) {
		super();
		DecimalFormatSymbols symbol = new DecimalFormatSymbols();
		symbol.setGroupingSeparator(' ');
		symbol.setDecimalSeparator('.');
		decFormatter = new DecimalFormat("##########.####################", symbol);
		this.testName = testName;
	}

	@Override
	public String getValue() {	
		return decFormatter.format(val);
	}

	@Override
	public String getLNorm() {
		return decFormatter.format(lnorm);
	}

	@Override
	public String getUNorm() {
		return decFormatter.format(unorm);
	}

	@Override
	public String getUnits() {
		return "tygrek";
	}

	@Override
	public String getFlag() {
		return flag;
	}

	@Override
	public String getTestName() {
		return testName;
	}
	
}