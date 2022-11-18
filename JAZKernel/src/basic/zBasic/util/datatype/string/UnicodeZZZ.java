package basic.zBasic.util.datatype.string;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;

/**Internally in Java all strings are kept in Unicode. 
 * Since not all text received from users or the outside world is in unicode, 
 * your application may have to convert from non-unicode to unicode. 
 * Additionally, when the application outputs text it may have to convert the internal unicode format 
 * to whatever format the outside world needs.

    Java has a few different methods you can use to convert text to and from unicode. These methods are:

    The String class
    The Reader and Writer classes and subclasses


First of all I would like to clarify that Unicode consist of a set of "code points" 
which are basically a numerical value that corresponds to a given character. 
There are several ways to "encode" these code points (numerical values) into bytes. 
The two most common ones are UTF-8 and UTF-16. In this tutorial I will only show examples of converting 
to UTF-8 - since this seems to be the most commonly used Unicode encoding.

 * 
 * @author Fritz Lindhauer, 15.11.2022, 08:32:28
 * 
 */
public class UnicodeZZZ {
	/** 
	 * You can also write unicode characters directly in strings in the code, by escaping the with "\\u". (only 1 backslash, the second backslash her is for escaping the unicode)
	 * Here is an example:
	 * The danish letters Æ Ø Å
	 * String myString = "\u00C6\u00D8\u00C5" ;
	 * 
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:28:35
	 */
	public static String from(char c) {
		String sReturn = null;
		main:{			
			if(CharZZZ.isEmpty(c)) break main;			
			sReturn = "\\u" + Integer.toHexString('÷' | 0x10000).substring(1);
		}//end main:
		return sReturn;
	}
	
	public static String from(String s) {
		String sReturn = "";
		main:{
			if(s==null)break main;
			if(s=="") {
				sReturn = null;
				break main;
			}
			
			String stemp;
			char[]ca=s.toCharArray();
			for(char c : ca) {
				stemp = UnicodeZZZ.from(c);
				sReturn += stemp;
			}
		}//end main:
		return sReturn;
	}
	
	/** Converting to and from Unicode UTF-8 Using the String Class

		You can use the String class to convert a byte array to a String instance. 
		You do so using the constructor of the String class. 
				
		This example first creates a byte array. 
		The byte array does not actually contain any sensible data, but for the sake of the example, 
		that does not matter. 
		The example then creates a new String, passing the byte array and the character set of the characters in the byte array as parameters to the constructor. 
		The String constructor will then convert the bytes from the character set of the byte array to unicode.
		
		
		Siehe auch StringZZZ.toUtf8(string)
	 * @param ba
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:38:36
	 */
	public static String from(byte[] bytea) {
		String sReturn = "";
		main:{
			if(bytea==null) {
				sReturn=null;
				break main;
			}
			if(bytea.length==0) {
				break main;
			}
			
			sReturn = new String(bytea, Charset.forName("UTF-8"));
		}//end main:
		return sReturn;
	}
	
	/** You can convert the text of a String to another format using the getBytes() method. 
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:44:16
	 */
	public static byte[] toByteArray(String s) {
		byte[] bytea = s.getBytes(Charset.forName("UTF-8"));
		return bytea;
	}
	
	public static int[] toIntArray(String s) {
		byte[]bytea = UnicodeZZZ.toByteArray(s);
		int[]iaReturn = new int[bytea.length];
		
		int iIndex=-1;
		for(byte bt : bytea) {
			iIndex++;
			iaReturn[iIndex] = bt;
		}
		return iaReturn;
	}
	
	/**
	 * Converting to and from Unicode UTF-8 Using the Reader and Writer Classes
	 * The Reader and Writer classes are stream oriented classes 
	 * that enable a Java application to read and write streams of characters. 
	 
	 * Here is an example that uses an InputStreamReader to convert from a certain character set (UTF-8) to unicode.
	 * This example creates a FileInputStream and wraps it in a InputStreamReader. 
	 * The InputStreamReader is told to interprete the characters in the file as UTF-8 characters. 
	 * This is done using the second constructor paramter in the InputStreamReader class.
	 * 
	 * @param sFilepath, e.g.: "c:\\data\\utf-8-text.txt"
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:56:56
	 */
	public static String fileToUtf8 (String sFilepath) throws ExceptionZZZ{
		String sReturn="";
		main:{
			if(StringZZZ.isEmpty(sFilepath)) {
				sReturn = null;
				break main;
			}
			
			try {
			
				InputStream inputStream = new FileInputStream(sFilepath);
				Reader      reader      = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

				int data = reader.read();
				while(data != -1){
					char theChar = (char) data;
					sReturn += theChar;
					
					data = reader.read();				
				}
				reader.close();
			} catch (FileNotFoundException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
			
		}//end main:
		return sReturn;
	}
	
	/**
	 * Here is an example writing a stream of characters back out to UTF-8.
	 * This example creates an OutputStreamWriter which converts the string written through it to the UTF-8 character set.
	 * @author Fritz Lindhauer, 15.11.2022, 09:09:16
	 */
	public static boolean writeUtf8ToFile(String s, String sFilepath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFilepath)) break main;
			
			try {
				OutputStream outputStream = new FileOutputStream(sFilepath);			
				Writer       writer       = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
				if(s!=null) writer.write(s);			
				writer.close();
				
				bReturn = true;
			} catch (FileNotFoundException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return bReturn;
	}
	
	/** Damit werden nichtdruckbare Zeichen ausgespart.
	 *  Z.B. angewendet in meiner Überarbeitung des Codes zum Buch "Kryptografie mit Java", Vigenere Verschluesselung, S. 33ff
	 *  
	 * @param s
	 * @return
	 * @author Fritz Lindhauer, 17.11.2022, 08:45:23
	 */
	public static int[] fromUtf8ToAscii(String s) {
		
		//int[] sa = IoUtil.Unicode(s.getBytes()); //Original, Klasse aus dem Buch
		int[] ia = UnicodeZZZ.fromByteToInt(s.getBytes()); 
		return UnicodeZZZ.fromUtf8ToAscii(ia);  	
	}
	public static int[] fromUtf8ToAscii(int[] ia) {
		    
		    //FGL: Nun die Zeichen vom UTF-8 Wert an ASCII anpassen
		    int[]iaPure = new int[ia.length];

		    for(int i=0;i<ia.length;i++) {
		    	//iaPure[i]=ia[i]-65;
		    	iaPure[i] = UnicodeZZZ.fromUtf8ToAscii(ia[i]);
		    }
		    
		 return iaPure;   		
	}
	public static int fromUtf8ToAscii(int iz) {
		int iReturn=-1;
		main:{
			if (iz<0) iz+=256;		
		    if (iz==10) {
		    	//System.out.println();
		    }else {
		       //Nichtdruckbare Zeichen und besondere rechnerspezifische Zeichen ausschliessen.	    	
			   if (((iz>31)&&(iz<127)) || ((iz>160)&&(iz<256) )) {       
		         //System.out.print((char)(z) + ":"+z+sSeparator);
				 iReturn = iz - 65;
		       }else {
		         //System.out.print("." + ":"+z+sSeparator);
		       }
		    }
		}//end main:
	    return iReturn;		
	}
	
	
	 /** Angelehnt an "Kryptografie mit Java", IoUtil.Unicode(s.getBytes());
	  * 
	 * @param ByteFeld
	 * @return
	 * @author Fritz Lindhauer, 17.11.2022, 08:53:24
	 */
	public static int[] fromByteToInt(byte[] ByteFeld) {
	    int[] dummy=new int[ByteFeld.length];
	    for (int i=0; i<dummy.length; i++) 
	      if (ByteFeld[i]>=0)  dummy[i]=ByteFeld[i];
	      else                 dummy[i]=256+ByteFeld[i];
	    return dummy;
	  }
	
}
