package basic.javagently;

import java.io.IOException;

/**Damit IStreamZZZ daraus erben kann 
 * @author Fritz Lindhauer, 11.10.2022, 17:38:28
 * 
 */
public interface IStream {
	public int  readInt () throws IOException;
	public double readDouble () throws IOException;
	public String readString () throws IOException;
	public char   readChar () throws IOException;
	
	public void println(String s);
	public void print(String s);
	public void close() throws IOException;
	
	//static Methoden können in Java 1.7 nicht ins Interface
	//public String format (int number, int align);
	//public String format (double number, int align, int frac);
}
