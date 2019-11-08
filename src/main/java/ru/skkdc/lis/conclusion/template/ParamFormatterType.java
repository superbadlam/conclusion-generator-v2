package ru.skkdc.lis.conclusion.template;

import java.util.HashMap;

public enum ParamFormatterType {
	TESTNAME("testname"), VAL("val"), LNORM("lnorm"), UNORM("unorm"), UNITS("units"), BARCODE("barcode"),
	SHORTID("shortId"), DAYSOFLIVE("daysOfLive"), MOUNTHESOFLIVE("mounthesOfLive"), YEARSOFLIVE("yearsOfLive"),
	SEX("sex"), FAZA("faza"), CONTINGENT("contingent"), BEREM("berem"), VES("ves"),
	VOLUME("volume"), FLAG("flag"), RESULTADDRESS("address"), UNKNOWN("unknown");

	private static final HashMap<String, ParamFormatterType> types=new HashMap<>();
	static {
		types.put(TESTNAME.getTypeName(), TESTNAME);
		types.put(VAL.getTypeName(), VAL);
		types.put(LNORM.getTypeName(), LNORM);
		types.put(UNORM.getTypeName(), UNORM);
		types.put(UNITS.getTypeName(), UNITS);
		types.put(BARCODE.getTypeName(), BARCODE);
		types.put(SHORTID.getTypeName(), SHORTID);
		types.put(DAYSOFLIVE.getTypeName(), DAYSOFLIVE);
		types.put(MOUNTHESOFLIVE.getTypeName(), MOUNTHESOFLIVE);
		types.put(YEARSOFLIVE.getTypeName(), YEARSOFLIVE);
		types.put(SEX.getTypeName(), SEX);
		types.put(FAZA.getTypeName(), FAZA);
		types.put(CONTINGENT.getTypeName(), CONTINGENT);
		types.put(BEREM.getTypeName(), BEREM);
		types.put(VES.getTypeName(), VES);
		types.put(VOLUME.getTypeName(), VOLUME);
		types.put(FLAG.getTypeName(), FLAG);
		types.put(RESULTADDRESS.getTypeName(), RESULTADDRESS);
		types.put(UNKNOWN.getTypeName(), UNKNOWN);		
	}
	
	private String typeName;

	private ParamFormatterType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	
	
	public static ParamFormatterType create(String typeName) {
		return types.getOrDefault(typeName, ParamFormatterType.UNKNOWN);
	}

}
