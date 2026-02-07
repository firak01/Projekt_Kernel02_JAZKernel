package basic.zKernel;

import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_NAME_DEFAULT;
import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_DIRECTORY_DEFAULT;

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
import basic.zBasic.util.string.formater.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.string.formater.ILogStringFormatZZZ;
import basic.zBasic.util.string.formater.LogStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.LogStringFormaterUtilZZZ;
import basic.zBasic.util.string.formater.LogStringFormaterZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import basic.zUtil.io.IFileExpansionZZZ;
import basic.zUtil.io.KernelFileExpansionZZZ;
import basic.zUtil.io.KernelFileZZZ;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import custom.zKernel.ConfigZZZ;
import custom.zKernel.ILogZZZ;
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
public abstract class AbstractKernelLogZZZ extends AbstractObjectWithFlagZZZ implements ILogZZZ{
	//flags 
	//private boolean bFlagUse_FILE_Expansion; //Zeigt an, ob eine Dateinamens Expansion angehängt werden muss, oder eine bestehende Expansion ersetzt hat.
	protected volatile IKernelConfigZZZ objConfig = null;   //die Werte für den Applikationskey, Systemnummer, etc.
		
	private String sLogFilename=null;
	private String sLogDirectorypath=null;
	
	protected IFileExpansionZZZ objFileExpansion = null;
	private FileTextWriterZZZ objFileTextWriter = null;
	private FileZZZ objFileZZZ = null;
	
	//++++++++++++++++++++++++
	//Konstruktoren
	public AbstractKernelLogZZZ(){	
		super();
	}


	/**
	 * Constructor KernelLogZZZ.
	 * @param LogFile-Path
	 */
	public AbstractKernelLogZZZ(String sDirectoryPathIn, String sLogFileIn) throws ExceptionZZZ {
		super();
		KernelLogNew_(null, sDirectoryPathIn, sLogFileIn, null, (String[])null);
	}
	
	public AbstractKernelLogZZZ(String sDirectoryPathIn, String sLogFileIn, String sFlagControl) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		KernelLogNew_(null, sDirectoryPathIn, sLogFileIn, null, saFlagControl);
	}
	
	public AbstractKernelLogZZZ(String sDirectoryPathIn, String sLogFileIn, String[] saFlagControl) throws ExceptionZZZ {
		super();
		KernelLogNew_(null, sDirectoryPathIn, sLogFileIn, null, saFlagControl);
	}
	
	public AbstractKernelLogZZZ(IKernelConfigZZZ objConfig) throws ExceptionZZZ {
		super();
		KernelLogNew_(objConfig, null, null,  null, (String[]) null);
	}
	
	private void KernelLogNew_(IKernelConfigZZZ objConfig, String sDirectoryPathIn, String sLogFileIn, IFileExpansionZZZ objFileExpansion, String[] saFlagControl) throws ExceptionZZZ{
		
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
		
		this.setConfigObject(objConfig);
		
		String sLogFile=null;
		if(StringZZZ.isEmpty(sLogFileIn)) {
			if(objConfig!=null) {
				sLogFile = objConfig.getLogFileName();
			}else {
				sLogFile = this.getFilename();
			}
		}else {
			sLogFile = sLogFileIn;
		}
		this.setFilename(sLogFile);
		
		String sDirectoryName=null;
		if(StringZZZ.isEmpty(sDirectoryPathIn)) {
			if(objConfig!=null) {
				sDirectoryName = objConfig.getLogDirectoryName();
			}else {
				sDirectoryName = this.getDirectory();
			}
		}else {
			sDirectoryName = sDirectoryPathIn;
		}
		this.setDirectory(sDirectoryName);
		
		FileTextWriterZZZ objFileTextWriter = this.getFileTextWriterObject();
		if(objFileTextWriter!=null) {
			this.WriteLineDate("Log created");
		}else {
			sDirectoryName=this.getDirectory();
			sLogFile = this.getFilename();
			System.out.println("Unable to create FileWriterObject for Log, for path '" + sDirectoryName + " and filename: " + sLogFile + "'.");
		}			
	}//end main:
		
	}

	//#### static Methoden #########################################
	
	//#######################################################
	//### Da diese Formate an mehreren Stellen verwendet werden .compute...Justified,  .compute...Jagged
	//### werden hier die Arrays einmal definiert durch diese Methoden.
	//#######################################################
	public static IEnumSetMappedLogStringFormatZZZ[] getFormatForComputeLine() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat = {
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	public static IEnumSetMappedLogStringFormatZZZ[] getFormatForComputeLine_withObject() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat = {
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	public static IEnumSetMappedLogStringFormatZZZ[] getFormatForComputeLineDate() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	public static IEnumSetMappedLogStringFormatZZZ[] getFormatForComputeLineDate_withObject() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	public static IEnumSetMappedLogStringFormatZZZ[] getFormatForComputeLineDateWithPosition_withObject() throws ExceptionZZZ{
		//Da die Position nicht an anderer Stelle ermittelt werden kann, sie hier in die Log-Strings aufnehmen.
		//Bei der Abarbeitung wird geprüft, ob der verwendete Tag "positioncurrent" vorhanden ist.
		//Wenn das der Fall ist, gib diesen an der durch die Formatanweisung festgelegten Position aus.
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 //Merke: Da nun alles STRING_BY... ist, muss man keine XML-Tags mehr aus dem String entfernen, wie mit:
		 //       sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_STRING_BY_XML,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,	
				 ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_XML,				 
		 };
		return iaFormat;
	}
	
	
	public static IEnumSetMappedLogStringFormatZZZ[] getFormatForComputeLineDateWithPositionXml_withObject() throws ExceptionZZZ{
		//Da die Position nicht an anderer Stelle ermittelt werden kann, sie hier in die Log-Strings aufnehmen.
		//Bei der Abarbeitung wird geprüft, ob der verwendete Tag "positioncurrent" vorhanden ist.
		//Wenn das der Fall ist, gib diesen an der durch die Formatanweisung festgelegten Position aus.
	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat= {
			ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_XML,
			ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_XML,
			ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_XML,
			ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_XML,
			//Merke: Die Methode aus ReflectCodeZZZ.getPositionCurrent stammt (nicht anders zu bekommen), ist die Quelle XML
			ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_XML,
			ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_XML_BY_XML,
			ILogStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_XML,
			ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_XML_BY_STRING,
			//Merke: Die Zeilenummer aus ReflectCodeZZZ.getPositionCurrent stammt (nicht anders zu bekommen), ist die Quelle XML
			ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_XML_BY_XML,
		};
		return iaFormat;
	}
	
	//#######################################################
	//### als einfache STRING Rueckgabe, basierend nur auf STRING Werte.
	//### Da PositionCurrent - XML ist, kann das hier nicht vorkommen.
	//#######################################################
	
	//+++ als moeglichst einfacher String.  
	public synchronized static String computeLine(String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLine();
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, sLog);
	}
	
	public synchronized static String computeLine(IEnumSetMappedLogStringFormatZZZ[]iaFormat, String sLog) throws ExceptionZZZ{		
    	String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, saLog);
	}
	

	//Merke: ohne diese sLog1, sLog2 Methode würde sLog1 nur als Object verwendet werden
	public synchronized static String computeLine(String sLog1, String sLog2) throws ExceptionZZZ { 
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLog2, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLine();
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, sLog1, sLog2);
	}
	
	public synchronized static String computeLine(IEnumSetMappedLogStringFormatZZZ[]iaFormat, String sLog1, String sLog2) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog2 = StringArrayZZZ.prepend(sLog2, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLog1, saLog2);
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, saLog);
	}
	
	public synchronized static String computeLine(String... sLogs) throws ExceptionZZZ {	
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLine();
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, saLog);
	}
	
	public synchronized static String computeLine(IEnumSetMappedLogStringFormatZZZ[]iaFormat, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, saLog);
	}
	
	public synchronized static String computeLine(Object obj, String sLog) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLine_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(IEnumSetMappedLogStringFormatZZZ[]iaFormat, Object obj, String sLog) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(Object obj, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLine_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, saLog);
	}	
	
	public synchronized static String computeLine(IEnumSetMappedLogStringFormatZZZ[]iaFormat, Object obj, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(Class classObj, String sLog) throws ExceptionZZZ {	
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLine_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(IEnumSetMappedLogStringFormatZZZ[]iaFormat, Class classObj, String sLog) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		
		return LogStringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(Class classObj, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Hole "auf Verdacht" die aufrufende Position. Xml deshalb, weil sich daraus die Details gezogen werden kann.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLine_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, saLog);
	}
	
	//##################################################################################
	//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
	//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
	//### sollte sie fehlen.
	//##################################################################################
	
	//+++ mit Datum
	public synchronized static String computeLineDate() throws ExceptionZZZ {			
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate();
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat);
	}

	public synchronized static String computeLineDate(String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate();
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, sLog);
	}
	
	//Merke: ohne diese sLog1, sLog2 Methode würde sLog1 nur als Object verwendet werden
	public synchronized static String computeLineDate(String sLog1, String sLog2) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate();
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, sLog1, sLog2);
	}
	
	
	public synchronized static String computeLineDate(String... sLogs) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate();
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, sLogs);
	}
	
	public synchronized static String computeLineDate(Object obj) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat);
	}
	
	public synchronized static String computeLineDate(Object obj, String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, sLog);
	}
	
	public synchronized static String computeLineDate(Object obj, String... sLogs) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, sLogs);
	}
	
	
	public synchronized static String computeLineDate(Class classObj, String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate_withObject();		 
		return LogStringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, sLog);
	}
	
	public synchronized static String computeLineDate(Class classObj, String... sLogs) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDate_withObject();
		return LogStringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, sLogs);
	}
	
	//#######################################################
	//### als einfache STRING Rueckgabe, aber teilweise basierende auf XML Werte.
	//### Merke1: Damit spart man sich ggfs. das Entfernen von XML-Tags.
	//### Merke2: Zeilennummer, etc aus der CodePosition kann nur als XML Wert zur Vefuegung gestellt werden.
	//#######################################################

	//Merke: ohne diese sLog1, sLog2 Methode würde sLog1 nur als Object verwendet werden
	public synchronized static String computeLineDateWithPosition(String sLog1) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLog1, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, saLog);
	}
	
	//Merke: ohne diese sLog1, sLog2 Methode würde sLog1 nur als Object verwendet werden
	public synchronized static String computeLineDateWithPosition(String sLog1, String sLog2) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog2 = StringArrayZZZ.prepend(sLog2, sPositionCalling);
		 
		String[] saLog1 = StringArrayZZZ.prepend(sLog1, saLog2);
		
		
		return LogStringFormatManagerZZZ.getInstance().compute(iaFormat, saLog1);
	}
	
	public synchronized static String computeLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, saLog);
	}
	
	public synchronized static String computeLineDateWithPosition(Object obj, String sLog1, String sLog2) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLog1, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, saLog);
	}	
	
	
	public synchronized static String computeLineDateWithPosition(Object obj, String... sLogs) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();
		
		 //Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		 String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		 //Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		 //String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		 String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		 
		 return LogStringFormatManagerZZZ.getInstance().compute(obj, iaFormat, saLog);
	}
	
	
	
	public synchronized static String computeLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();

		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPosition(Class classObj, String... sLogs) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(satemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, saLog);
	}
	
	
	//##########################################
	//### ALS XML RUECKGABE, daher .computeJagged(...) verwenden. 
	//### Bei .computeJustified(...) wird es zwar buendig gemacht, aber es werden die XML-Tags entfernt. 
	//###
	//### TODOGOON20251124;//Hier die Formattypen auch als XML_BY_XML zur Verfuegung stellen
	//###  
	//### ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_XML,
	//### ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_XML,
	//### ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_XML_BY_STRING,
	//##########################################
	//+++ mit CodePosition
	public synchronized static String computeLineDateWithPositionXml(Object obj, String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPositionXml_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().computeJagged(obj, iaFormat, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPositionXml(Object obj, String... sLogs) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPositionXml_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		return LogStringFormatManagerZZZ.getInstance().computeJagged(obj, iaFormat, saLog);
	}
	
	
	
	public synchronized static String computeLineDateWithPositionXml(Class classObj, String sLog) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPositionXml_withObject();		
		 
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(stemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().computeJagged(classObj, iaFormat, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPositionXml(Class classObj, String... sLogs) throws ExceptionZZZ {	
		IEnumSetMappedLogStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPositionXml_withObject();		
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml();
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(satemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		 
		return LogStringFormatManagerZZZ.getInstance().computeJagged(classObj, iaFormat, saLog);
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Biete die Log-Methoden auch static an, siehe ILogZZZ, bzw. AbstractObjectZZZ fuer den Code
	//+++++++++++++++++++++++++++++++++++++++++++++++
	public synchronized static void logProtocolStringStatic(String... sLogs) throws ExceptionZZZ{
		AbstractKernelLogZZZ.logProtocolStringStatic(null, sLogs);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
	
	public synchronized static void logProtocolStringStatic(String sLog) throws ExceptionZZZ{
		AbstractKernelLogZZZ.logProtocolStringStatic(null, sLog);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
	
	public synchronized static void logProtocolStringStatic(Object obj, String... sLogs) throws ExceptionZZZ{
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			
			if(obj==null) {
				for(String sLog : sLogs) {
					AbstractKernelLogZZZ.logProtocolStringStatic(sLog);
				}
			}else {
				for(String sLog : sLogs) {
					AbstractKernelLogZZZ.logProtocolStringStatic(obj, sLog);
				}	
			}
			
		}//end main:
	}
		
	public synchronized static void logProtocolStringStatic(Object obj, String sLog) throws ExceptionZZZ{
		String sLogUsed;
		sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, sLog);
		System.out.println(sLogUsed);
	}
	
	//++++++++++++++++++++++++++++++++++	
	public synchronized static void logProtocolStringStatic(IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		AbstractKernelLogZZZ.logProtocolStringStatic(null, ienumaMappedLogString, sLogs);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
	
	public synchronized static void logProtocolStringStatic(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		AbstractKernelLogZZZ.logProtocolStringStatic(null, ienumMappedLogString, sLog);//Merke: In der aehnlichen Methode von abstract Object (also nicht static) "this" statt null.
	}
		
	public synchronized static void logProtocolStringStatic(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		main:{
		if(ArrayUtilZZZ.isNull(sLogs)) break main;
		if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
			AbstractKernelLogZZZ.logProtocolStringStatic(sLogs);
			break main;
		}
		
		int iIndex=0;
		if(obj==null) {			
			for(String sLog : sLogs) {
				if(ienumaMappedLogString.length>iIndex) {
					AbstractKernelLogZZZ.logProtocolStringStatic(ienumaMappedLogString[iIndex],sLog);
					iIndex++;
				}else {
					AbstractKernelLogZZZ.logProtocolStringStatic(sLog);
				}
			}
		}else {
			for(String sLog : sLogs) {
				if(ienumaMappedLogString.length>iIndex) {
					AbstractKernelLogZZZ.logProtocolStringStatic(obj, ienumaMappedLogString[iIndex],sLog);
					iIndex++;
				}else {
					AbstractKernelLogZZZ.logProtocolStringStatic(sLog);
				}
			}			
		}
	}//end main:
	}
	
	public synchronized static void logProtocolStringStatic(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		String sLogUsed;
		if(obj==null) {
			sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(ienumMappedLogString, sLog);
		}else {
			sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, ienumMappedLogString, sLog);
		}
		System.out.println(sLogUsed);
	}
	
	
	
		
	//##############################################################
	private FileTextWriterZZZ createFileTextWriterInternal(String sFilepath) throws ExceptionZZZ {
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

	@Override
	public synchronized boolean WriteLine(String stemp){
		boolean bReturn = false;
		FileTextWriterZZZ objFileWriter;
		try {
			System.out.println(stemp);
			
			objFileWriter = this.getFileTextWriterObject();
			bReturn = objFileWriter.writeLine(stemp);						
		} catch (ExceptionZZZ e) {		
			e.printStackTrace();
		}	
		return bReturn;
	}

	//##################################################################################################
	
	@Override
	synchronized public boolean WriteLineDate(String sLog) throws ExceptionZZZ{
		return WriteLineDate_(this, sLog);
	}
	
	@Override
	synchronized public boolean WriteLineDate(String... sLogs) throws ExceptionZZZ{
		return WriteLineDate_(this, sLogs);
	}
	
	@Override
	synchronized public boolean WriteLineDate(Object obj, String sLog) throws ExceptionZZZ{
		return WriteLineDate_(obj, sLog);
	}
	
	synchronized private boolean WriteLineDate_(Object obj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = AbstractKernelLogZZZ.computeLineDate(obj, sLogs); //Darin wird die Zeile schon "bündig gemacht".		
		bReturn = WriteLine(sLine);
				
		return bReturn;
	}
	
	//######################################
	@Override
	synchronized public boolean WriteLineDateWithPosition(String sLog) throws ExceptionZZZ{
		return WriteLineDateWithPosition_(this.getClass(), 1, sLog);
	}
	
	@Override
	synchronized public boolean WriteLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{
		return WriteLineDateWithPosition_(classObj, 1, sLog);
	}
	
	synchronized private boolean WriteLineDateWithPosition_(Class classObj, int iStackTraceOffset, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = AbstractKernelLogZZZ.computeLineDate(classObj, "");
		
		//Jetzt die Position extra. Sie kommt ganz hintenan.
		int iLevelUsed = iStackTraceOffset + 1;		
		String sPosition = ReflectCodeZZZ.getPositionCurrentInFile(iLevelUsed);
		sLog = sLog + sPosition;
				
		//ggfs. mehrere Kommentartrenner auf mehrere Zeilen buendig aufteilen
		IStringJustifierZZZ objStringJustifier = SeparatorMessageStringJustifierZZZ.getInstance();
		sLine = LogStringFormaterUtilZZZ.justifyInfoPartAdded(objStringJustifier, sLine, sLog);
			
		bReturn = WriteLine(sLine);
		
		return bReturn;
	}
	

	//############################################
	@Override
	synchronized public boolean WriteLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		return WriteLineDateWithPosition_(obj, 1, sLog);
	}
	
	@Override
	synchronized public boolean WriteLineDateWithPosition(Object obj, int iStackTraceOffset, String sLog) throws ExceptionZZZ{
		return WriteLineDateWithPosition_(obj, iStackTraceOffset+1, sLog);
	}
	
	synchronized private boolean WriteLineDateWithPosition_(Object obj, int iStackTraceOffset, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;	
		main:{
		//Hier nicht die Position hinzunehmen. Wg. des Leerstring kommt sie dann VOR den Kommentar
		String sLine = AbstractKernelLogZZZ.computeLineDate(obj, "");
		
		//Jetzt die Position extra. Sie kommt ganz hintenan.
		int iLevelUsed = iStackTraceOffset + 1;		
		String sPosition = ReflectCodeZZZ.getPositionCurrentInFile(iLevelUsed);
		sLine = sLine + sPosition;
		
//		//ggfs. mehrere Kommentartrenner auf mehrere Zeilen buendig aufteilen
//		IStringJustifierZZZ objStringJustifier = SeparatorMessageStringJustifierZZZ.getInstance();
//		sLine = LogStringFormaterUtilZZZ.justifyInfoPartAdded(objStringJustifier, sLine, sLog);
//		
		bReturn = WriteLine(sLine);
		}//end main:
		return bReturn;
	}
	
	/** Merke: Beim XML String findet kein "Bündigmachen" mit dem Justifier statt. 
	 * @param obj
	 * @param stemp
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.11.2025, 20:26:58
	 */
	@Override
	synchronized public boolean WriteLineDateWithPositionXml(Object obj, String stemp) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = AbstractKernelLogZZZ.computeLineDateWithPositionXml(obj, stemp);
		bReturn = WriteLine(sLine);
		
		return bReturn;
	}
	
	@Override
	public void setFileObject(FileZZZ objFile) throws ExceptionZZZ{
		this.objFileZZZ = objFile;
	}
	
	@Override
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
	
	@Override
	public String getFilenameExpanded() throws ExceptionZZZ{
		return this.objFileZZZ.getNameExpandedCurrent();
	}
	
	@Override
	public String getFilename() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sLogFilename)) {
			String sLogFileNameFound=null;
			IKernelConfigZZZ objConfig = this.getConfigObject();
			if(objConfig!=null) {
				sLogFileNameFound = objConfig.getLogFileName();
			}
			
			if(StringZZZ.isEmpty(sLogFileNameFound)) {
				sLogFileNameFound =new String(sLOG_FILE_NAME_DEFAULT);
			}else {
				this.setFilename(sLogFileNameFound);
			}
		}
		return this.sLogFilename;
	}
		

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelLogZZZ#Write(java.lang.String)
	 */
	@Override
	public synchronized boolean Write(String stemp) throws ExceptionZZZ{
		boolean bReturn = false;
		FileTextWriterZZZ objFileWriter;
//		try {
			objFileWriter = this.getFileTextWriterObject();
			bReturn = objFileWriter.write(stemp); //Kein Zeilenumbruch.
			
			System.out.print(stemp); //Kein Zeilenumbruck
//		} catch (ExceptionZZZ e) {			
//			e.printStackTrace();
//		}	
		return bReturn;
	}



	//############################################
	//### Functions implemented by interface
	//.....
	@Override
	public void setFilename(String sLogFilename) throws ExceptionZZZ{
		this.sLogFilename = sLogFilename;
		
		//File Objekte wieder zurücksetzen
		this.objFileZZZ = null;
		this.objFileTextWriter = null;
	}
	
	@Override
	public void setDirectory(String sLogDirectorypath) throws ExceptionZZZ {
		
		//20190116: Suche nach dem Pfad. Er ist ggfs. als Serverapplication ohne den "src"-Ordner.
		File objDirectory = FileEasyZZZ.searchDirectory(sLogDirectorypath);
		if(objDirectory==null) {
			String sError = "Verzeichnis für das KernelLog  ='" + sLogDirectorypath +"' existiert nicht.";
			System.out.println(ReflectCodeZZZ.getPositionCurrent()+": " + sError);
			ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
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
	
	@Override
	public String getDirectory() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sLogDirectorypath)) {
			String sLogFileDirectoryFound=null;
			IKernelConfigZZZ objConfig = this.getConfigObject();
			if(objConfig!=null) {
				sLogFileDirectoryFound = objConfig.getLogDirectoryName(); 
			}
			
			if(StringZZZ.isEmpty(sLogFileDirectoryFound)) {
				sLogFileDirectoryFound =new String(sLOG_FILE_DIRECTORY_DEFAULT);
			}else {
				this.setDirectory(sLogFileDirectoryFound);
			}
		}
		return this.sLogDirectorypath;
	}
	
	@Override
	public IFileExpansionZZZ getFileExpansionObject() throws ExceptionZZZ{	
		if(this.getFlag(ILogZZZ.FLAGZ.USE_FILE_EXPANSION.name())) {
			if(this.objFileExpansion==null) {
				String sDir = this.getDirectory();			
				String sName = this.getFilename();
				FileZZZ objFileBase;
//				try {
					objFileBase = new FileZZZ(sDir,sName);
					this.objFileExpansion=new KernelFileExpansionZZZ(objFileBase);
//				} catch (ExceptionZZZ e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}			
			}
			return this.objFileExpansion;
		}else {
			return null;
		}
	}
	
	@Override
	public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion) throws ExceptionZZZ{
		this.objFileExpansion = objFileExpansion;
		
		//File Objekt aktualisieren
		FileZZZ objFile;
//		try {
			objFile = this.getFileObject();
			if(objFile!=null) {
				objFile.setFileExpansionObject(objFileExpansion);
			}
//		} catch (ExceptionZZZ e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
	}
	
	//### aus IKernelConfigUserZZZ
	@Override
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ{
		if(this.objConfig==null){
			this.objConfig = new ConfigZZZ(null);			
		}
		return this.objConfig;
	}
	
	@Override
	public void setConfigObject(IKernelConfigZZZ objConfig){
		this.objConfig = objConfig;
	}
}//end class
