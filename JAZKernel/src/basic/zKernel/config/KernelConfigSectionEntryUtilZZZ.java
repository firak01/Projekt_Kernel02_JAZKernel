package basic.zKernel.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.AbstractIniTagSimpleZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.KernelCallIniSolverZZZ;
import basic.zKernel.file.ini.KernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniConverterZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_PathZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_NullZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelConfigSectionEntryUtilZZZ implements IConstantZZZ{
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
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
			if(objReturn==null) {
				objReturn = new KernelConfigSectionEntryZZZ();
				objReturnReference.set(objReturn);
			}
			
			String sRawExpressionSolved = null;
			ReferenceZZZ<String> objsReturnValueExpressionSolved= new ReferenceZZZ<String>("");			
			boolean bExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionSolved(objFileIni, sRaw, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueExpressionSolved);							
			if(bExpressionSolved) {
				objReturnReference.get().isExpression(true);
				if(bUseFormula) objReturnReference.get().isFormula(true);
				String sValueSolved = objsReturnValueExpressionSolved.get();					
				objReturnReference.get().setValueAsExpression(sValueSolved);				
				sRawExpressionSolved = sValueSolved;
				iReturn = iReturn + 1;
			}else {
				sRawExpressionSolved = sRaw;
			}

			String sRawConverted = null;
			ReferenceZZZ<String> objsReturnValueConverted= new ReferenceZZZ<String>("");
			boolean bConverted = KernelConfigSectionEntryUtilZZZ.getValueConverted(objFileIni, sRawExpressionSolved, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueConverted);
			if(bConverted) {				
				objReturnReference.get().isConverted(true);
				
				
				String sValueAsConversion= objReturnReference.get().getValue();
				objReturnReference.get().setValueAsConversion(sValueAsConversion);
				
				sRawConverted = objsReturnValueConverted.get();				
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
			
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sValueExpressionSolved=null;
			boolean bAnyFormula = false;
			String sRaw = sRawIn;
			String sRawOld = sRaw;
			while(KernelConfigSectionEntryUtilZZZ.isExpression(sRaw,KernelZFormulaIniSolverZZZ.sTAG_NAME)){//Schrittweise die Formel auflösen.
				bAnyFormula = true;
									
				KernelZFormulaIniSolverZZZ ex = new KernelZFormulaIniSolverZZZ(objFileIni, hmVariable, saFlagZpassed);
				String stemp = ex.parse(sRaw);
				if(!StringZZZ.equals(stemp,sRaw)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniSolver verändert von '" + sRaw + "' nach '" + stemp +"'");
				}else {
					break;
				}
				sRaw=stemp;//Sonst Endlosschleife.
				if(sRawOld.equals(sRaw)) break;
				sRawOld = sRaw;
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
	 		
	 		if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

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
				
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
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
	 		
	 		if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
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
			 		
			 		if(objFileIni==null){
						String stemp = "'IniFile'";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
			 		
			 		boolean bUseJsonArray = objFileIni.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
					if(bUseJsonArray) {
						
						
						//ABER: Nicht alle flags aus saFlagZpassed mussen für JsonArray passen.
						//      Darum neu definieren
						KernelJsonArrayIniSolverZZZ exDummy03 = new KernelJsonArrayIniSolverZZZ();
						String[] saFlagZpassed03 = FlagZFassadeZZZ.seekFlagZrelevantForObject(objFileIni, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
					
						
						//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.						
						boolean bAnyJsonArray = KernelConfigSectionEntryUtilZZZ.getValueJsonArraySolved(objFileIni, sRaw, bUseJson, saFlagZpassed03, objalsReturnValueJsonSolved);			
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
						boolean bAnyJsonMap = KernelConfigSectionEntryUtilZZZ.getValueJsonMapSolved(objFileIni, sRaw, bUseJson, saFlagZpassed03, objhmReturnValueJsonSolved);			
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
				if(objFileIni==null){
					String stemp = "'IniFile'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				ArrayList<String> alsRawJsonSolved=null;
				boolean bAnyJson = false;
				
				ArrayList<String> alstemp = null;
				if(KernelConfigSectionEntryUtilZZZ.isExpression(sRaw,KernelJsonArrayIniSolverZZZ.sTAG_NAME)){
					
					//Ohne diese "minimal notwendigen "Flags macht diese utility - Methode keinen Sinn.
					//Dann noch die übergebenen Flags hinzunehmen.
					String[]saFlagZ = {IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() , IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()};
					saFlagZ =StringArrayZZZ.appendMissing(saFlagZ, saFlagZpassed, null);
										
					KernelJsonArrayIniSolverZZZ ex = new KernelJsonArrayIniSolverZZZ(objFileIni, saFlagZ);
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
			if(!bUseJson) break main;
			
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			boolean bAnyJson = false;
			
			HashMap<String,String> hmtemp = null;
			if(KernelConfigSectionEntryUtilZZZ.isExpression(sRaw,KernelJsonMapIniSolverZZZ.sTAG_NAME)){
				//Ohne diese "minimal notwendigen "Flags macht diese utility - Methode keinen Sinn.
				//Dann noch die übergebenen Flags hinzunehmen.
				String[]saFlagZ = {IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() , IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()};
				saFlagZ =StringArrayZZZ.appendMissing(saFlagZ, saFlagZpassed, null);
				
				KernelJsonMapIniSolverZZZ ex = new KernelJsonMapIniSolverZZZ(objFileIni, saFlagZ);
				hmtemp = ex.computeLinkedHashMap(sRaw);
				if(hmtemp.isEmpty()) {				
				}else{
					bAnyJson = true;
				}
			}

			if(bAnyJson){
				objhmReturnValueJsonSolved.set(hmtemp);	
				bReturn = true;
			}													
		}//end main:
		return bReturn;
	}
	
	public static boolean isExpression(String sLine, String sExpressionTagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Merke: z:null und z:empty sind nicht Tagspezifische Ausdrücke, gelten als Conversion
			
			//Merke: Contains reicht nicht. Die Positionen sind auch wichtig.
			boolean bAsTagFound = XmlUtilZZZ.containsTag(sLine, sExpressionTagName, false);
			if(!bAsTagFound) break main;

			bReturn = true;
		}//end main
		return bReturn;
	}
	
	public static boolean isConvertable(String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			//Anders als Tags sind NULL und EMPTY Werte Conversions. 
			//Sie entstehen sogar, wenn nur der Leerstring konfiguriert wird
			boolean bIsEmptyTag = StringZZZ.contains(sLine, XmlUtilZZZ.computeTagEmpty(KernelZFormulaIni_EmptyZZZ.sTAG_NAME));
			if(bIsEmptyTag) {
				bReturn = true;
				break main;
			}
			boolean bIsNullTag = StringZZZ.contains(sLine, XmlUtilZZZ.computeTagEmpty(ZTagFormulaIni_NullZZZ.sTAG_NAME));
			if(bIsNullTag) {
				bReturn = true;
				break main;
			}
			
		}//end main:
		return bReturn;
		
	}
	
	public static String getValueExpressionTagSurroundingRemoved(String sValueExpression, String sTagStart, String sTagEnd) throws ExceptionZZZ {
		String sReturn = sValueExpression;
		main:{
			if(StringZZZ.isEmpty(sValueExpression)) break main;
			if(StringZZZ.isEmpty(sTagStart)) break main;
			if(StringZZZ.isEmpty(sTagEnd)) break main;
			
			Vector<String>vecReturn = StringZZZ.vecMidFirst(sValueExpression, sTagStart, sTagEnd, false);//Also ohne Tags holen
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);

			sReturn = VectorZZZ.implode(vecReturn);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression per Schleife veraendert nach = '"+sReturn+"'");
			
		}//end main:
		return sReturn;
	}	
	
	public static void getValueExpressionTagSurroundingRemoved(Vector<String>vecReturn, String sTagStart, String sTagEnd) throws ExceptionZZZ {		
		main:{
			if(vecReturn==null)break main;
			if(StringZZZ.isEmpty(sTagEnd) && StringZZZ.isEmpty(sTagStart))break main;
			
			
			//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
			String sBefore = vecReturn.get(0);
			String sValue = vecReturn.get(1);
			String sRest = vecReturn.get(2);
			
			//WICHTIG: Die <Z>-Tags sind am Anfang/Ende UND es sind noch andere Formel Z-Tags "<Z:... im String vorhanden.
			//         Dann loesche sie nicht raus, auch nicht im Ergebnisstring.
			//         Will man sie loswerden, dann muessen halt die inneren Z: - Tags aufgeloest werden.
			//MERKE:   Dazu muessen ja dann auch die passenden Flags gesetzt sein. Das Setzen eines Flags ist ja vielleicht in dem Einzelfall nicht gewuenscht.
			boolean bSkip = false;
			if(StringZZZ.contains(sValue, "<Z:")) {
				if(StringZZZ.endsWithIgnoreCase(sBefore, "<Z>")) {
					if(!bSkip) bSkip = true;
				}
				if(StringZZZ.startsWithIgnoreCase(sRest, "</Z>")) {
					if(!bSkip) bSkip = true;
				}
			}
			if(bSkip) {
				System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Weitere Formeln im String vermutet. Entferne aeusser Z-Tags nicht.");
				break main;
			}
			
			
			String sBeforeOld = sBefore;
			String sRestOld = sRest;
			while(StringZZZ.endsWithIgnoreCase(sBefore, sTagStart) & StringZZZ.startsWithIgnoreCase(sRest, sTagEnd)) {
			
			if(!StringZZZ.isEmpty(sBefore)){
				if(vecReturn.size()>=1) vecReturn.removeElementAt(0);
				//Nachbereitung: Ein ggfs. /Z-Tag am Anfang des Rest entfernen
				//Hier: Nur dann, wenn es nicht der String selber ist.
				//if(!sRest.equals(sTagEnd) & StringZZZ.startsWithIgnoreCase(sRest, sTagEnd)) {
				//Nein: Der Z-Tag einzeln hat nur Sinn, wenn noch andere Z: Tags drin enthalten sind. Wird weiter oben schon erledigt.				
				if(StringZZZ.endsWithIgnoreCase(sBefore, sTagStart)) {
					sBefore = StringZZZ.leftback(sBefore, sTagStart);
				}
				vecReturn.add(0, sBefore);
			}else{
				vecReturn.add(0,"");
			}
													
			if(vecReturn.size()>=2) vecReturn.removeElementAt(1);
			vecReturn.add(1, sValue);
			
			if(vecReturn.size()>=3) vecReturn.removeElementAt(2); 
			if(!StringZZZ.isEmpty(sRest)){	
				//Nachbereitung: Ein ggfs. /Z-Tag am Anfang des Rest entfernen
				//Hier: Nur dann, wenn es nicht der String selber ist.
				//if(!sRest.equals(sTagEnd) & StringZZZ.startsWithIgnoreCase(sRest, sTagEnd)) {
				//Nein: Der Z-Tag einzeln hat nur Sinn, wenn noch andere Z: Tags drin enthalten sind. Wird weiter oben schon erledigt.
				
				if(StringZZZ.startsWithIgnoreCase(sRest, sTagEnd)) {
					sRest = StringZZZ.rightback(sRest, sTagEnd);
				}
				vecReturn.add(2, sRest); //Falls vorhanden einen Restwert eintragen.
			}else{
				vecReturn.add(2,"");
			}
			
			if(sBeforeOld.equals(sBefore) | sRestOld.equals(sRest)) break; //sonst ggfs. Endlosschleifengefahr.
			sBeforeOld=sBefore;
			sRestOld=sRest;
		}//end while
		}//end main
	}	
		
	public static String computeAsExpressionReflected(String sValue) {
		if(sValue.startsWith("<Z>") && sValue.endsWith("</Z>")) {
			//dann ist hier schon eine Expression drin
			return sValue;
		}else {
			//Merke: Das Problem ist: Wenn im String selbst vorher schon ein Z-Tag war und nicht nur am Anfang, dann wird hier trotzdem noch etwas drumgesetzt.
			
			//Suche, ob ein Z-Tag im String enthalten ist. 
			boolean bZExistsStart = StringZZZ.contains(sValue, "<Z>");
			boolean bZExistsEnd = StringZZZ.contains(sValue, "</Z>");
			if(bZExistsStart && bZExistsEnd){
				return sValue;
			}
		}	
			
		return KernelConfigSectionEntryUtilZZZ.computeAsExpression(sValue);
	}
	
	public static String computeAsExpression(String sValue) {
		return "<Z>" + sValue + "</Z>";
	}
}
