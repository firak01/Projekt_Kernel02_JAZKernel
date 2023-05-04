package basic.zKernel;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public interface IKernelZFormulaIniZZZ {
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
	
	public String compute(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! OHNE <z:.. voran.
	public String[] computeAsArray(String sLineWithExpression, String sSeparator) throws ExceptionZZZ;
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! MIT <z:... voran. Dann kann das Ergebnis weiterverarbeit werden.	
	public String convert(String sLine) throws ExceptionZZZ; //Mache aus einem String "..." den passenden <z: ... Ausdruck.
	boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ; //z.B. ein Leerstring wird für KernelExpressionIni_EmptyZZZ relevant sein, d.h. true zurück.
                                                                               	//wird verwendet in der KernelExpressionIniConverterZZZ - Klasse.
	boolean isStringForComputeRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
   																				//wird verwendet in der KernelExpressionIniConverterZZZ - Klasse.
	
	//Vector mit 3 Elementen und den Indexwerten 0 = Vor dem Tag, 1= Der Taginhalt 2= Nach dem Tag
	//Damit kann man dann Formeln aufloesen.
	public Vector computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;
}
