package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IValueArrayUserZZZ;
import basic.zBasic.IValueComputedBufferedUserZZZ;
import basic.zBasic.IValueMapUserZZZ;
import basic.zBasic.IValueUserZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
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
	public boolean isParsed();
	abstract void isParsed(boolean bIsParsed);
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isSolved();
	abstract void isSolved(boolean bIsSolved);
	
	public boolean isPathSolved();
	abstract void isPathSolved(boolean bIsPathSolved);
	
	public boolean isVariableSolved();
	abstract void isVariableSolved(boolean bIsVariableSolved);
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean isConversion();
	abstract void isConversion(boolean bIsConversion);
	
	public boolean isConverted();
	abstract void isConverted(boolean bIsConverted);
	
	public VectorDifferenceZZZ<String> getValueAsConversionVector();
	public String getValueAsConversion();
	public void setValueAsConversion(String sValueConverted);
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Formula
	public boolean isFormula();
	abstract void isFormula(boolean bIsFormulaSolved);
	
	public boolean isFormulaSolved();
	abstract void isFormulaSolved(boolean bIsFormulaSolved);
	
	
	public VectorDifferenceZZZ<String> getValueFormulaSolvedAndConvertedVector();
	public String getValueFormulaSolvedAndConverted();
	public String getValueFormulaSolvedAndConvertedAsExpression() throws ExceptionZZZ; 
	public void setValueFormulaSolvedAndConverted(String sValueSolvedAndConverted);
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
	public String getRawDecrypted();
	abstract void setRawDecrypted(String sRaw);
	
	boolean isEncrypted();
	abstract void isEncrypted(boolean bEncrypted);
	
	abstract void isRawDecrypted(boolean bRawDecrypted);
	boolean isRawDecrypted();
	
	public boolean isRawEncrypted();
	abstract void isRawEncrypted(boolean bIsRawEncrypted);
	
	public VectorDifferenceZZZ<String> getRawEncryptedVector();
	public String getRawEncrypted();//Wert mit den Z-Tags drumherum
	public String getRawEncryptedAsExpression() throws ExceptionZZZ;
	abstract void setRawEncrypted(String sRaw);
	
	public VectorDifferenceZZZ<String> getValueEncryptedVector(); 
	public String getValueEncrypted();
	public String getValueEncryptedAsExpression() throws ExceptionZZZ;
	public void setValueEncrypted(String sValueEncryptd);
	
	public VectorDifferenceZZZ<String> getValueDecryptedVector();
	public String getValueDecrypted();
	public String getValueDecryptedAsExpression() throws ExceptionZZZ;
	public void setValueDecrypted(String sValueEncryptd);
		
		
	//++++++++++++++++++++++++++++++++++++++++++++
	//CALL
	public boolean isCall();
	abstract void isCall(boolean bIsCall);
	
	public boolean isCallSolved();
	abstract void isCallSolved(boolean bIsCallSolved);
	
	public boolean isJavaCall();
	abstract void isJavaCall(boolean bIsJavaCall);
	
	
	
	
	//Falls ein Wert einen Aufruf enthält hier die Details ablegen
	public String getCallingClassname();
	public void setCallingClassname(String sJavaCallingClassName);
	
	public String getCallingMethodname();
	public void setCallingMethodname(String sJavaCallingMethodName);
		
	public String getValueCallSolved();
	public void setValueCallSolved(String sValueCallSolved);
	
	public String getValueCallSolvedAsExpression();
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression) throws ExceptionZZZ;
	
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
