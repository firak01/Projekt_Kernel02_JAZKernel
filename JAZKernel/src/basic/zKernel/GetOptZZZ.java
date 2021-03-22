package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.start.GetOpt;

/**Ein Wrapper um GetOpt. 
 * 
 * @author lindhauer
 *
 */
public class GetOptZZZ extends ObjectZZZ{
	private String sPattern; 
	private HashMap hmOpt = new HashMap();
	private Iterator itOpt = null;
	private boolean bFlagIsLoaded = false;
	
	
	public GetOptZZZ(){
	}
	
	/** Konstruktor, der sofort alles initiiert
	* lindhauer; 18.07.2007 06:47:43
	 * @param objKernel, kann ein mit new KernelZZZ() initiierter default - Kernel sein (vorausgesetzt die entsprechende Default ini-Datei existiert f�r den default kernel)
	 * @param sPattern, die Steuerzeichen. Ein Buchstabe, ein Doppelpunkt bedeutet, dass ein Argument folgt. z.B. "k:s:abc". Die Argumente tauchen dann im Argument Array auf
	 * @param saArg, Array der Argumente. Hier werden die Steuerzeichen mit Bindestrich eingeleitet. z.B. -k wert1 -s wert2. Es m�ssen nicht alle im Pattern definierten Steuerzeichen vorhanden sein.
	 * @throws ExceptionZZZ
	 */
	public GetOptZZZ( String sPattern, String[] saArg) throws ExceptionZZZ{
		this.setPattern(sPattern);
		this.loadOptionAll(saArg);
	}
	
	public String getPattern(){
		return this.sPattern;
	}
	/**Setzt die Kommandozeile in dem Format, wie es auch von GetOpt erwartet wird.
	 *  z.B. -a: erster_wert -b -c: zweiter_wert
	            Dabei ist -b ein Parameter ohne Wert (er ist einfach nur da), -a und -c haben einen Wert zugewiesen bekommen.
	            Die Parameter d�rfen nur die L�nge von 1 haben.
	             
	* @param sPattern
	* 
	* lindhauer; 20.06.2007 06:19:59
	 * @throws ExceptionZZZ 
	 */
	public void setPattern(String sPattern) throws ExceptionZZZ{
		if(this.sPattern!=null){
			if(! this.sPattern.equals(sPattern)){ //NUr falls ein anderer neuer Wert
				if(this.isPatternStringValid(sPattern)){
					this.sPattern = sPattern;	
					this.clearOptions(); //Bisherige HAshmap l�schen und Iteartor wieder zur�cksetzen
				}else{
					String sResult = this.proofPatternString(sPattern);
					ExceptionZZZ ez = new ExceptionZZZ("Not a valid pattern string." + sResult, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
			}else{
			  //Nix, das gleicher Wert
			}
		}else{
			//Neuen Wert setzen
			if(this.isPatternStringValid(sPattern)){
				this.sPattern = sPattern;								
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("Not a valid pattern string: " + sPattern, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}
		}
	}

	/**Die Interne HashMap zur�cksetzen
	* lindhauer; 28.06.2007 06:59:29
	 */
	public void clearOptions(){		
		this.getOptionMap().clear(); //Die Hashmap zur�cksetzen
		this.setOptionIterator(null);
		this.bFlagIsLoaded = false;	
	}
	public HashMap getOptionMap(){
		return this.hmOpt;
	}
	
	public static ArrayList Pattern2ListControlAll(String sPattern){
		ArrayList listaReturn = new ArrayList();
		main:{
			if(StringZZZ.isEmpty(sPattern)) break main;
			
			  for(int icount=0; icount<=sPattern.length()-1; icount++){
	            	String stemp = sPattern.substring(icount, icount + 1);
	            	if(!stemp.equals(":")){
	            		listaReturn.add(stemp);
	            	}
	            }
		}
		return listaReturn;
	}
	
	public static ArrayList Pattern2ListControlValue(String sPattern){
		ArrayList listaReturn = new ArrayList();
		main:{
			if(StringZZZ.isEmpty(sPattern)) break main;
			
//			1b Pattern String auf Doppelpunkte untersuchen. 
			//Merke: Irgendwelche Indexbetrachtungen k�nnen nicht funktionieren. Die Reihenfolge der Argumente ist n�mlich beliebig. 
			//Es muss vielmehr das Steuerungszeichen davor ermittelt werden. Damit kann dann das Argumentenarray untersucht werden: Folgt dem Steuerungszeichen immer ein anderer Wert
			String[] saDelim = {":"};
			Integer[] intaIndex = StringZZZ.allIndexOf(sPattern, saDelim);
			
			//Nun muss das Zeichen jeweils 1 Zeichen vor dem Indexwert geholt werden, 
			//dann hat man die STEUERZEICHEN, die ein ARGUMENT ERWARTEN	
			  if(intaIndex!=null){            	
	            	for(int icount=0; icount <= intaIndex.length-1; icount++){            		
	            		Integer inttemp = intaIndex[icount];
	            		if(inttemp.intValue()>=0){
	            			int itemp = inttemp.intValue()-1;  //Position des Zeichens vor dem ":" 
	            			String stemp = sPattern.substring(itemp, itemp + 1);
	            			if(!StringZZZ.isEmpty(stemp)){
	            				listaReturn.add(stemp);
	            			}
	            		}
	            	}
	           }
		}
		return listaReturn;
	}
	
	/** Füllt die übergebenen Argumente in eine HashMap.
	 *   Dazu wird erst ein GetOpt-Object verwendet und dann f�r Optionen des "Pattern" abgefragt.
	 *   
	* @param saArg
	* 
	* lindhauer; 28.06.2007 07:03:54
	 * @throws ExceptionZZZ 
	 */
	public boolean loadOptionAll(String[] saArg) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(saArg==null) break main;
			if(saArg.length==0) break main;
			
			//Prüfen, ob einer der Argumentstrings gefüllt ist.
			boolean bfound = false;
			for(int icount = 0; icount <= saArg.length - 1; icount++){
				if(! StringZZZ.isEmpty(saArg[icount])){
					bfound = true;
					break;
				}
			}
			if(bfound==false) break main; //ohne ein Argument lohnt sich das alles nicht
			
			
			String sPattern = this.getPattern();
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Pattern missing allthough Argument-Array not empty.", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			boolean btemp = this.isArgumentValid(saArg);
			if (btemp == false){
				String sError = this.proofArgument(saArg);
				ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			GetOpt objOption = new GetOpt(sPattern);
			char a = objOption.getopt(saArg);
			String sOption = StringZZZ.char2String(a).trim();			
			while(!StringZZZ.isEmpty(sOption)){
				String sParam = objOption.optarg();
				
				this.getOptionMap().put(sOption, sParam);
				
				//Zum naechsten Argument
				a = objOption.getopt(saArg);
				sOption = StringZZZ.char2String(a).trim();
			}
		    bReturn = true;
		}//end main
		 this.setFlag("isloaded",bReturn);
		return bReturn;
	}
	public String readOptionFirst(){
		String sReturn = null;
		main:{
			HashMap hm = this.getOptionMap();
			Iterator it = hm.keySet().iterator();
			this.setOptionIterator(it);
			if(it.hasNext()){
				sReturn =(String) it.next();
			}
		}//ENd main
		return sReturn;
	}
	
	public String readOptionNext(){
		String sReturn = null;
		main:{
			Iterator it = null;
			if(this.getOptionIterator()==null){
				HashMap hm = this.getOptionMap();
				it = hm.keySet().iterator();
				this.setOptionIterator(it);
			}else{
				it = this.getOptionIterator();
			}
			
			if(it != null){
				if(it.hasNext()){
					sReturn =(String) it.next();
				}
			}
			
		}//ENd main
		return sReturn;
	}
	public String readValue(String sOption){
		String sReturn = null;
		main:{
			HashMap hm = this.getOptionMap();
			if(hm==null) break main;
			if(hm.isEmpty()) break main;
			if(!hm.containsKey(sOption)) break main;
			
			sReturn = (String) hm.get(sOption);
		}
		return sReturn;
	}
	public String proofArgument(String sArgument) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			//#### Parameter f�r die Funktion pr�fen
//			Null oder Leerstring sind valid
			if(StringZZZ.isEmpty(sArgument)) break main;
				
			//Falls das Pattern leer ist, sArgument ist aber gefuellt, dann FEHLER:
			String sPattern = this.getPattern();
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//TODO: DAS PATTERN SELBST VALIDIEREN
			
			
			//#### Die Pruefung des Argument strings
//			1a Alle nach dem Leerzeichen zerlegen. Parameter pruefen.
			String[] saParamAll = StringZZZ.explode(sArgument, " ");
			if(saParamAll==null|saParamAll.length==0) break main;
			
			sReturn = this.proofArgument_(sPattern, saParamAll);
			
		}//end main
		return sReturn;
	}
	
	public String proofArgument(String[] saParamAll) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(saParamAll==null|saParamAll.length==0) break main;
			
//			Falls das Pattern leer ist, sArgument ist aber gef�llt, dann FEHLER:
			String sPattern = this.getPattern();
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//TODO: DAS PATTERN SELBST VALIDIEREN
			
			
			//##### Die Pruefung
			sReturn = this.proofArgument_(sPattern, saParamAll);
			
		}//End main
		return sReturn;
	}
	
	private String proofArgument_(String sPattern, String[] saParamAll) throws ExceptionZZZ{
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//#### Die Pruefung des Argumentarrays
			if(saParamAll==null|saParamAll.length==0) break main;
					
//			Die Liste der vorherigen Steuerzeichen
			ArrayList listaControlValue = GetOptZZZ.Pattern2ListControlValue(sPattern);			
            ArrayList listaControl = GetOptZZZ.Pattern2ListControlAll(sPattern);     
          
			
			//Nun die Argumentliste pr�fen:
            //Es muss immer ein Steuerzeichen sein,
            //nur wenn es ein Steuerzeichen mit Parameter ist, dann darf der folgende Wert beliebig sein
            ArrayList listaControlFound = new ArrayList();
            String sControlPrevious = new String("");
            boolean bNeedArgument = false;
			for(int icount=0; icount <= saParamAll.length-1;icount++){
				if(!StringZZZ.isEmpty(saParamAll[icount])){
					String stemp = saParamAll[icount].substring(0, 1);
					if(stemp.equals("-") & sControlPrevious.equals("")){
						listaControlFound.add(StringZZZ.rightback(saParamAll[icount], 1));  //Ohne den Bindestrich !!!
						//sControlPrevious = saParamAll[icount].substring(1);	      //Das sollte Doch der Wert bis zum n�chsten LEERZEICHEN SEIN
						sControlPrevious = StringZZZ.rightback(saParamAll[icount], 1);	      //Das ist der Wert bis zum n�chsten LEERZEICHEN
						
						//Falls dieses gefundene Steuerzeichen mehr kein Zeichen oder mehr als 2 Zeichen lang ist, FEHLER
						if(StringZZZ.isEmpty(sControlPrevious)){
							sReturn = "Error 10: Argument string contains empty control parameter. No character after '-'";
							break main;
						}
						if(sControlPrevious.length()>=2){
							sReturn = "Error 20: Argument string contains control parameter longer than 1 character: " + sControlPrevious;
							break main;
						}
						if(!listaControl.contains(sControlPrevious)){
							//Fehler ein Steuerelement, dass nicht im PatternString defineirt ist
							sReturn = "Error 25: Control character not defined in pattern: '" + sControlPrevious + "'";
							break main;
						}
						
						
						if(listaControlValue.contains(sControlPrevious)){
							bNeedArgument = true; //Das ist ein Steuerzeichen mit Doppelpunkt => Argument wird ben�tigt
//							Hier braucht man ggf. das vorherige Steuerzeiche noch
						}else{
							bNeedArgument = false; //Das ist Kein Steuerzeichen mit Doppelpunkt
							sControlPrevious = "";
						}
					}else if(stemp.equals("-") & ! listaControlValue.contains(sControlPrevious)){
						//Der vorherige Eintrag des Pattern endete nicht mit einem Doppelpunkt, dann ist das jetzt ein Steuerzeichen
						sControlPrevious = StringZZZ.rightback(saParamAll[icount], 1);	 //saParamAll[icount].substring(1);
						stemp = StringZZZ.rightback(saParamAll[icount], 1);
						if(listaControlValue.contains(stemp)){
							bNeedArgument = true; //Das ist ein Steuerzeichen mit Doppelpunkt => Argument wird ben�tigt
							//Hier braucht man ggf. das vorherige Steuerzeiche noch
						}else{
							bNeedArgument = false; //Das ist Kein Steuerzeichen mit Doppelpunkt
							sControlPrevious = "";
						}
					}else{						
						if(icount==0){
							//kein vorheriger Eintrag, Fehler !!!
							sReturn = "Error 30: No previous control character for the string: '" + saParamAll[icount] + "'";
							break main;
						}else if(sControlPrevious.equals("")){
							//Der vorherige Eintrag ist kein Steuerzeichen, Fehler !!!
							sReturn = "Error 40: Previous entry is no control character expecting an argument. Argument string: '" + saParamAll[icount] +"'";
							break main;																			
						}else if(! listaControlValue.contains(sControlPrevious)){
//							der vorherige Wert muss in der Liste der Steuerzeichen mit Argument sein
							sReturn = "Error 50: Previous entry is not a valid control character. current string: '" + saParamAll[icount] + "'";
							break main;													
						}
						bNeedArgument = false;
						sControlPrevious = "";
					}
				}else{
					if(bNeedArgument==true){
//						Leeres Argument f�r ein Steuerzeichen
						bNeedArgument = false;
						sControlPrevious = "";
					}else{
						//Leeres Steuerzeichen
					}
				}
			}
			//Fehler - LETZTES STEUERZEICHEN SOLLTE EINEN WERT HABEN, HAT�s ABER NCIHT
			if(bNeedArgument == true){
				sReturn = "Error 60: Last control character has no argument: '" + saParamAll[saParamAll.length-1] + "'";
				break main;
			}
		}//end main
		return sReturn;
	}
	
	public boolean isArgumentValid(String sArgument) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Null oder Leerstring sind valid
			if(StringZZZ.isEmpty(sArgument)){
				bReturn = true;
				break main;
			}
			
			//Argument-Pattern
			String sPattern = this.getPattern();
			
			//Falls das Pattern leer ist, sArgument ist aber gef�llt, dann FEHLER:
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sReturnError = this.proofArgument(sArgument);
			int iReturnError = GetOptZZZ.computeCodeFromProofResult(sReturnError);
			if (iReturnError <= -1){
				bReturn = true;
			}else{
				bReturn = false;
			}
		}
		return bReturn;
	}
	
	public boolean isArgumentValid(String[] saArg) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Null oder Leerstring sind valid
			if(saArg==null){
				bReturn = true;
				break main;
			}
			
			if(saArg.length==0){
				bReturn = false;
				break main;
			}
			
			
			//Argument-Pattern
			String sPattern = this.getPattern();
			
			//Falls das Pattern leer ist, sArgument ist aber gef�llt, dann FEHLER:
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sReturnError = this.proofArgument(saArg);
			int iReturnError = GetOptZZZ.computeCodeFromProofResult(sReturnError);
			if (iReturnError <= -1){
				bReturn = true;
			}else{
				bReturn = false;
			}
		}
		return bReturn;
		
	}
	
	
	/** TODO Muss noch entwickelt werden. Pr�ft. ob der Pattern String valide ist.
	 * Z.B: zwei Doppelpunkte hintereinander sind nicht valide
	 * - �berhaupt sind doppelte zeichen im pattern string nicht valide
	 * - Bindestriche sind im pattern string ebenfalls nicht erlaubt (sonst m�sste im Argument string ja "-- hallo" stehen d�rfen.
	 * 
	* @param sPattern
	* @return
	* 
	* lindhauer; 07.07.2007 12:04:03
	 * @throws ExceptionZZZ 
	 */
	public boolean isPatternStringValid(String sPattern) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sReturnError = this.proofPatternString(sPattern);
			int iReturnError = GetOptZZZ.computeCodeFromProofResult(sReturnError);
			if (iReturnError <= -1){
				bReturn = true;
			}else{
				bReturn = false;
			}
		}
		return bReturn;
	}
	
	/** Pr�ft den Pattern String auf folgendes:
	 *1. Kein wert darf doppelt vorkommen, mit Ausnahme des Doppelpunkts
 	* 2. Nach einem Doppelpunkt darf kein zweiter Doppelpunkt sofort folgen
	* 3. Der Pattern String darf nicht mit einem Doppelpunkt beginnen.	
	* @param sPattern
	* @return  Ein String, der eine Fehlermeldung enth�lt. 
	* 
	* lindhauer; 11.07.2007 06:55:02
	 */
	public String proofPatternString(String sPattern){
		String sReturn = new String("");
		main:{
			//#### Parameter f�r die Funktion pr�fen
//			Null oder Leerstring sind valid
			if(StringZZZ.isEmpty(sPattern))	break main;
			
			
//			+++ 1. Der Pattern String darf nicht mit einem Doppelpunkt beginnen.
			String sCharacter = sPattern.substring(0,1);
			if(sCharacter.equals(":")){
				sReturn = "Error 3: The character ':' is an argument placeholder. It is not allowed at the beginning. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
				break main;
			}
			
			//+++ 2. Kein Wert darf doppelt vorkommen, mit Ausnahme des Doppelpunkts
			String sRest = sPattern;
			while(sRest.length()>=1){
				sCharacter = sRest.substring(0,1);
				sRest = StringZZZ.rightback(sRest, 1);
				if(!sCharacter.equals(":")){
					int icount = StringZZZ.count(sRest, sCharacter);
					if(icount >= 1){   //d.h. wenn das Zeichen noch einmal vorkommt
						sReturn = "Error 1: The character '" + sCharacter + "' is " + (icount+1) + " times in the pattern string. Pattern '" + sPattern +"'";
						break main;
					}
				}
			}
			
			//+++ 3. Nach einem Doppelpunkt darf kein zweiter Doppelpunkt sofort folgen
			String[] saDelim = {":"};
			Integer[] intaIndex = StringZZZ.allIndexOf(sPattern, saDelim);
			
			//Nun muss das Zeichen jeweils 1 Zeichen vor dem Indexwert geholt werden, 
			//dann hat man die STEUERZEICHEN, die ein ARGUMENT ERWARTEN	
			  if(intaIndex!=null){            	
	            	for(int icount=0; icount <= intaIndex.length-1; icount++){            		
	            		Integer inttemp = intaIndex[icount];
	            		if(inttemp.intValue()>=0){
	            			int itemp = inttemp.intValue()-1;  //Position des Zeichens vor dem ":" 
	            			String stemp = sPattern.substring(itemp, itemp + 1);
	            			
	            			//Falls das ein Doppelpunkt ist : FEHLER
	            			if (stemp.equals(":")){
	            				sReturn = "Error 2: The character ':' is an argument placeholder. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
	            				break main;
	            			}
	            			
	            		}
	            	}
	           }
			  
			
		
			
		}//end main
		return sReturn;
	}
	
	
	/** Liest beispielsweise aus dem String "Error 123: der Fehler ist folgender" den Wert 123 aus 
	* @param sResult
	* @return -1 bei keinem gefundenen Fehler
	* 
	* lindhauer; 07.07.2007 09:57:08
	 * @throws ExceptionZZZ 
	 */
	public static int computeCodeFromProofResult(String sResult) throws ExceptionZZZ{
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sResult)) break main;
			
			String sReturn = StringZZZ.left(sResult, 5, ":");  //5 ist die Zeichenl�nge von "Error"
			if(StringZZZ.isEmpty(sReturn)){
				ExceptionZZZ ez = new ExceptionZZZ("No error code found in the string:'"+ sResult+"'", iERROR_RUNTIME, GetOptZZZ.class.getName());
				throw ez;
			}
			sReturn = sReturn.trim();
			
			if(!StringZZZ.isNumeric(sReturn)){
				ExceptionZZZ ez = new ExceptionZZZ("None numeric error code found in the string: '" + sResult + "'", iERROR_RUNTIME, GetOptZZZ.class.getName());
				throw ez;
			}
			
			Integer intReturn = new Integer(sReturn);
			iReturn = intReturn.intValue();
		}
		return iReturn;
	}
	
	
	public void setOptionIterator(Iterator it){
		this.itOpt = it;
	}
	public Iterator getOptionIterator(){
		return this.itOpt;
	}
	
	
	
	//############
	// Getter / Setter
	//############
	

	/**
	 * @see zzzKernel.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * 	"isLoaded": Wird gesetzt, wenn die interne HashMap erfolgreich gef�llt worden ist, z.B. in .loadOptionAll(..);
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue){
		boolean bFunction = false;
		main:{
		if(StringZZZ.isEmpty(sFlagName)) break main;
		bFunction = super.setFlag(sFlagName, bFlagValue);
		if(bFunction==true) break main;
	
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("isloaded")){
			bFlagIsLoaded = bFlagValue;
			bFunction = true;
			break main;
		}
		}//end main:
		return bFunction;
	}
	
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
			
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("isloaded")){
				bFunction = bFlagIsLoaded;
				break main;
			}
		}//end main:
		return bFunction;
	}

	@Override
	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub
		
	}
	
}//END CLASS
