package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterSuffixZipZZZ extends AbstractFilenamePartFilterZipZZZ implements IFilenamePartFilterZipZZZ  {
	private String sFileSuffix;
	
	public FilenamePartFilterSuffixZipZZZ() {
		super();
	}
	public FilenamePartFilterSuffixZipZZZ(String sFileSuffix){
		super();
		this.setSuffix(sFileSuffix);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;				
				if(StringZZZ.isEmpty(this.getSuffix())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
				
				//Ende des Dateinamens berechnen			
				String sSuffixCur;
				try {				
					sSuffixCur = FileEasyZZZ.getNameOnly(sName);
					if(StringZZZ.endsWithIgnoreCase(sSuffixCur, this.getSuffix())) bReturn = true;
				} catch (ExceptionZZZ e) {			
					e.printStackTrace();
				} 					
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter
		public String getSuffix(){
			return this.sFileSuffix;
		}
		public void setSuffix(String sSuffix){
			this.sFileSuffix = sSuffix;
		}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setSuffix(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getSuffix();
	}
	

}
