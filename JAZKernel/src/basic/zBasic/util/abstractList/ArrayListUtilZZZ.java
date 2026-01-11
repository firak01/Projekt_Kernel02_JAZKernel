package basic.zBasic.util.abstractList;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

/**Merksatz (wichtig!)(von ChatGPT, 20260110)
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 10.01.2026, 08:22:59
 */
public class ArrayListUtilZZZ<T>  implements IConstantZZZ{
	private ArrayListUtilZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
	
	public static boolean isEmpty(ArrayList<?> objAL) {
		boolean bReturn = false;
		main:{
			if(objAL==null) {
				bReturn = true;
				break main;
			}
			if(objAL.size()==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	public static Object getFirst(ArrayList<?> objAL) throws ExceptionZZZ {
		Object objReturn = null;
		main:{
			if(objAL==null)break main;
			if(objAL.isEmpty()) break main;
			
			objReturn = objAL.get(0);
		}
		return objReturn;
	}
	
	public static Object getLast(ArrayList<?> objAL) throws ExceptionZZZ {
		Object objReturn = null;
		main:{
			if(objAL==null)break main;
			if(objAL.isEmpty()) break main;
			
			int iSize = objAL.size();
			objReturn = objAL.get(iSize-1);
		}
		return objReturn;
	}
	
	public static boolean isSameSize(ArrayList objAL1, ArrayList objAL2) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(objAL1== null){
					ExceptionZZZ ez = new ExceptionZZZ("ArrayList1 to compare'", iERROR_PARAMETER_MISSING,  HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
					throw ez;	
				  }
				if(objAL2== null){
					ExceptionZZZ ez = new ExceptionZZZ("ArrayList2 to compare'", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
					throw ez;	
				  }
				//###################
				int iSize1 = objAL1.size();
				int iSize2 = objAL2.size();
				
				if (iSize1 == iSize2) bReturn = true;
			}//end main:
			return bReturn;
		}
	
	public static String implode(ArrayList<?>lista, String sDelimiterIn){
		String sReturn = null;
		main:{
			if(lista==null)break main;
			if(lista.size()==0)break main;
			
			String sDelimiter;
			if(sDelimiterIn==null){
				sDelimiter="";
			}else{
				sDelimiter=sDelimiterIn;
			}
			
			for(int icount=0; icount <= lista.size()-1; icount++){
				String sPosition = (String) lista.get(icount);
				if(sReturn==null){
					sReturn=sPosition;
				}else{
					sReturn+=sDelimiter+sPosition;
				}
			}
		}
		return sReturn;
	}
	
	public static String implodeReversed(ArrayList<?>lista, String sDelimiterIn){
		String sReturn = null;
		main:{
			if(lista==null)break main;
			if(lista.size()==0)break main;
			
			String sDelimiter;
			if(sDelimiterIn==null){
				sDelimiter="";
			}else{
				sDelimiter=sDelimiterIn;
			}
			
			for(int icount = lista.size()-1; icount >= 0; icount--){
				String sPosition = (String) lista.get(icount);
				if(sReturn==null){
					sReturn=sPosition;
				}else{
					sReturn+=sDelimiter+sPosition;
				}
			}		
		}
		return sReturn;
	}
	
	
	/* Gibt für die Elemente der Liste die instanceof - Werte zurück.
	 * Also nicht instanceof der Liste selbst...
	 * 
	 * heuristischer Ansatz, mit Probleme. 
	 * Gemäss: https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
	 * nicht sicher und man sollte statt dessen eine "GenericList" verwenden.
	 * 
	 * 
	 * Aber: Man kann sowieso keine Klasse an diese statische Methode übergeben (Merke: Class<T> geht nicht )
	 * Darum in einer Schleife alle durchgehen.
	 * 
	 * Hier: 
	 * Damit nicht x - Mal (z.B. in einer Fallunterscheidung) isInstanceOf aufgerufen werden muss
	 * Hier einmalig den Datentyp der Elemente bestimmen.
	 * Ggfs. einen Fehler werfen, wenn er nicht eindeutig ist.
	 */
	public static ArrayList<Class<?>> getInstanceOfList(Object objAsList) throws ExceptionZZZ {
		ArrayList<Class<?>>listaReturn=null;
		main:{			
			if(objAsList instanceof List){
				if(ArrayListUtilZZZ.isEmpty((ArrayList<?>) objAsList))break main;
				
				ArrayListZZZ<Class<?>> listaExtended = new ArrayListZZZ<Class<?>>();
				for(Object obj : (List)objAsList) {					
					ArrayList<Class<?>> listaClass = ReflectClassZZZ.getInstanceOfList(obj);
					listaExtended.addAllUnique(listaClass);
				}
				listaReturn = (ArrayList<Class<?>>) listaExtended.toArrayList();
			}
			
		}//end main:
		return listaReturn;	
	}
		
	/* heuristischer Ansatz, mit Probleme. 
	 * Gemäss: https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
	 * nicht sicher und man sollte statt dessen eine "GenericList" verwenden.
	 */
	public static boolean isInstanceOf(Object objAsList, Class objClass) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objClass==null)break main;
			
			if(objAsList instanceof List){
				if(ArrayListUtilZZZ.isEmpty((ArrayList<?>) objAsList))break main;
				
//			    if(((List)obj).size()>0 && (((List)obj).get(0) instanceof MeineTolleKlasse)){
//			        // The object is of List<MyObject> and is not empty. Do something with it.
//			    }
				
				//ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(objClass);												
				for(Object obj : (List)objAsList) {
					Class<? extends Object> classTemp = obj.getClass();//Das funktioniert aber nicht mit Interfaces
					
					//also: Die idee ist, das man die Klasse selbst eher findet und auch eher angibt. Darum nicht erst alle instanceOfList - Objekte holen.
					//      Statt hier getInstanceOfList() zu verwenden das hier ausprogrammieren
					//      getInstanceOfList() sollte dann im Vorfeld aufgerufen werden um halt mehrere isInstanceOfList-Aufrufe zu vermeiden
					if(!classTemp.equals(objClass))	{ 					
						ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(obj.getClass());							
						if(!listaInterface.contains(objClass)) break main; 
					}
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;	
	}
	
	/* heuristischer Ansatz, mit Problemen. 
	 * Gemäss: https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
	 * nicht sicher und man sollte statt dessen eine "GenericList" verwenden.
	 * 
	 * Aber: Man kann sowieso keine Klasse an diese statische Methode übergeben (Merke: Class<T> geht nicht )
	 * Darum in einer Schleife alle durchgehen.
	 */
	public static boolean isInstanceOf(ArrayList lista, Class objClass) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(objClass==null)break main;
			if(ArrayListUtilZZZ.isEmpty(lista))break main;
		
			for(Object obj : lista) {
				Class<? extends Object> classTemp = obj.getClass();//Das funktioniert aber nicht mit Interfaces
				
				//also: Die idee ist, das man die Klasse selbst eher findet und auch eher angibt. Darum nicht erst alle instanceOfList - Objekte holen.
				//      Statt hier getInstanceOfList() zu verwenden das hier ausprogrammieren.
				//      getInstanceOfList() sollte dann im Vorfeld aufgerufen werden um halt mehrere isInstanceOfList-Aufrufe zu vermeiden 
				if(!classTemp.equals(objClass))	{					
					ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(obj.getClass());							
					if(!listaInterface.contains(objClass)) break main; 
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	
		
	public static ArrayList<?> join(ArrayList<?> lista1, ArrayList<?> lista2){
		return ArrayListUtilZZZ.join(lista1, lista2, false);
	}
	
	public static ArrayList<?> join(ArrayList<?> lista1, ArrayList<?> lista2, boolean bFlagUnique){
		ArrayList listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
			
			
			if(bFlagUnique==false){
				//Wenn nicht ''uniqued' werden soll, dann kann man sofort in der Return-liste joinen
				listaReturn = new ArrayList();
				if(lista1!=null){
					for(int icount=0; icount < lista1.size(); icount++){
						listaReturn.add(lista1.get(icount));
					}
				}//lista1!=null
				if(lista2!=null){
					for(int icount=0; icount < lista2.size(); icount ++){
						listaReturn.add(lista2.get(icount));
					}
				}		
				break main;
				
			}else{
				//Wenn 'uniqued' werden soll, dann ist "keepFirst" die default-Strategie
				listaReturn = ArrayListUtilZZZ.joinKeepFirst(lista1,lista2);		
				break main;
			}//End if (bFlagUnique ....
			
		}//END main:
		return listaReturn;
	}
	
	public static ArrayList<?> joinKeepFirst(ArrayList<?> lista1, ArrayList<?> lista2){
		ArrayList<?> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
			
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			ArrayList listaTemp = new ArrayList();
			if(lista1!=null){
				for(int icount=0; icount < lista1.size(); icount++){
					listaTemp.add(lista1.get(icount));
				}
			}//lista1!=null
			if(lista2!=null){
				for(int icount=0; icount < lista2.size(); icount ++){
					listaTemp.add(lista2.get(icount));
				}
			}		
			
			listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(listaTemp);			
		}//END main:
		return listaReturn;
	}
	
	public static ArrayList<?> joinKeepLast(ArrayList<?> lista1, ArrayList<?> lista2){
		ArrayList listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
					
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			ArrayList listaTemp = new ArrayList();
			if(lista1!=null){
				for(int icount=0; icount < lista1.size(); icount++){
					listaTemp.add(lista1.get(icount));
				}
			}//lista1!=null
			if(lista2!=null){
				for(int icount=0; icount < lista2.size(); icount ++){
					listaTemp.add(lista2.get(icount));
				}
			}		
			
			listaReturn = ArrayListUtilZZZ.uniqueKeepLast(listaTemp);			
		}//END main:
		return listaReturn;
	}
	
	public static void remove(ArrayList<?> lista, String sToRemove, boolean bIgnoreCase){
		main:{
		if(lista==null) break main;
		if(sToRemove==null) break main;
		
		if(bIgnoreCase){	
			for(Object obj : lista){
				if(sToRemove.equalsIgnoreCase(obj.toString())){			
					lista.remove(obj);
					break main; //wenn man danach weiter durch die Liste gehen will, dann gibt es Fehler.
				}
			}
		}else{
			lista.remove(sToRemove);
		}
		
	  }//end main:
	}
	
	public static void remove(ArrayList<?> lista, Integer intToRemove){
		main:{
		if(lista==null) break main;
		if(intToRemove==null) break main;
		
			
		for(Object obj : lista){
			if(obj.equals(intToRemove)){			
				lista.remove(obj);	
				break main;
			}
		}		
	  }//end main:
	}
	
	
	
	
	public static void removeLast(ArrayList<?> lista, int iNumberOfElements2Remove){
		main:{
			if(lista==null) break main;
			if(lista.size()>iNumberOfElements2Remove){
				for(int iCount = 0; iCount <= iNumberOfElements2Remove; iCount++){
					int iLast = lista.size() - 1; //-1 da der Index mit 0 anfängt
					lista.remove(iLast);
				}
			}else{
				lista.clear();
			}
		}//end main:
	}
	
	/**
	 * @param lista
	 * @return
	 * 
	 * https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
	 */
//	public static <T> List<T> reverse(final List<T> list) {
//    final int size = list.size();
//    final int last = size - 1;
//
//    // create a new list, with exactly enough initial capacity to hold the (reversed) list
//    final List<T> result = new ArrayList<>(size);
//
//    // iterate through the list in reverse order and append to the result
//    for (int i = last; i >= 0; --i) {
//        final T element = list.get(i);
//        result.add(element);
//    }
//
//    // result now holds a reversed copy of the original list
//    return result;
//}
	public static <T> List<T> reverse(List<T> lista) {
	    List<T> listaReturn = null; 
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			
			
			// create a new list, with exactly enough initial capacity to hold the (reversed) list
			final int size = lista.size();
			final int last = size - 1;
			
		    listaReturn = new ArrayList<>(size);
		    
		    // iterate through the list in reverse order and append to the result
		    for (int i = last; i >= 0; --i) {
		        final T element = lista.get(i);
		        listaReturn.add(element);
		    }
		}//end main	
	    return listaReturn;
	}	
	
	/** von ChatGPT erstellt
	 * @param originalList
	 * @return
	 * @author Fritz Lindhauer, 09.11.2025, 08:21:28
	 */
	public static <T> ArrayList<T> reverse(ArrayList<T> listaOriginal) {
		ArrayList<T> listaReturn = null;
		main:{
			if(listaOriginal==null) break main;
			
        	// Sicherheitskopie erstellen, um die ursprüngliche Liste nicht zu verändern
        	listaReturn = new ArrayList<T>(listaOriginal);
        	Collections.reverse(listaReturn);
		}//end main
        return listaReturn;
    }
	
	public static <T> ArrayListUniqueZZZ<T> reverse(ArrayListUniqueZZZ<T> listaOriginal) {
		ArrayListUniqueZZZ<T> listaReturn = null;
		main:{
			if(listaOriginal==null) break main;
			
        	// Sicherheitskopie erstellen, um die ursprüngliche Liste nicht zu verändern
        	listaReturn = new ArrayListUniqueZZZ<T>(listaOriginal);
        	Collections.reverse(listaReturn);
		}//end main
        return listaReturn;
    }
	
	/**
	 * @param lista
	 * @return
	 * 
	 * siehe: https://javahungry.blogspot.com/2017/11/how-to-sort-arraylist-in-descending-order-in-java.html
	 */
	public static void  sortReverseAlphabetOrder(ArrayList<String> lista) {
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			 Collections.sort(lista, Collections.reverseOrder());		
		}//end main	
	}
	
	
	
	public static Object[]toArray(ArrayList<?> lista){
		Object[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new Object[lista.size()]);
			int iIndex = -1;
			for(Object obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(ArrayList<T> lista, Object obj) {
		T[]aReturn = null;
		main:{
			if(lista==null) break main;
			if(obj==null) {
				aReturn= (T[]) ArrayListUtilZZZ.toArray(lista);
				break main;
			}
			
			Class<T> objClass = (Class<T>) obj.getClass();
			aReturn = ArrayListUtilZZZ.toArray(lista, objClass);
			
		}//end main:
		return aReturn;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(ArrayList<T> lista, Class<T> classObjIn) {
		T[]aReturn = null;
		main:{
			if(lista==null) break main;
			
			Class<T> classObj = null; 
			if(classObjIn == null) {
				classObj = (Class<T>) Object.class;
			}else {
				classObj = classObjIn;				
			}
			
			aReturn = lista.toArray((T[]) java.lang.reflect.Array.newInstance(classObj, lista.size()));
		}
		return aReturn;
	}
	
	public static Component[]toComponentArray(ArrayList<Component> lista){
		Component[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new Component[lista.size()]);
			int iIndex = -1;
			for(Component obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}


	public static <E extends Enum> E[] toEnumArray(ArrayList<E> listae){
		E[] enumaReturn = null;
		main:{
			if(listae==null) break main;
			if(listae.size()==0) break main;
			
			enumaReturn = (E[]) listae.toArray(new Enum[listae.size()]);		
		}//end main:
		return enumaReturn;	
		}

	public static <E extends Enum & IEnumSetMappedZZZ> E[] toEnumArrayByMapped(ArrayList<E> listae){
		E[] objaeReturn = null;
		main:{
			if(listae==null) break main;
			if(listae.size()==0) break main;
			
			objaeReturn = (E[]) listae.toArray(new Enum[listae.size()]);
		}//end main:
		return objaeReturn;	
	}
	
	public static <E extends Enum & IEnumSetMappedStatusZZZ> E[] toEnumArrayByMappedStatus(ArrayList<E> listae){
		E[] objaeReturn = null;
		main:{
			if(listae==null) break main;
			if(listae.size()==0) break main;
			
			objaeReturn = (E[]) listae.toArray(new Enum[listae.size()]);
		}//end main:
		return objaeReturn;	
	}
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//	public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArrayByMapped(ArrayList<IEnumSetMappedZZZ> listae){
//		E[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			enumaReturn = (E[]) listae.toArray(new IEnumSetMappedZZZ[listae.size()]);
//		}//end main:
//		return enumaReturn;	
//	}
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//	public static <E extends IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ[] toEnumMappedStatusArrayByMapped(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
//		IEnumSetMappedStatusZZZ[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			///Das gibt eine java.lang.ArrayStoreException...
//			//enumaReturn = (E[]) listae.toArray(new IEnumSetMappedStatusZZZ[listae.size()]);
//			//enumaReturn = (IEnumSetMappedStatusZZZ[]) listae.toArray(new IEnumSetMappedStatusZZZ[listae.size()]);
//			
//			enumaReturn = EnumSetMappedUtilZZZ.toEnumMappedStatusArray(listae);
//			
//		}//end main:
//		return enumaReturn;	
//	}
	
	
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<E> toEnumMappedArrayListByMapped(List<E> listae) throws ExceptionZZZ{
		ArrayList<E> listaeReturn = null;
		main:{
			if(listae==null) break main;
			
			listaeReturn = new ArrayList<E>();
			if(listae.size()==0) break main;
			
			listaeReturn = EnumSetMappedUtilZZZ.toEnumMappedArrayList(listae);
		}//end main:
		return listaeReturn;	
	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<E> toEnumMappedStatusArrayListByMapped(List<E> listae) throws ExceptionZZZ{
		ArrayList<E> listaeReturn = null;
		main:{
			if(listae==null) break main;
			
			listaeReturn = new ArrayList<E>();
			if(listae.size()==0) break main;
			
			listaeReturn = EnumSetMappedUtilZZZ.toEnumMappedStatusArrayList(listae);
		}//end main:
		return listaeReturn;	
	}
	
	
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//	public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArray(ArrayList<E> listae){
//		E[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			enumaReturn = EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
//		}//end main:
//		return enumaReturn;	
//	}
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//	public static <E extends IEnumSetMappedStatusZZZ> E[] toEnumMappedStatusArray(ArrayList<E> listae) throws ExceptionZZZ{
//		E[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			enumaReturn = (E[]) EnumSetMappedUtilZZZ.toEnumMappedStatusArray(listae);
//		}//end main:
//		return enumaReturn;	
//	}

	
	public static File[]toFileArray(ArrayList<File> lista){
		File[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new File[lista.size()]);
			int iIndex = -1;
			for(File obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}
	
	public static int[]toIntArray(ArrayList<?> lista){
		int[] iaReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			Integer[]intaReturn = lista.toArray(new Integer[lista.size()]);
			
			int iIndex = -1;			
			iaReturn=new int[intaReturn.length];
			for(Integer objInt : intaReturn){
				iIndex++;
				iaReturn[iIndex] = objInt;
			}
		}//end main:
		return iaReturn;	
	}

	public static String[]toStringArray(ArrayList<?> lista){
		String[] saReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			saReturn = lista.toArray(new String[lista.size()]);
			int iIndex = -1;
			for(Object obj : lista){
				iIndex++;
				if(obj!=null) {
					saReturn[iIndex] = obj.toString();
				}else {
					saReturn[iIndex] = null;
				}
			}
		}//end main:
		return saReturn;	
	}

	
	public static ZipEntry[]toZipEntryArray(ArrayList<ZipEntry> lista){
		ZipEntry[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new ZipEntry[lista.size()]);
			int iIndex = -1;
			for(ZipEntry obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}
	
	//###############################
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<?> trim(ArrayList<?> lista){
		ArrayList listaReturn = null;
		main:{
			if(lista==null)break main;					
			
			listaReturn=new ArrayList();
			for(int icount=0; icount < lista.size(); icount++ ){
				Object obj = lista.get(icount);
				if(obj!=null) listaReturn.add(obj);
			}	
		}//End main:
		return listaReturn;
	}
	
	//###############################
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<?> unique(ArrayList<?> lista){
		ArrayList listaReturn = null;
		main:{
			listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(lista);
		}//End main:
		return listaReturn;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<?> uniqueKeepFirst(ArrayList<?> lista){
		ArrayList listaReturn = null;
		main:{
			if(lista==null)break main;					
		
			listaReturn=new ArrayList();
			for(int icount=0; icount < lista.size(); icount++ ){
				if(! listaReturn.contains(lista.get(icount))) listaReturn.add(lista.get(icount));
			}	
		}//End main:
		return listaReturn;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<?> uniqueKeepLast(ArrayList<?> lista){
		ArrayList listaReturn = null;
		main:{
			if(lista==null)break main;					
		
			//Strategie: KeepLast
			//Erst einmal die Listenreihenfolge umdrehen
			ArrayList<?> listaReversed = (ArrayList<?>) ArrayListUtilZZZ.reverse(lista);
			
			ArrayList listaReturnReversed=new ArrayList();
			for(int icount=0; icount < listaReversed.size(); icount++ ){
				if(! listaReturnReversed.contains(listaReversed.get(icount))) listaReturnReversed.add(listaReversed.get(icount));
			}	
			
			//Nach der Verarbeitung die Listenreihenfolge wieder zurückdrehen
			listaReturn = (ArrayList) ArrayListUtilZZZ.reverse(listaReturnReversed);
			
		}//End main:
		return listaReturn;
	}
}//END class
