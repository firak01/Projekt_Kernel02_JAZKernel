package basic.zKernel.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelModuleZZZ  extends KernelUseObjectZZZ implements IKernelModuleZZZ {	
	protected String sModuleName=null;	
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelModuleZZZ() throws ExceptionZZZ {
		super();
		KernelModuleNew_("", null);
	}
	
	public AbstractKernelModuleZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel); 		
		KernelModuleNew_("", null);
	}
	
	public AbstractKernelModuleZZZ(IKernelZZZ objKernel, String sModule) throws ExceptionZZZ {
		super(objKernel);
		KernelModuleNew_(sModule, null);
	}
	
	private boolean KernelModuleNew_(String sModule, String[] saFlagUsed) throws ExceptionZZZ {
		boolean bReturn = false;		
		main:{			
			String stemp=null; boolean btemp=false; String sLog = null;
			this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": Initializing ModuleObject");
						
			//Weitere Flags setzen
			if(saFlagUsed!=null) {
				//setzen der übergebenen Flags
				for(int iCount = 0;iCount<=saFlagUsed.length-1;iCount++){
				  stemp = saFlagUsed[iCount];
				  if(!StringZZZ.isEmpty(stemp)){
					  btemp = setFlag(stemp, true);
					  if(btemp==false){
						  sLog = "the flag '" + stemp + "' is not available.";
						  this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
						  throw ez;		 
					  }
				  }
				}
				if(this.getFlag("INIT")==true){
					bReturn = true;
					break main; 
				}	
			}//end if saFlagUsed!=null
		
			//Da dies ein KernelProgram ist automatisch das FLAG IKERNELMODULE Setzen!!!
			this.setFlag(IKernelModuleZZZ.FLAGZ.ISKERNELMODULE.name(), true);
			this.setModuleName(sModule);
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public String readModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = this.getClass().getName();
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
		this.sModuleName = null;
	}
	
	//### Methoden
	public abstract void reset();
}
