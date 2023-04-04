/*
 * Created on 05.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.file.ini;

import java.io.File;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelFileIniZZZ;

/**
@author 0823 ,date 05.10.2004
*/
public class FileIniZZZ extends KernelFileIniZZZ{
	
	public FileIniZZZ() throws ExceptionZZZ{
		super();
	}
	
	public FileIniZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
	}
	
	/** CONSTRUCTOR
	 @author 0823 , date: 05.10.2004
	 @param kernelZZZ
	 @param object
	 @param objFile
	 @param object2
	 */
	public FileIniZZZ(IKernelZZZ objKernel, File objFile) throws ExceptionZZZ {
		super(objKernel, objFile);
	}
	
	public FileIniZZZ(IKernelZZZ objKernel, File objFile, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, objFile, saFlagControl);
	}
	
	public FileIniZZZ(IKernelZZZ objKernel, File objFile, HashMap<String,Boolean>hmFlag) throws ExceptionZZZ {
		super(objKernel, objFile, hmFlag);
	}

	/** CONSTRUCTOR
	 @author 0823 , date: 05.10.2004
	 @param objKernel
	 @param objLog
	 @param saFlagControl
	 */
	public FileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFile, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, sDirectory, sFile, saFlagControl);
	}

	public FileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFile, HashMap<String,Boolean>hmFlag) throws ExceptionZZZ {
		super(objKernel, sDirectory, sFile, hmFlag);
	}
}
