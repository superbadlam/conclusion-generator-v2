import ru.skkdc.lis.conclusion.labinfo.*;

LaboratoryInfo labInfo=p1;
static Double ves(LaboratoryInfo labInfo) {
	return labInfo.getPatientInfo().getVes();
}

static Integer berem(LaboratoryInfo labInfo) {	
	return labInfo.getPatientInfo().getBerem();
}

return (ves(p1)<100 && berem(p1)<35);