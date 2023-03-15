package basic.zKernel.file.ini;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelUserZZZ;

public interface IKernelFileIniZZZ extends IKernelUserZZZ{		
	//Verwendete Konstanten stammen aus IniFile
	public static final String sINI_COMMENT = IniFile.sINI_COMMENT;
	public static final String sINI_SUBJECT_START = IniFile.sINI_SUBJECT_START;
	public static final String sINI_SUBJECT_END = IniFile.sINI_SUBJECT_END;
	public static final String sINI_PROPERTY_SEPERATOR= IniFile.sINI_PROPERTY_SEPERATOR;
	public static final String sINI_MULTIVALUE_SEPARATOR= IniFile.sINI_MULTIVALUE_SEPARATOR;
	
	//Weitere Konstanten sind Kernelspezifisch
	public static final String sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER = "!";
	public static final String sINI_SUBJECT_SEPARATOR_PROGRAM = "_";
		
	public enum FLAGZ{
		FILEUNSAVED, FILENEW, FILECHANGED;
	}
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelFileIniZZZ.FLAGZ objEnum_IKernelFileIniZZZ);
	public boolean setFlag(IKernelFileIniZZZ.FLAGZ objEnum_IKernelFileIniZZZ, boolean bFlagValue);
	public boolean[] setFlag(IKernelFileIniZZZ.FLAGZ[] objEnum_IKernelFileIniZZZ, boolean bFlagValue);
	
	
	public File getFileObject();		
	public IniFile getFileIniObject();
	//Setter als private, damit die Erstellung über den Konstruktor nicht umgangen werden kann:
	//public void setFileIniObject(IniFile objIniFile);
	
	public boolean proofSectionExists(String sSection) throws ExceptionZZZ;
}
