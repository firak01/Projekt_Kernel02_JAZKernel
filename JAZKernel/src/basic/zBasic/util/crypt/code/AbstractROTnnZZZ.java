package basic.zBasic.util.crypt.code;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Als Liste von Zeichen wird der gesamte ASCII Raum verwendet.
 * Diese wird dann durch das ROT (also Zeichenverschiebung) verschlüsselt.
 * Merke: Das ist schwieriger zu entschlüsseln als der ROTnn Mechanismus, da der CharacterPool bekannt ist.
 *        Buch_KrypthografieMitJava\\Buch.KryptografieMitJava\\tryout\\basic\\zBasic\\util\\crypt\\CaesarDecode.java
 *        Kann so etwas entschlüsseln, ohne die Zeichenverschiebung zu kennen.
 * 
 * @author Fritz Lindhauer, 25.09.2022, 08:10:43
 * 
 * Anregung von:  
 * https://codereview.stackexchange.com/questions/7241/rot-n-algorithm-in-java
 * 
 */
public abstract class AbstractROTnnZZZ extends AbstractROTZZZ implements IROTnnZZZ{
	private static final long serialVersionUID = 1L;
		
	protected CharacterExtendedZZZ objCharacterMissingReplacement = null;
	protected ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = new ArrayListExtendedZZZ<CharacterExtendedZZZ>();
	
	protected String sCharacterPoolBase = null;
	protected String sCharacterPoolAdditional = null;
	
	protected int[] iaEncryptedPositionInPool = null;
	protected int[] iaDecryptedPositionInPool = null;
	
	
	AbstractROTnnZZZ() throws ExceptionZZZ { //Paktesichtbarkeit dieses Konstrktors auf PACKAGE geändert, damit die Factory einfach das Objekt erstellen kann. 
		super();
		String[] saFlagControl = {"init"};
		RotAsciiNew_(-1,saFlagControl);
	}
	
	AbstractROTnnZZZ(String sFlagControl)throws ExceptionZZZ{
		super(sFlagControl);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotAsciiNew_(-1,saFlagControl);
	}
	
	public AbstractROTnnZZZ(int iCryptKey) throws ExceptionZZZ {
		super(null);
		RotAsciiNew_(iCryptKey, null);
	}
	public AbstractROTnnZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotAsciiNew_(iCryptKey,saFlagControl);
	}

	private boolean RotAsciiNew_(int iCryptKey, String[] saFlagControlIn) throws ExceptionZZZ {
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
		this.setCryptNumber(iCryptKey);
		
		String sCharacterPool = AbstractROTnnZZZ.getCharacterPoolStringAscii();
		this.setCharacterPoolBase(sCharacterPool);
		
		
		
	}//end main:
		return bReturn;
	}
	
	public static String getCharacterPoolStringAscii() {
		String sReturn = "";
		
		for(int i = 0; i<=255; i++) {
			char c = (char)(i);
			sReturn = sReturn+c;
		}
		
		return sReturn;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPool=this.getCharacterPool();		
		CharacterExtendedZZZ objCharacterMissingReplacement = this.getCharacterMissingReplacment();
		return AbstractROTnnZZZ.encrypt(sInput, sCharacterPool, objCharacterMissingReplacement, iCryptKey);
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPool=this.getCharacterPool();
		CharacterExtendedZZZ objCharacterMissingReplacement = this.getCharacterMissingReplacment();
		return AbstractROTnnZZZ.decrypt(sInput, sCharacterPool, objCharacterMissingReplacement, iCryptKey);
	}
	
	@Override
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ {
		if(this.objCharacterMissingReplacement==null) {
			this.objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolUserZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
		}
		return this.objCharacterMissingReplacement;		
	}
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement) {
		this.objCharacterMissingReplacement = objCharacterMissingReplacement;
	}
	
	@Override
	public ArrayListExtendedZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ {
		if(ArrayListZZZ.isEmpty(this.listasCharacterPool)) {
			String sCharacterPoolBase = this.getCharacterPoolBase();
			String sCharacterPoolAdditional = this.getCharacterPoolAdditional();
			
			boolean bUseUppercasePool = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USEUPPERCASE);
			boolean bUseLowercasePool = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USELOWERCASE);
			boolean bUseNumericPool = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USENUMERIC);
			boolean bUseBlank = this.getFlag(IROTUserZZZ.FLAGZ.USEBLANK);
			boolean bUseAdditionalCharacter = this.getFlag(ICharacterPoolUserZZZ.FLAGZ.USEADDITIONALCHARACTER);
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolBase, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseBlank, bUseAdditionalCharacter,sCharacterPoolAdditional);
					
			ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);
			this.listasCharacterPool = listasCharacterPool;
		}
		return this.listasCharacterPool;
	}
	
	@Override
	public String getCharacterPool() throws ExceptionZZZ {
		ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();
		return listasCharacterPool.debugString("");
	}
	
	@Override
	public String getCharacterPoolBase() {
		return this.sCharacterPoolBase;
	}
	
	@Override
	public void setCharacterPoolBase(String sCharacterPoolBase) {
		this.sCharacterPoolBase = sCharacterPoolBase;
		
		//Nach Ändern des Werts die Liste leeren, so dass diese neu aufgebaut werden muss.
		this.listasCharacterPool.clear();
	}
	
	@Override
	public void setCharacterPoolAdditional(String sCharacterPoolAdditional) {
		this.sCharacterPoolAdditional = sCharacterPoolAdditional;
		
		//Nach Ändern des Werts die Liste leeren, so dass diese neu aufgebaut werden muss.
		this.listasCharacterPool.clear();
	}
	
	@Override
	public String getCharacterPoolAdditional() {
		return this.sCharacterPoolAdditional;
	}
	
	public static String getCharacterPoolBaseDefault() {
		return CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
	}

	public static String getCharacterPoolAdditionalDefault() {
		return CharacterExtendedZZZ.sCHARACTER_ADDITIONAL;
	}
		
	public static String encrypt(String sInput, int n)  throws IllegalArgumentException, ExceptionZZZ {
		String sCharacterPool= AbstractROTnnZZZ.getCharacterPoolStringAscii();
		CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolUserZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
		return AbstractROTnnZZZ.encrypt(sInput, sCharacterPool, objCharacterMissingReplacement, n);
	
	}
	
	
	public static String encrypt(String sInput, String sCharacterPoolIn, CharacterExtendedZZZ objCharacterMissingReplacement, int n) throws IllegalArgumentException {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPool= null;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPool= AbstractROTnnZZZ.getCharacterPoolStringAscii();
			}else {
				sCharacterPool = sCharacterPoolIn;
			}
			
			String abcABC = sCharacterPool;
			
			
			int len = abcABC.length();
			
			//MERKE: Wg. der Zuordnung zu einer Map muss sichergestellt sein, dass kein Zeichen im CharacterPool doppelt vorkommt.
			//+++++++++++ CharacterPool normieren			
			ArrayListExtendedZZZ<Character> listasCharacterPool = new ArrayListExtendedZZZ<Character>();
			for (int i = 0; i < len; i++) {
				try {
					listasCharacterPool.addUnique(abcABC.charAt(i));
				} catch (ExceptionZZZ e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }			
			//+++++++++++
			
			
			len = listasCharacterPool.size();

		    Map<Character, Character> map = new HashMap<Character, Character>();
		    for (int i = 0; i < len; i++) {
		        //map.put(abcABC.charAt(i), abcABC.charAt((i + n + len) % len));
		    	map.put((Character)listasCharacterPool.get(i), (Character)listasCharacterPool.get((i + n + len) % len));
		    }
	
		    StringBuilder sb = new StringBuilder();
		    for(int i = 0; i < sInput.length(); i++) {
		    	char cEncoded = sInput.charAt(i);
		        Character ch = map.get(cEncoded);
		        if (ch == null) {
		        	if(objCharacterMissingReplacement==null) {
		        		throw new IllegalArgumentException("Illegal character '" + sInput.charAt(i) + "'");
		        	}else {
		        		ch = objCharacterMissingReplacement.getChar();	
		        		if(ch == null) {
		        			throw new IllegalArgumentException("Illegal character '" + sInput.charAt(i) + "' and no replacement character provided.");
		        		}
		        	}
		        }
		        sb.append(ch);
		    }
		    sReturn =  sb.toString();
		}//end main;
		return sReturn;
    }
	
	public static String decrypt(String sInput, int n)  throws IllegalArgumentException, ExceptionZZZ{

		String sCharacterPool= AbstractROTnnZZZ.getCharacterPoolStringAscii();
		sCharacterPool = StringZZZ.reverse(sCharacterPool);
		CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolUserZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
		return AbstractROTnnZZZ.encrypt(sInput, sCharacterPool, objCharacterMissingReplacement, n);		
	}
	
	public static String decrypt(String sInput, String sCharacterPoolIn, CharacterExtendedZZZ objCharacterMissingReplacement, int n)  throws IllegalArgumentException{

		String sCharacterPool=null;
		if(StringZZZ.isEmpty(sCharacterPool)) {
			sCharacterPool= AbstractROTnnZZZ.getCharacterPoolStringAscii();
		}else {
			sCharacterPool=sCharacterPoolIn;
		}
		sCharacterPool = StringZZZ.reverse(sCharacterPool);
		
		return AbstractROTnnZZZ.encrypt(sInput, sCharacterPool, objCharacterMissingReplacement, n);		
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
	
	
	@Override
	public boolean getFlag(ICharacterPoolUserZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(ICharacterPoolUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(ICharacterPoolUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ICharacterPoolUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(ICharacterPoolUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	
	@Override
	public void setCryptKey(String sCryptKey) {
		//Nur wg. ICryptZZZ
	}			
}
