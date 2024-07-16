package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;

public class Kernel_FlagControlZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = -8540131780423723580L;
	public static String sTAG_NAME = "Z:FlagControl";
	
	public Kernel_FlagControlZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public Kernel_FlagControlZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		Kernel_FlagControlNew_();
	}
	
	private boolean Kernel_FlagControlNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			
		}//end main:
		return bReturn;
	 }//end function Kernel_FlagControlNew_
		
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return Kernel_FlagControlZZZ.sTAG_NAME;
	}				
}//End class