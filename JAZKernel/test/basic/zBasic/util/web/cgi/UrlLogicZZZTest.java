package basic.zBasic.util.web.cgi;


import basic.zBasic.ExceptionZZZ;
import  basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.web.cgi.UrlLogicZZZ;
import junit.framework.TestCase;

public class UrlLogicZZZTest extends TestCase{

//+++ Die eigentlichen Test-Objekte
	private UrlLogicZZZ objUrl;  
	
	protected void setUp(){
		//try {			
			
	
			//### Die TestObjecte
			
			//An object just initialized, only for writing
			objUrl = new UrlLogicZZZ(); 

			
			
		//} catch (ExceptionZZZ ez) {
		//	fail("Method throws an exception." + ez.getMessageLast());
		//} 
	}//END setup
	
	
	public void testGetHost(){
		try{
			String stemp;
			
			
			//1. normal, mit protokoll
			String sUrl = "http://fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			
			stemp=objUrl.getHost();
			assertEquals("fgl02", stemp);
			
			//2. ohne Protokoll....
			sUrl = "fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			stemp=objUrl.getHost();
			assertEquals("fgl02", stemp);
			
			//3. mit fehlerhaftem Protokoll, soll eine Exception geworfen werden
			sUrl = "bla://fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			try{
				stemp=objUrl.getHost();
				fail("Method should throw an exception. Because of unknown protocol.");
			}catch(ExceptionZZZ ez){}
			
		
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} //*/
	
	public void testHasProtocol(){
		try{
			//1. normal, mit protokoll
			String sUrl = "http://fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			assertTrue(objUrl.hasProtocol());
			
			//2. ohne Protokoll....
			sUrl = "fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			assertFalse(objUrl.hasProtocol());
			
	//		3. mit fehlerhaftem Protokoll, aber das ist hier nicht gefragt
			sUrl = "bla://fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			assertTrue(objUrl.hasProtocol());
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testGetPath(){
		try{
			//1. normal, mit protokoll
			String sUrl = "http://fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			assertEquals("/db/fgl/VIA/via_Application.nsf", objUrl.getPath());
			
			//2. ohne Protokoll....
			sUrl = "fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			assertEquals("/db/fgl/VIA/via_Application.nsf", objUrl.getPath());
			
	//		3. mit fehlerhaftem Protokoll, aber das ist hier nicht gefragt
			sUrl = "bla://fgl02/db/fgl/VIA/via_Application.nsf?Opendatabase";
			objUrl.setUrl(sUrl);
			assertEquals("/db/fgl/VIA/via_Application.nsf", objUrl.getPath());
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}
