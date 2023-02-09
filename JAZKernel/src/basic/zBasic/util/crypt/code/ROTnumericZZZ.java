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
		super();
		RotNumericNew_(iCryptKey,null);
	}
	public ROTnumericZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super();
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
		return ROTnumericZZZ.crypt_(input, iCryptKey, bNumeric,false);
	}
	public static String decrypt(String input, int iCryptKey, boolean bNumeric) throws ExceptionZZZ{
		return ROTnumericZZZ.crypt_(input, iCryptKey, bNumeric,true);
	}
	private static String crypt_(String input, int iCryptKey, boolean bNumeric,boolean bReverse) throws ExceptionZZZ {
	   String sReturn = new String();
	   main:{
		   if(iCryptKey<0 || iCryptKey >=26) {
			   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range from 0 to 25", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			   throw ez;
		   }
		   
		   if(!bNumeric) { 		   
			   sReturn = ROTnumericZZZ.cryptNumericSimple_(input, iCryptKey, bReverse);
		   }else {
			   sReturn = ROTnumericZZZ.cryptNumericAdvanced_(input, iCryptKey, bReverse);
		   }
	   }//end main:
	   return sReturn;
	}
	
	/** Leerzeichen wird zu Zahlen.
	 *  Zahlen werden ggfs. in den Grossbuchstabenbereich verschoben.
	 *  Grossbuchstaben werden ggfs. in den Kleinbuchstabenbereich verschoben.
	 *  Kleinbuchstaben werden ggfs. in den Zahlenbereich verschoben.
	 * @param input
	 * @param iCryptKey
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String cryptNumericAdvanced_(String input, int iCryptKey, boolean bReverse) throws ExceptionZZZ {
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
			            //int iRotated = rotated;
			            			           
			            if(ascii ==32) {  //Blank is 32
			            	rotated = (char) (rotated + iCryptKey);	
			            	int iRotated = rotated;
			            	if (iRotated > 32) {
			                    iRotated = iRotated + 15; //15 Zeichen ueberspringen, bis zu den Zahlen.
			                }
			            	
			            	 if(iRotated>57) {
			                   	iRotated = iRotated + 7;  //Kleinbuchstaben zu Grossbuschstaben machen, ausgehend vom Leerzeichen
			                 }
			            	 rotated=(char)iRotated;
			            	
			            } else if (ascii > 64 && ascii < 91) {  //Capital letters are 60 to 90
			                rotated = (char) (rotated + iCryptKey);			            	
			                if (rotated > 90) {
			                    rotated += 6; //6 Zeichen ueberspringen, bis zu den Kleinbuchstaben.
			                }
			                if (rotated < 65) {
			                    rotated += -64 + 90;
			                }
			            } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
			                rotated = (char) (rotated + iCryptKey);
			            	int iRotated = rotated;
			                if (rotated >= 123) {
			                	//int iBisZumEnde=255-123;//=132
			                	iRotated = (rotated+132) + 32;
			                	iRotated = iRotated - 255;
			                	
			                	//rotated += ((255-rotated) + 32 );//Kleinbuchstaben zu Leerzeichen machen
			                    //rotated -= 254;      //... und wieder in der ASCII Tabelle von vorne anfangen.
			                    
			                    //Wenn das nicht das Leerzeichen ist, noch weiterschieben.
			                    //iRotated = rotated;
			                    if(iRotated>32) {
			                    	iRotated = iRotated + 15; //Kleinbuchstaben zu Zahlen machen, ausgehend vom Leerzeichen.			                    					                   
			                    }
			                    
			                    if(iRotated>57) {
			                    	iRotated = iRotated + 7;  //Kleinbuchstaben zu Grossbuschstaben machen, ausgehend von den Zahlen
			                    }
			                    rotated=(char)iRotated;
			                    
			                }
//			                if (rotated < 97) {
//			                    rotated += -96 + 122;
//			                }
			            }
			            //Numeric values are between 48 to 57 
			            if (ascii > 47 && ascii < 58) {
			                rotated = (char) (rotated + iCryptKey);
			            	//int iRotated = rotated;			            	
//			            	if (rotated < 47) {
//			                    rotated += 10;
//			                }
			                if (rotated >= 58) {
			                    rotated += 7;//7 Zeichen ueberspringen bis in den Grossbuchstabenbereich.
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
				            		            
				           		            		            
				            if (ascii > 64 && ascii < 91) {	 //Capital letters are 60 to 90
				            	rotated = (char) (rotated - iCryptKey);
				            	int iRotated = rotated;
				            	if(iRotated<=64) {
				            		iRotated=iRotated-7; //7 Zeichen ueberspringen, bis in den Zahlenbereich
				            	}
				            	
				            	if(iRotated <=47) {
				            		iRotated=iRotated-15; //15 Zeichen ueberspringen, bis zum Leerzeichen				            		
				            	}
				            	
//				                if (rotated > 90-iCryptKey) {
//				                    rotated += -90 + 64;
//				                }
//				                if (rotated < 65-iCryptKey) {
//				                    rotated += -64 + 90;
//				                }
				            	rotated = (char)iRotated;
				            } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
				            	rotated = (char) (rotated - iCryptKey);
				            	int iRotated = rotated;
				            	if(iRotated<=96) {				            		
				            		rotated -=6; //6 Zeichen ueberspringen, bis zu den Grossbuchstaben
				            	}
				            	
//				            	else if (rotated > 122) {
//				                    rotated += -122 + 96;
//				                }else if (rotated < 97) {
//				                    rotated += -96 + 122;
//				                }
				            } else if (ascii==32) { //Das Leerzeichen
				            	rotated = (char) (rotated - iCryptKey);
				            	int iRotated = rotated;
				            	iRotated = iRotated - 31 - (254-122); //Leerzeichen zu Zahlen machen
				            	iRotated +=254;                       //... und wieder in der ASCII Tabelle von vorne anfangen.
				            	
				            	rotated = (char)iRotated;
				            	
				            } else if (ascii > 47 && ascii < 58) { //Numeric values are between 48 to 57 
				            	rotated = (char) (rotated - iCryptKey);
				            	int iRotated = rotated;
				            	if(iRotated<=47) {
				            		iRotated = iRotated - 15; //genau diese Zahl zu Leerzeichen machen					            	
					            	rotated = (char)iRotated;
					            	
					            	iRotated = rotated; 
					            	if(iRotated<32) { //weiterschieben zu Zahlen
				            		   iRotated = iRotated - 31 - (254-122); //Kleinbuchstaben zu Zahlen machen
					                   iRotated += 254;      //... und wieder in der ASCII Tabelle von vorne anfangen.
					                   rotated = (char)iRotated;
					            	}
				            	}
				            }
				            result += (char) rotated;
				        }
				   }
		        sReturn = result;			   
		   }//end main:
		   return sReturn;
	}
	
	/** Zahlen werden mit anderen Zahlen vertauscht, d.h. sie bleiben Zahlen.
	 *  Buchstaben bleiben Buchstaben, sogar Grossbuchstaben bleiben Grossbuchstaben.
	 * 
	 * @param input
	 * @param iCryptKey
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String cryptNumericSimple_(String input, int iCryptKey, boolean bReverse) throws ExceptionZZZ {
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
		            	//int iRotated = rotated;
		                if (rotated > 90) {
		                    rotated += -90 + 64;
		                }
		                if (rotated < 65) {
		                    rotated += -64 + 90;
		                }
		            } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
		                rotated = (char) (rotated + iCryptKey);
		            	//int iRotated = rotated;
		                if (rotated > 122) {
		                    rotated += -122 + 96;
		                }
		                if (rotated < 97) {
		                    rotated += -96 + 122;
		                }
		            }
		            //Numeric values are between 48 to 57 
		            if (ascii > 47 && ascii < 58) {
		                rotated = (char) (rotated + iCryptKey);
		            	//int iRotated = rotated;
		            	
		            	if (rotated < 47) {
		                    rotated += 10;
		                }
		                if (rotated > 58) {
		                    rotated += -10;
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
			            	int iRotated = rotated;
			            	if(iRotated<=64) {
			            		iRotated = iRotated+26;
			            		rotated = (char) iRotated;
			            	}
			            	
			                if (rotated > 90-iCryptKey) {
			                    rotated += -90 + 64;
			                }
			                if (rotated < 65-iCryptKey) {
			                    rotated += -64 + 90;
			                }
			            } else if (ascii > 96 && ascii < 123) { //Lowercase letters are between 97 and 122
			            	rotated = (char) (rotated - iCryptKey);
			            	int iRotated = rotated;
			            	if(iRotated<=96) {
			            		iRotated = iRotated+26;
			            		rotated = (char) iRotated;
			            	}else if (rotated > 122) {
			                    rotated += -122 + 96;
			                }else if (rotated < 97) {
			                    rotated += -96 + 122;
			                }
			            }
			            //Numeric values are between 48 to 57 
			            if (ascii > 47 && ascii < 58) {
			            	rotated = (char) (rotated - iCryptKey);
			            	int iRotated = rotated;
			            	if(iRotated<=47) {
			            		iRotated = iRotated+10;
			            		rotated = (char) iRotated;
			            	}
			            }
			            result += (char) rotated;
			        }
			   }
		        sReturn = result;			   
		   }//end main:
		   return sReturn;
	}
}
