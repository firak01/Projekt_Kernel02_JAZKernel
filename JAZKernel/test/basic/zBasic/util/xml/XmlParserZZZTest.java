package basic.zBasic.util.xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.IHashMapMultiZZZ;
import basic.zBasic.util.abstractList.VectorExtended4XmlTagSimpleZZZ;
import basic.zBasic.util.abstractList.VectorExtended4XmlTagStringZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.tree.ITreeNodeZZZ;
import basic.zBasic.util.datatype.tree.TreeNodeZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import junit.framework.TestCase;

public class XmlParserZZZTest extends TestCase{
	
	private XmlParserZZZ objParserTest = null;
	
	 protected void setUp(){
		    	
		//The main object used for testing
		objParserTest = new XmlParserZZZ();
			
			
	}//END setup
	 
	 public void testParseToVectorTagString(){
		 try{
			 String sTest;
			 VectorExtended4XmlTagStringZZZ<String> vecTag=null;
			 

			 //TODOGOON20240619: XMLTestStringContainerZZZ nutzen fuer die enum-Werte
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			vecTag = (VectorExtended4XmlTagStringZZZ<String>) objParserTest.parseToVectorTagString(sTest);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			
			
			
			
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
					 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 public void testParseToVectorTagSimple(){
		 try{
			 String sTest;
			 VectorExtended4XmlTagSimpleZZZ<String> vecTag=null;
			 

			 //TODOGOON20240619: XMLTestStringContainerZZZ nutzen fuer die enum-Werte
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			vecTag = (VectorExtended4XmlTagSimpleZZZ<String>) objParserTest.parseToVectorTagSimple(sTest);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			
			
			
			
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
					 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testParseToMap(){
		 try{
			 String sTest; String sDebug;
			 HashMapMultiIndexedZZZ<String,String> hmTag=null;
			 

			 //TODOGOON20240619: XMLTestStringContainerZZZ nutzen fuer die enum-Werte
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			hmTag = (HashMapMultiIndexedZZZ<String,String>) objParserTest.parseToMap(sTest);
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			sDebug = hmTag.computeDebugString();
			System.out.println("### AS HashMapMultiIndexedZZZ #############");
			System.out.println(sDebug);
			System.out.println("###########################################");
			
			
			HashMap<String,Object>hmTagFirst = hmTag.toHashMapInnerKeyString(IHashMapMultiZZZ.FLAGZ.TO_HASHMAP_KEEPFIRST);
			sDebug = HashMapExtendedZZZ.computeDebugString(hmTagFirst);
			System.out.println("### AS HashMap ############################");
			System.out.println(sDebug);
			System.out.println("###########################################");						
			
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
					 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testParseToTree(){
		 try{
			 String sTest; String sDebug;
			 Vector<String> vecTag=null;
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			 
			 ITreeNodeZZZ<ITagSimpleZZZ> objTree=null;
			 
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
			 
			 
			 boolean bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
			 	iCount++;
			 	sMessage = iCount + ". Teststring wird ausgewertet.";
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}
			 				 	
			 	System.out.println("### AS TreeNodeZZZ#########################");
			 	System.out.println(sMessage);
			 	
				objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
				sTest= objEnumTestType.getXml();
				System.out.println("'" + sTest +"'");

				objTree =  objParserTest.parseToTree(sTest);
														
				if(bWithText) {
					iaIndexForTag = objEnumTestType.getIndexInTreeOfExpectedTagsWithText();
				}else {
					iaIndexForTag = objEnumTestType.getIndexInTreeOfExpectedTagsWithoutText();
				}
				
				TODOGOON20240622;
				if(ArrayUtilZZZ.isNull(iaIndexForTag)) {
					
				}else {
					assertNotNull(sMessage, objTree);
					assertTrue(sMessage, objTree.isEmpty());
					
					sDebug = objTree.computeDebugString();
					System.out.println(sDebug);
					
					
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
									
								/* TODOGOON20240622 zur Analyse der einzelnen TagWerte spaeter....
								//jetzt da wir den Tagnamen haben koennen wir dafuer den Wert holen
								//vecTag = XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sTest, sTagForValue);														
								//assertNotNull(sMessage, vecTag);
								
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
								*/							
							}//iIndexForTag <= -1							
						}//end for	
							
					}//isNull saValue || saTagForValue
				}//isNull iaIndexForTag
				
				System.out.println("###########################################");				
			 }//end for
			 
			 

			//#############################################################
			 
			System.out.println("### AS TreeNodeZZZ#########################");
			
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			System.out.println("'" + sTest + "'");
			
			objTree =  objParserTest.parseToTree(sTest);
			assertNotNull(objTree);
			
			iaIndexForTag = objEnumTestType.getIndexInVectorOfExpectedTagsWithoutText();
			if(!ArrayUtilZZZ.isNull(iaIndexForTag)) {
				assertFalse(objTree.isEmpty());
				
				
				saValue=objEnumTestType.getExpectedValues();
				saTagForValue=objEnumTestType.getTagsForExpectedValues();
				if(!(ArrayUtilZZZ.isNull(saValue)||ArrayUtilZZZ.isNull(saTagForValue))) {						
					for(int i=0;i<=saTagForValue.length-1;i++) { //Alle definierten Testwerte durchgehen.
						iIndexUsedForTag=0;
						
					}//end for
				}//end if .isNull( saValue );
			}
			
			sDebug = objTree.computeDebugString();									
			System.out.println(sDebug);
			System.out.println("###########################################");
									
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
					 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
}//End class
