package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;

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
public interface IKernelConfigSectionEntryZZZ extends ICachableObjectZZZ, Cloneable{
	public String getSection();
	public void setSection(String sSection);
	
	public String getProperty();
	public void setProperty(String sProperty);
	
	public String getRaw();
	public void setRaw(String sRaw);
	
	public String getValue();
	public void setValue(String sValue);
	
	public HashMap<String,String> getValueHashMap();
	public void setValue(HashMap<String,String> hmValue);
	
	public ArrayList<String> getValueArrayList();
	public void setValue(ArrayList<String> alValue);
	
	public boolean hasAnyValue();
	//soll nur private eingesetzt werden. abstract void hasAnyValue(boolean bAnyValue);
	
	boolean hasNullValue();
	//soll nur private eingesetzt werden. abstract void hasNullValue(boolean bNullValue);
	
	public boolean isFormula();
	abstract void isFormula(boolean bIsFormula);
	
	public boolean isExpression();
	abstract void isExpression(boolean bIsExpression);	
	
	public boolean isJson();
	abstract void isJson(boolean bIsJson);
	
	public boolean isJsonMap();
	abstract void isJsonMap(boolean bIsJsonMap);
	
	public boolean isJsonArray();
	abstract void isJsonArray(boolean bIsJsonArray);
		
	public boolean sectionExists();
	abstract void sectionExists(boolean bSectionExists);
	
	//In clonable protected
	public IKernelConfigSectionEntryZZZ clone() throws CloneNotSupportedException;
	
}
