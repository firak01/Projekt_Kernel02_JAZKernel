package basic.zBasic.util.datatype.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class JsonEasyZZZ extends AbstractObjectZZZ{
	
	/**Ein Json-Parser wird verwendet. 
	 * Intern arbeitet er so: Wenn er einen Fehler wirft, ist es kein valides Json. Dieser Fehler wird abgefangen.
	 *                        Nur ein Leerstring/Nullstring wirft einen ExceptionZZZ Fehler.
	 * @return
	 * @author Fritz Lindhauer, 24.03.2021, 09:37:42
	 * @throws ExceptionZZZ 
	 */
	public static boolean isJsonValid(String sJson) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sJson)){
				ExceptionZZZ ez = new ExceptionZZZ("No string provided.", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			try {
				
				JsonParser parser = new JsonParser();
				JsonElement objReturn = parser.parse(sJson);
				bReturn = true;
			}catch(JsonSyntaxException jse) {
				//Mache nix
			}
		}//end main:
		return bReturn;
	}
	
	public static String toJson(String[] saValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(saValue==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array provided.", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
							
			Gson gson=new GsonBuilder().create();
			sReturn =gson.toJson(saValue);
		}//end main;
		return sReturn;
	}
	
	public static JsonElement toJson(String s) throws ExceptionZZZ {
		JsonElement objReturn = null;
		main:{
			try {
			JsonParser parser = new JsonParser();
			objReturn = parser.parse(s);
			}catch(JsonSyntaxException jse) {
				ExceptionZZZ ez = new ExceptionZZZ("JsonSyntaxException: " + jse.getMessage(), iERROR_RUNTIME, JsonEasyZZZ.class, jse);
				throw ez;
			}
		}//end main;
		return objReturn;
	}
	
	public static JsonElement toJsonElement(String[] sa) throws ExceptionZZZ {
		JsonElement objReturn = null;
		main:{
			Gson gson=new GsonBuilder().create();
			String jsonArray=gson.toJson(sa);
	    
			objReturn = JsonEasyZZZ.toJson(jsonArray);
	    
		}//end main;
		return objReturn;
	}
	
	
	public static JsonArray toJsonArray(String s) throws ExceptionZZZ {
		JsonArray objReturn = null;
		main:{
			if(StringZZZ.isEmpty(s)){
				ExceptionZZZ ez = new ExceptionZZZ("No string available.", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			JsonElement objJson = JsonEasyZZZ.toJson(s);				
			objReturn = objJson.getAsJsonArray();											
		}//end main
		return objReturn;
	}
	
	public static JsonArray toJsonArray(String[] sa) throws ExceptionZZZ {
		JsonArray objReturn = null;
		main:{
			if(sa==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			JsonElement objJson = JsonEasyZZZ.toJsonElement(sa);
			objReturn = objJson.getAsJsonArray();									
		}
		return objReturn;
	}
	
	/** Liefert generisch aus dem JSON String eine HashMap zurück.
	 *  
	 * Beispiel für TypeToken Erstellung: 
	 * TypeToken<HashMap<String, Boolean>> typeToken = new TypeToken<HashMap<String, Boolean>>(){};
	 * 
	 * Der Rückgabewert muss dann entsprechend gecasted werden:
	 * HashMap<String, Boolean> hm = (HashMap<String, Boolean>) JsonEasyZZZ.toHashMap(typeToken, sJSON);
	 * @param type
	 * @param sJson
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.04.2021, 09:52:34
	 */
	public static HashMap<?,?> toHashMap(String sJson) throws ExceptionZZZ {
		HashMap<?,?> hmReturn = null; 
		main:{
			if(StringZZZ.isEmpty(sJson)){
				ExceptionZZZ ez = new ExceptionZZZ("No string available.", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!JsonEasyZZZ.isJsonValid(sJson)) {
				ExceptionZZZ ez = new ExceptionZZZ("JsonString not valid '" + sJson + "'", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//Das berücksichtigt nicht die Reihenfolge!!
			TypeToken<HashMap<?,?>> typeToken = new TypeToken<HashMap<?,?>>(){};
									
			Gson gson=new GsonBuilder().create();
			Type type = typeToken.getType();
			hmReturn = gson.fromJson(sJson, type);		
		}//end main:
		return hmReturn;
	}
	
	public static LinkedHashMap<?,?> toLinkedHashMap(String sJson) throws ExceptionZZZ{
		LinkedHashMap<?,?> hmReturn = null;
		main:{
			if(StringZZZ.isEmpty(sJson)){
				ExceptionZZZ ez = new ExceptionZZZ("No string available.", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!JsonEasyZZZ.isJsonValid(sJson)) {
				ExceptionZZZ ez = new ExceptionZZZ("JsonString not valid '" + sJson + "'", iERROR_PARAMETER_MISSING, JsonEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			TypeToken<LinkedHashMap<?,?>> typeToken = new TypeToken<LinkedHashMap<?,?>>(){};
			Type type = typeToken.getType();
			
			Gson gson=new GsonBuilder().create();
			hmReturn = gson.fromJson(sJson, type);		
		}//end main:
		return hmReturn;
	}
	
	//Benötigt JavaEE 1.7
//	public static ArrayList<String> toArrayList(javax.json.JsonArray jsonObject){
//		ArrayList<String> alsReturn = new ArrayList<String>();     
//		main:{
//			if(jsonObject==null)break main;
// 
//		   for (int i=0;i<jsonObject.length();i++){ 
//		    listdata.add(jArray.getString(i));
//		   } 
//
//		}//end main:
//		return alsReturn;
//	}
	
	public static ArrayList<String> toArrayListString(String sJson){
		ArrayList<String> alsReturn = new ArrayList<String>();     
		main:{			
			if(StringZZZ.isEmpty(sJson))break main;
			
		    Gson gson = new Gson();
		    Type type = new TypeToken<List<String>>(){}.getType();
		    List<String> ls = gson.fromJson(sJson, type);
		    for (String s : ls){
		        //Log.i("Contact Details", contact.id + "-" + contact.name + "-" + contact.email);
		    	alsReturn.add(s);
		    }
		}//end main:
		return alsReturn;
	}

	public static <T> ArrayList<T> toArrayList(TypeToken typeToken, String sJson){
		ArrayList<T> alsReturn = new ArrayList<T>();     
		main:{			
			if(StringZZZ.isEmpty(sJson))break main;
			
		    Gson gson = new Gson();
		    Type type = typeToken.getType();
		    List<?> ls = gson.fromJson(sJson, type);
		    for (Object obj : ls){
		        //Log.i("Contact Details", contact.id + "-" + contact.name + "-" + contact.email);
		    	alsReturn.add((T) obj);
		    }
		}
		return alsReturn;
	}

/** Verwendet Bibliothek: org.json
	/**aus: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	 * @param json
	 * @return
	 * @throws JSONException
	 * @author Fritz Lindhauer, 26.03.2021, 10:05:11

	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
	    Map<String, Object> retMap = new HashMap<String, Object>();

	    if(json != JSONObject.NULL) {
	        retMap = toMap(json);
	    }
	    return retMap;
	}

	/**aus: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	 * @param json
	 * @return
	 * @throws JSONException
	 * @author Fritz Lindhauer, 26.03.2021, 10:05:11
	 
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}

	/**aus: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
	 * @param json
	 * @return
	 * @throws JSONException
	 * @author Fritz Lindhauer, 26.03.2021, 10:05:11
	 
	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	*/
	
}
