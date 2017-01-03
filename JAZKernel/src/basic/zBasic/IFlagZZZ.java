package basic.zBasic;
import java.util.HashMap;

public interface IFlagZZZ {
	public abstract HashMap<String, Boolean>getHashMapFlagZ();
	public abstract boolean proofFlagZExists(String sFlag); //Wird per METHOD.INVOKE(...) aufgerufen, muss darum in jeder Klasse - per Vererbung - vorhanden sein.
	public abstract Class getClassFlagZ();
	public abstract boolean getFlagZ(String sFlag);
	public abstract boolean setFlagZ(String sFlag, boolean bValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Prüft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat. 
}
