package basic.zKernel.status;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectInterfaceZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**siehe EnumAvailableHelperZZZ
 *  
 * 
 * @author Fritz Lindhauer, 27.03.2024, 19:45:13
 * 
 */
public class StatusLocalAvailableHelperZZZ implements IConstantZZZ{	
	public static final String sENUM_NAME = "STATUSLOCAL";
	
	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Object objForClass, String sStatusName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sStatusName, true);
	}

	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Object objForClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sStatusName, bScanInterfaceImmidiate);
	}

	private static IEnumSetMappedStatusZZZ searchEnumMappedByName_(Object objForClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sStatusName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "StatusString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class objClass = objForClass.getClass();
			objReturn = StatusLocalAvailableHelperZZZ.searchEnumMappedByName(objClass, sStatusName, bScanInterfaceImmidiate);
		
		}//end main:
		return objReturn;		
	}
	
	//+++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Class objClass, String sStatusName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objClass, sStatusName, true);		
	}
	
	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Class objClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objClass, sStatusName, bScanInterfaceImmidiate);		
	}
	
	private static IEnumSetMappedStatusZZZ searchEnumMappedByName_(Class objClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			if(objClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sStatusName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "StatusString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			HashMap<String,IStatusBooleanMessageZZZ> hmStatus = StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessageZZZ(objClass, bScanInterfaceImmidiate);
			if(hmStatus==null)break main;
			
			IStatusBooleanMessageZZZ objBooleanMessage = hmStatus.get(sStatusName);
			if(objBooleanMessage==null) break main;
			
			objReturn = objBooleanMessage.getEnumObject();
		
		}//end main:
		return objReturn;		
	}
	
	
	//+++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++
	public static boolean proofOnChange(HashMap<String,Boolean>hmStatusLocal, String sStatusName, boolean bValue) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.proofOnChange(hmStatusLocal, sStatusName, bValue);
	}
	
	
	public static boolean proofExists(Class<?> cls, String sStatusName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sStatusName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "StatusString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
					
			//Die Klasse selbst und alle Elternklassen, sowie deren Interface. Achtung, Rekursion wird darin verwendet!
			String[] saFlagAvailable = StatusLocalAvailableHelperZZZ.search(cls);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
			if(saFlagAvailable!=null) {
				if(StringArrayZZZ.containsIgnoreCase(saFlagAvailable, sStatusName)) {
					bReturn = true;	
					break main;
				}
			}												
		}//end main:
		return bReturn;
	}
			
	public static boolean proofDirectExists(Class<?> cls, String sStatusName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sStatusName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "StatusString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			//NUR die Klasse selbst
			String[] saStatusAvailable = StatusLocalAvailableHelperZZZ.searchDirect(cls);			
			if(saStatusAvailable!=null) {
				if(StringArrayZZZ.contains(saStatusAvailable, sStatusName)) {
					bReturn = true;	
					break main;
				}
			}						
		}//end main:
		return bReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++
	public static ArrayList<String> searchListDirect(Class cls)  throws ExceptionZZZ {
		return searchListDirect_(cls, true, true);
	}
	
	public static ArrayList<String> searchListDirect(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchListDirect_(cls, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<String> searchListDirect(Class cls, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		return searchListDirect_(cls, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	}
	
	private static ArrayList<String> searchListDirect_(Class cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = EnumAvailableHelperZZZ.searchListDirect(cls, StatusLocalAvailableHelperZZZ.sENUM_NAME, bScanInterfaceImmediate,bScanSuperclassImmediate);
		return listasReturn;
	}
	
		
	//++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect(Class cls)  throws ExceptionZZZ {
		return searchEnumMappedListDirect_(cls, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect(Class cls, boolean bScanInterfaceImmediate)  throws ExceptionZZZ {
		return searchEnumMappedListDirect_(cls, bScanInterfaceImmediate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect_(Class cls, boolean bScanInterfaceImmediate)  throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
			ArrayList<IEnumSetMappedZZZ> listae = EnumAvailableHelperZZZ.searchEnumMappedListDirect(cls, StatusLocalAvailableHelperZZZ.sENUM_NAME, bScanInterfaceImmediate);
			if(listae==null)break main;
			
			listaeReturn = ArrayListZZZ.toEnumMappedStatusArrayListByMapped(listae);
		}//end main:
		return listaeReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList(Class cls, int iGroupId)  throws ExceptionZZZ {
		return searchForGroupList_(cls, iGroupId, true, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList(Class cls, int iGroupId, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchForGroupList_(cls, iGroupId, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList(Class cls, int iGroupId, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		return searchForGroupList_(cls, iGroupId, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList_(Class cls, int iGroupId, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		if(iGroupId<=-1)break main;
		
		//1. von der Classe selbst implementiert		
		IEnumSetMappedStatusZZZ[] enuma = StatusLocalAvailableHelperZZZ.searchEnumMapped(cls,bScanInterfaceImmediate, false);

		ArrayList<IEnumSetMappedStatusZZZ> listaeByDirect = null;
		if(!ArrayUtilZZZ.isEmpty(enuma)) {					
			for(IEnumSetMappedStatusZZZ objEnum : enuma) {
				int iGroupIdByEnum = objEnum.getStatusGroupId();
				if(iGroupIdByEnum == iGroupId) {
					if(ArrayListZZZ.isEmpty(listaeByDirect)) listaeByDirect = new ArrayList<IEnumSetMappedStatusZZZ>();
					if(!listaeByDirect.contains(objEnum)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listaeByDirect.add(objEnum);
					}
				}
			}			
		}
				
		//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
		//          Es fehlen hier die Interfaces extends Interface, usw.
		
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<IEnumSetMappedStatusZZZ> listaeByInterface = null;
		if(bScanInterfaceImmediate) {
			ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);			
			for(Class<?> objclsByInterface : listaInterfaceSuper) {
						
				IEnumSetMappedStatusZZZ[] enumaByInterface = null;
				enumaByInterface = searchEnumMapped(objclsByInterface, false, false);
				
				ArrayList<IEnumSetMappedStatusZZZ> listaeByInterfaceTemp = null;
				if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {
					listaeByInterfaceTemp = new ArrayList<IEnumSetMappedStatusZZZ>();
					for(IEnumSetMappedStatusZZZ objEnum : enumaByInterface) {					
						if(!listaeByInterfaceTemp.contains(objEnum)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listaeByInterfaceTemp.add(objEnum);
						}
					}
					if(!ArrayListZZZ.isEmpty(listaeByInterfaceTemp)) {
						if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusZZZ>();				
						listaeByInterface.addAll(listaeByInterfaceTemp);
					}
				}
			}
		}
		
		//3. Hole die Elternklassen.
		ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuper = null;	
		if(bScanSuperclassImmediate) {
			ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		
			for(Class<?> objclsSuper : listaClassSuper) {
				
				//!!!Rekursion
				ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuperTemp=StatusLocalAvailableHelperZZZ.searchForGroupList(objclsSuper, iGroupId, bScanInterfaceImmediate, bScanSuperclassImmediate);			
				listaeByClassSuper = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListZZZ.join(listaeByClassSuper, listaeByClassSuperTemp);
			}	
		}
		
		if(!ArrayListZZZ.isEmpty(listaeByDirect)) {		
			if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
			listaeReturn.addAll(listaeByDirect);
		}
		
		if(!ArrayListZZZ.isEmpty(listaeByInterface)) {
			if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
			listaeReturn.addAll(listaeByInterface);
		}
		
		if(!ArrayListZZZ.isEmpty(listaeByClassSuper)) {
			if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
			listaeReturn.addAll(listaeByClassSuper);
		}
		
		listaeReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListZZZ.unique(listaeReturn);		
	}//end main:
	return listaeReturn;
	}
	
	
	//#################################
	//#################################
	
	
	

	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
//	public static IEnumSetMappedStatusZZZ[] searchEnumMappedStatus(Class<?> cls)  throws ExceptionZZZ {
//		return searchEnumMappedStatus_(cls, true);
//	}
//	
//	public static IEnumSetMappedStatusZZZ[] searchEnumMappedStatus(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
//		return searchEnumMappedStatus_(cls, bScanInterfaceImmidiate);
//	}
//		
//	private static IEnumSetMappedStatusZZZ[] searchEnumMappedStatus_(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {	
//		IEnumSetMappedStatusZZZ[] objaeReturn = null;
//		main:{
//		
//		ArrayList<IEnumSetMappedStatusZZZ>listaeReturn=StatusLocalAvailableHelperZZZ.searchEnumMappedListInherited(cls, bScanInterfaceImmidiate);
//		if(listaeReturn==null)break main;
//		
//		//IEnumSetMappedZZZ[] objae = EnumSetMappedUtilZZZ.toEnumMappedArray(listaeReturn);
//		objaeReturn = ArrayListZZZ.toEnumMappedStatusArrayByMapped(listaeReturn);
//	}//end main:
//	return objaeReturn;
//	}
	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedStatusList(IStatusLocalMessageUserZZZ objForClass)  throws ExceptionZZZ {
		return searchEnumMappedStatusList_(objForClass, true, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedStatusList(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedStatusList_(objForClass, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedStatusList(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		return searchEnumMappedStatusList_(objForClass, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedStatusList_(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {		
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class objClass = objForClass.getClass();
			listaeReturn = StatusLocalAvailableHelperZZZ.searchEnumMappedList(objClass,bScanInterfaceImmediate, bScanSuperclassImmediate);
		
			}//end main:
	return listaeReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(Class<?> cls)  throws ExceptionZZZ {
		return searchEnumMappedList_(cls, true, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(Class<?> cls, boolean bScanInterfaceImmediate)  throws ExceptionZZZ {
		return searchEnumMappedList_(cls, bScanInterfaceImmediate, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(Class<?> cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		return searchEnumMappedList_(cls, bScanInterfaceImmediate, bScanSuperclassImmediate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList_(Class<?> cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
			ArrayList<IEnumSetMappedZZZ>listae = EnumAvailableHelperZZZ.searchEnumMappedList(cls, StatusLocalAvailableHelperZZZ.sENUM_NAME, bScanInterfaceImmediate, bScanSuperclassImmediate);
			if(listae==null)break main;
			
			listaeReturn = ArrayListZZZ.toEnumMappedStatusArrayListByMapped(listae);
	}//end main:
	return listaeReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++
	public static ArrayList<String> searchList(Class<?> cls) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchList_(cls, true, true);
	}
	
	public static ArrayList<String> searchList(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchList_(cls, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<String> searchList(Class<?> cls, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchList_(cls, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	}
	
	private static ArrayList<String> searchList_(Class<?> cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		ArrayList<String> listasReturn = EnumAvailableHelperZZZ.searchList(cls, StatusLocalAvailableHelperZZZ.sENUM_NAME, bScanInterfaceImmediate, bScanSuperclassImmediate);
		return listasReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++
	public static String[] search(Class<?> cls) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.search_(cls, true, true);	
	}
	
	public static String[] search(Class<?> cls, boolean bScanInterfaceImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.search_(cls, bScanInterfaceImmediate, true);	
	}
	
	public static String[] search(Class<?> cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.search_(cls, bScanInterfaceImmediate, bScanSuperclassImmediate);	
	}
	
	private static String[] search_(Class<?> cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}

		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.searchList(cls,bScanInterfaceImmediate, bScanSuperclassImmediate);
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	//+++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++

	public static String[] searchDirect(Class<?> cls) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchDirect_(cls, true);	
	}
	
	public static String[] searchDirect(Class<?> cls, boolean bScanInterfaceImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchDirect_(cls, bScanInterfaceImmediate);	
	}
		
	private static String[] searchDirect_(Class<?> cls, boolean bScanInterfaceImmediate)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.searchListDirect(cls, bScanInterfaceImmediate);			
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	//++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++
//	public static String[] searchInherited(Class<?> cls) throws ExceptionZZZ {
//		return StatusLocalAvailableHelperZZZ.searchInherited_(cls, true, true);	
//	}
//
//	public static String[] searchInherited(Class<?> cls, boolean bScanInterfaceImmediate) throws ExceptionZZZ {
//		return StatusLocalAvailableHelperZZZ.searchInherited_(cls, bScanInterfaceImmediate, true);	
//	}
//
//	public static String[] searchInherited(Class<?> cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
//		return StatusLocalAvailableHelperZZZ.searchInherited_(cls, bScanInterfaceImmediate, bScanSuperclassImmediate);	
//	}
//	
//	private static String[] searchInherited_(Class<?> cls, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
//		String[] saReturn = null;
//		main:{		
//			ArrayList<String> listas = StatusLocalAvailableHelperZZZ.searchList(cls, bScanInterfaceImmediate, bScanSuperclassImmediate);					
//			saReturn = ArrayListZZZ.toStringArray(listas);
//		}//end main:
//		return saReturn;
//	}
	
	//+++++++++++++++++++++++++++++
	//+++ Hole die Status-Strings fuer eine bestimmte StatusGroupId
	//+++++++++++++++++++++++++++++
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupListDirect(Class<?> cls, int iGroupId) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.searchForGroupListDirect_(cls, iGroupId, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupListDirect(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.searchForGroupListDirect_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupListDirect_(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
			if(iGroupId<=-1)break main;
			
			boolean bLocal = true;						
			IEnumSetMappedStatusZZZ[] enuma = null;
			if(bLocal) {
				enuma = searchEnumMapped(cls, bScanInterfaceImmidiate);
			}else {
				//enuma = getEnumFlagZ(cls);
			}
					
			
			ArrayList<IEnumSetMappedStatusZZZ> listaeByDirect = null;
			if(!ArrayUtilZZZ.isEmpty(enuma)) {
				for(IEnumSetMappedStatusZZZ objEnum : enuma) {					
					int iEnumGroupId = objEnum.getStatusGroupId();
					if(iEnumGroupId==iGroupId) {
						if(ArrayListZZZ.isEmpty(listaeByDirect)) listaeByDirect = new ArrayList<IEnumSetMappedStatusZZZ>();
						if(!listaeByDirect.contains(objEnum)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listaeByDirect.add(objEnum);
						}
					}
				}			
			}	
			
			//2. allen Interfaces der Klasse, auch den erbenden implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaeByInterface = null;
			if(bScanInterfaceImmidiate) {
				ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);				
				for(Class<?> objclsByInterface : listaClassInterface) {
					IEnumSetMappedStatusZZZ[] enumaByInterface = searchEnumMapped(objclsByInterface, false);//false, weil ja eh schon nach Interfaces-Klassen gesucht wird
					if(enumaByInterface!=null) {			
						for(IEnumSetMappedStatusZZZ objEnum : enumaByInterface) {
							int iGroupIdByEnum = objEnum.getStatusGroupId();
							if(iGroupIdByEnum == iGroupId) {
								if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusZZZ>();
								if(!listaeByInterface.contains(objEnum)) {
									//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
									listaeByInterface.add(objEnum);
								}
							}
						}			
					}
				}
			}
			
			//3. von den Elternklassen der Klasse implementiert
			//... eben nicht, da nur direkt gesucht wird ......
			
			
			if(!ArrayListZZZ.isEmpty(listaeByDirect)) {		
				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
				listaeReturn.addAll(listaeByDirect);
			}
			
			if(!ArrayListZZZ.isEmpty(listaeByInterface)) {
				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
				listaeReturn.addAll(listaeByInterface);
			}
//			
//			if(!ArrayListZZZ.isEmpty(listasByClassSuper)) {
//				if(listasReturn==null) listasReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//				listasReturn.addAll(listasByClassSuper);
//			}
			
			listaeReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListZZZ.unique(listaeReturn);
		}//end main:
		return listaeReturn;
	}	

	
	//+++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++
//	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList(Class<?> cls, int iGroupId) throws ExceptionZZZ{
//		return StatusLocalAvailableHelperZZZ.searchForGroupList_(cls, iGroupId, true);
//	}
//	
//	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
//		return StatusLocalAvailableHelperZZZ.searchForGroupList_(cls, iGroupId, bScanInterfaceImmidiate);
//	}
//	
//	private static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList_(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
//		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//		main:{
//			if(iGroupId<=-1) break main;
//			
//			//1. von der Classe selbst implementiert
//			ArrayList<IEnumSetMappedStatusZZZ> listaeByDirect = StatusLocalAvailableHelperZZZ.searchForGroupListDirect(cls, iGroupId, bScanInterfaceImmidiate);
//					
//			//2. allen Interfaces der Klasse, auch den erbenden implementiert
//			ArrayList<IEnumSetMappedStatusZZZ> listaeByInterface = null;
//			if(bScanInterfaceImmidiate) {
//				ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
//				ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
//				for(Class<?> objclsByInterface : listaClassInterface) {
//					IEnumSetMappedStatusZZZ[] enumaByInterface = searchEnumMapped(objclsByInterface, false);//false, weil ja eh schon nach Interfaces-Klassen gesucht wird
//					if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {
//						ArrayList<IEnumSetMappedStatusZZZ> listaeByInterfaceTemp = new ArrayList<IEnumSetMappedStatusZZZ>();
//						for(IEnumSetMappedStatusZZZ objEnum : enumaByInterface) {
//							int iGroupIdByEnum = objEnum.getStatusGroupId();
//							if(iGroupIdByEnum == iGroupId) {
//								if(!listaeByInterfaceTemp.contains(objEnum)) {
//									//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//									listaeByInterfaceTemp.add(objEnum);
//								}
//							}
//						}
//						if(!ArrayListZZZ.isEmpty(listaeByInterfaceTemp)) {
//							if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusZZZ>();				
//							listaeByInterface.addAll(listaeByInterfaceTemp);
//						}
//					}
//				}
//			}
//			
//			//3. von den Elternklassen der Klasse implementiert
//			ArrayList<IEnumSetMappedStatusZZZ> listaeByParent = null;		
//			ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
//			for(Class objcls : listaobjClass) {
//				//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.				
//				//Darum: Von der Vererbungshierarchie des Interface implementiert.
//				ArrayList<IEnumSetMappedStatusZZZ> listaeByParentTemp = StatusLocalAvailableHelperZZZ.searchForGroupListInherited(objcls, iGroupId, bScanInterfaceImmidiate);
//				if(!ArrayListZZZ.isEmpty(listaeByParentTemp)) {
//					if(ArrayListZZZ.isEmpty(listaeByParent)) listaeByParent = new ArrayList<IEnumSetMappedStatusZZZ>();				
//					listaeByParent.addAll(listaeByParentTemp);
//				}
//			}
//						
//			if(!ArrayListZZZ.isEmpty(listaeByDirect)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//				listaeReturn.addAll(listaeByDirect);
//			}
//			
//			if(!ArrayListZZZ.isEmpty(listaeByInterface)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//				listaeReturn.addAll(listaeByInterface);
//			}
//			
//			if(!ArrayListZZZ.isEmpty(listaeByParent)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//				listaeReturn.addAll(listaeByParent);
//			}
//
//			//listasReturn = (ArrayList<Collection<? extends Enum<?>>>) ArrayListZZZ.unique(listasReturn);
//			listaeReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListZZZ.unique(listaeReturn);
//		}//end main:
//		return listaeReturn;
//	}
	
	//++++++++++++++++++++++++++++++++++++++++
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IEnmSetMappdedStatusZZZ>. 
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IEnumSetMappedStatusZZZ> searchHashMapEnumMapped(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchHashMapEnumMapped(classToCheck, true);
	}
		
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IEnmSetMappdedStatusZZZ>. 
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IEnumSetMappedStatusZZZ> searchHashMapEnumMapped(Class<?> classToCheck, boolean bScanInterface) throws ExceptionZZZ {
		HashMap<String, IEnumSetMappedStatusZZZ> hmReturn = null;
		main:{
			IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalAvailableHelperZZZ.searchEnumMapped(classToCheck, bScanInterface);
			if(enumaMappedStatus==null) break main;
			
			hmReturn = new HashMap<String, IEnumSetMappedStatusZZZ>();
			if(ArrayUtilZZZ.isEmpty(enumaMappedStatus)) break main;
			
			for(IEnumSetMappedStatusZZZ objEnumMapped : enumaMappedStatus) {
				String sEnum = objEnumMapped.getName();
				hmReturn.put(sEnum, objEnumMapped);
			}		
		}//end main:
		return hmReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++
		/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IStatusBooleanMessageZZZ>.
		 *  Merke: Da der Wert hier nicht ermittelt werden kann (schliessliche kommt nur eine Klasse hier als Eingabeparameter rein und kein konkretes Objekt)
		 *         wird der Wert mit "false" initialisiert 
		 * @param classToCheck
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
		 * @param E 
		 */
		//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
		public static HashMap<String, IStatusBooleanMessageZZZ> searchHashMapBooleanMessageZZZ(Class<?> classToCheck) throws ExceptionZZZ {
			return StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessageZZZ_(classToCheck, true);
		}
		
		public static HashMap<String, IStatusBooleanMessageZZZ> searchHashMapBooleanMessageZZZ(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
			return StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessageZZZ_(classToCheck, bScanInterfaceImmidiate);
		}
	
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IStatusBooleanMessageZZZ>.
	 *  Merke: Da der Wert hier nicht ermittelt werden kann (schliessliche kommt nur eine Klasse hier als Eingabeparameter rein und kein konkretes Objekt)
	 *         wird der Wert mit "false" initialisiert.
	 *         Dito kann eine Message auch nicht gesetzt werden. 
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	private static HashMap<String, IStatusBooleanMessageZZZ> searchHashMapBooleanMessageZZZ_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		HashMap<String, IStatusBooleanMessageZZZ> hmReturn = null;
		main:{
			//Das hat nur direkte Klassen und Interfaces
			//IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalHelperZZZ.getEnumStatusLocalMapped(classToCheck, bScanInterfaceImmidiate);
			
			//IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalHelperZZZ.getStatusLocalEnumMapped(classToCheck, bScanInterfaceImmidiate);			


			//Hier werden auch Vererbte Klassen und Interfaces betrachtet.
			IEnumSetMappedStatusZZZ[] enumaMappedStatus = StatusLocalAvailableHelperZZZ.searchEnumMapped(classToCheck, bScanInterfaceImmidiate);
			if(enumaMappedStatus==null) break main;			
			
			hmReturn = new HashMap<String, IStatusBooleanMessageZZZ>();
			if(ArrayUtilZZZ.isEmpty(enumaMappedStatus)) break main;
			
			for(IEnumSetMappedStatusZZZ objEnumMapped : enumaMappedStatus) {
				String sEnum = objEnumMapped.getName();
				
				IStatusBooleanMessageZZZ objBooleanMessage = new StatusBooleanMessageZZZ(objEnumMapped,false);
				hmReturn.put(sEnum, objBooleanMessage);
			}
		}
		return hmReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IStatusBooleanMessageZZZ>.
	 *  Merke1: Die Werte der BooleanMessage stmmen aus dem Objekt selbst.
	 *  Merke2: Trotzdem sind das ALLE moeglichen/definierten Statuswerte. 
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IStatusBooleanMessageZZZ> searchHashMapBooleanMessage(IStatusLocalMessageUserZZZ objToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessage(objToCheck, true);
	}
	
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IStatusBooleanMessageZZZ>.
	 *  Merke1: Die Werte der BooleanMessage stammen aus dem Objekt selbst.
	 *  Merke2: Trotzdem sind das ALLE moeglichen/definierten Statuswerte.
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IStatusBooleanMessageZZZ> searchHashMapBooleanMessage(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterface) throws ExceptionZZZ {
		HashMap<String, IStatusBooleanMessageZZZ> hmReturn = null;
		main:{
			if(objToCheck==null)break main;
			
			Class<?> objClassToCheck = objToCheck.getClass();
			IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalAvailableHelperZZZ.searchEnumMapped(objClassToCheck, bScanInterface);
			if(enumaMappedStatus==null) break main;

			hmReturn = new HashMap<String, IStatusBooleanMessageZZZ>();
			if(ArrayUtilZZZ.isEmpty(enumaMappedStatus)) break main;
			
			for(IEnumSetMappedStatusZZZ objEnumMapped : enumaMappedStatus) {
				String sEnum = objEnumMapped.getName();
				
				boolean bValue = objToCheck.getStatusLocal(sEnum);								
				String sMessage = objToCheck.getStatusLocalMessage(sEnum); //Die "Besondere" Message aus der neu eingeführten HashMap holen.				
				
				IStatusBooleanMessageZZZ objBooleanMessage = new StatusBooleanMessageZZZ(objEnumMapped,bValue,sMessage);
				hmReturn.put(sEnum, objBooleanMessage);
			}
		}
		return hmReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++
	/** Rueckgabewert ist eine ArrayList in Form <IStatusBooleanMessageZZZ>.
	 *  Merke1: Die Werte der BooleanMessage stammen aus dem Objekt selbst.
	 *  Merke2: Trotzdem sind das ALLE moeglichen/definierten Statuswerte.
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	 public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(IStatusLocalMessageUserZZZ objToCheck) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMappedList_(objToCheck, true);
	 }
	 
	 /** Rueckgabewert ist eine ArrayList in Form <IStatusBooleanMessageZZZ>.
		 *  Merke1: Die Werte der BooleanMessage stammen aus dem Objekt selbst.
		 *  Merke2: Trotzdem sind das ALLE moeglichen/definierten Statuswerte.
		 * @param classToCheck
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
		 * @param E 
		 */
	 public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMappedList_(objToCheck,bScanInterfaceImmidiate);
	 }

		
		
	 /** Rueckgabewert ist eine ArrayList in Form <IStatusBooleanMessageZZZ>.
		 *  Merke1: Die Werte der BooleanMessage stammen aus dem Objekt selbst.
		 *  Merke2: Trotzdem sind das ALLE moeglichen/definierten Statuswerte.
		 * @param classToCheck
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
		 * @param E 
		 */
	 //public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumList(Class cls) throws ExceptionZZZ {
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList_(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaReturn = null;
		main:{
			if(objToCheck==null)break main;
			
			Class<?> objClassToCheck = objToCheck.getClass();
			IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalAvailableHelperZZZ.searchEnumMapped(objClassToCheck, bScanInterfaceImmidiate);
			if(enumaMappedStatus==null) break main;
			
			listaReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayUtilZZZ.toArrayList(enumaMappedStatus);
		}
		return listaReturn;
	}


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String[] searchForGroup(Class<?> cls, int iGroupId) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.searchForGroup_(cls, iGroupId, true);
	}
	
	public static String[] searchForGroup(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.searchForGroup_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static String[] searchForGroup_(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName()); 
				 throw ez;
			}
			if(iGroupId<=-1)break main;
	
			ArrayList<IEnumSetMappedStatusZZZ> lista = searchForGroupList(cls, iGroupId, bScanInterfaceImmidiate);
			ArrayList<String>listasReturn = null;
			if(!ArrayListZZZ.isEmpty(lista)) {
				listasReturn = new ArrayList<String>();
				for(IEnumSetMappedStatusZZZ objEnum : lista) {
					
					if(objEnum!=null) {
						String sEnum = objEnum.getName();
						listasReturn.add(sEnum);
					}
				}
			}
			listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
			saReturn = ArrayListZZZ.toStringArray(listasReturn);
		}//end main:
		return saReturn;
	}
	
	//+++++++++++++++++++++++++
	//+++++++++++++++++++++++++
	public static <E extends IEnumSetMappedStatusZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumPropertyName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumPropertyName, true, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumPropertyName, boolean bScanInterfaceImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumPropertyName, bScanInterfaceImmediate, true);
	}

	public static <E extends IEnumSetMappedStatusZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumPropertyName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumPropertyName, bScanInterfaceImmediate, bScanSuperclassImmediate);
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E searchEnumMapped_(Class<?> classToCheck, String sEnumPropertyName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			IEnumSetMappedZZZ enumReturnTemp = EnumAvailableHelperZZZ.searchEnumMapped(classToCheck, StatusLocalAvailableHelperZZZ.sENUM_NAME, sEnumPropertyName, bScanInterfaceImmediate, bScanSuperclassImmediate);
			enumReturn = (E) enumReturnTemp;
		}//end main:
		return enumReturn;
	}

	//+++++++++++++++++++++++++
	//+++++++++++++++++++++++++
	public static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMapped(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, true, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMapped(Class<?> classToCheck, boolean bScanInterfaceImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, bScanInterfaceImmediate, true);
	}

	public static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMapped(Class<?> classToCheck, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, bScanInterfaceImmediate, bScanSuperclassImmediate);
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMapped_(Class<?> classToCheck, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		E[] enumReturn = null;
		main:{
			IEnumSetMappedZZZ[] enumReturnTemp = EnumAvailableHelperZZZ.searchEnumMapped(classToCheck, StatusLocalAvailableHelperZZZ.sENUM_NAME, bScanInterfaceImmediate, bScanSuperclassImmediate);
			enumReturn = EnumSetMappedStatusUtilZZZ.toEnumSetMappedStatusArray(enumReturnTemp);
		}//end main:
		return enumReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ[] searchEnumMapped(IStatusLocalMessageUserZZZ objToCheck) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMapped_(objToCheck, true, true);
	 }

	public static IEnumSetMappedStatusZZZ[] searchEnumMapped(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMapped_(objToCheck, bScanInterfaceImmidiate, true);
	 }
	
	public static IEnumSetMappedStatusZZZ[] searchEnumMapped(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMapped_(objToCheck, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	 }
	
	 /** Rueckgabewert ist eine ArrayList in Form <IStatusBooleanMessageZZZ>.
	 *  Merke1: Die Werte der BooleanMessage stammen aus dem Objekt selbst.
	 *  Merke2: Trotzdem sind das ALLE moeglichen/definierten Statuswerte.
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	 //public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumList(Class cls) throws ExceptionZZZ {
	private static IEnumSetMappedStatusZZZ[] searchEnumMapped_(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ[] objaeReturn = null;
		main:{
			if(objToCheck==null)break main;
			
			//Irgendwie ein Problem der Namensgebung für die Methoden wenn nur eine anders benannte Methode aufgerufen wird
			Class<?> objClassToCheck = objToCheck.getClass();
			objaeReturn = StatusLocalAvailableHelperZZZ.searchEnumMapped(objClassToCheck, bScanInterfaceImmediate, bScanSuperclassImmediate);
		}
		return objaeReturn;
	}

	//++++++++++++++++++++++++
	//++++++++++++++++++++++++
	public static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMappedDirect(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMappedDirect_(classToCheck, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMappedDirect(Class<?> classToCheck, boolean bScannInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMappedDirect_(classToCheck, bScannInterfaceImmidiate);
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMappedDirect_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			
			//IEnumSetMappedZZZ[] enumaReturnTemp = StatusLocalAvailableHelperZZZ.searchEnumMapped(classToCheck, bScanInterfaceImmidiate, false);
			enumaReturn = StatusLocalAvailableHelperZZZ.searchEnumMapped(classToCheck, bScanInterfaceImmidiate, false);
			
			//Merke: Ein einfaches Casten fuehrt zu einer CAST Exception
			//enumaReturn = (E[]) enumaReturnTemp;
			
			//Darum in einer Schleife die Einzelwerte casten
			//enumaReturn = EnumSetMappedStatusUtilZZZ.toEnumSetMappedStatusArray(enumaReturnTemp);
		}
		return enumaReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
//	public static IEnumSetMappedStatusZZZ[] searchEnumMapped(IStatusLocalMessageUserZZZ objForClass)  throws ExceptionZZZ {
//		return searchEnumMapped_(objForClass, true);
//	}
//	
//	public static IEnumSetMappedStatusZZZ[] searchEnumMapped(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
//		return searchEnumMapped_(objForClass, bScanInterfaceImmidiate);
//	}
	
//	private static IEnumSetMappedStatusZZZ[] searchEnumMapped_(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {	
//		IEnumSetMappedStatusZZZ[] objaeReturn = null;
//		main:{
//			if(objForClass==null) {
//				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
//				 throw ez;
//			}
//			
//			Class objClass = objForClass.getClass();
//			objaeReturn = StatusLocalAvailableHelperZZZ.searchEnumMapped(objClass);
//		
//		}//end main:
//	return objaeReturn;
//	}

							
	//++++++++++++++++++++++++++++++
	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnum_(classToCheck, true, true);
	}

	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnum_(classToCheck, bScanInterfaceImmidiate, true);
	}

	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnum_(classToCheck, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	}
	
	private static <E extends Enum> E[] searchEnum_(Class<?> classToCheck, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			//String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			String sEnumName = StatusLocalAvailableHelperZZZ.sENUM_NAME;
			enumaReturn = EnumAvailableHelperZZZ.searchEnum(classToCheck, sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate );
		}//end main:
		return enumaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, true);
	}
	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> E searchEnum_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {		
		E enumReturn = null;
		main:{
			if(classToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + StatusLocalAvailableHelperZZZ.sENUM_NAME;
						
			//if(classToCheck.isInterface()) {
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				//z.B. das enum selbst gibt es so:
				//Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;))
		    	//Object[] objEnum = objClassFromInterface.getEnumConstants();
		    	//IEnumSetMappedStatusZZZ[] enumaByInterface3 = (IEnumSetMappedStatusZZZ[])objEnum; 
		  		
				Object[] objaEnum = classToCheck.getEnumConstants();
				for(Object objEnum : objaEnum) {
					Enum e = (E) objEnum;
					if(e.name().equals(sEnumName)) {
						enumReturn = (E) e;										
						break main;
					}					
				} 	
				
			}else {
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);			
				for(Class objClass : listaClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
						Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumName);	
						if(e!=null) {
							enumReturn = (E) e;										
							break main;
						}
					 }
				}
			}
			
			//Falls noch nix gefunden wurde... ggfs. die Interfaces scannen
			if(bScanInterfaceImmidiate) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces	
				//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);
				ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);				
				for(Class objClassInterface : listaInterfaceByClass) {
					
					//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
					ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
					for(Class objClass : listaClassByInterface) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
							Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumName);	
							if(e!=null) {
								enumReturn = (E) e;										
								break main;
							}
						 }
					}//end for												
				}//end for	
			}//end if
		}//end main:
		return enumReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
	public static <E extends Enum> ArrayList<E> searchEnumList(Object objToCheck) throws ExceptionZZZ {
		ArrayList<E> listaReturn = null;
		main:{
			if(objToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName()); 
				 throw ez;
			}
		
			Class<?>classToCheck = objToCheck.getClass();				
			listaReturn = StatusLocalAvailableHelperZZZ.searchEnumList(classToCheck);
		}//end main:
		return listaReturn;
	}

	public static <E extends Enum> ArrayList<E> searchEnumList(Object objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<E> listaReturn = null;
		main:{
			if(objToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName()); 
				 throw ez;
			}
		
			Class<?>classToCheck = objToCheck.getClass();				
			listaReturn = StatusLocalAvailableHelperZZZ.searchEnumList(classToCheck,bScanInterfaceImmidiate);
		}//end main:
		return listaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumList_(classToCheck,true);
	}

	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumList_(classToCheck,bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> ArrayList<E> searchEnumList_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<E> listaReturn = null;
		main:{
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + StatusLocalAvailableHelperZZZ.sENUM_NAME;
			
			ArrayList<E> listaeReturnByClass = null;
			//if(classToCheck.isInterface()) {
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				//z.B. das enum selbst gibt es so:
				//Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;))
		    	//Object[] objEnum = objClassFromInterface.getEnumConstants();
		    	//IEnumSetMappedStatusZZZ[] enumaByInterface3 = (IEnumSetMappedStatusZZZ[])objEnum; 
		  		    	 				
				Object[] objaEnum = classToCheck.getEnumConstants();
				if(!ArrayUtilZZZ.isEmpty(objaEnum)) {
					listaeReturnByClass = new ArrayList<E>();
					for(Object obj : objaEnum) {
						Enum e = (Enum) obj;
						listaeReturnByClass.add((E) e);
					} 
				}
			}else {
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
				if(!ArrayListZZZ.isEmpty(listaClass)) {
					listaeReturnByClass = new ArrayList<E>();
					for(Class objClass : listaClass) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
							Object[] obja = objClass.getEnumConstants();
							
							for(Object obj : obja) {
								Enum e = (Enum) obj;
								listaeReturnByClass.add((E) e);
							}
						}
					}	
				}
			}
			
			ArrayList<E>listaeReturnByInterface=null;
			if(bScanInterfaceImmidiate) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces	
				//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);
				ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
			
				if(!ArrayListZZZ.isEmpty(listaInterfaceByClass)) {
					listaeReturnByInterface = new ArrayList<E>();
					for(Class objClassInterface : listaInterfaceByClass) {
						
						//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
						ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
						if(!ArrayListZZZ.isEmpty(listaClassByInterface)) {
							for(Class objClass : listaClassByInterface) {
								String sEnumClass = objClass.getName();				
								if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
									Object[] obja = objClass.getEnumConstants();
									for(Object obj : obja) {
										IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) obj;
										listaeReturnByInterface.add((E) e);
									}
								 }
							}//end for	
						}
					}//end for
					
				}	
			}//end if
			
			//Verbinde beides
			listaReturn = (ArrayList<E>) ArrayListZZZ.join(listaeReturnByClass, listaeReturnByInterface);
			
		}//end main:
		return listaReturn;
	}
}
