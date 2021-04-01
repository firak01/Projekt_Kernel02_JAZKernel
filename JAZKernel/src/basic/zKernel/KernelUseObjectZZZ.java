package basic.zKernel;

import basic.zBasic.ObjectZZZ;
import custom.zKernel.LogZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class KernelUseObjectZZZ extends ObjectZZZ implements IKernelUserZZZ, IKernelContextUserZZZ {
	// Flags, die alle Z-Kernel-Objekte haben !!!	
	protected IKernelZZZ objKernel = null;	
	protected LogZZZ objLog = null;
	private IKernelContextZZZ objContext = null;

	/** This Constructor is used as 'implicit super constructor' 
	* Lindhauer; 10.05.2006 06:05:14
	 */
	public KernelUseObjectZZZ(){		
		//FGL 20080422 wenn objekte diese klasse erweitern scheint dies immer ausgeführt zu werden. Darum hier nicht setzen !!! this.setFlag("init", true);
	}
	
	/** This constructor declares the used Log-Object as the Kernel-LogObject.
	* Lindhauer; 10.05.2006 06:06:00
	 * @param objKernel
	 */
	public KernelUseObjectZZZ(IKernelZZZ objKernel){
		this.objKernel = objKernel;
		this.objLog = objKernel.getLogObject();
	}
	public KernelUseObjectZZZ(IKernelZZZ objKernel, String sFlag){
		super(sFlag);
		if(this.getFlag("init")==false){
			this.objKernel = objKernel;
			this.objLog = objKernel.getLogObject();
		}
	}
	public KernelUseObjectZZZ(IKernelZZZ objKernel, String[] saFlag){
		super(saFlag);
		if(this.getFlag("init")==false){
			this.objKernel = objKernel;
			this.objLog = objKernel.getLogObject();
		}
	}
	
	/** Dieser Konstruktor kann fuer Objkete verwendet werden, die auf bestimmte Bereiche der Modulkonfiguration zurueckgreifen m�ssen UND bei denen diese Bereiche nicht dem eigenen Klassennamen entsprechen.
	* lindhaueradmin; 12.04.2007 15:46:51
	 * @param objKernel
	 * @param objKernelSection
	 */
	public KernelUseObjectZZZ(IKernelZZZ objKernel, IKernelContextZZZ objKernelContext){
		this(objKernel);
		this.objContext = objKernelContext;		
	}
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#getKernelObject()
	 */
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#setKernelObject(zzzKernel.custom.KernelZZZ)
	 */
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel=objKernel;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#getLogObject()
	 */
	public LogZZZ getLogObject() {
		return this.objLog;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#setLogObject(zzzKernel.custom.LogZZZ)
	 */
	public void setLogObject(LogZZZ objLog) {
		this.objLog=objLog;
	}
	
	public String getModuleUsed(){
		if(this.getContextUsed() == null){
			return this.getClass().getName();
		}else{
			return this.getContextUsed().getModuleName();
		}
	}
	
	public String getProgramUsed(){
		if(this.getContextUsed() == null){
			return this.getClass().getName();
		}else{
			return this.getContextUsed().getProgramName();
		}
	}

	public void setContextUsed(IKernelContextZZZ objContext) {
		this.objContext = objContext;
	}

	public IKernelContextZZZ getContextUsed() {
		return this.objContext;
	}

	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public void logLineDate(String sLog) {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.WriteLineDate(sLog);
		}		
	}	
}//end class

