package ru.skkdc.lis.conclusion.template.impl;

import javax.xml.bind.annotation.XmlValue;

import ru.skkdc.lis.conclusion.template.Element;



/**
 * <b>AbstractElement</b> нужен для JAXB, так как JAXB не умеет работать с интерфейсами 
 *
 */
abstract class AbstractElement implements Element {
    @XmlValue   protected String text;
}
