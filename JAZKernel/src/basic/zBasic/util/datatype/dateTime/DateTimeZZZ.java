package basic.zBasic.util.datatype.dateTime;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;

/** Hole den Timestamp als einen String, der die Millisekunden seit dem 1. Januar 1970 repräsentiert.
 *   In einigen Methoden wird intern java.sql.Timestamp verwendet. Daher ist der Timestamp nicht zwingend Unique.
 *   In anderen Methoden wird intern mit AtomicLong gerechnet UND dieser Wert ggfs. sogar manipuliert. Dann sollte man einen Unique Wert erhalten.  
 * @author Fritz Lindhauer
 *
 */
public class DateTimeZZZ  implements IConstantZZZ {
	
	public static long  computeTimestamp() throws ExceptionZZZ{
		long lReturn = 0;
		main:{
			//Date objDate = new Date(); // Hole das aktuelle Datum
			//lReturn = DateTimeZZZ.computeTimestampByDate(objDate);
			
			long lTime = System.currentTimeMillis();
			Date objDate = new Date(lTime);
			lReturn = DateTimeZZZ.computeTimestampByDate(objDate);
			
		}//end main
		return lReturn;
	}
	
	public static long  computeTimestampByDate(Date objDate) throws ExceptionZZZ{
		long lReturn = 0;
		main:{
			if(objDate==null){
				ExceptionZZZ ez = new ExceptionZZZ("No Date object available.", iERROR_PARAMETER_MISSING, DateTimeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			Timestamp objTimestamp = new Timestamp(objDate.getTime());
			lReturn = objTimestamp.getTime();
			
		}//end main
		return lReturn;
	}
	
	public static String computeTimestampString() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			long lTimestamp = DateTimeZZZ.computeTimestamp();
			
			Long lngTimestamp = new Long(lTimestamp);
			sReturn = lngTimestamp.toString();
			
		}//end main
		return sReturn;
	}
	public static String computeTimestampStringByDate(Date objDate) throws ExceptionZZZ{
		String sReturn = null;
		main:{
				if(objDate==null){
					ExceptionZZZ ez = new ExceptionZZZ("No Date object available.", iERROR_PARAMETER_MISSING, DateTimeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				long lTimestamp = DateTimeZZZ.computeTimestampByDate(objDate);
				Long lngTimestamp = new Long(lTimestamp);
				sReturn = lngTimestamp.toString();				
				
		}
		return sReturn;
	}
	
	//###################
	//### UNIQUER TIMESTAMP, ausgehend von der aktuellen Systemzeit
	//siehe: https://stackoverflow.com/questions/9191288/creating-a-unique-timestamp-in-java
	private static final AtomicLong LAST_TIME_MS = new AtomicLong();
	private static long uniqueCurrentTimeMS() {
	    long now = System.currentTimeMillis();
	    while(true) {
	        long lastTime = LAST_TIME_MS.get();
	        if (lastTime >= now)
	            now = lastTime+1;
	        if (LAST_TIME_MS.compareAndSet(lastTime, now))
	            return now;
	    }
	}
	
	/** UNIQUER TIMESTAMP, ausgehend von der aktuellen Systemzeit
	 * @return
	 */
	public static long computeTimestampUnique(){
		long lReturn = 0;
		main:{
			lReturn = DateTimeZZZ.uniqueCurrentTimeMS();
		}
		return lReturn;
	}
	public static String computeTimestampUniqueString(){
		String sReturn = null;
		main:{
			long lTimestamp = DateTimeZZZ.computeTimestampUnique();
			Long lngTimestamp = new Long(lTimestamp);
			sReturn = lngTimestamp.toString();	
		}//end main
		return sReturn;
	}
	
	
	
	
	
}
