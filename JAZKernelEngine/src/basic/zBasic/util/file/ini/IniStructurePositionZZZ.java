package basic.zBasic.util.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.IIniTagBasicZZZ;

public class IniStructurePositionZZZ implements IIniStructurePositionZZZ{
	private String sSection = null;
	private String sProperty = null;
	
	//Merke: Der Wert wird dann in einem anderen Opbject gespeichert.
	//       z.B. IKernelConfigSectionEntryZZZ
	
	public IniStructurePositionZZZ() throws ExceptionZZZ{
		
	}
	
	public IniStructurePositionZZZ(IIniTagBasicZZZ objTag) throws ExceptionZZZ{
		
		//OVERFLOW SCHLEIFENGEFAHR, wg. Ruekursion !!!
		//Wenn in dem objTag das IniStructurePositionZZZ-Objekt neu erstellt werde muss 
		//und dabei auch das Tag übergeben wird (mit "this");
		//Darum in diesem Tag beim Holen der Werte ueber as KernelConfigSectionEntry-Objekt
		//unbedingt immer auf objIniStructurePosition==null abprüfen und dann ggfs. auch null oder false zurückliefern.
		
		this.setSection(objTag.getSection());
		this.setProperty(objTag.getProperty());
	}
	
	public IniStructurePositionZZZ(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		
		//OVERFLOW SCHLEIFENGEFAHR, wg. Ruekursion !!!
				//Wenn in dem objTag das IniStructurePositionZZZ-Objekt neu erstellt werde muss 
				//und dabei auch das Tag übergeben wird (mit "this");
				//Darum in diesem Tag beim Holen der Werte ueber as KernelConfigSectionEntry-Objekt
				//unbedingt immer auf objIniStructurePosition==null abprüfen und dann ggfs. auch null oder false zurückliefern.
				
				this.setSection(objEntry.getSection());
				this.setProperty(objEntry.getProperty());
				
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
