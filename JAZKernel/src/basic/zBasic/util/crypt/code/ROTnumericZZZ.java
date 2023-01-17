package basic.zBasic.util.crypt.code;

import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListExtendedZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;

/**
 * Returns a list with Strings which are rotated ROT-n. n = 26 - listIndex
 *
 * Source: http://www.rot-n.com/?page_id=4 
 *
 * @param input the string to mutate
 * @param numeric include numeric values
 * @return a list with mutated strings
 */
public class ROTnumericZZZ extends AbstractROTZZZ implements ICharacterPoolUserConstantZZZ{
	private static final long serialVersionUID = -7120488041281101978L;
	ROTnumericZZZ() throws ExceptionZZZ {//Konstruktor auf PAKETEBENE Sichtbar
		super();
		String[] saFlagControl = {"init"};
		RotNumericNew_(-1,saFlagControl);
	}
	ROTnumericZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super();
		RotNumericNew_(-1,saFlagControl);
	}
	public ROTnumericZZZ(int iCryptKey) throws ExceptionZZZ {
		this();
		RotNumericNew_(iCryptKey,null);
	}
	public ROTnumericZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		this();
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotNumericNew_(iCryptKey,saFlagControl);
	}
	private boolean RotNumericNew_(int iCryptKey, String[] saFlagControlIn) throws ExceptionZZZ {
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
		
	}//end main:
		return bReturn;
	}
	
	
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		boolean bNumeric = this.getFlag("useNumeric");
		return ROTnumericZZZ.encrypt(sInput, iCryptKey, bNumeric);
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		boolean bNumeric = this.getFlag("useNumeric");
		return ROTnumericZZZ.decrypt(sInput, iCryptKey, bNumeric);
	}
	
	
	//###########################################
	public static String encrypt(String input, int iCryptKey, boolean bNumeric) throws ExceptionZZZ{
		return ROTnumericZZZ.crypt(input, iCryptKey, bNumeric,false);
	}
	public static String decrypt(String input, int iCryptKey, boolean bNumeric) throws ExceptionZZZ{
		return ROTnumericZZZ.crypt(input, iCryptKey, bNumeric,true);
	}
	private static String crypt(String input, int iCryptKey, boolean bNumeric,boolean bReverse) throws ExceptionZZZ {
	   String sReturn = new String();
	   main:{
		   if(iCryptKey<0 || iCryptKey >=26) {
			   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range from 0 to 25", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			   throw ez;
		   }
		   
		   String result = "";
		   
		   int length; int iStart;
		   if(!bReverse) {
			 length = input.length();
			 iStart = 0;
		    
	        for (int i = iStart; i < length; i++) {
	            char ascii = input.charAt(i);
	            char rotated = ascii;
	            //Capital letters are 60 to 90
	            if (ascii > 64 && ascii < 91) {
	                rotated = (char) (rotated + iCryptKey);
	                if (rotated > 90) {
	                    rotated += -90 + 64;
	                }
	                if (rotated < 65) {
	                    rotated += -64 + 90;
	                }
	            } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
	                rotated = (char) (rotated + iCryptKey);
	                if (rotated > 122) {
	                    rotated += -122 + 96;
	                }
	                if (rotated < 97) {
	                    rotated += -96 + 122;
	                }
	            }
	            //Numeric values are between 48 to 57 
	            if (bNumeric && ascii > 47 && ascii < 58) {
	                rotated = (char) (rotated + iCryptKey);
	                if (rotated > 47) {
	                    rotated += -57 + 47;
	                }
	                if (rotated < 58) {
	                    rotated += -47 + 57;
	                }
	            }
	            result += (char) rotated;
	        }
		   }else {
			   //REVERSE, für das decrypten
			   length = input.length();
				 iStart = 0;
			    
		        for (int i = iStart; i < length; i++) {
		            char ascii = input.charAt(i);
		            char rotated = ascii;
		            //Capital letters are 60 to 90
		            if (ascii > 64 && ascii < 91) {
		                rotated = (char) (rotated - iCryptKey);
		                if (rotated > 90) {
		                    rotated += -90 + 64;
		                }
		                if (rotated < 65) {
		                    rotated += -64 + 90;
		                }
		            } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
		                rotated = (char) (rotated - iCryptKey);
		                if (rotated > 122) {
		                    rotated += -122 + 96;
		                }
		                if (rotated < 97) {
		                    rotated += -96 + 122;
		                }
		            }
		            //Numeric values are between 48 to 57 
		            if (bNumeric && ascii > 47 && ascii < 58) {
		                rotated = (char) (rotated - iCryptKey);
		                if (rotated > 47) {
		                    rotated += -57 + 47;
		                }
		                if (rotated < 58) {
		                    rotated += -47 + 57;
		                }
		            }
		            result += (char) rotated;
		        }
		   }
	        
	        sReturn = result;
	   }//end main:
	   return sReturn;
	}
	public static List<String> cryptAll(String input, boolean numeric) {
		    List<String> output = new ArrayList<>();
		    for (int n = 0; n < 26; n++) {
		        String result = "";
		        int length = input.length();

		        for (int i = 0; i < length; i++) {
		            char ascii = input.charAt(i);
		            char rotated = ascii;
		            //Capital letters are 60 to 90
		            if (ascii > 64 && ascii < 91) {
		                rotated = (char) (rotated + n);
		                if (rotated > 90) {
		                    rotated += -90 + 64;
		                }
		                if (rotated < 65) {
		                    rotated += -64 + 90;
		                }
		            } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
		                rotated = (char) (rotated + n);
		                if (rotated > 122) {
		                    rotated += -122 + 96;
		                }
		                if (rotated < 97) {
		                    rotated += -96 + 122;
		                }
		            }
		            //Numeric values are between 48 to 57 
		            if (numeric && ascii > 47 && ascii < 58) {
		                rotated = (char) (rotated + n);
		                if (rotated > 47) {
		                    rotated += -57 + 47;
		                }
		                if (rotated < 58) {
		                    rotated += -47 + 57;
		                }
		            }
		            result += (char) rotated;
		        }
		        output.add(result);
		    }
		    return output;
		}	
}
