package basic.zBasic.util.log;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;

public abstract class AbstractLogStringZZZ extends AbstractObjectWithFlagZZZ implements ILogStringZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	protected static ILogStringZZZ objLogStringSingleton; //muss als Singleton static sein	
	
	//Das Fomat
	protected int[]iaFormat=null;
	
	@Override
	public String compute(String sLog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getFormatPositions() {
		if(ArrayUtilZZZ.isEmpty(this.iaFormat)) {
			iaFormat = this.getFormatPostitionsCustom(); 
		}
		return iaFormat;
	}
	
	@Override
	public void setFormatPositions(int[] iaFormat) {
		this.iaFormat=iaFormat;
	}
	
	@Override
	public int computeFormatPositionsNumber() {
		//FGL 20240421 - experimentell und nicht notwendig, solange ich den Weg der Rückumwandlung noch nicht kenne.
		int iReturn = 0;
		main:{
			int[]ia = this.getFormatPositions();
			if(ArrayUtilZZZ.isEmpty(ia))break main;
			
			int iPosition=0;
			for(int i : ia) {
				iPosition++;
				iReturn = iReturn + PrimeNumberZZZ.primePosition(i, iPosition);
			}
		}//end main:
		return iReturn;
	}

	@Override
	public abstract int[] getFormatPostitionsCustom();
		

	//###################################################
	//### FLAG: ILogStringZZZ
	//###################################################
		
	@Override
	public boolean getFlag(ILogStringZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(ILogStringZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(ILogStringZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ILogStringZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(ILogStringZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(ILogStringZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
