package basic.zBasic.util.machine;

import basic.zBasic.ExceptionZZZ;

public interface IEnvironmentPlaceholderZZZ {
	final static String sCLOSING = "}";
	final static String sOPENING = "{";
	final static String sPREFIX = "$";
	final static String sPARAM_RETURN_NAME = ".";
	final static String sPARAM_RETURN_EMPTY = "!";
	
	final static String sDELIM_ALTERNATIVE = "#"; //Merke: Pipe | darf es von der Programmierung zwar sein, wenn in Hochkommat, obwohl in GetOptZZZ die Kommandozeilenoptionen ggfs. mit Pipe | getrennt werden
	                                              //       Aber um Probleme zu vermeiden nehme ich hier #
	
	public String getPlaceholder() throws ExceptionZZZ; //Der gesamte Platzhalter
	public void setPlaceholder(String sPlaceholder) throws ExceptionZZZ;
	
	public String getContent() throws ExceptionZZZ; //Was im Platzhalter zwischen { } steht
	public void setContent(String sContent) throws ExceptionZZZ;
	
	public String getValue() throws ExceptionZZZ; //Der Wert, der mit system.getenv(...) errechnet wurde
	public void setValue(String sValue) throws ExceptionZZZ;
	
	public String getName() throws ExceptionZZZ; //Der Name, der errechnet wurde
	public void setName(String sName) throws ExceptionZZZ;
	
}
