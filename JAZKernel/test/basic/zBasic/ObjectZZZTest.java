package basic.zBasic;

import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import junit.framework.TestCase;

public class ObjectZZZTest extends TestCase{
	
	private DummyTestObjectWithFlagZZZ objObjectTest = null;
	private DummyTestObjectWithFlagOnFlagListeningZZZ objObjectTestListening = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new DummyTestObjectWithFlagZZZ();
			objObjectTestListening = new DummyTestObjectWithFlagOnFlagListeningZZZ();
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testConstructor(){
		try{
			//Init - Object
			String[] saFlag = {"init"};
			DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ(saFlag);
			assertTrue(objObjectInit.getFlag("init")==true); 
			
			
			//TestKonfiguration pr�fen
			assertFalse(objObjectTest.getFlag("init")==true); //Nun wäre init falsch
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetFlagZ(){
		try{
		//Init - Object
		String[] saFlag = {"init"};
		DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ(saFlag);
		assertTrue(objObjectInit.getFlag("init")==true); 
		
		
		//TestKonfiguration prüfen.
		//1. Hole alle FlagZ des Objekts
		String[] saTest01 = objObjectInit.getFlagZ();
		assertNotNull(saTest01);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarchie 3 FlagZ erwartet: DEBUG und INIT.",saTest01.length==3);
		assertTrue(saTest01[0].equals("DUMMY"));
		assertTrue(saTest01[1].equals("DEBUG"));
		assertTrue(saTest01[2].equals("INIT"));
		
		
		//2. Hole alle FlagZ Einträge, die entsprechend true/false gesetzt sind.
		String[]saTest02 = objObjectInit.getFlagZ(true);
		assertNotNull(saTest02);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur 1 FlagZ für 'true' erwartet: INIT.",saTest02.length==1);
				
		String[]saTest02b = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02b);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie 2 FlagZ für 'false' erwartet: DEBUG.",saTest02b.length==2);
		assertTrue(saTest02b[0].equals("DUMMY"));
		assertTrue(saTest02b[1].equals("DEBUG"));
		
		objObjectInit.setFlag("DEBUG", true);
		String[]saTest02c = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02c);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'false' erwartet.",saTest02c.length==saTest02b.length-1);
				
		objObjectInit.setFlag("DUMMY", true);
		String[]saTest02d = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02d);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT KEIN FLAG MEHR für 'false' erwartet.",saTest02d.length==0);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
		
	}
	
	
	public void testSetFlagZ_listening(){
		try{
			//Init - Object
			String[] saFlag = {"init"};
			DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ();
			DummyTestObjectWithFlagOnFlagListeningZZZ objObjectInitListening = new DummyTestObjectWithFlagOnFlagListeningZZZ();
			
			//Das Objekt mit der "Faehigkeit" Listing am Objekt mit Flag registrieren.
			//Merke: Jedes Objekt mit Flag hat automatisch ein eingebauts SenderObjekt
			objObjectInit.registerForFlagEvent(objObjectInitListening);
			
			
			//1. +++ Reagieren auf einen fremden Wert
			//!!! Wichtig nun ein Flag Setzen
			objObjectInit.setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST, true);
			
			//Merke: das DummyTestObjekt hat eine Eigenschaft, die nur nach dem "erfolgreichen hoeren" gesetzt ist.
			String sValue = objObjectInitListening.getValueDummyByFlagEvent();
			assertNotNull("Ein Wert sollte in dem flagChanged() Event gesetzt worden sein", sValue);
			

			String sLog = ReflectCodeZZZ.getPositionCurrent()+"Per Event empfangener Wert aus dem setFlag='"+sValue+"'";
			System.out.println(sLog);
			assertEquals(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST.name(), sValue);
			
			objObjectInit.unregisterForFlagEvent(objObjectInitListening);
			
			//########################################################################
			//2. +++ Reagieren auf einen eigenen Wert
			objObjectInitListening.setValueDummyByFlagEvent(null);
			objObjectInitListening.setFlag(IListenerObjectFlagZsetZZZ.FLAGZ.REGISTER_SELF_FOR_EVENT, true);
			
			 //Ein anderes Objekt wird registriert. Aber wg. dem Flag wird das eigene Objekt auch registriert und hört das Objekt auf sich selbst auch!!!
			objObjectInitListening.registerForFlagEvent(objObjectTestListening);

			String sValue02 = objObjectInitListening.getValueDummyByFlagEvent();
			assertNull("Noch sollte kein Wert in dem flagChanged() Event gesetzt worden sein", sValue02);
			
			
			objObjectInitListening.setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST, true);
			sValue02 = objObjectTestListening.getValueDummyByFlagEvent();
			assertNotNull("Jetzt sollte ein Wert in dem flagChanged() Event gesetzt worden sein", sValue02);
			
			String sLog02 = ReflectCodeZZZ.getPositionCurrent()+"Per Event empfangener Wert aus dem EIGENEN setFlag='"+sValue02+"'";
			System.out.println(sLog02);
			assertEquals(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST.name(), sValue02);
			
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}

	

}//END Class
