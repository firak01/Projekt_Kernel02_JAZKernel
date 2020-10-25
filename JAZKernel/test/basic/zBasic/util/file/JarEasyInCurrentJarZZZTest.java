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
				String sJarFile = JarEasyTestConstantsZZZ.sJAR_FILENAME;				
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
		
	
	public void testExtractFileAsTemp(){
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileCreated;			
			if(JarEasyUtilZZZ.isInJarStatic())	{
				objFileCreated = JarEasyInCurrentJarZZZ.extractFileAsTemp("template/template_server_starter.txt");
				assertNotNull(objFileCreated);
				if(!objFileCreated.exists()) {
					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testSearchResource() {
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFileDummy;	
			String sPath; String sTargetDirectoryPathRoot;
			if(!JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Dieser Test wird nur innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				//Fall AA: Nur Verzeichnis als Dummy suchen, nicht erstellen
				sPath = "debug/zBasic";
				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_DUMMY";
				objFileDummy = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);				
				assertNotNull(objFileDummy);
				if(objFileDummy.exists()) {
					fail("Verzeichnis '" + objFileDummy.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
				}
				
				//Fall AB: Nur Daei als Dummy suchen, nicht erstellen.								
				sPath = "template/template_server_TCP_443.ovpn";
				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_FILE_DUMMY";
				objFileDummy = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);
				assertNotNull(objFileDummy);
				if(objFileDummy.exists()) {
					fail("Datei '" + objFileDummy.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
				}												
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	public void testSearchResourceToTemp() {		
		try{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objDirectoryCreated;	
			String sPath; String sTargetDirectoryPathRoot;File[] objaFile;
			if(!JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Dieser Test wird nur innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				//Fall AA: Nur Verzeichnis als Dummy suchen, nicht erstellen
				sPath = "debug/zBasic";
				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP_DUMMY";
				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);				
				assertNotNull(objDirectoryCreated);
				if(objDirectoryCreated.exists()) {
					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
				}
				
				
				
				//FALL BA: NUR VERZEICHNIS ERSTELLEN			
				sPath = "debug/zBasic";
				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP";
				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResourceToTemp(sPath, sTargetDirectoryPathRoot, false);				
				assertNotNull(objDirectoryCreated);
				if(!objDirectoryCreated.exists()) {
					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}
				
				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
				if(!FileArrayEasyZZZ.isEmpty(objaFile)){
					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' sollten nicht erstellt worden sein.");
				}
			
			
				//FALL BB: DATEI FINDEN
				sPath = "template/template_server_TCP_443.ovpn";
				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_FILE_TO_TEMP";
				File objFileCreated = JarEasyInCurrentJarZZZ.searchResourceToTemp(sPath, sTargetDirectoryPathRoot);
				assertNotNull(objFileCreated);
				
				objDirectoryCreated = objFileCreated.getParentFile();
				assertNotNull(objDirectoryCreated);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Parent=Directory='" + objDirectoryCreated.getAbsolutePath() + "'.";
			    System.out.println(sLog);
				
				if(!objDirectoryCreated.exists()) {
					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}					
																
				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
				if(FileArrayEasyZZZ.isEmpty(objaFile)){
					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurden nicht erstellt.");
				}
				for(File objFileTemp : objaFile) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File='" + objFileTemp.getAbsolutePath() + "'.";
				    System.out.println(sLog);
				}
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": sPath='" + sPath + "'.";
			    System.out.println(sLog);
			    
			    String sFilePath = JarEasyUtilZZZ.toFilePath(sPath);
			    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePath='" + sFilePath + "'.";
			    System.out.println(sLog);
			    
				String sFileName = FileEasyZZZ.getNameFromFilepath(sPath);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": sFileName='" + sFileName + "'.";
			    System.out.println(sLog);
			    
				if(!FileArrayEasyZZZ.contains(objaFile,sFilePath)){
					fail("Datei '" + sFilePath + "' innerhalb des Auflistung des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}
								
				if(!objFileCreated.exists()) {
					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}
				if(!objFileCreated.isFile()) {
					fail("Datei '" + objFileCreated.getAbsolutePath() + "' gilt nicht als Datei.");
				}
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}

	
	
	
}//END Class
