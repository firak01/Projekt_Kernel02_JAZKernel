package base.files;

import java.io.*;
import java.nio.charset.Charset;

import org.apache.commons.lang.CharSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

import java.awt.*;
import java.nio.file.Files;
import java.util.EnumSet;

/**aus dem Buch "Kryptographie mit Java".
 * Klasse aus dem Anhang, heisst dort nur Datei.java
 *  
 * Die Ausgangsdatei wird verschlüsselt und in eine Datei abgelegt, die man per Dateiauswahl-Dialogbox festlegen kann.
 * @author Fritz Lindhauer, 04.10.2022, 11:12:56
 * 
 */
public class DateiUtil {
	
	private String sFilePath=null;
	
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
      //InputStreamReader fr = new InputStreamReader(new FileInputStream(sPath),"ISO-8859-1");
      
      //InputStreamReader fr = new InputStreamReader(new FileInputStream(sPath),Charset.forName("UTF8"));
      String sCharset = EncodingMappedValueZZZ.EncodingTypeZZZ.UTF8.getAbbreviation();
      InputStreamReader fr = new InputStreamReader(new FileInputStream(sPath),Charset.forName(sCharset));
      
      String sEncoding = fr.getEncoding();
      System.out.println("FileReader: Encoding="+sEncoding);
      
      //Merke: 
      //65533 = Unicode Character 'REPLACEMENT CHARACTER' (U+FFFD)
      //i.e. Your character is not being interpreted correctly within the character encoding you are using, and so is being replaced by the fallback value.

      
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
  
  public boolean schreib(String datStr) {
	    int iEncodingType = EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal();
	    return this.schreib(datStr.getBytes(), iEncodingType);
	  }
  public boolean schreib(int[] Unicode, int iEncodingType) {
    byte[] b = new byte[Unicode.length];
    for (int i=0; i<b.length; i++) b[i] = (byte)Unicode[i];
    return this.schreib(b,iEncodingType);
  }
  public boolean schreib(String datStr, int iEncodingType) {
    return this.schreib(datStr.getBytes(), iEncodingType);
  }
  public boolean schreib(byte[] b, int iEncodingType) {
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
      //FGL: 20211119: Das schreibt aber die Daten wieder nur irgendwie.
      //FileOutputStream fos = new FileOutputStream(pfad + dateiname);
      //fos.write(b);
      //fos.close();
//      } catch(IOException e) { 
//      System.out.println("Datei:"+e); 
//      return false;
//      }
    	
      //Besser das Encoding mitgeben
      int[] ia = new int[b.length];
      for (int i=0; i<b.length; i++) ia[i] = (int)b[i];
        
      String sFilepath = pfad + dateiname;
      this.setFilePath(sFilepath);
     
      /* Merke: Case Expressions must be constant expressions */
      /*
      switch (iEncodingType) {
      case EncodingMappedValueZZZ.EncodingTypeZZZ.ASCII.ordinal():    	  
		UnicodeZZZ.writeAnsiToFile(iaUtf8, sFilepath);  //Mappingfehler, falls ungueltige Zeichen
      	break;
	  case EncodingMappedValueZZZ.EncodingTypeZZZ.UTF8.ordinal():
		UnicodeZZZ.writeUtf8ToFile(iaUtf8, sFilepath);
	    break;
      default:
    	  
    	break;
      }
      */
      
      int[] iaUtf8;
      if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal()) {  
    	  
    	//und nun von Ascii nach Unicode. Zumindest bei Vigenere26 !!! 
        iaUtf8 = UnicodeZZZ.fromAsciiToUtf8(ia); 
		UnicodeZZZ.writeAnsiToFile(iaUtf8, sFilepath);  //Mappingfehler, falls ungueltige Zeichen      	
      }else if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal()) {
    	//bei Vigenere256 nein?
          //int[] iaUtf8 = UnicodeZZZ.plus65(ia);
    	  
        iaUtf8 = ia;    	  
		UnicodeZZZ.writeUtf8ToFile(iaUtf8, sFilepath);
      }else {
    	System.out.println("not processed encoding main type: '" + iEncodingType + "'");
      }
      
    } catch (ExceptionZZZ e) {
	 System.out.println("Datei:"+e); 
     return false;
	}
    return true;
  }
  
  public String getFilePath() {
	  return this.sFilePath;
  }
  public void setFilePath(String sFilePath) {
	  this.sFilePath= sFilePath;
  }
}	

