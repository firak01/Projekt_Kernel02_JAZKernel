package base.strings;
//package de.his.core.base.strings;

//import de.his.core.base.invariants.EnsureArgument;
import base.invariants.EnsureArgument;

/**
 * @author MB
 *
 * various utils around Strings
 */
public class StringBufferUtil {

    private StringBufferUtil() {
    }

    /**
     * @param buf -
     *            StringBuffer in which replacement is to be made
     * @param token -
     *            String to be replaced
     * @param newval -
     *            String to value
     */
    public static void replace(StringBuffer buf, String token, String newval) {
        EnsureArgument.notNull(token);
        EnsureArgument.notNull(newval);
        if (token.equals(newval)) {
            return;
        }
        int index = 0;
        while (true) {
            index = buf.toString().indexOf(token, index);
            if (index == -1) {
                break;
            }
            int end = index + token.length();
            buf.replace(index, end, newval);
            index = index + newval.length();
        }
    }


    /**
     * gets the last char in a StringBuffer
     *
     * @param buf
     */
    public static char getLastChar(StringBuffer buf) {
        if (isEmpty(buf)) {
            throw new IllegalArgumentException("Leerer Buffer");
        }
        return buf.charAt(buf.length() - 1);
    }

    public static boolean isEmpty(StringBuffer sb) {
        return sb == null || sb.length() == 0;
    }

    public static void removeLastChar(StringBuffer sb) {
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
    }

    public static void removeSuffix(StringBuffer source, String removeIt) {
        if (source.length() < removeIt.length()) {
            throw new IllegalArgumentException("Source: " + source + " is shorter than " + removeIt);
        }
        if (!source.substring(source.length() - removeIt.length()).equals(removeIt)) {
            throw new IllegalArgumentException("Source: " + source + " doesn't end with " + removeIt);
        }

        source.setLength(source.length() - removeIt.length());

    }
}

