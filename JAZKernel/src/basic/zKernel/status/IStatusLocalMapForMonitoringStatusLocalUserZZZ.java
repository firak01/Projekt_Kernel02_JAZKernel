package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.component.IProgramMonitorZZZ.FLAGZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" 
 * mit anderen "lokalen Status" Werten aus anderen Klassen, die per Event ankommen 
 * zu mappen.
 * 
 * HashMap<IEnumSetMappedZZZ,IEnumSetMappedZZZ> = Map<Externer Wert aus Event, Lokaler Wert>
 * So können mehrer Klassen einen Lokalen Wert ansprechen, z.B. für den Error wichtig.
 */
public interface IStatusLocalMapForMonitoringStatusLocalUserZZZ extends IListenerObjectStatusLocalZZZ{
	public final String sERROR_STATUS_UNMAPPED = "this LOCAL status is not mapped";
	public final int iERROR_STATUS_UNMAPPED = 52;
	
	public abstract HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ>getHashMapEnumSetForCascadingStatusLocal();
	public abstract void setHashMapEnumSetForCascadingStatusLocal(HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> hmEnumSet);	
	
	//Map: ExternerWert aus Event, LokalerWert
	public abstract HashMap<IEnumSetMappedStatusZZZ,IEnumSetMappedStatusZZZ>createHashMapEnumSetForCascadingStatusLocalCustom();
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag);
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
}
