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
import basic.zBasic.util.machine.EnvironmentZZZ;

public class ResourceEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    
		    if(!JarEasyUtilZZZ.isInJarStatic())	{				
				//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
		    	objFileJarAsSource = JarKernelZZZ.getJarFileUsedAsFile(JarEasyUtilZZZ.iJAR_OVPN);					
			}else {
				objFileJarAsSource = JarKernelZZZ.getJarFileUsedAsFile();
			}
						
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
			    
				//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test selbst. Aber nicht im Setup, dann das würde das vor jedem Test ausgeführt.			 
				
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
			
	}//END setUp
	protected void tearDown() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": TEARDOWN ###############################################.";
		    System.out.println(sLog);
		    
			if(objJarAsSource!=null) {
				try {
					objJarAsSource.close();
				} catch (IOException e) {
					ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, ResourceEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
					ez.printStackTrace();
					fail("An exception happend testing: " + ez.getDetailAllLast());
				}
			}
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}//END tearDown
	
	public void testIsInSameJar() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    assertNotNull(objFileJarAsSource);
		    if(ResourceEasyZZZ.isInJarStatic()) {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    
		    	File objFileJar = JarEasyUtilZZZ.getCodeLocationJar();
		    	assertNotNull(objFileJar);
		    	assertNotNull(objFileJarAsSource);
		    	assertEquals(objFileJar, objFileJarAsSource);
		    	
		    	boolean bErg = ResourceEasyZZZ.isInSameJarStatic(objFileJarAsSource);
		    	assertTrue(bErg);
		    }else {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);
			    
			    File objFileJar = JarEasyUtilZZZ.getCodeLocationJar();
			    assertNotNull(objFileJar);
			    assertTrue(objFileJar.isDirectory());
		    }
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
		
	
	public void testPeekDirectoryInJar(){
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileCreated;
			String sPath = "debug/zBasic";
			String sTargetDirectoryPathRoot = "PEEK_RESOURCE_DIRECTORY_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			String sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true, true);
			FileEasyZZZ.removeDirectory(sDirToExtractTo);
			
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}
			
			File objFileDir = ResourceEasyZZZ.peekDirectoryInJar(objFileJarAsSource, sPath, sDirToExtractTo);
		    assertNotNull(objFileDir);
		    if(objFileDir.exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFileInJar(){
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileCreated;
			String sPath = "template/readmeFGL.txt";
			String sTargetDirectoryPathRoot = "PEEK_RESOURCE_FILE_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			String sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true, true);
			FileEasyZZZ.removeDirectory(sDirToExtractTo);
			
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}
			
			File objFile = ResourceEasyZZZ.peekFileInJar(objFileJarAsSource, sPath, sDirToExtractTo);
		    assertNotNull(objFile);
		    if(objFile.exists()) {
				fail("Datei '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Datei als Dummy * '" + objFile.getAbsolutePath() + "'";
			    System.out.println(sLog);
			}
		    		    		    			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFilesOfDirectoryInJar(){
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileCreated;
			String sPath = "template";
			String sTargetDirectoryPathRoot = "PEEK_RESOURCE_FILES_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			String sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true, true);
			FileEasyZZZ.removeDirectory(sDirToExtractTo);
			
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}
			
			File[] objaFile = ResourceEasyZZZ.peekFilesOfDirectoryInJar(objFileJarAsSource, sPath, sDirToExtractTo);
		    assertNotNull(objaFile);
		    for(File objFile : objaFile) {
			    if(objFile.exists()) {
					fail("Datei '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": FileDummy * '" + objFile.getAbsolutePath() + "'";
				    System.out.println(sLog);
				}
		    }
		    		    		    			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
	
	public void testSearchDirectoryInJar() {
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileDummy;	
			String sPath; String sTargetDirectoryPathRoot;
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			} 
			 
			
			
			//Fall AA: Nur Verzeichnis suchen, in temp erstellen
			sPath = "debug/zBasic";
			sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_DUMMY";
			if(!JarEasyUtilZZZ.isInJarStatic())	{				
				//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
				JarFile objJarFile = JarKernelZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_OVPN);
				assertNotNull("JarFile nicht gefunden", objJarFile);
					    
				objFileDummy = JarEasyZZZ.searchResourceDirectory(objJarFile, sPath, sTargetDirectoryPathRoot);				
			}else {
				
				objFileDummy = JarEasyInCurrentJarZZZ.searchResourceDirectory(sPath, sTargetDirectoryPathRoot);		
			}
			
			assertNotNull(objFileDummy);
			if(!FileEasyZZZ.exists(objFileDummy)) {
				fail("Verzeichnis '" + objFileDummy.getAbsolutePath() + "' sollte erstellt worden sein.");
			}
				
			
			
			
			
				
			//Fall AB: Nur Datei suchen, in temp  erstellen.								
			sPath = "template/template_server_TCP_443.ovpn";
			sTargetDirectoryPathRoot = "SEARCH_RESOURCE_FILE_DUMMY";
			if(!JarEasyUtilZZZ.isInJarStatic())	{				
				//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
				JarFile objJarFile = JarKernelZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_OVPN);
				assertNotNull("JarFile nicht gefunden", objJarFile);
					    
				objFileDummy = JarEasyZZZ.searchResourceFile(objJarFile, sPath, sTargetDirectoryPathRoot);				
			}else {
				objFileDummy = JarEasyInCurrentJarZZZ.searchResourceFile(sPath, sTargetDirectoryPathRoot);
			}
			assertNotNull(objFileDummy);
			if(!FileEasyZZZ.exists(objFileDummy)) {
				fail("Datei '" + objFileDummy.getAbsolutePath() + "' sollte erstellt worden sein.");
			}												
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
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

	
	
	
}//END Class
