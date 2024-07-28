package basic.zKernel.file.ini;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ.FLAGZ;

public interface IKernelFileIniZZZ extends IKernelUserZZZ{		
	//Verwendete Konstanten stammen aus IniFile
	public static final String sINI_COMMENT = IIniStructureConstantZZZ.sINI_COMMENT;
	public static final String sINI_SUBJECT_START = IIniStructureConstantZZZ.sINI_SUBJECT_START;
	public static final String sINI_SUBJECT_END = IIniStructureConstantZZZ.sINI_SUBJECT_END;
	public static final String sINI_PROPERTY_SEPERATOR= IIniStructureConstantZZZ.sINI_PROPERTY_SEPERATOR;
	public static final String sINI_MULTIVALUE_SEPARATOR= IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR;
	
	//Weitere Konstanten sind Kernelspezifisch
	public static final String sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER = "!";
	public static final String sINI_SUBJECT_SEPARATOR_PROGRAM = "_";
		
	public enum FLAGZ{
		FILEUNSAVED, FILENEW, FILECHANGED;
	}
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelFileIniZZZ.FLAGZ objEnum_IKernelFileIniZZZ);
	public boolean setFlag(IKernelFileIniZZZ.FLAGZ objEnum_IKernelFileIniZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelFileIniZZZ.FLAGZ[] objEnum_IKernelFileIniZZZ, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(IKernelFileIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public File getFileObject();		
	public IniFile getFileIniObject() throws ExceptionZZZ;
	//Setter als private, damit die Erstellung über den Konstruktor nicht umgangen werden kann:
	//public void setFileIniObject(IniFile objIniFile);
	
	public boolean proofSectionExistsDirectLookup(String sSection) throws ExceptionZZZ;
}
