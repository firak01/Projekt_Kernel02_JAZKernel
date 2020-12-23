package basic.zBasic.util.file;

import java.io.File;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

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
				String sLog = null;			    		
				JarFile jar = JarKernelZZZ.getJarFileCurrent();
				if(jar==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
				    System.out.println(sLog);
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
				    System.out.println(sLog);
				
				   objReturn = JarEasyZZZ.extractFileAsTemp(jar, sPath);
				}				
			}//end main:
			return objReturn;
		}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekDirectories(String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			String sLog = null;		
		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				objaReturn = JarEasyZZZ.peekDirectories(jar, sSourceDirectoryPath, sTargetDirectoryPathIn);
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
	public static File[] peekDirectories(String sSourceDirectoryPath, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			String sLog = null;		
		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				objaReturn = JarEasyZZZ.peekDirectories(jar, sSourceDirectoryPath, sTargetDirectoryPathIn, bWithFiles);
			}
		}//end main:
		return objaReturn;
	}
	
	public static File peekFileFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;			    		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
			    System.out.println(sLog);
						  
				objReturn = JarEasyZZZ.peekFileFirst(jar, sPath, sTargetDirectoryPathRootIn);				
			}
		}//end main:
		return objReturn;
	}
	
	public static File[] peekFiles(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{
			String sLog = null;			    		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
			    System.out.println(sLog);
						  
				objaReturn = JarEasyZZZ.peekFiles(jar, sPath, sTargetDirectoryPathRootIn);				
			}
		}//end main:
		return objaReturn;
	}
		
	public static File peekDirectoryFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;			    		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
			    System.out.println(sLog);
			
				objReturn = JarEasyZZZ.peekDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn);				
			}
		}//end main:
		return objReturn;
	}
	
	
	
	
	
	
	
	/** Man sucht hiermit die Datei, diese wird in ein  existieren.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File searchResourceDirectoryFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;			    		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
			    System.out.println(sLog);
			
				objReturn = JarEasyZZZ.searchResourceDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn);
				
			}
		}//end main:
		return objReturn;
		}
	
	/** Man sucht hiermit die Datei, diese wird in ein  existieren.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File searchResourceFileFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;			    		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
			    System.out.println(sLog);
			
				objReturn = JarEasyZZZ.searchResourceFileFirst(jar, sPath, sTargetDirectoryPathRootIn);
				
			}
		}//end main:
		return objReturn;
		}
	
	/** Man sucht hiermit die Datei, diese wird in ein  existieren.
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
	 */
	public static File[] searchResourceFiles(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{
			String sLog = null;			    		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
			    System.out.println(sLog);
			
				objaReturn = JarEasyZZZ.searchResourceFiles(jar, sPath, sTargetDirectoryPathRootIn);
				
			}
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
	public static File searchResourceDirectory(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;			    		
			JarFile jar = JarKernelZZZ.getJarFileCurrent();
			if(jar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
			    System.out.println(sLog);
			
				objReturn = JarEasyZZZ.searchResourceDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn, bWithFiles);
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
	public static File[] searchResources(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{
				String sLog = null;			    		
				JarFile jar = JarKernelZZZ.getJarFileCurrent();
				if(jar==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
				    System.out.println(sLog);
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
				    System.out.println(sLog);
				
				    objaReturn = JarEasyZZZ.searchResources(jar, sPath, sTargetDirectoryPathRootIn);
				}
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
				String sLog = null;			    		
				JarFile jar = JarKernelZZZ.getJarFileCurrent();
				if(jar==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE NOT FOUND.";
				    System.out.println(sLog);
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) JAR FILE FOUND.";
				    System.out.println(sLog);
				
				    objaReturn = JarEasyZZZ.searchResources(jar, sPath, sTargetDirectoryPathRootIn, bWithFiles);
				}
			}//end main:
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
