package basic.zBasic;

public interface IVectorLimitedZZZ {
	public int sizeMax(); //Merke: Kein Setter, da diese nicht flexible sesin soll.
	public boolean isValidSize(int iSize) throws ExceptionZZZ;
	public boolean ensureValidSize(int iSize) throws ExceptionZZZ; 
	public boolean ensureValidIndex(int iSize) throws ExceptionZZZ;
}
