package basic.zBasic.persistence.interfaces.enums;

import java.io.Serializable;


/**
 * Value object enabling the setting and getting of his key identifiers.
 *
 * @author mweyland
 * @version $Revision: 1.7 $
 */
public interface IThiskeyValueZZZ<E extends Enum<E> & IThiskeyProviderZZZ<Long>> extends Serializable {

    /**
     * Returns the his key identifier.
     *
     * @return a value representing the his key identifier
     */
   abstract Long getThiskey();

    /**
     * Sets the his key identifier.
     *
     * @param hisKeyId  a value representing the his key identifier to be set
     */
   abstract void setThiskey(Long thisKeyId);

    /**
     * @return HisKeyProvider<Long> Enum mit allen unterstützten Hiskeys
     */
   abstract Class<E> getThiskeyEnumClass();
   
   /** Weitere Spalte zur Differenzierung welche DAO-Klasse den Schlüssel erzeugt hat.
    *  Wird z.B. für die Suche oder für das Löschen per DOA-Klasse herangezogen.
    * @return
    */
   abstract String getKeyType();
   abstract void setKeyType(String sKeyType);
}

