package basic.zBasic.rule;

import basic.zBasic.ExceptionZZZ;

public class MatchRuleZZZ<K,V> implements IMatchRuleZZZ<K,V>{

	@Override
	public boolean matches(K key, V value) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(key==null) break main;
			if(value==null) break main;
			
			
			//bReturn = value.contains(key);
			bReturn = value.equals(key);
		}//end main:
		return bReturn;
	}
}
