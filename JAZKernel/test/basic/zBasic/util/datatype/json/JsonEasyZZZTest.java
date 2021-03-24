/**
 * 
 */
package basic.zBasic.util.datatype.json;


import junit.framework.TestCase;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

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
	
}
