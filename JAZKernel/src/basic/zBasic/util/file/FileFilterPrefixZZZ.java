package basic.zBasic.util.file;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zUtil.io.FileZZZ;

public class FileFilterPrefixZZZ extends ObjectZZZ implements FilenameFilter  {
	private String sFilePrefix;
	
	public FileFilterPrefixZZZ(String sFilePrefix){
		this.setPrefix(sFilePrefix);
	}
	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)	 
	 */
	public boolean accept(File objFile, String sName) {
		boolean bReturn=false;
		main:{
			//if(objFile==null) break main;
			if(sName==null) break main;				
			if(StringZZZ.isEmpty(this.getPrefix())) {
				bReturn = true;
				break main;
			}
		
			
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
	
	/**A file filter, but unlike the method, implemented be the interface,
	 * this method doesn�t need a string as parameter.
	 * @return boolean
	 *
	 * javadoc created by: 0823, 14.07.2006 - 11:43:07
	 */
	public boolean accept(File objFile){
		boolean bReturn = false;
		main:{
			if(objFile==null) break main;				
					
			String sName = objFile.getPath();
			
			//Ende berechnen		
			String sPrefixCur;
			try {
				sPrefixCur = FileEasyZZZ.getNameOnly(sName);
				if(StringZZZ.startsWithIgnoreCase(sPrefixCur, this.getPrefix())) bReturn = true;
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
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

}
