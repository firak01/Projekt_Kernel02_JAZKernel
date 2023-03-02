package basic.zKernel.component;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelProgramZZZ  extends KernelUseObjectZZZ implements IKernelProgramZZZ, IKernelModuleUserZZZ {
	protected IKernelModuleZZZ objModule=null; //Das Modul, z.B. die Dialogbox, in der das Program gestartet wird.
	protected String sModuleName=null;	
	protected String sProgramName=null;
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 */
	public AbstractKernelProgramZZZ() {
		super();
	}
	
	public AbstractKernelProgramZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel); 
		
		//Da dies ein KernelProgram ist automatisch das FLAG IKERNELPROGRAM Setzen!!!
		this.setFlag(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name(), true);		
	}
	
	public void setProgramName(String sProgramName){
		this.sProgramName = sProgramName;
	}
	
	
	//### Aus IKernelProgramZZZ
	@Override
	public String getProgramName(){
		if(StringZZZ.isEmpty(this.sProgramName)) {
			if(this.getFlag(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name())) {
				this.sProgramName = this.getClass().getName();
			}
		}
		return this.sProgramName;
	}
		
		
	
	@Override
	public void resetProgramUsed() {
		this.sProgramName = null;
		//this.sProgramAlias = null;
	}
	
	
	@Override
	public String getProgramAlias() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean getFlag(IKernelProgramZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelProgramZZZ.FLAGZ objEnumFlag, boolean bFlagValue) {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelProgramZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelProgramZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	
	
	
	public String readModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			IKernelModuleZZZ objModule = this.getModule();
			if(objModule!=null) {
				sReturn = objModule.getModuleName();
				if(StringZZZ.isEmpty(sReturn)) {
					sReturn = this.getKernelObject().getSystemKey();
				}
			}else {
				sReturn = this.getKernelObject().getSystemKey();
			}
		}//end main:
		return sReturn;
	}
	
	public String getModuleName() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sModuleName)) {
			this.sModuleName = this.readModuleName();
		}
		return this.sModuleName;
	}
	public void setModuleName(String sModuleName){
		this.sModuleName=sModuleName;
	}
	
	public void resetModuleUsed() {
		this.objModule = null;
		this.sModuleName = null;
	}
	
	//### Aus IKernelModuleUserZZZ	
	@Override
	public IKernelModuleZZZ getModule() {
		return this.objModule;
	}
	
	@Override
	public void setModule(IKernelModuleZZZ objModule) {
		this.objModule = objModule;
	}
	
	@Override
	public boolean getFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelModuleUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelModuleUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
			
	//### Methoden
	public abstract void reset();
}
