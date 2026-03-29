package base.numeric;
//package de.his.core.base.numeric;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

//import de.his.core.base.strings.StringUtil;
import base.strings.StringUtil;
//import de.his.core.util.JSFConverterUtil;
import base.util.JSFConverterUtil;

/**
 * @author Krull
 * @version $Id: NumberUtil.java,v 1.13 2017/03/23 12:43:07 siegel#his.de Exp $
 *
 */
public class NumberUtil {
    public static Logger logger = Logger.getLogger(NumberUtil.class);

    /**
     * Konvertiert den Dezimaltrenner von Komma nach Punkt und entfernt evtl. vorhandene Tausendertrenner
     *
     * @param input
     * @return Die konvertierte Zahl als Zeichenkette
     */
    // FIXME Funktion ist spezieller, als ihr Name vermuten lässt!
    public static String convertDecimalStringToEnglishFormat(String input) {
        // Nur Zahlenwerte bzw. kein Wert sollen als gültige Eingabe akzeptiert werden
        String value = input;
        // Ersetze ggf. den Dezimaltrenner von Komma nach Punkt und entferne evtl. vorhandene Tausendertrenner
        if (input.length() >= 4) {
            final char separator = input.charAt(input.length() - 3);

            if (separator == ',') {
                value = value.replace(".", "");
                value = value.substring(0, value.length() - 3) + '.' + value.substring(value.length() - 2, value.length());
            }
        }

        return value;
    }

    /**
     * Verifies that an object can be converted into a natural number (N = {0, 1, 2, ...}) non-null {@link Long}-value.
     *
     * @param value to verify
     *
     * @return {@code true} if the given object can be converted into a natural number (N = {0, 1, 2, ...}) non-null {@link Long}-value; {@code false} otherwise.
     */
    public static boolean is0PositivLong(Object value) {
        try {
            final Long l = JSFConverterUtil.asLong(value);
            return l != null && l.longValue() > -1;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * Verifies that an object can be converted into a natural number (N = {0, 1, 2, ...}) non-null {@link Integer}-value.
     *
     * @param value to verify
     *
     * @return {@code true} if the given object can be converted into a natural number (N = {0, 1, 2, ...}) non-null {@link Integer}-value; {@code false} otherwise.
     */
    public static boolean isNonnegativeInteger(Object value) {
        try {
            final Integer valueAsInteger = JSFConverterUtil.asInteger(value);
            return valueAsInteger != null && valueAsInteger.intValue() >= 0;
        } catch (final Exception e) {
            return false;
        }
    }

    /**
     * Formats the given number with the given locale into the corresponding string representation with the given number of fraction digits.
     *
     * @param number to format
     * @param minimumFractionDigits minimum number of digits allowed in the fraction
     * @param locale to use
     *
     * @return number formatted as String for the given locale showing the given minimum number of fractions.
     */
    public static String formatNumber(Number number, int minimumFractionDigits, Locale locale) {
        final Locale localeCurrent = locale == null ? Locale.GERMAN : locale;

        final NumberFormat numberFormat = NumberFormat.getInstance(localeCurrent);
        numberFormat.setMinimumFractionDigits(minimumFractionDigits);
        return numberFormat.format(number);
    }

    /**
     * Converts a comma separated list of numbers to a list of Longs<br>
     * <small>Example:<br>
     * </small>
     * @param s
     * @return Long list
     */
    public static Long[] stringToLongArray(String s) {
        if (s == null) {
            return new Long[0];
        }
        final String sNoFlaw = s.replaceAll(",$|,,|\\s|^,", "");
        final String[] split = StringUtils.split(sNoFlaw, ',');
        final Long[] transform = new Long[split.length];

        for (int i = 0; i < transform.length; i++) {
            transform[i] = Long.valueOf(split[i]);
        }
        return transform;
    }

    /**
     * Ermitteln des kleinsten Elementes in einem String, welcher eine Liste von Zahlen separiert mit sep enthält.
     *
     * @param arrayStr String (Liste von Zahlen separiert mit sep)
     * @param sep Separator
     * @return kleinstes Element der Liste
     */
    public static int getLeastElement(String arrayStr, char sep) {
        final List<String> list = StringUtil.splitWithEmptyEntries(arrayStr, sep);
        final Iterator<String> itr = list.iterator();
        int ele = Integer.parseInt(itr.next());
        while (itr.hasNext()) {
            final int current = Integer.parseInt(itr.next());
            if (ele > current) {
                ele = current;
            }
        }
        return ele;
    }

    /**
     * @return Zufallswert aus [lowest, highest)
     */
    public static int getRandom(int lowest, int limit) {
        return lowest + (int) (Math.random() * (limit - lowest));
    }

    public static int nonnegativeRandomNumber() {
        final Random rdFile = new Random();
        final int rdNumber = rdFile.nextInt();
        // Since Math.abs(Integer.MIN_VALUE) == Integer.MIN_VALUE, we cannot use Math.abs here!
        return rdNumber >= 0 ? rdNumber : -(rdNumber + 1);
    }

    public static double parseLocalizedDouble(String str) throws ParseException {
        Number n = parseLocalizedNumber(str);
        return n.doubleValue();
    }

    public static Number parseLocalizedNumber(String str) throws ParseException {
        // wenn es eine englische Formatierung ist, dann auch so behandeln
        Locale loc = guessLocaleOfNumber(str);
        NumberFormat format = NumberFormat.getInstance(loc);
        return format.parse(str);
    }

    public static Locale guessLocaleOfNumber(String str) {
        boolean decimalMarkIsDot = str.lastIndexOf(".") > str.lastIndexOf(",");
        return decimalMarkIsDot ? Locale.ENGLISH : Locale.GERMAN;
    }

    /**
     * String wird bei der ersten nicht numerischen Stelle abgeschnitten und in einen int umgewandelt.
     * @param string darf auch mit "-" beginnen
     * @return int
     * @throws NumberFormatException
     */
    public static int parseInt(String string) throws NumberFormatException {
        String substring = numericPrefix(string);
        return Integer.parseInt(substring);
    }

    /**
     * String wird bei der ersten nicht numerischen Stelle abgeschnitten und in einen long umgewandelt.
     * @param string stellt nichtnegative Zahl dar
     * @return long
     * @throws NumberFormatException
     */
    public static long parseLong(String string) throws NumberFormatException {
        String substring = nonnegativeNumericPrefix(string);
        return Long.parseLong(substring);
    }

    public static String nonnegativeNumericPrefix(String string) {
        int i = 0;
        for (; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                break;
            }
        }
        String substring = string.substring(0, i);
        return substring;
    }

    private static String numericPrefix(String string) {
        int i = string.startsWith("-") ? 1 : 0;
        for (; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                break;
            }
        }
        String substring = string.substring(0, i);
        return substring;
    }

    /**
     * Versucht einen String in eine Zahl umzuwandeln und gibt
     * bei einer ungueltigen Zahl den Default-Wert zurueck.
     * Sofern der String nicht <code>null</code> ist, wird
     * in diesem Fall eine Warnung ausgegeben.
     *
     * @param zahl Zeichenfolge
     * @param defaultValue Default-Wert, wenn die Zeichenfolge keine Ganzzahl ist.
     * @return long
     */
    public static long parseLong(String zahl, long defaultValue) {
        try {
            return Long.parseLong(zahl);
        } catch (final NumberFormatException e) {
            logger.warn("Ungueltige Zahl \"" + zahl + "\". Default-Wert " + defaultValue + " wird verwendet.");
            return defaultValue;
        }
    }

    /**
     * Gibt true zurück, wenn das Argument eine Zahl repräsentiert.
     * Ein Dezimalpunkt wird unterstützt.
     *
     * @param str Zu betrachtender Wert
     * @return true wenn der Wert numerisch ist
     */
    public static boolean representsDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (final NumberFormatException ne) {
            return false;
        }
    }

    public static boolean representsFloat(String parameterName) {
        try {
            Float.valueOf(parameterName);
            return true;
        } catch (final NumberFormatException exc) {
            return false;
        }
    }

    /**
     * Gibt true zurück, wenn das Argument einen Integer-Wert repräsentiert.
     * (Minus-Zeichen erlaubt, Dezimal- und Tausendertrennzeichen nicht)
     *
     * @param str Zu betrachtender Wert
     * @return true wenn der Wert eine ganze Zahl ist
     */
    public static boolean representsInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (final NumberFormatException ne) {
            return false;
        }
    }

    /**
     * Wandelt einen String in ein Long um. Wenn der String
     * leer ist, dann wird 0 zurückgeliefert.
     *
     * @param str the str
     * @return Integer
     */
    public static Long parseLongOrZero(String str) {
        try {
            if (!StringUtils.isEmpty(str)) {
                return Long.valueOf(str);
            }
        } catch (final NumberFormatException e) {
            // nothing
        }
        return Long.valueOf(0);
    }


    /**
     * Versucht einen String in eine Zahl umzuwandeln und gibt bei einer
     * ungueltigen Zahl den Default-Wert zurueck. Sofern der String nicht
     * <code>null</code> ist, wird in diesem Fall eine Warnung ausgegeben.
     *
     * @param zahl Zeichenfolge
     * @param defaultValue Default-Wert, wenn die Zeichenfolge keine Ganzzahl
     *        ist.
     * @return int
     */
    public static int parseInt(String zahl, int defaultValue) {
        if (zahl == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(zahl);
        } catch (final NumberFormatException e) {
            StringUtil.logger.warn("Ungueltige Zahl \"" + zahl + "\". Default-Wert " + defaultValue + " wird verwendet.");
            return defaultValue;
        }
    }
}

