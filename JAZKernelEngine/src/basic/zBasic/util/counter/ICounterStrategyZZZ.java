package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectReflectableImplementerZZZ;

public interface ICounterStrategyZZZ extends IObjectReflectableImplementerZZZ{
	public int getCounterStart() throws ExceptionZZZ;
	public void setCounterStart(int iStart) throws ExceptionZZZ;
	public int getCounterStartDefault() throws ExceptionZZZ;
	public void setCounterStartDefault(int iStart) throws ExceptionZZZ;
	
	//Entsprechende Konstante gibt es im jeweiligen Interface ICounter...ConstantsZZZ, z.B. ICounterAlphabetConstantsZZZ
	//In den Klassen, die ICounterStrategyZZZ implementieren, können nun die jeweils passenden Statischen Werte zurückgegeben werden.
	public int getCharacterPositionMax() throws ExceptionZZZ;
	public int getCharacterPositionMin() throws ExceptionZZZ;	
	public String getCharForPosition(int iPositionInMinMaxRange) throws ExceptionZZZ;
	
	public int getDigitValueMax() throws ExceptionZZZ;
	public int getDigitValueMin() throws ExceptionZZZ;
	public String getCharForDigitValue(int iDigitValueInMinMaxRange) throws ExceptionZZZ;

	//Für die Transformation zwischen Stellenwert und Zeichenwert
	public int getPositionValueForDigitValue(int iDigitValueInMinMaxRange) throws ExceptionZZZ;
	public int getDigitValueForPositionValue(int iPositionInMinMaxRange) throws ExceptionZZZ;
	
	public boolean isRightAligned() throws ExceptionZZZ;
	public void isRightAligned(boolean bValue) throws ExceptionZZZ;
	public boolean isLeftAligned() throws ExceptionZZZ;
	public void isLeftAligned(boolean bValue) throws ExceptionZZZ;
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ;
	public int computeNumberForString(String sTotal) throws ExceptionZZZ;
	public String computeStringForNumber(int iNumber) throws ExceptionZZZ; 
	
	public boolean isIncreasableInOtherMethod() throws ExceptionZZZ; //true=Nur in der initialisierenden Methode kann der Zähler erhöht werden.
	public void isIncreasableInOtherMethod(boolean bValue) throws ExceptionZZZ;
//	public String getClassMethodInitialisedName();
//	public void setClassMethodInitialisedName() throws ExceptionZZZ; //Der Name der Initialisierenden Methode Klasse.Methodenname.Siehe: ReflectCodeZZZ.getCallingStackName().
//	public void setClassMethodInitialisedName(String sClassWithMethod) throws ExceptionZZZ; 
//	public void setClassMethodInitialisedName(Class<?> objClass, String sMethod) throws ExceptionZZZ;	
}
