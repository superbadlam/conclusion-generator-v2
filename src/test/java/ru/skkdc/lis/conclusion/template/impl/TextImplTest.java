package ru.skkdc.lis.conclusion.template.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

import ru.skkdc.lis.conclusion.template.ParamFormatter;
import ru.skkdc.lis.conclusion.template.ParamFormatterType;
import ru.skkdc.lis.conclusion.template.ProcessContext;
import ru.skkdc.lis.conclusion.template.TextAlignment;
import ru.skkdc.lis.utils.expressionevaluators.ParamDouble;
import ru.skkdc.lis.utils.expressionevaluators.ParamString;
import ru.skkdc.lis.utils.expressionevaluators.ParamValue;

class TextImplTest {
    
    @Test
    void processNullText() {
    	String text=null;    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(5);
    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(1, lines.size());
    	assertEquals("     ", lines.get(0));
    }
	
    @Test
    void processEmptyText() {
    	String text="";    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(5);
    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(1, lines.size());
    	assertEquals("     ", lines.get(0));
    }
	
    @Test
    void processSimpleTextAndAlignmentLeft() {
    	String text="text";    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("left");
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);
    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(1, lines.size());
    	assertEquals("text      ", lines.get(0));
    }

    @Test
    void processMultilineText03() {
    	String text="te\r\nxt";    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);
    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(2, lines.size());
    	assertEquals("te        ", lines.get(0));
    	assertEquals("xt        ", lines.get(1));
    }
    
    @Test
    void processMultilineText02() {
    	String text="te\rxt";    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);
    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(2, lines.size());
    	assertEquals("te        ", lines.get(0));
    	assertEquals("xt        ", lines.get(1));
    }
    
    @Test
    void processMultilineText01() {
    	String text="te\nxt";    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);
    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(2, lines.size());
    	assertEquals("te        ", lines.get(0));
    	assertEquals("xt        ", lines.get(1));
    }
    
    @Test
    void processSimpleTextAndAlignmentRight() {
    	final String text="text";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("right");
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);

    	List<String> lines=t1.process(context);
    	assertEquals(1, lines.size());
    	assertEquals("      text", lines.get(0));
    }
    
    @Test
    void processSimpleTextAndAlignmentCenter01() {
    	final String text="text";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("center");
    	t1.setText(text);  
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);    	
    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(1, lines.size());
    	assertEquals("   text   ",lines.get(0));    	
    }
    @Test
    void processSimpleTextAndAlignmentCenter02() {
    	final String text="text1";    	
    	TextImpl t1 = new TextImpl();    	
    	t1.setAlignment("center");
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);
    	
    	List<String> lines=t1.process(context);
    	assertEquals(1, lines.size());
    	assertEquals("  text1   ", lines.get(0));    	
    }
    @Test
    void processWidthLessThenTextCenter() {
    	final String text="text";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("center");
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(3);
    	
    	List<String> lines=t1.process(context);
    	assertEquals(2, lines.size());
    	assertEquals("tex",lines.get(0));    	
    	assertEquals(" t ",lines.get(1));
    }
    @Test
    void processWidthLessThenTextLef() {
    	final String text="text";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("left");
    	t1.setText(text);
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(3);
    	
    	List<String> lines=t1.process(context);
    	assertEquals(2, lines.size());
    	assertEquals("tex",lines.get(0));    	
    	assertEquals("t  ",lines.get(1));
    }
    @Test
    void processWidthLessThenTextRight() {
    	final String text="text";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("right");
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(3);
    	
    	List<String> lines=t1.process(context);
    	assertEquals(2, lines.size());
    	assertEquals("tex",lines.get(0));    	
    	assertEquals("  t",lines.get(1));
    }    
    @Test
    void processWrongWidth() {
    	final String text="text";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("center");    	
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(0);
    	
    	assertThrows(IllegalArgumentException.class, ()->t1.process(context));
    }

    @Test
    void processMinimalWidth() {
    	final String text="text";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("center");    	
    	t1.setText(text);    	
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(1);
    	
    	List<String> lines=t1.process(context);
    	assertEquals(4, lines.size());
    	assertEquals("t",lines.get(0));    	
    	assertEquals("e",lines.get(1));
    	assertEquals("x",lines.get(2));
    	assertEquals("t",lines.get(3));
    	
    }
    
    @Test
    void processWithSubstitution01() {
    	
    	HashMap<String, ParamValue<?>> substMap=new HashMap<>();
    	substMap.put("Val", new ParamDouble(-0.001));
    	String text="[Val]";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("left");
    	t1.setText(text);
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(10);    	
    	context.setParams(substMap);
    	
    	List<String> lines=t1.process(context);
    	assertEquals(1, lines.size());
    	assertEquals("-0.001    ", lines.get(0));
    }
    
    @Test
    void processWithSubstitution02() {
    	
    	HashMap<String, ParamValue<?>> substMap=new HashMap<>();
    	substMap.put("Val", new ParamDouble(-0.001));
    	substMap.put("Units", new ParamString("tygrek"));
    	String text="[Val] [Units]";    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("left");
    	t1.setText(text);
    	
    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(15);    	
    	context.setParams(substMap);

    	
    	List<String> lines=t1.process(context);
    	
    	assertEquals(1, lines.size());
    	assertEquals("-0.001 tygrek  ", lines.get(0));
    }
    
    @Test
    void processWithSubstitutionWithFormatters01() {
    	
    	String valKey="val";
    	String lnormKey="lnorm";
    	String unormKey="unorm";
		String unitsKey="units";
		String flagKey="flag";
		String testnameKey="testname";
    	
    	Map<String, ParamFormatter> formatters=new HashMap<>();
		formatters.put(unitsKey, new ParamFormatterImpl(ParamFormatterType.UNITS, TextAlignment.ALIGNMENT_CENTER, 10));
		formatters.put(unormKey, new ParamFormatterImpl(ParamFormatterType.UNORM, TextAlignment.ALIGNMENT_LEFT, 10, 2));
		formatters.put(lnormKey, new ParamFormatterImpl(ParamFormatterType.LNORM, TextAlignment.ALIGNMENT_RIGHT, 10, 2));
		formatters.put(valKey, new ParamFormatterImpl(ParamFormatterType.VAL, TextAlignment.ALIGNMENT_LEFT, 10, 0));
		formatters.put(flagKey, new ParamFormatterImpl(ParamFormatterType.FLAG, TextAlignment.ALIGNMENT_LEFT, 10));
    	
    	HashMap<String, ParamValue<?>> substMap=new HashMap<>();
    	substMap.put(unormKey, new ParamDouble(0.001,ParamFormatterType.UNORM));
    	substMap.put(lnormKey, new ParamDouble(-0.008,ParamFormatterType.LNORM));
    	substMap.put(valKey, new ParamDouble(-0.01,ParamFormatterType.VAL));
    	substMap.put(unitsKey, new ParamString("tyg",ParamFormatterType.UNITS));
    	substMap.put(flagKey, new ParamString("!",ParamFormatterType.FLAG));    	
		substMap.put(testnameKey, new ParamString("test",ParamFormatterType.UNKNOWN));
    	String text="[testname][flag][val][units][lnorm]-[unorm]";
    	

    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(60);    	
    	context.setParams(substMap);    	
		context.setParamFormatters(formatters);
    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("left");
    	t1.setText(text);
    	List<String> lines=t1.process(context);
    	
    	assertEquals(1, lines.size());
                   //"123456789012345678901234567890123456789012345678901234567890"    	
    	           //"12341234567890123456789012345678901234567890 1234567890"
    	assertEquals("test!         0            tyg         -0.01-0.00           ", lines.get(0));
    }
    
    @Test
    void processWithSubstitutionWithFormatters02() {
    	
    	String valKey="val";
    	String lnormKey="lnorm";
    	String unormKey="unorm";
		String unitsKey="units";
		String flagKey="flag";
		String testnameKey="testname";
    	
    	Map<String, ParamFormatter> formatters=new HashMap<>();
		formatters.put(unitsKey, new ParamFormatterImpl(ParamFormatterType.UNITS, TextAlignment.ALIGNMENT_CENTER, 10));
		formatters.put(unormKey, new ParamFormatterImpl(ParamFormatterType.UNORM, TextAlignment.ALIGNMENT_LEFT, 10, 2));
		formatters.put(lnormKey, new ParamFormatterImpl(ParamFormatterType.LNORM, TextAlignment.ALIGNMENT_RIGHT, 10, 2));
		formatters.put(valKey, new ParamFormatterImpl(ParamFormatterType.VAL, TextAlignment.ALIGNMENT_LEFT, 10, 0));
		formatters.put(flagKey, new ParamFormatterImpl(ParamFormatterType.FLAG, TextAlignment.ALIGNMENT_LEFT, 10));
    	
    	HashMap<String, ParamValue<?>> substMap=new HashMap<>();
    	substMap.put(unormKey, new ParamDouble(0.001,ParamFormatterType.UNORM));
    	substMap.put(lnormKey, new ParamDouble(-0.008,ParamFormatterType.LNORM));
    	substMap.put(valKey, new ParamString("отрицательный",ParamFormatterType.VAL));
    	substMap.put(unitsKey, new ParamString("tyg",ParamFormatterType.UNITS));
    	substMap.put(flagKey,new ParamString( "!",ParamFormatterType.FLAG));    	
		substMap.put(testnameKey, new ParamString("test"));
    	String text="[testname][flag][val][units][lnorm]-[unorm]";
    	

    	ProcessContext context=new ProcessContextImpl(); 
    	context.setWidth(60);    	
    	context.setParams(substMap);    	
		context.setParamFormatters(formatters);
    	
    	
    	TextImpl t1 = new TextImpl();
    	t1.setAlignment("left");
    	t1.setText(text);
    	List<String> lines=t1.process(context);
    	
    	assertEquals(1, lines.size());
                   //"123456789012345678901234567890123456789012345678901234567890"    	
    	           //"12341234567890123456789012345678901234567890 1234567890"
    	assertEquals("test!         отрицатель   tyg         -0.01-0.00           ", lines.get(0));

    }
    
    @Test
    void restoreFromXMLAndProcess() {
        try {
        	HashMap<String, ParamValue<?>> substMap=new HashMap<>();
        	substMap.put("Val", new ParamDouble(-0.001));
        	substMap.put("Units",new ParamString(  "tygrek"));
            String xmlAsString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><Text alignment=\"center\">|  [Val] [Units]   |</Text>";

            JAXBContext jc = JAXBContext.newInstance(TextImpl.class);
            Unmarshaller um = jc.createUnmarshaller();
            TextImpl t1 = (TextImpl) um.unmarshal(new StringReader(xmlAsString));

        	ProcessContext context=new ProcessContextImpl(); 
        	context.setWidth(21);    	
        	context.setParams(substMap);

        	
        	List<String> lines=t1.process(context);
        	
        	assertEquals(1, lines.size());
        	assertEquals("|  -0.001 tygrek   | ", lines.get(0));
        } catch (Exception e) {
            fail(e);
        }
    }   
    
}
    