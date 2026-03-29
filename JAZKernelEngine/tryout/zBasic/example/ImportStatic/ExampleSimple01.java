package zBasic.example.ImportStatic;

import static java.lang.Math.*;
import static java.lang.System.*;

/**siehe: https://www.geeksforgeeks.org/static-import-java/
 * @author fl86kyvo
 * 
Advantage of static import:
If user wants to access any static member of class then less coding is required.

Disadvantage of static import:
Static import makes the program unreadable and unmaintainable if you are reusing this feature, especially in large codebases or projects with multiple developers.
 */
public class ExampleSimple01 {
	public static void main(String[] args) {
		//Also:
		//man spart system. ein
		//man spart Math. ein
		out.println(sqrt(4));
		
	}
    
}
