package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import junit.framework.TestCase;

public class KernelConfigSectionEntryUtilZZZTest extends TestCase{
		
	public void testGetValueExpressionTagSurroundingRemoved_SingleTag(){
		String sTagStartZ; String sTagEndZ;String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpressionTarget; String sValue; String sExpression; String sExpressionOld;
		Vector3ZZZ<String> vecExpressionSource;
		try{	
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			assertNotNull(sExpression);
			sExpressionOld = sExpression;
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++
			sTagStartZ = "<Z><Z:Encrypted>";
			sTagEndZ = "</Z:Encrypted></Z>";
			sExpression = "<Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code>";
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartZ);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndZ);
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, true, false);
			

			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);
			
		
			//+++++++++++++++++++++++++++++++++++++++++++++++
			sTagStartZ = "<Z><Z:Encrypted>";
			sTagEndZ = "</Z:Encrypted></Z>";
			sExpression = "<Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code>";
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartZ);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndZ);
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			

			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);

			
			
			
			
			
					
			//#########################################
			//die JSON Map ist schoen verschachtelt
			sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			assertNotNull(sExpression);
			sExpressionOld = sExpression;
			
			//20241103: Einfacher Fall, ohne pre - post Werte
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartZ);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndZ);
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			

			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);
			
			//############################
			//20241103: Aber jetzt mit pre - post Werten
		
			//TODOGOON20241103;
			/*/Beim Call solver kommt eine Situation vor, hier abbilden
			 * Vector 0= PRE<Z><Z:Call>
			  Vector 1= <Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java>
			  Vector 2= </Z:Call></Z>POST

			  und<Z>
              bzw </Z> soll entfernt werden.
			*/
			vecExpressionSource = new Vector3ZZZ<String>();			
			vecExpressionSource.replace(0, "PRE<Z><Z:Call>");
			vecExpressionSource.replace(1, "<Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java>");
			vecExpressionSource.replace(2, "</Z:Call></Z>POST");
			
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			
			sExpressionTarget = "PRE<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>POST";
			sValue = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sValue);
			assertEquals(sExpressionTarget, sValue);
			
			
			
			
			//########################################################################
			sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			assertNotNull(sExpression);
			sExpressionOld = sExpression;
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartZ);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndZ);
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);
						
			//########################################################################
			sTagStartZ = "<JSON>";
			sTagEndZ = "</JSON>";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			assertNotNull(sExpression);
			sExpressionOld = sExpression;
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartZ);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndZ);
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);
			
			
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testGetValueExpressionTagSurroundingRemoved_MultiTags(){
		String sTagStartZ; String sTagEndZ;String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpression; String sExpressionOld;
		Vector3ZZZ<String> vecExpressionSource; boolean bDirectionFromInToOut;
		try{	
			//Hier der Test für "mehrere Tags auf anderen Positionen des Vectors".
			
			//die JSON Map ist schoen verschachtelt
			//sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
		
			//########################################################################
			//### Test: Entferne aus dem Vector die Tags von "aussen nach innen" vs "innen nach aussen".
			//### Merke: Default ist von "innen nach aussen"...
			//#########################################################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "innen nach aussen", bleibt der Z-Tag bestehen...
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
						
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagEndZ));
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "innen nach aussen", wird der JSON:MAP-Tag entfernt..
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
						
			//################################################################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll der Z-Tag entfernt werden...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innnen", soll der JSON:MAP-Tag bestehen bleiben...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
		
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			
			//################################################################################	
			//### noch gemischter 
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag entfernt werden...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON>";
			sTagEndSource = "</JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON:MAP>" + sExpression + "</JSON:MAP>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag erhalten bleiben...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag geloescht werden...
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testGetValueExpressionTagSurroundingRemoved_FromOutToIn_MultiTags(){
		String sTagStartZ; String sTagEndZ;String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpression; String sExpressionOld;
		String sExpressionSolved; String sExpressionSolvedTagless;
		Vector3ZZZ<String> vecExpressionSource; boolean bDirectionFromInToOut;
		String sValue;
		
		try{	
			//Hier der Test für "mehrere Tags auf anderen Positionen des Vectors - von Aussen nach Innen entfernt".

			
			//+ Wichtige Hilfsmethode pruefen, wichtig ist false am Ende, damit wird von aussen nach innen das Tag entfernt.
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			
			sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt
			sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false, false);
			assertEquals(sExpressionSolved, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}
