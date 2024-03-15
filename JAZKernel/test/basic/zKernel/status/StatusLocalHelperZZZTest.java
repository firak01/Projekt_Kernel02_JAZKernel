package basic.zKernel.status;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.DummyTestObjectWithStatusByDirectZZZ;
import basic.zBasic.DummyTestObjectWithStatusByInterfaceZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithStatusByInterfaceZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class StatusLocalHelperZZZTest  extends TestCase{
	 private DummyTestObjectWithStatusByInterfaceZZZ objTestWithStatusByInterface = null;
	 private DummyTestObjectWithStatusByDirectZZZ objTestWithStatusByDirect = null;
	 
	    protected void setUp(){
		//try {			
		
	    	objTestWithStatusByInterface = new DummyTestObjectWithStatusByInterfaceZZZ();
	    	objTestWithStatusByDirect = new DummyTestObjectWithStatusByDirectZZZ();
		
			
			/*
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			*/
			
		}//END setup
	    
	    public void testGetEnumSet(){
			try{
				//Merke; Dieser Test bezieht sich nur auf das Verarbeiten der Enums an sich, nicht auf eine konkrete Klasse. 
				//       Darum Interface als Grundlage der Klassenangabe.
				
				boolean btemp;
				
				//Static Zugriff
				//EnumSet soll von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden.
				EnumSet setEnumGenerated = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.getEnumSet();		
				int iSize = setEnumGenerated.size();
				assertTrue("6 Elemente im Set erwartet.", iSize==6);
				
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();				
				Class objClass = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;
				EnumSetMappedUtilZZZ enumSetUtil = new EnumSetMappedUtilZZZ(objFactory, objClass);
				
				//Beim Wiederholten Zugriff über die Util-Klasse soll das einmal erstellte EnumSet wiederverwendet werden.	
				EnumSet setEnumReused = enumSetUtil.getEnumSetCurrent();
				assertNotNull(setEnumReused);
				
				int iSizeReused = setEnumReused.size();
				assertTrue("6 Elemente im SetReused erwartet.", iSizeReused==6);				
			
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    public void testStartsWith(){
			try{
				//Merke; Dieser Test bezieht sich nur auf das Verarbeiten der Enums an sich, nicht auf eine konkrete Klasse. 
				//       Darum Interface als Grundlage der Klassenangabe.
				boolean btemp;
				
				//Merke: Voraussetzung ist: EnumSet wird von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden. Alles basisert auf einer static Methode getEnumSet().

				//Variante A) EnumSet selbst erzeugen
				//Positivfall
				EnumSet<IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL> setEnumCurrent = EnumSet.of(IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.ISSTARTNEW, IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.ISSTOPPED, IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.HASERROR);
				assertNotNull(setEnumCurrent);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ISSTOPPED", setEnumCurrent);
				assertTrue("A) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				EnumSet<IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL> setEnumCurrent02 = EnumSet.of(IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.ISSTARTNEW, IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.ISSTOPPED, IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.HASERROR);
				assertNotNull(setEnumCurrent02);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("Nixdaaa", setEnumCurrent02);
				assertFalse("A) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
				
				//Variante B) EnumSet per statischer Methode holen
				//Positivfall
				EnumSet<?> setEnumGenerated = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.getEnumSet();
				assertNotNull(setEnumGenerated);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ISSTOPPED", setEnumGenerated);
				assertTrue("B) Prüfstring 'ISSTOPPED' sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				EnumSet<?> setEnumGenerated02 = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.getEnumSet();
				assertNotNull(setEnumGenerated02);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("NIXDA", setEnumGenerated02);
				assertFalse("B) Prüfstring 'NIXDA' sollte in der Enumeration NICHT vorhanden sein.", btemp);
				
				//Variante C) direkter
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();	
				Class objClass = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;
				EnumSetMappedUtilZZZ enumSetUtil = new EnumSetMappedUtilZZZ(objFactory, objClass);
				
				//Positivfall
				btemp = enumSetUtil.startsWithAnyAbbreviation("isstopped");
				assertTrue("C) Prüfstring 'isstopped' sollte in der Enumeration vorhanden sein.", btemp);
												
				//Negativfall
				btemp = enumSetUtil.startsWithAnyAbbreviation("XXXX");
				assertFalse("C) Prüfstring 'XXXX' sollte in der Enumeration NICHT vorhanden sein.", btemp);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}    
	    
	    public void testGetEnumConstant(){
			//Merke; Dieser Test bezieht sich nur auf das Verarbeiten der Enums an sich, nicht auf eine konkrete Klasse. 
			//       Darum Interface als Grundlage der Klassenangabe.
	    	
	    	Class<?> objClass = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;
	    	String sName = EnumSetMappedUtilZZZ.getEnumConstant_NameValue(objClass, "ISSTOPPED");
	    	assertTrue("Prüfstring sollte als Ergebnis 'ISSTOPPED' sein", "ISSTOPPED".equals(sName));
	    	
	    	String sString = EnumSetMappedUtilZZZ.getEnumConstant_StringValue(objClass, "ISSTOPPED");
	    	assertTrue("Prüfstring sollte als Ergebnis 'isstopped' sein", "isstopped".equals(sString));
	    	
	    	Integer intOrdinal = EnumSetMappedUtilZZZ.getEnumConstant_OrdinalValue(objClass, "ISSTOPPED");
	    	assertEquals("Prüfinteger sollte als Ergebnis 4 sein", intOrdinal.intValue(),4);
	    	
	    	@SuppressWarnings("unchecked")
			String sAbbreviation = EnumSetMappedUtilZZZ.getEnumConstant_AbbreviationValue((Class<IEnumSetMappedZZZ>) objClass, "ISSTOPPED");
	    	assertTrue("Prüfstring sollte als Ergebnis 'isstopped' sein", "isstopped".equals(sAbbreviation));
	    	assertTrue("Prüfstring sAbbreviation sollte gleich sString sein.", sString.equals(sAbbreviation));
	    	
	    	@SuppressWarnings("unchecked")
			Integer intPosition = EnumSetMappedUtilZZZ.getEnumConstant_PositionValue((Class<IEnumSetMappedZZZ>) objClass, "ISSTOPPED");
	    	assertEquals("Prüfinteger sollte als Ergebnis 5 sein", intPosition.intValue(),5);
	    		    	
	     	@SuppressWarnings("unchecked")
			String sDescription = EnumSetMappedUtilZZZ.getEnumConstant_DescriptionValue((Class<IEnumSetMappedZZZ>) objClass, "ISSTOPPED");
	     	assertTrue("Prüfstring sollte als Ergebnis leer sein", "".equals(sDescription));
	    	
	     	@SuppressWarnings("unchecked")
			Integer intIndex = EnumSetMappedUtilZZZ.getEnumConstant_IndexValue((Class<IEnumSetMappedZZZ>) objClass, "ISSTOPPED");
	    	assertEquals("Prüfinteger sollte als Ergebnis 4 sein", intIndex.intValue(),4);
	    	assertEquals("Prüfinteger sollte als Ergebnis dem ordinal - Wert entsprechen", intIndex.intValue(), intOrdinal.intValue());
	    	assertEquals("Prüfinteger sollte als Ergebnis um 1 höher als der Index sein", intPosition.intValue(),intIndex.intValue()+1);
	     	
	     	//#########################################################
	     	//Speziell für STATUS
	     	@SuppressWarnings("unchecked")
	     	String sStatusMessage = EnumSetMappedUtilZZZ.getEnumConstant_StatusMessageValue((Class<IEnumSetMappedStatusZZZ>) objClass, "ISSTOPPED");	     	
	    	assertTrue("Prüfstring sollte als Ergebnis 'ZZZ: DummyTestObjectWithStatusZZZ beendet' sein", "ZZZ: DummyTestObjectWithStatusZZZ beendet".equals(sStatusMessage));
	    		    		    		    		 	    
	    }
	    
	    
	    public void testGetEnumStatusLocalMapped() {
	    	
	    	try {
	    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
	    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
	    	Class<?> objClassByInterface = objTestWithStatusByInterface.getClass();	
	    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
	    	IEnumSetMappedStatusZZZ[] enumaByInterface = StatusLocalHelperZZZ.getEnumStatusLocalMapped(objClassByInterface, false);
	    	assertNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird.", enumaByInterface);
	    	
	    	boolean bIsEmpty = ArrayUtilZZZ.isEmpty(enumaByInterface);
	    	assertTrue("Es sollte kein STATUSLOCAL gefunden werden, da dieser ueber das Interface eingebunden wird", bIsEmpty);
	    	
	    	
            //Für einen Positivtest dann mit einem Objekt Arbeiten, dass den Status direkt einbindet.
	    	//a) per Klasse ohne Status im Interface
	    	Class<?> objClassByDirect = objTestWithStatusByDirect.getClass();
	    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen   	
	    	IEnumSetMappedStatusZZZ[] enumaByDirect = StatusLocalHelperZZZ.getEnumStatusLocalMapped(objClassByDirect, false);
	    	assertNotNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", enumaByDirect);
	    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",enumaByDirect.length==6);
	    	
	    	//b) per Klasse mit Status im Interface	    	
	    	Class<?> objClassByInterface2 = objTestWithStatusByInterface.getClass();
	    	
	    	//Es sollen per Default auch Interfaces durchsucht werden
	    	IEnumSetMappedStatusZZZ[] enumaByInterface2 = StatusLocalHelperZZZ.getEnumStatusLocalMapped(objClassByInterface2);
	    	assertNotNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", enumaByInterface2);
	    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",enumaByInterface2.length==6);
	    	
	    	//c) per Klasse aus dem Enum selbst (aus Interface mit Status). 
	    	//    Dazu gibt es jetzt ueberall eine Fallunterscheidung auf den Inputtyp enum selbst.	    	
	    	Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;

	    	IEnumSetMappedStatusZZZ[] enumaByInterface3 = StatusLocalHelperZZZ.getEnumStatusLocalMapped(objClassFromInterface);
	    	assertNotNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", enumaByInterface3);
	    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",enumaByInterface3.length==6);
	    	
	    	
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    
	    public void testGetHashMapEnumStatusLocalMapped(){
	    	try {
	    		//Arbeiten mit Enum direkt in der Klasse
	    		//POSITIVTEST:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByDirect = objTestWithStatusByDirect.getClass();	
		    	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IEnumSetMappedStatusZZZ>hmEnumStatusByDirect = StatusLocalHelperZZZ.getHashMapEnumStatusLocalMapped(objClassByDirect, false);
		    	assertNotNull("NULL sollte als HashMap der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", hmEnumStatusByDirect);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByDirect.size()==6);
		    		    			    			    	
		    	//++++++++++++++++++++++++++++++++++++++++++++++
		    	//Arbeiten mit Enum ueber ein Interface eingebunden.
		    	//Negativtest:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByInterface = objTestWithStatusByInterface.getClass();	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IEnumSetMappedStatusZZZ>hmEnumStatusByInterface = StatusLocalHelperZZZ.getHashMapEnumStatusLocalMapped(objClassByInterface, false);
		    	assertNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird.", hmEnumStatusByInterface);
		    	
		    	//Postivtest:
		    	//Merke: True gibt an, dass Interfaces durchsucht werden sollen
		    	hmEnumStatusByInterface = StatusLocalHelperZZZ.getHashMapEnumStatusLocalMapped(objClassByInterface, true);
		    	assertNotNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> NICHT zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird und dies sollte gescannt werden.", hmEnumStatusByInterface);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByInterface.size()==6);
		    	 	
		    } catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    
	    public void testCreateHashMapStatusBooleanMessageZZZ(){
	    	try {
	    		//Arbeiten mit Enum direkt in der Klasse
	    		//POSITIVTEST:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByDirect = objTestWithStatusByDirect.getClass();	
		    	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IStatusBooleanMessageZZZ>hmEnumStatusByDirect = StatusLocalHelperZZZ.createHashMapStatusBooleanMessageZZZ(objClassByDirect, false);
		    	assertNotNull("NULL sollte als HashMap der IEnumSetMappedStatusZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", hmEnumStatusByDirect);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByDirect.size()==6);
		    		    			    			    	
		    	//++++++++++++++++++++++++++++++++++++++++++++++
		    	//Arbeiten mit Enum ueber ein Interface eingebunden.
		    	//Negativtest:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByInterface = objTestWithStatusByInterface.getClass();	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IStatusBooleanMessageZZZ>hmEnumStatusByInterface = StatusLocalHelperZZZ.createHashMapStatusBooleanMessageZZZ(objClassByInterface, false);
		    	assertNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird.", hmEnumStatusByInterface);
		    	
		    	//Postivtest:
		    	//Merke: True gibt an, dass Interfaces durchsucht werden sollen
		    	hmEnumStatusByInterface = StatusLocalHelperZZZ.createHashMapStatusBooleanMessageZZZ(objClassByInterface, true);
		    	assertNotNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> NICHT zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird und dies sollte gescannt werden.", hmEnumStatusByInterface);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByInterface.size()==6);
		    	
	    	
		    } catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    
	    public void testGetHashMapStatusBooleanMessageZZZ(){
	    	try {
	    		//Merke: 
	    		//die konkrete verwendeten Objekte dieser Klasse müssen mit StatusWerten belegt werden.
	    		//für einen Postivfall
	    		
	    		
	    		//Arbeiten mit Enum direkt in der Klasse
	    		//POSITIVTEST:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	//Class<?> objClassByDirect = objTestWithStatusByDirect.getClass();	
		    	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IStatusBooleanMessageZZZ>hmEnumStatusByDirect = StatusLocalHelperZZZ.getHashMapStatusBooleanMessageZZZ(objTestWithStatusByDirect, false);
		    	assertNotNull("NULL sollte als HashMap der IEnumSetMappedStatusZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", hmEnumStatusByDirect);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByDirect.size()==6);
		    		    			    			    	
		    	//++++++++++++++++++++++++++++++++++++++++++++++
		    	//Arbeiten mit Enum ueber ein Interface eingebunden.
		    	//Negativtest:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByInterface = objTestWithStatusByInterface.getClass();	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IStatusBooleanMessageZZZ>hmEnumStatusByInterface = StatusLocalHelperZZZ.createHashMapStatusBooleanMessageZZZ(objClassByInterface, false);
		    	assertNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird.", hmEnumStatusByInterface);
		    	
		    	//Postivtest:
		    	//Merke: True gibt an, dass Interfaces durchsucht werden sollen
		    	hmEnumStatusByInterface = StatusLocalHelperZZZ.createHashMapStatusBooleanMessageZZZ(objClassByInterface, true);
		    	assertNotNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> NICHT zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird und dies sollte gescannt werden.", hmEnumStatusByInterface);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByInterface.size()==6);
		    	
	    	
		    } catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    
	    
	    
	    
	    public void testGetStatusLocalEnumListZZZ() {
	    	try {
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByDirect = objTestWithStatusByDirect.getClass();	
		    	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
//		    	HashMap<String,IStatusBooleanMessageZZZ>hmEnumStatusByDirect = StatusLocalHelperZZZ.getHashMapStatusBooleanMessage(objClassByDirect, false);
//		    	assertNull("NULL sollte als HashMap der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", hmEnumStatusByDirect);
		    	
		    	
		    	
//		    	Class<?> objClassByInterface = objTestWithStatusByInterface.getClass();	
//		    	
//		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
//		    	HashMap<String,IEnumSetMappedStatusZZZ>hmEnumStatusByInterface = StatusLocalHelperZZZ.getHashMapEnumStatusLocalMapped(objClassByInterface, false);
//		    	assertNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird.", enumaByInterface);
//		    	
		    	
			    } catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
	    }
	    
	
	}//end class
	
