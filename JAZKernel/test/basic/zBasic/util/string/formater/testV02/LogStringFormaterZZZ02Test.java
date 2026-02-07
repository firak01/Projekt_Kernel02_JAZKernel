package basic.zBasic.util.string.formater.testV02;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;

/**Per ChatGPT von Redundanz befreit durch Verwendung einer Abstrakten Test-Klasse
 * Diese wurde von ChatGPT basierend auf den .testV00 Klassen erstellt  
 * @author Fritz Lindhauer, 15.11.2025, 16:22:53
 * 
 */
public class LogStringFormaterZZZ02Test extends AbstractLogStringFormatZZZTest {

	private StringFormaterZZZ formater = null;

	protected void setUp(){
		try {			
			formater = new StringFormaterZZZ();
								
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 	
	}//END setup

    @Override
    protected String compute(DummyTestObjectZZZ dummy, String... logs) throws ExceptionZZZ {
        return formater.compute(dummy, logs);
    }

    @Override
    protected String computeWithFormat(DummyTestObjectZZZ dummy,
            IEnumSetMappedStringFormatZZZ[] format,
            String... logs) throws ExceptionZZZ {

        formater.setFormatPositionsMapped(format);
        return formater.compute(dummy, logs);
    }
}
