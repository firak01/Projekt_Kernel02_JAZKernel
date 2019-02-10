package basic.zBasic.util;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IUrlLogicZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.web.cgi.UrlLogicZZZ;

public abstract class UrlLogicBaseZZZ implements IUrlLogicZZZ{	
	public static final String sURL_SEPARATOR_PROTOCOL = "://";
	public static final String sURL_SEPARATOR_PROTOCOL_FILE = ":///"; //Irgendwie ein Slash mehr, warum?
	private String sUrl;
	
	public UrlLogicBaseZZZ(){		
	}
	
	public UrlLogicBaseZZZ(String sUrl){
		this.setUrl(sUrl);
	}
	
	public static String getProtocol(String sUrl) throws ExceptionZZZ{		
		/* Besser wäre es  diese Methode abstract zu machen 
		  als einen ExceptionZZZ - Hinweis auszugeben. Wie aktell gemacht wird..
		 ABER: static Methoden können wohl erst ab Java 8.0 abstract sein!!!
		*/
		
		//Hinweis darauf, dass diese Methode überschrieben werden muss !!!
		ExceptionZZZ ez = new ExceptionZZZ(sERROR_ZFRAME_METHOD, iERROR_ZFRAME_METHOD, UrlLogicBaseZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
		throw ez;		
	}

	//### GETTER / SETTER ###############
	@Override	
		public String getUrl(){
			return this.sUrl; 
		}

	@Override
	public void  setUrl(String sUrl){
		this.sUrl = sUrl;
	}
	
	/** Prüft, ob ein Protokoll in der URL vorhanden ist (also etwas vor :// steht).
	 *   !!! Damit ist noch nicht gesagt, dass es ein bekanntes / g�ltiges Protokol ist.
	 *   
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 04.04.2009 11:32:02
	 */
	public boolean hasProtocol() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sUrl = this.getUrl();
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			bReturn = UrlLogicBaseZZZ.hasProtocol(sUrl);
			
		}//END main:
		return bReturn;
	}

	/** Prüft, ob rechts neben einem :// String noch ein Wert steht
	* @param sUrl
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 05.04.2009 09:38:22
	 */
	public static boolean hasProtocol(String sUrl) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			bReturn = StringZZZ.contains(sUrl, sURL_SEPARATOR_PROTOCOL);
			
			if(bReturn){
				String stemp = StringZZZ.left(sUrl, sURL_SEPARATOR_PROTOCOL);
				if(StringZZZ.isEmpty(stemp)) bReturn = false;
			}			
		}
		return bReturn;
	}
}
