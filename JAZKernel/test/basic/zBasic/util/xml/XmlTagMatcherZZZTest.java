package basic.zBasic.util.xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import junit.framework.TestCase;

public class XmlTagMatcherZZZTest extends TestCase{
	
	HashMap<String,String> hmXmlTests;
	 protected void setUp(){
		    	
		//The main object used for testing
		//Momentan werden nur statische Methoden angeboten
		//objMatcherTest = new XmlTagMatcherZZZ();
		 
		 
//Ich entscheid mich fuer den alternativen Ansatz mit einer Enum		 
		//Mit dieser HashMap werden die als Test zur Verfuegung stehenden Xmls an dieser zentralen Stelle gepflegt.
		//HashMap in der Form hm(TestAlias, TestXml)
//		hmXmlTests = new HashMap<String,String>();
//		
//		XmlTestStringContainerZZZ objXmlContainer = new XmlTestStringContainerZZZ();
//		String saTestValue = {"",0,0};
//		objXmlContainer.fill(saTestValue);
//		
//		hmXmlTests.put("neg00", "");
//		hmXmlTests.put("neg01", "Kein Xml Tag da");
//			
		
		
	}//END setup
	 
	 public void testParseElementsAsVector(){
		 try{
			 String sTest;
			 Vector<String> vecTag=null;
			 
//			 TODOGOON20240528: Und nun in einer Schleife die TESTVALUE Enum durchgehen
//			                   Dann muss man auch nicht den TESTVALUE-Namen immer raussuchen
			 
			//a) Negativtests
//			//sTest = hmXmlTests.get("neg00");
//			sTest = XmlTestStringContainerZZZ.TESTVALUE.neg00.getXml();
//			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
//			assertNull(vecTag);
//			 
//			//sTest = hmXmlTests.get("neg01");
//			sTest = XmlTestStringContainerZZZ.TESTVALUE.neg01.getXml();
//			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
//			assertNotNull(vecTag);
//			assertFalse(vecTag.isEmpty());
//			assertTrue(vecTag.size()==1);
//			assertEquals(sTest, vecTag.get(0));
			

			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);
			 
			 
			 
			//Teste alle XML Strings hinsichtlich der Anzahl der gefundenen Knoten
			//A) bWithAnyValue=false  //also nur reine XML Knoten und den Rest weglassen.
			 int iCount=0;
			 boolean bWithText = false;
			 String sMessage = null;
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 	iCount++;
				 	sMessage = iCount + ". Teststring wird fehlerhaft ausgewertet (bWithText="+bWithText+").";
				 	
					IEnumSetMappedTestXmlTypeZZZ objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					sTest= objEnumTestType.getXml();
					vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, bWithText);
					
					int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
				    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
					if(iElementsWithoutText==0 && iElementsWithText==0) {
						assertNull(sMessage,vecTag);
					}else {
						assertNotNull(sMessage, vecTag);
						assertEquals(sMessage, vecTag.size(), iElementsWithoutText);
						
						if(iElementsWithoutText==0) {
							assertTrue(sMessage, vecTag.isEmpty());
						}else {
							assertFalse(sMessage, vecTag.isEmpty());							
						}
				}
			 }//end for
			 
			
			
			//B) 
			 iCount=0;
			bWithText=true;						
			for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				iCount++;
			 	sMessage = iCount + ". Teststring wird fehlerhaft ausgewertet (bWithAnyValue="+bWithText+").";
						 	
				IEnumSetMappedTestXmlTypeZZZ objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
				sTest= objEnumTestType.getXml();
				vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, bWithText);
				
				int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
			    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
				if(iElementsWithoutText==0 && iElementsWithText==0) {
					assertNull(sMessage, vecTag);
				}else {
					assertNotNull(sMessage, vecTag);
					assertFalse(sMessage, vecTag.isEmpty());
					assertEquals(sMessage, vecTag.size(), iElementsWithText);
					
					//Merke: Nur bei 1 (nicht XML) Wert kann man das automatisiert prüfen.
					if(iElementsWithText==0) {
						
					}else if(iElementsWithText==1) {
						assertEquals(sMessage, sTest, vecTag.get(0));
					}
				}
			}//end for
			 
				
			//Merke: Nun stichpunktartig die Werte der Knoten prüfen.
			//..............
		
			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 public void testParseAnyValueForTagAsVector(){
		 try{
			 String sTest;
			 Vector<String> vecTag=null;
			 
			//a) Negativtests
			sTest = "";
			vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest,"abc");
			assertNull(vecTag);
			 
			sTest = "Kein Xml Tag da";
			vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, "");
			assertNull(vecTag);
			
			vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, "abc");
			assertNotNull(vecTag);
			assertTrue(vecTag.isEmpty());
			
			 
			//b) Positivtests
			//###############################################
						
			//+++ Tag kommt nur 1x vor
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			
			vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, "abc");
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==1);
			
			vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, "b");
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==1);
			assertEquals("Wert in b",vecTag.get(0)); 
			
			
			//+++ Tag kommt mehrmals vor
			sTest = "Wert vor dem 1. abc<abc><a>1. Wert in a</a>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach dem 1. abc<abc><a>2. Wert in a</a></abc>";
						
			vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, "a");
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==2);
			assertEquals("1. Wert in a",vecTag.get(0)); 
			assertEquals("2. Wert in a",vecTag.get(1)); 
			
			
			
			//### tiefer verschachtelt #####################
			sTest = "Wert vor abc<abc>wert vor b<b>Wert vor bc<bc>1. Wert in bc</bc>Wert nach 1. bc<bc>2. Wert in bc</bc></b>wert nach b</abc>wert nach abc";
			
			vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, "bc");
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==2);
			assertEquals("1. Wert in bc",vecTag.get(0)); 
			assertEquals("2. Wert in bc",vecTag.get(1));
			
			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 public void testParseElementsAsMap(){
		 try{
			 String sTest;
			 HashMapMultiIndexedZZZ<String, String> hmTag=null;
			 
			//a) Negativtests
			sTest = "";
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest);
			assertNull(hmTag);
			 
			sTest = "Kein Xml Tag da";
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest);
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			assertTrue(hmTag.size()==1);
			
			HashMap hmTemp = hmTag.get(0);
			String sTemp = (String) hmTemp.get("text");
			assertEquals(sTest, sTemp);
			
			String sTemp2 = (String) hmTag.getEntryLast();
			assertEquals(sTest, sTemp2);
			
			
			 
			//b) Positivtests
			//###############################################
			hmTag = null;
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
						
			//+++ Nur die Tags
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest, false);
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			assertTrue(hmTag.size()==2);
			
			String sTemp3 = (String) hmTag.getEntryLast();
			assertEquals("Wert in b", sTemp3);
			
			//+++ Auch alle Werte
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest, true);
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			assertTrue(hmTag.size()==6);
			
			String sTemp4 = (String) hmTag.getEntryLast();
			assertEquals("wert nach abc", sTemp4);
			
			//TODOGOON20240525;
			//KANN MAN MIT DER HASHMAPINDEXED auch so getFirst, getNext machen?
			
			
			//############################
			sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements>               <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";

			//+++ Nur die Tags
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest, false); //Weder Leerstring noch Werte in den Tags
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			assertTrue(hmTag.size()==8);
			
			//+++ auch alle Werte			
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest, true); //Nimm also den Leerstring mit auf...
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			assertTrue(hmTag.size()==9);
			
			
			//### tiefer verschachtelt #####################
			sTest = "Wert vor abc<abc>wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert nach bc</b>wert nach b</abc>wert nach abc";
			
			//+++ Nur die Tags
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest, false);
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			assertTrue(hmTag.size()==3);
		 
			//+++ Auch alle Werte		
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest, true);
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			assertTrue(hmTag.size()==9);
			
			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
}//End class
