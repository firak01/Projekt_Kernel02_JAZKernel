package basic.zBasic.util.file;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class JarEasyInCurrentJarZZZ implements IConstantZZZ, IResourceHandlingObjectZZZ {

    // === NEUE Hilfsmethode ===
    private static JarFile getJarFileOrLog() throws ExceptionZZZ {
        JarFile jar = JarKernelZZZ.getJarFileCurrent();
        if(jar==null) {
            ObjectZZZ.logLineWithDate("(D) JAR FILE NOT FOUND.");
        } else {
            ObjectZZZ.logLineWithDate("(D) JAR FILE FOUND.");
        }
        return jar;
    }

    public static boolean isDirectory(JarFile jar, String sPath) throws ExceptionZZZ {
        boolean bReturn = false;
        main:{
            if(StringZZZ.isEmpty(sPath)){
                ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
                throw ez;
            }
            if(jar==null){
                ExceptionZZZ ez = new ExceptionZZZ("No JarFile-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
                throw ez;
            }
            
            JarEntry entry = JarEasyUtilZZZ.getEntryForDirectory(jar,sPath);
            if(entry==null){
                ObjectZZZ.logLineWithDate("(D) ENTRY IN JAR FILE NOT FOUND: '" + sPath +"'");	    	
            }else{				    	
                ObjectZZZ.logLineWithDate("(D) ENTRY IN JAR FILE FOUND: '" + sPath +"'");
                bReturn = entry.isDirectory();
            }				   
        }//end main:
        return bReturn;
    }
    
    public static File extractFileAsTemp(String sPath) throws ExceptionZZZ {
        File objReturn = null;
        main:{						    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objReturn = JarEasyZZZ.extractFileAsTemp(jar, sPath);
            }				
        }//end main:
        return objReturn;
    }

    public static File[] peekDirectories(String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
        File[] objaReturn=null;
        main:{
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objaReturn = JarEasyZZZ.peekDirectories(jar, sSourceDirectoryPath, sTargetDirectoryPathIn);
            }
        }//end main:
        return objaReturn;
    }

    public static File[] peekDirectories(String sSourceDirectoryPath, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
        File[] objaReturn=null;
        main:{			
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objaReturn = JarEasyZZZ.peekDirectories(jar, sSourceDirectoryPath, sTargetDirectoryPathIn, bWithFiles);
            }
        }//end main:
        return objaReturn;
    }

    public static File peekFileFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main:{    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objReturn = JarEasyZZZ.peekFileFirst(jar, sPath, sTargetDirectoryPathRootIn);				
            }
        }//end main:
        return objReturn;
    }

    public static File[] peekFiles(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File[] objaReturn = null;
        main:{	    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objaReturn = JarEasyZZZ.peekFiles(jar, sPath, sTargetDirectoryPathRootIn);				
            }
        }//end main:
        return objaReturn;
    }

    public static File peekDirectoryFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main:{						    	
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objReturn = JarEasyZZZ.peekDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn);				
            }
        }//end main:
        return objReturn;
    }

    public static File searchResourceDirectoryFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main:{		    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objReturn = JarEasyZZZ.searchResourceDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn);
            }
        }//end main:
        return objReturn;
    }

    public static File searchResourceFileFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main:{			    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objReturn = JarEasyZZZ.searchResourceFileFirst(jar, sPath, sTargetDirectoryPathRootIn);
            }
        }//end main:
        return objReturn;
    }

    public static File[] searchResourceFiles(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File[] objaReturn = null;
        main:{			    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objaReturn = JarEasyZZZ.searchResourceFiles(jar, sPath, sTargetDirectoryPathRootIn);
            }
        }//end main:
        return objaReturn;
    }

    public static File searchResourceDirectory(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
        File objReturn = null;
        main:{			    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objReturn = JarEasyZZZ.searchResourceDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn, bWithFiles);
            }
        }//end main:
        return objReturn;
    }

    public static File[] searchResources(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File[] objaReturn = null;
        main:{			    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objaReturn = JarEasyZZZ.searchResources(jar, sPath, sTargetDirectoryPathRootIn);
            }
        }//end main:
        return objaReturn;
    }

    public static File[] searchResources(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
        File[] objaReturn = null;
        main:{			    		
            JarFile jar = getJarFileOrLog();
            if(jar!=null) {
                objaReturn = JarEasyZZZ.searchResources(jar, sPath, sTargetDirectoryPathRootIn, bWithFiles);
            }
        }//end main:
        return objaReturn;
    }

    public static boolean isInJarStatic() throws ExceptionZZZ{
        return JarEasyUtilZZZ.isInJar(JarEasyInCurrentJarZZZ.class);
    }
    
    //### Interfaces
    public boolean isInJar() throws ExceptionZZZ{
        return JarEasyUtilZZZ.isInJar(this.getClass());
    }
}
