package basic.zBasic.util.datatype.calling;

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
public class ReferenceZZZ<T> {
    private T referent;

    public ReferenceZZZ(T initialValue) {
       referent = initialValue;
    }

    public void set(T newVal) {
       referent = newVal;
    }

    public T get() {
       return referent;
    }
}
