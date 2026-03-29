package basic.zBasic.util.abstractList;



import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;


public class VectorLimitedZZZTest  extends TestCase{
    private VectorLimitedZZZ<String> vec = null;
	
    protected void setUp(){
    	try {
    		vec = new VectorLimitedZZZ<String>(4);
    	} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    }//END setup
    
    public void testAdd(){
//		try {
			//neue
    		vec.add("1");
    		vec.add("2");
    		vec.add("3");
    		vec.add("4");
    		assertEquals(4,vec.size()); //Dieser Vektor soll immer 4 Elemente haben
    		
    		vec.add("5"); //es wird keine Exception geworfen, sondern einfach ignoriert.
    		assertEquals(4,vec.size()); //Dieser Vektor soll immer 4 Elemente haben
    		
    		
			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 
    	
    }
    
   
}
