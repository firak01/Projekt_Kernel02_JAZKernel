package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterEndingZipZZZ extends AbstractFilenamePartFilterZipZZZ implements IFilenamePartFilterZipZZZ  {
	private String sFileEnding;
	
	public FilenamePartFilterEndingZipZZZ() {
		super();
	}
	public FilenamePartFilterEndingZipZZZ(String sFileEnding){
		super();
		this.setEnding(sFileEnding);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;				
				if(StringZZZ.isEmpty(this.getEnding())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
				
				//Dateiendung berechnen
				String sEndingCur = FileEasyZZZ.NameEndCompute(sName);
				if(sEndingCur.equals(this.sFileEnding)) bReturn = true;	
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter
	public String getEnding(){
		return this.sFileEnding;
	}
	public void setEnding(String sEnding){
		this.sFileEnding = sEnding;
	}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setEnding(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getEnding();
	}
}
