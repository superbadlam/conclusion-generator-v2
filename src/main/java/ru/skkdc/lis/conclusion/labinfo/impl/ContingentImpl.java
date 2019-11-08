package ru.skkdc.lis.conclusion.labinfo.impl;

import lombok.EqualsAndHashCode;
import ru.skkdc.lis.conclusion.labinfo.Contingent;

@EqualsAndHashCode
public final class ContingentImpl implements Contingent {

	private final String name;
	private final long code;
	
	public ContingentImpl(String name, long code) {
		super();
		this.name = name;
		this.code = code;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getCode() {
		return code;
	}

}
