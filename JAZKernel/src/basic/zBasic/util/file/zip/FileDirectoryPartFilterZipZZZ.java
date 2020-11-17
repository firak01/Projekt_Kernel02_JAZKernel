package basic.zBasic.util.file.zip;

import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zBasic.util.file.JarEasyZZZ;

public class FileDirectoryPartFilterZipZZZ extends ObjectZZZ implements IFileDirectoryPartFilterZipZZZ{
	private String sDirectoryPath;
	
	public FileDirectoryPartFilterZipZZZ() {
		super();
	}
	public FileDirectoryPartFilterZipZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();		
		this.setDirectoryPath(sDirectoryPath);
	}
	
	public void setDirectoryPath(String sDirectoryPath) throws ExceptionZZZ {
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)) break main;
			
			String sDirectoryJarPath = JarEasyUtilZZZ.toJarDirectoryPath(sDirectoryPath);			
			boolean bCheck = JarEasyUtilZZZ.isJarPathDirectoryValid(sDirectoryJarPath);
			if(!bCheck) {
				ExceptionZZZ ez = new ExceptionZZZ("Provided Path is not a valid JarFileDirectory: " + sDirectoryPath + "'", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		this.sDirectoryPath = sDirectoryPath;
	}
	public String getDirectoryPath() {
		return this.sDirectoryPath;
	}
	
	
	@Override
	public boolean accept(ZipEntry ze) {
		 boolean bReturn=false;
			main:{
				if(ze==null) break main;
				
				//Wichtig: Filtert dadurch nur Verzeichnisse!!!
				if(!ze.isDirectory()) break main;
				
				if(StringZZZ.isEmpty(this.getCriterion())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
								
				//Verzeichnisname vergleichen!!!
				if(sName.equals(this.getCriterion())) bReturn = true;	
			}//END main:
			return bReturn;
	}

	@Override
	public void setCriterion(String sCriterion) throws ExceptionZZZ {
		this.setDirectoryPath(sCriterion);
	}

	@Override
	public String getCriterion() {
		return this.getDirectoryPath();
	}
	

}
