package basic.zKernel.flag.json;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

/** Wenn es jetzt das FlagObjekt als eigene Klasse gibt, diese Interface zur Verfügung stellen.
 *  Merke: Das bestehende IFlagZZZ Interface wurde umbenannt in IFlagZUserZZZ
 * 
 * @author Fritz Lindhauer, 26.03.2021, 08:34:37
 * 
 */
public interface IFlagContainerZZZ {
	public abstract HashMap<String, Boolean>getHmFlag();
	public abstract void setHmFlag(HashMap<String, Boolean>hmFlag);
	public abstract HashMap<String, Boolean>getHashMap();
	public abstract void setHashMap(HashMap<String, Boolean>hmFlag);
	public abstract boolean getFlag(String sFlag) throws ExceptionZZZ;
	public abstract void setFlag(String sFlag, boolean bValue) throws ExceptionZZZ;	
}
