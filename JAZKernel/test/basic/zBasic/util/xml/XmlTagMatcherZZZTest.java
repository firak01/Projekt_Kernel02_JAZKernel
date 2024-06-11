package basic.zBasic.util.xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.VectorExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.tree.TreeNodeZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import junit.framework.TestCase;

public class XmlTagMatcherZZZTest extends TestCase{
	
	HashMap<String,String> hmXmlTests;
	 protected void setUp(){
		    	
		//The main object used for testing
		//Momentan werden nur statische Methoden angeboten
		//objMatcherTest = new XmlTagMatcherZZZ();		
		
	}//END setup
	 
	 public void testParseAnyElementsAsVector(){
		 try{
			 String sTest;
			 Vector<String> vecTag=null;
			 
//			 In einer Schleife die TESTVALUE Enum durchgehen
//			 Dann muss man auch nicht den TESTVALUE-Namen immer raussuchen
	
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);
			 		 
			//Teste alle XML Strings hinsichtlich der ANZAHL der gefundenen Knoten
			//A) nur reine XML Knoten und den Rest weglassen
			 int iCount=0;
			 boolean bWithText = false;
			 String sMessage = null;
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 	iCount++;
				 	if(iCount==9) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 	sMessage = iCount + ". Teststring wird fehlerhaft ausgeWertet (bWithText="+bWithText+").";
				 	
					IEnumSetMappedTestXmlTypeZZZ objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					sTest= objEnumTestType.getXml();
					vecTag = XmlTagMatcherZZZ.parseAnyElementsAsVector(sTest, bWithText);
					
					int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
				    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
					if(iElementsWithoutText==0 && iElementsWithText==0) {
						assertNull(sMessage,vecTag);
					}else {
						assertNotNull(sMessage, vecTag);
						assertEquals(sMessage, iElementsWithoutText, vecTag.size());
						
						if(iElementsWithoutText==0) {
							assertTrue(sMessage, vecTag.isEmpty());
						}else {
							assertFalse(sMessage, vecTag.isEmpty());
							//auf Wert kann hier  nicht abgeprueft werden, da kein Texte ermittelt werden. ... assertEquals(sMessage, sTest, vecTag.get(0));
						}
				}
			 }//end for
			 
			
			
			//B) Werte ohne XML Knoten werden mit einem <text>-Tag umschlossen.
			iCount=0;
			bWithText=true;						
			for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				iCount++;
				if(iCount==9) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}
			 	sMessage = iCount + ". Teststring wird fehlerhaft ausgeWertet (bWithText="+bWithText+").";
						 	
				IEnumSetMappedTestXmlTypeZZZ objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
				sTest= objEnumTestType.getXml();
				vecTag = XmlTagMatcherZZZ.parseAnyElementsAsVector(sTest, bWithText);
				
				int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
			    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
				if(iElementsWithoutText==0 && iElementsWithText==0) {
					assertNull(sMessage, vecTag);
				}else {
					assertNotNull(sMessage, vecTag);
					assertFalse(sMessage, vecTag.isEmpty());
					assertEquals(sMessage, iElementsWithText, vecTag.size());
					
					//Merke: Nur bei 1 (nicht XML) Wert kann man das automatisiert prüfen.
					if(iElementsWithText==0) {
						assertTrue(sMessage, vecTag.isEmpty());
					}else if(iElementsWithText==1) {
						assertFalse(sMessage, vecTag.isEmpty());
						assertEquals(sMessage, sTest, vecTag.get(0));
					}
				}
			}//end for
			 
				
			//Merke: Hier geht es nur um die Knotenanzahl.
			//       Werte werden hier nicht (auch nicht stichpunktartig) geprueft.
		    
			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 public void testParseAnyValueForTagAsVector(){
		 try{
			 String sTest;
			 Vector<String> vecTag=null;
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			 			 
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
			//Merke: Nun stichpunktartig die Werte der Knoten prüfen.
			//..............
					
//			 Und nun in einer Schleife die TESTVALUE Enum durchgehen
//			 Dann muss man auch nicht den TESTVALUE-Namen immer raussuchen
	
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);
			 		 
			//Teste alle XML Strings hinsichtlich des Wert in dem Knoten
			//A) //+++ Tag kommt nur 1x vor
			 int iCount=0;
			 String sMessage = null;
			 String[]saTagForValue=null; String sTagForValue;
			 String[][]saValue=null;
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
			 	iCount++;
			 	if(iCount==9) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}
			 	
			 	sMessage = iCount + ". Teststring wird fehlerhaft ausgeWertet.";
			 	
				objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
				sTest= objEnumTestType.getXml();
				saTagForValue=objEnumTestType.getTagsForExpectedValue();
				if(saTagForValue!=null) {
					if(!ArrayUtilZZZ.isNull(saTagForValue)) {
						saValue=objEnumTestType.getExpectedValues();
						if(saValue!=null) {
							for(int i=0;i<=saTagForValue.length-1;i++) {
								sTagForValue = saTagForValue[i];
								vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, sTagForValue);
								
								assertNotNull(sMessage, vecTag);
								if(ArrayUtilZZZ.isEmpty(saValue[i])) {
									assertTrue(sMessage, vecTag.isEmpty());
								}else {
									assertFalse(sMessage, vecTag.isEmpty());
									
									assertEquals(sMessage, saValue[i].length, vecTag.size());
									for(int j=0;j<=saValue[i].length-1;j++) {
										assertEquals(sMessage, saValue[i][j],vecTag.get(j));//Merke: vecTag wird in jeder schleife neu ausgerechnet.									
									}
								}													
							}//end for
						}else {
							//!!! Wenn keine BeispielWerte angegeben wurden, trotzdem testen....
							for(int i=0;i<=saTagForValue.length-1;i++) {
								sTagForValue = saTagForValue[i];
								vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, sTagForValue);
								
								assertNotNull(sMessage, vecTag);
								assertFalse(sMessage, vecTag.isEmpty());
							}//end for
						}
					}
				}else {
					
					//!!! Wenn kein Beispieltag angegeben wurde, normalterweise trotzdem testen....
					//... aber hier geht es ja wirklich explizit um die Ermittlung der Tags.
										
				}
			 }//end for
			 

			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testParseElementsAsVector() {
		 try{
			 String sTest;
			 VectorExtendedZZZ<String> vecTag=null;
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			 
//			 In einer Schleife die TESTVALUE Enum durchgehen
//			 Dann muss man auch nicht den TESTVALUE-Namen immer raussuchen
			 
			 
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);			
			 
			//A) bWithAnyValue=false  //also nur reine XML Knoten und den Rest weglassen.
						 
			//Teste alle XML Strings hinsichtlich des Wert in dem Knoten
			//A) //+++ Tag kommt nur 1x vor
			 int iCount=0;
			 String sMessage = null;
			 String[]saTagForValue=null; String sTagForValue; String sTagValue;
			 String[][]saValue=null;
			 boolean bWithText=false;	
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 iCount++;
				 	if(iCount==9) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 	
				 	sMessage = iCount + ". Teststring wird fehlerhaft ausgeWertet.";
				 	
					objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					sTest= objEnumTestType.getXml();
					saTagForValue=objEnumTestType.getTagsForExpectedValue();
					if(saTagForValue!=null) {
						if(!ArrayUtilZZZ.isNull(saTagForValue)) {
							saValue=objEnumTestType.getExpectedValues();
							if(saValue!=null) {
								for(int i=0;i<=saTagForValue.length-1;i++) {
									sTagForValue = saTagForValue[i];
									vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, bWithText);
									
									assertNotNull(sMessage, vecTag);
									if(ArrayUtilZZZ.isEmpty(saValue[i])) {
										assertTrue(sMessage, vecTag.isEmpty());
									}else {
										assertFalse(sMessage, vecTag.isEmpty());
										
										GENERELLES PROBLEM: 
										WIR MÜSSEN DEN KNOTEN ABZAHLEN UND KÖNNEN DANN DEN ERWARTETEN WERT angeben
										DAZU MUSS DIE POSITION IM TEST-CONTAINER angegeben werden.
										
										assertEquals(sMessage, saValue[i].length, vecTag.size());
										for(int j=0;j<=saValue[i].length-1;j++) {
											sTagValue = (String) vecTag.get(j); //XmlUtilZZZ.computeTag(sTagForValue, (String) vecTag.get(j));
											
											//Hier kommt der Tag in den gespeicherten Vector - String, darum errechnen und dann vergleichen.
											assertEquals(sMessage, XmlUtilZZZ.computeTag(sTagForValue, saValue[i][j]),sTagValue);//Merke: vecTag wird in jeder schleife neu ausgerechnet.
										}
									}													
								}//end for
							}else {
								//!!! Wenn keine BeispielWerte angegeben wurden, trotzdem testen....
								for(int i=0;i<=saTagForValue.length-1;i++) {
									sTagForValue = saTagForValue[i];
									vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
									
									assertNotNull(sMessage, vecTag);
									assertFalse(sMessage, vecTag.isEmpty());
								}//end for
							}
						}
					}else {
						
						//!!! Wenn kein Beispieltag angegeben wurde, normalterweise trotzdem testen....
						//... Dann geht es nur um die ANZAHL der Tags
						vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, bWithText);
												
						int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
					    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
						if(iElementsWithoutText==0 && iElementsWithText==0) {
							assertNull(sMessage,vecTag);
						}else {
							assertNotNull(sMessage, vecTag);
							assertEquals(sMessage, iElementsWithoutText, vecTag.size());
							
							if(iElementsWithoutText==0) {
								assertTrue(sMessage, vecTag.isEmpty());
							}else {
								assertFalse(sMessage, vecTag.isEmpty());							
							}
						}
						
					}
			 }//end for
			 
			
			
			//B) 
			iCount=0;
			bWithText=true;						
			for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 iCount++;
				 	if(iCount==9) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 	
				 	sMessage = iCount + ". Teststring wird fehlerhaft ausgeWertet.";
				 	
					objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					sTest= objEnumTestType.getXml();
					saTagForValue=objEnumTestType.getTagsForExpectedValue();
					if(saTagForValue!=null) {
						if(!ArrayUtilZZZ.isNull(saTagForValue)) {
							saValue=objEnumTestType.getExpectedValues();
							if(saValue!=null) {
								for(int i=0;i<=saTagForValue.length-1;i++) {
									sTagForValue = saTagForValue[i];
									vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, bWithText);
									
									assertNotNull(sMessage, vecTag);
									if(ArrayUtilZZZ.isEmpty(saValue[i])) {
										assertTrue(sMessage, vecTag.isEmpty());
									}else {
										assertFalse(sMessage, vecTag.isEmpty());
										
										assertEquals(sMessage, saValue[i].length, vecTag.size());
										for(int j=0;j<=saValue[i].length-1;j++) {
											//assertEquals(sMessage, saValue[i][j],vecTag.get(j));//Merke: vecTag wird in jeder schleife neu ausgerechnet.
											
											sTagValue = (String) vecTag.get(j); //XmlUtilZZZ.computeTag(sTagForValue, (String) vecTag.get(j));
											
											//Hier kommt der Tag in den gespeicherten Vector - String, darum errechnen und dann vergleichen.
											assertEquals(sMessage, XmlUtilZZZ.computeTag(sTagForValue, saValue[i][j]),sTagValue);//Merke: vecTag wird in jeder schleife neu ausgerechnet.
										}
									}													
								}//end for
							}else {
								//!!! Wenn keine BeispielWerte angegeben wurden, trotzdem testen....
								for(int i=0;i<=saTagForValue.length-1;i++) {
									sTagForValue = saTagForValue[i];
									vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
									
									assertNotNull(sMessage, vecTag);
									assertFalse(sMessage, vecTag.isEmpty());
								}//end for
							}
						}
					}else {
						
						//!!! Wenn kein Beispieltag angegeben wurde, normalterweise trotzdem testen....
						//... dann geht es nur um die ANZAHL der Tags
						
						vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, bWithText);
						
						int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
					    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
						if(iElementsWithoutText==0 && iElementsWithText==0) {
							assertNull(sMessage,vecTag);
						}else {
							assertNotNull(sMessage, vecTag);
							assertEquals(sMessage, iElementsWithText, vecTag.size());
							
							if(iElementsWithText==0) {
								assertTrue(sMessage, vecTag.isEmpty());
							}else {
								assertFalse(sMessage, vecTag.isEmpty());							
							}
						}
						
					}
			}//end for
			 
				
			//Merke: Nun stichpunktartig die Werte der Knoten prüfen.
			//..............
			 
			
			//a) Negativtests
			sTest = "";
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
			assertNull(vecTag);
			 
			sTest = "Kein Xml Tag da";
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==1);
			
			String sTemp = (String) vecTag.get(0);			
			assertEquals(XmlUtilZZZ.computeTag("text",sTest), sTemp);
						 
			//b) Positivtests
			//###############################################
			vecTag = null;
			sTest = "Wert vor abc<abc>Wert vor b<b>Wert in b</b>Wert hinter b</abc>Wert hinter abc";
						
			//+++ Nur die Tags
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, false);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==2);
			
			String sTemp3 = (String) vecTag.getEntryLast();
			assertEquals(XmlUtilZZZ.computeTag("b","Wert in b"), sTemp3);
			
			//+++ Auch alle Werte
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, true);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==6);
			
			String sTemp4 = (String) vecTag.getEntryLast();
			assertEquals(XmlUtilZZZ.computeTag("text","Wert hinter abc"), sTemp4);
			
			//TODOGOON20240525;
			//KANN MAN MIT DER HASHMAPINDEXED auch so getFirst, getNext machen?
			
			
			//############################
			sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements>               <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";

			//+++ Nur die Tags
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, false); //Weder Leerstring noch Werte in den Tags
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==8);
			
			//+++ auch alle Werte			
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, true); //Nimm also den Leerstring mit auf...
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==9);
			
			
			//### tiefer verschachtelt #####################
			sTest = "Wert vor abc<abc>Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b</abc>Wert hinter abc";
			
			//+++ Nur die Tags
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, false);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==3);
		 
			//+++ Auch alle Werte		
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, true);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==9);
			
			
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
			sTest = "Wert vor abc<abc>Wert vor b<b>Wert in b</b>Wert hinter b</abc>Wert hinter abc";
						
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
			assertEquals("Wert hinter abc", sTemp4);
			
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
			sTest = "Wert vor abc<abc>Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b</abc>Wert hinter abc";
			
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
	 
	 public void testParseElementsAsTree(){
		 try{
			 String sTest; String sTagTemp; String sValueTemp;
			 ITagSimpleZZZ objTagTemp;
			 
			 TreeNodeZZZ<ITagSimpleZZZ> objNode = null;
			 TreeNodeZZZ<ITagSimpleZZZ> objNodeTemp=null;
			 
			 List<TreeNodeZZZ<ITagSimpleZZZ>> listTag=null;
/*
			//a) Negativtests
			sTest = "";
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest);
			assertNull(objNode);

			
			sTest = "Kein Xml Tag da";
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest);
			assertNotNull(objNode);
			assertTrue(objNode.children.isEmpty()); //Der ITagZZZ ist im Knoten selbst definiert
			assertFalse(objNode.sibling.isEmpty());
			
			objTagTemp = objNode.data;
			sTagTemp = objTagTemp.getName();
			assertEquals("text", sTagTemp);
			
			sValueTemp = objTagTemp.getValue();
			assertEquals(sTest, sValueTemp);
*/			
					
			//b) Positivtests
			//###############################################
			objNode = null;
			sTest = "Wert vor abc<abc>Wert vor b<b>Wert in b</b>Wert hinter b</abc>Wert hinter abc";
/*			
			//+++ Nur die Tags
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest, false);
			assertNotNull(objNode);
			assertFalse(objNode.children.isEmpty());
			assertTrue(objNode.children.size()==1); //Ein ITagZZZ ist im Knoten selbst definiert
			assertFalse(objNode.sibling.isEmpty());
			assertTrue(objNode.sibling.size()==1); //Ein ITagZZZ ist im Knoten selbst definiert			
			
			listTag = objNode.children;			
			objNodeTemp = listTag.get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
		    sValueTemp = objTagTemp.getValue();
			assertEquals("Wert in b", sValueTemp);
*/
			 
				
			/* Merke es gilt als wichtiger Punkt
Wert vor abc
<abc>
	Wert vor b   ------------------------ DARF KEIN SIBLING SEIN FÜR ROOT, Sondern muss CHILD sein.
	<b>Wert in b</b>
	Wert hinter b
</abc>
Wert hinter abc
			 */
			
			//+++ Auch alle Werte
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest, true);
			assertNotNull(objNode);
			assertFalse(objNode.children.isEmpty());
			assertTrue(objNode.children.size()==3); //Ein ITagZZZ ist im Knoten selbst definiert
			assertFalse(objNode.sibling.isEmpty());
			assertTrue(objNode.sibling.size()==3); //Ein ITagZZZ ist im Knoten selbst definiert			
				
			
			objTagTemp = objNode.data;
		    sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor b<b>Wert in b</b>Wert hinter b", sValueTemp);
			
			objNodeTemp = objNode.children.get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor b", sValueTemp);
			
			objNodeTemp = objNode.children.get(1);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert in b", sValueTemp);
			
			
			objNodeTemp = objNode.children.get(2);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert hinter b", sValueTemp);
			
			
			//################################################
			//### Postivtest mit komplexerem String
			//### - Mehrer Childelemente auf einer Ebene
			//### - Ein Leerstringteil zwischen den beiden Root Elementen
			/*
			<DataElements>
				<EmpStatus>2.0</EmpStatus>
				<Expenditure>95465.00</Expenditure>
				<StaffType>11.A</StaffType>
				<Industry>13</Industry>
			</DataElements>
			               
			<InteractionElements>
				<TargetCenter>92f4-MPA</TargetCenter>
				<Trace>7.19879</Trace>
			</InteractionElements>
			 */
			//################################################
			sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements>               <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";

			//+++ Nur die Tags
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest, false);
			assertNotNull(objNode);
			assertFalse(objNode.children.isEmpty());
			assertTrue(objNode.children.size()==4); //Ein ITagZZZ ist im Knoten selbst definiert
			assertFalse(objNode.sibling.isEmpty());
			assertTrue(objNode.sibling.size()==2); //Ein ITagZZZ ist im Knoten selbst definiert			
			
			listTag = objNode.children;			
			objNodeTemp = listTag.get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
		    sValueTemp = objTagTemp.getValue();
			assertEquals("2.0", sValueTemp);
			
			
			//+++ Auch alle Werte
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest, true);
			assertNotNull(objNode);
			assertFalse(objNode.children.isEmpty());
			assertTrue(objNode.children.size()==4); //Ein ITagZZZ ist im Knoten selbst definiert
			assertFalse(objNode.sibling.isEmpty());
			assertTrue(objNode.sibling.size()==3); //Ein ITagZZZ ist im Knoten selbst definiert			
				
			
			objTagTemp = objNode.data;
		    sValueTemp = objTagTemp.getValue();
			assertEquals("<EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry>", sValueTemp);
			
			objNodeTemp = objNode.children.get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("2.0", sValueTemp);
			
			objNodeTemp = objNode.children.get(1);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("95465.00", sValueTemp);
			
			
			objNodeTemp = objNode.children.get(2);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("11.A", sValueTemp);
			
					
			//################################################
			//### Postivtest mit komplexerem String
			//### - tiefer verschachtelt
			/*
			 	Wert vor abc
				<abc>
					Wert vor b
					<b>
						Wert vor bc
						<bc>Wert in bc</bc>
						Wert hinter bc
					</b>
					Wert hinter b
				</abc>
				Wert hinter abc
			*/
			//##############################################
			
			sTest = "Wert vor abc<abc>Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b</abc>Wert hinter abc";
			
			//+++ Nur die Tags
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest, false);
			assertNotNull(objNode);
			assertFalse(objNode.children.isEmpty());
			assertTrue(objNode.children.size()==1); //Ein ITagZZZ ist im Knoten selbst definiert
			assertFalse(objNode.sibling.isEmpty());
			assertTrue(objNode.sibling.size()==1); //Ein ITagZZZ ist im Knoten selbst definiert			
			
			objTagTemp = objNode.data;
		    sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b", sValueTemp);
			
			listTag = objNode.children;			
			objNodeTemp = listTag.get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
		    sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor bc<bc>Wert in bc</bc>Wert hinter bc", sValueTemp);
			
			assertEquals(1,objNode.children.size() );
			
			
			//+++ Auch alle Werte
			objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest, true);
			assertNotNull(objNode);
			assertFalse(objNode.children.isEmpty());
			assertTrue(objNode.children.size()==3); //Ein ITagZZZ ist im Knoten selbst definiert
			assertFalse(objNode.sibling.isEmpty());
			assertTrue(objNode.sibling.size()==3); //Ein ITagZZZ ist im Knoten selbst definiert			
				
			
			objTagTemp = objNode.data;
		    sValueTemp = objTagTemp.getValue();
		    assertEquals("Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b", sValueTemp);
			
		    assertEquals(3,objNode.children.size() );			
		    
		    //++		    
		    
		    objNodeTemp = objNode.children.get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor b", sValueTemp);
			
			
			objNodeTemp = objNode.children.get(1);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor bc<bc>Wert in bc</bc>Wert hinter bc", sValueTemp);
		    			
			//+eine kindebene tiefer: objNodetemp
			objNodeTemp = objNodeTemp.children.get(2);
			assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert hinter bc", sValueTemp);
		    			
			//++und wieder auf einer kindebene hoeher: objNode
			objNodeTemp = objNode.children.get(2);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert hinter b", sValueTemp);
		    
			
			
			//++
			objNodeTemp = objNode.children.get(1);
			assertNotNull(objNodeTemp);
			assertEquals(3, objNodeTemp.children.size());
			
			objNodeTemp = objNodeTemp.children.get(0);
			assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.data;
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor bc", sValueTemp);
			
			
			
				
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
}//End class
