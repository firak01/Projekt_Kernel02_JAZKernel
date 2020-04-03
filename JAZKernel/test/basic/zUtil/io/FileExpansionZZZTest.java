package basic.zUtil.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.DummyObjectZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.TxtReaderZZZ;
import junit.framework.TestCase;
import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;

public class FileExpansionZZZTest extends TestCase {	
	private FileExpansionZZZ objExpansionTest;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglkernel\\kerneltest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTestExpansion.txt");
	

	protected void setUp(){
		try {			
			
			//DEFINITION DER BASISDATEI... Merke: OHNE ANSPRUCH DARAUF, dass sie wirklich existiert.
			//Eine Beispieldatei. Mit der Basisdatei kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erhält.
			String sFilePathUsed = null;		
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathUsed = strFILE_DIRECTORY_DEFAULT;
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    
			    sFilePathUsed = sPathEclipse + File.separator + "test";		   
			}
			
			//NEIN: Nicht pauschal Dateien erzeugen. Das sollte den einzelnen Tests überlassen bleiben.
//			String sFilePathTotal =  FileEasyZZZ.joinFilePathName(sFilePathUsed, strFILE_NAME_DEFAULT );			
//			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file
//			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
//			objStreamFile.close();

			//The main object used for testing
			FileZZZ objFileTest = new FileZZZ(sFilePathUsed, strFILE_NAME_DEFAULT);
			objExpansionTest = new FileExpansionZZZ(objFileTest);
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}		
	}//END setup
	
	public void testFlagZ(){
//		try{
		boolean  bExists = objExpansionTest.proofFlagZExists("NIXDA");
		assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
		
		boolean bSetted = false;
			try{
				bSetted = objExpansionTest.setFlagZ("NIXDA", true);
				assertFalse("Setting an unavailable FLAGZ 'NIXDA' should return false",bSetted);
			} catch (ExceptionZZZ ez) {
				fail("Setting an unavailable FLAGZ should NOT throw an error.");		
			}
			
			//TestKonfiguration prüfen
			assertTrue(objExpansionTest.getFlag("init")==false); //Nun wäre init falsch		
			
			//An object just initialized
			FileExpansionZZZ objFileExpansionInit = new FileExpansionZZZ();
			assertTrue(objFileExpansionInit.getFlag("init")==true);
			
			//Flags aus der FileZZZ-Klasse
			assertTrue(objExpansionTest.getFlag(KernelFileExpansionZZZ.FLAGZ.FILE_CURRENT_FOUND.name())==false);
			
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
		
	}
		
	/** void, Tests: Validating the result of computing a expansion. 
	 * a) Computing the biggest expansion number which could be created, by a given number of digits 
	* Lindhauer; 21.04.2006 09:51:08
	 */
	public void testExpansionLookalike(){
//		try {
		//wichtig: Ich will die Gewissheit haben, dass das auch mit anderen Werten als dem Standardfall von 3 Ziffern funktioniert
		//Zu beachten ist, das die Funktion einen String zur�ckliefert.
		
		assertEquals("99",FileExpansionZZZ.getExpansionMax(2));		
		assertEquals("999",FileExpansionZZZ.getExpansionMax(3));
		assertEquals("9999",FileExpansionZZZ.getExpansionMax(4));
		assertEquals("99999",FileExpansionZZZ.getExpansionMax(5));
		
		//Hier wird eine F�llvariable zur Berechnung verwendet.
		//Dies soll auch wieder mit den unterschiedlichsten Werten m�glich sein
		objExpansionTest.setExpansionLength(0);
		assertEquals("",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(1);
		assertEquals("2",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(3);
		assertEquals("002",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(4);
		assertEquals("0002",objExpansionTest.computeExpansion("0",2));
		
		//Falls ein anderes F�llzeichen �bergeben werden soll
		// Hier 3 Unterstriche vor der Ziffer
		assertEquals("___2",objExpansionTest.computeExpansion("_",2));
		
		// Hier 2 Unterstriche und die Zahl ist 2 stellig
		assertEquals("__32",objExpansionTest.computeExpansion("_",32));
		
//		 Hier kein Unterstrich und die Zahl ist 4 stellig
		assertEquals("4321",objExpansionTest.computeExpansion("_",4321));
		
		
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}
	}
	
	public void testIterateExpansionAll(){
		try {
		//######## Erstellen der Testdateien
		String sFilePathUsed = null;		
		if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
			sFilePathUsed = strFILE_DIRECTORY_DEFAULT;
		}else{
			//Eclipse Workspace
			File f = new File("");
		    String sPathEclipse = f.getAbsolutePath();
		    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
		    
		    sFilePathUsed = sPathEclipse + File.separator + "test";		   
		}
		
		//Variante OHNE Basisdatei		
		String sFilename = FileEasyZZZ.getNameOnly(strFILE_NAME_DEFAULT);
		String sEnding = FileEasyZZZ.getNameEnd(strFILE_NAME_DEFAULT);
		if(!StringZZZ.isEmpty(sEnding)) {
			sEnding = FileEasyZZZ.sFILE_ENDING_SEPARATOR + sEnding;
		}
		
		//Die einzelnen Tests immmer mit verschiedenenen Varianten des Arrays ( Elemente: Lücken in der Folge und Anzahl) durchführen.
		//Zudem mit einer beliebig häufigen Nutzung von next(); mit oder ohne hasNext();
		//String[] saString = {"001","002","003","004","005","006"};
		//String[] saString = {"002","003","004","005","006","007"};
		String[] saString = {"002","004","005","006","007","008"};
		
		
		
		
		
		String[] saFileName = StringArrayZZZ.plusString(saString, sFilename, "BEFORE");
		String[] saFileNameWithEnding = StringArrayZZZ.plusString(saFileName, sEnding, "BEHIND");
		
		for(int iCount=0;iCount<=saFileNameWithEnding.length-1;iCount++) {
			String sFileNameWithEnding = saFileNameWithEnding[iCount];
			String sFilePathTotal =  FileEasyZZZ.joinFilePathName(sFilePathUsed, sFileNameWithEnding );			
			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file
			objStreamFile.println("This is a temporarily test file: " + iCount);      //Now the File is created
			objStreamFile.close();
		}
		
		//TODO GOON: NACH DEM TEST ALLE DATEIEN LÖSCHEN!!!!
		
		
		
		
		//######## Der eigentliche Test
		//TODO GOON
		//A) Schleife mit hasNext() und 1x darin next()
		//B) Schleife mit hasNext() und 2x darin next()
		//C) Schleife mit hasNext() imd 3x darin next()
		//D) Ohne Schleife, nur 3x hintereinder next() aufrufen.
		int iCounter=0;
		objExpansionTest.setExpansionFilling('0');
		objExpansionTest.setExpansionLength(3);
		Iterator<String> itExpansion = objExpansionTest.iterator();		
		while(itExpansion.hasNext()) {
			iCounter++;			
			String sExpansion = (String) itExpansion.next();			
			System.out.print("Expansion: '" + sExpansion + "', ");
			if(iCounter%10==0)System.out.println();
			
			//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
			iCounter++;
			sExpansion = (String) itExpansion.next();			
			System.out.print("Expansion: '" + sExpansion + "', ");
			if(iCounter%10==0)System.out.println();
			
			//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
			iCounter++;
			sExpansion = (String) itExpansion.next();			
			System.out.print("Expansion: '" + sExpansion + "', ");
			if(iCounter%10==0)System.out.println();
							
		}
		System.out.println("Expansionsuche beendet.");
				
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
}
