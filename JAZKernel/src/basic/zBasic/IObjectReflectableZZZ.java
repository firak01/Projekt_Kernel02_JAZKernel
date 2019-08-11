package basic.zBasic;

import java.util.HashMap;

public interface IObjectReflectableZZZ {
	public String getClassMethodCallingString() throws ExceptionZZZ;
	public void setClassMethodCallingString(String sClassMethodCalling) throws ExceptionZZZ;
	abstract boolean initClassMethodCallingString() throws ExceptionZZZ; //Diese Initialisierung wird von makeObjectReflectableInitialization() aufgerufen. Ist allerdings in den Abstracten Klassen schon ausprogrammierrt.	 
	HashMap<String, String> getClassMethodCallingHashMap() throws ExceptionZZZ;
	void putClassMethodCallingHashMap(String sClassMethodCalling) throws ExceptionZZZ;
}
