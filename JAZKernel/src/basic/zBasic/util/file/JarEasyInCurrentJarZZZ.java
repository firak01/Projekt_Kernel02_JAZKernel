package basic.zBasic.util.file;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Methoden für die Arbeit mit der gleichen Jar Datei, wenn die Methoden aus der Jar-Datei aufgerufen werden.
 * @author Fritz Lindhauer, 05.08.2020, 14:05:25
 * 
 */
public class JarEasyInCurrentJarZZZ  implements IConstantZZZ{

	public static File searchRessourceAsTempFile(String sPath) throws ExceptionZZZ {
			File objReturn = null;
			main:{
				if(StringZZZ.isEmpty(sPath)){
					ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sLog = null;
				final File jarFile = JarEasyZZZ.getJarCurrent(); //new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				if(jarFile.isFile()) {  // Run with JAR file
				    JarFile jar;
					try {
						jar = new JarFile(jarFile);
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
					    System.out.println(sLog);
					    String sPathInJar = JarEasyZZZ.toJarFilePath(sPath); 
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
					    	if(entry.isDirectory()) { //Dateien nicht extrahieren!!!
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY, WILL NOT BE EXTRACTED AS TEMP-FILE";
						    	System.out.println(sLog);
					    	}else {					    	
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

	public static File searchRessourceToTempAsTrunkFileDummy(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File objReturn = null;
			main:{
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
					    	if(entry.isDirectory()) { //Dateien nicht extrahieren!!!
					    		//TODOGOON; 20200805 
					    		//objReturn = JarEasyZZZ.extractDirectoryFromJarAsTrunkFileDummy(jar, entry,sTargetDirectoryPathRoot, false);
					    	}else {
					    		//TODOGOON; 20200805 
					    		//objReturn = JarEasyZZZ.extractFileFromJarAsTrunkFileDummy(jar, entry, sTargetDirectoryPathRoot);//JarEasyZZZ.extractFileFromJarAsTemp(jar, sPathInJar);
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
