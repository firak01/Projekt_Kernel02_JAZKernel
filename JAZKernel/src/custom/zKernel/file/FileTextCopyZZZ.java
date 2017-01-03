package custom.zKernel.file;

import custom.zKernel.LogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.*;


/**
 * @author Lindhauer
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FileTextCopyZZZ extends KernelFileTextCopyZZZ {



	/**Constructor
	 * @param objKernel
	 * @param objLog
	 * @param saFileSource
	 * @param saFileTarget
	 * @param strings
	 */
	public FileTextCopyZZZ(KernelZZZ objKernel, LogZZZ objLog, String sDirectorySource, String sDirectoryTarget, String[] saFileSource, String[] saFileTarget, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, objLog, sDirectorySource, sDirectoryTarget, saFileSource, saFileTarget, saFlagControl);
	}


}
