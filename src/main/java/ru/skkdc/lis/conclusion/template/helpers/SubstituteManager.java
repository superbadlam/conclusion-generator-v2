package ru.skkdc.lis.conclusion.template.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubstituteManager {
	
	public static final String VALUE = "val";
	public static final String LNORM = "lnorm";
	public static final String UNORM = "unorm";
	public static final String FLAG = "flag";
	public static final String UNITS = "units";
	public static final String TESTNAME = "testname";
	public static final String BARCODE = "barcode";
	public static final String SHORTID = "shortId";
	public static final String SEX = "sex";
	public static final String FAZA = "faza";
	public static final String CONTINGENT = "contingent";
	public static final String BEREM = "berem";
	public static final String VES = "ves";
	public static final String VOLUME = "volume";
	public static final String RESULT_ADDERSS = "address";
	
	
	private Map<String, String> substitutionMap = null;
	private Pattern pattern;
	
	
	public static String substitute(String originalText, Map<String, String> substitutionMap) {
		SubstituteManager manager =new SubstituteManager(substitutionMap);
		return manager.substitute(originalText);
	}
	
	
	private SubstituteManager(Map<String, String> substitutionMap) {
		super();
		if(substitutionMap==null)
			substitutionMap=new HashMap<>();
		this.substitutionMap = substitutionMap;
		String patternString = "\\[(" + String.join("|", substitutionMap.keySet()) + ")\\]";
		pattern = Pattern.compile(patternString);
	}

	private String substitute(String originalText) {

		if (originalText == null || originalText.isEmpty())
			return "";

		StringBuffer sb = new StringBuffer();
		Matcher matcher = pattern.matcher(originalText.replace("\t", ""));
		while (matcher.find()) {
			matcher.appendReplacement(sb, substitutionMap.get(matcher.group(1)));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

}
