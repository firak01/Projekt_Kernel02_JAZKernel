package basic.zBasic.util.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.util.abstractList.VectorExtended4XmlTagSimpleZZZ;
import basic.zBasic.util.abstractList.VectorExtended4XmlTagStringZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileTextParserZZZ;
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
}//End class
