package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ResourceEasyZZZ extends ObjectZZZ{
	private ResourceEasyZZZ(){
		//Zum Verstecken des Konstruktors
	}
	
	public static File doClassloaderGetResource(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath))break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) isearching for path '" + sPath + "'";
			System.out.println(sLog);
			URL workspaceURL = ResourceEasyZZZ.class.getClassLoader().getResource(sPath);
			objReturn = ResourceEasyZZZ.getResource(workspaceURL, sPath, true);		
		}
		return objReturn;
	}
	
	public static File doClassGetResource(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath))break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) isearching for path '" + sPath + "'";
			System.out.println(sLog);
			URL workspaceURL = ResourceEasyZZZ.class.getResource(sPath);
			objReturn = ResourceEasyZZZ.getResource(workspaceURL, sPath, false);			
		}
		return objReturn;
	}
	
	private static File getResource(URL workspaceURL, String sPath, boolean byClassloader) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog=null;
			if(JarEasyZZZ.isInJarStatic()){				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) in .jar searching.";
				System.out.println(sLog);
				if(workspaceURL!=null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) URL is not null '" + workspaceURL.toString() + "')";
				    System.out.println(sLog);
				    try {
				    	objReturn = FileEasyZZZ.createTempFile();
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) temp File created.";
					    System.out.println(sLog);
					    
				    	FileOutputStream fosResource = new FileOutputStream(objReturn);
				   
					    int i; boolean bAnyFound=false;
					    byte[] ba = new byte[1024];
					    InputStream isClass = null;
					    if(byClassloader){
					    	isClass = ResourceEasyZZZ.class.getClassLoader().getResourceAsStream(sPath);
					    }else{
					    	isClass = ResourceEasyZZZ.class.getResourceAsStream(sPath);
					    }
					   
					   //while the InputStram has bytes				   
						while((i=isClass.read(ba))>0){
							bAnyFound=true;
							
							 //write the bytes to the output stream
							fosResource.write(ba,0,i);						  					
						}
						if(bAnyFound){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) any Bytes found.";
						    System.out.println(sLog);
						}else{
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) no Bytes found!!!";
						    System.out.println(sLog);
						}
						
						//close streams to preven errors
						isClass.close();
						fosResource.close();
				
				} catch (FileNotFoundException fnfe) {
					ExceptionZZZ ez = new ExceptionZZZ("FileNotFoundException: '" + fnfe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				} catch (IOException ioe) {
					ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) URL is null'";
				    System.out.println(sLog);
				}
				
			}else{								
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) not in .jar searching for path '" + sPath + "'";
				System.out.println(sLog);
				if(workspaceURL!=null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) URL is not null for path '" + sPath + "' ('" + workspaceURL.toString() + "')";
				    System.out.println(sLog);
					try {			
						objReturn = new File(workspaceURL.toURI());
						if(objReturn.exists()){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) Datei gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}
					} catch(URISyntaxException e) {
						objReturn = new File(workspaceURL.getPath());
						if(objReturn.exists()){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) Datei gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}
					}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (B) URL is null'";
				    System.out.println(sLog);
				}
				
			}
			
		}
		return objReturn;
	}
}
