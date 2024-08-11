package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.IValueComputedBufferedUserZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IValueSolverZTagIniZZZ extends IObjectWithExpressionZZZ, IValueVariableUserZZZ{
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
		
	public String solve(String sLineWithExpression) throws ExceptionZZZ;
	
	//Methoden zur Aufloesung der "Ini-Pfade" und Variablen
	//public Vector solveFirstVector(String sLineWithExpression) throws ExceptionZZZ;
		
	
	
	//public String parse(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! OHNE <z:.. voran.
	//siehe IIniTagBasicZZZ   public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ;
	//siehe .... public String[] parseAsArray(String sLineWithExpression, String sSeparator) throws ExceptionZZZ;
	//siehe IExpressionUser public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! MIT <z:... voran. Dann kann das Ergebnis weiterverarbeit werden.	
	//boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
   																				//wird verwendet in der KernelExpressionIniConverterZZZ - Klasse.
	
		
	
	
	//Innerhalb des Vectors werden die Elemente auch mit den Tags zurueckgegeben.
	//Damit kann man dann diese zum weiteren Aufloesen der Formel (z.B. mit anderen Funktionen, s. Z:math) verwenden.
	//public Vector<String>computeAsExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;	
	//public Vector<String>computeAsExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
}
