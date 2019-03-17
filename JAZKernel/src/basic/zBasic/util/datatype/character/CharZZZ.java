package basic.zBasic.util.datatype.character;

import basic.zBasic.util.datatype.string.StringZZZ;

public class CharZZZ {

	
	private CharZZZ(){
		//zum 'Verstecken" des Konstruktors
	}
	
	public static boolean isLowercase(char c){
		boolean bReturn = false;
		main:{
			String stemp = Character.toString(c);			
			bReturn = StringZZZ.isLowerized(stemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean isNumeric(char c){
		boolean bReturn = false;
		main:{
			String stemp = Character.toString(c);			
			bReturn = StringZZZ.isNumeric(stemp);
		}//end main:
		return bReturn;
	}
		
}
