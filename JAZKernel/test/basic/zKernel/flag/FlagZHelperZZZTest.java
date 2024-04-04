package basic.zKernel.flag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.DummyTestObjectWithFlagByDirectZZZ;
import basic.zBasic.DummyTestObjectWithFlagByInterfaceExtendedZZZ;
import basic.zBasic.DummyTestObjectWithFlagByInterfaceZZZ;
import basic.zBasic.DummyTestObjectWithStatusByDirectZZZ;
import basic.zBasic.DummyTestObjectWithStatusByInterfaceExtendedZZZ;
import basic.zBasic.DummyTestObjectWithStatusByInterfaceZZZ;
import basic.zBasic.DummyTestProgramWithFlagByInterfaceExtendedZZZ;
import basic.zBasic.DummyTestProgramWithStatusByInterfaceExtendedZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithFlagByInterfaceExtendedZZZ;
import basic.zBasic.IDummyTestObjectWithFlagByInterfaceZZZ;
import basic.zBasic.IDummyTestObjectWithStatusByInterfaceZZZ;
import basic.zBasic.component.IProgramRunnableZZZ;
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
	private DummyTestObjectWithFlagByInterfaceZZZ objTestWithFlagByInterface = null;
	private DummyTestObjectWithFlagByDirectZZZ objTestWithFlagByDirect = null;
	private DummyTestObjectWithFlagByInterfaceExtendedZZZ objTestWithFlagByInterfaceExtended = null;
	private DummyTestProgramWithFlagByInterfaceExtendedZZZ objTestProgramWithFlagByInterfaceExtended = null;
	 
    protected void setUp(){
    	try {
    		objTestWithFlagByDirect = new DummyTestObjectWithFlagByDirectZZZ();
    		objTestWithFlagByInterface = new DummyTestObjectWithFlagByInterfaceZZZ();
    		objTestWithFlagByInterfaceExtended = new DummyTestObjectWithFlagByInterfaceExtendedZZZ();	    					
	    	objTestProgramWithFlagByInterfaceExtended = new DummyTestProgramWithFlagByInterfaceExtendedZZZ();
    	
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}//END setup
    

	    
	    
	    public void testGetFlagZLocalListAvailableZZZ(){
	    	try {
	    		//Arbeiten mit Enum direkt in der Klasse
	    		//POSITIVTEST:
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByDirect = objTestWithFlagByDirect.getClass();	
		    	ArrayList<String> listas01 = FlagZHelperZZZ.getFlagsZLocalListAvailable(objClassByDirect);		    			    
		    	assertNotNull("NULL sollte als ArrayList der Strings der Enums NICHT zurueckkommen, da FLAGZLocal direct eingebunden wird.", listas01);
		    	assertTrue("Es sollten 2 Elemente in dem Array der Status Enums sein.",listas01.size()==2);
	    	
		    	//ByInterfaceObjekte: Die InterfaceEinbindung wird Ignoriert, da nicht wirklich lokal
		    	Class<?> objClassByInterface = objTestWithFlagByDirect.getClass();	
		    	ArrayList<String> listas02 = FlagZHelperZZZ.getFlagsZLocalListAvailable(objClassByInterface);		    			    
		    	assertNotNull("NULL sollte als ArrayList der Strings der Enums NICHT zurueckkommen, da FLAGZLocal direct eingebunden wird.", listas01);
		    	assertTrue("Es sollten 2 Elemente in dem Array der Status Enums sein.",listas02.size()==2);
	    	
		    	
		    } catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    public void testGetFlagZListAvailableZZZ(){
	    	try {
	    		//Arbeiten mit Enum direkt in der Klasse
	    		
		    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
		    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
		    	Class<?> objClassByDirect = objTestWithFlagByDirect.getClass();	
		    	ArrayList<String> listas01 = FlagZHelperZZZ.getFlagsZListAvailable(objClassByDirect);		    			    
		    	assertNotNull("NULL sollte als ArrayList der Strings der Enums NICHT zurueckkommen, da FLAGZLocal direct eingebunden wird.", listas01);
		    	assertTrue("Es sollten 4 Elemente in dem Array der Status Enums sein.",listas01.size()==4);
	    	
		    	//ByInterfaceObjekte
		    	Class<?> objClassByInterface = objTestWithFlagByInterface.getClass();	
		    	ArrayList<String> listas02 = FlagZHelperZZZ.getFlagsZListAvailable(objClassByInterface);		    			    
		    	assertNotNull("NULL sollte als ArrayList der Strings der Enums NICHT zurueckkommen, da FLAGZLocal direct eingebunden wird.", listas02);
		    	assertTrue("Es sollten 6 Elemente in dem Array der Status Enums sein.",listas02.size()==6);
	    	
		    	//ByInterfaceObjekteExtended
		    	Class<?> objClassByInterfaceExtended = objTestWithFlagByInterfaceExtended.getClass();	
		    	ArrayList<String> listas03 = FlagZHelperZZZ.getFlagsZListAvailable(objClassByInterfaceExtended);		    			    
		    	assertNotNull("NULL sollte als ArrayList der Strings der Enums NICHT zurueckkommen, da FLAGZLocal direct eingebunden wird.", listas03);
		    	assertTrue("Es sollten 8 Elemente in dem Array der Status Enums sein.",listas03.size()==8);
	    	
		    	//ProgramByInterfaceObjekteExtended
		    	Class<?> objClassFromProgramByInterfaceExtended = objTestProgramWithFlagByInterfaceExtended.getClass();	
		    	ArrayList<String> listas04 = FlagZHelperZZZ.getFlagsZListAvailable(objClassFromProgramByInterfaceExtended);		    			    
		    	assertNotNull("NULL sollte als ArrayList der Strings der Enums NICHT zurueckkommen, da FLAGZLocal direct eingebunden wird.", listas04);
		    	assertTrue("Es sollten 11 Elemente in dem Array der Status Enums sein.",listas04.size()==11);
	    	
		    	
		    } catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
    public void testProofFlagZExists() {
    	try {
	    	//Arbeiten mit Enum direkt in der Klasse
    		
	    	//Merke: Dieser Test bezieht sich auf eine konkrete Klasse und nicht nur auf das Verarbeiten der Enums an sich.
	    	//       Darum ein konkretes Objekt und dessen Klasse verwenden.
	    	Class<?> objClassByDirect = objTestWithFlagByDirect.getClass();
    		String sName01 = IFlagZUserZZZ.FLAGZ.INIT.name(); 	
	    	boolean bProof01 = FlagZHelperZZZ.proofFlagZDirectExists(objClassByDirect,sName01);		    			    
	    	assertTrue("Das "+ IFlagZUserZZZ.FLAGZ.INIT.name() + " Flag sollte vorhanden sein, egal ob gesetzt oder nicht",bProof01);
    	
	    	//ByInterfaceObjekte
	    	Class<?> objClassByInterface = objTestWithFlagByInterface.getClass();
	    	String sName02 = IDummyTestObjectWithFlagByInterfaceZZZ.FLAGZ.DUMMY02INTERFACE.name();	    
	    	boolean bProof02 = FlagZHelperZZZ.proofFlagZDirectExists(objClassByInterface, sName02);		    			    
	    	assertTrue("Das "+ sName02 + " Flag sollte vorhanden sein, egal ob gesetzt oder nicht",bProof02);
    	
	    	//ByInterfaceObjekteExtended
	    	Class<?> objClassByInterfaceExtended = objTestWithFlagByInterfaceExtended.getClass();	
	    	String sName03 = IDummyTestObjectWithFlagByInterfaceExtendedZZZ.FLAGZ.DUMMY02INTERFACEExtended.name();	    
	    	boolean bProof03 = FlagZHelperZZZ.proofFlagZDirectExists(objClassByInterfaceExtended, sName03);		    			    
	    	assertTrue("Das "+ sName03 + " Flag sollte vorhanden sein, egal ob gesetzt oder nicht",bProof03);
    	
	    	//ProgramByInterfaceObjekteExtended
	    	Class<?> objClassFromProgramByInterfaceExtended = objTestProgramWithFlagByInterfaceExtended.getClass();	
	    	String sName04 = IProgramRunnableZZZ.FLAGZ.REQUEST_STOP.name();	    
	    	boolean bProof04 = FlagZHelperZZZ.proofFlagZDirectExists(objClassFromProgramByInterfaceExtended, sName04);		    			    
	    	assertTrue("Das "+ sName04 + " Flag sollte vorhanden sein, egal ob gesetzt oder nicht",bProof04);
    	
	    } catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    }	       
}//end class
	
