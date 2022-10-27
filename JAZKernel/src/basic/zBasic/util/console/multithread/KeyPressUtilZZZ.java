package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class KeyPressUtilZZZ implements IKeyPressConstantZZZ, IConstantZZZ{
		
	public static String computeKeyTag(char cKey) {
		String sReturn = IKeyPressConstantZZZ.sKeyTagOpen + cKey + IKeyPressConstantZZZ.sKeyTagClose;
		return sReturn;
	}
	
	public static String computeKeyTagStringYesNoCancel() {
		String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
		sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_noZZZ.getKey()) + "/";
		sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_cancelZZZ.getKey());	
		return sReturn;
	}
	
		public static String makeQuestionYesNoCancel(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(inputReader==null){
					String stemp = "'Scanner as InputReader'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sQuestion = StringZZZ.trim(sQuestionIn);
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
												
				KeyPressUtilZZZ.printlnQuestionYesNoCancel(sQuestion);
				
				boolean bGoon=false; String sInput = null;
				do {
					sInput = inputReader.nextLine();	                
	                if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
	                	bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)) {
                		//System.out.println("Abbruch eingegeben");
                		bGoon = true;
                	}else {
                		System.out.println("ungültige Eingabe");			                		
	                	bGoon=false;				                	
                	}				
				}while(!bGoon);
				sReturn = sInput;
			}//end main:
			return sReturn;
		}
		
		public static void printlnQuestionYesNoCancel(String sQuestionIn) throws ExceptionZZZ{

				String sQuestion = StringZZZ.trim(sQuestionIn);
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				
				if(sQuestion.endsWith("?")) {
					sQuestion = StringZZZ.stripRight(sQuestion, "?");
				}
				sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoCancel()+ "?";
				System.out.println( sQuestion);				
		}
}
