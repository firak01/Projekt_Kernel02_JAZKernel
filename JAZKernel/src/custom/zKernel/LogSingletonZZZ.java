package custom.zKernel;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import basic.zKernel.AbstractKernelObjectZZZ;
import basic.zKernel.ConfigZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.file.ini.IIniTagWithExpressionZZZ;

public class LogSingletonZZZ extends AbstractKernelLogZZZ{
	private static final long serialVersionUID = 1L;
	private static LogSingletonZZZ objLogSingleton; //muss als Singleton static sein	
	public static LogSingletonZZZ getInstance() throws ExceptionZZZ{
		if(objLogSingleton==null){
			//Das hier nur zu initialisieren ist falsch.
			//String[] saFlagZ={"init"};
			//objKernelSingelton = new KernelSingletonZZZ(saFlagZ);	

			objLogSingleton = new LogSingletonZZZ(null, null, (String[]) null);
		}
		return objLogSingleton;	
	}
		
	public static  LogSingletonZZZ getInstance(String sFlagControl) throws ExceptionZZZ{
		if(objLogSingleton==null){
			String[] saFlagControl= new String[1];
			saFlagControl[0]=sFlagControl;
			
			objLogSingleton = new LogSingletonZZZ(null, null, saFlagControl);
		}
		return objLogSingleton;	
	}
	
	public static  LogSingletonZZZ getInstance(String... sFlags) throws ExceptionZZZ{
		if(objLogSingleton==null){			
			objLogSingleton = new LogSingletonZZZ(null, null, sFlags);
		}
		return objLogSingleton;	
	}
	
	public static LogSingletonZZZ getInstance(String sDirectoryPath, String sLogFile) throws ExceptionZZZ{
		if(objLogSingleton==null){
			//Verwende hier das Suffix der Klasse als Applicationkey, also ZZZ
			objLogSingleton = new LogSingletonZZZ(sDirectoryPath, sLogFile, (String[]) null);
		}
		return objLogSingleton;	
	}
	
	public static LogSingletonZZZ getInstance(String sDirectoryPath, String sLogFile, String sFlag) throws ExceptionZZZ{
		if(objLogSingleton==null){
			//Verwende hier das Suffix der Klasse als Applicationkey, also ZZZ
			objLogSingleton = new LogSingletonZZZ(sDirectoryPath, sLogFile, sFlag);
		}
		return objLogSingleton;	
	}
	
	
	public static  LogSingletonZZZ getInstance(String sDirectoryPath, String sLogFile, String... sFlags) throws ExceptionZZZ{
		if(objLogSingleton==null){
			objLogSingleton = new LogSingletonZZZ(sDirectoryPath, sLogFile, sFlags);
		}
		return objLogSingleton;	
	}
		
	
	//########################################################################
	//Die Konstruktoren nun verbergen, wg. Singleton
	private LogSingletonZZZ() throws ExceptionZZZ{
		super();
	}
	
	private LogSingletonZZZ(String sDirectoryPath, String sLogFile) throws ExceptionZZZ {
		super(sDirectoryPath, sLogFile);
	}
	
	private LogSingletonZZZ(String sDirectoryPath, String sLogFile, String sFlagControl) throws ExceptionZZZ {
		super(sDirectoryPath, sFlagControl);
	}
	
	private LogSingletonZZZ(String sDirectoryPath, String sLogFile, String[] saFlagControl) throws ExceptionZZZ {
		super(sDirectoryPath, sLogFile, saFlagControl);
	}	
	
	private LogSingletonZZZ(IKernelConfigZZZ objConfig) throws ExceptionZZZ{
		super(objConfig);
	}
}
