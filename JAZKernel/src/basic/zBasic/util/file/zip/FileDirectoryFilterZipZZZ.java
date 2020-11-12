package basic.zBasic.util.file.zip;

import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;

public class FileDirectoryFilterZipZZZ extends ObjectZZZ implements IFileDirectoryFilterZipZZZ{
	private String sDirectoryPath;
	
	public FileDirectoryFilterZipZZZ() {
		super();
	}
	public FileDirectoryFilterZipZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();		
		this.setDirectoryPath(sDirectoryPath);
	}
	
	public void setDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ {
		String sDirectoryPath = JarEasyUtilZZZ.toJarDirectoryPath(sDirectoryPathIn);
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
				if(!ze.isDirectory()) break main;
				
				if(StringZZZ.isEmpty(this.getDirectoryPath())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
								
				//Verzeichnisname vergleichen
				if(sName.equals(this.sDirectoryPath)) bReturn = true;	
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