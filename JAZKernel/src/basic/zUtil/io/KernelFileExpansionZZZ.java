package basic.zUtil.io;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.integer.IntegerZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.MathZZZ;
import custom.zUtil.io.FileZZZ;

public class KernelFileExpansionZZZ extends ObjectZZZ implements IFileExpansionZZZ {
	private FileZZZ objFileBase=null;
	
	private int iExpansionLength = -1; //Merke: Bei -1 wird der Defaultwert genommen, definiert als Konstante
	private char cExpansionFilling = CharZZZ.getEmpty();
	
	//flags 		
	public enum FLAGZ{
		EXPANSIONAPPEND,FILE_CURRENT_FOUND; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
	}
	
	public KernelFileExpansionZZZ() {
		super();
	}
	public KernelFileExpansionZZZ(FileZZZ objFile) {		
		super();
		this.setFileBase(objFile);
	}
	
	public KernelFileExpansionZZZ(FileZZZ objFile, int iExpansionLength) {
		this(objFile);
		this.setExpansionLength(iExpansionLength);		
	}
	
	
	public String computeExpansion(int iExpansionValue) {
		String sReturn = new String("");		
		main:{
			String sFilling = this.getExpansionFilling(); 
			int iExpansionLength = this.getExpansionLength();
			sReturn = this.computeExpansion(sFilling, iExpansionValue, iExpansionLength);
		}//end main
		return sReturn;
	}
	public String computeExpansion(String sFilling, int iExpansionValue) {
		String sReturn = new String("");		
		main:{
			int iExpansionLength = this.getExpansionLength();
			sReturn = this.computeExpansion(sFilling, iExpansionValue, iExpansionLength);
		}//end main
		return sReturn;
	}
	
	/** 
	 @param sFilling
	 @param iEndingValue
	 @param iEndingLength
	 @return String Expansion, e.g. '001'
	 */
	public String computeExpansion(String sFilling, int iExpansionValue, int iExpansionLength) {
		String sReturn = new String("");		
		main:{
			if(iExpansionValue <= 0) break main;				
			Integer intExpansionValue = new Integer(iExpansionValue);
			String sExpansionValue = intExpansionValue.toString();
			
			if(sExpansionValue.length() == iExpansionLength){
			//Den Eingangswert zurückgeben 
			sReturn = sExpansionValue;
			break main;
		} else if(iExpansionLength<=0){
			// !!! nix weiter zu tun, es soll keine Endung zur�ckgegeben werden
			sReturn = "";
			break main;
		}else{
			//Führende Füllwerte setzen
			int iEndingToFill = iExpansionLength - sExpansionValue.length();
			for(int iCounter = 0; iCounter < iEndingToFill; iCounter++){
				sExpansionValue = sFilling + sExpansionValue;				
			}
			sReturn = sExpansionValue;
		}				
		}//end main
		return sReturn;
	} //end function
	
		
	public String getExpansionCurrent() throws ExceptionZZZ{
		int iExpansionLenght = this.getExpansionLength();		
		return this.getExpansionCurrent(iExpansionLenght);
	}
	
	/**
	 @param iExpansionLength
	 @return String, last used expansion, e.g. 000 ---> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionCurrent(int iExpansionLength) throws ExceptionZZZ{
		String sReturn = new String("");								
		main:{
			FileZZZ objFileBase = this.getFileBase();
			
			//if the current file exists, then a expansion must be appended, except the expansion must be appended.
			if(this.getFlag("ExpansionAppend")==false) {
				if(objFileBase.exists()){
					this.setFlag("ExpansionAppend", true);	
				}else {
					this.setFlag("ExpansionAppend", false);
					sReturn = "";
					break main;
				}
			}
		
			//get file details
			String sPath = objFileBase.getPathDirectory();
			if(sPath.length() > 0){
				sPath = sPath + File.separator;
			}
			String sEnding = objFileBase.getNameEnd();
			if(sEnding.length() > 0){
				sEnding = "." + sEnding;
			}
			
			String sFilling = this.getExpansionFilling();
		
			//maximale Zahl errechen
			//double dTemp = Math.pow(10, iExpansionLength);
			//iCounter = (int)dTemp;
				
			boolean bFound = false;											
			Integer intCounter = new Integer(getExpansionMax(iExpansionLength));
			int iCounter = intCounter.intValue();
			
			//create new expansions and try their existance.
			String sExpansionFoundLast = new String("");		
			do{
				String sExpansion = computeExpansion(sFilling, iCounter, iExpansionLength);
				File f = new File(sPath + objFileBase.getNameOnly() + sExpansion + sEnding);
				if(f.exists() == true){
					bFound = true;
					sExpansionFoundLast = sExpansion;
					//Remark: Don't leave this loop, because there can be a gap
				}
				iCounter--;			
			}while(iCounter >= 0 && bFound == false);
			
			//das wird ausserhalb der Schleife gemacht, performance
			if(bFound==true){
				this.setFlag("ExpansionAppend", true);
				sReturn = sExpansionFoundLast; 
			}else {
								
				//Keiner gefunden, also ist das ein rein rechnerischer Wert.
				sReturn = computeExpansion(this.getExpansionFilling(),1, iExpansionLength);
			}
			
		}//end main:
		return sReturn;	
	} // end function
	
	public String getExpansionFirst() throws ExceptionZZZ{
		int iExpansionLenght = this.getExpansionLength();		
		return this.getExpansionFirst(iExpansionLenght);
	}
	
	/**
	 @param iExpansionLength
	 @return String, the first found expansion for the file (e.g. the filename itself when there are no files  or  000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionFirst(int iExpansionLength) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{	
			if(iExpansionLength <= 0) break main;	
				
			FileZZZ objFileBase = this.getFileBase();
			if(objFileBase.exists()) break main; //falls die Originaldatei existiert, so gilt diese als erste Datei in der Reihenfolge.		
															
			String sPath = objFileBase.getPathDirectory();
			if(sPath.length() > 0){
				sPath = sPath + "\\";
			}
			String sEnding = objFileBase.getNameEnd();
			if(sEnding.length() > 0){
				sEnding = "." + sEnding;
			}
			
			//Die h�chste Expansion ermitteln
			Integer intNrOfExpansion = 	new Integer(getExpansionMax(iExpansionLength));
			int iNrOfExpansion = intNrOfExpansion.intValue();
			for (int iCount = 0; iCount <= iNrOfExpansion; iCount++){
					String sExpansion = computeExpansion("0", iCount, iExpansionLength);
					File f = new File(sPath + objFileBase.getNameOnly() + sExpansion + sEnding);
					if(f.exists() == true){
						this.setFlag("ExpansionAppend",true);
						this.setFlag("File_Current_found", true);
						sReturn = sExpansion;
						break main;
					}else {
						this.setFlag("File_Current_found", false);
					}
			}
		
			
							
		}//end main:
		return sReturn;	
	}
	
	public String getExpansionNext() throws ExceptionZZZ{
		int iExpansionLenght = this.getExpansionLength();		
		return this.getExpansionNext(iExpansionLenght);
	}
	
	/**
	 @param iExpansionLength
	 @return String, the Expansion which has not been used by any other file, e.g. 000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionNext(int iExpansionLength) throws ExceptionZZZ{
		String sReturn = new String("");				
		main:{									
			String sExpansionCur = getExpansionCurrent(iExpansionLength);//Merke: Das dauert lange bei langen Dateiexpansionen, weil rückwärts alles gesucht wird.
			//System.out.println("Gefundene letzte Datei-Expansion: '" + sExpansionCur + "'");
			if(sExpansionCur.length() > 0 && this.getFlag("FILE_CURRENT_FOUND")){
				
				//Zahlenwerte von hinten einlesen, finden, .... . Füllzeichen, die keine Zahl sind werden ignoriert
				Integer intTemp = IntegerZZZ.parseAbsolutFromRight(sExpansionCur);
								
				//Integer intTemp = new Integer(sExpansionCur);				
				sReturn = computeExpansion(this.getExpansionFilling(),intTemp.intValue() + 1, iExpansionLength);				
			}else{
				if(this.getFlag("ExpansionAppend")) {
					sReturn =  computeExpansion(this.getExpansionFilling(), 1, iExpansionLength);
				}else {
					sReturn = "";//Das ist der Fall, wenn die Ausgangsdatei (also die Datei ohne Expansion) noch nicht vorhanden ist.				
				}
			}
		}//end main:		
		return sReturn;
	} // end function
	
	/** e.g. on an expansion Length of 3. The return value will be '999'
	* @return String
	* @param iExpansionLength
	* 
	* lindhaueradmin; 19.10.2006 09:37:46
	 */
	public static String getExpansionMax(int iExpansionLength){
		String sFunction = null;
		function:{
			int itemp = MathZZZ.pow(10, iExpansionLength);	
			sFunction = Integer.toString(itemp-1); 
		}//end function
		return sFunction;
	}
	
	
	
	
	//####### GETTER / SETTER
	@Override
	public FileZZZ getFileBase() {
		return this.objFileBase;
	}
	@Override
	public void setFileBase(FileZZZ objFile) {
		this.objFileBase = objFile;
	}
	
	public int getExpansionLength() {
		if(this.iExpansionLength<=-1) {
			return IFileExpansionConstZZZ.iEXPANSION_LENGTH_DEFAULT;
		}else {
			return this.iExpansionLength;
		}
	}
	public void setExpansionLength(int iExpansionLength) {
		this.iExpansionLength = iExpansionLength;
	}
	
	/** String, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is "0" ---> e.g. "0001"
	 * but it is possible to change this to e.g.  "-"  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:15:07
	 * @return String
	 */
	public String getExpansionFilling() {
		String sReturn = null;
		main:{
		if(CharZZZ.isEmpty(cExpansionFilling)) {
			sReturn = String.valueOf(IFileExpansionConstZZZ.cEXPANSION_FILLING_DEFAULT);
			break main;
		}
					
		sReturn = String.valueOf(cExpansionFilling);
		if(sReturn.equals("")){
			sReturn = String.valueOf(IFileExpansionConstZZZ.cEXPANSION_FILLING_DEFAULT);
		}
		}//end main:
		return sReturn;
	}
	
	/** void, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is '0' ---> e.g. "0001"
	 * but it is possible to change this to e.g.  '-'  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:20:14
	 * @param cExpansionFilling
	 */
	public void setExpansionFilling(char cExpansionFilling) {
		this.cExpansionFilling = cExpansionFilling;
	}
	public void setExpansionFilling(String sExpansionFilling)throws ExceptionZZZ{
		if(!StringZZZ.isEmpty(sExpansionFilling)) {
			if(sExpansionFilling.length()>=2) {
				ExceptionZZZ ez = new ExceptionZZZ("An expansion-filling should be one character", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			this.cExpansionFilling = sExpansionFilling.charAt(0);
		}else {
			this.cExpansionFilling = CharZZZ.getEmpty();
		}
	}		
	
	public static boolean isExpansionOfFilename(File fileToCheck, String sBasicFilename, int iExpansionLength) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(fileToCheck==null) break main;
			if(StringZZZ.isEmpty(sBasicFilename)) break main;
			if(iExpansionLength<=0)break main;
			if(sBasicFilename.length()<=iExpansionLength) break main;

			String sNameOnly = FileEasyZZZ.getNameOnly(fileToCheck); //Das lässt die Dateiendung weg.
			String sNameOnlyWithoutExpansion = StringZZZ.leftback(sNameOnly, iExpansionLength);
			
			String sEnding = FileEasyZZZ.getNameEnd(fileToCheck);
			String sNameWithoutExpansion = sNameOnlyWithoutExpansion + FileEasyZZZ.sFILE_ENDING_SEPARATOR + sEnding;
			if(sBasicFilename.equalsIgnoreCase(sNameWithoutExpansion)) bReturn = true;			
		}
		return bReturn;
	}
	
	public static boolean isExpansionOrSameFilename(File fileToCheck, String sBasicFilename, int iExpansionLength) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(fileToCheck==null) break main;
			if(StringZZZ.isEmpty(sBasicFilename)) break main;
			if(iExpansionLength<=0)break main;
			if(sBasicFilename.length()<=iExpansionLength) break main;
			
			String sNameToCheck = fileToCheck.getName(); //Das würde die Dateiendung weglassen. FileEasyZZZ.getNameOnly(fileToCheck);			
			if(sNameToCheck.equalsIgnoreCase(sBasicFilename)) {
				bReturn = true;
				break main;				
			}
			
			bReturn = KernelFileExpansionZZZ.isExpansionOfFilename(fileToCheck, sBasicFilename, iExpansionLength);			
		}
		return bReturn;
	}
}
