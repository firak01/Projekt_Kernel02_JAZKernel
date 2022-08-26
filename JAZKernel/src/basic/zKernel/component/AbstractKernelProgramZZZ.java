package basic.zKernel.component;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
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
	
	public String getProgramName(){
		if(StringZZZ.isEmpty(this.sProgramName)) {
			if(this.getFlag(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name())) {
				this.sProgramName = this.getClass().getName();
			}
		}
		return this.sProgramName;
	}
	public void setProgramName(String sProgramName){
		this.sProgramName = sProgramName;
	}	
	
	public void resetProgramUsed() {
		this.sProgramName = null;
		//this.sProgramAlias = null;
	}
	
	
	@Override
	public String getProgramAlias() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
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
	public IKernelModuleZZZ getModule() {
		return this.objModule;
	}
	public void setModule(IKernelModuleZZZ objModule) {
		this.objModule = objModule;
	}
			
	//### Methoden
	public abstract void reset();
}
