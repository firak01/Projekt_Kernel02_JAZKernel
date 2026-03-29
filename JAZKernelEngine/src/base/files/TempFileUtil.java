package base.files;
//package de.his.core.base.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Logger;

public class TempFileUtil {
    @SuppressWarnings("unused")
    private static Logger logger = Logger.getLogger(TempFileUtil.class);

    /**
     * Creates Directory with a random generated Name.
     * @throws IOException
     */
    public static File createTempDir() throws IOException {
        Path path = Files.createTempDirectory("tmp");
        return path.toFile();
    }

    public static String inTempdirIfRelative(String pathString) {
        return FilenameUtil.containsDirSeparator(pathString) ? pathString : getJavaTempDir() + "/" + pathString;
    }

    private static String getJavaTempDir() {
        return System.getProperty("java.io.tmpdir").trim();
    }
}
