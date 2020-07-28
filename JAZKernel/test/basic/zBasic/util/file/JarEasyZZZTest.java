package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

public class JarEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			//final File jarFile = new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String sJarFilePath = JarEasyTestConstantsZZZ.sJAR_FILEPATH;
			objFileJarAsSource = new File(sJarFilePath);
				if(objFileJarAsSource.isFile()) {  // Run with JAR file		
					objJarAsSource = new JarFile(objFileJarAsSource);
					String sLog = ReflectCodeZZZ.getPositionCurrent()+": JAR FILE FOUND.";
				    System.out.println(sLog);
				}
				
				sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
			    System.out.println(sLog);
				
			} catch (IOException e1) {
				ExceptionZZZ ez  = new ExceptionZZZ("Beim setUp - IOException: " + e1.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
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
		
	public void testExtractDirectoryToTemp(){
		try{
			String sDirToExtract="template";
			String sDirToExtractTo; 
			File objDirCreated;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL_WITHOUTFILES";;			
			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objFileJarAsSource, sDirToExtract, sDirToExtractTo, false);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
			}
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL_WITHFILES";			
			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objFileJarAsSource, sDirToExtract, sDirToExtractTo, true);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//B) Fall: Dateien exitieren bereits. D.h. alles komplett löschen in der Methode und neu anlegen.
			//Ba) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL_WITHOUTFILES";				
			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objFileJarAsSource, sDirToExtract, sDirToExtractTo, false);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
			}
			
			//Bb) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL_WITHFILES";					
			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objFileJarAsSource, sDirToExtract, sDirToExtractTo, true);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
//	public void testExtractDirectoryToTemp(){
//		try{
//			JarFile objJar = new JarFile(objFileJarAsSource);
//			
//			
//			String sDirToExtract="template";
//			String sDirToExtractTotal; String sTargetDirPathTotal;
//			File objDirCreated;
//			
//			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
//			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
//			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
//			sDirToExtractTotal = "FGL_WITHOUTFILES_TEMP" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
//			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);	
//						
//			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objJar, sDirToExtract, sTargetDirPathTotal, false);
//			if(!objDirCreated.exists()) {
//				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
//			}
//			
//			//Ab) Erfolgsfall, mit Dateien erzeugen						
//			sDirToExtractTotal = "FGL_WITHFILES_TEMP" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
//			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
//			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, true);
//			if(!objDirCreated.exists()) {
//				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
//			}
//			
////			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
////			//B) Fall: Dateien exitieren bereits. D.h. alles komplett löschen in der Methode und neu anlegen.
////			//Ba) Erfolgsfall, ohne Dateien zu erzeugen					
////			sDirToExtractTotal = "FGL_WITHOUTFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
////			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
////			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, false);
////			if(!objDirCreated.exists()) {
////				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
////			}
////			
////			//Bb) Erfolgsfall, mit Dateien erzeugen						
////			sDirToExtractTotal = "FGL_WITHFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
////			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
////			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, true);
////			if(!objDirCreated.exists()) {
////				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
////			}
//			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		} catch (IOException e) {
//			fail("An IOException happend testeing: " + e.getMessage());
//		}
//	}
	
	public void testExtractFromJarAsTrunk() {
		try{
			JarFile objJar = JarEasyZZZ.toJarFile(objFileJarAsSource);
			String sDirToExtract="template";
			String sDirToExtractTo; 
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL_TRUNK_OF_DIRECTORY";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);
			
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN FILE OBJEKT.
			File[] objaCreated01 = JarEasyZZZ.extractFromJarAsTrunkFileDummy(objJar,sDirToExtract, sDirToExtractTo, false);
			if(objaCreated01[0].exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
			}
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL_TRUNK_OF_FILES";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);			
			File[] objaCreated02 = JarEasyZZZ.extractFromJarAsTrunkFileDummy(objJar, sDirToExtract, sDirToExtractTo, true);
			if(objaCreated02[0].exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte nicht erstellt sein.");
			}
			
			//####################################################################
			//Merke: Mit diesen File-Objekten kann man eigentlich nur die Existenz in der JAR-Datei prüfen.
			//Einen Input Stream zu bekommen ist die Voraussetzung für das Erstellen der Datei als Kopie
			//
			//BEISPIEL, aus Dokugründen stehen lassen;
			//ZipFile zf = objJarInfo.getZipFile();
			//while(itEntryName.hasNext()) {
			//	String sKey = itEntryName.next();
			//	ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
			//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)														
			//InputStream is = zf.getInputStream(zeTemp);
			//String sKeyNormed = StringZZZ.replace(sKey, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
			//String sPath = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sApplicationKeyAsSubDirectoryTemp + FileEasyZZZ.sDIRECTORY_SEPARATOR + sKeyNormed);			
			//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
			//FileEasyZZZ.removeFile(sPath);								
			//Files.copy(is, Paths.get(sPath))
			
			//Um also auf ein echtes File-Objekt zugreifen zu können als "TRUNK" ein ZipFileEntry-Array zurückliefern.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL_TRUNK2_OF_DIRECTORY";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);
			
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN FILE OBJEKT.
			ZipEntry[] objaCreated03 = JarEasyZZZ.extractFromJarAsTrunkEntry(objJar,sDirToExtract, sDirToExtractTo, false);
			if(!objaCreated03[0].isDirectory()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte  als TRUNK zurückgeliefert worden sein.");
			}
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL_TRUNK2_OF_FILES";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);			
			ZipEntry[] objaCreated04 = JarEasyZZZ.extractFromJarAsTrunkEntry(objJar, sDirToExtract, sDirToExtractTo, true);
			if(objaCreated04[0].isDirectory()) {
				fail("Kein Verzeichnis '" + sDirToExtractTo + "' sollte als TRUNK zurückgeliefert worden sein.");
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
	public void testSaveTrunkEntryToDirectory() {
		try{
			JarFile objJar = JarEasyZZZ.toJarFile(objFileJarAsSource);
			String sDirToExtract="template";
			String sDirToExtractTo; 
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL_TRUNKENTRY_OF_DIRECTORY";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);
			
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN FILE OBJEKT.
			File[] objaCreated01 = JarEasyZZZ.extractFromJarAsTrunkFileDummy(objJar,sDirToExtract, sDirToExtractTo, false);
			if(objaCreated01[0].exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
			}
			
			
			TODOGOON;
			//Es muss eine .extractFromJarAsTrunk(objJar,sDirToExtraxt,objTrunkHolder) Methode geben.
			//objTrunkHolder hat dann sowohl ein Array von ZipEntry[] als auch von File[].
			//bzw.: vielleicht eine hashMap von File-Objekten, die zum ZipEntry Passen.
			//HashMap<ZipEntry>() ... mit File Objekt als Wert.
			//Also; 
			//Es muss eine .extractFromJarAsTrunk(objJar,sDirToExtraxt,HashMap<ZipEntry,File) Methode geben.
			//... besser noch eine HashMapZZZ....
			
			HashMap<ZipEntry,File>hmZe = new HashMap<ZipEntry,File>();
			JarEasyZZZ.saveTrunkEntryAsFile(objJar, hmZe);
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL_TRUNK_OF_FILES";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);			
			File[] objaCreated02 = JarEasyZZZ.extractFromJarAsTrunkFileDummy(objJar, sDirToExtract, sDirToExtractTo, true);
			if(objaCreated02[0].exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte nicht erstellt sein.");
			}
			
			//####################################################################
			//Merke: Mit diesen File-Objekten kann man eigentlich nur die Existenz in der JAR-Datei prüfen.
			//Einen Input Stream zu bekommen ist die Voraussetzung für das Erstellen der Datei als Kopie
			//
			//BEISPIEL, aus Dokugründen stehen lassen;
			//ZipFile zf = objJarInfo.getZipFile();
			//while(itEntryName.hasNext()) {
			//	String sKey = itEntryName.next();
			//	ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
			//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)														
			//InputStream is = zf.getInputStream(zeTemp);
			//String sKeyNormed = StringZZZ.replace(sKey, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
			//String sPath = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sApplicationKeyAsSubDirectoryTemp + FileEasyZZZ.sDIRECTORY_SEPARATOR + sKeyNormed);			
			//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
			//FileEasyZZZ.removeFile(sPath);								
			//Files.copy(is, Paths.get(sPath))
			
			//Um also auf ein echtes File-Objekt zugreifen zu können als "TRUNK" ein ZipFileEntry-Array zurückliefern.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL_TRUNK2_OF_DIRECTORY";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);
			
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN FILE OBJEKT.
			ZipEntry[] objaCreated03 = JarEasyZZZ.extractFromJarAsTrunkEntry(objJar,sDirToExtract, sDirToExtractTo, false);
			if(!objaCreated03[0].isDirectory()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte  als TRUNK zurückgeliefert worden sein.");
			}
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL_TRUNK2_OF_FILES";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);			
			ZipEntry[] objaCreated04 = JarEasyZZZ.extractFromJarAsTrunkEntry(objJar, sDirToExtract, sDirToExtractTo, true);
			if(objaCreated04[0].isDirectory()) {
				fail("Kein Verzeichnis '" + sDirToExtractTo + "' sollte als TRUNK zurückgeliefert worden sein.");
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
	
}//END Class
