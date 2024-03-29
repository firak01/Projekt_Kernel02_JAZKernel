package base.files;

import java.io.*;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.CharSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
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
	
	
	
  public class java {

	}
private String pfad=null;
private String dateiname=null;
private File objFile=null;

  public DateiUtil() { 
    dateiname = pfad = null;      	//  Konstruktor
  }
  public DateiUtil(String nDateiname) {	//  Konstruktor
    pfad = "";
    dateiname = nDateiname;
  }
  public DateiUtil(String sPath, String sFilename) {
	  this.pfad = sPath;
	  this.dateiname = sFilename;
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
	  int[] inhalt=null;
	  main:{
    if (dateiname == null) {
    	boolean btemp = this.selectLoad();
    	if(!btemp)break main;
    }
    
    try { 
      File file = new File(pfad+dateiname);
      int dateigroesse = (int)file.length();
      int dateigroesse2 = (int) FileUtils.sizeOf(file);
      inhalt = new int[dateigroesse];
      
      
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
      InputStreamReader fr = new InputStreamReader(new FileInputStream(this.computeFilePath()),Charset.forName(sCharset));
      
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
  	}//end main:
    return inhalt;
  }
  
  public boolean schreib(String datStr) {
	    int iEncodingType = EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal();
	    return this.schreib(datStr.getBytes(), iEncodingType);
	  }
  public boolean schreib(int[] unicode) {
	  int iEncodingType = EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal();
	    return this.schreib(unicode, iEncodingType);
  }
  
  public boolean schreib(String datStr, int iEncodingType) {
    return this.schreib(datStr.getBytes(), iEncodingType);
  }
  public boolean schreib(int[] iaIn, int iEncodingType) {
	  boolean bReturn = false;
      main:{
	     try {
		    if(dateiname == null) {
		    	boolean btemp = this.selectSave();
		    	bReturn = btemp;
		    }
		    if(!bReturn)break main;
	      //FGL: 20211119: Das schreibt aber die Daten wieder nur irgendwie.
	      //FileOutputStream fos = new FileOutputStream(pfad + dateiname);
	      //fos.write(b);
	      //fos.close();
//	      } catch(IOException e) { 
//	      System.out.println("Datei:"+e); 
//	      return false;
//	      }
	    	
	      //Besser das Encoding mitgeben     
	     
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
	     
	      /* schreibt in der Codierung ANSI
	      FileOutputStream fos = new FileOutputStream(pfad + dateiname);
			for(int i=0;i<ia.length;i++) {
				char c = (char)ia[i];
		      fos.write(c);			      
		      }
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	      
	      
	      int[] ia = iaIn;
	      if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal()) {   
			File objFile = UnicodeZZZ.writeAnsiToFile(ia, this.computeFilePath());  //Mappingfehler, falls ungueltige Zeichen
			this.setFile(objFile);
	      }else if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal()) { 	  
			File objFile = UnicodeZZZ.writeUtf8ToFile(ia, this.computeFilePath());
			this.setFile(objFile);
	      }else {
	    	System.out.println("not processed encoding main type: '" + iEncodingType + "'");
	    	bReturn = false;
	      }	     
	    } catch (ExceptionZZZ e) {
		 System.out.println("Datei:"+e); 
	     return false;
		}
	  	}//end main:
	    return bReturn;
	  }
  
  public boolean schreibUsingPoolPosition(int[] iaIn, int iEncodingType, ArrayListExtendedZZZ<CharacterExtendedZZZ>listasCharacterPool) {
	  	boolean bReturn = false;
        main:{
	     try {
		    if(dateiname == null) {
		    	boolean btemp = this.selectSave();
		    	bReturn = btemp;
		    }
		    if(!bReturn)break main;
		     
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
		     
		      /* schreibt in der Codierung ANSI
		      FileOutputStream fos = new FileOutputStream(pfad + dateiname);
				for(int i=0;i<ia.length;i++) {
					char c = (char)ia[i];
			      fos.write(c);			      
			      }
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
	      
	      int[] ia = iaIn;
	      if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal()) {   
			File objFile = UnicodeZZZ.writeAnsiToFileUsingPoolPosition(ia, this.computeFilePath(),listasCharacterPool);  //Mappingfehler, falls ungueltige Zeichen
			this.setFile(objFile);
	      }else if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal()) { 	  
			File objFile = UnicodeZZZ.writeUtf8ToFileUsingPoolPosition(ia, this.computeFilePath(),listasCharacterPool);
			this.setFile(objFile);
	      }else {
	    	System.out.println("not processed encoding main type: '" + iEncodingType + "'");
	    	bReturn = false;
	      }	     
	    } catch (ExceptionZZZ e) {
		 System.out.println("Datei:"+e); 
	     return false;
		}
	  	}//end main:
	    return bReturn;
	  }
  
  public boolean schreib(byte[] b, int iEncodingType) {
	  boolean bReturn = false;
      main:{
	     try {
		    if(dateiname == null) {
		    	boolean btemp = this.selectSave();
		    	bReturn = btemp;
		    }
		    if(!bReturn)break main;
      //FGL: 20211119: Das schreibt aber die Daten wieder nur irgendwie.
      //FileOutputStream fos = new FileOutputStream(pfad + dateiname);
      //fos.write(b);
      //fos.close();
//      } catch(IOException e) { 
//      System.out.println("Datei:"+e); 
//      return false;
//      }
    	
      //Besser das Encoding mitgeben     
      this.setPfad(pfad);
      this.setDateiname(dateiname);
     
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
      
      int[] iaIn = new int[b.length];
      for (int i=0; i<b.length; i++) iaIn[i] = (int)b[i];
      
      int[] ia = iaIn;
      if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.ASCII.ordinal()) {  
		File objFile = UnicodeZZZ.writeAnsiToFile(ia, this.computeFilePath());  //Mappingfehler, falls ungueltige Zeichen
		this.setFile(objFile);
      }else if(iEncodingType == EncodingMaintypeZZZ.TypeZZZ.UTF8.ordinal()) {    	    	 
		File objFile = UnicodeZZZ.writeUtf8ToFile(ia, this.computeFilePath());
		this.setFile(objFile);
      }else {
    	System.out.println("not processed encoding main type: '" + iEncodingType + "'");
    	bReturn = false;
      }
      
    } catch (ExceptionZZZ e) {
	 System.out.println("Datei:"+e); 
     return false;
	}
	  }//end main:
    return bReturn;
  }
  
  public String computeFilePath() {
	  String sPath = pfad + dateiname;
	  return sPath;
  }  
  public void setPfad(String sPath) {
	  this.pfad = sPath;	  
  }
  public void setDateiname(String sFilename) {
	  this.dateiname = sFilename;
  }
  public File getFile() {
	  return this.objFile;
  }
  /**Im Konstruktor nur Pfad und Dateiname.
   * Die hier gesetzte Datei kommt aus der Verarbeitung
   * und wird nur gespeichert, um mit getFile() geholt werden zu können.
 * @param objFile
 * @author Fritz Lindhauer, 07.12.2022, 09:09:58
 */
private void setFile(File objFile) {
	  this.objFile=objFile;	  
  }
  private boolean select(int iWhatFor) {
	  boolean bReturn = false;
	  main:{
	      Frame f = new Frame();
	      FileDialog fd = new FileDialog(f);
	      fd.setMode(iWhatFor);
	      fd.show();
	      dateiname = fd.getFile();
	      pfad = fd.getDirectory();
	      f = null;
	      fd = null;
    
	      this.setPfad(pfad);
	      this.setDateiname(dateiname);
	      
	      bReturn = true;
	  }
	  return bReturn;
  }
  public boolean selectLoad() {
	  boolean bReturn = false;
	  main:{
		  bReturn = this.select(FileDialog.LOAD);	      
	  }
	  return bReturn;
  }
  public boolean selectSave() {
	  boolean bReturn = false;
	  main:{
	     bReturn = this.select(FileDialog.SAVE);
	  }
	  return bReturn;
  }
}	

