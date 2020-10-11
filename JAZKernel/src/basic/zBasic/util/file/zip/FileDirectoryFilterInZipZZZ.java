package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.AbstractFileFilterZZZ;
import basic.zBasic.util.file.FilenamePartFilterEndingZZZ;
import basic.zBasic.util.file.FilenamePartFilterMiddleZZZ;
import basic.zBasic.util.file.FilenamePartFilterPrefixZZZ;
import basic.zBasic.util.file.FilenamePartFilterSuffixZZZ;
import basic.zBasic.util.file.jar.AbstractFileDirectoryFilterInJarZZZ;
import basic.zBasic.util.file.jar.AbstractFileFileFilterInJarZZZ;

public class FileDirectoryFilterInZipZZZ extends AbstractFileDirectoryFilterInJarZZZ{
//	public static String sDIRECTORY_PATH="template";
//	public static String sPREFIX="template_";
//	public static String sMIDDLE="";
//	public static String sSUFFIX="";
//	public static String sENDING="ovpn";
	
	public FileDirectoryFilterInZipZZZ(String sDirectoryPath, String[] saFlagControl) throws ExceptionZZZ {
		super(sDirectoryPath, saFlagControl);			
	} 
	public FileDirectoryFilterInZipZZZ(String sDirectoryPath, String sFlagControl) throws ExceptionZZZ {
		super(sDirectoryPath, sFlagControl);
		
	} 
	public FileDirectoryFilterInZipZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super(sDirectoryPath);		
	} 
	public FileDirectoryFilterInZipZZZ() throws ExceptionZZZ {
		super();		
			}
	private void FileDirectoryFilterNew_(String sDirectoryPath) throws ExceptionZZZ {
		this.setDirectoryPath(sDirectoryPath);
	}
	
	
	//##### GETTER / SETTER	
	
	

}//END class
