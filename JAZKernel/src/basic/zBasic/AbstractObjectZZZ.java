package basic.zBasic;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.LogStringFormatManagerZZZ;
import basic.zBasic.util.log.LogStringFormaterZZZ;
import custom.zKernel.ILogZZZ;
import custom.zKernel.LogSingletonZZZ;

public class AbstractObjectZZZ<T> implements IObjectZZZ, IOutputDebugNormedZZZ, IObjectPositionLogZZZ{
	private static final long serialVersionUID = 4785854649300281154L;

	//fuer IOutputDebugNormedZZZ
	protected volatile String sDebugEntryDelimiterUsed = null; //zum Formatieren einer Debug Ausgabe
	
	//fuer IOutputDebugNormedWithKeyZZZ
	//protected volatile String sDebugKeyDelimiterUsed = null; 
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	public AbstractObjectZZZ() {		
	}	
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	@Override
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	

	//### aus Clonable
	@Override
	//https://www.geeksforgeeks.org/clone-method-in-java-2/
	//Hier wird also "shallow clone" gemacht. Statt einem deep Clone.
	//Beim Deep Clone müssten alle intern verwendeten Objekte ebenfalls neu erstellt werden.	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	
	//Meine Variante Objekte zu clonen, aber erzeugt nur einen "Shallow Clone".
	@Override
	public Object clonez() throws ExceptionZZZ {
		try {
			return this.clone();
		}catch(CloneNotSupportedException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
				
		}
	}
		
		
	//### aus IOutputDebugNormedZZZ
	@Override
	public String computeDebugString() throws ExceptionZZZ{
		return this.toString();
	}
	
	@Override
	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ {
		return this.computeDebugString() + sEntryDelimiter;
	}
	
	@Override
	public String getDebugEntryDelimiter() {
		String sEntryDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sDebugEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}
	
	@Override
	public void setDebugEntryDelimiter(String sEntryDelimiter) {
		this.sDebugEntryDelimiterUsed = sEntryDelimiter;
	}
			
	//### aus ILogZZZ, Merke: Dazu gibt es jeweils auch eine static-Methode fuer die Klasse als Argument.	
	@Override
	public synchronized void logLineDate(String sLog) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLog);
	}

	@Override
	public synchronized void logLineDateWithPosition(String sLog) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLog);
	}
	
	@Override
	public synchronized void logLineDate(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLogs);
	}
	
	@Override
	public synchronized void logLineDateWithPosition(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.logLineDateWithPosition(this, sLogs);
	}
			
	//#########################################
	//### log Protocol bedeutete, das dies (falls möglich) in einen Protokolldatei geschrieben wird.
	//### Also sind alle System.outs zu ersetzten durch die Arbeit mit einme LogZZZ-Objekt
	//#########################################
	@Override
	public synchronized void logProtocol(String sLog) throws ExceptionZZZ {
		this.logProtocol(this, sLog); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void logProtocol(String... sLogs) throws ExceptionZZZ{
		this.logProtocol(this, sLogs); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void logProtocol(Object obj, String sLog) throws ExceptionZZZ {
		String sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, sLog);						
		System.out.println(sLogUsed);
		
		ILogZZZ objLog = LogSingletonZZZ.getInstance();
		objLog.WriteLine(sLogUsed);
	}
	
	@Override
	public synchronized void logProtocol(Object obj, String... sLogs) throws ExceptionZZZ{
		String sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, sLogs);						
		System.out.println(sLogUsed);
		
		ILogZZZ objLog = LogSingletonZZZ.getInstance();
		objLog.WriteLine(sLogUsed);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public synchronized void logProtocol(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		this.logProtocol(this, ienumMappedLogString, sLog); //Merke: In der aehnlichen Methode von KerneleLogZZZ (also static) "null" statt this
	}
	
	@Override
	public void logProtocol(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		this.logProtocol(this, ienumaMappedLogString, sLogs);
	}
	
	@Override
	public synchronized void logProtocol(IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		this.logProtocol(this, ienumaMappedLogString, sLogs); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void logProtocol(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		this.logProtocol(ienumaMappedLogString, sLogs);
	}
	
	@Override
	public synchronized void logProtocol(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				this.logProtocol(sLogs);
				break main;
			}
			
			int iIndex=0;
			if(obj==null) {			
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						this.logProtocol(ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						this.logProtocol(sLog);
					}
				}
			}else {
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						this.logProtocol(obj, ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						this.logProtocol(sLog);
					}
				}			
			}
		}//end main:
	}
	
	@Override
	public synchronized void logProtocol(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		String sLogUsed;
		if(obj==null) {
			sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(sLog, ienumMappedLogString);
		}else {
			sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, ienumMappedLogString, sLog);
		}
		System.out.println(sLogUsed);
		
		ILogZZZ objLog = LogSingletonZZZ.getInstance();
		objLog.WriteLine(sLogUsed);
	}
	
	//############ ALLE METHODEN NUN AUCH NOCH MIT POSITIONSANGABE

	@Override
	public synchronized void logProtocolWithPosition(String... sLogs) throws ExceptionZZZ{
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		this.logProtocol(this, saLog);
	}
	
	@Override
	public synchronized void logProtocolWithPosition(String sLog) throws ExceptionZZZ{
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		this.logProtocol(this, saLog);
	}
			
	@Override
	public synchronized void logProtocolWithPosition(Object obj, String... sLogs) throws ExceptionZZZ{
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		this.logProtocol(obj, saLog); 
	}
	
	@Override
	public synchronized void logProtocolWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		this.logProtocol(obj, saLog); 
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public void logProtocolWithPosition(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		this.logProtocol(this, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void logProtocolWithPosition(IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		this.logProtocol(this, ienumaMappedLogString, saLog); 
	}
	
	@Override
	public synchronized void logProtocolWithPosition(IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		this.logProtocol(this, ienumaMappedLogString, saLog); 
	}
	
	@Override
	public void logProtocolWithPosition(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		
		IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		this.logProtocol(this, ienumMappedLogString, saLog);
	}
	
	@Override
	public synchronized void logProtocolWithPosition(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
		this.logProtocol(this, ienumaMappedLogString, saLog); 
	}
	
	@Override
	public synchronized void logProtocolWithPosition(Object obj, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
		this.logProtocol(this, ienumMappedLogString, saLog); 
	}	
}
