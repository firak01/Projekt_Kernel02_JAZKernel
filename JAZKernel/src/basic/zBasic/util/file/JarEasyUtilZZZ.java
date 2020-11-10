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
import java.util.jar.JarEntry;
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
import basic.zBasic.util.file.zip.IFileDirectoryWithContentFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryFilterZipZZZ;
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
	
	public static File[] findDirectoryInJar(JarFile objJar, IFileDirectoryPartFilterZipUserZZZ objDirectoryFilterInJar, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{		
			if(objJar==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile-Object missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}				
			File objFileJar = JarEasyUtilZZZ.toFile(objJar);				
			objaReturn = JarEasyUtilZZZ.findDirectoryInJar(objFileJar, objDirectoryFilterInJar, sTargetDirectoryPathIn, bWithFiles);					
		}//End main		 	
		return objaReturn;
	}
		
	public static File[] findDirectoryInJar(File objFileJar, IFileDirectoryPartFilterZipUserZZZ objDirectoryFilterInJar, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{	
			
			//Wenn im Ergebnis Dateien sein sollen, dann einen anderen FileFilter verwenden			
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
			JarInfo objJarInfo = null;
			if(bWithFiles) {
				IFileDirectoryWithContentFilterZipZZZ objPartFilter = objDirectoryFilterInJar.getDirectoryPartFilterWithConent();		
				objJarInfo = new JarInfo( archiveName,  objPartFilter );//Mit dem Filter wird nur das Verzeichnis herausgefiltert.
			}else {
				IFileDirectoryFilterZipZZZ objPartFilter = objDirectoryFilterInJar.getDirectoryPartFilter();			
				objJarInfo = new JarInfo( archiveName,  objPartFilter );//Mit dem Filter wird nur das Verzeichnis herausgefiltert.
			}
			
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
					File objFileTemp = objFileTemp = new File(sPathDirTemp, sNameTemp);
										
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
	}//End main		 	
	return hmReturn;
	}
	
	public static File[] findFileInJar(JarFile objJar, IFileFilePartFilterZipUserZZZ objFilterFileInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{		
			if(objJar==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile-Object missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}				
			File objFileJar = JarEasyUtilZZZ.toFile(objJar);				
			objaReturn = JarEasyUtilZZZ.findFileInJar(objFileJar, objFilterFileInJar, sApplicationKeyAsSubDirectoryTempIn);					
		}//End main		 	
		return objaReturn;
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
			//TODOGOON
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) XXXXXXXXXXXXXX.";
	   	System.out.println(sLog);
		
			//SUCHE IN JAR FILE		
			String archiveName = objFileJar.getAbsolutePath();		
			
			//A) VERZEICHNIS-FILTER
			IFilenamePartFilterZipZZZ objPartFilter = objFilterFileInJar.getDirectoryPartFilter();
			if(!StringZZZ.isEmpty(objPartFilter.getCriterion())) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (FA) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				String sDirPathInJar = objPartFilter.getCriterion();
				JarInfo objJarInfo = new JarInfo( archiveName, objPartFilter ); //MERKE: DAS DAUERT LAAAANGE
				objaReturn = JarEasyUtilZZZ.findFileInJar(objJarInfo, sDirPathInJar, sApplicationKeyAsSubDirectoryTempIn);
				break main;
			}
			
			//B) Kompletter Dateinamensfilter (mit Verzeichnis)
			objPartFilter = objFilterFileInJar.getNamePartFilter();
			if(!StringZZZ.isEmpty(objPartFilter.getCriterion())) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (FB) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				String sFilePathTotalInJar = objPartFilter.getCriterion();
				JarInfo objJarInfo = new JarInfo( archiveName, objPartFilter ); //MERKE: DAS DAUERT LAAAANGE
				objaReturn = JarEasyUtilZZZ.findFileInJar(objJarInfo, sFilePathTotalInJar, sApplicationKeyAsSubDirectoryTempIn);
				break main;
			}
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
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (G) XXXXXXXXXXXXXX.";
		   	System.out.println(sLog);
			
			File objFileTemp = new File(sTargetDirPath);
			boolean bSuccess = false;
			if(!objFileTemp.exists()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (HA) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
				bSuccess = FileEasyZZZ.createDirectory(sTargetDirPath);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (HB) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
				bSuccess = FileEasyZZZ.removeDirectoryContent(objFileTemp, true);
			}
			if(!bSuccess) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (I) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Keine Operation mit dem temporären Verzeichnis möglich '" + sTargetDirPath + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden
			//https://www.javaworld.com/article/2077586/java-tip-83--use-filters-to-access-resources-in-java-archives.html
				
			//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
			Hashtable<String,ZipEntry> ht = objJarInfoFiltered.zipEntryTable();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": (JA) XXXXXXXXXXXXXX.";
		   	System.out.println(sLog);
			
			//Wie nun vom ht nach objaReturn, also dem fertigen File-Objekt ???
			//Es geht nur als temporäres Objekt, das man in ein temp-Verzeichnis ablegt.								
			Set<String> setEntryName = ht.keySet();
			Iterator<String> itEntryName = setEntryName.iterator();
			ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
			try {					
				ZipFile zf = objJarInfoFiltered.getZipFile();
				while(itEntryName.hasNext()) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JB) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
					File objFileTempInTemp = ZipEasyZZZ.extractZipEntry(zf, zeTemp, sTargetDirPathRoot);
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JC) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
					objaFileTempInTemp.add(objFileTempInTemp);
				}					
				if(zf!=null) zf.close();
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (JD) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
			} catch (IOException e) {
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (J ERROR) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
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
	
	public static boolean isInSameJar(Class classObject, File objFileAsJar) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileAsJar==null)break main;
			if(!JarEasyUtilZZZ.isInJar(classObject)) {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": Not running from jar file.";
			    System.out.println(sLog);
				break main;			
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)){
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile: '" + objFileAsJar.getAbsolutePath() + "'", iERROR_PARAMETER_VALUE, ResourceEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 File objFileJarCodeLocation = JarEasyUtilZZZ.getCodeLocationJar();
			 if(objFileJarCodeLocation==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Unable to get CodeLocation for running jar", iERROR_RUNTIME, ResourceEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			 }
			 
			 String sLog = ReflectCodeZZZ.getPositionCurrent()+": A) File from JarEasyUtilZZZ.getCodeLocationUsed() = '" + objFileJarCodeLocation.getAbsolutePath() + "'";
			 System.out.println(sLog);

			  sLog = ReflectCodeZZZ.getPositionCurrent()+": B) File provided as argument, to be tested = '" + objFileAsJar.getAbsolutePath() + "'";
			  System.out.println(sLog);
			  
			 bReturn = objFileJarCodeLocation.equals(objFileAsJar);
		}//end main;
		return bReturn;
	}
	
	public static boolean isInSameJarStatic(File objFileAsJar) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileAsJar==null)break main;
			
			bReturn = isInSameJar(JarEasyUtilZZZ.class, objFileAsJar);			
		}//end main;
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
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": C1) URL as location '"+url.toString()+"'";
			System.out.println(sLog);
			File objTemp = JarEasyHelperZZZ.urlToFile(url);
			if(objTemp==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) File is NULL";
				System.out.println(sLog);
				break main;
			}
			if(!objTemp.exists()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) File does not exist '" + objTemp.getAbsolutePath() + "'";
				System.out.println(sLog);
				break main;
			}
			if(!objTemp.isFile()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) Fileobject is not a file '" + objTemp.getAbsolutePath() + "'";
				System.out.println(sLog);
				break main;
			}
			objReturn = objTemp;
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
	
	/**Für Tests, z.B. ob eine Resource in der gleichen Jar-Datei liegt.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.11.2020, 10:34:34
	 */
	public static File getJarFileDummyAsFile() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DUMMY";
		System.out.println(sLog);
		
		return JarEasyUtilZZZ.getJarFileByConstants_(JarEasyTestConstantsZZZ.sJAR_DIRECTORYPATH_DUMMY,JarEasyTestConstantsZZZ.sJAR_FILENAME_DUMMY);
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
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM WORKSPACE AND FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = JarEasyUtilZZZ.getJarFileByConstants_(objFileExcecutionDirectory.getAbsolutePath(),JarEasyTestConstantsZZZ.sJAR_FILENAME_KERNEL);
				if(objReturn!=null) break main;
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = JarEasyUtilZZZ.getJarFileByConstants_(JarEasyTestConstantsZZZ.sJAR_DIRECTORYPATH_KERNEL,JarEasyTestConstantsZZZ.sJAR_FILENAME_KERNEL);
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DD) UNEXPECTED CASE.";
				System.out.println(sLog);
				break main;
			}
		}//end main:
		return objReturn;
	}
	
	/**Für Tests, z.B. ob eine Resource in der gleichen Jar-Datei liegt.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.11.2020, 10:34:34
	 */
	public static File getJarFileTestAsFile() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarEasyUtilZZZ.getJarFileByConstants_(JarEasyTestConstantsZZZ.sJAR_DIRECTORYPATH_TEST,JarEasyTestConstantsZZZ.sJAR_FILENAME_TEST);
	}
	
	private static File getJarFileByConstants_(String sFileDirectory, String sFileName) throws ExceptionZZZ {
		File objReturn = null;
		main:{				
					//1. Suche: Im Eclipse Workspace. Das wäre ggfs. für Ressource angesagt.
					if(StringZZZ.isEmpty(sFileName)) {
						ExceptionZZZ ez = new ExceptionZZZ("No JarFileName provided", iERROR_PARAMETER_MISSING, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}					
					
					//2. Suche in einem absoluten Verzeichnis. Das wäre der Normalfall beim Testen.
					if(StringZZZ.isEmpty(sFileDirectory)) {
						ExceptionZZZ ez = new ExceptionZZZ("No JarFileDirectory provided", iERROR_PARAMETER_MISSING, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}				
					String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFileDirectory, sFileName);
					String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE '" + sFilePathTotal + "'";
					System.out.println(sLog);
					File objTemp  = new File(sFilePathTotal);
					if(objTemp.exists()) {
						objReturn = objTemp;
						break main;
					}
										
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) UNABLE TO FIND JAR FILE";
					System.out.println(sLog);			
		}//end main:
		return objReturn;		
	}

	public static JarEntry getEntryAsFile(JarFile jar, String sPath) throws ExceptionZZZ {
		JarEntry objReturn = null;
		main:{
			objReturn = JarEasyUtilZZZ.getEntry_(jar, sPath, false);
		}//end main:
		return objReturn;
	}
	
	public static JarEntry getEntryAsDirectory(JarFile jar, String sPath) throws ExceptionZZZ {
		JarEntry objReturn = null;
		main:{
			objReturn = JarEasyUtilZZZ.getEntry_(jar, sPath, true);
		}//end main:
		return objReturn;
	}
	
	private static JarEntry getEntry_(JarFile jar, String sPath, boolean bAsDirectory) throws ExceptionZZZ {
		JarEntry objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(jar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
			String sLog = null;			
			String sPathInJar = null;
			if(bAsDirectory) {
				sPathInJar = toJarDirectoryPath(sPath);
			}else{
				sPathInJar = toJarFilePath(sPath); 
			}
			sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) Searching for '" + sPathInJar + "'";				
			System.out.println(sLog);
				
			objReturn = jar.getJarEntry(sPathInJar);
				  
		}//end main:
		return objReturn;
	}
}

