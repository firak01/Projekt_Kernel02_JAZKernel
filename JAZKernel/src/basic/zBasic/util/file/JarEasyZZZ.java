package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.FileDirectoryFilterInJarZZZ;
import basic.zBasic.util.file.jar.FileFileFilterInJarZZZ;
import basic.zBasic.util.file.jar.JarInfo;
import basic.zBasic.util.file.zip.FileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFilenamePartFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEasyZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class JarEasyZZZ implements IConstantZZZ, IResourceHandlingObjectZZZ{

	/**
	*  This method is responsible for extracting resource files from within the .jar to an temporarily existing file.
	*  NOT a file peristed in the temp - Directory !!!
	*  @param filePath The filepath is th	e directory within the .jar from which to extract the file.
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
			        File f = FileEasyZZZ.createTempFile(sFilePath); 
			        FileOutputStream resourceOS = new FileOutputStream(f);
			        byte[] byteArray = new byte[1024];
			        int i;

			      //Der Pfad muss so sein, wie in der JAR - Datei abgelegt. Also mit Slashes, z.B.: InputStream classIS = getClass().getClassLoader().getResourceAsStream("Resources/"+filePath);
			        String sFilePathInJar = JarEasyUtilZZZ.toJarFilePath(sFilePath);
			        String sLog = ReflectCodeZZZ.getPositionCurrent()+": (G) Trying to create InputStream for : '" + sFilePathInJar + "'";
					System.out.println(sLog);
					
			        InputStream classIS = JarEasyZZZ.class.getClassLoader().getResourceAsStream(sFilePathInJar);
			//While the input stream has bytes
			        while ((i = classIS.read(byteArray)) > 0) 
			        {
			//Write the bytes to the output stream
			            resourceOS.write(byteArray, 0, i);
			        }
			//Close streams to prevent errors
			        if(classIS!=null) classIS.close();
			        if(resourceOS!=null) resourceOS.close();
			        if(f==null) {
			        	break main;		    
			        }else {
			        	objReturn = f;
			        }
				}catch (Exception e){
			    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			    }	    
				}//end main:
				return objReturn;
		}
	
		
	/**
	*  This method is responsible for extracting resource files from within the .jar to an temporarily existing file.
	*  NOT a file peristed in the temp - Directory !!!
	*  @param filePath The filepath is the directory within the .jar from which to extract the file.
	*  @return A file object to the extracted file
	*  **/
	public static File extractFileFromJarAsTemp(JarFile objJarFile, JarEntry entry) throws ExceptionZZZ {
			File objReturn=null;
			main:{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(entry==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarEntry-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sFilePath = entry.getName();
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Extracting Entry '" + sFilePath + "'";
				System.out.println(sLog);
				
				sFilePath = JarEasyUtilZZZ.toFilePath(sFilePath);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Entry to FilePath '" + sFilePath + "'";
				System.out.println(sLog);
				
				objReturn = JarEasyZZZ.extractFileFromJarAsTemp(objJarFile, sFilePath);
				
			}//end main:
			return objReturn;
		}
	
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47	
	 */
	public static File[] peekResourceFile(JarFile objJarFile, String sSourceFilePath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
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
						
			JarEntry entry = JarEasyUtilZZZ.getEntryAsFile(objJarFile, sSourceFilePath);
			if(entry==null){
			  	String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR (File) PATH: '" + sSourceFilePath +"'";
			   	System.out.println(sLog);			    				   				   			   	
			}else{					    	
			   	String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND FOR (File) PATH: '" + sSourceFilePath +"'";
			   	System.out.println(sLog);
					
			   	objaReturn = JarEasyZZZ.peekResourceFile(objJarFile, entry,sTargetDirectoryPathIn);
			}
		}//end main:
		return objaReturn;
	}
	
		
//	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
//	 * Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
//	 * @param filePath
//	 * @return
//	 * @throws ExceptionZZZ
//	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
//	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
//	 */
//	public static File[] extractDirectoryFromJarAsFileDummies(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
//		File[] objaReturn=null;
//		main:{
//			objaReturn = JarEasyZZZ.peekResourceDirectory(objJarFile, sSourceDirectoryPath, sTargetDirectoryPathRootIn, true);
//		}//end main:
//		return objaReturn;
//	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekResourceFile(JarFile objJarFile, JarEntry entry, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			try{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(entry==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarEntry-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sFilePath = entry.getName();
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) Extracting Entry '" + sFilePath + "'";
				System.out.println(sLog);
								
												
				//1. Aus der Jar Datei nur die Datei herausfiltern.						
				String archiveName = objJarFile.getName();								
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(null, entry.getName());
				IFilenamePartFilterZipZZZ objFilterFilePathPart = objFilterFileInJar.getNamePartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );  //Merke: Das dauert laaange
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<File>objaFileTempInTemp = new ArrayList<File>();

				//Nur Dateien-Fall
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
					if(!zeTemp.isDirectory()) {
						//Nun aus dem ZipEntry ein File Objekt machen 						
						File objFileTemp = JarEasyZZZ.createFileDummy(entry, sTargetDirectoryPathIn);
						objaFileTempInTemp.add(objFileTemp);
					}
				}
										
				objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);					
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objaReturn;
	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekResourceDirectory(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{			
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				JarEntry entry = JarEasyUtilZZZ.getEntryAsDirectory(objJarFile, sSourceDirectoryPath);
				if(entry==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarEntry-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				objaReturn = JarEasyZZZ.peekResourceDirectory(objJarFile, entry, sTargetDirectoryPathIn, bWithFiles);
		}//end main:
		return objaReturn;
	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekResourceDirectory(JarFile objJarFile, JarEntry entry, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			try{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(entry==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarEntry-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sFilePath = entry.getName();
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) Extracting Entry '" + sFilePath + "'";
				System.out.println(sLog);
															
				if(bWithFiles) { //Dateien im Verzeichnis
					//1. Aus der Jar Datei nur das Verzeichnis herausfiltern.						
					String archiveName = objJarFile.getName();								
					IFileFilePartFilterZipUserZZZ objFilterDirectoryInJar = new FileFileFilterInJarZZZ(null, entry.getName());
					IFilenamePartFilterZipZZZ objFilterFilePathPart = objFilterDirectoryInJar.getDirectoryPartFilter();
					objFilterFilePathPart.setCriterion(entry.getName());
					JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );  //Merke: Das dauert laaange
					
					//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
					Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
					Set<String> setEntryName = ht.keySet();
					Iterator<String> itEntryName = setEntryName.iterator();
					ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
					
					while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
							
						//Nun aus dem ZipEntry ein File Objekt machen 
						File objFileTemp = JarEasyZZZ.createFileDummy(entry, sTargetDirectoryPathIn);
						objaFileTempInTemp.add(objFileTemp);							
					}	
					objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
					
				}else { //Nur Verzeichnis-Fall
					//1. Aus der Jar Datei nur das Verzeichnis herausfiltern.						
					String archiveName = objJarFile.getName();								
					IFileDirectoryPartFilterZipUserZZZ objFilterDirectoryInJar = new FileDirectoryFilterInJarZZZ(entry.getName());
					IFileDirectoryPartFilterZipZZZ objFilterFilePathPart = objFilterDirectoryInJar.getDirectoryPartFilter();
					JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );  //Merke: Das dauert laaange
					
					//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
					Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
					Set<String> setEntryName = ht.keySet();
					Iterator<String> itEntryName = setEntryName.iterator();
					ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
	
					while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
						if(zeTemp.isDirectory()) {
							//Nun aus dem ZipEntry ein File Objekt machen 							
							File objFileTemp = JarEasyZZZ.createFileDummy(entry, sTargetDirectoryPathIn);
							objaFileTempInTemp.add(objFileTemp);
						}
					}
					objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);
				}

			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objaReturn;
	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractFileFromJarAsFileDummy(JarFile objJarFile, String sSourcePath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			try{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				JarEntry entry = JarEasyUtilZZZ.getEntryAsFile(objJarFile, sSourcePath);
				if(entry==null){
				  	String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR PATH: '" + sSourcePath +"'";
				   	System.out.println(sLog);	
				   	break main;
				}						
								
				objReturn = JarEasyZZZ.createFileDummy(entry, sTargetDirectoryPathIn);
								
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	/** 
	 Merke1: Wenn ein Verzeichnis aus der JAR Datei zu extrahieren ist, dann werden lediglich die File Objekt zurückgegeben.
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
			
			if(bExtractFiles) {
				objaReturn = JarEasyZZZ.extractFilesFromJarAsTrunkZipEntries(objJarFile, sSourceFilePath);				
			}else {				
				objaReturn = JarEasyZZZ.extractDirectoryFromJarAsTrunkEntry(objJarFile, sSourceFilePath);
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
	public static ZipEntry[] extractDirectoryFromJarAsTrunkEntry(JarFile objJarFile, JarEntry jarEntryOfDirectory, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
		main:{
				if(jarEntryOfDirectory==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarEntry provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
				
				String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
				sDirectoryFilePathInJar = FileEasyZZZ.getParent(sDirectoryFilePathInJar);
			
				objaReturn = JarEasyZZZ.extractDirectoryFromJarAsTrunkEntry(objJarFile, sDirectoryFilePathInJar);
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
	public static ZipEntry[] extractDirectoryFromJarAsTrunkEntry(JarFile objJarFile, String sSourceDirectoryPath) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
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
				
			
				//1. Aus der Jar Datei nur das Verzeichnis herausfiltern.						
				String archiveName = objJarFile.getName();				
				sSourceDirectoryPath = JarEasyUtilZZZ.toJarFilePath(sSourceDirectoryPath);				
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
				}
				objaReturn = ArrayListZZZ.toZipEntryArray(objaZipEntry);
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objaReturn;
	}
	
	public static ZipEntry[] extractFilesFromJarAsTrunkZipEntries(JarFile objJarFile, String sSourceDirectoryPath) throws ExceptionZZZ {
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
						
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(null, sSourceDirectoryPath);
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
	
	public static File extractFromJarAsTrunk(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn,ReferenceZZZ<HashMap<ZipEntry,File>>hashmapTrunk) throws ExceptionZZZ {	
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
			
			//Merke: Weil es "trunk" ist, wird das Verzeichnis HIER nicht erstellt, sondern erst beim Speichern des Trunk-Inhalts.
			//Merke: Hier gibt es zudem eine "Call by Reference" Problematik, darum über ein Zwischenobjekt arbeiten und dort hinein die Objekte ablegen.
			HashMap<ZipEntry,File> hmTrunk = hashmapTrunk.get();
			hmTrunk = JarEasyZZZ.extractFromJarAsTrunk(objJarFile, sSourceDirectoryPath, sTargetDirectoryPath);	//DAS DAUERT LANGE					
			hashmapTrunk.set(hmTrunk);
		}//end main:
		return objReturn;
	}
	
	public static HashMap<ZipEntry,File>extractFromJarAsTrunk(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ{
		HashMap<ZipEntry,File>hmReturn = new HashMap<ZipEntry,File>();
		main:{
			
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				//IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(null, sSourceDirectoryPath);
				//FilenamePartFilterPathZipZZZ objFilterFilePathPart = objFilterFileInJar.getDirectoryPartFilter();
				//JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );//Das dauert laaange
				
				IFileDirectoryPartFilterZipUserZZZ objFilterDirectoryInJar = new FileDirectoryFilterInJarZZZ(sSourceDirectoryPath);
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterDirectoryInJar );//Das dauert laaange
								
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();				
				while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
												
						File objFileTemp = JarEasyZZZ.createFileDummy(zeTemp, sTargetDirectoryPathIn);
						
						//Das Ergebnis in die Trunk - HashMap packen
						hmReturn.put(zeTemp, objFileTemp);
				}
								
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
			
		}//end main:
		return hmReturn;
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
	public static File extractDirectoryToTemp(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{		
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, true);		
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
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, bWithFiles);
		}//end main:
		return objReturn;
	}
	
	public static File extractDirectoryToTemp(JarFile objJarAsFile, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn = null;		
		main:{
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
			
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, bWithFiles);
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
	public static File extractDirectoryToTemp(File objFileAsJar, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{			
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);			
			
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, true);
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
	public static File extractDirectoryToTemp(JarFile objJarAsFile, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{			
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
						
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);	
			
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, true);

		}//end main:
		return objReturn;
	}
	
	private static File extractDirectoryToTemp_(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			File[] objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, bWithFiles);
			if(objaReturn==null) break main;
			
			//Problem: In dem Array sind nun alle extrahierten Dateien. Aber nicht das Verzeichnis selbst.
			//Daher parent zurückgeben
			objReturn = objaReturn[0].getParentFile();	
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
	public static File[] extractDirectoryToTemps(File objFileAsJar, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{						
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, true);
											
			}//end main:
			return objaReturn;
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
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{	
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, bWithFiles);			
		}//end main:
		return objaReturn;
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
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{	
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
									
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, true);
		}//end main:
		return objaReturn;
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
	public static File[] extractDirectoryToTemps(File objFileAsJar, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{								
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
															
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, bWithFiles);
		}//end main:
		return objaReturn;
	}
	
	
	
	
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
File[] objaReturn = null;
		
		main:{
			try {
				if(objJarAsFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
							
				File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
				objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, true);
			
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
		}//end main:
		return objaReturn;
	}
	
	
	public static File[] extractDirectoryToTemps(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{									
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, bWithFiles);
		}//end main:
		return objaReturn;
	}
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn = null;
		
		main:{
			try {
				if(objJarAsFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
							
				File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
				objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, bWithFiles);
			
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
		}//end main:
		return objaReturn;
	}
	
	private static File[] extractDirectoryToTemps_(File objFileAsJar, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn = null;
		
		main:{
			if(objFileAsJar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)) {
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			String sTargetDirectoryFilepath;
			if(sTargetDirectoryFilepathIn==null)
				sTargetDirectoryFilepath="";  //TEMP VERZEICHNIS???
			else {
				//Links und rechts ggfs. übergebenen Trennzeichen entfernen. So normiert kann man gut weiterarbeiten.				
				sTargetDirectoryFilepath=StringZZZ.stripFileSeparators(sTargetDirectoryFilepathIn);									
			}
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) sTargetDirectoryFilepath='" + sTargetDirectoryFilepath +"'.";
		   	System.out.println(sLog);
			
			//+++ Verwende nun die Resourcen - Behandlung, damit das Verzeichnis auch tatsächlich erstellt wird ++++++++++++
			try {			
			    if(bWithFiles) {
			    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) With Files Case.";
				   	System.out.println(sLog);
				   	
					//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
					IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sDirectoryFilePathInJar, null);
					objaReturn = JarEasyUtilZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryFilepath);					
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Without Files Case.";
				   	System.out.println(sLog);
				   	
					//Nur das Verzeichnis erstellen... also den reinen Verzeichnis Filter
					IFileDirectoryPartFilterZipUserZZZ objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sDirectoryFilePathInJar);										
					objaReturn = JarEasyUtilZZZ.findDirectoryInJar(objFileAsJar, objFilterDirInJar, sTargetDirectoryFilepath, false);
				}
			
			}catch (Exception e){
				
				//TODOGOON; //Ich will endlich über e.getMessage die exceptionZZZ Message bekommen!!!
				
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
		    }	    
		}//end main:
		return objaReturn;
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
						
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(null, sSourceDirectoryPath);
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
					
					File objFileTemp = JarEasyZZZ.createFileDummy(zeTemp, sTargetDirectoryPathIn);
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
		boolean bReturn = false;
		main:{
			if(hmTrunk==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "HashMap with Trunk ZipEntry, File - Objects", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			Set<ZipEntry> setEntry = hmTrunk.keySet();
			Iterator<ZipEntry> itEntry = setEntry.iterator();				
			while(itEntry.hasNext()) {
				ZipEntry zeTemp = itEntry.next();
				File fileTemp = (File)hmTrunk.get(zeTemp);
				
				File objReturn = JarEasyZZZ.saveTrunkToFile(jf, zeTemp, fileTemp);
				if(objReturn==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Unable to save File - Object '" + fileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;

				}
			}
			bReturn = true;		
		}//end main:
		return bReturn;
	}
	
	public static File[] saveTrunkAsFiles(JarFile jf, HashMap<ZipEntry,File> hmTrunk) throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{			
			if(hmTrunk==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "HashMap with Trunk ZipEntry, File - Objects", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}

			Set<ZipEntry> setEntry = hmTrunk.keySet();
			Iterator<ZipEntry> itEntry = setEntry.iterator();
			
			ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
			while(itEntry.hasNext()) {
				ZipEntry zeTemp = itEntry.next();
				File fileTemp = (File)hmTrunk.get(zeTemp);
				
				File objReturn = JarEasyZZZ.saveTrunkToFile(jf, zeTemp, fileTemp);
				if(objReturn==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Unable to save File - Object '" + fileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}else {
					objaFileTempInTemp.add(objReturn);
				}
			}
			objaReturn = ArrayListZZZ.toFileArray(objaFileTempInTemp);	
		}//end main:
		return objaReturn;
	}
	
	public static File saveTrunkToFile(JarFile jf, ZipEntry ze, File fileAsTrunk) throws ExceptionZZZ {
		File objReturn=null;
		main:{

				//### Nun neu erstellen, dabei wird ggfs. zuvor gelöscht
				objReturn = ZipEasyZZZ.extractZipEntryTo(jf, ze, fileAsTrunk);

		}//end main:
		return objReturn;
	}
	
	public static File saveTrunkToDirectory(JarFile jf, ZipEntry ze, String sTargetDirectoryPathIn ) throws ExceptionZZZ {
		File objReturn=null;
		main:{								
			//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)																
			File objFile = JarEasyZZZ.createFileDummy(ze, sTargetDirectoryPathIn);
			objReturn = ZipEasyZZZ.extractZipEntryTo(jf, ze, objFile);						
		}//end main:
		return objReturn;
	}
	
	public static File createFileDummy(ZipEntry ze, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog;
			if(ze==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= ".";
			}else {
				sTargetDirectoryPath = sTargetDirectoryPathIn;
			}
			
			String sEntryName = ze.getName();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": sEntryName='" + sEntryName + "'.";
		    System.out.println(sLog);
			
		    String sFilePath = JarEasyUtilZZZ.toFilePath(sEntryName);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePath='" + sFilePath + "'.";
		    System.out.println(sLog);
		    
		    String sFilePathTotal = FileEasyZZZ.joinFilePathName(sTargetDirectoryPath, sFilePath);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePathTotal='" + sFilePathTotal + "'.";
		    System.out.println(sLog);
		    
			//Nun aus dem ZipEntry ein File Objekt machen 
			//https://www.rgagnon.com/javadetails/java-0429.html
			objReturn = new File(sFilePathTotal);
			
		}
		return objReturn;
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
			bReturn = JarEasyUtilZZZ.isInJar(JarEasyZZZ.class);
		}
		return bReturn;
	}
	
	//### Interfaces
	//aus IRessourceHandlingObjectZZZ
	
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
				bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
			}
			return bReturn;
		}
}



