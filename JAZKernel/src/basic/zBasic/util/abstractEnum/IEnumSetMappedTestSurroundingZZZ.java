package basic.zBasic.util.abstractEnum;

public interface IEnumSetMappedTestSurroundingZZZ extends IEnumSetMappedZZZ{
	public static String sENUMNAME="SURROUNDING";
	
	//weitere Erweiterungen, speziell für TESTCASE
	public String getSurroundingValueUsedTestMessage();
	//public boolean getSurroundingValueUsed(); //was in der Enumeration eingestellt ist
	
	//Komfortable für if-Abfragen, denn was in der Enumeration eingestellt ist, wird ggfs. anders interpretiert.
	public boolean isSurroundingValueToKeep_OnParse();
	public boolean isSurroundingValueToRemove_OnParse();
	public boolean isSurroundingValueToKeep_OnSolve();
	public boolean isSurroundingValueToRemove_OnSolve();
}
