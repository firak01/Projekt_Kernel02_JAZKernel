package basic.zBasic.util.string.formater;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.rule.AbstractMatchRuleZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;

public class LogStringFormatMatchRuleZZZ<V extends IStringJustifierZZZ,K extends IEnumSetMappedLogStringFormatZZZ> extends AbstractMatchRuleZZZ<V,K>{

	@Override
	public boolean matches(V value, K key) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(value==null) break main;
			if(key==null) break main;
		
			HashMap<Integer, String> hmSeparator = LogStringFormaterUtilZZZ.getHashMapLogStringSeparatorAllForLine();
			
			//Hole den Faktor aus dem IEnum, damit ein ggfs. vorhandener Faktor mit Steuerkennzeichen und Separator 
			//mit dem Separtor des Justifiers verglichen werden kann.
			int iFaktor = key.getFactor();					
			String sSeparatorByFormat = hmSeparator.get(iFaktor);
			if(StringZZZ.isEmpty(sSeparatorByFormat)) break main;
			
			String sSeparatorByJustifier = value.getPositionSeparator();
			//bReturn = value.contains(key);
			//bReturn = value.equals(key);
			bReturn = sSeparatorByJustifier.equals(sSeparatorByFormat);
			
			
			//bReturn = value.contains(key);
			//bReturn = value.equals(key);
		}//end main:
		return bReturn;
	}
}
