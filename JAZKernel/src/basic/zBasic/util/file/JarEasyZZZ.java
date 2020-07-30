package basic.zBasic.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.FileDirectoryFilterInJarZZZ;
import basic.zBasic.util.file.jar.FileFileFilterInJarZZZ;
import basic.zBasic.util.file.jar.JarInfo;
import basic.zBasic.util.file.zip.FileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.FileDirectoryFilterInZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFilenamePartFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class JarEasyZZZ implements IConstantZZZ{

	public static String computeUrlPathForContainingResource(JarFile objJar, String sResourcePath) {
		String sReturn = null;
		main:{
			if(objJar==null) break main;
			if(StringZZZ.isEmpty(sResourcePath)) break main;
				
			String sJarFile = objJar.getName();
			sReturn = "jar:file:/" + sJarFile + "!/" + sResourcePath; //Merke das Ausrufezeichen ist wichtig. Sonst Fehler: no !/ in spec
		}//end main:
		return sReturn;
	}
	
		
	/**
	*  This method is responsible for extracting resource files from within the .jar to an temporarily existing file.
	*  NOT a file peristed in the temp - Directory !!!
	*  @param filePath The filepath is the directory within the .jar from which to extract the file.
	*  @return A file object to the extracted file
	*  **/
	public static File extractFileFromJarAsTemp(JarFile objJarFile, String sFilePath) throws ExceptionZZZ {
			File objReturn=null;
			main:{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(StringZZZ.isEmpty(sFilePath)){
					ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}

				try{
			        File f = File.createTempFile(sFilePath, null);
			        FileOutputStream resourceOS = new FileOutputStream(f);
			        byte[] byteArray = new byte[1024];
			        int i;
			        //InputStream classIS = getClass().getClassLoader().getResourceAsStream("Resources/"+filePath);
			        InputStream classIS = JarEasyZZZ.class.getClassLoader().getResourceAsStream(sFilePath);
			//While the input stream has bytes
			        while ((i = classIS.read(byteArray)) > 0) 
			        {
			//Write the bytes to the output stream
			            resourceOS.write(byteArray, 0, i);
			        }
			//Close streams to prevent errors
			        classIS.close();
			        resourceOS.close();
			        objReturn = f;		    
				}catch (Exception e){
			    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			    }	    
				}//end main:
				return objReturn;
		}
	
	
	/** Merke: Wenn ein Verzeichnis aus der JAR Datei zu extrahieren ist, dann wird lediglich ein File Objekt zurückgegeben.
	 *         Die Datei selbst - oder das Verzeichnis - wird nicht erzeugt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47	
	 */
	public static File[] extractFromJarAsTrunkFileDummies(JarFile objJarFile, String sSourceFilePath, String sTargetDirectoryPathIn, boolean bExtractFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			if(StringZZZ.isEmpty(sSourceFilePath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= ".";
			}else {
				sTargetDirectoryPath = sTargetDirectoryPathIn;
			}
			
			if(bExtractFiles) {
				objaReturn = JarEasyZZZ.extractFilesFromJarAsTrunkFileDummies(objJarFile, sSourceFilePath,sTargetDirectoryPath);				
			}else {				
				File objReturn = JarEasyZZZ.extractDirectoryFromJarAsTrunkFileDummy(objJarFile, sSourceFilePath,sTargetDirectoryPath);
				objaReturn = new File[1];
				objaReturn[0] = objReturn;
			}
			    
			}//end main:
			return objaReturn;
	}
	
	/** Merke: Wenn ein Verzeichnis aus der JAR Datei zu extrahieren ist, dann wird lediglich die JAR Datei zurückgegeben.
	 *         Verwende für die Behandlung eines ganzen Verzeichnisses (oder auch nur des Verzeichnisses) 
	 *         die Methode, die ein Verzeichnis im TEMP-Ordner des HOST Rechners erstellt. 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractDirectoryFromJarAsTrunkFileDummy(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			try{
				if(StringZZZ.isEmpty(sSourceDirectoryPath)){
					ExceptionZZZ ez = new ExceptionZZZ("No source filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sTargetDirectoryPath;
				if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
					sTargetDirectoryPath= ".";
				}else {
					sTargetDirectoryPath = sTargetDirectoryPathIn;
				}
				
			
			
				//1. Aus der Jar Datei nur das Verzeichnis herausfiltern.						
				String archiveName = objJarFile.getName();				
				sSourceDirectoryPath = JarEasyZZZ.toJarPath(sSourceDirectoryPath);				
				IFileDirectoryPartFilterZipUserZZZ objFilterDirectoryInJar = new FileDirectoryFilterInJarZZZ(sSourceDirectoryPath);
				IFileDirectoryPartFilterZipZZZ objFilterFilePathPart = objFilterDirectoryInJar.getDirectoryPartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );  //Merke: Das dauert laaange
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
				
				
					ZipFile zf = null;
					while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
						
						//Nun aus dem ZipEntry ein File Objekt machen 
						//https://www.rgagnon.com/javadetails/java-0429.html
						File objFileTemp = new File(sTargetDirectoryPath, zeTemp.getName());
						objaFileTempInTemp.add(objFileTemp);
						
//						geht das nur in einem anderen Verzeichnis, als Kopie?					
//						zf = objJarInfo.getZipFile();						
//						InputStream is = zf.getInputStream(zeTemp);
//						String sKeyNormed = StringZZZ.replace(sKey, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
//						String sPath = FileEasyZZZ.joinFilePathName(sDirPathRoot, sKeyNormed);
//						
//						//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
//						FileEasyZZZ.removeFile(sPath);
//												
//						Files.copy(is, Paths.get(sPath));
//						File objFileTempInTemp = new File(sPath);	
//						objaFileTempInTemp.add(objFileTempInTemp);
					}
					if(zf!=null) zf.close();
					File[] objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
					objReturn = objaReturn[0];
				} catch (IOException e) {
					ExceptionZZZ ez  = new ExceptionZZZ("IOException: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}catch (Exception e){
			    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//end main:
			return objReturn;
	}
	
	/** 
	 Merke1: Wenn ein Verzeichnis aus der JAR Datei zu extrahieren ist, dann wird lediglich ein File Objekt zurückgegeben.
             Die Datei selbst - oder das Verzeichnis - wird nicht erzeugt.
	 Merke2: Mit diesen File-Objekten kann man eigentlich nur die Existenz in der JAR-Datei prüfen.
		     Einen Input Stream zu bekommen ist die Voraussetzung für das Erstellen der Datei als Kopie.
		     Diesen kann man aus dem ZipEntry bekommen....
		     
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
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47	
	 */
	public static ZipEntry[] extractFromJarAsTrunkZipEntries(JarFile objJarFile, String sSourceFilePath, String sTargetDirectoryPathIn, boolean bExtractFiles) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
		main:{
			if(StringZZZ.isEmpty(sSourceFilePath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= ".";
			}else {
				sTargetDirectoryPath = sTargetDirectoryPathIn;
			}
			
			if(bExtractFiles) {
				objaReturn = JarEasyZZZ.extractFilesFromJarAsTrunkZipEntries(objJarFile, sSourceFilePath,sTargetDirectoryPath);				
			}else {				
				ZipEntry objReturn = JarEasyZZZ.extractDirectoryFromJarAsTrunkEntry(objJarFile, sSourceFilePath,sTargetDirectoryPath);
				objaReturn = new ZipEntry[1];
				objaReturn[0] = objReturn;
			}
			    
			}//end main:
			return objaReturn;
	}
	
	/** Merke: Wenn ein Verzeichnis aus der JAR Datei zu extrahieren ist, dann wird lediglich die JAR Datei zurückgegeben.
	 *         Verwende für die Behandlung eines ganzen Verzeichnisses (oder auch nur des Verzeichnisses) 
	 *         die Methode, die ein Verzeichnis im TEMP-Ordner des HOST Rechners erstellt. 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static ZipEntry extractDirectoryFromJarAsTrunkEntry(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		ZipEntry objReturn=null;
		main:{
			try{
				if(StringZZZ.isEmpty(sSourceDirectoryPath)){
					ExceptionZZZ ez = new ExceptionZZZ("No source filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sTargetDirectoryPath;
				if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
					sTargetDirectoryPath= ".";
				}else {
					sTargetDirectoryPath = sTargetDirectoryPathIn;
				}
				
			
			
				//1. Aus der Jar Datei nur das Verzeichnis herausfiltern.						
				String archiveName = objJarFile.getName();				
				sSourceDirectoryPath = JarEasyZZZ.toJarPath(sSourceDirectoryPath);				
				IFileDirectoryPartFilterZipUserZZZ objFilterDirectoryInJar = new FileDirectoryFilterInJarZZZ(sSourceDirectoryPath);
				IFileDirectoryPartFilterZipZZZ objFilterFilePathPart = objFilterDirectoryInJar.getDirectoryPartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<ZipEntry>objaZipEntry = new ArrayList<ZipEntry>();
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);					
					objaZipEntry.add(zeTemp);
						
					ZipEntry[] objaReturn = ArrayListZZZ.toZipEntryArray(objaZipEntry);
					objReturn = objaReturn[0];
				}
				}catch (Exception e){
			    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
		}//end main:
		return objReturn;
	}
	
	public static ZipEntry[] extractFilesFromJarAsTrunkZipEntries(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
		main:{
			if(StringZZZ.isEmpty(sSourceDirectoryPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= ".";
			}else {
				sTargetDirectoryPath = sTargetDirectoryPathIn;
			}
			
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sSourceDirectoryPath);
				FilenamePartFilterPathZipZZZ objFilterFilePathPart = objFilterFileInJar.getDirectoryPartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<ZipEntry>objaEntryTemp = new ArrayList<ZipEntry>();
				while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
						objaEntryTemp.add(zeTemp);
				}
				objaReturn = ArrayListZZZ.toZipEntryArray(objaEntryTemp);				
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
			}//end main:
			return objaReturn;
	}
	
	public static File extractFromJarAsTrunk(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn,HashMap<ZipEntry,File>hmTrunk) throws ExceptionZZZ {
		File objReturn = null; //Das soll das Target - Directory sein
		main:{
			if(StringZZZ.isEmpty(sSourceDirectoryPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= ".";
			}else {
				sTargetDirectoryPath = sTargetDirectoryPathIn;
			}
			objReturn = new File(sTargetDirectoryPath);//Aber: Das sollte damit nicht automatisch erstellt sein.
			
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sSourceDirectoryPath);
				FilenamePartFilterPathZipZZZ objFilterFilePathPart = objFilterFileInJar.getDirectoryPartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );//Das dauert laaange
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();				
				while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
						
						//Nun aus dem ZipEntry ein File Objekt machen 
						//https://www.rgagnon.com/javadetails/java-0429.html
						File objFileTemp = new File(sTargetDirectoryPath, zeTemp.getName());
						
						//Das Ergebnis in die Trunk - HashMap packen
						hmTrunk.put(zeTemp, objFileTemp);
				}
								
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
			}//end main:
			return objReturn;
	}
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractDirectoryToTemp(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn=null;
		main:{			
			if(objFileAsJar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)) {
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			JarFile objJar = JarEasyZZZ.toJarFile(objFileAsJar);			
			objReturn = JarEasyZZZ.extractDirectoryToTemp(objJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, bWithFiles);
			
			
			}//end main:
			return objReturn;
	}
	
	public static File extractDirectoryToTemp(JarFile objJar, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn = null;
		
		main:{
			if(objJar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			if(StringZZZ.isEmpty(sDirectoryFilePathInJar)){
				//ODER: ROOT DER JAR DATEI, WIE?
				ExceptionZZZ ez = new ExceptionZZZ("No source filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			sDirectoryFilePathInJar = JarEasyZZZ.toJarPath(sDirectoryFilePathInJar);
			
			
			String sTargetDirectoryFilepath;
			if(sTargetDirectoryFilepathIn==null)
				sTargetDirectoryFilepath="";  //TEMP VERZEICHNIS???
			else {
				//Links und rechts ggfs. übergebenen Trennzeichen entfernen. So normiert kann man gut weiterarbeiten.				
				sTargetDirectoryFilepath=StringZZZ.stripFileSeparators(sTargetDirectoryFilepathIn);									
			}
			
			//+++ Verwende nun die Resourcen - Behandlung, damit das Verzeichnis auch tatsächlich erstellt wird ++++++++++++
			try {
				File[] objaDir = null;			
				//Nur das Verzeichnis erstellen... also den reinen Verzeichnis Filter
				IFileDirectoryPartFilterZipUserZZZ objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sDirectoryFilePathInJar);
				
				File objFileAsJar = JarEasyZZZ.toFile(objJar);
				objaDir = ResourceEasyZZZ.findDirectoryInJar(objFileAsJar, objFilterDirInJar, sTargetDirectoryFilepath);
			    objReturn = objaDir[0];
			
			    if(bWithFiles) {			
					//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
					IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sDirectoryFilePathInJar);
					objaDir = ResourceEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryFilepath);					
				}
			
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
		}//end main:
		return objReturn;
	}
	
	
	public static File[] extractFilesFromJarAsTrunkFileDummies(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			if(StringZZZ.isEmpty(sSourceDirectoryPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= ".";
			}else {
				sTargetDirectoryPath = sTargetDirectoryPathIn;
			}
			
			

			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sSourceDirectoryPath);
				FilenamePartFilterPathZipZZZ objFilterFilePathPart = objFilterFileInJar.getDirectoryPartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
					//Nun aus dem ZipEntry ein File Objekt machen 
					//https://www.rgagnon.com/javadetails/java-0429.html
					File objFileTemp = new File(sTargetDirectoryPath, zeTemp.getName());
					objaFileTempInTemp.add(objFileTemp);					
				}
				objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
				
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
			}//end main:
			return objaReturn;
	}
	
	public static boolean saveTrunkAsFile(JarFile jf, HashMap<ZipEntry,File> hmTrunk) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
//			try {
				if(jf==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(hmTrunk==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "HashMap with Trunk ZipEntry, File - Objects", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				
				
				Set<ZipEntry> setEntry = hmTrunk.keySet();
				Iterator<ZipEntry> itEntry = setEntry.iterator();				
				while(itEntry.hasNext()) {
					ZipEntry zeTemp = itEntry.next();
					File fileTemp = (File)hmTrunk.get(zeTemp);
					
					bReturn = JarEasyZZZ.saveTrunkToFile(jf, zeTemp, fileTemp);
					if(bReturn=false) {
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Unable to save File - Object '" + fileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;
						//break main;
					}
				}
				bReturn = true;
//			} catch (IOException e) {
//				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
//				throw ez;	
//			}		
		}//end main:
		return bReturn;
	}
	
	public static boolean saveTrunkToFile(JarFile jf, ZipEntry ze, File fileAsTrunk) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			try {
				if(jf==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(fileAsTrunk==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "File Object as trunk", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(fileAsTrunk.exists()) {					
					boolean bErg = FileEasyZZZ.removeFile(fileAsTrunk);//!!! Bereits existierende Datei ggfs. löschen.
					if(!bErg) {
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "File Object as trunk existed, but was not replacable", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;
					}
				}
				
				String sPath = fileAsTrunk.getAbsolutePath();
				
				//Das Verzeichnis (inkl. Parentverzeichnisse) erstellen.				
				boolean bErg = FileEasyZZZ.createDirectoryForFile(fileAsTrunk);
				
				//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)																
				InputStream is = jf.getInputStream(ze);
				Files.copy(is, Paths.get(sPath));
				bReturn = true;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;	
			}
			
		}//end main:
		return bReturn;
	}
	
	public static File saveTrunkToDirectory(JarFile jf, ZipEntry ze, String sTargetDirectoryPathIn ) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			try {
				if(jf==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(ze==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				String sTargetDirectoryPath;
				if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
					sTargetDirectoryPath= ".";
				}else {
					sTargetDirectoryPath = sTargetDirectoryPathIn;
				}
				
							
				//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)																
				InputStream is = jf.getInputStream(ze);
				objReturn = new File(sTargetDirectoryPath, ze.getName());
				if(objReturn.exists()) {
					boolean bErg = FileEasyZZZ.removeFile(objReturn);//!!! Bereits existierende Datei ggfs. löschen.
					if(!bErg) {
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "File Object as trunk existed, but was not replacable", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;
					}
				}
				
				String sPath = objReturn.getAbsolutePath();
				Files.copy(is, Paths.get(sPath));				
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;	
			}
			
		}//end main:
		return objReturn;
	}
	
	public static File getJarCurrent() throws ExceptionZZZ{
		File objReturn=null;
		try {
			String sPath = JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			objReturn = new File(sPath);
		} catch (URISyntaxException e) {
			ExceptionZZZ ez = new ExceptionZZZ("URISyntaxException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;			
		}
		return objReturn;
	}
	
	public static File getJarDirectoryCurrent() throws ExceptionZZZ{
		File objReturn = null;
		try {
			String sLog = null;
			if(JarEasyZZZ.isInJarStatic()){
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Running in a jar file.";
				System.out.println(sLog);
				
				String sPath = JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Current Directory of jar: '" + sPath + "'";
				System.out.println(sLog);
				
				objReturn = FileEasyZZZ.getDirectoryFromFilepath(sPath);
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Not running in a jar file.";
				System.out.println(sLog);
			}
		} catch (URISyntaxException e) {
			ExceptionZZZ ez = new ExceptionZZZ("URISyntaxException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;			
		}
		return objReturn;
	}
	
	/** Wenn man einen Pfad innerhalb einer JAR-Datei übergibt, bekommt man hier die JAR-als File Objekt zurück.
	 *  https://stackoverflow.com/questions/8014099/how-do-i-convert-a-jarfile-uri-to-the-path-of-jar-file
	 *  Eine URL wird aussehen wie in JarEasyZZZ.computeUrlPathForContainingResource(...) erstellt.
	 * @author Fritz Lindhauer, 19.07.2020, 08:43:29
	 * @throws ExceptionZZZ 
	 */
	public static File getJarCurrentFromUrl(JarFile objJarFile, String sUrl) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			try {
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//							
			//String sUrl = JarEasyZZZ.computeUrlPathForContainingResource(objJarFile, sDirectoryPath); 
	//		String sUrl = JarEasyZZZ.computeUrlPathForContainingResource(objJarFile, sKey);
	//		String sLog = ReflectCodeZZZ.getPositionCurrent()+": String to fetch URL from JarFileObject '" + sUrl + "'" ;
	//	    System.out.println(sLog);			   
		    
			URL url = new URL(sUrl);		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": URL created from JarFileObject '" + url + "'" ;
		    System.out.println(sLog);
		    
			
			//DAS Holt immer nur die JAR - Datei selbst:
		    JarURLConnection connection = (JarURLConnection) url.openConnection();
			URI uri = connection.getJarFileURL().toURI();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": URI.getPath created from JarFileObject '" + uri.getPath() + "'" ;
		    System.out.println(sLog);
		    	    
		    objReturn = new File(uri);
	    
			} catch (MalformedURLException e) {
				ExceptionZZZ ez = new ExceptionZZZ("MalformedURLException '" + e.getMessage() + "'", iERROR_PARAMETER_VALUE, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException '" + e.getMessage() + "'", iERROR_PARAMETER_VALUE, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (URISyntaxException e) {
				ExceptionZZZ ez = new ExceptionZZZ("URISyntaxException '" + e.getMessage() + "'", iERROR_PARAMETER_VALUE, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	
	//### Interfaces
	//aus iRessourceHandlingObjectZZZ
	
	//### Ressourcen werden anders geholt, wenn die Klasse in einer JAR-Datei gepackt ist. Also:
		/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
		 *   Merke: Static Klassen müssen diese Methode selbst implementieren.
		 * @return
		 * @author lindhaueradmin, 21.02.2019
		 * @throws ExceptionZZZ 
		 */
		public boolean isInJar() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = JarEasyZZZ.isInJar(this.getClass());
			}
			return bReturn;
		}
		
		/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
		 *   Merke: Static Klassen müssen diese Methode selbst implementieren. Das ist dann das Beispiel.
		 * @return
		 * @author lindhaueradmin, 21.02.2019
		 * @throws ExceptionZZZ 
		 */
		public static boolean isInJarStatic() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = JarEasyZZZ.isInJar(JarEasyZZZ.class);
			}
			return bReturn;
		}
	
	/** Prüft, ob die Datei / Ressource einer JAR-Datei liegt.
	 *  Merke: In einer .jar Datei kann kein Zugriff über File-Objekte erfolgen.
	 *  
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static boolean isInJar(Class classObject) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(classObject==null){
				ExceptionZZZ ez = new ExceptionZZZ("No class object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Siehe https://www.rgagnon.com/javadetails/java-0391.html
			String className = classObject.getName().replace('.', '/');
			//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": className='"+className+"'");
						
			String classJar = classObject.getResource("/" + className + ".class").toString();
			//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": classJar='"+classJar+"'");
			
			if (classJar.startsWith("jar:")) {
			    System.out.println("*** running from jar!");
				 bReturn=true;
			}else{
				 System.out.println("*** NOT running from jar!");
			}
		}//end main:
		return bReturn;
		
		   //Alternativer Ansatz: https://www.edureka.co/community/5035/retrieving-the-path-of-a-running-jar-file			   
		   //return new File(AbcClass.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
	}
	
	public static File searchRessource(String sPath) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLog = null;
			final File jarFile = new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			if(jarFile.isFile()) {  // Run with JAR file
			    JarFile jar;
				try {
					jar = new JarFile(jarFile);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
				    System.out.println(sLog);
				    String sPathInJar = StringZZZ.replace(sPath, File.separator, "/"); //Innerhalb der JAR-Datei wird immer mit / gearbeitet.
				    sPathInJar = StringZZZ.stripLeft(sPathInJar, "/"); 
				    sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Searching for '" + sPathInJar + "'";
				    System.out.println(sLog);
				    JarEntry entry = jar.getJarEntry(sPathInJar);
				    if(entry==null){
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND: '" + sPathInJar +"'";
				    	System.out.println(sLog);			    	
				    }else{
				    	
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND: '" + sPathInJar +"'";
				    	System.out.println(sLog);
				    	
				    	//Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
				    	if(entry.isDirectory()) {
				    		
				    	}else {
				    		objReturn = JarEasyZZZ.extractFileFromJarAsTemp(jar, sPathInJar);
				    	}
				    }
				    
//Aus Doku gründen stehen lassen: Alle Einträge eines Jar-Files durchgehen:				    
//			    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
//			    while(entries.hasMoreElements()) {
//			        final String name = entries.nextElement().getName();
//			        if (name.startsWith(sFile)) { //filter according to the path
//			        	System.out.println("BINGO");
//			            System.out.println(name);
//			            break main;
//			        }else{
//			        	//System.out.println(name);
//			        }
//			    }
			    jar.close();
				} catch (IOException e1) {
					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else{
				//Keine Jar Datei
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) NO JAR FILE FOUND'";
				System.out.println(sLog);			
			}						
		}//end main:
		return objReturn;
	}
	
	public static JarFile toJarFile(File objFileAsJar) throws ExceptionZZZ{
		JarFile objReturn = null;
		main:{
			if(objFileAsJar==null)break main;
			if(!FileEasyZZZ.isJar(objFileAsJar)){
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
			try {
				objReturn = new JarFile(objFileAsJar);
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + e.getMessage(),ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	public static File toFile(JarFile objJar) {
		File objReturn = null;
		main:{
			if(objJar==null)break main;
			
			String sFileAsJar = objJar.getName();
			objReturn = new File (sFileAsJar);
		}//end main:
		return objReturn;
	}
	
	/** Innerhalb der JAR-Datei wird immer mit / gearbeitet.
	 *  Also einen Dateipfad dahingehend normieren.
	 * @param sFilePath
	 * @return
	 * @author Fritz Lindhauer, 19.07.2020, 07:35:31
	 * @throws ExceptionZZZ 
	 */
	public static String toJarPath(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)){
				//ODER: ROOT DER JAR DATEI, WIE?
				ExceptionZZZ ez = new ExceptionZZZ("No source FilePath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Innerhalb der JAR-Datei wird immer mit / gearbeitet.
			sReturn = StringZZZ.replace(sFilePath, FileEasyZZZ.sDIRECTORY_SEPARATOR, "/");
		
			//UND: Abschliessend gibt es bei Verzeichnissen ein / ... aber NUR 1x
			sReturn=StringZZZ.stripFileSeparatorsRight(sReturn);
			sReturn=sReturn+"/";
		}
		return sReturn;
	}
	
	/**Innerhalb der JAR-Datei wird immer mit / gearbeitet dies wieder rückgängig machen.
	 * @param sJarPath
	 * @return
	 * @author Fritz Lindhauer, 19.07.2020, 07:35:08
	 * @throws ExceptionZZZ 
	 */
	public static String toFilePath(String sJarPath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sJarPath)){
				//ODER: ROOT DER JAR DATEI, WIE?
				ExceptionZZZ ez = new ExceptionZZZ("No source JarPath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			sReturn = StringZZZ.replace(sJarPath, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
			
			//UND: Abschliessend gibt es bei Verzeichnissen ein \ ... aber NUR 1x
			sReturn=StringZZZ.stripFileSeparatorsRight(sReturn);
			
		}
		return sReturn;
		
	}
}



