package basic.zBasic.util.datatype.string;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.math.MathZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class StringArrayZZZ implements IConstantZZZ{
	private String[] saIntern;
	private boolean bIsString = false;

	//Konstruktoren
	public StringArrayZZZ(String args[]) throws ExceptionZZZ{
		if(args==null){
			ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		this.saIntern = args;
		this.bIsString = true;
	}
	
	public boolean contains(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		
		if(this.bIsString == true){
			bReturn = StringArrayZZZ.contains(this.getArray(), sToFind);
		} //end if
			
		return bReturn;
	}//end contains
	
	public static boolean contains(String[] saSource, String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(saSource==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 for(int iCounter = 0; iCounter <= saSource.length -1; iCounter++){
			 	String sTemp = saSource[iCounter];
			 	if (sTemp.equalsIgnoreCase(sToFind)){	
			 		bReturn = true;
			 		break main;
			 	}
			 } // end for	
		}//end main;		
		return bReturn;
	}
	
	
	/** long, returns the index of the first sToFind in the string Array
	* Lindhauer; 16.05.2006 07:42:37
	 * @param sToFind
	 * @return long
	 */
	public long getIndexFirst(String sToFind){
		long lFunction = -1;
		
		if(this.bIsString == true){
			 for(int iCounter = 0; iCounter <= this.saIntern.length -1; iCounter++){
		 	String sTemp = saIntern[iCounter];
		 	// Erst ab Java 1.2   if (sTemp.compareToIgnoreCase(sToFind)== 0){
		 	if (sTemp.compareTo(sToFind)== 0){
		 		lFunction = (long)iCounter;
		 		break;
		 	}
		 } // end for				
		} //end if
output:
		return lFunction;
	}//end IndexFirstGet
	
	
	public static String[] insertSorted(String[] saSorted, String sString, String sFlagIn) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
		if(saSorted==null && StringZZZ.isEmpty(sString)) break main;
		if(saSorted==null){
			objReturn = new String[0];
			objReturn[0] = sString;
			break main;
		}
		if(StringZZZ.isEmpty(sString)){
			objReturn = new String[saSorted.length];
			System.arraycopy(saSorted,0,objReturn, 0, saSorted.length);
			break main;
		}
		
		String sFlag = null;
		if(StringZZZ.isEmpty(sFlagIn)){
			sFlag = "";
		}else{
			sFlag = sFlagIn.toUpperCase();
			if(!(sFlag.equals("IGNORECASE"))){
				ExceptionZZZ ez = new ExceptionZZZ("Flag='"+sFlagIn+"', but expected '', 'IGNORECASE', ''", iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
		
		boolean bInserted = false;
		int iPositionOriginal = 0;
		objReturn = new String[saSorted.length+1];
		int iSize = objReturn.length;
		
		
		if(sFlag.equals("IGNORECASE")){
//			bis zur vorletzen Position inder Schleife behandeln
			for(int icount = 0; icount<=iSize-2;icount++){	
				String stemp = saSorted[iPositionOriginal];
				if(stemp.compareToIgnoreCase(sString)<=0 && bInserted ==false){
					//Bis zum gefundenen Wert das R�ckgabeArray auff�llen					
					objReturn[icount] = saSorted[iPositionOriginal];
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}else if(stemp.compareToIgnoreCase(sString)>0 && bInserted ==false){
					
					//Neuen Wert einf�gen
					objReturn[icount] = sString;		
					bInserted = true;
					
				}else if(bInserted == true){
					
					// Bis zum Ende die Originalwerte in das R�ckgabeArray f�llen					
					objReturn[icount] = stemp;
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}
			}//end for			
		}else{
			//bis zur vorletzen Position inder Schleife behandeln
			for(int icount = 0; icount<=iSize-2;icount++){	
				String stemp = saSorted[iPositionOriginal];
				if(stemp.compareToIgnoreCase(sString)<=0 && bInserted ==false){
					//Bis zum gefundenen Wert das R�ckgabeArray auff�llen				
					objReturn[icount] = saSorted[iPositionOriginal];
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}else if(stemp.compareTo(sString)>0 && bInserted ==false){
				
					//Neuen Wert einf�gen
					objReturn[icount] = sString;	
					bInserted = true;
					
				}else if(bInserted == true){
					
					// Bis zum Ende die Originalwerte in das R�ckgabeArray f�llen				
					objReturn[icount] = stemp;
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}
			}//end for
		}
		
//		immer separat die letzen Position einf�gen
		if(bInserted == false){
			objReturn[iSize-1] = sString;
		}else{
			objReturn[iSize-1] = saSorted[iPositionOriginal];
		}			
		
		}//End main:
		return objReturn;
	}
	
	/**Alle Elemente des String Arrays werden um einen weiteren String erweitert.
	 * @param saString
	 * @param sString
	 * @param sFlagin, BEFORE oder BEHIND
	 * @return
	 * @throws ExceptionZZZ, 
	 *
	 * @return String[]
	 *
	 * javadoc created by: 0823, 08.12.2006 - 13:13:09
	 */
	public static String[] plusString(String[] saString, String sString, String sFlagin) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
		if(StringZZZ.isEmpty(sString)){
			objReturn = new String[saString.length];
			System.arraycopy(saString,0,objReturn, 0, saString.length);
			break main;
		}
		
		String sFlag = null;
		if(StringZZZ.isEmpty(sFlagin)){
			sFlag = "BEHIND";
		}else{
			sFlag = sFlagin.toUpperCase();
			if(!(sFlag.equals("BEHIND")|sFlag.equals("BEFORE"))){
				ExceptionZZZ ez = new ExceptionZZZ("Flag='"+sFlagin+"', but expected '', 'BEFORE', 'BEHIND'", iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
		
		int iSize = saString.length;
		objReturn = new String[iSize];
		
		if(sFlag.equals("BEHIND")){
			for(int icount = 0; icount<=iSize-1;icount++){
				objReturn[icount]=saString[icount] + sString;
			}
			break main;
		}else{
			for(int icount = 0; icount<=iSize-1;icount++){
				objReturn[icount]=sString + saString[icount];
			}
			break main;
		}
		
		}//End main:
		return objReturn;
	}
	
	public void plusString(String sString, String sFlagin) throws ExceptionZZZ{
		String[] saNew = StringArrayZZZ.plusString(this.getArray(), sString, sFlagin);
		this.setArray(saNew);
	}
	
	
	public static String[] plusStringArray(String[] saString1, String[] saString2, String sFlagin) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
		if(saString1==null && saString2==null) break main;
		if(saString1==null){
			objReturn = new String[saString2.length];
			System.arraycopy(saString2,0,objReturn, 0, saString2.length);
			break main;
		}else if(saString2 == null){
			objReturn = new String[saString1.length];
			System.arraycopy(saString1,0,objReturn, 0, saString1.length);
			break main;			
		}
		
		String sFlag = null;
		if(StringZZZ.isEmpty(sFlagin)){
			sFlag = "BEHIND";
		}else{
			sFlag = sFlagin.toUpperCase();
			if(!(sFlag.equals("BEHIND")|sFlag.equals("BEFORE"))){
				ExceptionZZZ ez = new ExceptionZZZ("Flag='"+sFlagin+"', but expected '', 'BEFORE', 'BEHIND'", iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
				
		int iSize = MathZZZ.max(saString1.length, saString2.length);
		objReturn = new String[iSize];
		
		if(sFlag.equals("BEHIND")){//MErke: Diese if-Abfrage ist aus performance-gr�nden ausserhalb der Schleife
			for(int icount = 0; icount<=iSize-1;icount++){
				if(saString1.length-1>=icount && saString2.length-1>= icount){
					objReturn[icount]=saString1[icount] + saString2[icount];
				}else if(saString1.length-1<icount && saString2.length-1 >= icount){
					objReturn[icount] = saString2[icount];
				}else if(saString1.length-1 >= icount && saString2.length-1 < icount){
					objReturn[icount] = saString1[icount];
				}
			}
			break main;
		}else{
			for(int icount = 0; icount<=iSize-1;icount++){
				if(saString1.length-1>=icount && saString2.length-1>= icount){
					objReturn[icount]= saString2[icount] + saString1[icount] ;                    //Hier dann die Reihenfolge anders
				}else if(saString1.length-1<icount && saString2.length-1 >= icount){
					objReturn[icount] = saString2[icount];
				}else if(saString1.length-1 >= icount && saString2.length-1 < icount){
					objReturn[icount] = saString1[icount];
				}
			}
			break main;
		}
		
		}//End main:
		return objReturn;
	}
	public void plusStringArray(String[] saString, String sFlagin) throws ExceptionZZZ{
		String[] saNew = StringArrayZZZ.plusStringArray(this.getArray(), saString, sFlagin);
		this.setArray(saNew);
	}
	
	public void insertSorted(String sString, String sFlagin) throws ExceptionZZZ{
		String[] saNew = StringArrayZZZ.insertSorted(this.getArray(), sString, sFlagin);
		this.setArray(saNew);
	}
	
	public static Vector toVector(String[] saString){
		Vector objReturn = new Vector();
		main:{
			if(saString==null) break main;
			if(saString.length==0) break main;
			
			for(int icount = 0; icount <= saString.length-1; icount++){
				objReturn.add(saString[icount]);
			}
			
		}
		return objReturn;
	}
	
	public Vector toVector(){
		Vector objReturn = new Vector();
		main:{
			String[] saString = this.getArray();
			objReturn = StringArrayZZZ.toVector(saString);
		}
		return objReturn;
	}
	
	public static ArrayList toArrayList(String[] saString){
		ArrayList listaString = new ArrayList();
		main:{
			if(saString==null) break main;
			if(saString.length==0) break main;
			
			for(int icount=0; icount <= saString.length-1; icount++){
				String stemp = saString[icount];
				listaString.add(stemp);
			}
			
		}
		return listaString;
	}
	
	public ArrayList toArrayList(){
		ArrayList listaString = new ArrayList();
		main:{
			String[] saString = this.getArray();
			listaString = StringArrayZZZ.toArrayList(saString);
		}
		return listaString;
	}
	
	public static String implode(String[] saString){
		return StringUtils.join(saString);   //
	}
	
	public static String[] intersect(String[] saString01, String[] saString02) throws ExceptionZZZ{
		String[]saReturn = null;
		main:{
			if(saString01==null)break main;
			if(saString02==null)break main;
			if(saString01.length==0) break main;
			if(saString02.length==0) break main;
						
			ArrayList<String>listasTemp = new ArrayList<String>();
			for(String s01 : saString01){
				if(StringArrayZZZ.contains(saString02, s01)){
					listasTemp.add(s01);
				}				
			}			
			saReturn = listasTemp.toArray(new String[listasTemp.size()]);
		}//End main:
		return saReturn;
	}

	
	
	
	//#### GETTER / SETTER
	public String[] getArray(){
		return this.saIntern;
	}
	public void setArray(String[] saString){
		this.saIntern = saString;
	}
}
