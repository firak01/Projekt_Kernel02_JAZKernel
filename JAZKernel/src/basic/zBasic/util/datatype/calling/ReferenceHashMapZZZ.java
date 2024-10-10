package basic.zBasic.util.datatype.calling;



import java.util.HashMap;

import basic.zBasic.util.abstractList.ArrayListUtilZZZ;

/*
 * https://stackoverflow.com/questions/7884581/how-can-i-simulate-pass-by-reference-in-java
 * 
 * Also: Da Java nur Call-By-Value in den Methodenaufrufen machen kann, 
 *       besteht ein Workaround darin die Werte in ein Objekt zu schreiben und diese hier auszulesen.
 *       
 *       z.B: 
 *      Reference<Integer> myInt = new Reference<>(4);
  		makeAThree(myInt);
  		System.out.println(myInt.get());
 *      
 *      Wenn in der makeAThree Methode ein einfaches Integer Objekt übergeben worden wäre, dann wäre der Wert in der aufrufenden Methode unverändert.
 *      Durch die Übergabe einer Objektreferenz wird dieses "Call by value" umgangen. Änderungen an dem Objekt myInt können in der aufrufenden Methode gelesen werden.  
 *       
 */
public class ReferenceHashMapZZZ<T,O> {
    private HashMap<T,O> hm =new HashMap<T,O>();

    public ReferenceHashMapZZZ() {    	
    }
    
    public ReferenceHashMapZZZ(HashMap<T,O> initialValue) {
    	if(initialValue!=null) {
    		this.hm = initialValue;
    	}    	
    }

  
    public void set(HashMap<T,O> initialValue) {
    	if(initialValue!=null) {
    		this.hm = initialValue;
    	}  
    }

    public HashMap<T,O> get() {
       return this.hm;
    }     
}
