package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import junit.framework.TestCase;

public class KernelConfigSectionEntryUtilZZZTest extends TestCase{
		
	public void testGetValueExpressionTagSurroundingRemoved_SingleTag(){
		String sTagStartZ; String sTagEndZ;String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpression; String sExpressionOld;
		Vector<String> vecExpressionSource;
		try{	
			//die JSON Map ist schoen verschachtelt
			sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			assertNotNull(sExpression);
			sExpressionOld = sExpression;
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartZ);
			vecExpressionSource.add(sExpression);
			vecExpressionSource.add(sTagEndZ);
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			

			sExpression = VectorZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);
			
			
			//########################################################################
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			assertNotNull(sExpression);
			sExpressionOld = sExpression;
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartZ);
			vecExpressionSource.add(sExpression);
			vecExpressionSource.add(sTagEndZ);
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			
			sExpression = VectorZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);
						
			//########################################################################
			sTagStartZ = "<JSON>";
			sTagEndZ = "</JSON>";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);
			assertNotNull(sExpression);
			sExpressionOld = sExpression;
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartZ);
			vecExpressionSource.add(sExpression);
			vecExpressionSource.add(sTagEndZ);
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ);
			
			sExpression = VectorZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertEquals(sExpressionOld, sExpression);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testGetValueExpressionTagSurroundingRemoved_MultiTags(){
		String sTagStartZ; String sTagEndZ;String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpression; String sExpressionOld;
		Vector<String> vecExpressionSource; boolean bDirectionFromInToOut;
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
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartSource);
			vecExpressionSource.add(sExpression);
			vecExpressionSource.add(sTagEndSource);
						
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, bDirectionFromInToOut);					
			sExpression = VectorZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "innen nach aussen", wird der JSON:MAP-Tag entfernt..
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartSource);
			vecExpressionSource.add(sExpression);
			vecExpressionSource.add(sTagEndSource);
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, bDirectionFromInToOut);					
			sExpression = VectorZZZ.implode(vecExpressionSource);
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
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartSource);
			vecExpressionSource.add(sExpression);
			vecExpressionSource.add(sTagEndSource);
			
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, bDirectionFromInToOut);					
			sExpression = VectorZZZ.implode(vecExpressionSource);
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
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartSource);
			vecExpressionSource.add(sExpression);
			vecExpressionSource.add(sTagEndSource);
			
		
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, bDirectionFromInToOut);					
			sExpression = VectorZZZ.implode(vecExpressionSource);
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
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartSource);
			vecExpressionSource.add("<JSON:MAP>" + sExpression + "</JSON:MAP>");
			vecExpressionSource.add(sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, bDirectionFromInToOut);					
			sExpression = VectorZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag erhalten bleiben...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartSource);
			vecExpressionSource.add("<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.add(sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, bDirectionFromInToOut);					
			sExpression = VectorZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag geloescht werden...
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector<String>();
			vecExpressionSource.add(sTagStartSource);
			vecExpressionSource.add("<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.add(sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, bDirectionFromInToOut);					
			sExpression = VectorZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}
