package zBasic.example.ImportStatic;

import static java.lang.Math.*;
import static java.lang.Integer.*;
import static java.lang.Byte.*;
import static java.lang.System.*;

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
public class ExampleSimple02ambiguousError {
	public static void main(String[] args) {
		//Also:
		//man spart system. ein
		//man spart Math. ein
		out.println(sqrt(4));
		
		//Nun die Fehlerquelle, MAX_VALUE laesst sich noch nicht mal complieren.
		//ist ambiguous
		//out.println(MAX_VALUE);
		
		//FAZIT für den Z-Kernel:
		//Z.B. FLAGZ lassen sich so nicht abkuerzen, da ja der "ambiguous" Fehler kommt.
	}
    
}
