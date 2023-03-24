package basic.zKernel.config;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniConverterZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.KernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonMapIniSolverZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelConfigEntryUtilZZZ {
	/** Die statischen Methoden dieser Klasse leisten mehr als nur die ...Solver... aufzurufen.
	 *  Durch den Rückgabewert können dann die Details im letztendlich benötigten IKernelConfigSectionEntryZZZ object gesetzt werden.
	 *  Also z.B. bJson, bJsonMap, etc...
	 *  
	 * Nur true / false zurückzugeben reicht daher nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix
	 *  1 = Formel
	 *  2 = Expression
	 *  3 = Formel UND Expression
	 *  
	 *  5 = JsonMap
	 *  6 = JsonArray
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
	public static int getValueExpressionSolvedAndConverted(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		int iReturn = 0;
		main:{

			String sRawExpressionSolved = null;
			ReferenceZZZ<String> objsReturnValueExpressionSolved= new ReferenceZZZ<String>();			
			boolean bExpressionSolved = KernelConfigEntryUtilZZZ.getValueExpressionSolved(objFileIni, sRaw, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueExpressionSolved);							
			if(bExpressionSolved) {
				objReturnReference.get().isExpression(true);
				sRawExpressionSolved = objsReturnValueExpressionSolved.get();				
				iReturn = iReturn + 1;
			}else {
				sRawExpressionSolved = sRaw;
			}

			String sRawConverted = null;
			ReferenceZZZ<String> objsReturnValueConverted= new ReferenceZZZ<String>();
			boolean bConverted = KernelConfigEntryUtilZZZ.getValueConverted(objFileIni, sRawExpressionSolved, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueConverted);
			if(bConverted) {
				objReturnReference.get().isConverted(true);
				sRawConverted = objsReturnValueExpressionSolved.get();				
				iReturn = iReturn + 2;
			}else {
				sRawConverted = sRawExpressionSolved;
			}
			objReturnReference.get().setValue(sRawConverted);
		}//end main:
		return iReturn;
	}
	
	public static boolean getValueExpressionSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueExpressionSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!bUseFormula) break main;
			
			String sValueExpressionSolved=null;
			boolean bAnyFormula = false;
			while(KernelZFormulaIniSolverZZZ.isExpression(sRaw)){//Schrittweise die Formel auflösen.
				bAnyFormula = true;
									
				KernelZFormulaIniSolverZZZ ex = new KernelZFormulaIniSolverZZZ(objFileIni, hmVariable, saFlagZpassed);
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
		}//end main:
		return bReturn;
	}
	
	public static boolean getValueConverted(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueConverted) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!bUseFormula) break main;
							
			//20190122: Ein Ansatz leere Werte zu visualisieren. Merke: <z:Empty/> wird dann als Wert erkannt und durch einen echten Leerstring erstetzt.
			//Merke: Der Expression-Wert kann sowohl direkt in der Zeile stehen, als auch erst durch einen Formel gesetzt worden sein.
			boolean bAnyExpression = false;				
			String sValueConverted = KernelZFormulaIniConverterZZZ.getAsString(sRaw);
			if(!StringZZZ.equals(sRaw,sValueConverted)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sRaw + "' nach '" + sValueConverted +"'");
				bAnyExpression = true;						
			}					
			if(bAnyExpression){
				objsReturnValueConverted.set(sValueConverted);					
				bReturn = true;
			}				
		}//end main:
		return bReturn;
	}
	
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix

		
	 *  usw. denkbar fortsetzbar
	 *  
	 * 
	 */
	 //public static boolean getValueEncryptionSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseEncryption, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueEncryptionSolved, ReferenceZZZ<ICryptZZZ>objobjReturn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
	public static boolean getValueEncryptionSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseEncryption, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		 boolean bReturn = false;
		 main:{			 			 								
	 		if(!bUseEncryption)break main;
	 		

		 	IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
			if(objReturn==null) objReturn = new KernelConfigSectionEntryZZZ();//Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
				 		
	 		String sValueEncryptionSolved=null;
			boolean bAnyFormula = false;
			
			KernelEncryptionIniSolverZZZ objDummy = new KernelEncryptionIniSolverZZZ();			
			while(objDummy.isExpression(sRaw)){//Schrittweise die Formel auflösen.
				objReturn.setRaw(sRaw);
				bAnyFormula = true;
									
				IKernelZZZ objKernel = null;
				if(objFileIni!=null) {
					objKernel = objFileIni.getKernelObject();
				}
				KernelEncryptionIniSolverZZZ ex = new KernelEncryptionIniSolverZZZ(objKernel, saFlagZpassed);
				if(!bForFurtherProcessing) {
					String stemp = ex.compute(sRaw);
					if(!StringZZZ.equals(stemp,sRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": (Fall a) Value durch EncryptionIniSolverZZZ verändert von '" + sRaw + "' nach '" + stemp +"'");
						objReturn.setRawEncrypted(sRaw);
						objReturn.isDecrypted(true);
						objReturn.setRawDecrypted(stemp);
					}					
					sRaw=stemp;//Sonst Endlosschleife.
				}else {
					String stemp = ex.computeAsExpression(sRaw);
					if(!StringZZZ.equals(stemp,sRaw)){						
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": (Fall b) Value durch EncryptionIniSolverZZZ verändert von '" + sRaw + "' nach '" + stemp +"'");
						objReturn.setRawEncrypted(sRaw);
						objReturn.isDecrypted(true);
						objReturn.setRawDecrypted(stemp);
					}					
					sRaw=stemp;//Sonst Endlosschleife.
				}
				ICryptZZZ objCrypt = ex.getCryptAlgorithmType();//Zur weiteren Verwendung, z.B. zum erneuten Verschluesseln mit einem geaenderten Wert hier auch zurueckgeben.
				objReturn.setCryptAlgorithmType(objCrypt);				
			}
			//sValueEncryptionSolved = sRaw;
			if(bAnyFormula){
				
				//objsReturnValueEncryptionSolved.set(sValueEncryptionSolved);	//TODO raus
				bReturn = true;
			}				
					
			objReturnReference.set(objReturn);
		 }//end main:
		 return bReturn;
	 }
	
	
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix
	 *  5 = JsonArray
	 *  6 = JsonMap
	 *  usw. denkbar fortsetzbar
	 *  
	 * 
	 */
	 public static int getValueJsonSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseJson, String[] saFlagZpassed, ReferenceArrayZZZ<String>objalsReturnValueJsonSolved,ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved) throws ExceptionZZZ{
		 int iReturn = 0;
		 main:{			 			 								
			 		if(!bUseJson)break main;
			 		
			 		//boolean bUseJsonArray = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name());
					//if(bUseJsonArray) {
						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.						
						boolean bAnyJsonArray = KernelConfigEntryUtilZZZ.getValueJsonArraySolved(objFileIni, sRaw, bUseJson, saFlagZpassed, objalsReturnValueJsonSolved);			
						if(bAnyJsonArray) {
							iReturn = 5;
							break main;
						}else {										
						}					
					//}
			 		
			 		//boolean bUseJsonMap = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON_MAP.name());
					//if(bUseJsonMap) {				
						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.				                   
						boolean bAnyJsonMap = KernelConfigEntryUtilZZZ.getValueJsonMapSolved(objFileIni, sRaw, bUseJson, saFlagZpassed, objhmReturnValueJsonSolved);			
						if(bAnyJsonMap) {
							iReturn = 6;
							break main;
						}else {										
						}
					//}
					
					
			 		 			 
		 }//end main:
		 return iReturn;
	 }
	
	 /*
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
