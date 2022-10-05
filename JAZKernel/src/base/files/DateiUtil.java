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
    byte[] inhalt = this.lies();
    return new String(inhalt);
  }
  public byte[] lies() {
    int[] inhalt = this.liesUnicode();
    byte[]b = new byte[inhalt.length];
    for (int i=0; i<inhalt.length;i++) b[i] = (byte)inhalt[i];
    return b;
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
      FileReader fr = new FileReader(pfad + dateiname);
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

