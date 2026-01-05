package basic.zBasic.util.log.testV02;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.LogStringFormatManagerZZZ;

/**Per ChatGPT von Redundanz befreit durch Verwendung einer Abstrakten Test-Klasse
 * Diese wurde von ChatGPT basierend auf den .testV00 Klassen erstellt
 * @author Fritz Lindhauer, 15.11.2025, 16:22:02
 * 
 */
public class LogStringFormatManagerZZZ02Test extends AbstractLogStringFormatZZZTest {

    @Override
    protected String compute(DummyTestObjectZZZ dummy, String... logs) throws ExceptionZZZ {
        return LogStringFormatManagerZZZ.getNewInstance().compute(dummy, logs);
    }

    @Override
    protected String computeWithFormat(DummyTestObjectZZZ dummy,IEnumSetMappedLogStringFormatZZZ[] format,String... logs) throws ExceptionZZZ {
        return LogStringFormatManagerZZZ.getNewInstance().compute(dummy, format, logs);
    }
}

