package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectReflectableImplementerZZZ;

public interface ICounterStrategyZZZ extends IObjectReflectableImplementerZZZ{
	public int getCounterStart();
	public void setCounterStart(int iStart);
	
	//Entsprechende Konstante gibt es im jeweiligen Interface ICounter...ConstantsZZZ, z.B. ICounterAlphabetConstantsZZZ
	//In den Klassen, die ICounterStrategyZZZ implementieren, können nun die jeweils passenden Statischen Werte zurückgegeben werden.
	public int getCharacterPositionMax();
	public int getCharacterPositionMin();	
	public String getCharForPosition(int iPositionInMinMaxRange) throws ExceptionZZZ;
	
	public int getDigitValueMax();
	public int getDigitValueMin();
	public String getCharForDigitValue(int iDigitValueInMinMaxRange) throws ExceptionZZZ;

	//Für die Transformation zwischen Stellenwert und Zeichenwert
	public int getPositionValueForDigitValue(int iDigitValueInMinMaxRange);
	public int getDigitValueForPositionValue(int iPositionInMinMaxRange);
	
	
	public boolean isRightAligned();
	public void isRightAligned(boolean bValue);
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ;
	public int computeNumberForString(String sTotal);
	public String computeStringForNumber(int iNumber) throws ExceptionZZZ; 
	
	public boolean isIncreasableInOtherMethod(); //true=Nur in der initialisierenden Methode kann der Zähler erhöht werden.
	public void isIncreasableInOtherMethod(boolean bValue);
//	public String getClassMethodInitialisedName();
//	public void setClassMethodInitialisedName() throws ExceptionZZZ; //Der Name der Initialisierenden Methode Klasse.Methodenname.Siehe: ReflectCodeZZZ.getCallingStackName().
//	public void setClassMethodInitialisedName(String sClassWithMethod) throws ExceptionZZZ; 
//	public void setClassMethodInitialisedName(Class<?> objClass, String sMethod) throws ExceptionZZZ;	
}
