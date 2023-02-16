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

	 
	 public void testFromLetterLowercaseReverse(){
//		 try{
		char cAscii = 'b';
		int iBorder = AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getStart();
		System.out.println("OBERE GRENZE= " + iBorder);

	    //Nun in einer Schleife testen	
		int itemp; 
		TODOGOON20230216;
	    int[] iaRotValue = {1,2,3,4,26,27,28}; int iRotValue;
	    char[] caExpected = {'b','a','Z','y','B','A','9'};
	    
	    for(int i=0; i>=2;i++) {
	    	iRotValue=iaRotValue[i];
	    	itemp = AsciiZZZ.fromLetterLowercaseReverse(cAscii, iRotValue);
	    	System.out.println("abzuziehender iRotValue= " + iRotValue + "| iAsciiNew= " + i + " | itemp = " + itemp + " | Zeichen ='" + (char)itemp + "'");
    		assertTrue("New Ascii code", caExpected[i]==(char)itemp);	    
	    }
	 }
	
	 public void testFromNumber2LetterLowercaseReverse(){
//		 try{
		char cAscii = '0';
		int iBorder = AsciiTableZZZ.SectionZZZ.LETTER_LOWERCASE.getEnd();
		System.out.println("OBERE GRENZE= " + iBorder);

		int itest = (int) cAscii;
	    int itemp = AsciiZZZ.fromNumber2LetterLowercaseReverse(itest);
	    //Wenn durch den ROT -  Crypt - Algorithmus nix verschoben wird, kommt eine Zahl +1 hoeher als die obere Grenze heraus.
	    assertTrue("New Ascii code", itemp==iBorder+1 ); 

	    //Wird durch den Crypt-Algorithmus etwas verschoben (-1), dann kommt die Zahl der oberen Grenze heraus.		    
	    itest = (int) cAscii;
	    itest = itest - 1;
	    itemp = AsciiZZZ.fromNumber2LetterLowercaseReverse(itest);
	    assertTrue("New Ascii code", itemp==iBorder ); 

	    //Nun in einer Schleife testen		    
	    int iRotValue = -1;
	    for(int i=(int)cAscii; i>=-1;i--) {
	    	iRotValue++;
	    	itemp = AsciiZZZ.fromNumber2LetterLowercaseReverse(i);
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
