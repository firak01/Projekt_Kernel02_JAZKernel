package basic.zBasic.util.datatype.character;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**
 * Merke: Character ist final, kann also nicht selbst "extended" werden 
 * 
 * @author Fritz Lindhauer, 23.12.2022, 10:17:15
 * 
 */
public class CharacterExtendedZZZ extends ObjectZZZ implements ICharacterExtendedZZZ, Comparable<ICharacterExtendedZZZ>{
	private static final long serialVersionUID = 1L;
	private Character objChar=null; 
	
	//Kontruktor verstecken
	private CharacterExtendedZZZ() {
		super();
	}
	public CharacterExtendedZZZ(char c) throws ExceptionZZZ {
		Character objChar = new Character(c);
		CharacterExtendedNew_(objChar);
	}
	public CharacterExtendedZZZ(Character objChar) throws ExceptionZZZ {
		super();
		CharacterExtendedNew_(objChar);
	}
	
	private void CharacterExtendedNew_(Character objChar) throws ExceptionZZZ {
		if(objChar== null){
			ExceptionZZZ ez = new ExceptionZZZ("Character Object missing", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());								  
			throw ez;	
		  }
		
		this.setChar(objChar);
	}
	
	@Override
	public void setChar(Character objChar) {
		this.objChar=objChar;
	}
	
	@Override
	public char getChar() {
		return this.objChar.charValue();
	}
		
	public String toString() {
		Character objChar = this.getChar();
		return objChar.toString();
	}
	
	//##############################
	public static String computeCharacterPoolExtended(String sCharacterPoolIn, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric) {
		String sReturn="";
		main:{
			String sCharacterPoolStarting;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPoolStarting=CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
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
				sReturn = StringZZZ.appendMissing(sReturn, CharacterExtendedZZZ.sCHARACTER_NUMERIC);
			}
		}//end main:
		return sReturn;
	}
	
	public static ArrayListExtendedZZZ<CharacterExtendedZZZ> computeListFromCharacterPoolString(String sCharacterPool) throws ExceptionZZZ {
		ArrayListExtendedZZZ<CharacterExtendedZZZ> listReturn = null;
		main:{
					
			//IDEE DER NN-Behandlung:
			//Jeden Buchstaben in einen Integer-Wert ueberfuehren, der seine Position in dem Character-Pool hat.			
			//MERKE: Wg. der Zuordnung zu einer Map muss sichergestellt sein, dass kein Zeichen im CharacterPool doppelt vorkommt.
			//+++++++++++ CharacterPool normieren
			int len = sCharacterPool.length();			
			listReturn = new ArrayListExtendedZZZ<CharacterExtendedZZZ>();
			for (int i = 0; i < len; i++) {		
				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(sCharacterPool.charAt(i));
				listReturn.addUnique(objChar);				
		    }			
			//+++++++++++
			
		} //End main:
		return listReturn;		
	}
	
	@Override
	public int compareTo(ICharacterExtendedZZZ o) {
		//Das macht lediglich .sort funktionsfähig und wird nicht bei .equals(...) verwendet.
		int iReturn = 0;
		main:{
			if(o==null)break main;
			
			int itoCompare = o.getChar();
			int i = this.getChar();
			
			if(i==itoCompare) iReturn = 1;
		}
		return iReturn;
	}
	
	 /**
	   * Define equality of state.
	   */
	   @Override 
	   public boolean equals(Object aThat) {
	     if (this == aThat) return true;
	     if (!(aThat instanceof ICharacterExtendedZZZ)) return false;
	     ICharacterExtendedZZZ that = (ICharacterExtendedZZZ)aThat;
	    	     
	     if(that.getChar() == this.getChar()) {
	    	 return true;
	     }else {
	    	 return false;
	     }
	       
	   }

	   /**
	   * A class that overrides equals must also override hashCode.
	   */
	   @Override 
	   public int hashCode() {
		   char c = this.getChar();
		   Character objCharacter = new Character(c);
		   return objCharacter.hashCode();     
	   }
}
