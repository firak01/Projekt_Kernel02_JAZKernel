package basic.zBasic.util.crypt;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Definiere eine Liste von Zeichen.
 * Diese wird dann (auch als Großbuchstaben) durch das ROT (also Zeichenverschiebung) verschlüsselt.
 * 
 * Da dies nur etwas besser ist als nur den ASCII Raum gesammt zu verwenden,
 * erbt diese Klasse von ROTasciiZZZ.
 * Der grundsätzliche Mechanismus bleibt gleich.
 * 
 * @author Fritz Lindhauer, 25.09.2022, 08:10:43
 * 
 * Anregung von:  
 * https://codereview.stackexchange.com/questions/7241/rot-n-algorithm-in-java
 * 
 */
public class ROTnnZZZ extends ROTasciiZZZ{
	public static String sCHARACTER_POOL_DEFAULT=" abcdefghijklmnopqrstuvwxyz";
	
	ROTnnZZZ() throws ExceptionZZZ { //Paktesichtbarkeit dieses Konstrktors auf PACKAGE geändert, damit die Factory einfach das Objekt erstellen kann. 
		super();
		String[] saFlagControl = {"init"};
		RotNnNew_(null,-1,saFlagControl);
	}
	public ROTnnZZZ(int iCryptKey) throws ExceptionZZZ {
		super(null);
		RotNnNew_(null,iCryptKey,null);
	}
	public ROTnnZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super(null);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotNnNew_(null,iCryptKey,saFlagControl);
	}
	public ROTnnZZZ(String sCharacterPool, int iCryptKey) throws ExceptionZZZ {
		super(null);
		RotNnNew_(sCharacterPool,iCryptKey,null);
	}
	public ROTnnZZZ(String sCharacterPool, int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotNnNew_(sCharacterPool,iCryptKey,saFlagControl);
	}
	private boolean RotNnNew_(String sCharacterPool, int iCryptKey, String[] saFlagControlIn) throws ExceptionZZZ {
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
		this.setCharacterPool(sCharacterPool);
		
	}//end main:
		return bReturn;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPool=this.getCharacterPool();
		boolean bUseUpperCase = this.getFlag(ICryptZZZ.FLAGZ.USEUPPERCASE.name());
		boolean bUseLowerCase = this.getFlag(ICryptZZZ.FLAGZ.USELOWERCASE.name());
		boolean bUseNumeric = this.getFlag(ICryptZZZ.FLAGZ.USENUMERIC.name());
		return ROTnnZZZ.encrypt(sInput, sCharacterPool, iCryptKey,  bUseUpperCase, bUseLowerCase, bUseNumeric);
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPool=this.getCharacterPool();
		boolean bUseUpperCase = this.getFlag(ICryptZZZ.FLAGZ.USEUPPERCASE.name());
		boolean bUseLowerCase = this.getFlag(ICryptZZZ.FLAGZ.USELOWERCASE.name());
		boolean bUseNumeric = this.getFlag(ICryptZZZ.FLAGZ.USENUMERIC.name());
		return ROTnnZZZ.decrypt(sInput, sCharacterPool, iCryptKey, bUseUpperCase, bUseLowerCase, bUseNumeric);
	}
	
	public String getCharacterPool() {
		if(StringZZZ.isEmpty(this.sCharacterUsedForRot)) {
			this.sCharacterUsedForRot =  ROTnnZZZ.getCharacterPoolDefault();
		}
		return this.sCharacterUsedForRot;		
	}
	
	public static String getCharacterPoolDefault() {
		return ROTnnZZZ.sCHARACTER_POOL_DEFAULT;
	}
	
	public static String computeCharacterPoolExtended(String sCharacterPoolIn, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric) {
		String sReturn="";
		main:{
			String sCharacterPoolStarting;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPoolStarting=ROTnnZZZ.getCharacterPoolDefault();
			}else {
				sCharacterPoolStarting = sCharacterPoolIn;
			}
			
			sReturn=sCharacterPoolStarting;
			
			
			//Wichtig: Das Leerzeichen bei der Erweiterung der Liste raus. Spätestens beim Dekodieren faellt einem die andere Position (beim Umdrehen des Strings) auf die Fuesse.
			if(bUseUppercase) {
				String stemp = sCharacterPoolStarting.trim().toUpperCase();
				sReturn = StringZZZ.appendMissing(sCharacterPoolStarting, stemp);
			}
			
			if(bUseLowercase) {
				String stemp = sCharacterPoolStarting.trim().toLowerCase();
				sReturn = StringZZZ.appendMissing(sReturn, stemp);
			}
			
			if(bUseNumeric) {
				sReturn = StringZZZ.appendMissing(sReturn, "0123456789");
			}
		}//end main:
		return sReturn;
	}
	
	public static String encrypt(String sInput, String sCharacterPoolIn, int n, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric) {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolStarting;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPoolStarting=ROTnnZZZ.sCHARACTER_POOL_DEFAULT;
			}else {
				sCharacterPoolStarting = sCharacterPoolIn;
			}
			
			String abcABC = ROTnnZZZ.computeCharacterPoolExtended(sCharacterPoolStarting, bUseUppercase, bUseLowercase, bUseNumeric);
			sReturn = ROTasciiZZZ.encrypt(sInput, abcABC, n);		
		}//end main;
		return sReturn;
    }
	
	public static String decrypt(String sInput, String sCharacterPoolIn, int n, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric) {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolStarting;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPoolStarting=ROTnnZZZ.sCHARACTER_POOL_DEFAULT;
			}else {
				sCharacterPoolStarting = sCharacterPoolIn;
			}
			
			String abcABC = ROTnnZZZ.computeCharacterPoolExtended(sCharacterPoolStarting, bUseUppercase, bUseLowercase, bUseNumeric);
			String sCharacterPool = StringZZZ.reverse(abcABC);	
			sReturn = ROTasciiZZZ.encrypt(sInput, sCharacterPool, n);
		}
		return sReturn;						
	}
}