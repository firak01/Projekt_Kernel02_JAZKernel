package basic.zBasic.util.datatype.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import junit.framework.TestCase;

public class XmlUtilZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
	}//END setup
	 
	
	 
	 
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ PREVIOUS
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testFindFirstTagNamePreviousTo() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POS";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++++++
			//+++ Einfache Verschachtelung aus der Praxis, es gibt keinen Tagname vorher, also den einzigen holen.
			sExpression="Der dynamische Wert1 ist '{[Section A]Testentry1}'. FGL rulez.";
			sExpressionSolved = null;
			sStringToSearch= "{[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
						
			//mit Leerstring
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++ Negativtest ohne Suchwert im String und Leerstring als Suchtstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 
	 public void testFindFirstOpeningTagNamePreviousTo() {
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
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Class";
			
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POS";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//mit Leerstring
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testFindFirstClosingTagNamePreviousTo() {
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
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = null;//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
            ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			
			sExpression="PRE"+ objTagTypeMethod.getTagPartOpening() + "eine Methode" +objTagTypeMethod.getTagPartClosing() +  "a"+ objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POS";
			sExpressionSolved = objTagTypeMethod.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();			
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = null;
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
							
			//################################################
			//### Negativtest ohne einen CLOSING Tag vorher
			//################################################
			sExpression="PRE" + objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POS";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	 
	 
	 
	 
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ NEXT
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testFindFirstTagNameNextTo() {
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
			
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "/Z:Class";
			sExpressionSolved = "Z:Method";
			
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Class";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();		
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POST";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			

			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = "positioncurrent";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//############################################
			//### Negativtest
			//############################################
			
			//+++++ ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			

			//und das auch mit Leerstring als Suchstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 
	 public void testFindFirstOpeningTagNameNextTo() {
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
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "/Z:Java";
			sExpressionSolved = "Z:Method";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Method";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();				
			sExpression="PRE"+ objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "a" + objTagTypeMethod.getTagPartOpening() + "eine Methode" + objTagTypeMethod.getTagPartClosing() + "POST";								
			sExpressionSolved = objTagTypeMethod.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
						
			//++++ Negativtest, es gibt keinen folgenden, offenen TagPArt
			sExpression="PRE" + objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POST";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			

			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();			
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = "positioncurrent";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//und das auch mit Leerstring als Suchstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testFindFirstClosingTagNameNextTo() {
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
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POST";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
            ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();						
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = "method";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//#####################################
			//### Negativtest ohne Suchwert im String
			//#######################################
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//und das auch mit Leerstring als Suchstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ TAG-FIRST
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
		 
		
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
	 
	 public void testGetTagNextByName() {
		 try {
			 
			TODOGOON20251103;//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			String sExpression; String sExpressionSolved;
			String sValue; String sTagName;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "<Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class>";
			sTagName= "Z:Class";

			sValue = XmlUtilZZZ.getTagNextByName(sTagName, sExpression);
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "<Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class>";
			sTagName= "Z:Class";
				
			//Der gesuchte TagType muss in der TagByTypeFactory vorhanden sein.
			//Das ist hierbei der Fall: filename
			sExpression = "<Z:Call><Z:Java><filename>{[ArgumentSection for testCallComputed]JavaClass}</filename><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaClass}";
			sTagName= "filename";
			sValue =  XmlUtilZZZ.getTagNextByName(sTagName, sExpression);			
			assertEquals(sExpressionSolved, sValue);
			
			//Der gesuchte TagType muss in der TagByTypeFactory vorhanden sein.
			//Das ist hierbei der Fall: filename und darunter filename
			//Also verschachtelt
			sExpression = "<Z:Call><filename><filename>{[ArgumentSection for testCallComputed]JavaClass}</filename><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></filename></Z:Call>";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaClass}";
			sTagName= "filename";
			sValue = XmlUtilZZZ.getTagNextByName(sTagName, sExpression);			
			assertEquals(sExpressionSolved, sValue);
			
			
			//Also verschachtelt mit anderer Reihenfolge im inneren Tag.
			sExpression = "<Z:Call><filename><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><filename>{[ArgumentSection for testCallComputed]JavaMethod}</filename></filename></Z:Call>";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaMethod}";
			sTagName= "filename";
			sValue = XmlUtilZZZ.getTagNextByName(sTagName, sExpression);						
			assertEquals(sExpressionSolved, sValue);
			
			
			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			sTagName= "filename";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = "positioncurrent";
			//sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			sValue = XmlUtilZZZ.getTagNextByName(sTagName, sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 public void testGetTagPreviousByName() {
			fail("Test existiert noch nicht");		 
	 }
	 
	 public void testGetTagFirst() {
			fail("Test existiert noch nicht");		 
	 }
	 
	 public void testGetTagLast() {
			fail("Test existiert noch nicht");		 
	 }
	 
	
}//End class
