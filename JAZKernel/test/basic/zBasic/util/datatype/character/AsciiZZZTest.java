package basic.zBasic.util.datatype.character;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import junit.framework.TestCase;

public class AsciiZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
	}//END setup
	 
	public void testFromNumber2BlankReverse() {
//		 try{
	    char cAscii = '0';
	    int iBorder = AsciiTableZZZ.SectionZZZ.BLANK.getEnd();
	    System.out.println("OBERE GRENZE= " + iBorder);
	    
	    int itest = (int) cAscii;
	    int itemp = AsciiZZZ.fromNumber2BlankReverse(itest);
	    //Wenn durch den ROT -  Crypt - Algorithmus nix verschoben wird, kommt eine Zahl +1 hoeher als die obere Grenze heraus.
	    assertTrue("New Ascii code", itemp==iBorder+1 ); 

	    //Wird durch den Crypt-Algorithmus etwas verschoben (-1), dann kommt die Zahl der oberen Grenze heraus.
	    itest = (int) cAscii;
	    itest = itest - 1;
	    itemp = AsciiZZZ.fromNumber2BlankReverse(itest);
	    assertTrue("New Ascii code", itemp==iBorder ); 

	    //Nun in einer Schleife testen, um ggfs. Grenzwertfehler zu bekommen	   
	    int iRotValue = -1;
	    for(int i=(int)cAscii; i>=0;i--) {
	    	iRotValue++;
	    	itemp = AsciiZZZ.fromNumber2BlankReverse(i);
	    	//System.out.println("abzuziehender iRotValue= " + iRotValue + "| i= " + i + " | itemp = " + itemp + " | Zeichen ='" + (char)itemp + "'");
	    	assertTrue("New Ascii code", itemp==(iBorder-iRotValue+1)); 
	    }
	    
	    
	  //++++++++++++++++++
//		try{
//			sTest = "AA";
//			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 1);
//			fail("Method should have thrown an exception");
//		 
//		}catch(ExceptionZZZ ez){
//			//HIER WIRD EIN FEHLER ERWARTET
//		}
	    
//	 }catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//	}
 }

	 public void testFromLetterLowercase(){
//		 try{
		 int iAsciiNew; char cAscii; char cAsciiNew;
		
		
		//!!! TESTDATEN OHNE LEERZEICHEN / BLANK, das zu verschieben waere.		
		int[] iaIndex    =   {0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15, 16, 17 }; int iIndex; //Damit man nicht immer wieder abzählen muss...
	    int[] iaRotValue =   {0,  1,  2,  3, 26, 27, 30, 31, 32, 55, 56, 57, 58, 62, 63, 64, 82, 83 }; int iRotValue;
	    int iUbound = iaRotValue.length-1;
	    
	    
	    
	  //############################
	    cAscii = 'f'; //Das Ausgangszeichen
	    char[] caExpected = {'f','g','h','i','5','6','9','A','B','Y','Z','a','b','f','g','h','z','0'};
	    
	    //Nun in einer Schleife testen	   
	    for(int i=0; i<=iUbound;i++) {
	    	iIndex = iaIndex[i];
	    	iRotValue=iaRotValue[iIndex];
	    	if(iRotValue==57) {
	    		System.out.println("TEST BREAKPOINT");
	    		System.out.println("TEST BREAKPOINT");
	    	}
	    	iAsciiNew = AsciiZZZ.fromLetterLowercase(cAscii, iRotValue);
    		cAsciiNew = (char)iAsciiNew;	    
	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + cAsciiNew + "'");
    		assertTrue("1. Testcharacter: False Ascii code at Index " + i + " ('"+cAsciiNew+"'). Expected: '"+(char)caExpected[iIndex]+"'. Rotation: " + iaRotValue[iIndex], caExpected[iIndex]==(char)iAsciiNew);	    
	    }
	    
	    
	    //############################
//	    cAscii = '5'; //Anderes Ausgangszeichen
//	    int[] caExpected2 = {'5','4','3','2','f','e','d','c','F','E','D','C','5','4','3','2','f'};
//	    
//	    //Nun in einer Schleife testen	    
//	    for(int i=0; i<=iUbound;i++) {
//	    	iIndex = iaIndex[i];
//	    	iRotValue=iaRotValue[iIndex];
//	    	if(iRotValue==14) {
//	    		System.out.println("TEST BREAKPOINT");
//	    		System.out.println("TEST BREAKPOINT");
//	    	}
//	    	iAsciiNew = AsciiZZZ.fromNumberReverse(cAscii, iRotValue);
//	    	cAsciiNew = (char)iAsciiNew;
//	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + cAsciiNew + "'");
//    		assertTrue("2. Testcharacter: False Ascii code at Index " + i + " ('"+cAsciiNew+"'). Expected: '"+(char)caExpected2[iIndex]+"'. Rotation: " + iaRotValue[iIndex], caExpected2[iIndex]==(char)iAsciiNew);	    
//	    }
	    
	    //############################
//	    cAscii = 'f'; //Anderes Ausgangszeichen
//	    int[] caExpected3 = {'5','4','3','2','f','e','d','c','F','E','D','C','5','4','3','2','f'};
//	    
//	    //Nun in einer Schleife testen	    
//	    for(int i=0; i<=iUbound;i++) {
//	    	iIndex = iaIndex[i];
//	    	iRotValue=iaRotValue[iIndex];
//	    	if(iRotValue==14) {
//	    		System.out.println("TEST BREAKPOINT");
//	    		System.out.println("TEST BREAKPOINT");
//	    	}
//	    	iAsciiNew = AsciiZZZ.fromNumberReverse(cAscii, iRotValue);
//	    	cAsciiNew = (char)iAsciiNew;
//	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + cAsciiNew + "'");
//    		assertTrue("2. Testcharacter: False Ascii code at Index " + i + " ('"+cAsciiNew+"'). Expected: '"+(char)caExpected3[iIndex]+"'. Rotation: " + iaRotValue[iIndex], caExpected3[iIndex]==(char)iAsciiNew);	    
//	    }
//	    
	 }
	 public void testFromLetterLowercaseReverse(){
//		 try{
		
	    	
		int iAsciiNew;
		
		//!!! TESTDATEN OHNE LEERZEICHEN / BLANK, das zu verschieben waere.
		char cAscii = 'b'; //Das Ausgangszeichen		
	    int[] iaRotValue =   {0,  1,  2,  3, 26, 27, 28, 29, 36, 37, 38, 39, 62, 63 }; int iRotValue;
	    char[] caExpected = {'b','a','Z','Y','B','A','9','8','1','0','z','y','b','a'};
	    
	    //Nun in einer Schleife testen
	    int iUbound = iaRotValue.length-1;
	    for(int i=0; i<=iUbound;i++) {
	    	iRotValue=iaRotValue[i];
	    	if(iRotValue==2) {
	    		System.out.println("TEST BREAKPOINT");
	    		System.out.println("TEST BREAKPOINT");
	    	}
	    	iAsciiNew = AsciiZZZ.fromLetterLowercaseReverse(cAscii, iRotValue);
	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + (char)iAsciiNew + "'");
    		assertTrue("New Ascii code", caExpected[i]==(char)iAsciiNew);	    
	    }
	 }
	 
	 public void testFromLetterUppercaseReverse(){
//		 try{
		
	    	
		int iAsciiNew;
		
		//!!! TESTDATEN OHNE LEERZEICHEN / BLANK, das zu verschieben waere.
		char cAscii = 'B'; //Das Ausgangszeichen		
	    int[] iaRotValue =   {0,  1,  2,  3, 10, 11, 12, 13, 36, 37, 38, 39, 62, 63 }; int iRotValue;
	    char[] caExpected = {'B','A','9','8','1','0','z','y','b','a','Z','Y','B','A'};
	    
	    //Nun in einer Schleife testen
	    int iUbound = iaRotValue.length-1;
	    for(int i=0; i<=iUbound;i++) {
	    	iRotValue=iaRotValue[i];
	    	if(iRotValue==38) {
	    		System.out.println("TEST BREAKPOINT");
	    		System.out.println("TEST BREAKPOINT");
	    	}
	    	iAsciiNew = AsciiZZZ.fromLetterUppercaseReverse(cAscii, iRotValue);
	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + (char)iAsciiNew + "'");
    		assertTrue("New Ascii code", caExpected[i]==(char)iAsciiNew);	    
	    }
	 }
	 
	 public void testFromNumberReverse(){
//		 try{
		 int iAsciiNew; char cAscii; char cAsciiNew;
		
		
		//!!! TESTDATEN OHNE LEERZEICHEN / BLANK, das zu verschieben waere.		
		int[] iaIndex    =   {0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15, 16 }; int iIndex; //Damit man nicht immer wieder abzählen muss...
	    int[] iaRotValue =   {0,  1,  2,  3, 26, 27, 28, 29, 52, 53, 54, 55, 62, 63, 64, 65, 88 }; int iRotValue;
	    int iUbound = iaRotValue.length-1;
	    
	    
	    
	  //############################
//	    cAscii = '1'; //Das Ausgangszeichen
//	    char[] caExpected = {'1','0','z','y','b','a','Z','Y','B','A','9','8','1','0','z','y','b'};
//	    
//	    //Nun in einer Schleife testen	   
//	    for(int i=0; i<=iUbound;i++) {
//	    	iIndex = iaIndex[i];
//	    	iRotValue=iaRotValue[iIndex];
//	    	if(iRotValue==14) {
//	    		System.out.println("TEST BREAKPOINT");
//	    		System.out.println("TEST BREAKPOINT");
//	    	}
//	    	iAsciiNew = AsciiZZZ.fromNumberReverse(cAscii, iRotValue);
//    		cAsciiNew = (char)iAsciiNew;	    
//	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + cAsciiNew + "'");
//    		assertTrue("1. Testcharacter: False Ascii code at Index " + i + " ('"+cAsciiNew+"'). Expected: '"+(char)caExpected[iIndex]+"'. Rotation: " + iaRotValue[iIndex], caExpected[iIndex]==(char)iAsciiNew);	    
//	    }
	    
	    
	    //############################
	    cAscii = '5'; //Anderes Ausgangszeichen
	    int[] caExpected2 = {'5','4','3','2','f','e','d','c','F','E','D','C','5','4','3','2','f'};
	    
	    //Nun in einer Schleife testen	    
	    for(int i=0; i<=iUbound;i++) {
	    	iIndex = iaIndex[i];
	    	iRotValue=iaRotValue[iIndex];
	    	if(iRotValue==14) {
	    		System.out.println("TEST BREAKPOINT");
	    		System.out.println("TEST BREAKPOINT");
	    	}
	    	iAsciiNew = AsciiZZZ.fromNumberReverse(cAscii, iRotValue);
	    	cAsciiNew = (char)iAsciiNew;
	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + cAsciiNew + "'");
    		assertTrue("2. Testcharacter: False Ascii code at Index " + i + " ('"+cAsciiNew+"'). Expected: '"+(char)caExpected2[iIndex]+"'. Rotation: " + iaRotValue[iIndex], caExpected2[iIndex]==(char)iAsciiNew);	    
	    }
	    
	    //############################
//	    cAscii = 'f'; //Anderes Ausgangszeichen
//	    int[] caExpected3 = {'5','4','3','2','f','e','d','c','F','E','D','C','5','4','3','2','f'};
//	    
//	    //Nun in einer Schleife testen	    
//	    for(int i=0; i<=iUbound;i++) {
//	    	iIndex = iaIndex[i];
//	    	iRotValue=iaRotValue[iIndex];
//	    	if(iRotValue==14) {
//	    		System.out.println("TEST BREAKPOINT");
//	    		System.out.println("TEST BREAKPOINT");
//	    	}
//	    	iAsciiNew = AsciiZZZ.fromNumberReverse(cAscii, iRotValue);
//	    	cAsciiNew = (char)iAsciiNew;
//	    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + cAsciiNew + "'");
//    		assertTrue("2. Testcharacter: False Ascii code at Index " + i + " ('"+cAsciiNew+"'). Expected: '"+(char)caExpected3[iIndex]+"'. Rotation: " + iaRotValue[iIndex], caExpected3[iIndex]==(char)iAsciiNew);	    
//	    }
//	    
	 }
	 
	 
	
	 public void testFromNumber2LetterLowercaseReverse(){
//		 try{
		 int iAsciiNew;int iAscii;
		 
		//!!! TESTDATEN OHNE LEERZEICHEN / BLANK, das zu verschieben waere.
			char cAscii = '0'; //Das Ausgangszeichen		
		    int[] iaRotValue =   {1,  2,  3, 26, 27 }; int iRotValue;
		    char[] caExpected = {'z','y','x','a','`'};
		    
		    //Nun in einer Schleife testen
		    int iUbound = iaRotValue.length-1;
		    for(int i=0; i<=iUbound;i++) {
		    	iRotValue=iaRotValue[i];
		    	if(iRotValue==53) {
		    		System.out.println("TEST BREAKPOINT");
		    		System.out.println("TEST BREAKPOINT");
		    	}
		    	iAscii = cAscii - iRotValue;
		    	iAsciiNew = AsciiZZZ.fromNumber2LetterLowercaseReverse(iAscii);
		    	System.out.println("Index="+ i + " |abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + iAsciiNew + " | Zeichen ='" + (char)iAsciiNew + "'");
	    		assertTrue("New Ascii code", caExpected[i]==(char)iAsciiNew);	    
		    }
	 }
	 
	 public void testFromLetterLowercase2LetterUppercaseReverse(){
//		 try{
		char cAscii = 'a';
		int iBorder = AsciiTableZZZ.SectionZZZ.LETTER_UPPERCASE.getEnd();
		System.out.println("OBERE GRENZE= " + iBorder);

		int itest = (int) cAscii;
	    int itemp = AsciiZZZ.fromLetterLowercase2LetterUppercaseReverse(itest);
	    //Wenn durch den ROT -  Crypt - Algorithmus nix verschoben wird, kommt eine Zahl +1 hoeher als die obere Grenze heraus.
	    assertTrue("New Ascii code", itemp==iBorder+1 ); 

	    //Wird durch den Crypt-Algorithmus etwas verschoben (-1), dann kommt die Zahl der oberen Grenze heraus.		    
	    itest = (int) cAscii;
	    itest = itest - 1;
	    itemp = AsciiZZZ.fromLetterLowercase2LetterUppercaseReverse(itest);
	    assertTrue("New Ascii code", itemp==iBorder ); 

	    //Nun in einer Schleife testen		    
	    int iRotValue = -1;
	    for(int i=(int)cAscii; i>=-1;i--) {
	    	iRotValue++;
	    	itemp = AsciiZZZ.fromLetterLowercase2LetterUppercaseReverse(i);
	    	System.out.println("abzuziehender iRotValue= " + iRotValue + "| i= " + i + " | itemp = " + itemp + " | Zeichen ='" + (char)itemp + "'");
	    	assertTrue("New Ascii code", itemp==(iBorder-iRotValue+1));
	    	
	    	//Merke: Wenn iRotValue > 26 Zeichen ist, kommen keine Lowercase - Buchstaben mehr heraus.
	    }
		    
		  //++++++++++++++++++
//			try{
//				sTest = "AA";
//				sErg = StringZZZ.abbreviateStrictFromRight(sTest, 1);
//				fail("Method should have thrown an exception");
//			 
//			}catch(ExceptionZZZ ez){
//				//HIER WIRD EIN FEHLER ERWARTET
//			}
		    
//		 }catch(ExceptionZZZ ez){
//				fail("Method throws an exception." + ez.getMessageLast());
//		}
	 }

}//End class
