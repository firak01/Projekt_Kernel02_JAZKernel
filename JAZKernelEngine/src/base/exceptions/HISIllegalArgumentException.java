package base.exceptions;
//package de.his.core.base.exceptions;

/**
 * Diese Exception wird geworfen, wenn ein von einer Klasse geforderter Parameter
 * fehlerhaft ist.
 *
 * @author <a href="mailto:leben.exe@gmx.net">David Roden</a>, <a href="http://www.his.de/">HIS eG</a>
 * @version 1.0, 19. November 1998
 */
public class HISIllegalArgumentException extends HISException {

    private static final long serialVersionUID = -4162202650498450104L;

    /**
     * Erstellt eine Exception ohne Fehlermeldung.
     */
    public HISIllegalArgumentException() {
    }

    /**
     * Erstellt eine Exception mit der angegebenen Fehlermeldung.
     *
     * @param errorText Fehlermeldung
     */
    public HISIllegalArgumentException(String errorText) {
        super(errorText);
    }

}
