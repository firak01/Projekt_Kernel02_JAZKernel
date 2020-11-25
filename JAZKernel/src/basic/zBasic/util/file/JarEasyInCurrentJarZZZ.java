package basic.zBasic.util.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

/** Methoden für die Arbeit mit der gleichen Jar Datei, wenn die Methoden aus der Jar-Datei aufgerufen werden.
 * @author Fritz Lindhauer, 05.08.2020, 14:05:25
 * 
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
 * 
 */
public class JarEasyInCurrentJarZZZ  implements IConstantZZZ,IResourceHandlingObjectZZZ{

	public static boolean  isDirectory(JarFile jar, String sPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(jar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLog=null;
			JarEntry entry = JarEasyUtilZZZ.getEntryAsDirectory(jar,sPath);
		    if(entry==null){
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND: '" + sPath +"'";
		    	System.out.println(sLog);			    	
		    }else{
				    	
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND: '" + sPath +"'";
		    	System.out.println(sLog);
				    	
		    	//Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
		    	if(entry.isDirectory()) { //Dateien nicht extrahieren!!!
			    	bReturn = true;
				 }else {							    		
				    bReturn = false;
				 }
		    }				   
		}//end main:
		return bReturn;
	}
	
	/**Extrahiert die Datei als echte TEMP Datei. Verzeichnisse werden dabei nicht angegelgt, sondern kommen in den Dateinamen
	 * Z.B. template_template_server_starter.txt35232264199775134.tmp ist dann die Datei template_server_starter.txt im template Verzeichnis.
	 * @param sPath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.11.2020, 08:38:17
	 */
	public static File extractFileAsTemp(String sPath) throws ExceptionZZZ {
			File objReturn = null;
			main:{
				if(StringZZZ.isEmpty(sPath)){
					ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sLog = null;			    
				try {			
					JarFile jar = JarEasyUtilZZZ.getJarFileCurrent();
					if(jar==null) {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
					    System.out.println(sLog);
					}else {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
					    System.out.println(sLog);
					
					   JarEntry entry = JarEasyUtilZZZ.getEntryAsFile(jar, sPath); 
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
					}
					} catch (IOException e1) {
						ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}				
			}//end main:
			return objReturn;
		}

	public static File peekFile(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			File[] objaReturn = JarEasyInCurrentJarZZZ.searchResources_(sPath, sTargetDirectoryPathRootIn, false, false, false);
			if(objaReturn!=null) {
				objReturn = objaReturn[0];
			}
		}//end main:
		return objReturn;
	}
	
	public static File peekDirectory(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			File[] objaReturn = JarEasyInCurrentJarZZZ.searchResources_(sPath, sTargetDirectoryPathRootIn, true, false, false);
			if(objaReturn!=null) {
				objReturn = objaReturn[0];
			}
		}//end main:
		return objReturn;
	}
	
	public static File[] peekFilesOfDirectory(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{
			objaReturn = JarEasyInCurrentJarZZZ.searchResources_(sPath, sTargetDirectoryPathRootIn, false, true, false);			
		}//end main:
		return objaReturn;
	}
	
	
	
	
	
	/** Man sucht hiermit die Datei, diese wird in ein  existieren.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File searchResource(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			File[] objaReturn = JarEasyInCurrentJarZZZ.searchResources_(sPath, sTargetDirectoryPathRootIn, false, false, true);
			if(objaReturn==null) break main;
			
			objReturn = objaReturn[0];			
		}//end main:
		return objReturn;
		}
	
	/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
	 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
	 *  und das anschliessende speichern davon.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File searchResource(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			File[] objaReturn = JarEasyInCurrentJarZZZ.searchResources_(sPath, sTargetDirectoryPathRootIn, false, bWithFiles, true);
			if(objaReturn==null) break main;
			
			objReturn = objaReturn[0];
		}//end main:
		return objReturn;
		}
	
	
	/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
	 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
	 *  und das anschliessende speichern davon.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File[] searchResources(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{
				objaReturn = JarEasyInCurrentJarZZZ.searchResources_(sPath, sTargetDirectoryPathRootIn, false, false, true);
			}//end main:
			return objaReturn;
		}
	
	/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
	 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
	 *  und das anschliessende speichern davon.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File[] searchResources(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{
				objaReturn = JarEasyInCurrentJarZZZ.searchResources_(sPath, sTargetDirectoryPathRootIn, false, bWithFiles, true);
			}//end main:
			return objaReturn;
		}
	
	
	
	/** Private Methode, um Redundanz zu vermeiden
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @param bSave
	 * @return
	 * @author Fritz Lindhauer, 28.10.2020, 10:48:25
	 * @throws ExceptionZZZ 
	 */
	private static File[] searchResources_(String sPath, String sTargetDirectoryPathRootIn, boolean bAsDirectory, boolean bWithFiles, boolean bSave) throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{
			try {
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			String sTargetDirectoryPathRoot=null;
			if(StringZZZ.isEmpty(sTargetDirectoryPathRootIn)) {
				sTargetDirectoryPathRoot =  "ZZZ";
			}else {
				sTargetDirectoryPathRoot = sTargetDirectoryPathRootIn;				
			}

			String sLog = null;
			JarFile jar = JarEasyUtilZZZ.getJarFileUsed();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			    break main;
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND: '" + jar.getName() + "'";
			    System.out.println(sLog);
			}
			
			JarEntry entry = null;
			if(bAsDirectory) {
				entry = JarEasyUtilZZZ.getEntryAsDirectory(jar, sPath);
			} else {
				entry = JarEasyUtilZZZ.getEntryAsFile(jar, sPath);
			}
			if(entry==null){
			  	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR PATH: '" + sPath +"'";
			   	System.out.println(sLog);			    	
			}else{					    	
			   	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND FOR PATH: '" + sPath +"'";
			   	System.out.println(sLog);
				    	
			   	String sTargetDirPath = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sTargetDirectoryPathRoot);
			   	
			    //Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
			    if(entry.isDirectory()) { //DATEIEN NICHT SOFORT EXTRAHIEREN!!!  

			    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
				   	System.out.println(sLog);
				    
				   	if(bSave) {
				   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
					   	
				   		objaReturn = JarEasyZZZ.extractDirectoryToTemps(jar, entry, sTargetDirPath, bWithFiles);
				   		
				   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA2) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
				   	}else {				   		
				   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
					   	
				   		objaReturn = JarEasyZZZ.peekResourceDirectory(jar, entry,sTargetDirPath, bWithFiles);	
				   		
				   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB2) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
				   	}
				   	
			    	if(objaReturn!=null) {
			    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY - FILE OBJECTS FROM JARENTRY CREATED: '" + entry.getName() +"'";
				    	System.out.println(sLog);
				    	
				    	if(objaReturn[0].exists()) {
				    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT EXISTS AS: '" + objaReturn[0].getAbsolutePath() +"'";
					    	System.out.println(sLog);
				    	}else {
				    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT NOT(!) EXISTS AS: '" + objaReturn[0].getAbsolutePath() +"'";
					    	System.out.println(sLog);
				    	}				    		
		    		}else {
		    			sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY NOT CREATED '" + sTargetDirPath + "' (NULL CASE)";
				    	System.out.println(sLog);
		    		}
		    	
			    }else {
			    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS NOT A DIRECTORY: '" + entry.getName() +"'";
				   	System.out.println(sLog);
				   	
				   	File objReturnTrunk = JarEasyZZZ.createFileDummy(entry,sTargetDirPath);
			    	if(objReturnTrunk!=null) {
			    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT FROM JARENTRY CREATED: '" + entry.getName() +"'->'"+objReturnTrunk.getAbsolutePath()+"'";
				    	System.out.println(sLog);

				    	if(bSave) {
					    	File objReturn = JarEasyZZZ.saveTrunkToFile(jar, entry, objReturnTrunk);
					    	if(objReturn!=null) {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT PERSISTED AS: '" + objReturn.getAbsolutePath() +"'";
						    	System.out.println(sLog);
								    	
						    	if(objReturn.exists()) {
						    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT EXISTS AS: '" + objReturn.getAbsolutePath() +"'";
							    	System.out.println(sLog);
						    	}else {
						    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT NOT(!) EXISTS AS: '" + objReturn.getAbsolutePath() +"'";
							    	System.out.println(sLog);
						    	}						    							    	
					    	}else {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT NOT PERSISTED AS: '" + objReturn.getAbsolutePath() +"' (NULL CASE)";
						    	System.out.println(sLog);
					    	}
					    	objaReturn = FileArrayEasyZZZ.add(objaReturn, objReturn);
				    	}else {
				    		objaReturn = FileArrayEasyZZZ.add(objaReturn, objReturnTrunk);
				    	}
				    					    	
			    	}
			    }
			}
				jar.close();
			} catch (IOException e1) {
				ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}// end main:
		return objaReturn;
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
			bReturn = JarEasyUtilZZZ.isInJar(JarEasyInCurrentJarZZZ.class);
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
