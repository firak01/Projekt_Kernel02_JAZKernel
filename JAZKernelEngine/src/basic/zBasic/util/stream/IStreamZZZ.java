package basic.zBasic.util.stream;

import basic.javagently.IStream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExceptionZZZ;
import basic.zBasic.IObjectZZZ;

public interface IStreamZZZ extends IObjectZZZ, IObjectWithExceptionZZZ, IStream{
	public void setCharsetName(String sCharsetName);
	public String getCharsetName();
	
	public String readLineNext() throws ExceptionZZZ;
	public String readLineLast() throws ExceptionZZZ;
}
