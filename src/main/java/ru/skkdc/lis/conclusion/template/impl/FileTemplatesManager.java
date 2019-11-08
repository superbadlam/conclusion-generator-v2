package ru.skkdc.lis.conclusion.template.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import lombok.extern.slf4j.Slf4j;
import ru.skkdc.lis.conclusion.template.Template;
import ru.skkdc.lis.conclusion.template.TemplateDescription;
import ru.skkdc.lis.conclusion.template.TemplateManagerExceptions;
import ru.skkdc.lis.conclusion.template.TemplatesManager;

@Slf4j
public class FileTemplatesManager implements TemplatesManager {
	public static final URL SCHEMA_FILE = getSchemaFile();
	private static final Class<?>[] JAXB_CLASSES = { TemplateImpl.class, LabeledBlockImpl.class, ConditionalBlockImpl.class, TextImpl.class };
	public static final String TEMPLATE_FILE_EXT = "xml";
	
	private static URL getSchemaFile() {
        ClassLoader classLoader = FileTemplatesManager.class.getClassLoader();
        URL resource = classLoader.getResource("schemas/schema.xsd");
        if (resource == null) 
            throw new IllegalArgumentException("file is not found!");
		else
			return resource;
	}
	
	private File directory;

	public FileTemplatesManager setDirectory(File directory) {
		this.directory = directory;
		return this;
	}

	public FileTemplatesManager(File directory) {
		this.directory = directory;
	}

	public FileTemplatesManager() {
		super();
	}

	@Override
	public List<TemplateDescription> getAvailable() {
		log.trace("проверим содержимое директории: directory={}", directory.getAbsolutePath());

		File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(TEMPLATE_FILE_EXT));
		List<TemplateDescription> definitions = new ArrayList<>();

		if (files == null || files.length==0) {
			log.warn("директория не содержит ни одного шаблона: directory={}", directory.getAbsolutePath());
		} else {
			for (File file : files) {
				log.trace("найден файл шаблона: file={}", file.getAbsolutePath());
				definitions.add(new FileTemplateDescription(file));
			}
		}

		return definitions;
	}
	
	@Override
	public TemplateDescription find(String templateName) {
		log.trace("проверим содержимое директории: directory={}", directory.getAbsolutePath());
		String requiredEnd="."+TEMPLATE_FILE_EXT;
		
		if(templateName==null)
			throw new IllegalArgumentException("templateName не может быть null");
		
		if(templateName.isEmpty())
			throw new IllegalArgumentException("templateName не может быть пустым"); 
		
		if(!templateName.toLowerCase().endsWith(requiredEnd))
			throw new IllegalArgumentException("templateName должен иметь расширение '"+requiredEnd+"'");

		File[] files = directory.listFiles((dir, name) -> name.equalsIgnoreCase(templateName));
		TemplateDescription definitions = null;

		if (files == null || files.length==0) {
			log.warn("директория не содержит ни одного шаблона c заданым именем: directory={}, templateName={}", directory.getAbsolutePath(),templateName);
			definitions = null;
		} else {
			log.trace("найден файл шаблона: file={}", files[0].getAbsolutePath());
			definitions = new FileTemplateDescription(files[0]);
		}

		return definitions;
	}
	

	@Override
	public Template get(TemplateDescription templateDefinition) {
		File f = new File(templateDefinition.getid());
		if (!f.exists()) {
			log.warn("по идентификатору не удалось найти ни одного шаблона: template-id={}",
					templateDefinition.getid());
			throw new TemplateManagerExceptions(
					String.format("по идентификатору не удалось найти ни одного шаблона: template-id=%s",
							templateDefinition.getid()));
		}

		JAXBContext jc = createJAXBContext();
		Template template = create(f, jc);
		validateTemplate(template, jc);
		if(template.isEmpty()) {
			log.warn("шаблон не содержит ни одного элемента: template-id={}",
					templateDefinition.getid());
			throw new TemplateManagerExceptions(
					String.format("шаблон не содержит ни одного элемента: template-id=%s",
							templateDefinition.getid()));
		}

		return template;
	}

	private JAXBContext createJAXBContext() {
		JAXBContext jc = null;
		try {
			jc = JAXBContext.newInstance(JAXB_CLASSES);
		} catch (Exception e) {
			throw new TemplateManagerExceptions("не удалось создать JAXB-контекст", e);
		}
		return jc;
	}

	private Template create(File f, JAXBContext jc) {
		log.trace("произведем маршалинг шаблона из файла: file={}", f.getAbsolutePath());
		try {
			Template template = null;
			Unmarshaller m = jc.createUnmarshaller();
			template = (Template) m.unmarshal(f);
			return template;
		} catch (Exception e) {
			throw new TemplateManagerExceptions("не удалось загрузить файл шаблона: file-path=" + f.getAbsolutePath(),
					e);
		}

	}

	private void validateTemplate(Template template, JAXBContext jc) {
		log.trace("произведем валидацию шаблона по схеме: template-name=={}, schema={}", template.getName(),
				SCHEMA_FILE);
		try {
			JAXBSource source = new JAXBSource(jc, template);

			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(SCHEMA_FILE);

			Validator validator = schema.newValidator();
			validator.validate(source);
		} catch (Exception e) {
			throw new TemplateManagerExceptions("не удалось проверить шаблон по схеме: template-name="
					+ template.getName() + ": schema=" + SCHEMA_FILE, e);
		}
	}

}
