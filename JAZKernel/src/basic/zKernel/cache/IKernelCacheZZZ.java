package basic.zKernel.cache;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public interface IKernelCacheZZZ {
	public HashMapMultiZZZ<String,String,IObjectCachableZZZ> getHashMapCache();
	public void setHashMapCache(HashMapMultiZZZ<String,String,IObjectCachableZZZ> hmMulti);
	
	public IObjectCachableZZZ getCacheEntry(String sSection, String sProperty) throws ExceptionZZZ; //Merke: In dem Cache wird wie in der Konfiguratins-Ini-Datei von Section und Property als Schlüsselwerten gesprochen.
	public void setCacheEntry(String sSection, String sProperty, IObjectCachableZZZ objEntry)throws ExceptionZZZ;
}
