package basic.zBasic.util.datatype.character;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**
 * Merke: Character ist final, kann also nicht selbst "extended" werden 
 * 
 * @author Fritz Lindhauer, 23.12.2022, 10:17:15
 * 
 */
public class CharacterExtendedZZZ extends ObjectZZZ{
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
	
	public void setChar(Character objChar) {
		this.objChar=objChar;
	}
	
	public char getChar() {
		return this.objChar.charValue();
	}
	
	public String toString() {
		Character objChar = this.getChar();
		return objChar.toString();
	}
}
