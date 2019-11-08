package ru.skkdc.lis.conclusion.template.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.template.TemplateDescription;
import ru.skkdc.lis.conclusion.template.TemplateManagerExceptions;
import ru.skkdc.lis.conclusion.template.TemplatesManager;

class FileTemplateFactoryTest {

	static File templateDir=null;

	@Test
	void findNullTest(){
		FileTemplatesManager templates=new FileTemplatesManager(templateDir);
		assertThrows(IllegalArgumentException.class, ()->templates.find(null));
	}
	
	@Test
	void findEmptyTest(){
		FileTemplatesManager templates=new FileTemplatesManager(templateDir);
		assertThrows(IllegalArgumentException.class, ()->templates.find(""));
	}
	@Test
	void findWhitespaceTest(){
		FileTemplatesManager templates=new FileTemplatesManager(templateDir);
		assertThrows(IllegalArgumentException.class, ()->templates.find("   "));
	}
	@Test
	void findNoXMLTest(){
		FileTemplatesManager templates=new FileTemplatesManager(templateDir);
		assertThrows(IllegalArgumentException.class, ()->templates.find("1.dat"));
	}
	
	@Test
	void findTemplateWhichWillNotBeFoundTest(){
		FileTemplatesManager templates=new FileTemplatesManager(templateDir);
		assertNull(templates.find("1.xml"));
	}
	@Test
	void findTemplateWhichWillBeFoundTest(){
		File f=createFile(templateDir.getAbsolutePath()+"/01.xml","01");		
		FileTemplatesManager templates=new FileTemplatesManager(templateDir);
		TemplateDescription definition = templates.find(f.getName());
		assertNotNull(definition);
		assertEquals(f.getName(), definition.getName());
		assertEquals(f.getAbsolutePath(), definition.getid());
	}
    @Test
    void getAvaliableListFromEmptyDirTest() {
    	File dir=new File("output/empty-dir/");
    	dir.mkdirs();
    	dir.deleteOnExit();
    	
    	FileTemplatesManager templates=new FileTemplatesManager(dir);
    	assertTrue(templates.getAvailable().isEmpty());
    }
    @Test
    void getAvailableListFromNoXMLDirTest() {
    	File dir=new File("output/noXMLContent");
    	dir.mkdirs();
    	dir.deleteOnExit();
    	
    	createFile(dir.getAbsolutePath()+"/01.txt","01");
    	createFile(dir.getAbsolutePath()+"/02.txt","02");
    	FileTemplatesManager templates=new FileTemplatesManager(dir);    	
    	assertTrue(templates.getAvailable().isEmpty());
        
    }
    @BeforeAll
    static void setupAll() {
    	templateDir=new File("output/templates/");
    	templateDir.mkdirs();
    	templateDir.deleteOnExit();
    }
    @Test
    void getAvailableListFromTemplateDirTest() {
  	
    	createFile("output/templates/01.xml","01");
    	createFile("output/templates/02.xml","02");
    	
    	FileTemplatesManager templates=new FileTemplatesManager(templateDir);        
        List<TemplateDescription> list = templates.getAvailable();
        
        File[] files = templateDir.listFiles((f, name) -> name.toLowerCase().endsWith(FileTemplatesManager.TEMPLATE_FILE_EXT));
        assertEquals(files.length,list.size());
    }
    @Test
    void getForEmptyFileTest() {    	
        final String xmlString="";
		File f = createFile("output/templates/test-empty-file.xml",xmlString);
		TemplatesManager templates = new FileTemplatesManager();
		assertThrows(TemplateManagerExceptions.class, ()->templates.get(new FileTemplateDescription(f)));
    }
    @Test
    void getForEmptyXMLTest() {    	
        final String xmlString=
    			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		File f = createFile(templateDir.getAbsolutePath()+"/test-empty-xml.xml",xmlString);
		TemplatesManager templates = new FileTemplatesManager();		
		assertThrows(TemplateManagerExceptions.class, ()->templates.get(new FileTemplateDescription(f)));
    }
    @Test
    void getForEmptyTemplateTest() {    	
        final String xmlString=
    			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + 
    			"<Template version=\"1.1\" name=\"test-template\" width=\"60\"/>\r\n" ;
		File f = createFile(templateDir.getAbsolutePath()+"/test-empty-template.xml",xmlString);
		TemplatesManager templates = new FileTemplatesManager();
		assertThrows(TemplateManagerExceptions.class, ()->templates.get(new FileTemplateDescription(f)));
    }
    @Test
    void getForValidTemplateTest() {    	
        final String xmlString=
    			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Template version=\"1.1\" name=\"test-template\" width=\"60\"><Text alignment=\"center\">Header-text-01</Text><ConditionalBlock condition=\"condition-01\"><Text alignment=\"center\">condition-text-01</Text><Text>condition-text-02</Text></ConditionalBlock><Text alignment=\"left\">Tail-text-01</Text></Template>";
		File f = createFile(templateDir.getAbsolutePath()+"/valid-template.xml",xmlString);		
		try {
			TemplatesManager templates = new FileTemplatesManager();		
			templates.get(new FileTemplateDescription(f));
		} catch (Exception e) {
			fail(e);
		}		
    }
	private File createFile(String name, String content) {		
		File f = new File(name);
		try(FileOutputStream os=new FileOutputStream(f)){
			os.write(content.getBytes());
		}catch (Exception e) {
			fail(e);
		}
		f.deleteOnExit();
		return f;
	}
}