package basic.zBasic.util.datatype.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import junit.framework.TestCase;

public class XmlUtilZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
		}//END setup
	 
	 public void testComputeExpressionFirstVector(){
		 try{
			 ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			 
			String sTest = "Das ist ein Wert";
		    String sTestXml = "aBc" + objTagType.getTagPartStarting() + sTest + objTagType.getTagPartClosing()+"xYz";
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
			String sTestXml = "aBc" + objTagType.getTagPartStarting() + sTest + objTagType.getTagPartClosing()+"xYz";
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
