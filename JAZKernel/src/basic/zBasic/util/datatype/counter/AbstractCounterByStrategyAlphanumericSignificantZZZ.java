package basic.zBasic.util.datatype.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

//public abstract class AbstractCounterByStrategyAlphanumericSignificantZZZ extends CounterByCharacterAscii_AlphanumericZZZ implements ICounterStrategyAlphanumericSignificantZZZ{
public abstract class AbstractCounterByStrategyAlphanumericSignificantZZZ extends AbstractCounterByCharacterAsciiZZZ implements ICounterAlphanumericSignificantZZZ{
	//Merke: Wenn man das darinbehält, bekommt man 2 objCounterStrategy - Objekte
		//TESTE TODO NACHDER VERSCHIEBUNG
		private ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy;	
			//Lösung: Dies hier herausnehmen und die Methoden, welche diese Klasse nutzen mit einem CAST versehene.
		 
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(boolean bLowercase){
		super();
		this.isLowercase(true);
	}	
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);		
	}	
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(){
		super();
		this.setValueCurrent(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN);
	}
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(int iStartingValue){
		super(iStartingValue);		
	}
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(int iStartingValue, ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public AbstractCounterByStrategyAlphanumericSignificantZZZ(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		super(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	
	//###################
	// ++++ Aus Interface
	@Override
	public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategyObject(){
	if(this.objCounterStrategy==null){
		ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
		this.objCounterStrategy = objCounterStrategy;
	}
	return this.objCounterStrategy;
	}
	@Override
	public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}

	
	
	

	

	
}
