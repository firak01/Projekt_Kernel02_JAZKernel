package basic.zBasic.util.datatype.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class JsonEasyZZZ extends ObjectZZZ{
	
	/**Ein Json-Parser wird verwendet. 
	 * Wenn er einen Fehler wirft, ist es kein valides Json
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

}
