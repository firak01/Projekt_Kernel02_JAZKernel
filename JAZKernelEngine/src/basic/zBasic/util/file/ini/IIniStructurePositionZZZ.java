package basic.zBasic.util.file.ini;

import basic.zBasic.ExceptionZZZ;

public interface IIniStructurePositionZZZ {
	public String getSection() throws ExceptionZZZ;
	public void setSection(String sSection) throws ExceptionZZZ;
	
	public String getProperty()  throws ExceptionZZZ;
	public void setProperty(String sProperty) throws ExceptionZZZ;
}
