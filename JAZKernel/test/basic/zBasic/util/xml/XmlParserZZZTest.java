package basic.zBasic.util.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;
import basic.zBasic.xml.ITagTypeZZZ;
import basic.zBasic.xml.ITagZZZ;
import junit.framework.TestCase;

public class XmlParserZZZTest extends TestCase{
	
	private XmlParserZZZ objParserTest = null;
	
	 protected void setUp(){
		    	
		//The main object used for testing
		objParserTest = new XmlParserZZZ();
			
			
	}//END setup
	 
	 public void testParse(){
		 try{
			 String sTest;
			 Vector<ITagZZZ> vecTag=null;
			 
			//a) Negativtest
//			String sTest = "Nix da";
//			Vector<ITagZZZ> vecTag = objParserTest.parse(sTest);
//			assertNotNull(vecTag);
//			assertTrue(vecTag.isEmpty());
//			
			
			//b) Positivtest
			 
			//TODOGOON20240524;//Dieses Example aufbereiten. Ggf. Alles in ein Tryout....
			sTest = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			XmlUtilZZZ.parseByRegexV04(sTest);
			
			
			//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
			vecTag = objParserTest.parse(sTest);
			assertNotNull(vecTag);
			assertTrue(vecTag.isEmpty());
			
		 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
}//End class
