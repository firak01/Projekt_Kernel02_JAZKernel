package basic.zKernel;

import basic.javagently.Stream;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import custom.zUtil.io.FileZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class KernelLogZZZ extends ObjectZZZ implements IObjectZZZ {
	public static final String sLOG_FILE_NAME_DEFAULT= "ZKernelLog_default.txt";
	
	private FileZZZ objFileZZZ;
	private Stream objStream = null;
	
		//Constructor
		public KernelLogZZZ(){	
			boolean btemp = createStreamInternal(KernelLogZZZ.sLOG_FILE_NAME_DEFAULT);
			if(btemp) {
				WriteLineDate("Default Log created");
			}else{
				System.out.println("Unable to create default Log.");	
			}
		}


		/**
		 * Constructor KernelLogZZZ.
		 * @param LogFile-Path
		 */
		public KernelLogZZZ(String sDirectoryPathIn, String sLogFileIn) throws ExceptionZZZ {
			main:{
			/*
			 *TODO: get the default filename from the Z-Kernel configuration file
			 */	
			String sLogFile;
			
			/*
			 * TODO: get the default directory from the Z-Kernel configuration file
			 */			 			
			String sDirectoryPath;
			
			//Idee dahinter: Auch ohne Konfiguration soll soll protokollierung möglich sein.
			if(StringZZZ.isEmpty(sDirectoryPathIn)){
				sDirectoryPath = ".";
			}else{
				sDirectoryPath = sDirectoryPathIn;
			}
			if(StringZZZ.isEmpty(sLogFileIn)){
				sLogFile =new String(KernelLogZZZ.sLOG_FILE_NAME_DEFAULT);
			} else{
				sLogFile = sLogFileIn;
			}
			
			//20190116: Suche nach dem Pfad. Er ist ggfs. als Serverapplication ohne den "src"-Ordner.
			File objDirectory = FileEasyZZZ.searchDirectory(sDirectoryPath);
			String sDirectoryPathNormed = objDirectory.getAbsolutePath();
			System.out.println(ReflectCodeZZZ.getPositionCurrent()+": Errechneter Pfad für das KernelLog='" + sDirectoryPathNormed +"'");
			
			FileZZZ objFile = new FileZZZ(sDirectoryPathNormed, sLogFile, 3,null);
			String sLogFileNew = objFile.PathNameTotalExpandedNextCompute();
			boolean btemp = createStreamInternal(sLogFileNew);
			if(btemp) {
				WriteLineDate("Log '" + sLogFileNew + "' created");
			}else{
				System.out.println("Unable to create Log '" + sLogFileNew + "'.");	
			}
						
			//die ermittelten Werte als Property - �bernehmen			
			this.setFileObject(objFile);
			
		}//end main:
		}

		
		
		//##############################################################
public synchronized boolean WriteLine(String stemp){
	boolean bHasStream = true;
	if(this.objStream==null) bHasStream = createStreamInternal("");
	if(bHasStream){
		this.objStream.println(stemp);
	}
	return bHasStream;
	}

synchronized public boolean WriteLineDate(String stemp){
	GregorianCalendar d = new GregorianCalendar();
	 Integer iDateYear = new Integer(d.get(Calendar.YEAR));
	 Integer iDateMonth = new Integer(d.get(Calendar.MONTH) + 1);
	 Integer iDateDay = new Integer(d.get(Calendar.DAY_OF_MONTH));
	 Integer iTimeHour = new Integer(d.get(Calendar.HOUR_OF_DAY));
	 Integer iTimeMinute = new Integer(d.get(Calendar.MINUTE)); 			
	 String sDate =new String( iDateYear.toString() + "-" + iDateMonth.toString() + "-" + iDateDay.toString()
	 + "_" + iTimeHour.toString() + "_" + iTimeMinute.toString()); 
	
	String sLine = new String(sDate + ": " + stemp);
	WriteLine(sLine);
	return true;
}
public void setFileObject(FileZZZ objFile){
	this.objFileZZZ = objFile;
}
public FileZZZ getFileObject(){
	return this.objFileZZZ;
}

public String getDirectory(){
	return this.objFileZZZ.getPathDirectory();    //.getPath();
}

public String getFilenameExpanded() throws ExceptionZZZ{
	return this.objFileZZZ.getNameExpandedCurrent();
}

public String getFilename(){
	return this.objFileZZZ.getName();
}
		
public synchronized boolean Write(String stemp){
	boolean bHasStream = true;
	if(this.objStream==null) bHasStream = createStreamInternal("");
	if(bHasStream){
		this.objStream.print(stemp);
	}
	return bHasStream;
}

private boolean createStreamInternal(String sFileNameIn){
	boolean bReturn = false;
	try {
		String sFileName;
		if(StringZZZ.isEmpty(sFileNameIn)){
			sFileName = KernelLogZZZ.sLOG_FILE_NAME_DEFAULT;
		}else{
			sFileName = sFileNameIn;
		}
		this.objStream = new Stream(sFileName,1); //1 = Write
		bReturn = true;
	} catch (FileNotFoundException e) {
	} catch (IOException e) {
	} 
	return bReturn;
}


//############################################
//### Functions implemented by interface
public ExceptionZZZ getExceptionObject() {
	// TODO Auto-generated method stub
	return null;
}

public void setExceptionObject(ExceptionZZZ objException) {
	// TODO Auto-generated method stub
	
}
}//end class
