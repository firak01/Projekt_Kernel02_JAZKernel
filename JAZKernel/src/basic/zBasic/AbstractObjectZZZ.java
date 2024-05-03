package basic.zBasic;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringZZZ;
import basic.zBasic.util.log.LogStringZZZ;
import basic.zKernel.KernelLogZZZ;

public abstract class AbstractObjectZZZ <T> implements Serializable, IObjectZZZ, ILogZZZ{
	private static final long serialVersionUID = 1L;	
	protected volatile ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt

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
	
	//### aus IObjectZZZ
	@Override
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	} 

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
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
	
	
	//### aus ILogZZZ
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		String sTemp = KernelLogZZZ.computeLineDate(this, sLog);
		System.out.println(sTemp);		
	}
	
	@Override
	public void logProtocolString(String sLog) throws ExceptionZZZ{
		String sLogUsed = LogStringZZZ.getInstance().compute(this, sLog);
		System.out.println(sLogUsed);
	}
	
	
	@Override
	public void logProtocolString(String[] saLog) throws ExceptionZZZ{
		main:{
			if(ArrayUtilZZZ.isEmpty(saLog)) break main;
			
			for(String sLog : saLog) {
				this.logProtocolString(sLog);
			}
		}//end main:
	}
	


	@Override
	public void logProtocolString(String sLog, IEnumSetMappedLogStringZZZ ienumMappedLogString)
			throws ExceptionZZZ {
		String sLogUsed = LogStringZZZ.getInstance().compute(sLog, ienumMappedLogString);
		System.out.println(sLogUsed);
	}

	@Override
	public void logProtocolString(String[] saLog, IEnumSetMappedLogStringZZZ[] ienumaMappedLogString)
			throws ExceptionZZZ {
		main:{
		if(ArrayUtilZZZ.isEmpty(saLog)) break main;
		if(ArrayUtilZZZ.isEmpty(ienumaMappedLogString)){
			this.logProtocolString(saLog);
			break main;
		}
		
		int iIndex=0;
		for(String sLog : saLog) {
			if(ienumaMappedLogString.length>iIndex) {
				this.logProtocolString(sLog,ienumaMappedLogString[iIndex]);
				iIndex++;
			}else {
				this.logProtocolString(saLog);
			}
		}
	}//end main:
	}
}
