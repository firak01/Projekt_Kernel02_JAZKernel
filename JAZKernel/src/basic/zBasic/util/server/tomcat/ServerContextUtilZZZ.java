package basic.zBasic.util.server.tomcat;

import basic.zBasic.util.datatype.string.StringZZZ;

public class ServerContextUtilZZZ {

	//Wird verwendet für Server-WebService. U.a. Projekt JAZKernelService
	public static String computeContextJndiLookupPath(String sJndi){
		String sReturn = null;
		main:{			
			if(StringZZZ.isEmpty(sJndi)){
				
			}else{
				sReturn = "java:comp/env/" + sJndi;
			}
			
		}//end main:
		return sReturn;
	}
}
