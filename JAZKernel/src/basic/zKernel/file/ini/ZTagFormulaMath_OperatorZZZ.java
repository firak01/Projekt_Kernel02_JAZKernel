package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

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
	
	//###### Getter / Setter
	public String getOperator(){
		return this.sOperator;
	}
	public void setOperator(String sOperator){
		this.sOperator=sOperator;
	}
	
	//### Aus ITagBasicZZZ
	@Override
	public String getNameDefault() throws ExceptionZZZ {
		return ZTagFormulaMath_OperatorZZZ.sTAG_NAME;
	}

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
			
			String sOp = "+"; //+ als Default
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
	
	public Vector3ZZZ<String> computeFirstVector(String sLineWithExpression) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn = sLineWithExpression;
		String sReturnTag = null;
		boolean bIsError = false;
		main:{
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			vecReturn = this.parseFirstVector(sLineWithExpression);			
			sReturnTag = (String) vecReturn.get(1);		
									
			if(!StringZZZ.isEmpty(sReturnTag)){															
				this.setOperator(sReturnTag);
				String sValue01 = (String)vecReturn.get(0);
				String sValue02 = (String)vecReturn.get(2);
				
				try{
					sReturn = this.compute(sValue01, sValue02);
				}catch(ExceptionZZZ ez){
					bIsError=true;
				}					
			} //end if sExpression = ""
				
			if(bIsError) sReturn = sLineWithExpression;					
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
		//Wichtig, die Werte drum herum leer setzen
		vecReturn.replace("",sReturn,"");				
		return vecReturn;
	}

	//### Aus Interface IKernelExpressionIniZZZ	
		@Override
		public String parse(String sLineWithExpression) throws ExceptionZZZ{
			String sReturn = sLineWithExpression;
			String sReturnTag = null;
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
				
				Vector3ZZZ<String> vecAll = this.computeFirstVector(sLineWithExpression);
				//Nein, der Operator selbst ist ja ein z.B. "+",    sReturnTag = (String) vecAll.get(1);
				//this.setValue(sReturnTag);
				
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturn = VectorUtilZZZ.implode(vecAll);
			}//end main:
			return sReturn;
		}

		

		//### aus IConvertableZZZ
		@Override
		public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
			return false;
		}
}//End class