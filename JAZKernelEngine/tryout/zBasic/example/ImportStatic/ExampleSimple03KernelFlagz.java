package zBasic.example.ImportStatic;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Byte.*;
import static java.lang.System.*;

import basic.zBasic.DummyTryoutObjectWithConstantsByImportStaticDirectZZZ;
import basic.zBasic.DummyTryoutObjectWithFlagByDirectZZZ;
import basic.zBasic.DummyTryoutObjectWithFlagByImportStaticDirectExtendedZZZ2;
import basic.zBasic.DummyTryoutObjectWithFlagByImportStaticDirectZZZ;
import basic.zBasic.DummyTryoutObjectWithFlagZZZ;


/**siehe: https://www.geeksforgeeks.org/static-import-java/
 * @author fl86kyvo
 * 
 Explanation : In the program, we are trying to access MAX_VALUE variable, but Every primitive data type contains MAX_VALUE variable which is pre-declared in there Wrapper class. Here we are importing Integer and Byte class simultaneously and trying to access static variable MAX_VALUE but here compiler will be confused by seeing two import statements because both Integer and Byte class contains a static variable MAX_VALUE. Therefore here compiler throw an error saying Reference to MAX_VALUE is ambiguous.
		NOTE : Two package contains two class/interface with the same name is very rare. Therefore it is very rare that we will get any ambiguity while importing normal way i.e. normal import. But it is possible that two classes contains same variable, therefore it is very common in static import to get error saying reference is ambiguous. Thats why it is not recommended to use if there is no such requirement. 
 

	Difference between import and static import:
    With the help of import, we are able to access classes and interfaces which are present in any package. 
    But using static import, we can access all the static members (variables and methods) of a class 
    directly without explicitly calling class name.
    
    The main difference is Readability, ClassName.dataMember (System.out) is less readable when compared to dataMember(out), static import can make your program more readable by eliminating repetitive class names and making code more concise.
 */
public class ExampleSimple03KernelFlagz {
	public static void main(String[] args) {
		//Also:
		//man spart system. ein
		//man spart Math. ein
		out.println(sqrt(4));
		
		//Nun die Fehlerquelle, MAX_VALUE laesst sich noch nicht mal complieren.
		//ist ambiguous
		//out.println(MAX_VALUE);
		
		//Hier wird trotz implementierung des Intefaces, keine FLAGZ Enum angeboten
		DummyTryoutObjectWithFlagZZZ obj = new DummyTryoutObjectWithFlagZZZ();
		
		DummyTryoutObjectWithFlagByDirectZZZ objByDirect = new DummyTryoutObjectWithFlagByDirectZZZ();
		String sName = DummyTryoutObjectWithFlagByDirectZZZ.FLAGZ.DUMMY02DIRECT.name();
		
		
		//Nun mal ein Objekt verwenden, das speziell ueber import direct das DummyTestObjectWithFlagByDirectZZZ einbindet.
		DummyTryoutObjectWithFlagByImportStaticDirectZZZ objByImportDirect = new DummyTryoutObjectWithFlagByImportStaticDirectZZZ();
		//Merke: enum FLAGZ steht nicht zur Verfuegung DummyTestObjectWithFlagByImportStaticDirectZZZ.
		//ABER: In der Methode der Klasse selbst kann die eingebundenen enum dirket OHNE Klassename verwendet werden.
		String sByEnumValue = objByImportDirect.usingConstants();
		out.println(sByEnumValue);
		
		DummyTryoutObjectWithConstantsByImportStaticDirectZZZ objByConstantsImportDirect = new DummyTryoutObjectWithConstantsByImportStaticDirectZZZ();
		//Merke: Die eingebundenen Konstante steht hier auch nicht zur Verfügung.
		//ABER: In der Methode der Klasse selbst, kann die eingebundene Konstante direkt OHNE Klassennamen verwendet werden.
		String sByConstantValue = objByConstantsImportDirect.usingConstants();
		out.println(sByConstantValue);
		
		//ABER: Sobald von einer anderen Klasse geerbt wird, stehen die enums nicht mehr direkt zur Verfuegung
		DummyTryoutObjectWithFlagByImportStaticDirectExtendedZZZ2 objByConstantsImportDirectExtended = new DummyTryoutObjectWithFlagByImportStaticDirectExtendedZZZ2();
		//Merke: Die eingebundenen Konstante steht hier auch nicht zur Verfügung.
		//ABER: In der Methode der Klasse selbst, kann die eingebundene Konstante direkt OHNE Klassennamen verwendet werden.
		String sByEnumExtendedValue = objByConstantsImportDirectExtended.usingConstants();
		out.println(sByEnumExtendedValue);
		
		//#####################
		//### FAZIT 20240514:
		//### Damit ist "import static" fuer den ZKernel keine wirkliche Erleichterung.
		//### Ich denke die Vererbung macht dies nicht praktikabel.
		//#####################
		
	}
    
}
