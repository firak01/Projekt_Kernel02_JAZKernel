package base.reflection;
//package de.his.core.base.reflection;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.WeakHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.reflect.MethodSignature;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

//FGL 20180216: Rausgenommen beim Reengineering
//import de.his.appserver.service.dto.base.DtoBase;






//import de.his.core.base.invariants.EnsureArgument;
import base.invariants.EnsureArgument;
//import de.his.core.base.strings.StringUtil;
import base.strings.StringUtil;

//import de.his.core.base.tuples.Pair;
import base.datatype.tuples.Pair;
//import de.his.core.base.xml.XMLUtil;
import base.xml.XMLUtil;

//import de.his.core.common.datetime.DateUtil;
import base.common.datetime.DateUtil;


/**
 * Methoden fuer Java-Reflection
 *
 * @author Brummermann
 */
public class ReflectionUtil {
	//FGL: Herausgenommen wg. Java 1.7 private static final Map<String, Iterable<Field>> _ALL_FIELDS = new WeakHashMap<>();
	private static final Map<String, Iterable<Field>> _ALL_FIELDS = new WeakHashMap<String, Iterable<Field>>(); //Eclipse Lösungsvorschlag: Insert Inferred Type arguments

	//FGL: Herausgenommen wg. Java 1.7 public static final Map<String, Method> _ALL_METHODS = new WeakHashMap<>();
	public static final Map<String, Method> _ALL_METHODS = new WeakHashMap<String, Method>();//Eclipse Lösungsvorschlag: Insert Inferred Type arguments

    private static final List<String> globalUnneededValues = Arrays.asList("Class");

    public static final Logger logger = Logger.getLogger(ReflectionUtil.class);


    /**
     * Instantiiert ein Objekt der gewuenschten Klasse ueber den Konstruktor
     * mit den gegebenen Parametern.
     *
     * @param <T>
     * @param cl Class-Objekt fuer die gewuenschte Klasse
     * @param argClasses String-Array mit den Namen der Konsteruktor-Parameter
     * @param args Array mit den Konstruktor-Parametern
     * @return Erzeugtes Objekt
     * @throws ClassNotFoundException wenn die Klasse mit dem gewuenschten Namen nicht existiert.
     * @throws NoSuchMethodException wenn der Konstruktor mit den gegebenen Argument-Typen nicht existiert.
     * @throws InvocationTargetException wenn der Konstruktor eine Exception wirft.
     * @throws InstantiationException wenn die Klasse abstrakt ist und deshalb nicht instantiiert werden darf.
     * @throws IllegalAccessException wenn der Konstruktor nicht sichtbar ist.
     */
    public static <T> T createObject(Class<T> cl, Class<?>[] argClasses, Object[] args)
                    throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        int nofArgs0 = args != null ? args.length : 0;
        int argClassesLength = argClasses != null ? argClasses.length : 0;
        if (argClassesLength != nofArgs0) {
            throw new IllegalArgumentException("Anzahl der Argumente ist ungleich der Anzahl der Argument-Beschreibungen!");
        }

        // Konstruktor-Objekt erzeugen:
        // Wenn es keinen solchen Konstruktor gibt, wird eine NoSuchMethodException geworfen...
        // läßt sich ohne Cast auf -> Class[] nicht übersetzen.
        final Constructor<T> constructor = cl.getConstructor(argClasses);

        // Instanziieren mit Konstruktor-Argumenten:
        return constructor.newInstance(args);
    }

    /**
     * Erstellt ein Object der angegebenen Klasse ueber den Default-Konstruktor ohne Parameter.
     *
     * @param className     Name der Klasse
     * @return Erzeugtes Object oder null, wenn ein Fehler aufgetreten ist.
     */
    public static Object createObject(String className) {
        return createObject(className, (Class<?>) null);
    }

    /**
     * Erstellt ein Object der angegebenen Klasse ueber den Default-Konstruktor ohne Parameter
     * und stellt sicher, dass die angegebene Klasse das Interface implementiert.
     *
     * @param className     Name der Klasse
     * @param iface Class des zu implementierenden Interfaces
     * @return Erzeugtes Object oder null, wenn ein Fehler aufgetreten ist.
     */
    public static <T> T createObject(String className, Class<T> iface) {
        final Class<T> moduleClass = findClass(className);
        if (moduleClass == null) {
            return null;
        }
        if (iface != null && !iface.isAssignableFrom(moduleClass)) {
            logger.error("Instantiierung von " + className + " fehlgeschlagen: ist kein " + iface.getName());
            return null;
        }
        try {
            return moduleClass.newInstance();
          //FGL: Erst ab Java 1.7
          //Multi-catch parameters are not allowed for source level below 1.7
//        } catch (InstantiationException | IllegalAccessException e) {
//            logger.error("Instantiierung von " + className + " fehlgeschlagen.", e);
//            return null;
//        }
        } catch (InstantiationException  e) {
            logger.error("Instantiierung von " + className + " fehlgeschlagen.", e);
            return null;
        } catch (IllegalAccessException e) {
            logger.error("Instantiierung von " + className + " fehlgeschlagen.", e);
            return null;
        }
    }

    public static <T> T createObjekt(String theClass, Class<T> superclass) {
        final String className = stripAccidentalClassSuffix(theClass);
        try {
            final Class<?> moduleClass = Class.forName(className);
            if (superclass.isAssignableFrom(moduleClass)) {
                @SuppressWarnings("unchecked")
                T newInstance = (T) moduleClass.newInstance();
                return newInstance;
            }
          //FGL: Erst ab Java 1.7
          //Multi-catch parameters are not allowed for source level below 1.7
//        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException e1) {
//            // just return null
//        }
        } catch (final ClassNotFoundException e) {
            // just return null
        } catch (final InstantiationException e) {
            // just return null
        } catch (final IllegalAccessException e) {
            // just return null
        }
        return null;
    }

    /**
     * Erzeugt ein spezialisiertes Object der angegebenen Klasse basierend
     * auf den Daten aus einem String-Objekt. Dazu muss die Klasse einen
     * Konstruktor mit einem String-Parameter haben. Primitive Datentypen
     * werden in Objekttypen gewrappt.
     *
     */
    public static Object createSpecializedObjectByString(Class<?> clazz, String value)
                    throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        logger.debug(clazz);
        if (clazz.isPrimitive()) {
            return PrimitivesUtil.createSpecializedPrimitiveByName(clazz.getSimpleName(), value);
        }
        if ("Date".equals(clazz.getSimpleName())) {
            return DateUtil.parseISO2014Date(value);
        }
        if ("Element".equals(clazz.getSimpleName())) {
            return XMLUtil.parseXML(value);
        }
        final Class<?>[] paramTypes = new Class<?>[] { String.class };
        final Object paramValues[] = new Object[] { value };
        return ReflectionUtil.createObject(clazz, paramTypes, paramValues);
    }

    /**
     * Sucht alle zu den Parametern passende Methoden.
     * @param className
     * @param methodName
     *
     * @return Method oder null
     * @throws ClassNotFoundException
     */
    public static List<Method> findAllMethodsForMethodName(String className, String methodName) throws ClassNotFoundException {
        final Class<?> clazz = Class.forName(className);
        return findAllMethodsForMethodName(clazz, methodName);
    }

    private static List<Method> findAllMethodsForMethodName(final Class<?> clazz, String methodName) {
    	//FGL: Herausgenommen wg. Java 1.7 final List<Method> matchedMethods = new ArrayList<>();
    	 final List<Method> matchedMethods = new ArrayList<Method>(); //Eclipse Lösungsvorschlag: Insert Inferred Type arguments
        for (final Method method : ReflectionMethodCache.getOrderedMethodsFor(clazz)) {
            if (method.getName().equals(methodName)) {
                matchedMethods.add(method);
            }
        }
        return matchedMethods;
    }

    /**
     * sucht eine Klasse anhand ihres Namens
     *
     * @param rawClassName qualifizierter Klassenname mit oder ohne Suffix ".class".
     * @return die Klasse oder null, wenn ein Fehler aufgetreten ist.
     **/
    @SuppressWarnings("unchecked")
    public static <T> Class<T> findClass(String rawClassName) {
        final String className = stripAccidentalClassSuffix(rawClassName);
        try {
            return (Class<T>) Class.forName(className);
        } catch (final ClassNotFoundException e1) {
            logger.error("Klasse " + className + " nicht gefunden.", e1);
            return null;
        }
    }

    /**
     * Durchsucht alle Interfaces einer Klasse nach einer Methode mit dem Namen methodName
     * und liefert die Klasse des ersten gefundenen Interfaces zurück.
     */
    public static List<Class<?>> findInterfacesWithMethod(Class<?> clazz, String methodName) {
    	//FGL: Erst ab Java 1.7  final List<Class<?>> result = new LinkedList<>();
    	final List<Class<?>> result = new LinkedList<Class<?>>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        findInterfacesWithMethod(clazz, methodName, result);
        return result;
    }

    /**
     * Rekursive Worker-Funktion
     *
     */
    private static void findInterfacesWithMethod(Class<?> clazz, String methodName, List<Class<?>> result) {
        final Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> interfaze : interfaces) {
            final Method[] methods = interfaze.getDeclaredMethods(); // Keine vererbten Interface-Methoden
            for (final Method method : methods) {
                if (method.getName().equals(methodName)) {
                    if (!result.contains(interfaze)) {
                        result.add(interfaze);
                    }
                    break;
                }
            }
        }

        for (final Class<?> interface1 : interfaces) {
            findInterfacesWithMethod(interface1, methodName, result);
        }

        if (!clazz.isInterface() && clazz.getSuperclass() != null) {
            findInterfacesWithMethod(clazz.getSuperclass(), methodName, result);
        }
    }

    /**
     * Durchsucht alle Interfaces einer Klasse nach einer Methode mit dem Namen methodName
     * und liefert die Klasse des ersten gefundenen Interfaces zurück.
     *
     * @param clazz
     * @param methodName
     * @return Klasse des Interface, dass eine Methode mit dem Namen methodName beinhaltet
     */
    public static Class<?> findInterfaceWithMethod(Class<?> clazz, String methodName) {
        final Class<?>[] interfaces = clazz.getInterfaces();
        for (final Class<?> interface1 : interfaces) {
            final Method[] methods = interface1.getDeclaredMethods(); // Keine vererbten Interface-Methoden
            for (final Method method : methods) {
                if (method.getName().equals(methodName)) {
                    return interface1;
                }
            }

            final Class<?> ret = findInterfaceWithMethod(interface1, methodName);
            if (ret != null) {
                return ret;
            }
        }

        if (!clazz.isInterface() && clazz.getSuperclass() != null) {
            return findInterfaceWithMethod(clazz.getSuperclass(), methodName);
        }
        return null;
    }

    private static Pair<Method, Object[]> findMatchingMethod(Class<?> theClass, String methodName, Object[] providedParamsRaw, boolean includeNonPublicMethods,
                                                             boolean allowConversion, boolean exceptionIfAmbiguous)
                    throws NoSuchMethodException, AmbiguousMethodException {
        final Object[] providedParameters = ArrayUtils.nullToEmpty(providedParamsRaw);

        // Methode mit dem Namen und richtiger Signatur finden.
        Method theMethod = null;
        Object[] theConvertedParams = null;
        for (final Method method : getMethods(theClass, includeNonPublicMethods)) {
            if (!method.getName().equals(methodName)) {
                continue;
            }
            final Class<?>[] methodParameterTypes = method.getParameterTypes();
            if (methodParameterTypes.length != providedParameters.length) {
                continue;
            }
            final Object[] params_ = providedParameters.clone();
            if (!isMethodCompatible(allowConversion, providedParameters, methodParameterTypes, params_)) {
                continue;
            }
            if (!includeNonPublicMethods && !methodIsPublic(method)) {
                continue;
            }
            if (theMethod != null) {
                // es wurde bereits eine passende Methode gefunden!
                // gebe Warnmeldung aus und liefere erste gefundene Methode zurück.
                final String exc = "Es wurden mehrere kompatible Methoden mit dem Namen " + methodName + " in der Klasse " + theClass.getName() + " gefunden!";
                if (exceptionIfAmbiguous) {
                    throw new AmbiguousMethodException(exc);
                }
                logger.warn(exc);
                break;
            }
            theMethod = method;
            theConvertedParams = params_;
        }
        if (theMethod == null) {
            throw new NoSuchMethodException("Method " + methodName + " does not exist with the requested signature in " + theClass + ".");
        }

        // Sichtbarkeit ueberschreiben
        theMethod.setAccessible(includeNonPublicMethods);

      //FGL: Erst ab Java 1.7 return new Pair<>(theMethod, theConvertedParams);
        return new Pair<Method, Object[]>(theMethod, theConvertedParams);//FGL: Eclipse Vorschlag: Insert Inferred Type
    }

    public static Pair<Method, Object[]> findMatchingMethodWithConversion(Class<?> clazz, String methodName, Object[] params)
                    throws NoSuchMethodException, AmbiguousMethodException {
        return findMatchingMethod(clazz, methodName, params, false, true, false);
    }

    public static Pair<Method, Object[]> findMatchingMethodWithExceptionIfAmbiguous(Class<?> clazz, String methodName, Object[] params)
                    throws NoSuchMethodException, AmbiguousMethodException {
        return findMatchingMethod(clazz, methodName, params, false, false, true);
    }

    /**
     * Sucht eine zu den Parametern aufrufkompatible Methode.
     * Bei Mehrdeutigkeiten wird die erste gefundene und (passende) Methode zurück geliefert
     * oder es kann eine Exception geworfen werden (je nach Aufrufparameter).
     * <b>Achtung:</b> Sind Elemente von params null, so kann die Methode u.U. nur noch durch die Anzahl
     * der Parameter bestimmt werden, welches schnell zu Mehrdeutigkeiten führen kann!
     *
     * @param clazz                   Klasse, in der gesucht werden soll (deren Oberklassen werden auch berücksichtigt)
     * @param methodName              Name der zu suchenden Methode
     * @return Pair<Method, Object[]>: Die gefundene Methode und die evtl. konvertierten Aufrufparameter
     * @throws NoSuchMethodException  Wird geworfen, falls keine passende Methode gefunden wurde
     * @throws AmbiguousMethodException Wird geworfen, falls mehr als eine passende Methode gefunden wurde
     */
    public static Pair<Method, Object[]> findMatchingMethodWithNonpublic(Class<?> clazz, String methodName) throws NoSuchMethodException, AmbiguousMethodException {
        return findMatchingMethod(clazz, methodName, null, true, false, false);
    }

    /**
     * Sucht die erste zum Methodennamen passende Methode.
     * Bei Mehrdeutigkeiten wird die erste gefundene Methode zurück geliefert
     * @param clazz
     * @param methodName
     *
     * @return Method oder null
     */
    public static Method findMethodForMethodName(Class<?> clazz, String methodName) {
        for (final Method method : ReflectionMethodCache.getOrderedMethodsFor(clazz)) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    /**
     * Sucht die erste zum Methodennamen passende Methode.
     * Bei Mehrdeutigkeiten wird die erste gefundene Methode zurück geliefert
     * @param className
     * @param methodName
     *
     * @return Method oder null
     * @throws ClassNotFoundException
     */
    public static Method findMethodForMethodName(String className, String methodName) throws ClassNotFoundException {
        return findMethodForMethodName(Class.forName(className), methodName);
    }

    /**
     * Liefert alle Fields (auch nicht-public) dieser Klasse und der Superklassen zurück.
     *
     * @param clazz Die Klasse
     * @return Liste von Field[]
     */
    public static List<Field[]> getAllFieldArrays(Class<?> clazz) {
    	//FGL: Erst ab Java 1.7 final List<Field[]> list = new ArrayList<>();
    	final List<Field[]> list = new ArrayList<Field[]>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        getAllFields(clazz, list);
        return list;
    }

    /**
     * Gets all fields of the given class and its super classes.
     * @param clazz
     * @return all fields or empty collection
     */
    public static Iterable<Field> getAllFields(final Class<?> clazz) {
        return getAllFields(clazz, (Class<?>) null);
    }

    /**
     * Gets all fields of the given class and its super classes but only up to {@code upToClazz} in the hierarchy.
     * @param clazz
     * @param upToClazz
     * @return all fields or empty collection
     */
    public static Iterable<Field> getAllFields(final Class<?> clazz, final Class<?> upToClazz) {
        final String key = getFieldCacheKey(clazz, upToClazz);
        Iterable<Field> fields = _ALL_FIELDS.get(key);
        if (fields == null) {
            fields = getAllFields(clazz, upToClazz, new ImmutableSet.Builder<Field>()).build();
            _ALL_FIELDS.put(key, fields);
        }
        return fields;
    }

    private static ImmutableSet.Builder<Field> getAllFields(final Class<?> clazz, final Class<?> upToClazz, final ImmutableSet.Builder<Field> setBuilder) {
        for (final Field f : clazz.getDeclaredFields()) {
            setBuilder.add(f);
        }
        if (Objects.equal(upToClazz, clazz) || clazz.getSuperclass() == null) {
            return setBuilder;
        }
        return getAllFields(clazz.getSuperclass(), upToClazz, setBuilder);
    }

    /**
     * Liefert alle Fields (auch nicht-public) dieser Klasse und der Superklassen zurück.
     *
     * @param list Dient der Rückgabe des Ergebnisses
     * @param clazz Die Klasse
     */
    private static void getAllFields(Class<?> clazz, List<Field[]> list) {
        final Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            list.add(fields);
        }
        if (clazz.getSuperclass() != null) {
            getAllFields(clazz.getSuperclass(), list);
        }
    }

    /**
     * Liefert alle Getter-Methoden dieser Klasse und der Superklassen zurück.
     * @param clazz
     * @return Liste von Getter-Method[]
     */
    public static List<Method[]> getAllGetterMethods(Class<?> clazz) {
    	//FGL: Erst ab Java 1.7 final List<String> methodeNameList = new ArrayList<>();
    	final List<String> methodeNameList = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        final List<Field[]> fields = getAllFieldArrays(clazz);
        for (final Field[] fieldArray : fields) {
            for (final Field field : fieldArray) {
                final String fieldName = field.getName();
                final String methodName = "get".concat(fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1)));
                methodeNameList.add(methodName);
            }
        }
      //FGL: Erst ab Java 1.7 final List<Method[]> getterMethodeList = new ArrayList<>();
        final List<Method[]> getterMethodeList = new ArrayList<Method[]>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        final List<Method[]> methodeList = getAllMethods(clazz);
        for (final Method[] methodArray : methodeList) {
        	//FGL: Erst ab Java 1.7 final List<Method> getterMethodList = new ArrayList<>();
            final List<Method> getterMethodList = new ArrayList<Method>();//FGL: Eclipse Vorschlag: Insert Inferred Type
            for (final Method method : methodArray) {
                if (methodeNameList.contains(method.getName())) {
                    getterMethodList.add(method);
                }
            }
            final Method[] getterMethodArray = getterMethodList.toArray(new Method[getterMethodList.size()]);
            getterMethodeList.add(getterMethodArray);
        }
        return getterMethodeList;
    }

    /**
     * Liefert alle Methoden (auch nicht-public) dieser Klasse und der Superklassen zurück.
     *
     * @param clazz Die Klasse
     * @return Liste von Method[]
     */
    public static List<Method[]> getAllMethods(Class<?> clazz) {
    	//FGL: Erst ab Java 1.7 final List<Method[]> list = new ArrayList<>();
        final List<Method[]> list = new ArrayList<Method[]>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        getAllMethods(clazz, list);
        return list;
    }

    /**
     * Erstellt eine Liste aller Methoden der Klasse
     * und aller Superklassen und Interfaces. Im Gegensatz
     * zu Class.getAllMethods werden auch nicht-public
     * Methoden zurueckgegeben.
     *
     * @param classObj     Class-Object
     * @param foundMethods Zu fuellende ArrayList.
     */
    private static void getAllMethods(Class<?> classObj, ArrayList<Method> foundMethods) {

        // Alle direkten Methoden in die Liste aufnehmen
        for (final Method method : classObj.getDeclaredMethods()) {
            foundMethods.add(method);
        }

        // Alle Interfaces rekursiv durchgehen
        for (final Class<?> interface1 : classObj.getInterfaces()) {
            getAllMethods(interface1, foundMethods);
        }

        final Class<?> superClass = classObj.getSuperclass();
        if (superClass != null) {
            getAllMethods(superClass, foundMethods);
        }
    }

    /**
     * Liefert alle Methoden (auch nicht-public) dieser Klasse und der Superklassen zurück.
     *
     * @param list Dient der Rückgabe des Ergebnisses
     * @param clazz Die Klasse
     */
    private static void getAllMethods(Class<?> clazz, List<Method[]> list) {
        final Method[] methods = clazz.getDeclaredMethods();
        if (methods.length > 0) {
            list.add(methods);
        }
        if (clazz.getSuperclass() != null) {
            getAllMethods(clazz.getSuperclass(), list);
        }
    }

    private static ArrayList<Method> getAllMethodsEvenTheNonpublicOnes(Class<?> clazz) {
    	//FGL: Erst ab Java 1.7 final ArrayList<Method> methods = new ArrayList<>();
        final ArrayList<Method> methods = new ArrayList<Method>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        getAllMethods(clazz, methods);
        return methods;
    }

    /**
     * Erzeugt ein Class-Objekt zum gewuenschten Klassennamen und
     * prueft ob dieses ein eventuell gegebenes Interface implementiert.
     *
     * @param className Voll qualifizierter Name der gewuenschten Klasse.
     * @param interfaceName Voll qualifizierter Name des gewuenschten Interfaces, oder null wenn keine Implementierung erwartet.
     * @return Class-Objekt fuer die gewuenschte Klasse
     * @throws ClassNotFoundException wenn die Klasse nicht existiert
     * @throws IllegalArgumentException wenn die Klasse ein evtl. gewuenschtes Interface nicht implementiert
     */
    public static Class<?> getClassForName(String className, String interfaceName) throws ClassNotFoundException {
        Class<?> moduleClass = getClassForName(className);
        // Sicherstellen, dass die gefundene Klasse ein evtl. gewuenschtes Interface implementiert:
        if (interfaceName != null && !isTypeCompatible(moduleClass, interfaceName)) {
            //FGL: Erst ab Java 1.8 steht class.getTypeName() zur Verfügung 
        	//throw new IllegalArgumentException(moduleClass.getTypeName() + " implementiert " + interfaceName + " nicht!");
        	throw new IllegalArgumentException(moduleClass.getName() + " implementiert " + interfaceName + " nicht! (FGL: Java 1.8 würde ggfs. noch mehr über class.getTypeName() zurückgeben)");
        }

        return moduleClass;
    }

    public static Class<?> getClassForName(String className) throws ClassNotFoundException {
        String classNameWithoutSuffix = stripAccidentalClassSuffix(className);
        return Class.forName(classNameWithoutSuffix);
    }

    /**
     * Sucht das Feld "name" in der Klasse "clazz" und ihren Oberklassen auf iterative Art.
     *
     * @param clazz die Klasse
     * @param name der Feldname
     * @return das Feld oder null wenn es nicht gefunden wird
     */
    // XXX what's the difference to clazz.getField(name)?
    public static Field getField(Class<?> clazz, String name) {
        if (name == null) {
            return null;
        }

        Class<?> currentClass = clazz;
        while (currentClass != null && !Object.class.equals(currentClass)) {
            final Field[] fields = currentClass.getDeclaredFields();
            for (final Field field : fields) {
                if (name.equals(field.getName())) {
                    return field;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }

    private static String getFieldCacheKey(final Class<?> clazz, final Class<?> clazz2) {
        final StringBuilder sb = new StringBuilder(clazz.getName());
        if (clazz2 != null) {
            sb.append(",").append(clazz2.getName());
        }
        return sb.toString();
    }

    /**
     * Returns the first available property from {@code properties} of the {@code item}.
     *
     * @param item          the item thats properties will be inspected
     * @param properties    the searched properties in descending order
     *
     * @return the property as {@link Object}
     *
     * @throws IllegalArgumentException if none of the queried properties has been found
     */
    public static Object getFirstMatchingProperty(Object item, String... properties) {
        Object detectedProperty = null;
        for (final String property : properties) {
            try {
                detectedProperty = PropertyUtils.getProperty(item, property);
                if (detectedProperty != null) {
                    return detectedProperty;
                }
            } catch (final Exception e) {
                // do nothing; it's expected that this might fail
            }
        }

        throw new IllegalArgumentException("no non null key found for item '" + item + "' using key properties " + ArrayUtils.toString(properties));
    }

    /**
     *
     * @param clazz
     * @return the first type parameter if any exists
     */
    public static Class<?> getFirstTypeParameter(Class<?> clazz) {
        return getTypeParameterAtIndex(clazz, 0);
    }


    /**
     * @return non static relevant Getter Methods
     */
    public static Map<Method, String> getGetterMethods(Class<?> clazz) {
        return getGetterMethods(clazz, null);
    }

    /**
     * @return non static relevant Getter Methods without unneededValues
     */
    public static Map<Method, String> getGetterMethods(Class<?> clazz, Collection<String> unneededValues) {
        final List<Method> methods = ReflectionMethodCache.getOrderedMethodsFor(clazz);
      //FGL: Erst ab Java 1.7  final Map<Method, String> relevantMethods = new LinkedHashMap<>(); // LinkedHashMap ist ordnungserhaltend
        final Map<Method, String> relevantMethods = new LinkedHashMap<Method, String>(); // LinkedHashMap ist ordnungserhaltend //FGL: Eclipse Vorschlag: Insert Inferred Type
        for (final Method method : methods) {
            final String methodName = method.getName();
            if (!isNonStaticGetMethod(method, methodName)) {
                continue;
            }
            final String attributeName = method.getName().substring(3);
            if (unneededValues != null && unneededValues.contains(attributeName) || globalUnneededValues.contains(attributeName)) {
                continue;
            }
            relevantMethods.put(method, attributeName);
        }
        return relevantMethods;
    }

    /**
     * @return non static is...Set Methods
     */
    public static Map<String, Method> getIsSetMethods(Class<?> clazz) {
        final List<Method> methods = ReflectionMethodCache.getOrderedMethodsFor(clazz);
      //FGL: Erst ab Java 1.7 final Map<String, Method> relevantMethods = new LinkedHashMap<>(); // LinkedHashMap ist ordnungserhaltend
        final Map<String, Method> relevantMethods = new LinkedHashMap<String, Method>(); // LinkedHashMap ist ordnungserhaltend //FGL: Eclipse Vorschlag: Insert Inferred Type
        for (final Method method : methods) {
            final String methodName = method.getName();
            if (!isNonStaticIsSetMethod(method, methodName)) {
                continue;
            }
            final String attributeName = method.getName().substring(2, method.getName().length() - 3);
            relevantMethods.put(attributeName, method);
        }
        return relevantMethods;
    }

    /**
     * Gets the implementing method of interface method that the method signature represents.
     * @param targetClass the concrete class implementing the interface method
     */
    public static Method getImplementingMethod(Class<?> targetClass, MethodSignature sig) {
        return getMethod(targetClass, sig.getMethod().getName(), sig.getMethod().getParameterTypes());
    }

    /**
     * Gets the method with the given name and argument types.
     */
    public static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>... argumentClasses) {
        final String key = getMethodCacheKey(clazz, methodName, argumentClasses);
        Method m = _ALL_METHODS.get(key);
        if (m != null) {
            return m;
        }
        try {
            if (argumentClasses != null && argumentClasses.length > 0) {
                m = clazz.getMethod(methodName, argumentClasses);
            } else {
                m = clazz.getMethod(methodName);
            }
            _ALL_METHODS.put(key, m);
        } catch (final SecurityException e) {
            logger.error(e);
        } catch (final NoSuchMethodException e) {
            logger.debug(e);
        }
        return m;
    }

    public static String getMethodCacheKey(final Class<?> clazz, String methodName, final Class<?>... parameterClasses) {
        final StringBuilder sb = new StringBuilder();
        sb.append(clazz.getName()).append(".").append(methodName);

        if (parameterClasses != null && parameterClasses.length > 0) {
            for (final Class<?> parameterClazz : parameterClasses) {
                if (parameterClazz == null) {
                    sb.append(",null");
                } else {
                    sb.append(",").append(parameterClazz.getName());
                }
            }
        }
        return sb.toString();
    }

    private static List<Method> getMethods(Class<?> clazz, boolean includeNonPublicMethods) {
        EnsureArgument.notNull(clazz);
        if (includeNonPublicMethods) {
            return getAllMethodsEvenTheNonpublicOnes(clazz);
        }
        return Arrays.asList(clazz.getMethods());
    }

    /**
     * Gets the value of a (possibly) private member field.
     *
     */
    public static Object getPrivateField(Class<?> theClass, Object theObject, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        final Field field = theClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(theObject);
    }

    /**
     * Gets the value of a (possibly) private field.
     *
     * @param theInstance Object the field belongs to or instance of Class if it is a static field
     * @param fieldName name of field
     * @return value
     * @throws NoSuchFieldException wenn das Feld nicht existiert
     * @throws IllegalAccessException IllegalAccessException
     */
    public static Object getPrivateField(Object theInstance, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getPrivateField(theInstance.getClass(), theInstance, fieldName);
    }

    /**
     * Gets the value of a (possibly) private static field.
     *
     * @param fieldName name of field
     * @return value
     * @throws NoSuchFieldException wenn das Feld nicht existiert
     * @throws IllegalAccessException IllegalAccessException
     */
    public static Object getPrivateStaticField(Class<?> theClass, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getPrivateField(theClass, null, fieldName);
    }

    /**
     * Gets the root cause of an exception, returning the exception itself if there is no cause.
     *
     * @param e Exception or other throwable
     * @return root cause
     */
    public static Throwable getRootCause(Throwable e) {
        Throwable res = e;
        while (res.getCause() != null) {
            res = res.getCause();
        }
        return res;
    }

    /**
     *
     * @param clazz
     * @param i - first parameter is at index 0!
     * @return the first type parameter if any exists
     */
    public static Class<?> getTypeParameterAtIndex(Class<?> clazz, int i) {
        final ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
        final Type[] types = pt.getActualTypeArguments();
        //        for (Type t : types) {
        //            log.debug(t);
        //        }
        return types.length > 0 && i >= 0 ? (Class<?>) types[i] : null;
    }

    /**
     * Liefert den reinen Klassennamen eines Objects, d.h. ohne Package-Pfad,
     * oder null, wenn kein Object übergeben wurde.
     *
     * @param anObject nicht null
     * @return unqualifizierter Klassenname
     */
    public static String getUnqualifiedClassName(Object anObject) {
        final String qualifiedClassname = anObject.getClass().getName();
        // is zero when no period is found:
        final int startindexOfSimpleName = qualifiedClassname.lastIndexOf('.') + 1;
        return qualifiedClassname.substring(startindexOfSimpleName);
    }

    /**
     * Wenn Throwable gefangen wird, damit auch Errors erwischt werden, darf
     * ThreadDeath nicht unterdrückt werden. Diese Methode prüft, ob es sich
     * um ThreadDeath handelt und wirft es ggf. erneut.
     *
     * @param throwable throwable
     */
    public static void handleCaughtThreadDeath(Throwable throwable) {
        if (throwable instanceof ThreadDeath) {
            throw (ThreadDeath) throwable;
        }
    }

    /**
     * checks whether the specified class has a constructor without parameters
     *
     * @param clazz class to check
     * @return true if there is a constructor without parameters
     */
    public static boolean hasNoArgumentContructor(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        final Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            return true;
        }
        for (final Constructor<?> c : constructors) {
            if (c.getParameterTypes().length == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ruft eine Methode auf. (Kompatibilitaet mit alten JDKs oder Compilern).
     *
     * @param object     Object, auf dem die Methode ausgef&uuml;hrt wird.
     *                   (F&uuml;r statische Methoden kann eine Instanz
     *                   der Klasse <code>class</code> &uuml;bergeben werden.
     * @param methodName Name der Methode
     * @param params     Object-Array mit Parametern
     * @return           R&uuml;ckgabewert der Methode.
     *                   (primitive Datentypen werden gewrapt; void wird zu null).
     * @throws IllegalAccessException wenn die Methode nicht sichtbar ist.
     * @throws InvocationTargetException wenn die Methode selbst eine Exception wirft
     * @throws NoSuchMethodError if method does not exist
     */
    public static Object invokeMethod(Object object, String methodName, Object... params) throws IllegalAccessException, InvocationTargetException, NoSuchMethodError {
        if (object instanceof Class<?>) {
            // call static method
            return invokeMethod(null, (Class<?>) object, methodName, params);
        }
        return invokeMethod(object, object.getClass(), methodName, params);
    }

    private static Object invokeMethod(final Object objectLocal, final Class<?> classObj, String methodName, Object[] params)
                    throws IllegalAccessException, InvocationTargetException {
        final ArrayList<Method> methods = getAllMethodsEvenTheNonpublicOnes(classObj);

        // Methode mit dem Namen und richtiger Signatur finden.
        Method theMethod = findCompatibleMethod(methods, methodName, params);
        if (theMethod == null) {
            throw new RuntimeException("Method '" + methodName + "' does not exist with the requested signature in '" + classObj + "'");
        }

        if (isDtoAttributeThatIsSet(objectLocal, methodName, methods)) {
            // for getter-methods of Dtos: avoid method invocation by reflection if the attribute is not set
            return null;
        }
        return theMethod.invoke(objectLocal, params);
    }

    
    private static boolean isDtoAttributeThatIsSet(final Object objectLocal, String methodName, final ArrayList<Method> methods)
                    throws IllegalAccessException, InvocationTargetException {
        // XXX diese Referenz auf DtoBase kriege ich nicht weg!
    	//FGL 20180217 rausgenommen wg. Reengineering
//        if (!(objectLocal instanceof DtoBase)) {
//            return false;
//        }
        if (!methodName.startsWith("get")) {
            return false;
        }
        final String isSetMethodName = "is" + methodName.substring(3) + "Set";
        for (final Method method : methods) {
            if (!method.getName().equals(isSetMethodName)) {
                continue;
            }
            Boolean invoked = (Boolean) method.invoke(objectLocal);
            if (!invoked.booleanValue()) {
                return true;
            }
        }
        return false;
    }

    private static Method findCompatibleMethod(final ArrayList<Method> methods, String methodName, Object[] parameters) {
        for (final Method method : methods) {
            if (method.getName().equals(methodName) && parametersAreCompatible(method, parameters)) {
                return method;
            }
        }
        return null;
    }

    private static boolean parametersAreCompatible(final Method method, Object[] params) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (params == null) {
            return true;
        }
        if (parameterTypes.length != params.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (params[i] != null && !isAssignableWithAutoWrapping(parameterTypes[i], params[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Ruft eine Methode auf. (Kompatibilitaet mit alten JDKs oder Compilern).
     *
     * @param className  Klasse, auf dem die Methode ausgef&uuml;hrt wird.
     * @param methodName Name der Methode
     * @param params     Object-Array mit Parametern
     * @return           R&uuml;ckgabewert der Methode.
     *                   (primitive Datentypen werden gewrapt; void wird zu null).
     * @throws IllegalAccessException wenn die Methode nicht sichtbar ist.
     * @throws InvocationTargetException wenn die Methode selbst eine Exception wirft
     * @throws NoSuchMethodError if method does not exist
     * @throws ClassNotFoundException
     * @throws InstantiationException
     */
    public static Object invokeMethod(String className, String methodName, Object... params)
                    throws IllegalAccessException, InvocationTargetException, NoSuchMethodError, InstantiationException, ClassNotFoundException {
        return invokeMethod(Class.forName(className).newInstance(), methodName, params);
    }

    /**
     * Ruft die Set-Methode von dem Objekt "obj" für das Attribut mit dem Namen "name" und dem Wert "value" auf.
     * Dabei besitzt "value" bereits den passenden Datentyp.
     *
     * @param obj Objekt
     * @param name der Name des Attributs, dessen Set-Methode aufgerufen werden soll
     * @param value der Wert, der gesetzt werden soll
     * @return true, wenn der Aufruf glückt, false sonst
     */
    public static boolean invokeSetter(Object obj, String name, Object value) {
        final String setter = setterName(name);
        final Object[] params = new Object[] { value };

        try {
            invokeMethod(obj, setter, params);
            return true;
        } catch (final Throwable e) {
            logger.debug("Couldn't call Method!", e);
            return false;
        }

    }

    /**
     * Ruft die Set-Methode von dem Objekt "obj" für das Attribut mit dem Namen "name" und dem Wert "value" auf.
     * Dabei wird "value" vom String auf den passenden Datentyp gecastet.
     *
     * @param obj der Wert, der gesetzt werden soll, als String
     * @param name der Name des Attributs, dessen Set-Methode aufgerufen werden soll (ohne ein "set" davor, nur Attributname!)
     * @param value der Wert, der gesetzt werden soll, als String
     * @return true, wenn der Aufruf glückt, false sonst
     */
    public static boolean invokeSetter(Object obj, String name, String value) {
        final String setter = setterName(name);
        try {
            final Field field = getField(obj.getClass(), name);
            final Class<?> clazz = field.getType();
            final Object[] params = new Object[1];
            params[0] = createSpecializedObjectByString(clazz, value);
            ReflectionUtil.invokeMethod(obj, setter, params);
            return true;
        } catch (final Throwable e) {
            logger.debug("Couldn't call Method!", e);
            return false;
        }
    }

    private static String setterName(String name) {
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }

    public static void invokeStaticMethod(String qualifiedClassname, String methodName, Class<?>[] parameterTypes, Object[] parameterValues) {
        try {
            final Class<?> clazz = Class.forName(qualifiedClassname);
            final Method m = clazz.getMethod(methodName, parameterTypes);
            if (m != null) {
                m.invoke(null, parameterValues);
            }
          //FGL: Erst ab Java 1.7
          //Multi-catch parameters are not allowed for source level below 1.7
//        } catch (ClassNotFoundException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//            logger.debug(e);
//        }            
        } catch (ClassNotFoundException e){
            logger.debug(e);
	    } catch (SecurityException e) {
	        logger.debug(e);
	    } catch (NoSuchMethodException e) {
	        logger.debug(e);
	    } catch (IllegalArgumentException e) {
            logger.debug(e);
	    } catch (IllegalAccessException e) {
            logger.debug(e);
	    } catch (InvocationTargetException e) {
            logger.debug(e);
        }
    }


    /**
     * Ueberpreuft, ob zwei Klassen zuweisungskompatibel sind.
     * Im Gegensatz zu Class.isAssignableFrom werden Wrapper-Typen
     * bei Bedarf in primitive Datentypen konvertiert.
     *
     * @param theGeneralClass die allgemeinere Klasse
     * @param theSpecialClass       die spezielle Klasse
     * @return true, wenn die Klassen kompatibel sind, sonst false.
     */
    private static boolean isAssignableWithAutoWrapping(Class<?> theGeneralClass, Class<?> theSpecialClass) {
        Class<?> generalOrWrapperClass = theGeneralClass;
        if (generalOrWrapperClass.isPrimitive()) {
            generalOrWrapperClass = PrimitivesUtil.findWrapperClass(generalOrWrapperClass);
        }

        return generalOrWrapperClass.isAssignableFrom(theSpecialClass);
    }

    /**
     * Wurde diese Methode (indirekt) durch JUnit aufgerufen?
     *
     * @return true, wenn ueber JUnit aufgerufen, sonst false
     */
    public static boolean isCalledByJUnit() {
        final StringWriter sw = new StringWriter();
        new Throwable().printStackTrace(new PrintWriter(sw));
        return StringUtil.containsWord(sw.toString(), "junit") > -1;
    }


    private static boolean isMethodCompatible(boolean allowConversion, final Object[] params, final Class<?>[] parameterTypes, final Object[] convertedParameters) {
        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameterClass = parameterTypes[i];
            final Object parameterObject = params[i];
            if (parameterObject == null) {
                continue;
            }
            if (isAssignableWithAutoWrapping(parameterClass, parameterObject.getClass())) {
                continue;
            }
            if (!allowConversion || !Number.class.isAssignableFrom(parameterClass)) {
                return false;
            }
            final String tmp = parameterObject.toString().trim();
            final Object converted = parseNumericValue(tmp, parameterClass);
            if (converted == null) {
                return false;
            }
            convertedParameters[i] = converted;
        }
        return true;
    }

    private static boolean isNonStaticGetMethod(final Method method, final String methodName) {
        return methodName.startsWith("get") && !"get".equals(methodName) && !Modifier.isStatic(method.getModifiers());
    }

    private static boolean isNonStaticIsSetMethod(final Method method, final String methodName) {
        return methodName.startsWith("is") && methodName.endsWith("Set") && !Modifier.isStatic(method.getModifiers());
    }

    /**
     * Ist diese Klasse zum angegebenen Interface-Namen kompatibel?
     *
     * @param theClass   Klasse
     * @param classOrInterface Interface-Name (kann auch ein Klassenname sein
     * @return true, wenn kompatibel, sonst false
     */
    public static boolean isTypeCompatible(Class<?> theClass, String classOrInterface) {
        // XXX ist das nicht das Gleiche wie classOrInterface.isAssignableFrom(theClass) ?
        if (theClass == null) {
            return false;
        }
        if (theClass.getName().equals(classOrInterface)) {
            // Heisst diese Klasse so?
            return true;
        }

        for (final Class<?> implementedInterface : theClass.getInterfaces()) {
            // Implementiert diese Klasse ein Interface mit diesem Namen?

            if (classOrInterface.equals(implementedInterface.getName())) {
                return true;
            }

            // Ein Interface könnte von dem gesuchten Interface abgeleitet sein
            if (isTypeCompatible(implementedInterface, classOrInterface)) {
                return true;
            }
        }

        return isTypeCompatible(theClass.getSuperclass(), classOrInterface);
    }

    private static boolean methodIsPublic(final Method method) {
        return Modifier.isPublic(method.getModifiers());
    }

    private static Object parseNumericValue(String string, final Class<?> numericClass) {
        Object converted = null;
        try {
            if (Long.class.isAssignableFrom(numericClass)) {
                converted = Long.valueOf(string);
            } else if (Integer.class.isAssignableFrom(numericClass)) {
                converted = Integer.valueOf(string);
            } else if (Short.class.isAssignableFrom(numericClass)) {
                converted = Short.valueOf(string);
            } else if (Float.class.isAssignableFrom(numericClass)) {
                converted = Float.valueOf(string);
            } else if (Byte.class.isAssignableFrom(numericClass)) {
                converted = Byte.valueOf(string);
            }
        } catch (final Throwable e) {
            // nix
        }
        return converted;
    }

    /**
     *
     * Sets the value of a (possibly) private member field.
     *
     * FOR TESTING PURPOSES ONLY !!
    */
    public static void setPrivateFieldForTestsOnly(Class<?> theClass, Object theObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        final Field field = theClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(theObject, value);
    }

    public static String stripAccidentalClassSuffix(String className) {
        final String suffix = ".class";
        if (className.endsWith(suffix)) {
            logger.warn("Klasse " + className + " sollte ohne Suffix " + suffix + " angegeben werden.");
            return StringUtil.removeSuffixIfpresent(className, suffix);
        }
        return className;
    }

    /**
     * Folgt privaten Feldern in einem Objektgefl&auml;cht
     *
     * @param object Ausgangsobjekt
     * @param fields Feldernamen mit "." getrennt
     * @return Object oder null bei einem Fehler
     */
    public static Object traverseFieldsIgnoringErrors(Object object, String fields) {
        Object currentObject = object;
        try {
            final StringTokenizer st = new StringTokenizer(fields, ".,");
            while (st.hasMoreTokens()) {
                if (currentObject == null) {
                    return null;
                }
                final Field field = getField(currentObject.getClass(), st.nextToken());
                if (field == null) {
                    return null;
                }
                field.setAccessible(true);
                currentObject = field.get(currentObject);
            }
        } catch (final Exception e) {
            logger.debug(e, e);
            return null;
        }
        return currentObject;
    }

}
