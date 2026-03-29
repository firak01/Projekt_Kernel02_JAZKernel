package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedStatusLocalTestTypeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class EnumSetMappedStatusLocalUtilZZZTest  extends TestCase{
	 private HashMapZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
			hmTestGenerics = new HashMapZZZ<String, EnumSetMappedTestTypeZZZ>();
			
			Set<EnumSetMappedTestTypeZZZ> allTypes = EnumSet.allOf(EnumSetMappedTestTypeZZZ.class);
			for(EnumSetMappedTestTypeZZZ myType : allTypes) {
				//String sType = myType.getAbbreviation();
				String sName = myType.name();
				hmTestGenerics.put(sName,myType);
			}
			
		
			
		/*
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		*/
		
	}//END setup
 
	    
    public void testToEnumMappedStatusArrayLists() {
    	try {
       Class<?> objClass = EnumSetMappedTestTypeZZZ.class;
  	   Object[] obja = objClass.getEnumConstants();
  	   
  	   Enum[] objaEnum = (Enum[]) obja;
  	   
  	   ArrayList<IEnumSetMappedStatusLocalZZZ> listae = EnumSetMappedStatusLocalUtilZZZ.toEnumMappedArrayList(objaEnum);
  	   assertNull(listae); //Da der Datentyp IEnumSetMappedZZZ /aus dem ...TestTypeZZZ-Objekt) nicht mit IEnumSetMappedStatusZZZ castfaehig ist.
    	}catch(ExceptionZZZ ez) {
    		ez.printStackTrace();
    		fail("Method throws an exception: " + ez.getMessageLast());
    	}
	}
    
   
}//end class
	
