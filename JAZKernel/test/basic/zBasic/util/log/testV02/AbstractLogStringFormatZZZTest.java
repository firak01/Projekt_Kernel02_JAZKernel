package basic.zBasic.util.log.testV02;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ;
import basic.zBasic.util.log.ILogStringFormatZZZ.LOGSTRINGFORMAT;
import junit.framework.TestCase;

public abstract class AbstractLogStringFormatZZZTest extends TestCase {

    // -------------------------------------------------------------------------
    // ABSTRACT: von Kindklassen zu implementieren
    // -------------------------------------------------------------------------
    
    /** Reines Formatieren ohne Position/Format-Enum */
    protected abstract String compute(DummyTestObjectZZZ dummy, String... logs) throws ExceptionZZZ;

    /** Formatieren mit Format-Enum */
    protected abstract String computeWithFormat(
            DummyTestObjectZZZ dummy,
            IEnumSetMappedLogStringFormatZZZ[] format,
            String... logs
    ) throws ExceptionZZZ;

    // -------------------------------------------------------------------------
    // Hilfsmethoden, die in beiden Klassen identisch waren:
    // -------------------------------------------------------------------------

    protected void assertContainsOnce(String text, String part) {
        assertTrue(StringZZZ.contains(text, part));
        assertEquals(1, StringZZZ.count(text, part));
    }

    protected void assertThreadAndClassPresent(String logValue, String threadMarker, String classMarker) {
        assertEquals(1, StringZZZ.count(logValue, threadMarker));
        assertEquals(1, StringZZZ.count(logValue, classMarker));
    }

    protected void assertOrder(String logValue, String className, String threadMarker, String logEntry) {
        int idxLog   = StringZZZ.indexOfFirst(logValue, logEntry);
        int idxThread= StringZZZ.indexOfFirst(logValue, threadMarker);
        int idxCls   = StringZZZ.indexOfFirst(logValue, className);

        assertTrue(idxLog > idxCls + className.length());
        assertTrue(idxThread + threadMarker.length() < idxCls);
    }

    // -------------------------------------------------------------------------
    // Gemeinsame Tests:
    // -------------------------------------------------------------------------

    public void testSingleLogEntry_DefaultFormat() {
        try {
            DummyTestObjectZZZ dummy = new DummyTestObjectZZZ();
            String sLog = "der erste Logeintrag";
            String thread = "[Thread:";
            String cls = dummy.getClass().getSimpleName() + ":";

            String result = compute(dummy, sLog);
            System.out.println(result);
            
            assertNotNull(result);
            assertContainsOnce(result, sLog);
            assertThreadAndClassPresent(result, thread, cls);
            assertOrder(result, cls, thread, sLog);

        } catch(ExceptionZZZ e) {
            fail("Exception: " + e.getMessageLast());
        }
    }

    public void testMultipleLogs_WithArray_UsingFormat() {
        try {
            DummyTestObjectZZZ dummy = new DummyTestObjectZZZ();

            String s1="erster"; 
            String s2="zweiter";
            String s3="dritter";

            String thread = "[Thread:";
            String cls = dummy.getClass().getSimpleName()+":";      

            IEnumSetMappedLogStringFormatZZZ[] format = new IEnumSetMappedLogStringFormatZZZ[]{
                ILogStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME,
                ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01,
                ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID, 
                ILogStringFormatZZZ.LOGSTRINGFORMAT.LINENEXT,
                ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02,
                ILogStringFormatZZZ.LOGSTRINGFORMAT.LINENEXT,
                ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03
            };

            String result = computeWithFormat(dummy, format, s1, s2, s3);

            assertContainsOnce(result, s1);
            assertContainsOnce(result, s2);
            assertContainsOnce(result, s3);

            assertThreadAndClassPresent(result, thread, cls);

        } catch(ExceptionZZZ e) {
            fail("Exception: " + e.getMessageLast());
        }
    }
}
