package basic.zKernel.file.ini;

import basic.zBasic.util.file.ini.IniFile;

public interface IKernelFileIniZZZ {
	
	//Verwendete Konstanten stammen aus IniFile
	public static final String sINI_COMMENT = IniFile.sINI_COMMENT;
	public static final String sINI_SUBJECT_START = IniFile.sINI_SUBJECT_START;
	public static final String sINI_SUBJECT_END = IniFile.sINI_SUBJECT_END;
	public static final String sINI_PROPERTY_SEPERATOR= IniFile.sINI_PROPERTY_SEPERATOR;
	public static final String sINI_MULTIVALUE_SEPARATOR= IniFile.sINI_MULTIVALUE_SEPARATOR;
	
	//Weitere Konstanten sind Kernelspezifisch
	public static final String sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER = "!";
		
	public enum FLAGZ{
		FILEUNSAVED, FILENEW, FILECHANGED;
	}
}
