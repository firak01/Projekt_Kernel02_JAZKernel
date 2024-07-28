package basic.zKernel;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;

public interface IKernelZFormulaIniZZZ {
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface: 
	//getExpressionTagName()
	//getExpressionTagStarting()
	//getExpressionTagClosing()
	//getExpressionTagEmpty()
	
	//Verlagert nach IComputable
	public String parse(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! OHNE <z:.. voran.	
	public String[] parseAsArray(String sLineWithExpression, String sSeparator) throws ExceptionZZZ;
	boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ;//z.B. als Ausdrück für KernelExpressionIni_EmptyZZZ wäre relevant: <z:Empty/>, d.h. true zurück.
   	
    //Verlagert nach IConvertable		
	public String convert(String sLine) throws ExceptionZZZ; //Mache aus einem String "..." den passenden <z: ... Ausdruck.
	boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ; //z.B. ein Leerstring wird für KernelExpressionIni_EmptyZZZ relevant sein, d.h. true zurück.
                                                                               	//wird verwendet in der KernelExpressionIniConverterZZZ - Klasse.
	public String computeAsExpression(String sLineWithExpression) throws ExceptionZZZ; //Mache aus einem String mit <z: ... den errechneten Ausdruck "..." !!! MIT <z:... voran. Dann kann das Ergebnis weiterverarbeit werden.
	
	//wird verwendet in der KernelExpressionIniConverterZZZ - Klasse.
	
	//Vector mit 3 Elementen und den Indexwerten 0 = Vor dem Tag, 1= Der Taginhalt 2= Nach dem Tag
	//Damit kann man dann Formeln aufloesen.
	public Vector<String>computeExpressionFirstVector(String sLineWithExpression) throws ExceptionZZZ;
}
