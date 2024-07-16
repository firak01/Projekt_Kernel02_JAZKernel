package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryption_KeyNumberZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	public static String sTAG_NAME = "Z:KeyNumber";
	
	public KernelEncryption_KeyNumberZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public KernelEncryption_KeyNumberZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionKeyNumberNew_();
	}
	
	private boolean KernelEncryptionKeyNumberNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			
		}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
		
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelEncryption_KeyNumberZZZ.sTAG_NAME;
	}				
}//End class