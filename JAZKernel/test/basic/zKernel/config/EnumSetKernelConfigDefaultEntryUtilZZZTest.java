package basic.zKernel.config;
import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zKernel.IKernelConfigConstantZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ.EnumConfigDefaultEntryZZZ;

public class EnumSetKernelConfigDefaultEntryUtilZZZTest  extends TestCase{
	 private HashMapExtendedZZZ<String, EnumConfigDefaultEntryZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
			hmTestGenerics = new HashMapExtendedZZZ<String, EnumConfigDefaultEntryZZZ>();
			
			Set<EnumConfigDefaultEntryZZZ> allTypes = EnumSet.allOf(EnumConfigDefaultEntryZZZ.class);
			for(EnumConfigDefaultEntryZZZ myType : allTypes) {
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
	    public void testGetEnumSet(){
			try{
				boolean btemp;
				
				//Static Zugriff
				//EnumSet soll von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden.
				EnumSet setEnumGenerated = EnumConfigDefaultEntryZZZ.getEnumSet();		
				int iSize = setEnumGenerated.size();
				assertTrue("2 Elemente im Set erwartet.", iSize==2);
				
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();				
				Class objClass = EnumConfigDefaultEntryZZZ.class;
				EnumSetConfigDefaultEntryUtilZZZ enumSetUtil = new EnumSetConfigDefaultEntryUtilZZZ(objFactory, objClass);
				
				//Beim Wiederholten Zugriff über die Util-Klasse soll das einmal erstellte EnumSet wiederverwendet werden.	
				EnumSet setEnumReused = enumSetUtil.getEnumSetCurrent();
				assertNotNull(setEnumReused);
				
				int iSizeReused = setEnumReused.size();
				assertTrue("2 Elemente im SetReused erwartet.", iSizeReused==2);				
			
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    
	    public void testStartsWith(){
			try{
				boolean btemp;
				
				
				//######## 
				//### Varianten zum Holen per TestFactory - Klasse:
				//### EnumSetConfigDefaultEntryUtilZZZ enumSetUtil = new EnumSetConfigDefaultEntryUtilZZZ(objFactory, objClass);
				//####
				//#### Merke: Voraussetzung ist: EnumSet wird von der EnumerationZZZ-Klasse schon "generisch/per Reflektion" erzeugt und mitgebracht werden. Alles basisert auf einer static Methode getEnumSet().

				//Variante A) EnumSet selbst erzeugen
				//Positivfall
				
				EnumSet<EnumConfigDefaultEntryZZZ> setEnumCurrent = EnumSet.of(EnumConfigDefaultEntryZZZ.T01, EnumConfigDefaultEntryZZZ.T02);
				assertNotNull(setEnumCurrent);
				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("T01", setEnumCurrent);
				assertTrue("A) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativfall
				EnumSet<EnumConfigDefaultEntryZZZ> setEnumCurrent02 = EnumSet.of(EnumConfigDefaultEntryZZZ.T01, EnumConfigDefaultEntryZZZ.T02);
				assertNotNull(setEnumCurrent02);
				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("TNixdaaa", setEnumCurrent02);
				assertFalse("A) Prüfstring sollte in der Enumeration NICHT vorhanden sein .", btemp);
				
				//Variante B) EnumSet per statischer Methode holen
				//Positivfall
				EnumSet<?> setEnumGenerated = EnumConfigDefaultEntryZZZ.getEnumSet();
				assertNotNull(setEnumGenerated);
				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("T02", setEnumGenerated);
				assertTrue("B) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
				
				//Negativvall
				EnumSet<?> setEnumGenerated02 = EnumConfigDefaultEntryZZZ.getEnumSet();
				assertNotNull(setEnumGenerated02);
				btemp = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyAlias("TNIXDA", setEnumGenerated02);
				assertFalse("B) Prüfstring sollte in der Enumeration NICHT vorhanden sein.", btemp);
				
				//Variante C) direkter
				//Generischer Zugriff, ist  nicht ganz möglich, darum Verwendung der Factory Klasse
				IEnumSetFactoryZZZ objFactory = EnumSetTestFactoryZZZ.getInstance();					
				Class objClass = EnumConfigDefaultEntryZZZ.class;
				EnumSetConfigDefaultEntryUtilZZZ enumSetUtil = new EnumSetConfigDefaultEntryUtilZZZ(objFactory, objClass);//EnumSetKernelConfigDefaultEntryUtilZZZTest
				
				String sValue = "The default filename of the configuration";				
				Integer intIndex = enumSetUtil.readEnumConstant_FirstIndexValueByDescription(sValue);
				assertNotNull(intIndex);
				assertEquals(1,intIndex.intValue());
				
				///FGL########## dann hören hier ggfs. die Gemeinsamkeiten mit der mapped... Struktur auf, hier haben wir Kernel-Konfigurationseinträge
				// ############# siehe IEnumSetKernelConfigDefaultEntryZZZ
				
				//+++ Description
				//Positivfall				
				btemp = enumSetUtil.startsWithAnyDescription("The default");
				assertTrue("C) Prüfstring sollte in der Enumeration vorhanden sein.", btemp);
												
				//Negativfall				
				btemp = enumSetUtil.startsWithAnyDescription("XXXX");
				assertFalse("C) Prüfstring sollte in der Enumeration NICHT vorhanden sein.", btemp);
				
				//+++ ConfigProperty
				intIndex = enumSetUtil.readEnumConstant_FirstIndexValueByProperty(IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);
				assertNotNull(intIndex);
				assertEquals(1,intIndex.intValue());
				
				
				//+++ ValueDefault By Property
				String sProperty = enumSetUtil.readEnumConstant_DefaultValueByProperty(IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);
				assertNotNull(intIndex);
				assertEquals("ZKernelConfigKernel.ini",sProperty);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}    

	
	}//end class
