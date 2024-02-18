package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;

public abstract class AbstractObjectWithFlagOnStatusListeningZZZ <T> extends AbstractObjectWithFlagZZZ<Object> implements IListenerObjectStatusLocalZZZ{
	private static final long serialVersionUID = 1L;

	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public AbstractObjectWithFlagOnStatusListeningZZZ() {	
		super();		
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(String sFlag) throws ExceptionZZZ {
		super();
		if(!StringZZZ.isEmpty(sFlag)) this.setFlag(sFlag, true);
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super();
		if(saFlag!=null){
			if(saFlag.length>=1){
				for(int icount =0; icount <= saFlag.length-1; icount++){
					if(!StringZZZ.isEmpty(saFlag[icount])){						
						boolean bFound = this.setFlag(saFlag[icount], true);
						if(!bFound) System.out.println(ReflectCodeZZZ.getPositionCurrent()+" - Flag not available: '" + saFlag[icount] +"'");
					}
				}
			}
		}
	}
	public AbstractObjectWithFlagOnStatusListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super();
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				String stemp = sKey;
				boolean btemp = this.setFlag(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap).", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
			}
		}
	}
	
	//######################################################################
	//### FLAGZ: aus IListenerObjectStatusLocalZZZ                 ##########################
	//######################################################################
	@Override
	public boolean getFlag(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IListenerObjectStatusLocalZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZUserZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
}
