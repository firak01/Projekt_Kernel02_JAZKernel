package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.AbstractFileFilterZZZ;
import basic.zBasic.util.file.FilenamePartFilterEndingZZZ;
import basic.zBasic.util.file.FilenamePartFilterMiddleZZZ;
import basic.zBasic.util.file.FilenamePartFilterPrefixZZZ;
import basic.zBasic.util.file.FilenamePartFilterSuffixZZZ;
import basic.zBasic.util.file.jar.AbstractFileFileFilterInJarZZZ;

public class FileFileFilterInJarZZZ extends AbstractFileFileFilterInJarZZZ{
	public static String sDIRECTORY_PATH="";
	public static String sPREFIX="";
	public static String sMIDDLE="";
	public static String sSUFFIX="";
	public static String sENDING="";
	
	public FileFileFilterInJarZZZ(String sDirectroyPath, String[] saFlagControl) throws ExceptionZZZ {
		super(sDirectroyPath, saFlagControl);	
		FileFilterInJarNew_();
	} 
	public FileFileFilterInJarZZZ(String sDirectroyPath, String sFlagControl) throws ExceptionZZZ {
		super(sDirectroyPath, sFlagControl);
		FileFilterInJarNew_();
	} 
	public FileFileFilterInJarZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super(sDirectoryPath);		
		FileFilterInJarNew_();
	} 
	public FileFileFilterInJarZZZ() throws ExceptionZZZ {
		super();		
		FileFilterInJarNew_();
	}
	private void FileFilterInJarNew_() {		
		//Merke: Hier könnenten dann in einer speziellen Klasse Statische Konstantenwerte gesetzt werden, z.B.:
		//this.setPrefix(ConfigFileTemplateOvpnOVPN.sFILE_TEMPLATE_PREFIX);
		//this.setMiddle(this.getOvpnContext());
	}
	
	//##### GETTER / SETTER	
	public void setDirectoryPath(String sDirectoyPath) {
		if(StringZZZ.isEmpty(sDirectoyPath)) {
			super.setDirectoryPath(FileFileFilterInJarZZZ.sDIRECTORY_PATH);
		}else {
			super.setDirectoryPath(sDirectoyPath);
		}
	}
	
	public void setPrefix(String sPrefix) {
		if(StringZZZ.isEmpty(sPrefix)) {
			super.setPrefix(FileFileFilterInJarZZZ.sPREFIX);
		}else {
			super.setPrefix(sPrefix);
		}
	}
	
	public void setMiddle(String sMiddle) {
		if(StringZZZ.isEmpty(sMiddle)) {
			super.setMiddle(FileFileFilterInJarZZZ.sMIDDLE);
		}else {
			super.setMiddle(sMiddle);
		}
	}
	
	public void setSuffix(String sSuffix) {
		if(StringZZZ.isEmpty(sSuffix)) {
			super.setSuffix(FileFileFilterInJarZZZ.sSUFFIX);
		}else {
			super.setSuffix(sSuffix);
		}
	}

				
	public void setEnding(String sEnding) {
		if(StringZZZ.isEmpty(sEnding)) {
			super.setEnding(FileFileFilterInJarZZZ.sENDING);
		}else {
			super.setEnding(sEnding);
		}
	}
}//END class