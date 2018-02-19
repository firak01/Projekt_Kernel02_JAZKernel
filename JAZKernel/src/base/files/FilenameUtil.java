package base.files;
//package de.his.core.base.files;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.base.Optional;




//import de.his.core.base.numeric.NumberUtil;
import base.numeric.NumberUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

//FGL 20180216 Rausgenommen beim reeengineering
//import de.his.core.common.globalConfiguration.GlobalConfiguration;

/**
 * Tools that manipulate filename strings without accessing the filesystem.
 * No File objects please.
 *
 * @author hsi
 *
 */
public class FilenameUtil {

    /**
     * Methode zum Erstellen von zufälligen Dateinamen.
     * @param prefix Start-Zeichenfolge des Dateinamens
     * @return Dateiname
     */
    public static String generateFileName(String prefix) {
        return prefix + NumberUtil.nonnegativeRandomNumber();
    }

    /**
     * Hängt an einen Dateinamen vor der Erweiterung ein Kürzel an.
     *
     * @param filename
     * @param tokenToAdd
     * @return erweiterter Dateiname
     */
    public static String appendTokenBeforeSuffix(String filename, String tokenToAdd) {
        final int letzterPunkt = filename.lastIndexOf(".");
        final boolean filenameHasSuffix = letzterPunkt > filename.lastIndexOf("/");
        final String toAppend = "_" + tokenToAdd;
        if (filenameHasSuffix) {
            return filename.substring(0, letzterPunkt) + toAppend + filename.substring(letzterPunkt);
        }
        return filename + toAppend;
    }



    /**
     * Entfernt Slashes und Backslashes vom Ende von str1 und vom Anfang von str2, und
     * h&auml;ngt diese dann getrennt durch einen Slash aneinander.
     * Diese Methode ist nützlich zum Verbinden von Dateinamen-Komponenten.
     *
     * @param str1 Der vordere Teil
     * @param str2 Der hintere Teil
     * @return Die zwei Teile aneinandergehaengt mit genau einem Slash dazwischen
     */
    public static String concatDirs(String str1, String str2) {
        return removeTrailingSlashes(str1) + "/" + FilenameUtil.removeLeadingSlashes(str2);
    }

    /**
     * Entfernt Slashes und Backslashes vom Ende des übergebenen Strings, nachdem
     * dieser getrimmt worden ist.
     *
     * @param aString Zu behandelnden String
     * @return Input-String ohne angehaengte Slashes oder Backslashes
     */
    public static String removeTrailingSlashes(String aString) {
        String trimmed = StringUtils.trimToEmpty(aString);
        int i = trimmed.length();
        while (i > 0 && isDirSeparator(trimmed.charAt(i - 1))) {
            i--;
        }
        return trimmed.substring(0, i);
    }

    /**
     * Entfernt Slashes und Backslashes vom Anfang des übergebenen Strings, nachdem
     * dieser getrimmt worden ist.
     *
     * @param aString Zu behandelnden String
     * @return Input-String ohne fuehrende Slashes oder Backslashes
     */
    public static String removeLeadingSlashes(String aString) {
        String trimmed = StringUtils.trimToEmpty(aString);
        int i = 0;
        while (i < trimmed.length() && isDirSeparator(trimmed.charAt(i))) {
            i++;
        }
        return trimmed.substring(i);
    }

    public static String relativeFileNameFor(File file, String baseDirectory) {
        return FilenameUtils.separatorsToUnix(StringUtils.removeStart(FilenameUtils.normalize(file.getAbsolutePath()), baseDirectory));
    }

    public static String stripSlashes(String templateName) {
        // es dürfen keine nach Dir-Trennzeichen aussehenden Bezeichnungen
        // vorkommen
        return templateName.replace('/', ' ').replace('\\', ' ');
    }

    static boolean containsDirSeparator(String pathString) {
        return pathString.contains("/") || pathString.contains("\\");
    }

    public static boolean isDirSeparator(final char ch) {
        return ch == '/' || ch == '\\';
    }

    public static String removeFileSuffix(String filename) {
        int ix = filename.lastIndexOf(".");
        String tabName = filename.substring(0, ix);
        return tabName;
    }

    public static Optional<String> relativizeToWebInf(File dir) {
        return relativizeToWebInf(dir.getAbsolutePath());
    }

    public static Optional<String> relativizeToWebInf(String dir) {
    	//FGL: 20180216: Rausgenommen beim Reengineering    	
        //Path base = Paths.get(GlobalConfiguration.findQisserver() + "WEB-INF");
    	Path base = Paths.get("WEB-INF");
        Path absolute = Paths.get(dir);
        if (absolute.startsWith(base)) {
            return Optional.of(base.relativize(absolute).toString());
        }
        return Optional.absent();
    }

    /**
     * Checks if a given String array contains any full filename ignoring system specific separators.
     *
     * @param filenameArray an Array containing filenames with paths as Strings
     * @param filename a filename and path to find within {@code filenameArray}
     *
     * @return true, if filename was found within filenameArray (ignoring system specific separators), false otherwise
     */
    public static boolean filenameEqualsAny(String[] filenameArray, String filename) {
        if (ArrayUtils.isEmpty(filenameArray)) {
            return false;
        }
        if (StringUtils.isEmpty(filename)) {
            // XXX why this exception?
            return false;
        }
        for (final String filenameToCheck : filenameArray) {
            if (FilenameUtils.equalsNormalized(filenameToCheck, filename)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Extrahiert den Verzeichnispfad aus einem kompletten Dateinamen.
     *
     * @param fileName Dateiname
     * @return Verzeichnispfad
     */
    public static String getDirectory(String fileName) {
        final int lastIndex = Math.max(fileName.lastIndexOf("/"), fileName.lastIndexOf("\\"));
        if (lastIndex < 0) {
            return "";
        }
        return fileName.substring(0, lastIndex);
    }

    public static String appendFileExtensionIfNotPresent(String aString, final String suffix) {
        return aString.endsWith(suffix) ? aString : aString + suffix;
    }

    /**
     * Joins path elements with the OS specific path separator.
     */
    public static String joinToOsPath(String... pathElements) {
    	//FGL: Erst ab Java 8: return String.join(File.separator, pathElements);
    	//FGL Ersetzt  durch einen ZZZ Klasse
    	String sReturn = new String("");
	    main:{
	    	try {	       	
	       	sReturn = StringZZZ.join(pathElements, File.separator, "BEFORE");
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}
	    }//End main
    	return sReturn;
    }

    public static String filenameWithoutPath(String fileName) {
        int index = fileName.lastIndexOf("/");
        if (index < 0) {
            index = fileName.lastIndexOf("\\");
        }
        String fileNameOnly = fileName.substring(index);
        return fileNameOnly;
    }

    /**
     * Umsetzten der Trennzeichen im String entspr. dem BetreriebsSystem
     * !ACHTUNG Trennzeichen am Anfang des Pfades(UNIX-Absolutpath) wird
     * entfernt!
     *
     * @param inPfad
     *            String
     * @return String
     */
    public static String convertTrennzeichen_ToUnix(String inPfad) {
        final String t = "/";
        // --- Wenn andere Trennzeichne verwendet wurden dann umsetzten
        String trz = "/";
        if (t.equals("/")) {
            trz = "\\";
        }
        // --- Zerlegen des Pfades in Teilstrings und wierder zusammensetzen mit
        // neuem Trennzeiochen
        // --- da inPfad.replaceAll(trz,t); funktioniert nicht
        final StringTokenizer st = new StringTokenizer(inPfad, trz, false);
        String rs = "";
        int i = 0;
        while (st.hasMoreElements()) {
            if (i > 0) {
                rs += t;
            }
            i++;
            rs += st.nextElement().toString();
        }
        return rs;
    
    }

}

