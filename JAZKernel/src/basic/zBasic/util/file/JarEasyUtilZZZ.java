package basic.zBasic.util.file;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.JarInfo;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFilenamePartFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEasyZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zBasic.util.machine.EnvironmentZZZ;

/**
 *  
 * 
 * @author Fritz Lindhauer, 19.10.2020, 08:10:14
 * 
 */
public class JarEasyUtilZZZ extends ObjectZZZ{
	private JarEasyUtilZZZ(){
		//Zum Verstecken des Konstruktors
	}
	
	public static File[] findFile(File objDirectory, IFilenameFilterExpansionUserZZZ objFilter) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			
			check:{				
				String sDirPath = null;
				if(objDirectory==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "The Directory '" + objDirectory.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				boolean bIsJar = FileEasyZZZ.isJar(objDirectory);
				if(bIsJar) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The Directory '" + objDirectory.getPath() + "', is a jar File. To extract from a jar File use another method.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(objDirectory.exists()==false){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The Directory '" + objDirectory.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;
				}else if(objDirectory.isDirectory()==false){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The file '" + objDirectory.getPath() + "', was expected to be a file, not e.g. a directory.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;					
				}
								
				if(objFilter==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileFilter missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;				
				}
				
			}//End check
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden		
			objaReturn = objDirectory.listFiles(objFilter);		
		}//End main		 	
		return objaReturn;
	}
		
	public static File[] findDirectoryInJar(File objFileJar, IFileDirectoryPartFilterZipUserZZZ objDirectoryFilterInJar, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{	
			
			HashMap<ZipEntry,File> hmTrunk = JarEasyUtilZZZ.findDirectoryInJarAsTrunk(objFileJar, objDirectoryFilterInJar, sTargetDirectoryPathIn, bWithFiles);
			
			JarFile jf = JarEasyUtilZZZ.toJarFile(objFileJar);
			objaReturn = JarEasyZZZ.saveTrunkAsFiles(jf, hmTrunk);
						
		}//End main		 	
		return objaReturn;
	}
	
	public static HashMap<ZipEntry,File> findDirectoryInJarAsTrunk(File objFileJar, IFileDirectoryPartFilterZipUserZZZ objDirectoryFilterInJar, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ{
		HashMap<ZipEntry,File>hmReturn = new HashMap<ZipEntry,File>();
		main:{
			try {
			check:{								
				String sDirPath = null;
				if(objFileJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				boolean bIsJar = FileEasyZZZ.isJar(objFileJar);
				if(!bIsJar) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The File '" + objFileJar.getPath() + "', is not a jar File. To extract from a directory use another method.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}

				if(objFileJar.exists()==false){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The JarFile '" + objFileJar.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
																
				if(objDirectoryFilterInJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileDirectoryFilterForJar missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}														
			}//End check
		
			String sApplicationKeyAsSubDirectoryTemp;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)) {
				sApplicationKeyAsSubDirectoryTemp = "FGL";
			}else {
				sApplicationKeyAsSubDirectoryTemp = sTargetDirectoryPathIn;
			}
			
			//####################
			String sPathDirTemp;
			if(FileEasyZZZ.isPathRelative(sApplicationKeyAsSubDirectoryTemp)){
				String sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
				sPathDirTemp = FileEasyZZZ.joinFilePathName(sDirTemp, sApplicationKeyAsSubDirectoryTemp);					
			}else {
				sPathDirTemp = sApplicationKeyAsSubDirectoryTemp;
			}
				
			//Innerhalb der JAR-Datei wird immer mit / gearbeitet dies wieder rückgängig machen.
			sPathDirTemp = JarEasyUtilZZZ.toFilePath(sPathDirTemp);
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden
			//https://www.javaworld.com/article/2077586/java-tip-83--use-filters-to-access-resources-in-java-archives.html			
			String archiveName = objFileJar.getAbsolutePath();
			IFileDirectoryPartFilterZipZZZ objPartFilter = objDirectoryFilterInJar.getDirectoryPartFilter();
			JarInfo objJarInfo = new JarInfo( archiveName,  objPartFilter );//Mit dem Filter wird nur das Verzeichnis herausgefiltert.
			
			//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
			Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();																		
			Set<String> setEntryName = ht.keySet();
			Iterator<String> itEntryName = setEntryName.iterator();	
			if(bWithFiles) { //Fall: Mit Dateien
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					String sNameTemp = JarEasyUtilZZZ.toFilePath(zeTemp.getName());
					
					//Nun aus dem ZipEntry ein File Objekt machen 
					//https://www.rgagnon.com/javadetails/java-0429.html				
					File objFileTemp = new File(sPathDirTemp, sNameTemp);
					
					//Das Ergebnis in die Trunk - HashMap packen
					hmReturn.put(zeTemp, objFileTemp);
				}
			}else { //Fall: Ohne Dateien
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					if(zeTemp.isDirectory()) {
						String sNameTemp = JarEasyUtilZZZ.toFilePath(zeTemp.getName());
				
						//Nun aus dem ZipEntry ein File Objekt machen 
						//https://www.rgagnon.com/javadetails/java-0429.html				
						File objFileTemp = new File(sPathDirTemp, sNameTemp);
				
						//Das Ergebnis in die Trunk - HashMap packen
						hmReturn.put(zeTemp, objFileTemp);
					}
				}
			}
						
	}catch (Exception e){
    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
    }	    
			
			
			//Wie nun vom ht nach objaReturn, also dem fertigen File-Objekt ???
			//Es geht nur als temporäres Objekt, das man in ein temp-Verzeichnis ablegt.
						
			//NEIN, jetzt als HashMap-Trunk zurückgeben
//			ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
//			try {
//				ZipFile zf = null;
//				while(itEntryName.hasNext()) {
//					String sKey = itEntryName.next();
//					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
//					
//					//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)					
//					zf = objJarInfo.getZipFile();						
//					InputStream is = zf.getInputStream(zeTemp);
//					
//					//Entferne ggf. künstlich hinzugefügte DirectorySeparatoren am Anfang/Ende.
//					//z.B. in Jar - Dateien steht für Verzeichnisse immer ein /  am Ende.
//					//Links und rechts ggfs. übergebenen Trennzeichen entfernen. So normiert kann man gut weiterarbeiten.				
//					String sDirName = StringZZZ.stripFileSeparators(sKey);
//					
//					//Ggfs. in den Jar/Zip Verzeichnissen verwendete / wieder in Backslashes abändern.
//					String sDirNameNormed = StringZZZ.replace(sDirName, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
//					String sPath = FileEasyZZZ.joinFilePathName(sPathDirTemp, sDirNameNormed);
//					
//					File objFileTempInTemp = null;
//					if(ht.get(sKey).isDirectory()) {
//						objFileTempInTemp = new File(sPath);
//						
////						File objFileDir = new File(sPath);							
////						if(objFileDir.exists()) {
////							//!!! Bereits existierende Inhalte  löschen
////							FileEasyZZZ.removeDirectoryContent(objFileDir,true);
////						}else{
////							//Nun das Verzeichnis erstellen.
////							FileEasyZZZ.createDirectory(sPath);
////						}
//					}else {
//						//Dateiene kopieren						
////						Files.copy(is, Paths.get(sPath));
//					}
//					File objFileTempInTemp = new File(sPath);	
//					objaFileTempInTemp.add(objFileTempInTemp);						
//				}
//				zf = objJarInfo.getZipFile();		
//				if(zf!=null) zf.close();
//				objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
				
	}//End main		 	
	return hmReturn;
	}
	
	
	public static File[] findFileInJar(File objFileJar, IFileFilePartFilterZipUserZZZ objFilterFileInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			check:{				
				if(objFileJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				boolean bIsJar = FileEasyZZZ.isJar(objFileJar);
				if(!bIsJar) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The File '" + objFileJar.getPath() + "', is not a jar File. To extract from a directory use another method.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}

				if(objFileJar.exists()==false){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The JarFile '" + objFileJar.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
																
				if(objFilterFileInJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileFileFilterForJar missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}														
			}//End check
												
			//Falls noch nicht vorhanden: Verzeichnis neu erstellen. Falls vorhanden, leer machen.
			String sDirPathInJar = objFilterFileInJar.getDirectoryPartFilter().getDirectoryPath();
			
			String archiveName = objFileJar.getAbsolutePath();
			IFilenamePartFilterZipZZZ objPartFilter = objFilterFileInJar.getDirectoryPartFilter();
			JarInfo objJarInfo = new JarInfo( archiveName, objPartFilter ); //MERKE: DAS DAUERT LAAAANGE
			objaReturn = JarEasyUtilZZZ.findFileInJar(objJarInfo, sDirPathInJar, sApplicationKeyAsSubDirectoryTempIn);					
		}//End main		 	
		return objaReturn;
	}
	
	public static File[] findFileInJar(File objFileJar, String sDirPathInJar, ZipEntryFilter objFilterInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			check:{				
				if(objFileJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				boolean bIsJar = FileEasyZZZ.isJar(objFileJar);
				if(!bIsJar) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The File '" + objFileJar.getPath() + "', is not a jar File. To extract from a directory use another method.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}

				if(objFileJar.exists()==false){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The JarFile '" + objFileJar.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
																
				if(objFilterInJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileFilterForJar missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}														
			}//End check
		
			
			String archiveName = objFileJar.getAbsolutePath();
			JarInfo objJarInfoFiltered = new JarInfo( archiveName, objFilterInJar );
			objaReturn = JarEasyUtilZZZ.findFileInJar(objJarInfoFiltered,sDirPathInJar ,sApplicationKeyAsSubDirectoryTempIn);			
		}//End main		 	
		return objaReturn;
	}

	public static File[] findFileInJar(JarInfo objJarInfoFiltered, String sDirPathInJar, String sTargetDirPathRootIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			if(objJarInfoFiltered==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarInfo missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sTargetDirPathRoot;
			if(StringZZZ.isEmpty(sTargetDirPathRootIn)){
				sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), "FGL");				
			}else {
				if(FileEasyZZZ.isPathAbsolut(sTargetDirPathRootIn)) {
					sTargetDirPathRoot = sTargetDirPathRootIn;
				}else {
					sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sTargetDirPathRootIn);
				}
			}
			
			String sTargetDirPath;
			if(StringZZZ.isEmpty(sDirPathInJar)) {
				sTargetDirPath = sTargetDirPathRoot;
			}else {
				sDirPathInJar = JarEasyUtilZZZ.toFilePath(sDirPathInJar);
				sTargetDirPath = FileEasyZZZ.joinFilePathName(sTargetDirPathRoot, sDirPathInJar);
			}
			
			
			File objFileTemp = new File(sTargetDirPath);
			boolean bSuccess = false;
			if(!objFileTemp.exists()) {
				bSuccess = FileEasyZZZ.createDirectory(sTargetDirPath);
			}else {
				bSuccess = FileEasyZZZ.removeDirectoryContent(objFileTemp, true);
			}
			if(!bSuccess) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Keine Operation mit dem temporären Verzeichnis möglich '" + sTargetDirPath + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden
			//https://www.javaworld.com/article/2077586/java-tip-83--use-filters-to-access-resources-in-java-archives.html
				
			//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
			Hashtable<String,ZipEntry> ht = objJarInfoFiltered.zipEntryTable();
	
			//Wie nun vom ht nach objaReturn, also dem fertigen File-Objekt ???
			//Es geht nur als temporäres Objekt, das man in ein temp-Verzeichnis ablegt.								
			Set<String> setEntryName = ht.keySet();
			Iterator<String> itEntryName = setEntryName.iterator();
			ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
			try {					
				ZipFile zf = objJarInfoFiltered.getZipFile();
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
					File objFileTempInTemp = ZipEasyZZZ.extractZipEntry(zf, zeTemp, sTargetDirPathRoot);	
					objaFileTempInTemp.add(objFileTempInTemp);
				}					
				if(zf!=null) zf.close();
				objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//End main		 	
	return objaReturn;
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

	/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
	 *   Merke: Static Klassen müssen diese Methode selbst implementieren. Das ist dann das Beispiel.
	 * @return
	 * @author lindhaueradmin, 21.02.2019
	 * @throws ExceptionZZZ 
	 */
	public static boolean isInJarStatic() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = isInJar(JarEasyZZZ.class);
		}
		return bReturn;
	}

	public static File getCodeLocationJar() throws ExceptionZZZ{
		File objReturn=null;
		main:{
			/*
			https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file						
			 */
			
			//1.Schritt die URL holen
			URL url = JarEasyHelperZZZ.getLocation(JarEasyUtilZZZ.class);
			if(url==null)break main;
			
			//Step 2: URL to File	
//			String sLog = ReflectCodeZZZ.getPositionCurrent()+": C1) File from url '"+url.toString()+"'";
//			System.out.println(sLog);
			objReturn = JarEasyHelperZZZ.urlToFile(url);
//			if(objReturn==null) {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) File is NULL";
//				System.out.println(sLog);
//			}else {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) File = '" + objReturn.getAbsolutePath() +"'";
//				System.out.println(sLog);
//			}
		}
		return objReturn;
	}
	
	/** Versucht erst die CodeLocationJar zu holen. Wenn das nicht hilft, den aktullen classpath bzw. Eclipse Workspace
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 22.10.2020, 08:54:58
	 */
	public static File getCodeLocationUsed() throws ExceptionZZZ{
		File objReturn=null;
		main:{			
			objReturn = JarEasyUtilZZZ.getCodeLocationJar();
			if(objReturn==null) {
				objReturn = FileEasyZZZ.getDirectoryOfExecution();
			}
		}
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

	/** Innerhalb der JAR-Datei wird immer mit / gearbeitet.
	 *  Also einen Dateipfad dahingehend normieren.
	 *  
	 *  Verzeichnisse sind mit / abgeschlossen.
	 * @param sFilePath
	 * @return
	 * @author Fritz Lindhauer, 19.07.2020, 07:35:31
	 * @throws ExceptionZZZ 
	 */
	public static String toJarDirectoryPath(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = JarEasyUtilZZZ.toJarFilePath(sFilePath);
			
			//UND: Abschliessend gibt es bei Verzeichnissen ein / ... aber NUR 1x
			sReturn=sReturn+"/";
		}
		return sReturn;
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

	/** Innerhalb der JAR-Datei wird immer mit / gearbeitet.
	 *  Also einen Dateipfad dahingehend normieren.
	 * @param sFilePath
	 * @return
	 * @author Fritz Lindhauer, 19.07.2020, 07:35:31
	 * @throws ExceptionZZZ 
	 */
	public static String toJarFilePath(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)){
				//ODER: ROOT DER JAR DATEI, WIE?
				ExceptionZZZ ez = new ExceptionZZZ("No source FilePath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Innerhalb der JAR-Datei wird immer mit / gearbeitet.
			sReturn = StringZZZ.replace(sFilePath, FileEasyZZZ.sDIRECTORY_SEPARATOR, "/");
		
			//UND: Abschliessend gibt es NUR bei Verzeichnissen ein / ... aber NUR 1x
			sReturn=StringZZZ.stripFileSeparatorsRight(sReturn);
						
			//UND: Links gibt es keinen /
			sReturn=StringZZZ.stripLeft(sReturn, "/");
			
			
		}
		return sReturn;
	}

	public static JarFile getJarFileCurrent() throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			String sLog = null;
			final File jarFile = JarEasyUtilZZZ.getJarFileCurrentAsFile();
			objReturn = JarEasyUtilZZZ.toJarFile(jarFile);
		}//end main:
		return objReturn;
	}
	
	/**Versuche erst das JarFileCurrentAsFile zu holen. Wenn das nicht vorhanden ist, wird über das Excecution-Verzeichnis ein definierter .Jar-Dateiname gesucht.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 22.10.2020, 14:36:19
	 * @throws ExceptionZZZ 
	 */
	public static JarFile getJarFileUsed() throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			String sLog = null;
			objReturn = JarEasyUtilZZZ.getJarFileCurrent();
			if(objReturn!=null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE CURRENT FOUND.";
			    System.out.println(sLog);
				break main;
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) NOT RUNNING IN JAR FILE.";
			    System.out.println(sLog);			    
			    final File jarFile = JarEasyUtilZZZ.getJarFileDefaultAsFile();
			    objReturn = JarEasyUtilZZZ.toJarFile(jarFile);
			}
		}//end main:
		return objReturn;
	}
	
	
	public static File getJarFileCurrentAsFile() throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;
			final File objCodeLocation = JarEasyUtilZZZ.getCodeLocationJar(); //new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			if(objCodeLocation!=null) {
				if(objCodeLocation.isFile()) {  // Run with JAR file
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE FOUND.";
				    System.out.println(sLog);
				    
					objReturn = objCodeLocation;
					break main;
				}else if(objCodeLocation.isDirectory()) {  // Run with Eclipse or so.					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) JAR FILE NOT FOUND, RUNNING IN DIRECTORY: '" + objCodeLocation.getAbsolutePath() +"'";
					System.out.println(sLog);
					break main;
				}
			}else {
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) CODE LOCATION IS NULL. JAR FILE NOT FOUND.";
				System.out.println(sLog);
				break main;
			}
		}//end main:
		return objReturn;
	}

	/**Versuche erst das JarFileCurrentAsFile zu holen. Wenn das nicht vorhanden ist, wird über das Excecution-Verzeichnis ein definierter .Jar-Dateiname gesucht.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 22.10.2020, 14:36:19
	 * @throws ExceptionZZZ 
	 */
	public static File getJarFileUsedAsFile() throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;
			objReturn = JarEasyUtilZZZ.getJarFileCurrentAsFile();
			if(objReturn!=null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE CURRENT FOUND.";
			    System.out.println(sLog);
				break main;
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) NOT RUNNING IN JAR FILE.";
			    System.out.println(sLog);			    
			    objReturn = JarEasyUtilZZZ.getJarFileDefaultAsFile();
			}
		}//end main:
		return objReturn;
	}
	
	public static File getJarFileDefaultAsFile() throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) SEARCHING FOR DEFAULT JAR-FILE.";
			System.out.println(sLog);
			
			File objFileExcecutionDirectory = JarEasyUtilZZZ.getCodeLocationUsed();
			if(objFileExcecutionDirectory==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) NO EXECUTION DIRECTORY FOUND.";
			    System.out.println(sLog);
				break main;
			}else if(objFileExcecutionDirectory.isFile()) { 
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) NO EXECUTION DIRECTORY FOUND IS NOT A DIRECTORY, RETURNING NULL";
				    System.out.println(sLog);
				    break main; //Aufgeben....
			}else if(objFileExcecutionDirectory.isDirectory()) {  // Run with Eclipse or so.					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) EXECUTION DIRECTORY FOUND, RUNNING IN DIRECTORY: '" + objFileExcecutionDirectory.getAbsolutePath() +"'";
					System.out.println(sLog);

					//1. Suche: Im Eclipse Workspace. Das wäre ggfs. für Ressource angesagt.
					String sFileName = JarEasyTestConstantsZZZ.sJAR_FILENAME;
					String sFilePathTotal = FileEasyZZZ.joinFilePathName(objFileExcecutionDirectory, sFileName);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE '" + sFilePathTotal + "'";
					System.out.println(sLog);					
					objReturn = new File(sFilePathTotal);
					if(objReturn.exists())break main;
					
					//2. Suche in einem absoluten Verzeichnis. Das wäre der Normalfall beim Testen.
					String sFileDirectory = JarEasyTestConstantsZZZ.sJAR_DIRECTORYPATH;					
					sFilePathTotal = FileEasyZZZ.joinFilePathName(sFileDirectory, sFileName);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE '" + sFilePathTotal + "'";
					System.out.println(sLog);
					objReturn = new File(sFilePathTotal);
					if(objReturn.exists())break main;
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) UNABLE TO FIND JAR FILE";
					System.out.println(sLog);
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DD) UNEXPECTED CASE.";
				System.out.println(sLog);
				break main;
			}
		}//end main:
		return objReturn;
	}
}

