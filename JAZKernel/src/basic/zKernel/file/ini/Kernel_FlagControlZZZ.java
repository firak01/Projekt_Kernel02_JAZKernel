package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public class Kernel_FlagControlZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -8540131780423723580L;
	public static String sTAG_NAME = "Z:FlagControl";
	
	public Kernel_FlagControlZZZ() throws ExceptionZZZ{
		super();
		Kernel_FlagControlNew_();
	}
		
	
	private boolean Kernel_FlagControlNew_() throws ExceptionZZZ {
//		 boolean bReturn = false;
//		 main:{
//			if(this.getFlag("init")==true){
//				bReturn = true;
//				break main;
//			}
//			
//			
//		}//end main:
		return true;
	 }//end function Kernel_FlagControlNew_
		
	
	//###### Getter / Setter
	@Override
	public String getNameDefault() throws ExceptionZZZ{
		return Kernel_FlagControlZZZ.sTAG_NAME;
	}


	


	
}//End class