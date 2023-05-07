package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigEntryUtilZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelIniTagCascadedZZZ  extends AbstractKernelIniTagSimpleZZZ implements IKernelZFormulaIniZZZ{	
	public AbstractKernelIniTagCascadedZZZ() throws ExceptionZZZ{
		super();		
	}
		
	public AbstractKernelIniTagCascadedZZZ(String[] saFlag) throws ExceptionZZZ{		
		AbstractKernelIniTagNew_(saFlag);
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniTagNew_(null);
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniTagNew_(saFlag);
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ{
		super(objKernelUsing);
		AbstractKernelIniTagNew_(null);
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ{
		super(objKernelUsing);
		AbstractKernelIniTagNew_(saFlag);
	}
	
	
	private boolean AbstractKernelIniTagNew_(String[] saFlagControlIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
	 main:{
		 	
	 	//try{	 		
	 			//setzen der uebergebenen Flags	
				if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){
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
		
	//In den erbenden Klassen werden darin die enthaltenden Tags aufgelöst
	abstract public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
	
}//End class