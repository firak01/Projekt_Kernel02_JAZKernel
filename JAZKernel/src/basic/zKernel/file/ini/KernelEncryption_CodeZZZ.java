package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.formula.AbstractIniTagSimpleZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryption_CodeZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5159343273872632561L;
	public static String sTAG_NAME = "Z:Code";
	
	public KernelEncryption_CodeZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public KernelEncryption_CodeZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionCodeNew_();
	}
	
	private boolean KernelEncryptionCodeNew_() throws ExceptionZZZ {
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
		return KernelEncryption_CodeZZZ.sTAG_NAME;
	}					
}//End class