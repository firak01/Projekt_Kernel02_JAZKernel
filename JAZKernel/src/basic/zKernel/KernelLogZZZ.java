package basic.zKernel;

import basic.javagently.Stream;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphanumericZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;

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
	private FileTextWriterZZZ objFileTextWriter = null;
	private FileZZZ objFileZZZ = null;
	//private Stream objStream = null;
	
		//Constructor
		public KernelLogZZZ(){	
//			20200206: DAS AUSSTELLEN
//			boolean btemp = createStreamInternal(KernelLogZZZ.sLOG_FILE_NAME_DEFAULT);
//			if(btemp) {
//				WriteLineDate("Default Log created");
//			}else{
//				System.out.println("Unable to create default Log.");	
//			}
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
			if(objDirectory==null) {
				String sError = "Verzeichnis für das KernelLog  ='" + sDirectoryPath +"' existiert nicht.";
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+": " + sError);
				ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, KernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}
			
			String sDirectoryPathNormed = objDirectory.getAbsolutePath();
			System.out.println(ReflectCodeZZZ.getPositionCurrent()+": Errechneter existierender Pfad für das KernelLog='" + sDirectoryPathNormed +"'");
			
			
			FileZZZ objFile = new FileZZZ(sDirectoryPathNormed, sLogFile, 3,null);
			this.setFileObject(objFile);
								
			FileTextWriterZZZ objFileTextWriter = this.getFileTextWriterObject();
			if(objFileTextWriter!=null) {
				this.WriteLineDate("Log created");
			}else {
				System.out.println("Unable to create FileWriterObject for Log '" + objFile.getNameExpandedCurrent() + "'.");
			}			
		}//end main:
		}

		
		
		//##############################################################
public FileTextWriterZZZ getFileTextWriterObject(){
	if(this.objFileTextWriter==null) {
		FileZZZ objFile = this.getFileObject();
		String sFilename;
		try {
			sFilename = objFile.PathNameTotalExpandedNextCompute();
			this.objFileTextWriter = createFileTextWriterInternal(sFilename);
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	return this.objFileTextWriter;
}
public synchronized boolean WriteLine(String stemp) throws ExceptionZZZ{
	boolean bReturn = false;
	FileTextWriterZZZ objFileWriter = this.getFileTextWriterObject();
	bReturn = objFileWriter.writeLine(stemp);
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
	try {
		String sLine = KernelLogZZZ.computeLineDate(stemp);
		bReturn = WriteLine(sLine);
	} catch (ExceptionZZZ e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return bReturn;
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
	boolean bReturn = false;
	FileTextWriterZZZ objFileWriter = this.getFileTextWriterObject();
	bReturn = objFileWriter.write(stemp);
	return bReturn;
	}

private FileTextWriterZZZ createFileTextWriterInternal(String sFilepath) {
	FileTextWriterZZZ objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilepath))break main;
	
		objReturn = new FileTextWriterZZZ(sFilepath);
	}
	return objReturn;
}

//############################################
//### Functions implemented by interface
//.....
}//end class
