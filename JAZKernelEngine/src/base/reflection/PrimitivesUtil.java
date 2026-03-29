package base.reflection;
//package de.his.core.base.reflection;

public class PrimitivesUtil {
    private static final Class<?>[] primitiveTypes = { Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE };

    private static final Class<?>[] wrapperTypes = { Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, java.lang.Double.class };

    static Class<?> findWrapperClass(Class<?> primitiveClass) {
        for (int i = 0; i < primitiveTypes.length; i++) {
            if (primitiveTypes[i].equals(primitiveClass)) {
                return wrapperTypes[i];
            }
        }
        return primitiveClass;
    }

    public static Object createSpecializedPrimitiveByName(String cname, String value) {
        if ("boolean".equals(cname)) {
            return Boolean.valueOf(value);
        } else if ("byte".equals(cname)) {
            return Byte.valueOf(value);
        } else if ("char".equals(cname)) {
            return Character.valueOf(value.charAt(0));
        } else if ("short".equals(cname)) {
            return Short.valueOf(value);
        } else if ("int".equals(cname)) {
            return Integer.valueOf(value);
        } else if ("long".equals(cname)) {
            return Long.valueOf(value);
        } else if ("float".equals(cname)) {
            return Float.valueOf(value);
        } else if ("double".equals(cname)) {
            return Double.valueOf(value);
        } else {
            return value;
        }
    }
}
