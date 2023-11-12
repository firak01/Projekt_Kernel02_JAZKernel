package basic.zKernel.status;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;

/**Dieses Interface stellt Methoden zur Verfügung um "lokale Status" 
 * mit anderen "lokalen Status" Werten aus anderen Klassen, die per Event ankommen 
 * zu mappen.
 * 
 * HashMap<IEnumSetMappedZZZ,IEnumSetMappedZZZ> = Map<Externer Wert aus Event, Lokaler Wert>
 * So können mehrer Klassen einen Lokalen Wert ansprechen, z.B. für den Error wichtig.
 */
public interface IStatusLocalMapForCascadingStatusLocalUserZZZ{
	public final String sERROR_STATUS_UNMAPPED = "this LOCAL status is not mapped";
	public final int iERROR_STATUS_UNMAPPED = 52;
	
	public abstract HashMap<IEnumSetMappedZZZ, IEnumSetMappedZZZ>getHashMapEnumSetForCascadingStatusLocal();
	public abstract void setHashMapEnumSetForCascadingStatusLocal(HashMap<IEnumSetMappedZZZ, IEnumSetMappedZZZ> hmEnumSet);	
	
	//Map: ExternerWert aus Event, LokalerWert
	public abstract HashMap<IEnumSetMappedZZZ,IEnumSetMappedZZZ>createHashMapEnumSetForCascadingStatusLocalCustom();
}
