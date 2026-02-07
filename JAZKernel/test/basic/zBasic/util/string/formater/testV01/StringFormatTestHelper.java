package basic.zBasic.util.string.formater.testV01;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import junit.framework.TestCase;

/**
 * Hilfsklasse zur Reduktion redundanter Testlogik.
 * Beide Testklassen (Formater & Manager) können diese statischen Methoden nutzen.
 */
public class StringFormatTestHelper extends TestCase {

    // -------------------------------------------------------------
    // Grundlegende Prüfungen
    // -------------------------------------------------------------

    /** Prüft, ob ein Text exakt einmal im Ergebnis vorkommt */
    public static void assertContainsOnce(String result, String part) {
    	try {
        assertTrue("Fehlt: " + part, StringZZZ.contains(result, part));
        assertEquals("Nicht exakt 1x enthalten: " + part,
                1, StringZZZ.count(result, part));
	    }catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }

    /** Prüft, ob Thread- und Klassenname exakt einmal vorkommen */
    public static void assertThreadAndClass(String result, String thread, String cls) {
    	try {
	        assertEquals("Thread fehlt oder mehrfach",
	                1, StringZZZ.count(result, thread));
	        assertEquals("Klassenname fehlt oder mehrfach",
	                1, StringZZZ.count(result, cls));
    	}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }

    /** Prüft Reihenfolge: class -> log -> thread */
    public static void assertOrder(String result, String cls, String thread, String log) {
    	try {
	        int iLog   = StringZZZ.indexOfFirst(result, log);
	        int iThread= StringZZZ.indexOfFirst(result, thread);
	        int iCls   = StringZZZ.indexOfFirst(result, cls);
	
	        assertTrue("Log müsste hinter Class kommen",  iLog > iCls + cls.length());
	        assertTrue("Thread müsste hinter Log kommen", iThread + thread.length() < iLog );
    	}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }

    // -------------------------------------------------------------
    // Multi-Log-Prüfungen
    // -------------------------------------------------------------

    /** Prüft, dass mehrere Logs jeweils genau 1x vorkommen */
    public static void assertContainsLogsOnce(String result, String... logs) {
        for (String log : logs) {
            assertContainsOnce(result, log);
        }
    }

    /** Prüft, dass ein Log vorhanden ist und hinter Thread steht */
    public static void assertLogBehindThread(
            String result, String log, String threadMarker) {
    	try {
        int idxLog = StringZZZ.indexOfLast(result, log);
        int idxThread = StringZZZ.indexOfLast(result, threadMarker);

        assertTrue("Log sollte hinter Thread stehen",
                idxLog > idxThread + threadMarker.length());
    	}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }

    /** Prüft, dass ein Log NICHT enthalten ist */
    public static void assertLogNotPresent(String result, String log) {
    	try {
    		assertEquals(-1, StringZZZ.indexOfLast(result, log));
    	}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }
}

