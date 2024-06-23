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
			 int iCount;
			 String sMessage = null;
			 int[] iaTagIndex=null; int iTagIndex; int iTagIndexUsed;
			 String[]saTagForValue=null; String sTagForValue;			 
			 String[]saValue=null;String[]saValueUsed=null;//ggf. gibt es fuer ein Tag mehrere Werte, trotzdem keine "Array von Array". Statt dessen extra ein Array aufbauen.
			 boolean bWithText;
			 
			 
			 //Merke: Default ist "Mit Text"
			 iCount = 0;			 
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
			 	iCount++;			 	
			 	objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
			 	
			 	System.out.println("### AS TreeNodeZZZ #########################");
			 	
			 	//#################################################################
				//### OHNE TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, true);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
				testParseToTreeByTestType_(objEnumTestType, bWithText, sMessage);
				
			 	//#################################################################
				//### MIT TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, false);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
				testParseToTreeByTestType_(objEnumTestType, bWithText, sMessage);
					
				System.out.println("###########################################");
			 }//end for iEnumSet
				
			
			 //###############################################################
			 //#############################################################
			 
			System.out.println("### AS TreeNodeZZZ #########################");
			
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			System.out.println("'" + sTest + "'");
			
			objTree =  objParserTest.parseToTree(sTest);
			assertNotNull(objTree);
			
			sDebug = objTree.computeDebugString();									
			System.out.println(sDebug);
			System.out.println("###########################################");
									
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
					 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 private void testParseToTreeByTestType_(IEnumSetMappedTestXmlTypeZZZ objEnumTestType, boolean bWithText, String sMessage) {
		 try{
			String sTest; String sDebug;
			int[]iaTagIndex; int iTagIndex; 
			String[]saTagForValue=null; String sTagForValue; String sTagValue;
 			String[]saValue=null;
 						
			sTest= objEnumTestType.getXml();
			System.out.println("'" + sTest +"'");

 			ITreeNodeZZZ<ITagSimpleZZZ> objTree =  objParserTest.parseToTree(sTest);
 			sDebug = objTree.computeDebugString();									
			System.out.println(sDebug);
 			
 			
			 //Ermittle in diesem Text nicht die Anzahl der Knoten, sondern konkrete Werte
			 //Daher von den angegebenen Indexpositionen ausgehen
			if(bWithText) {
				iaTagIndex = objEnumTestType.getIndexInTreeOfExpectedTagsWithText();
			}else {
				iaTagIndex = objEnumTestType.getIndexInTreeOfExpectedTagsWithoutText();
			}
			
			//Ohne Indexpositionen also (wie bei iElementsWithoutText, ect) ueberhaupt kein Wert
			if(ArrayUtilZZZ.isNull(iaTagIndex)) {
				if(sTest==null) {
					assertNull(sMessage, objTree);
				}else {
					//Das kann dann nur ein Leerstring sein
					assertNotNull(sMessage,objTree);
					assertTrue(sMessage, objTree.isEmpty());					
				}
			}else {
				assertNotNull(sMessage,objTree);
				
				//Nun auf Werte abpruefen, falls Testwerte gepflegt sind
				
//				... sind Testwerte gepflegt?
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
									assertFalse(sMessage, objTree.isEmpty());
									
									sTagValue = saValue[iTestLaufWert];
									sTagForValue = saTagForValue[iTestLaufWert];
										
									//Merke: objNode ist der Root-Knoten.
									//Das geht also mit dem Root Knoten, sprich nur 1 XML - Element
									//Aber wir suche auch Kindelemente
									//ITagSimpleZZZ objTag = objNode.data;
									
									ITreeNodeZZZ<ITagSimpleZZZ> objNodeForTag = objTree.getElementByIndex(iTagIndex);
									assertNotNull(sMessage, objNodeForTag);

									ITagSimpleZZZ objTag = objNodeForTag.getData();
									assertEquals(sMessage, sTagForValue, objTag.getName());
									assertEquals(sMessage, sTagValue, objTag.getValue());
								}//end if isEmpty(iTagIndex)
							}//end if isEmpty(iaTagIndex)														
						}//end if isEmpty(saValue)
					}//end for ...wert																	
				}//end for (Testlauf)													
			}//end if isEmpty(iaTagIndex)
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }//end testParseToTreeByTestType_
		 
}//End class
