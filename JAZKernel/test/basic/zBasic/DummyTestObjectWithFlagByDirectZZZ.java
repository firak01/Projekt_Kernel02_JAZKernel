package basic.zBasic;

public class DummyTestObjectWithFlagByDirectZZZ extends AbstractObjectWithFlagZZZ<Object> implements IDummyTestObjectWithFlagByDirectZZZ{
	private static final long serialVersionUID = 9214280301056845842L;

	public DummyTestObjectWithFlagByDirectZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestObjectWithFlagByDirectZZZ() {
		super();
	}

	//#############################################################
	//### FLAGZ, hier direkt eingebunden
	//#############################################################
	public enum FLAGZ{
		DUMMYF01DIREKT,DUMMY02DIRECT
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
	//... Keine MEthoden hier, da nicht im Interface eingebunden	
//	@Override
//	public boolean getFlag(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) {
//		return this.getFlag(objEnumFlag.name());
//	}	
//	
//	@Override
//	public boolean setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
//		return this.setFlag(objEnumFlag.name(), bFlagValue);
//	}
//
//	@Override
//	public boolean[] setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
//		boolean[] baReturn=null;
//		main:{
//			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
//				baReturn = new boolean[objaEnumFlag.length];
//				int iCounter=-1;
//				for(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
//					iCounter++;
//					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
//					baReturn[iCounter]=bReturn;
//				}
//				
//				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
//				//    Es wird entfernt.
//				this.setFlag(IFlagZUserZZZ.FLAGZ.INIT, false);
//			}
//		}//end main:
//		return baReturn;
//	}
//
//	@Override
//	public boolean proofFlagExists(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
//		return this.proofFlagExists(objEnumFlag.name());
//	}
//
//	@Override
//	public boolean proofFlagSetBefore(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
//		return this.proofFlagSetBefore(objEnumFlag.name());
//	}
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................



}
