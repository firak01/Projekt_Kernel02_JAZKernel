package basic.zKernel;

import custom.zKernel.LogZZZ;


/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface IKernelUserZZZ {
	public abstract IKernelZZZ getKernelObject();
	public abstract void setKernelObject(IKernelZZZ objKernel);		
	public abstract LogZZZ getLogObject();
	public abstract void setLogObject(LogZZZ objLog);
}
