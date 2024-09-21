package basic.zBasic.util.abstractList;

import java.util.Vector;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;

public class VectorZZZTest  extends TestCase{
    private Vector vec = null;
	
    protected void setUp(){
		
		vec = new Vector();
		vec.add("a");
		vec.add("b");
		vec.add("c");
	
}//END setup

    public void testTrim(){
		try {
			//1. unver�ndert
			Vector vecNew = VectorUtilZZZ.unique(vec);
			assertEquals(vec.size(), vecNew.size());
	    	
			
			//2. "doppelte Eintr�ge"
			vec.add("a");
			Vector vecNew2 = VectorUtilZZZ.unique(vec);
			assertEquals(vec.size() - 1 , vecNew2.size());
			
			vec.add("c");
			Vector vecNew3 = VectorUtilZZZ.unique(vec);
			assertEquals(vec.size() - 2 , vecNew3.size());
			
			Vector vec4Append = new Vector();
    		vec4Append.add("a");
    		vec4Append.add("c");
    		Vector vecNew4temp = VectorUtilZZZ.append(vec, vec4Append);
			Vector vecNew4 = VectorUtilZZZ.unique(vecNew4temp);
			assertEquals(vec.size() - 2, vecNew4.size());
    		
    		
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    	
    }
    
    public void testAppend(){
    	try{
    		Vector vec2Append = new Vector();
    		vec2Append.add("d");
    		vec2Append.add("e");
    		
    		Vector vecNew = VectorUtilZZZ.append(vec, vec2Append);
    		assertEquals(vecNew.size(), vec.size() + vec2Append.size());
    		
    		
    		
    	} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    }
    
    public void testReverse(){
    	try{
    		Vector vec = new Vector();
    		vec.add("a");
    		vec.add("b");
    		vec.add("c");
    		vec.add("d");
    		
    		Vector vecNew = VectorUtilZZZ.reverse(vec);
    		assertEquals(vecNew.size(), vec.size() );
    		assertEquals(vecNew.get(0), vec.get(vec.size()-1));
    		assertEquals(vecNew.get(vecNew.size()-1), vec.get(0));
    		
    		
      	} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
    }
}
