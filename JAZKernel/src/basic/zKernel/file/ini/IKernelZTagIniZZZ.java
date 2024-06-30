package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface IKernelZTagIniZZZ extends IValueSolvedUserZZZ, IKernelConfigSectionEntryUserZZZ{
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! OHNE <z:.. voran.
	public IKernelConfigSectionEntryZZZ computeAsEntry(String sLineWithExpression) throws ExceptionZZZ;
	public String[] computeAsArray(String sLineWithExpression, String sSeparator) throws ExceptionZZZ;
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! MIT <z:... voran. Dann kann das Ergebnis weiterverarbeit werden.	
	//boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
   																				//wird verwendet in der KernelExpressionIniConverterZZZ - Klasse.
	
	//Vector mit 3 Elementen und den Indexwerten 0 = Vor dem Tag, 1= Der Taginhalt 2= Nach dem Tag
	//Damit kann man dann Formeln aufloesen.
	public Vector<String>computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;	
	public Vector<String>computeExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
	
	//Innerhalb des Vectors werden die Elemente auch mit den Tags zurueckgegeben.
	//Damit kann man dann diese zum weiteren Aufloesen der Formel (z.B. mit anderen Funktionen, s. Z:math) verwenden.
	public Vector<String>computeAsExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;	
	public Vector<String>computeAsExpressionAllVector(String sLineWithExpression) throws ExceptionZZZ;
}
