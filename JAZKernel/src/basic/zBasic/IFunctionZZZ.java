package basic.zBasic;

import java.util.HashMap;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface IFunctionZZZ {
	
	public final String sERROR_FLAG_UNAVAILABLE = "this flag is not available: ";
	public final int iERROR_FLAG_UNAVAILABLE = 50;

	public abstract boolean getFlag(String sFlagName);
	public abstract boolean setFlag(String sFlagName, boolean bValue);	
	
	public enum FLAGZ{
		DEBUG, INIT ; //20170307 - Verschoben aus ObjectZZZ, weil nicht alle Klassen von ObjectZZZ erben können (weil sie schon von einer anderen Java spezifischen Klasse erben).
	}
	public HashMap<String, Boolean>getHashMapFlagZ();
	public boolean proofFlagZExists(String sFlagName);
	public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ;
	public boolean getFlagZ(String sFlagName);
}
