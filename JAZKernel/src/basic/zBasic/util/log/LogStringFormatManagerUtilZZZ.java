package basic.zBasic.util.log;

import java.util.HashMap;
import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.rule.GenericArrayMatcher;
import basic.zBasic.rule.IMatchRuleZZZ;
import basic.zBasic.rule.MatchRuleZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
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
	public static IStringJustifierZZZ[] getStringJustifierArray(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault) throws ExceptionZZZ{	
		TODOGOON20260102: Algorithmus entwickeln hier aus dem Format die optimale Reihenfolge der Justifier festzulegen
		
		//MatchRuleZZZ<IEnumSetMappedLogStringFormatZZZ[],ArrayListZZZ<IStringJustifierZZZ>> containsRule = new MatchRuleZZZ<IEnumSetMappedLogStringFormatZZZ[], ArrayListZZZ<IStringJustifierZZZ>>() {
		MatchRuleZZZ<IEnumSetMappedLogStringFormatZZZ,IStringJustifierZZZ> containsRule = new MatchRuleZZZ<IEnumSetMappedLogStringFormatZZZ, IStringJustifierZZZ>() {
						
		    @Override
		    public boolean matches(IEnumSetMappedLogStringFormatZZZ key, IStringJustifierZZZ value) throws ExceptionZZZ {
		       
		    	HashMap<Integer, String> hmSeparator = LogStringFormaterUtilZZZ.getHashMapLogStringSeparatorAllForLine();
		    	
				boolean bReturn=false;
				main:{
					if(key==null) break main;
					if(value==null) break main;
					
					//Hole den Faktor aus dem IEnum, damit ein ggfs. vorhandener Faktor mit Steuerkennzeichen und Separator 
					//mit dem Separtor des Justifiers verglichen werden kann.
					int iFaktor = key.getFactor();					
					String sSeparatorByFormat = hmSeparator.get(iFaktor);
					if(StringZZZ.isEmpty(sSeparatorByFormat)) break main;
					
					String sSeparatorByJustifier = value.getPositionSeparator();
					//bReturn = value.contains(key);
					//bReturn = value.equals(key);
					bReturn = sSeparatorByJustifier.equals(sSeparatorByFormat);
				}//end main:
				return bReturn;
				// return key != null && value != null && value.contains(key);
		    }
		};

		IStringJustifierZZZ[] aStringJustifierDefault = ArrayListUtilZZZ.toArray(listaStringJustifierDefault, IStringJustifierZZZ.class);
		IStringJustifierZZZ[] result = GenericArrayMatcher.filter(ienumaFormatLogString,aStringJustifierDefault,containsRule, IStringJustifierZZZ.class);
		return result;
	}
	
	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ>listaReturn=null;
		main:{
			IStringJustifierZZZ[] aStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierArray(ienumaFormatLogString, listaStringJustifierDefault);
			if(aStringJustifier==null) break main;
		
			listaReturn = new ArrayListZZZ<IStringJustifierZZZ>();
			for(IStringJustifierZZZ objJustifier : aStringJustifier) {
				listaReturn.add(objJustifier);
			}					
		}//end main;
		return listaReturn;
	}
	
	
	/** Algorithmus der hier aus dem Format die optimale Reihenfolge der Justifier festlegt.
	 *  Ggfs. wird eine Liste der moeglichen Justifiert/Defaultjustifier als Alternative uebergeben.
	 * @param ienumFormatLogString
	 * @param listaStringJustifierDefault
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.01.2026, 10:40:25
	 */
	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault) throws ExceptionZZZ{
		//TODOGOON20260102: Algorithmus entwickeln hier aus dem Format die optimale Reihenfolge der Justifier festzulegen
		return listaStringJustifierDefault;
	}

	/** Algorithmus der hier aus dem Format die optimale Reihenfolge der Justifier festlegt.
	 *  Ggfs. wird eine Liste der moeglichen Justifiert/Defaultjustifier als Alternative uebergeben.
	 * @param ienumFormatLogString
	 * @param listaStringJustifierDefault
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.01.2026, 10:40:25
	 */
	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String>hm, ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault) throws ExceptionZZZ{
		//TODOGOON20260102: Algorithmus entwickeln hier aus dem Format die optimale Reihenfolge der Justifier festzulegen
		return listaStringJustifierDefault;
	}
	
	/** Algorithmus der hier aus dem Format die optimale Reihenfolge der Justifier festlegt.
	 *  Ggfs. wird eine Liste der moeglichen Justifiert/Defaultjustifier als Alternative uebergeben.
	 * @param ienumFormatLogString
	 * @param listaStringJustifierDefault
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.01.2026, 10:40:25
	 */
	public static ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(ILogStringFormaterZZZ objFormater, ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault) throws ExceptionZZZ{
		//TODOGOON20260102: Algorithmus entwickeln hier aus dem Format die optimale Reihenfolge der Justifier festzulegen
		return listaStringJustifierDefault;
	}
}
