package basic.zKernel.config;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelExpressionIniConverterZZZ;
import basic.zKernel.file.ini.KernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonMapIniSolverZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelConfigEntryUtilZZZ {
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix
	 *  1 = Formel
	 *  2 = Expression
	 *  3 = Formel UND Expression
	 *  usw. denkbar fortsetzbar
	 *  
	 *  
	 * @param objFileIni
	 * @param sRaw
	 * @param bUseFormula
	 * @param hmVariable
	 * @param saFlagZpassed
	 * @param objsReturnValueExpressionSolved
	 * @param objsReturnValueConverted
	 * @param objsReturnValue
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 19.12.2019, 11:18:39
	 */
	public static int getValueExpressionSolvedAndConverted(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueExpressionSolved, ReferenceZZZ<String>objsReturnValueConverted, ReferenceZZZ<String>objsReturnValue) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			String sRawExpressionSolved = null;
			boolean bExpressionSolved = KernelConfigEntryUtilZZZ.getValueExpressionSolved(objFileIni, sRaw, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueExpressionSolved);							
			if(bExpressionSolved) {
				sRawExpressionSolved = objsReturnValueExpressionSolved.get();
				objsReturnValue.set(sRawExpressionSolved);
				iReturn = iReturn + 1;
			}else {
				sRawExpressionSolved = sRaw;
			}

			String sRawConverted = null;
			boolean bConverted = KernelConfigEntryUtilZZZ.getValueConverted(objFileIni, sRawExpressionSolved, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueConverted);
			if(bConverted) {
				sRawConverted = objsReturnValueExpressionSolved.get();
				iReturn = iReturn + 2;
			}else {
				sRawConverted = sRawExpressionSolved;
			}
						
			objsReturnValue.set(sRawConverted);						
		}//end main:
		return iReturn;
	}
	
	public static boolean getValueExpressionSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueExpressionSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(bUseFormula){
				String sValueExpressionSolved=null;
				boolean bAnyFormula = false;
				while(KernelExpressionIniSolverZZZ.isExpression(sRaw)){//Schrittweise die Formel auflösen.
					bAnyFormula = true;
										
					KernelExpressionIniSolverZZZ ex = new KernelExpressionIniSolverZZZ(objFileIni, hmVariable, saFlagZpassed);
					String stemp = ex.compute(sRaw);
					if(!StringZZZ.equals(stemp,sRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniSolver verändert von '" + sRaw + "' nach '" + stemp +"'");
					}					
					sRaw=stemp;//Sonst Endlosschleife.
				}
				sValueExpressionSolved = sRaw;
				if(bAnyFormula){
					objsReturnValueExpressionSolved.set(sValueExpressionSolved);	
					bReturn = true;
				}												
			}else{
				//Fall: Keine Formel soll interpretiert werden.
				//unverändert
			}		
		}//end main:
		return bReturn;
	}
	
	public static boolean getValueConverted(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueConverted) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(bUseFormula){
							
				//20190122: Ein Ansatz leere Werte zu visualisieren. Merke: <z:Empty/> wird dann als Wert erkannt und durch einen echten Leerstring erstetzt.
				//Merke: Der Expression-Wert kann sowohl direkt in der Zeile stehen, als auch erst durch einen Formel gesetzt worden sein.
				boolean bAnyExpression = false;				
				String sValueConverted = KernelExpressionIniConverterZZZ.getAsString(sRaw);
				if(!StringZZZ.equals(sRaw,sValueConverted)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sRaw + "' nach '" + sValueConverted +"'");
					bAnyExpression = true;						
				}					
				if(bAnyExpression){
					objsReturnValueConverted.set(sValueConverted);					
					bReturn = true;
				}	
			}else{
				//Fall: Keine Formel soll interpretiert werden.
				//unverändert
			}		
		}//end main:
		return bReturn;
	}
	
	
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix
	 *  1 = Json
	 *  2 = JsonArray
	 *  3 = JsonMap
	 *  usw. denkbar fortsetzbar
	 *  
	 *  
	 * @param objFileIni
	 * @param sRaw
	 * @param bUseJson	 
	 * @param saFlagZpassed
	 * @param objsReturnJson
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 11.07.2021
	 */
	public static boolean getValueJsonArraySolved(FileIniZZZ objFileIni, String sRaw, boolean bUseJson, String[] saFlagZpassed, ReferenceArrayZZZ<String>objalsReturnValueJsonSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(bUseJson){
				ArrayList<String> alsRawJsonSolved=null;
				boolean bAnyJson = false;
				
				ArrayList<String> alstemp = null;
				if(KernelJsonArrayIniSolverZZZ.isExpression(sRaw)){
														
					KernelJsonArrayIniSolverZZZ ex = new KernelJsonArrayIniSolverZZZ(objFileIni, saFlagZpassed);
					alstemp = ex.computeArrayList(sRaw);
					if(alstemp.isEmpty()) {				
					}else{
						bAnyJson = true;
					}
				}

				if(bAnyJson){
					objalsReturnValueJsonSolved.set(alstemp);	
					bReturn = true;
				}												
			}else{
				//Fall: Keine Formel soll interpretiert werden.
				//unverändert
			}		
		}//end main:
		return bReturn;
	}
	
	
	public static boolean getValueJsonMapSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseJson, String[] saFlagZpassed, ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(bUseJson){
				HashMap<String,String> hmValueSolved=null;
				boolean bAnyJson = false;
				
				HashMap<String,String> hmtemp = null;
				if(KernelJsonMapIniSolverZZZ.isExpression(sRaw)){
														
					KernelJsonMapIniSolverZZZ ex = new KernelJsonMapIniSolverZZZ(objFileIni, saFlagZpassed);
					hmtemp = ex.computeHashMap(sRaw);
					if(hmtemp.isEmpty()) {				
					}else{
						bAnyJson = true;
					}
				}

				if(bAnyJson){
					objhmReturnValueJsonSolved.set(hmtemp);	
					bReturn = true;
				}												
			}else{
				//Fall: Keine Formel soll interpretiert werden.
				//unverändert
			}		
		}//end main:
		return bReturn;
	}
}
