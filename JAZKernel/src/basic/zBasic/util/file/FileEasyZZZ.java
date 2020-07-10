package basic.zBasic.util.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.KernelKernelZZZ;
import basic.zKernel.file.ini.KernelExpressionIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelExpressionIni_NullZZZ;

/**Einfache Dateioperationen
 * @author lindhaueradmin
 *
 */
public class FileEasyZZZ extends ObjectZZZ{
	public static String sDIRECTORY_CURRENT = ".";
	public static String sDIRECTORY_PARENT = "..";
	public static String sDIRECTORY_SEPARATOR = File.separator; //z.B. Backslash in Windows

	public static String sFILE_ENDING_SEPARATOR = ".";
	public static String sFILE_ABSOLUT_REGEX="^[A-Za-z]:[\\\\/]"; //Merke: Um 1 Backslash auszukommentieren 4 verwenden zum ausmaskieren.
	public static String sFILE_VALID_WINDOWS_REGEX="^(?>[a-z]:)?(?>\\|/)?([^\\/?%*:|\"<>\r\n]+(?>\\|/)?)+$";
	
	
	
	public static String sDIRECTORY_CONFIG_SOURCEFOLDER="src";//Dient zur Unterscheidung, ob die Applikation auf deinem Server oder lokal läuft. Der Ordner ist auf dem Server nicht vorhanden (Voraussetzung)!!!
	public static String sDIRECTORY_CONFIG_TESTFOLDER="test";//FÜR DIE AUSFÜHRUNG VON TESTKLASSEN
private FileEasyZZZ(){
	//Zum Verstecken des Konstruktors
}

/** Überprüft, ob unter dem angegebenen pfadnamen "fileName" eine Datei existiert.
 * 
 * @param fileName
 * @return
 * @throws ExceptionZZZ 
 */
public static boolean exists (String sFileName) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFileName)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Prüfen, ob der Dateiname existiert oder nicht. Dabei wird ggf. auch ein relativer DateiPfad ber�cksichtig.
		File f = FileEasyZZZ.getFile(sFileName);
		if(f!=null) bReturn = f.exists();
	}//end main:
	return bReturn;
} 

/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File getDirectoryFromFilepath(String sFilePath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(sFilePath==null){ //Merke: Anders als bei einer Datei, darf der Directory-Name leer sein.
			ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		
		ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
    	FileEasyZZZ.splitFilePathName(sFilePath, strDirectory, strFileName);
    	String sDirectory=strDirectory.get();
		
    	objReturn = FileEasyZZZ.getDirectory(sDirectory);
    	
	}//end main:
	return objReturn;
}

/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File getDirectory(String sDirectoryPath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(sDirectoryPath==null){ //Merke: Anders als bei einer Datei, darf der Directory-Name leer sein.
			ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//+++++++++
		if(FileEasyZZZ.isPathRelative(sDirectoryPath)){
			//Mit relativen Pfaden
			//Problematik: In der Entwicklungumgebung quasi die "Codebase" ermitteln oder dort wo der Code aufgerufen wird			
			objReturn = FileEasyZZZ.getFileObjectInProjectPath(sDirectoryPath);						
	    }else{
		   	//Absolute Pfadangabe
	    	objReturn = FileEasyZZZ.getFileObject(sDirectoryPath);	
		}	
	}//end main:
	return objReturn;
}

/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File getFile(String sFilePath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//TODO GOON 20181014: InputStream inputStream = YourClass.class.getResourceAsStream(“file.txt”);
		//+++++++++
		if(FileEasyZZZ.isPathRelative(sFilePath)){
			//Mit relativen Pfaden
			//Problematik: In der Entwicklungumgebung quasi die "Codebase" ermitteln oder dort wo der Code aufgerufen wird							
			objReturn = FileEasyZZZ.getFileObjectInProjectPath(sFilePath);											
	    }else{
		   	//Absolute Pfadangabe
	    	//sFilePath = StringZZZ.stripRightFileSeparators(sFilePath);	//TODO GOON 20190212: Das müssten eigentlich ...MidFileSeperators(...) sein
	    	//objReturn = new File(sFilePath);
	    	
	    	objReturn = FileEasyZZZ.getFileObject(sFilePath);
	    	
		}	
	}//end main:
	return objReturn;
}

/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File getFile(String sDirectoryIn, String sFileName)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFileName)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	
		//Verzeichnis analysieren	
	    String sDirectory = FileEasyZZZ.sDIRECTORY_CURRENT;
		if(!StringZZZ.isEmpty(sDirectoryIn)){
			sDirectory=sDirectoryIn;	    
		}
		
		//+++++++++
		objReturn = FileEasyZZZ.getFile(sDirectory+File.separator+sFileName);

	}//END main:
	return objReturn;	
}

/**Gibt eine gesuchte Datei zurück. Ggfs. auch einen temporär erzeugte Datei, falls die andere nicht gefunden wird.
 * @param sFilePath
 * @param bTempFileAlternative
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sFilePath, boolean bTempFileAlternative) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Hier der Workaround mit Refenz-Objekten, aus denen dann der Wert geholt werden kann. Also PASS_BY_REFERENCE durch auslesen der Properties der Objekte.  
		ReferenceZZZ<String> strDirectory = new ReferenceZZZ("");
		ReferenceZZZ<String> strFileName = new ReferenceZZZ("");
		FileEasyZZZ.splitFilePathName(sFilePath, strDirectory, strFileName);
		
		String sDirectory = strDirectory.get();
		if(StringZZZ.isEmpty(sDirectory))sDirectory="."; //Lokal suchen.
		String sFileName = strFileName.get();
		
		objReturn = FileEasyZZZ.searchFile(sDirectory, sFileName);
		
	}//END main:
	return objReturn;
}

/**Gibt eine gesuchte Datei zurück. Ggfs. auch einen temporär erzeugte Datei, falls die andere nicht gefunden wird.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sFilePath) throws ExceptionZZZ{
	return FileEasyZZZ.searchFile(sFilePath, true);
}

/** Gibt eine gesuchte Datei zurück. Ggfs. auch einen temporär erzeugte Datei, falls die andere nicht gefunden wird. 
 * @param sDirectoryIn
 * @param sFileName
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sDirectoryIn, String sFileName)throws ExceptionZZZ{
	return FileEasyZZZ.searchFile(sDirectoryIn, sFileName, true);
}

/** Gibt eine gesuchte Datei zurück. Ggfs. auch einen temporär erzeugte Datei, falls die andere nicht gefunden wird. 
 * @param sDirectoryIn
 * @param sFileName
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sDirectoryIn, String sFileName, boolean bTempFileAlternative)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFileName)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}			
		
		//1. Prüfen, ob die Pfadangabe absolut ist:
		if(sDirectoryIn!=null){
			boolean bIsAbsolut = FileEasyZZZ.isPathAbsolut(sDirectoryIn);
			if(bIsAbsolut){
				//System.out.println(ReflectCodeZZZ.getPositionCurrent()+": (0) Path is absolut. '" + sDirectoryIn + "'");
				File objDirectoryNormed = FileEasyZZZ.searchDirectory(sDirectoryIn);
				if(objDirectoryNormed==null){
					String sLog = ReflectCodeZZZ.getPositionCurrent()+": (0) Directory does not exist. '" + sDirectoryIn + "'";
					System.out.println(sLog);				
					break main;
				}else{
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+": (0) Directory exists. '" + sDirectoryIn + "'");
				}
				
				
				//Versuch im Input-Pfad suchen (also unterhalb des Source - Folders, z.B. src.). Merke: Dort liegende Dateien sind dann auch per WebServer erreichbar, Aber NICHT gepackt in ein .jar File.
				String sDirectoryNormed = objDirectoryNormed.getAbsolutePath();
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+": (1) Normed Path for file is: '" + sDirectoryNormed+File.separator+sFileName + "'");
				objReturn = FileEasyZZZ.getFile(sDirectoryNormed+File.separator+sFileName);
				String sLog = null;
				if(objReturn==null){
					 sLog = ReflectCodeZZZ.getPositionCurrent()+": (1) File does not exist (=null). '" + sDirectoryNormed+File.separator+sFileName + "'";	
					 System.out.println(sLog);				     
				}else if(objReturn.exists()) {
					 sLog = ReflectCodeZZZ.getPositionCurrent()+": (1) File exists. '" + sDirectoryNormed+File.separator+sFileName + "'";	
					 System.out.println(sLog);
				     break main;
				}else{
				     sLog = ReflectCodeZZZ.getPositionCurrent()+": (1) File does not exist. '" + sDirectoryNormed+File.separator+sFileName + "'";
				     System.out.println(sLog);
				}
			}//end if isAbsolut
		}else{
			//4. Bei NULL: Versuch den Projektordner verwenden
			//System.out.println(ReflectCodeZZZ.getPositionCurrent()+": (2) Path is relativ. '" + sDirectoryIn + "'");
			String sDirectory = null;
			File objDirectory = FileEasyZZZ.searchDirectory(FileEasyZZZ.sDIRECTORY_CURRENT); 
			String sLog = null;
			if(objDirectory!=null){
				sDirectory = objDirectory.getPath();
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (2) Directory-Root exist for '.'='"+sDirectory+"'";
			    System.out.println(sLog);
				objReturn = FileEasyZZZ.getFile(sDirectory+File.separator+sFileName);
				if(objReturn!=null){
					if(objReturn.exists()) break main;
				}
			}else{
			     sLog = ReflectCodeZZZ.getPositionCurrent()+": (2) Directory-Root does not exist for '.'";
			     System.out.println(sLog);
			}
			
		}
		
		//2. Relative Pfadangaben per Classloader holen. Das Funktioniert dann auch in .jar-Dateien gepackt.		
		ClassLoader classLoader = FileEasyZZZ.class.getClassLoader();	
		URL fileURL = null;		
		String sFilePathTotal = null;
		String sLog = null;
		if(sDirectoryIn==null){
			sFilePathTotal = sFileName; 
		}else{
			sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectoryIn, sFileName);	
		}
		sLog = ReflectCodeZZZ.getPositionCurrent()+": (3) Searching for file '" + sFilePathTotal +"'";
	    System.out.println(sLog);
		objReturn = FileEasyZZZ.getFileObject(sFilePathTotal);
		if(objReturn!=null){
			if(objReturn.exists()) break main;
		}
			
				
		//3. Versuche den RootPfad zu verwenden
		String sRoot = FileEasyZZZ.getFileRootPath();
		if(sDirectoryIn==null){
			sFilePathTotal = sRoot + File.separator + sFileName; 
		}else{
			sFilePathTotal = FileEasyZZZ.joinFilePathName(sRoot + File.separator + sDirectoryIn, sFileName);	
		}
		sLog = ReflectCodeZZZ.getPositionCurrent()+": (4) Searching for file '" + sFilePathTotal +"'";
	    System.out.println(sLog);
		objReturn = FileEasyZZZ.getFileObjectInProjectPath(sFilePathTotal);
		if(objReturn!=null){
			if(objReturn.exists()) break main;
		}
		

		
		//+++ 4. Versuch (WebService) im Classpath suchen 
		//Problematik: Zugriff auf die Datei, wenn Sie in einem WAR File gepackt ist.
		//Ansatz, siehe: https://www.java-forum.org/thema/auf-dateien-im-war-zugreifen.157897/
	    //1. Sie muss unterhalb des Source Ordners liegen
	    //2. Über eine Ressource eine "Kopie" erzeugen....
		sLog = ReflectCodeZZZ.getPositionCurrent()+": (5) creating temp file.";
	    System.out.println(sLog);
		 try {
			 	String sDirectoryOnClasspath = sDirectoryIn;
			 	objReturn = FileEasyZZZ.createTempFile();
												
				InputStream resourceAsStream = FileEasyZZZ.class.getClassLoader().getResourceAsStream(sDirectoryOnClasspath + File.separator + sFileName);
				if(resourceAsStream != null){
					FileUtils.copyInputStreamToFile(resourceAsStream, objReturn);
					if(objReturn.exists()) break main;	
				}
		} catch (IOException e) {
				e.printStackTrace();
		}	
		 
		//Wenn man hierher kommt, wurde keine Datei gefunden. Falls keine temporäre Datei gewünscht wird, diese hier wieder entfernen.
		if(!bTempFileAlternative){
			objReturn = null;
		}
				 							
	}//END main:	
	return objReturn;	
}

public static File searchFilePath(String sFilePath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//1. Aufteilen auf Datei und Verzeichnis
		File objFile = new File(sFilePath);
		String sDirectory = objFile.getParent();
		if(sDirectory==null){ //ROOT
			objReturn = new File(sFilePath);
			break main;
		}	
		
		File objDirectoryNormed = FileEasyZZZ.searchDirectory(sDirectory);
		String sDirectoryPathNormed = objDirectoryNormed.getAbsolutePath();
		String sFileName = objFile.getName();
		
		String sFilePathNormedTotal = sDirectoryPathNormed + File.separator + sFileName;
		objReturn = new File(sFilePathNormedTotal);
		
	}//END main:
	return objReturn;
}

public static boolean searchIsDirectoryEmpty(String sDirectoryPath) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			File objFile = FileEasyZZZ.searchDirectory(sDirectoryPath);						
			if(objFile.exists()==false){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' does not exist (... on the web server).", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}				
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory (... on the web server).", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String[] saFile = objFile.list();
			if(saFile==null){
				bReturn = true;
			}else{
				if(saFile.length>=1){
					bReturn = false;
				}else{
					bReturn = true;
				}
			}
	}
	return bReturn;
}

public static File searchMakeDirectory(String sDirectoryPath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		File objDirectory = FileEasyZZZ.searchDirectory(sDirectoryPath);
		String sDirectoryPathNormed = objDirectory.getAbsolutePath();
		System.out.println(ReflectCodeZZZ.getPositionCurrent()+": Errechneter Pfad für das KernelLog='" + sDirectoryPathNormed +"'");
	
		FileEasyZZZ.makeDirectory(sDirectoryPathNormed);
        
		objReturn = new File(sDirectoryPathNormed);
	}//end main:
	return objReturn;
}

/*Rückgabewert ist das 'File-Objekt' vom Eingabe FilePath.
 * Darüber hinaus wird der Pfad aufgeteilt und zurückgegeben.
 * Da Java nur ein CALL_BY_VALUE machen kann, weden hier für die eingefüllten Werte Referenz-Objekte verwendet.
 */
public static File splitFilePathName(String sFilePath, ReferenceZZZ<String> strDirectory, ReferenceZZZ<String> strFileName) throws ExceptionZZZ{
	File objReturn = null;
	main:{
	if(StringZZZ.isEmpty(sFilePath)){
		ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}

	objReturn = new File(sFilePath);
	String sDirectory = "";
	if(sFilePath.contains(File.separator)){
		//Aufteilen auf Datei und Verzeichnis, nur wenn es einen Seperator im Pfad gibt, sonst ist es lediglich der Dateiname.
		sDirectory = objReturn.getParent();
		if(sDirectory==null){ //ROOT		
			break main;
		}	
	}

	String sFileName = objReturn.getName();
	
	//#### Die Rückgabewerte
	strDirectory.set(sDirectory);
	strFileName.set(sFileName);
	
	}//END main:
	return objReturn;	
}

public static File searchDirectory(String sDirectoryIn) throws ExceptionZZZ {
	File objReturn = null;
	main:{
		objReturn = FileEasyZZZ.searchDirectory(sDirectoryIn, false);//DER NORMALFALL IST ES ALSO IN EINER JAR DATEI ZU SUCHEN
		if(objReturn!=null)break main;				
	}//end main:
	return objReturn;
}
/** Eine NULL oder ".." Angabe bedeutet "Projekt-Ordner-Ebene".
 *  Ein Leerstring, Empty oder "." bedeutet "Root vom Classpath" (also: "src-Ordner-Ebene" bei Eclipse - Anwendung). 
 *  
 *  Merke: Wenn das Verzeichnis in einer .jar Datei liegt, kann man nur die JAR - Datei selbst zurückgeben.
 * @param sDirectoryIn
 * @return
 * @throws ExceptionZZZ
 * @author lindhaueradmin, 13.02.2019, 07:14:31
 */
public static File searchDirectory(String sDirectoryIn, boolean bNotInJar)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		String sDirectory = null;
		boolean bUseProjectBase=false;
		boolean bUseProjectBaseForTest=false;
		boolean bUseClasspathSource=false;
		if(sDirectoryIn==null){			
			bUseProjectBase=true;		
		}else if(sDirectoryIn.equals(KernelExpressionIni_NullZZZ.getExpressionTagEmpty())){		
			bUseProjectBase=true;			
		}else if(sDirectoryIn.equals("")){			
			bUseClasspathSource=true;
		}else if(sDirectoryIn.equals(KernelExpressionIni_EmptyZZZ.getExpressionTagEmpty())){			
			bUseClasspathSource=true;		
		}else if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CURRENT)){			
			bUseClasspathSource=true;			
		}else if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_PARENT)){
			bUseProjectBase=true;			
		}else if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CONFIG_TESTFOLDER)){
			bUseProjectBaseForTest=true;			
		}else{
			//+++ Der Normalfall
			sDirectory = sDirectoryIn;
		}
		
		//+++ Spezialfall (Null)
		if(bUseProjectBase){		
			sDirectory=sDirectoryIn;						
			objReturn = FileEasyZZZ.getFileObjectInProjectPath(sDirectory);
			if(objReturn!=null){
				if(objReturn.exists()) break main;
			}
		}
		
		//+++ Spezialfall ('test')
		if(bUseProjectBaseForTest){		
			sDirectory = FileEasyZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
			objReturn = FileEasyZZZ.getFileObjectInProjectPath(sDirectory);			
			if(objReturn!=null){
				if(objReturn.exists()) break main;
			}
		}
		
		//+++ Spezialfälle (Empty, ".")
		if(bUseClasspathSource){
			sDirectory = FileEasyZZZ.getFileRootPath();
			objReturn = FileEasyZZZ.getDirectory(sDirectory);			
			if(objReturn!=null){
				if(objReturn.exists()) break main;
			}
		}
		
		//+++ Normalfall			
			//+++ 1. Versuch im Classpath suchen (also unterhalb des Source - Folders, z.B. src.). Merke: Dort liegende Dateien sind dann auch per WebServer erreichbar, gepackt in ein .jar File.
			if(sDirectory.equals(FileEasyZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER)){
				sDirectory = FileEasyZZZ.getFileRootPath();
				objReturn = FileEasyZZZ.getDirectory(sDirectory);
			}else if(sDirectory.equals(FileEasyZZZ.sDIRECTORY_CONFIG_TESTFOLDER)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": TESTORDNER VERWENDET");
				sDirectory = FileEasyZZZ.getFileRootPath() + File.separator + sDirectory;	
				objReturn = FileEasyZZZ.getDirectory(sDirectory);
			}else if(FileEasyZZZ.isPathRelative(sDirectory)){
				sDirectory = FileEasyZZZ.getFileRootPath() + File.separator + sDirectory;	
				objReturn = FileEasyZZZ.getDirectory(sDirectory);
			}else{
				//Absolute Pfadangabe....
				objReturn = FileEasyZZZ.getDirectory(sDirectory);
			}	
			if(objReturn!=null){
				if(objReturn.exists()) break main;
			}
			
			//+++ 2. Versuch im Eclipse Workpace zu suchen.
			objReturn = FileEasyZZZ.getFileObjectInProjectPath(sDirectory);	
			if(objReturn!=null){
				if(objReturn.exists()) break main;
			}
			
			if(bNotInJar) break main;
			
			//##################################################
			//Suche nach dem Verzeichnis in einer JAR DAtei:
			objReturn = JarEasyZZZ.searchRessource(sDirectory);
			if(objReturn!=null){
				if(objReturn.exists()) break main;
			}
												
			//+++ Wenn noch nichts existiert NULL 
			objReturn = null;						
	}//END main:
	return objReturn;	
}


public static boolean searchExists(String sFilePath) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Prüfen, ob der Dateiname existiert oder nicht. Dabei wird ggf. auch ein relativer DateiPfad berücksichtig.
		File f = FileEasyZZZ.searchFile(sFilePath);
		bReturn = f.exists();
	}//end main:
	return bReturn;	
}


/** Pr�ft, ob der Pfad relativ ist: Das ist der Fall, wenn 
 * 
 *     A file name is relative to the current directory if it does not begin with one of the following:

        A UNC name of any format, which always start with two backslash characters ("\\"). For more information, see the next section.
        A disk designator with a backslash, for example "C:\" or "d:\".
        A single backslash, for example, "\directory" or "\file.txt". This is also referred to as an absolute path.


* @param sPath
* @return
* 
* lindhaueradmin; 04.11.2012 05:04:10
 */
public static boolean isPathRelative(String sFilePathName)throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(sFilePathName==null){ //Merke: Darf ein Leerstring sein.
			ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		bReturn = !FileEasyZZZ.isPathAbsolut(sFilePathName);
		
	}//end main:
	return bReturn;
}

public static  boolean isPathAbsolut(String sFilePathName)throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(sFilePathName==null){//Merke: Darf ein Leerstring sein.
			ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(sFilePathName.equals("")) break main; //Merke: Leerstring ist kein absoluter Pfad.
		
		Pattern p = Pattern.compile(FileEasyZZZ.sFILE_ABSOLUT_REGEX); 
		 Matcher m =p.matcher( sFilePathName ); 
		 bReturn =  m.find(); //Es geht nicht darum den ganzen Ausdruck, sondern nur einen teil zu finden: m.matches(); 	
		 if(bReturn) break main;
		 
		 //Wohl für Netzwerkpfade...
		 if ( sFilePathName.startsWith("\\\\")) {
			 bReturn = true;
			 break main;
		 }		                        
	}//end main:
	return bReturn;
}

	/** prüft ob ein gegebener dirName ein Directory repräsentiert und erzeugt gegebenenfalls ein solches dir */
	public static boolean makeDirectory (String dirName) {
		File d = new File (dirName);
		if (d.exists() && !d.isDirectory()) {
			return false;
		}
		if (d.exists() && d.isDirectory()) {
			return true;
		}
		return d.mkdirs ();
	}
	
	public static boolean removeFile(String sFilePathName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFilePathName)){
				ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = new File(sFilePathName);
			if(objFile.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFile.isFile()==false){
				ExceptionZZZ ez = new ExceptionZZZ("FilePathName='" + sFilePathName + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			bReturn = objFile.delete();
			
		}
		return bReturn;
}
	
	/** Löscht ein Verzeichnis.
	* @return boolean,	 true, wenn das Verzeichnis nicht existiert hat oder erfolgreich gel�scht werden konnte.
	*                					 false, wenn das Verzeichnis nicht leer ist.
	* @param sDirectoryPath
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 25.10.2006 09:38:28
	 */
	public static boolean searchRemoveDirectory(String sDirectoryPath) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = new File(sDirectoryPath);
			if(objFile.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Gibt false zur�ck, wenn z.B. das Directory nicht leer ist.
			bReturn = objFile.delete();
			
		}
		return bReturn;
	}
	
	
	public static boolean searchRemoveDirectory(String sDirectoryPath, boolean bEmptyDirectoryBefore) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = new File(sDirectoryPath);
			if(objFile.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(bEmptyDirectoryBefore==true){
				//Hole alle dateien und l�sche diese
				File[] objaFile =  objFile.listFiles();
				for(int icount = 0; icount <= objaFile.length - 1; icount++){
					objaFile[icount].delete();
				}		
				bReturn = objFile.delete(); //Das Verzeichnis sollte nun leer sein und kann dadurch gel�scht werden
			}else{			
				//Gibt false zur�ck, wenn z.B. das Directory nicht leer ist.
				bReturn = objFile.delete();
			}
		}
		return bReturn;
	}
	
	public static boolean searchRemoveFile(String sFilePathName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFilePathName)){
				ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//20190116: Sicherstellen, dass die Datei (auch auf einem WebServer) gefunden wird.
			//File objFile = new File(sFilePathName);
			File objFileNormed = FileEasyZZZ.searchFile(sFilePathName);						
			if(objFileNormed.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFileNormed.isFile()==false){
				ExceptionZZZ ez = new ExceptionZZZ("FilePathName='" + sFilePathName + "' is a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			bReturn = objFileNormed.delete();
			
		}
		return bReturn;
	}
	
	
	
	
	
	public static boolean isDirectoryEmpty(String sDirectoryPath) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
				if(StringZZZ.isEmpty(sDirectoryPath)){
					ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				File objFile = new File(sDirectoryPath);
				if(objFile.exists()==false){
					ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' does not exist.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
				if(objFile.isDirectory()==false){
					ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String[] saFile = objFile.list();
				if(saFile==null){
					bReturn = true;
				}else{
					if(saFile.length>=1){
						bReturn = false;
					}else{
						bReturn = true;
					}
				}
		}
		return bReturn;
	}
	
	/** Löscht ein Verzeichnis.
	* @return boolean,	 true, wenn das Verzeichnis nicht existiert hat oder erfolgreich gel�scht werden konnte.
	*                					 false, wenn das Verzeichnis nicht leer ist.
	* @param sDirectoryPath
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 25.10.2006 09:38:28
	 */
	public static boolean removeDirectory(String sDirectoryPath) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = FileEasyZZZ.searchDirectory(sDirectoryPath); //Soll auch auf einem Web Server die passende Datei finden.
			if(objFile.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory (... on the web server).", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Gibt false zurück, wenn z.B. das Directory nicht leer ist.
			bReturn = objFile.delete();
			
		}
		return bReturn;
	}
	
	/** Entferne das Verzeichnis. Wenn eine Datei übergeben wird, entferne das Elternverzeichnis. 
	 * @param objFileIn
	 * @param bEmptyDirectoryBefore
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectory(String sDirectoryPath, boolean bEmptyDirectoryBefore) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile =  FileEasyZZZ.searchDirectory(sDirectoryPath); //Soll auch auf einem Web Server die passende Datei finden.
			bReturn = FileEasyZZZ.removeDirectory(objFile, bEmptyDirectoryBefore);
		}
		return bReturn;
	}
	
	/** Entferne nur den Inhalt eines Verzeichnisses. Das Verzeichnis selbst bleibt besthen. 
	 *  Wird eine Datei übergeben, wird sie gelöscht, sofern sie alleine im Verzeichnis ist.
	 *  Löschen mehrerer Dateien des Parent-Verzeichnis nur  wenn bEmptyDirectoryContainingMoreFiles true ist.
	 * @param objFileIn
	 * @param bEmptyDirectoryContainingMoreFile    Sicherheitsflag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectoryContent(File objFileIn, boolean bEmptyDirectoryContainingMoreFiles) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileIn==null){
				ExceptionZZZ ez  = new ExceptionZZZ("File Object for DirectoryPath ", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Merke: Wenn kein Verzeichnis übergeben wurde, dann wird das Verzeichnis eben geholt.
			File objFileDirectory;
//			if(objFile.isDirectory()==false){
//				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}
			boolean bFileStart = false;
			if(objFileIn.isFile()) {
				bFileStart = true;
				objFileDirectory = objFileIn.getParentFile();
				if(objFileDirectory==null) break main;			
			}else {
				objFileDirectory = objFileIn;
			}
			if(objFileDirectory.exists()==false){
				bReturn = true;
				break main;
			}
			
			
			
				//Hole alle dateien und lösche diese ggfs.
				File[] objaFile =  objFileDirectory.listFiles();
				if(objaFile.length==1) {
					//Es ist ggfs. nur die Ausgangsdatei vorhanden, also löschen
					objaFile[0].delete();
					bReturn = true; //Merke: Das Verzeichnis selbst soll ja nicht gelöscht werden und andere sind nicht im Verzeichnis.
				}else {
					//Nur löschen, wenn explizit gesagt worden ist "alle Dateien" löschen
					if(bEmptyDirectoryContainingMoreFiles) {
						for(int icount = 0; icount <= objaFile.length - 1; icount++){
							objaFile[icount].delete();
						}		
						bReturn = true; //Merke: Das Verzeichnis selbst soll ja nicht gelöscht werden.
					}else {
						//Das Verzeichnis wird nicht geleert, darf also nicht gelöscht werden.
						ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + objFileDirectory.getAbsolutePath() + "' is not a single file containing directory. Call this method with the 'bEmptyDirectoryContainingMoreFiles' argument.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;						
					}
				}							
		}
		return bReturn;
	}
	
	/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
	 *  Darum wird hier per classloader die Ressource geholt.
	 * @param sFilePath
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String getNameFromFilepath(String sFilePath) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(sFilePath==null){ //Merke: Anders als bei einer Datei, darf der Directory-Name leer sein.
				ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
	    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
	    	FileEasyZZZ.splitFilePathName(sFilePath, strDirectory, strFileName);
	    	sReturn=strFileName.get();	    	
		}//end main:
		return sReturn;
	}
	
	public static String getNameEnd(File objFile) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			check:{
				if(objFile==null){
					 ExceptionZZZ ez = new ExceptionZZZ("Missing Fileobject parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END Check:
		
			String sFileName = objFile.getName();		
			sReturn = NameEndCompute(sFileName);
		
		return sReturn;
		}//END main:
	}
	
	public static String getNameEnd(String sFilenameTotal) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			check:{
				if(sFilenameTotal==null){
					 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END Check:
		
			String sFileName = FileEasyZZZ.getNameFromFilepath(sFilenameTotal);		
			sReturn = NameEndCompute(sFileName);
		
		return sReturn;
		}//END main:
	}
	
	public static String NameEndCompute(String sFileName){
		String sFunction = new String("");
		
		int iFileOnlyLength;
				
		main:{
					
		//Ermitteln der Dateinamensendung.
		iFileOnlyLength = sFileName.lastIndexOf(FileEasyZZZ.sFILE_ENDING_SEPARATOR);
		if(iFileOnlyLength > -1){
			sFunction = sFileName.substring(iFileOnlyLength + 1);
		}

		}
		
		end:{
			return sFunction;
		}		
	} // end function
	
	
	public static String getNameOnly(File objFile) throws ExceptionZZZ {
		String sReturn = new String("");
		main:{
			if(objFile == null) {
				 ExceptionZZZ ez = new ExceptionZZZ("Missing Fileobject as parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				  //doesn�t work. Only works when > JDK 1.4
				  //Exception e = new Exception();
				  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				  throw ez;
			}
			
			String sFilenameTotal = objFile.getAbsolutePath();
			sReturn = FileEasyZZZ.getNameOnly(sFilenameTotal);
		}//end main:
		return sReturn;
	}
	public static String getNameOnly(String sFilenameTotal) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			check:{
				if(sFilenameTotal==null){
					 ExceptionZZZ ez = new ExceptionZZZ("Missing Filepath parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END Check:
		
			
			String sFileName = FileEasyZZZ.getNameFromFilepath(sFilenameTotal);
		
			sReturn = NameOnlyCompute(sFileName);
		
		return sReturn;
		}//END main:
	}
	
	
	public static String NameOnlyCompute(String sFileName) throws ExceptionZZZ{
		String sFunction = new String("");
		
		main:{
			check:{
				if(sFileName==null){
				 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename Parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				  //doesn�t work. Only works when > JDK 1.4
				  //Exception e = new Exception();
				  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				  throw ez;		 
				}
			}//end check:
				
				
			int iEnding = getNameEnd(sFileName).length();
			if(iEnding > 0){
				sFunction = sFileName.substring(0,sFileName.length()-(iEnding + 1));
			}else{
				sFunction = sFileName;
			}
		}
		end:{
			return sFunction;
		}		
	}
	
	public static String getNameWithChangedEnd(String sFileName, String sEndIn) throws ExceptionZZZ{
		String sReturn = null;
		
			if(StringZZZ.isEmpty(sFileName)){
			 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename Parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			  //doesn�t work. Only works when > JDK 1.4
			  //Exception e = new Exception();
			  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			  throw ez;		 
			}
			
			String sEnd;
			if(sEndIn == null){
				sEnd = "";
			}else{
				sEnd = sEndIn;
			}

		
		//#########################################
		//Ermitteln der Dateinamensendung.
		int iFileOnlyLength = sFileName.lastIndexOf(FileEasyZZZ.sFILE_ENDING_SEPARATOR);
		if(iFileOnlyLength > -1){
			sReturn = StringZZZ.left(sFileName, iFileOnlyLength + 1);
			if(StringZZZ.isEmpty(sReturn)){
				//Dann ist irgendwas anders als geplant......
				sReturn = sFileName + FileEasyZZZ.sFILE_ENDING_SEPARATOR + sEnd;
			}else{
				sReturn = sReturn + sEnd;
			}
		}else{
			//Es gibt keinen Punkt im Dateinamen
			sReturn = sFileName + FileEasyZZZ.sFILE_ENDING_SEPARATOR + sEnd;
		}
		return sReturn;
	}
	


public static String getNameWithChangedSuffixKeptEnd(String sFileName, String sSuffix) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFileName)){
			 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename Parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			  //doesn�t work. Only works when > JDK 1.4
			  //Exception e = new Exception();
			  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			  throw ez;		 
			}
			
			if(StringZZZ.isEmpty(sSuffix)) break main;
			
			String sFileNameLeft = FileEasyZZZ.getNameOnly(sFileName);
			String sFileNameRight = FileEasyZZZ.getNameEnd(sFileName);
			
			sReturn = sFileNameLeft + sSuffix + FileEasyZZZ.sFILE_ENDING_SEPARATOR + sFileNameRight;
		
		}//end main:
		return sReturn;
	}

	public static String getParent(String sFilePathIn) throws ExceptionZZZ{
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
			sReturn = FileEasyZZZ.getParent(sFilePathIn, null);
		}//end main:
		return sReturn;
	}
	public static String getParent(String sFilePathIn, String sDirectorySeparatorUsedIn) {
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
			
			//An empty string is allowed
			if(StringZZZ.isEmpty(sFilePathIn)) break main;
			String sFilePath=StringZZZ.stripRightFileSeparators(sFilePathIn);			
			
			String sDirectorySeparatorUsed;
			if(StringZZZ.isEmpty(sDirectorySeparatorUsedIn)) {
				sDirectorySeparatorUsed = FileEasyZZZ.sDIRECTORY_SEPARATOR;
			}else {
				sDirectorySeparatorUsed = sDirectorySeparatorUsedIn;
			}
			
			
			StringTokenizer token = new StringTokenizer(sFilePath, sDirectorySeparatorUsed);
			while(token.hasMoreTokens()) {
				String stemp = (String) token.nextToken();			
				if(token.hasMoreTokens()) {
					if(sReturn.equals("")) {
						sReturn = stemp; //Merke: Es geht ja darum den "Vorletzten" Eintrag zu finden.
					}else {
						sReturn = sReturn + sDirectorySeparatorUsed + stemp;
					}
				}else {					
					break; //Merke: Es geht ja darum den "Vorletzten" Eintrag zu finden.
				}
				
					//Auskommentiert, weil in einer JAR-Datei alle Pfade des  ZipEntries mit "Slash" sind. Also wäre "\\" falsch extra anzuhängen.
//					if(!StringZZZ.isEmpty(stemp)) {
//						if(! stemp.endsWith(File.separator)){
//							stemp = stemp + File.separator;
//						}
//					}					
			}
			
		}//end main:
		return sReturn;
	}
	
	
	public static String joinFilePathName(File objFileForDirectory, String sFileNameIn) throws ExceptionZZZ{
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
			//An empty string is allowed
			if(objFileForDirectory==null){
				//here is the code throwing an ExceptionZZZ
				String stemp = " 'FileObject for directory'";
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
				   //doesn�t work. Only works when > JDK 1.4
				   //Exception e = new Exception();
				   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
				   throw ez;	
			}
			
			String sFilePath=objFileForDirectory.getAbsolutePath();
			String sDirectory;
			if(objFileForDirectory.isFile()) {
				File objFileDirectory = FileEasyZZZ.getDirectoryFromFilepath(sFilePath);
				sDirectory = objFileDirectory.getAbsolutePath();
			}else {
				sDirectory = sFilePath;
			}
			
			sReturn = FileEasyZZZ.joinFilePathName(sDirectory, sFileNameIn);
		
		}//end main:
		return sReturn;
	}

	/** Joins any Filepath-String with the Filename. E.g. in the case the filepath has an backslash at the end
	 * @param sFilePath
	 * @param sFileName
	 * @throws ExceptionZZZ 
	 */
	public static String joinFilePathName(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ{
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
		String stemp;
		String sFilePath; 	String sFileName; 
		
		//An empty string is allowed
		if(sFileNameIn==null){
			//here is the code throwing an ExceptionZZZ
			stemp = " 'FileName'";
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
			   //doesn�t work. Only works when > JDK 1.4
			   //Exception e = new Exception();
			   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
			   throw ez;	
		}else{
			sFileName = StringZZZ.stripLeftFileSeparators(sFileNameIn);
		}
		
		//An empty string is allowed
		if(sFilePathIn==null){
			//here is the code throwing an ExceptionZZZ
			stemp = "''FilePath'";
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
			   //doesn�t work. Only works when > JDK 1.4
			   //Exception e = new Exception();
			   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
			   throw ez;	
		}else{
			sFilePath = StringZZZ.stripRightFileSeparators(sFilePathIn);
		}
					
			StringTokenizer objToken;
			char[] caSep = new char[1];  //String has no constructor for a single char, but for an array
			caSep[0] = File.separatorChar;
			
			String sDelim = new String(caSep);
			
			//+++ Get the strings in the expected value for joining them
			if(!sFilePath.equals("")){
				objToken = new StringTokenizer(sFilePath, sDelim);
				
				//Eleminate empty strings
				String sFilePathTemp = new String("");
				int iNrOfTokenTotal = objToken.countTokens(); //Merke: Dies wird durch den Aufruf von .nextToken() immer ver�ndert/weniger. Darum vorher in einer Variablen sichern.
				for( int icount = 1;icount <= iNrOfTokenTotal; icount++){
					stemp = objToken.nextToken();									
					if(!stemp.equals("") && !stemp.equals(FileEasyZZZ.sDIRECTORY_SEPARATOR)){
							if(!sFilePathTemp.equals("")){
								sFilePathTemp = sFilePathTemp + FileEasyZZZ.sDIRECTORY_SEPARATOR + stemp;
							}else{
								sFilePathTemp = stemp;
							}
						}
					} //END for
				sFilePath = sFilePathTemp;
				}
			
			if(!sFileName.equals("")){
				objToken = new StringTokenizer(sFileName, sDelim);
				// Eleminate empty strings 
				
			}
			
			//+++  Join the strings
			//A)			
			if(sFilePath.equals("")){
				sReturn = sFileName;
			}else{
				//B)
				if(sFileName.equals("")){
				sReturn = sFilePath;	
				}else{
				//C)					
				sReturn = sFilePath + FileEasyZZZ.sDIRECTORY_SEPARATOR + sFileName;
				}
			}
			
		}//end main
		return sReturn;
	}
	
	

	/** Changes the ending of a filename. e.g. test.csv --> test.ini 
	 @param sFileName
	 @param sEndNew
	 @return the name with the new ending
	 */
	public static String NameEndChange(String sFileNameIn, String sEndNewIn)throws ExceptionZZZ{
	String sReturn = null;
	main:{
		String stemp; String sFileName;String sEndNew;
		if(sFileNameIn==null){
			//here is the code throwing an ExceptionZZZ
			stemp = "''FileName'";			   
			  ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
			   //doesn�t work. Only works when > JDK 1.4
			   //Exception e = new Exception();
			   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
			   throw ez;	
		}else if (sFileNameIn.equals("")){
				//	here is the code throwing an ExceptionZZZ
					  stemp = "'FileName'";
					  ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_EMPTY + stemp, iERROR_PARAMETER_EMPTY,  ReflectCodeZZZ.getMethodCurrentName(), "");
						 //doesn�t work. Only works when > JDK 1.4
						 //Exception e = new Exception();
						 //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
						 throw ez;	
		}else{
			sFileName = sFileNameIn;
		}
		
		if(sEndNewIn==null){
			sReturn = sFileName;
			break main;
		}else if(sEndNewIn.equals("")){
			sReturn = sFileName;
			break main;
		}else{
			sEndNew = sEndNewIn;
		}
		
		//#################
		String sFileOnly;
		int iFileOnlyLength = sFileName.lastIndexOf(FileEasyZZZ.sFILE_ENDING_SEPARATOR);
		if(iFileOnlyLength > -1){
			sFileOnly = sFileName.substring(0, iFileOnlyLength);   //.substring(iFileOnlyLength + 1);
		}else{
			sFileOnly = sFileName;
		}
		
		sReturn = sFileOnly + FileEasyZZZ.sFILE_ENDING_SEPARATOR + sEndNew;
			
	}//end main
	return sReturn;	
	}//end function
	
	
	public static String PathNameTotalCompute(String sDirectoryName, String sFileName){
		String sReturn = new String("");
		
		main:{
			sReturn = sDirectoryName + File.separator + sFileName;	
		}
		
		end:{
			return sReturn;	
		}
	} // end function
	
	
	/** Checks if a file-object is the root-directory.
	* @param file2proof
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 06.02.2007 11:06:26
	 */
	public static boolean isRoot(File file2proof) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(file2proof==null){
				ExceptionZZZ ez = new ExceptionZZZ("No file object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			//nein, das ist �berfl�ssig   if(file2proof.exists()==false) break main;
			if(file2proof.isDirectory()==false) break main;
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Ist Verzeichnis: '" + file2proof.getAbsolutePath() + "'");
			
			File fileParent = file2proof.getParentFile();
			if(fileParent == null) bReturn = true;
		}
		System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Return=" + bReturn) ;
		return bReturn;
	}
	
	/** Prüft, ob die Anwendung auf einem Webserver läuft oder im Eclipse Workspace.
	 *  Beim Eclipse Workspace wird vom Vorhandensein des "src" - Ordners ausgegangen.
	 *  Beim Serverlauf, gibt es den src-Ordner nicht. Er liegt dann auf dem Classpath. 
	 *  
	 *  Heuristische Lösung. 
	 *  Funktioniert so im Vergleich "Webservice" vs. "Swing Standalone in Eclipse"
	 * @return
	 */
	public static boolean isOnServer(){
		boolean bReturn = false;
		main:{
			File objFileTest = new File(FileEasyZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER);
			bReturn = !objFileTest.exists();			
		}//end main:
		return bReturn;
	}
	
	//### Ressourcen werden anders geholt, wenn die Klasse in einer JAR-Datei gepackt ist. Also:
		/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
		 *   Merke: Static Klassen müssen diese Methode selbst implementieren.
		 * @return
		 * @author lindhaueradmin, 21.02.2019
		 * @throws ExceptionZZZ 
		 */
		public static boolean isInJarStatic() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = JarEasyZZZ.isInJar(FileEasyZZZ.class);
			}
			return bReturn;
		}
		
		 /**
		   * Determine whether a file is a JAR File.
		   * Idee aus: http://www.java2s.com/Code/Java/File-Input-Output/DeterminewhetherafileisaJARFile.htm
		   */
		public static boolean isJar(File objFile) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(objFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No fileobject provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
								 
				try {
					//Merke: Wenn es kein ZipFile ist, so wird ein Fehler (IOException) geworfen. Dieses vorher prüfen und so die Exception vermeiden. 
					if(!FileEasyZZZ.isZip(objFile)) break main;
					
					ZipFile zip = new ZipFile(objFile);
					bReturn = zip.getEntry("META-INF/MANIFEST.MF") != null;
					zip.close();
				} catch (ZipException e) {
					ExceptionZZZ ez = new ExceptionZZZ("ZIPExpeption for '" + objFile.getAbsolutePath() + "' : " + e.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				} catch (IOException e) {
					ExceptionZZZ ez = new ExceptionZZZ("IOExpeption for '" + objFile.getAbsolutePath() + "' : "  + e.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				 								
			}//end main
			return bReturn;
		}
		
	
		  /**
		   * Determine whether a file is a ZIP File.
		   * Idee aus: http://www.java2s.com/Code/Java/File-Input-Output/DeterminewhetherafileisaJARFile.htm
		   */
		  public static boolean isZip(File objFile) throws ExceptionZZZ{
			  boolean bReturn = false;
				main:{
					if(objFile==null){
						ExceptionZZZ ez = new ExceptionZZZ("No fileobject provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
		      if(objFile.isDirectory())  break main;
		      
		      if(!objFile.canRead()) {
		    		ExceptionZZZ ez = new ExceptionZZZ("Cannot read file '" + objFile.getAbsolutePath() + "'", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
		      }

		      if(objFile.length() < 4)  break main;

		      try {
			      DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(objFile)));
			      int test = in.readInt();
			      in.close();
			      bReturn = (test == 0x504b0304);
		      } catch (IOException e) {
					ExceptionZZZ ez = new ExceptionZZZ("IOExpeption for '" + objFile.getAbsolutePath() + "' : "  + e.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			  }//end main;
			  return bReturn;
		  }
		
	/** Holt eine Datei / Ressource auch aus einer JAR-Datei.
	 *   Merke: In einer .jar Datei kann kein Zugriff über File-Objekte erfolgen.
	 *  
	 * @return
	 */
	public static File getFileObject(String sFile) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			//Problem: WAS MIT NULL!!!! Da hier nicht im Workspace gesucht wird, Fehler.
			if(sFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
			objReturn = getFileObject_(sFile);
		}//end main:
		return objReturn;			
		
	}
	
	/** Problematik: In der Entwicklungumgebung quasi die "Codebase" ermitteln oder dort wo der Code aufgerufen wird
	 * @param sFile
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static File getFileObjectInProjectPath(String sFile) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": Suche auf Projektebene.";
		    System.out.println(sLog);
		    
			//Problem: WAS MIT NULL!!!!
			//Lösungsidee 3: Merke: "." holt doch tatsächlich den Ordner des Projekts!!!
			if(sFile==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": NULL in '.' umgeändert.";
			    System.out.println(sLog);
				sFile=".";
			}
			objReturn = getFileObject_(sFile);
								
			//Lösungsidee 2:
			//20190215: Arbeite mit TEMP-Ordner
//			if(sFile==null){
//			try {
//				objReturn = File.createTempFile("temp", "ZZZ");						
//				objReturn.deleteOnExit();
//				} catch (IOException e1) {
//					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}	
//			}
			
			//Lösungsidee 1: 
			//20190215: Versuch des Zugriffs über den Eclipse Workspace
			//https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresInt_filesystem.htm
			//Nette Idee: Bleibt aber wohl der PluginEntwicklung vorbehalten.
			//Als Standalone bekomme ich folgenden Fehler: java.lang.NoClassDefFoundError: org/eclipse/osgi/util/NLS
			/*
			try {
			IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();									
			IProject myWebProject = myWorkspaceRoot.getProject("JAZKernel");
			   // open if necessary
			   if (myWebProject.exists() && !myWebProject.isOpen()) myWebProject.open(null);
			
			   IFolder imagesFolder = myWebProject.getFolder("images");
			   if (imagesFolder.exists()) {
			      // create a new file
			      IFile newLogo = imagesFolder.getFile("newLogo.png");
			      FileInputStream fileStream = new FileInputStream("c:/MyOtherData/newLogo.png");
			      newLogo.create(fileStream, false, null);
			      // create closes the file stream, so no worries.   
			   }

//Weitere Code Snippets
//			   IFolder newImagesFolder = myWebProject.getFolder("newimages");
//			   newImagesFolder.create(false, true, null);
//			   IPath renamedPath = newImagesFolder.getFullPath().append("renamedLogo.png");
//			   newLogo.move(renamedPath, false, null);
//			   IFile renamedLogo = newImagesFolder.getFile("renamedLogo.png");
			   
				} catch (CoreException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
			
			
				
		}//end main:
		return objReturn;			
	}
	
	private static File getFileObject_(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			ClassLoader classLoader = FileEasyZZZ.class.getClassLoader();
			URL workspaceURL = null; String sLog=null;
			
			//1. Versuch
			try {		
				workspaceURL = new File(sPath).toURI().toURL();
				if(workspaceURL!=null){	
					String sWorkspaceURL = workspaceURL.getPath();					
					sWorkspaceURL = StringZZZ.stripRightFileSeparators(sWorkspaceURL);
					objReturn = new File(sWorkspaceURL);	
					if(objReturn.exists()) {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (A1) Datei gefunden '" + sPath + "'";
					    System.out.println(sLog);
						break main;
					}else{
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (A1) Datei nicht gefunden '" + sPath + "'";
					    System.out.println(sLog);
					}
				}else{
//					sLog = ReflectCodeZZZ.getPositionCurrent()+": (A1) Objekt null für '" + sPath + "'";
//				    System.out.println(sLog);
				}
			} catch (MalformedURLException e) {	
				ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Wenn in einer jar Datei ausgeführt wird, das lokale Verzeichnis der jar holen.
			//Ziel ist es, dass eine lokale Datei immer die Datei im .jar übersteuert.			
			if(JarEasyZZZ.isInJarStatic()){
			File objTest = JarEasyZZZ.getJarDirectoryCurrent();			
			if(objTest!=null){
				String sPathJarDirectoryCurrent = objTest.getAbsolutePath();
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (X1) JarDirectoryCurrent= '" + sPathJarDirectoryCurrent +"'";
			    System.out.println(sLog);
			    
			    if(FileEasyZZZ.isPathRelative(sPath)){
					String sPathDirectory = objTest.getAbsolutePath();
					String sPathTotal = FileEasyZZZ.joinFilePathName(sPathDirectory, sPath);
					//TODO GOON 20190227 Das Kapseln in private Methode: getFileObjectFromFileSystem(..) also das was hier A1 und X1 ist....
					
					//1. Versuch
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (X1) Searching for file be File .toURI().toURL()  '" + sPathTotal +"'";
				    System.out.println(sLog);
					try {		
						workspaceURL = new File(sPathTotal).toURI().toURL();
						if(workspaceURL!=null){	
//							sLog = ReflectCodeZZZ.getPositionCurrent()+": (X1) Objekt nicht null für '" + sPathTotal + "'";
//						    System.out.println(sLog);
							String sWorkspaceURL = workspaceURL.getPath();					
							sWorkspaceURL = StringZZZ.stripRightFileSeparators(sWorkspaceURL);
							objReturn = new File(sWorkspaceURL);	
							if(objReturn.exists()) {
								sLog = ReflectCodeZZZ.getPositionCurrent()+": (X1) Datei gefunden '" + sPathTotal + "'";
							    System.out.println(sLog);
								break main;
							}else{
								sLog = ReflectCodeZZZ.getPositionCurrent()+": (X1) Datei nicht gefunden '" + sPathTotal + "'";
							    System.out.println(sLog);
							}
						}else{
//							sLog = ReflectCodeZZZ.getPositionCurrent()+": (X1) Objekt null für '" + sPathTotal + "'";
//						    System.out.println(sLog);
						}
					} catch (MalformedURLException e) {	
						ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					
					//+++++++++
				}
			}else{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": (X1) Searching for file by classloader.getResource '" + sPath +"'";
//			    System.out.println(sLog);
			}}
				
			//2a. Versuch (z.B. für innerhalb einer .jar Datei.
			sLog = ReflectCodeZZZ.getPositionCurrent()+": (B1) Searching for file by classloader.getResource '" + sPath +"'";
		    System.out.println(sLog);
			objReturn = ResourceEasyZZZ.doClassloaderGetResource(sPath);
			if(objReturn!=null){			
				if(objReturn.exists()) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (B1) Datei gefunden '" + sPath + "'";
				    System.out.println(sLog);
					break main;
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (B1) Datei NICHT gefunden '" + sPath + "'";
				    System.out.println(sLog);					
				}
			}else{			
			}
			
			
			//2b. Versuch (z.B. für innerhalb einer .jar Datei.
			sLog = ReflectCodeZZZ.getPositionCurrent()+": (B2) Searching for file by class.getResource '" + sPath +"'";
		    System.out.println(sLog);
			objReturn = ResourceEasyZZZ.doClassGetResource(sPath);
			if(objReturn!=null){						    
				if(objReturn.exists()) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (B2) Datei gefunden '" + sPath + "'";
				    System.out.println(sLog);
					break main;
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (B2) Datei NICHT gefunden '" + sPath + "'";
				    System.out.println(sLog);					
				}
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (B2) Objekt null für '" + sPath + "'";
			    System.out.println(sLog);			
			}
			

//			workspaceURL = classLoader.getResource(sPath);
//			if(workspaceURL!=null){
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": (B) URL is not null'";
//			    System.out.println(sLog);
//				try {			
//					objReturn = new File(workspaceURL.toURI());
//					if(objReturn.exists()){
//						sLog = ReflectCodeZZZ.getPositionCurrent()+": (B) Datei gefunden '" + sPath + "'";
//					    System.out.println(sLog);
//						break main;
//					}
//				} catch(URISyntaxException e) {
//					objReturn = new File(workspaceURL.getPath());
//					if(objReturn.exists()){
//						sLog = ReflectCodeZZZ.getPositionCurrent()+": (B) Datei gefunden '" + sPath + "'";
//					    System.out.println(sLog);
//						break main;
//					}
//				}
//			}else{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": (B) URL is null'";
//			    System.out.println(sLog);
//			}
//			
//			//3. Versuch (z.B. für innerhalb einer .jar Datei.	
//			sLog = ReflectCodeZZZ.getPositionCurrent()+": (C) Searching for file by classloader.getResource '" + File.separator +sPath +"'";
//		    System.out.println(sLog);
//			workspaceURL = classLoader.getResource(File.separator+sPath);
//			if(workspaceURL!=null){
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": (C) URL is not null'";
//			    System.out.println(sLog);
//				try {			
//					objReturn = new File(workspaceURL.toURI());
//					if(objReturn.exists()) {
//						sLog = ReflectCodeZZZ.getPositionCurrent()+": (C) Datei gefunden '" + sPath + "'";
//					    System.out.println(sLog);
//						break main;
//					}
//				} catch(URISyntaxException e) {
//					objReturn = new File(workspaceURL.getPath());
//					if(objReturn.exists()) {
//						sLog = ReflectCodeZZZ.getPositionCurrent()+": (C) Datei gefunden '" + sPath + "'";
//					    System.out.println(sLog);
//						break main;
//					}
//				}
//			}else{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": (C) URL is null'";
//			    System.out.println(sLog);
//			}
			
			//Hole die Ressource unter der Annahme, das sie in dem aktuellen, gleichen JAR-File wie diese Klasse steckt.
			if(FileEasyZZZ.isInJarStatic()){
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Searching for file by JarFile-Class '" + sPath +"'";
			    System.out.println(sLog);
				objReturn = JarEasyZZZ.searchRessource(sPath);
				if(objReturn!=null){
					if(objReturn.exists()) {
						if(objReturn.length()>0){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) file in jar found '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}
					}else{
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) file in jar not found '" + sPath + "'";
					    System.out.println(sLog);
					}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) file not a jar or ressourc not in jar found '" + sPath + "'";
				    System.out.println(sLog);
				}
			}else{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Not a JarFile-Class. Not searching in any jar File for '" + sPath +"'";
			    System.out.println(sLog);
			}
			
					
			//6. Versuch (beim "." wird ggfs. das bin - - Verzeichnis zurükgegeben. Dies entfernen.
			if(workspaceURL!=null){
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) Searching for file by classloader.getResource '" + sPath +"', stripping bin - Directory";
			    System.out.println(sLog);
				String sPathInWorkspace = workspaceURL.getPath();
				String[] saStringsToBeStripped ={"bin",File.separator};
				String sParthNormed = StringZZZ.stripRight(sPathInWorkspace, saStringsToBeStripped);
				workspaceURL = classLoader.getResource(sParthNormed);
				if(workspaceURL!=null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) URL is not null'";
				    System.out.println(sLog);
					try {			
						objReturn = new File(workspaceURL.toURI());
						if(objReturn.exists()) break main;
					} catch(URISyntaxException e) {
						objReturn = new File(workspaceURL.getPath());
						if(objReturn.exists()) break main;
					}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) URL is null'";
				    System.out.println(sLog);
				}
			}
		}
		return objReturn;
	}
	
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Root-Pfad zurück.
	 * @return
	 */
	public static String getFileRootPath(){
		String sReturn = "";
		main:{
			if(isOnServer()){
				sReturn = "";
			}else{
				sReturn = FileEasyZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
			}
		}//end main:
		return sReturn;
	}
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Pfad zurück.
	 * @param sFilePathRaw
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getFileUsedPath(String sFilePathRaw) throws ExceptionZZZ{
		String sReturn=null;
		main:{
			//if(sFilePathRaw==null) break main;
			if(sFilePathRaw==null){
				File objReturn = FileEasyZZZ.getFileObjectInProjectPath(null);
				sReturn = objReturn.getAbsolutePath();
			}else if(sFilePathRaw.equals(KernelExpressionIni_NullZZZ.getExpressionTagEmpty())){
				File objReturn = FileEasyZZZ.getFileObjectInProjectPath(null);
				sReturn = objReturn.getAbsolutePath();
			}else if(sFilePathRaw.equals(KernelExpressionIni_EmptyZZZ.getExpressionTagEmpty()) || sFilePathRaw.equals("")){
				sReturn = FileEasyZZZ.getFileRootPath();		//Merke: Damit soll es sowohl auf einem WebServer als auch als Standalone Applikation funtkionieren.
			}else if(sFilePathRaw.equals(FileEasyZZZ.sDIRECTORY_CURRENT)){
				sReturn = FileEasyZZZ.getFileRootPath();		//Merke: Damit soll es sowohl auf einem WebServer als auch als Standalone Applikation funtkionieren.
			}else if (sFilePathRaw.equals(FileEasyZZZ.sDIRECTORY_CONFIG_TESTFOLDER)){
				File objReturn = FileEasyZZZ.getFileObjectInProjectPath(null);
				sReturn = objReturn.getAbsolutePath() + File.separator + sFilePathRaw;
			}else{
				if(FileEasyZZZ.isPathRelative(sFilePathRaw)){				               				
					sReturn = FileEasyZZZ.getFileRootPath() +  File.separator + sFilePathRaw;
				}else{
					sReturn = sFilePathRaw;
				}
			}
		}//end main
		return sReturn;
	}
	
	/** Returns the root-string of a file.
	 *   if the file-object does not have a path, then null will be returned.
	 *   Format will be: e.g. c:    
	 *   Ergo: No File.Seperator at the end.
	 *   
	* @param file2read
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 08.02.2007 12:28:09
	 */
	public static String getRoot(File file2read) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(file2read==null){
				ExceptionZZZ ez = new ExceptionZZZ("No file object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sPathTotal = file2read.getAbsolutePath();
			if(StringZZZ.isEmpty(sPathTotal)) break main;
			
			sReturn = FileEasyZZZ.getRoot(sPathTotal);			
		}
		return sReturn;
	}
	
	public static String getRoot(String sFilePath) {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)) break main;
			
			StringTokenizer token = new StringTokenizer(sFilePath, File.separator );
			sReturn = (String) token.nextElement();
			if(! sReturn.endsWith(File.separator)){
				sReturn = sReturn + File.separator;
			}
		}
		return sReturn;
	}
	
	public static void copyFile(File src, File dest, int bufSize,  boolean force) throws ExceptionZZZ {
		main:{
		try{
	
		    if(dest.exists()) {
		        if(force) {
		            dest.delete();
		        } else {
		            throw new IOException("Cannot overwrite existing file: " + dest.getPath());
		        }
		    }
		    byte[] buffer = new byte[bufSize];
		    int read = 0;
		    InputStream in = null;
		    OutputStream out = null;
		    try {
		        in = new FileInputStream(src);
		        out = new FileOutputStream(dest);
		        while(true) {
		            read = in.read(buffer);
		            if (read == -1) {
		                //-1 bedeutet EOF
		                break;
		            }
		            out.write(buffer, 0, read);
		        }
		    } finally {
		        // Sicherstellen, dass die Streams auch
		        // bei einem throw geschlossen werden.
		        // Falls in null ist, ist out auch null!
		        if (in != null) {
		            //Falls tats�chlich in.close() und out.close()
		            //Exceptions werfen, die jenige von 'out' geworfen wird.
		            try {
		                in.close();
		            }
		            finally {
		                if (out != null) {
		                    out.close();
		                }
		            }
		        }
		    }
	    
		}catch(IOException ioe){
			ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;
		}
		}//End main
	}
	
	public static File createTempFile() throws ExceptionZZZ{
		File objReturn = null;
		main:{
			try {
				objReturn = File.createTempFile("temp", "ZZZ");
				objReturn.deleteOnExit();
			} catch (IOException ioe) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}					
			
		}
		return objReturn;
	}
	
	public static boolean  createDirectory(String sDirectoryName) throws ExceptionZZZ{
		return FileEasyZZZ.makeDirectory(sDirectoryName);
	}

	/** Entferne das Verzeichnis. Wenn eine Datei übergeben wird, entferne das Elternverzeichnis. 
		 * @param objFileIn
		 * @param bEmptyDirectoryBefore
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
		 */
		public static boolean removeDirectory(File objFileIn, boolean bEmptyDirectoryBefore) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(objFileIn==null){
					ExceptionZZZ ez  = new ExceptionZZZ("File Object for DirectoryPath ", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//Merke: Wenn kein Verzeichnis übergeben wurde, dann wird das Verzeichnis eben geholt.
				File objFileDirectory;
	//			if(objFile.isDirectory()==false){
	//				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
	//				throw ez;
	//			}
				boolean bFileStart = false;
				if(objFileIn.isFile()) {
					bFileStart = true;
					objFileDirectory = objFileIn.getParentFile();
					if(objFileDirectory==null) break main;			
				}else {
					objFileDirectory = objFileIn;
				}
				if(objFileDirectory.exists()==false){
					bReturn = true;
					break main;
				}
				if(FileEasyZZZ.isRoot(objFileDirectory)) break main;
				
				if(bEmptyDirectoryBefore || bFileStart){
					//Hole alle dateien und lösche diese ggfs.
					File[] objaFile =  objFileDirectory.listFiles();
					if(objaFile.length==1) {
						//Es ist nur die Ausgangsdatei vorhanden, also löschen
						objaFile[0].delete();
						bReturn = objFileDirectory.delete(); //Das Verzeichnis sollte nun leer sein und kann dadurch gel�scht werden
					}else {
						//Nur löschen, wenn explizit gesagt worden ist "alle Dateien" löschen
						if(bEmptyDirectoryBefore) {
							for(int icount = 0; icount <= objaFile.length - 1; icount++){
								objaFile[icount].delete();
							}		
							bReturn = objFileDirectory.delete(); //Das Verzeichnis sollte nun leer sein und kann dadurch gel�scht werden
						}else {
							//Das Verzeichnis wird nicht geleert, darf also nicht gelöscht werden.
							ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + objFileDirectory.getAbsolutePath() + "' is not an empty directory. Call this method with the 'emptyDirectoryBefore=true' argument.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;						
						}
					}				
				}else{			
					//Gibt false zurück, wenn z.B. das Directory nicht leer ist.
					bReturn = objFileDirectory.delete();
				}
			}
			return bReturn;
		}
	


}//END class
