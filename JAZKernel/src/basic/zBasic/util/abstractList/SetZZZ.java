package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetZZZ {
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<?>sortAsString(Set<?> setToBeSorted){
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<String> hsKey = new HashSet<String>((Collection<? extends String>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<String> numbersList = new ArrayList<String>(hsKey) ;        //set -> list
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<String>sortToString(Set<?> setToBeSorted){
		List<String> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<String> hsKey = new HashSet<String>((Collection<? extends String>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<String> numbersList = new ArrayList<String>(hsKey) ;        //set -> list
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<?>sortAsInteger(Set<?> setToBeSorted){
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<Integer>sortToInteger(Set<?> setToBeSorted){
		List<Integer> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
}
