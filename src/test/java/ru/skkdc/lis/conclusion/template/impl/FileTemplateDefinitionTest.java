package ru.skkdc.lis.conclusion.template.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;


class FileTemplateDefinitionTest {

	@Test
	void testGetid() {
		File file=new File("1.txt");
		FileTemplateDescription d=new FileTemplateDescription(file);
		assertEquals(file.getAbsolutePath(), d.getid());
		assertEquals(file.getName(), d.getName());
	}
	
}
