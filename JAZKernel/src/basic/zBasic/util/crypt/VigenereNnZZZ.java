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
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.crypt.encode.IVigenereNnZZZ;
import basic.zBasic.util.crypt.encode.IVigenereZZZ;
import basic.zBasic.util.crypt.encode.Vigenere256ZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

public class VigenereNnZZZ extends AbstractVigenereZZZ implements IVigenereNnZZZ{
	private static final long serialVersionUID = -2833560399688739434L;	
	protected String sCharacterUsedForRot = null; //protected, damit erbende Klassen auch nur auf diesen Wert zugreifen!!!
	protected ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = null;
	protected int[] iaEncryptedPositionInPool = null;
	protected int[] iaDecryptedPositionInPool = null;
	
	
	public static int iOffsetForAsciiRange=0;//wird dann später aus der Laenge des CharacterPools errechnet.
	public static int iOffsetForUtf8Range=0; //im CharacterPool sind keine nicht druckbaren Zeichen.
	
	
	public VigenereNnZZZ() {
		super();
	}
	
	public VigenereNnZZZ(String sKeyString) throws ExceptionZZZ {
		super();
		this.setCryptKey(sKeyString);
	}
	
	
	public VigenereNnZZZ(String sKeyString, String sFilePath) throws ExceptionZZZ {
		super();
		this.setCryptKey(sKeyString);
		this.setFilePath(sFilePath);
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
			sReturn = VigenereNnZZZ.encrypt(sInput, sCharacterPoolIn, true,true,true, sKeyword);
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
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArrayCharacterPoolPosition(sKeyword, listasCharacterPool);
			
			int[] iaText = UnicodeZZZ.toIntArrayCharacterPoolPosition(sInput, listasCharacterPool);

			int[]iaPositionInPool = VigenereNnZZZ.encryptAsPositionInPool(iaText, listasCharacterPool, iaSchluesselwort);			
			sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPositionInPool, listasCharacterPool);
		    
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
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] encryptAsPositionInPool(String sInput, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			if(ArrayListZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyword);
			
			int[] iaText = UnicodeZZZ.toIntArray(sInput);
			
			int[] iaPositionInPool = VigenereNnZZZ.encryptAsPositionInPool(iaText, listasCharacterPool, iaSchluesselwort);
			iaReturn = iaPositionInPool;
		}//end main;
		return iaReturn;
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
	public static int[] encryptAsPositionInPool(int[] iaText, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, int[] iaSchluesselwort) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			if(ArrayUtilZZZ.isEmpty(iaText)) break main;
			if(ArrayUtilZZZ.isEmpty(iaSchluesselwort)) {
				iaReturn = iaText;
				break main;
			}
			
			if(ArrayListZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			int[]iaTextAsPositionInCharacterPool = VigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaText, listasCharacterPool);
			int[]iaSchluesselwortAsPositionInCharacterPool = VigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaSchluesselwort, listasCharacterPool);
			
			int laengeSW = iaSchluesselwortAsPositionInCharacterPool.length;			
			
			int[]ppure = new int[iaTextAsPositionInCharacterPool.length];
		    for (int i = 0; i < iaTextAsPositionInCharacterPool.length; i++) {
		    	if(i>=1) System.out.print("|");
		        //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	int iSum = iaSchluesselwortAsPositionInCharacterPool[iIndexS]+iaTextAsPositionInCharacterPool[i];
		    	int iFormula = (iSum)% listasCharacterPool.size();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128    	
		    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.            
		      ppure[i] = c;				// nur wegen abspeichern  
		      System.out.print("i="+i+", c='"+c+"'"); 
		    }
		    System.out.print("\n");
			
		    iaReturn = ppure;		   
		}//end main;
		return iaReturn;
    }
	
	
	
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sKeyword = this.getCryptKey();

			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();
			int[] iaPosition = VigenereNnZZZ.encryptAsPositionInPool(sInput,listasCharacterPool,sKeyword);
			this.setEncryptedCharacterPoolPosition(iaPosition);
			
			sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPosition, listasCharacterPool);
			int[] iaEncrypted = UnicodeZZZ.toIntArray(sReturn);			
			this.setEncryptedValues(iaEncrypted);
		}//end main:
		return sReturn;
	}
	
	/** Der reine Entschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 */
	public int[]encrypt(int[]iaText) throws ExceptionZZZ{		
		int[]iaReturn=null;
		main:{
			if(ArrayUtilZZZ.isEmpty(iaText))break main;
			
			String sKeyWord = this.getCryptKey();
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=iaText;
				break main;
			}
			
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listAsCharacterPool = this.getCharacterPoolList();
            int[] iaSchluesselwort = UnicodeZZZ.toIntArrayCharacterPoolPosition(sKeyWord,listAsCharacterPool);
			
            iaReturn = VigenereNnZZZ.encryptAsPositionInPool(iaText, listAsCharacterPool, iaSchluesselwort);
            this.setEncryptedCharacterPoolPosition(iaReturn);
            
            String sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaReturn, listasCharacterPool);
            iaReturn = UnicodeZZZ.toIntArray(sReturn);
			this.setEncryptedValues(iaReturn);		
		}//end main:
		return iaReturn;
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
	public static String decrypt(String sInput, String sCharacterPoolIn, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		//Merke: Bei Vigenere reicht das einfache Umdrehen des Codes wie bei Caesar oder anderen Rotierenden Verschluesselungen nicht mehr aus.
//		public static String decrypt(String sInput, String sCharacterPoolIn,boolean bUseUppercase,boolean bUseLowercase, boolean bUseNumeric, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
//			String sReturn = sInput;
//			main:{
//				if(StringZZZ.isEmpty(sInput)) break main;
//				
//				String sCharacterPoolStarting;
//				if(StringZZZ.isEmpty(sCharacterPoolIn)) {
//					sCharacterPoolStarting=CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
//				}else {
//					sCharacterPoolStarting = sCharacterPoolIn;
//				}
//				
//				String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolStarting, bUseUppercase, bUseLowercase, bUseNumeric);
//				String sCharacterPool = StringZZZ.reverse(abcABC);	
//				sReturn = VigenereNnZZZ.encrypt(sInput, sCharacterPool, sKeyword);
//			}
//			return sReturn;						
//		}
		
		
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
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);					
			
			int[]ppure = VigenereNnZZZ.decrypt(sInput,listasCharacterPool,sKeyword);
			//this.setDecryptedValues(ppure);
			
			sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(ppure, listasCharacterPool);		    			
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
	public static int[] decrypt(String sInput, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			if(StringZZZ.isEmpty(sInput)) break main;
			if(ArrayListZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			int[]iaEncryptedTextAsPositionInPool = UnicodeZZZ.toIntArrayCharacterPoolPosition(sInput, listasCharacterPool);
			if(StringZZZ.isEmpty(sKeyword)) {
				iaReturn = UnicodeZZZ.toIntArray(sInput);
				break main;
			}			
			int[] iaSchluesselwortAsPositionInPool = UnicodeZZZ.toIntArrayCharacterPoolPosition(sKeyword, listasCharacterPool);
			
			int[] iaPositionInPool = VigenereNnZZZ.decryptAsPositionInPool(iaEncryptedTextAsPositionInPool, listasCharacterPool, iaSchluesselwortAsPositionInPool);
		    
		    String sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPositionInPool, listasCharacterPool);
		    iaReturn = UnicodeZZZ.toIntArray(sReturn);
		}//end main;
		return iaReturn;
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
	public static int[] decryptAsPositionInPool(int[] iaEncryptedText, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, int[] iaSchluesselwort) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			if(ArrayUtilZZZ.isEmpty(iaEncryptedText)) break main;
			if(ArrayUtilZZZ.isEmpty(iaSchluesselwort)) {
				iaReturn = iaEncryptedText;
				break main;
			}
			
			if(ArrayListZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			
			//int[] iaTextPositionInPool = iaEncryptedText;
			int[] iaTextPositionInPool = VigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaEncryptedText, listasCharacterPool);
			
			//int[] iaSchluesselwortPositionInPool = iaSchluesselwort;
			int[] iaSchluesselwortPositionInPool = VigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaSchluesselwort, listasCharacterPool);
			
			
			int laengeSW = iaSchluesselwortPositionInPool.length;			
			
			//!Nun den iaEncryptedText in ein Array der Postionen umwandeln
			
			
			int[]ppure = new int[iaTextPositionInPool.length];												
		    for (int i = 0; i < iaTextPositionInPool.length; i++) {
		    	if(i>=1) System.out.print("|");
		        //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	int iSum = iaTextPositionInPool[iIndexS]-iaSchluesselwortPositionInPool[iIndexS];
		    	int iFormula = (iSum)% listasCharacterPool.size();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128
		    	if(iFormula<0) {
		    		iFormula += listasCharacterPool.size();
		    	}
		    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
		    	
		      ppure[i] = c;				// nur wegen abspeichern  
		      System.out.print("i="+i+", c='"+c+"'"); 
		    }
		    System.out.print("\n");
			
		    //TODOGOON20221231; //nun aus der Postion in der CharacterList wieder eine Char machen!!!
		    
		    iaReturn = ppure;		    
		}//end main;
		return iaReturn;
    }
	
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			int[] iaEncryptedText = UnicodeZZZ.toIntArray(sInput);
			this.setEncryptedValues(iaEncryptedText);
			
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();
			int[] iaEncryptedCharacterPoolPosition = UnicodeZZZ.toIntArrayCharacterPoolPosition(sInput, listasCharacterPool);
			this.setEncryptedCharacterPoolPosition(iaEncryptedCharacterPoolPosition);
			
			int[]iaDecryptedText = this.decrypt(iaEncryptedText);					
			sReturn = CharArrayZZZ.toString(iaDecryptedText);
		}//end main:
		return sReturn;
	}
	
	/** Der reine Verschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 * @throws ExceptionZZZ 
	 */
	public int[] decrypt(int[]iaEncryptedText) throws ExceptionZZZ {
		int[]iaReturn=null;
		main:{
			if(ArrayUtilZZZ.isEmpty(iaEncryptedText)) break main;
			
			String sKeyWord = this.getCryptKey();
			int[] iaSchluesselwort=null;
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=iaEncryptedText;
				break main;
			}else {
				iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
			}
			
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listCharacterPool = this.getCharacterPoolList();				
			int[]iaPosition = VigenereNnZZZ.decryptAsPositionInPool(iaEncryptedText, listCharacterPool, iaSchluesselwort);
			this.setDecryptedCharacterPoolPosition(iaPosition);
			
			String sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPosition, listasCharacterPool);
			iaReturn = UnicodeZZZ.toIntArray(sReturn);
			this.setDecryptedValues(iaReturn);
		}//end main:
		return iaReturn;   
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
		    

		this.encrypt(p);
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
				System.out.println("\nSchluesselwort: '"+SchluesselWort+"'");
				  		    
			    System.out.println("\nBeginne Entschluesselung ... ");

			    this.decrypt(c);
			    bReturn = true;
		  }//end main:
		  return bReturn;
	}


	
	
	@Override
	public int getOffsetForAsciiRange() {
		try {
			return this.getCharacterPoolList().size();//Vigenere256ZZZ.iOffsetForAsciiRange;
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} 
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
	
	public ArrayListExtendedZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ {
		if(ArrayListZZZ.isEmpty(this.listasCharacterPool)) {
			String sCharacterPool = this.getCharacterPool();
			
			boolean bUseUppercasePool = this.getFlag(IVigenereNnZZZ.FLAGZ.USEUPPERCASE);
			boolean bUseLowercasePool = this.getFlag(IVigenereNnZZZ.FLAGZ.USELOWERCASE);
			boolean bUseNumericPool = this.getFlag(IVigenereNnZZZ.FLAGZ.USENUMERIC);
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPool, bUseUppercasePool, bUseLowercasePool, bUseNumericPool);
					
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);
			this.listasCharacterPool = listasCharacterPool;
		}
		return this.listasCharacterPool;
	}
	
	public int[] readOriginalValuesAsCharacterPoolPosition() throws ExceptionZZZ {
		int[]iaReturn = null;
		main:{
			int[]iaPure = null;	
			if(this.getOriginalValues()==null) {
				DateiUtil Original = this.getFileOriginal();
						
				if(Original!=null) {
					iaPure = Original.liesUnicode();//FGL: Der Klartextbuchstabe
					
					//TODOGOON20221231; //Methode aus dem Buchstaben die Position in der CharacterPool-Liste zu ermitteln
					this.setOriginalValues(iaPure);
				}
			}else {
				iaPure = this.getOriginalValues();
			}
			
			iaReturn = this.makeOriginalValuesAsCharacterPoolPosition(iaPure);
		}//end main:
		return iaReturn;
	}
	
	@Override
	public int[] getEncryptedCharacterPoolPosition() {
		return this.iaEncryptedPositionInPool;
	}
	@Override	
	public void setEncryptedCharacterPoolPosition(int[]iaPosition) {
		this.iaEncryptedPositionInPool = iaPosition;
	}
	@Override
	public int[] getDecryptedCharacterPoolPosition() {
		return this.iaDecryptedPositionInPool;
	}
	@Override
	public void setDecryptedCharacterPoolPosition(int[]iaPosition) {
		this.iaDecryptedPositionInPool = iaPosition;
	}
	
	public int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure) throws ExceptionZZZ {
		int[] iaReturn = null;
		main:{
			
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();
			iaReturn = VigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaPure, listasCharacterPool);
		}//end main:
		return iaReturn;
	}
	
	public static int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool) throws ExceptionZZZ {
		int[] iaReturn = null;
		main:{
			iaReturn = new int[iaPure.length];			
			for(int i=0; i<iaPure.length;i++){
				int iPure = iaPure[i];
				char c = (char) iPure;
				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(c);				
				int iReturn = listasCharacterPool.getIndex(objChar);
				iaReturn[i] = iReturn;				
			}
		}//end main:
		return iaReturn;
	}
	
	@Override
	public void setCharacterPoolList(ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool) {
		this.listasCharacterPool = listasCharacterPool;
		
		String sCharacterPool =this.listasCharacterPool.debugString("");
		this.setCharacterPool(sCharacterPool);
	}
	
	public static String getCharacterPoolDefault() {
		return CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
	}

	public boolean getFlag(IVigenereNnZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	public void setFlag(IVigenereNnZZZ.FLAGZ objEnumFlag, boolean bFlagValue) {
		this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	
}
