package basic.zBasic;

public interface IConvertEnabledZZZ {
	public String convert(String sLine) throws ExceptionZZZ; //Mache aus einem String "..." den passenden <z: ... Ausdruck.
	boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ; //z.B. ein Leerstring wird für KernelExpressionIni_EmptyZZZ relevant sein, d.h. true zurück.
}
