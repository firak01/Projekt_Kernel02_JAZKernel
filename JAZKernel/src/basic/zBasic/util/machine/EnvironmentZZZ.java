package basic.zBasic.util.machine;

import java.net.InetAddress;
import java.net.UnknownHostException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**
 *  
 * 
 * @author Fritz Lindhauer, 23.01.2020, 09:30:25
 * 
 */
public class EnvironmentZZZ implements IConstantZZZ{
	public static String getHostName() throws ExceptionZZZ{
		String sReturn = null;
		try {			
			InetAddress objInet = InetAddress.getLocalHost();
			sReturn = objInet.getHostName();						
		} catch (UnknownHostException e) {			
			ExceptionZZZ ez = new ExceptionZZZ("UnknownHostException: ", iERROR_RUNTIME,  EnvironmentZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return sReturn;		
	}
}
