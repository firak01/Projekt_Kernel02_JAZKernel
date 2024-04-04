package basic.zBasic;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public class DummyTestObjectWithFlagZZZ extends AbstractObjectWithFlagZZZ<Object> implements IDummyTestObjectWithFlagZZZ{
	private static final long serialVersionUID = 2089503406657203691L;

	public DummyTestObjectWithFlagZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestObjectWithFlagZZZ() {
		super();
	}

	
	//###################################################
	//### FLAGS #########################################
	//###################################################
		
	@Override
	public boolean getFlag(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

}
