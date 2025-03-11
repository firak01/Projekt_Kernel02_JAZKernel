package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

public class DummyTestProgramWithStatusByInterfaceExtendedZZZ extends AbstractDummyTestProgramWithStatusByInterfaceExtendedZZZ{
	private static final long serialVersionUID = -4468952293339389490L;

	public DummyTestProgramWithStatusByInterfaceExtendedZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestProgramWithStatusByInterfaceExtendedZZZ() throws ExceptionZZZ {
		super();
	}
	
	//+++++++++++++++++++++++++++
	//+++ CUSTOM STATUS LISTENING
	//+++++++++++++++++++++++++++
	@Override
	public boolean startCustom() throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
	}

		@Override
		public boolean start() throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
			return true;
		}

		
}
