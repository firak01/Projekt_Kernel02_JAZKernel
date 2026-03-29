//start extract UQueue
package basic.javareflection.mopex;
import java.util.*;
import java.lang.reflect.*;

public class UQueue {

//============= F I E L D S ======================
    private List   myList = new ArrayList();
    private Object eltArray;
    private Class  eltType;
    private Method equalsMethod = null;

//============= C O N S T R U C T O R S ==========
    public UQueue( Class eltType ) {                                //#1
	this.eltType = eltType;                                     //#1
  	eltArray = Array.newInstance( eltType, 0 );                 //#1
    }

    public UQueue( Class eltType, Method m ) {                      //#2
        Class[] fpl = m.getParameterTypes();                        //#2
        if (!(Modifier.isStatic(m.getModifiers())                   //#2
              && m.getReturnType() == boolean.class                 //#2
              && fpl[0] == eltType                                  //#2
              && fpl[1] == eltType                                  //#2
              && fpl.length == 2))                                  //#2
                throw new RuntimeException("illegal signature");    //#2
        equalsMethod = m;                                           //#2
	this.eltType = eltType;                                     //#2
  	eltArray = Array.newInstance( eltType, 0 );                 //#2
   }

//============= M E T H O D S ====================
    public boolean isEmpty()          { return myList.size()==0 ; }
    public int     size()             { return myList.size(); }
    public Object  remove()           { return myList.remove(0); }
    public Object  elementAt( int i ) { return myList.get(i); }

    public UQueue add( Object element ) {                           //#3
	if ( !eltType.isInstance( element ) )                       //#3
                throw new RuntimeException("illegal arg type");     //#3
        if (!contains(element))                                     //#3
            myList.add(element);                                    //#3
        return this;                                                //#3
    }

    public boolean contains( Object obj ) {                              //#4
        if ( equalsMethod == null ) {                                    //#4
             return myList.contains(obj);                                //#4
        } else {                                                         //#4
            for ( int i = 0; i < myList.size(); i++ ) {                  //#4
                try {                                                    //#4
                    Object[] apl = {obj,myList.get(i)};                  //#4
                    Boolean rv = (Boolean)equalsMethod.invoke(obj,apl);  //#4
                    if ( rv.booleanValue() )                             //#4
                        return true;                                     //#4
                } catch (Exception e){                                   //#4
                    throw new RuntimeException(e);                       //#4
                }                                                        //#4
            }                                                            //#4
            return false;                                                //#4
        }
    }

    public Object[] toArray() {                                    //#5
	return myList.toArray( (Object[])eltArray );               //#5
    }                                                              //#5

    public String toString( String separator ) {                   //#6
        String result = "";                                        //#6
        for ( int i = 0; i < myList.size(); i++ ) {                //#6
            result += myList.get(i);                               //#6
            if ( i < myList.size()-1 )                             //#6
                result += separator;                               //#6
        }                                                          //#6
        return result;                                             //#6
    }
}
//stop extract UQueue
