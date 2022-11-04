package basic.zBasic.util.crypt;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
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
public class ROTasciiZZZ extends AbstractROTZZZ{	
	protected String sCharacterUsedForRot = null; //protected, damit erbende Klassen auch nur auf diesen Wert zugreifen!!!
	
	ROTasciiZZZ() throws ExceptionZZZ { //Paktesichtbarkeit dieses Konstrktors auf PACKAGE geändert, damit die Factory einfach das Objekt erstellen kann. 
		super();
		String[] saFlagControl = {"init"};
		RotAsciiNew_(-1,saFlagControl);
	}
	
	ROTasciiZZZ(String sFlagControl)throws ExceptionZZZ{
		super(sFlagControl);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotAsciiNew_(-1,saFlagControl);
	}
	
	public ROTasciiZZZ(int iCryptKey) throws ExceptionZZZ {
		super(null);
		RotAsciiNew_(iCryptKey, null);
	}
	public ROTasciiZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
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
		
		String sCharacterPool = ROTasciiZZZ.getCharacterPoolStringAscii();
		this.setCharacterPool(sCharacterPool);
		
		
		
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
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, iCryptKey);
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPool=this.getCharacterPool();		
		return ROTasciiZZZ.decrypt(sInput, sCharacterPool, iCryptKey);
	}
	
	public String getCharacterPool() {
		if(StringZZZ.isEmpty(this.sCharacterUsedForRot)) {
			return ROTasciiZZZ.getCharacterPoolStringAscii();
		}else {
			return this.sCharacterUsedForRot;
		}
	}
	
	public void setCharacterPool(String sCharacterPool) {
		this.sCharacterUsedForRot = sCharacterPool;
	}
	
	public static String encrypt(String sInput, int n) {
		String sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, n);
	
	}
	
	
	public static String encrypt(String sInput, String sCharacterPoolIn, int n) {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPool= null;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
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
		            throw new IllegalArgumentException("Illegal character '" + sInput.charAt(i) + "'");
		        }
		        sb.append(ch);
		    }
		    sReturn =  sb.toString();
		}//end main;
		return sReturn;
    }
	
	public static String decrypt(String sInput, int n) {

		String sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
		sCharacterPool = StringZZZ.reverse(sCharacterPool);
		
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, n);		
	}
	
	public static String decrypt(String sInput, String sCharacterPoolIn, int n) {

		String sCharacterPool=null;
		if(StringZZZ.isEmpty(sCharacterPool)) {
			sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
		}else {
			sCharacterPool=sCharacterPoolIn;
		}
		sCharacterPool = StringZZZ.reverse(sCharacterPool);
		
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, n);		
	}
}
