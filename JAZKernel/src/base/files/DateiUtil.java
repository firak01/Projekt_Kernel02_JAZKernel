package base.files;

import java.io.*;
import java.awt.*;

/**aus dem Buch "Kryptographie mit Java".
 * Klasse aus dem Anhang, heisst dort nur Datei.java
 *  
 * Die Ausgangsdatei wird verschlüsselt und in eine Datei abgelegt, die man per Dateiauswahl-Dialogbox festlegen kann.
 * @author Fritz Lindhauer, 04.10.2022, 11:12:56
 * 
 */
public class DateiUtil {
  public class java {

	}
private String pfad;
  public static String dateiname;

  public DateiUtil() { 
    dateiname = pfad = null;      	//  Konstruktor
  }
  public DateiUtil(String nDateiname) {	//  Konstruktor
    pfad = "";
    dateiname = nDateiname;
  }
//------------  Methoden ------------------
  public String liesString() {
    byte[] inhalt = this.liesAsByte();
    return new String(inhalt);
  }
  public byte[] lies() {
	   return liesAsByte();
	  }
  /** FGL Merke:
   * https://stackoverflow.com/questions/9581530/converting-from-byte-to-int-in-java
   * If you were thinking of the byte as unsigned (156) rather than signed (-100), as of Java 8 there's Byte.toUnsignedInt:
   * <code>byte b2 = -100; // Or `= (byte)156;`
     int = Byte.toUnsignedInt(b2);
     System.out.println(i2); // 156</code>
   * 
   * Prior to Java 8, to get the equivalent value in the int you'd need to mask off the sign bits:
   * <code>byte b2 = -100; // Or `= (byte)156;`
     int i2 = (b2 & 0xFF);
     System.out.println(i2); // 156</code>
   * 
 * @return
 * @author Fritz Lindhauer, 09.10.2022, 08:24:16
 */
public byte[] liesAsByte() {
    int[] inhalt = this.liesUnicode();
    byte[]b = new byte[inhalt.length];
    for (int i=0; i<inhalt.length;i++) b[i] = (byte)inhalt[i];
    return b;
  }
  public int[] liesAsInt() {
	  return liesUnicode();
  }
  public int[] liesUnicode() {
    if (dateiname == null) {
      Frame f = new Frame();
      FileDialog fd = new FileDialog(f);
      fd.setMode(FileDialog.LOAD);
      fd.show();
      dateiname = fd.getFile();
      pfad = fd.getDirectory();
      f = null;
      fd = null;
    }
    int[] inhalt;
    try { 
      File file = new File(pfad+dateiname);
      int dateigroesse = (int)file.length();
      inhalt = new int[dateigroesse];
      String sPath = pfad + dateiname;
      
      /*FGL Fehlerkorrektur
       * https://stackoverflow.com/questions/696626/java-filereader-encoding-issue
Yes, you need to specify the encoding of the file you want to read.
Yes, this means that you have to know the encoding of the file you want to read.
No, there is no general way to guess the encoding of any given "plain text" file.
The one-arguments constructors of FileReader always use the platform default encoding which is generally a bad idea.

Since Java 11 FileReader has also gained constructors that accept an encoding: new FileReader(file, charset) and new FileReader(fileName, charset).
In earlier versions of java, you need to use new InputStreamReader(new FileInputStream(pathToFile), <encoding>).
       
       */
      //Also nicht einfach so: FileReader fr = new FileReader(sPath);
      //Die definierten Standard charsets stehen hier:
      //https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html
      //Merke: ANSI ist also kein passender Typ.
      InputStreamReader fr = new InputStreamReader(new FileInputStream(sPath),"ISO-8859-1");
      
      String sEncoding = fr.getEncoding();
      System.out.println("FileReader: Encoding="+sEncoding);
      for (int i = 0; i < dateigroesse; i++)
      inhalt[i] = fr.read();
      fr.close();
    }
    catch(FileNotFoundException f) {
      System.out.println("Datei " + dateiname + " nicht gefunden!");
      return null;
    }
    catch(IOException e) {
      System.out.println("Datei:"+e); 
      return null;
    }
    return inhalt;
  }
  public boolean schreib(int[] Unicode) {
    byte[] b = new byte[Unicode.length];
    for (int i=0; i<b.length; i++) b[i] = (byte)Unicode[i];
    return this.schreib(b);
  }
  public boolean schreib(String datStr) {
    return this.schreib(datStr.getBytes());
  }
  public boolean schreib(byte[] b) {
    if(dateiname == null) {
      Frame f = new Frame();
      FileDialog fd = new FileDialog(f);
      fd.setMode(FileDialog.SAVE);
      fd.show();
      dateiname = fd.getFile();
      pfad = fd.getDirectory();
      f = null;
      fd = null;
    }
    try { 
      FileOutputStream fos = new FileOutputStream(pfad + dateiname);
      fos.write(b);
      fos.close();
    }
    catch(IOException e) { 
      System.out.println("Datei:"+e); 
      return false;
    }
    return true;
  }
}	

