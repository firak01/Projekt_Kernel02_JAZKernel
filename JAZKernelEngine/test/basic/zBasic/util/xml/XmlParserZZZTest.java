package basic.zBasic.util.xml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.IHashMapMultiZZZ;
import basic.zBasic.util.abstractList.IVector4XmlZZZ;
import basic.zBasic.util.abstractList.Vector4XmlTagSimpleZZZ;
import basic.zBasic.util.abstractList.Vector4XmlTagStringZZZ;
import basic.zBasic.util.datatype.enums.EnumMappedAvailableHelperZZZ;
import basic.zBasic.util.datatype.tree.ITreeNodeZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
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
			 Vector4XmlTagStringZZZ<String> vecTag=null;
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumMappedAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);
			 
			//Teste alle XML Strings hinsichtlich des Werts in dem Knoten
			//Merke: Getestet wird auf den puren Eingabestring.
			 //      Da also keine weiteren Knoten <text> hinzugeneriert werden,
			 //      gilt die Position des Erwarteten Strings im Ergebnissektor für den "ohne umgebende texte" Fall. 
			 
			//A) //+++ Tag kommt nur 1x vor
			 int iCount;
			 String sMessage = null;
			 boolean bWithText;
			 boolean bAsTagString = true; //!!!!!!!!!!!!!!!!!!!
			 
			 //Merke: Default ist "Mit Text"
			 iCount = 0;			 
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
			 	iCount++;			 	
			 	objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
			 	
			 	System.out.println("### AS VectorExtended4XmlTagStringZZZ #########################");
			 	
			 	//#################################################################
				//### OHNE TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, true);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
				testParseToVectorByTestType_(bAsTagString, objEnumTestType, bWithText, sMessage);
				
			 	//#################################################################
				//### MIT TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, false);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
			 	testParseToVectorByTestType_(bAsTagString, objEnumTestType, bWithText, sMessage);
					
				System.out.println("###########################################");
			 }//end for iEnumSet
				
			
			 //###############################################################
			 //#############################################################
			 
			 
			 System.out.println("### AS VectorExtended4XmlTagStringZZZ #########################");			
							 
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			vecTag = (Vector4XmlTagStringZZZ<String>) objParserTest.parseToVectorTagString(sTest);
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
			 Vector4XmlTagSimpleZZZ<String> vecTag=null;
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumMappedAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);
			 
			//Teste alle XML Strings hinsichtlich des Werts in dem Knoten
			//Merke: Getestet wird auf den puren Eingabestring.
			 //      Da also keine weiteren Knoten <text> hinzugeneriert werden,
			 //      gilt die Position des Erwarteten Strings im Ergebnissektor für den "ohne umgebende texte" Fall. 
			 
			//A) //+++ Tag kommt nur 1x vor
			 int iCount;
			 String sMessage = null;
			 boolean bWithText;
			 boolean bAsTagString = false; //!!!!!!!!!!!!!!!!!!!!!!!!!
			 
			 //Merke: Default ist "Mit Text"
			 iCount = 0;			 
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
			 	iCount++;			 	
			 	objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
			 	
			 	System.out.println("### AS VectorExtended4XmlTagSimpleZZZ #########################");
			 	
			 	//#################################################################
				//### OHNE TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, true);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
				testParseToVectorByTestType_(bAsTagString, objEnumTestType, bWithText, sMessage);
				
			 	//#################################################################
				//### MIT TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, false);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
			 	testParseToVectorByTestType_(bAsTagString, objEnumTestType, bWithText, sMessage);
					
				System.out.println("###########################################");
			 }//end for iEnumSet
				
			
			 //###############################################################
			 //#############################################################
			 
			System.out.println("### AS VectorExtended4XmlTagSimpleZZZ #########################");
			
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			vecTag = (Vector4XmlTagSimpleZZZ<String>) objParserTest.parseToVectorTagSimple(sTest);
			assertNotNull(vecTag);
			assertFalse(vecTag.isEmpty());
			System.out.println("###########################################");
			
			
			
			
			
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
					 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 private void testParseToVectorByTestType_(boolean bVectorAsTagString, IEnumSetMappedTestXmlTypeZZZ objEnumTestType, boolean bWithText, String sMessage) {
		 try{
			String sTest; String sDebug;
			int[]iaTagIndex; int iTagIndex; 
			String[]saTagForValue=null; String sTagForValue; String sTagValue;
 			String[]saValue=null;
 						
			sTest= objEnumTestType.getXml();
			System.out.println("'" + sTest +"'");

			IVector4XmlZZZ vecTag = null;
			if(bVectorAsTagString) {			
			//VectorExtended4XmlTagSimpleZZZ<String> vecTag=null;
				vecTag = objParserTest.parseToVectorTagString(sTest);
			}else {
				vecTag = objParserTest.parseToVectorTagSimple(sTest);
			}
			 
 			//ITreeNodeZZZ<ITagSimpleZZZ> objTree =  objParserTest.parseToVectorTagString(sTest);
 			//sDebug = objTree.computeDebugString();
			
			sDebug = vecTag.computeDebugString();
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
					assertNull(sMessage, vecTag);
				}else {
					//Das kann dann nur ein Leerstring sein
					assertNotNull(sMessage,vecTag);
					assertTrue(sMessage, vecTag.isEmpty());					
				}
			}else {
				assertNotNull(sMessage,vecTag);
				
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
									assertFalse(sMessage, vecTag.isEmpty());
									
									sTagValue = saValue[iTestLaufWert];
									sTagForValue = saTagForValue[iTestLaufWert];
										
									//Merke: Nun wieder unterscheiden zwischen den Varianten, als Tag oder oder Text
									if(bVectorAsTagString) {
										Object obj = vecTag.getElementByIndex(iTagIndex);
										assertNotNull(sMessage, obj);
	
										String sValueToProof = XmlUtilZZZ.computeTag(sTagForValue, sTagValue);
										assertEquals(sMessage, sValueToProof, obj.toString());
									}else {
										ITagSimpleZZZ objTag = (ITagSimpleZZZ) vecTag.getElementByIndex(iTagIndex);
										assertNotNull(sMessage, objTag);
										
										assertEquals(sMessage, sTagForValue, objTag.getName());
										assertEquals(sMessage, sTagValue, objTag.getValue());										
									}
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
	 
	 public void testParseToMap(){
		 try{
			 String sTest; String sDebug;
			 HashMapMultiIndexedZZZ<String,String> hmTag=null;
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumMappedAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);
			 
			//Teste alle XML Strings hinsichtlich des Werts in dem Knoten
			//Merke: Getestet wird auf den puren Eingabestring.
			 //      Da also keine weiteren Knoten <text> hinzugeneriert werden,
			 //      gilt die Position des Erwarteten Strings im Ergebnissektor für den "ohne umgebende texte" Fall. 
			 
			//A) //+++ Tag kommt nur 1x vor
			 int iCount;
			 String sMessage = null;
			 boolean bWithText;
			
			 //Merke: Default ist "Mit Text"
			 iCount = 0;			 
			 for(IEnumSetMappedZZZ objEnumMapped : listaEnumMapped){
			 	iCount++;			 	
			 	objEnumTestType = (IEnumSetMappedTestXmlTypeZZZ) objEnumMapped;
			 	
			 	System.out.println("### AS HashMapMultiIndexedZZZ #########################");
			 	
			 	//#################################################################
				//### OHNE TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, true);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
				testParseToMapByTestType_(objEnumTestType, bWithText, sMessage);
				
			 	//#################################################################
				//### MIT TEXT			 	
			 	objParserTest.setFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT, false);
			 	bWithText = !objParserTest.getFlag(IParserXmlZZZ.FLAGZ.PARSE_WITHOUTTEXT);
			 	sMessage = iCount + ". Teststring wird ausgewertet (bWithText="+bWithText+").";
			 	System.out.println(sMessage);
			 	if(iCount==4) {
					System.out.println("Breakpoint Pause zum debuggen");					
				}			 	
			 	testParseToMapByTestType_(objEnumTestType, bWithText, sMessage);
					
				System.out.println("###########################################");
			 }//end for iEnumSet
				
			
			 //###############################################################
			 //#############################################################
			 
			 
			//####################################################
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			hmTag = (HashMapMultiIndexedZZZ<String,String>) objParserTest.parseToMap(sTest);
			assertNotNull(hmTag);
			assertFalse(hmTag.isEmpty());
			sDebug = hmTag.computeDebugString();
			System.out.println("### AS HashMapMultiIndexedZZZ #############");
			System.out.println(sDebug);
			System.out.println("###########################################");
			
			
			HashMap<String,Object>hmTagFirst = hmTag.toHashMapInnerKeyString(IHashMapMultiZZZ.FLAGZ.TO_HASHMAP_KEEPFIRST);
			sDebug = HashMapZZZ.computeDebugString(hmTagFirst);
			System.out.println("### AS HashMap ############################");
			System.out.println(sDebug);
			System.out.println("###########################################");						
			
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
					 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 private void testParseToMapByTestType_(IEnumSetMappedTestXmlTypeZZZ objEnumTestType, boolean bWithText, String sMessage) {
		 try{
			String sTest; String sDebug;
			int[]iaTagIndex; int iTagIndex; 
			String[]saTagForValue=null; String sTagForValue; String sTagValue;
 			String[]saValue=null;
 						
			sTest= objEnumTestType.getXml();
			System.out.println("'" + sTest +"'");

			IHashMapMultiZZZ hmTag = objParserTest.parseToMap(sTest);
			
			sDebug = hmTag.computeDebugString();
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
					assertNull(sMessage, hmTag);
				}else {
					//Das kann dann nur ein Leerstring sein
					assertNotNull(sMessage,hmTag);
					assertTrue(sMessage, hmTag.isEmpty());					
				}
			}else {
				assertNotNull(sMessage,hmTag);
				
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
									assertFalse(sMessage, hmTag.isEmpty());
									
									sTagValue = saValue[iTestLaufWert];
									sTagForValue = saTagForValue[iTestLaufWert];
																											
									Object obj = hmTag.getElementByIndex(iTagIndex);
									assertNotNull(sMessage, obj);
																		
									String sValueToProof = XmlUtilZZZ.computeTagAsHashMapEntry(sTagForValue, sTagValue);									
									assertEquals(sMessage, sValueToProof, obj.toString());
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
	 
	 public void testParseToTree(){
		 try{
			 String sTest; String sDebug;
			 IEnumSetMappedTestXmlTypeZZZ objEnumTestType=null;
			 
			 ITreeNodeZZZ<ITagSimpleZZZ> objTree=null;
			 
			 //Hole die XML Strings aus dem Enum
			 ArrayList<IEnumSetMappedZZZ>listaEnumMapped = EnumMappedAvailableHelperZZZ.searchEnumMappedList(XmlTestStringContainerZZZ.class,XmlTestStringContainerZZZ.sENUMNAME);
			 
			//Teste alle XML Strings hinsichtlich des Werts in dem Knoten
			//Merke: Getestet wird auf den puren Eingabestring.
			 //      Da also keine weiteren Knoten <text> hinzugeneriert werden,
			 //      gilt die Position des Erwarteten Strings im Ergebnissektor für den "ohne umgebende texte" Fall. 
			 
			//A) //+++ Tag kommt nur 1x vor
			 int iCount;
			 String sMessage = null;			 
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
