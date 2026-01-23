package basic.zBasic.rule;

import basic.zBasic.ExceptionZZZ;

public interface IMatchRuleZZZ<V, K> {
	boolean matches(V value, K key) throws ExceptionZZZ;	
}