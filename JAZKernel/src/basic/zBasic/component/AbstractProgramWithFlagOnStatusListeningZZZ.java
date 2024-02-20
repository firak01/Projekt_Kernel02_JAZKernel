package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.AbstractObjectWithFlagOnStatusListeningZZZ;
import basic.zBasic.AbstractObjectWithStatusOnStatusListeningZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalReactZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalUserZZZ;

public abstract class AbstractProgramWithFlagOnStatusListeningZZZ extends AbstractObjectWithFlagOnStatusListeningZZZ implements IProgramZZZ, IModuleUserZZZ{
	private static final long serialVersionUID = 8381960801083154549L;
	protected volatile IModuleZZZ objModule=null; //Das Modul, in der KernelUI - Variante wäre das die Dialogbox aus der das Program gestartet wird.	
	protected volatile String sProgramName = null;
	protected volatile String sModuleName = null;
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractProgramWithFlagOnStatusListeningZZZ() throws ExceptionZZZ {
		super();
		AbstractProgramNew_();
	}
	
	public AbstractProgramWithFlagOnStatusListeningZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractProgramNew_();
	}
	
	public AbstractProgramWithFlagOnStatusListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);
		AbstractProgramNew_();
	}
	
		
	private boolean AbstractProgramNew_() throws ExceptionZZZ {
		
		//Da dies ein KernelProgram ist automatisch das FLAG IKERNELPROGRAM Setzen!!!
	    this.setFlag(IProgramZZZ.FLAGZ.ISPROGRAM.name(), true);	
				
		return true;
	}
	
	//################################################################################
	//### Aus IProgramZZZ
	@Override
	public String getProgramName(){
		if(StringZZZ.isEmpty(this.sProgramName)) {
			if(this.getFlag(IProgramZZZ.FLAGZ.ISPROGRAM.name())) {
				this.sProgramName = this.getClass().getName();
			}
		}
		return this.sProgramName;
	}
	
	@Override
	public String getProgramAlias() throws ExceptionZZZ {		
		return null;
	}
		
	@Override
	public void resetProgramUsed() {
		this.sProgramName = null;
	}

	@Override
	public boolean reset() throws ExceptionZZZ {
		this.resetProgramUsed();
		this.resetModuleUsed();
		this.resetFlags();
		return true;
	}
	
	@Override
	public abstract boolean startAsThread() throws ExceptionZZZ;


	//###########################################
	//### FLAGZ IProgramZZZ
	//###########################################
	@Override
	public boolean getFlag(IProgramZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IProgramZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IProgramZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IProgramZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}
	
	@Override
	public boolean proofFlagSetBefore(IProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	

	//###########################################
	//### FLAGZ IModuleUserZZZ
	//###########################################
	@Override
	public boolean getFlag(IModuleUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IModuleUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IModuleUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IModuleUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	//####################################################################
	//### Aus IKernelModuleUserZZZ	
	@Override
	public String readModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			IModuleZZZ objModule = this.getModule();
			if(objModule!=null) {
				sReturn = objModule.getModuleName();
			}
		}//end main:
		return sReturn;
	}
	
	@Override
	public String getModuleName() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sModuleName)) {
			this.sModuleName = this.readModuleName();
		}
		return this.sModuleName;
	}
	
	@Override
	public void setModuleName(String sModuleName){
		this.sModuleName=sModuleName;
	}
	
	@Override
	public void resetModuleUsed() {
		this.objModule = null;
		this.sModuleName = null;
	}
	
	@Override
	public IModuleZZZ getModule() {
		return this.objModule;
	}
	
	@Override
	public void setModule(IModuleZZZ objModule) {
		this.objModule = objModule;
	}
}
