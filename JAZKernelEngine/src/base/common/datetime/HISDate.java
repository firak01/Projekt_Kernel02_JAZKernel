package base.common.datetime;
//package de.his.core.common.datetime;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

//import de.his.core.base.exceptions.HISException;
import base.exceptions.HISException;
//import de.his.core.base.strings.StringUtil;
import base.strings.StringUtil;

/**
 * Klasse für "reine" Datumswerte, d.h. ohne zusätzliche Zeitangaben.
 *
 * Intern werden Jahres-, Monats- und Tagesnummern genauso verwaltet wie
 * wir sie in einem Datum schreiben wuerden. Das Datum 24.07.1993 wuerde
 * also intern die Werte year=1993, month=7 und day=24 produzieren.
 *
 * Man beachte, dass java.util.Calendar Tage ab 0 zaehlt (6 bedeutet Juli),
 * und java.util.Date ausserdem von den Jahren 1900 abzieht (93 bedeutet 1993).
 *
 * @author Strube
 * @version $Revision: 1.10 $
 */
public class HISDate implements Comparable<HISDate> {
    private static Logger logger = Logger.getLogger(HISDate.class);

    /**
     * Dummy für fehlerhafte Datumswerte ist der 06.06.2079 (frueher: 31.12.9999).
     * Dies ist der groesste von Microsoft SQL-Server SMALLDATETIME akzeptierte Wert.
     * Der Datentyp darf nicht java.util.Date sein, da dieser von Microsoft SQL-Server
     * nicht unterstuetzt wird.
     *
     * ACHTUNG: Die Verwendung eines Dummy-Werts bei fehlerhaften Werten ist nur ein Provisorium,
     * fehlerhafte Zeitwerte sollten von den Anwendungen bei der Eingabe abgefangen werden !!
     *
     * @see "Hiszilla Nr. 2745"
     */
    public static final java.sql.Date SQL_DATE_ERROR_DUMMY = java.sql.Date.valueOf("2079-06-06");


    // Die Menge der als Separatoren erlaubten Zeichen.
    private static final String ALLOWED_SEPARATORS = ".-/";

    private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;

    private static final String[] MONTH_SHORT = { "\r\n", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    public final int year;

    private final int month;

    private final int day;

    private final int weekday;

    private final int weekOfYear;

    private final int weekOfMonth;

    /**
     * Liefert das aktuelle Datum. Ein solches Objekt kann z.B. an Velocity übergeben werden,
     * so dass die Funktionalität dieser Klasse in Templates genutzt werden kann.
     */
    public HISDate() {
        this(new Date());
    }

    /**
     * Erzeugt ein HISDate-Objekt aus einem java.util.Date-Objekt.
     * @param date Ein java.util.Date-Objekt
     */
    public HISDate(Date date) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
        Calendar cal = df.getCalendar();
        cal.setTime(date);
        this.year = cal.get(Calendar.YEAR);
        //logger.debug("year = " + year);
        this.month = cal.get(Calendar.MONTH) + 1;
        //logger.debug("month = " + month);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        //logger.debug("day = " + day);
        this.weekday = cal.get(Calendar.DAY_OF_WEEK);
        this.weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        this.weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * Erzeugt ein HISDate-Objekt mit den gegebenen Angaben für Jahr, Monat und Tag.
     * @param d Tag (1..31)
     * @param m Monat (1..12)
     * @param y Jahr
     */
    public HISDate(int y, int m, int d) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMAN);
        Calendar cal = df.getCalendar();
        cal.set(y, m - 1, d);
        this.year = y;
        this.month = m;
        this.day = d;
        this.weekday = cal.get(Calendar.DAY_OF_WEEK);
        this.weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
        this.weekOfMonth = cal.get(Calendar.WEEK_OF_MONTH);
    }


    /**
     * Factory-Methode zur Erzeugung eines HISDate-Objekts für Argumente
     * vom Typ Object. Diese ruft speziellere Implementierungen auf, oder wirft eine
     * Exception, wenn keine spezifische Implementierung für den Datentyp des Arguments
     * vorhanden ist.
     *
     * @param dateObj Ein Objekt, das ein Datum beschreibt.
     * @return HISDate-Objekt
     * @throws HISDateException wenn das Argument nicht als Datumsangabe
     * interpretiert werden konnte.
     */
    public static HISDate valueOf(Object dateObj) throws HISDateException {
        //logger.debug("dateObj of instance " + dateObj.getClass().getName());
        if (dateObj instanceof java.sql.Time) {
            logger.debug("Objekt vom Typ java.sql.Time hat keine Datumsangaben...");
            return null;
        }
        if (dateObj instanceof Date) {
            return HISDate.valueOf((Date) dateObj);
        }
        if (dateObj instanceof String) {
            return HISDate.valueOf((String) dateObj);
        }
        if (dateObj instanceof Long) {
            return HISDate.valueOf(new Date(((Long) dateObj).longValue()));
        }
        throw new HISDateException("Factory-Methode für Datentyp " + dateObj.getClass().getName() + " noch nicht implementiert!");
    }

    /**
     * Factory-Methode zur Erzeugung eines HISDate-Objekts für Argumente vom Typ java.util.Date
     * und dessen Unterklassen java.sql.Date, java.sql.Time und java.sql.Timestamp.
     *
     * @param d Datums-, Zeit-, oder Zeitstempel-Objekt.
     * @return HISDate-Objekt mit reiner Datumsangabe, oder null, wenn das Argument d ein java.sql.Time-Objekt war...
     */
    public static HISDate valueOf(Date d) {
        HISDate hd = null;
        if (d instanceof java.sql.Time) {
            // Verhindert IllegalArgumentException...
            logger.debug("Argument " + d + " ist reiner Zeitwert, kann keine Datumsangaben extrahieren!");
        } else {
            hd = new HISDate(d);
        }
        return hd;
    }

    /**
     * Factory-Methode zur Erzeugung eines neuen HISDate-Objekts von einem String.
     * Bei dieser Methode wird versucht, das format aus dem Wert zu erraten.
     *
     * @param datStr Datumsangabe als String
     * @return HISDate-Objekt
     * @throws HISDateException wenn das Argument nicht als Datumsangabe
     * interpretiert werden konnte.
     */
    public static HISDate valueOf(String datStr) throws HISDateException {
        return HISDate.valueOf(datStr, null);
    }

    /**
     * Factory-Methode zur Erzeugung eines neuen HISDate-Objekts.
     * Erlaubte Format-Strings haben am Anfang die drei Buchstaben
     * D, M und Y in beliebiger Reihenfolge, und als viertes Zeichen
     * einen der drei erlaubten Separatoren '.', '-' oder '/'.
     *
     * @param date Datumsangabe als String
     * @param format Definition des Datumsformat im String wie "DMY." oder "YMD-"
     * @return HISDate-Objekt
     * @throws HISDateException wenn das Argument nicht als Datumsangabe
     * interpretiert werden konnte.
     */
    public static HISDate valueOf(String date, String format) throws HISDateException {
        HISDate ret = null;
        String datStr = date;
        String formatStr = format;
        if (datStr != null) {
            // Wir haben ein Datum gegeben...
            datStr = format(datStr);
            if (datStr.toUpperCase().equals("TODAY") || datStr.toUpperCase().equals("NOW") || datStr.toUpperCase().equals("CURRENT")) {
                // Schlüsselwort für aktuelles Datum !!
                if (formatStr != null) {
                    logger.debug("Unnötige Format-Angabe für TODAY!");
                }
                // Hole aktuelles Datum:
                ret = HISDate.getToday();
            } else {
                // Kein Schlüsselwort für Datum...
                try {
                    // Betrachte Formatangabe:
                    if (formatStr == null) {
                        // Kein Format gegeben.
                        // Muss Format aus Datumswert erraten:
                        formatStr = HISDate.guessFormat(datStr);
                    } else {
                        // Schlüsselworte ersetzen oder Format validieren:
                        formatStr = HISDate.substKeysCheckFormat(formatStr);
                    } // endif (Formatangabe nicht null)

                    // Wenn wir bis hierhin kommen, haben wir eine
                    // gültige Formatangabe in Grossschrift...

                    // Versuche jetzt, die Werte zu holen:
                    int y = -1;
                    int m = -1;
                    int d = -1;
                    String sep = formatStr.substring(3);
                    StringTokenizer datTokenizer = new StringTokenizer(datStr, sep);
                    for (int i = 0; i < 3; i++) {
                        char component = formatStr.charAt(i);
                        if (!datTokenizer.hasMoreTokens()) {
                            if (component == 'Y') {
                                y = HISDate.getToday().getYear();
                                break;
                            }
                        }
                        String val = datTokenizer.nextToken();
                        switch (component) {
                        case 'D': {
                            d = Integer.parseInt(val);
                            break;
                        }
                        case 'M': {
                            m = ArrayUtils.indexOf(MONTH_SHORT, val);
                            if (m < 0) {
                                m = Integer.parseInt(val);
                            }
                            break;
                        }
                        case 'Y': {
                            int intVal = Integer.parseInt(val);
                            if (val.length() == 2) {
                                // Zweistellige Jahresangaben ergänzen:
                                if (intVal < 50) {
                                    intVal += 2000; // 2000...2049
                                } else {
                                    intVal += 1900; // 1950...1999
                                }
                            }
                            y = intVal;
                            break;
                        }
                        // Andere können wegen checkFormat() nicht mehr vorkommen...
                        } // endswitch (aktuelle Datums-Komponente)
                    } // endfor (drei Datumskomponenten)
                    if (datTokenizer.hasMoreTokens()) {
                        // Fehler, es gibt mehr als zwei Separatoren !!
                        throw new HISException(datStr + " ist ungültiger Datumswert. Ein Datum besteht nur aus drei Komponenten!");
                    }

                    // Datums-Objekt erzeugen:
                    ret = new HISDate(y, m, d);
                } catch (Exception ex) {
                    throw new HISDateException("Fehler beim Erzeugen eines Datums-Objekts aus " + datStr + ": ", ex);
                }
            }
        }
        return ret;
    }

    /**
     * removes non significant time-parts as " 00:00:00" or " 00:00:00.0"
     *
     * @param value
     * @return formatted Value
     */
    private static String format(String value) {
        String formattedValue = value.trim();
        if (formattedValue.endsWith(" 00:00:00")) {
            formattedValue = formattedValue.substring(0, formattedValue.length() - 9);
        } else {
            if (formattedValue.endsWith(" 00:00:00.0")) {
                formattedValue = formattedValue.substring(0, formattedValue.length() - 11);
            }
        }
        return formattedValue;
    }

    /**
     * Ersetzt Schluesselwoerter fuer Datums-Formatangaben und ueberprueft
     * die Korrektheit der Formatangaben.
     *
     * @param format Eine Datums-Formatangabe
     * @return Ueberarbeitete Formatangabe (Ersetzung von Schluesselworten und mehr)
     * @throws HISDateException Wenn das Argument keine gueltige Datums-Formatangabe darstellt
     */
    public static String substKeysCheckFormat(String format) throws HISDateException {
        String formatStr = format;
        // Trimmen und in Großschrift konvertieren:
        formatStr = formatStr.trim().toUpperCase();
        // Schlüsselworte für Format checken und ersetzen:
        if (formatStr.equals("GERMAN")) {
            formatStr = "DMY.";
        } else if (formatStr.equals("ISO")) {
            formatStr = "YMD-";
        } else if (formatStr.equals("ENGLISH")) {
            formatStr = "DMY/";
        } else if (formatStr.equals("US")) {
            formatStr = "MDY/";
            // TODO: Sind die letzten beiden richtig ??
            // Gibts noch andere ?? Mit guessFormat() abgleichen !!!
        } else {
            // Nicht als Schlüsselwort gegebene Formatangabe prüfen...
            HISDate.checkFormat(formatStr);
        }
        return formatStr;
    }

    /**
     * Liefert HISDate-Objekt für heute.
     * @return Aktuelles Datum als HISDate-Objekt
     */
    public static HISDate getToday() {
        Date dat = new Date();
        //logger.debug("dat = " + dat);
        return HISDate.valueOf(dat);
    }

    /**
     * Versucht aus Datumswert das Format zu erraten.
     * @param datStr Eine Datumsangabe als String.
     * @return Die aus der Datumsangabe abgeleitete Datums-Formatangabe
     * @throws HISDateException wenn das Argument nicht als Datumsangabe
     * interpretiert werden konnte.
     */
    public static String guessFormat(String datStr) throws HISDateException {
        String formatStr = null;

        // Prüfe Separatoren:
        char sep = 0;
        int nofSeparatorTypes = 0;
        for (int i = 0; i < ALLOWED_SEPARATORS.length(); i++) {
            char actualSep = ALLOWED_SEPARATORS.charAt(i);
            if (datStr.indexOf(actualSep) > -1) {
                // Potentieller Separator kommt vor...
                nofSeparatorTypes++;
                sep = actualSep;
            }
        }
        if (nofSeparatorTypes == 0) {
            throw new HISDateException("Datumsangabe " + datStr + " enthält kein erlaubtes Separator-Zeichen!");
        } else if (nofSeparatorTypes > 1) {
            throw new HISDateException("Datumsangabe " + datStr + " enthält mehrere erlaubte Separator-Zeichen!");
        }

        // Es kommt nur ein erlaubter Separator vor. Auswertung:
        // TODO: Mit substKeysCheckFormat() abgleichen !!
        if (sep == '.') {
            formatStr = "DMY."; // German
        } else if (sep == '-') {
            formatStr = "YMD-"; // ISO
        } else if (sep == '/') {
            // Bei '/' isses ein bisschen kniffliger...
            StringTokenizer datTokenizer = new StringTokenizer(datStr, "/");
            String val1 = datTokenizer.nextToken();
            int l1 = val1.length();
            String val2 = datTokenizer.nextToken();
            int l2 = val2.length();
            String val3 = datTokenizer.nextToken();
            int l3 = val3.length();
            if ((l1 > 2) || ((l1 == 2) && (l2 < 2) && (l3 < 2))) {
                formatStr = "YMD/";
            } else if ((l3 > 2) || ((l3 == 2) && (l1 < 2) && (l2 < 2))) {
                formatStr = "DMY/";
            } else {
                throw new HISDateException("Datumsangabe " + datStr + " ist mehrdeutig, kann Format nicht ableiten!");
            }
        } else {
            throw new HISDateException("Auswertung für Separator-Typ noch nicht implementiert!");
        }

        return formatStr;
    }

    /**
     * Überprüft Korrektheit von nicht-null-Formatsangaben.
     * @param formatStr Datums-formatangabe als String
     * @throws HISDateException Wenn das Argument keine gueltige Datums-Formatangabe darstellt
     */
    public static void checkFormat(String formatStr) throws HISDateException {
        // Länge der Formatangabe prüfen:
        if (formatStr.length() != 4) {
            throw new HISDateException("Datumsformat-Angabe " + formatStr + " hat ungültige Länge! Erwartet wird ein Format wie \"DMY.\"");
        }
        // Hole und prüfe Separator:
        String sep = formatStr.substring(3);
        if (ALLOWED_SEPARATORS.indexOf(sep) < 0) {
            throw new HISDateException("Ungültiger Separator in Datumsformat-Angabe " + formatStr + " enthält ungültigen Separator! Erlaubt sind \"" + ALLOWED_SEPARATORS + "\".");
        }
        // Prüfe Komponenten (Jahr, Monat, Tag):
        String components = formatStr.substring(0, 3);
        if ((components.indexOf('Y') < 0) || (components.indexOf('M') < 0) || (components.indexOf('D') < 0)) {
            throw new HISDateException("Datumsformat-Angabe " + formatStr + " enthält ungültige Komponenten-Bezeichner! Erlaubt sind nur Y, M und D!");
        }
    }

    /**
     * Liefert den letzten Tag eines Monats unter Berücksichtigung von Schaltjahren.
     * @param month Der Monat (1..12), dessen letzter Tag gewuenscht ist
     * @param year Das zu betrachtende Jahr
     * @return Letzter Tag des Monats als int (28, 29, 30 oder 31)
     */
    public static int getMaxDay(int month, int year) {
        int maxDay = 31;
        if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            maxDay = 30;
        } else if (month == 2) {
            // Februar...
            maxDay = 28;
            if (new GregorianCalendar().isLeapYear(year)) {
                // Schaltjahr hat 29 Tage im Februar...
                maxDay = 29;
            }
        }
        return maxDay;
    }

    /**
     * Überprüft, ob ein Datum in korrektem Format wirklich ein existierender Kalendertag ist.
     * @throws HISDateException wenn die Datumsangabe keinen erlaubten Kalendertag darstellt.
     */
    public void validate() throws HISDateException {
        if ((this.month < 1) || (this.month > 12) || (this.day < 1) || (this.day > HISDate.getMaxDay(this.month, this.year))) {
            throw new HISDateException("Unzulässiges Datum: " + this.day + "." + this.month + "." + this.year);
        }
    }

    /**
     * zieht von einem Datum einen Tag ab
     * @param date
     * @return HISDate
     */
    public static HISDate decreaseDate(HISDate date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        //keine Monatsgrenze überschritten
        if (day > 1) {
            day--;
        } else {
            // überschreiten einer Monatsgrenze
            if (day == 1) {
                //vormonat hat 31 Tage
                if (month == 2 || month == 4 || month == 6 || month == 8 || month == 9 || month == 11) {
                    month--;
                    day = 31;
                }
                //Jahreswechsel
                else if (month == 1) {
                    day = 31;
                    month = 12;
                    year--;
                }
                // Vormonat ist Februar
                else if (month == 3) {
                    month = 2;
                    if (new GregorianCalendar().isLeapYear(year)) {
                        day = 29;
                    } else {
                        day = 28;
                    }
                } else {
                    month--;
                    day = 30;
                }
            }
        }
        return new HISDate(year, month, day);
    }
    /**
     * erhöht ein Datum um einen Tag
     * @param date
     * @return increased Date
     */
    public static HISDate increaseDate(HISDate date) {
        int day = date.getDay();
        int month = date.getMonth();
        int year = date.getYear();
        //keine Monatsgrenze überschreiten
        //31 Tage
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (day < 31) {
                day++;
            } else {
                day = 1;
                if (month < 12) {
                    month++;
                } else {
                    month = 1;
                    year++;
                }
            }
        }
        //Februar
        else if (month == 2) {
            if (day < 28) {
                day++;
            }
            if (new GregorianCalendar().isLeapYear(year)) {
                if (day < 29) {
                    day++;
                } else {
                    day = 1;
                    month++;
                }
            } else if (day == 28) {
                day = 1;
                month++;
            }
        }
        // die restlichen Monate
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day < 30) {
                day++;
            } else {
                day = 1;
                if (month < 12) {
                    month++;
                } else {
                    month = 1;
                    year++;
                }
            }
        }
        return new HISDate(year, month, day);
    }


    /**
     * Ausgabe als String im angegebenen Format.
     * @param format Gewünschtes Ausgabe-Format
     * @return Im gegebenen Format formatierte String-Repräsentation dieses Datums
     * @throws HISDateException Wenn kein Format angegeben
     */
    public String toString(String format) throws HISDateException {
        String formatStr = format;
        StringBuilder ret = new StringBuilder();
        if (formatStr == null) {
            throw new HISDateException("Kein Ausgabeformat angegeben!");
        }

        formatStr = substKeysCheckFormat(formatStr);
        String components = formatStr.substring(0, 3);
        char sep = formatStr.charAt(3);
        for (int i = 0; i < 3; i++) {
            char component = components.charAt(i);
            switch (component) {
            case 'Y': {
                ret.append(StringUtil.alignRight(String.valueOf(this.year), "0000"));
                break;
            }
            case 'M': {
                ret.append(StringUtil.alignRight(String.valueOf(this.month), "00"));
                break;
            }
            case 'D': {
                ret.append(StringUtil.alignRight(String.valueOf(this.day), "00"));
                break;
            }
            } // endswitch (component)
            if (i < 2) {
                // Separator zwischenschieben:
                ret.append(sep);
            }
        } // endfor (drei Komponenten)
        return ret.toString();
    }

    /**
     * Gibt dieses Datum als String zur Ausgabe auf dem Bildschirm oder in Dateien zurueck.
     * Bisher wird nur das deutsche Format "dd.mm.yyyy" unterstuetzt.
     * @return Dieses Datum als Ausgabestring im deutschen Format
     */
    public String toDisplayString() {
        String ret = null;
        try {
            ret = this.toString("DMY.");
        } catch (Exception ex) {/* Exception kann nicht auftreten */
        }
        return ret;
    }

    /**
     * Gibt das uebergebene Datum als String zur Ausgabe auf dem Bildschirm oder
     * in Dateien zurueck. Zur Zeit wird hierzu nur deutsches Datumsformat unterstuetzt.
     * Wenn das Argument null war, wird ein Leerstring zurueckgegeben.
     * @param date Eine Datumsangabe als HISDate-Objekt
     * @return Gegebenes Datum als Ausgabestring im deutschen Format
     */
    public static String toDisplayString(HISDate date) {
        String foo = "";
        if (date != null) {
            foo = date.toDisplayString();
        }

        return foo;
    }

    /**
     * Ausgabe als String im ISO-Format, passend für die Übergabe von
     * Datumswerten über die JDBC-Schnittstelle.
     * @return Dieses Datum als String zum Einfuegen in SQL-Befehle
     */
    public String toSQLString() {
        String ret = null;
        try {
            ret = this.toString("YMD-");
        } catch (Exception ex) {/* Exception kann nicht auftreten */
        }
        return ret;
    }


    /**
     * Ausgabe dieses Datums als java.sql.Date-Wert.
     * @return Dieses Datum als java.sql.Date-Objekt, geeignet zur
     * Uebergabe an PreparedStatements.
     */
    public java.sql.Date toSQLDate() {
        String isoStr = this.toSQLString();
        return java.sql.Date.valueOf(isoStr);
        //return new GregorianCalendar(this.year, this.month, this.day).getTime();
    }

    /**
     * Vergleicht dieses Datum mit dem Argument.
     *
     * @param d2 Vergleichsdatum
     * @return <0 / 0 / >0 wenn dieses Datum kleiner / gleich / größer als d2
     */
    public int compare(HISDate d2) {
        int cmpResult = -1;
        if (d2 != null) {
            // Vergleichsdatum ist gegeben
            cmpResult = this.year - d2.year;
            if (cmpResult == 0) {
                cmpResult = this.month - d2.month;
                if (cmpResult == 0) {
                    cmpResult = this.day - d2.day;
                }
            }
        } // else: Vergleichsdatum nicht vorhanden, deshalb größer...

        return cmpResult;
    }

    /**
     * Vergleicht zwei als Strings gegebene Datumsangaben.
     * Diese Methode ist gut aus Templates heraus zu verwenden.
     *
     * @param date1 1. Datumsangabe
     * @param date2 2. Datumsangabe
     * @return <0/0/>0 wenn die erste Datumsangabe kleiner/gleich/groesser
     * der zweiten Angabe ist; -11111 bei einem Fehler
     */
    public static int compareDates(String date1, String date2) {
        int cmpResult = -11111; // ERROR
        try {
            HISDate d1 = HISDate.valueOf(date1);
            HISDate d2 = HISDate.valueOf(date2);
            cmpResult = d1.compare(d2);
        } catch (Exception e) {
            logger.error("compareDates(): Mindestens einer der zwei Datumswerte ist ungueltig: " + date1 + ", " + date2);
            cmpResult = -11111;
        }
        return cmpResult;
    }

    /**
     * Vergleicht dieses Datum mit dem gegebenen Vergleichsdatum.
     *
     * @param date Vergleichsdatum.
     * @return <0/0/>0 wenn dieses Datum vor/gleich/nach dem Vergleichsdatum ist,
     * -11111 wenn das Vergleichsdatum kein gueltiges Datum ist.
     */
    @Override
    public int compareTo(HISDate date) {
        int cmpResult = -11111; // ERROR
        try {
            cmpResult = this.compare(date);
        } catch (Exception e) {
            logger.error("compareTo(): Ungültiges Datum: " + date);
            cmpResult = -11111;
        }
        return cmpResult;
    }

    /**
     * Vergleicht dieses Datum mit dem Vergleichsobjekt.
     *
     * @param o Vergleichsdatum.
     * @return <0/0/>0 wenn dieses Datum vor/gleich/nach dem Vergleichsdatum ist,
     * -11111 wenn das Vergleichsdatum kein gueltiges Datum ist.
     */
    public int compareToObject(Object o) {
        if (o instanceof HISDate) {
            return compareTo((HISDate) o);
        }
        try {
            return compareTo(HISDate.valueOf(o));
        } catch (HISDateException e0) {
            String oStr = o.toString();
            try {
                return compareTo(HISDate.valueOf(oStr));
            } catch (HISDateException e1) {
                logger.error("compareTo(): Ungültiges Datum: " + oStr);
                return -11111;
            }
        }
    }

    /**
     * Überpr&uuml;ft ob zwei Datumsangaben höchstens diff Monate auseinander sind.
     * @param date1
     * @param date2
     * @param diff
     * @return true, wenn sich date1 und date2 um höchstens 6 Monate unterscheiden
     */
    public static boolean isNearDateInMonths(final Date date1, final Date date2, final int diff) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();

        long l = Math.abs(l1 - l2);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(l);

        GregorianCalendar calendar2 = new GregorianCalendar();
        // 1.1.1970
        calendar2.setTimeInMillis(0);
        calendar2.set(Calendar.MONTH, diff);
        // calendar2 muss größer sein als calendar
        int i = calendar2.compareTo(calendar);
        // logger.debug("------------XXXXXXXXXXXXXXXXXX------- " + new Date(calendar.getTimeInMillis()) + " " + new Date(calendar2.getTimeInMillis()) + "; i=" + i);
        if (i > 0) {
            return true;
        }
        return false;
    }

    /**
     * Überpr&uuml;fung des Datums auf Mindest- und Maximalabstand in Jahren im Vergleich zu einem anderen Datum.
     * Dazu wird gepr&uuml;ft ob das Datum + min Jahre kleiner/gleich als das Vergleichsdatum ist und ob das Datum + max
     * Jahre gr&ouml;sser/gleich als das Vergleichsdatum ist.
     * @param min Minimalwert in Jahren
     * @param max Maximalwert in Jahren
     * @param comp das zweite Datum
     * @return -1 -> Abstand kleiner min, 0 -> OK, 1 -> Abstand gr&ouml;sser max
     */
    public int isMinMaxYearsFromDate(final int min, final int max, final HISDate comp) {
        logger.debug("Methode isDateBetweenMinMaxYears");
        int retVal = -1;
        // Ist das zu prüfende Datum kleiner als das Vergleichsdatum?
        if (this.compareTo(comp) < 0) {
            HISDate minCheck = this.addYears(min);
            // Ist das zu prüfende Datum + min Jahre kleiner/gleich als das Vergleichsdatum?
            if (minCheck.compareTo(comp) <= 0) {
                HISDate maxCheck = this.addYears(max);
                // Ist das zu prüfende Datum + max Jahre grösser/gleich als das Vergleichsdatum?
                if (maxCheck.compareTo(comp) >= 0) {
                    retVal = 0;
                } else {
                    retVal = 1;
                }
            }
        }
        return retVal;
    }

    /**
     * Ermittelt ob dieses Datum gleich einem Vergleichsobjekt ist.
     * @param o Vergleichsobjekt
     * @return true wenn gleich
     */
    @Override
    public boolean equals(Object o) {
        if ((o != null) && (o instanceof HISDate)) {
            return compareTo((HISDate) o) == 0;
        }

        return false;
    }

    /**
     * @return hashCode fuer dieses Datum.
     */
    @Override
    public int hashCode() {
        return getCalendar().hashCode();
    }

    /**
     * Vergleicht dieses Datum mit dem aktuellen Datum.
     *
     * @return <0/0/>0 wenn dieses Datum kleiner/gleich/groesser heute
     */
    public int compareWithToday() {
        return this.compare(HISDate.getToday());
    }

    /**
     * Vergleicht das gegebene Datum mit dem aktuellen Datum.
     * Diese Methode ist gut aus Templates heraus zu verwenden.
     *
     * @param dat Zu vergleichendes Datum als String
     * @return <0/0/>0 wenn die Datumsangabe kleiner/gleich/groesser
     * dem aktuellen Datum ist; -11111 bei einem Fehler
     */
    public static int compareWithToday(String dat) {
        int cmpResult = -11111; // ERROR
        try {
            HISDate d = HISDate.valueOf(dat);
            HISDate today = HISDate.getToday();
            cmpResult = d.compare(today);
        } catch (Exception e) {
            logger.error("compareWithToday(): Ungültiges Datum: " + dat);
            cmpResult = -11111;
        }
        return cmpResult;
    }

    /**
     * Liefert den Wochentag dieses Datums.
     * @return Wochentag als int-Wert (Sonntag = 0; Montag = 1, ... , Samstag = 6)
     */
    public int getWeekDay() {
        return this.weekday - 1;
    }

    /**
     * Liefert den Wochentag dieses Datums in Engl/USA-Form für die Migration in CreateIndividualDate, entspricht k_day_of_week.hiskey_id
     * @return Wochentag als int-Wert (Sonntag = 1; Montag = 2, ... , Samstag = 7)
     */
    public int getWeekDayEngl() {
        return this.weekday;
    }

    /**
     * Liefert das Jahr  dieses Datums.
     * @return Jahr als int-Wert
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Liefert den Monat dieses Datums.
     * @return Monat als int-Wert (Januar = 1,..., Dezember= 12)
     */
    public int getMonth() {
        // ersetzt deprecated Methode Date.getMonth()
        return this.month;
    }

    /**
     * @return Tag im Monat (1..31)
     */
    public int getDay() {
        return this.day;
    }

    /**
     * @return Nummer der Woche im Monat
     */
    public int getWeekOfMonth() {
        return this.weekOfMonth;
    }

    /**
     * Liefert die Nummer der Woche im Jahr dieses Datums.
     * @return Nummer der Woche als int-Wert
     */
    public int getWeekOfYear() {
        return this.weekOfYear;
    }

    /**
     * Bestimmt ob dieses Datum in einer geraden Woche liegt.
     * @return true, wenn das Datum in einer geraden Woche liegt, sonst false.
     */
    public boolean evenWeek() {
        boolean even = false;
        int week = this.getWeekOfYear();
        if ((week % 2) == 0) {
            even = true;
        }
        return even;
    }

    /**
     * &Uuml;berprüft, ob 14t&auml;gl./Einzel-Termine in gleicher Woche liegen
     * @param dateTwo Vergleichsdatum als HISDate-Objekt
     * @return true wenn Datumse in gleicher Woche liegen
     */
    public boolean check14(HISDate dateTwo) {
        boolean bSameWeek = true;
        if (dateTwo != null) {
            boolean evenOne = this.evenWeek();
            boolean evenTwo = dateTwo.evenWeek();
            if (evenOne != evenTwo) {
                bSameWeek = false;
            }
        }
        return bSameWeek;
    }

    /**
     * Ueberprueft, ob zwei Termine in der gleichen Woche liegen.
     * @param dateOne 1. Termin
     * @param dateTwo 2. Termin
     * @return true wenn Datumse in gleicher Woche liegen
     */
    public static boolean check14(String dateOne, String dateTwo) {
        boolean bSameWeek = true;
        try {
            HISDate dateObj = HISDate.valueOf(dateOne);
            HISDate dateObj2 = HISDate.valueOf(dateTwo);
            bSameWeek = dateObj.check14(dateObj2);
        } catch (HISDateException hde) {
            logger.error("check14(String, String): " + hde);
        }

        return bSameWeek;
    }

    /**
     * F&uuml;gt zu diesem Datum numberOfDays-viele Tage hinzu.
     *
     * @param numberOfDays
     * @return Neues Datum.
     */
    public HISDate addDays(int numberOfDays) {
        GregorianCalendar gc = new GregorianCalendar(this.getYear(), this.getMonth() - 1, this.getDay());
        gc.add(Calendar.DAY_OF_MONTH, numberOfDays);
        return new HISDate(gc.getTime());
    }

    /**
     * F&uuml;gt zu diesem Datum numberOfMonths-viele Monate hinzu.
     *
     * @param numberOfMonths
     * @return Neues Datum.
     */
    public HISDate addMonths(int numberOfMonths) {
        GregorianCalendar gc = new GregorianCalendar(this.getYear(), this.getMonth() - 1, this.getDay());
        gc.add(Calendar.MONTH, numberOfMonths);
        return new HISDate(gc.getTime());
    }

    /**
     * F&uuml;gt zu diesem Datum numberOfYears-viele Jahre hinzu.
     * @param numberOfYears
     * @return neues Datum
     */
    public HISDate addYears(int numberOfYears) {
        GregorianCalendar gc = new GregorianCalendar(this.getYear(), this.getMonth() - 1, this.getDay());
        gc.add(Calendar.YEAR, numberOfYears);
        return new HISDate(gc.getTime());
    }

    /**
     * &Uuml;berpr&uuml;fung, ob die Kalenderwoche <code>week</code> innerhalb der Datumsangaben <code>beginDate</code>
     * und <code>endDate</code> liegt.
     * Bsp.: * Datumse: 01.10.2005 und 31.12.2006;
     *       - Woche 44 liefert true
     *       - Woche 12 liefert false
     *       * Datumsangaben: 01.10.2005 und 01.10.2005
     *       - Woche 39 liefert true (der 01.10.2005 liegt in der 39 Woche)
     *       - Woche 38 liefert false
     *
     * @param week       Kalenderwoche
     * @param endDate    Enddatum im HISDate Format
     * @return  true, falls die Kalenderwoche innerhalb der Datumsangaben liegt
     */
    public boolean isWeekBetweenDates(int week, HISDate endDate) {
        boolean val = false;
        Vector<String> weeksVec = new Vector<String>(53);
        try {
            if ((endDate != null) && (this.compare(endDate) <= 0)) {
                GregorianCalendar gcBegin = new GregorianCalendar(this.getYear(), this.getMonth() - 1, this.getDay());
                GregorianCalendar gcEnd = new GregorianCalendar(endDate.getYear(), endDate.getMonth() - 1, endDate.getDay());
                HISDate hDate = null;
                while (gcBegin.before(gcEnd) || gcBegin.equals(gcEnd)) {
                    hDate = new HISDate(gcBegin.getTime());
                    weeksVec.add(Integer.toString(hDate.getWeekOfYear()));
                    gcBegin.add(Calendar.DAY_OF_MONTH, 7);
                }
                // Letztes Datum wurde wegen des add + 7 evtl. nicht hinzugefügt, also nun (evtl. doppelt, das ist aber kein Problem)
                hDate = new HISDate(gcEnd.getTime());
                weeksVec.add(Integer.toString(hDate.getWeekOfYear()));

                if (weeksVec.contains(Integer.toString(week))) {
                    val = true;
                }
            }
        } catch (Exception e) {
            logger.error(e);
        }

        return val;
    }

    /**
     * Ermittelt die Anzahl der vergangenen Tage
     *
     * @return Anzahl der Tage
     */
    public int getDaysSinceStartOfEpoch() {
        long millis = new GregorianCalendar(year, month - 1, day).getTimeInMillis();
        return (int) (millis / MILLIS_PER_DAY);

    }

    /**
     * Gibt einen GregorianCalendar f&uuml;r das aktuelle Datum zur&uuml;ck.
     *
     * @return GregorianCalendar
     */
    public GregorianCalendar getCalendar() {
        return new GregorianCalendar(this.year, this.month - 1, this.day);
    }

    /**
     * @return Dieses Datum als String.
     */
    @Override
    public String toString() {
        return "HISDate<" + toSQLString() + ">";
    }

    /** Map 0->"So", 1->"Mo", 2->"Di", ..., 6->"Sa" */
    private static final String[] daysOfWeek = { "So", "Mo", "Di", "Mi", "Do", "Fr", "Sa" };

    /**
     * Gibt das passende Wochentagskuerzel zurück.
     *
     * @param date
     * @return Wochentagskuerzel (Mo..So).
     */
    public static String getDay(HISDate date) {
        return daysOfWeek[date.getWeekDay()];
    }

    /**
     * Bekommt eine Wochentags-ID (zwischen 0 und 6) und gibt das passende
     * Wochentagskuerzel zurück.
     *
     * @param day Wochentags-ID (0..6).
     * @return Wochentagskuerzel (Mo..So).
     * @throws HISDateException wenn die Wochentags-ID nicht in 0..6 liegt.
     */
    public static String getDayForWeekDay(int day) throws HISDateException {
        if ((day < 0) || (day > 6)) {
            throw new HISDateException("Wochentag-ID liegt nicht zwischen 0 und 6!");
        }
        return daysOfWeek[day];
    }

    /**
     * Berechung des Ostersonntag.
     * @param tmpYear (im Format YYYY)
     * @return Osersonntag
     */
    public static GregorianCalendar getOsterSonntag(int tmpYear) {
        int i = tmpYear % 19;
        int j = tmpYear / 100;
        int k = tmpYear % 100;
        int l = (19 * i + j - (j / 4) - ((j - ((j + 8) / 25) + 1) / 3) + 15) % 30;
        int m = (32 + 2 * (j % 4) + 2 * (k / 4) - l - (k % 4)) % 7;
        int n = l + m - 7 * ((i + 11 * l + 22 * m) / 451) + 114;
        int tmpMonth = n / 31;
        int tmpDay = (n % 31) + 1;

        return new GregorianCalendar(tmpYear, tmpMonth - 1, tmpDay);
    }


    public static void insertFeiertag(HashMap<HISDate, String> feiertagHM, HISDate date, String feiertagName) {
        if (feiertagHM.containsKey(date)) {
            //z. B.  01.05.2008 = Chr. Himmelfahrt und Maifeiertag
            String name = feiertagHM.get(date);
            feiertagHM.put(date, name + " / " + feiertagName);
        } else {
            feiertagHM.put(date, feiertagName);
        }
    }


    /**
     * Calculates the amount of days between two dates.
     *
     * @param dBeginn
     * @param dEnde
     * @return int amount of days to display
     */
    public static int getDayBetweenDates(Date dBeginn, Date dEnde) {
        int iReturn = -1;

        long lngBetween = dEnde.getTime() - dBeginn.getTime();
        lngBetween = ((lngBetween / 1000) / 3600) / 24;

        iReturn = (int) lngBetween;

        return (iReturn);
    }

    /**
     * Calculates the amount of days between two dates.
     *
     * @param dBeginn
     * @param dEnde
     * @return int amount of days to display
     */
    public static int getDayBetweenDates(HISDate dBeginn, HISDate dEnde) {
        return HISDate.getDayBetweenDates(dBeginn.toSQLDate(), dEnde.toSQLDate());
    }

    /**
     * @param startDate
     * @param endDate
     * @return true if the interval between (exclusive) contains only WeekendDays
     */
    public static boolean gapConsistsOfWeekendDays(HISDate startDate, HISDate endDate) {
        // 6=Saturday, 0=Sunday
        // int[] weekdayList = {6,0};
        if (startDate.compareTo(endDate) < 0) {
            HISDate current = startDate.addDays(1);
            while (current.compareTo(endDate) < 0) {
                if (!HISDate.containsWeekendDay(current, current)) {
                    return false;
                }
                current = current.addDays(1);
            }
        }
        return true;
    }

    /**
     * @param startDate
     * @param endDate
     * @return true if the interval contains saturday or sunday
     */
    public static boolean containsWeekendDay(HISDate startDate, HISDate endDate) {
        // 6=Saturday, 0=Sunday
        int[] weekdayList = { 6, 0 };
        return containsAtleastOneWeekdayOfWeekdayList(startDate, endDate, weekdayList);
    }

    /**
     * @param startDate
     * @param endDate
     * @return true if the interval contains saturday or sunday
     */
    public static boolean containsWeekendDay(String startDate, String endDate) {
        // 6=Saturday, 0=Sunday
        int[] weekdayList = { 6, 0 };
        return containsAtleastOneWeekdayOfWeekdayList(startDate, endDate, weekdayList);
    }

    /**
     * @param startDate
     * @param endDate
     * @return true if the interval contains sunday
     */
    public static boolean containsSaturday(HISDate startDate, HISDate endDate) {
        int[] weekdayList = { 6 };
        return containsAtleastOneWeekdayOfWeekdayList(startDate, endDate, weekdayList);
    }

    /**
     * @param startDate
     * @param endDate
     * @return true if the interval contains sunday
     */
    public static boolean containsSunday(HISDate startDate, HISDate endDate) {
        int[] weekdayList = { 0 };
        return containsAtleastOneWeekdayOfWeekdayList(startDate, endDate, weekdayList);
    }

    /**
     * @param startDate
     * @param endDate
     * @return true if the interval contains sunday
     */
    public static boolean containsSunday(String startDate, String endDate) {
        int[] weekdayList = { 0 };
        return containsAtleastOneWeekdayOfWeekdayList(startDate, endDate, weekdayList);
    }

    /**
     * @param startDate
     * @param endDate
     * @param weekdayList
     * @return true if the interval contains at least on day of weekdayList
     */
    public static boolean containsAtleastOneWeekdayOfWeekdayList(String startDate, String endDate, int[] weekdayList) {
        boolean containsWeekendDay = false;
        try {
            HISDate dateStart = HISDate.valueOf(startDate);
            HISDate dateEnd = HISDate.valueOf(endDate);
            containsWeekendDay = containsAtleastOneWeekdayOfWeekdayList(dateStart, dateEnd, weekdayList);
        } catch (HISDateException hde) {
            logger.error("containsAtleastOneWeekdayOfWeekdayList(...): " + hde);
        }
        return containsWeekendDay;
    }

    /**
     * @param startDate
     * @param endDate
     * @param weekdayList
     * @return true if the interval contains at least on day of weekdayList
     */
    public static boolean containsAtleastOneWeekdayOfWeekdayList(HISDate startDate, HISDate endDate, int[] weekdayList) {
        boolean containsWeekendDay = false;
        HISDate hisDate = new HISDate(startDate.toSQLDate());
        if (!(hisDate.compare(endDate) > 0)) {
            while (!(hisDate.compare(endDate) > 0)) {
                // logger.debug("------------- dateStart=" + dateStart + "; day=" + dateStart.getWeekDay());
                for (int weekday : weekdayList) {
                    if (hisDate.getWeekDay() == weekday) {
                        containsWeekendDay = true;
                        break;
                    }
                }
                hisDate = hisDate.addDays(1);
            }
        }
        return containsWeekendDay;
    }

    /**
     * Liefert das als maximal festgelegte Datum zurück
     * @return maximal festgelegte Datum
     */
    public static Date getMaxDate() {
        Calendar maxDateCal = Calendar.getInstance();
        maxDateCal.clear();
        maxDateCal.set(2100, 11, 31);
        return maxDateCal.getTime();
    }
}

