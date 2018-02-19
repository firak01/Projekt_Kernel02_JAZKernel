package base.util;
//package de.his.core.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

//FGL 20180216: Beim Reengineering auf diese Klassen verzichten.
//import de.his.appserver.service.Constants;
//import de.his.appserver.service.iface.i18n.MessageService;
//import de.his.core.webapp.i18n.HisLocaleHolder;
//import de.his.core.webapp.spring.SpringApplicationContext;

/**
 * Methoden zur Konvertierung von Object in spezifischere Ziel-Datentypen.
 * @author D.Scholz
 */
// XXX Vorsicht beim Refaktorisieren, diese Klasse wird als Spring Bean "converterUtil" verwendet
public class JSFConverterUtil {
    private static final Logger LOG = Logger.getLogger(JSFConverterUtil.class);

    /**
     * Versucht, ein Object in einen Long umzuwandeln.
     * @param value
     * @return Long oder null
     * @throws NumberFormatException
     */
    public static Long asLong(Object value) throws NumberFormatException {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        if ((value instanceof String) && ((String) value).trim().length() == 0) {
            return null;
        }
        return Long.valueOf(value.toString().trim());
        // TODO: Hier wird u.a. bei Dto's ausgenutzt, dass die toString()-Methode von Dtos die id zurückgibt.
        // Das sollte explizit gemacht werden.
    }

    /**
     * @param value
     * @return value converted to Long if possible
     */
    // TODO: Wird nur einmal aufgerufen. Implementierung oben benutzen?
    public static Long asLong2(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
            // TODO: Was passiert hier mit Dezimalzahlen?
        }

        try {
            return Long.valueOf(value.toString().trim());
        } catch (NumberFormatException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Value '" + value + "' could not be converted to Long!");
            }
            return null;
        }
    }

    /**
     * Versucht, ein Object in einen Integer umzuwandeln.
     * @param value
     * @return Integer oder null
     * @throws NumberFormatException
     */
    public static Integer asInteger(Object value) throws NumberFormatException {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if ((value instanceof String) && ((String) value).trim().length() == 0) {
            return null;
        }
        return Integer.valueOf(value.toString().trim());
    }

    /**
     * Versucht, ein Object in einen Integer umzuwandeln.
     * @param value
     * @param defaultValue
     * @return Integer oder null
     * @throws NumberFormatException
     */
    public static Integer asInteger(Object value, int defaultValue) throws NumberFormatException {
        Integer integer = asInteger(value);
        if (integer != null) {
            return integer;
        }
        return Integer.valueOf(defaultValue);
    }

    /**
     * Versucht, ein Object in einen Integer umzuwandeln. Falls ein Fehler auftritt wird defaultValue benutzt.
     * @param value
     * @param defaultValue
     * @return Integer or defaultValue
     */
    public static Integer asIntegerWithoutParseException(Object value, int defaultValue) {
        try {
            return asInteger(value, defaultValue);
        } catch (NumberFormatException e) {
            //
        }
        return Integer.valueOf(defaultValue);
    }

    /**
     * Versucht, ein Object in einen Boolean umzuwandeln.
     * @param value
     * @return Boolean
     */
    public static Boolean asBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (!(value instanceof String)) {
            return Boolean.valueOf(value.toString());
        }
        String value_ = ((String) value).trim();
        if (value_.length() == 0) {
            return null;
        }
        return Boolean.valueOf(value_);
    }

    /**
     * Versucht, ein Object in einen boolean umzuwandeln.
     * @param value
     * @param defaultValue
     * @return Boolean
     */
    public static boolean asBoolean(Object value, boolean defaultValue) {
        Boolean bool = asBoolean(value);
        return bool != null ? bool.booleanValue() : defaultValue;
    }


    /**
     * @param value
     * @return value as String
     */
    public static String asString(Object value) {
        if (value == null) {
            return null;
        }
        if (!(value instanceof String)) {
            return value.toString();
        }
        String value_ = ((String) value).trim();
        if (value_.length() == 0) {
            return null;
        }
        
        //FGL 20180216: Dieser Fall ist verzichtbar beim Reengineerng
//        if (value_.startsWith("#{messages['") || value_.startsWith("#{msg['")) {
//            MessageService messageService = (MessageService) SpringApplicationContext.getBean(Constants.SERVICES.messageService.name());
//            int beginIndex = value_.indexOf("['");
//            int endIndex = value_.indexOf("']");
//            value_ = messageService.getMessage(value_.substring(beginIndex + 2, endIndex), HisLocaleHolder.getCurrentAppLocale());
//        }
        return value_;
    }

    /**
     * @param value
     * @param defaultValue
     * @return value as String
     */
    public static String asString(Object value, String defaultValue) {
        String string = asString(value);
        return string != null ? string : defaultValue;
    }

    /**
     * Transforms a collection of objects to a list of other objects. Therefore a specific
     * converting functions is mandatory.
     *
     * @param col a collection
     * @param converterFunction function which converts the elements of the given collection to another type
     * @return new collection
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> List<T> transform(Collection<? extends Object> col, ConverterFunctions converterFunction) {
        return (List<T>) Lists.newArrayList(Collections2.transform(col, converterFunction.convert()));

    }

    /**
     * Convert {@code value} to BigDecimal, if {@code value} is a Long object. NULL and BigDecimals stay unchanged and other objects are not expected (throw a ClassCastException).
     * @param value
     * @return {@code value} as BigDecimal
     */
    public static BigDecimal asBigDecimal(Object value) {
        BigDecimal retVal = null;
        if (value != null && value instanceof Long) {
            retVal = BigDecimal.valueOf(((Long) value).longValue());
        } else {
            retVal = (BigDecimal) value;
        }
        return retVal;
    }

}

