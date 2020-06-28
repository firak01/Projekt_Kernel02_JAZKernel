package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FileFilterPathZipZZZ extends ObjectZZZ implements IFilenameFilterZipZZZ  {
	private String sDirectoryPath;
	
	public FileFilterPathZipZZZ() {
		super();
	}
	public FileFilterPathZipZZZ(String sDirectoryPath){
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
				try {				
					TODOGOON; //String zurückgeben, einfach den Parent des Dateipfads: FileEasyZZZ.getParent(sName);
					sDirectoryPath = FileEasyZZZ.getFileUsedPath(sName);
					if(StringZZZ.startsWithIgnoreCase(sName, this.getDirectoryPath())) bReturn = true;
				} catch (ExceptionZZZ e) {			
					e.printStackTrace();
				} 					
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter
		public String getDirectoryPath(){
			return this.sDirectoryPath;
		}
		public void setDirectoryPath(String sDirectoryPath){
			this.sDirectoryPath = sDirectoryPath;
		}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setDirectoryPath(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getDirectoryPath();
	}
	

}
