package basic.zBasic.util.abstractList;

import basic.zBasic.ExceptionZZZ;

public interface IVector3ZZZ<T> {
	public void replace(T value) throws ExceptionZZZ;
	public void replace(T valueLeft, T valueMid, T valueRight) throws ExceptionZZZ;
	public void replace(Object objValueLeft, Object objValueMid, Object objValueRight, boolean bReturnSeparators, String sSepLeft, String sSepRight) throws ExceptionZZZ;
	public void replace(Object objValueLeft, Object objValueRight, boolean bReturnSeparators, String sSepMid) throws ExceptionZZZ;

}
