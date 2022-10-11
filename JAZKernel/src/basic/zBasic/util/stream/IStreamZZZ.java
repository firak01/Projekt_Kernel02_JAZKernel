package basic.zBasic.util.stream;

import basic.javagently.IStream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;

public interface IStreamZZZ extends IObjectZZZ, IStream{
	public void setCharsetName(String sCharsetName);
	public String getCharsetName();
	
	public String readLineLast() throws ExceptionZZZ;
}
