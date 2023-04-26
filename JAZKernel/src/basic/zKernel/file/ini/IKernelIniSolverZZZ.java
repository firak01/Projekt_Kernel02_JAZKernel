package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelIniSolverZZZ {
	
	//Es war notwendig mehr Informationen zurückzuliefern als nur einen aufgeloesten String
	//Z.B. bei Zeilen mit Verschluesselung will man nicht nur den entschluessleten Wert haben, sondern auch den verschluesselten Ausgangswert
	//     aus der Zeile haben.
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ;
}
