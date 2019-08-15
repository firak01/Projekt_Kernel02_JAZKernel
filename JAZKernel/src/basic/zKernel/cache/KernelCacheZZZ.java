package basic.zKernel.cache;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public class KernelCacheZZZ extends AbstractKernelCacheZZZ{
	private HashMapMultiZZZ<String, String, IObjectCachableZZZ>hmCache=null;
	
	public KernelCacheZZZ(){
		super();
	}
	
	@Override
	public HashMapMultiZZZ<String, String, IObjectCachableZZZ> getHashMapCache() {
		if(this.hmCache==null){
			this.hmCache=new HashMapMultiZZZ<String,String,IObjectCachableZZZ>();
		}
		return this.hmCache;
	}

	@Override
	public void setHashMapCache(HashMapMultiZZZ<String, String, IObjectCachableZZZ> hmCache) {
		this.hmCache=hmCache;
	}

	@Override
	public IObjectCachableZZZ getCacheEntry(String sSection, String sProperty) throws ExceptionZZZ {
		HashMapMultiZZZ<String, String, IObjectCachableZZZ> hmCache = this.getHashMapCache();
		IObjectCachableZZZ objEntry = (IObjectCachableZZZ) hmCache.get(sSection, sProperty);
		return objEntry;
	}

	@Override
	public void setCacheEntry(String sSection, String sProperty, IObjectCachableZZZ objEntry) throws ExceptionZZZ {
		HashMapMultiZZZ<String, String, IObjectCachableZZZ> hmCache = this.getHashMapCache();
		hmCache.put(sSection, sProperty, objEntry);
	}

}
