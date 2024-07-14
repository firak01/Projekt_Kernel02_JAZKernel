package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.formula.AbstractIniTagSimpleZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelJavaCall_ClassZZZ  extends AbstractIniTagSimpleZZZ{
	public static String sTAG_NAME = "Z:Class";
	
	public KernelJavaCall_ClassZZZ() throws ExceptionZZZ{
		super();
	}
			
	public KernelJavaCall_ClassZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJavaCallClassNew_(saFlag);
	}
	
	private boolean KernelJavaCallClassNew_(String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{	 		
 			//setzen der uebergebenen Flags	
			if(saFlagControlIn != null){
				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
					stemp = saFlagControlIn[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){
						// String sKey = stemp;
						// sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
						// this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
						// //Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!
						ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;		 
					}
				}
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
			}	
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
		
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelJavaCall_ClassZZZ.sTAG_NAME;
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValue(String sValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRaw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRaw(String sRaw) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IKernelConfigSectionEntryZZZ getEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		// TODO Auto-generated method stub
		
	}					
}//End class