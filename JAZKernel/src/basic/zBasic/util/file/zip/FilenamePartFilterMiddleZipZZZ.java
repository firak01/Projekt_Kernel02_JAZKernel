package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterMiddleZipZZZ extends ObjectZZZ implements IFilenamePartFilterZipZZZ  {
	private String sFileMiddle;
	
	public FilenamePartFilterMiddleZipZZZ() {
		super();
	}
	public FilenamePartFilterMiddleZipZZZ(String sFileMiddle){
		super();
		this.setMiddle(sFileMiddle);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;				
				if(StringZZZ.isEmpty(this.getMiddle())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
				
				//"Mitte" des Dateinamens berechnen			
				String sMiddleCur;
				try {				
					sMiddleCur = FileEasyZZZ.getNameOnly(sName);				
					sMiddleCur = StringZZZ.midBounds(sMiddleCur, 1, 1);
					if(StringZZZ.contains(sMiddleCur, this.getMiddle())) bReturn = true;
				} catch (ExceptionZZZ e) {			
					e.printStackTrace();
				} 							
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter
		public String getMiddle(){
			return this.sFileMiddle;
		}
		public void setMiddle(String sMiddle){
			this.sFileMiddle = sMiddle;
		}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setMiddle(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getMiddle();
	}
	

}
