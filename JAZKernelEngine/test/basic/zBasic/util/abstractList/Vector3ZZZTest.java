package basic.zBasic.util.abstractList;



import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;


public class Vector3ZZZTest  extends TestCase{
    private Vector3ZZZ<String> vec = null;
	
    protected void setUp(){
    	try {
    		vec = new Vector3ZZZ<String>();
    	} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    }//END setup
    
    public void testReplace_1(){
		try {
			//neue
    		vec.replace("neu");
    		
    		assertEquals(3,vec.size()); //Dieser Vektor soll immer 3 Elemente haben
    		assertEquals(null, vec.getValueOrString(0));
    		assertEquals("neu", vec.getValueOrString(1));
    		assertEquals(null,vec.getValueOrString(2));
    		
    		vec.add("4");
    		assertEquals(3,vec.size()); //Dieser Vektor soll immer 3 Elemente haben
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    	
    }
    
    public void testReplace_1_nullable(){
  		try {
  			//neue
      		vec.replace(null);
      		
      		assertEquals(null, vec.getValueOrString(0));
    		assertEquals(null, vec.getValueOrString(1));
    		assertEquals(null, vec.getValueOrString(2));
    		
    		vec.add("4");
    		assertEquals(3,vec.size()); //Dieser Vektor soll immer 3 Elemente haben
			      		  			
  		} catch (ExceptionZZZ ez) {
  			fail("Method throws an exception." + ez.getMessageLast());
  		} 
      	
      }
    
    public void testReplace_3(){
		try {
			//neue
    		vec.replace("1","neu","3");
    		
    		assertEquals(3,vec.size()); //Dieser Vektor soll immer 3 Elemente haben
    		assertEquals("1", vec.getValueOrString(0));
    		assertEquals("neu", vec.getValueOrString(1));
    		assertEquals("3",vec.getValueOrString(2));
    		
    		vec.add("4");
    		assertEquals(3,vec.size()); //Dieser Vektor soll immer 3 Elemente haben
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    	
    }
    
    public void testReplace_3_nullable(){
		try {
			//neue
    		vec.replace("1",null,"3");
    		
    		assertEquals(3,vec.size()); //Dieser Vektor soll immer 3 Elemente haben
    		assertEquals("1", vec.getValueOrString(0));
    		assertEquals(null, vec.getValueOrString(1));
    		assertEquals("3",vec.getValueOrString(2));
    		
    		vec.add("4");
    		assertEquals(3,vec.size()); //Dieser Vektor soll immer 3 Elemente haben
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    	
    }
    
    
   
    
    
}
