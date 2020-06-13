package basic.zBasic.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class JarEasyZZZ  extends ObjectZZZ implements IResourceHandlingObjectZZZ{

	/**
	*  This method is responsible for extracting resource files from within the .jar to the temporary directory.
	*  @param filePath The filepath is the directory within the .jar from which to extract the file.
	*  @return A file object to the extracted file
	**/
	public static File extractFromJar(JarFile objJarFile, String sFilePath, boolean bIsDirectory) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			if(!bIsDirectory) {
				objReturn = JarEasyZZZ.extractFileFromJar(objJarFile, sFilePath);
			}else {
				objReturn = JarEasyZZZ.extractDirectoryFromJar(objJarFile, sFilePath);
			}
			}//end main:
			return objReturn;
	}
	
	public static File extractFileFromJar(JarFile objJarFile, String sFilePath) throws ExceptionZZZ {
			File objReturn=null;
			main:{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(StringZZZ.isEmpty(sFilePath)){
					ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}

				try{
			        File f = File.createTempFile(sFilePath, null);
			        FileOutputStream resourceOS = new FileOutputStream(f);
			        byte[] byteArray = new byte[1024];
			        int i;
			        //InputStream classIS = getClass().getClassLoader().getResourceAsStream("Resources/"+filePath);
			        InputStream classIS = JarEasyZZZ.class.getClassLoader().getResourceAsStream(sFilePath);
			//While the input stream has bytes
			        while ((i = classIS.read(byteArray)) > 0) 
			        {
			//Write the bytes to the output stream
			            resourceOS.write(byteArray, 0, i);
			        }
			//Close streams to prevent errors
			        classIS.close();
			        resourceOS.close();
			        objReturn = f;		    
				}catch (Exception e){
			    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			    }	    
				}//end main:
				return objReturn;
		}
	
	/**
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractDirectoryFromJar(JarFile objJarFile, String sFilePath) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			try{
				//https://stackoverflow.com/questions/8014099/how-do-i-convert-a-jarfile-uri-to-the-path-of-jar-file
				String sName = objJarFile.getName();
				
				String sUrl = "jar:file:/" + sName + "!/" + sFilePath;
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": String to fetch URL from JarFileObject '" + sUrl + "'" ;
			    System.out.println(sLog);			   
			    
				URL url = new URL(sUrl);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": URL created from JarFileObject '" + url + "'" ;
			    System.out.println(sLog);
			    
				JarURLConnection connection = (JarURLConnection) url.openConnection();
				objReturn = new File(connection.getJarFileURL().toURI());			    
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
			}//end main:
			return objReturn;
	}
	
	
	
	public static File getJarCurrent() throws ExceptionZZZ{
		File objReturn=null;
		try {
			String sPath = JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			objReturn = new File(sPath);
		} catch (URISyntaxException e) {
			ExceptionZZZ ez = new ExceptionZZZ("URISyntaxException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;			
		}
		return objReturn;
	}
	
	public static File getJarDirectoryCurrent() throws ExceptionZZZ{
		File objReturn = null;
		try {
			String sLog = null;
			if(JarEasyZZZ.isInJarStatic()){
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Running in a jar file.";
				System.out.println(sLog);
				
				String sPath = JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Current Directory of jar: '" + sPath + "'";
				System.out.println(sLog);
				
				objReturn = FileEasyZZZ.getDirectoryFromFilepath(sPath);
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Not running in a jar file.";
				System.out.println(sLog);
			}
		} catch (URISyntaxException e) {
			ExceptionZZZ ez = new ExceptionZZZ("URISyntaxException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;			
		}
		return objReturn;
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
		@Override
		public boolean isInJar() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = JarEasyZZZ.isInJar(this.getClass());
			}
			return bReturn;
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
				bReturn = JarEasyZZZ.isInJar(JarEasyZZZ.class);
			}
			return bReturn;
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
	
	public static File searchRessource(String sPath) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
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
				    	objReturn = JarEasyZZZ.extractFromJar(jar, sPathInJar, entry.isDirectory());
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
}

