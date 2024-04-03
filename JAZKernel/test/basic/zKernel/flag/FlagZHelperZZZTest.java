package basic.zKernel.flag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.DummyTestObjectWithFlagByDirectZZZ;
import basic.zBasic.DummyTestObjectWithStatusByDirectZZZ;
import basic.zBasic.DummyTestObjectWithStatusByInterfaceExtendedZZZ;
import basic.zBasic.DummyTestObjectWithStatusByInterfaceZZZ;
import basic.zBasic.DummyTestProgramWithStatusByInterfaceExtendedZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithStatusByInterfaceZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class FlagZHelperZZZTest  extends TestCase{
	// private DummyTestObjectWithFlagByInterfaceExtendedZZZ objTestWithFlagByInterfaceExtended = null;
	// private DummyTestObjectWithFlagByInterfaceZZZ objTestWithFlagByInterface = null;
	 private DummyTestObjectWithFlagByDirectZZZ objTestWithFlagByDirect = null;
	 
	// private DummyTestProgramWithFlagByInterfaceExtendedZZZ objTestProgramWithFlagByInterfaceExtended = null;
	 
	    protected void setUp(){
	    	//try {			
		    	//objTestWithFlagByInterfaceExtended = new DummyTestObjectWithFlagByInterfaceExtendedZZZ();
		    	//objTestWithFlagByInterface = new DummyTestObjectWithFlagByInterfaceZZZ();
		    	objTestWithFlagByDirect = new DummyTestObjectWithFlagByDirectZZZ();
			
		    	//objTestProgramWithFlagByInterfaceExtended = new DummyTestProgramWithFlagByInterfaceExtendedZZZ();
	    	
//			} catch (ExceptionZZZ ez) {
//				fail("Method throws an exception." + ez.getMessageLast());
//			} 
		}//END setup
	    

	    
	    
	    public void testGetHashMapStatusBooleanMessageZZZ(){
	    	try {
	    		//Arbeiten mit Enum direkt in der Klasse
	    		//POSITIVTEST:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByDirect = objTestWithStatusByDirect.getClass();	
		    	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IStatusBooleanMessageZZZ>hmEnumStatusByDirect = StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessageZZZ(objClassByDirect, false);
		    	assertNotNull("NULL sollte als HashMap der IEnumSetMappedStatusZZZ-Objekte NICHT zurueckkommen, da STATUSLOCAL direct eingebunden wird.", hmEnumStatusByDirect);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByDirect.size()==6);
		    		    			    			    	
		    	//++++++++++++++++++++++++++++++++++++++++++++++
		    	//Arbeiten mit Enum ueber ein Interface eingebunden.
		    	//Negativtest:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByInterface = objTestWithStatusByInterface.getClass();	
		    	
		    	//Merke: False gibt an, dass keine Interfaces durchsucht werden sollen
		    	HashMap<String,IStatusBooleanMessageZZZ>hmEnumStatusByInterface = StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessageZZZ(objClassByInterface, false);
		    	assertNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird.", hmEnumStatusByInterface);
		    	
		    	//Postivtest:
		    	//Merke: True gibt an, dass Interfaces durchsucht werden sollen
		    	hmEnumStatusByInterface = StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessageZZZ(objClassByInterface, true);
		    	assertNotNull("NULL sollte als HashMap<String,IEnumSetMappedStatusZZZ-Objekte> NICHT zurueckkommen, da STATUSLOCAL ueber Interface eingebunden wird und dies sollte gescannt werden.", hmEnumStatusByInterface);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",hmEnumStatusByInterface.size()==6);
		    
	    	
		    } catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	}//end class
	
