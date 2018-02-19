package base.reflection;
//package de.his.core.base.reflection;

/**
 * Exception, die geworfen wird, wenn mehrere passende Methoden gefunden wurden.
 * 
 */
public class AmbiguousMethodException extends RuntimeException {

    private static final long serialVersionUID = -3784314691276921477L;

    public AmbiguousMethodException() {
    }

    public AmbiguousMethodException(String errorText) {
        super(errorText);
    }

    public AmbiguousMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public AmbiguousMethodException(Throwable cause) {
        super(cause);
    }

}
