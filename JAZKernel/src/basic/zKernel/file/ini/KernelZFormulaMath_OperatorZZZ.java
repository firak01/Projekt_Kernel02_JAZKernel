package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZFormulaIniZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaMath_OperatorZZZ  extends AbstractKernelIniTagZZZ{ //KernelUseObjectZZZ implements IKernelZFormulaIniZZZ{
	public static String sTAG_NAME = "Z:op";
	private String sOperator = null;
		
	public KernelZFormulaMath_OperatorZZZ() throws ExceptionZZZ{
		super();
	}
		
	public KernelZFormulaMath_OperatorZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	public KernelZFormulaMath_OperatorZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionMathSolverNew_(saFlag);
	}
	
	
	private boolean KernelExpressionMathSolverNew_(String[] saFlagControlIn) throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 String stemp; boolean btemp; 
//	 main:{
//		 	
//	 	//try{	 		
//	 			//setzen der übergebenen Flags	
//				if(saFlagControlIn != null){
//					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
//						stemp = saFlagControlIn[iCount];
//						btemp = setFlag(stemp, true);
//						if(btemp==false){
//							ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
//							throw ez;		 
//						}
//					}
//					if(this.getFlag("init")==true){
//						bReturn = true;
//						break main;
//					}
//				}			
//	 	}//end main:
//		return bReturn;
		
		return true;
	 }//end function KernelExpressionMathSolverNew_
	
	
	
	public String compute(String sValue01, String sValue02) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sValue01)) break main;
			if(StringZZZ.isEmpty(sValue02)) break main;
			try{
				String sOperator = this.getOperator();
				
			//erst ab Java 1.7 kann auf Strings in einenr Switch - Anweisung gepürft werden.
//			switch(sOperator){
//			case "*":
//				break;
//			case "+":
//				
//			case "-":
//			case "/":
//			case else:
//				//+ als Default
//				
//			}
			
			String sOp = "+";
			if(!StringZZZ.isEmpty(sOperator)){
				sOp = sOperator.trim();				
			}
			
			if(StringZZZ.isFloatExplizit(sValue01) || StringZZZ.isFloatExplizit(sValue02)){ //Explizit: Damit nicht Integer Werte (die auch gültig als Float umgerechnet werden könnten, hier nicht umgeändert werden)
				Float fltA = new Float(sValue01);
				Float fltB = new Float(sValue02);
				
				float fReturn = 0.0f;
				if(sOp.equals("+")){
					fReturn = fltA.floatValue() + fltB.floatValue();				
				}else if(sOp.equals("*")){
					fReturn = fltA.floatValue() * fltB.floatValue();
				}else{
					ExceptionZZZ ez = new ExceptionZZZ( "the operator '" + sOperator + "' is not handled yet.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
				Float fltReturn = new Float(fReturn);
				sReturn = fltReturn.toString();

			}else{
				Integer intA = new Integer(sValue01);
				Integer intB = new Integer(sValue02);
				int iReturn = 0;
				if(sOp.equals("+")){
					iReturn = intA.intValue() + intB.intValue();				
				}else if(sOp.equals("*")){
					iReturn = intA.intValue() * intB.intValue();
				}else{
					ExceptionZZZ ez = new ExceptionZZZ( "the operator '" + sOperator + "' is not handled yet.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
				Integer intReturn = new Integer(iReturn);
				sReturn = intReturn.toString();
				}
			}catch(NumberFormatException e){
				String stemp = "Fehler bei Berechung: '" + sValue01 + "'_'" + sOperator + "'_'" + sValue02 + "'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + stemp);
				ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
									
		}//end main:
		return sReturn;
	}
	
		public Vector computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ{
			Vector vecReturn = new Vector();
			main:{
				if(StringZZZ.isEmpty(sLineWithExpression)) break main;
				
				vecReturn = this.computeExpressionFirstVector(sLineWithExpression);			
				String sExpression = (String) vecReturn.get(1);		
				boolean bIsError = false;
				if(!StringZZZ.isEmpty(sExpression)){															
					this.setOperator(sExpression);
					String sValue01 = (String)vecReturn.get(0);
					String sValue02 = (String)vecReturn.get(2);
					
					try{
						sExpression = this.compute(sValue01, sValue02);
					}catch(ExceptionZZZ ez){
						bIsError=true;
					}					
				} //end if sExpression = ""
					
				//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
				if(!bIsError){
				vecReturn.remove(0)	;
				vecReturn.add(0,"");
				
				vecReturn.remove(1);
				vecReturn.add(1, sExpression);
				
				vecReturn.remove(2);
				vecReturn.add(2,"");
				}					
			}//end main:
			return vecReturn;
		}

	
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return KernelZFormulaMath_OperatorZZZ.sTAG_NAME;
	}
	
	
	//++++ 
	public String getOperator(){
		return this.sOperator;
	}
	public void setOperator(String sOperator){
		this.sOperator=sOperator;
	}

	//### Aus Interface IKernelExpressionIniZZZ	
		@Override
		public String compute(String sLineWithExpression) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
				
				
				//links vom Operator
				KernelZFormulaMath_ValueZZZ objValue01 = new KernelZFormulaMath_ValueZZZ();
				if(objValue01.isExpression(sLineWithExpression)){
					sLineWithExpression = objValue01.compute(sLineWithExpression);						
				}	
				
				//rechts vom Operator
				KernelZFormulaMath_ValueZZZ objValue02 = new KernelZFormulaMath_ValueZZZ();
				if(objValue02.isExpression(sLineWithExpression)){
					sLineWithExpression = objValue02.compute(sLineWithExpression);						
				}	
				
				Vector vecAll = this.computeExpressionAllVector(sLineWithExpression);
										
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturn = VectorZZZ.implode(vecAll);
				
				this.setValue(sReturn);
			}//end main:
			return sReturn;
		}

		@Override
		public String convert(String sLine) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean isStringForComputeRelevant(String sExpressionToProof)
				throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isStringForConvertRelevant(String sToProof) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}
				
}//End class