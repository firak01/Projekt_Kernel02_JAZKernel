package basic.zBasic.util.string.formater.testV02;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;

/**Per ChatGPT von Redundanz befreit durch Verwendung einer Abstrakten Test-Klasse
 * Diese wurde von ChatGPT basierend auf den .testV00 Klassen erstellt
 * @author Fritz Lindhauer, 15.11.2025, 16:22:02
 * 
 */
public class LogStringFormatManagerZZZ02Test extends AbstractLogStringFormatZZZTest {

    @Override
    protected String compute(DummyTestObjectZZZ dummy, String... logs) throws ExceptionZZZ {
        return StringFormatManagerZZZ.getNewInstance().compute(dummy, logs);
    }

    @Override
    protected String computeWithFormat(DummyTestObjectZZZ dummy,IEnumSetMappedStringFormatZZZ[] format,String... logs) throws ExceptionZZZ {
        return StringFormatManagerZZZ.getNewInstance().compute(dummy, format, logs);
    }
}

