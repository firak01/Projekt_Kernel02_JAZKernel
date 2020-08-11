package basic.zBasic.util.file;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

/** Methoden für die Arbeit mit der gleichen Jar Datei, wenn die Methoden aus der Jar-Datei aufgerufen werden.
 * @author Fritz Lindhauer, 05.08.2020, 14:05:25
 * 
 */
public class JarEasyInCurrentJarZZZ  implements IConstantZZZ{

	public static JarFile getJarFileCurrent() throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			String sLog = null;
			final File jarFile = JarEasyZZZ.getJarCurrent(); //new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			if(jarFile.isFile()) {  // Run with JAR file
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE FOUND.";
			    System.out.println(sLog);
				try {
					objReturn = new JarFile(jarFile);									
				} catch (IOException e1) {
					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}
		}//end main:
		return objReturn;
	}
		
	public static JarEntry getEntry(JarFile jar, String sPath) throws ExceptionZZZ {
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
			String sPathInJar = JarEasyZZZ.toJarFilePath(sPath); 
			sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) Searching for '" + sPathInJar + "'";				
			System.out.println(sLog);
				
			objReturn = jar.getJarEntry(sPathInJar);
				   
				    
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
				    
		}//end main:
		return objReturn;
	}
	
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
			JarEntry entry = JarEasyInCurrentJarZZZ.getEntry(jar,sPath);
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
//			  }
//			  jar.close();
//			} catch (IOException e1) {
//					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//			}else{
//				//Keine Jar Datei
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) NO JAR FILE FOUND'";
//				System.out.println(sLog);			
//			}						
		}//end main:
		return bReturn;
	}
	
	public static File extractFileAsTemp(String sPath) throws ExceptionZZZ {
			File objReturn = null;
			main:{
				if(StringZZZ.isEmpty(sPath)){
					ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sLog = null;			    
				try {			
					JarFile jar = JarEasyInCurrentJarZZZ.getJarFileCurrent();
					if(jar==null) {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
					    System.out.println(sLog);
					}else {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
					    System.out.println(sLog);
					
					   JarEntry entry = JarEasyInCurrentJarZZZ.getEntry(jar, sPath); 
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
					}
					} catch (IOException e1) {
						ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}				
			}//end main:
			return objReturn;
		}

	public static File searchResourceToTemp(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File objReturn = null;
			main:{
				try {
				if(StringZZZ.isEmpty(sPath)){
					ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sTargetDirectoryPathRoot=null;
				if(StringZZZ.isEmpty(sTargetDirectoryPathRootIn)) {
					sTargetDirectoryPathRoot = "ZZZ";
				}else {
					sTargetDirectoryPathRoot = sTargetDirectoryPathRootIn;				
				}

				String sLog = null;
				JarFile jar = JarEasyInCurrentJarZZZ.getJarFileCurrent();
				if(jar==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
				    System.out.println(sLog);
				    break main;
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND: '" + jar.getName() + "'";
				    System.out.println(sLog);
				}
				
				JarEntry entry = JarEasyInCurrentJarZZZ.getEntry(jar, sPath);
				if(entry==null){
				  	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR PATH: '" + sPath +"'";
				   	System.out.println(sLog);			    	
				}else{					    	
				   	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND FOR PATH: '" + sPath +"'";
				   	System.out.println(sLog);
					    	
				   	String sTargetDirPath = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sTargetDirectoryPathRoot);
					    	
					    	
				    //Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
				    if(entry.isDirectory()) { //Dateien nicht extrahieren!!!
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
					    		
				    	File fileDirTemp = JarEasyZZZ.extractDirectoryFromJarAsTrunkFileDummy(jar, entry,sTargetDirPath, false);
				    	if(fileDirTemp!=null) {
				    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK FILE OBJECT FROM JARENTRY CREATED: '" + entry.getName() +"'";
					    	System.out.println(sLog);
					    			
					    	//Merke, damit es kein TRUNK-OBJEKT bleibt, das Verzeichnis erstellen.
				    		boolean bErg= FileEasyZZZ.createDirectory(fileDirTemp);
				    		if(bErg) {
				    			sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY SUCCESFULLY CREATED '" + fileDirTemp.getAbsolutePath() +"'";
						    	System.out.println(sLog);
								    	
				    			objReturn = fileDirTemp;
				    		}else {
				    			sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY NOT CREATED '" + fileDirTemp.getAbsolutePath() +"'";
						    	System.out.println(sLog);
				    		}
				    	}
				    }else {
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS NOT A DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);

					   	File fileTemp = JarEasyZZZ.extractFileFromJarAsTrunkFileDummy(jar, entry,sTargetDirPath);
				    	if(fileTemp!=null) {
				    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT FROM JARENTRY CREATED: '" + entry.getName() +"'";
					    	System.out.println(sLog);
							    	
					    	boolean bErg = JarEasyZZZ.saveTrunkToFile(jar, entry, fileTemp);
					    	if(bErg) {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT PERSISTED AS: '" + fileTemp.getAbsolutePath() +"'";
						    	System.out.println(sLog);
								    	
						    	objReturn = fileTemp;
					    	}else {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT NOT PERSISTED AS: '" + fileTemp.getAbsolutePath() +"'";
						    	System.out.println(sLog);
					    	}
				    	}
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
			bReturn = JarEasyZZZ.isInJar(JarEasyInCurrentJarZZZ.class);
		}
		return bReturn;
	}
}
