package basic.zBasic.util.abstractList;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
/* Erweiterung von Apache Commons CaseInsensitiveMap
 * Aus der Doku dazu: ...
 * 
 * A case-insensitive Map.

Before keys are added to the map or compared to other existing keys, they are converted to all lowercase in a locale-independent fashion by using information from the Unicode data file.

Null keys are supported.

The keySet() method returns all lowercase keys, or nulls.

Example:


  Map<String, String> map = new CaseInsensitiveMap<String, String>();
  map.put("One", "One");
  map.put("Two", "Two");
  map.put(null, "Three");
  map.put("one", "Four");
 

creates a CaseInsensitiveMap with three entries.
map.get(null) returns "Three" and map.get("ONE") returns "Four". The Set returned by keySet() equals {"one", "two", null}.

This map will violate the detail of various Map and map view contracts. As a general rule, don't compare this map to other maps. In particular, you can't use decorators like ListOrderedMap on it, which silently assume that these contracts are fulfilled.

Note that CaseInsensitiveMap is not synchronized and is not thread-safe. If you wish to use this map from multiple threads concurrently, you must use appropriate synchronization. The simplest approach is to wrap this map using Collections.synchronizedMap(Map). This class may throw exceptions when accessed by concurrent threads without synchronization. 
 * 
 */
public class HashMapCaseInsensitiveZZZ<T,X> extends CaseInsensitiveMap  implements  IConstantZZZ, IObjectZZZ{
	private static final long serialVersionUID = 1L;
	private ExceptionZZZ objException;
	
	public HashMapCaseInsensitiveZZZ(){
		super();
	}
	
	//#### GETTER / SETTER
		public ExceptionZZZ getExceptionObject() {
			return this.objException;
		}
		public void setExceptionObject(ExceptionZZZ objException) {
			this.objException = objException;
		}

}
