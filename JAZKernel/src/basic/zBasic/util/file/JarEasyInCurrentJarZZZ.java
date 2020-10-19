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
 */
public class JarEasyInCurrentJarZZZ  implements IConstantZZZ,IResourceHandlingObjectZZZ{

	public static JarFile getJarFileCurrent() throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			String sLog = null;
			final File jarFile = JarEasyUtilZZZ.getJarCurrent(); //new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
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
			String sPathInJar = JarEasyUtilZZZ.toJarFilePath(sPath); 
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

	/** Man sucht hiermit die Datei als File-Dummy, diese wird nicht existieren.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File searchResource(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
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
				    if(entry.isDirectory()) { //DATEIEN DOCH AUCH SOFORT EXTRAHIEREN!!! Grund: Ggfs. wird dann in dem zurückgegebenen Verzeichnis sofort nach den Dateien gesucht.				    	
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
					   	
				    	File[] objaFile = JarEasyZZZ.extractDirectoryFromJarAsFileDummies(jar, entry, sTargetDirectoryPathRootIn, false);
				    	objReturn = objaFile[0];
				    	
				    }else {
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS FILE '" + entry.getName() + "'";
					   	System.out.println(sLog);
					   	
					   	objReturn = JarEasyZZZ.extractFileFromJarAsFileDummy(jar, entry, sTargetDirectoryPathRootIn);
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
	
	/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
	 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
	 *  und das anschliessende speichern davon.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File searchResourceToTemp(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
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
				    if(entry.isDirectory()) { //DATEIEN DOCH AUCH SOFORT EXTRAHIEREN!!! Grund: Ggfs. wird dann in dem zurückgegebenen Verzeichnis sofort nach den Dateien gesucht. 
				    	
				    	
				    	//Dateien erst mal nicht extrahieren, später schon!!!
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
					    		
					   	//HIER NICHT FILE DUMMIES VERWENDEN sondern TRUNK
				    	File[] fileaDirTemp = JarEasyZZZ.extractDirectoryToTemps(jar, entry,sTargetDirPath, bWithFiles);				    	
				    	if(fileaDirTemp!=null) {
				    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY - FILE OBJECTS FROM JARENTRY CREATED: '" + entry.getName() +"'";
					    	System.out.println(sLog);
					    	
					    	if(fileaDirTemp[0].exists()) {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT EXISTS AS: '" + fileaDirTemp[0].getAbsolutePath() +"'";
						    	System.out.println(sLog);
					    	}else {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT NOT(!) EXISTS AS: '" + fileaDirTemp[0].getAbsolutePath() +"'";
						    	System.out.println(sLog);
					    	}
					    	
					    	objReturn = fileaDirTemp[0]; 
					    		
			    		}else {
			    			sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY NOT CREATED '" + sTargetDirPath + "'";
					    	System.out.println(sLog);
			    		}
			    	
				    }else {
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS NOT A DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);

					   	File objReturnTrunk = JarEasyZZZ.extractFileFromJarAsTrunkFileDummy(jar, entry,sTargetDirPath);
				    	if(objReturnTrunk!=null) {
				    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT FROM JARENTRY CREATED: '" + entry.getName() +"'";
					    	System.out.println(sLog);

					    	objReturn = JarEasyZZZ.saveTrunkToFile(jar, entry, objReturnTrunk);
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
						    	
						    	
//						    	if(objReturnDummy==null){
//						    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILEDUMMY) IS NULL";
//							    	System.out.println(sLog);
//						    	}else {						    	
//							    	if(objReturnDummy.exists()) {
//							    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILEDUMMY) TRUNK OBJECT EXISTS AS: '" + objReturnDummy.getAbsolutePath() +"'";
//								    	System.out.println(sLog);
//							    	}else {
//							    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILEDUMMY) TRUNK OBJECT NOT(!) EXISTS AS: '" + objReturnDummy.getAbsolutePath() +"'";
//								    	System.out.println(sLog);
//							    	}
//						    	}
					    	}else {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT NOT PERSISTED AS: '" + objReturn.getAbsolutePath() +"'";
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
	
	
	/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
	 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
	 *  und das anschliessende speichern davon.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File searchResourceToTemp(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File objReturn = null;
			main:{
				objReturn = JarEasyInCurrentJarZZZ.searchResourceToTemp(sPath, sTargetDirectoryPathRootIn,true);
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
			bReturn = JarEasyUtilZZZ.isInJar(JarEasyInCurrentJarZZZ.class);
		}
		return bReturn;
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
					bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
				}
				return bReturn;
			}
}
