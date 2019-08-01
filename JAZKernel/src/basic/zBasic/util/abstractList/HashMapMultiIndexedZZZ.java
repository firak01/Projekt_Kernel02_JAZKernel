package basic.zBasic.util.abstractList;

import basic.zBasic.ExceptionZZZ;

public class HashMapMultiIndexedZZZ extends HashMapMultiZZZ{
	private int iIndex = -1;
	
	public void put(String sKey, String sValue) throws ExceptionZZZ{
		this.iIndex=this.iIndex + 1 ;
		Integer intKey = new Integer(this.iIndex);
		super.put(intKey, sKey, sValue);				
	}
	
	public int getIndexLast(){
		return this.iIndex;
	}
}
