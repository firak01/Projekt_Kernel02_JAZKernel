package basic.zBasic.util.string.formater;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedLogStringFormatUtilZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;

public class StringFormatManagerUtilZZZ  implements IConstantZZZ{

	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType01(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, int iNumberOfLines) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{			
			for(int i=1;i<=iNumberOfLines;i++) {
				objaReturn = appendLines_StringType01(objaReturn);
			}			
		}//end main:
		return objaReturn;
	}
	
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType01(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{
			ArrayList<IEnumSetMappedStringFormatZZZ> listaFormat = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArrayList(ienumaFormatLogString);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING);
			
			objaReturn = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaFormat);
		}//end main:
		return objaReturn;
	}
	
	public static boolean isStringType01(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iFaktor = ienumFormatLogString.getFactor();
			int[] iaFaktorStringType01 = new int[]{
					IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING,
					IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING
			};
			
			if(IntegerArrayZZZ.contains(iaFaktorStringType01, iFaktor)) bReturn=true;
		}//end main:
		return bReturn;
	}
	
	public static int evaluateNumberOf_StringType01(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{				
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, StringFormatManagerUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) break main;
			
			
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogStringIn) {
				if(isStringType01(ienumFormatLogString)){
					iReturn++;
				}
				
				
			}								
		}//end main:
		return iReturn;
	}
}
