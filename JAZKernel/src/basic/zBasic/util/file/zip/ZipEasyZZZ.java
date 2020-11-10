package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class ZipEasyZZZ implements IConstantZZZ {
	public static File extractZipEntry(ZipFile zf, ZipEntry ze, String sDirPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			try {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBB) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				if(zf==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBB1->Error) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipFile Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(ze==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBB2->Error) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				String sDirPathRoot;
				if(StringZZZ.isEmpty(sDirPathRootIn)) {
					sDirPathRoot = EnvironmentZZZ.getHostDirectoryTemp();
				}else {
					sDirPathRoot = sDirPathRootIn;
				}
				
				String sKey = ze.getName();
				String sKeyNormed = StringZZZ.replace(sKey, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
				String sPath = FileEasyZZZ.joinFilePathName(sDirPathRoot, sKeyNormed);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": sPath='" + sPath + "'";
			   	System.out.println(sLog);
				
				if(ze.isDirectory()) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBBC) IS DIRECTORY.";
				   	System.out.println(sLog);
					
					FileEasyZZZ.createDirectory(sPath);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBBCA) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
					
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBBC) IS FILE.";
				   	System.out.println(sLog);
				   	
					//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)																
					InputStream is = zf.getInputStream(ze);
					
					//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
					FileEasyZZZ.removeFile(sPath);
					
					//!!! Notwendige Unterverzeichnisse wieder erstellen
					FileEasyZZZ.createDirectoryForFile(sPath);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBBCB) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);		
					
					Path target = Paths.get(sPath);
					Files.copy(is, target);							
				}
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBBD) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				objReturn = new File(sPath);
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBBE) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			
				
			} catch (IOException e) {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBB3->Error) IOException: '" + e.getMessage() + "' XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;	
			}
			
		}//end main:
		return objReturn;
	}
}
