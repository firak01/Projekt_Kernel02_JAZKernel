package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
				if(zf==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipFile Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(ze==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				String sDirPathRoot;
				if(StringZZZ.isEmpty(sDirPathRootIn)) {
					sDirPathRoot = EnvironmentZZZ.getHostDirectoryTemp();
				}else {
					sDirPathRoot = sDirPathRootIn;
				}
			
				//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)																
				InputStream is = zf.getInputStream(ze);
				
				String sKey = ze.getName();
				String sKeyNormed = StringZZZ.replace(sKey, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
				String sPath = FileEasyZZZ.joinFilePathName(sDirPathRoot, sKeyNormed);
				
				//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
				FileEasyZZZ.removeFile(sPath);
										
				Files.copy(is, Paths.get(sPath));
				objReturn = new File(sPath);
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;	
			}
			
		}//end main:
		return objReturn;
	}
}
