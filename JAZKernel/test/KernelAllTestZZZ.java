import junit.framework.Test;
import junit.framework.TestSuite;
import zBasic.util.abstractList.ArrayListExtendedZZZTest;
import zBasic.util.abstractList.HashMapExtendedZZZTest;
import zBasic.util.abstractList.HashMapIndexedZZZTest;
import zBasic.util.abstractList.HashMapMultiZZZTest;
import zBasic.util.abstractList.HashtableSortedZZZTest;
import zBasic.util.abstractList.VectorZZZTest;
import zBasic.util.data.DataStoreZZZTest;
import zBasic.util.datatype.integer.IntegerArrayZZZTest;
import zBasic.util.datatype.string.StringArrayZZZTest;
import zBasic.util.datatype.string.StringZZZTest;
import zBasic.util.file.FileTextParserZZZTest;
import zBasic.util.file.ini.IniFileTest;
import zBasic.util.file.txt.TxtReaderZZZTest;
import zBasic.util.file.txt.TxtWriterZZZTest;
import zKernel.GetOptZZZTest;
import zKernel.KernelPropertyZZZTest;
import zKernel.KernelUseObjectZZZTest;
import zKernel.KernelZZZTest;
import zKernel.LogZZZTest;
import zKernel.file.ini.FileIniZZZTest;
import zKernel.file.ini.KernelExpressionIniSolverZZZTest;
import zUtil.io.FileZZZTest;
 

public class KernelAllTestZZZ {

	public static Test suite(){
		TestSuite objReturn = new TestSuite();
		//Merke: Die Tests bilden in ihrer Reihenfolge in etwa die Hierarchie im Framework ab. 
		//            Dies beim Einf�gen weiterer Tests bitte beachten.         
		
		
		//Tests f�r Datentypen etc.
		objReturn.addTestSuite(ArrayListExtendedZZZTest.class);		
		
		objReturn.addTestSuite(HashMapMultiZZZTest.class);
		objReturn.addTestSuite(HashMapExtendedZZZTest.class);
		objReturn.addTestSuite(HashMapIndexedZZZTest.class);
		objReturn.addTestSuite(HashtableSortedZZZTest.class);

		//TODO Noch nicht fertig objReturn.addTestSuite(HashtableWithDupsTestZZZ.class));
		objReturn.addTestSuite(VectorZZZTest.class);
		objReturn.addTestSuite(DataStoreZZZTest.class);
		objReturn.addTestSuite(IntegerArrayZZZTest.class);
		objReturn.addTestSuite(StringArrayZZZTest.class);
		objReturn.addTestSuite(StringZZZTest.class);
		objReturn.addTestSuite(TxtReaderZZZTest.class);
		objReturn.addTestSuite(TxtWriterZZZTest.class);
		objReturn.addTestSuite(IniFileTest.class); //Merke: Diese Klasse ist nicht origin�r ZZZ, darum der Test auch nicht
				
		
		//Echte KernelObjekte testen
		objReturn.addTestSuite(KernelPropertyZZZTest.class);
		objReturn.addTestSuite(KernelZZZTest.class);
		objReturn.addTestSuite(KernelUseObjectZZZTest.class);
		objReturn.addTestSuite(LogZZZTest.class);
		objReturn.addTestSuite(FileTextParserZZZTest.class);
		objReturn.addTestSuite(FileIniZZZTest.class);
		objReturn.addTestSuite(KernelExpressionIniSolverZZZTest.class);
		objReturn.addTestSuite(FileZZZTest.class);
		objReturn.addTestSuite(GetOptZZZTest.class);
		
		
		
		
		return objReturn;
	}
	/**
	 * Hiermit eine Swing-Gui starten.
	 * Das ist bei eclipse aber nicht notwendig, au�er man will alle hier eingebundenen Tests durchf�hren.
	 * @param args
	 */
	public static void main(String[] args) {
		//Ab Eclipse 4.4 ist junit.swingui sogar nicht mehr Bestandteil des Bundles
		//also auch nicht mehr unter der Eclipse Variablen JUNIT_HOME/junit.jar zu finden. 
	    //junit.swingui.TestRunner.run(KernelAllTestZZZ.class);
	}

}
