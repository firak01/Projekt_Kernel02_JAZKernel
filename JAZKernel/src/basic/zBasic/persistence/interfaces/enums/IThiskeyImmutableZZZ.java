package basic.zBasic.persistence.interfaces.enums;

import java.io.Serializable;


/**
 * Value object enabling the getting of his key identifiers.
 * Dient der Verwendung der Annotation org.hibernate.annotations.immtable.
 * Die diese Annotation nutzenden Pojos dürfen keine Setter haben. Die Werte werden im Kontruktor gesetzt.
 *
 * @author lindhauer
 * @version $Revision: 1.7 $
 */
public interface IThiskeyImmutableZZZ<E extends Enum<E> & IThiskeyProviderZZZ<Long>> extends Serializable {

    /**
     * Returns the his key identifier.
     *
     * @return a value representing the his key identifier
     */
   abstract Long getThiskey();


    /**
     * @return HisKeyProvider<Long> Enum mit allen unterstützten Hiskeys
     */
   abstract Class<E> getThiskeyEnumClass();
}

