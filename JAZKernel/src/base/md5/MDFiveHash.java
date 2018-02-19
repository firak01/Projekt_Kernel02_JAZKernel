package base.md5;
//package de.his.core.base.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * MD5 Encoding
 *
 * @author Wenskat, Brummermann
 */

public class MDFiveHash {
    private static final String MD_INSTANCE_NAME = "MD5";

    // SALT_PREFIX von '$1$' in '$apr1$' geaendert (htpasswd(2) konform)
    private static final String SALT_PREFIX = "$apr1$";

    private static final String SALT_CHARS = "./0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[64];

    private static Logger logger = Logger.getLogger(MDFiveHash.class);

    /**
     * Private function to turn md5 result to 32 hex-digit string
     */
    public static String getHex(byte[] hash) {
        final StringBuffer buf = new StringBuffer(hash.length * 2);

        for (int i = 0; i < hash.length; i++) {
            final byte current = hash[i];
            if ((current & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(current & 0xff, 16));
        }

        return buf.toString();
    }

    /**
     * Take a byte array and return its md5 hash as a hex digit string
     *
     * @param data Daten
     * @return Hash in Hexadezimal Darstellung
     */
    public static String hash(byte[] data) {
        return hash(data, 0, data.length);
    }

    /**
     * Take a byte array and return its md5 hash as a hex digit string
     *
     * @param data Daten
     * @param offset Start im Datenarray
     * @param length L&auml;nge der Daten
     * @return Hash in Hexadezimal Darstellung
     */
    public static String hash(byte[] data, int offset, int length) {
        byte[] result = EMPTY_BYTE_ARRAY;
        try {
            final MessageDigest md = MessageDigest.getInstance(MD_INSTANCE_NAME);
            md.reset();
            md.update(data, offset, length);
            final byte[] rslt = md.digest();
            result = rslt;

        } catch (final Exception ee) {
            logger.error(ee, ee);
        }
        return getHex(result);
    }

    /**
     * Take a string and return its md5 hash as a hex digit string
     *
     * @param arg zu hashender String
     * @return gehashter String
     */
    public static String hash(String arg) {
        try {
            return hash(arg.getBytes("UTF-8"));
        } catch (final UnsupportedEncodingException e) {
            // cannot happen because UTF-8 is required by the Java Spec
            return null;
        }
    }

    /**
     * Verhindert Wörterbuchangriffe auf gehashte Passwörter.
     * 1.) Geniert einen Salt
     * 2.) Bildet MD5 Hash aus Passwort + Salt
     * 3.) Ueberprüft ob Hash+Salt bereits in der Datenbank ist, wenn ja, starte wieder mit 1.)
     *
     * @param password eingegebenes Passwort durch den Nutzer
     * @return NewpasswordHashWithSalt in htpasswd2 konformer Notation $apr1$salt$mitSaltGehashtesPasswort
     * @throws UnsupportedEncodingException das JDK kann kein UTF-8
     * @throws NoSuchAlgorithmException das JDK kann kein MD5
     */
    public static String hashWithSalt(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        final StringBuilder salt = new StringBuilder("");
        final int step = SALT_CHARS.length();

        final SecureRandom generator = new SecureRandom();
        for (int i = 0; i < 8; i++) {
            final int sign = generator.nextInt(step);
            salt.append(SALT_CHARS.substring(sign, sign + 1));
        }
        return MDFiveHash.hashWithSalt(password, salt.toString());
    }

    /**
     * Hasht ein Passwort mit einem Salt
     *
     * @param password Passwort
     * @param salt     Salt
     * @return gesalteter Hash des Passworts
     * @throws UnsupportedEncodingException das JDK kann kein UTF-8
     * @throws NoSuchAlgorithmException das JDK kann kein MD5
     */
    public static String hashWithSalt(String password, String salt) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Strings in UTF-8 codierte byte-Array konvertieren
        final byte[] saltBytes = salt.getBytes("UTF-8");
        final byte[] passwordBytes = password.getBytes("UTF-8");

        // Hier salt und passwort zum Testen eingeben
        // saltBy ="ZYtEg...".getBytes("UTF-8");
        // passwordBy ="ääääää%&".getBytes("UTF-8");

        // Hash-Funktiony aufrufen
        final MD5Crypt md5Crypt = new MD5Crypt(SALT_PREFIX);
        return new String(md5Crypt.crypt(passwordBytes, saltBytes)).trim();
    }

    /**
     * Compares a password to a stored hash with salt.
     *
     * @param hash salted hash  (in htpasswd-format)
     * @param password the password to verify
     * @return true, wenn das Passwort stimmt, sonst false
     */
    public static boolean compareSaltedHash(String hash, String password) {
        if (StringUtils.isBlank(hash) || StringUtils.isBlank(password)) {
            return false;
        }

        // Extrahieren des Salts.
        if (hash.length() < SALT_PREFIX.length()) {
            return false;
        }
        String salt = hash.substring(SALT_PREFIX.length());
        final int pos = salt.indexOf('$');
        if (pos < 0) {
            return false;
        }
        salt = salt.substring(0, pos);

        try {
            final String newHash = hashWithSalt(password, salt);
            return newHash.equals(hash);
        } catch (final Exception e) {
            logger.error("Fehler beim Hashen des Passworts: ", e);
            return false;
        }
    }
}
