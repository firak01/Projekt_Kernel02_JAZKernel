package basic.zBasic.util.crypt;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.HashMap;
import java.util.Map;

import base.files.DateiUtil;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.crypt.encode.Vigenere256ZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

public class VigenereNnZZZ extends AbstractVigenereZZZ{
	private static final long serialVersionUID = -2833560399688739434L;	
	protected String sCharacterUsedForRot = null; //protected, damit erbende Klassen auch nur auf diesen Wert zugreifen!!!
	public static int iOffsetForAsciiRange=0;//wird dann später aus der Laenge des CharacterPools errechnet.
	public static int iOffsetForUtf8Range=0; //im CharacterPool sind keine nicht druckbaren Zeichen.
	
	
	public VigenereNnZZZ() {
		super();
	}
	
	public VigenereNnZZZ(String sKeyString) throws ExceptionZZZ {
		super();
		this.setCryptKey(sKeyString);
	}
	
	
	public VigenereNnZZZ(String sKeyString, String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
		this.setCryptKey(sKeyString);
	}

	public static String encrypt(String sInput, String sCharacterPoolIn, String sKeyword, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric) throws IllegalArgumentException, ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolStarting;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPoolStarting=CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
			}else {
				sCharacterPoolStarting = sCharacterPoolIn;
			}
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolStarting, bUseUppercase, bUseLowercase, bUseNumeric);
			sReturn = VigenereNnZZZ.encrypt(sInput, abcABC, sKeyword);		
		}//end main;
		return sReturn;
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static String encrypt(String sInput, String sCharacterPoolIn, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		String sReturn = sInput;
		main:{
			sReturn = VigenereNnZZZ.encrypt(sInput, sCharacterPoolIn, false, false, false, sKeyword);
		}//end main;
		return sReturn;
    }
	
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static String encrypt(String sInput, String sCharacterPoolIn, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPool= null;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPool= VigenereNnZZZ.getCharacterPoolDefault();
			}else {
				sCharacterPool = sCharacterPoolIn;
			}
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPool, bUseUppercasePool, bUseLowercasePool, bUseNumericPool);
			
			
			
			//IDEE DER NN-Behandlung:
			//Jeden Buchstaben in einen Integer-Wert ueberfuehren, der seine Position in dem Character-Pool hat.
			//TODOGOON20221218;
			
			int len = abcABC.length();
			
			//MERKE: Wg. der Zuordnung zu einer Map muss sichergestellt sein, dass kein Zeichen im CharacterPool doppelt vorkommt.
			//+++++++++++ CharacterPool normieren			
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = new ArrayListExtendedZZZ<CharacterExtendedZZZ>();
			for (int i = 0; i < len; i++) {		
				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(abcABC.charAt(i));
				listasCharacterPool.addUnique(objChar);				
		    }			
			//+++++++++++
			
			
			len = listasCharacterPool.size();

			//So würde ein um n Positionen verschobenes Zeichen gemappt.
			/* 
		    Map<Character, Character> map = new HashMap<Character, Character>();
		    for (int i = 0; i < len; i++) {		    	
		        //map.put(abcABC.charAt(i), abcABC.charAt((i + n + len) % len));
		    	map.put((Character)listasCharacterPool.get(i), (Character)listasCharacterPool.get((i + n + len) % len));		    			    	
		    }
		    */
			
			//Was ich aber brauche ist eine Alternative hierzu, bei dem jedes Zeichen mit dem ASCII / UTF - Wert ersetzt wird
			//int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyword, listasCharacterPool);
			System.out.println("Schluesselwort zu ia");
			
			
			//die Map nutzen zum Verschieben der Position
			/*
		    StringBuilder sb = new StringBuilder();
		    for(int i = 0; i < sInput.length(); i++) {
		    	char cEncoded = sInput.charAt(i);
		        Character ch = map.get(cEncoded);
		        if (ch == null) {
		            throw new IllegalArgumentException("Illegal character '" + sInput.charAt(i) + "'");
		        }
		        sb.append(ch);
		    }
		    sReturn =  sb.toString();
		    */
		}//end main;
		return sReturn;
    }
	
	
	public static String decrypt(String sInput, String sCharacterPoolIn, String sKeyword, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric) throws IllegalArgumentException, ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolStarting;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPoolStarting=CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
			}else {
				sCharacterPoolStarting = sCharacterPoolIn;
			}
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolStarting, bUseUppercase, bUseLowercase, bUseNumeric);
			String sCharacterPool = StringZZZ.reverse(abcABC);	
			sReturn = VigenereNnZZZ.encrypt(sInput, sCharacterPool, sKeyword);
		}
		return sReturn;						
	}

	@Override
	public boolean encryptUI() throws ExceptionZZZ {
		  boolean bReturn = false;
		  main:{
		    String SchluesselWort=this.getCryptKey();
		    
		    System.out.println("Schluesselwort: " + SchluesselWort);

		    String sFilePath = this.getFilePath();
		    DateiUtil Original = new DateiUtil(sFilePath);
		    this.setFileOriginal(Original);
		   
		    int[] p = Original.liesUnicode();//FGL: Der Klartextbuchstabe
		    System.out.print("\nOriginaltext ausgeben? (J/N): ");
		    if (IoUtil.JaNein()) {
		      System.out.println("---- Originaltext von: "+Original.computeFilePath()+" ----");
		      for (int i=0; i < p.length; i++) {
		        IoUtil.printChar(p[i]);	// druckbares Zeichen?
		        if (((i+1)%80)==0) System.out.println();
		      }
		    }		
		    System.out.println("\n-- Verschluessele Text von: "+Original.computeFilePath()+" --");    
		    

		int[]ppure = this.encrypt(p);
	    this.setEncryptedValues(ppure);
	    bReturn = true;
	}//end main:
	return bReturn;    
	}

	@Override
	public boolean decryptUI() throws ExceptionZZZ {
		 boolean bReturn = false;
		  main:{
//			    System.out.print("\nChiffrierte Datei ausgeben? (J/N): ");
//			    if (IoUtil.JaNein()) {
//			      System.out.println("---- Chiffretext von: "+Chiffre.computeFilePath()+" ----");
//			      for (int i=0; i < c.length; i++) {
//			        IoUtil.printCharWithPosition(c[i],i,"|");
//			        if (((i+1)%80)==0) System.out.println(); 	// neue Zeile
//			      }
//			    }
			   
			    System.out.println("\nDatei einlesen ...");
			    DateiUtil Chiffre = this.getFileEncrypted();
			    System.out.println("Datei: '" + Chiffre.computeFilePath() + "'" );;
			    int[] c = Chiffre.liesAsInt(); //FGL: Fehlerkorrektur... das ist ja nicht als Unicode in die Datei geschrieben worden...  Chiffre.liesUnicode();	// Datei einlesen
			    for(int i=0; i < c.length; i++) {
			    	int i2 = c[i];
			    	IoUtil.printChar(i2);
			    }
			    
			    String SchluesselWort=this.getCryptKey();		   
				System.out.println("Schluesselwort: '"+SchluesselWort+"'");
				  		    
			    System.out.println("\nBeginne Entschluesselung ... ");

			    int[]iaPure = this.decrypt(c);
			    this.setDecryptedValues(iaPure);		    
			   
			    bReturn = true;
		  }//end main:
		  return bReturn;
	}


	
	
	@Override
	public int getOffsetForAsciiRange() {
		return Vigenere256ZZZ.iOffsetForAsciiRange;
	}

	@Override
	public int[] fromUtf8ToAsciiForOffset(int[] p) {
		 //Analog zu Vigenere96.java, nur mit 32
		 //ImBuch, auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
		 //    for(int i=0; i < p.length; i++) {
		 //    	p[i]=p[i]-65;
		 //    }
		 return UnicodeZZZ.fromUtf8ToAsciiForNn(p);
	}

	@Override
	public int[] fromAsciiToUtf8ForOffset(int[] p) {
		//Analog zum buch, nur mit +32
	    //Im Buch auf Seite 36 wird dann 32 draufgerechnet ("blank")
	    return UnicodeZZZ.fromAsciiToUtf8ForNn(p);//funktioniert bei Viginere26 Verschluesselung, es wird 65 draufgerechnet
	}
	
	@Override
	public int getOffsetForUtf8Range() {
		return VigenereNnZZZ.iOffsetForUtf8Range;
	}
	
	@Override
	public void setCharacterPool(String sCharacterPool) {
		this.sCharacterUsedForRot = sCharacterPool;
	}
	
	public String getCharacterPool() {
		if(StringZZZ.isEmpty(this.sCharacterUsedForRot)) {
			this.sCharacterUsedForRot =  ROTnnZZZ.getCharacterPoolDefault();
		}
		return this.sCharacterUsedForRot;		
	}
	
	public static String getCharacterPoolDefault() {
		return CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
	}
	
	
}
