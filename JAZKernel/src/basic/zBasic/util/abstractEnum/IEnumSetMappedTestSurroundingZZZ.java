package basic.zBasic.util.abstractEnum;

import basic.zBasic.ExceptionZZZ;

public interface IEnumSetMappedTestSurroundingZZZ extends IEnumSetMappedZZZ{
	public static String sENUMNAME="SURROUNDING";
	
	//weitere Erweiterungen, speziell für TESTCASE
	public String getSurroundingValueUsedTestMessage();
	
	//was in der Enumeration eingestellt ist fuer parse oder solve
	//Merke: Die Methoden sind hinsichtlich des verwendeten boolschen Parameters unterschiedlich "gepolt". Das haengt von dem Defaultwert ab.
	public boolean getSurroundingValueUsedForMethod() throws ExceptionZZZ; 
	
	//Komfortable für if-Abfragen, denn was in der Enumeration eingestellt ist, wird ggfs. anders interpretiert.
	public boolean isSurroundingValueToKeep_OnParse() throws ExceptionZZZ;
	public boolean isSurroundingValueToRemove_OnParse() throws ExceptionZZZ;
	public boolean isSurroundingValueToKeep_OnSolve() throws ExceptionZZZ;
	public boolean isSurroundingValueToRemove_OnSolve() throws ExceptionZZZ;
}
