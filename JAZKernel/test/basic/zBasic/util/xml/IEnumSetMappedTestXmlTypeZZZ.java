package basic.zBasic.util.xml;

import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

public interface IEnumSetMappedTestXmlTypeZZZ extends IEnumSetMappedTestContainerTypeZZZ{
	
	//weitere Erweiterungen, speziell fuer die Xml - Tests
	public String getXml() ;
	
	public int getExpectedElementsWithoutText();
	public int getExpectedElementsWithText();
	
	public int[]getIndexInVectorOfExpectedTagsWithoutText();
	public int[]getIndexInVectorOfExpectedTagsWithText();
	
	public String[]getTagsForExpectedValues(); //Tag an der Indexposition
	public String[][]getExpectedValues(); //Merke "Array von Array", weil es ja fuer ein Tag ggfs. Array Wert gibt.
}
