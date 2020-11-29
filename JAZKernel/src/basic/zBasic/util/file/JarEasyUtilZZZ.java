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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.JarInfo;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryWithContentPartFilterZipZZZ;
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
public class JarEasyUtilZZZ extends ObjectZZZ implements IJarEasyConstantsZZZ{
	public static String sDIRECTORY_SEPARATOR = "/";
	
	//Ich lasse mal untenstehend da, aus Dokumentationsgründen.
	//https://stackoverflow.com/questions/51494579/regex-windows-path-validator
	//und zum Testen: https://regex101.com/r/4JY31I/1
	//
	//Das wäre für Windows Dateipfade public static String sDIRECTORY_VALID_REGEX="^(?:[a-z]:)?[\\/\\\\]{0,2}(?:[.\\/\\\\ ](?![.\\/\\\\\\n])|[^<>:\"|?*.\\/\\\\ \\n])+$";
		/* So the regex is as follows:
		
		    ^ - Start of the string.
		    (?:[a-z]:)? - Drive letter and a colon, optional.
		    [\/\\]{0,2} - Either a backslash or a slash, between 0 and 2 times.
		    (?: - Start of the non-capturing group, needed due to the + quantifier after it.
		        [.\/\\ ] - The first alternative.
		        (?![.\/\\\n]) - Negative lookahead - "forbidden" chars.
		    | - Or.
		        [^<>:"|?*.\/\\ \n] - The second alternative.
		    )+ - End of the non-capturing group, may occur multiple times.
		    $ - End of the string.
		
		If you attempt to match each path separately, use only i option.
		
		But if you have multiple paths in separate rows, and match them globally in one go, add also g and m options.
		
		For a working example see https://regex101.com/r/4JY31I/1
		 */
	//Also angepasst an eine JAR Datei
	//* Ohne Laufwerk
	//* Ohne Slashes nach dem Laufwerk
	//* Es gilt: Keine Slashe am Anfang ^(?![/])
	//  Es gilt: Slash am Ende (?:[/]$)
	//	([(?:\/{1}]$)
	//  Das wäre mindestens 2 Slash am Ende (\/{2,}$)
	//  Als Negativer Lookahead: (?![\/{2,}]$)
	
	
	//ABER: Ich habe es nicht geschaft, über die Prüfung von Anfangszeichen und Endzeichen hinaus
	//      im gleichen Ausdruck auf doppelte und mehr "/" zu prüfen.
	//      Dabei u.a. den negativen Lookahead ausprobiert, wie (?![\/{2,}]$)
	
	//TODOGOON: Es müsste auch noch angegeben werden, welche Zeichen gültig sind.
	
	
	//Findet alle die nicht mit / beginnen und mit / enden .... 
	public static String sDIRECTORY_VALID_REGEX="^(?![/]{1,}).*([/]$)";
			
	//Findet alle die nicht mit / beginnen und nicht mit / enden ....
	//https://stackoverflow.com/questions/16398471/regex-for-string-not-ending-with-given-suffix
	public static String sFILE_VALID_REGEX="^(?![/]{1,}).*(?<![/])$";
	
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
				IFileDirectoryWithContentPartFilterZipZZZ objPartFilter = objDirectoryFilterInJar.getDirectoryPartFilterWithConent();		
				objJarInfo = new JarInfo( archiveName,  objPartFilter );//Mit dem Filter wird nur das Verzeichnis herausgefiltert.
			}else {
				IFileDirectoryPartFilterZipZZZ objPartFilter = objDirectoryFilterInJar.getDirectoryPartFilter();			
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
			String sLog;
			check:{																		
				if(objFilterFileInJar==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (Exception 01).";
					System.out.println(sLog);
					
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileFileFilterForJar missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}														
			}//End check
			sLog = ReflectCodeZZZ.getPositionCurrent()+": START XXXXXXXXXXXXXX.";
			System.out.println(sLog);									
			
			//Zuerst den genauen Namensfilter-Verwenden, sofern vorhanden, danach den allgemeineren Verzeichnisfilter
			IFilenamePartFilterZipZZZ objPartFilter = objFilterFileInJar.computeFilePartFilterUsed();
			String sDirPathInJar = objFilterFileInJar.computeDirectoryPathInJarUsed();
			String sFileNameInJar = objFilterFileInJar.computeFileNameInJarUsed();
			objaReturn = JarEasyUtilZZZ.findFileInJar_(objFileJar, objPartFilter, sDirPathInJar, sFileNameInJar, sApplicationKeyAsSubDirectoryTempIn);
												
		}//End main		 	
		return objaReturn;
	}
	
	public static File[] findFileInJar(File objFileJar, ZipEntryFilter objFilterInJar, String sDirPathInJar, String sFileNameInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			objaReturn = JarEasyUtilZZZ.findFileInJar_(objFileJar,objFilterInJar ,sDirPathInJar, sFileNameInJar, sApplicationKeyAsSubDirectoryTempIn);			
		}//End main		 	
		return objaReturn;
	}

	public static File[] findFileInJar(JarInfo objJarInfoFiltered, String sDirPathInJar, String sTargetDirPathRootIn) throws ExceptionZZZ{
		return JarEasyUtilZZZ.findFileInJar(objJarInfoFiltered, sDirPathInJar, sTargetDirPathRootIn);
	}
	
	private static File[] findFileInJar_(File objFileJar, ZipEntryFilter objPartFilter, String sDirPathInJarIn, String sFileNameInJar, String sTargetDirPathRootIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			String sLog;
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
															
			if(objPartFilter==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "IFilenamePartFilterZipZZZ Object missing.", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}	
				
//Merke: Wenn man nur nach dem Dateinamen sucht, dann ist der Pfad in der Jar Datei natürlich leer.
//			if(StringZZZ.isEmpty(sDirPathInJarIn)) {
//				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "Directory Path in Jar Stringobject. ", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
//				throw ez;
//			}
			
			//SUCHE IN JAR FILE		
			String archiveName = objFileJar.getAbsolutePath();			
			JarInfo objJarInfoFiltered = new JarInfo( archiveName, objPartFilter ); //MERKE: DAS DAUERT LAAAANGE
			
			
			
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
				boolean bTargetDirPathRootCreated=false; File objFileTemp=null; String sTargetDirPathRoot=null;
				ZipFile zf = objJarInfoFiltered.getZipFile();
				while(itEntryName.hasNext()) {
					
					//Achtung: Wenn die gesuchte Ressource nicht in der Jar - Datei gefunden worden ist, dann darf auch nicht das Zielverzeichnis erstellt worden sein.
					//         Darum erst jetzt (nur 1x ausgeführt!!!) ggfs. alte Verzeichnisse löschen und die Verzeichnisse wieder neu erstellen.
					if(!bTargetDirPathRootCreated) {
						ReferenceZZZ<String> strTargetDirPath = new ReferenceZZZ<String>(sTargetDirPathRootIn);
						ReferenceZZZ<String> strPathInJar = new ReferenceZZZ<String>(sDirPathInJarIn);
						ReferenceZZZ<String> strFilenameInJar = new ReferenceZZZ<String>(sFileNameInJar);						
						objFileTemp = JarEasyUtilZZZ.createTargetDirectoryRoot(strTargetDirPath, strPathInJar, strFilenameInJar);
						
						bTargetDirPathRootCreated=true;						
					}					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JB) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JB) Extrahierter Eintrag: '" + zeTemp.getName() + "'";
				   	System.out.println(sLog);
					
					File objFileTempInTemp = ZipEasyZZZ.extractZipEntryToDirectoryRoot(zf, zeTemp, sTargetDirPathRootIn);
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JC) Extrahierter Eintrag als Datei: '" + objFileTempInTemp.getAbsolutePath() + "'";
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
	
	public static File computeTargetDirectoryUsedAsTrunk(String sTargetDirPathRootIn, String sPathInJarIn, String sFilenameInJarIn) throws ExceptionZZZ {
		ReferenceZZZ<String> strTargetDirPath = new ReferenceZZZ<String>(sTargetDirPathRootIn);
		ReferenceZZZ<String> strPathInJar = new ReferenceZZZ<String>(sPathInJarIn);
		ReferenceZZZ<String> strFilenameInJar = new ReferenceZZZ<String>(sFilenameInJarIn);
		return JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(strTargetDirPath, strPathInJar, strFilenameInJar);
	}
	
	public static File computeTargetDirectoryUsedAsTrunk(ReferenceZZZ<String> strTargetDirPathRoot, ReferenceZZZ<String> strPathInJar, ReferenceZZZ<String> strFilenameInJar) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			//RÜCKGABE DES VERZEICHNISSES ALS FILE-OBJEKT, DAS DANN ERZEUGT WERDEN KANN.
			String sLog;		
			
			//##################
			// A) Pfade und Dateiname in der JAR - Datei
			String sDirPathInJar = strPathInJar.get();
			String sFilenameInJar = strFilenameInJar.get();
			
			//Nun alles erneut ausrechnen und für die Rückgabe vorbereiten.
			String sFilePathTotal;
			if(StringZZZ.isEmpty(sDirPathInJar) && !StringZZZ.isEmpty(sFilenameInJar)) {
				sDirPathInJar = JarEasyUtilZZZ.computeDirectoryFromJarPath(sFilenameInJar);
				sFilenameInJar = JarEasyUtilZZZ.computeFilenameFromJarPath(sFilenameInJar);	
			}else if(!StringZZZ.isEmpty(sDirPathInJar) && StringZZZ.isEmpty(sFilenameInJar)) {
				sDirPathInJar = JarEasyUtilZZZ.toJarDirectoryPath(sDirPathInJar);//Sicherheithalber ggfs. noch ein / anhängen.
				sDirPathInJar = JarEasyUtilZZZ.computeDirectoryFromJarPath(sDirPathInJar);
				sFilenameInJar = JarEasyUtilZZZ.computeFilenameFromJarPath(sDirPathInJar);							
			}
			
			//Nun alles für die Rückgabe vorbereiten.			
			strPathInJar.set(sDirPathInJar);
			strFilenameInJar.set(sFilenameInJar);

			//##################
			//Da ggfs. der Pfad im Dateinamen steht, hier erst einmal alles zu einem String zusammenfassen.
			sFilePathTotal = JarEasyUtilZZZ.joinJarFilePathName(sDirPathInJar, sFilenameInJar);

			// B) (Temporärer) Pfad auf der Platte als Zielverzeichnis für die extrahierten Dateien			
			String sTargetDirPathRoot=strTargetDirPathRoot.get();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Übergebener Zieldateipfad. '" + sTargetDirPathRoot + "'";
		   	System.out.println(sLog);
			
			if(StringZZZ.isEmpty(sTargetDirPathRoot) && StringZZZ.isEmpty(sDirPathInJar)){
				//FALL 1: Kein Zielverzeichnis und Kein Pfad in der Jar Datei angegeben
				sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), "ZZZ");				
			}else if(StringZZZ.isEmpty(sTargetDirPathRoot) && !StringZZZ.isEmpty(sDirPathInJar)) {
				//FALL 2: Kein Zielverzeichnis, aber Pfad in der Jar Datei angegeben
				sDirPathInJar = JarEasyUtilZZZ.toFilePath(sDirPathInJar);
				sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sDirPathInJar);				
			}else {
				//FALL 3: Zielverzeichnis angegeben
				if(!FileEasyZZZ.isPathAbsolut(sTargetDirPathRoot)) {
					//3A) Als relativer Pfad					
					sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sTargetDirPathRoot);
					
					if(!StringZZZ.isEmpty(sDirPathInJar)) {
						//sDirPathInJar = JarEasyUtilZZZ.toFilePath(sDirPathInJar);
						//sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(sTargetDirPathRoot, sDirPathInJar);
					}else {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": FALL: Kein Pfadbestandteil für die Datei innerhalb der JarDatei übergeben. VERMUTLICH SUCHE IN ALLEN VERZEICHNISSEN.";
					   	System.out.println(sLog);					
					}
				}else {
					//3B) Als absoluter Pfad
					sDirPathInJar = JarEasyUtilZZZ.toFilePath(sDirPathInJar);
				   	sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(sTargetDirPathRoot, sDirPathInJar);					
				}
			}
			sLog = ReflectCodeZZZ.getPositionCurrent()+": sTargetDirPathRoot='"+sTargetDirPathRoot+"'";
		   	System.out.println(sLog);
		   	
		   	//Für die Rückgabe vorbereiten.
		   	strTargetDirPathRoot.set(sTargetDirPathRoot);
			objReturn = new File(sTargetDirPathRoot);			
		}
		return objReturn;
	}
	
	public static File createTargetDirectoryRoot(String sTargetDirPathRootIn, String sPathInJarIn, String sFilenameInJarIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			ReferenceZZZ<String> strTargetDirPath = new ReferenceZZZ<String>(sTargetDirPathRootIn);
			ReferenceZZZ<String> strPathInJar = new ReferenceZZZ<String>(sPathInJarIn);
			ReferenceZZZ<String> strFilenameInJar = new ReferenceZZZ<String>(sFilenameInJarIn);
			File fileDirTrunk = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(strTargetDirPath, strPathInJar, strFilenameInJar);
	
			boolean bSuccess = FileEasyZZZ.replaceDirectory(fileDirTrunk);
			if(bSuccess) {
				objReturn = fileDirTrunk;
				break main;
			}
		}//end main:
		return objReturn;
	}
	
	public static File createTargetDirectoryRoot(ReferenceZZZ<String> strTargetDirPath, ReferenceZZZ<String> strPathInJar, ReferenceZZZ<String> strFilenameInJar) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			File fileDirTrunk = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(strTargetDirPath, strPathInJar, strFilenameInJar);
		
			boolean bSuccess = FileEasyZZZ.replaceDirectory(fileDirTrunk);
			if(bSuccess) {
				objReturn = fileDirTrunk;
				break main;
			}
		}//end main:
		return objReturn;
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
			sReturn=StringZZZ.stripRight(sReturn, "/");
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
	
	public static JarFile getJarFileUsed(int iConstantKeyZZZ) throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			objReturn = JarEasyUtilZZZ.getJarFileByConstantKey_(iConstantKeyZZZ);		
		}//end main:
		return objReturn;
	}
	
	public static File getJarFileUsedAsFile(int iConstantKeyZZZ) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			objReturn = JarEasyUtilZZZ.getJarFileByConstantKeyAsFile_(iConstantKeyZZZ);
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
		
		return JarEasyUtilZZZ.getJarFileAsFile_(JarEasyTestCommonsZZZ.sJAR_DIRECTORYPATH_DUMMY,JarEasyTestCommonsZZZ.sJAR_FILENAME_DUMMY);
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
				objReturn = JarEasyUtilZZZ.getJarFileAsFile_(objFileExcecutionDirectory.getAbsolutePath(),JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
				if(objReturn!=null) break main;
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = JarEasyUtilZZZ.getJarFileAsFile_(JarEasyTestCommonsZZZ.sJAR_DIRECTORYPATH_KERNEL,JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DD) UNEXPECTED CASE.";
				System.out.println(sLog);
				break main;
			}
		}//end main:
		return objReturn;
	}
	
	public static File getJarFileDefaultAsFile(String sKey) throws ExceptionZZZ{
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
				objReturn = JarEasyUtilZZZ.getJarFileAsFile_(objFileExcecutionDirectory.getAbsolutePath(),JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
				if(objReturn!=null) break main;
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = JarEasyUtilZZZ.getJarFileAsFile_(JarEasyTestCommonsZZZ.sJAR_DIRECTORYPATH_KERNEL,JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
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
		
		return JarEasyUtilZZZ.getJarFileAsFile_(JarEasyUtilZZZ.sJAR_DIRECTORYPATH_TEST,JarEasyUtilZZZ.sJAR_FILENAME_TEST);
	}
	
	public static JarFile getJarFileTest() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarEasyUtilZZZ.getJarFile_(JarEasyUtilZZZ.sJAR_DIRECTORYPATH_TEST,JarEasyUtilZZZ.sJAR_FILENAME_TEST);
	}
	
	public static File getJarFileKernelAsFile() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarEasyUtilZZZ.getJarFileAsFile_(JarEasyUtilZZZ.sJAR_DIRECTORYPATH_KERNEL,JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
	}
	
	public static JarFile getJarFileKernel() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarEasyUtilZZZ.getJarFile_(JarEasyUtilZZZ.sJAR_DIRECTORYPATH_KERNEL,JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
	}
	
	private static JarFile getJarFileByConstantKey_(int iConstantKeyZZZ) throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			switch(iConstantKeyZZZ){
			case JarEasyUtilZZZ.iJAR_DUMMY:
				break;
			case JarEasyUtilZZZ.iJAR_TEST:
				objReturn = JarEasyUtilZZZ.getJarFileTest();
				break;
			case JarEasyUtilZZZ.iJAR_KERNEL:
				objReturn = JarEasyUtilZZZ.getJarFileKernel();
				break;
			case JarEasyUtilZZZ.iJAR_OVPN:
				objReturn = JarEasyUtilZZZ.getJarFile_(JarEasyUtilZZZ.sJAR_DIRECTORYPATH_OVPN, JarEasyUtilZZZ.sJAR_FILENAME_OVPN);
				break;
			default:
				ExceptionZZZ ez = new ExceptionZZZ("Constant for this key not defined: " + iConstantKeyZZZ, iERROR_PARAMETER_VALUE, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
		return objReturn;
	}
	
	private static File getJarFileByConstantKeyAsFile_(int iConstantKeyZZZ) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			switch(iConstantKeyZZZ){
			case JarEasyUtilZZZ.iJAR_DUMMY:
				break;
			case JarEasyUtilZZZ.iJAR_TEST:
				objReturn = JarEasyUtilZZZ.getJarFileTestAsFile();
				break;
			case JarEasyUtilZZZ.iJAR_KERNEL:
				objReturn = JarEasyUtilZZZ.getJarFileKernelAsFile();
				break;
			case JarEasyUtilZZZ.iJAR_OVPN:
				objReturn = JarEasyUtilZZZ.getJarFileAsFile_(JarEasyUtilZZZ.sJAR_DIRECTORYPATH_OVPN, JarEasyUtilZZZ.sJAR_FILENAME_OVPN);
				break;
			default:
				ExceptionZZZ ez = new ExceptionZZZ("Constant for this key not defined: " + iConstantKeyZZZ, iERROR_PARAMETER_VALUE, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
		return objReturn;
	}
	
	
	private static File getJarFileAsFile_(String sFileDirectory, String sFileName) throws ExceptionZZZ {
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
	
	private static JarFile getJarFile_(String sFileDirectory, String sFileName) throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{		
			File objFile = JarEasyUtilZZZ.getJarFileAsFile_(sFileDirectory, sFileName);
			if(objFile!=null) {
				objReturn = JarEasyUtilZZZ.toJarFile(objFile);
			}			
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
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) Searching for Directory '" + sPathInJar + "'";				
				System.out.println(sLog);
			}else{
				sPathInJar = toJarFilePath(sPath); 
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) Searching for File '" + sPathInJar + "'";				
				System.out.println(sLog);
			}
			
				
			objReturn = jar.getJarEntry(sPathInJar);
				  
		}//end main:
		return objReturn;
	}
	
	/* Es wird der Pfad aufgeteilt und zurückgegeben.
	 * Da Java nur ein CALL_BY_VALUE machen kann, weden hier für die eingefüllten Werte Referenz-Objekte verwendet.
	 */
	public static void splitJarFilePathToDirectoryAndName(String sJarPath, ReferenceZZZ<String> strDirectory, ReferenceZZZ<String> strFileName) throws ExceptionZZZ{		
		main:{
			if(StringZZZ.isEmpty(sJarPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sDirectory=null;
			String sFileName=null;
			if(JarEasyUtilZZZ.isDirectory(sJarPath)){
				sDirectory = sJarPath;
				sFileName = "";
			}else {
				sDirectory = StringZZZ.leftback(sJarPath, JarEasyUtilZZZ.sDIRECTORY_SEPARATOR);
				if(!StringZZZ.isEmpty(sDirectory)) {
					sDirectory=sDirectory + JarEasyUtilZZZ.sDIRECTORY_SEPARATOR;
					sFileName = StringZZZ.right(sJarPath, sDirectory);
				}else {
					sDirectory = "";
					sFileName = sJarPath;
				}			
			}
			
			//#### Die Rückgabewerte
			strDirectory.set(sDirectory);
			strFileName.set(sFileName);		
		}//END main:		
	}
	
	public static boolean isDirectory(String sJarPath) {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) break main;
			
			if(sJarPath.endsWith(JarEasyUtilZZZ.sDIRECTORY_SEPARATOR)) bReturn = true;			
		}//end main:
		return bReturn;		
	}
	
	public static String computeDirectoryFromJarPath(String sJarPath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
	    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
	    	
	    	if(JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);		    	
	    		sReturn = strDirectory.get();
	    		break main;
	    	}
	    	
	    	if(JarEasyUtilZZZ.isJarPathFileValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);
	    		sReturn = strDirectory.get();
	    		break main;
	    	}
	    	
		}
		return sReturn;
	}
	public static String computeFilenameFromJarPath(String sJarPath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
	    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
	    	
	    	if(JarEasyUtilZZZ.isJarPathFileValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);	
	    		sReturn = strFileName.get();
	    		if(!StringZZZ.isEmpty(sReturn)) break main;
	    		
	    		
	    		
	    	}
	    	
	    	if(JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);
	    		sReturn = strFileName.get();
	    		if(!StringZZZ.isEmpty(sReturn)) break main;	    		
	    	}
	    	
	    	
	    	//Wenn jetzt noch nicht gefunden wurde, dann als Dateinamen den rechten Teil vom Separator nehmen.
	    	//Das ist z.B. der Fall wenn nach einem reinen Dateinamen gesucht wird. Also gar kein Pfad angegeben wurde.
	    	if(StringZZZ.startsWithIgnoreCase(sJarPath, JarEasyUtilZZZ.sDIRECTORY_SEPARATOR)) {
	    		sReturn = StringZZZ.right(sJarPath, JarEasyUtilZZZ.sDIRECTORY_SEPARATOR);
	    	}	    	
    		break main;
	    	
	    	
		}
		return sReturn;
	}
	
	public static boolean isJarPathValid(String sJarPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) {
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Jar-Verzeichniseinträge dürfen keinen "Backslash" haben.
//			if(StringZZZ.contains(sJarPath, FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS)) {
//				bReturn = false;
//				break main;
//			}
			
			//Ansonsten eben nicht wie Dateipfade
			boolean bErg = JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath);
			if(bErg) {
				bReturn = true;
				break main;
			}
			
			bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
			if(bErg) {
				bReturn = true;
				break main;
			}
			
		}
		return bReturn;
	}
	
	public static boolean isJarPathDirectoryValid(String sJarPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) {
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Da es wohl zu kompliziert ist alles in einem Ausdruck zu erfassen:
			//0. Jar-Verzeichniseinträge dürfen keinen "Backslash" haben.
			if(StringZZZ.contains(sJarPath, FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS)) break main;
						
			//1. prüfe auf doppelte / ab. Die sind auch nicht erlaubt.
			boolean bErg = StringZZZ.hasConsecutiveDuplicateCharacter(sJarPath, '/');
			if(bErg) break main;
			
			//2. Nun doch noch den regulären Ausdruck verwenden
			Pattern p = Pattern.compile(JarEasyUtilZZZ.sDIRECTORY_VALID_REGEX); 
			 Matcher m =p.matcher( sJarPath ); 
			 boolean bMatched  =  m.find(); //Es geht nicht darum den ganzen Ausdruck, sondern nur einen teil zu finden: m.matches(); 	
			 if(bMatched) {
				 bReturn = true;
				 break main;
			 }			 			 					 
		}
		return bReturn;
	}
	
	public static boolean isJarPathFileValid(String sJarPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) {
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Da es wohl zu kompliziert ist alles in einem Ausdruck zu erfassen:
			//0. Jar-Verzeichniseinträge dürfen keinen "Backslash" haben.
			if(StringZZZ.contains(sJarPath, FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS)) break main;
			
			//1. prüfe auf doppelte / ab. Die sind auch nicht erlaubt.
			boolean bErg = StringZZZ.hasConsecutiveDuplicateCharacter(sJarPath, '/');
			if(bErg) break main;
			
			//Ansonsten eben nicht wie Dateipfade			 
			 Pattern p = Pattern.compile(JarEasyUtilZZZ.sFILE_VALID_REGEX); 
			 Matcher m =p.matcher( sJarPath ); 
			 boolean bMatched  =  m.find(); //Es geht nicht darum den ganzen Ausdruck, sondern nur einen teil zu finden: m.matches(); 	
			 if(bMatched) {
				 bReturn = true;
				 break main;
			 }						 
		}
		return bReturn;
	}
	
	public static String joinJarFilePathName(String sDirectoryPath, String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sDirectoryPathNormed = "";
			if(!StringZZZ.isEmpty(sDirectoryPath)) {
				sDirectoryPathNormed = JarEasyUtilZZZ.toFilePath(sDirectoryPath);
			}
			String sFilePathNormed = "";
			if(!StringZZZ.isEmpty(sFilePath)){
				sFilePathNormed = JarEasyUtilZZZ.toFilePath(sFilePath);
			}
			
			sReturn = FileEasyZZZ.joinFilePathName(sDirectoryPathNormed, sFilePathNormed);
			sReturn = JarEasyUtilZZZ.toJarFilePath(sReturn);
		}//end main:
		return sReturn;		
	}

	public static File extractFileAsTemp(JarFile jar, String sPath) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(jar==null) {
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLog = null;			    
			try {						
				   JarEntry entry = getEntryAsFile(jar, sPath); 
				   if(entry==null){
					   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND: '" + sPath +"'";
					   System.out.println(sLog);			    	
					}else{					    	
					   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND: '" + sPath +"'";
					   System.out.println(sLog);
					    	
					   //Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
					   if(entry.isDirectory()) { //Dateien nicht extrahieren!!!
						   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY, WILL NOT BE EXTRACTED AS TEMP-FILE";
						   System.out.println(sLog);
					   }else {				
						   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS FILE, WILL BE EXTRACTED AS TEMP-FILE";
						   System.out.println(sLog);
					    		
						    	
					   		objReturn = JarEasyZZZ.extractFileFromJarAsTemp(jar, entry);
					    }
					}						    		
				   jar.close();
				} catch (IOException e1) {
					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
		}//end main:
		return objReturn;
	}
}


