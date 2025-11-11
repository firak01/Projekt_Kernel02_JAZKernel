package basic.zKernel;

import basic.javagently.Stream;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphanumericZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringZZZ;
import basic.zBasic.util.log.LogStringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
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
public abstract class KernelLogZZZ extends AbstractObjectWithFlagZZZ implements IKernelLogZZZ{
	
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
					   ExceptionZZZ ez = new ExceptionZZZ( IFlagZEnabledZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
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

	//#### static Methoden #########################################
	//+++ als moeglichst einfacher String
	public synchronized static String computeLine(String stemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01				 
		 };
		 return LogStringZZZ.getInstance().compute(stemp, iaFormat);
	}
	public synchronized static String computeLine(Object obj, String stemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {				 
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01
		 };
		 return LogStringZZZ.getInstance().compute(obj, stemp, iaFormat);
	}
	
	
	public synchronized static String computeLine(Class classObj, String stemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {				 
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01
		 };
		 return LogStringZZZ.getInstance().compute(classObj, stemp, iaFormat);
	}
	
	//+++ mit Datum
	public synchronized static String computeLineDate(String stemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.DATE,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01				 
		 };
		 return LogStringZZZ.getInstance().compute(stemp, iaFormat);
	}
	public synchronized static String computeLineDate(Object obj, String stemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.DATE,
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01
		 };
		 return LogStringZZZ.getInstance().compute(obj, stemp, iaFormat);
	}
	
	
	public synchronized static String computeLineDate(Class classObj, String stemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.DATE,
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01
		 };
		 return LogStringZZZ.getInstance().compute(classObj, stemp, iaFormat);
	}
	
	//+++ mit CodePosition
	public synchronized static String computeLineDateWithPosition(Object obj, String stemp) throws ExceptionZZZ {	
		//Da die Position nicht an anderer Stelle ermittelt werden kann, sie hier in die Log-Strings aufnehmen.
		//Bei der Abarbeitung wird geprüft, ob der verwendete Tag "positioncurrent" vorhanden ist.
		//Wenn das der Fall ist, gib diesen an der durch die Formatanweisung festgelegten Position aus.
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.DATE,
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.CLASSMETHOD_REFLECTED,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01,
				 ILogStringZZZ.LOGSTRING.CLASSFILEPOSITION_REFLECTED,
		 };
		 String sPositionCalling = ReflectCodeZZZ.getPositionCallingPlus(1);
		 String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		 return LogStringZZZ.getInstance().compute(obj, satemp, iaFormat);
	}
	
	public synchronized static String computeLineDateWithPosition(Class classObj, String stemp) throws ExceptionZZZ {	
		//Da die Position nicht an anderer Stelle ermittelt werden kann, sie hier in die Log-Strings aufnehmen.
		//Bei der Abarbeitung wird geprüft, ob der verwendete Tag "positioncurrent" vorhanden ist.
		//Wenn das der Fall ist, gib diesen an der durch die Formatanweisung festgelegten Position aus.
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.DATE,
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.CLASSMETHOD_REFLECTED,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01,
				 ILogStringZZZ.LOGSTRING.CLASSFILEPOSITION_REFLECTED,
		 };
		 String sPositionCalling = ReflectCodeZZZ.getPositionCallingPlus(1);
		 //String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		 String[] satemp = StringArrayZZZ.prepend(stemp, sPositionCalling);
		 return LogStringZZZ.getInstance().compute(classObj, satemp, iaFormat);
	}
	
	public synchronized static String computeLineDateWithPosition(Object obj, String[] satemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.DATE,
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.CLASSMETHOD_REFLECTED,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE02,	
				 ILogStringZZZ.LOGSTRING.CLASSFILEPOSITION_REFLECTED,				 
		 };
		 String sPositionCalling = ReflectCodeZZZ.getPositionCallingPlus(1);
		 //satemp = StringArrayZZZ.append(satemp, sPositionCalling);
		 satemp = StringArrayZZZ.prepend(satemp, sPositionCalling);
		 return LogStringZZZ.getInstance().compute(obj, satemp, iaFormat);
	}
	
	public synchronized static String computeLineDateWithPosition(Class classObj, String[] satemp) throws ExceptionZZZ {	
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringZZZ.LOGSTRING.DATE,
				 ILogStringZZZ.LOGSTRING.CLASSFILENAME,
				 ILogStringZZZ.LOGSTRING.CLASSMETHOD_REFLECTED,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE01,
				 ILogStringZZZ.LOGSTRING.STRINGTYPE02,	
				 ILogStringZZZ.LOGSTRING.CLASSFILEPOSITION_REFLECTED,
				 ILogStringZZZ.LOGSTRING.POSITIONCURRENT_REFLECTED
		 };
		 String sPositionCalling = ReflectCodeZZZ.getPositionCallingPlus(1);
		 //satemp = StringArrayZZZ.append(satemp, sPositionCalling);
		 satemp = StringArrayZZZ.prepend(satemp, sPositionCalling);
		 return LogStringZZZ.getInstance().compute(classObj, satemp, iaFormat);
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Biete die Log-Methoden auch static an, siehe ILogZZZ, bzw. AbstractObjectZZZ fuer den Code
	//+++++++++++++++++++++++++++++++++++++++++++++++
	public synchronized static void logProtocolStringStatic(String[] saLog) throws ExceptionZZZ{
		KernelLogZZZ.logProtocolStringStatic(null, saLog);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
	
	public synchronized static void logProtocolStringStatic(String sLog) throws ExceptionZZZ{
		KernelLogZZZ.logProtocolStringStatic(null, sLog);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
	
	public synchronized static void logProtocolStringStatic(Object obj, String[] saLog) throws ExceptionZZZ{
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;
			
			if(obj==null) {
				for(String sLog : saLog) {
					KernelLogZZZ.logProtocolStringStatic(sLog);
				}
			}else {
				for(String sLog : saLog) {
					KernelLogZZZ.logProtocolStringStatic(obj, sLog);
				}	
			}
			
		}//end main:
	}
		
	public synchronized static void logProtocolStringStatic(Object obj, String sLog) throws ExceptionZZZ{
		String sLogUsed;
		sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog);
		System.out.println(sLogUsed);
	}
	
	//++++++++++++++++++++++++++++++++++	
	public synchronized static void logProtocolStringStatic(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
		KernelLogZZZ.logProtocolStringStatic(null, saLog, ienumaMappedLogString);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
	
	public synchronized static void logProtocolStringStatic(String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
		KernelLogZZZ.logProtocolStringStatic(null, sLog, ienumMappedLogString);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
		
	public synchronized static void logProtocolStringStatic(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
		main:{
		if(ArrayUtilZZZ.isNull(saLog)) break main;
		if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
			KernelLogZZZ.logProtocolStringStatic(saLog);
			break main;
		}
		
		int iIndex=0;
		if(obj==null) {			
			for(String sLog : saLog) {
				if(ienumaMappedLogString.length>iIndex) {
					KernelLogZZZ.logProtocolStringStatic(sLog,ienumaMappedLogString[iIndex]);
					iIndex++;
				}else {
					KernelLogZZZ.logProtocolStringStatic(saLog);
				}
			}
		}else {
			for(String sLog : saLog) {
				if(ienumaMappedLogString.length>iIndex) {
					KernelLogZZZ.logProtocolStringStatic(obj, sLog,ienumaMappedLogString[iIndex]);
					iIndex++;
				}else {
					KernelLogZZZ.logProtocolStringStatic(saLog);
				}
			}			
		}
	}//end main:
	}
	
	public synchronized static void logProtocolStringStatic(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
		String sLogUsed;
		if(obj==null) {
			sLogUsed = LogStringZZZ.getInstance().compute(sLog, ienumMappedLogString);
		}else {
			sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog, ienumMappedLogString);
		}
		System.out.println(sLogUsed);
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
		
		System.out.println(stemp);
	} catch (ExceptionZZZ e) {		
		e.printStackTrace();
	}	
	return bReturn;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelLogZZZ#WriteLineDate(java.lang.String)
	 */
	synchronized public boolean WriteLineDate(String stemp) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = KernelLogZZZ.computeLineDate(stemp);
		bReturn = WriteLine(sLine);
		
		return bReturn;
	}
	synchronized public boolean WriteLineDate(Object obj, String stemp) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = KernelLogZZZ.computeLineDate(obj, stemp);
		bReturn = WriteLine(sLine); //Schreibe in eine Logdatei
		return bReturn;
	}
	synchronized public boolean WriteLineDateWithPosition(Class classObj, String stemp) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = KernelLogZZZ.computeLineDateWithPosition(classObj, stemp);
		bReturn = WriteLine(sLine);
		
		return bReturn;
	}

	synchronized public boolean WriteLineDateWithPosition(Object obj, String stemp) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = KernelLogZZZ.computeLineDateWithPosition(obj, stemp);
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
			bReturn = objFileWriter.write(stemp); //Kein Zeilenumbruch.
			
			System.out.print(stemp); //Kein Zeilenumbruck
		} catch (ExceptionZZZ e) {			
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
		String sLog = "Errechneter existierender Pfad für das KernelLog='" + sDirectoryPathNormed +"'";
		//System.out.println(ReflectCodeZZZ.getPositionCurrent()+": Errechneter existierender Pfad für das KernelLog='" + sDirectoryPathNormed +"'");
		ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, sLog);
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
