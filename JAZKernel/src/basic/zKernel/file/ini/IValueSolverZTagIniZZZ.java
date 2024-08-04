package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.IValueComputedBufferedUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IValueSolverZTagIniZZZ extends IObjectWithExpressionZZZ{
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
	
	//public String parse(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! OHNE <z:.. voran.
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sLineWithExpression) throws ExceptionZZZ;
	public String[] parseAsArray(String sLineWithExpression, String sSeparator) throws ExceptionZZZ;
	public String parseAsExpression(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! MIT <z:... voran. Dann kann das Ergebnis weiterverarbeit werden.	
	//boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
   																				//wird verwendet in der KernelExpressionIniConverterZZZ - Klasse.
	
	//Vector mit 3 Elementen und den Indexwerten 0 = Vor dem Tag, 1= Der Taginhalt 2= Nach dem Tag
	//Damit kann man dann Formeln aufloesen.
	//public Vector<String>parseExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;	
	public Vector<String>parseAllVector(String sLineWithExpression) throws ExceptionZZZ;
	
	//Innerhalb des Vectors werden die Elemente auch mit den Tags zurueckgegeben.
	//Damit kann man dann diese zum weiteren Aufloesen der Formel (z.B. mit anderen Funktionen, s. Z:math) verwenden.
	//public Vector<String>computeAsExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;	
	//public Vector<String>computeAsExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
}
