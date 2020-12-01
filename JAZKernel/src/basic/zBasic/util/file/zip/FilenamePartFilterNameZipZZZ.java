package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IJarEasyConstantsZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterNameZipZZZ extends ObjectZZZ implements IFilenamePartFilterZipZZZ  {
	private String sFileName;
	
	public FilenamePartFilterNameZipZZZ() {
		super();
	}
	public FilenamePartFilterNameZipZZZ(String sFileName){
		super();
		this.setName(sFileName);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;				
				if(StringZZZ.isEmpty(this.getName())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
				
				//Ende des JarPfads als Dateinamens berechnen			
				String sNameCur;
//				try {					
					String sCriterion = this.getCriterion();
					if(StringZZZ.endsWithIgnoreCase(sName, sCriterion)) {
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
		public String getName(){
			return this.sFileName;
		}
		public void setName(String sName){
			this.sFileName = sName;
		}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setName(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR + this.getName();
	}
	

}
