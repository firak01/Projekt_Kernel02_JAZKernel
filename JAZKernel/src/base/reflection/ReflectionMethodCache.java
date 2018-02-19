package base.reflection;
//package de.his.core.base.reflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper for sorted Access to Method- and Fieldnames
 * Not the particular sortorder is important, but the fact that Fields and Methods
 * are returned in a reproduceable order at all.
 *
 * Already sorted Sets are cached for performance
 *
 * @author lustig#his.de
 * @version $Id: ReflectionMethodCache.java,v 1.1 2016/08/05 09:23:43 siegel#his.de Exp $
 */
public class ReflectionMethodCache {

    private static Map<Class<?>, List<Method>> methodCache = new HashMap<Class<?>, List<Method>>();

    /**
     * Return all public member methods of clazz including superclasses in a reproduceable order
     * Results are cached for performance.
     *
     * @param clazz
     * @return all public member methods of clazz including superclasses in a reproduceable order
     */
    public static List<Method> getOrderedMethodsFor(Class<?> clazz) {
        List<Method> methods = methodCache.get(clazz);

        if (methods == null) {
            methods = fetchOrderedMethodsFor(clazz);
            methodCache.put(clazz, methods);
        }

        return methods;
    }

    /**
     * @param clazz
     * @return all public member methods of clazz including superclasses in a reproduceable order
     */
    private static List<Method> fetchOrderedMethodsFor(Class<?> clazz) {
        List<Method> methods = new ArrayList<Method>(Arrays.asList(clazz.getMethods()));
        Collections.sort(methods, new MethodByNameComparator());
        return methods;
    }

    /**
     * Comparator to sort Methods by Name
     */
    static class MethodByNameComparator implements Comparator<Method> {

        @Override
        public int compare(Method m1, Method m2) {
            return (m1.getName().compareTo(m2.getName()));
        }

    }

}

