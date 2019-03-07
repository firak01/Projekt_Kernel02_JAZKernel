package basic.zBasic.util.datatype.counter;

public interface ICounterByCharacterAsciiFactoryZZZ {
	public static int iCOUNTER_TYPE_NUMERIC=1;
	public static int iCOUNTER_TYPE_ALPHABET=2;
	public static int iCOUNTER_TYPE_ALPAHNUMERIC=3;
	
	public ICounterStringZZZ createCounter(int iCounterType);
	public ICounterStringZZZ createCounter(int iCounterType, String sStart);
	
	
}
