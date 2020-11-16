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

public class FilenamePartFilterPathTotalZipZZZ extends ObjectZZZ implements IFilenamePartFilterZipZZZ  {
	private String sDirectoryPath=null;
	private String sFileName=null;
	
	
	public FilenamePartFilterPathTotalZipZZZ() {
		super();
	}
	public FilenamePartFilterPathTotalZipZZZ(String sDirectoryPath) throws ExceptionZZZ{
		super();
		this.setDirectoryPath(sDirectoryPath);
		this.setFileName(sFileName);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
		    	try {
					if(ze==null) break main;								
					if(StringZZZ.isEmpty(this.getDirectoryPath())) {
						bReturn = true;
						break main;
					}	
					String sPathTotal=this.getCriterion();
					String sName = ze.getName();
					
					if(sPathTotal.equals(sName)) {//Genaue übereinstimmung zwischen Pfad und Dateiname. Also EINZIGARTIG!
						bReturn = true;
						break main;
					}
				
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
	public void setDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ{
		main:{
		if(StringZZZ.isEmpty(sDirectoryPath)) break main;

		String sDirectoryPath = JarEasyUtilZZZ.toJarDirectoryPath(sDirectoryPathIn);				
		boolean bCheck = JarEasyUtilZZZ.isJarPathDirectoryValid(sDirectoryPath);
		if(!bCheck) {
			ExceptionZZZ ez = new ExceptionZZZ("Provided Path is not a valid JarFileDirectory: " + sDirectoryPathIn + "'", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	}//end main:
	this.sDirectoryPath = sDirectoryPath;
	}
	
	public String getFileName(){
		return this.sFileName;
	}
	public void setFileName(String sName){
		this.sFileName = sName;
	}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) throws ExceptionZZZ{
		
		String sFileName = JarEasyUtilZZZ.computeFilenameFromJarPath(sCriterion);
		String sDirectory = JarEasyUtilZZZ.computeDirectoryFromJarPath(sCriterion);
		
		this.setDirectoryPath(sDirectory);
		this.setFileName(sFileName);
	}
	
	@Override
	public String getCriterion() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sDirectoryPath = this.getDirectoryPath();
			String sFileName = this.getFileName();
			sReturn = JarEasyUtilZZZ.joinJarFilePathName(sDirectoryPath, sFileName);
		}//end main:
		return sReturn;
	}
	

}
