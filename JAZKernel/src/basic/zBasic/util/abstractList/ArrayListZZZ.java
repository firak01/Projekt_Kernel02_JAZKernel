package basic.zBasic.util.abstractList;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class ArrayListZZZ<T> extends AbstractObjectZZZ {
private ArrayListZZZ() { } //static methods only
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
public static boolean isSameSize(ArrayList objAL1, ArrayList objAL2) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objAL1== null){
				ExceptionZZZ ez = new ExceptionZZZ("ArrayList1 to compare'", iERROR_PARAMETER_MISSING,  HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(objAL2== null){
				ExceptionZZZ ez = new ExceptionZZZ("ArrayList2 to compare'", iERROR_PARAMETER_MISSING,   HashMapExtendedZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
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
				sReturn=sPosition+sDelimiter;
			}else{
				sReturn+=sPosition+sDelimiter;
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
				sReturn+=sPosition;
			}
		}		
	}
	return sReturn;
}
	
public static ArrayList join(ArrayList lista1, ArrayList lista2, boolean bFlagUnique){
	ArrayList listaReturn = null;
	main:{
		check:{
			if(lista1==null && lista2 ==null) break main;
		}//END check:
	
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
		listaReturn = ArrayListZZZ.joinKeepFirst(lista1,lista2);		
		break main;
	}//End if (bFlagUnique ....
	
	}//END main:
	return listaReturn;
}

public static ArrayList joinKeepFirst(ArrayList lista1, ArrayList lista2){
	ArrayList listaReturn = null;
	main:{
		check:{
			if(lista1==null && lista2 ==null) break main;
		}//END check:
	
	
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
		
		listaReturn = ArrayListZZZ.uniqueKeepFirst(listaTemp);			
	}//END main:
	return listaReturn;
}

public static ArrayList joinKeepLast(ArrayList lista1, ArrayList lista2){
	ArrayList listaReturn = null;
	main:{
		check:{
			if(lista1==null && lista2 ==null) break main;
		}//END check:
	
	
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
		
		listaReturn = ArrayListZZZ.uniqueKeepLast(listaTemp);			
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
//public static void  reverse(ArrayList<String> lista) {
//	main:{
//		if(lista==null) break main;
//		if(lista.size()==0) break main;
//		
//		 	
//	}//end main	
//}
public static <T> List<T> reverse(final List<T> list) {
    final int size = list.size();
    final int last = size - 1;

    // create a new list, with exactly enough initial capacity to hold the (reversed) list
    final List<T> result = new ArrayList<>(size);

    // iterate through the list in reverse order and append to the result
    for (int i = last; i >= 0; --i) {
        final T element = list.get(i);
        result.add(element);
    }

    // result now holds a reversed copy of the original list
    return result;
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

	public static <E extends Enum> E[] toEnumArray(ArrayList<E> listae){
		E[] enumaReturn = null;
		main:{
			if(listae==null) break main;
			if(listae.size()==0) break main;
			
			enumaReturn = (E[]) listae.toArray(new Enum[listae.size()]);
//			saReturn = lista.toArray(new String[lista.size()]);
//			int iIndex = -1;
//			for(Object obj : lista){
//				iIndex++;
//				saReturn[iIndex] = obj.toString();
//			}
		}//end main:
		return enumaReturn;	
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
				saReturn[iIndex] = obj.toString();
			}
		}//end main:
		return saReturn;	
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
	
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<?> unique(ArrayList<?> lista){
		ArrayList listaReturn = null;
		main:{
			listaReturn = ArrayListZZZ.uniqueKeepFirst(lista);
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
			ArrayList<?> listaReversed = (ArrayList<?>) ArrayListZZZ.reverse(lista);
			
			ArrayList listaReturnReversed=new ArrayList();
			for(int icount=0; icount < listaReversed.size(); icount++ ){
				if(! listaReturnReversed.contains(listaReversed.get(icount))) listaReturnReversed.add(listaReversed.get(icount));
			}	
			
			//Nach der Verarbeitung die Listenreihenfolge wieder zurückdrehen
			listaReturn = (ArrayList) ArrayListZZZ.reverse(listaReturnReversed);
			
		}//End main:
		return listaReturn;
	}
}//END class
