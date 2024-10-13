package basic.zBasic.util.datatype.string;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelCallIniSolverZZZTest;
import basic.zKernel.file.ini.KernelZFormulaIni_PathZZZ;
import junit.framework.TestCase;

public class StringZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
		}//END setup
	 
	 public void testCrlf(){
		 try{
		    String stemp;
		    stemp = StringZZZ.crlf();
		    assertNotNull("crlf is never NULL", stemp);
		    assertFalse("Length of crlf is never 0", stemp.length()==0 );
		    assertEquals(System.getProperty("line.separator"),stemp);		   
		    
		    System.out.println("Zeile1: " + stemp + "Das soll in Zeile 2 stehen");
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	public void testLeft(){
		 String stemp;
		 
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.left("das ist ein Test", 0);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("das ist ein Test", -1);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("das ist ein Test", 1);
		 assertEquals("d", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.left("das ist ein Test", sDummy.length());
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.left("das ist ein Test", sDummy.length() + 1);
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.left("das ist ein Test", sDummy.length() - 1);
		 assertEquals("das ist ein Tes", stemp);
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.left("das ist ein Test", " ");
		 assertEquals("das", stemp);
		 
		 
		 //System.out.println(stemp);
	 }
	
	public void testLeftBack(){
		 String stemp;
		 
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.leftback("das ist ein Test", 0);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.leftback("das ist ein Test", -1);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.leftback("das ist ein Test", 1);
		 assertEquals("das ist ein Tes", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.leftback("das ist ein Test", sDummy.length());
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.leftback("das ist ein Test", sDummy.length() + 1);
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.leftback("das ist ein Test", sDummy.length() - 1);
		 assertEquals("d", stemp);
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.leftback("das ist ein Test", " ");
		 assertEquals("das ist ein",stemp);
		 				 
		 //System.out.println(stemp);
	 }
	
	public void testRight(){
		String stemp;
		 
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.right("das ist ein Test", 0);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.right("das ist ein Test", -1);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.right("das ist ein Test", 1);
		 assertEquals("t", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.right("das ist ein Test", sDummy.length());
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.right("das ist ein Test", sDummy.length() + 1);
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.right("das ist ein Test", sDummy.length() - 1);
		 assertEquals("as ist ein Test", stemp); //!!! DAS D fehlt
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.right("das ist ein Test", " ");
		 assertEquals("Test", stemp);
		 
		 //System.out.println(stemp);
	}
	
	public void testRightback01(){
		String stemp;
		 
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.rightback("das ist ein Test", 0);
		 assertEquals("das ist ein Test", stemp);
		 
		 stemp = StringZZZ.rightback("das ist ein Test", -1);
		 assertEquals("das ist ein Test", stemp);
		 
		 stemp = StringZZZ.rightback("das ist ein Test", 1);
		 assertEquals("as ist ein Test", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.rightback("das ist ein Test", sDummy.length());
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.rightback("das ist ein Test", sDummy.length() + 1);
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.rightback("das ist ein Test", sDummy.length() - 1);
		 assertEquals("t", stemp); 
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.rightback("das ist ein Test", " ");
		 assertEquals("ist ein Test", stemp);
		 
		 //System.out.println(stemp);
	}
	
	public void testRightback02(){
		String stemp;
		stemp = StringZZZ.rightback("123456789", 0);
		assertEquals("123456789", stemp);
		stemp = StringZZZ.rightback("123456789", -1);
		assertEquals("123456789", stemp);
		
		stemp = StringZZZ.rightback("123456789", 1);
		assertEquals("23456789", stemp);
		
		stemp = StringZZZ.rightback("123456789", 9);
		assertEquals("", stemp);
		
		stemp = StringZZZ.rightback("123456789", 10);
		assertEquals("", stemp);
		
		stemp = StringZZZ.rightback("123456789", 2);
		assertEquals("3456789", stemp);	
	}
	
	public void testMid(){
		String stemp;
		 String sTest = "abcdefghijk";
		 //#######################################
		
		 //Teste den linken Rand
		 stemp = StringZZZ.mid(sTest, -1, 1);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.mid(sTest, 0, -1);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.mid(sTest, 0, 0);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.mid(sTest, 0, 1);
		 assertEquals("a", stemp);
		 stemp = StringZZZ.mid(sTest, 0, 2);
		 assertEquals("ab", stemp);
		 

		 //########################################'
		 //Teste den rechten Rand
		 stemp = StringZZZ.mid(sTest, sTest.length()+1, 1);
		 assertEquals("",stemp);
		 
		 stemp =StringZZZ.mid(sTest, sTest.length(), 1);
		assertEquals("", stemp);
		
		 stemp =StringZZZ.mid(sTest, sTest.length()-1, 1);
		 assertEquals("k", stemp);
		 stemp =StringZZZ.mid(sTest, sTest.length()-1, 0);
		 assertEquals("", stemp);
		
		 stemp =StringZZZ.mid(sTest, sTest.length()-2, 2);
		 assertEquals("jk", stemp);
		 
	}
	
	
	public void testMidLeftRightback(){
		String stemp;
		String sTest = "abc~=~xyz";
		//#######################################
		
		//Teste den linken Rand
		stemp = StringZZZ.midLeftRightback(sTest, "", "~");
		assertNotNull(stemp);
		assertEquals("abc", stemp);
		 
		
		//########################################'
		//Teste den rechten Rand
		stemp = StringZZZ.midLeftRightback(sTest, "~", "");
		assertNotNull(stemp);
		assertEquals("xyz",stemp);
		 
		//########################################
		//Teste die Mitte
		stemp =StringZZZ.midLeftRightback(sTest, "~", "~");
		assertNotNull(stemp);
		assertEquals("=", stemp);
		
		
		//########################################
		//Teste ueber die Raender hinaus
		//a) ueber den rechten Rand
		 stemp =StringZZZ.midLeftRightback(sTest, "x", "~");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightback(sTest, "y", "~");
		 assertEquals("", stemp);
		 
		 //b) ueber den linken Rand
		 stemp =StringZZZ.midLeftRightback(sTest, "~", "c");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightback(sTest, "~", "b");
		 assertEquals("", stemp);
		 
		 //### mal was praktisches				
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "c");
		 assertEquals("ab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "c");
		 assertEquals("de", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightback(sTest, "c", "c");
		 assertEquals("deab", stemp);//also wie bei .midLeftRight(...)
		 
		 //+++++++++++++++++++++++++++++++++
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "b", "b"); //es gibt also keine gemeinsame Schnittmenge, aber wir betrachten hier nur Grenzen
		 assertEquals("cdea", stemp);
		 
		 //Da sich hier alles ausschliessen wuerde einen anderen Teststring verwenden, in dem keine Zeichen doppelt vorkommen
		 sTest = "abcdefghijk";
		 stemp =StringZZZ.midLeftRight(sTest, "d", "h");
		 assertEquals("efg", stemp);
		 
		 
		 //++++++++++++++++++++		 		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightback(sTest, "b", "");
		 assertEquals("cde", stemp);
		 				
		 sTest = "x[test]y";
		 stemp =StringZZZ.midLeftRightback(sTest, "x", "y");
		 assertEquals("[test]", stemp);
		 
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRightback(sTest, "[", "]");
		 assertEquals("test", stemp);  //!!! Wg. diesem Ziel wurde die Methode ueberhaupt entwickelt!!! Es ist anders als bei midLeftRight(...)
	}
	
	public void testMidLeftRight(){
		String stemp;
		 String sTest = "abc~=~xyz";
		 //#######################################
		
		 //Teste den linken Rand
		 stemp = StringZZZ.midLeftRight(sTest, "", "~");
		 assertNotNull(stemp);
		 assertEquals("abc~=", stemp);
		 
		
		 //########################################'
		 //Teste den rechten Rand
		 stemp = StringZZZ.midLeftRight(sTest, "~", "");
		 assertNotNull(stemp);
		 assertEquals("=~xyz",stemp);
		 
		//########################################
		//Teste die Mitte (das sollte dann wie bei midLeftRightback(...) sein
		stemp =StringZZZ.midLeftRight(sTest, "~", "~");
		assertNotNull(stemp);
		assertEquals("=", stemp);
		
		
		//########################################
		//Teste ueber die Raender hinaus
		//a) ueber den rechten Rand
		 stemp =StringZZZ.midLeftRight(sTest, "x", "~");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "y", "~");
		 assertEquals("", stemp);
		 
		 //b) ueber den linken Rand
		 stemp =StringZZZ.midLeftRight(sTest, "~", "c");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "~", "b");
		 assertEquals("", stemp);
		 
		 //ca) mal die Eingabeparameter Strings umgedreht
		 stemp =StringZZZ.midLeftRight(sTest, "~", "x");
		 assertEquals("=~", stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "~", "y");
		 assertEquals("=~x", stemp);
		 
		 //cb) ...
		 stemp =StringZZZ.midLeftRight(sTest, "c", "~");
		 assertEquals("~=", stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "b", "~");
		 assertEquals("c~=", stemp);
		 
		 
		 
		 //########################################
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "c");
		 assertEquals("ab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "c");
		 assertEquals("de", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "c", "c");
		 assertEquals("deab", stemp); //!!! Das ist das gleiche Ergebnis wie bei midLeftRightBack(...), es wird halt so erwartet. Fuer Schnittmenge gibt es midLeftIntersected(...);
		 		 		 
		 //+++++++++++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "b");
		 assertEquals("a", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "b", ""); //also rechts keine Grenze...
		 assertEquals("cdeabcde", stemp); 
		 		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "b");
		 assertEquals("cde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "", "b");//also links keine Grenze...
		 assertEquals("abcdea", stemp);
		 
		 //+++++++++++++++++++++++++++++++++		 
		 stemp =StringZZZ.midLeftRight(sTest, "b", "b"); //es gibt also keine gemeinsame Schnittmenge, aber wir betrachten hier nur Grenzen
		 assertEquals("cdea", stemp);
		 
		 //Da sich hier alles ausschliessen wuerde einen anderen Teststring verwenden, in dem keine Zeichen doppelt vorkommen
		 sTest = "abcdefghijk";
		 stemp =StringZZZ.midLeftRight(sTest, "d", "h");
		 assertEquals("efg", stemp);
		  
		 //++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.leftback(sTest, "c");
		 assertEquals("abcdeab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.rightback(sTest, "c");
		 assertEquals("deabcde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "c", "c");
		 assertEquals("deab", stemp);
		 
		 //### mal was praktisches
		 sTest = "x[test]y";
		 stemp =StringZZZ.midLeftRight(sTest, "[", "]");
		 assertEquals("test", stemp);
		 
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRight(sTest, "[", "]");
		 assertEquals("[test]", stemp);  //!!!!! anders als bei midLeftRightback
		 
		
	}
	
	public void testMidLeftRightbackIntersect(){
		String stemp;
		 String sTest = "abc~=~xyz";
		 //#######################################
		
		 //Teste den linken Rand
		 stemp = StringZZZ.midLeftRightIntersect(sTest, "", "~");
		 assertNotNull(stemp);
		 assertEquals("abc~=", stemp);
		 
		
		 //########################################'
		 //Teste den rechten Rand
		 stemp = StringZZZ.midLeftRightIntersect(sTest, "~", "");
		 assertNotNull(stemp);
		 assertEquals("=~xyz",stemp);
		 
		//########################################
		//Teste die Mitte, da es von links und rechts keine ueberschneidung gibt, leer, aber da die Separatoren gleich sind, wird doch etwas gefunden. 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "~");
		assertNotNull(stemp);
		assertEquals("=", stemp);
		
		
		//########################################
		//Teste ueber die Raender hinaus
		//a) ueber den rechten Rand
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "x", "~");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "y", "~");
		 assertEquals("", stemp);
		 
		 //b) ueber den linken Rand
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "c");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "b");
		 assertEquals("", stemp);
		 
		 //ca) mal die Eingabeparameter Strings umgedreht
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "x");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "y");
		 assertEquals("x", stemp);
		 
		 //cb) ...
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "c", "~");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "b", "~");
		 assertEquals("c", stemp);
		 
		 
		 
		 //########################################
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "c");
		 assertEquals("ab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "c");
		 assertEquals("de", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "c", "c");
		 assertEquals("deab", stemp); //!!! Das ist das gleiche Ergebnis wie bei midLeftRightBack(...), es wird halt so erwartet. Fuer Schnittmenge gibt es midLeftIntersected(...);
		 		 		 
		 //+++++++++++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "b");
		 assertEquals("a", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "b", ""); //also rechts keine Grenze...
		 assertEquals("cdeabcde", stemp); 
		 		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "b");
		 assertEquals("cde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "", "b");//also links keine Grenze...
		 assertEquals("abcdea", stemp);
		 
		 //+++++++++++++++++++++++++++++++++		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "b", "b"); //es gibt also keine gemeinsame Schnittmenge, aber wir betrachten hier nur Grenzen
		 assertEquals("cdea", stemp);
		 
		 //Da sich hier alles ausschliessen wuerde einen anderen Teststring verwenden, in dem keine Zeichen doppelt vorkommen
		 sTest = "abcdefghijk";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "d", "h");
		 assertEquals("efg", stemp);
		 
		 //++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.leftback(sTest, "c");
		 assertEquals("abcdeab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.rightback(sTest, "c");
		 assertEquals("deabcde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "c", "c");
		 assertEquals("deab", stemp);
		 
		 //### mal was praktisches
		 sTest = "x[test]y";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "[", "]");
		 assertEquals("test", stemp);
		 
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "[", "]");
		 assertEquals("test", stemp);  //!!!!! anders als bei midLeftRight(...) , wie bei midLeftRightback(...)
		 
		
	}
	
	
	public void testStrLeft(){
		 String stemp;
		 
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.left("123456789", "1");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("123456789", "0");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("123456789", "2");
		 assertEquals("1", stemp);
		  
		 		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, sDummy);
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.left("123456789", "9");
		 assertEquals("12345678", stemp);
		 
		 stemp =StringZZZ.left("123456789", "");
		 assertEquals("",stemp);
		 
		 
		 //#####################################
		 //TEST F�R die Erweiterung um den index-parameter
		 //#####################################
		 //#######################################
		 //Teste den linken Rand (s. oben)
		 stemp = StringZZZ.left("123456789", 0, "1");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("123456789", 0, "0");
		 assertEquals("",stemp);
		 
		 stemp = StringZZZ.left("123456789", 0, "2");
		 assertEquals("1", stemp);
		  
		 		 
		 //########################################'
		 //Teste den rechten Rand (s. oben)
		 sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, 0, sDummy);
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.left("123456789", 0, "9");
		 assertEquals("12345678", stemp);
		 
		 stemp =StringZZZ.left("123456789", 0, "");
		 assertEquals("",stemp);
		 
		 //#######################################
		 //Eweiterungen fuer den index-parameter
		 //		Teste den linken Rand 
		 stemp = StringZZZ.left("123456789", 1, "2");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("123456789",1, "1");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("123456789", 1, "3");
		 assertEquals("2", stemp);
		  
		 		 
		 //########################################'
		 //Teste den rechten Rand
		 sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, 8, sDummy);  //Dat haengt nicht im speziellen von der 8 ab, sondern gilt fuer jden wert > 0, wg. sDummy = sDummy
		 assertEquals("",stemp);
		 
		 sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, 7, sDummy);
		 assertEquals("",stemp);
		 		 
		 stemp =StringZZZ.left("123456789", 8, "9");
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.left("123456789",7, "9");
		 assertEquals("8", stemp);
		 
		 stemp =StringZZZ.left("123456789", 8, "");
		 assertEquals("",stemp);
		 
		 stemp =StringZZZ.left("123456789", 7, "");
		 assertEquals("",stemp);
		 
		 
		 //##################
		 //Was passiert, wenn es ein Zeichen im String nicht gibt ?
		 stemp =StringZZZ.left("123456789",7, "A");
		 assertEquals("",stemp);
		 
		 //System.out.println(stemp);
	 }
	
	public void testStrLeftback(){
		String stemp;
		
		//Teste den linken Rand
		stemp = StringZZZ.leftback("123456789 123456789","1" );
		assertEquals("123456789 ", stemp);
		
		//Teste den rechten Rand
		stemp = StringZZZ.leftback("123456789 123456789", "9");
		assertEquals("123456789 12345678", stemp);
		
		//Teste fehlenden Wert
		stemp = StringZZZ.leftback("123456789 123456789", "0");
		assertEquals("", stemp);
		
		//Teste normale
		stemp = StringZZZ.leftback("123456789 123456789", "2");
		assertEquals("123456789 1", stemp);
		
		stemp = StringZZZ.leftback("123456789 123456789", "8");
		assertEquals("123456789 1234567", stemp);
		
		stemp = StringZZZ.leftback("123456789 123456789", "5");
		assertEquals("123456789 1234", stemp);
		
	}
	
	public void testStrRight(){
		 String stemp;
		 
		 //#######################################
		 //Teste den rechten Rand
		 stemp = StringZZZ.right("123456789", "9");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.right("123456789", "0");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.right("123456789", "8");
		 assertEquals("9", stemp);
		  
		 		 
		 //########################################'
		 //Teste den linken Rand
		 String sDummy = new String("123456789");		
		 stemp =StringZZZ.right(sDummy, sDummy);
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.right("123456789", "1");
		 assertEquals("23456789", stemp);
		 
		 stemp =StringZZZ.right("123456789", "");
		 assertEquals("", stemp);
		 
		 
		 //System.out.println(stemp);
	 }
	
	public void testStrRightback(){
		
		String stemp;
		
		//Teste den linken Rand
		stemp = StringZZZ.rightback("123456789 123456789","1" );
		assertEquals("23456789 123456789", stemp);
		
		//Teste den rechten Rand
		stemp = StringZZZ.rightback("123456789 123456789", "9");
		assertEquals(" 123456789", stemp);
		
		//Teste fehlenden Wert
		stemp = StringZZZ.rightback("123456789 123456789", "0");
		assertEquals("", stemp);
		
		//Teste normale
		stemp = StringZZZ.rightback("123456789 123456789", "2");
		assertEquals("3456789 123456789", stemp);
		
		stemp = StringZZZ.rightback("123456789 123456789", "8");
		assertEquals("9 123456789", stemp);
		
		stemp = StringZZZ.rightback("123456789 123456789", "5");
		assertEquals("6789 123456789", stemp);
		
		
	}
	
public void testPadLeft() {
	String stemp = StringZZZ.padLeft("abc", 5);
	assertEquals("  abc", stemp);
	
	
	String stemp2 = StringZZZ.padLeft("abc",  5, '-');
	assertEquals("--abc",stemp2);
	
}

public void testPadRight() {
	String stemp = StringZZZ.padRight("abc", 5);
	assertEquals("abc  ", stemp);
	
	
	String stemp2 = StringZZZ.padRight("abc",  5, '-');
	assertEquals("abc--",stemp2);
	
}
	

public void testRepeat(){
	 //#######################################
	 //Teste normal
	 String stemp = StringZZZ.repeat("1", 2);
	 assertEquals("11", stemp);
}

public void testStrWord(){
	String sTest = "aaa bbb ccc";
	
	String stemp = StringZZZ.word(sTest," ", 2);
	assertEquals("bbb", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 1);
	assertEquals("aaa", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 3);
	assertEquals("ccc", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 4);
	assertEquals("", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 0);
	assertNull(stemp);
}

public void testContains(){
	String sTest = "aaa bbb ccc";
	
	boolean btemp = StringZZZ.contains(sTest, "  "); //zwei Leerzeichen
	assertFalse(btemp); // der String ist ja nicht vorhanden
	
	boolean btemp2 = StringZZZ.contains(sTest,"c ");
	assertFalse(btemp2); // der String ist ja nicht vorhanden
	
	boolean btemp3 = StringZZZ.contains(sTest, "b ");
	assertTrue(btemp3);
	
	boolean btemp4 = StringZZZ.contains(sTest, "a");
	assertTrue(btemp4);
	
	boolean btemp5 = StringZZZ.contains(sTest, "ccc");
	assertTrue(btemp5);
	
	boolean btemp6 = StringZZZ.contains(sTest, "aaa");
	assertTrue(btemp6);
	
}

public void testCount(){
	int itemp = StringZZZ.count("12-3-45", "-");
	assertEquals(itemp,2);
}

public void testCountMatches(){
	int itemp = StringZZZ.countMatches("12-3-45", "-");
	assertEquals(itemp,2);
}

public void testCountSubstring(){
	int itemp = StringZZZ.countSubstring("12-3-45", "-");
	assertEquals(itemp,2);
}

public void testCountChar(){
	char cToFind='-';
	int itemp = StringZZZ.countChar("12-3-45", cToFind);
	assertEquals(itemp,2);
	
	Character objChar = new Character(cToFind);
	itemp = StringZZZ.countChar("12-3-45", objChar);
	assertEquals(itemp,2);
}


public void testExplode(){
	//Erst einmal den einfachen Fall
	String sTest = "panel1<./>text1.getText()";
	String[] saToken = StringZZZ.explode(sTest, "<./>");
	assertNotNull(saToken);
	assertEquals(saToken.length, 2);
	assertEquals(saToken[1], "text1.getText()");
	
	//Nun den Fall: Exploden mit einem Array von Delimitern
	String[] saDelim ={"<./>"};
	saToken = StringZZZ.explode(sTest, saDelim);
	assertNotNull(saToken);
	assertEquals(saToken.length, 2);
	assertEquals(saToken[1], "text1.getText()");
	
	
	
	//##############################################
	//FALL: ARRAY VON MEHREREN DELIMITERN
	sTest = "CarrierSequenze<?/>CarrierCreated<+/>'#'<+/>CarrierSequenze<:/>''";
	String[] saDelim2 ={"<./>","<?/>","<+/>","<:/>"};
	saToken = StringZZZ.explode(sTest, saDelim2);
	assertNotNull(saToken);
	assertEquals(saToken.length, 5);
	assertEquals(saToken[0], "CarrierSequenze");
	assertEquals(saToken[1], "CarrierCreated");
	assertEquals(saToken[2], "'#'");
	assertEquals(saToken[3], "CarrierSequenze");
	assertEquals(saToken[4], "''");
	
	//!!! Das muss auch herauskommen, wenn das Delimiter Array ganz anders ist !!!
	String[] saDelim3 ={"<?/>","<./>","<:/>","<+/>"};
	saToken = StringZZZ.explode(sTest, saDelim3);
	assertNotNull(saToken);
	assertEquals(saToken.length, 5);
	assertEquals(saToken[0], "CarrierSequenze");
	assertEquals(saToken[1], "CarrierCreated");
	assertEquals(saToken[2], "'#'");
	assertEquals(saToken[3], "CarrierSequenze");
	assertEquals(saToken[4], "''");
	
}

public void testFindSorted(){
	String sTest = "eins  zwei  drei  eins  zwei  vier  acht sechzehn";
	String[] saPattern = {"drei", "zwei"};
	
	ArrayList<String>listaString = StringZZZ.findSorted(sTest, saPattern);
	assertNotNull(listaString);
	assertEquals(listaString.size(), 3);
	assertEquals(listaString.get(0), "zwei");
	assertEquals(listaString.get(1), "drei");
	assertEquals(listaString.get(2), "zwei");	
}


public void testVecMid(){
	try{
		String sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
		Vector<String> vec = StringZZZ.vecMid(sTest, "<Z>", "</Z>", false);
		assertEquals(vec.size(), 3);
		
		String sFormula0 = (String) vec.get(0);
		assertEquals("", sFormula0);
		
		String sFormula1 = (String) vec.get(1);
		assertEquals("<Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>", sFormula1);
		
		String sFormula2 = (String) vec.get(2);
		assertEquals("", sFormula2);
		
		
		
		
		//######################################
		sTest = "Anfang<Z>das ist der <Z>[Section a]Number</Z> Test</Z>Ende";						
		vec = StringZZZ.vecMid(sTest, "<Z>", "</Z>", false);
		assertEquals(vec.size(), 3);
		
		sFormula0 = (String) vec.get(0);
		assertEquals("Anfang", sFormula0);
		
		sFormula1 = (String) vec.get(1);
		assertEquals("das ist der <Z>[Section a]Number</Z> Test", sFormula1);
		
		sFormula2 = (String) vec.get(2);
		assertEquals("Ende", sFormula2);
		
		//+++++++ Nun Randwerte Testen: links 
		String sTestB = "<Z>[Section a]Number</Z> Test";
		
		Vector<String> vecB = StringZZZ.vecMid(sTestB, "<Z>", "</Z>", false);
		assertEquals(vecB.size(), 3);
		
		String sFormulaB0 = (String) vecB.get(0);
		assertEquals("", sFormulaB0);
		
		String sFormulaB1 = (String) vecB.get(1);
		assertEquals("[Section a]Number", sFormulaB1);
		
		String sFormulaB2 = (String) vecB.get(2);
		assertEquals(" Test", sFormulaB2);
		
		
//		+++++++ Nun Randwerte Testen: rechts		
		String sTestC = "<Z>[Section a]Number</Z>";
		
		Vector<String> vecC = StringZZZ.vecMid(sTestC, "<Z>", "</Z>", false);
		assertEquals(vecB.size(), 3);
		
		String sFormulaC0 = (String) vecC.get(0);
		assertEquals("", sFormulaC0);
		
		String sFormulaC1 = (String) vecC.get(1);
		assertEquals("[Section a]Number", sFormulaC1);
		
		String sFormulaC2 = (String) vecC.get(2);
		assertEquals("", sFormulaC2);
		
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testVecMidFirst(){
	try{
		String sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
		Vector vec = StringZZZ.vecMidFirst(sTest, "[", "]", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
		assertEquals(vec.size(), 3);
		
		//Es soll noch 1x der umgebenden Tags mehr vorhanden sein, weil die Separatoren-Tags nicht zuruekgekommen sein sollen.
		String sProof = VectorUtilZZZ.implode(vec);
		assertEquals(StringZZZ.count(sProof, "["),1);
		assertEquals(StringZZZ.count(sProof, "]"),1);
		

		String sFormula0 = (String) vec.get(0);
		assertEquals("<Z><Z:Call><Z:Java><Z:Class><Z>", sFormula0);
		
		String sFormula1 = (String) vec.get(1);
		assertEquals("ArgumentSection for testCallComputed", sFormula1);
		
		String sFormula2 = (String) vec.get(2);
		assertEquals("JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>", sFormula2);

		//###############################
		
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

	public void testCapitalize(){
			String sTest = "das ist der Test";
			String stemp = StringZZZ.capitalize(sTest);
			assertEquals("Das ist der Test", stemp);
	}
	
	public void testCamelcase(){
		//Variante 1: Ohne Delimiter
		String sTest = "DASIsTEinTest";
		String stemp = StringZZZ.toCamelCase(sTest);
		assertEquals("dasIstEinTest", stemp);
		
	}
	
	public void testAbbreviateStrict(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateStrict(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateStrict(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateStrict(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 2);
			System.out.println(sErg);
			assertEquals("A.", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 3);
			System.out.println(sErg);
			assertEquals("A..", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 4);
			System.out.println(sErg);
			assertEquals("AAAA", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 4);
			System.out.println(sErg);
			assertEquals("A...", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrict(sTest, 4);
			System.out.println(sErg);
			assertEquals("a...", sErg);
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrict(sTest, 5);
			System.out.println(sErg);
			assertEquals("ab...", sErg);
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testAbbreviateStrictFromRight(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateStrictFromRight(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateStrictFromRight(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 2);
			System.out.println(sErg);
			assertEquals(".A", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 3);
			System.out.println(sErg);
			assertEquals("..A", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 4);
			System.out.println(sErg);
			assertEquals("AAAA", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 4);
			System.out.println(sErg);
			assertEquals("...A", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 4);
			System.out.println(sErg);
			assertEquals("...g", sErg);
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 5);
			System.out.println(sErg);
			assertEquals("...fg", sErg);
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testAbbreviateDynamic(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateDynamic(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateDynamic(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateDynamic(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 2);
			System.out.println(sErg);
			assertEquals("A.", sErg);
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 3);
			System.out.println(sErg);
			assertEquals("AAA", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 3);
			System.out.println(sErg);
			assertEquals("AA.", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 3);
			System.out.println(sErg);
			assertEquals("A..", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 4);
			System.out.println(sErg);
			assertEquals("AAA.", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamic(sTest, 4);
			System.out.println(sErg);
			assertEquals("a...", sErg); /// DAS IST EINE BESONDERHEIT, da besonders langer String
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamic(sTest, 5);
			System.out.println(sErg);
			assertEquals("abc..", sErg);
			
			//++++++++++++++++++
			sTest = "abcdefghijklmenop";
			sErg = StringZZZ.abbreviateDynamic(sTest, 6); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertEquals("abc...", sErg);
			
			
			//++++++++++++++++++
			sTest = "use.openvpn.serverui.component.IPExternalUpload.DlgIPExternalOVPN";
			sErg = StringZZZ.abbreviateDynamic(sTest, 20); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertEquals("use.openvpn.serve...", sErg);
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testAbbreviateDynamicLeft(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateDynamicLeft(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateDynamicLeft(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 2);
			System.out.println(sErg);
			assertEquals(".A", sErg);
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 3);
			System.out.println(sErg);
			assertEquals("AAA", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 3);
			System.out.println(sErg);
			assertEquals(".AA", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 3);
			System.out.println(sErg);
			assertEquals("..A", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 4);
			System.out.println(sErg);
			assertEquals(".AAA", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 4);
			System.out.println(sErg);
			assertEquals("...g", sErg); /// DAS IST EINE BESONDERHEIT, da besonders langer String
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 5);
			System.out.println(sErg);
			assertEquals("..efg", sErg);  //Das ist wieder anders als bei der abbreviateStrict !!!
			
			//++++++++++++++++++
			sTest = "abcdefghijklmenop";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 6); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertEquals("...nop", sErg);
			
			//++++++++++++++++++
			sTest = "use.openvpn.serverui.component.IPExternalUpload.DlgIPExternalOVPN";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 22); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertTrue(sErg.length()==22);
			assertEquals("...d.DlgIPExternalOVPN", sErg);
			
			sTest = sErg;
			sErg = StringZZZ.abbreviateDynamic(sTest, 21); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertTrue(sErg.length()==21);
			assertEquals("...d.DlgIPExternalOV.", sErg);
			
			sErg = StringZZZ.abbreviateDynamic(sTest, 20); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertTrue(sErg.length()==20);
			assertEquals("...d.DlgIPExternal..", sErg);
			
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testIndexOfAll(){
		String sTest = "das:ist:ein:Test:";
		String [] saToFind = {":"};
		Integer[] intaIndex = StringZZZ.indexOfAll(sTest, saToFind);
		assertNotNull(intaIndex);
		assertEquals(4,intaIndex.length);
		assertEquals(3,intaIndex[0].intValue());
		assertEquals(7, intaIndex[1].intValue());
		assertEquals(11, intaIndex[2].intValue());
		assertEquals(16, intaIndex[3].intValue());
		
		
		//++++++++++++ Das Ergebnis ist nicht sortiert
		String[] saToFind2 = {":", "s"};
		intaIndex = StringZZZ.indexOfAll(sTest, saToFind2);
		assertNotNull(intaIndex);
		assertEquals(7,intaIndex.length);
		assertEquals(3,intaIndex[0].intValue());
		assertEquals(7, intaIndex[1].intValue());
		assertEquals(11, intaIndex[2].intValue());
		assertEquals(16, intaIndex[3].intValue());
		assertEquals(2, intaIndex[4].intValue());
		assertEquals(5, intaIndex[5].intValue());
		assertEquals(14, intaIndex[6].intValue());
	}
	
	
	public void testMatchesPattern_ForIniExpression(){
		try{
			//#########################################################
			//1. Methode: Ermittle ob ein Ausdruck wie im Kernel-Ini-Expression-Path vorkommt
			String sPattern = KernelZFormulaIni_PathZZZ.sTAG_NAME;
			String sString = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			

			sString ="this is text";
			sPattern = "this is text";
			boolean btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			sString ="this is text";
			sPattern = ".*is.*";
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			
//			sString="\n//zum verzweifeln\n";
//			sPattern = "//[^\\r\\n]*[\\r\\n]"; //contains a Java or C# slash-slash comment. Merke fuer Java Ausfuehurung die Backslashe im String escaped.
//			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
//			assertTrue(btemp);
			
			sString = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sPattern = ".*[\\[]*[\\]]*.";  //finde einen Ausdruck in eckigen Klammern
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			
			sString = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sPattern = ".*<Z>.*[\\[]*[\\]].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text 
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
	}
	
	public void testMatchesPattern(){
		try{
			//#########################################################
			//1. Methode: Nur die Werte sollen in dem String vorkommen (und keine anderen)
			String sPattern = "1234567890ABCD"; //Alle HEX Werte, die in einer Notes-DokumentenId vorkommen k�nnen
			
			String sString = "1234567890ABCD";
			boolean btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertTrue(btemp);
			
			sString = "1234567890ABCD098"; //L�ngerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertTrue(btemp);
			
			sString = "90ABCD098"; //K�rzerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertTrue(btemp);
			
			sString = "E234567890ABCD"; //Anderer/Falscher Wert am Anfang
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567890ABCE"; //Anderer/Falscher Wert am Ende
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567E90ABCD"; //Anderer/Falscher Wert in der Mitte
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDE"; //Anderer/Falscher Wert am Ende und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDEF"; //Anderer/Falscher Wert am Ende und String l�nger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "E1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "EF1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "34567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "4567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "E1234567890AB"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "EF1234567890A"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
//			#########################################################
			//2. Methode: Keiner der Werte darf in dem Pattern String vorkommen
			//Das spare ich mir, weil es nur der umgekehrte Fall ist
			
			//########################################################
			//3. Methode RegEx verwenden			
			sPattern = "[0-9A-D]+";
			
			sString = "1234567890ABCD";
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertTrue(btemp);
			
			sString = "1234567890ABCD098"; //L�ngerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertTrue(btemp);
			
			sString = "90ABCD098"; //K�rzerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertTrue(btemp);
			
			sString = "E234567890ABCD"; //Anderer/Falscher Wert am Anfang
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567890ABCE"; //Anderer/Falscher Wert am Ende
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567E90ABCD"; //Anderer/Falscher Wert in der Mitte
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDE"; //Anderer/Falscher Wert am Ende und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDEF"; //Anderer/Falscher Wert am Ende und String l�nger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "E1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "EF1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "34567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "4567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "E1234567890AB"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "EF1234567890A"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
	}
	
	public void testReplaceCharacterGerman(){
		try{
			String sReplaceOrig="";
			String sErg = "";
			
			//++++ Ersetzungen testen
			//A) Ae			
			sReplaceOrig = "Maenner";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("Männer", sErg); 
			
			//B) Oe
			sReplaceOrig = "Moerser";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("Mörser", sErg); 
			
			sReplaceOrig = "Oelde";  //intern wird nur eld analysiert (erster und letzter Buchstabe wird nicht betrachtet).
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			//C) Ss
			sReplaceOrig = "Waffenss";  //Dabei wird intern nur affens ber�cksichtig, daher keine Umwandlung !!!
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Asseln";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Odyssee";  //Das y wird als Ausnahme im Regul�ren Ausdruck ber�cksichtig, daher keine Umwandlung
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig =  "OdXssee";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("OdXßee", sErg);
			
			
			//D) Ue  //Intern wird hier das ganze Wort betrachtet
			sReplaceOrig = "lquelle"; //Darf nicht verändert werden
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "ilquelle"; //Darf nicht ver�ndert werden
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Quelle"; //Das Q ist als Ausnahme im intern verwendeten RegEx-Ausdruck definiert
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//Qualle darf garnicht davon betroffen sein
			sReplaceOrig = "Qualle"; //Das Q ist als Ausnahme im intern verwendeten RegEx-Ausdruck definiert
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//##### Kombinationen
			sReplaceOrig="Muessen";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("Müßen", sErg); 
			
			sReplaceOrig = "MMuessen";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("MMüßen", sErg); 
			
			sReplaceOrig = "groesste";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("größte", sErg); 
			
			//###### Wortl�nge
			sReplaceOrig = "Suez";  //es werden nur Worte >= 5 Buchstaben ersetzt
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//##### Keine Ersetzung bei 3 oder mehr aufeinanderfolgenden Vokalen
			sReplaceOrig = "treuer";   // 3 und mehr aufeinanderfolgende Vokale  werden mit RegEx gepr�ft
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			sReplaceOrig = "nachbauen";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			
			sReplaceOrig = "Queen"; //Merke: Das Q ist auch als Ausnahme im intern verwendeten RegEx-Ausdruck definiert (s. Quelle")
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//und nun mehr als 3
			sReplaceOrig = "Schueeeeebe"; //Merke: Das Q ist auch als Ausnahme im intern verwendeten RegEx-Ausdruck definiert (s. Quelle")
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			
			}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
	
	public void testReplaceLeft(){
		String sValue = null;
		//try{			
			String sOrg="aaabcbaaa";
						
			String sOld = "aa";
			String sNew = "x";
			String sErg = "xabcbaaa";			
			sValue = StringZZZ.replaceLeft(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "xxxbcbaaa";
			sValue = StringZZZ.replaceLeft(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
						
			sOld = "x";
			sNew = "yy";
			sErg = sOrg;
			sValue = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
		//}catch(ExceptionZZZ ez){
		//	fail("Method throws an exception." + ez.getMessageLast());
		//}
	}
	
	public void testReplaceRight(){
		String sValue = null;
		//try{			
			String sOrg="aaabcbaaa";
						
			String sOld = "aa";
			String sNew = "x";
			String sErg = "aaabcbax";			
			sValue = StringZZZ.replaceRight(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "aaabcbxxx";
			sValue = StringZZZ.replaceRight(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "x";
			sNew = "yy";
			sErg = sOrg;
			sValue = StringZZZ.replaceRight(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
		//}catch(ExceptionZZZ ez){
		//	fail("Method throws an exception." + ez.getMessageLast());
		//}
	}
	
	public void testReplaceFarFrom(){
		//try{
			String sErg = "";
				
			String sOrg="aaabcbaaa";
			String sOld = "c";
			String sNew = "bcb";
			
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals(sOrg, sErg);
			
			sNew = "aa";
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals("aaabaabaaa", sErg);
			
			sNew = "aca";
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals("aaabacabaaa", sErg);
			
			sNew = "";
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals("aaabbaaa", sErg);
			
		//}catch(ExceptionZZZ ez){
		//	fail("Method throws an exception." + ez.getMessageLast());
		//}
	}
	
	public void testStartsWithIgnoreCase(){
		//try{
			boolean bErg = false;
				
			String sString = "Lorem Ipsum";
			String sMatch = "Lore";
			
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMatch);
			assertTrue(bErg);
			
			String sMiss = "NIXDA";
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMiss);
			assertFalse(bErg);
			
			
			sMiss = "Viel länger als Lorem Ispum";
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMiss);
			assertFalse(bErg);
			
			sMatch = "loreM";
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMatch);
			assertTrue(bErg);
						
		//}catch(ExceptionZZZ ez){
		//	fail("Method throws an exception." + ez.getMessageLast());
		//}
	}
	
	public void testShorten(){
		try{
			boolean bErg = false;
				
			String sString = "Lorem Ipsum";
			String sMatch = "Lorem Ipsum";
			
			String sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_NONE, 1);		
			assertTrue(sMatch.equals(sErg));
			
			sMatch = "Lrm psm";			
			sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_VOWEL, 1);		
			assertTrue(sMatch.equals(sErg));
			
			sMatch = "Lrm Ipsm";
			sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_VOWEL_LOWERCASE, 1);		
			assertTrue(sMatch.equals(sErg));		
			
			sMatch = "Lorem psum";
			sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_VOWEL_UPPERCASE, 1);		
			assertTrue(sMatch.equals(sErg));																				
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}					
	}
	
	public void testIsJson() {
		try{
			boolean bTest;
			
			String sString = "Lorem Ipsum";
			bTest = StringZZZ.isJson(sString);	
			assertFalse(bTest);						
			
			String sValue = "[[\"110917       \", 3.0099999999999998, -0.72999999999999998, 2.8500000000000001, 2.96, 685.0, 38603.0], [\"110917    \", 2.71, 0.20999999999999999, 2.8199999999999998, 2.8999999999999999, 2987.0, 33762.0]]";
			bTest = StringZZZ.isJson(sValue);	
			assertTrue(bTest);
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testIsNumericWithPrefix() {
		//try{
			String sString = "Lorem Ipsum";
			boolean bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertFalse(bTest);
			
			sString="---1";		
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertFalse(bTest);
			
			sString = "-1";
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertTrue(bTest);		
			
			//######
			sString="+++1";		
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertFalse(bTest);
			
			sString = "+1";
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertTrue(bTest);
			
			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}		
	}
	
	public void testHasConsecutiveDuplicateCharacter() {
		
		String sString; char cMatch;
		
		
		sString = "Lorem Ipsum";
		boolean bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString);	
		assertFalse(bTest);
		
		sString="---1";		
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString);
		assertTrue(bTest);
		
		sString = "-1";
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString);
		assertFalse(bTest);	
		
		
		//###############################
		sString = "template//";
		cMatch='/';
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString, cMatch);
		assertTrue(bTest);
		
		sString = "template//TEST";
		cMatch='/';
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString, cMatch);
		assertTrue(bTest);
		
		sString = "template/TEST/";
		cMatch='/';
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString, cMatch);
		assertFalse(bTest);
	}
	

	
	public void testEndsWithConsecutiveDuplicateCharacter() {
		
		String sString = "Lorem Ipsum";
		boolean bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);	
		assertFalse(bTest);
		
		sString="-1--";		
		bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);
		assertTrue(bTest);
		
		sString = "-1---";
		bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);
		assertTrue(bTest);
		
		sString = "-1---2-";
		bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);
		assertFalse(bTest);
	}
}//End class
