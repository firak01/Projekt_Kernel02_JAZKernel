package basic.zBasic;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.LogStringZZZ;
import basic.zKernel.KernelLogZZZ;

public abstract class AbstractObjectZZZ <T> implements Serializable, Cloneable, IOutputDebugNormedZZZ, IObjectZZZ, ILogZZZ{
	private static final long serialVersionUID = 1L;	
	protected volatile ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	//fuer IOutputDebugNormedZZZ
	protected volatile String sDebugEntryDelimiterUsed = null; //zum Formatieren einer Debug Ausgabe
	
	//fuer IOutputDebugNormedWithKeyZZZ
	//protected volatile String sDebugKeyDelimiterUsed = null; 
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectZZZ() {	
		//Darf so nicht definiert werden, da dieser Konstruktor implizit immer aufgerufen wird. 
		//wenn dieser Defaultkonstruktor nicht explizit definiert ist in der Kindklasse 
		//this.setFlag("init", true);
		//
		//Lösung dies trotzdem zu setzten:
		//rufe im Default Konstuktor der Kindklasse auf:
		//super("init");		
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
	
	//### aus IObjectZZZ
	@Override
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	} 

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	//Meine Variante Objekte zu clonen
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
	
	//### aus IOutputDebugNormedWithKeyZZZ
//	@Override
//	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ {
//		String sKeyDelimiter = this.getDebugKeyDelimiter();
//		return this.computeDebugString(sKeyDelimiter, sEntryDelimiter);
//	}
	
//	@Override
//	public String computeDebugString(String sKeyDelimiter,String sEntryDelimiter) throws ExceptionZZZ{
//		return this.computeDebugString();
//	}
	
//	public String getDebugKeyDelimiter() {
//		String sKeyDelimiter;
//		if(this.sDebugKeyDelimiterUsed==null){
//			sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
//		}else{
//			sKeyDelimiter = this.sDebugKeyDelimiterUsed;
//		}
//		return sKeyDelimiter;
//	}
//	
//	@Override
//	public void setDebugKeyDelimiter(String sKeyDelimiter) {
//		this.sDebugKeyDelimiterUsed = sKeyDelimiter;
//	}
	
	//### aus ILogZZZ	
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		ObjectZZZ.logLineDate(this, sLog);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public synchronized void logProtocolString(String[] saLog) throws ExceptionZZZ{
		this.logProtocolString(this, saLog); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void logProtocolString(String sLog) throws ExceptionZZZ{
		this.logProtocolString(this, sLog); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void logProtocolString(Object obj, String[] saLog) throws ExceptionZZZ{
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;
			
			if(obj==null) {
				for(String sLog : saLog) {
					this.logProtocolString(sLog);
				}
			}else {
				for(String sLog : saLog) {
					this.logProtocolString(obj, sLog);
				}	
			}
			
		}//end main:
	}
	
	@Override
	public synchronized void logProtocolString(Object obj, String sLog) throws ExceptionZZZ{
		String sLogUsed;
		if(obj==null) {
			sLogUsed = LogStringZZZ.getInstance().compute(sLog);			
		}else {
			sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog);			
		}
		System.out.println(sLogUsed);
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public synchronized void logProtocolString(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
		this.logProtocolString(this, saLog, ienumaMappedLogString); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void logProtocolString(String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
		this.logProtocolString(this, sLog, ienumMappedLogString); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void logProtocolString(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				this.logProtocolString(saLog);
				break main;
			}
			
			int iIndex=0;
			if(obj==null) {			
				for(String sLog : saLog) {
					if(ienumaMappedLogString.length>iIndex) {
						this.logProtocolString(sLog,ienumaMappedLogString[iIndex]);
						iIndex++;
					}else {
						this.logProtocolString(saLog);
					}
				}
			}else {
				for(String sLog : saLog) {
					if(ienumaMappedLogString.length>iIndex) {
						this.logProtocolString(obj, sLog,ienumaMappedLogString[iIndex]);
						iIndex++;
					}else {
						this.logProtocolString(saLog);
					}
				}			
			}
		}//end main:
	}
	
	@Override
	public synchronized void logProtocolString(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
		String sLogUsed;
		if(obj==null) {
			sLogUsed = LogStringZZZ.getInstance().compute(sLog, ienumMappedLogString);
		}else {
			sLogUsed = LogStringZZZ.getInstance().compute(obj, sLog, ienumMappedLogString);
		}
		System.out.println(sLogUsed);
	}
	
	
}
