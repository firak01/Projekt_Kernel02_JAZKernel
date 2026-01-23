package basic.zBasic.rule;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractMatchRuleZZZ<V,K> implements IMatchRuleZZZ<V,K>{

	@Override
	public abstract boolean matches(V value, K key) throws ExceptionZZZ;
}
