package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

public class EnumUtilZZZ {
	public static String[] toString(Enum[] enuma) {
		String[] saReturn = null;
		main:{
			if(ArrayUtilZZZ.isEmpty(enuma)) break main;
			
			ArrayList<String>listasReturn = new ArrayList<String>();
			for(Enum e : enuma) {
				String s = e.toString();
				listasReturn.add(s);
			}
			
			saReturn = ArrayListZZZ.toStringArray(listasReturn);
		}//end main:
		return saReturn;
	}
	
	public static ArrayList<IEnumSetMappedZZZ> toArrayListMapped(Enum[] enuma) {
		ArrayList<IEnumSetMappedZZZ> listaeReturn = null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(enuma)) {	
				listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
				for(Enum objEnum : enuma) {				
					IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;				
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
