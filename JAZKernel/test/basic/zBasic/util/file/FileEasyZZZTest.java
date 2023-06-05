package basic.zBasic.util.file;


import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

import junit.framework.TestCase;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.dateTime.DateTimeZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class FileEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}			
	}//END setUp
	protected void tearDown() {
		if(objJarAsSource!=null) {
			try {
				objJarAsSource.close();
			} catch (IOException e) {
				ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
		}
	}//END tearDown
	
	public void testJoinFilePathName() {
		try {
			String sDirPathTotal = FileEasyZZZ.joinFilePathName("directory", "testNotExisting.txt");
			System.out.println(sDirPathTotal);
			assertEquals("src\\directory\\testNotExisting.txt",sDirPathTotal);
			
			sDirPathTotal = FileEasyZZZ.joinFilePathName("tryout\\basic\\zKernel\\html\\writer","TableData4Debug.xml");
			System.out.println(sDirPathTotal);
			assertEquals("tryout\\basic\\zKernel\\html\\writer\\TableData4Debug.xml",sDirPathTotal);
			
			sDirPathTotal = FileEasyZZZ.joinFilePathName("test\\basic\\zKernel\\html\\writer","TableData4Debug.xml");
			System.out.println(sDirPathTotal);
			assertEquals("test\\basic\\zKernel\\html\\writer\\TableData4Debug.xml",sDirPathTotal);
			
			sDirPathTotal = FileEasyZZZ.joinFilePathName("src\\basic\\zKernel\\html\\writer","TableData4Debug.xml");
			System.out.println(sDirPathTotal);
			assertEquals("src\\basic\\zKernel\\html\\writer\\TableData4Debug.xml",sDirPathTotal);
			
			
			System.out.println(sDirPathTotal);
			
			
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
		
	public void testRemoveDirectoryContent(){
		try{
			
			//1. Hole ein Jar-File für die Struktur der Verzeichnisse in diesem Test
			objFileJarAsSource = JarKernelZZZ.getJarFileTestAsFile();
			
			
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
		    
			String sDirToExtract="FileEasyZZZ_removeDirectoryContent";
			String sDirToExtractTo; 
			File objDirCreated;
			File[] objaDirCreated;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//TESTDATEIEN UND VERZEICHNISSE ERZEUGEN, 
			//Merke: Verzeichnisnamen mit Zeitstempel versehen, damit ist es immer wieder neu
			//       Das ist wichtig, da beim Extrahieren in ein bestehndes Verzeichnis versucht würde dies zu löschen
			//       aber das Löschen soll ja erst hier getestet werden!!!
			sDirToExtractTo = "FGL\\FILEASYZZZTEST";
			String sTimestamp = DateTimeZZZ.computeTimestampString();
			sDirToExtractTo = sDirToExtractTo+sTimestamp;
			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sDirToExtractTo,true);
			if(objaDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Verzeichnis '" + objFileTemp.getAbsolutePath() + "' wurde nicht erstellt.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File: '" + objFileTemp.getAbsolutePath() + "'";
				    System.out.println(sLog);
				}
			}
			assertTrue("Es sollten mehrere Dateien/Verzeichnisse entpackt worden sein", objaDirCreated.length>=2);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//LÖSCHEN DES VERZEICHNISSES: Inhalt mit Verzeichnissen
			String sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sDirToExtractTo);
			boolean bErg = FileEasyZZZ.removeDirectoryContent(sTargetDirPathTotal, true, true);//1. true: remove Files, 2. true: remove Direcotories
			
			//Es bleibt nur das Ausgangsverzeichnis mit dem DateTimeStamp im Namen erhalten
			File objDir = new File(sTargetDirPathTotal);
			File[] objaDirRemoved = objDir.listFiles();
			assertFalse("Es sollte kein Verzeichnis/Datei nach dem entfernen übrig sein", objaDirRemoved.length>=1);
			assertTrue("Das Verzeichnis '" + objDir.getAbsolutePath() + "' sollte erfolgreich geleert worden sein.", bErg);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//LÖSCHEN DES LEEREN VERZEICHNISSES SELBST
			bErg = FileEasyZZZ.removeDirectory(objDir);
			assertTrue("Das Verzeichnis '" + objDir.getAbsolutePath() + "' sollte leer sein und gelöscht worden sein.", bErg);
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}
