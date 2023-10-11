package basic.zKernel;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**This class is used for reading/writing from/to 1 one or more .property-file(s).
 * !!! This class is build as a Singleton., with a private constructor !!! Therefore use the static KernelPropertyZZZ.getInstance(...) to receive an object.
 * 
 * Usage in this framework:
 *  The .property-file(s) will contain parameters, which were used by a "Program" of a "Module".
 *  Remember: The KernelObject is used for acessing module configuration files, etc.
 *  
 * @author 0823
 *
 */
public class KernelPropertyZZZ extends AbstractObjectZZZ implements java.io.Serializable {
		private static KernelPropertyZZZ objProperty;                           //dies ist das eigentliche Singleton Objekt, von dem es nur ein einziges gibt 
		private static HashMap hmProperty = new HashMap();
		
		/** This is a private constructor !!! 
		 * You should use the static .getInstance(...)-Method to receive an object.
		 * 
		* 0823; 01.06.2006 08:42:46
		 * @param sConfigFile
		 */
		private KernelPropertyZZZ(String sConfigFile) throws IOException{
			load(sConfigFile);
		}
		
		
		public void finalize(){
			//Der JUnit Test hat herausgefunden, dass das Objekt so am besten zerst�rt wird.
			objException=null;
			hmProperty=null;
		}
		
		
	/** KernelPropertyZZZ, receive the singleton object. This method does only work, if .getInstance( sFilePath ) was used before.
	* 0823; 01.06.2006 09:59:53
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static KernelPropertyZZZ getInstance() throws ExceptionZZZ{
		if(objProperty == null){
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PROPERTY_MISSING, iERROR_PROPERTY_MISSING, null,ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objProperty;
	}
		
	/** KernelPropertyZZZ, receive the singleton object. If the file has been loaded before it will become reloaded, but no new property will be stored in the hashmap ( .getFileLoadedAll() ).
	* 0823; 01.06.2006 10:01:37
	 * @param sConfigFile
	 * @return
	 * @throws IOException
	 */
	public static KernelPropertyZZZ getInstance(String sConfigFile) throws IOException{
		KernelPropertyZZZ objReturn = null;
		main:{
			objReturn = objProperty;
			if(objReturn==null){
				objReturn = new KernelPropertyZZZ(sConfigFile);
				objProperty = objReturn;
			}
			
			if (hmProperty==null) hmProperty = new HashMap();
			Properties p = load(sConfigFile);						
		}
		return objReturn;
	}
	
	public static Properties load(String sConfigFile) throws IOException{
		Properties objReturn=null;
		main:{
			objReturn = new Properties();			
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(sConfigFile)));			
			objReturn.load(in);		
			
			//Falls das "neue" File nicht vorhanden ist, dann wird eine Exception ausgel�st. Darum kann man nun schreiben... 
			hmProperty.put(sConfigFile, objReturn);  //.... ohne das p == null ist.
			
		}//END main:
		return objReturn;
	}
	
	/** int, the number of elements stored in the hashmap, which stores all filepath and the corresponding java.util.properties - objects
	* 0823; 01.06.2006 10:13:52
	 * @return int
	 */
	public int getFileLoadedCounter(){
		int iReturn=0;
		main:{
			if(hmProperty==null) break main;
			iReturn = hmProperty.size();
		}
		return iReturn;
	}
	
	/** HashMap, the key is a filepath. The stored value is a java.util.properties-object.
	 * 
	* 0823; 01.06.2006 10:12:42
	 * @return HashMap
	 */
	public HashMap getFileLoadedAll(){
		return hmProperty;
	}
	
	/** String, read from the propery file. If the file has not been loaded before, it will be loaded now.
	 * 
	* 0823; 01.06.2006 10:25:53
	 * @param sFilepath
	 * @param sKey
	 * @return
	 * @throws IOException
	 */
	public String getProperty(String sFilepath, String sKey) throws IOException{
		String sReturn = null;
		main:{
			check:{
				if(hmProperty==null) break main;				
			}//END Check
		
			//Get a property from hashmap (file read before) or load the file new into the hashmap and read the property. 
			Properties p = null;
			if(! hmProperty.containsKey(sFilepath)){
				p = load(sFilepath);					
			}else{
				p = (Properties) hmProperty.get(sFilepath);			
			}
			if (p==null) break main;  //Sicherheitshalber
			sReturn = p.getProperty(sKey);
		}//END main:
		return sReturn;
	}
}
