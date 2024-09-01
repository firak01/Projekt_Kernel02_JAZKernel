package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public class ZTagFormulaMath_OperatorZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 3096748072633354679L;
	public static String sTAG_NAME = "Z:op";
	private String sOperator = null;
		
	public ZTagFormulaMath_OperatorZZZ() throws ExceptionZZZ{
		super();
		ZTagFormulaMath_OperatorNew_();
	}
		
	private boolean ZTagFormulaMath_OperatorNew_() throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 main:{
//			if(this.getFlag("init")==true){
//				bReturn = true;
//				break main;
//			}
//			
//	 	}//end main:
		return true;
	 }//end function ZTagFormulaMath_OperatorNew_
	

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
	
		public Vector solveFirstVector(String sLineWithExpression) throws ExceptionZZZ{
			Vector vecReturn = new Vector();
			main:{
				if(StringZZZ.isEmpty(sLineWithExpression)) break main;
				
				vecReturn = this.parseFirstVector(sLineWithExpression);			
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
					//if(vecReturn.size()==0) vecReturn.add(0,"");					
					if(vecReturn.size()>=1) {
						vecReturn.remove(0);
						vecReturn.add(0,"");//Anders als normal, hier den 0er Wert leersetzen, da nur noch das Ergebnis aus compute(...,...) uebrigbleiben soll
					}else if(vecReturn.size()==0) {
						vecReturn.add(0,"");
					}
					
					if(vecReturn.size()>=2) vecReturn.remove(1);
					vecReturn.add(1, sExpression);
					
					if(vecReturn.size()>=3) {
						vecReturn.remove(2);
						vecReturn.add(2,"");//Anders als normal, hier den 0er Wert leersetzen, da nur noch das Ergebnis aus compute(...,...) uebrigbleiben soll
					}else if(vecReturn.size()==2) {
						vecReturn.add(2,"");
					}					
				}					
			}//end main:
			return vecReturn;
		}

	
	
	//###### Getter / Setter
	public String getExpressionTagName(){
		return ZTagFormulaMath_OperatorZZZ.sTAG_NAME;
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
		public String parse(String sLineWithExpression) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(StringZZZ.isEmptyTrimmed(sLineWithExpression)) break main;
				
				
				//links vom Operator
				ZTagFormulaMath_ValueZZZ objValue01 = new ZTagFormulaMath_ValueZZZ();
				if(objValue01.isExpression(sLineWithExpression)){
					sLineWithExpression = objValue01.parse(sLineWithExpression);						
				}	
				
				//rechts vom Operator
				ZTagFormulaMath_ValueZZZ objValue02 = new ZTagFormulaMath_ValueZZZ();
				if(objValue02.isExpression(sLineWithExpression)){
					sLineWithExpression = objValue02.parse(sLineWithExpression);						
				}	
				
				Vector vecAll = this.solveFirstVector(sLineWithExpression);
										
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturn = VectorZZZ.implode(vecAll);
				
				this.setValue(sReturn);
			}//end main:
			return sReturn;
		}

		@Override
		public String getNameDefault() throws ExceptionZZZ {
			return ZTagFormulaMath_OperatorZZZ.sTAG_NAME;
		}

		//### aus IConvertableZZZ
		@Override
		public boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
			return false;
		}
}//End class