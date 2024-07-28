package basic.zBasic.util.file.ini;

import basic.zBasic.ExceptionZZZ;

public class IniStructurePositionZZZ implements IIniStructurePositionZZZ{
	private String sSection = null;
	private String sProperty = null;
	
	//Merke: Der Wert wird dann in einem anderen Opbject gespeichert.
	//       z.B. IKernelConfigSectionEntryZZZ
	
	public IniStructurePositionZZZ() throws ExceptionZZZ{
		
	}
	
	public IniStructurePositionZZZ(String sSection, String sProperty)throws ExceptionZZZ{
		this.setSection(sSection);
		this.setProperty(sProperty);
	}

	//### Aus IIniStructurePositionZZZ
	@Override
	public String getSection() throws ExceptionZZZ {
		return this.sSection;
	}

	@Override
	public void setSection(String sSection) throws ExceptionZZZ {
		this.sSection = sSection;
	}

	@Override
	public String getProperty() throws ExceptionZZZ {
		return this.sProperty;
	}

	@Override
	public void setProperty(String sProperty) throws ExceptionZZZ {
		this.sProperty = sProperty;
	}
	
	
}
