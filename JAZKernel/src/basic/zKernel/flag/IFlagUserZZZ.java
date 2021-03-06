package basic.zKernel.flag;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public interface IFlagUserZZZ{
	public final String sERROR_FLAG_UNAVAILABLE = "this flag is not available: ";
	public final int iERROR_FLAG_UNAVAILABLE = 50;

	public abstract boolean getFlag(String sFlagName);
	public abstract boolean setFlag(String sFlagName, boolean bValue);	
	
	public enum FLAGZ{
		DEBUG, INIT ; //20170307 - Verschoben aus ObjectZZZ, weil nicht alle Klassen von ObjectZZZ erben können (weil sie schon von einer anderen Java spezifischen Klasse erben).
	}
	
	public abstract HashMap<String, Boolean>getHashMapFlagZ();
	public abstract boolean getFlagZ(String sFlag);
	public abstract boolean setFlagZ(String sFlag, boolean bValue) throws ExceptionZZZ; //Holt sich zuerst alle Eltern/Superklassen, die IFlagZZZ implementieren. Pr�ft dann, ob diese Klasse das Flag in der Enumeration .getClassFLAGZ() hat.
	
	public abstract boolean proofFlagZExists(String sFlag) throws ExceptionZZZ; //Wird per METHOD.INVOKE(...) aufgerufen, muss darum in jeder Klasse - per Vererbung - vorhanden sein.		
	public String[] getFlagZ(boolean bFlagValueToSearchFor) throws ExceptionZZZ; //20180712 - zur Weitergabe der Flags an andere Objekte)
	public String[] getFlagZ() throws ExceptionZZZ;
	
	public abstract HashMap<String, Boolean>getHashMapFlagZpassed();
	public abstract void setHashMapFlagZpassed(HashMap<String, Boolean> hmFlagPassed);
	public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ;
	public String[] getFlagZ_passable(IFlagUserZZZ objUsingFlagZ) throws ExceptionZZZ;//Hole alle auf true gesetzten Flags....
}
