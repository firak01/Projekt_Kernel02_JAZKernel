package basic.zBasic.util.file;


import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

import junit.framework.TestCase;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class FileEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    			
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
				ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
		}
	}//END tearDown
	
	
		
	public void testRemoveDirectoryContent(){
		try{
			
			//1. Hole ein Jar-File für die Struktur der Verzeichnisse in diesem Test
			objFileJarAsSource = JarEasyUtilZZZ.getJarFileTestAsFile();
			
			
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
		    
			String sDirToExtract="FileEasyZZZ_removeDirectoryContent";
			String sDirToExtractTo; 
			File objDirCreated;
			File[] objaDirCreated;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//TESTDATEIEN UND VERZEICHNISSE ERZEUGEN								
			sDirToExtractTo = "FGL_TEST_REMOVE_DIRECTORY";;			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sDirToExtractTo,true);
			if(objaDirCreated==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).");
			}
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Verzeichnis '" + objFileTemp.getAbsolutePath() + "' wurde nicht erstellt.");
				}
			}
			
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}
