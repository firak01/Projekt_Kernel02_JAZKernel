package basic.javagently;

import java.io.*;
import java.util.*;
import java.text.*;


public class Stream implements IStream{

  /** The Stream class     by J M Bishop and B Worrall May 2000
   *                      based on the Text class Aug 1996
   *                      on suggestion from Jens Kaasb�ll
   *
   * Provides simple input from the keyboard and files.
   * And formatted output to the screen and files.
   *
   * Constructors
   * ------------
   * public Stream (InputStream in)
   * public Stream (String filename, int why)
   *
   * Input
   * -----
   * public int    readInt ()
   * public double readDouble ()
   * public String readString ()
   * public char   readChar ()
   *
   * Output
   * ------
   * public void println  - for Objects, String, int, double, char
   * public void print    - for Objects, String, int, double, char
   * public void close()
   *
   * Output - class methods
   * ----------------------
   * public String format (int number, int align)
   * public String format (double number, int align, int frac)
   */


  //FGL 20221009: Mache die Variablen protected, damit sie von aus dieser Klasse erbenden Klassen sicher genutzt werden können
  //                Z.B. SreamZZZ
  //  private BufferedReader in;
  //  private PrintWriter out;
  protected BufferedReader in;
  protected PrintWriter out;
  private StringTokenizer T;
  private String S;

  public static final int
	READ = 0,
	WRITE = 1;

  public Stream () {
	  //FGL: Default Konstruktor, zum Besseren Vererben.
  }
  
  public Stream(InputStream i) throws Exception{
	in = open(i);
  }




/** Creates/Opens a file
* Lindhauer; 19.04.2006 09:54:29
 * @param filename
 * @param how; 0=open file for reading, 1= open/create file  
 * @throws FileNotFoundException
 * @throws IOException
 */
public Stream(String filename, int how) throws FileNotFoundException, IOException, Exception {
	switch(how) {
	  case READ: in = open(filename); break;
	  case WRITE: out = create(filename); break;
	}
  }

  protected BufferedReader open(InputStream in) throws Exception {
	return new BufferedReader(new InputStreamReader(in));
  }

  protected BufferedReader open(String filename) throws FileNotFoundException,Exception {
	return new BufferedReader(new FileReader(filename));
  }

  protected PrintWriter create(String filename) throws IOException,Exception {
	return new PrintWriter(new FileWriter(filename));
  }

  public String readLine () throws IOException {
    refresh();
    return S;
  }

  public int readInt () throws IOException {
    if (T==null) refresh();
    while (true) {
      try {
        return Integer.parseInt(T.nextToken());
      }
      catch (NoSuchElementException e1) {
        refresh ();
      }
      catch (NumberFormatException e2) {
        System.out.println("Error in number, try again.");
      }
    }
  }

  public char readChar () throws IOException {
    if (T==null) refresh();
    while (true) {
      try {
        return T.nextToken().trim().charAt(0);
      }
      catch (NoSuchElementException e1) {
        refresh ();
      }
    }
  }

  public double readDouble () throws IOException {
    if (T==null) refresh();
    while (true) {
      try {
        String item = T.nextToken();
        return Double.valueOf(item.trim()).doubleValue();
      }
      catch (NoSuchElementException e1) {
        refresh ();
      }
      catch (NumberFormatException e2) {
        System.out.println("Error in number, try again.");
      }
    }
  }

  public String readString () throws IOException {
    if (T==null) refresh ();
    while (true) {
      try {
        return T.nextToken();
      }
      catch (NoSuchElementException e1) {
        refresh ();
      }
    }
  }

  private void refresh () throws IOException {
    S = in.readLine ();
    if (S==null) throw new EOFException();
    T = new StringTokenizer (S);
  }

  private static DecimalFormat N = new DecimalFormat();
  private static final String spaces = "                    ";

  public static String format(double number, int align, int frac) {
    N.setGroupingUsed(false);
	N.setMaximumFractionDigits(frac);
	N.setMinimumFractionDigits(frac);
	String num = N.format(number);
	if (num.length() < align)
	   num = spaces.substring(0,align-num.length()) + num;
    return num;
  }

  public static String format(int number, int align) {
    N.setGroupingUsed(false);
    N.setMaximumFractionDigits(0);
    String num = N.format(number);
    if (num.length() < align)
       num = spaces.substring(0,align-num.length()) + num;
    return num;
  }

	public void println(Object s) {
		out.println(String.valueOf(s));
		out.flush();
	}

	public void println(String s) {
		out.println(s);
		out.flush();
	}

	public void println(int s) {
		out.println(s);
		out.flush();
	}

	public void println(double s) {
		out.println(s);
		out.flush();
	}

	public void println(char s) {
		out.println(s);
		out.flush();
	}

	public void print(Object s) {
		out.print(String.valueOf(s));
		out.flush();
	}

	public void print(String s) {
		out.print(s);
		out.flush();
	}

	public void print(int s) {
		out.print(s);
		out.flush();
	}

	public void print(double s) {
		out.print(s);
		out.flush();
	}

	public void print(char s) {
		out.print(s);
		out.flush();
	}

	public void close() throws IOException {
		if (out != null)
			out.close();
		if (in != null)
			in.close();
	}

	public void flush() {
		out.flush();
	}
	
	/** Erweitert um Getter/Setter, damit man aus dieser Klasse erben kann
	 *  s. StreamZZZ und nur gezielt Methoden überschreibt.
	 *  
	 * @param reader
	 * @author Fritz Lindhauer, 09.10.2022, 09:17:20
	 */
	public void setBufferedReader(BufferedReader reader) {
		this.in = reader;
	}
	public BufferedReader getBufferedReader() {
		return this.in;
	}
	
	/** Erweitert um Getter/Setter, damit man aus dieser Klasse erben kann
	 *  s. StreamZZZ und nur gezielt Methoden überschreibt.
	 *  
	 * @param reader
	 * @author Fritz Lindhauer, 09.10.2022, 09:17:20
	 */
	public void setPrintWriter(PrintWriter writer) {
		this.out = writer;
	}
	public PrintWriter getPrintWriter() {
		return this.out;
	}


}
