package basic.zBasic.util.xml;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IEnumSetMappedTestXmlTypeZZZ extends IEnumSetMappedTestContainerTypeZZZ{
	public static String sENUMNAME="TESTVALUE";
	
	//weitere Erweiterungen, speziell fuer die Xml - Tests
	public String getXml() ;
	
	public int getExpectedElementsWithoutText();

	public int getExpectedElementsWithText();
	
	public String[]getTagsForExpectedValue();
	public String[][]getExpectedValues(); //Merke "Array von Array", weil es ja fuer ein Tag ggfs. Array Wert gibt.
}
