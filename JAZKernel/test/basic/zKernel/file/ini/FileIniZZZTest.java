package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.*;
import custom.zUtil.io.FileZZZ;

public class FileIniZZZTest extends TestCase {
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTest.ini");
	
	private final static String[] saValueTestGetAll ={"ValueA", "ValueB", "ValueC", "ValueD", "ValueE"} ;
	private Hashtable objHtSection;  
	
	private File objFile;
	private KernelZZZ objKernel;
	private LogZZZ objLog;
	
	/// +++ Die eigentlichen Test-Objekte
	private FileIniZZZ objFileIniInit;
	private FileIniZZZ objFileIniTest;
	

	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Eintr�ge werden immer neu geschrieben und nicht etwa angeh�ngt.
			//Merke: Erst wenn es �bberhaupt einen Test gibt, wird diese DAtei erstellt
			String sFileDirectoryUsed = strFILE_DIRECTORY_DEFAULT;
			String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFileDirectoryUsed, strFILE_NAME_DEFAULT );
			Stream objStreamFile = null;
			try{
				objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file			
			} catch (FileNotFoundException e) {
				sFileDirectoryUsed = "c:\\temp";
				sFilePathTotal = FileEasyZZZ.joinFilePathName(sFileDirectoryUsed, strFILE_NAME_DEFAULT );
				objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file
			} 
			objStreamFile.println(";This is a temporarily test file for FileIniZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println(";This file will be newly created by the setUp()-method of this JUnit Test class, every time before a testMethod will be invoked.");
			objStreamFile.println("#This is another commentline");
			objStreamFile.println("[Section A]");
			objStreamFile.println("Testentry1=Testvalue1");			
			objStreamFile.println("TestentryToBeDeleted=will be deleted by testStringPropertyDelete()");
			
			objStreamFile.println("[Section for testGetPropertyAll]");
			objStreamFile.println("Property1=");
			objStreamFile.println("Property2=");
			objStreamFile.println("Property3=");
			objStreamFile.println("Property4=");
			objStreamFile.println("Property5=");
			
			objStreamFile.println("[Section for testGetValueAll]");
			objStreamFile.println("PropertyA=" + saValueTestGetAll[0]);
			objStreamFile.println("PropertyB=" + saValueTestGetAll[1]);
			objStreamFile.println("PropertyC=" + saValueTestGetAll[2]);
			objStreamFile.println("PropertyD=" + saValueTestGetAll[3]);
			objStreamFile.println("PropertyE=" + saValueTestGetAll[4]);			
			
			objStreamFile.println("[Section for deletion]");
			objStreamFile.println("TestentryToBeDeleted=the one and only entry in this section. Will be deleted by testStringPropertyDelete()");
			
			objStreamFile.println("[Section for formula value]");
			objStreamFile.println("Value1=first value");
			
			objStreamFile.println("[Section for formula]");
			objStreamFile.println("Formula1=Das ist der '<Z>[Section for formula value]Value1</Z>' Wert.");
			
			objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched]");
			objStreamFile.println("Value1=first value global");
			objStreamFile.println("ValueGlobalOnly=second value global");
			objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched!01]");
			objStreamFile.println("Value1=first value systemnr 01");
			objStreamFile.println("ValueLocalOnly=local value systemnr 01");
			objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched!02]");
			objStreamFile.println("Value1=first value systemnr 02");
			
			objStreamFile.println("[Section for ProofSectionExists]");
			
			objStreamFile.close();
			
			
			objFile = new File(sFilePathTotal);
			
			//### Hashtable zum Setzen einer Beispielsection in testSetSection()
			objHtSection = new Hashtable();
			objHtSection.put("PropertySetA", "Testvalue set by hashtableA");
			objHtSection.put("PropertySetB", "Testvalue set by hashtableB");
			
			
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			objLog = objKernel.getLogObject();
			
			//### Die TestObjecte
			
			//An object just initialized
			//TODO Default construktor in der Klasse zur Verf�gung stellen objFileInit = new FileIniZZZ();
			
			String[] saFlag = {"init"};
			objFileIniInit = new FileIniZZZ(objKernel,  objFile, saFlag);
			
			//The main object used for testing
			objFileIniTest = new FileIniZZZ(objKernel, sFileDirectoryUsed, strFILE_NAME_DEFAULT, (String[]) null);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//END setup
	
	
	public void testFlagHandling(){
		//try{
		
			
			//TestKonfiguration pr�fen
			assertTrue(objFileIniInit.getFlag("init")==true);
			assertFalse(objFileIniTest.getFlag("init")==true); //Nun wäre init falsch
			

			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	/** Erweitertes Flag Handling. 
	 *   Das Ziel ist es gesetzte Flags an andere Objekte "Weiterzureichen"
	 * 
	 */
	public void testFlagPassHandling(){
		//A) Teste an dier Stelle die Funktionalitäten aus ObjectZZZ
		
//		try{
				
		//TestKonfiguration prüfen.
		//1. Hole alle FlagZ des Objekts
		String[] saTest01 = objFileIniInit.getFlagZ();
		assertNotNull(saTest01);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 2 FlagZ erwartet: Also nicht nur DEBUG und INIT.",saTest01.length>=3);
		
		//2. Hole alle FlagZ Einträge, die entsprechend true/false gesetzt sind.
		//Init - Object
		assertTrue(objFileIniInit.getFlag("init")==true);
		
		String[]saTest02 = objFileIniInit.getFlagZ(true);
		assertNotNull(saTest02);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur 1 FlagZ für 'true' erwartet: INIT.",saTest02.length==1);
				
		String[]saTest02b = objFileIniInit.getFlagZ(false);
		assertNotNull(saTest02b);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 1 FlagZ für 'false' erwartet: Also nicht nur DEBUG.",saTest02b.length>=2);
		
		objFileIniInit.setFlag("DEBUG", true);
		String[]saTest02c = objFileIniInit.getFlagZ(false);
		assertNotNull(saTest02c);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'false' erwartet.",saTest02c.length==saTest02b.length-1);
		
		
		//B) TESTE DIE FUNKTIONALITÄT DER FLAG - ÜBERGABE.
		
		
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}
		
	}
	
	
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testGetProperty(){
		try {
			//Erst testen, dass auch kein Leerwert kommt
			String sTestValueTemp =  objFileIniTest.getPropertyValue("Section A", "Testentry1").getValue();
			assertFalse("An empty entry was not expected for  the property 'Testentry1' in 'Section A'", sTestValueTemp.equals(""));
			
			//nun den Wert testen, wie er im setup definiert wurde
			assertEquals("Testvalue1", objFileIniTest.getPropertyValue("Section A", "Testentry1").getValue());
			
			//auch wenn es die Section überhaupt nicht gibt, darf kein Fehler entstehen
			assertEquals("", objFileIniTest.getPropertyValue("blablbllalbl SECTION DOES NOT EXIST", "Not existing entry").getValue());
			
			//NEU 20070306: Hier über eine Formel die Property auslesen
			objFileIniTest.setFlag("useFormula", false);
			String sTestValueFormula = objFileIniTest.getPropertyValue("Section for formula", "Formula1").getValue();
			assertEquals("Das ist der '<Z>[Section for formula value]Value1</Z>' Wert.", sTestValueFormula); 
			
			objFileIniTest.setFlag("useFormula", true);
			sTestValueFormula = objFileIniTest.getPropertyValue("Section for formula", "Formula1").getValue();
			assertEquals("Das ist der 'first value' Wert.", sTestValueFormula); //Schliesslich soll erst hier umgerechnet werden.
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	/** void, Test: Reading all properties of a section
	* Lindhauer; 23.04.2006 10:24:46
	 */
	public void testGetPropertyAll(){
		try{
			String[] saProperty = objFileIniTest.getPropertyAll("Section for testGetPropertyAll");
			assertNotNull("Unexpected: The array of all properties is null", saProperty);
			assertEquals(5, saProperty.length);
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Creating and changing entries of a section.
	* Lindhauer; 22.04.2006 13:18:49
	 */
	public void testSetProperty(){
		try {
			//Bestehenden Wert in einer bestehenden Section ändern
		

		    //Testen: Wird die neue Section gleichzeitig anlegen ???
		    //erst einmal nicht speichern
			assertTrue(objFileIniTest.setPropertyValue("Section B", "Testentry1","Testvalue1", false));
			
			//nun mal mit speichern, demnach wird alles andere auch gespeichert
			assertTrue(objFileIniTest.setPropertyValue("Section C", "Testentry1","Testvalue1", true));
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testDeleteProperty(){
			try{
			//eine property entfernen, die es nicht gib. Auch das soll true zur�ckliefern
			assertTrue(objFileIniTest.deleteProperty("Section A", "das gibt es nicht", true));
			
			//eine property entfernen, die im Setup daf�r erstellt worden ist
			assertTrue(objFileIniTest.deleteProperty("Section A", "TestentryToBeDeleted", true));
			
			//die einzige Property einer Sektion entfernen, die daf�r im Setup extra erstellt worden ist.
			assertTrue(objFileIniTest.deleteProperty("Section for deletion", "TestentryToBeDeleted", true));
			
			//assertTrue()
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
		
	public void testGetSectionAll(){
		try{
		String[] saSection = objFileIniTest.getSectionAll();
		assertNotNull("Unexpected: The array of all sections is null", saSection);
		assertEquals(10, saSection.length);
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
/** void, Test: Getting an unsorted Array of the values of a section.
* Lindhauer; 25.04.2006 08:23:16
 */
public void testGetValueAll(){
	try{
		String[] saValue = objFileIniTest.getPropertyValueAll("Section for testGetValueAll");
		assertNotNull("Unexpected: The array of all properties is null", saValue);
		assertEquals(5, saValue.length);
		for(int icount= 0; icount < saValue.length; icount++){
			assertEquals(saValueTestGetAll[icount], saValue[icount]);
		}
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


/** void, Test: Setting all property=value pairs of a section. Test: Saving it.
* Lindhauer; 25.04.2006 08:22:30
 */
public void testSetSectionSave(){
	try{
		//Anlegen der Section, Paramter true bedeutet, das zuvor eine ggf. vorhandene Section gleichen Namens entfernt wird.
		boolean btemp = objFileIniTest.setSection("Section for testSetSection", objHtSection, true);
		assertTrue("Setting the section 'Section for testSetSection' seems to have failed.",btemp);
				
		//Diese �nderung auch mal abspeichern. Damit ist sie am Ende der Datei.
		btemp = objFileIniTest.save();
		assertTrue("Saving the newly set section 'Section for testSetSection' seems to have failed.",btemp);
				
		//Nun testen, ob die Werte der Hashtable tats�chlich in der Datei stehen. Sie sollten angeh�ngt werden
		//TODO GOON: Klasse entwickeln, welche eine Datei "von hinten nach vorne liest."
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testProofSectionExists(){
	try{
		//Fehlerfall pr�fen
		boolean btemp = objFileIniTest.proofSectionExists("Section This does not exist");
		assertFalse("The section 'Section This does not exist' was expected not to exist",btemp);
				
		//Truefall pr�fen
		boolean btemp2 = objFileIniTest.proofSectionExists("Section for proofsectionexists");   //!!! Damit ist auch bewiesen, dass Gro�-/Kleinschreibung keine Rolle spielt 
		assertTrue("The section 'Section for ProofSectionExists' was expected to exist",btemp2);
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testGetPropertyValueSystemNrSearched(){
	/*
	      objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched!01]");
			objStreamFile.println("Value1=first value systemnr 01");
	 */
	try{
		String sValue = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched","Value1", "01").getValue();
		assertEquals("first value systemnr 01",sValue);
		
		//Wenn der Wert nicht gefunden wird, so wird im globalen nachgesehen
		sValue = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched","ValueGlobalOnly", "01").getValue();
		assertEquals("second value global",sValue);
		
		//Wird allerdings die Sektion schon lokal übergeben, so wird nur in der lokalen konfiguration nachgesehen
		IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched!01","ValueGlobalOnly", "01"); 
		sValue = objEntry.getValue();
		assertTrue("Ein richtiger Wert für die Property 'ValueGlobalOnly' wurde bei der Verwendung einer explizit mit einer Systemnr versehenen Section nicht erwartet.",StringZZZ.isEmpty(sValue));
		boolean bHasAnyValue = objEntry.hasAnyValue();
		assertFalse("Ein Wert für die Property 'ValueGlobalOnly' wurde bei der Verwendung einer explizit mit einer Systemnr versehenen Section nicht erwartet.",bHasAnyValue);
		
		//Nun die Suche nach einem Wert, den es nicht global gibt, muss trotzdem funktionieren
		sValue = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched","ValueLocalOnly", "01").getValue();
		assertEquals("local value systemnr 01",sValue);
		
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

}//END class
	
