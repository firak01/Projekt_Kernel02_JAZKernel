package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public class KernelJavaCall_ClassZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 8075675133317942190L;
	public static String sTAG_NAME = "Z:Class";
	
	public KernelJavaCall_ClassZZZ() throws ExceptionZZZ{
		super();
		KernelJavaCallClassNew_();
	}
			
	private boolean KernelJavaCallClassNew_() throws ExceptionZZZ {
//		 boolean bReturn = false;
//		 main:{
//				if(this.getFlag("init")==true){
//					bReturn = true;
//					break main;
//				}
//				
//		 	}//end main:
			return true;
	 }//end function KernelJavaCallClassNew_
		
	
	//###### Getter / Setter
	
	
	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ{
		return KernelJavaCall_ClassZZZ.sTAG_NAME;
	}
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
		return false;
	}

}//End class