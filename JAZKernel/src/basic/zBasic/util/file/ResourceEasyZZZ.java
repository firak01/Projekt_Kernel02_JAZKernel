package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ResourceEasyZZZ extends ObjectZZZ implements IResourceHandlingObjectZZZ{
	private ResourceEasyZZZ(){
		//Zum Verstecken des Konstruktors
	}

	public static File doClassloaderGetResource(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath))break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) isearching for path '" + sPath + "'";
			System.out.println(sLog);
			URL workspaceURL = JarEasyUtilZZZ.class.getClassLoader().getResource(sPath);
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
			URL workspaceURL = JarEasyUtilZZZ.class.getResource(sPath);
			objReturn = ResourceEasyZZZ.getResource(workspaceURL, sPath, false);			
		}
		return objReturn;
	}

	static File getResource(URL workspaceURL, String sPath, boolean byClassloader) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog=null;
			if(JarEasyUtilZZZ.isInJarStatic()){				
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
					    	isClass = JarEasyUtilZZZ.class.getClassLoader().getResourceAsStream(sPath);
					    }else{
					    	isClass = JarEasyUtilZZZ.class.getResourceAsStream(sPath);
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
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) WorkspaceURL is null, searching for JarFile.";
				    System.out.println(sLog);
				    try {
					    //Nun muss mann die Ressource aus einer .jar-Datei holen.
					    //Für das Return - Objekt muss man differenziert verfahren: Verzeichnis oder Datei
					    //0. JarFile holen
					    JarFile jar = JarKernelZZZ.getJarFileCurrent();
					    if(jar==null) {
						    sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) JarFileCurrent not available.";
						    System.out.println(sLog);
						    break main;
					    }
					    
					    //1. Prüfe auf Verzeichnis / Datei
					    boolean bEntryIsDirectory = JarEasyInCurrentJarZZZ.isDirectory(jar, sPath);
					    if(bEntryIsDirectory) {
					    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) ENTRY IS DIRECTORY, WILL NOT BE EXTRACTED AS TEMP-FILE, BUT AS REAL TEMP DIRECTORY.";
					    	System.out.println(sLog);
					    	objReturn = JarEasyInCurrentJarZZZ.searchResourceDirectory(sPath, "");
					    }else {
					    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) ENTRY IS FILE, WILL BE EXTRACTED AS TEMP-FILE";
						    System.out.println(sLog);						    	
					    	objReturn = JarEasyInCurrentJarZZZ.extractFileAsTemp(sPath);
					    }
					    
					    if(objReturn==null){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Ressource NICHT gefunden '" + sPath + "' (NULL CASE)";
						    System.out.println(sLog);
						}else {
						    if(FileEasyZZZ.exists(objReturn)){
								sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Ressource gefunden '" + sPath + "'";
							    System.out.println(sLog);							
							}else {
								sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Ressource NICHT gefunden '" + sPath + "'";
							    System.out.println(sLog);								
							}
						}
				    
					  //JarFile wieder schliessen.
					  jar.close();
				    } catch (IOException ioe) {
						ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;
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
			
		}//end main:
		return objReturn;
	}
	
	
	/** Finde nur das Verzeichnis in der JAr Datei. Es wird ein Dummy-Objekt zurückgegeben, das NICHT auf der Platte ist.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File peekDirectoryInJar(File objFileAsJar, String sPath, String sDirExtractTo)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objReturn = JarEasyInCurrentJarZZZ.peekDirectory(sPath, sDirExtractTo);
			}else {
				JarFile objFileJar = JarKernelZZZ.getJarFileUsed();
				File[] objaReturn = JarEasyZZZ.peekResourceDirectories(objFileJar, sPath, sDirExtractTo, false);
				if(objaReturn==null) break main;
				
				objReturn = objaReturn[0];
			}
			
		}//end main:
		return objReturn;
	}
	
	/** Finde nur die angegebene Datei in der Jar Datei. Es wird ein Dummy-Objekt zurückgegeben, das NICHT auf der Platte ist.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File peekFileInJar(File objFileAsJar, String sPath, String sDirExtractTo)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objReturn = JarEasyInCurrentJarZZZ.peekFile(sPath, sDirExtractTo);
			}else {
				JarFile objFileJar = JarKernelZZZ.getJarFileUsed();
				File[] objaReturn = JarEasyZZZ.peekResourceFiles(objFileJar, sPath, sDirExtractTo);
				if(objaReturn==null) break main;
				
				objReturn = objaReturn[0];
			}
			
		}//end main:
		return objReturn;
	}
	
	/** Finde nur die angegebene Datei in der Jar Datei. Es wird ein Dummy-Objekt zurückgegeben, das NICHT auf der Platte ist.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File[] peekFilesInJar(File objFileAsJar, String sFilename, String sDirExtractTo)throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objaReturn = JarEasyInCurrentJarZZZ.peekFiles(sFilename, sDirExtractTo);
			}else {
				JarFile objFileJar = JarKernelZZZ.getJarFileUsed();
				objaReturn = JarEasyZZZ.peekResourceFiles(objFileJar, null, sDirExtractTo);
			}
			
		}//end main:
		return objaReturn;
	}
	
	
	
	
	/** Finde nur das Verzeichnis in der JAr Datei. Es werden die Dateien als Dummy-Objekt zurückgegeben, die NICHT auf der Platte sind.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File[] peekFilesOfDirectoryInJar(File objFileAsJar, String sPath, String sDirExtractTo)throws ExceptionZZZ {
		File[]objaReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objaReturn = JarEasyInCurrentJarZZZ.peekFiles(sPath, sDirExtractTo);
			}else {
				JarFile objFileJar = JarKernelZZZ.getJarFileUsed();
				objaReturn = JarEasyZZZ.peekResourceDirectories(objFileJar, sPath, sDirExtractTo, true);		
			}
		}//end main:
		return objaReturn;
	}
	
	/** Finde das Verzeichnis in der JAr Datei. Es wird ein File-Objekt zurückgegeben, das auf der Platte gespeichert ist.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File searchDirectoryInJar(File objFileAsJar, String sPath, String sDirExtractTo)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			//TODOGOON // mache auch in den JAREasy-Klassen die Konvention: 
			//peek => nix erzeugen
			//search => im Temp erzeugen
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
			//	objReturn = JarEasyInCurrentJarZZZ.searchResourceToDummy(sPath, sDirExtractTo);
			}else {
				JarFile objFileJar = JarKernelZZZ.getJarFileUsed();
			//	File[] objaReturn = JarEasyZZZ.searchResourceToDummies(objFileJar, sPath, sDirExtractTo, false);
			//	if(objaReturn==null) break main;
				
			//	objReturn = objaReturn[0];
			}
			
		}//end main:
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
			bReturn = JarEasyUtilZZZ.isInJar(ResourceEasyZZZ.class);
		}
		return bReturn;
	}
	
	public static boolean isInSameJarStatic(File objFileAsJar) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileAsJar==null)break main;
			if(!ResourceEasyZZZ.isInJarStatic()) {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": Not running from jar file.";
			    System.out.println(sLog);
				break main;			
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)){
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile.", iERROR_PARAMETER_VALUE, ResourceEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 File objFileJar = JarEasyUtilZZZ.getCodeLocationJar();
			 if(objFileJar==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Unable to get CodeLocation for running jar", iERROR_RUNTIME, ResourceEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			 }
			  
			 bReturn = objFileJar.equals(objFileAsJar);
		}//end main;
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

