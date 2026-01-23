package basic.zBasic.rule;

import basic.zBasic.ExceptionZZZ;

public class ExampleMatchRuleZZZ<V,K> extends AbstractMatchRuleZZZ<V,K>{

	@Override
	public boolean matches(V value, K key) throws ExceptionZZZ {
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
