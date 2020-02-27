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
	 * Merke: Hier wird der Datentyp nach Integer geändert.
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
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * Merke: Hier ist die Sortierung in "reverseOrder", also anders als die natürliche Sortierung. 
	 * Merke: Hier wird der Datentyp nach Integer geändert.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<Integer>sortToIntegerReversed(Set<?> setToBeSorted){
		List<Integer> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list
			Collections.sort(numbersList, Collections.reverseOrder());//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	public static List<Integer>toListInteger(Set<Integer>setInt){
		List<Integer> listIntReturn = null;
		main:{
			if(setInt == null) break main;

			listIntReturn =new ArrayList<Integer>(setInt) ;        //set -> list
		}//end main:
		return listIntReturn;
	}
		
	public static List<String>toListString(Set<String>setString){
		List<String> listStringReturn = null;
		main:{
			if(setString == null) break main;

			listStringReturn =new ArrayList<String>(setString) ;        //set -> list
		}//end main:
		return listStringReturn;
	}
	
	
	public static Set<Integer>toInteger(Set<String>setStrToBeSorted){
		Set<Integer> setIntReturn = null;
		main:{
			if(setStrToBeSorted == null) break main;
			
			HashSet<Integer> hsInt = new HashSet<Integer>();
			for(String stemp : setStrToBeSorted) {
				Integer intTemp=new Integer(stemp);
				hsInt.add(intTemp);								
			}
			setIntReturn = hsInt;
		}//end main:
		return setIntReturn;
	}
}
