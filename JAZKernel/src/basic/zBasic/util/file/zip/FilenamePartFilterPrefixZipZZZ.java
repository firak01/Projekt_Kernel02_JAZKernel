package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterPrefixZipZZZ extends ObjectZZZ implements IFilenamePartFilterZipZZZ  {
	private String sFilePrefix;
	
	public FilenamePartFilterPrefixZipZZZ() {
		super();
	}
	public FilenamePartFilterPrefixZipZZZ(String sFilePrefix){
		super();
		this.setPrefix(sFilePrefix);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;				
				if(StringZZZ.isEmpty(this.getPrefix())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
				
				//Anfang des Dateinamens berechnen			
				String sPrefixCur;
				try {				
					sPrefixCur = FileEasyZZZ.getNameOnly(sName);
					if(StringZZZ.startsWithIgnoreCase(sPrefixCur, this.getPrefix())) bReturn = true;
				} catch (ExceptionZZZ e) {			
					e.printStackTrace();
				} 					
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter
	public String getPrefix(){
		return this.sFilePrefix;
	}
	public void setPrefix(String sPrefix){
		this.sFilePrefix = sPrefix;
	}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setPrefix(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getPrefix();
	}
	

}
