package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zBasic.util.file.JarEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterPathZipZZZ extends ObjectZZZ implements IFilenamePartFilterZipZZZ  {
	private String sDirectoryPath=null;
		
	public FilenamePartFilterPathZipZZZ() {
		super();
	}
	public FilenamePartFilterPathZipZZZ(String sDirectoryPath, String sFileName) throws ExceptionZZZ{
		super();
		this.setDirectoryPath(sDirectoryPath);		
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;				
				if(StringZZZ.isEmpty(this.getDirectoryPath())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
				
				//Pfad des Dateinamens berechnen			
				String sDirectoryPath;
//				try {				
					//String zurückgeben, einfach den Parent des Dateipfads. //Wichtig: Der abschliessende Slash dient dazu die Verzeichnispfade zu "normieren".
					sDirectoryPath = FileEasyZZZ.getParent(sName,"/")+"/";//Also: In der aktuell  betrachteten JAR - Datei sind die Pfade mit "SLASH" getrennt.
					if(StringZZZ.startsWithIgnoreCase(sDirectoryPath, this.getCriterion()))	{					
						bReturn = true;
						break main;
					}
//				} catch (ExceptionZZZ e) {			
//					e.printStackTrace();
//				} 					
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter
		public String getDirectoryPath(){
			return this.sDirectoryPath;
		}
		public void setDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ{			
			this.sDirectoryPath = FileCommonsForFilterZipZZZ.computeDirectoryPath(sDirectoryPathIn);
		}
		
		
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) throws ExceptionZZZ {	
		this.setDirectoryPath(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getDirectoryPath();
	}
	

}
