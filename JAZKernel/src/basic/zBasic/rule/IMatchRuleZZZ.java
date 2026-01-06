package basic.zBasic.rule;

import basic.zBasic.ExceptionZZZ;

public interface IMatchRuleZZZ<K, V> {
	boolean matches(K key, V value) throws ExceptionZZZ;	
}