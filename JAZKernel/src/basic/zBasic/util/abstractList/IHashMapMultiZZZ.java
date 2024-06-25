package basic.zBasic.util.abstractList;

import java.util.Map;

import basic.zBasic.IConstantZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;

public interface IHashMapMultiZZZ<K,V> extends IHashMapExtendedZZZ, IOutputDebugNormedWithKeyZZZ, IConstantZZZ, Map{
	public V getElementByIndex(int iIndex); //zur Vereinheitlichung, weil das in HashMapExtendedZZZ auch verwendet wird.
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		TO_HASHMAP_KEEPFIRST, TO_HASHMAP_KEEPLAST
	}
	
	//............ Flagmethoden erst einmal nicht, die Klasse erweitert nix mit Abstract...FlagUser... 
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
