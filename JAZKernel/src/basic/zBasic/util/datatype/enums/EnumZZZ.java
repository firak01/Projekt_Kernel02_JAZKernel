package basic.zBasic.util.datatype.enums;

import java.util.Collection;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import base.invariants.EnsureArgument;
import base.tools.his.ModelPathUtil;
import base.tools.his.Functions;

/**
 * Utility enum that provides methods required in combination with enums.
*/
public class EnumZZZ {

    /**
     * Returns a key for an enum-instance that can be used for translating the enum to
     * the current local.
     *
     * <p>The key is the fully qualified class-name of the given {@code enumInstance}.</p>
     *
     * <p>If the fully qualified class-name contains a '$'-sign that indicates that the
     * used enum is an inner-class the '$' will be replaced by an '.'.</p>
     *
     * <p>If {@code enumInstance} is located anywhere below ...appserver.model
     * this prefix won't be part of the key.</p>
     *
     * @param enumInstance  the enum instance that's key should be returned
     *
     * @return  the key
     *
     * @throws IllegalArgumentException if {@code enumInstance == null}
     */
    public static String getEnumKey(Enum<?> enumInstance) {
        EnsureArgument.notNull(enumInstance, "enumInstance must not be null");
        String prefix = ModelPathUtil.getModelName(enumInstance.getClass());
        String identifier = prefix.replace('$', '.') + "." + enumInstance.name();
        return identifier;
    }

    /**
     * Gets all enum constants names of the given enum.
     * @param <E>
     * @param enumClass
     * @return all names
     */
    public static <E extends Enum<E>> Collection<String> getNames(Class<E> enumClass) {
        return Collections2.transform(Lists.newArrayList(enumClass.getEnumConstants()), Functions.toName(enumClass));
    }
}

