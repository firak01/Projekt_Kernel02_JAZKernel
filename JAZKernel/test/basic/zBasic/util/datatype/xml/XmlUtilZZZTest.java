package basic.zBasic.util.datatype.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import junit.framework.TestCase;

public class XmlUtilZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
	}//END setup
	 
	
	 
	 
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ PREVIOUS
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testFindFirstTagNamePrevious() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POS";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++++++
			//+++ Einfache Verschachtelung aus der Praxis, es gibt keinen Tagname vorher, also den einzigen holen.
			sExpression="Der dynamische Wert1 ist '{[Section A]Testentry1}'. FGL rulez.";
			sExpressionSolved = null;
			sStringToSearch= "{[";
			sValue = XmlUtilZZZ.findFirstTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 
	 public void testFindFirstOpeningTagNamePrevious() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Method";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Class";
			
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		
			
			
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POS";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testFindFirstClosingTagNamePrevious() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Method";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = null;//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
            ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			
			sExpression="PRE"+ objTagTypeMethod.getTagPartOpening() + "eine Methode" +objTagTypeMethod.getTagPartClosing() +  "a"+ objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POS";
			sExpressionSolved = objTagTypeMethod.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++ Negativtest ohne einen CLOSING Tag vorher
			sExpression="PRE" + objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POS";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	 
	 
	 
	 
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ NEXT
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testFindFirstTagNameNext() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Class";
			sExpressionSolved = "Z:Class";
			
			sValue = XmlUtilZZZ.findFirstTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "/Z:Class";
			sExpressionSolved = "Z:Method";
			
			sValue = XmlUtilZZZ.findFirstTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Class";
			sValue = XmlUtilZZZ.findFirstTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POST";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 
	 public void testFindFirstOpeningTagNameNext() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//+++++++++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Method";			
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "/Z:Java";
			sExpressionSolved = "Z:Method";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Method";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePrevious(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
				
			sExpression="PRE"+ objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "a" + objTagTypeMethod.getTagPartOpening() + "eine Methode" + objTagTypeMethod.getTagPartClosing() + "POST";								
			sExpressionSolved = objTagTypeMethod.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
						
			//++++ NEgativtest, es gibt keinen folgenden, offenen TagPArt
			sExpression="PRE" + objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POST";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testFindFirstClosingTagNameNext() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Java";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POST";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNext(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	
	 

	 
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testComputeTagNameFromTagPart() {
		 String sExpression; String sExpressionSolved; 
		 String sValue;
		 try {
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression=objTagType.getTagPartOpening();
			sExpressionSolved = objTagType.getTagName();
			sValue = XmlUtilZZZ.computeTagNameFromTagPart(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression=objTagType.getTagPartClosing();
			sExpressionSolved = objTagType.getTagName();
			sValue = XmlUtilZZZ.computeTagNameFromTagPart(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			
			//#### Negativtest
			sExpression = "Blabla";
			sExpressionSolved = null;
			try {
				sValue = XmlUtilZZZ.computeTagNameFromTagPart(sExpression);							
				fail("Method should have thrown an exception.");
			}catch(ExceptionZZZ ez){				
			}
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testParseFirstVector_ByObjTagType(){
		 try{
			 ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			 
			String sTest = "Das ist ein Wert";
		    String sTestXml = "aBc" + objTagType.getTagPartOpening() + sTest + objTagType.getTagPartClosing()+"xYz";
		    System.out.println("Zeile1: " + sTestXml);
		    
		    Vector<String> vec01 = XmlUtilZZZ.parseFirstVector(sTestXml, objTagType);		    
		    assertNotNull(vec01);
		    assertTrue(vec01.size()==3);
		    assertTrue(((Object)vec01.get(0)).toString().equals("aBc"));
		    assertTrue(((Object)vec01.get(1)).toString().equals(sTest));
		    assertTrue(((Object)vec01.get(2)).toString().equals("xYz"));
		    
		    //B) Empty Tag
		    sTestXml = "aBc" + objTagType.getTagPartEmpty() +"xYz";
		    System.out.println("Zeile1: " + sTestXml);
		    
		    Vector<String> vec02 = XmlUtilZZZ.parseFirstVector(sTestXml, objTagType);		    
		    assertNotNull(vec02);
		    assertTrue(vec02.size()==3);
		    assertTrue(((Object)vec02.get(0)).toString().equals("aBc"));
		    
		    assertNotNull((Object)vec02.get(1));                      //empty Tag wird zu NullObjectZZZ
		    assertTrue(((Object)vec02.get(1)).toString().equals("")); //NullObjectZZZ .toString()==""
		    assertNull(((NullObjectZZZ)((Object)vec02.get(1))).valueOf()); //Caste Object zu NullObjectZZZ .valueOf()==null
		    
		    assertTrue(((Object)vec02.get(2)).toString().equals("xYz"));
		    
		    //C) Nicht vorhandener Tag
		    sTestXml = "aBcxYz";
		    System.out.println("Zeile1: " + sTestXml);
		    
		    Vector<String> vec03 = XmlUtilZZZ.parseFirstVector(sTestXml, objTagType);		    
		    assertNotNull(vec03);
		    assertTrue(vec03.size()==3);
		    assertTrue(((Object)vec03.get(0)).toString().equals("aBcxYz"));
		    
		    assertNotNull((Object)vec03.get(1));                      //empty Tag wird zu NullObjectZZZ
		    assertTrue(((Object)vec03.get(1)).toString().equals("")); //NullObjectZZZ .toString()==""
		    assertNull(((NullObjectZZZ)((Object)vec03.get(1))).valueOf()); //Caste Object zu NullObjectZZZ .valueOf()==null
		    
		    assertNotNull((Object)vec03.get(2));                      //empty Tag wird zu NullObjectZZZ
		    assertTrue(((Object)vec03.get(2)).toString().equals("")); //NullObjectZZZ .toString()==""
		    assertNull(((NullObjectZZZ)((Object)vec03.get(2))).valueOf()); //Caste Object zu NullObjectZZZ .valueOf()==null
		    
		    
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testIsExpression(){
		 try{
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			String sTest = "Das ist ein Wert";
			
			//+++++ Negativtest
			boolean bErg = XmlUtilZZZ.isExpression(sTest, objTagType);		    
			assertFalse(bErg); 
			
			//+++++ Positivtest
			//A) Ganzer Tag
			String sTestXml = "aBc" + objTagType.getTagPartOpening() + sTest + objTagType.getTagPartClosing()+"xYz";
			System.out.println("Zeile1: " + sTestXml);
		    
			bErg = XmlUtilZZZ.isExpression(sTestXml, objTagType);		    
			assertTrue(bErg);
			
			//B) Empty Tag
			sTestXml = "aBc" + objTagType.getTagPartEmpty() +"xYz";
			System.out.println("Zeile1: " + sTestXml);
		    
			bErg = XmlUtilZZZ.isExpression(sTestXml, objTagType);		    
			assertTrue(bErg);
			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	
}//End class
