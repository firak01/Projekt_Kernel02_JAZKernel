/**
 * 
 */
package basic.zBasic.util.datatype.json;


import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.FlagZZZ;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

/**
 * @author 0823
 *
 */
public class JsonEasyZZZTest extends TestCase implements IConstantZZZ {
//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufr�umen um tearDown().
	
//	Objekt, das getestet werden soll
	private JsonArrayZZZ objArrayTest=null;
	
	protected void setUp(){
		try{
			String[] satest = {"eins", "zwei", "drei"};
			objArrayTest = new JsonArrayZZZ(satest);
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		    			
	}//END setup
	 
	public void testIsJsonValid(){
		try{
			boolean bErg;
			String sValue = "[[\"110917       \", 3.0099999999999998, -0.72999999999999998, 2.8500000000000001, 2.96, 685.0, 38603.0], [\"110917    \", 2.71, 0.20999999999999999, 2.8199999999999998, 2.8999999999999999, 2987.0, 33762.0]]";
		
			bErg = JsonEasyZZZ.isJsonValid(sValue);
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	
	
	public void testToJsonByStringBuilder(){
		try{
			//1. Die statische Methode testen.
			String[] saValue ={"B wert", "D wert", "F wert"};
			
			String sErg = StringArrayZZZ.toJsonByStringBuilder(saValue);
			assertNotNull(sErg);
			
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testToJson() {
		try{
			String sErg; String stemp;
			
			//1. Die statische Methode testen.
			String[] saValue ={"B wert", "D wert", "F wert"};
			
			sErg = StringArrayZZZ.toJson(saValue);
			assertNotNull(sErg);
			
			String sValue = "[[\"110917       \", 3.0099999999999998, -0.72999999999999998, 2.8500000000000001, 2.96, 685.0, 38603.0], [\"110917    \", 2.71, 0.20999999999999999, 2.8199999999999998, 2.8999999999999999, 2987.0, 33762.0]]";
			JsonArray objaJson = JsonEasyZZZ.toJsonArray(sValue);
			JsonElement objJsonElement = objaJson.get(0);
			stemp = objJsonElement.toString();
			System.out.println(stemp);
			
			JsonArray objArrayInner = JsonEasyZZZ.toJsonArray(stemp);
			
			int iCounter = 0;
			JsonArrayZZZ objJsonArray = new JsonArrayZZZ(objArrayInner);			
			Iterator it = objJsonArray.iterator();			
			while(it.hasNext()) {
				JsonElement element = (JsonElement) it.next();
				stemp = element.getAsString();
				System.out.println(stemp);
			}
			
			
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCreateJsonFromObject() {
//		try {
		String sErg;String sArg;
		
			FlagZZZ objFlag = new FlagZZZ();
			
			Gson gson = new Gson();
			sErg = gson.toJson(objFlag);
			System.out.println(sErg); //Ausgabe: {"hmFlag":{}}
			
			sArg = "{\"FlagZZZ\":{\"hmFlag\":{}}}";
			JsonObject obj = gson.fromJson(sArg, JsonObject.class);			
			JsonObject objFromJson = obj.getAsJsonObject("FlagZZZ");
		    System.out.println(objFromJson.toString()); //Ausgabe: {"hmFlag":{}}
			
		    //++++++++++++++++++++++++++++++++++
		    objFlag.setFlag("XYZ", true);
		    sErg = gson.toJson(objFlag);
			System.out.println(sErg); //Ausgabe: {"hmFlag":{"XYZ":true}}
		    
			objFlag.setFlag("abc", true);
			 sErg = gson.toJson(objFlag);
			System.out.println(sErg); //Ausgabe: {"hmFlag":{"XYZ":true,"abc":true}}
		    
			//+++++++++++++++++++++++++++++++++++
			//sArg = "{\"FlagZZZ\":{\"hmFlag\":{}}}";
			sArg = "{\"FlagZZZ\":{\"hmFlag\":{\"XYZ\":true,\"abc\":true}}}";
			FlagZZZ objFlagFromJson = gson.fromJson(sArg, FlagZZZ.class);
			
			//PROBLEM: Darin ist die HashMap nicht enthalten...
			//boolean bX = objFlagFromJson.getFlag("xxx");
			//System.out.println("xxx="+bX);			
			//PROBLEM: Hiermit werden also keine HashMaps übergeben....
			
			
			//##########
			//also etwas komplexer:
			//aus: https://stackoverflow.com/questions/12155800/how-to-convert-hashmap-to-json-object-in-java/61930940#61930940
			HashMap<String,Boolean>elements=new HashMap<String,Boolean>();
			elements.put("123", true);
			
			Gson gson2 = new Gson();
			Type gsonType = new TypeToken<HashMap>(){}.getType();
			String gsonString = gson2.toJson(elements,gsonType);
			System.out.println(gsonString);
			
			
			/*
			 {"profile": 
    {"name":"Josh",
     "position":"Programmer",
     "profile_id":1,
     "profile_image_id":10,
     "user_id":1472934469
    },
 "user":
    {"email":"example@you.co.za",
     "phone_numbers":[],
     "user_id":1,
     "user_type_id":1
    },
 "follower":
    {"follower_id":3,
     "following_date":1.4729345E9,
     "referred_by_id":2,
     "user_from_id":1,
     "user_to_id":2
    },
 "media":
    {"link":"uploads/profiles/profile-photos/originals/1-G9FSkRCzikP4QFY.png",
     "media_description":"",
     "media_id":10,
     "media_name":"",
     "media_slug":"",
     "medium_link":"uploads/profiles/profile-photos/thumbs-medium/1-G9FSkRCzikP4QFY.png",
     "thumbnail_link":"uploads/profiles/profile-photos/thumbs-small/1-G9FSkRCzikP4QFY.png",
     "uploader_id":1
    }
}

Now I create the Gson object:

Gson gson = new Gson();
// this creates the JSON string you see above with all of the objects
String str_obj = new Gson().toJson(mContactList.get(i)); 

Now instead of creating a custom class - just pass it through as a JsonObject using the following code:

JsonObject obj = gson.fromJson(str_obj, JsonObject.class);

And now, you can call the object like so:

JsonObject profile = obj.getAsJsonObject("profile");

			 */
			
			
			
			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
//	public void testCreateObjectFromJson() {
//		try {
//		
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
//	}
	
}
