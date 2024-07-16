package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
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
		KernelJavaCallClassNew_();
	}
			
	private boolean KernelJavaCallClassNew_() throws ExceptionZZZ {
//	private boolean KernelJavaCallClassNew_(String[] saFlagControlIn) throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 String stemp; boolean btemp; 
//	 main:{	 		
// 			//setzen der uebergebenen Flags	
//			if(saFlagControlIn != null){
//				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
//					stemp = saFlagControlIn[iCount];
//					btemp = setFlag(stemp, true);
//					if(btemp==false){
//						// String sKey = stemp;
//						// sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
//						// this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
//						// //Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!
//						ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//						throw ez;		 
//					}
//				}
//				if(this.getFlag("init")==true){
//					bReturn = true;
//					break main;
//				}
//			}	
//	 	}//end main:
		return true;
	 }//end function KernelJavaCallClassNew_
		
	
	//###### Getter / Setter
	public String getNameDefault() throws ExceptionZZZ{
		return KernelJavaCall_ClassZZZ.sTAG_NAME;
	}
}//End class