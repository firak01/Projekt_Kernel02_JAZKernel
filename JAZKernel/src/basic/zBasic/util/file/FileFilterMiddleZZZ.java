package basic.zBasic.util.file;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zUtil.io.FileZZZ;

public class FileFilterMiddleZZZ extends ObjectZZZ implements FilenameFilter  {
	private String sFileMiddle;
	
	public FileFilterMiddleZZZ(String sFileMiddle){
		this.setMiddle(sFileMiddle);
	}
	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)	 
	 */
	public boolean accept(File objFile, String sName) {
		boolean bReturn=false;
		main:{
			check:{
				//if(objFile==null) break main;
				if(sName==null) break main;				
			}//END check:
		
			
			//"Mitte" des Dateinamens berechnen			
			String sMiddleCur;
			try {				
				sMiddleCur = FileEasyZZZ.getNameOnly(sName);
				sMiddleCur = StringZZZ.midLeftRight(sMiddleCur, 1, 1);
				if(StringZZZ.contains(sMiddleCur, this.getMiddle())) bReturn = true;
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
			check:{
				if(objFile==null) break main;				
			}//END check:
		
			String sName = objFile.getPath();
			
			//Ende berechnen		
			String sMiddleCur;
			try {
				sMiddleCur = FileEasyZZZ.getNameOnly(sName);
				sMiddleCur = StringZZZ.midLeftRight(sMiddleCur, 1, 1);
				if(StringZZZ.contains(sMiddleCur, this.getMiddle())) bReturn = true;
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
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

}
