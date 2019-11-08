package ru.skkdc.lis.conclusion.template;

import java.util.List;

public interface TemplatesManager {
    Template get(TemplateDescription templateDefinition);
	List<TemplateDescription> getAvailable();	
	TemplateDescription find(String templateName);
}
