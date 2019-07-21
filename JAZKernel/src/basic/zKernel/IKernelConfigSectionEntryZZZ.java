package basic.zKernel;

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
public interface IKernelConfigSectionEntryZZZ {
	public String getSection();
	public void setSection(String sSection);
	
	public String getProperty();
	public void setProperty(String sProperty);
	
	public String getRaw();
	public void setRaw(String sRaw);
	
	public String getValue();
	public void setValue(String sValue);
	
	public boolean hasAnyValue();
	abstract void hasAnyValue(boolean bAnyValue);
	
	public boolean isFormula();
	abstract void isFormula(boolean bIsFormula);
	
	public boolean isExpression();
	abstract void isExpression(boolean bIsExpression);	
	
	public boolean sectionExists();
	abstract void sectionExists(boolean bSectionExists);
}
