package basic.zBasic;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class DummyTestObjectWithFlagByInterfaceZZZ extends AbstractObjectWithFlagZZZ<Object> implements IDummyTestObjectWithFlagByInterfaceZZZ{
	private static final long serialVersionUID = 9214280301056845842L;

	public DummyTestObjectWithFlagByInterfaceZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestObjectWithFlagByInterfaceZZZ() {
		super();
	}

	//#############################################################
	//### FLAGZ, hier direkt eingebunden
	//#############################################################
	public enum FLAGZ{
		DUMMY01DIREKT,DUMMY02DIRECT
	}
	
	//#############################################################
	//### FLAGZLOCAL, hier direkt eingebunden
	//#############################################################
	public enum FLAGZLOCAL{
		DUMMY01LOCALDIREKT,DUMMY02LOCALDIRECT
	}
		
	//###################################################
	//### FLAGS #########################################
	//###################################################
	//Methoden hier, da im Interface eingebunden	
	@Override
	public boolean getFlag(IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................



}
