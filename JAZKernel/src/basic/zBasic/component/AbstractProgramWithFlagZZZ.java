package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractProgramWithFlagZZZ extends AbstractObjectWithFlagZZZ<Object> implements IProgramZZZ, IModuleUserZZZ {
	private static final long serialVersionUID = 3180361146916576628L;
	protected volatile IModuleZZZ objModule=null; //Das Modul, in der KernelUI - Variante wäre das die Dialogbox aus der das Program gestartet wird.	
	protected volatile String sProgramName = null;
	protected volatile String sModuleName = null;
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractProgramWithFlagZZZ() throws ExceptionZZZ {
		super();
		AbstractProgramNew_();
	}
	
	public AbstractProgramWithFlagZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractProgramNew_();
	}
	
	public AbstractProgramWithFlagZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);
		AbstractProgramNew_();
	}
		
	private boolean AbstractProgramNew_() throws ExceptionZZZ {
		
		//Da dies ein KernelProgram ist automatisch das FLAG IKERNELPROGRAM Setzen!!!
	    this.setFlag(IProgramZZZ.FLAGZ.ISPROGRAM.name(), true);	
				
		return true;
	}
	
	
	
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
	public boolean start() throws ExceptionZZZ {
		return this.startCustom();
	}
	
	//############################################
	//### FLAGZ IProgramZZZ
	//##########################################
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
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
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
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
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
			
	
}
