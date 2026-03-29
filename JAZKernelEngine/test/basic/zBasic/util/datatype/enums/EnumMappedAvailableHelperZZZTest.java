package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.DummyTestObjectWithStatusLocalByDirectZZZ;
import basic.zBasic.DummyTestObjectWithStatusLocalByInterfaceExtendedZZZ;
import basic.zBasic.DummyTestObjectWithStatusLocalByInterfaceZZZ;
import basic.zBasic.DummyTestProgramWithStatusLocalByInterfaceExtendedZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithStatusLocalByInterfaceExtendedZZZ;
import basic.zBasic.IDummyTestObjectWithStatusLocalByInterfaceZZZ;
import basic.zBasic.component.AbstractProgramWithStatusLocalOnStatusLocalListeningRunnableZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class EnumMappedAvailableHelperZZZTest  extends TestCase{
	 private DummyTestObjectWithStatusLocalByInterfaceExtendedZZZ objTestWithStatusByInterfaceExtended = null;
	 private DummyTestObjectWithStatusLocalByInterfaceZZZ objTestWithStatusByInterface = null;
	 private DummyTestObjectWithStatusLocalByDirectZZZ objTestWithStatusByDirect = null;
	 
	 private DummyTestProgramWithStatusLocalByInterfaceExtendedZZZ objTestProgramWithStatusByInterfaceExtended = null;
	 
	 
	    protected void setUp(){
		try {			
		
	    	objTestWithStatusByInterfaceExtended = new DummyTestObjectWithStatusLocalByInterfaceExtendedZZZ();
	    	objTestWithStatusByInterface = new DummyTestObjectWithStatusLocalByInterfaceZZZ();
	    	objTestWithStatusByDirect = new DummyTestObjectWithStatusLocalByDirectZZZ();
		
	    	objTestProgramWithStatusByInterfaceExtended = new DummyTestProgramWithStatusLocalByInterfaceExtendedZZZ();
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 						
		}//END setup
	    
   public void testSearchEnumMapped() {
	    	
	    	try {
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByInterface = objTestWithStatusByInterface.getClass();	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	IEnumSetMappedZZZ[] enumaByInterface = EnumMappedAvailableHelperZZZ.searchEnumMappedArray(objClassByInterface, "STATUSLOCAL", false);
		    	assertNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird.", enumaByInterface);
		    	
		    	boolean bIsEmpty = ArrayUtilZZZ.isNull(enumaByInterface);
		    	assertTrue("Es sollte kein STATUSLOCAL gefunden werden, da dieser ueber das Interface eingebunden wird", bIsEmpty);
		    	
		    	
	            //Für einen Positivtest dann mit einem Objekt Arbeiten, dass den Status direkt einbindet.
		    	//a) per Klasse ohne Status im Interface
		    	Class<?> objClassByDirect = objTestWithStatusByDirect.getClass();
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen   	
		    	IEnumSetMappedZZZ[] enumaByDirect = EnumMappedAvailableHelperZZZ.searchEnumMappedArray(objClassByDirect, "STATUSLOCAL", false);
		    	assertNotNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", enumaByDirect);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",enumaByDirect.length==6);
		    	
		    	//b) per Klasse mit Status im Interface	    	
		    	Class<?> objClassByInterface2 = objTestWithStatusByInterface.getClass();
		    	
		    	//Es sollen per Default auch Interfaces durchsucht werden
		    	IEnumSetMappedZZZ[] enumaByInterface2 = EnumMappedAvailableHelperZZZ.searchEnumMappedArray(objClassByInterface2, "STATUSLOCAL" );
		    	assertNotNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", enumaByInterface2);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",enumaByInterface2.length==6);
		    	
		    	//++++++++++++++++++++++++++++++++++
		    	//SPEZIAL
		    	//c) per Klasse aus dem Enum selbst (aus Interface mit Status). 
		    	//    Dazu gibt es jetzt ueberall eine Fallunterscheidung auf den Inputtyp enum selbst.	    	
		    	Class<?> objClassFromInterface = IDummyTestObjectWithStatusLocalByInterfaceZZZ.STATUSLOCAL.class;
	
		    	IEnumSetMappedZZZ[] enumaByInterface3 = EnumMappedAvailableHelperZZZ.searchEnumMappedArray(objClassFromInterface, "STATUSLOCAL");
		    	assertNotNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", enumaByInterface3);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",enumaByInterface3.length==6);
		    	
		    	//+++++++++++++++++++++++++++++++++++++
		    	//D) Per Klasse, mit einer Klasse mit "Interface mit Status" extends "Interface mit Status". 
		    	//    Dazu gibt es jetzt ueberall eine Fallunterscheidung auf den Inputtyp enum selbst.
		    	Class<?> objClassFromInterfaceExtended = objTestWithStatusByInterfaceExtended.getClass();
		    	IEnumSetMappedZZZ[] enumaByInterface4 = EnumMappedAvailableHelperZZZ.searchEnumMappedArray(objClassFromInterfaceExtended, "STATUSLOCAL");
		    	assertNotNull("NULL sollte als Array der IEnumSetMappedStatzsZZZ-Objekte NICHT NULL zurueckkommen, da STATUSLOCAL ueber Interfaces eingebunden wird.", enumaByInterface4);
		    	assertTrue("Es sollten mehr als 6 Elemente in dem Array der Status Enums sein.",enumaByInterface4.length>6);
	
		    	
	    	} catch (ExceptionZZZ ez) {
	    		ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    

	}//end class
	
