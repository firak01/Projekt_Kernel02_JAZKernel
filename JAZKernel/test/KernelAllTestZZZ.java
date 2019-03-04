import junit.framework.Test;
import junit.framework.TestSuite;
import basic.zBasic.util.abstractEnum.EnumSetMappedZZZTest;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZTest;
import basic.zBasic.util.abstractList.HashMapExtendedZZZTest;
import basic.zBasic.util.abstractList.HashMapIndexedZZZTest;
import basic.zBasic.util.abstractList.HashMapMultiZZZTest;
import basic.zBasic.util.abstractList.HashtableSortedZZZTest;
import basic.zBasic.util.abstractList.VectorZZZTest;
import basic.zBasic.util.data.DataStoreZZZTest;
import basic.zBasic.util.datatype.counter.CounterByCharacterAsciiZZTest;
import basic.zBasic.util.datatype.dateTime.DateTimeZZZTest;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZTest;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZTest;
import basic.zBasic.util.datatype.string.StringArrayZZZTest;
import basic.zBasic.util.datatype.string.StringZZZTest;
import basic.zBasic.util.file.FileTextParserZZZTest;
import basic.zBasic.util.file.ini.IniFileTest;
import basic.zBasic.util.file.txt.TxtReaderZZZTest;
import basic.zBasic.util.file.txt.TxtWriterZZZTest;
import basic.zKernel.GetOptZZZTest;
import basic.zKernel.KernelPropertyZZZTest;
import basic.zKernel.KernelUseObjectZZZTest;
import basic.zKernel.KernelZZZTest;
import basic.zKernel.LogZZZTest;
import basic.zKernel.file.ini.FileIniZZZTest;
import basic.zKernel.file.ini.KernelExpressionIniSolverZZZTest;
import basic.zUtil.io.FileZZZTest;
 
public class KernelAllTestZZZ {

	public static Test suite(){
		TestSuite objReturn = new TestSuite();
		//Merke: Die Tests bilden in ihrer Reihenfolge in etwa die Hierarchie im Framework ab. 
		//            Dies beim Einfügen weiterer Tests bitte beachten.         
		
		//Tests für Enum
		objReturn.addTestSuite(EnumSetMappedUtilZZZTest.class);
		objReturn.addTestSuite(EnumSetMappedZZZTest.class);
		
		//Tests für Datentypen etc.
		objReturn.addTestSuite(ArrayListExtendedZZZTest.class);		
		
		objReturn.addTestSuite(HashMapMultiZZZTest.class);
		objReturn.addTestSuite(HashMapExtendedZZZTest.class);
		objReturn.addTestSuite(HashMapIndexedZZZTest.class);
		objReturn.addTestSuite(HashtableSortedZZZTest.class);

		//TODO Noch nicht fertig objReturn.addTestSuite(HashtableWithDupsTestZZZ.class));
		objReturn.addTestSuite(CounterByCharacterAsciiZZTest.class);
		objReturn.addTestSuite(VectorZZZTest.class);
		objReturn.addTestSuite(DataStoreZZZTest.class);
		objReturn.addTestSuite(DateTimeZZZTest.class);
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
