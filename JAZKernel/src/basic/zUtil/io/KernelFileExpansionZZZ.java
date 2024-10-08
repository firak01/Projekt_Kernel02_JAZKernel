package basic.zUtil.io;

import java.io.File;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.integer.IntegerZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.math.MathZZZ;
import custom.zUtil.io.FileZZZ;

public class KernelFileExpansionZZZ<T> extends AbstractObjectWithFlagZZZ implements IFileExpansionZZZ, Iterable<T> {
	private FileZZZ objFileBase=null;
	private int iExpansionUsedCurrent=0;
	
	private int iExpansionLength = -1; //Merke: Bei -1 wird der Defaultwert genommen, definiert als Konstante
	private char cExpansionFilling = CharZZZ.getEmpty();
	
	public KernelFileExpansionZZZ() throws ExceptionZZZ {
		super("init");
	}
	public KernelFileExpansionZZZ(char cExpansionFilling, int iExpansionLength) {		
		super();
		this.setExpansionLength(iExpansionLength);
		this.setExpansionFilling(cExpansionFilling);
	}	
	public KernelFileExpansionZZZ(FileZZZ objFileBase) {		
		super();
		this.setFileBase(objFileBase);
	}
	
	public KernelFileExpansionZZZ(FileZZZ objFileBase, int iExpansionLength) {
		this(objFileBase);
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
	
		
	public String searchExpansionCurrent() throws ExceptionZZZ{
		int iExpansionLenght = this.getExpansionLength();		
		return this.searchExpansionCurrent(iExpansionLenght);
	}
	
	/**
	 @param iExpansionLength
	 @return String, last used expansion, e.g. 000 ---> 999
	 * @throws ExceptionZZZ 
	 */
	public String searchExpansionCurrent(int iExpansionLength) throws ExceptionZZZ{
		String sReturn = new String("");								
		main:{
			FileZZZ objFileBase = this.getFileBase();
			if(objFileBase==null) break main;
			
			//if the current file exists, then a expansion must be appended.
			if(this.getFlag("FILE_Expansion_Append")==false) {
				if(objFileBase.exists()){
					this.setFlag("FILE_Expansion_Append", true);	
				}else {
					this.setFlag("FILE_Expansion_Append", false);
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
					this.setExpansionValueCurrent(iCounter);
					//Remark: Leave this loop, we don´t care about a gap.
					break;
				}
				iCounter--;			
			}while(iCounter >= 0 && bFound == false);
			
			//das wird ausserhalb der Schleife gemacht, performance
			if(bFound==true){
				this.setFlag("FILE_Expansion_Append", true);
				sReturn = sExpansionFoundLast; 
			}else {
				//Keiner gefunden, also ist das ein rein rechnerischer Wert, der von aussen ggfs. gesteuert werden kann um mit 1 anzufangen.
				if(this.getFlag("FILE_Expansion_Append") && !this.getFlag("File_Current_Found")){	
					this.setExpansionValueCurrent(1);
					sReturn = computeExpansion(this.getExpansionFilling(),this.getExpansionValueCurrent(), iExpansionLength);
				}else if(this.getFlag("FILE_Expansion_Append") && this.getFlag("File_Current_Found")){
					sReturn = computeExpansion(this.getExpansionFilling(),this.getExpansionValueCurrent(), iExpansionLength);
				}else {				
					this.setExpansionValueCurrent(0);
					sReturn = "";
				}
			}
			
		}//end main:
		return sReturn;	
	} // end function
	
	public String searchExpansionUsedLowest() throws ExceptionZZZ{
		int iExpansionLenght = this.getExpansionLength();		
		return this.searchExpansionUsedLowest(iExpansionLenght);
	}
	
	/**
	 @param iExpansionLength
	 @return String, the first found expansion for the file (e.g. the filename itself when there are no files  or  000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String searchExpansionUsedLowest(int iExpansionLength) throws ExceptionZZZ{
		String sReturn = null;;
		main:{	
			if(iExpansionLength <= 0) break main;	
				
			FileZZZ objFileBase = this.getFileBase();
			if(objFileBase==null) break main;
			if(objFileBase.exists()) {
				sReturn = new String("");
				break main; //falls die Originaldatei existiert, so gilt diese als erste Datei in der Reihenfolge.		
			}
															
			String sPath = objFileBase.getPathDirectory();
			if(sPath.length() > 0){
				sPath = sPath + "\\";
			}
			String sEnding = objFileBase.getNameEnd();
			if(sEnding.length() > 0){
				sEnding = "." + sEnding;
			}
			
			String sExpansionFilling = this.getExpansionFilling();
			
			//Die höchste Expansion ermitteln
			Integer intNrOfExpansionMax = 	new Integer(getExpansionMax(iExpansionLength));
			int iNrOfExpansionMax = intNrOfExpansionMax.intValue();
			for (int iCount = 0; iCount <= iNrOfExpansionMax; iCount++){
					String sExpansion = computeExpansion(sExpansionFilling, iCount, iExpansionLength);
					String sNameOnly = objFileBase.getNameOnly();
					File f = new File(sPath + sNameOnly + sExpansion + sEnding);
					if(f.exists() == true){
						this.setFlag("FILE_Expansion_Append",true);
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
	
	public String searchExpansionFreeLowest() throws ExceptionZZZ{
		int iExpansionLenght = this.getExpansionLength();		
		return this.searchExpansionFreeLowest(iExpansionLenght);
	}
	
	/**
	 @param iExpansionLength
	 @return String, the Expansion which has not been used by any other file, e.g. 000 --> 999
	 @throws ExceptionZZZ 
	 */
	public String searchExpansionFreeLowest(int iExpansionLength) throws ExceptionZZZ{
		String sReturn = null;				
		main:{									
			String sExpansionCur = searchExpansionUsedLowest(iExpansionLength);//Merke: Das dauert lange bei langen Dateiexpansionen, weil rückwärts alles gesucht wird.
			//System.out.println("Gefundene letzte Datei-Expansion: '" + sExpansionCur + "'");
			if(sExpansionCur.length() > 0 && this.getFlag("FILE_Expansion_Append")){
				FileZZZ objFileBase = this.getFileBase();
				if(objFileBase==null) break main;
				
				//get file details
				String sPath = objFileBase.getPathDirectory();
				if(sPath.length() > 0){
					sPath = sPath + File.separator;
				}
				String sEnding = objFileBase.getNameEnd();
				if(sEnding.length() > 0){
					sEnding = "." + sEnding;
				}
				
				Integer intNrOfExpansionMax = 	new Integer(getExpansionMax(iExpansionLength));
				int iNrOfExpansionMax = intNrOfExpansionMax.intValue();
				
				//Zahlenwerte von hinten einlesen, finden, .... . Füllzeichen, die keine Zahl sind werden ignoriert
				Integer intTemp = IntegerZZZ.parseAbsolutFromRight(sExpansionCur);
				int iCounter = intTemp.intValue();
								
				String sFilling = this.getExpansionFilling();
				String sExpansionFoundLast = new String("");
				boolean bFound = false;
				do{
					iCounter++;
					String sExpansion = computeExpansion(sFilling, iCounter, iExpansionLength);
					File f = new File(sPath + objFileBase.getNameOnly() + sExpansion + sEnding);
					if(f.exists() == true){
						bFound = true;						
						break;
					}		
				}while(iCounter <= iNrOfExpansionMax && bFound == false);
								
				//das wird ausserhalb der Schleife gemacht, performance
				if(bFound==true){
					this.setFlag("FILE_Expansion_Append", true);
					sReturn = computeExpansion(this.getExpansionFilling(),iCounter + 1, iExpansionLength);
				}else {								
					//Keiner gefunden, also ist das ein rein rechnerischer Wert.
					sReturn = computeExpansion(this.getExpansionFilling(),1, iExpansionLength);
				}				
			}else{
				if(this.getFlag("FILE_CURRENT_FOUND")) {
					sReturn =  computeExpansion(this.getExpansionFilling(), 1, iExpansionLength);
				}else {
					sReturn = "";//Das ist der Fall, wenn die Ausgangsdatei (also die Datei ohne Expansion) noch nicht vorhanden ist.				
				}
			}					
			
		}//end main:		
		return sReturn;
	} // end function
	
	public String searchExpansionFreeNext() throws ExceptionZZZ{
		int iExpansionLenght = this.getExpansionLength();		
		return this.searchExpansionFreeNext(iExpansionLenght);
	}
	
	/**
	 @param iExpansionLength
	 @return String, the Expansion which has not been used by any other file, e.g. 000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String searchExpansionFreeNext(int iExpansionLength) throws ExceptionZZZ{
		String sReturn = null;				
		main:{									
			String sExpansionCur = searchExpansionCurrent(iExpansionLength);//Merke: Das dauert lange bei langen Dateiexpansionen, weil rückwärts alles gesucht wird.
			//System.out.println("Gefundene letzte Datei-Expansion: '" + sExpansionCur + "'");
			if(sExpansionCur.length() > 0 && this.getFlag("FILE_Expansion_Append") && this.getFlag("FILE_CURRENT_FOUND")){
				
				//Zahlenwerte von hinten einlesen, finden, .... . Füllzeichen, die keine Zahl sind werden ignoriert
				Integer intTemp = IntegerZZZ.parseAbsolutFromRight(sExpansionCur);
								
				//Integer intTemp = new Integer(sExpansionCur);				
				sReturn = computeExpansion(this.getExpansionFilling(),intTemp.intValue() + 1, iExpansionLength);
				
				//HOCHZÄHLEN:
				this.setExpansionValueCurrent(intTemp.intValue() + 1);
			}else{
				if(this.getFlag("FILE_Expansion_Append") && !this.getFlag("FILE_CURRENT_FOUND")) {
					//Das ist der Fall, wenn die Ausgangsdatei (also die Datei ohne Expansion) noch nicht vorhanden ist.
					//aber von aussen "übersteuert" wird, so dass unbeding eine 1 angehängt werden soll.
					sReturn = sExpansionCur; //computeExpansion(this.getExpansionFilling(), 1, iExpansionLength);
				}else {			
					//Das ist der Fall, wenn die Ausgangsdatei (also die Datei ohne Expansion) noch nicht vorhanden ist.
					//und nicht von aussen "übersteuert" wird.
					sReturn = "";				
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
			String sNameWithoutExpansion = sNameOnlyWithoutExpansion + IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR + sEnding;
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
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 * 
	 * Merke: 
	 * Hier wird von der niedrigsten, VORHANDENEN Datei (ausgehend von 0) zur aktuellen vorhandenenen Datei in Richtung iExpansionUsedCurrent iteriert.
	 * 
	 */
	@Override
    public Iterator<T> iterator() {		
        Iterator<T> it = new Iterator<T>() {
        	private int iExpansionIteratedCurrent=-1; //Der Index des gerade verarbeiteten Keys im Iterator
        	private int iExpansionIteratedWatched=-1; //Der Index des gerade mit hasNext() betrachteten Keys im Iterator
        	private T objCachedFromHasNext=null;
        	
            /* Besonderheit:
             * Da es sich um Wete handelt, die in keiner Collection handelt, 
             * wird der über hasNext() ermittelte Wert "gecached", so dass er in "next()" sofort zur Verfügung steht 
             * und das Prozedere den Wert zu ermitteln nicht erneut gemacht werden muss.
             *  
             * (non-Javadoc)
             * @see java.util.Iterator#hasNext()           
             */
            @Override
            public boolean hasNext() {
            	boolean bReturn = false;
            	T objReturn = null;
            	main:{
            		objReturn = nextByContext_("hasNext");
            		
	            	this.objCachedFromHasNext = objReturn;
	            	if(objReturn!=null) bReturn = true;	            	
            	}//end main:
            	return bReturn;
            }

            /**Besonderheit:
             * Erst wird geprüft, ob über hasNext() ein Wert geholt wurde.
             * Falls ja, wird dieser "gecachte Wert" verwendet, 
             * falls nein, wird der Wert neu ermittelt. 
             * 
             * @return Beginnend von dem niedrigsten "gefundenen" Wert bis hin zum höchsten gefundenen Wert in den Dateien.
             * @author Fritz Lindhauer, 01.04.2020, 08:48:57
             */
            @SuppressWarnings("unchecked")
			@Override
            public T next() {
                T objReturn = null;
                main:{
                	//Hier gibt es keinen Vektor, etc. sondern immer nur einen "frisch" gesuchten/errechneten String.
                	if(this.iExpansionIteratedWatched>=0 && this.objCachedFromHasNext!=null) {
                		this.iExpansionIteratedCurrent = this.iExpansionIteratedWatched;
                		objReturn = this.objCachedFromHasNext;
                		
                		this.iExpansionIteratedWatched=-1;
                		this.objCachedFromHasNext = null;
                	} else {                		
                		//Wie kommt man an die Variante, dass, kein "hasNext" gemacht wird....
                		//Lösung: Gemeinsame private Methode aufrufen.
                		//        Nur mit einer anderen Zählvariablen als Input, oder besser: Contextangabe, um die andere Zählvariable auszuwählen.
                		objReturn = nextByContext_("next");
                	}                	
                }//end main:
            	return objReturn;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            
            //+++ ZKernel - Cache
            public T getHasNextCachedObject() {
        		return this.objCachedFromHasNext;
        	}
            public void setHasNextCachedObject(T objForCacheFromHasNext) {
            	this.objCachedFromHasNext = objForCacheFromHasNext;
            }
            
            //+++ Aufrufbar aus next()) und hasNext();
            private T nextByContext_(String sContextFlagIn) {
            	T objReturn = null;
            	main:{
            	try {
            		String sContextFlag;
            		if(StringZZZ.isEmpty(sContextFlagIn)) {
            			sContextFlag="";
            		}else {
            			sContextFlag=sContextFlagIn;
            		}
            		
            		//Hole den zu untersuchenden Wert Abfrage
            		//Merke: Unten in der Schleife gibt es ein icounter++. Diese Zeile ist alleinig für das "Vorankommen" des Zählers wichtig.
            		int iValue=-1;
            		if(sContextFlag.equalsIgnoreCase("hasNext")) {        			        			
	        			if(this.iExpansionIteratedWatched<=-1) {
	        				if(this.iExpansionIteratedCurrent<=-1) {;
	        				}else {
	        					iValue = this.iExpansionIteratedCurrent;
	        				}
	        			}else {
	        				iValue=this.iExpansionIteratedWatched;
	        			}
            		}else {
            			//z.B. "next"-Fall, wenn er ohne ein hasNext() durchgeführt wird.
            			//Merke: Mit einem hasNext() würde auf das "gecachte Objekt" zurückgegriffen.
            			iValue = this.iExpansionIteratedCurrent; 
            		}
        			
            		
            		if(iValue<=-1) {
            			T sExpansion = (T) searchExpansionUsedLowest();
            			int iCounter = 0;
            			if(!StringZZZ.isEmpty((String) sExpansion)) {
            				iCounter = StringZZZ.toInteger((String) sExpansion);                			                			
            			}
            			this.iExpansionIteratedWatched = iCounter;
            			this.iExpansionIteratedCurrent = iCounter;
            			objReturn = (T) sExpansion;                			
            		}else{
            			                			                		
            			boolean bFound = false;											
            			int iExpansionMax = StringZZZ.toInteger(getExpansionMax(iExpansionLength));
            			
            			//create new expansions and try their existence.
            			String sExpansionFoundLast = null;
            			String sExpansionFilling = getExpansionFilling();
            			
            			FileZZZ objFileBase = getFileBase();
            			if(objFileBase==null) break main;                					
            															
            			String sPath = objFileBase.getPathDirectory();
            			if(sPath.length() > 0){
            				sPath = sPath + "\\";
            			}
            			String sEnding = objFileBase.getNameEnd();
            			if(sEnding.length() > 0){
            				sEnding = IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR + sEnding;
            			}
            			
            			int iCounter = iValue;
            			do{
            				iCounter++;	//Merke: Die einzige Stelle, die den Zähler um 1 weiterbewegt.
            			
            				T sExpansion = (T) computeExpansion(sExpansionFilling, iCounter, iExpansionLength);
            				File f = new File(sPath + objFileBase.getNameOnly() + (String) sExpansion + sEnding);
            				if(f.exists() == true){
            					bFound = true;
            					this.iExpansionIteratedWatched = iCounter;
            					this.iExpansionIteratedCurrent = iCounter; 
            					sExpansionFoundLast = (String) sExpansion;
            					break;
            				}
            						
            			}while(iCounter <= iExpansionMax && bFound == false);
            			objReturn = (T) sExpansionFoundLast;
            		}            		
            	} catch (ExceptionZZZ ez) {						
					ez.printStackTrace();						
				}
            	}//end main:
            	return objReturn;
            }            
        };
        return it;
    }
	@Override
	public int getExpansionValueCurrent() {
		return this.iExpansionUsedCurrent;
	}
	@Override
	public void setExpansionValueCurrent(int iExpansionValue) {
		this.iExpansionUsedCurrent = iExpansionValue;
	}
	@Override
	public String computeExpansionValueCurrentString() {
		String sFilling = this.getExpansionFilling();
		int iExpansionValue = this.getExpansionValueCurrent();
		int iExpansionLEngth = this.getExpansionLength();
		return this.computeExpansion(sFilling, iExpansionValue, iExpansionLength);
	}
	@Override
	public String computeExpansionValueCurrentString(int iExpansionLength) {
		String sFilling = this.getExpansionFilling();
		int iExpansionValue = this.getExpansionValueCurrent();
		return this.computeExpansion(sFilling, iExpansionValue, iExpansionLength);
	}
	
	
	
}
