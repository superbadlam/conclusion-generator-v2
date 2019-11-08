package ru.skkdc.lis.conclusion.template;

public enum TextAlignment {
	ALIGNMENT_LEFT("left"), ALIGNMENT_CENTER("center"), ALIGNMENT_RIGHT("right");
	
	private String alignment;

	private TextAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getAlignment() {
		return alignment;
	}
	
	public static TextAlignment create(String alignment) {
		TextAlignment result;
		
		if ("center".equalsIgnoreCase(alignment))
			result = TextAlignment.ALIGNMENT_CENTER;
		else if ("left".equalsIgnoreCase(alignment))
			result = TextAlignment.ALIGNMENT_LEFT;
		else if ("right".equalsIgnoreCase(alignment))
			result = TextAlignment.ALIGNMENT_RIGHT;
		else
			result = TextAlignment.ALIGNMENT_LEFT;
		
		return result;		
	}
	
	
}
