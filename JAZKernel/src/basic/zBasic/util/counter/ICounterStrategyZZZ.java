package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectReflectableImplementerZZZ;

public interface ICounterStrategyZZZ extends IObjectReflectableImplementerZZZ{
	public boolean isRightAligned();
	public void isRightAligned(boolean bValue);
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ;
	public int computeNumberForString(String sTotal);
	public String computeStringForNumber(int iNumber); 
	
	public boolean isIncreasableInOtherMethod(); //true=Nur in der initialisierenden Methode kann der Zähler erhöht werden.
	public void isIncreasableInOtherMethod(boolean bValue);
//	public String getClassMethodInitialisedName();
//	public void setClassMethodInitialisedName() throws ExceptionZZZ; //Der Name der Initialisierenden Methode Klasse.Methodenname.Siehe: ReflectCodeZZZ.getCallingStackName().
//	public void setClassMethodInitialisedName(String sClassWithMethod) throws ExceptionZZZ; 
//	public void setClassMethodInitialisedName(Class<?> objClass, String sMethod) throws ExceptionZZZ;	
}
