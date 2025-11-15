package basic.zBasic;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.LogStringFormatManagerZZZ;
import basic.zBasic.util.log.LogStringFormaterZZZ;

public class AbstractObjectZZZ<T> implements IObjectZZZ, IOutputDebugNormedZZZ, ILogZZZ{
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
			ObjectZZZ.logLineDateWithPosition(this, sLog);
		}
		
		@Override
		public synchronized void logLineDateWithPosition(String[] saLog) throws ExceptionZZZ {
			ObjectZZZ.logLineDateWithPosition(this, saLog);
		}
				
		//++++++++++++++++++++++++++++++++++++++++++++++++
		
		@Override
		public synchronized void logProtocol(String[] saLog) throws ExceptionZZZ{
			this.logProtocol(this, saLog); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
		}
		
		@Override
		public synchronized void logProtocol(String sLog) throws ExceptionZZZ{
			this.logProtocol(this, sLog); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
		}
		
		@Override
		public synchronized void logProtocol(Object obj, String sLog) throws ExceptionZZZ{
			String sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, sLog);						
			System.out.println(sLogUsed);
		}
		
		@Override
		public synchronized void logProtocol(Object obj, String[] saLog) throws ExceptionZZZ{
			String sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, saLog);						
			System.out.println(sLogUsed);
		}

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		@Override
		public synchronized void logProtocol(String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			this.logProtocol(this, sLog, ienumMappedLogString); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
		}
		
		@Override
		public void logProtocol(String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			this.logProtocol(this, saLog, ienumaMappedLogString);
		}
		
		@Override
		public synchronized void logProtocol(String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			this.logProtocol(this, saLog, ienumaMappedLogString); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
		}
		
		
		
		@Override
		public synchronized void logProtocol(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			this.logProtocol(saLog, ienumaMappedLogString);
		}
		
		@Override
		public synchronized void logProtocol(Object obj, String[] saLog, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			main:{
				if(ArrayUtilZZZ.isNull(saLog)) break main;
				if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
					this.logProtocol(saLog);
					break main;
				}
				
				int iIndex=0;
				if(obj==null) {			
					for(String sLog : saLog) {
						if(ienumaMappedLogString.length>iIndex) {
							this.logProtocol(sLog,ienumaMappedLogString[iIndex]);
							iIndex++;
						}else {
							this.logProtocol(saLog);
						}
					}
				}else {
					for(String sLog : saLog) {
						if(ienumaMappedLogString.length>iIndex) {
							this.logProtocol(obj, sLog,ienumaMappedLogString[iIndex]);
							iIndex++;
						}else {
							this.logProtocol(saLog);
						}
					}			
				}
			}//end main:
		}
		
		@Override
		public synchronized void logProtocol(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			String sLogUsed;
			if(obj==null) {
				sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(sLog, ienumMappedLogString);
			}else {
				sLogUsed = LogStringFormatManagerZZZ.getInstance().compute(obj, sLog, ienumMappedLogString);
			}
			System.out.println(sLogUsed);
		}
		
		//############ ALLE METHODEN NUN AUCH NOCH MIT POSITIONSANGABE
		@Override
		public synchronized void logProtocolWithPosition(String[] saLogIn) throws ExceptionZZZ{
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
			this.logProtocol(this, saLog);
		}
		
		@Override
		public synchronized void logProtocolWithPosition(String sLog) throws ExceptionZZZ{
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
			this.logProtocol(this, saLog);
		}
		
		@Override
		public synchronized void logProtocolWithPosition(Object obj, String[] saLogIn) throws ExceptionZZZ{
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
			this.logProtocol(obj, saLog); 
		}
		
		@Override
		public synchronized void logProtocolWithPosition(Object obj, String sLog) throws ExceptionZZZ{
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
			this.logProtocol(obj, saLog); 
		}
		
		

	

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		@Override
		public void logProtocolWithPosition(String[] saLogIn, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
			
			IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			this.logProtocol(this, saLog, ienumaMappedLogString);
		}
		
		@Override
		public synchronized void logProtocolWithPosition(String[] saLogIn, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
			this.logProtocol(this, saLog, ienumaMappedLogString); 
		}
		
		@Override
		public synchronized void logProtocolWithPosition(String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
			
			IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			this.logProtocol(this, saLog, ienumaMappedLogString); 
		}
		
		@Override
		public void logProtocolWithPosition(Object obj, String[] saLogIn, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
			
			IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			this.logProtocol(this, saLog, ienumMappedLogString);
		}
		
		@Override
		public synchronized void logProtocolWithPosition(Object obj, String[] saLogIn, IEnumSetMappedLogStringFormatZZZ[] ienumaMappedLogString) throws ExceptionZZZ {
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
			this.logProtocol(this, saLog, ienumaMappedLogString); 
		}
		
		@Override
		public synchronized void logProtocolWithPosition(Object obj, String sLog, IEnumSetMappedLogStringFormatZZZ ienumMappedLogString) throws ExceptionZZZ {
			String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
			String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
			this.logProtocol(this, saLog, ienumMappedLogString); 
		}
}
