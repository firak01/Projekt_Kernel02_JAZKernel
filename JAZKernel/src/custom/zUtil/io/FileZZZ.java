package custom.zUtil.io;

import basic.zBasic.ExceptionZZZ;
import basic.zUtil.io.KernelFileZZZ;

/**
 * @author Lindhauer
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FileZZZ extends KernelFileZZZ{

	public FileZZZ() throws ExceptionZZZ{
		super();
	}
	
	/**
	 * Constructor FileZZZ.
	 * @param sPathTarget
	 * @param sFileTarget
	 */
	public FileZZZ(String sPathTarget, String sFileTarget, String[] saFlagControl) throws ExceptionZZZ {
		super(sPathTarget, sFileTarget, saFlagControl);
	}
	
	public FileZZZ(String sPathTarget, String sFileTarget, String sFlagControl) throws ExceptionZZZ{
		super(sPathTarget, sFileTarget, sFlagControl);
	}
	 
	public FileZZZ(String sPathTarget, String sFileTarget, int iExpansionLength, String[] saFlagControl) throws ExceptionZZZ{
		super(sPathTarget, sFileTarget, iExpansionLength, saFlagControl);
	}

}
