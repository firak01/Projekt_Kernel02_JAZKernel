package basic.zBasic.util.string.formater;

import java.util.HashMap;
import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.rule.GenericMatcherArray;
import basic.zBasic.rule.IMatchRuleZZZ;
import basic.zBasic.rule.ExampleMatchRuleZZZ;
import basic.zBasic.rule.GenericMatcherArrayList;
import basic.zBasic.rule.GenericMatcherArrayListZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedLogStringFormatUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class LogStringFormatManagerUtilZZZ implements IConstantZZZ{
	
	
	
	
	/** Algorithmus der hier aus dem Format die optimale Reihenfolge der Justifier festlegt.
	 *  Ggfs. wird eine Liste der moeglichen Justifiert/Defaultjustifier als Alternative uebergeben.
	 * @param ienumFormatLogString
	 * @param listaStringJustifierDefault
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.01.2026, 10:40:25
	 */
	public static IStringJustifierZZZ[] getStringJustifierArray(ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		IStringJustifierZZZ[] objaReturn = null;	
		main:{
			if(listaStringJustifier==null) break main;
			
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierReturn = LogStringFormatManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifier,ienumaFormatLogString);	
			objaReturn = ArrayListUtilZZZ.toArray(listaStringJustifierReturn, IStringJustifierZZZ.class);
		}
		return objaReturn;
	}
		
	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ>listaReturn=null;
		main:{			
			if(listaStringJustifier==null) break main;
			
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString= new IEnumSetMappedLogStringFormatZZZ[1];
			ienumaFormatLogString[0] = ienumFormatLogString;
			listaReturn = getStringJustifierListFiltered(listaStringJustifier, ienumaFormatLogString);						
		}//end main:
		return listaReturn;			
	}
	
	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ>listaReturn=null;
		main:{			
			if(listaStringJustifier==null) break main;
			
			LogStringFormatMatchRuleZZZ<IStringJustifierZZZ, IEnumSetMappedLogStringFormatZZZ> containsRule = new LogStringFormatMatchRuleZZZ<IStringJustifierZZZ, IEnumSetMappedLogStringFormatZZZ>();				
			listaReturn = listaStringJustifier.filter(ienumFormatLogString, containsRule);						
		}//end main:
		return listaReturn;			
	}
	
	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ>listaReturn=null;
		main:{			
			if(listaStringJustifier==null) break main;
			
			LogStringFormatMatchRuleZZZ<IStringJustifierZZZ, IEnumSetMappedLogStringFormatZZZ> containsRule = new LogStringFormatMatchRuleZZZ<IStringJustifierZZZ, IEnumSetMappedLogStringFormatZZZ>();				
			listaReturn = listaStringJustifier.filter(ienumaFormatLogString, containsRule);						
		}//end main:
		return listaReturn;			
	}



	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		ArrayListZZZ<IStringJustifierZZZ>listaReturn=null;
		main:{
			if(listaStringJustifier == null) break main;
			
			//Hole aus der HashMap die Bestandteile, die Kontrollformat sind als Arraylist						
			ArrayListZZZ<IEnumSetMappedLogStringFormatZZZ> listaEnumFormatLogString= LogStringFormaterUtilZZZ.getArrayListLogStringSeparatorFrom(hm);
			 
			IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaEnumFormatLogString);
			
			LogStringFormatMatchRuleZZZ<IStringJustifierZZZ, IEnumSetMappedLogStringFormatZZZ> containsRule = new LogStringFormatMatchRuleZZZ<IStringJustifierZZZ, IEnumSetMappedLogStringFormatZZZ>();							
			listaReturn = listaStringJustifier.filter(ienumaFormatLogString, containsRule);
		}//end main:
		return listaReturn;	
	}
}
