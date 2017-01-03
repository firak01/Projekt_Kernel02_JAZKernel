package basic.zBasic.util.file;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ObjectZZZ;

import custom.zUtil.io.FileZZZ;

public class FileFilterSuffixZZZ extends ObjectZZZ implements FilenameFilter  {
	private String sFileSuffix;
	
	public FileFilterSuffixZZZ(String sFileSuffix){
		this.sFileSuffix = sFileSuffix;
	}
	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)	 
	 */
	public boolean accept(File objFileDir, String sName) {
		boolean bReturn=false;
		main:{
			check:{
				if(sName==null) break main;				
			}//END check:
		
			//Ende berechnen
			String sSuffixCur = FileEasyZZZ.NameEndCompute(sName);
			if(sSuffixCur.equals(this.sFileSuffix)) bReturn = true;			
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
			String sSuffixCur = FileEasyZZZ.NameEndCompute(sName);
			if(sSuffixCur.equals(this.sFileSuffix)) bReturn = true;			
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

}
