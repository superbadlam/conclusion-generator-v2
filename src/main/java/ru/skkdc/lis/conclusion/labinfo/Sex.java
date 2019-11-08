package ru.skkdc.lis.conclusion.labinfo;

public enum Sex implements PairDetail {
	UNDEF(-1,"не указан"),MALE(0,"муж."),FEMALE(1,"жен.");
	
	private int code=-1;
	private String name;

	
	
	private Sex(int code, String name) {
		this.code = code;
		this.name = name;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public long getCode() {
		return code;
	}

	public static Sex get(int sexCode) {
		switch (sexCode) {
		case -1: return UNDEF;
		case 0:	 return MALE;			
		case 1:  return FEMALE;
		default: throw new IllegalArgumentException();		
		}
	}
}
