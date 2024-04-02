package basic.zBasic;

import java.io.Serializable;
import java.util.HashMap;

import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ.FLAGZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface IObjectWithStatusZZZ extends IObjectZZZ {
	public enum FLAGZ{
		STATUSLOCAL_PROOF_VALUE, STATUSLOCAL_PROOF_VALUETRUE, STATUSLOCAL_PROOF_VALUECHANGED, STATUSLOCAL_PROOF_MESSAGECHANGED ; 
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public abstract boolean getFlag(IObjectWithStatusZZZ.FLAGZ objEnumFlag);
	public abstract boolean setFlag(IObjectWithStatusZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlag(IObjectWithStatusZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	//20230730 Merke: In IEventBrokerFlagZsetUserZZZ gibt es noch diese Besonderheit, mit der Enum - Werte gesetzt werden können. Die werden dann an Events uebergeben und koennen weitere Informationen enthalten.
	//boolean setFlag(Enum enumFlag, boolean bFlagValue) throws ExceptionZZZ;
		
	public abstract boolean proofFlagExists(IObjectWithStatusZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IObjectWithStatusZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ; //Prueft auf die Existenz des Flags in der HashMap.
		
	//KONVENTION, speichere alle jemals gesetzten StatusLocal-Werte in der Hashmap: 	
	public abstract HashMap<String, Boolean>getHashMapStatusLocal();
	public abstract void setHashMapStatusLocal(HashMap<String, Boolean> hmStatusLocal);
	
	//KONVENTION, speichere alle jemals gesetzten StatusLocalMessage-Werte in der Hashmap. 
	//            Diese Werte sind Meldungen, die vom definierten Standard im Enum abweichen.
	public abstract HashMap<String, String>getHashMapStatusLocalMessage();
	public abstract void setHashMapStatusLocalMessage(HashMap<String, String> hmStatusLocal);
	
	
	//zum Ueberpruefen, ob sich ein Status geaendert hat
	public abstract boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ;
	
	//zum Ueberpruefen, ob ein Status ueberhaupt mal gesetzt worden ist.
	public abstract boolean isStatusSetBefore(String sStatusString) throws ExceptionZZZ;
}
