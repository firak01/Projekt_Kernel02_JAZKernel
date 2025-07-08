package basic.zBasic.util.abstractList;

import java.util.Iterator;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectUtilZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class VectorUtilZZZ extends AbstractObjectZZZ {
	public static Vector unique(Vector vec) throws ExceptionZZZ{
		Vector objReturn = new Vector();
		main:{
			if(vec==null){
				ExceptionZZZ ez = new ExceptionZZZ("Vector is null", iERROR_PARAMETER_MISSING, "VectorZZZ", ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Iterator it = vec.iterator();
			while(it.hasNext()){				
				Object obj = it.next();
				if(!objReturn.contains(obj)){
					objReturn.add(obj);
				}				
			}
		}//END main
		return objReturn;
	}
	
	
	public static Vector append(Vector vecFirst, Vector vecBehind) throws ExceptionZZZ{
		Vector objReturn = null;
		main:{
			if(vecFirst==null && vecBehind == null) break main;
			if(vecFirst == null){
				objReturn = (Vector)vecBehind.clone();
				break main;
			}
			if(vecBehind==null){
				objReturn = (Vector) vecFirst.clone();
				break main;
			}
			
			//+++++++++++++++++++++++++++++++++++++++++
			objReturn = (Vector) vecFirst.clone();
			for(int icount = 0; icount <= vecBehind.size()-1; icount++){
				objReturn.addElement(vecBehind.elementAt(icount));
			}
			
		}//End main:
	return objReturn;
	}
	
	public static Vector reverse(Vector vecIn) throws ExceptionZZZ{
		Vector objReturn = null;
		main:{
			if(vecIn==null){
				break main;
			}
			
			objReturn = new Vector();
			for(int icount = vecIn.size()-1; icount >= 0; icount--){
				objReturn.addElement(vecIn.elementAt(icount));
			}
			
		}
		return objReturn;
	}
	
	public static boolean containsString(Vector vecIn, String sToFind) throws ExceptionZZZ{
		return 	VectorUtilZZZ.containsString(vecIn, sToFind, true);
	}
	
	public static boolean containsString(Vector vecIn, String sToFind, boolean bExactMatch) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			ExtendedVectorZZZ vecExtended = new ExtendedVectorZZZ(vecIn);
			bReturn = vecExtended.containsString(sToFind,!bExactMatch); //!!!Achtung VectorZZZ verwendet ignoreCase und nicht wie alle StringZZZ-Methoden bExcactMatch
		}//end main:
		return bReturn;		
	}
	
	/**Merke: Ensure Methoden werfen sofort die ExceptionZZZ
	 * @param vecIn
	 * @param iSize
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.09.2024, 09:01:00
	 */
	public static boolean ensureSize(Vector vecIn, int iSize) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(vecIn==null) break main;
			if(iSize<=0) break main;
			
			if(vecIn.size()!=iSize) {
				ExceptionZZZ ez = new ExceptionZZZ("Vector has invalid size: '" + vecIn.size() +"'", iERROR_PARAMETER_VALUE, VectorUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static Vector rightOfString(Vector vecIn, String sToFind, boolean bIgnoreCase) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(vecIn==null){
				break main;
			}
			if(StringZZZ.isEmpty(sToFind)){
				break main;
			}
			
			for(int icount = vecIn.size()-1; icount >= 0; icount--){
				String sString =(String)vecIn.elementAt(icount);
				if(StringZZZ.isEmpty(sString)){
					vecReturn.add("");					
				}else{
					String sResult = StringZZZ.right(sString, sToFind, bIgnoreCase);
					vecReturn.addElement(sResult);
				}				
			}
			
		}//End main
		return vecReturn;
	}
	
	public static String implode(Vector vecIn){
		String sReturn = null;
		main:{
			sReturn = VectorUtilZZZ.implode(vecIn, "");
		}//end main:
		return sReturn;
		
	}
	
	public static String implode(Vector vecIn, String sDelimiter){
		String sReturn = null;
		main:{
			if(vecIn==null) break main;
			if(vecIn.size()==0 || vecIn.isEmpty()){
				sReturn = new String("");
				break main;
			}
			
			if(vecIn.size()>=2){
				Iterator itValue = vecIn.iterator();
				while(itValue.hasNext()){
					Object obj = itValue.next();
					if(obj!=null){
						if(!StringZZZ.isEmpty(sReturn)){
							sReturn = sReturn + sDelimiter + obj.toString();
						}else{
							sReturn = obj.toString();
						}
					}
				}
			}else{
				Object obj = vecIn.firstElement();
				if(obj!=null){
					sReturn = obj.toString();
				}							
			}			
		}//end  main:
		return sReturn;			
	}
	
	public static <T> boolean isEmpty(Vector<T> vecIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(vecIn == null) break main;
			if(vecIn.isEmpty()){
				bReturn = true;
				break main;
			}
			
			boolean btemp;
			for(int icount = vecIn.size()-1; icount >= 0; icount--){
				Object obj =(Object)vecIn.elementAt(icount);
				
				//FGL 20241227 -Das muss noch getestet werden
				if(obj==null) {
					//mache nix...
				}else if(obj.getClass().isArray()) {
					btemp = ArrayUtilZZZ.isNullOrEmpty((T[]) obj);
					if(!btemp) break main;
					
					btemp = ArrayUtilZZZ.isEmpty((T[]) obj);
					if(!btemp) break main;
				}else {
					//FGL 20241227 -Das muss ggfs. fuer andere Typen mit ...else if ...erweitert werden
					if(obj==null || obj instanceof String) {
						btemp = StringZZZ.isEmpty(obj.toString());
					    if(!btemp) break main;
					} else {
						ExceptionZZZ ez = new ExceptionZZZ("Objekt with this type not handled yet: '" + obj.getClass().getName() +"'", iERROR_PARAMETER_VALUE, VectorUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
			}//end for
			
			bReturn = true;
		}//end main:
		return bReturn;		
	}
}//end class
