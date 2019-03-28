package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;

public interface ICounterByCharacterAsciiFactoryZZZ {
	public static int iCOUNTER_TYPE_NUMERIC=1;
	public static int iCOUNTER_TYPE_ALPHABET=2;
	public static int iCOUNTER_TYPE_ALPAHNUMERIC=3;
	
	//Default ist immer die Multiple-Strategy
	public ICounterStringZZZ createCounter(int iCounterType)  throws ExceptionZZZ;
	public ICounterStringZZZ createCounter(int iCounterType, int iStart) throws ExceptionZZZ ;
	public ICounterStringZZZ createCounter(int iCounterType, String sStart) throws ExceptionZZZ ;
	
	//Alternative Strategy angeben
	public ICounterStringStrategyNumericUserZZZ createCounter(int iCounterType, ICounterStrategyNumericZZZ objCounterStrategy)  throws ExceptionZZZ;
	public ICounterStringStrategyNumericUserZZZ createCounter(int iCounterType, int iStart, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ ;
	public ICounterStringStrategyNumericUserZZZ createCounter(int iCounterType, String sStart, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ ;
	
	public ICounterStringStrategyAlphanumericUserZZZ createCounter(int iCounterType, ICounterStrategyAlphanumericZZZ objCounterStrategy)  throws ExceptionZZZ;
	public ICounterStringStrategyAlphanumericUserZZZ createCounter(int iCounterType, int iStart, ICounterStringStrategyAlphanumericUserZZZ objCounterStrategy) throws ExceptionZZZ ;
	public ICounterStringStrategyAlphanumericUserZZZ createCounter(int iCounterType, String sStart, ICounterStringStrategyAlphanumericUserZZZ objCounterStrategy) throws ExceptionZZZ ;
	
}
