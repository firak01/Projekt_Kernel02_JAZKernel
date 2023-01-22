package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;

public abstract class AbstractVigenereNnZZZ extends AbstractVigenereZZZ implements ICharacterPoolUserZZZ{	
	private static final long serialVersionUID = 1L;
	public static int iOffsetForAsciiRange=0;//wird dann später aus der Laenge des CharacterPools errechnet.
	public static int iOffsetForUtf8Range=0; //im CharacterPool sind keine nicht druckbaren Zeichen.
	
	protected String sCharacterUsedForRot = null; //protected, damit erbende Klassen auch nur auf diesen Wert zugreifen!!!
	protected ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = null;
	protected int[] iaEncryptedPositionInPool = null;
	protected int[] iaDecryptedPositionInPool = null;
	
	public AbstractVigenereNnZZZ() {
		super();		
	}
	public AbstractVigenereNnZZZ(String[]saFlagControl) throws ExceptionZZZ {
		super();		
		AbstractVigenereNnNew_("","",saFlagControl);
	}
	public AbstractVigenereNnZZZ(String sSchluesselWort) throws ExceptionZZZ {
		super();
		AbstractVigenereNnNew_(sSchluesselWort,"",null);
	}
	public AbstractVigenereNnZZZ(String sSchluesselWort, String sFilePath) throws ExceptionZZZ {
		super();
		AbstractVigenereNnNew_(sSchluesselWort,sFilePath,null);
	}
	
	private boolean AbstractVigenereNnNew_(String sSchluesselWort, String sFilePath, String[] saFlagControlIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		//try{	 		
			//setzen der übergebenen Flags	
		if(saFlagControlIn != null){
			 String stemp; boolean btemp; String sLog;
			for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				stemp = saFlagControlIn[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){
					 String sKey = stemp;
					 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
					 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					 
					// Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
					// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
					// throw ez;		 
				}
			}
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}						
		}
		
		this.setCryptKey(sSchluesselWort);
		this.setFilePath(sFilePath);
		bReturn = true;
	}//end main:
		return bReturn;
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
	public static String encrypt(String sInput, String sCharacterPoolIn, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, boolean bUseAdditionalCharacter, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPool= null;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPool= AbstractVigenereNnZZZ.getCharacterPoolDefault();
			}else {
				sCharacterPool = sCharacterPoolIn;
			}
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPool, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseAdditionalCharacter);			
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArrayCharacterPoolPosition(sKeyword, listasCharacterPool);
			
			int[] iaText = UnicodeZZZ.toIntArrayCharacterPoolPosition(sInput, listasCharacterPool);

			int[]iaPositionInPool = AbstractVigenereNnZZZ.encryptAsPositionInPool(iaText, listasCharacterPool, iaSchluesselwort);			
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
			          
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listAsCharacterPool = this.getCharacterPoolList();
			
            iaReturn = AbstractVigenereNnZZZ.encryptAsPositionInPool(iaText, listAsCharacterPool, iaSchluesselwort);
            this.setEncryptedCharacterPoolPosition(iaReturn);
            
            String sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaReturn, listasCharacterPool);
            iaReturn = UnicodeZZZ.toIntArray(sReturn);
			this.setEncryptedValues(iaReturn);		
		}//end main:
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

			int[]iaTextAsPositionInCharacterPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaText, listasCharacterPool);
			int[]iaSchluesselwortAsPositionInCharacterPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaSchluesselwort, listasCharacterPool);
			
			int laengeSW = iaSchluesselwortAsPositionInCharacterPool.length;			
			
			int[]ppure = new int[iaTextAsPositionInCharacterPool.length];
		    for (int i = 0; i < iaTextAsPositionInCharacterPool.length; i++) {		    	
		        //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	int iSum = iaTextAsPositionInCharacterPool[i]+iaSchluesselwortAsPositionInCharacterPool[iIndexS];
		    	int iFormula = (iSum)% listasCharacterPool.size();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128    	
		    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.            
		      ppure[i] = c;				// nur wegen abspeichern  
		      
		      //if(i>=1) System.out.print("|");
		      //System.out.print("i="+i+", c='"+c+"'"); 
		    }
		    //System.out.print("\n");
			
		    iaReturn = ppure;		   
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
	public static String decrypt(String sInput, String sCharacterPoolIn, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, boolean bUseAdditionalCharacter, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
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
				sCharacterPool= AbstractVigenereNnZZZ.getCharacterPoolDefault();
			}else {
				sCharacterPool = sCharacterPoolIn;
			}
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPool, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseAdditionalCharacter);	
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);					
			
			int[]ppure = AbstractVigenereNnZZZ.decrypt(sInput,listasCharacterPool,sKeyword);
			
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
			
			int[] iaPositionInPool = AbstractVigenereNnZZZ.decryptAsPositionInPool(iaEncryptedTextAsPositionInPool, listasCharacterPool, iaSchluesselwortAsPositionInPool);
		    
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

			//!Nun den iaEncryptedText in ein Array der Postionen umwandeln			
			int[] iaTextPositionInPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaEncryptedText, listasCharacterPool);
			int[] iaSchluesselwortPositionInPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaSchluesselwort, listasCharacterPool);

			int laengeSW = iaSchluesselwortPositionInPool.length;			
			
			int[]ppure = new int[iaTextPositionInPool.length];												
		    for (int i = 0; i < iaTextPositionInPool.length; i++) {		    	
		        //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	//int iSum = iaTextPositionInPool[iIndexS]-iaSchluesselwortPositionInPool[iIndexS];
		    	int iSum = iaTextPositionInPool[i]-iaSchluesselwortPositionInPool[iIndexS];
		    	int iFormula = (iSum)% listasCharacterPool.size();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128
		    	if(iFormula<0) {
		    		iFormula += listasCharacterPool.size();
		    	}
		    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
		    	
		      ppure[i] = c;				// nur wegen abspeichern  
		      //if(i>=1) System.out.print("|");
		      //System.out.print("i="+i+", c='"+c+"'"); 
		    }
		    //System.out.print("\n");
			
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
	public int getOffsetForUtf8Range() {
		return AbstractVigenereNnZZZ.iOffsetForUtf8Range;
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
	public boolean encryptUI() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean decryptUI() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCharacterPool(String sCharacterPool) {
		this.sCharacterUsedForRot = sCharacterPool;
	}
	
	
	@Override
	public ArrayListExtendedZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ {
		if(ArrayListZZZ.isEmpty(this.listasCharacterPool)) {
			String sCharacterPool = this.getCharacterPool();
			
			boolean bUseUppercasePool = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USEUPPERCASE);
			boolean bUseLowercasePool = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USELOWERCASE);
			boolean bUseNumericPool = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USENUMERIC);
			boolean bUseAdditionalCharacter = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USEADDITIONALCHARACTER);
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPool, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseAdditionalCharacter);
					
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);
			this.listasCharacterPool = listasCharacterPool;
		}
		return this.listasCharacterPool;
	}
	

	@Override
	public void setCharacterPoolList(ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool) {
		this.listasCharacterPool = listasCharacterPool;
		
		String sCharacterPool =this.listasCharacterPool.debugString("");
		this.setCharacterPool(sCharacterPool);
	}

	@Override
	public String getCharacterPool() {
		if(StringZZZ.isEmpty(this.sCharacterUsedForRot)) {
			this.sCharacterUsedForRot =  ROTnnZZZ.getCharacterPoolDefault();
		}
		return this.sCharacterUsedForRot;		
	}
	
	public static String getCharacterPoolDefault() {
		return CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
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
	
	public int[] readOriginalValuesAsCharacterPoolPosition() throws ExceptionZZZ {
		int[]iaReturn = null;
		main:{
			int[]iaPure = null;	
			if(this.getOriginalValues()==null) {
				DateiUtil Original = this.getFileOriginal();
						
				if(Original!=null) {
					iaPure = Original.liesUnicode();										
					this.setOriginalValues(iaPure);
				}
			}else {
				iaPure = this.getOriginalValues();
			}
			
			iaReturn = this.makeOriginalValuesAsCharacterPoolPosition(iaPure);
		}//end main:
		return iaReturn;
	}
	
	
	
	public int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure) throws ExceptionZZZ {
		int[] iaReturn = null;
		main:{
			
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();
			iaReturn = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaPure, listasCharacterPool);
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
	public boolean getFlag(ICharacterPoolUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public void setFlag(ICharacterPoolUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) {
		this.setFlag(objEnumFlag.name(), bFlagValue);
	}

}
