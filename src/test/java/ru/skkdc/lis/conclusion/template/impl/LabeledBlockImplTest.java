package ru.skkdc.lis.conclusion.template.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.template.ProcessContext;

class LabeledBlockImplTest {

    @Test
    void testProcessWhereContextWithoutLaboratoryInfos() {
        try {

        	String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
            		"<LabeledBlock label=\"label-01\"/>";

        	LabeledBlockImpl t1 = createConditionalBlock(xmlAsString);

            assertEquals("label-01", t1.getLabel());
            assertNull(t1.getElementList());
        	assertThrows(NullPointerException.class, ()->t1.process(new ProcessContextImpl()));
        	
        } catch (Exception e) {
            fail(e);
        }
    }
	
    @Test
    void testProcessEmptyConditionalBlock() {
        try {

        	String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
            		"<LabeledBlock label=\"label-01\"/>";

        	LabeledBlockImpl t1 = createConditionalBlock(xmlAsString);

            assertEquals("label-01", t1.getLabel());
            assertNull(t1.getElementList());
        	
			assertEquals(true, t1.process(createContext(20)).isEmpty());
        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessWithRightLabel() {
        try {

        	String textContent="some content";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<LabeledBlock label=\""+LaboratoryInfoTestImpl.LABEL01+"\">\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</LabeledBlock>";

			LabeledBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals(LaboratoryInfoTestImpl.LABEL01, t1.getLabel());        
        	assertEquals(1, t1.getElementList().size());
        	        	
			List<String> lines=t1.process(createContext(textContent.length()));
			
			assertEquals(1, lines.size());
			assertEquals(textContent, lines.get(0));			
			
        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessWithWrongLabel() {
        try {

        	String textContent="some content";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<LabeledBlock label=\""+"wrong_label"+"\">\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</LabeledBlock>";

			LabeledBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals("wrong_label", t1.getLabel());        
        	assertEquals(1, t1.getElementList().size());        	       		
			assertEquals(true, t1.process(createContext(textContent.length())).isEmpty());			
			        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessWithSingleLongText() {
        try {

        	String textContent="some long content";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<LabeledBlock label=\""+LaboratoryInfoTestImpl.LABEL01+"\">\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</LabeledBlock>";

            LabeledBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals(LaboratoryInfoTestImpl.LABEL01, t1.getLabel());        
        	assertEquals(1, t1.getElementList().size());
        	        	
			List<String> lines=t1.process(createContext(textContent.length()-3));
			
			assertEquals(2, lines.size());
			assertEquals("some long cont", lines.get(0));
			assertEquals("ent           ", lines.get(1));
			
        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessWithMultipleText() {
        try {

        	String textContent01="some content";
        	String textContent02="another content";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
					"<LabeledBlock label=\""+LaboratoryInfoTestImpl.LABEL01+"\">\n" + 
        			"	<Text alignment=\"right\">"+textContent01+"</Text>\n" + 
        			"	<Text alignment=\"center\">"+textContent02+"</Text>\n" +        			
        			"</LabeledBlock>";

            LabeledBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals(LaboratoryInfoTestImpl.LABEL01, t1.getLabel());        
        	assertEquals(2, t1.getElementList().size());
        	        	
			List<String> lines=t1.process(createContext(20));
			
			assertEquals(2, lines.size());
			assertEquals("        some content", lines.get(0));
			assertEquals("  another content   ", lines.get(1));			
        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
	private ProcessContext createContext(int width) {
		ProcessContext context=new ProcessContextImpl();
		context.setWidth(width);
		context.setLaboratoryInfo(new LaboratoryInfoTestImpl());
		return context;
	}

	private LabeledBlockImpl createConditionalBlock(String xmlAsString) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(LabeledBlockImpl.class, TextImpl.class);
		Unmarshaller um = jc.createUnmarshaller();
		return (LabeledBlockImpl) um.unmarshal(new StringReader(xmlAsString));
	}

}
