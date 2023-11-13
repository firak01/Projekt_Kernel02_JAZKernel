package basic.zKernel;

import basic.zKernel.ConfigZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;

public class KernelSingletonZZZ extends KernelKernelZZZ{
	private static final long serialVersionUID = 1L;
	private static KernelSingletonZZZ objKernelSingleton; //muss als Singleton static sein	
	public static KernelSingletonZZZ getInstance() throws ExceptionZZZ{
		if(objKernelSingleton==null){
			//Das hier nur zu initialisieren ist falsch. Schliesslich kennt man doch den Application-Key
			//String[] saFlagZ={"init"};
			//objKernelSingelton = new KernelSingletonZZZ(saFlagZ);	
			
			//Verwende hier Config-Objekt mit dem gleichen Suffix der Klasse, also ZZZ
			IKernelConfigZZZ objConfig = new ConfigZZZ();
			objKernelSingleton = new KernelSingletonZZZ(objConfig, (String) null);
		}
		return objKernelSingleton;	
	}
		
	public static  KernelSingletonZZZ getInstance(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		if(objKernelSingleton==null){
			objKernelSingleton = new KernelSingletonZZZ(objConfig, sFlagControl);
		}
		return objKernelSingleton;	
	}
	
	public static  KernelSingletonZZZ getInstance(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
		if(objKernelSingleton==null){
			objKernelSingleton = new KernelSingletonZZZ(objConfig, saFlagControl);
		}
		return objKernelSingleton;	
	}
	
//	public static KernelSingletonZZZ getInstance(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
//		if(objKernelSingelton==null){
//			objKernelSingelton = new KernelSingletonZZZ(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
//		}
//		return objKernelSingelton;	
//	}
	
	public static KernelSingletonZZZ getInstance(String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		if(objKernelSingleton==null){
			//Verwende hier das Suffix der Klasse als Applicationkey, also ZZZ
			objKernelSingleton = new KernelSingletonZZZ("ZZZ", sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
		}
		return objKernelSingleton;	
	}
	
	public static KernelSingletonZZZ getInstance(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		if(objKernelSingleton==null){
			//Verwende hier das Suffix der Klasse als Applicationkey, also ZZZ
			objKernelSingleton = new KernelSingletonZZZ(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
		}
		return objKernelSingleton;	
	}
	
	//Die Konstruktoren nun verbergen, wg. Singleton
		private KernelSingletonZZZ() throws ExceptionZZZ{
			super();
		}
		
		private KernelSingletonZZZ(String[] saFlagControl) throws ExceptionZZZ{
			super(saFlagControl);
		}
		
		private KernelSingletonZZZ(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
			super(objConfig, sFlagControl);
		}
		
		private KernelSingletonZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
			super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
		}	
				
		private KernelSingletonZZZ(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
			super(objConfig, saFlagControl);
		}
		
		private KernelSingletonZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String sFlagControl ) throws ExceptionZZZ{
			super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, sFlagControl);
		}		
		
		public String getFileConfigKernelName() throws ExceptionZZZ{			
			return super.getFileConfigKernelName();
		}
		public String getApplicationKey() throws ExceptionZZZ{			
			return super.getApplicationKey();
		}
		
		
		//#### Interfaces
//		public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ{
//			//IKernelConfigZZZ objConfig = super.getConfigObject();
//			IKernelConfigZZZ objConfig = this.getConfigObject();
//			if(objConfig==null){
//				objConfig = new ConfigZZZ(null);			
//			}
//			return objConfig;
//		}

		//Aus IRessourceHandlingObject	
		//### Ressourcen werden anders geholt, wenn die Klasse in einer JAR-Datei gepackt ist. Also:
		/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
		 *   Merke: Static Klassen müssen diese Methode selbst implementieren.
		 * @return
		 * @author lindhaueradmin, 21.02.2019
		 * @throws ExceptionZZZ 
		 */
		@Override
		public boolean isInJar() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
			}
			return bReturn;
		}
		
		/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
		 *   Merke: Static Klassen müssen diese Methode selbst implementieren. Das ist dann das Beispiel.
		 * @return
		 * @author lindhaueradmin, 21.02.2019
		 * @throws ExceptionZZZ 
		 */
		public static boolean isInJarStatic() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = JarEasyUtilZZZ.isInJar(KernelSingletonZZZ.class);
			}
			return bReturn;
		}	
}
