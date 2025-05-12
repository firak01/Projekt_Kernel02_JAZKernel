package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueArrayUserZZZ;
import basic.zBasic.IValueComputedBufferedUserZZZ;
import basic.zBasic.IValueMapUserZZZ;
import basic.zBasic.IValueUserZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.crypt.code.ICryptUserZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionUserZZZ;
import basic.zBasic.util.file.ini.IIniStructurePositionZZZ;
import basic.zKernel.cache.ICachableObjectZZZ;

/** Ein Objekt dieser Klasse enthält die Werte aus einer konfigurierten ini-Datei.
 *  Gefüllt wird dieses Objekt in den Kernel.getParameter.... oder ähnlich lautenden Klassen.
 *  Diese geben das Objekt zurück.
 *  
 *  Ziel:
 *  Den aufrufenden Methoden eine Möglichkeit einräumen festzustellen, ob eine Einstellung in der ini-Datei
 *  (absichtlich) leer ist oder tatsächlich nicht konfiguriert wurde.
 *  Das spielt zusammen mit der von IKernelExpressionIniZZZ bereitgestellten Funktionalität.
 *  Dabei errechnet die Formel ggfs. einen Leerstring, obwohl eine Konfiguration vorliegt. 
 *       
 * @author Fritz Lindhauer, 17.07.2019, 09:27:00
 * 
 */
public interface IKernelConfigSectionEntryZZZ extends IValueComputedBufferedUserZZZ, IValueUserZZZ, IValueArrayUserZZZ, IValueMapUserZZZ, ICachableObjectZZZ, ICryptUserZZZ, IIniStructurePositionZZZ, IIniStructurePositionUserZZZ, Cloneable{		
	public void setSection(String sSection, boolean bExists) throws ExceptionZZZ; //Wenn die Section gesetzt wird, wird zuerst der Wert bSectionExists auf false gesetzt. Darum ist die Reihenfolge erst Section-Name, dann Section-Wert wichtig. Diese Methode beruecksichtigt dies. 
	public HashMapMultiIndexedZZZ<String,Boolean> getSectionsSearchedHashMap();
	public void setSectionsSearchedHashMap(HashMapMultiIndexedZZZ<String,Boolean> hmSectionsSearched); //wenn für jeden Suchschritt ein neues EntrySection-Objekt geholt wird, geht ohne das neue Setzen der bisherigen Suche, der Suchpfad verloren. Darum ist das Setzen wichtig.
	
	public void setProperty(String sProperty, boolean bExists) throws ExceptionZZZ; //Wenn die Section gesetzt wird, wird zuerst der Wert bSectionExists auf false gesetzt. Darum ist die Reihenfolge erst Section-Name, dann Section-Wert wichtig. Diese Methode beruecksichtigt dies. 
	public HashMapMultiIndexedZZZ<String,Boolean> getPropertiesSearchedHashMap();
	public void setPropertiesSearchedHashMap(HashMapMultiIndexedZZZ<String,Boolean> hmProperiesSearched); //wenn für jeden Suchschritt ein neues EntrySection-Objekt geholt wird, geht ohne das neue Setzen der bisherigen Suche, der Suchpfad verloren. Darum ist das Setzen wichtig.
	
	public HashMapMultiZZZ<String,String> getPropertiesSectionHashMap();
	public void setPropertiesSectionHashMap(HashMapMultiZZZ<String,String> hmProperiesSearched); 
	
	public String getSystemNumber();
	public void setSystemNumber(String sSystemNumber);
	
	public VectorZZZ<String> getHistorySolveCalledVector() throws ExceptionZZZ;
	public void setHistorySolveCalled(String sTagName) throws ExceptionZZZ;
	
	public VectorZZZ<String> getHistoryParseCalledVector() throws ExceptionZZZ;
	public void setHistoryParseCalled(String sTagName) throws ExceptionZZZ;
	
	public VectorDifferenceZZZ<String> getValueAsExpressionVector();
	public String getValueAsExpression();
	public void setValueAsExpression(String sValueAsExpression) throws ExceptionZZZ;
	public void setValueAsExpression(String sValueAsExpression, boolean bEnforce) throws ExceptionZZZ;
			
	public VectorDifferenceZZZ<HashMap<String,String>> getValueHashMapVector();
	public HashMap<String,String> getValueHashMap();
	public void setValue(HashMap<String,String> hmValue);
	
	public VectorDifferenceZZZ<ArrayList<String>> getValueArrayListVector();
	public ArrayList<String> getValueArrayList();
	public void setValue(ArrayList<String> alValue);
	
	public boolean hasAnyValue();
	//soll nur private eingesetzt werden. abstract void hasAnyValue(boolean bAnyValue);
	
	boolean hasNullValue();
	//soll nur private eingesetzt werden. abstract void hasNullValue(boolean bNullValue);
	
	public boolean isExpression();
	abstract void isExpression(boolean bIsExpression);	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isParseCalled();
	abstract void isParseCalled(boolean bIsParseCalled);
	
	public boolean isParsed();
	abstract void isParsed(boolean bIsParsed);
	
	public boolean isParsedChanged();
	abstract void isParsedChanged(boolean bIsParsedChanged);
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isSubstituteCalled();
	abstract void isSubstituteCalled(boolean bIsSubstitutedCalled);
	
	public boolean isSubstituted();
	abstract void isSubstituted(boolean bIsSubstituted);
		
	public boolean isSubstitutedChanged();
	abstract void isSubstitutedChanged(boolean bIsSubstitutedChanged);
	
	public boolean isPathSubstituteCalled();
	abstract void isPathSubstituteCalled(boolean bIsSubstitutedCalled);
		
	public boolean isPathSubstituted();
	abstract void isPathSubstituted(boolean bIsPathSubstituted);
	
	public boolean isPathSubstitutedChanged();
	abstract void isPathSubstitutedChanged(boolean bIsPathSubstitutedChanged);
		
	public boolean isVariableSubstituteCalled();
	abstract void isVariableSubstituteCalled(boolean bIsSubstitutedCalled);
	
	public boolean isVariableSubstituted();
	abstract void isVariableSubstituted(boolean bIsVariableSubstituted);
	
	public boolean isVariableSubstitutedChanged();
	abstract void isVariableSubstitutedChanged(boolean bIsVariableSubstitutedChanged);
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isSolveCalled();
	abstract void isSolveCalled(boolean bIsSolved);
	
	public boolean isSolved();
	abstract void isSolved(boolean bIsSolved);

	public boolean isSolvedChanged();
	abstract void isSolvedChanged(boolean bIsSolvedChanged);
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isConversion();
	abstract void isConversion(boolean bIsConversion);
	
	public boolean isConverted();
	abstract void isConverted(boolean bIsConverted);
	
	public VectorDifferenceZZZ<String> getValueAsConversionVector();
	public void setValueAsConversionVector(VectorDifferenceZZZ<String> vecValueConversion);
	
	public String getValueAsConversion();
	public void setValueAsConversion(String sValueConverted);
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Formula
	public boolean isFormula();
	abstract void isFormula(boolean bIsFormulaSolved);
	
	public boolean isFormulaSolved();
	abstract void isFormulaSolved(boolean bIsFormulaSolved);
	
	
	public VectorDifferenceZZZ<String> getValueFormulaSolvedAndConvertedVector();
	public void setValueFormulaSolvedAndConvertedVector(VectorDifferenceZZZ<String> vecValueSolvedAndConverted);
	
	public String getValueFormulaSolvedAndConverted();
	abstract void setValueFormulaSolvedAndConverted(String sValueFormulaSolvedAndConverted);
	public String getValueFormulaSolvedAndConvertedAsExpression() throws ExceptionZZZ;
		
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression, boolean bEnforce) throws ExceptionZZZ;
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isFormulaMathSolved();
	abstract void isFormulaMathSolved(boolean bIsFormulaMathSolved);
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//JSON
	public boolean isJson();
	abstract void isJson(boolean bIsJson);
	
	public boolean isJsonMap();
	abstract void isJsonMap(boolean bIsJsonMap);
	
	public boolean isJsonArray();
	abstract void isJsonArray(boolean bIsJsonArray);

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//CRYPT
	public boolean isCrypt();
	abstract void isCrypt(boolean bIsCrypt);
	
	//Falls ein Wert einen Verschlüsselungsalgorithmus enthielt und dieser zur Berechnung erfolgreich angewendet wurde
	public boolean isDecrypted();
	abstract void isDecrypted(boolean bIsDecrypted);
	
	public VectorDifferenceZZZ<String> getRawDecryptedVector();
	public void setRawDecryptedVector(VectorDifferenceZZZ<String> vecRawDecrypted);
	public String getRawDecrypted();
	abstract void setRawDecrypted(String sRaw);
	
	boolean isEncrypted();
	abstract void isEncrypted(boolean bEncrypted);
	
	abstract void isRawDecrypted(boolean bRawDecrypted);
	boolean isRawDecrypted();
	
	public boolean isRawEncrypted();
	abstract void isRawEncrypted(boolean bIsRawEncrypted);
	
	public VectorDifferenceZZZ<String> getRawEncryptedVector();
	public void setRawEncryptedVector(VectorDifferenceZZZ<String> vecRawEncrypted);
	public String getRawEncrypted();//Wert mit den Z-Tags drumherum
	abstract void setRawEncrypted(String sRaw);
	public String getRawEncryptedAsExpression() throws ExceptionZZZ;
	
	public VectorDifferenceZZZ<String> getValueEncryptedVector();
	abstract void setValueEncryptedVector(VectorDifferenceZZZ<String> vecValueEncrypted);	
	public String getValueEncrypted();
	public void setValueEncrypted(String sValueEncryptd);
	public String getValueEncryptedAsExpression() throws ExceptionZZZ;
	
	public VectorDifferenceZZZ<String> getValueEncryptedPartVector();
	abstract void setValueEncryptedPartVector(VectorDifferenceZZZ<String> vecValueEncryptedPart);
	public String getValueEncryptedPart();	
	public void setValueEncryptedPart(String sValueEncryptd);
	
	public VectorDifferenceZZZ<String> getValueDecryptedVector();
	abstract void setValueDecryptedVector(VectorDifferenceZZZ<String> vecValueDecrypted);
	public String getValueDecrypted();	
	public void setValueDecrypted(String sValueEncryptd);
	public String getValueDecryptedAsExpression() throws ExceptionZZZ;	
	
	public VectorDifferenceZZZ<String> getValueDecryptedPartVector();
	abstract void setValueDecryptedPartVector(VectorDifferenceZZZ<String> vecValueDecryptedPart);
	public String getValueDecryptedPart();	
	public void setValueDecryptedPart(String sValueDecryptd);
		
	//++++++++++++++++++++++++++++++++++++++++++++
	//CALL
	public boolean isCall();
	abstract void isCall(boolean bIsCall);
	
	public boolean isCallParsed();
	abstract void isCallParsed(boolean bIsCallParsed);
	
	public boolean isCallParsedChanged();
	abstract void isCallParsedChanged(boolean bIsCallParsedChanged);

	public boolean isCallParseCalled();
	abstract void isCallParseCalled(boolean bIsCallParseCalled);
		
	public boolean isCallSolveCalled();
	abstract void isCallSolveCalled(boolean bIsCallSolveCalled);
	
	public boolean isCallSolved();
	abstract void isCallSolved(boolean bIsCallSolved);
		
	public boolean isCallSolvedChanged();
	abstract void isCallSolvedChanged(boolean bIsCallSolvedChanged);

	public boolean isJavaCall();
	abstract void isJavaCall(boolean bIsJavaCall);
	
	public boolean isJavaCallParseCalled();
	abstract void isJavaCallParseCalled(boolean bIsJavaCallParseCalled);
		
	public boolean isJavaCallParsed();
	abstract void isJavaCallParsed(boolean bIsJavaCallParsed);
	
	public boolean isJavaCallParsedChanged();
	abstract void isJavaCallParsedChanged(boolean bIsJavaCallParsedChanged);
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isJavaCallSolveCalled();
	abstract void isJavaCallSolveCalled(boolean bIsJavaCallSolveCalled);
	
	public boolean isJavaCallSolved();
	abstract void isJavaCallSolved(boolean bIsJavaCallSolved);
		
	public boolean isJavaCallSolvedChanged();
	abstract void isJavaCallSolvedChanged(boolean bIsJavaCallSolvedChanged);
	
	
	
	//Falls ein Wert einen Aufruf enthält hier die Details ablegen
	public String getCallingClassname();
	public void setCallingClassname(String sJavaCallingClassName);
	
	public String getCallingMethodname();
	public void setCallingMethodname(String sJavaCallingMethodName);
		
	public String getValueCallSolved();
	public void setValueCallSolved(String sValueCallSolved);
	public String getValueCallSolvedAsExpression();
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression) throws ExceptionZZZ;
	
	public String getValueFromTag();
	public void setValueFromTag(String sValueFromTag);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//SONSTIGES:
	//Falls eine Section eine Form des Arrays enthält und dieses in Einzelwerte zerlegt wurde (mit explode)
	public boolean isExploded();
	abstract void isExploded(boolean bIsExploded);
	
	public int getIndex();
	abstract void setIndex(int iIndex);
	
	//Falls eine Section eine Form der HashMap enthält und diese in Einzelwerte zerlegt wurde (mit explode)
	public String getKey();
	public void setKey(String sKey);

	
	//Suchpfaddetail	
	public boolean sectionExists();
	abstract void sectionExists(boolean bSectionExists) throws ExceptionZZZ;
	public boolean hasAnySectionExists(); //existierte auf dem Suchpfad nach einer Property ueberhaupt einmal eine Section?
	//soll nur private eingesetzt werden. abstract void hasAnySectionExists(boolean bValue);
	
	public boolean propertyExists();
	abstract void propertyExists(boolean bPropertyExists) throws ExceptionZZZ;
	public boolean hasAnyPropertyExists(); //existierte auf dem Suchpfad nach einer Property ueberhaupt einmal eine Section?
	//soll nur private eingesetzt werden. abstract void hasAnyPropertyExists(boolean bValue);
	
	
	//In clonable protected
	public IKernelConfigSectionEntryZZZ clone() throws CloneNotSupportedException;
	
}
