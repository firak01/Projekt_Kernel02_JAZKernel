package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelIniSolverZZZ {
	
	//Methoden zur Aufloesung der "Ini-Pfade" und Variablen
	public Vector parseAllVector(String sLineWithExpression) throws ExceptionZZZ;
	
	
	//Es war notwendig mehr Informationen zurückzuliefern als nur einen aufgeloesten String
	//Z.B. bei Zeilen mit Verschluesselung will man nicht nur den entschluessleten Wert haben, sondern auch den verschluesselten Ausgangswert
	//     aus der Zeile haben.
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ;
}
