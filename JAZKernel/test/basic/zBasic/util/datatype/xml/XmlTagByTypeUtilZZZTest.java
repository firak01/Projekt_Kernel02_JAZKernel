package basic.zBasic.util.datatype.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import junit.framework.TestCase;

public class XmlTagByTypeUtilZZZTest extends TestCase{
	
	
	protected void setUp(){
		    			
	}//END setup
	 
	
	public void testGetTagNextFromFactoryByName() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sTagName;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "<Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class>";
			sTagName= "Z:Class";
				
			//Der gesuchte TagType muss in der TagByTypeFactory vorhanden sein.
			//Das ist hierbei nicht der Fall
			try {
				ITagByTypeZZZ objTag = XmlUtilTagByTypeZZZ.getTagNextFromFactoryByName(sTagName, sExpression);
				fail("Exception should have been thrown. TagType not handled in Factory");
			}catch(ExceptionZZZ ez1){
			}
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "<Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class>";
			sTagName= "Z:Class";
				
			//Der gesuchte TagType muss in der TagByTypeFactory vorhanden sein.
			//Das ist hierbei der Fall: filename
			sExpression = "<Z:Call><Z:Java><filename>{[ArgumentSection for testCallComputed]JavaClass}</filename><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaClass}";
			sTagName= "filename";
			ITagByTypeZZZ objTag = XmlUtilTagByTypeZZZ.getTagNextFromFactoryByName(sTagName, sExpression);			
			sValue = objTag.getValue();
			assertEquals(sExpressionSolved, sValue);
			
			//Der gesuchte TagType muss in der TagByTypeFactory vorhanden sein.
			//Das ist hierbei der Fall: filename und darunter filename
			//Also verschachtelt
			sExpression = "<Z:Call><filename><filename>{[ArgumentSection for testCallComputed]JavaClass}</filename><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></filename></Z:Call>";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaClass}";
			sTagName= "filename";
			ITagByTypeZZZ objTag2 = XmlUtilTagByTypeZZZ.getTagNextFromFactoryByName(sTagName, sExpression);			
			sValue = objTag2.getValue();
			assertEquals(sExpressionSolved, sValue);
			
			
			//Also verschachtelt mit anderer Reihenfolge im inneren Tag.
			sExpression = "<Z:Call><filename><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><filename>{[ArgumentSection for testCallComputed]JavaMethod}</filename></filename></Z:Call>";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaMethod}";
			sTagName= "filename";
			ITagByTypeZZZ objTag3 = XmlUtilTagByTypeZZZ.getTagNextFromFactoryByName(sTagName, sExpression);			
			sValue = objTag3.getValue();
			assertEquals(sExpressionSolved, sValue);
			
			
			//Einfacher Test, wenn Leerstring als Tagname angegeben ist, ist null das Ergebnisobjekt
			//Merke: Der intern verwendete RegEx funktioniert nur, wenn man den ersten Tag kennt, beim Namen
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			sTagName= "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";			
			ITagByTypeZZZ objTag4 = XmlUtilTagByTypeZZZ.getTagNextFromFactoryByName(sTagName, sExpression);
			assertNull(objTag4);
			
			
			//#######################################################
			//### Negativtest: Der Tag existiert nicht im String, es muss aber ein von der TagFactory verwendeter Tag sein.
			//###              Sonst gibt es eh einen Fail   
			//#######################################################
			ITagTypeZZZ objTagTypeLineNr = new TagTypeLineNumberZZZ();
			sTagName= objTagTypeLineNr.getTagName();
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = objTagTypePositionCurrent.getTagName();
			ITagByTypeZZZ objTag5 = XmlUtilTagByTypeZZZ.getTagNextFromFactoryByName(sTagName, sExpression);
			assertNull(objTag5);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	public void testIsExpression(){
		 try{
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			String sTest = "Das ist ein Wert";
			
			//+++++ Negativtest
			boolean bErg = XmlUtilTagByTypeZZZ.isExpression(sTest, objTagType);		    
			assertFalse(bErg); 
			
			//+++++ Positivtest
			//A) Ganzer Tag
			String sTestXml = "aBc" + objTagType.getTagPartOpening() + sTest + objTagType.getTagPartClosing()+"xYz";
			System.out.println("Zeile1: " + sTestXml);
		    
			bErg = XmlUtilTagByTypeZZZ.isExpression(sTestXml, objTagType);		    
			assertTrue(bErg);
			
			//B) Empty Tag
			sTestXml = "aBc" + objTagType.getTagPartEmpty() +"xYz";
			System.out.println("Zeile1: " + sTestXml);
		    
			bErg = XmlUtilTagByTypeZZZ.isExpression(sTestXml, objTagType);		    
			assertTrue(bErg);
			
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
		
}//End class
