package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;

public class JarEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			//final File jarFile = new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String sJarFilePath = JarEasyTestConstantsZZZ.sJAR_FILEPATH;
			objFileJarAsSource = new File(sJarFilePath);
				if(objFileJarAsSource.isFile()) {  // Run with JAR file		
					objJarAsSource = new JarFile(objFileJarAsSource);
					String sLog = ReflectCodeZZZ.getPositionCurrent()+": JAR FILE FOUND.";
				    System.out.println(sLog);
				}
				
				sTargetDirPath="c:\\temp\\kerneltest";
				
			} catch (IOException e1) {
				ExceptionZZZ ez  = new ExceptionZZZ("Beim setUp - IOException: " + e1.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
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
				ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
		}
	}//END tearDown
		
		
	public void testExtractDirectory(){
		try{
			String sDirToExtract="template";
			String sDirToExtractTotal; String sTargetDirPathTotal;
			File objDirCreated;
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTotal = "FGL_WITHOUTFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, false);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
			}
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTotal = "FGL_WITHFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, true);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//B) Fall: Dateien exitieren bereits. D.h. alles komplett löschen in der Methode und neu anlegen.
			//Ba) Erfolgsfall, ohne Dateien zu erzeugen					
			sDirToExtractTotal = "FGL_WITHOUTFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, false);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
			}
			
			//Bb) Erfolgsfall, mit Dateien erzeugen						
			sDirToExtractTotal = "FGL_WITHFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, true);
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
}//END Class
