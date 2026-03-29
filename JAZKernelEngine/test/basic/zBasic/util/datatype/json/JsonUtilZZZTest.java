package basic.zBasic.util.datatype.json;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zKernel.flag.json.FlagContainerZZZ;
import junit.framework.TestCase;

public class JsonUtilZZZTest  extends TestCase implements IConstantZZZ {
	public void testCreateJsonFromObjectTryout() {
		try {
			String sErg;String sArg;
		
			//########################################
			//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
			FlagContainerZZZ objFlagContainer = new FlagContainerZZZ();		
			Gson gson = new Gson();
			sErg = gson.toJson(objFlagContainer);
			System.out.println(sErg); //Ausgabe: {"hmFlag":{}}
			
			//###############################################
			//BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB									
			//sArg = "{\"FlagZZZ\":{\"hmFlag\":{}}}";
			sArg = "{\"FlagZZZ\":{\"hmFlag\":{\"XYZ\":true,\"abc\":true}}}";
			//JsonObject obj = gson.fromJson(sArg, JsonObject.class);	
			
			/* WAS NICHT FUNKTIONIERT
			//s. https://mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
			//Gson gsonB = new Gson();
			//JsonElement objElement = gsonB.fromJson(sArg, JsonElement.class); //Fehlermeldung: java.lang.RuntimeException: Unable to invoke no-args constructor for class com.google.gson.JsonElement. Register an InstanceCreator with Gson for this type may fix this problem.
			 if (objElement.isJsonObject()) {
		            JsonObject obj = objElement.getAsJsonObject();
		            JsonObject objFromJson = obj.getAsJsonObject("FlagZZZ");
		            System.out.println(objFromJson.toString()); //Ausgabe: {"hmFlag":{}}
			 }
			  */
			
			//###############################################################################
			//CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC
			//Versuch, wir bauen im FlagContainer das Objekt und geben es als Json aus.
			//Danach umgekehrt vom Json das Objekt versuchen zu erzeugen.
			FlagContainerZZZ objFlagC = new FlagContainerZZZ();
			HashMap<String,Boolean> hmC = objFlagC.getHashMap();
			hmC.put("aaa", true);
			
			Gson gsonC = new Gson();
			String sJsonC = gsonC.toJson(objFlagC);
			System.out.println("C: " + sJsonC);
			
			//Nun umgekehrt:
			FlagContainerZZZ objFlagCReturn = gson.fromJson(sJsonC, FlagContainerZZZ.class);
			assertNotNull(objFlagCReturn);
			
			HashMap<String, Boolean> hmCReturn = objFlagCReturn.getHashMap();
			assertNotNull(hmCReturn);
			
			hmCReturn = objFlagCReturn.getHmFlag();
			assertNotNull(hmCReturn);
			
			
			
			
		    //#################################			
			//DDDDDDDDDDDDDDDDDDDDDDDDDDDDD
			FlagContainerZZZ objFlagContainerD = new FlagContainerZZZ();		
			Gson gsonD = new Gson();			
		    objFlagContainerD.setFlag("XYZ", true);
		    sErg = gsonD.toJson(objFlagContainerD);
			System.out.println(sErg); //Ausgabe: {"hmFlag":{"XYZ":true}}
		    
			objFlagContainerD.setFlag("abc", true);
			sErg = gsonD.toJson(objFlagContainerD);
			System.out.println(sErg); //Ausgabe: {"hmFlag":{"XYZ":true,"abc":true}}
		    
			//+++++++++++++++++++++++++++++++++++
			//sArg = "{\"FlagZZZ\":{\"hmFlag\":{}}}";
			//Beachte: CaseSensitivitaet bei der Verwendung der public Objekte der Klasse.
			sArg = "{\"HmFlag\":{\"XYZ\":true,\"abc\":true}}"; //Merke FlagContainerZZZ hat ein public Objekt: HmFlag
			FlagContainerZZZ objFlagFromJsonDReturn = gson.fromJson(sArg, FlagContainerZZZ.class); 
			assertNotNull(objFlagFromJsonDReturn);
			boolean bXYZ = objFlagFromJsonDReturn.getFlag("XYZ");
			System.out.println("XYZ="+bXYZ);			
			assertTrue(bXYZ);
			
			boolean bABC = objFlagFromJsonDReturn.getFlag("ABC");//FlagZ sollen ja caseinsensiv sein!!!
			System.out.println("ABC="+bABC);			
			assertTrue(bXYZ);
			
						
			//##########
			//EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
			//VARIENTE DIREKT MIT HASHMP ARBEITEN, leider irgendwie gescheitert
			//aus: https://stackoverflow.com/questions/12155800/how-to-convert-hashmap-to-json-object-in-java/61930940#61930940
			HashMap<String,Boolean>elements=new HashMap<String,Boolean>();
			elements.put("123", true);
			
			Gson gsonE = new Gson();
			Type gsonType = new TypeToken<HashMap<String,Boolean>>(){}.getType();
			String gsonString = gsonE.toJson(elements,gsonType);
			System.out.println(gsonString);
			
			//JsonObject obj2 = gsonE.fromJson(gsonString, gsonType);// java.lang.ClassCastException: java.util.HashMap cannot be cast to com.google.gson.JsonObject
			
			
			//#################
			//FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF
			//VARIANTE MIT DEM CONTAINEROBJEKT ARBEITEN, erfolgreich
			HashMap<String,Boolean>elementsF=new HashMap<String,Boolean>();
			elementsF.put("123", true);
			
			FlagContainerZZZ objFlagContainerF = new FlagContainerZZZ();		
			objFlagContainerF.setHashMap(elementsF);
			
			Gson gsonF = new Gson();
			Type gsonTypeF = new TypeToken<FlagContainerZZZ>(){}.getType();  //ja so kann man den TypeToken erzeugen. 
			String gsonStringF = gsonF.toJson(objFlagContainerF,gsonTypeF);
			System.out.println(gsonStringF);
			assertEquals("{\"HmFlag\":{\"abc\":true,\"xyz\":true}}", gsonStringF);
		
		//#############################
		//### Ein Beispiel aus dem Web
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
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	//Muss noch implementiert werden
//	public void testCreateObjectFromJson() {
//		try {
//		
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
//	}
	
	//Muss noch implementiert werden
//	public void testCreateJsonFromObject() {
//		try {
//			JsonUtilZZZ.
//			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
//	}
}
