package basic.zBasic.util.datatype.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
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
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
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
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
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
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
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
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNext(sExpression, sStringToSearch);
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
	 
	 public void testComputeExpressionFirstVector(){
		 try{
			 ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			 
			String sTest = "Das ist ein Wert";
		    String sTestXml = "aBc" + objTagType.getTagPartOpening() + sTest + objTagType.getTagPartClosing()+"xYz";
		    System.out.println("Zeile1: " + sTestXml);
		    
		    Vector<String> vec01 = XmlUtilZZZ.computeExpressionFirstVector(sTestXml, objTagType);		    
		    assertNotNull(vec01);
		    assertTrue(vec01.size()==3);
		    assertTrue(vec01.get(0).equals("aBc"));
		    assertTrue(vec01.get(1).equals(sTest));
		    assertTrue(vec01.get(2).equals("xYz"));
		    
		    //B) Empty Tag
		    sTestXml = "aBc" + objTagType.getTagPartEmpty() +"xYz";
		    System.out.println("Zeile1: " + sTestXml);
		    
		    Vector<String> vec02 = XmlUtilZZZ.computeExpressionFirstVector(sTestXml, objTagType);		    
		    assertNotNull(vec02);
		    assertTrue(vec02.size()==3);
		    assertTrue(vec02.get(0).equals("aBc"));
		    assertTrue(vec02.get(1).equals(""));
		    assertTrue(vec02.get(2).equals("xYz"));
		    
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
