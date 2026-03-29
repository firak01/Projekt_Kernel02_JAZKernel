package basic.zBasic.util.xml;

public interface IEnumSetMappedTestXmlTypeZZZ extends IEnumSetMappedTestContainerTypeZZZ{
	
	//weitere Erweiterungen, speziell fuer die Xml - Tests
	public String getXml() ;
	
	public int getExpectedElementsWithoutText();
	public int getExpectedElementsWithText();
	
	public int[]getIndexInVectorOfExpectedTagsWithoutText();
	public int[]getIndexInVectorOfExpectedTagsWithText();
	
	public int[]getIndexInHashMapOfExpectedTagsWithoutText();
	public int[]getIndexInHashMapOfExpectedTagsWithText();
	
	public int[]getIndexInTreeOfExpectedTagsWithoutText();
	public int[]getIndexInTreeOfExpectedTagsWithText();
	
	public String[]getTagsForExpectedValues(); //Tag an der Indexposition
	
	//Merke: "Array von Array" laesst sich auch mit einem enum darstellen
	//       z.B. pos01("pos01",TESTVALUE.s2, new int[] {0}, new int[] {1}, new String[]{"b"},new String[][]{{"Wert in b"}},2,5,"Positivtest, mit XMLTags und Werten vor den Tags"),
	//       Beachte die doppelte geschweifte Klammer.
	//
	//Merke: Ggfs. gibt es fuer einen Tag mehrere Werte, trotzdem kein "Array von Array".
	public String[] getExpectedValues(); //Fuer einen Test mit mehrern Werten in einem Tag, muss dann extra ein Array aufgebaut werden.
}