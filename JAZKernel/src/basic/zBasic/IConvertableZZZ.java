package basic.zBasic;

public interface IConvertableZZZ {
	public String convert(String sLine) throws ExceptionZZZ; //Mache aus einem String "..." den passenden <z: ... Ausdruck.
	boolean isStringForConvertRelevant(String sStringToProof) throws ExceptionZZZ; //z.B. ein Leerstring wird für KernelExpressionIni_EmptyZZZ relevant sein, d.h. true zurück.   
}
