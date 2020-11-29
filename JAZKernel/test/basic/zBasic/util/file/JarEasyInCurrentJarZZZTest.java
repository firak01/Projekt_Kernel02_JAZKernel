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

public class JarEasyInCurrentJarZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    
			objFileJarAsSource = null;
			if(JarEasyInCurrentJarZZZ.isInJarStatic()) {
				objFileJarAsSource = JarEasyUtilZZZ.getCodeLocationJar();
			}else {				
				File fileDir = JarEasyUtilZZZ.getCodeLocationUsed();								
				String sJarFile = JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL;				
				String sJarFilePath = FileEasyZZZ.joinFilePathName(fileDir, sJarFile);
				objFileJarAsSource = new File(sJarFilePath);
				if(objFileJarAsSource.isFile()) {  // Run with JAR file		
					objJarAsSource = new JarFile(objFileJarAsSource);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": JAR FILE FOUND.";
				    System.out.println(sLog);
				}
			}
			
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
			    
				//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test selbst. Aber nicht im Setup, dann das würde das vor jedem Test ausgeführt.			 
				
			} catch (IOException e1) {
				ExceptionZZZ ez  = new ExceptionZZZ("Beim setUp - IOException: " + e1.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyInCurrentJarZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
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
					ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyInCurrentJarZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
					ez.printStackTrace();
					fail("An exception happend testing: " + ez.getDetailAllLast());
				}
			}
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}//END tearDown
		
	public void testPeekDirectoryInJar(){
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileCreated;File objFileDir;
			String sPath = "debug/zBasic";
			String sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_DIRECTORY_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			String sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true, true);
			FileEasyZZZ.removeDirectory(sDirToExtractTo);
			
			if(!JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": DER TEST WIRD NORMAL NUR IN EINER ECHTEN JAR DATEI DURCHGEFÜHRT. ZUM DEBUGGEN NACHSGESTELLT.";
			    System.out.println(sLog);
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}//end if 
			
			 if(!JarEasyUtilZZZ.isInJarStatic())	{
				//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
				 JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_OVPN);
				 assertNotNull("JarFile nicht gefunden", objJarFile);
				    
				 objFileDir = JarEasyZZZ.peekDirectory(objJarFile, sPath, sDirToExtractTo);
			 }else {
				 objFileDir = JarEasyInCurrentJarZZZ.peekDirectory(sPath, sDirToExtractTo);
			}
		    assertNotNull(objFileDir);
		    if(objFileDir.exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryDummy * '" + objFileDir.getAbsolutePath() + "'";
			    System.out.println(sLog);
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFileInJar(){
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileCreated;File objFile;
			String sPath = "template/readmeFGL.txt";
			String sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_FILE_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			String sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true, true);
			FileEasyZZZ.removeDirectory(sDirToExtractTo);
			
			if(!JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": DER TEST WIRD NORMAL NUR IN EINER ECHTEN JAR DATEI DURCHGEFÜHRT. ZUM DEBUGGEN NACHSGESTELLT.";
			    System.out.println(sLog);
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}//end if 
			
			 if(!JarEasyUtilZZZ.isInJarStatic())	{
				//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
				 JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_KERNEL);
				 assertNotNull("JarFile nicht gefunden", objJarFile);
				    
				 objFile = JarEasyZZZ.peekFile(objJarFile, sPath, sDirToExtractTo);
			 }else {
				 objFile = JarEasyInCurrentJarZZZ.peekFile(sPath, sDirToExtractTo);
			}
		    assertNotNull(objFile);
		    if(FileEasyZZZ.exists(objFile)) {
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
		    
			File objFileCreated;File[] objaFile;
			String sPath = "template";
			String sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_FILES_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			String sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true, true);
			FileEasyZZZ.removeDirectory(sDirToExtractTo);
			
			if(!JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": DER TEST WIRD NORMAL NUR IN EINER ECHTEN JAR DATEI DURCHGEFÜHRT. ZUM DEBUGGEN NACHSGESTELLT.";
			    System.out.println(sLog);
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}//end if 
				
			 if(!JarEasyUtilZZZ.isInJarStatic())	{
				//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
				 JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_KERNEL);
				 assertNotNull("JarFile nicht gefunden", objJarFile);
				    
				 objaFile = JarEasyZZZ.peekFilesOfDirectory(objJarFile, sPath, sDirToExtractTo);
			}else {
				 objaFile = JarEasyInCurrentJarZZZ.peekFilesOfDirectory(sPath, sDirToExtractTo);
			}
		    assertNotNull(objaFile);
		    for(File objFile : objaFile) {
			    if(FileEasyZZZ.exists(objFile)) {
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
	
	
	
	public void testExtractFileAsTemp(){
		try{
			String sLog;boolean btemp;
			
			sLog= ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileCreated=null;
			String sPath = "template/template_server_starter.txt";
			//DAS WIRD DIREKT IM TEMP VERZEICHNIS ALS TEMP DATEI ERSTELLT ..... String sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_DIRECTORY_DUMMY";
			
			if(!JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": DER TEST WIRD NORMAL NUR IN EINER ECHTEN JAR DATEI DURCHGEFÜHRT. ZUM DEBUGGEN NACHSGESTELLT.";
			    System.out.println(sLog);
			    
			    //Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
			    JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_OVPN);
			    assertNotNull("JarFile nicht gefunden", objJarFile);
			    
			    objFileCreated = JarEasyZZZ.extractFileFromJarAsTemp(objJarFile, sPath);			
			    assertNotNull(objFileCreated);
				if(!objFileCreated.exists()) {
					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}
			    
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			    
			    objFileCreated = JarEasyInCurrentJarZZZ.extractFileAsTemp(sPath);
				assertNotNull(objFileCreated);
				if(!objFileCreated.exists()) {
					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}			    
			}//end if 
			
			
			
				
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testSearchResource() {
		try{
		    
			File objFile; File objDirectoryCreated; boolean btemp;
			String sPath; String sTargetDirectoryPathRoot;
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    if(!JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": DER TEST WIRD NORMAL und nicht innerhalb einer ECHTEN JAR DATEI DURCHGEFÜHRT. ZUM DEBUGGEN NACHSGESTELLT.";
			    System.out.println(sLog);
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}//end if 
			  
										
				//FALL AA: NUR VERZEICHNIS ERSTELLEN, keine Dateien darin	
				sLog = ReflectCodeZZZ.getPositionCurrent()+": START ### FALL AA ##########.";
			    System.out.println(sLog);
			    
				sPath = "bat";
				sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_DIRECTORY_TO_TEMP_EMPTY";				
				 if(!JarEasyUtilZZZ.isInJarStatic())	{
					//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
					 JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_KERNEL);
					 assertNotNull("JarFile nicht gefunden", objJarFile);
					    
					 objDirectoryCreated = JarEasyZZZ.searchResourceDirectory(objJarFile, sPath, sTargetDirectoryPathRoot, false);
				 }else {
					 objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResourceDirectory(sPath, sTargetDirectoryPathRoot, false);
				 }
				assertNotNull(objDirectoryCreated);
				if(!objDirectoryCreated.exists()) {
					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}else {
					//Das soll aber ein Verzeichnis sein!!!
					assertTrue("Es sollte das erstellte Verzeichnis zurückgegeben werden.", objDirectoryCreated.isDirectory());
					
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objDirectoryCreated.getAbsolutePath() + "'";
				    System.out.println(sLog);
				}
				
				File[] objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
				if(!FileArrayEasyZZZ.isEmpty(objaFile)){
					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' sollten nicht erstellt worden sein.");
				}
				
				//Fall AB: Anders als beim Peek wird beim Suchen das Verzeichnis erstellt, MIT Inhalt.				 
				sLog = ReflectCodeZZZ.getPositionCurrent()+": START ### FALL AB ##########.";
			    System.out.println(sLog);
				sPath = "debug/zBasic";
				sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_DIRECTORY_TO_TEMP";
				 if(!JarEasyUtilZZZ.isInJarStatic())	{
					//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
					JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_KERNEL);
					assertNotNull("JarFile nicht gefunden", objJarFile);
						    
					objDirectoryCreated = JarEasyZZZ.searchResourceDirectory(objJarFile, sPath, sTargetDirectoryPathRoot, true);
				}else {
					objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResourceDirectory(sPath, sTargetDirectoryPathRoot,true);	
				}
				assertNotNull(objDirectoryCreated);
				if(!FileEasyZZZ.exists(objDirectoryCreated)) {
					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' sollte erstellt worden sein.");
				}else {
					//Das soll aber ein Verzeichnis sein!!!
					assertTrue("Es sollte das erstellte Verzeichnis zurückgegeben werden.", objDirectoryCreated.isDirectory());
					
					btemp = JarEasyTestCommonsZZZ.ensureDirectoryStructureInTempExistsForDirectory(objDirectoryCreated, sTargetDirectoryPathRoot, sPath);
					assertTrue("Die Verzeichnisstruktur ist nicht korrekt: sTargetDirectoryPathRoot='"+sTargetDirectoryPathRoot+"'| sPath='"+sPath+"'", btemp);
					
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory * '" + objDirectoryCreated.getAbsolutePath() + "'";
				    System.out.println(sLog);	
				    
				    File[] objaFileInDir = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
					if(FileArrayEasyZZZ.isEmpty(objaFileInDir)){
						fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' sollten erstellt worden sein.");
					}else {												
						for(File objFileInDir : objaFileInDir) {
							sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFileInDir.getAbsolutePath() + "'";
							System.out.println(sLog);
							
							btemp = JarEasyTestCommonsZZZ.ensureDirectoryStructureInTempExistsForFiles(objaFileInDir, sTargetDirectoryPathRoot, sPath);
							assertTrue("Die Verzeichnisstruktur ist nicht korrekt: sTargetDirectoryPathRoot='"+sTargetDirectoryPathRoot+"'| sPath='"+sPath+"'", btemp);
						}																
					}
				}
				
				
				//Fall BA: Anders als beim Peek wird beim Suchen die Datei im Verzeichnis erstellt.
				//         Merke: Das ist schwieriger, da hier zuerst erkannt werden muss, dass es sich um eine Datei und nicht um ein Verzeichnis handelt!
				sLog = ReflectCodeZZZ.getPositionCurrent()+": START ### FALL BA ##########.";
			    System.out.println(sLog);
				sPath = "template/template_server_TCP_443.ovpn";
				sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_FILE_TO_TEMP";
				 if(!JarEasyUtilZZZ.isInJarStatic())	{
					//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
					JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_OVPN);
					assertNotNull("JarFile nicht gefunden", objJarFile);
						    
					objFile = JarEasyZZZ.searchResourceFile(objJarFile, sPath, sTargetDirectoryPathRoot);
				}else {
					objFile = JarEasyInCurrentJarZZZ.searchResourceFile(sPath, sTargetDirectoryPathRoot);
				}
				assertNotNull(objFile);
				if(!FileEasyZZZ.exists(objFile)) {
					fail("File-Objekt '" + objFile.getAbsolutePath() + "' sollte erstellt worden sein.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
				    System.out.println(sLog);					
				}		
				
				assertTrue("Es wurde als Ergbnis eine echte Datei erwartet: '"+objFile.getAbsolutePath()+"'",objFile.isFile());
				
				//Fall BB: Anders als beim Peek wird beim Suchen die Datei im Verzeichnis erstellt.
				//         Merke: Das ist schwieriger, da hier zuerst erkannt werden muss, dass es sich um eine Datei und nicht um ein Verzeichnis handelt!
				//TODOGOON: Das sollte ein Array aller erstellten Dateien sein als Rückgabewert.
				sLog = ReflectCodeZZZ.getPositionCurrent()+": START ### FALL BB ##########.";
			    System.out.println(sLog);
				sPath = "custom/zKernel";
				sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_DIRECTORY_TO_TEMP02";
				 if(!JarEasyUtilZZZ.isInJarStatic())	{
					//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
					JarFile objJarFile = JarEasyUtilZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_OVPN);
					assertNotNull("JarFile nicht gefunden", objJarFile);
						    
					objFile = JarEasyZZZ.searchResourceDirectory(objJarFile, sPath, sTargetDirectoryPathRoot);
				}else {
					objFile = JarEasyInCurrentJarZZZ.searchResourceDirectory(sPath, sTargetDirectoryPathRoot);
					//TODOGOON 20201127; //SO SOLL ABER NUR 1 ERSTELLTES VERZEICHNIS ZURÜCKKOMMEN!!!
				}
				assertNotNull(objFile);
				if(!FileEasyZZZ.exists(objFile)) {
					fail("Datei '" + objFile.getAbsolutePath() + "' sollte erstellt worden sein.");
				}else {
					//Das soll aber ein Verzeichnis sein!!!
					assertTrue("Es sollte das erstellte Verzeichnis zurückgegeben werden.", objFile.isDirectory());
					
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
				    System.out.println(sLog);					
				}			
		
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
//	public void testSearchResourceToTemp() {		
//		try{
//			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
//		    System.out.println(sLog);
//		    
//			File objDirectoryCreated;	
//			String sPath; String sTargetDirectoryPathRoot;File[] objaFile;
//			if(!JarEasyUtilZZZ.isInJarStatic())	{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Dieser Test wird nur innerhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			}else {
//				//Fall AA: Nur Verzeichnis als Dummy suchen, nicht erstellen
//				sPath = "debug/zBasic";
//				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP_DUMMY";
//				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);				
//				assertNotNull(objDirectoryCreated);
//				if(objDirectoryCreated.exists()) {
//					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
//				}
//				
//				
//				
//				//FALL BA: NUR VERZEICHNIS ERSTELLEN			
//				sPath = "debug/zBasic";
//				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP";
//				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot, false);				
//				assertNotNull(objDirectoryCreated);
//				if(!objDirectoryCreated.exists()) {
//					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
//				}
//				
//				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
//				if(!FileArrayEasyZZZ.isEmpty(objaFile)){
//					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' sollten nicht erstellt worden sein.");
//				}
//			
//			
//				//FALL BB: DATEI FINDEN
//				sPath = "template/template_server_TCP_443.ovpn";
//				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_FILE_TO_TEMP";
//				File objFileCreated = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);
//				assertNotNull(objFileCreated);
//				
//				objDirectoryCreated = objFileCreated.getParentFile();
//				assertNotNull(objDirectoryCreated);
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Parent=Directory='" + objDirectoryCreated.getAbsolutePath() + "'.";
//			    System.out.println(sLog);
//				
//				if(!objDirectoryCreated.exists()) {
//					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
//				}					
//																
//				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
//				if(FileArrayEasyZZZ.isEmpty(objaFile)){
//					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurden nicht erstellt.");
//				}
//				for(File objFileTemp : objaFile) {
//					sLog = ReflectCodeZZZ.getPositionCurrent()+": File='" + objFileTemp.getAbsolutePath() + "'.";
//				    System.out.println(sLog);
//				}
//				
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": sPath='" + sPath + "'.";
//			    System.out.println(sLog);
//			    
//			    String sFilePath = JarEasyUtilZZZ.toFilePath(sPath);
//			    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePath='" + sFilePath + "'.";
//			    System.out.println(sLog);
//			    
//				String sFileName = FileEasyZZZ.getNameFromFilepath(sPath);
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": sFileName='" + sFileName + "'.";
//			    System.out.println(sLog);
//			    
//				if(!FileArrayEasyZZZ.contains(objaFile,sFilePath)){
//					fail("Datei '" + sFilePath + "' innerhalb des Auflistung des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
//				}
//								
//				if(!objFileCreated.exists()) {
//					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
//				}
//				if(!objFileCreated.isFile()) {
//					fail("Datei '" + objFileCreated.getAbsolutePath() + "' gilt nicht als Datei.");
//				}
//			}
//			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
//		
//	}

	
	
	
}//END Class
