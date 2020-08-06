package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class JarEasyInCurrentJarZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			File objFileJarAsSource = null;
			if(JarEasyInCurrentJarZZZ.isInJarStatic()) {
//				ProtectionDomain dom = JarEasyInCurrentJarZZZ.class.getProtectionDomain();
//				if(dom==null) {
//					String sLog = ReflectCodeZZZ.getPositionCurrent()+": No Protection Domain available";
//				    System.out.println(sLog);
//				}else {
//					CodeSource codeSource = dom.getCodeSource();
//					if(codeSource==null) {
//						String sLog = ReflectCodeZZZ.getPositionCurrent()+": No CodeSource available";
//						System.out.println(sLog);
//					}else {						
//						URL url = codeSource.getLocation();
//						if(url==null) {
//							String sLog = ReflectCodeZZZ.getPositionCurrent()+": No URL available";
//							System.out.println(sLog);	
//							
//							sLog = ReflectCodeZZZ.getPositionCurrent()+": Using jar file '" + JarEasyTestConstantsZZZ.sJAR_FILEPATH + "'";
//							System.out.println(sLog);	
//							File objFileJar = new File(JarEasyTestConstantsZZZ.sJAR_FILEPATH);
//							JarFile objJar = JarEasyZZZ.toJarFile(objFileJar);
//							
//							objFileJarAsSource = JarEasyZZZ.getJarCurrentFromUrl(objJar, "file:///template");
//						}else {
//							url = JarEasyInCurrentJarZZZ.class.getClassLoader().getResource(".");
//							String sLog = ReflectCodeZZZ.getPositionCurrent()+": URL used '" + url.getPath() +"'";
//							System.out.println(sLog);
//							
//							objFileJarAsSource = new File(url.getPath());
//						}
//						
//						
//					}
//				}
				objFileJarAsSource = JarEasyZZZ.getJarCurrent();
			}else {
				String sJarFilePath = JarEasyTestConstantsZZZ.sJAR_FILEPATH;
				objFileJarAsSource = new File(sJarFilePath);
				if(objFileJarAsSource.isFile()) {  // Run with JAR file		
					objJarAsSource = new JarFile(objFileJarAsSource);
					String sLog = ReflectCodeZZZ.getPositionCurrent()+": JAR FILE FOUND.";
				    System.out.println(sLog);
				}
			}
			
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
			    
				//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.			 
				
			} catch (IOException e1) {
				ExceptionZZZ ez  = new ExceptionZZZ("Beim setUp - IOException: " + e1.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyInCurrentJarZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
			
	}//END setUp
	protected void tearDown() {
		if(objJarAsSource!=null) {
			try {
				objJarAsSource.close();
			} catch (IOException e) {
				ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyInCurrentJarZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
		}
	}//END tearDown
		
	public void testSearchRessourceAsTempFile(){
		try{
			
			File objFileCreated;			
			if(JarEasyZZZ.isInJarStatic())	{
				objFileCreated = JarEasyInCurrentJarZZZ.searchRessourceAsTempFile("template/template_server_starter.txt");
				assertNotNull(objFileCreated);
				if(!objFileCreated.exists()) {
					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
				}
			}
			
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}

	
	
	
}//END Class
