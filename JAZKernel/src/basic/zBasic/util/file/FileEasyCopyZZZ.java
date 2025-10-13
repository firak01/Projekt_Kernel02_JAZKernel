package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.KernelZZZTest;

public class FileEasyCopyZZZ extends AbstractObjectWithExceptionZZZ implements IFileEasyConstantsZZZ{
	public static File copyFile(String sDirectorySourceIn, String sFilenameSourceIn, String sDirectoryTargetIn, String sFilenameTargetIn, boolean bOverwrite) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			try {
				String sFileDirectorySourceUsed = sDirectorySourceIn;
				sFileDirectorySourceUsed = FileEasyZZZ.getFileUsedPath(sFileDirectorySourceUsed);
				if(!FileEasyZZZ.exists(sFileDirectorySourceUsed)){
					String sLog = "Directory does not exists sFileDirectorySourceUsed='"+ sFileDirectorySourceUsed +"'";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
							
				String sFileNameSourceUsed = null;
				if(StringZZZ.isEmpty(sFilenameSourceIn)){
					String sLog = "Missing sFilenameSourceIn";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_EMPTY, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					sFileNameSourceUsed = sFilenameSourceIn;
				}
				
				boolean bSameDirectory=false;
				String sFileDirectoryTargetUsed = null;
				if(StringZZZ.isEmpty(sDirectoryTargetIn)){
					String sLog = "Missing sDirectoryTargetIn, using sourcedirectory as targetdirectory.";
					ObjectZZZ.logLineWithDate(sLog);
					
					bSameDirectory = true;
					sFileDirectoryTargetUsed = sFileDirectorySourceUsed;
				}else {								
					sFileDirectoryTargetUsed = sDirectoryTargetIn;				
					sFileDirectoryTargetUsed = FileEasyZZZ.getFileUsedPath(sFileDirectoryTargetUsed);
					boolean bDirectoryAvailable;
					if(!FileEasyZZZ.exists(sFileDirectoryTargetUsed)){
						bDirectoryAvailable = FileEasyZZZ.createDirectoryForDirectory(sFileDirectoryTargetUsed);
					}else {
						bDirectoryAvailable=true;
					}
					if(!bDirectoryAvailable){
						String sLog = "Directory does not exists and unable to create directory sFileDirectoryTargetUsed='"+ sFileDirectoryTargetUsed +"'";
						ObjectZZZ.logLineWithDate(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
	
				String sFileNameTargetUsed = null;
				if(StringZZZ.isEmpty(sFilenameTargetIn)){
					if(bSameDirectory) {
						String sLog = "Missing sFilenameTargetIn, using same directory.";
						ObjectZZZ.logLineWithDate(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_EMPTY, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else {
						String sLog = "Missing sFilenameTargetIn, using same filename like in source.";
						ObjectZZZ.logLineWithDate(sLog);
						
						sFileNameTargetUsed = sFileNameSourceUsed;
					}
				}else {
					sFileNameTargetUsed = sFilenameTargetIn;
				}
		
				//#############################################
				//Erst einmal die Pfade ausrechnen
				sFileDirectorySourceUsed = FileEasyZZZ.getFileUsedPath(sFileDirectorySourceUsed);
				if(!FileEasyZZZ.exists(sFileDirectorySourceUsed)){
					FileEasyZZZ.createDirectoryForDirectory(sFileDirectorySourceUsed);
				}
				String sFilePathTotalSource = FileEasyZZZ.joinFilePathName(sFileDirectorySourceUsed, sFileNameSourceUsed );				
		//			if(sFilePathTotal==null){
		//				//Eclipse Workspace
		//				File f = new File("");
		//			    String sPathEclipse = f.getAbsolutePath();
		//			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
		//			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
		//			}
				
							
				String sFilePathTotalTarget = FileEasyZZZ.joinFilePathName(sFileDirectoryTargetUsed, sFileNameSourceUsed );
		//		if(sFilePathTotal==null){
		//			//Eclipse Workspace
		//			File f = new File("");
		//		    String sPathEclipse = f.getAbsolutePath();
		//		    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
		//		    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
		//		}
				
				if(sFilePathTotalSource.equals(sFilePathTotalTarget)) {				
					String sLog = "Ziel und Quellpfad identisch:'" + sFilePathTotalSource + "'";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_EMPTY, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				
				//### Nun die Streams erstellen
				IStreamZZZ objStreamFileSource = null;
				try{
					objStreamFileSource = new StreamZZZ(sFilePathTotalSource, 0); //0 = read the file			
				} catch (FileNotFoundException e) {				
					String sLog = "Quelldatei nicht gefunden:'" + sFilePathTotalSource + "'";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				} catch (Exception e) {
					e.printStackTrace();
				} 
					
					
				IStreamZZZ objStreamFileTarget = null;
				try{
					objStreamFileTarget = new StreamZZZ(sFilePathTotalTarget, 1);  // 1 = write to the file		
				} catch (FileNotFoundException e) {
					String sLog = "Zieldatei nicht gefunden:'" + sFilePathTotalTarget + "'";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				} catch (Exception e) {
					e.printStackTrace();
				} 
				
				//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehängt. 
				//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
				//Merke: Damit die Datei nicht im Code-Repository landet, wird sie explizit immer auf der lokalen Festplatte erzeugt.
				//       ALSO NICHT im Eclipse Workspace
				//Kopiere aus der bestehenden Datei in die JUnitDatei
				String sLine; boolean bGoon=true;
				do {
					sLine = objStreamFileSource.readLineNext();
					if(sLine!=null) {
						objStreamFileTarget.println(sLine);
					}else {
						bGoon = false;
					}
				}while(bGoon);
							
				objStreamFileTarget.close();
				objStreamFileSource.close();
				
				objReturn = new File(sFilePathTotalTarget);
			} catch (IOException e) {			
				e.printStackTrace();
				ExceptionZZZ ez = new ExceptionZZZ("IOException", e);
				throw ez;
			} 
		}//end main:
		return objReturn;
	}
	
	
	public static File copyFileByBatch(String sDirectorySourceIn, String sFilenameSourceIn, String sDirectoryTargetIn, String sFilenameTargetIn, boolean bOverwrite) throws ExceptionZZZ{
		File objReturn = null;
		
		TODOGOON20251013;//Werte aus, ob eine bestehende Datei ueberschreiben werden soll.
		
		main:{
//			try {
				String sFileDirectorySourceUsed = sDirectorySourceIn;
				sFileDirectorySourceUsed = FileEasyZZZ.getFileUsedPath(sFileDirectorySourceUsed);
				if(!FileEasyZZZ.exists(sFileDirectorySourceUsed)){
					String sLog = "Directory does not exists sFileDirectorySourceUsed='"+ sFileDirectorySourceUsed +"'";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
							
				String sFileNameSourceUsed = null;
				if(StringZZZ.isEmpty(sFilenameSourceIn)){
					String sLog = "Missing sFilenameSourceIn";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_EMPTY, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					sFileNameSourceUsed = sFilenameSourceIn;
				}
				
				boolean bSameDirectory=false;
				String sFileDirectoryTargetUsed = null;
				if(StringZZZ.isEmpty(sDirectoryTargetIn)){
					String sLog = "Missing sDirectoryTargetIn, using sourcedirectory as targetdirectory.";
					ObjectZZZ.logLineWithDate(sLog);
					
					bSameDirectory = true;
					sFileDirectoryTargetUsed = sFileDirectorySourceUsed;
				}else {								
					sFileDirectoryTargetUsed = sDirectoryTargetIn;				
					sFileDirectoryTargetUsed = FileEasyZZZ.getFileUsedPath(sFileDirectoryTargetUsed);
					boolean bDirectoryAvailable;
					if(!FileEasyZZZ.exists(sFileDirectoryTargetUsed)){
						bDirectoryAvailable = FileEasyZZZ.createDirectoryForDirectory(sFileDirectoryTargetUsed);
					}else {
						bDirectoryAvailable=true;
					}
					if(!bDirectoryAvailable){
						String sLog = "Directory does not exists and unable to create directory sFileDirectoryTargetUsed='"+ sFileDirectoryTargetUsed +"'";
						ObjectZZZ.logLineWithDate(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
	
				String sFileNameTargetUsed = null;
				if(StringZZZ.isEmpty(sFilenameTargetIn)){
					if(bSameDirectory) {
						String sLog = "Missing sFilenameTargetIn, using same directory.";
						ObjectZZZ.logLineWithDate(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_EMPTY, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else {
						String sLog = "Missing sFilenameTargetIn, using same filename like in source.";
						ObjectZZZ.logLineWithDate(sLog);
						
						sFileNameTargetUsed = sFileNameSourceUsed;
					}
				}else {
					sFileNameTargetUsed = sFilenameTargetIn;
				}
		
				//#############################################
				//Erst einmal die Pfade ausrechnen
				sFileDirectorySourceUsed = FileEasyZZZ.getFileUsedPath(sFileDirectorySourceUsed);
				if(!FileEasyZZZ.exists(sFileDirectorySourceUsed)){
					FileEasyZZZ.createDirectoryForDirectory(sFileDirectorySourceUsed);
				}
				String sFilePathTotalSource = FileEasyZZZ.joinFilePathName(sFileDirectorySourceUsed, sFileNameSourceUsed );				
		//			if(sFilePathTotal==null){
		//				//Eclipse Workspace
		//				File f = new File("");
		//			    String sPathEclipse = f.getAbsolutePath();
		//			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
		//			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
		//			}
				
							
				String sFilePathTotalTarget = FileEasyZZZ.joinFilePathName(sFileDirectoryTargetUsed, sFileNameSourceUsed );
		//		if(sFilePathTotal==null){
		//			//Eclipse Workspace
		//			File f = new File("");
		//		    String sPathEclipse = f.getAbsolutePath();
		//		    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
		//		    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
		//		}
				
				if(sFilePathTotalSource.equals(sFilePathTotalTarget)) {				
					String sLog = "Ziel und Quellpfad identisch:'" + sFilePathTotalSource + "'";
					ObjectZZZ.logLineWithDate(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_EMPTY, FileEasyCopyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				
				//############### NUN DAS KOPIEREN DER DATEI PER BATCH ################
				
				
//			} catch (IOException e) {			
//				e.printStackTrace();
//				ExceptionZZZ ez = new ExceptionZZZ("IOException", e);
//				throw ez;
//			} 
		}//end main:
		return objReturn;
	}
	
}
