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
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.AbstractKernelIniTagSimpleZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.KernelCallIniSolverZZZ;
import basic.zKernel.file.ini.KernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonIniSolverZZZ;
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
			IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
			if(objReturn==null) {
				objReturn = new KernelConfigSectionEntryZZZ();
				objReturnReference.set(objReturn);
			}
			
			String sRawExpressionSolved = null;
			ReferenceZZZ<String> objsReturnValueExpressionSolved= new ReferenceZZZ<String>("");			
			boolean bExpressionSolved = KernelConfigEntryUtilZZZ.getValueExpressionSolved(objFileIni, sRaw, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueExpressionSolved);							
			if(bExpressionSolved) {
				objReturnReference.get().isExpression(true);
				if(bUseFormula) objReturnReference.get().isFormula(true);
				sRawExpressionSolved = objsReturnValueExpressionSolved.get();					
				iReturn = iReturn + 1;
			}else {
				sRawExpressionSolved = sRaw;
			}

			String sRawConverted = null;
			ReferenceZZZ<String> objsReturnValueConverted= new ReferenceZZZ<String>("");
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
	
	public static boolean getValueExpressionSolved(FileIniZZZ objFileIni, String sRawIn, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueExpressionSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!bUseFormula) break main;
			
			String sValueExpressionSolved=null;
			boolean bAnyFormula = false;
			String sRaw = sRawIn;
			while(KernelConfigEntryUtilZZZ.isExpression(sRaw,KernelZFormulaIniSolverZZZ.sTAG_NAME)){//Schrittweise die Formel auflösen.
				bAnyFormula = true;
									
				KernelZFormulaIniSolverZZZ ex = new KernelZFormulaIniSolverZZZ(objFileIni, hmVariable, saFlagZpassed);
				String stemp = ex.compute(sRaw);
				if(!StringZZZ.equals(stemp,sRaw)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniSolver verändert von '" + sRaw + "' nach '" + stemp +"'");
				}else {
					break;
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
	
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix

		
	 *  usw. denkbar fortsetzbar
	 *  
	 * 
	 */
	 //public static boolean getValueEncryptionSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseEncryption, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueEncryptionSolved, ReferenceZZZ<ICryptZZZ>objobjReturn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
	public static boolean getValueCallSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseCall, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		 boolean bReturn = false;
		 main:{			 			 								
	 		if(!bUseCall)break main;
	 		

		 	IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
			if(objReturn==null) objReturn = new KernelConfigSectionEntryZZZ();//Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.

			boolean bAnyFormula = false;
			
			KernelCallIniSolverZZZ objDummy = new KernelCallIniSolverZZZ();			
			while(objDummy.isExpression(sRaw)){//Schrittweise die Formel auflösen.
				objReturn.setRaw(sRaw);
				bAnyFormula = true;
									
				IKernelZZZ objKernel = null;
				if(objFileIni!=null) {
					objKernel = objFileIni.getKernelObject();
				}
				KernelCallIniSolverZZZ ex = new KernelCallIniSolverZZZ(objKernel, saFlagZpassed);
				ex.setEntry(objReturn);
				String sValue = null; String sFall=null;
				if(!bForFurtherProcessing) {
					sFall = "computeAsEntry";
					objReturn= ex.computeAsEntry(sRaw);
					sValue = objReturn.getValue();
					
				}else {
					sFall = "computeAsExpression";
					sValue = ex.computeAsExpression(sRaw);
					objReturn = ex.getEntry();					
				}
				
				if(!StringZZZ.equals(sValue,sRaw)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " - " + sFall + ": Value durch CallIniSolverZZZ verändert von '" + sRaw + "' nach '" + sValue +"'");
					objReturn.setRaw(sRaw);					
				}					
				sRaw=sValue;//Sonst Endlosschleife.							
			}

			if(bAnyFormula){
				bReturn = true;
			}				
					
			objReturnReference.set(objReturn);
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

			boolean bAnyEncryption = false;
			
			KernelEncryptionIniSolverZZZ objDummy = new KernelEncryptionIniSolverZZZ();			
			while(objDummy.isExpression(sRaw)){//Schrittweise die Formel auflösen.
				objReturn.setRawEncrypted(sRaw);
				bAnyEncryption = true;
									
				IKernelZZZ objKernel = null;
				if(objFileIni!=null) {
					objKernel = objFileIni.getKernelObject();
				}
				KernelEncryptionIniSolverZZZ ex = new KernelEncryptionIniSolverZZZ(objKernel, saFlagZpassed);
				ex.setEntry(objReturn);
				String sValue = null; String sFall=null;
				if(!bForFurtherProcessing) {
					sFall = "computeAsEntry";
					objReturn = ex.computeAsEntry(sRaw);
					sValue = objReturn.getValue();
					
				}else {
					sFall = "computeAsExpression";
					sValue = ex.computeAsExpression(sRaw);
					objReturn = ex.getEntry();					
				}
				
				if(!StringZZZ.equals(sValue,sRaw)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " - " + sFall + ": Value durch EncryptionIniSolverZZZ verändert von '" + sRaw + "' nach '" + sValue +"'");
					objReturn.setRawEncrypted(sRaw);
					objReturn.isDecrypted(true);
					objReturn.setRawDecrypted(sValue);
					objReturn.setValueDecrypted(sValue);					
				}					
				sRaw=sValue;//Sonst Endlosschleife.
				
				
				ICryptZZZ objCrypt = ex.getCryptAlgorithmType();//Zur weiteren Verwendung, z.B. zum erneuten Verschluesseln mit einem geaenderten Wert hier auch zurueckgeben.
				objReturn.setCryptAlgorithmType(objCrypt);				
			}

			if(bAnyEncryption){
				objReturn.isCrypt(true);
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
			 		
			 		boolean bUseJsonArray = objFileIni.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
					if(bUseJsonArray) {
						
						//ABER: Nicht alle flags aus saFlagZpassed mussen für JsonArray passen.
						//      Darum neu definieren
						KernelJsonArrayIniSolverZZZ exDummy03 = new KernelJsonArrayIniSolverZZZ();
						String[] saFlagZpassed03 = FlagZFassadeZZZ.seekFlagZrelevantForObject(objFileIni, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
					
						
						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.						
						boolean bAnyJsonArray = KernelConfigEntryUtilZZZ.getValueJsonArraySolved(objFileIni, sRaw, bUseJson, saFlagZpassed03, objalsReturnValueJsonSolved);			
						if(bAnyJsonArray) {
							iReturn = 5;
							break main;
						}else {										
						}					
					}
			 		
			 		boolean bUseJsonMap = objFileIni.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
					if(bUseJsonMap) {				
						
						//ABER: Nicht alle flags aus saFlagZpassed mussen für JsonMap passen
//				      Darum neu definieren
						KernelJsonMapIniSolverZZZ exDummy03 = new KernelJsonMapIniSolverZZZ();
						String[] saFlagZpassed03 = FlagZFassadeZZZ.seekFlagZrelevantForObject(objFileIni, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
					
						
						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.				                   
						boolean bAnyJsonMap = KernelConfigEntryUtilZZZ.getValueJsonMapSolved(objFileIni, sRaw, bUseJson, saFlagZpassed03, objhmReturnValueJsonSolved);			
						if(bAnyJsonMap) {
							iReturn = 6;
							break main;
						}else {										
						}
					}
					
					
			 		 			 
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
				if(KernelConfigEntryUtilZZZ.isExpression(sRaw,KernelJsonArrayIniSolverZZZ.sTAG_NAME)){
														
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
				if(KernelConfigEntryUtilZZZ.isExpression(sRaw,KernelJsonMapIniSolverZZZ.sTAG_NAME)){
														
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
	
	public static boolean isExpression(String sLine, String sExpressionTagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sLine, AbstractKernelIniTagSimpleZZZ.computeExpressionTagStarting(sExpressionTagName),false);
			if(btemp==false) break main;
		
			btemp = StringZZZ.contains(sLine, AbstractKernelIniTagSimpleZZZ.computeExpressionTagClosing(sExpressionTagName),false);
			if(btemp==false) break main;
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
}
