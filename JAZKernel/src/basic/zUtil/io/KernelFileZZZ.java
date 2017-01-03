package basic.zUtil.io;

import java.io.File;
import java.util.StringTokenizer;

import basic.zBasic.IConstantZZZ;
import basic.zBasic.IFunctionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.math.MathZZZ;


/**
This class extends File and not ObjectZZZ !!!
==> it inherits the methods of file and can also be used as input for printwriter/reader - objects !!!
==> it implements AssetObjectZZZ for throwing ExcetptionZZZ
==> it doesn´t implement assetKernelZZZ, because this class is used to create the log-file of the kernel.
         I never implement any functionality in an object which is used for this functionality !!!

TODO Einige static Methoden an basic.zBasic.Util.file.FileEasyZZZ abgeben  
 * @author Lindhauer
 */
public class KernelFileZZZ extends File implements IConstantZZZ, IObjectZZZ, IFunctionZZZ {
	private static final char cEXPANSION_FILLING_DEFAULT = '0'; 
	private String sFileNameExpandedNext = new String("");
	private String sFileNameExpandedFirst = new String("");
	private String sFileNameExpandedCurrent = new String("");
	//private String sFileNameExpanded = new String("");
	private ExceptionZZZ objException;
	private int iExpansionLength;
	
	private String sFileEnding = new String("");        // die Endung, z.B. txt
	private String sFileNameOnly = new String("");      // der Dateiname ohne Endung, also FileNamePure + Expansion
	private String sDirectoryPath = new String("");
	private String sFileName = new String(""); // Der ganze Dateiname, also: FileNamePure + Expansion + "." + Ending
	private char cExpansionFilling=cEXPANSION_FILLING_DEFAULT;
	
	//flags 
	private boolean bFlagExpansionAppend; //Zeigt an, ob eine Dateinamens Expansion angehängt werden muss, oder eine bestehende Expansion ersetzt hat.
	
//	### Constructor ##########################
	public KernelFileZZZ(){
		this("","","init");
	}
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName, String[] saFlagControlIn) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		String stemp; boolean btemp;
		main:{
			
		//seten der übergebenen Flags	
			if(saFlagControlIn != null){
				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
					stemp = saFlagControlIn[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( sERROR_FLAG_UNAVAILABLE + stemp, iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
						   //doesn´t work. Only works when > JDK 1.4
						   //Exception e = new Exception();
						   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
						   throw ez;		 
					}
				}
				}

			//+++ Falls das Debug-Flag gesetzt ist, muss nun eine Session über das Factory-Objekt erzeugt werden. 
			// Damit kann auf andere Datenbanken zugegriffen werden (z.B. im Eclipse Debugger)
			// Besser jedoch ist es beim Debuggen mit einem anderen Tool eine Notes-ID zu verwenden, die ein leeres Passwort hat.
			btemp = this.getFlag("init");
			if(btemp==true) break main;
		
		this.sDirectoryPath = sDirectoryPath;
		this.sFileName = sFileName;
		this.iExpansionLength = 3; //der Defaultwert
		}//end main		
	}
	
	
	public KernelFileZZZ(String sDirectoryPath, String sFileName, String sFlagControl){
		super(sDirectoryPath + "\\" + sFileName);
		this.sDirectoryPath = sDirectoryPath;
		this.sFileName = sFileName;
		this.iExpansionLength = 3; //der Defaultwert 
	}

	
	/** CONSTRUCTOR
	 @date: 04.10.2004
	 @param sPathTarget
	 @param sFileTarget
	 @param iExpansionLength
	 * @throws ExceptionZZZ 
	 */
	public KernelFileZZZ(String sDirectoryPath, String sFileName, int iExpansionLength, String[] saFlagControl) throws ExceptionZZZ {		
		super(sDirectoryPath + "\\" + sFileName);
		main:{
			if(saFlagControl!=null){
				boolean btemp = false;
				for(int icount=0;icount <= saFlagControl.length-1;icount++){
					String stemp = saFlagControl[icount];
					btemp = this.setFlag(stemp, true);
					
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( sERROR_FLAG_UNAVAILABLE + stemp, iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
						   throw ez;		 
					}
				}
				if(this.getFlag("init")) break main;
			}
			
			this.sDirectoryPath = sDirectoryPath;
			this.sFileName = sFileName;
			this.iExpansionLength = iExpansionLength;			
		
		}//End main:
	}

	
	//### Acessor - Methods ###################
	//+++++++++++++++++++++++++++++++++++++++++
	public String getNameEnd(){
		if(this.sFileEnding.equals("")){
			this.sFileEnding =FileEasyZZZ.NameEndCompute(this.sFileName);
		}
		return this.sFileEnding;
	}
	
	public String getNameWithChangedEnd(String sEnd) throws ExceptionZZZ{
		String sReturn = null;
		
		String sFileName = this.sFileName;
		sReturn = FileEasyZZZ.getNameWithChangedEnd(sFileName, sEnd);
		/*
		int iFileOnlyLength = sFileName.lastIndexOf(".");
		if(iFileOnlyLength > -1){
			sReturn = StringZZZ.left(sFileName, iFileOnlyLength + 1);
			if(StringZZZ.isEmpty(sReturn)){
				sReturn = sFileName + File.separator + sEnd;
			}else{
				sReturn = sReturn + sEnd;
			}
		}else{
			sReturn = sFileName + File.separator + sEnd;
		}*/
		return sReturn;
	}
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++
	public String getNameOnly() throws ExceptionZZZ{
		if(this.sFileNameOnly.equals("")){
			this.sFileNameOnly =FileEasyZZZ.NameOnlyCompute(this.sFileName);	
		}
		return this.sFileNameOnly;
	}
	
	
	/** Berechnet den nächsten Dateinamen. Dabei wird ggf. eine Zählvariable an den Dateinamen angehängt
	 * 
	 * Merke: Der Rückgabewert enthält nur den Dateinamen nicht den Pfad
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 25.02.2008 11:58:46
	 */
	public String getNameExpandedNext() throws ExceptionZZZ{
		if(this.sFileNameExpandedNext.equals("")){
			String stemp = PathNameTotalExpandedNextCompute();
			File objFile = new File(stemp);
			this.sFileNameExpandedNext = objFile.getName();  				
		}
		return this.sFileNameExpandedNext;
	}
	
	public String getNameExpandedFirst() throws ExceptionZZZ{
		if(this.sFileNameExpandedFirst.equals("")){
					String stemp = PathNameTotalExpandedFirstCompute();
					File objFile = new File(stemp);
					this.sFileNameExpandedFirst = objFile.getName();  				
				}
				return this.sFileNameExpandedFirst;
	}
	
	public String getNameExpandedCurrent() throws ExceptionZZZ{
		if(this.sFileNameExpandedCurrent.equals("")){
					String stemp = PathNameTotalExpandedCurrentCompute();
					File objFile = new File(stemp);
					this.sFileNameExpandedCurrent = objFile.getName();  				
				}
				return this.sFileNameExpandedCurrent;
	}
	

	
	
	//++++++++++++++++++++++++++++++++++++++++++
	public String getPathDirectory(){
		//Merke: hierfür gibt es keine Berechnung. Dies wird konstant gesetzt.
		return this.sDirectoryPath;			
	}
	
	public void setPathDirectory(String sToSet){
		this.sDirectoryPath = sToSet;
	}
	
	
	//### FlagMethods ##########################
	//++++++++++++++++++++++++++++++++++++++++++
	public boolean getFlag(String sFlagAlias){
		boolean bFunction = false;
		
		main:{
			String sTemp = sFlagAlias.toLowerCase();
			if(sTemp.equals("expansionappend")){
					bFunction = this.bFlagExpansionAppend;
			}
		}
		end:{
			return bFunction;	
		}		
	} // end function
	
	//+++++++++++++++++++++++++++++++++++++++++++
	public boolean setFlag(String sFlagAlias, boolean bValue){
		boolean bFunction = false;
		
		main:{
			String sTemp = sFlagAlias.toLowerCase();
			if(sTemp.equals("expansionappend")){
				this.bFlagExpansionAppend = bValue;
				bFunction = true;
			}			
		}
		end:{
			return bFunction;	
		}	
	}
	
	
	
	
	
	//### Functions #########################
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	/**
	 @param sDirectoryName
	 @param sFileName
	 @param iExpansionLength
	 @return String, Path with filename. The filename does have the next expansion. 
	 * @throws ExceptionZZZ 
	 */
	public String PathNameTotalExpandedNextCompute(String sDirectoryNameIn, String sFileNameIn,int iExpandLengthIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			int iFileLength;
			int iExpandLength;
			String sFileName = new String("");
			String sDirectoryName = new String("");
			
			//////
			paramcheck:
			{
				if(sDirectoryNameIn == null){
					sDirectoryName = this.sDirectoryPath;
				}else if(sDirectoryNameIn.equals("")){
					sDirectoryName = this.sDirectoryPath;
				}
				
				
				if(sFileNameIn == null){
					sFileName = this.sFileName;
				}else if(sFileNameIn.equals("")){
					sFileName = this.sFileName;
				}
				
				//Leere Dateinamen können nicht "expanidert" werden.
				iFileLength = sFileName.length();
				if(iFileLength <= 0){
					break main;
				}				
			
				if(iExpandLengthIn <= 0 || iExpandLengthIn >= 6){
					iExpandLength = 3; //Default
				} else {
					iExpandLength = iExpandLengthIn;				
				}							 
			}//end paramcheck
 	 	
 	 		sReturn = PathNameTotalExpandedNextCompute_(sDirectoryName, sFileName, iExpandLength);
		
		}//end main
		return sReturn;
	}
	
	public String PathNameTotalExpandedFirstCompute(String sDirectoryNameIn, String sFileNameIn,int iExpandLengthIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		int iFileLength;
		int iExpandLength;
		String sFileName = new String("");
		String sDirectoryName = new String("");
			
		//////
		paramcheck:
		{
			if(sDirectoryNameIn == null){
				sDirectoryName = this.sDirectoryPath;
			}else if(sDirectoryNameIn.equals("")){
				sDirectoryName = this.sDirectoryPath;
			}
				
				
			if(sFileNameIn == null){
				sFileName = this.sFileName;
			}else if(sFileNameIn.equals("")){
				sFileName = this.sFileName;
			}
				
			//Leere Dateinamen können nicht "expanidert" werden.
			iFileLength = sFileName.length();
			if(iFileLength <= 0){
				break main;
			}				
			
			if(iExpandLengthIn <= 0 || iExpandLengthIn >= 6){
				iExpandLength = 3; //Default
			} else {
				iExpandLength = iExpandLengthIn;				
			}							 
		}//end paramcheck
 	 	
		sReturn = PathNameTotalExpandedFirstCompute_(sDirectoryName, sFileName, iExpandLength);
		
	}//end main
	return sReturn;
}


public String PathNameTotalExpandedCurrentCompute(String sDirectoryNameIn, String sFileNameIn,int iExpandLengthIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		int iFileLength;
		int iExpandLength;
		String sFileName = new String("");
		String sDirectoryName = new String("");
			
		//////
		paramcheck:
		{
			if(sDirectoryNameIn == null){
				sDirectoryName = this.sDirectoryPath;
			}else if(sDirectoryNameIn.equals("")){
				sDirectoryName = this.sDirectoryPath;
			}
				
				
			if(sFileNameIn == null){
				sFileName = this.sFileName;
			}else if(sFileNameIn.equals("")){
				sFileName = this.sFileName;
			}
				
			//Leere Dateinamen können nicht "expanidert" werden.
			iFileLength = sFileName.length();
			if(iFileLength <= 0){
				break main;
			}				
			
			if(iExpandLengthIn <= 0 || iExpandLengthIn >= 6){
				iExpandLength = 3; //Default
			} else {
				iExpandLength = iExpandLengthIn;				
			}							 
		}//end paramcheck
 	 	
		sReturn = PathNameTotalExpandedCurrentCompute_(sDirectoryName, sFileName, iExpandLength);
		
	}//end main
	return sReturn;
}

public String PathNameTotalExpandedFirstCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedFirstCompute_(this.getPathDirectory(), this.getName(), this.getExpansionLength());				
} //end main
return sReturn;
}

public String PathNameTotalExpandedCurrentCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedCurrentCompute_(this.getPathDirectory(), this.getName(), this.getExpansionLength());				
} //end main
return sReturn;
}


	
	public String PathNameTotalExpandedNextCompute() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			sReturn = PathNameTotalExpandedNextCompute_(this.getPathDirectory(), this.getName(), this.getExpansionLength());				
	} //end main
	return sReturn;
	}
	
	private String PathNameTotalExpandedNextCompute_(String sDirectory, String sFile, int iExpansionLength) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
					String sExpandValue = this.getExpansionNext(iExpansionLength);
					String sFileOnly = this.getNameOnly();
					String sEnding = this.getNameEnd();
					if(sEnding.length() > 0){
						sEnding = "." + sEnding;
					}
		
					if(bFlagExpansionAppend == false){
						sReturn =  this.getPathDirectory() + "\\" + sFileOnly + sEnding;	
						this.sFileNameExpandedNext = sFileOnly + sEnding;
						break main;
					}else{
						sReturn = this.getPathDirectory() + "\\" + sFileOnly + sExpandValue + sEnding;
						this.sFileNameExpandedNext = sFileOnly+sExpandValue+sEnding;
						break main;			
					}			 
		}//end main
		return sReturn;
	}
	
	private String PathNameTotalExpandedFirstCompute_(String sDirectory, String sFile, int iExpansionLength) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
				String sExpandValue = this.getExpansionFirst(iExpansionLength);
				String sFileOnly = this.getNameOnly();
				String sEnding = this.getNameEnd();
				if(sEnding.length() > 0){
					sEnding = "." + sEnding;
				}
		
				if(bFlagExpansionAppend == false){
					sReturn =  this.getPathDirectory() + "\\" + sFileOnly + sEnding;		
					this.sFileNameExpandedFirst = sFileOnly + sEnding;
					break main;
				}else{
					sReturn = this.getPathDirectory() + "\\" + sFileOnly + sExpandValue + sEnding;
					this.sFileNameExpandedFirst = sFileOnly + sExpandValue + sEnding;
					break main;			
				}			 
	}//end main
	return sReturn;
}

private String PathNameTotalExpandedCurrentCompute_(String sDirectory, String sFile, int iExpansionLength) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
				String sExpandValue = this.getExpansionCurrent(iExpansionLength);
				String sFileOnly = this.getNameOnly();
				String sEnding = this.getNameEnd();
				if(sEnding.length() > 0){
					sEnding = "." + sEnding;
				}
		
				if(bFlagExpansionAppend == false){
					sReturn =  this.getPathDirectory() + "\\" + sFileOnly + sEnding;		
					this.sFileNameExpandedCurrent = sFileOnly + sEnding;
					break main;
				}else{
					sReturn = this.getPathDirectory() + "\\" + sFileOnly + sExpandValue + sEnding;
					this.sFileNameExpandedCurrent = sFileOnly + sExpandValue + sEnding;
					break main;			
				}			 
	}//end main
	return sReturn;
}	
	
	
	/**
	 @param iExpansionLength
	 @return String, the Expansion which has not been used by any other file, e.g. 000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionNext(int iExpansionLength) throws ExceptionZZZ{
		String sFunction = new String("");
		
		Integer intTemp = new Integer(0);
		String sTemp = new String("");
		boolean bTemp = false;
		
		main:{
			String sExpansionCur = getExpansionCurrent(iExpansionLength);
			//System.out.println("Gefundene letzte Datei-Expansion: '" + sExpansionCur + "'");
			if(sExpansionCur.length() > 0){
				intTemp = new Integer(sExpansionCur);
				sTemp = ExpansionCompute(this.getExpansionFilling(),intTemp.intValue() + 1, iExpansionLength);
				sFunction = sTemp;
			}else{
				bTemp = this.getFlag("ExpansionAppend");
				if(bTemp == true){
					sTemp =  ExpansionCompute(this.getExpansionFilling(), 1, iExpansionLength);
					sFunction = sTemp;
				}
			}		
		}
		
		end:{
			return sFunction;
		}
	} // end function
	
	/** String, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is "0" ---> e.g. "0001"
	 * but it is possible to change this to e.g.  "-"  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:15:07
	 * @return String
	 */
	public String getExpansionFilling() {
		String sReturn = String.valueOf(cExpansionFilling);
		if(sReturn.equals("")){
			sReturn = String.valueOf(cEXPANSION_FILLING_DEFAULT);
		}
		return sReturn;
	}
	
	/** void, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is '0' ---> e.g. "0001"
	 * but it is possible to change this to e.g.  '-'  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:20:14
	 * @param cExpansionFilling
	 */
	public void setExpansionFilling(char cExpansionFilling){
		//TODO Das Filling-Zeichen muss dahingehend geprüft werden, ob es ein Zeichene ist, das in einem Dateinamen enthalten ist
		this.cExpansionFilling = cExpansionFilling;
	}
	
	public void setExpansionFilling(String sExpansionFilling) throws ExceptionZZZ{
		//		TODO Das Filling-Zeichen muss dahingehend geprüft werden, ob es ein Zeichene ist, das in einem Dateinamen enthalten ist
		if(sExpansionFilling.length() >= 2) {
			ExceptionZZZ ez = new ExceptionZZZ("An expansion-filling should be one character", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;		 
		}else if(sExpansionFilling.length() == 0){
			this.cExpansionFilling='0';
		}else{
			this.cExpansionFilling = sExpansionFilling.charAt(0);
		} 
		
		
	}
	

	/**
	 @param iExpansionLength
	 @return String, last used expansion, e.g. 000 ---> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionCurrent(int iExpansionLength) throws ExceptionZZZ{
		String sFunction = new String("");				
		String sExpansionFoundLast = new String("");
		
		boolean bFound = false;
		int iCounter = 0;
		Integer intCounter = new Integer(0);
						
		main:{
			
			//if the current file exists, then a expansion must be appended
			if(this.exists()){
				sFunction = "";
				this.setFlag("ExpansionAppend", true);	
			}
		
		//get file details
		String sPath = this.getPathDirectory();
		if(sPath.length() > 0){
			sPath = sPath + "\\";
		}
		String sEnding = this.getNameEnd();
		if(sEnding.length() > 0){
			sEnding = "." + sEnding;
		}
		
		//maximale Zahl errechen
		//double dTemp = Math.pow(10, iExpansionLength);
		//iCounter = (int)dTemp;
		intCounter = new Integer(getExpansionMax(iExpansionLength));
		iCounter = intCounter.intValue();
		
			//create new expansions and try their existance.
			do{
				String sExpansion = ExpansionCompute("0", iCounter, iExpansionLength);
				File f = new File(sPath + this.getNameOnly() + sExpansion + sEnding);
				if(f.exists() == true){
					bFound = true;
					sExpansionFoundLast = sExpansion;
					//Remark: Don´t leave this loop, because there can be a gap
				}
				iCounter--;			
			}while(iCounter >= 0 && bFound == false);
		
		//das wird ausserhalb der Schleife gemacht, performance
		if(bFound==true){
			this.setFlag("ExpansionAppend", true);	
		}
			
		}
		
		end:{
			if(this.getFlag("ExpansionAppend") == true){
			sFunction = sExpansionFoundLast;
			}else{
				sFunction = "";
			}
			return sFunction;
		}
	} // end function

	/**
	 @param iExpansionLength
	 @return String, the first found expansion for the file (e.g. the filename itself when there are no files  or  000 --> 999
	 * @throws ExceptionZZZ 
	 */
	public String getExpansionFirst(int iExpansionLength) throws ExceptionZZZ{
		String sFunction = new String("");
		function:{	
			paramCheck:{
				if(iExpansionLength <= 0){
					break function;	
				}
				
				if(this.exists()){
					break function; //falls die Originaldatei existiert, so gilt diese als erste Datei in der Reihenfolge.		
				}
			}//end paramCheck
									
			String sPath = this.getPathDirectory();
			if(sPath.length() > 0){
				sPath = sPath + "\\";
			}
			String sEnding = this.getNameEnd();
			if(sEnding.length() > 0){
				sEnding = "." + sEnding;
			}
			
			//Die höchste Expansion ermitteln
			Integer intNrOfExpansion = 	new Integer(getExpansionMax(iExpansionLength));
			int iNrOfExpansion = intNrOfExpansion.intValue();
			for (int iCount = 0; iCount <= iNrOfExpansion; iCount++){
					String sExpansion = ExpansionCompute("0", iCount, iExpansionLength);
					File f = new File(sPath + this.getNameOnly() + sExpansion + sEnding);
					if(f.exists() == true){
						this.setFlag("ExpansionAppend",true);
						sFunction = sExpansion;
						break function;
					}
			}
		
			
							
		}//end function
		return sFunction;	
	}
	
	public int getExpansionLength(){		
		return iExpansionLength;
	}

	public void setExpansionLength(int iExpansionLength){
		this.iExpansionLength = iExpansionLength;
	}
	
	
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
	
	/** 
	 @param sFilling
	 @param iEndingValue
	 @param iEndingLength
	 @return String Expansion, e.g. '001'
	 */
	public String ExpansionCompute(String sFilling, int iEndingValue, int iEndingLength) {
		String sReturn = new String("");
		String sEndingValue = new String("");
		Integer intEndingValue = new Integer(0);
		main:{
			paramcheck:{
				if(iEndingValue <= 0){
					break main;	
				}	
			}//end paramcheck
		
		intEndingValue = new Integer(iEndingValue);
		sEndingValue = intEndingValue.toString();
				
		if(sEndingValue.length() == iEndingLength){
			//Den Eingangswert zurückgeben 
			sReturn = sEndingValue;
		} else if(iEndingLength==0){
			// !!! nix weiter zu tun, es soll keine Endung zurückgegeben werden
			sReturn = "";
		}else{
			//Führende Nullen setzen
			int iEndingToFill = iEndingLength - sEndingValue.length();
			for(int iCounter = 0; iCounter < iEndingToFill; iCounter++){
				sEndingValue = sFilling + sEndingValue;				
			}
			sReturn = sEndingValue;
		}				
		}//end main
		return sReturn;
	} //end function
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return objException;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}//end function

	public boolean proofFlagExists(String sFlagName) {
		// TODO Auto-generated method stub
		return false;
	}
}//end class
