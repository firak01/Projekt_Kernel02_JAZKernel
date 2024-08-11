package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelIniSolverZZZ extends IValueSolverZTagIniZZZ{
	
	//Merke: KernelIniSolver fuellen immer die gerade gemachten Schritte in ein IKernelConfigSectionEntryObject
	//       Darum folgende Methoden:
		
	//gib ueber einen int - Wert zurueck welche "Loesungsschritte" gemacht wurden.
	public int solve(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
				
	//Vector mit 3 Elementen und den Indexwerten 0 = Vor dem Tag, 1= Der Taginhalt 2= Nach dem Tag
	//Damit kann man dann weiter aufloesen.
	public Vector<String>solveFirstVector(String sLineWithExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;

		
	//auch parse um eine Referenz auf das IKernelConfigSectionEnryZZZ-Object als Parameter erweitern.
	public int parse(String sLineWithExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
		
	
	//Es war notwendig mehr Informationen zurückzuliefern als nur einen aufgeloesten String
	//Z.B. bei Zeilen mit Verschluesselung will man nicht nur den entschluessleten Wert haben, sondern auch den verschluesselten Ausgangswert
	//     aus der Zeile haben.
	//public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ;
}
