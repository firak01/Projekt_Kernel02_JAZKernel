package basic.zBasic;

public interface IVectorLimitedZZZ extends IResettableValuesWithDefaultZZZ{
	public int sizeMax(); //Merke: Kein Setter, da diese nicht flexible sesin soll.
	public int getSizeMax(); //wie sizeMax...
	
	public boolean isValidSize(int iSize) throws ExceptionZZZ;
	public boolean ensureValidSize(int iSize) throws ExceptionZZZ; 
	public boolean ensureValidIndex(int iSize) throws ExceptionZZZ;
		
	public Object getObjectDefaultNew() throws ExceptionZZZ;
	
}
