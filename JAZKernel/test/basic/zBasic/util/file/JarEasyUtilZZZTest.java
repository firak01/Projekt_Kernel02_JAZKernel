package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;
import basic.zBasic.util.file.jar.FileDirectoryFilterInJarZZZ;
import basic.zBasic.util.file.jar.FileFileFilterInJarZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class JarEasyUtilZZZTest extends TestCase{	
	private JarFile objJarAsSource=null;
	
	protected void setUp(){

		String sLog;
		main:{
		try {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": ############################################### SETUP .";
			System.out.println(sLog);

			objJarAsSource = JarEasyUtilZZZ.getJarFileUsed();
				
			//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test selbst. Aber nicht im Setup, dann das würde das vor jedem Test ausgeführt.			 

		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		}//End main:
		
			
	}//END setUp
	protected void tearDown() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": ############################################### TEARDOWN.";
		    System.out.println(sLog);
		    
			if(objJarAsSource!=null) {
				try {
					objJarAsSource.close();
				} catch (IOException e) {
					ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyUtilZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
					ez.printStackTrace();
					fail("An exception happend testing: " + ez.getDetailAllLast());
				}
			}
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}//END tearDown
	
	public void testGetCodeLocationUsed() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    

			assertNotNull("Im setup sollte die Jar Datei gefunden worden sein.", objJarAsSource);
		    File objFile = JarEasyUtilZZZ.toFile(objJarAsSource);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": Im Setup gefundene Jar Datei... objFile.getAbsolutePath() = '" + objFile.getAbsolutePath() + "'";
		    System.out.println(sLog);		    
		    assertTrue(objFile.isFile());
		    assertTrue(FileEasyZZZ.isJar(objFile));
			
			
			//Vorab ein Vergleichsdummy holen.		
			if(JarEasyUtilZZZ.isInJarStatic()) { //FALL: IN JAR AUSFÜHREN
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    
			   
			    //+++++++++
				//Nun der eigentliche Test
				File objFileTest = JarEasyUtilZZZ.getCodeLocationUsed();
				assertNotNull(objFileTest);
			    sLog = ReflectCodeZZZ.getPositionCurrent()+": JAR File as CodeLocationUsed = '" + objFileTest.getAbsolutePath() +"'";
				System.out.println(sLog);
			    assertTrue(objFileTest.exists());
			    assertTrue(FileEasyZZZ.isJar(objFileTest));
			    assertTrue(objFileTest.isFile());
				
			    //+++++++++
				//Nun der eigentliche Test
			    assertEquals(objFileTest,objFile);
			    			    
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt, vermutlich eclipse: !IsInJarStatic";
			    System.out.println(sLog);
			    objFile = JarEasyUtilZZZ.getCodeLocationJar();
			    assertNull(objFile);
			    
			    objFile = FileEasyZZZ.getDirectoryOfExecution();			    
			    assertNotNull(objFile);
			    sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory of Execution = '" + objFile.getAbsolutePath() +"'";
				System.out.println(sLog);
			    assertTrue(objFile.exists());
			    assertFalse(FileEasyZZZ.isJar(objFile));
			    assertFalse(objFile.isFile());
			    
			    
			    //+++++++++
				//Nun der eigentliche Test
				File objFileTest = JarEasyUtilZZZ.getCodeLocationUsed();
				assertNotNull(objFileTest);
			    sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory of Execution as CodeLocationUsed = '" + objFileTest.getAbsolutePath() +"'";
				System.out.println(sLog);
			    assertTrue(objFileTest.exists());
			    assertFalse(FileEasyZZZ.isJar(objFileTest));
			    assertFalse(objFileTest.isFile());
				
			    assertEquals(objFileTest,objFile);
			}
			    
		
			   
		  
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 
	}
	
	
	
	public void testGetJarFileCurrentAsFile() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFile = null;
			if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    objFile = JarEasyUtilZZZ.getJarFileCurrentAsFile();
			    
			    assertNotNull(objFile);
			    assertTrue(objFile.isFile());
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}		    		  		    
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 
	}
	
	public void testGetJarFileUsedAsFile() {
		try {
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
	    System.out.println(sLog);
		
		if(JarEasyUtilZZZ.isInJarStatic()) {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": ISinJarStatic.";
		    System.out.println(sLog);			
		}else {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": NOT ISinJarStatic.";
		    System.out.println(sLog);		    
		}
		File objFile = JarEasyUtilZZZ.getJarFileUsedAsFile();
		assertNotNull(objFile);
		assertTrue(objFile.exists());
		assertTrue(objFile.isFile());
		    
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 		
	}
	
	public void testIsInSameJar() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyZZZ.isInJarStatic()) {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    
		    	assertNotNull("Im setup sollte die Jar Datei gefunden worden sein.", objJarAsSource);
				File objFileJarAsSource = JarEasyUtilZZZ.toFile(objJarAsSource);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": A) objFileJarAsSource.getAbsolutePath() = '" + objFileJarAsSource.getAbsolutePath() + "'";
				System.out.println(sLog);
				    		    	
		    	boolean bErg = JarEasyUtilZZZ.isInSameJarStatic(objFileJarAsSource);
		    	assertTrue(bErg);
		    	
		    	//+++ Gegenprobe mit einem Dummy - Jar - Objekt
		    	File objFileJarAsDummy = JarEasyUtilZZZ.getJarFileDummyAsFile();
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": B) objFileJarAsDummy.getAbsolutePath() = '" + objFileJarAsDummy.getAbsolutePath() + "'";
				System.out.println(sLog);
				
				bErg = JarEasyUtilZZZ.isInSameJarStatic(objFileJarAsDummy);
		    	assertFalse(bErg);
		    	
		    }else {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);
			    
			    File objFileJar = JarEasyUtilZZZ.getCodeLocationUsed();
			    assertNotNull(objFileJar);
			    assertTrue(objFileJar.isDirectory());
		    }
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
		
	
//	public void testFindDirectoryInJar(){
//		try{
//			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
//		    System.out.println(sLog);
//		    
//			File objFileCreated;
//			String sPath = "debug/zBasic";
//			String sTargetDirectoryPathRoot = "FIND_RESOURCE_DIRECTORY_DUMMY";
//			
//			//VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
//			String sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
//			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true);
//			FileEasyZZZ.removeDirectory(sDirToExtractTo);
//			
//			if(JarEasyUtilZZZ.isInJarStatic())	{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			    
////				objFileCreated = JarEasyInCurrentJarZZZ.extractFileAsTemp("template/template_server_starter.txt");
////				assertNotNull(objFileCreated);
////				if(!objFileCreated.exists()) {
////					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}
//			}else {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//				
//			    File objFileDir = ResourceEasyZZZ.findDirectoryInJar(objFileJarAsSource, sPath, sDirToExtractTo);
//			    assertNotNull(objFileDir);
//			    if(objFileDir.exists()) {
//					fail("Verzeichnis '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
//				}
//			}
//			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
//	}
//	
//	public void testFindDirectoryInJarAsTrunk() {
//		try{
//			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
//		    System.out.println(sLog);
//		    
//			File objFileDummy;	
//			String sPath; String sTargetDirectoryPathRoot;
//			if(JarEasyUtilZZZ.isInJarStatic())	{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			}else {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			    
//				//Fall AA: Nur Verzeichnis als Dummy suchen, nicht erstellen
////				sPath = "debug/zBasic";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_DUMMY";
////				objFileDummy = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);				
////				assertNotNull(objFileDummy);
////				if(objFileDummy.exists()) {
////					fail("Verzeichnis '" + objFileDummy.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
////				}
////				
////				//Fall AB: Nur Daei als Dummy suchen, nicht erstellen.								
////				sPath = "template/template_server_TCP_443.ovpn";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_FILE_DUMMY";
////				objFileDummy = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);
////				assertNotNull(objFileDummy);
////				if(objFileDummy.exists()) {
////					fail("Datei '" + objFileDummy.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
////				}												
//			}
//			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
//		
//	}
//	
//	public void testFindFile() {		
//		try{
//			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
//		    System.out.println(sLog);
//		    
//			File objDirectoryCreated;	
//			String sPath; String sTargetDirectoryPathRoot;File[] objaFile;
//			if(JarEasyUtilZZZ.isInJarStatic())	{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Dieser Test wird nur innerhalb einer JAR-Datei durchgeführt.";
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			}else {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			    
//				//Fall AA: Nur Verzeichnis als Dummy suchen, nicht erstellen
////				sPath = "debug/zBasic";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP_DUMMY";
////				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);				
////				assertNotNull(objDirectoryCreated);
////				if(objDirectoryCreated.exists()) {
////					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
////				}
////				
////				
////				
////				//FALL BA: NUR VERZEICHNIS ERSTELLEN			
////				sPath = "debug/zBasic";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP";
////				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResourceToTemp(sPath, sTargetDirectoryPathRoot, false);				
////				assertNotNull(objDirectoryCreated);
////				if(!objDirectoryCreated.exists()) {
////					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}
////				
////				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
////				if(!FileArrayEasyZZZ.isEmpty(objaFile)){
////					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' sollten nicht erstellt worden sein.");
////				}
////			
////			
////				//FALL BB: DATEI FINDEN
////				sPath = "template/template_server_TCP_443.ovpn";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_FILE_TO_TEMP";
////				File objFileCreated = JarEasyInCurrentJarZZZ.searchResourceToTemp(sPath, sTargetDirectoryPathRoot);
////				assertNotNull(objFileCreated);
////				
////				objDirectoryCreated = objFileCreated.getParentFile();
////				assertNotNull(objDirectoryCreated);
////				sLog = ReflectCodeZZZ.getPositionCurrent()+": Parent=Directory='" + objDirectoryCreated.getAbsolutePath() + "'.";
////			    System.out.println(sLog);
////				
////				if(!objDirectoryCreated.exists()) {
////					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}					
////																
////				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
////				if(FileArrayEasyZZZ.isEmpty(objaFile)){
////					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurden nicht erstellt.");
////				}
////				for(File objFileTemp : objaFile) {
////					sLog = ReflectCodeZZZ.getPositionCurrent()+": File='" + objFileTemp.getAbsolutePath() + "'.";
////				    System.out.println(sLog);
////				}
////				
////				sLog = ReflectCodeZZZ.getPositionCurrent()+": sPath='" + sPath + "'.";
////			    System.out.println(sLog);
////			    
////			    String sFilePath = JarEasyZZZ.toFilePath(sPath);
////			    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePath='" + sFilePath + "'.";
////			    System.out.println(sLog);
////			    
////				String sFileName = FileEasyZZZ.getNameFromFilepath(sPath);
////				sLog = ReflectCodeZZZ.getPositionCurrent()+": sFileName='" + sFileName + "'.";
////			    System.out.println(sLog);
////			    
////				if(!FileArrayEasyZZZ.contains(objaFile,sFilePath)){
////					fail("Datei '" + sFilePath + "' innerhalb des Auflistung des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}
////								
////				if(!objFileCreated.exists()) {
////					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}
////				if(!objFileCreated.isFile()) {
////					fail("Datei '" + objFileCreated.getAbsolutePath() + "' gilt nicht als Datei.");
////				}
//			}
//			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
//		
//	}

	
	public void testFindFileInJar() {
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}	
		    
		    JarFile objFileAsJar = JarEasyUtilZZZ.getJarFileUsed();		    
		    assertNotNull(objFileAsJar);
		    	
		    String sTargetDirectoryPathRoot = "FIND_RESOURCE_FILE";
		    String sPath = "bat/KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		
			//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
			IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(null, sPath);
			File[] objaReturn = JarEasyUtilZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNotNull(objaReturn);
			assertTrue("Sollte nur 1 Element haben", objaReturn.length==1);
			
			File objFile = objaReturn[0];
			sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
			System.out.println(sLog);	
			assertTrue("Dies sollte auf der Platte existieren", objFile.exists());
			assertTrue("Das sollte eine Datei sein: " + objFile.getAbsolutePath(), objFile.isFile());
			
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	}
	
	public void testFindDirectoryInJar() {
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}	
		    
		    JarFile objFileAsJar = JarEasyUtilZZZ.getJarFileUsed();		    
		    assertNotNull(objFileAsJar);
		    
			//A) Nur das Verzeichnis erstellen... also den reinen Verzeichnis Filter
		    String sTargetDirectoryPathRoot = "FIND_RESOURCE_DIRECTORY_EMPTY";
		    String sPath = "bat";
			IFileDirectoryPartFilterZipUserZZZ objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sPath);										
			File[] objaReturn = JarEasyUtilZZZ.findDirectoryInJar(objFileAsJar, objFilterDirInJar, sTargetDirectoryPathRoot, false);
			assertNotNull(objaReturn);
			for(File objDir : objaReturn) {
				assertNotNull(objDir);	
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory * '" + objDir.getAbsolutePath() + "'";
				System.out.println(sLog);									
				assertTrue("Das sollte ein Verzeichnis sein: " + objDir.getAbsolutePath(), objDir.isDirectory());
			}
			
			//B) Das Verzeichnis mit allen darin enthaltenen Dateien erstellen
			sTargetDirectoryPathRoot = "FIND_RESOURCE_DIRECTORY";
		    sPath = "bat";
			objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sPath);										
			objaReturn = JarEasyUtilZZZ.findDirectoryInJar(objFileAsJar, objFilterDirInJar, sTargetDirectoryPathRoot, true);
			assertNotNull(objaReturn);
			assertTrue("Es sollten mehrere Dateien enthalten sein in dem Verzeichnis '" + sPath + "'", objaReturn.length>=2);
			for(File objFile : objaReturn) {
				assertNotNull(objFile);
				assertTrue("Das File-objekt sollte auf der Platte existieren: " + objFile.getAbsolutePath(), objFile.exists());
				if(objFile.isDirectory()){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory * '" + objFile.getAbsolutePath() + "'";
					System.out.println(sLog);
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
					System.out.println(sLog);
				}				
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
	}
	
}//END Class
