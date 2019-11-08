package ru.skkdc.lis.conclusion.template.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.template.ProcessContext;
import ru.skkdc.lis.utils.expressionevaluators.ExpressionEvaluatorException;

class ConditionalBlockImplTest {

	private ProcessContext createContext(int width) {
		ProcessContext context=new ProcessContextImpl();
		context.setWidth(width);
		context.setLaboratoryInfo(new LaboratoryInfoTestImpl());
		return context;
	}
	
	private ConditionalBlockImpl createConditionalBlock(String xmlAsString) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(ConditionalBlockImpl.class, TextImpl.class);
		Unmarshaller um = jc.createUnmarshaller();
		return (ConditionalBlockImpl) um.unmarshal(new StringReader(xmlAsString));
	}
	
    @Test
    void testProcessContextWithoutLaboratoryInfos() {
        try {

        	String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<ConditionalBlock condition='condition'>\n" + 
        			"	<Text>some content</Text>\n" + 
        			"</ConditionalBlock>";

        	ConditionalBlockImpl t1 = createConditionalBlock(xmlAsString);

            assertEquals("condition", t1.getCondition());
            assertEquals(1,t1.getElementList().size());
        	assertThrows(NullPointerException.class, ()->t1.process(new ProcessContextImpl()));
        	
        } catch (Exception e) {
            fail(e);
        }
    }
	
    @Test
    void testProcessEmptyConditionalBlock() {
        try {

        	String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
            		"<ConditionalBlock condition='condition-01'/>";

        	ConditionalBlockImpl t1 = createConditionalBlock(xmlAsString);

            assertEquals("condition-01", t1.getCondition());
            assertNull(t1.getElementList());        	
			assertTrue(t1.process(createContext(20)).isEmpty());
        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessTrueCondition() {
        try {

        	String textContent="some content";
        	String condition="label01_val>=0 &amp;&amp; label02_val>=0 &amp;&amp; label03_val>=0";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<ConditionalBlock condition='"+condition+"'>\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</ConditionalBlock>";

			ConditionalBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals("label01_val>=0 && label02_val>=0 && label03_val>=0", t1.getCondition());        
        	assertEquals(1, t1.getElementList().size());
        	        	
			List<String> lines=t1.process(createContext(textContent.length()));
			
			assertEquals(1, lines.size());			
			assertEquals(textContent, lines.get(0));			
			        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessTrueCondition02() {
        try {

        	String textContent="some content";
        	String condition="label01_val>=0 &amp;&amp; label02_val>=0 &amp;&amp; label03_val>=0";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<ConditionalBlock condition='"+condition+"'>\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</ConditionalBlock>";

			ConditionalBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals("label01_val>=0 && label02_val>=0 && label03_val>=0", t1.getCondition());        
        	assertEquals(1, t1.getElementList().size());
        	        	
			List<String> lines=t1.process(createContext(textContent.length()));
			
			assertEquals(1, lines.size());			
			assertEquals(textContent, lines.get(0));			
			        	
        } catch (Exception e) {
            fail(e);
        }
    }
    @Test
    void testProcessFalseCondition() {
        try {

        	String textContent="some content";
        	String condition="label01_val&lt;0 || label02_val&lt;0 || label03_val&lt;0";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<ConditionalBlock condition='"+condition+"'>\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</ConditionalBlock>";

			ConditionalBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals("label01_val<0 || label02_val<0 || label03_val<0", t1.getCondition());        
        	assertEquals(1, t1.getElementList().size());
        	        	
			List<String> lines=t1.process(createContext(textContent.length()));			
			assertEquals(0, lines.size());						
			        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessMisprintingForCondition() {
        try {

        	String textContent="some content";
        	String condition="ladel01_val&lt;0";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<ConditionalBlock condition='"+condition+"'>\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</ConditionalBlock>";

			ConditionalBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals("ladel01_val<0", t1.getCondition());        
        	assertEquals(1, t1.getElementList().size());
        	        	
        	assertThrows(ExpressionEvaluatorException.class, ()->t1.process(createContext(textContent.length())));					
			        	
        } catch (Exception e) {
            fail(e);
        }
    }
    
    @Test
    void testProcessSyntaxErrorForCondition() {
        try {

        	String textContent="some content";
        	String condition="label01_val+&quot;qwerty&quot;&lt;0";
			String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + 
        			"<ConditionalBlock condition='"+condition+"'>\n" + 
        			"	<Text>"+textContent+"</Text>\n" + 
        			"</ConditionalBlock>";

			ConditionalBlockImpl t1 = createConditionalBlock(xmlAsString);
            
            assertEquals("label01_val+\"qwerty\"<0", t1.getCondition());        
        	assertEquals(1, t1.getElementList().size());
        	        	
			assertThrows(ExpressionEvaluatorException.class, ()->t1.process(createContext(textContent.length())));			
									
			        	
        } catch (Exception e) {
            fail(e);
        }
    }
}
