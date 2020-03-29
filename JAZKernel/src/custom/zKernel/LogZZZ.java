package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelLogZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LogZZZ extends KernelLogZZZ{
	public LogZZZ(){
		super();	
	}

	/**
	 * Constructor LogZZZ.
	 * @param stemp
	 */
	public LogZZZ(String sDirectory, String sFile) throws ExceptionZZZ {
		super(sDirectory, sFile);
	}
	
	public LogZZZ(String sDirectory, String sFile, String sFlagControl) throws ExceptionZZZ {
		super(sDirectory, sFile, sFlagControl);
	}
	
	public LogZZZ(String sDirectory, String sFile, String[] saFlagControl) throws ExceptionZZZ {
		super(sDirectory, sFile, saFlagControl);
	}
	
}
