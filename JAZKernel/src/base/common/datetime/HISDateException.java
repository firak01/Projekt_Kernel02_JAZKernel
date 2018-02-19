package base.common.datetime;
//package de.his.core.common.datetime;

//import de.his.core.base.exceptions.HISException;
import base.exceptions.HISException;

/**
 * Exception fuer Probleme mit Datumsangaben.
 *
 * @author neumann
 */
public class HISDateException extends HISException {

    private static final long serialVersionUID = 6083108680728704791L;

    /**
     * Konstruktor
     * @param msg Beschreibung der Ursache fuer die Exception
     */
    public HISDateException(String msg) {
        super(msg);
    }

    /**
     * Konstruktor
     *
     * @param msg Beschreibung der Ursache fuer die Exception
     * @param cause Ausloesende Exception
     */
    public HISDateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

