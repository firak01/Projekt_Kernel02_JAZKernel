package zBasic.util.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.data.DataFieldZZZ;
import basic.zBasic.util.data.DataStoreZZZ;

public class DataStoreZZZTest  extends TestCase{
    private DataStoreZZZ objStoreTest = null;
	
    protected void setUp(){
      
	try {			
		//### Die TestObjecte		
		objStoreTest=new DataStoreZZZ("MyTest");
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 	
}//END setup
    
    
public void testFieldSetGet(){
	
	try{
		//1. die Metadaten definieren + setzen
		DataFieldZZZ objField = new DataFieldZZZ("Alias1");
		objField.Datatype= DataFieldZZZ.sSTRING;
		objField.Fieldname = "MyField1";
		objStoreTest.setField(objField);
	
		objField = new DataFieldZZZ("Alias2");
		objField.Datatype = DataFieldZZZ.sINTEGER;
		objField.Fieldname = "MyField2";
		objStoreTest.setField(objField);
		
		objField = new DataFieldZZZ("Alias3");
		objField.Datatype = DataFieldZZZ.sDATE;
		objField.Format="dd.MM.yyyy";
		objField.Fieldname = "MyField3";
		objStoreTest.setField(objField);
		
				
		//3. auslesen
		String stemp = objStoreTest.getMetadata("Alias1", "Fieldname");
		assertEquals("MyField1", stemp);
		
		stemp = objStoreTest.getMetadata("Alias3", "Format");
		assertEquals("dd.MM.yyyy", stemp);
		
		
		//###########################
		//nun einen Werte definieren
		objStoreTest.appendValue("Alias1", "Das ist ein Test");
		
		Integer objInt = new Integer(43);
		objStoreTest.appendValue("Alias2", objInt);
		
		String stemp2 = objStoreTest.getValueString("Alias1", 0);
		assertEquals("Das ist ein Test", stemp2);
		
		String stemp3 = objStoreTest.getValueString("Alias2", 0); //!!! DAS WURDE ALS INTEGER WEGGESPEICHERT, WIR HOLEN ES ABER ALS STRING ZUR�CK
		assertEquals("43", stemp3);
					
		//nun mehrere Werte einf�gen
		objStoreTest.appendValue("Alias1", "Das ist ein Test 2");
		String stemp4 = objStoreTest.getValueString("Alias1", 1);
		assertEquals("Das ist ein Test 2", stemp4);
		
		
		//##########################################
		//Datumswerte testen
		//1. Leerstring auslesen
		stemp = objStoreTest.getValueString("Alias3", 0);
		assertNull(stemp);
		
		objStoreTest.appendValue("Alias3", "");
		stemp = objStoreTest.getValueString("Alias3", 0);
		assertEquals("", stemp);
		
		//2. Datumswert setzen
		String sDateIn = "10.5.2006";
		objStoreTest.appendValue("Alias3", sDateIn);
			
		//... als String auslesen
		String sDateOut = objStoreTest.getValueString("Alias3", 1);
		assertEquals("10.05.2006", sDateOut);
		
		//nun ein Datum weiteres einf�gen
		objStoreTest.replaceValue("Alias3", "6.12.2006");
		String stemp5 = objStoreTest.getValueString("Alias3", 0);
		assertEquals("06.12.2006", stemp5);                                    //!!! Konveriterung in das passende Format !!!
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

	public void testValueReplace(){
		try{
	//		1. die Metadaten definieren + setzen
			DataFieldZZZ objField = new DataFieldZZZ("Alias1");
			objField.Datatype= DataFieldZZZ.sSTRING;
			objField.Fieldname = "MyField1";
			objStoreTest.setField(objField);
			
			objField = new DataFieldZZZ("Alias2");
			objField.Datatype= DataFieldZZZ.sSTRING;
			objField.Fieldname = "MyField2";
			objStoreTest.setField(objField);
			
			
			//2. nun mehrere Werte definieren, auch f�r den anderen Alias
			objStoreTest.appendValue("Alias1", "Das ist ein Test 1");	
			objStoreTest.appendValue("Alias2", "Das muss stehenbleiben 1");
			objStoreTest.appendValue("Alias2", "Das muss stehenbleiben 2");
			objStoreTest.appendValue("Alias1", "Das ist ein Test 2");
			
			//TEST starten
			//1. An der Stelle 0 soll nun der erste gesetzte Wert stehen
			String stemp = objStoreTest.getValueString("Alias1", 0);
			assertEquals("Das ist ein Test 1", stemp);
			
			//2. Nun das Ersetzen, anschliessend soll an der Stelle 0 der neue Wert stehen. Eine Stelle 1 darf es nicht geben.
			objStoreTest.replaceValue("Alias1", "Das ist neu");
			stemp = objStoreTest.getValueString("Alias1", 0);
			assertEquals("Das ist neu", stemp);
		
			stemp = objStoreTest.getValueString("Alias1", 1);
			assertNull(stemp);
			
			//Die alten Werte des anderen Alias d�rfen nicht ver�ndert sein
			stemp = objStoreTest.getValueString("Alias2", 0);
			assertEquals("Das muss stehenbleiben 1", stemp);
			
			stemp = objStoreTest.getValueString("Alias2", 1);
			assertEquals("Das muss stehenbleiben 2", stemp);
			
			
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	}
	
	public void testVectorSetGet(){
		try{
			//1. die Metadaten definieren + setzen
			DataFieldZZZ objField = new DataFieldZZZ("Alias1");
			objField.Datatype=DataFieldZZZ.sSTRING;
			objField.Fieldname = "MyField1";
			objStoreTest.setField(objField);
		
			objField = new DataFieldZZZ("Alias2");
			objField.Datatype = DataFieldZZZ.sINTEGER;
			objField.Fieldname = "MyField2";
			objStoreTest.setField(objField);
			
			objField = new DataFieldZZZ("Alias3");
			objField.Datatype=DataFieldZZZ.sSTRING;
			objField.Fieldname = "MyField3";
			objStoreTest.setField(objField);
			
			objField = new DataFieldZZZ("Alias4");
			objField.Datatype = DataFieldZZZ.sDATE;
			objField.Format="dd.MM.yyyy";
			objField.Fieldname = "MyField4";
			
			objStoreTest.setField(objField);
			
			//###########################
			//nun die Werte einzeln setzen
			objStoreTest.appendValue("Alias1", "Das ist ein Test");
			objStoreTest.appendValue("Alias1", "Das ist ein Test 2");
			
			Integer objInt = new Integer(43);
			objStoreTest.appendValue("Alias2", objInt);
								
			//nun die Werte als Vektor auslesen
			Vector objVectorOut = objStoreTest.getValueVectorString("Den Alias gibt es nicht");
			assertNotNull(objVectorOut);
			assertEquals(0, objVectorOut.size());
			
			objVectorOut = objStoreTest.getValueVectorString("Alias1");
			assertEquals(2, objVectorOut.size());
			
			objVectorOut = objStoreTest.getValueVector("Alias2");          //!!!Direkt als Vektor
			assertEquals(1, objVectorOut.size());
			
		
			
			//##########################################
			//nun die Werte als Vektor setzen und als Vektor auslesen
			Vector objVectorIn = new Vector();
			objVectorIn.add("Vectortest1");
			objVectorIn.add("Vectortest2");
			objVectorIn.add("Vectortest3");
			objStoreTest.appendValueVector("Alias3", objVectorIn);
			
			objVectorOut = objStoreTest.getValueVectorString("Alias3");
			assertEquals(3, objVectorOut.size());
			
			//Mal testen, ob an der Inexpos 2 (beginnedn bei 0) auch der erwartete Wert steht
			String stemp4 = objStoreTest.getValueString("Alias3", 2);
			assertEquals("Vectortest3", stemp4);
			
			//################################
			//Datumswerte 
			Vector objVecInDate = new Vector();
			objVecInDate.addElement("10.5.2006");
			boolean bNoError = false;
			try{
			objStoreTest.appendValueVector("Alias4", objVecInDate);
			bNoError = true;
			}catch(Exception e){
				bNoError = false;
			}finally{
				if (bNoError) fail("An error was expected, because this was not the right datatype (a Data-Objekt should be passed to the vector)");
			}
			
			objVectorOut  = objStoreTest.getValueVector("Alias4");         //!!!Direkt als Vektor
			assertEquals(0, objVectorOut.size());
			
			//Nun mal echt einen Vector mit nem Date-Objekt erzeugen
			Calendar cal = Calendar.getInstance();
			cal.set(2006,5,25);
			
			Date objDate = cal.getTime();
			objVecInDate.clear();
			objVecInDate.addElement(objDate);
			
			objStoreTest.appendValueVector("Alias4", objVecInDate);   //!!! einf�gen
						
			objVectorOut = objStoreTest.getValueVectorString("Alias4");
			assertEquals(1, objVectorOut.size());                                   //Merke: Der erste Werte des Vektors hat die Indexpositon 0
			String sDateOut = (String) objVectorOut.get(0);
			assertEquals("25.06.2006", sDateOut);                                 //!!!Datumsstring wurde entsprechend dem Format angepasst
			
			
					
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testValueKeyStringAllGet(){
		try{
	//		1. die Metadaten definieren + setzen
			DataFieldZZZ objField = new DataFieldZZZ("Alias1");
			objField.Datatype=DataFieldZZZ.sSTRING;
			objField.Fieldname = "MyField1";
			objStoreTest.setField(objField);
			
			objField = new DataFieldZZZ("Alias2");
			objField.Datatype = DataFieldZZZ.sINTEGER;
			objField.Fieldname = "MyField2";
			objStoreTest.setField(objField);
			
			objField = new DataFieldZZZ("Alias: Hier wird nix an Werten gesetzt");
			objField.Datatype = DataFieldZZZ.sINTEGER;
			objField.Fieldname = "MyField2";
			objStoreTest.setField(objField);
			
		
			//###########################
			//nun die Werte einzeln setzen und dann die ArrayList ermitteln
			objStoreTest.appendValue("Alias1", "Das ist ein Test");
			
			ArrayList alsKey = objStoreTest.getValueKeyStringAlll();
			assertEquals(1, alsKey.size());
			alsKey.clear();
			
//			Das Hinzuf�gen eines zweiten Werts unter den gleichen Alias darf die ArrayList nicht ver�ndern
			objStoreTest.appendValue("Alias1", "Das ist ein zweiter Test"); 
			alsKey = objStoreTest.getValueKeyStringAlll();
			assertEquals(1, alsKey.size());
			alsKey.clear();
			
			//Das Hinzuf�gen eines Wertes unter einen anderen Alias ergibt ein weiteres Element in der ArrayList, der Datentyp ist dabei unnerheblich
			Integer intTemp = new Integer(43);
			objStoreTest.appendValue("Alias2", intTemp);
			alsKey = objStoreTest.getValueKeyStringAlll();
			assertEquals(2, alsKey.size());
			alsKey.clear();
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void myPostInsert(){
		System.out.println("Methode wurde im testValuePostStoreRead() ausgef�hrt");
	}
	public void testValuePostStoreRead(){
		try{
			DataFieldZZZ 	objField = new DataFieldZZZ("Alias4");
			objField.Datatype = DataFieldZZZ.sDATE;
			objField.Format="dd.MM.yyyy";
			objField.Fieldname = "MyField4";
			
			Class objClassTest = this.getClass();
			objField.CustomClassPostStoreRead =objClassTest.getName();   //die aktuelle Klasse in diesem Test verwenden
			objField.CustomMethodPostStoreRead ="myPostInsert";  //Die Methode, die auf dem Feld in testCustomMethodInvoke() aufgerufen wird.
		
			objStoreTest.setField(objField);
			
			objStoreTest.invokePostStoreRead("Alias4");
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
}//END class
  

