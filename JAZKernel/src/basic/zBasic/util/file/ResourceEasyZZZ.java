package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.JarInfo;
import basic.zBasic.util.file.zip.ZipEntryFilter;

public class ResourceEasyZZZ extends ObjectZZZ{
	private ResourceEasyZZZ(){
		//Zum Verstecken des Konstruktors
	}
	
	public static File doClassloaderGetResource(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath))break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) isearching for path '" + sPath + "'";
			System.out.println(sLog);
			URL workspaceURL = ResourceEasyZZZ.class.getClassLoader().getResource(sPath);
			objReturn = ResourceEasyZZZ.getResource(workspaceURL, sPath, true);		
		}
		return objReturn;
	}
	
	public static File doClassGetResource(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath))break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) isearching for path '" + sPath + "'";
			System.out.println(sLog);
			URL workspaceURL = ResourceEasyZZZ.class.getResource(sPath);
			objReturn = ResourceEasyZZZ.getResource(workspaceURL, sPath, false);			
		}
		return objReturn;
	}
	
	private static File getResource(URL workspaceURL, String sPath, boolean byClassloader) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog=null;
			if(JarEasyZZZ.isInJarStatic()){				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) in .jar searching.";
				System.out.println(sLog);
				if(workspaceURL!=null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) WorkspaceURL is not null '" + workspaceURL.toString() + "')";
				    System.out.println(sLog);
				    try {
				    	objReturn = FileEasyZZZ.createTempFile();
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) temp File created.";
					    System.out.println(sLog);
					    
				    	FileOutputStream fosResource = new FileOutputStream(objReturn);
				   
					    int i; boolean bAnyFound=false;
					    byte[] ba = new byte[1024];
					    InputStream isClass = null;
					    if(byClassloader){
					    	isClass = ResourceEasyZZZ.class.getClassLoader().getResourceAsStream(sPath);
					    }else{
					    	isClass = ResourceEasyZZZ.class.getResourceAsStream(sPath);
					    }
					   
					   //while the InputStram has bytes				   
						while((i=isClass.read(ba))>0){
							bAnyFound=true;
							
							 //write the bytes to the output stream
							fosResource.write(ba,0,i);						  					
						}
						if(bAnyFound){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) any Bytes found.";
						    System.out.println(sLog);
						}else{
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) no Bytes found!!!";
						    System.out.println(sLog);
						}
						
						//close streams to prevent errors
						isClass.close();
						fosResource.close();
				
				} catch (FileNotFoundException fnfe) {
					ExceptionZZZ ez = new ExceptionZZZ("FileNotFoundException: '" + fnfe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				} catch (IOException ioe) {
					ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) WorkspaceURL is null";
				    System.out.println(sLog);
				    
				    objReturn = JarEasyZZZ.searchRessource(sPath);
				    if(objReturn==null){
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Datei NICHT gefunden '" + sPath + "' (NULL CASE)";
					    System.out.println(sLog);
						break main;
					}
				    if(objReturn.exists()){
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Datei gefunden '" + sPath + "'";
					    System.out.println(sLog);
						break main;
					}else {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Datei NICHT gefunden '" + sPath + "'";
					    System.out.println(sLog);
						break main;
					}
				}
				
			}else{								
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) not in .jar searching for path '" + sPath + "'";
				System.out.println(sLog);
				if(workspaceURL!=null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) URL is not null for path '" + sPath + "' ('" + workspaceURL.toString() + "')";
				    System.out.println(sLog);
					try {			
						objReturn = new File(workspaceURL.toURI());
						if(objReturn.exists()){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBA) Datei gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}else {
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBA) Datei NICHT gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}
					} catch(URISyntaxException e) {
						objReturn = new File(workspaceURL.getPath());
						if(objReturn.exists()){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBB) Datei gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}else {
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBB) Datei NICHT gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}
					}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (B) URL is null'";
				    System.out.println(sLog);
				}
				
			}
			
		}
		return objReturn;
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
	
	public static File[] findFileInJar(File objFileJar, String sDirPathInJar, ZipEntryFilter objFilterInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
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
																
				if(objFilterInJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileFilterForJar missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}														
			}//End check
		
			String sApplicationKeyAsSubDirectoryTemp;
			if(StringZZZ.isEmpty(sApplicationKeyAsSubDirectoryTempIn)) {
				sApplicationKeyAsSubDirectoryTemp = "FGL";
			}else {
				sApplicationKeyAsSubDirectoryTemp = sApplicationKeyAsSubDirectoryTempIn;
			}
				
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden
			//B) IN JAR Datei
			//https://www.javaworld.com/article/2077586/java-tip-83--use-filters-to-access-resources-in-java-archives.html
			//String archiveName = objDirectory.getAbsolutePath();
			
			
				//Einschränken der Hashtable auf ein Verzeichnis
				//NEUE KLASSE JarDirectoryInfoZZZ oder JarInfo um ein Array der zu holenden Verzeichnisse erweitern.
				//            a) ohne Unterverzeichnisse
				//            b) mit Unterverzeichnisse				
				//Aus der ht die des gesuchten Verzeichnisses holen.
				//String sDirTemplate = this.readDirectoryTemplatePath();
			
				//TODOGOON;
				//Das muss auf Dateien des Template Verzeichnis beschränkt sein.
				//FileFilterConfigOvpnTemplateInJarOVPN objFilterConfig = new FileFilterConfigOvpnTemplateInJarOVPN(this.getOvpnContextUsed());
				//IApplicationOVPN objApplication = this.getApplicationObject();
				//IMainOVPN objMain = objApplication.getMainObject();
				//String sJarPath = objMain.getJarFilePathUsed();
				//File objJarAsDirectoryMock = new File(sJarPath);
				//String archiveName = objJarAsDirectoryMock.getAbsolutePath();
				
				//Falls noch nicht vorhanden: Verzeichnis neu erstellen. Falls vorhanden, leer machen.
				String sDirPath;
				if(StringZZZ.isEmpty(sDirPathInJar))
					sDirPath = "c:\\temp"+ FileEasyZZZ.sDIRECTORY_SEPARATOR + sApplicationKeyAsSubDirectoryTemp;
				else {
					sDirPath = "c:\\temp"+ FileEasyZZZ.sDIRECTORY_SEPARATOR + sApplicationKeyAsSubDirectoryTemp + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirPathInJar;
				File objFileTemp = new File(sDirPathInJar);
				boolean bSuccess = false;
				if(!objFileTemp.exists()) {
					bSuccess = FileEasyZZZ.createDirectory(sDirPathInJar);
				}else {
					bSuccess = FileEasyZZZ.removeDirectoryContent(objFileTemp, true);
				}
				if(!bSuccess) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Keine Operation mit dem temporären Verzeichnis möglich '" + sDirPath + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				String archiveName = objFileJar.getAbsolutePath();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterInJar );
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();
				
				//Wie nun vom ht nach objaReturn ???
				//objaReturn = objDirectory.listFiles(objFilterConfig);
				//Es geht nur als temporäres Objekt, das man in ein temp-Verzeichnis ablegt.								
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
				try {
					ZipFile zf = null;
					while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
						
						//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)					
						zf = objJarInfo.getZipFile();						
						InputStream is = zf.getInputStream(zeTemp);
						String sKeyNormed = StringZZZ.replace(sKey, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
						String sPath = "c:\\temp"+ FileEasyZZZ.sDIRECTORY_SEPARATOR + sApplicationKeyAsSubDirectoryTemp + FileEasyZZZ.sDIRECTORY_SEPARATOR + sKeyNormed;
						
						//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
						FileEasyZZZ.removeFile(sPath);
												
						Files.copy(is, Paths.get(sPath));
						File objFileTempInTemp = new File(sPath);	
						objaFileTempInTemp.add(objFileTempInTemp);
					}
					if(zf!=null) zf.close();
					objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}//End main		 	
		return objaReturn;
	}
}
