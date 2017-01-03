/* Copyright 2002 -- Ira R. Forman and Nate Forman */ 
/**
 * This class provides a set of static methods that
 * extend the Java metaobject protocol.
 *
 * @author: Ira R. Forman
 */
package basic.javareflection.mopex;

import java.lang.reflect.*;
import java.util.*;

abstract public class Mopex {


    /**
     * Returns a syntactically correct name for a class object.
     * If the class object represents an array, the proper
     * number of square bracket pairs are appended to the component type.
     *
     * @return java.lang.String
     * @param cls java.lang.Class
     */
//start extract classNameToString

    public static String getTypeName( Class cls ){
	if ( !cls.isArray() ) {
	    return cls.getName();
	} else {
	    return getTypeName( cls.getComponentType() ) + "[]";
	}
    }
//stop extract classNameToString
    
    /**
     * Returns an array of the superclasses of cls.
     *
     * @return java.lang.Class[]
     * @param cls java.lang.Class
     */
//start extract getSuperclasses
    public static Class[] getSuperclasses( Class cls ){ 
	int i = 0;
	for ( Class x = cls.getSuperclass(); x != null; x = x.getSuperclass() ) 
	    i++;
	Class[] result = new Class[i];
	i = 0;
	for ( Class x = cls.getSuperclass(); x != null; x = x.getSuperclass() )
	    result[i++] = x;
	return result;
    }
//stop extract getSuperclasses

    /**
     * Returns an array of the instance variablies of the 
     * the specified class.  An instance variable is defined
     * to be a non-static field that is declared by the class
     * or inherited.
     *
     * @return java.lang.Field[]
     * @param cls java.lang.Class
     */
//start extract getInstanceVariables
    public static Field[] getInstanceVariables(Class cls) {
	List accum = new LinkedList();
	while (cls != null) {
	    Field[] fields = cls.getDeclaredFields();
	    for (int i=0; i<fields.length; i++) {
		if (!Modifier.isStatic(fields[i].getModifiers())) {
		    accum.add(fields[i]);
		}
	    }
	    cls = cls.getSuperclass();
	}
	Field[] retvalue = new Field[accum.size()];
	return (Field[]) accum.toArray(retvalue);
    }
//stop extract getInstanceVariables

    /**
     * Returns an array of fields that are the declared 
     * instance variables of cls.  An instance variable is
     * a field that is not static.
     * 
     * @return java.lang.reflect.Field[]
     * @param cls java.lang.Class
     */
//start extract getDeclaredIVS

    public static Field[] getDeclaredIVs(Class cls) {
	Field[] fields = cls.getDeclaredFields();
	// Count the IVs
	int numberOfIVs = 0;
	for (int i = 0; i < fields.length; i++) {
	    if (!Modifier.isStatic(fields[i].getModifiers())) 
		numberOfIVs++;
	}
	Field[] declaredIVs = new Field[ numberOfIVs ];
	// Populate declaredIVs
	int j = 0;
	for (int i = 0; i < fields.length; i++) {
	    if (!Modifier.isStatic(fields[i].getModifiers())) 
		declaredIVs[j++] = fields[i];
	}
	return declaredIVs;
    }
//stop extract getDeclaredIVS

    
    /**
     * Return an array of the supported instance variables of
     * this class.  A supported instance variable is not static
     * and is either declared or inherited from a superclass.
     *
     * @return java.lang.reflect.Field[]
     * @param cls java.lang.Class
     */
//start extract getSupportedIVS
    public static Field[] getSupportedIVs(Class cls) {
        if ( cls == null ) {
	    return new Field[0];
	} else {
	    Field[] inheritedIVs = getSupportedIVs( cls.getSuperclass() );
	    Field[] declaredIVs = getDeclaredIVs( cls );
	    Field[] supportedIVs = new Field[ declaredIVs.length + inheritedIVs.length ];
	    for ( int i = 0; i < declaredIVs.length; i++ ) {
		supportedIVs[i] = declaredIVs[i];
	    }
	    for ( int i = 0; i < inheritedIVs.length; i++ ) {
		supportedIVs[i+declaredIVs.length] = inheritedIVs[i];
	    }
	    return supportedIVs;
	}
    }
//stop extract getSupportedIVS

    /**
     * Returns an array of the methods that are not static.
     *
     * @return java.lang.reflect.Method[]
     * @param cls java.lang.Class
     */
//start extract getInstanceMethods
    public static Method[] getInstanceMethods(Class cls) {
	List instanceMethods = new ArrayList();
	for ( Class c = cls;  c != null; c = c.getSuperclass() ) {
	    Method[] methods = c.getDeclaredMethods();
	    for ( int i = 0; i < methods.length; i++ )
		if (!Modifier.isStatic(methods[i].getModifiers())) 
		    instanceMethods.add(methods[i]);
	}
	Method[] ims = new Method[ instanceMethods.size() ];
	for ( int j = 0; j < instanceMethods.size(); j++ )
	    ims[j] = (Method)instanceMethods.get(j);
	return  ims;
    }
//stop extract getInstanceMethods
    
    /**
     * Returns an array of methods to which instances
     * of this class respond.
     *
     * @return java.lang.reflect.Method[]
     * @param cls java.lang.Class
     */
//start extract getSupportedMethods
    public static Method[] getSupportedMethods( Class cls ) {
	return getSupportedMethods( cls, null );
    }
//stop extract getSupportedMethods

    /**
     * This method retrieves the modifiers of a Method without the 
     * unwanted modifiers specified in the second parameter.  Because
     * this method uses bitwise operations, multiple unwanted modifiers
     * may be specified by bitwise or.
     *
     * @return int
     * @param m java.lang.Method
     * @param unwantedModifiers int
     */
//start extract getModifiersWithout

    public static int getModifiersWithout( Method m, int unwantedModifiers ) {
        int mods = m.getModifiers();
        return (mods ^ unwantedModifiers) & mods;
    }
//stop extract getModifiersWithout

    /**
     * Returns a Method that has the signature specified 
     * by the calling parameters.
     *
     * @return Method
     * @param cls java.lang.Class
     * @param name String
     * @param paramTypes java.lang.Class[]
     */
//start extract getSupportedMethod

    public static Method getSupportedMethod( Class cls, 
					     String name, 
					     Class[] paramTypes) 
	throws NoSuchMethodException 
    {
	if (cls == null) {
	    throw new NoSuchMethodException();
	}
	try {
	    return cls.getDeclaredMethod( name, paramTypes );
	}
	catch (NoSuchMethodException ex) {
	    return getSupportedMethod( cls.getSuperclass(), name, paramTypes );
	}
    }
//stop extract getSupportedMethod
    /**
     * Returns a Method array of the methods to which instances
     * of the specified respond except for those methods defined
     * in the class specifed by limit or any of its superclasses.
     * Note that limit is usually used to eliminate them methods
     * defined by java.lang.Object.
     *
     * @return Method[]
     * @param cls java.lang.Class
     * @param limit java.lang.Class
     */
//start extract getSupportedMethods
    public static Method[] getSupportedMethods( Class cls, Class limit ) {
	Vector supportedMethods = new Vector();
	for ( Class c = cls;  c != limit; c = c.getSuperclass() ) {
	    Method[] methods = c.getDeclaredMethods();
	    for ( int i = 0; i < methods.length; i++ ) {
		boolean found = false;
		for ( int j = 0; j < supportedMethods.size(); j++ )
		    if ( equalSignatures( methods[i], (Method)supportedMethods.elementAt(j) ) ) {
			found = true;
			break;
		    }
		if (!found)
		    supportedMethods.add(methods[i]);
	    }
	}
	Method[] mArray = new Method[supportedMethods.size()];
	for ( int k = 0; k < mArray.length; k++ )
	    mArray[k] = (Method)supportedMethods.elementAt(k);
	return  mArray;
    }

//stop extract getSupportedMethods

    /**
     * This field is initialized with a method object for
     * the equalSignatures method. This is an optimization
     * in that selectMethods can use this field instead of
     * calling getMethod each time it is called.
     */

//start extract equalSignaturesMethod

    static private Method equalSignaturesMethod;

    static {
	Class[] fpl = { Method.class, Method.class };
	try {
	    equalSignaturesMethod = Mopex.class.getMethod( "equalSignatures", 
							   fpl );
	} catch(NoSuchMethodException e){
	    throw new RuntimeException(e);
	}
    }
//stop extract equalSignaturesMethod
    /**
     * Determines if the signatures of two method objects are equal.
     * In Java, a signature comprises the method name and the array of
     * of formal parameter types.  For two signatures to be equal,
     * the method names must be the same and the formal parameters
     * must be of the same type (in the same order).
     *
     * @return boolean
     * @param m1 java.lang.Method
     * @param m2 java.lang.Method
     */
//start extract equalSignatures

    public static boolean equalSignatures( Method m1, Method m2 ){
	if ( !m1.getName().equals(m2.getName() ) ) return false;
	if ( !Arrays.equals( m1.getParameterTypes(), m2.getParameterTypes() ) )
	     return false;
	return true;
    }
//stop extract equalSignatures
    


    /**
     * Return a string that represents the signature of the specified method.
     *
     * @return String
     * @param m java.lang.Method
     */
//start extract signatureToString

    public static String signatureToString( Method m ){
	return m.getName() 
	    + "(" + formalParametersToString( m.getParameterTypes() ) + ")";
    }

//stop extract signatureToString
    /**
     * Returns a string that can be used as a formal parameter list
     * for a method that has the parameter types of the specified array.
     *
     * @return String
     * @param pts java.lang.Class[]
     */
//start extract formalParametersToString

    public static String formalParametersToString( Class[] pts ){
	String result = "";
	for ( int i = 0; i < pts.length; i++) {
	    result += getTypeName( pts[i] ) + " p" + i ;
	    if ( i < pts.length-1 )
		result += ",";
	}
	return result;
    }
//stop extract formalParametersToString
    /**
     * Returns a string that is an actual parameter list that matches
     * the formal parameter list produced by formalParametersToString.
     *
     * @return String
     * @param pts java.lang.Class[]
     */
//start extract actualParametersToString

    public static String actualParametersToString( Class[] pts ){
	String result = "";
	for ( int i = 0; i < pts.length; i++) {
	    result += "p" + i ;
	    if ( i < pts.length-1 )
		result += ",";
	}
	return result;
    }
//stop extract actualParametersToString

    /**
     * Returns a String that represents the header for a constructor.
     *
     * @return String
     * @param c java.lang.Constructor
     */
//start extract constructorHeaderToString

    public static String headerToString( Constructor c ){
	String mods = Modifier.toString( c.getModifiers() );
	if ( mods.length() == 0 )
	    return headerSuffixToString( c );
	else
	    return mods + " " + headerSuffixToString( c );
    }

//stop extract constructorHeaderToString
    /**
     * Returns a String that represents the header suffix for a constructor.
     * The term "header suffix" is not a standard Java term.  We use it to
     * mean the Java header without the modifiers.
     *
     * @return String
     * @param c java.lang.Constructor
     */
//start extract constructorHeaderToString
    public static String headerSuffixToString( Constructor c ) {
	String header = signatureToString( c );
	Class[] eTypes =  c.getExceptionTypes();
	if ( eTypes.length != 0 )
	    header += " throws " + classArrayToString( eTypes );
	return header;
    }

//stop extract constructorHeaderToString
    /**
     * Returns a String that represents the signature for a constructor.
     *
     * @return String
     * @param c java.lang.Constructor
     */
//start extract constructorHeaderToString
    public static String signatureToString( Constructor c ){
	return c.getName() 
	    + "(" + formalParametersToString( c.getParameterTypes() ) + ")";
    }
//stop extract constructorHeaderToString


    /**
     * Returns a String that represents the header of a method.
     *
     * @return String
     * @param m java.lang.Method
     */
//start extract headerToString

    public static String headerToString( Method m ){
	String mods = Modifier.toString( m.getModifiers() );
	if ( mods.length() == 0 )
	    return headerSuffixToString( m );
	else
	    return mods + " " + headerSuffixToString( m );
    }

//stop extract headerToString
    /**
     * Returns a String that represents the suffix of the header of a method.
     * The suffix of a header is not a standard Java term.  We use the
     * term to mean the Java header without the method modifiers.
     *
     * @return String
     * @param m java.lang.Method
     */
//start extract headerToString
    public static String headerSuffixToString( Method m ){
	String header = getTypeName( m.getReturnType() ) 
	            + " " + signatureToString( m );
	Class[] eTypes =  m.getExceptionTypes();
	if ( eTypes.length != 0 ) {
	    header += " throws " + classArrayToString( eTypes );
	}
	return header;
    }
    
//stop extract headerToString
    /**
     * Returns a String that is a comma separated list of the
     * typenames of the classes in the array pts.
     *
     * @return String
     * @param pts java.lang.Class[]
     */
//start extract classArrayToString

    public static String classArrayToString( Class[] pts ){
	String result = "";
	for ( int i = 0; i < pts.length; i++) {
	    result += getTypeName( pts[i] );
	    if ( i < pts.length-1 )
		result += ",";
	}
	return result;
    }
//stop extract classArrayToString

    /**
     * Turns true if and only if the header suffixes of the two
     * specified methods are equal. The header suffix is defined
     * to be the signature, the return type, and the exception types.
     *
     * @return boolean
     * @param m1 java.lang.Method
     * @param m2 java.lang.Method
     */
    public static boolean equalsHeaderSuffixes( Method m1, Method m2 ){
	if ( m1.getReturnType() != m2.getReturnType() ) return false;
	if ( !Arrays.equals( m1.getExceptionTypes(), m2.getExceptionTypes() ) ) 
	     return false;
	return equalSignatures(m1,m2);
    }

    /**
     * Creates constructor with the signature of c and a new name.  It adds some code
     * after generating a super statement to call c.  This method is used when generating
     * a subclass of class that declared c.
     *
     * @return String
     * @param c java.lang.Constructor
     * @param name String
     * @param code String
     */
//start extract createRenamedConstructor

    public static String createRenamedConstructor( Constructor c, 
						   String name, 
						   String code ) 
    { 
	Class[] pta = c.getParameterTypes();
	String fpl = formalParametersToString( pta );
	String apl = actualParametersToString( pta );
	Class[] eTypes =  c.getExceptionTypes();
	String result = name + "(" + fpl + ")\n";
	if ( eTypes.length != 0 )
	    result += "    throws " 
		      + classArrayToString( eTypes ) 
		      + "\n";
	result += "{\n    super(" + apl + ");\n" + code + "}\n";
	return result;
    }
//stop extract createRenamedConstructor

    /**
     * Returns a String that is formatted as a Java method declaration
     * having the same header as the specified method but with the code
     * parameter substituted for the method body.
     *
     * @return String
     * @param m java.lang.Method
     * @param code String
     */
//start extract createReplacementMethod
    public static String createReplacementMethod( Method m, String code ) { 
	Class[] pta = m.getParameterTypes();
	String fpl = formalParametersToString( pta );
	Class[] eTypes =  m.getExceptionTypes();
	String result = m.getName() + "(" + fpl + ")\n";
	if ( eTypes.length != 0 )
	    result += "    throws " + classArrayToString( eTypes ) + "\n";
	result += "{\n" + code + "}\n";
	return result;
    }
//stop extract createReplacementMethod

    /**
     * Returns a string for a cooperative override of the method m.  That is,
     * The string has the same return type and signature as m but the body
     * has a super call that is sandwiched between the strings code1 and code2.
     *
     * @return String
     * @param m java.lang.Method
     * @param code1 String
     * @param code2 String
     */
//start extract createCooperativeWrapper

    public static String createCooperativeWrapper( Method m, 
						   String code1, 
						   String code2 ) { 
	Class[] pta = m.getParameterTypes();
	Class retType = m.getReturnType();
	String fpl = formalParametersToString( pta );
	String apl = actualParametersToString( pta );
	Class[] eTypes =  m.getExceptionTypes();
	String result 
	    = retType.getName() + " " + m.getName() + "(" + fpl + ")\n";
	if ( eTypes.length != 0 )
	    result += "    throws " + classArrayToString( eTypes ) + "\n";
	result += "{\n" + code1 + "    ";
	if (retType != void.class)
	    result += retType.getName() + " cooperativeReturnValue = ";
	result += "super." + m.getName() + "(" + apl + ");\n";
	result += code2;
	if (retType != void.class)
	    result += "    return cooperativeReturnValue;\n";
	result += "}\n";
	return result;
    }
//stop extract createCooperativeWrapper

    /**
     * Returns all of the interfaces that cls inherits.
     * There are no duplicates. The order of the returned
     * array is that of a breadth-first search.
     *
     * @return Class[]
     * @param cls java.lang.Class
     * @param limit java.lang.Class
     */
//start extract getAllInterfaces

    public static Class[] getAllInterfaces( Class cls, Class limit ) {
     
    	main:{
    //Das geht erst ab Version 1.4: Ist der Originalcode:   assert (	limit == null || (!limit.isInterface() && !limit.isPrimitive()));
    if (!(limit==null || (!limit.isInterface() && !limit.isPrimitive()))) return null;
 
    
	UQueue cq = new UQueue(Class.class);
	if ( cls.isInterface() )
	    cq.add( cls );
	for (Class x = cls; x != null && x != limit; x = x.getSuperclass())
	    getInterfaceSubtree( x, cq );
	return (Class[])cq.toArray();
    	}
    }

//stop extract getAllInterfaces
    public static Class[] getAllInterfaces( Class cls ) {
	return getAllInterfaces( cls, null );
    }
    
    /**
     * Adds to cq all the interfaces in the subtree above cls.
     * Because cq is a UQueueOfClass, duplicates are eliminated by the add
     * operations.
     *
     * @param cls java.lang.Class
     * @param cq UQueue
     */
//start extract getAllInterfaces
    private static void getInterfaceSubtree( Class cls, 
					     UQueue cq ) {
	Class[] iArray = cls.getInterfaces();
	for ( int j = 0; j < iArray.length; j++ ) {
	    cq.add( iArray[j] );
	    getInterfaceSubtree( iArray[j], cq );
	}
    }
//stop extract getAllInterfaces

    /**
     * Returns the method object for the unique method named mName.
     * If no such method exists, a null is returned.
     * If there is more than one such method, a runtime exception is thrown.
     *
     * @return Method
     * @param cls java.lang.Class
     * @param mName String
     */
    public static Method getUniquelyNamedMethod( Class cls, String mName ) {
	Method result = null;
	Method[] mArray = cls.getDeclaredMethods();
	for ( int i = 0; i < mArray.length; i++ )
	    if ( mName.equals( mArray[i].getName() ) ) {
		if ( result == null )
		    result = mArray[i];
		else
		    throw new RuntimeException( "name is not unique" );
	    }
	return result;
    }

    /**
     * Finds the first (from the bottom of the inheritance hierarchy) field 
     * with the specified name. Note that Class.getField returns only 
     * public fields.
     *
     * @return Field
     * @param cls java.lang.Class
     * @param name String
     */
//start extract findField

    public static Field findField( Class cls, String name ) 
	                           throws NoSuchFieldException {
	if ( cls != null ) {
	    try {
		return cls.getDeclaredField( name );
	    } catch(NoSuchFieldException e){
		return findField( cls.getSuperclass(), name );
	    }
	} else {
	    throw new NoSuchFieldException();
	}
    }
//stop extract findField

    /**
     * Produces an array of all the fields in a class, each of which has all of the 
     * modifiers indicated by the int parameter mustHave and none of the modifiers indicated
     * by the int parameter mustNotHave. Note that "all the fields in a class" means the
     * class and all of its superclasses. For example, selectFields(x,0,Modifier.FINAL)
     * returns all the fields that are not final.
     *
     * @return Field[]
     * @param cls java.lang.Class
     * @param mustHave int
     * @param mustNotHave int
     */
//start extract selectFields
    public static Field[] selectFields( Class cls, 
					int mustHave, 
					int mustNotHave ) {
	UQueue fq = new UQueue(Field.class);
	Class[] ca = selectAncestors(cls,0,0);
	for ( int j = 0; j < ca.length; j++ ){
	    Field[] fa = ca[j].getDeclaredFields();
	    for ( int i = 0; i < fa.length; i++ ) {
		int mods = fa[i].getModifiers();
		if ( ((mods & mustHave) == mustHave) 
		     && ((mods & mustNotHave) == 0) )
		    fq.add( fa[i] );
	    }
	}
	return (Field[])fq.toArray();
    }
//start extract selectFields

    /**
     * Produces an array of all the methods in a class, each of which has all of the 
     * modifiers indicated by the int parameter mustHave and none of the modifiers indicated
     * by the int parameter mustNotHave. Note that "all the methods in a class" means the
     * class and all of its superclasses. In addition, selectMethod returns only one entry
     * for any specific signature.
     *
     * For example, selectMethods(x,Modifier.PUBLIC,0)
     * should return the same result as x.getMethods() (is this true? selectMethods eliminates
     * duplicates).
     * 
     * IMPORTANT NOTE: selectMethods is not sensitive to semantic issues.  For example, in
     *                    selectMethod( cls, Modifier.ABSTRACT, 0 )
     * if a method is declared abstract by a superclass of cls 
     * and subsequently defined by cls, that method appears in the returned array.
     * @see getMethodsLackingImplementation.
     *
     * @return Method[]
     * @param cls java.lang.Class
     * @param mustHave int
     * @param mustNotHave int
     */
//start extract selectMethods

    public static Method[] selectMethods( Class cls, 
					  int mustHave, 
					  int mustNotHave ) {
	return (Method[])selectMethods0( cls, 
					 mustHave, 
					 mustNotHave, 
					 null ).toArray();
    }

    public static Method[] selectMethods( Class cls, 
					  int mustHave, 
					  int mustNotHave, 
					  Class limit ) {
	return (Method[])selectMethods0( cls, 
					 mustHave, 
					 mustNotHave, 
					 limit ).toArray();
    }

    private static UQueue selectMethods0( Class cls, 
					  int mustHave, 
					  int mustNotHave, 
					  Class limit ) {
	UQueue mq = new UQueue( Method.class, equalSignaturesMethod );
	Class[] ca = selectAncestors(cls,0,0,limit);
	for ( int j = 0; j < ca.length; j++ ){
	    Method[] ma = ca[j].getDeclaredMethods();
	    for ( int i = 0; i < ma.length; i++ ) {
		int mods = ma[i].getModifiers();
		if ( ((mods & mustHave) == mustHave) 
		     && ((mods & mustNotHave) == 0) )
		    mq.add( ma[i] );
	    }
	}
	return mq;
    }
//stop extract selectMethods

    /**
     * Returns an array of all of the abstract methods of class cls.
     * A method that is declared abstract in a superclass or superinterface
     * of cls is not abstract if an implementation has been provided for
     * the method.
     *
     * Note that the implementation uses UQueueOfMethod, which is 
     * generated by the C2C framework. The UQueueOfMethod is constructed
     * with a Method object for the equality test used by the queue.
     *
     * @return Method[]
     * @param cls java.lang.Class
     */

//start extract getMethodsLackingImplementation
    public static Method[] getMethodsLackingImplementation( Class cls ) {
	UQueue imq = selectMethods0( cls, 
				     0, 
				     Modifier.ABSTRACT, 
				     null );
	UQueue amq = selectMethods0( cls,
				     Modifier.ABSTRACT, 
				     0, 
				     null );
	UQueue rmq = new UQueue( Method.class, equalSignaturesMethod );
	for ( int i = 0; i < amq.size(); i++ ){
	    Method rm = (Method)amq.elementAt(i);
	    if ( !imq.contains(rm) )
		rmq.add( rm );
	}
	return (Method[])rmq.toArray();
    }
//stop extract getMethodsLackingImplementation


    /**
     * Returns an array of all the ancestor classes and interfaces of cls.  
     * The class cls is first.  The array is a topological sort.
     *
     * @return Class[]
     * @param cls java.lang.Class
     * @param mustHave int
     * @param mustNotHave int
     */
//start extract selectAncestors

    public static Class[] selectAncestors( Class cls, 
					   int mustHave, 
					   int mustNotHave ) {
	return selectAncestors( cls, mustHave, mustNotHave, null );
    }

//stop extract selectAncestors
    /**
     * Returns an array of all the ancestor classes and interfaces of cls.  
     * The class cls is first.  The array is a topological sort. 
     * The search of superclasses stop at the limit. If the limit
     * is not a superclass of cls, this method fails with a NullPointerException.
     *
     * @return Class[]
     * @param cls java.lang.Class
     * @param mustHave int
     * @param mustNotHave int
     * @param limit java.lang.Class
     */
//start extract selectAncestors
    public static Class[] selectAncestors( Class cls, 
					   int mustHave, 
					   int mustNotHave, 
					   Class limit ) {
	UQueue cq = new UQueue(Class.class);
	if ( !cls.isInterface() ) {
	    for ( Class x = cls; x != limit; x = x.getSuperclass() ){
		int mods = x.getModifiers();
		if ( ((mods & mustHave) == mustHave) 
		     && ((mods & mustNotHave) == 0) )
		    cq.add( x );
	    }
	}
	Class[] ca = getAllInterfaces( cls, limit );
	for ( int i = 0; i < ca.length; i++ ) {
	    int mods = ca[i].getModifiers();
	    if ( ((mods & mustHave) == mustHave) 
		 && ((mods & mustNotHave) == 0) )
		cq.add( ca[i] );
	}
	return (Class[])cq.toArray();
    }
//stop extract selectAncestors

    /**
     * Returns the Method object for the first declaration of the method
     * the signature matching the specified name and fpl.  
     * The search order has classes preceed interfaces.
     * This means that if the method object returned is abstract,
     * no implementation is defined for the method.
     * If no match is found, null is returned.
     *
     * @return Method
     * @param cls java.lang.Class
     * @param name String
     * @param fpl java.lang.Class[]
     */
//start extract getMethod
    public static Method getMethod( Class cls, String name, Class[] fpl ) {
	for ( Class c = cls;  c != null; c = c.getSuperclass() ) {
	    try {
		return c.getDeclaredMethod( name, fpl );
	    } catch (NoSuchMethodException e) {}
	}
	Class[] ca = getAllInterfaces(cls);
	for ( int i = 0; i < ca.length; i++ ) {
	    try {
		return ca[i].getDeclaredMethod( name, fpl );
	    } catch (NoSuchMethodException e) {}
	}
	return null;
    }
//stop extract getMethod

/* End of Mopex */}
