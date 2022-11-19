package basic.zBasic.datatype.string;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.Tika;
import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

import com.google.common.collect.Sets;
//import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author wang.qingsong
 * Created on 2021/09/16
 */
//@Slf4j
public class FileEncodingUtil {

    public static boolean isFileEncodingUtf8(File inputFile) throws IOException {
        return isUtf8(getFileEncoding(inputFile));
    }

    
    /** Merke: Es scheint so zu sein, das gilt: Je laenger die Datei, desto genauer die Encoding Detection
     * @param file
     * @return
     * @throws IOException
     * @author Fritz Lindhauer, 19.11.2022, 12:33:22
     */
    public static String getFileEncoding(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            return getInputStreamEncoding(fileInputStream);
        }
    }

    public static String getInputStreamEncoding(InputStream input) throws IOException {
        CharsetDetector charsetDetector = new CharsetDetector();
        BufferedInputStream buffInput = null; // close new BufferedInputStream
        try {
            charsetDetector.setText(
                input instanceof BufferedInputStream ? input : (buffInput = new BufferedInputStream(input)));
            charsetDetector.enableInputFilter(true);
            CharsetMatch cm = charsetDetector.detect();
            return cm.getName();
        } finally {
            IOUtils.closeQuietly(buffInput);
        }
    }
    
    
    /**
     * @param inputFile
     * @param outputFile
     * @throws IOException
     * @author Fritz Lindhauer, 19.11.2022, 12:14:36
     */
    public static void convertFileToANSI(File inputFile, File outputFile) throws IOException {
        final String encoding = getFileEncoding(inputFile);
        if (StringUtils.isEmpty(encoding)) {
            throw new RuntimeException("inputFile encoding can not parsed!");
        }
        if (isAnsi(encoding)) {
            throw new RuntimeException("inputFile is already ANSI, no need convert.");
        }

        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             InputStreamReader inputReader = new InputStreamReader(inputStream, encoding);
             // output
             FileOutputStream outputStream = new FileOutputStream(outputFile);
             OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, StandardCharsets.ISO_8859_1)) {
            IOUtils.copy(inputReader, outputWriter);
        }
    }

    private static boolean isAnsi(String encoding) {
        final Set<String> aliases = Sets.newHashSet("ISO_8859_1", "cp1252");
        for (String ansi : aliases) {
            if (StringUtils.equalsIgnoreCase(ansi, encoding)) {
                return true;
            }
        }
        return false;
    }
    
    //######################################################################
    public static void convertFileToUtf8(File inputFile, File outputFile) throws IOException {
        final String encoding = getFileEncoding(inputFile);
        if (StringUtils.isEmpty(encoding)) {
            throw new RuntimeException("inputFile encoding can not parsed!");
        }
        if (isUtf8(encoding)) {
            throw new RuntimeException("inputFile is already utf8, no need convert.");
        }

        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             InputStreamReader inputReader = new InputStreamReader(inputStream, encoding);
             // output
             FileOutputStream outputStream = new FileOutputStream(outputFile);
             OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            IOUtils.copy(inputReader, outputWriter);
        }
    }

    private static boolean isUtf8(String encoding) {
        final Set<String> aliases = Sets.newHashSet("utf-8", "utf_8", "utf8");
        for (String utf8 : aliases) {
            if (StringUtils.equalsIgnoreCase(utf8, encoding)) {
                return true;
            }
        }
        return false;
    }
}
