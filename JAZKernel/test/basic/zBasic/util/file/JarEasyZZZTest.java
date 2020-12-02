package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;
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
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class JarEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    
			objFileJarAsSource = JarKernelZZZ.getJarFileUsedAsFile();
						
				
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
		    
			//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.			 
						
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
			boolean btemp;
			File objDirCreated; File[] objaDirCreated;
			String sDirToExtractTo;
			String sDirTemp; String sDirParentTemp;	String sDirParent;
			
			
			String sDirToExtract="template";
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen										
			sDirToExtractTo = "FGL\\EXTRACT_DIRECTORY_WITHOUTFILES";
			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sDirToExtractTo,false);
			if(objaDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			btemp = JarEasyTestCommonsZZZ.ensureDirectoryStructureInTempExistsForDirectories(objaDirCreated, sDirToExtractTo, sDirToExtract);
			assertTrue("Die Verzeichnisstruktur ist nicht korrekt: sDirToExtractTo='"+sDirToExtractTo+"'|sDirToExtract='"+sDirToExtract+"'", btemp);
			
			//Ab) Erfolgsfall, mit Dateien erzeugen							
			sDirToExtractTo = "FGL\\EXTRACT_DIRECTORY_WITHFILES";			
			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objFileJarAsSource, sDirToExtract, sDirToExtractTo);
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sDirToExtractTo);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			if(objDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}else {
				//Verzeichnispfad vergleichen
				assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objDirCreated.getAbsolutePath() + "'", sDirParent.equals(objDirCreated.getAbsolutePath()));
			}
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
			}
			
			//Ac) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL\\EXTRACT_DIRECTORY_WITHFILES02";			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sDirToExtractTo, true);									
			if(objaDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sDirToExtractTo);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);			
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Datei '" + objFileTemp.getAbsolutePath() + " im Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfad mit Elternverzeichnis vergleichen
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getParentFile().getAbsolutePath()));
				}

			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//B) Fall: Dateien exitieren bereits. D.h. alles komplett löschen in der Methode und neu anlegen.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Ba) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL\\EXTRACT_DIRECTORY_WITHOUTFILES";				
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sDirToExtractTo, false);		
			if(objaDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sDirToExtractTo);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Verzeichnis '" + objFileTemp.getAbsolutePath() + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfade vergleichen!!!!
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getAbsolutePath()));
				}
			}
			
			//Bb) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL\\EXTRACT_DIRECTORY_WITHFILES";					
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sDirToExtractTo, true);						
			if(objDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sDirToExtractTo);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Datei '" + objFileTemp.getAbsolutePath() + " im Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfad mit Elternverzeichnis vergleichen
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getParentFile().getAbsolutePath()));
				}
			}
			
			//Bc) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL\\EXTRACT_DIRECTORY_WITHFILES02";			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sDirToExtractTo, true);
			if(objDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sDirToExtractTo);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Datei '" + objFileTemp.getAbsolutePath() + " im Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfad mit Elternverzeichnis vergleichen
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getParentFile().getAbsolutePath()));
				}
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
			JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
			String sDirToExtract="template";
			String sDirToExtractTo; 
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL\\TRUNK_OF_DIRECTORY";
			
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN FILE OBJEKT.
			File[] objaCreated01 = JarEasyZZZ.peekResourceDirectories(objJar,sDirToExtract, sDirToExtractTo, false);
			if(objaCreated01==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht gefunden (NULL-WERT).");
			}
			if(objaCreated01[0].exists()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte  nicht erstellt sein.");
			}
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL\\TRUNK_OF_FILES";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);			
			File[] objaCreated02 = JarEasyZZZ.peekResourceDirectories(objJar, sDirToExtract, sDirToExtractTo, true);
			if(objaCreated02==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht gefunden (NULL-WERT).");
			}
			if(objaCreated02[0].exists()) {
				fail("Datei sollte nicht erstellt sein '" + objaCreated02[0] + "' und das Verzeichnis '" + sDirToExtractTo + "' sollte auch nicht erstellt sein.");
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
			ZipEntry[] objaCreated03 = JarEasyZZZ.extractFromJarAsTrunkZipEntries(objJar,sDirToExtract, sDirToExtractTo, false);
			if(objaCreated03==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			if(!objaCreated03[0].isDirectory()) {
				fail("Verzeichnis '" + sDirToExtractTo + "' sollte zwar nicht auf Platte existieren aber als TRUNK zurückgeliefert worden sein.");
			}
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTo = "FGL\\TRUNK2_OF_FILES";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);			
			ZipEntry[] objaCreated04 = JarEasyZZZ.extractFromJarAsTrunkZipEntries(objJar, sDirToExtract, sDirToExtractTo, true);
			if(objaCreated04==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			if(objaCreated04[0].isDirectory()) {
				fail("Kein Verzeichnis '" + sDirToExtractTo + "' sollte als TRUNK zurückgeliefert worden sein, nur Dateien.");
			}
			
			
			//Ba) Verzeichnis mit Unterverzeichnissen
			sDirToExtractTo = "FGL\\TRUNK3_OF_DIRECTORY";
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testExtractFromJarAsTrunk_WithoutCreatingIt() {
		try{
			JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
			String sDirToExtract="template";
			String sDirToExtractTo; 
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL\\TRUNKENTRY_OF_DIRECTORYDUMMY"; //D.h. dieses Verzeichnis darf nicht erstellt werden.
			boolean bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sDirToExtractTo);			
			if(!bErg) {
				fail("Verzeichnis '" + sDirToExtractTo + "' konnte zu Testbeginn nicht geloescht werden.");
			}
						
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN ZipEntry und FILE OBJEKTE, die es noch nicht auf der Platte zu geben braucht.
			HashMap<ZipEntry,File> hmTrunk = new HashMap<ZipEntry,File>();
			
			//Merke: hmTrunk ist sonst leer. CallByReference-Problematik, Lösung mit Zwischenobjekt
			ReferenceZZZ<HashMap<ZipEntry,File>> hashmapTrunk=new ReferenceZZZ<HashMap<ZipEntry,File>>(hmTrunk);			
			bErg = JarEasyZZZ.extractFromJarAsTrunk(objJar, sDirToExtract, sDirToExtractTo, hashmapTrunk);// Das dauert laaange!!!
			if(!bErg) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht als Trunk extrahiert.");
			}			
			hmTrunk = hashmapTrunk.get();
			assertFalse(hmTrunk.size()==0);
			assertTrue(hmTrunk.size()>=1);
			
			Set<ZipEntry>setEntry = hmTrunk.keySet();
			for(ZipEntry entry : setEntry) {
				File fileTemp = hmTrunk.get(entry);
				bErg = FileEasyZZZ.exists(fileTemp);
				if(bErg) {
					fail("Datei sollte nur als Trunk und nicht jetzt schon existieren: " + fileTemp.getAbsolutePath());
				}
			}
								
			
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testSaveTrunkAsFile() {
		try{
			JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
			String sDirToExtract="template";
			String sDirToExtractTo; 
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTo = "FGL\\TRUNKENTRY_OF_DIRECTORY";
					    
			//VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			boolean bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sDirToExtractTo);
			if(!bErg) {
				fail("Verzeichnis '" + sDirToExtractTo + "' konnte zu Testbeginn nicht geloescht werden.");
			}
						
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN ZipEntry und FILE OBJEKTE, die es noch nicht auf der Platte zu geben braucht.
			HashMap<ZipEntry,File> hmTrunk = new HashMap<ZipEntry,File>();
			
			//Merke: hmTrunk ist sonst leer. CallByReference-Problematik, Lösung mit Zwischenobjekt
			ReferenceZZZ<HashMap<ZipEntry,File>> hashmapTrunk=new ReferenceZZZ<HashMap<ZipEntry,File>>(hmTrunk);			
			bErg = JarEasyZZZ.extractFromJarAsTrunk(objJar, sDirToExtract, sDirToExtractTo, hashmapTrunk);//das dauert Laaange!!!
			if(!bErg) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht als Trunk extrahiert.");
			}	
			
			hmTrunk = hashmapTrunk.get();			
			assertFalse(hmTrunk.size()==0);
			assertTrue(hmTrunk.size()>=1);
						
			Set<ZipEntry>setEntry = hmTrunk.keySet();
			for(ZipEntry entry : setEntry) {
				File fileTemp = hmTrunk.get(entry);
				bErg = FileEasyZZZ.exists(fileTemp);
				if(bErg) {
					fail("Datei sollte nur als Trunk und nicht jetzt schon existieren: " + fileTemp.getAbsolutePath());
				}
			}
			
			
			//Nun erst auf die Platte bringen
			bErg = JarEasyZZZ.saveTrunkAsFile(objJar, hmTrunk);
			assertTrue(bErg);
			for(ZipEntry entry : setEntry) {
				File fileTemp = hmTrunk.get(entry);
				bErg = FileEasyZZZ.exists(fileTemp);
				if(!bErg) {
					fail("Datei sollte jetzt am Schluss existieren: " + fileTemp.getAbsolutePath());
				}
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFiles() {
		try{
			
			File[] objaReturn; File objFile; IFileFilePartFilterZipUserZZZ objFilterFileInJar;
			String sTargetDirectoryPathRoot; String sPath; String sFilename; String sLog;
			
			//#############
			
			sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}	
		    		    		   		    		
		    //#######################################################################
		    //TESTFÄLLE FÜR: DATEIEN GEFUNDEN
		    //VARIANTE 1: Pfad und Dateinamen im Dateinamen
		    sTargetDirectoryPathRoot = "FGL\\PEEK_FILES01";
		    sFilename = "test01.txt";  //Dies Datei soll über alle Verzeichnisse der JAR-DAtei gesuht werden
						
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
			
			JarFile objJarFile = JarKernelZZZ.getJarFileUsed(JarKernelZZZ.iJAR_TEST);	    
		    assertNotNull("Die Jar-Dummy Datei sollte gefunden worden sein.", objJarFile);
		    	  
			objaReturn = JarEasyZZZ.peekFiles(objJarFile, sFilename, sTargetDirectoryPathRoot);
			assertNotNull(objaReturn);
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
	
	
}//END Class
