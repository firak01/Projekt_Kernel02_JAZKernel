package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.util.crypt.code.ICryptUserZZZ;
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
public interface IKernelConfigSectionEntryZZZ extends ICachableObjectZZZ, ICryptUserZZZ, Cloneable{
	public String getSection();
	public void setSection(String sSection);
	
	public String getProperty();
	public void setProperty(String sProperty);
	
	public String getSystemNumber();
	public void setSystemNumber(String sSystemNumber);
	
	public String getRaw();
	public void setRaw(String sRaw);
	
	public String getValue();
	public void setValue(String sValue);
	
	public String getValueAsExpression();
	public void setValueAsExpression(String sValueAsExpression);
			
	public HashMap<String,String> getValueHashMap();
	public void setValue(HashMap<String,String> hmValue);
	
	public ArrayList<String> getValueArrayList();
	public void setValue(ArrayList<String> alValue);
	
	public boolean hasAnyValue();
	//soll nur private eingesetzt werden. abstract void hasAnyValue(boolean bAnyValue);
	
	boolean hasNullValue();
	//soll nur private eingesetzt werden. abstract void hasNullValue(boolean bNullValue);
	
	public boolean isExpression();
	abstract void isExpression(boolean bIsExpression);	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Formula
	public boolean isFormula();
	abstract void isFormula(boolean bIsFormula);
		
	public boolean isConverted();
	abstract void isConverted(boolean bIsConverted);
	
	public String getValueFormulaSolvedAndConverted(); 
	public void setValueFormulaSolvedAndConverted(String sValueSolvedAndConverted);
	
	
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
	public String getRawDecrypted();
	abstract void setRawDecrypted(String sRaw);
	
	boolean isEncrypted();
	abstract void isEncrypted(boolean bEncrypted);
	
	abstract void isRawDecrypted(boolean bRawDecrypted);
	boolean isRawDecrypted();
	
	public boolean isRawEncrypted();
	abstract void isRawEncrypted(boolean bIsRawEncrypted);
	public String getRawEncrypted();//Wert mit den Z-Tags drumherum
	abstract void setRawEncrypted(String sRaw);
	public String getValueEncrypted(); 
	public void setValueEncrypted(String sValueEncryptd);
	public String getValueDecrypted(); 
	public void setValueDecrypted(String sValueEncryptd);
		
		
	//++++++++++++++++++++++++++++++++++++++++++++
	//CALL
	public boolean isCall();
	abstract void isCall(boolean bIsCall);
	
	public boolean isJavaCall();
	abstract void isJavaCall(boolean bIsJavaCall);
	
	//Falls ein Wert einen Aufruf enthält hier die Details ablegen
	public String getCallingClassname();
	public void setCallingClassname(String sJavaCallingClassName);
	
	public String getCallingMethodname();
	public void setCallingMethodname(String sJavaCallingMethodName);
		
	public String getValueCallSolvedAsExpression();
	public void setValueCallSolvedAsExpression(String sValueCallSolvedAsExpression);
	
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
	abstract void sectionExists(boolean bSectionExists);
	
	public boolean hasAnySectionExists(); //existierte auf dem Suchpfad nach einer Property ueberhaupt einmal eine Section?
	//soll nur private eingesetzt werden. abstract void hasAnySectionExists(boolean bValue);
	
	//In clonable protected
	public IKernelConfigSectionEntryZZZ clone() throws CloneNotSupportedException;
	
	
}
