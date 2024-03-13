package basic.zKernel.status;

import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.DummyTestObjectWithStatusZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithStatusZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;

public class StatusLocalHelperZZZTest  extends TestCase{
	 private DummyTestObjectWithStatusZZZ objTestWithStatus = null;
	 
	    protected void setUp(){
		//try {			
		
	    	objTestWithStatus = new DummyTestObjectWithStatusZZZ();
		
			
			/*
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			*/
			
		}//END setup
	    
	    public void testGetEnumSet(){
			try{
				boolean btemp;
				
				//Static Zugriff
				//EnumSet soll von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden.
				EnumSet setEnumGenerated = IDummyTestObjectWithStatusZZZ.STATUSLOCAL.getEnumSet();		
				int iSize = setEnumGenerated.size();
				assertTrue("6 Elemente im Set erwartet.", iSize==6);
				
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();				
				Class objClass = IDummyTestObjectWithStatusZZZ.STATUSLOCAL.class;
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
				boolean btemp;
				
				//Merke: Varaussetzung ist: EnumSet wird von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden. Alles basisert auf einer static Methode getEnumSet().

				//Variante A) EnumSet selbst erzeugen
				//Positivfall
				EnumSet<IDummyTestObjectWithStatusZZZ.STATUSLOCAL> setEnumCurrent = EnumSet.of(IDummyTestObjectWithStatusZZZ.STATUSLOCAL.ISSTARTNEW, IDummyTestObjectWithStatusZZZ.STATUSLOCAL.ISSTOPPED, IDummyTestObjectWithStatusZZZ.STATUSLOCAL.HASERROR);
				assertNotNull(setEnumCurrent);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ISSTOPPED", setEnumCurrent);
				assertTrue("A) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				EnumSet<IDummyTestObjectWithStatusZZZ.STATUSLOCAL> setEnumCurrent02 = EnumSet.of(IDummyTestObjectWithStatusZZZ.STATUSLOCAL.ISSTARTNEW, IDummyTestObjectWithStatusZZZ.STATUSLOCAL.ISSTOPPED, IDummyTestObjectWithStatusZZZ.STATUSLOCAL.HASERROR);
				assertNotNull(setEnumCurrent02);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("Nixdaaa", setEnumCurrent02);
				assertFalse("A) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
				
				//Variante B) EnumSet per statischer Methode holen
				//Positivfall
				EnumSet<?> setEnumGenerated = IDummyTestObjectWithStatusZZZ.STATUSLOCAL.getEnumSet();
				assertNotNull(setEnumGenerated);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("ISSTOPPED", setEnumGenerated);
				assertTrue("B) Prüfstring 'ISSTOPPED' sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativvall
				EnumSet<?> setEnumGenerated02 = IDummyTestObjectWithStatusZZZ.STATUSLOCAL.getEnumSet();
				assertNotNull(setEnumGenerated02);
				btemp = EnumSetMappedUtilZZZ.startsWithAnyAlias("NIXDA", setEnumGenerated02);
				assertFalse("B) Prüfstring 'NIXDA' sollte in der Enumeration NICHT vorhanden sein.", btemp);
				
				//Variante C) direkter
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();	
				Class objClass = IDummyTestObjectWithStatusZZZ.STATUSLOCAL.class;
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
	    	Class<?> objClass = IDummyTestObjectWithStatusZZZ.STATUSLOCAL.class;
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
	    
	    
	    public void testGetHASHMAP(){
	    	Class<?> objClass = IDummyTestObjectWithStatusZZZ.STATUSLOCAL.class;
	    	TODOGOON20240310;
	    }
	    
	    
	    
	
	}//end class
	
