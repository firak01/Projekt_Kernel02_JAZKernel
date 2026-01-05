package basic.zBasic.rule;

public class MatchRuleZZZ implements IMatchRuleZZZ{

	@Override
	public boolean matches(Object key, Object value) {
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
