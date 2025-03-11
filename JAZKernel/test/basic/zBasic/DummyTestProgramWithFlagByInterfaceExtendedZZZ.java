package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

public class DummyTestProgramWithFlagByInterfaceExtendedZZZ extends AbstractDummyTestProgramWithFlagByInterfaceExtendedZZZ{
	private static final long serialVersionUID = -4468952293339389490L;

	public DummyTestProgramWithFlagByInterfaceExtendedZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestProgramWithFlagByInterfaceExtendedZZZ() throws ExceptionZZZ {
		super();
	}
	
	
	
	@Override
	public boolean startCustom() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	//#############################################################
	//### FLAGZ, hier direkt eingebunden
	//#############################################################
	public enum FLAGZ{
		DUMMY01DIREKT,DUMMY02DIRECT
	}
}
