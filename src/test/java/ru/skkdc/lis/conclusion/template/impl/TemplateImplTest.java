package ru.skkdc.lis.conclusion.template.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.template.LabeledBlock;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;
import ru.skkdc.lis.conclusion.template.Template;
import ru.skkdc.lis.conclusion.template.Text;
import ru.skkdc.lis.conclusion.template.TextAlignment;


class TemplateImplTest {

	private Class<?>[] jaxbClasses = { TemplateImpl.class, LabeledBlockImpl.class, ConditionalBlockImpl.class ,TextImpl.class, ParamFormatterImpl.class };
	
	@Test
	void schemaValidationTest01() {
		
		TemplateImpl template = generateSimpleTemplate();
		
		List<ParamFormatterImpl> formatters=new ArrayList<>();
		formatters.add(new ParamFormatterImpl(ParamFormatterType.TESTNAME, TextAlignment.ALIGNMENT_CENTER, 10));
		formatters.add(new ParamFormatterImpl(ParamFormatterType.VAL));
		formatters.add(new ParamFormatterImpl(ParamFormatterType.LNORM, TextAlignment.ALIGNMENT_LEFT));
		formatters.add(new ParamFormatterImpl(ParamFormatterType.UNORM, 10));
		
		template.setFormatters(formatters);
		

		try {
			JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
			JAXBSource source = new JAXBSource(jc, template);

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(FileTemplatesManager.SCHEMA_FILE);

			Validator validator = schema.newValidator();
			validator.validate(source);
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	void schemaValidationTest02() {
		
		TemplateImpl template = generateSimpleTemplate();

		try {
			JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
			JAXBSource source = new JAXBSource(jc, template);

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(FileTemplatesManager.SCHEMA_FILE);

			Validator validator = schema.newValidator();
			validator.validate(source);
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	void schemaValidationUnknownFormatter() {

		TemplateImpl template = generateSimpleTemplate();
		
		List<ParamFormatterImpl> formatters=new ArrayList<>();
		formatters.add(new ParamFormatterImpl(ParamFormatterType.UNKNOWN, TextAlignment.ALIGNMENT_CENTER, 10));
		template.setFormatters(formatters);

		try {
			JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
			JAXBSource source = new JAXBSource(jc, template);

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(FileTemplatesManager.SCHEMA_FILE);

			Validator validator = schema.newValidator();
			assertThrows(org.xml.sax.SAXParseException.class, ()->validator.validate(source));
			
		} catch (Exception e) {
			fail(e);
		}
	}	
	


	@Test
	void schemaValidationFormatterWrongWidth() {

		TemplateImpl template = generateSimpleTemplate();
		
		List<ParamFormatterImpl> formatters=new ArrayList<>();
		formatters.add(new ParamFormatterImpl(ParamFormatterType.VAL, TextAlignment.ALIGNMENT_LEFT, 0));
		template.setFormatters(formatters);

		try {
			JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
			JAXBSource source = new JAXBSource(jc, template);

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(FileTemplatesManager.SCHEMA_FILE);

			Validator validator = schema.newValidator();
			assertThrows(org.xml.sax.SAXParseException.class, ()->validator.validate(source));
			
		} catch (Exception e) {
			fail(e);
		}
	}	
	@Test
	void unmarshallingTest() {
		
		
        try {
        	int width=60;
        	String xmlAsString = 
        	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        	"<Template version=\"1.1\" name=\"test-template\" width=\""+width+"\">\n" + 
        	"	<ParamFormatter paramtype=\"testname\" alignment=\"center\" width=\"10\"/>\n" + 
        	"	<ParamFormatter paramtype=\"val\" width=\"10\" precision=\"-1\"/>\n" + 
        	"	<ParamFormatter paramtype=\"lnorm\" alignment=\"right\" width=\"10\" precision=\"2\"/>\n" + 
        	"	<ParamFormatter paramtype=\"unorm\" width=\"10\" precision=\"3\"/>\n" + 
        	"	<ParamFormatter paramtype=\"units\" width=\"10\"/>\n" +  
        	"<Text alignment=\"left\">Наименование       значение  ед.изм.           норма</Text>\n" + 
        	"	<LabeledBlock label=\""+"label01"+"\">\n" + 
        	"<Text alignment=\"center\">123456789012345678901234567890123456789012345678901234567890</Text>\n" + 
        	"<Text alignment=\"left\">[testname]         [val][units][lnorm]-[unorm]</Text>\n" + 
        	"	</LabeledBlock>\n" + 
        	"	<Text alignment=\"left\">Tail-text-01</Text>\n" + 
        	"</Template>";

			JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
			Unmarshaller m = jc.createUnmarshaller();
			TemplateImpl template = (TemplateImpl) m.unmarshal(new StringReader(xmlAsString));            
        	assertEquals(3, template.getElementList().size());
        	
        	assertDoesNotThrow(()->(Text)template.getElementList().get(0));
        	assertDoesNotThrow(()->(Text)template.getElementList().get(2));
        	assertDoesNotThrow(()->(LabeledBlock)template.getElementList().get(1));

        	LabeledBlockImpl c1=(LabeledBlockImpl) template.getElementList().get(1);
        	assertEquals(2, c1.getElementList().size());
        	assertDoesNotThrow(()->(Text)c1.getElementList().get(0));
        	assertDoesNotThrow(()->(Text)c1.getElementList().get(1));
        	
        	String text=template.buildConclusion(new LaboratoryInfoTestImpl(),e->System.out.println(e.getMessage()));        	
        	System.out.println(text);
        	
        } catch (Exception e) {
            fail(e);
        }		
	}

	@Test
	void unmarshallingTest02() {
		
		
        try {
        	String xmlAsString = 
        	"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" + 
        	"<Template version=\"1.1\" name=\"test-template\" width=\"70\">\r\n" + 
        	"	<ParamFormatter paramtype=\"testname\" alignment=\"center\" width=\"10\"/>\r\n" + 
        	"	<ParamFormatter paramtype=\"val\" width=\"10\" precision=\"-1\"/>\r\n" + 
        	"	<ParamFormatter paramtype=\"lnorm\" alignment=\"right\" width=\"10\" precision=\"2\"/>\r\n" + 
        	"	<ParamFormatter paramtype=\"unorm\" width=\"10\" precision=\"3\"/>\r\n" + 
        	"	<ParamFormatter paramtype=\"units\" width=\"10\"/>\r\n" + 
        	"<!--                                  1         2         3         4         5         6\r\n" + 
        	"	                         123456789012345678901234567890123456789012345678901234567890 -->\r\n" + 
        	"	<Text alignment=\"left\">Наименование       значение  ед.изм.           норма</Text>\r\n" + 
        	"	<LabeledBlock label='label01'>\r\n" + 
        	"		<Text alignment=\"center\">some description for label01</Text>\r\n" + 
        	"		<Text alignment=\"left\">[testname]          [val][units][lnorm]-[unorm]</Text>\r\n" + 
        	"	</LabeledBlock>\r\n" + 
        	"	<LabeledBlock label='label02'>\r\n" + 
        	"		<Text alignment=\"center\">some description for label02</Text>\r\n" + 
        	"		<Text alignment=\"left\">[testname]          [val][units][lnorm]-[unorm]</Text>\r\n" + 
        	"	</LabeledBlock>	\r\n" + 
        	"	<Text alignment=\"left\">Tail-text-01</Text>\r\n" + 
        	"	<Text/>\r\n" + 
        	"	<Text/>\r\n" + 
        	"	<Text/>\r\n" + 
        	"	<Text alignment=\"center\">ЗАКЛЮЧЕНИЕ И КОММЕНТАРИИ</Text>\r\n" + 
        	"	<ConditionalBlock condition='label01_val&gt;=label01_lnorm &amp;&amp; label01_val&lt;=label01_unorm'>\r\n" + 
        	"		<Text alignment=\"left\">[label01_testname] в норме</Text>\r\n" + 
        	"	</ConditionalBlock>\r\n" + 
        	"	<ConditionalBlock condition='label01_val&lt;label01_lnorm || label01_val&gt;=label01_unorm'>\r\n" + 
        	"		<Text alignment=\"left\">[label01_testname] вне нормы</Text>\r\n" + 
        	"	</ConditionalBlock>\r\n" + 
        	"	<ConditionalBlock condition='label02_val&gt;=label02_lnorm &amp;&amp; label02_val&lt;=label02_unorm'>\r\n" + 
        	"		<Text alignment=\"left\">[label02_testname] в норме</Text>\r\n" + 
        	"	</ConditionalBlock>\r\n" + 
        	"	<ConditionalBlock condition='label02_val&lt;label02_lnorm || label02_val&gt;=label02_unorm'>\r\n" + 
        	"		<Text alignment=\"left\">[label02_testname] вне нормы</Text>\r\n" + 
        	"	</ConditionalBlock>	\r\n" + 
        	"	<ConditionalBlock condition='label04_val&gt;=label04_lnorm &amp;&amp; label04_val&lt;=label04_unorm'>\r\n" + 
        	"		<Text alignment=\"left\">[label04_testname] в норме</Text>\r\n" + 
        	"	</ConditionalBlock>\r\n" + 
        	"	<ConditionalBlock condition='label04_val&lt;label04_lnorm || label04_val&gt;=label04_unorm'>\r\n" + 
        	"		<Text alignment=\"left\">[label04_testname] вне нормы</Text>\r\n" + 
        	"	</ConditionalBlock>	\r\n" + 
        	"\r\n" + 
        	"</Template>";
        	
        	List<Exception> warnings=new ArrayList<>();

			JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
			Unmarshaller m = jc.createUnmarshaller();
			Template template = (Template) m.unmarshal(new StringReader(xmlAsString));            
        	String text=template.buildConclusion(new LaboratoryInfoTestImpl(),e->warnings.add(e));
        	System.out.println(text);
        	
        	assertEquals(2, warnings.size());
        	assertEquals("не удалось вычислить выражение: expr='label04_val>=label04_lnorm && label04_val<=label04_unorm', cause=Line 1, Column 1: Expression \"label04_val\" is not an rvalue", warnings.get(0).getMessage());
        	assertEquals("не удалось вычислить выражение: expr='label04_val<label04_lnorm || label04_val>=label04_unorm', cause=Line 1, Column 1: Expression \"label04_val\" is not an rvalue", warnings.get(1).getMessage());
        	
        	
        } catch (Exception e) {
            fail(e);
        }		
	}
	
	@Test
	void marshallingTest() {

		String xmlString 
		    = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Template version=\"1.1\" name=\"test-template\" width=\"60\"><Text alignment=\"center\">Header-text-01</Text><LabeledBlock label=\"condition-01\"><Text alignment=\"center\">condition-text-01</Text><Text alignment=\"left\">condition-text-02</Text></LabeledBlock><Text alignment=\"left\">Tail-text-01</Text></Template>";

		try {

			TemplateImpl template = generateSimpleTemplate();
			JAXBContext jc = JAXBContext.newInstance(jaxbClasses);
			Marshaller m = jc.createMarshaller();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			m.marshal(template, outputStream);
			String xmlAsString = new String(outputStream.toByteArray());

			assertEquals(xmlString, xmlAsString);

		} catch (Exception e) {
			fail(e);
		}
	}

	private TemplateImpl generateSimpleTemplate() {
		List<AbstractElement> upperElements = new ArrayList<>();

		List<AbstractElement> condElements = new ArrayList<>();
		TextImpl condText1 = new TextImpl();
		condText1.setAlignment("center");
		condText1.setText("condition-text-01");
		condElements.add(condText1);
		TextImpl condText2 = new TextImpl();
		condText2.setText("condition-text-02");
		condElements.add(condText2);

		TextImpl headerText = new TextImpl();
		headerText.setAlignment("center");
		headerText.setText("Header-text-01");
		upperElements.add(headerText);

		LabeledBlockImpl conditionalBlock = new LabeledBlockImpl();
		conditionalBlock.setLabel("condition-01");
		conditionalBlock.setElementList(condElements);
		upperElements.add(conditionalBlock);

		TextImpl tailText = new TextImpl();
		tailText.setAlignment("left");
		tailText.setText("Tail-text-01");
		upperElements.add(tailText);

		TemplateImpl template = new TemplateImpl();
		template.setName("test-template");
		template.setVersion("1.1");
		template.setWidth(60);
		template.setElementList(upperElements);
		return template;
	}
}