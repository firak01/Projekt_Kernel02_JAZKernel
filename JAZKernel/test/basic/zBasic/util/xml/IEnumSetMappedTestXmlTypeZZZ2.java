package basic.zBasic.util.xml;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IEnumSetMappedTestXmlTypeZZZ2 extends IEnumSetMappedTestContainerTypeZZZ{
	public static String sENUMNAME="TESTVALUE";
	
	//weitere Erweiterungen, speziell fuer die Xml - Tests
	public String getXml() ;
	
	public int getExpectedElementsWithoutText();

	public int getExpectedElementsWithText();
}
