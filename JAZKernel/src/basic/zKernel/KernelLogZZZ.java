package basic.zKernel;

import basic.javagently.Stream;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphanumericZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zUtil.io.IFileExpansionUserZZZ;
import basic.zUtil.io.IFileExpansionZZZ;
import basic.zUtil.io.KernelFileExpansionZZZ;
import basic.zUtil.io.KernelFileZZZ;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class KernelLogZZZ extends AbstractObjectZZZ implements IKernelLogZZZ{
	
	public static final String sLOG_FILE_NAME_DEFAULT= "ZKernelLog_default.txt";
	//flags 
	//private boolean bFlagUse_FILE_Expansion; //Zeigt an, ob eine Dateinamens Expansion angehängt werden muss, oder eine bestehende Expansion ersetzt hat.

		
	private String sLogFilename=null;
	private String sLogDirectorypath=null;
	
	protected IFileExpansionZZZ objFileExpansion = null;
	private FileTextWriterZZZ objFileTextWriter = null;
	private FileZZZ objFileZZZ = null;
	
	//++++++++++++++++++++++++
	//Konstructoren
	public KernelLogZZZ(){	
		super();
	}


	/**
	 * Constructor KernelLogZZZ.
	 * @param LogFile-Path
	 */
	public KernelLogZZZ(String sDirectoryPathIn, String sLogFileIn) throws ExceptionZZZ {
		super();
		KernelLogNew_(sDirectoryPathIn, sLogFileIn, null, (String[])null);
	}
	
	public KernelLogZZZ(String sDirectoryPathIn, String sLogFileIn, String sFlagControl) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelLogNew_(sDirectoryPathIn, sLogFileIn, null, saFlagControl);
	}
	
	public KernelLogZZZ(String sDirectoryPathIn, String sLogFileIn, String[] saFlagControl) throws ExceptionZZZ {
		super();
		KernelLogNew_(sDirectoryPathIn, sLogFileIn, null, saFlagControl);
	}
	
	private void KernelLogNew_(String sDirectoryPathIn, String sLogFileIn, IFileExpansionZZZ objFileExpansion, String[] saFlagControl) throws ExceptionZZZ{
		
		main:{
		if(saFlagControl!=null){
			boolean btemp = false;
			for(int icount=0;icount <= saFlagControl.length-1;icount++){
				String stemp = saFlagControl[icount];
				btemp = this.setFlag(stemp, true);
				
				if(btemp==false){ 								   
					   ExceptionZZZ ez = new ExceptionZZZ( IFlagZUserZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					   throw ez;		 
				}
			}
			if(this.getFlag("init")) break main;
		}
		
		
		/*
		 *TODO: get the default filename from the Z-Kernel configuration file
		 */	
		String sLogFile;
		//Idee dahinter: Auch ohne Konfiguration soll soll protokollierung möglich sein.			
		if(StringZZZ.isEmpty(sLogFileIn)){
			sLogFile =new String(KernelLogZZZ.sLOG_FILE_NAME_DEFAULT);
		} else{
			sLogFile = sLogFileIn;
		}
		this.setFilename(sLogFile);
		
		/*
		 * TODO: get the default directory from the Z-Kernel configuration file
		 */			 			
		String sDirectoryPath;
		if(StringZZZ.isEmpty(sDirectoryPathIn)){
			sDirectoryPath = ".";
		}else{
			sDirectoryPath = sDirectoryPathIn;
		}
		this.setDirectory(sDirectoryPath);
	
		FileTextWriterZZZ objFileTextWriter = this.getFileTextWriterObject();
		if(objFileTextWriter!=null) {
			this.WriteLineDate("Log created");
		}else {
			System.out.println("Unable to create FileWriterObject for Log, for path '" + sDirectoryPath + " and filename: " + sLogFile + "'.");
		}			
	}//end main:
		
	}

		
		
		//##############################################################
private FileTextWriterZZZ createFileTextWriterInternal(String sFilepath) {
	FileTextWriterZZZ objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilepath))break main;
	
		objReturn = new FileTextWriterZZZ(sFilepath);
	}
	return objReturn;
}
		
public FileTextWriterZZZ getFileTextWriterObject() throws ExceptionZZZ{
	if(this.objFileTextWriter==null) {
		FileZZZ objFile = this.getFileObject();
		String sFilename = objFile.PathNameTotalExpandedNextCompute();
		this.objFileTextWriter = createFileTextWriterInternal(sFilename);		
	}
	return this.objFileTextWriter;
}
/* (non-Javadoc)
 * @see basic.zKernel.IKernelLogZZZ#WriteLine(String)
 */
public synchronized boolean WriteLine(String stemp){
	boolean bReturn = false;
	FileTextWriterZZZ objFileWriter;
	try {
		objFileWriter = this.getFileTextWriterObject();
		bReturn = objFileWriter.writeLine(stemp);
	} catch (ExceptionZZZ e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	return bReturn;
	}

public synchronized static String computeLineDate(String stemp) {	
	GregorianCalendar d = new GregorianCalendar();
	 Integer iDateYear = new Integer(d.get(Calendar.YEAR));
	 Integer iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	 Integer iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	 Integer iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	 Integer iTimeMinute = new Integer(d.get(Calendar.MINUTE)); 			
	 String sDate =new String( iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	 + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString()); 
	
	String sReturn = new String(sDate + ": " + stemp);
	return sReturn;
}
synchronized public boolean WriteLineDate(String stemp){
	boolean bReturn = false;	
	
	String sLine = KernelLogZZZ.computeLineDate(stemp);
	bReturn = WriteLine(sLine);
	
	return bReturn;
}
public void setFileObject(FileZZZ objFile){
	this.objFileZZZ = objFile;
}
public FileZZZ getFileObject() throws ExceptionZZZ{
	if(this.objFileZZZ == null) {
		String sDirectoryPathNormed = this.getDirectory();
		String sLogFile = this.getFilename();
		
		IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();				
		FileZZZ objFile = new FileZZZ(sDirectoryPathNormed, sLogFile, objFileExpansion, null);
		this.setFileObject(objFile);
	}
	return this.objFileZZZ;
}



public String getFilenameExpanded() throws ExceptionZZZ{
	return this.objFileZZZ.getNameExpandedCurrent();
}

public String getFilename(){
	//früher  return this.objFileZZZ.getName();
	return this.sLogFilename;
}
		

/* (non-Javadoc)
 * @see basic.zKernel.IKernelLogZZZ#Write(java.lang.String)
 */
public synchronized boolean Write(String stemp){
	boolean bReturn = false;
	FileTextWriterZZZ objFileWriter;
	try {
		objFileWriter = this.getFileTextWriterObject();
		bReturn = objFileWriter.write(stemp);
	} catch (ExceptionZZZ e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	return bReturn;
	}



//############################################
//### Functions implemented by interface
//.....
public void setFilename(String sLogFilename) {
	this.sLogFilename = sLogFilename;
	
	//File Objekte wieder zurücksetzen
	this.objFileZZZ = null;
	this.objFileTextWriter = null;
}

public void setDirectory(String sLogDirectorypath) throws ExceptionZZZ {
	
	//20190116: Suche nach dem Pfad. Er ist ggfs. als Serverapplication ohne den "src"-Ordner.
	File objDirectory = FileEasyZZZ.searchDirectory(sLogDirectorypath);
	if(objDirectory==null) {
		String sError = "Verzeichnis für das KernelLog  ='" + sLogDirectorypath +"' existiert nicht.";
		System.out.println(ReflectCodeZZZ.getPositionCurrent()+": " + sError);
		ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, KernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;					
	}	
	String sDirectoryPathNormed = objDirectory.getAbsolutePath();
	System.out.println(ReflectCodeZZZ.getPositionCurrent()+": Errechneter existierender Pfad für das KernelLog='" + sDirectoryPathNormed +"'");
	this.sLogDirectorypath = sDirectoryPathNormed; 
	
	//File Objekte wieder zurücksetzen
	this.objFileZZZ = null;
	this.objFileTextWriter = null;
}
public String getDirectory() {
	return this.sLogDirectorypath;
	//früher... return this.objFileZZZ.getPathDirectory();    //.getPath();
}


public IFileExpansionZZZ getFileExpansionObject(){	
	if(this.getFlag(IKernelLogZZZ.FLAGZ.USE_FILE_EXPANSION.name())) {
		if(this.objFileExpansion==null) {
			String sDir = this.getDirectory();			
			String sName = this.getFilename();
			FileZZZ objFileBase;
			try {
				objFileBase = new FileZZZ(sDir,sName);
				this.objFileExpansion=new KernelFileExpansionZZZ(objFileBase);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return this.objFileExpansion;
	}else {
		return null;
	}
}
public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion){
	this.objFileExpansion = objFileExpansion;
	
	//File Objekt aktualisieren
	FileZZZ objFile;
	try {
		objFile = this.getFileObject();
		if(objFile!=null) {
			objFile.setFileExpansionObject(objFileExpansion);
		}
	} catch (ExceptionZZZ e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
}
}//end class
