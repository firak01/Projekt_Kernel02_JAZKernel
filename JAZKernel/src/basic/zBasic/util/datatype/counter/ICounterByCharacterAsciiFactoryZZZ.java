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
	public ICounterStrategyNumericUserZZZ createCounter(int iCounterType, ICounterStrategyNumericZZZ objCounterStrategy)  throws ExceptionZZZ;
	public ICounterStrategyNumericUserZZZ createCounter(int iCounterType, int iStart, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ ;
	public ICounterStrategyNumericUserZZZ createCounter(int iCounterType, String sStart, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ ;
	
	public ICounterStrategyAlphanumericUserZZZ createCounter(int iCounterType, ICounterStrategyAlphanumericZZZ objCounterStrategy)  throws ExceptionZZZ;
	public ICounterStrategyAlphanumericUserZZZ createCounter(int iCounterType, int iStart, ICounterStrategyAlphanumericUserZZZ objCounterStrategy) throws ExceptionZZZ ;
	public ICounterStrategyAlphanumericUserZZZ createCounter(int iCounterType, String sStart, ICounterStrategyAlphanumericUserZZZ objCounterStrategy) throws ExceptionZZZ ;
	
}
