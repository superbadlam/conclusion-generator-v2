package ru.skkdc.lis.conclusion.template.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.skkdc.lis.conclusion.template.TextAlignment;

public class AdjustmentManager {

	private TextAlignment alignment;
	private int width;
	private char[] spaceHolder;
	
	public static List<String> adjustText(String text, TextAlignment alignment, int width) {
		AdjustmentManager adjustor=new AdjustmentManager(alignment, width);
		return adjustor.splitText(text);
	}

	public static List<String> adjustText(String text, TextAlignment alignment, int width, int precision) {
		String newText=null;
		if(text!=null && precision>=0 && isNumber(text))
			newText=round(text, precision);
		else
			newText=text;
		
		return adjustText(newText,alignment,width);
	}
	
	private static boolean isNumber(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private static String round(String text, int precision) {
	    if (precision < 0) throw new IllegalArgumentException();
	    
	    BigDecimal bd = new BigDecimal(text);
	    bd = bd.setScale(precision, RoundingMode.HALF_UP);
	    return bd.toPlainString();
	}
	
	private AdjustmentManager(TextAlignment alignment, int width) {
		super();
		switch (alignment) {
		case ALIGNMENT_CENTER:
		case ALIGNMENT_LEFT:
		case ALIGNMENT_RIGHT:
			this.alignment = alignment;
			break;
		default:
			throw new IllegalArgumentException("invalid alignment");
		}
		if (width < 1)
			throw new IllegalArgumentException("width must be greater than or equal to one");
		this.width = width;
		
		spaceHolder=new char[width];
		Arrays.fill(spaceHolder, ' ');
	}

	private List<String> splitText(String text) {
		List<String> list = new ArrayList<>();
		
		if (text == null)
			return list;

		list=splitByLines(text)
			.map(line->splitByLenght(line))
			.flatMap(lines->lines.stream())
			.collect(Collectors.toList());

		return list;
	}

	private Stream<String> splitByLines(String text) {
		return Arrays.stream(text.replace("\r\n","\r").replace('\n', '\r').split("\r"));
	}

	
	private List<String> splitByLenght(String text) {
		List<String> list = new ArrayList<>();
		String t=text.isEmpty() ? " " : text;
		for (int i = 0; i < t.length(); i = i + width) {
			int endindex = Math.min(i + width, t.length());
			list.add(format(t.substring(i, endindex)));
		}
		return list;
	}

	private void pad(StringBuilder to, int howMany) {
		to.append(spaceHolder,0,howMany);
	}

	private String format(String s) {
		StringBuilder where = new StringBuilder();
		switch (alignment) {
		case ALIGNMENT_RIGHT:
			pad(where, width - s.length());
			where.append(s);
			break;
		case ALIGNMENT_CENTER:
			int toAdd = width - s.length();
			pad(where, (toAdd / 2));
			where.append(s);
			pad(where, toAdd - (toAdd / 2));
			break;
		case ALIGNMENT_LEFT:
		default:
			where.append(s);
			pad(where, width - s.length());
			break;
		}
		return where.toString();
	}
}
