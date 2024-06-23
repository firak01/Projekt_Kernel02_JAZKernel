package basic.zBasic.util.xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.IVectorExtended4XmlZZZ;
import basic.zBasic.util.abstractList.VectorExtendedZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.tree.ITreeNodeZZZ;
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
				 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
				 	if(iCount==9) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 	
				 	
					IEnumSetMappedTestXmlTypeZZZ objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					sTest= objEnumTestType.getXml();
					vecTag = XmlTagMatcherZZZ.parseAnyElementsAsVector(sTest, bWithText);
					
					int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
				    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
					//if(iElementsWithoutText==0 && iElementsWithText==0) {
				    if(sTest==null) {
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
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
						 	
				IEnumSetMappedTestXmlTypeZZZ objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
				sTest= objEnumTestType.getXml();
				vecTag = XmlTagMatcherZZZ.parseAnyElementsAsVector(sTest, bWithText);
				
				int iElementsWithoutText = objEnumTestType.getExpectedElementsWithoutText();
			    int iElementsWithText = objEnumTestType.getExpectedElementsWithText();
				
			    if(sTest==null) {
					assertNull(sMessage, vecTag);
				}else {
					assertNotNull(sMessage, vecTag);
					assertEquals(sMessage, iElementsWithText, vecTag.size());
					
					if(iElementsWithoutText==0 && iElementsWithText==0) {
						assertTrue(sMessage, vecTag.isEmpty());					
					}else {
						assertFalse(sMessage, vecTag.isEmpty());
																		
						//Merke: Nur bei 1 (nicht XML) Wert kann man das automatisiert prüfen.
						if(iElementsWithText==0) {
							assertTrue(sMessage, vecTag.isEmpty());
						}else if(iElementsWithText==1) {
							assertFalse(sMessage, vecTag.isEmpty());
							assertEquals(sMessage, sTest, vecTag.get(0));
						}
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
			 		 
			//Teste alle XML Strings hinsichtlich des Werts in dem Knoten
			//Merke: Getestet wird auf den puren Eingabestring.
			 //      Da also keine weiteren Knoten <text> hinzugeneriert werden,
			 //      gilt die Position des Erwarteten Strings im Ergebnissektor für den "ohne umgebende texte" Fall. 
			 
			//A) //+++ Tag kommt nur 1x vor
			 int iCount=0;
			 String sMessage = null;
			 int[] iaIndexForTag=null; int iIndexForTag; int iIndexUsedForTag;
			 String[]saTagForValue=null; String sTagForValue;			 
			 String[]saValue=null;String[]saValueUsed=null;//ggf. gibt es fuer ein Tag mehrere Werte, trotzdem keine "Array von Array". Statt dessen extra ein Array aufbauen.			 
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
			 	iCount++;
			 	sMessage = iCount + ". Teststring wird ausgewertet.";
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}
			 				 				 	
				objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
				sTest= objEnumTestType.getXml();
				System.out.println("'" + sTest + "'");
				
				iaIndexForTag = objEnumTestType.getIndexInVectorOfExpectedTagsWithoutText();
				if(!ArrayUtilZZZ.isNull(iaIndexForTag)) {
					saValue=objEnumTestType.getExpectedValues();
					saTagForValue=objEnumTestType.getTagsForExpectedValues();
					if(!(ArrayUtilZZZ.isNull(saValue)||ArrayUtilZZZ.isNull(saTagForValue))) {						
						for(int i=0;i<=saTagForValue.length-1;i++) { //Alle definierten Testwerte durchgehen.
							iIndexUsedForTag=0;
							
							
							//Der IndexForTag ist in diesem Test nur ein Kennzeichen, ob ueberhaupt ein Tag erwartet wird.
							//Hier wird nach einem konkreten Tag gesucht darum hat das Ergebnis immer 0 als Index im Vector.
							iIndexForTag =iaIndexForTag[i];
							if(iIndexForTag<=-1) {
								
							}else {
								sTagForValue = saTagForValue[i];
																														
								//jetzt da wir den Tagnamen haben koennen wir dafuer den Wert holen
								vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, sTagForValue);														
								assertNotNull(sMessage, vecTag);
								
								if(ArrayUtilZZZ.isEmpty(saValue)) {
									assertTrue(sMessage, vecTag.isEmpty());
								}else {
									assertFalse(sMessage, vecTag.isEmpty());
							
									//Da es in diesem Test mehree Werte fuer einen Tagnamen geben kann, muessen die Werte in einem extra Array aufbereitet werden.
									int[]iaIndex = StringArrayZZZ.getIndexContains(saTagForValue, sTagForValue);
									saValueUsed = StringArrayZZZ.get(saValue, iaIndex);									
									
									//Nun die Einzelnen Werte durchgehen. Es gibt ggfs. fuer ein Tag mehrere Werte.
									for(int j=0; j<=saValueUsed.length-1; j++) {
										assertEquals(sMessage, saValueUsed[j],vecTag.get(iIndexUsedForTag));//Merke: vecTag wird in jeder schleife neu ausgerechnet.
										iIndexUsedForTag++; //Falls es mehrer Tag-Werte gibt, dann sollten sie an der naechsten Vektor-Position stehen
									}
																
									
								}								
							}//iIndexForTag <= -1							
						}//end for	
							
					}//isNull saValue || saTagForValue
				}//isNull iaIndexForTag
				
				
			 }//end for
			 

			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testParseElementsAsVector() {
		 try{
			 String sTest;
			 //VectorExtendedZZZ<String> vecTag=null;
			 IVectorExtended4XmlZZZ<?> vecTag=null;
			 
//			 In einer Schleife die TESTVALUE Enum durchgehen
//			 Dann muss man auch nicht den TESTVALUE-Namen immer raussuchen
			 			 			
			//#######################################################################
			//### Dynamische Test mit einem EnumTestType
			//### Prueft alle XML Strings hinsichtlich des Wert in dem Knoten
			//#######################################################################
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
				 
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);			
						
			//A) bWithText=false
			 int iCount=0;
			 String sMessage = null;			 
			 boolean bWithText=false;//also nur reine XML Knoten und den Rest weglassen.	
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 	iCount++;
				 	sMessage = iCount + ". Teststring wird ausgewertet. (bWithText="+bWithText+")";				 	
				 	if(iCount==5) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 					 	
					objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					this.testParseElementsAsVectorByTestType_(objEnumTestType, bWithText, sMessage);
					
					
																
			}//end for ... teststringend aus IEnumSetMappedZZZ
			
			//B) 
			iCount=0;
			bWithText=true;						
			for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				iCount++;
				sMessage = iCount + ". Teststring wird ausgewertet. (bWithText="+bWithText+")";
				if(iCount==5) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}
				
				objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
				this.testParseElementsAsVectorByTestType_(objEnumTestType, bWithText, sMessage);
					
			}//end for
			 
				
			//Merke: Nun stichpunktartig die Werte der Knoten prüfen.
			//..............
			 
			//#####################################################################
			//### Statische Tests, die eigentlich durch obige Schleifenlösung und dem EnumTestType abgeloest werden sollen.			 
			//### ABER: In den Statischen Tests wird auch die Anzahl der Elemente gezählt und sind somit eine gute Ergänzung
			//#####################################################################
						
			//a) Negativtests
			sTest = null;
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
			assertNull(vecTag);
			
			sTest = "";
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
			assertNotNull(vecTag);
			
			sTest = "";
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
			assertNotNull(vecTag);
			 
			sTest = "Kein Xml Tag da";
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==1);
			
			Object objTemp = vecTag.get(0);			
			assertEquals(XmlUtilZZZ.computeTag("text",sTest), objTemp.toString());
						 
			//b) Positivtests
			//###############################################
			vecTag = null;
			sTest = "Wert vor abc<abc>Wert vor b<b>Wert in b</b>Wert hinter b</abc>Wert hinter abc";
						
			//+++ Nur die Tags
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, false);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==2);
			
			Object objTemp3 = vecTag.getEntryLast();
			assertEquals(XmlUtilZZZ.computeTag("b","Wert in b"), objTemp3.toString());
			
			//+++ Auch alle Werte
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, true);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			assertTrue(vecTag.size()==6);
			
			Object objTemp4 = vecTag.getEntryLast();
			assertEquals(XmlUtilZZZ.computeTag("text","Wert hinter abc"), objTemp4.toString());
			
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
	 
	 private void testParseElementsAsVectorByTestType_(IEnumSetMappedTestXmlTypeZZZ objEnumTestType, boolean bWithText, String sMessage) {
		 try{
			 String sTest;
			 int[]iaTagIndex; int iTagIndex; 
			 String[]saTagForValue=null; String sTagForValue; String sTagValue;
 			 String[]saValue=null;
			 //VectorExtendedZZZ<String> vecTag=null;
 			IVectorExtended4XmlZZZ<?> vecTag=null;
			 
			sTest= objEnumTestType.getXml();
			vecTag = XmlTagMatcherZZZ.parseElementsAsVector(sTest, bWithText);								
				
			//Ermittle in diesem Text nicht die Anzahl der Knoten, sondern konkrete Werte
			//Daher von den angegebenen Indexpositionen ausgehen
			if(bWithText) {
				iaTagIndex = objEnumTestType.getIndexInVectorOfExpectedTagsWithText();
			}else {
				iaTagIndex = objEnumTestType.getIndexInVectorOfExpectedTagsWithoutText();
			}
			
			//Ohne Indexpositionen also (wie bei iElementsWithoutText, ect) ueberhaupt kein Wert
			if(ArrayUtilZZZ.isNull(iaTagIndex)) {
				if(sTest==null) {
					assertNull(sMessage, vecTag);
				}else {
					//Das kann dann nur ein Leerstring sein
					assertNotNull(sMessage,vecTag);
					assertTrue(sMessage, vecTag.isEmpty());					
				}
			}else {
				assertNotNull(sMessage,vecTag);
					
				//Nun auf Werte abpruefen, falls Testwerte gepflegt sind
					
				//	... sind Testwerte gepflegt?
				saValue=objEnumTestType.getExpectedValues();
				if(ArrayUtilZZZ.isEmpty(saValue)) {
										
				}else {
					if(iaTagIndex.length!=saValue.length) {
						fail(sMessage+" ungleiche Anzahl an Testwerten und TestIndexwerten im Vektor");
					}
					
					
					//... sind Testtags gepfegt?
					saTagForValue=objEnumTestType.getTagsForExpectedValues();
					
						
					//Die Indexpositionen durchsehen
					for(int iTestLauf = 0; iTestLauf <= iaTagIndex.length-1; iTestLauf++) {
								
						//Die Werte durchsehen	
						for(int iTestLaufWert = 0; iTestLaufWert <= saValue.length - 1; iTestLaufWert++) {
							
							//...aber nur fall auch gepflegt
							if(ArrayUtilZZZ.isEmpty(iaTagIndex)) {
								assertTrue(sMessage, vecTag.isEmpty());
								
							}else {								
								iTagIndex = iaTagIndex[iTestLaufWert];																
								if(iTagIndex<=-1) {
									//dann gibt es keinen zu pruefenden Wert fuer diese Stelle, aber ggfs. sind andere Werte vorhanden also nicht leer. Z.B. weil es ein Text ist, aber in diesem Lauf Texte ignoriert werden.																
																				
								}else {
									assertFalse(sMessage, vecTag.isEmpty());
										
									sTagValue = saValue[iTestLaufWert];
									sTagForValue = saTagForValue[iTestLaufWert];
																			
									//dann den Wert pruefen, mit reingerechentem Tag
									String sValueInVector = XmlUtilZZZ.computeTag(sTagForValue, sTagValue);
									assertEquals(sMessage, sValueInVector, (vecTag.get(iTagIndex)).toString());			
								}//end if(iTagIndex <= -1
							}//end if isEmpty(iaTagIndex)
						}//end for ...wert
					}//end for ... lauf
				}//end if isNull(saValue)
			}//end if isNull(iaTagIndex)							
									
		
				
																						
//									GENERELLES PROBLEM: 
//									WIR MÜSSEN DEN KNOTEN ABZAEHLEN UND KÖNNEN DANN DEN ERWARTETEN WERT angeben
//									DAZU MUSS DIE POSITION IM TEST-CONTAINER angegeben werden.
//									
									//Bloedsinn, der Vektor ist immer viel größer als das Array mit den zu ueberpruefenden Werten   assertEquals(sMessage, saValue[i].length, vecTag.size());
//									for(int j=0;j<=saValue[i].length-1;j++) {
//										sTagValue = (String) vecTag.get(j); //XmlUtilZZZ.computeTag(sTagForValue, (String) vecTag.get(j));
//										
//										//Hier kommt der Tag in den gespeicherten Vector - String, darum errechnen und dann vergleichen.
//										assertEquals(sMessage, XmlUtilZZZ.computeTag(sTagForValue, saValue[i][j]),sTagValue);//Merke: vecTag wird in jeder schleife neu ausgerechnet.
//									}	
	 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testParseElementsAsMap(){
		 try{
			 String sTest;
			 HashMapMultiIndexedZZZ<String, String> hmTag=null;
			 
			//#######################################################################
			//### Dynamische Test mit einem EnumTestType
			//### Prueft alle XML Strings hinsichtlich des Wert in dem Knoten
			//#######################################################################
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			 
			 
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);			
			
			 
			//A) bWithText=false
			 int iCount=0;
			 String sMessage = null;			 
			 boolean bWithText=false;//also nur reine XML Knoten und den Rest weglassen.	
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 	iCount++;
				 	sMessage = iCount + ". Teststring wird ausgewertet. (bWithText="+bWithText+")";				 	
				 	if(iCount==5) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 					 	
					objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					this.testParseElementsAsMapByTestType_(objEnumTestType, bWithText, sMessage);
					
			 }//end for
			 
			//B) bWithText=true
			 iCount=0;
			 sMessage = null;			 
			 bWithText=true;//also nur reine XML Knoten und den Rest weglassen.	
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 	iCount++;
				 	sMessage = iCount + ". Teststring wird ausgewertet. (bWithText="+bWithText+")";				 	
				 	if(iCount==5) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 					 	
					objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					this.testParseElementsAsMapByTestType_(objEnumTestType, bWithText, sMessage);
					
			 }//end for
			 
			//#####################################################################
			//### Statische Tests, die eigentlich durch obige Schleifenlösung und dem EnumTestType abgeloest werden sollen.			 
			//### ABER: In den Statischen Tests wird auch die Anzahl der Elemente gezählt und sind somit eine gute Ergänzung
			//#####################################################################
			//+++++++++++++++++++++++++++
			//a) Negativtests
			sTest = null;
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest);
			assertNull(hmTag);
			 
			sTest = "";
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest);
			assertNotNull(hmTag);
			 
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
	 
	 private void testParseElementsAsMapByTestType_(IEnumSetMappedTestXmlTypeZZZ objEnumTestType, boolean bWithText, String sMessage) {
		 try{
			 String sTest;
			 int[]iaTagIndex; int iTagIndex; 
			 String[]saTagForValue=null; String sTagForValue; String sTagValue;
 			 String[]saValue=null;
 			HashMapMultiIndexedZZZ<String, String> hmTag=null;
			 
			sTest= objEnumTestType.getXml();
			hmTag = XmlTagMatcherZZZ.parseElementsAsMap(sTest, bWithText);								
				
			//Ermittle in diesem Text nicht die Anzahl der Knoten, sondern konkrete Werte
			//Daher von den angegebenen Indexpositionen ausgehen
			if(bWithText) {
				iaTagIndex = objEnumTestType.getIndexInHashMapOfExpectedTagsWithText();
			}else {
				iaTagIndex = objEnumTestType.getIndexInHashMapOfExpectedTagsWithoutText();
			}
			
			//Ohne Indexpositionen also (wie bei iElementsWithoutText, ect) ueberhaupt kein Wert
			if(ArrayUtilZZZ.isNull(iaTagIndex)) {
				if(sTest==null) {
					assertNull(sMessage, hmTag);
				}else {
					//Das kann dann nur ein Leerstring sein
					assertNotNull(sMessage,hmTag);
					assertTrue(sMessage, hmTag.isEmpty());					
				}						
			}else {
				assertNotNull(sMessage,hmTag);
					
				//Nun auf Werte abpruefen, falls Testwerte gepflegt sind
					
				//	... sind Testwerte gepflegt?
				saValue=objEnumTestType.getExpectedValues();
				if(ArrayUtilZZZ.isEmpty(saValue)) {
					
				}else {
					if(iaTagIndex.length!=saValue.length) {
						fail(sMessage+" ungleiche Anzahl an Testwerten und TestIndexwerten im Vektor");
					}
					
					//... sind Testtags gepfegt?
					saTagForValue=objEnumTestType.getTagsForExpectedValues();
					
					
					//Die Indexpositionen durchsehen
					for(int iTestLauf = 0; iTestLauf <= iaTagIndex.length-1; iTestLauf++) {
								
						//Die Werte durchsehen	
						for(int iTestLaufWert = 0; iTestLaufWert <= saValue.length - 1; iTestLaufWert++) {
								
							//...aber nur fall auch gepflegt
							if(ArrayUtilZZZ.isEmpty(iaTagIndex)) {
								assertTrue(sMessage, hmTag.isEmpty());
								
							}else {
								iTagIndex = iaTagIndex[iTestLaufWert];
								
								if(iTagIndex<=-1) {
									//dann gibt es keinen zu pruefenden Wert an dieser Stelle, ggfs. aber sind andere Werte vorhanden. Z.B. weil es ein Text ist, aber in diesem Lauf Texte ignoriert werden.																
																			
								}else {																
									if(ArrayUtilZZZ.isEmpty(saValue)) {
										assertTrue(sMessage, hmTag.isEmpty());
										
									}else {
										assertFalse(sMessage, hmTag.isEmpty());
										
										sTagForValue = saTagForValue[iTestLaufWert];
										sTagValue = saValue[iTestLaufWert];
										
									
										//dann den Wert pruefen, mit reingerechentem Tag
										String sValueInVector = XmlUtilZZZ.computeTagAsHashMapEntry(sTagForValue, sTagValue);
										//also einfach mit Equals kann man den String mit dem HashMap-Inhalt nicht vergleichen: assertEquals(sMessage, sValueInVector, hmTag.get(iTagIndexInVector));
										//darum erst in einen String umwandeln:
										assertEquals(sMessage, sValueInVector, hmTag.get(iTagIndex).toString());
									}
								}// end if(iTagIndex<=-1)
							}//end if isEmpty(iaTagIndex)
						}//end for ...wert
					}//end for ... lauf
				}//end isNull (saValue)
			}//end if isNull(iaTagIndex)							
									
		
				
																						
//									GENERELLES PROBLEM: 
//									WIR MÜSSEN DEN KNOTEN ABZAEHLEN UND KÖNNEN DANN DEN ERWARTETEN WERT angeben
//									DAZU MUSS DIE POSITION IM TEST-CONTAINER angegeben werden.
//									
									//Bloedsinn, der Vektor ist immer viel größer als das Array mit den zu ueberpruefenden Werten   assertEquals(sMessage, saValue[i].length, vecTag.size());
//									for(int j=0;j<=saValue[i].length-1;j++) {
//										sTagValue = (String) vecTag.get(j); //XmlUtilZZZ.computeTag(sTagForValue, (String) vecTag.get(j));
//										
//										//Hier kommt der Tag in den gespeicherten Vector - String, darum errechnen und dann vergleichen.
//										assertEquals(sMessage, XmlUtilZZZ.computeTag(sTagForValue, saValue[i][j]),sTagValue);//Merke: vecTag wird in jeder schleife neu ausgerechnet.
//									}	
	 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testParseElementsAsTree(){
		 try{
			
			 
			//#######################################################################
			//### Dynamische Test mit einem EnumTestType
			//### Prueft alle XML Strings hinsichtlich des Wert in dem Knoten
			//#######################################################################
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			 
			 
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);			
			
			 
			//A) bWithText=false
			 int iCount=0;
			 String sMessage = null;			 
			 boolean bWithText=false;//also nur reine XML Knoten und den Rest weglassen.	
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 	iCount++;
				 	sMessage = iCount + ". Teststring wird ausgewertet. (bWithText="+bWithText+")";				 	
				 	if(iCount==7) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 					 	
					objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					this.testParseElementsAsTreeByTestType_(objEnumTestType, bWithText, sMessage);
					
			 }//end for
			 
			//B) bWithText=true
			 iCount=0;
			 sMessage = null;			 
			 bWithText=true;//also nur reine XML Knoten und den Rest weglassen.	
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
				 	iCount++;
				 	sMessage = iCount + ". Teststring wird ausgewertet. (bWithText="+bWithText+")";				 	
				 	if(iCount==3) {
						System.out.println("Breakpoint Pause zum debuggen");					
					}
				 					 	
					objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
					this.testParseElementsAsTreeByTestType_(objEnumTestType, bWithText, sMessage);
					
			 }//end for
			 
			//#####################################################################
			//### Statische Tests, die eigentlich durch obige Schleifenlösung und dem EnumTestType abgeloest werden sollen.			 
			//### ABER: In den Statischen Tests wird auch die Anzahl der Elemente gezählt und sind somit eine gute Ergänzung
			//#####################################################################
			
		 
			 String sTest; String sTagTemp; String sValueTemp;
			 ITagSimpleZZZ objTagTemp;
			 
			 ITreeNodeZZZ<ITagSimpleZZZ> objNodeRoot = null;
			 ITreeNodeZZZ<ITagSimpleZZZ> objNodeCurrent = null;
			 ITreeNodeZZZ<ITagSimpleZZZ> objNodeTemp=null;
			 
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
			objNodeRoot = null;
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
			
			//Merke: Der als Ergebnis des Parsens erhaltene Knoten ist das Root.
			
			//+++ Auch alle Werte
			objNodeRoot = XmlTagMatcherZZZ.parseElementsAsTree(sTest, true);
			assertNotNull(objNodeRoot); 
			assertFalse(objNodeRoot.getChildren().isEmpty());
			assertTrue(objNodeRoot.getChildren().size()==3); //Ein ITagZZZ ist im Knoten selbst definiert
			assertNull(objNodeRoot.getSiblings());
												
			objTagTemp = objNodeRoot.getData();
			assertNull(objTagTemp);
			
			objNodeTemp = objNodeRoot.getChildren().get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor abc", sValueTemp);
			
			//assertEquals("Wert vor b<b>Wert in b</b>Wert hinter b", sValueTemp);
			
			
			objNodeCurrent = objNodeTemp; //text - Tag
			assertNull(objNodeCurrent.getChildren());
			
			objNodeCurrent = objNodeRoot.getChildren().get(1);
			assertNotNull(objNodeCurrent.getChildren());
			
			objNodeTemp = objNodeCurrent.getChildren().get(0);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor b", sValueTemp);			
			assertFalse(objNodeTemp.getSiblings().isEmpty());
			assertTrue(objNodeTemp.getSiblings().size()==3); //Ein ITagZZZ ist im Knoten selbst definiert
			
			
			
			objNodeTemp = objNodeCurrent.getChildren().get(1);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert in b", sValueTemp);
			assertFalse(objNodeTemp.getSiblings().isEmpty());
			assertTrue(objNodeTemp.getSiblings().size()==3); //Ein ITagZZZ ist im Knoten selbst definiert
			
			
			objNodeTemp = objNodeCurrent.getChildren().get(2);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert hinter b", sValueTemp);
			assertFalse(objNodeTemp.getSiblings().isEmpty());
			assertTrue(objNodeTemp.getSiblings().size()==3); //Ein ITagZZZ ist im Knoten selbst definiert
			
			
			
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
			objNodeRoot = XmlTagMatcherZZZ.parseElementsAsTree(sTest, false);
			assertNotNull(objNodeRoot);
			assertFalse(objNodeRoot.getChildren().isEmpty());
			assertTrue(objNodeRoot.getChildren().size()==2); //Ein ITagZZZ ist im Knoten selbst definiert
			assertNull(objNodeRoot.getSiblings());
						
			objNodeTemp = objNodeRoot.getChildren().get(0);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("<EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry>", sValueTemp);
			
			
			objNodeCurrent = objNodeTemp;
			objNodeTemp = objNodeCurrent.getChildren().get(0);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("2.0", sValueTemp);
			
			objNodeTemp = objNodeCurrent.getChildren().get(1);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("95465.00", sValueTemp);
			
			//-+-+-+-+
			objNodeTemp = objNodeRoot.getChildren().get(1);
		    assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("<TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace>", sValueTemp);
									
			objNodeCurrent = objNodeTemp;
			objNodeTemp = objNodeCurrent.getChildren().get(0);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("92f4-MPA", sValueTemp);
			
			
					
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
			objNodeRoot = XmlTagMatcherZZZ.parseElementsAsTree(sTest, false);
			assertNotNull(objNodeRoot);
			assertFalse(objNodeRoot.getChildren().isEmpty());
			assertEquals(1,objNodeRoot.getChildren().size() );					    
			assertNull(objNodeRoot.getSiblings());		
			assertNull(objNodeRoot.getData());
			
			
			objNodeTemp = objNodeRoot.getChildren().get(0);
		    assertNotNull(objNodeTemp);
		    objNodeCurrent = objNodeTemp;
		    objTagTemp = objNodeCurrent.getData();
		    sValueTemp = objTagTemp.getValue();			
			assertEquals("Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b", sValueTemp);
			
			//- eine Ebene tiefer
			objNodeTemp = objNodeCurrent.getChildren().get(0);
			assertNotNull(objNodeTemp);
			objNodeCurrent = objNodeTemp;	
			assertEquals(1,objNodeCurrent.getChildren().size() );
			
			objTagTemp = objNodeCurrent.getData();
		    sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor bc<bc>Wert in bc</bc>Wert hinter bc", sValueTemp);
			
			
			//+++ Auch alle Text-Werte
			objNodeRoot = XmlTagMatcherZZZ.parseElementsAsTree(sTest, true);
			assertNotNull(objNodeRoot);
			assertFalse(objNodeRoot.getChildren().isEmpty());
			assertEquals(3,objNodeRoot.getChildren().size() );					    
			assertNull(objNodeRoot.getSiblings());				
			assertNull(objNodeRoot.getData());
			
			//+- Kinder der Root untersuchen
			objNodeTemp = objNodeRoot.getChildren().get(0);
			assertNotNull(objNodeTemp);
			objNodeCurrent = objNodeTemp;
			objTagTemp = objNodeCurrent.getData();
		    sValueTemp = objTagTemp.getValue();
		    assertEquals("Wert vor abc", sValueTemp);
					    		    	    		
			objNodeTemp = objNodeRoot.getChildren().get(1);
		    assertNotNull(objNodeTemp);
		    objNodeCurrent = objNodeTemp;
			objTagTemp = objNodeCurrent.getData();
		    sValueTemp = objTagTemp.getValue();
		    assertEquals("Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b", sValueTemp);
		    
		    objNodeTemp = objNodeRoot.getChildren().get(2);
		    assertNotNull(objNodeTemp);
		    objNodeCurrent = objNodeTemp;
			objTagTemp = objNodeCurrent.getData();
		    sValueTemp = objTagTemp.getValue();
		    assertEquals("Wert hinter abc", sValueTemp);
		    
		    			
			//+eine kindebene tiefer: objNodetemp
		  //assertEquals("Wert vor bc<bc>Wert in bc</bc>Wert hinter bc", sValueTemp);
		    
			objNodeTemp = objNodeRoot.getChildren().get(1);
			assertNotNull(objNodeTemp);
			objNodeCurrent = objNodeTemp;
			assertEquals(3,objNodeCurrent.getChildren().size());
			
			objNodeTemp = objNodeCurrent.getChildren().get(1);
			objTagTemp = objNodeCurrent.getData();
		    sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor b<b>Wert vor bc<bc>Wert in bc</bc>Wert hinter bc</b>Wert hinter b", sValueTemp);
		    			
			//++und wieder auf einer kindebene hoeher: objNode
			objNodeTemp = objNodeCurrent.getChildren().get(2);
		    assertNotNull(objNodeTemp);
		    objNodeCurrent = objNodeTemp;
		    objTagTemp = objNodeCurrent.getData();
		    sValueTemp = objTagTemp.getValue();
			assertEquals("Wert hinter b", sValueTemp);
		    
			
			
			//++
			objNodeTemp = objNodeCurrent.getParent().getChildren().get(1);
			assertNotNull(objNodeTemp);
			assertEquals(3, objNodeTemp.getChildren().size());
			
			objNodeTemp = objNodeTemp.getChildren().get(0);
			assertNotNull(objNodeTemp);
			objTagTemp = objNodeTemp.getData();
			sValueTemp = objTagTemp.getValue();
			assertEquals("Wert vor bc", sValueTemp);
			
			
			
				
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 private void testParseElementsAsTreeByTestType_(IEnumSetMappedTestXmlTypeZZZ objEnumTestType, boolean bWithText, String sMessage) {
		 try{
			String sTest;
			int[]iaTagIndex; int iTagIndex; 
			String[]saTagForValue=null; String sTagForValue; String sTagValue;
	 		String[]saValue=null;
	 			
	 		sTest= objEnumTestType.getXml();
	 		ITreeNodeZZZ<ITagSimpleZZZ> objNode = XmlTagMatcherZZZ.parseElementsAsTree(sTest, bWithText);
					
	 		//Ermittle in diesem Text nicht die Anzahl der Knoten, sondern konkrete Werte
	 		//Daher von den angegebenen Indexpositionen ausgehen
	 		if(bWithText) {
	 			iaTagIndex = objEnumTestType.getIndexInHashMapOfExpectedTagsWithText();
	 		}else {
				iaTagIndex = objEnumTestType.getIndexInHashMapOfExpectedTagsWithoutText();
	 		}
				
			//Ohne Indexpositionen also (wie bei iElementsWithoutText, ect) ueberhaupt kein Wert
			if(ArrayUtilZZZ.isNull(iaTagIndex)) {
				if(sTest==null) {
					assertNull(sMessage, objNode);
				}else {
					//Das kann dann nur ein Leerstring sein
					assertNotNull(sMessage,objNode);
					assertTrue(sMessage, objNode.isEmpty());					
				}						
			}else {
				assertNotNull(sMessage,objNode);
						
				//Nun auf Werte abpruefen, falls Testwerte gepflegt sind
						
				//	... sind Testwerte gepflegt?
				saValue=objEnumTestType.getExpectedValues();
				if(ArrayUtilZZZ.isEmpty(saValue)) {
					
				}else {
					
					if(iaTagIndex.length!=saValue.length) {
						fail(sMessage+" ungleiche Anzahl an Testwerten und TestIndexwerten im Vektor");
					}
					
					//... sind Testtags gepfegt?
					saTagForValue=objEnumTestType.getTagsForExpectedValues();
										
					//Die Indexpositionen durchsehen
					for(int iTestLauf = 0; iTestLauf <= iaTagIndex.length-1; iTestLauf++) {
								
						//Die Werte durchsehen	
						for(int iTestLaufWert = 0; iTestLaufWert <= saValue.length - 1; iTestLaufWert++) {
							iTagIndex = iaTagIndex[iTestLaufWert];
								
							//...aber nur falls auch gepflegt
							if(ArrayUtilZZZ.isEmpty(iaTagIndex)) {
									
							}else {
								if(iTagIndex<=-1) {
									//dann gibt es an dieser Stelle keinen zu pruefenden Wert aber andere Wete sind ggfs. vorhanden. Z.B. weil es ein Text ist, aber in diesem Lauf Texte ignoriert werden.																
									
								}else {
									assertFalse(sMessage, objNode.isEmpty());
									
									sTagValue = saValue[iTestLaufWert];
									sTagForValue = saTagForValue[iTestLaufWert];
										
									//Merke: objNode ist der Root-Knoten.
									//Das geht also mit dem Root Knoten, sprich nur 1 XML - Element
									//Aber wir suche auch Kindelemente
									//ITagSimpleZZZ objTag = objNode.data;
									
									ITreeNodeZZZ<ITagSimpleZZZ> objNodeForTag = objNode.getElementByIndex(iTagIndex);
									assertNotNull(sMessage, objNodeForTag);
	
									ITagSimpleZZZ objTag = objNodeForTag.getData();
									assertEquals(sMessage, sTagForValue, objTag.getName());
									assertEquals(sMessage, sTagValue, objTag.getValue());
								}//end if isEmpty(iTagIndex)
							}//end if isEmpty(iaTagIndex)			
						}//end if isEmpty(saValue)
					}//end for ...wert
				}//end for ... lauf
			}//end if isNull(iaTagIndex)																
		 }catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
	 	}
	 }	//end testParseElementsAsTryByTestType_
}//End class
