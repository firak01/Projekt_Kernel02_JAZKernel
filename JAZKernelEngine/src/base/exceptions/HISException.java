package base.exceptions;
//package de.his.core.base.exceptions;

/**
 * Allgemeine Exception zum Abfangen von HIS-spezifischen Exceptions.
 *
 * @author <a href="mailto:leben.exe@gmx.net">David Roden</a>, <a href="http://www.his.de/">HIS eG</a>
 * @version $Id: HISException.java,v 1.2 2017/03/16 13:57:28 siegel#his.de Exp $
 */
public class HISException extends Exception {

    private static final long serialVersionUID = -8335388992128921519L;

    /**
     * Erstellt eine Exception ohne Meldung.
     */
    public HISException() {
    }

    /**
     * Erstellt eine Exception mit der angegebenen Fehlermeldung.
     *
     * @param errorText Fehlermeldung
     */
    public HISException(String errorText) {
        super(errorText);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval
     *                by the Throwable.getMessage() method).
     * @param cause	  The cause (which is saved for later retrieval by the
     *                Throwable.getCause() method). (A null value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     */
    public HISException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * (cause==null ? null : cause.toString()) (which typically contains the class and
     * detail message of cause).
     *
     * @param cause  The cause (which is saved for later retrieval by the
     *               Throwable.getCause() method). (A null value is permitted, and
     *               indicates that the cause is nonexistent or unknown.)
     */
    public HISException(Throwable cause) {
        super(cause);
    }

}
