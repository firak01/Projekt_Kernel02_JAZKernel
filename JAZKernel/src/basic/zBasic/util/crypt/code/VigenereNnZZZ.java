package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

public class VigenereNnZZZ extends AbstractVigenereNnZZZ implements IVigenereNnZZZ{
	private static final long serialVersionUID = -2833560399688739434L;		

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
		return VigenereNnZZZ.encrypt(sInput, sCharacterPoolIn, true,true,true, sKeyword);		
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
		return AbstractVigenereNnZZZ.encrypt(sInput, sCharacterPoolIn, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, sKeyword);
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
		return AbstractVigenereNnZZZ.encryptAsPositionInPool(sInput, listasCharacterPool, sKeyword);
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
		return AbstractVigenereNnZZZ.encryptAsPositionInPool(iaText, listasCharacterPool, iaSchluesselwort);
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
		return AbstractVigenereNnZZZ.decrypt(sInput, sCharacterPoolIn, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, sKeyword);
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
		return AbstractVigenereNnZZZ.decrypt(sInput, listasCharacterPool, sKeyword);
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
		return AbstractVigenereNnZZZ.decryptAsPositionInPool(iaEncryptedText, listasCharacterPool, iaSchluesselwort); 
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

	public static int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool) throws ExceptionZZZ {
		return AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaPure, listasCharacterPool);
	}

}
