package basic.zKernel;

import basic.zKernel.ConfigZZZ;
import basic.zBasic.ExceptionZZZ;

public class KernelSingletonZZZ extends KernelKernelZZZ{
	private static final long serialVersionUID = 1L;
	private static KernelSingletonZZZ objKernelSingelton; //muss als Singleton static sein	
	public static KernelSingletonZZZ getInstance() throws ExceptionZZZ{
		if(objKernelSingelton==null){
			String[] saFlagZ={"init"};
			objKernelSingelton = new KernelSingletonZZZ(saFlagZ);			
		}
		return objKernelSingelton;	
	}
		
	public static  KernelSingletonZZZ getInstance(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		if(objKernelSingelton==null){
			objKernelSingelton = new KernelSingletonZZZ(objConfig, sFlagControl);
		}
		return objKernelSingelton;	
	}
	
	public static  KernelSingletonZZZ getInstance(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
		if(objKernelSingelton==null){
			objKernelSingelton = new KernelSingletonZZZ(objConfig, saFlagControl);
		}
		return objKernelSingelton;	
	}
	
//	public static KernelSingletonZZZ getInstance(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
//		if(objKernelSingelton==null){
//			objKernelSingelton = new KernelSingletonZZZ(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
//		}
//		return objKernelSingelton;	
//	}
	
	public static KernelSingletonZZZ getInstance(String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		if(objKernelSingelton==null){
			objKernelSingelton = new KernelSingletonZZZ("THM", sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
		}
		return objKernelSingelton;	
	}
	
	//Die Konstruktoren nun verbergen, wg. Singleton
		private KernelSingletonZZZ() throws ExceptionZZZ{
			super();
		}
		
		private KernelSingletonZZZ(String[] saFlagControl) throws ExceptionZZZ{
			super(saFlagControl);
		}
		
		//Die Konstruktoren nun verbergen, wg. Singleton
		private KernelSingletonZZZ(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
			super(objConfig, sFlagControl);
		}
		
		//Die Konstruktoren nun verbergen, wg. Singleton
		private KernelSingletonZZZ(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
			super(objConfig, saFlagControl);
		}
		
		private KernelSingletonZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
			super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
		}		
		
		public String getFileConfigKernelName() throws ExceptionZZZ{			
			return super.getFileConfigKernelName();
		}
		public String getApplicationKey() throws ExceptionZZZ{			
			return super.getApplicationKey();
		}
		
		
		//#### Interfaces
		public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ{
			IKernelConfigZZZ objConfig = super.getConfigObject();
			if(objConfig==null){
				objConfig = new ConfigZZZ(null);			
			}
			return objConfig;
		}
}
