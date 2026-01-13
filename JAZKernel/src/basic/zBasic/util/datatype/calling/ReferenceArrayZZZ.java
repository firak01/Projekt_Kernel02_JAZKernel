package basic.zBasic.util.datatype.calling;

import java.lang.reflect.Array;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
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
public class ReferenceArrayZZZ<T> {
    private ArrayList<T> listaReferent=new ArrayList<T>();

    public ReferenceArrayZZZ() {    	
    }
    
    public ReferenceArrayZZZ(T initialValue) {
    	if(initialValue!=null) {
    		if(initialValue.getClass().isArray()) {
    			//listaReferent.addAll(initialValue); GEHT NICHT SO
    			
    			int iMax = Array.getLength(initialValue)-1;
    			for(int i=0; i<=iMax;i++) {
    				Object objTemp = Array.get(initialValue, i);
    				listaReferent.add((T) objTemp);
    			}
    		}else {
    			listaReferent.add(initialValue);
    		}
    	}    	
    }

    public void add(T newVal) {
       listaReferent.add(newVal);
    }
    
    public void add(T[] newVal) {
    	main:{
    		if(ArrayUtilZZZ.isNull(newVal)) break main;

    		for(T elementVal : newVal) {
    			listaReferent.add(elementVal);
    		}
    	}
     }
   
    
    public void set(T[] newArray) {
    	listaReferent.clear();
    	int iMax = Array.getLength(newArray)-1;
		for(int i=0; i<=iMax;i++) {
			Object objTemp = Array.get(newArray, i);
			listaReferent.add((T) objTemp);
		}
    }
    
    public void set(ArrayList<T> listaReferent) {
    	this.listaReferent = listaReferent;
    }

    public T[] get() throws ExceptionZZZ {
    	//Wg. cannot Cast... Fehler hier ggfs. auf die InstanceOf mit meiner heuristischen Loesung prufen
    	if(ArrayListUtilZZZ.isInstanceOf(listaReferent, String.class)) {
    		return this.getAsString();
    	}else {
    		return (T[]) ArrayListUtilZZZ.toArray(listaReferent);//!!! GEFÄHRLICH !!! FRAGLICH !!! listaReferent.toArray(T);
    	}
    }
    
    public T[] getAsString() throws ExceptionZZZ {
    	return (T[]) ArrayListUtilZZZ.toStringArray(listaReferent);
    }
    
    public ArrayList<T> getArrayList(){
    	return this.listaReferent;
    }
}
