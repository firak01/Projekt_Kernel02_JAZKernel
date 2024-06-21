package basic.zBasic.util.abstractList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadEncryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

/** Diese Klasse erweitert Hashmap um einige Funktionen.
 *   - Es bleibt nur der erste Eintrag bestehen.
 *     wenn man einen weiteren Eintrag fuer den gleichen Schluessel hinzufuegt.
 *     (Daher wollte ich diese Klasse ggfs. auch nennen "HashMapDictionaryZZZ")
 *     
 * - Merke: Klasse HashMapKeepLastZZZ gibt es nicht.
 *          Jede HashMap ist de facto "keep last";
 * @author lindhaueradmin
 *
 */
public class HashMapKeepFirstZZZ<K,V> extends HashMap implements  IConstantZZZ, IObjectZZZ, IHashMapKeepFirstZZZ{
	private static final long serialVersionUID = -576703130885041379L;
	private ExceptionZZZ objException;
	
	public HashMapKeepFirstZZZ(){
	}
	
	public static HashMapKeepFirstZZZ toHashMapKeepFirst(HashMap hm){
		HashMapKeepFirstZZZ hmReturn=null;
		main:{
			if(hm==null)break main;
			
			hmReturn=new HashMapKeepFirstZZZ();
			hmReturn.putAll(hm);
			
//			Set setKey = hm.keySet();
//			Iterator itKey = setKey.iterator();
//			while(itKey.hasNext());{
//				hmReturn.putAll(m);
//			}
			
		}//end main:
		return hmReturn;
	}
	
	public static HashMapKeepFirstZZZ clone(HashMap hm) {
		HashMapKeepFirstZZZ hmReturn=null;
		main:{
			if(hm==null)break main;
			
			hmReturn=new HashMapKeepFirstZZZ();
			HashMap hmNew = (HashMap) hm.clone();
			hmReturn.putAll(hmNew);
			
		}//end main:
		return hmReturn;
	}
	
	public static Object put(HashMap<Object, Object> hm, Object key, Object value) throws ExceptionZZZ{
		return HashMapKeepFirstZZZ.putButKeep(hm, key, value);
	}
	
	public static Object putButKeep(HashMap<Object, Object> hm, Object key, Object value) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			if(hm==null) break main;
			if(key==null) {				
				ExceptionZZZ ez = new ExceptionZZZ( "Key missing", iERROR_PARAMETER_MISSING, HashMapKeepFirstZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			boolean bExists = hm.containsKey(key);	
			if(!bExists) {
				//hm.putIfAbsent(key, value);		    //So eine Funktion mit der Anforderung gibt es also schon in HashMap selbst.
				//aber: Exception in thread "main" java.lang.NoSuchMethodError: java.util.HashMap.putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
				//Grund: Erst ab Java 1.8.
					
				hm.put(key, value);
				objReturn = value;
			}else {
				objReturn = hm.get(key);
			}
		}//end main:		
		return objReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		Object objReturn = null;
		
		//ACHTUNG: Hier ruft die HashMap wg. "this" immer wieder diese put(...) Methode selbst auf, darum putButKeep(...) .
		//objReturn = HashMapKeepFirstZZZ.putButKeep(this, key, value);
			
		if(this.containsKey(key)) {
				//mach nix
		}else {
			super.put(key, value);
		}
		return objReturn;				
	}
	
	@Override
	public Object putButKeep(Object key, Object value) throws ExceptionZZZ {
		return HashMapKeepFirstZZZ.putButKeep(this, key, value);
	}

	
	//#### GETTER / SETTER
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}

	
}
