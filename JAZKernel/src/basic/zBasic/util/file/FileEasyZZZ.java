package basic.zBasic.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.KernelKernelZZZ;

/**Einfache Dateioperationen
 * @author lindhaueradmin
 *
 */
public class FileEasyZZZ extends ObjectZZZ{
	public static String sDIRECTORY_CURRENT = ".";
	public static String sDIRECTORY_PARENT = "..";
	public static String sFILE_ENDING_SEPARATOR = ".";
	public static String sFILE_ABSOLUT_REGEX="^[A-Za-z]:[\\\\/]"; //Merke: Um 1 Backslash auszukommentieren 4 verwenden zum ausmaskieren.
	public static String sFILE_VALID_WINDOWS_REGEX="^(?>[a-z]:)?(?>\\|/)?([^\\/?%*:|\"<>\r\n]+(?>\\|/)?)+$";
	
	public static String sDIRECTORY_CONFIG_SOURCEFOLDER="src";//Dient zur Unterscheidung, ob die Applikation auf deinem Server oder lokal läuft. Der Ordner ist auf dem Server nicht vorhanden (Voraussetzung)!!!
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
		bReturn = f.exists();
	}//end main:
	return bReturn;
} 

public static File getDirectory(String sDirectoryPath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(sDirectoryPath==null){ //Merke: Anders als bei einer Datei, darf der Directory-Name leer sein.
			ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//TODO GOON 20181014: InputStream inputStream = YourClass.class.getResourceAsStream(“file.txt”);
		//+++++++++
		if(FileEasyZZZ.isPathRelative(sDirectoryPath)){
			//Mit relativen Pfaden
			//Problematik: In der Entwicklungumgebung quasi die "Codebase" ermitteln oder dort wo der Code aufgerufen wird
			try {
				URL workspaceURL = new File(sDirectoryPath).toURI().toURL();
				String sWorkspaceURL = workspaceURL.getPath();					
				sWorkspaceURL = StringZZZ.stripRightFileSeparators(sWorkspaceURL);
				objReturn = new File(sWorkspaceURL);							
			} catch (MalformedURLException e) {	
				ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	    }else{
		   	//Absolute Pfadangabe
	    	objReturn = new File(sDirectoryPath);
		}	
	}//end main:
	return objReturn;
}

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
			try {
				URL workspaceURL = new File(sFilePath).toURI().toURL();
				objReturn = new File(workspaceURL.getPath());
								
			} catch (MalformedURLException e) {				
				e.printStackTrace();
			}
	    }else{
		   	//Absolute Pfadangabe
	    	objReturn = new File(sFilePath);
		}	
	}//end main:
	return objReturn;
}


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
		File objDirectoryNormed = FileEasyZZZ.searchDirectory(sDirectory);
		String sDirectoryNormed = objDirectoryNormed.getAbsolutePath();
		
		String sFileName = strFileName.get();
		
		objReturn = FileEasyZZZ.searchFile(sDirectoryNormed, sFileName);
		
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
		String sDirectory = null;
		
		//+++ 1. Versuch im Input-Pfad suchen (also unterhalb des Source - Folders, z.B. src.). Merke: Dort liegende Dateien sind dann auch per WebServer erreichbar, gepackt in ein .jar File.		
		objReturn = FileEasyZZZ.getFile(sDirectoryIn+File.separator+sFileName);
		if(objReturn.exists()) break main;
					
		//+++ 2. Versuch im Eclipse Workspace suchen +++++++		    
		File objDirectory = FileEasyZZZ.searchDirectory(FileEasyZZZ.sDIRECTORY_CURRENT); 
		if(objDirectory!=null){
			sDirectory = objDirectory.getPath();
			objReturn = FileEasyZZZ.getFile(sDirectory+File.separator+sFileName);
			if(objReturn.exists()) break main;
		}
	    
		//+++ 3. Versuch im Classpath suchen (also unterhalb des Source - Folders, z.B. src.). Merke: Dort liegende Dateien sind dann auch per WebServer erreichbar, gepackt in ein .jar File.		
		objDirectory = FileEasyZZZ.searchDirectory(FileEasyZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER); 
		if(objDirectory!=null){
			sDirectory = objDirectory.getPath();
			objReturn = FileEasyZZZ.getFile(sDirectory+File.separator+sFileName);
			if(objReturn.exists()) break main;
								
		}
		
		//+++ 4. Versuch (WebService) im Classpath suchen 
		//Problematik: Zugriff auf die Datei, wenn Sie in einem WAR File gepackt ist.
		//Ansatz, siehe: https://www.java-forum.org/thema/auf-dateien-im-war-zugreifen.157897/
	    //1. Sie muss unterhalb des Source Ordners liegen
	    //2. Über eine Ressource eine "Kopie" erzeugen....
		 try {
			 	String sDirectoryOnClasspath = sDirectoryIn;
			 	objReturn = File.createTempFile("temp", "ZZZ");					
				objReturn.deleteOnExit();
												
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
	
	//1. Aufteilen auf Datei und Verzeichnis
	objReturn = new File(sFilePath);
	String sDirectory = objReturn.getParent();
	if(sDirectory==null){ //ROOT		
		break main;
	}	
	
//	File objDirectoryNormed = FileEasyZZZ.searchDirectory(sDirectory);
//	String sDirectoryPathNormed = objDirectoryNormed.getAbsolutePath();
	String sFileName = objReturn.getName();
	
	//#### Die Rückgabewerte
	strDirectory.set(sDirectory);
	strFileName.set(sFileName);
	
	}//END main:
	return objReturn;	
}

public static File searchDirectory(String sDirectoryIn)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(sDirectoryIn==null){
			ExceptionZZZ ez  = new ExceptionZZZ("DirctoryName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}	
		String sDirectory = null;
		
		//+++ 1. Versuch im Classpath suchen (also unterhalb des Source - Folders, z.B. src.). Merke: Dort liegende Dateien sind dann auch per WebServer erreichbar, gepackt in ein .jar File.
		//if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CURRENT) | sDirectoryIn.equals("")){
		if(sDirectoryIn.equals("")| sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER)){
			sDirectory = FileEasyZZZ.getFileRootPath();	
		}else if(FileEasyZZZ.isPathRelative(sDirectoryIn)& !sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CURRENT)){
			sDirectory = FileEasyZZZ.getFileRootPath() + File.separator + sDirectoryIn;
		}else{
			sDirectory = sDirectoryIn;			
		}		
		objReturn = FileEasyZZZ.getDirectory(sDirectory);
		if(objReturn.exists()){
			break main;
		}else{
			//Pfad relativ zum Eclipse Workspace
			URL workspaceURL;
			try {
				workspaceURL = new File(sDirectoryIn).toURI().toURL();
				objReturn = new File(workspaceURL.getPath());
				if(objReturn.exists()){
					break main;
				}else{
					//Pfad relativ zum src - Ordner
					if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CURRENT)){
						sDirectory = FileEasyZZZ.getFileRootPath();
					}else{
						sDirectory = FileEasyZZZ.getFileRootPath() + File.separator + sDirectoryIn;
					}
					objReturn = new File(workspaceURL.getPath());
					if(objReturn.exists()){
						break main;
					}
				}
			} catch (MalformedURLException e) {
				ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}
		return null;
		
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
		 if ( sFilePathName.indexOf("\\") == 0 ) {
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
	
	
	public static boolean removeDirectory(String sDirectoryPath, boolean bEmptyDirectoryBefore) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile =  FileEasyZZZ.searchDirectory(sDirectoryPath); //Soll auch auf einem Web Server die passende Datei finden.
			if(objFile.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(bEmptyDirectoryBefore==true){
				//Hole alle dateien und läsche diese
				File[] objaFile =  objFile.listFiles();
				for(int icount = 0; icount <= objaFile.length - 1; icount++){
					objaFile[icount].delete();
				}		
				bReturn = objFile.delete(); //Das Verzeichnis sollte nun leer sein und kann dadurch gel�scht werden
			}else{			
				//Gibt false zurück, wenn z.B. das Directory nicht leer ist.
				bReturn = objFile.delete();
			}
		}
		return bReturn;
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
		
			sReturn = NameEndCompute(sFilenameTotal);
		
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
		
			sReturn = NameOnlyCompute(sFilenameTotal);
		
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
	
	
	/** Joins any Filepath-String with the Filename. E.g. in the case the filepath has an backslash at the end
	 * @param sFilePath
	 * @param sFileName
	 * @throws ExceptionZZZ 
	 */
	public static String joinFilePathName(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ{
		String sReturn = "";
		String stemp;
		String sFilePath; 	String sFileName; 
		
		check:{
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
			sFileName = sFileNameIn;
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
			sFilePath = sFilePathIn;
		}
		}//END Check
		
		
		main:{
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
					if(!stemp.equals("")){
							if(!sFilePathTemp.equals("")){
								sFilePathTemp = sFilePathTemp + File.separatorChar + stemp;
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
				sReturn = sFilePath + File.separatorChar + sFileName;
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
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Root-Pfad zurück.
	 * @param sFilePathRaw
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
			if(sFilePathRaw==null) break main;
			
			if(FileEasyZZZ.isPathRelative(sFilePathRaw)){
				if(sFilePathRaw.equals(".")){
					sReturn = FileEasyZZZ.getFileRootPath();		//Merke: Damit soll es sowohl auf einem WebServer als auch als Standalone Applikation funtkionieren.	
				}else{
					if(sFilePathRaw.equals("")){
						sReturn = FileEasyZZZ.getFileRootPath();
					}else{
						sReturn = FileEasyZZZ.getFileRootPath() +  File.separator + sFilePathRaw;
					}					
				}
			}else{
				sReturn = sFilePathRaw;
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
			
			//###################################
			StringTokenizer token = new StringTokenizer(sPathTotal, File.separator );
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
}//END class
