package ru.skkdc.lis.conclusion.template.impl;

import java.io.File;

import ru.skkdc.lis.conclusion.template.TemplateDescription;

class FileTemplateDescription implements TemplateDescription{

	private File file;

	public FileTemplateDescription(File file) {
		super();
		this.file = file;
	}

	@Override
	public String getid() {
		return file.getAbsolutePath();
	}

	@Override
	public String getName() {
		return file.getName();
	}

	
}
