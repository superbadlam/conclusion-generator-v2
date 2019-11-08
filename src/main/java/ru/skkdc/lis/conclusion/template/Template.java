package ru.skkdc.lis.conclusion.template;

import java.util.function.Consumer;

import ru.skkdc.lis.conclusion.labinfo.LaboratoryInfo;

public interface Template {
    String getVersion();
    String getName();
    int getWidth();
    boolean isEmpty();
	String buildConclusion(LaboratoryInfo labContext, Consumer<Exception> warningsConsumer);
}
