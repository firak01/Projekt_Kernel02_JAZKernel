package base.tools.his;

import org.apache.commons.lang.StringUtils;

/**
 * Utilities zum Konvertieren zwischen qualifizierten Namen der Modellklasssen
 * und die in Konfigurationen verwendete Kurzform, z.B. zwischen
 * "de.his.appserver.model.psv.Person" und "psv.Person"
 *
 * Hier sind die Methoden versammelt, die vorher über das genze Projekt verstreut
 * waren. Ziel dabei ist, einen definierten Einstiegspunkt für die Verwendung von
 * Entity-Namen zu bieten, so dass bei der Umbenennung von Entities leichter
 * festgestellt werden kann, wo weitere Anpassungen vorgenommen werden müssen.
 *
 * @author Siegel
 */
public class ModelPathUtil {

    /**
     * start path of the model
     */
    public static final String MODEL_PACKAGE_NAME = "de.his.appserver.model";

    private static final String MODEL_PACKAGE_PREFIX = MODEL_PACKAGE_NAME + ".";

    public final static String MODEL_PATH = StringUtils.replace(MODEL_PACKAGE_PREFIX, ".", "/");

    public static String getModelName(Class<?> clazz) {
        return getModelName(clazz.getName());
    }

    public static String getModelName(final String fullName) {
        return StringUtils.replace(fullName, MODEL_PACKAGE_PREFIX, "");
    }

    public static String getFullName(final String modelName) {
        return MODEL_PACKAGE_PREFIX + modelName;
    }

    public static boolean isModelClass(final Class<?> clazz) {
        return isModelClass(clazz.getName());
    }

    public static boolean isModelClass(String fullName) {
        return fullName.startsWith(MODEL_PACKAGE_PREFIX);
    }
}
