package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;

/**Merksatz (wichtig!) (Von ChatGPT)
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 *   
 * @author Fritz Lindhauer, 10.01.2026, 08:15:10
 * 
 */
public class EnumUtilZZZ {
	public static String[] toString(Enum[] enuma) {
		String[] saReturn = null;
		main:{
			if(ArrayUtilZZZ.isNull(enuma)) break main;
			
			ArrayList<String>listasReturn = new ArrayList<String>();
			for(Enum e : enuma) {
				String s = e.toString();
				listasReturn.add(s);
			}
			
			saReturn = ArrayListUtilZZZ.toStringArray(listasReturn);
		}//end main:
		return saReturn;
	}
	
	public static <E extends IEnumSetMappedZZZ> ArrayList<E> toArrayListMapped(Enum[] enuma) {	
		ArrayList<E> listaeReturn = null;
		main:{
			if(!ArrayUtilZZZ.isNull(enuma)) {	
				listaeReturn = new ArrayList<E>();
				for(Enum objEnum : enuma) {								
					E e = (E) objEnum;
					if(!listaeReturn.contains(e)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listaeReturn.add(e);
					}
				}			
			}
		}//end main:
		return listaeReturn;
	}
}
