package basic.zBasic.util.crypt;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import base.files.DateiUtil;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

public class VigenereZZZ extends AbstractVigenereZZZ{
	private static final long serialVersionUID = -2833560399688739434L;
	
	public VigenereZZZ() {
		super();
	}
	
	public VigenereZZZ(String sKeyString) throws ExceptionZZZ {
		super();
		this.setCryptKey(sKeyString);
	}
	
	
	public VigenereZZZ(String sKeyString, String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
		this.setCryptKey(sKeyString);
	}
	
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		String sKeyString = this.getCryptKey();
		return VigenereZZZ.encrypt(sInput, sKeyString);
	}
	
	public static String encrypt(String sInput, String sKeyString) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			if(sInput==null) {
				sReturn = null;			
				break main;
			}
			if(sInput=="") break main;
		
	
		      
		    int[] s = IoUtil.Unicode(sKeyString.getBytes()); //FGL: Die Schlüsselwortbuchstaben
		    
		    
		    DateiUtil Original;
		    int c, i, laengeSW;   
		   
		    
//		    if (arg.length > 0) {
//		    	Original = new DateiUtil(arg[0]);
//		    } else {
//		    	Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Beispieltext2_ohne_Sonderzeichen.txt");
//		    	//Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext2_zur_Vigenere_Verschluesselung.txt");
//		    	//Original = new DateiUtil("tryout\\basic\\zBasic\\util\\crypt\\encode\\file\\Langer_Beispieltext1_ohne_Sonderzeichen.txt");
//		    }
//		    
//		    if (arg.length > 1) {
//		    	SchluesselWort = (arg[1]); 
//		    }else {
//		    	//Nur weil das Schlüsselwort nicht angegeben wurde... ?    System.exit(0);
//		    }
		    
		    
		    laengeSW = sKeyString.length();
		    int[] p = UnicodeZZZ.toIntArray(sInput);
		    
//		    int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
//		    System.out.print("Originaltext ausgeben? (J/N): ");
//		    if (IoUtil.JaNein()) {
//		      System.out.println("---- Originaltext von: "+DateiUtil.dateiname+" ----");
//		      for (i=0; i < p.length; i++) {
//		        IoUtil.printChar(p[i]);	// druckbares Zeichen?
//		        if (((i+1)%80)==0) System.out.println();
//		      }
//		    }
//		    System.out.println("\n-- Verschluessele Text von: "+DateiUtil.dateiname+" --");
		    for (i = 0; i < p.length; i++) {
		    	if(i>=1) System.out.print("|");
		      //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	int iSum = s[iIndexS]+p[i];
		    	int iFormula = (iSum)%256;
		      c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
		      System.out.print("i="+c);
		      p[i] = c;				// nur wegen abspeichern
		      
		    }	
		    
		    char[] ca = new char[p.length];
		    int iindex=-1;
		    for(int itemp : p) {
		    	iindex++;
		    	char ctemp = (char)itemp;
		    	ca[iindex]=ctemp;
		    }
		    
		    try {
			    CharBuffer cbuf = CharBuffer.wrap(ca);
			    //CharBuffer charBuffer = CharBuffer.wrap("Hello World");
			    
			    //Charset charset1 = Charset.forName("ISO-8859-1");
			    Charset charset1 = Charset.forName("UTF8");
			    //ByteBuffer byteBuffer = Charset.forName("utf-8").encode(charBuffer);
			    ByteBuffer byteBuffer = charset1.encode(cbuf);
			    	        
		        CharsetDecoder decoder = charset1.newDecoder();	        
			    CharBuffer cbuf2 = decoder.decode(byteBuffer);
			    String s2 = cbuf2.toString();
			    System.out.println("\n");
			    System.out.println(s2);
			} catch (CharacterCodingException e) {
			}
		    
		    String stemp;
		    for(int itemp : p) {
		    	//das macht nur aus der Zahl einen String-Wert, nicht das Zeichen  stemp = IoUtil.intToString(itemp);
		    	stemp = CharZZZ.toString(itemp);
		    	
		    	// Gets charset
		        Charset charset1 = Charset.forName("UTF8");
		        CharsetDecoder decoder = charset1.newDecoder();
		        //CharsetEncoder encoder = charset1.newEncoder();

				
				  // Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
				  // The new ByteBuffer is ready to be read.
				  //ByteBuffer bbuf = encoder.encode(CharBuffer.wrap("a string"));
				  
					
				  // Convert ISO-LATIN-1 bytes in a ByteBuffer to a character ByteBuffer and then to a string.
				  // The new ByteBuffer is ready to be read.
//				  CharBuffer cbuf = decoder.decode(bbuf);
//				  String s = cbuf.toString();
				
		        
		    	sReturn += stemp;
		    }
		    
		    
//		    System.out.print("Verschluesselten Text ausgeben? (J/N): ");
//		    if (IoUtil.JaNein()) {
//		      System.out.println("\n\n-- Verschluesselter Text von: "+DateiUtil.dateiname+" --");
//		      for (i = 0; i < p.length; i++) {
//		    	IoUtil.printCharWithPosition((p[i]),"|");
//		        if (((i+1)%80)==0) System.out.println();	// neue Zeile
//		      }
//		    }
//		    System.out.println("\n---- Dateilaenge: "+p.length+" Bytes ----\n ");
//		    DateiUtil Kodiert = new DateiUtil();
//		    Kodiert.schreib(p);
//		    System.exit(0);
		}//end main:
		return sReturn;
	}

	@Override
	public int[] decrypt(int[] p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getEncryptedValuesAsInt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DateiUtil getFileOriginal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean encryptUI() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean decryptUI() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
