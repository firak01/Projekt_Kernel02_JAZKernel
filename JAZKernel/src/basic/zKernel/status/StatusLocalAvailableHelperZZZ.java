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
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumHelperZZZ;
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
			String[] saFlagAvailable = StatusLocalAvailableHelperZZZ.searchInherited(cls);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
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
		return searchListDirect_(cls, true);
	}
	
	public static ArrayList<String> searchListDirect(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchListDirect_(cls, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<String> searchListDirect_(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = searchEnum(cls, bScanInterfaceImmidiate);
		
		ArrayList<String>listasByDirect=null;
		if(!ArrayUtilZZZ.isEmpty(enuma)) {				
			listasByDirect = new ArrayList<String>();
			for(Enum objEnum : enuma) {
				String sEnum = objEnum.name();
				if(!listasByDirect.contains(sEnum)) {
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
					listasByDirect.add(sEnum);
				}
			}			
		}
		
		//2. von den Interfaces der Klasse DIREKT implementiert
		ArrayList<String>listasByInterface=null;
		if(bScanInterfaceImmidiate) {
			Class[] objclsaByInterface = cls.getInterfaces();
			if(!ArrayUtilZZZ.isEmpty(objclsaByInterface)){				
				for(Class objclsByInterface : objclsaByInterface) {
					Enum[] enumaByInterface = searchEnum_(objclsByInterface, false);//false, weil ja die Interfaces eh betrachtet werde sollen

					ArrayList<String> listasByInterfaceTemp = null;
					if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {	
						listasByInterfaceTemp = new ArrayList<String>();
						for(Enum objEnum : enumaByInterface) {
							String sEnum = objEnum.name();
							if(!listasByInterfaceTemp.contains(sEnum)) {
								//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
								listasByInterfaceTemp.add(sEnum);
							}
						}	
					}
					if(!ArrayListZZZ.isEmpty(listasByInterfaceTemp)) {
						if(ArrayListZZZ.isEmpty(listasByInterface)) listasByInterface = new ArrayList<String>();				
						listasByInterface.addAll(listasByInterfaceTemp);
					}
				}								
			}
		}
		
		//3. von den Elternklassen der Klasse implementiert
		//... eben nicht, da direct implementiert
		
		if(!ArrayListZZZ.isEmpty(listasByDirect)) {		
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByDirect);
		}
		
		if(!ArrayListZZZ.isEmpty(listasByInterface)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByInterface);
		}
//		
//		if(!ArrayListZZZ.isEmpty(listasByClassSuper)) {
//			if(listasReturn==null) listasReturn = new ArrayList<String>();
//			listasReturn.addAll(listasByClassSuper);
//		}
		
		listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
	}//end main:
	return listasReturn;
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListDirectAvailable(Class cls)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect(Class cls)  throws ExceptionZZZ {
		return searchEnumMappedListDirect_(cls, true);
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListDirectAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedListDirect_(cls, bScanInterfaceImmidiate);
	}
	
	//private static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListDirectAvailable_(Class cls, boolean bLocal, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect_(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		//ArrayList<Collection<? extends Enum<?>>> listaeReturn = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;

		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = searchEnum_(cls, bScanInterfaceImmidiate);

		ArrayList<IEnumSetMappedStatusZZZ> listaeClass = null;
		if(!ArrayUtilZZZ.isEmpty(enuma)) {
			listaeClass = new ArrayList<IEnumSetMappedStatusZZZ>();
			
//			for(Enum objEnum : enuma) {
//				String sEnum = objEnum.name();
//				if(!listasReturn.contains(sEnum)) {
//					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//					listasReturn.add(sEnum);
//				}
//			}	
			
			for(Enum objEnum : enuma) {
				//TypeCastException: listasReturn.add((Collection<? extends Enum<?>>) objEnum);
				IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) objEnum;
				listaeClass.add(e);
			}
		}
		
		//2. von den Interfaces der Klasse DIREKT implementiert
		ArrayList<IEnumSetMappedStatusZZZ> listaeInterface = new ArrayList<IEnumSetMappedStatusZZZ>();
		if(bScanInterfaceImmidiate) {
			Class[] objclsaByInterface = cls.getInterfaces();
			if(!ArrayUtilZZZ.isEmpty(objclsaByInterface)) {
				for(Class objclsByInterface : objclsaByInterface) {
					Enum[] enumaByInterface = searchEnum_(objclsByInterface, bScanInterfaceImmidiate);

					if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {
						listaeInterface = new ArrayList<IEnumSetMappedStatusZZZ>();
						
		//				for(Enum objEnum : enumaByInterface) {
		//					String sEnum = objEnum.name();
		//					if(!listasReturn.contains(sEnum)) {
		//						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
		//						listasReturn.add(sEnum);
		//					}
		//				}
						
						for(Enum objEnum : enumaByInterface) {
							//listasReturn.add((Collection<? extends Enum<?>>) objEnum);
							IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) objEnum;
							listaeInterface.add(e);
						}
					}
				}
			}
		}
		
		//...eben nicht die Elternklassen holen, wg: "directAvailable"
//		//3. Hole die Elternklassen.
//...................................	

		listaeReturn = ArrayListZZZ.join(listaeClass, listaeInterface);
	}//end main:
	return listaeReturn;
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupListInherited(Class cls, int iGroupId)  throws ExceptionZZZ {
		return searchForGroupListInherited_(cls, iGroupId, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupListInherited(Class cls, int iGroupId, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchForGroupListInherited_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupListInherited_(Class cls, int iGroupId, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		if(iGroupId<=-1)break main;
		
		//1. von der Classe selbst implementiert		
		IEnumSetMappedStatusZZZ[] enuma = searchEnumMapped(cls,bScanInterfaceImmidiate);

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
		if(bScanInterfaceImmidiate) {
			ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);			
			for(Class<?> objclsByInterface : listaInterfaceSuper) {
						
				IEnumSetMappedStatusZZZ[] enumaByInterface = null;
				enumaByInterface = searchEnumMapped(objclsByInterface, bScanInterfaceImmidiate);
				
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
		ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuper = null;		
		for(Class<?> objclsSuper : listaClassSuper) {
			
			//!!!Rekursion
			ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuperTemp=StatusLocalAvailableHelperZZZ.searchForGroupListInherited(objclsSuper, iGroupId, bScanInterfaceImmidiate);			
			listaeByClassSuper = ArrayListZZZ.join(listaeByClassSuper, listaeByClassSuperTemp);
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
	
	public static ArrayList<String> searchListInherited(Class cls)  throws ExceptionZZZ {
		return searchListInherited_(cls, true);
	}
	
	public static ArrayList<String> searchListInherited(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchListInherited_(cls, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<String> searchListInherited_(Class cls, boolean bScanInterfaceImmiediate)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert		
		Enum[] enuma = searchEnum_(cls, bScanInterfaceImmiediate);
		
		ArrayList<String>listasByDirect = null;
		if(!ArrayUtilZZZ.isEmpty(enuma)) {	
			listasByDirect = new ArrayList<String>();
			for(Enum objEnum : enuma) {
				String sEnum = objEnum.name();
				if(!listasByDirect.contains(sEnum)) {
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
					listasByDirect.add(sEnum);
				}
			}			
		}
				
		//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
		//          Es fehlen hier die Interfaces extends Interface, usw.
		
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<String>listasByInterface = null;
		if(bScanInterfaceImmiediate) {
			ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);
			for(Class<?> objclsByInterface : listaInterfaceSuper) {
				
				Enum[] enumaByInterface = null;
				enumaByInterface = searchEnum(objclsByInterface);
								
				ArrayList<String>listasByInterfaceTemp = null;
				if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {
					listasByInterfaceTemp = new ArrayList<String>();
					for(Enum objEnum : enumaByInterface) {
						String sEnum = objEnum.name();
						if(!listasByInterfaceTemp.contains(sEnum)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listasByInterfaceTemp.add(sEnum);
						}
					}			
				}
				if(!ArrayListZZZ.isEmpty(listasByInterfaceTemp)) {
					if(ArrayListZZZ.isEmpty(listasByInterface)) listasByInterface = new ArrayList<String>();				
					listasByInterface.addAll(listasByInterfaceTemp);
				}
			}
		}
		
		//3. Hole die Elternklassen.
		ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		ArrayList<String>listasByClassSuper=null;
		for(Class<?> objclsSuper : listaClassSuper) {
			//!!!Rekursion
			ArrayList<String> listasByClassSuperTemp=StatusLocalAvailableHelperZZZ.searchListInherited(objclsSuper);
			listasByClassSuper = ArrayListZZZ.join(listasByClassSuper, listasByClassSuperTemp);			
		}			
		
		
		if(!ArrayListZZZ.isEmpty(listasByDirect)) {		
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByDirect);
		}
		
		if(!ArrayListZZZ.isEmpty(listasByInterface)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByInterface);
		}
		
		if(!ArrayListZZZ.isEmpty(listasByClassSuper)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByClassSuper);
		}
		
		listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
	}//end main:
	return listasReturn;
	}
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ[] searchEnumMappedInherited(IStatusLocalMessageUserZZZ objForClass)  throws ExceptionZZZ {
		return searchEnumMappedInherited_(objForClass, true);
	}
	
	public static IEnumSetMappedStatusZZZ[] searchEnumMappedInherited(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedInherited_(objForClass, bScanInterfaceImmidiate);
	}
	
	private static IEnumSetMappedStatusZZZ[] searchEnumMappedInherited_(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {	
		IEnumSetMappedStatusZZZ[] objaeReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class objClass = objForClass.getClass();
			objaeReturn = StatusLocalAvailableHelperZZZ.searchEnumMappedInherited(objClass);
		
		}//end main:
	return objaeReturn;
	}


	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ[] searchEnumMappedInherited(Class<?> cls)  throws ExceptionZZZ {
		return searchEnumMappedInherited_(cls, true);
	}
	
	public static IEnumSetMappedStatusZZZ[] searchEnumMappedInherited(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedInherited_(cls, bScanInterfaceImmidiate);
	}
		
	private static IEnumSetMappedStatusZZZ[] searchEnumMappedInherited_(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {	
		IEnumSetMappedStatusZZZ[] objaeReturn = null;
		main:{
		
		ArrayList<IEnumSetMappedStatusZZZ>listaeReturn=StatusLocalAvailableHelperZZZ.searchEnumMappedListInherited(cls, bScanInterfaceImmidiate);
		if(listaeReturn==null)break main;
		
		//IEnumSetMappedZZZ[] objae = EnumSetMappedUtilZZZ.toEnumMappedArray(listaeReturn);
		objaeReturn = ArrayListZZZ.toEnumMappedStatusArrayByMapped(listaeReturn);
	}//end main:
	return objaeReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListInherited(IStatusLocalMessageUserZZZ objForClass)  throws ExceptionZZZ {
		return searchEnumMappedListInherited_(objForClass, true);
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListInherited(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedListInherited_(objForClass, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListInherited_(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {		
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class objClass = objForClass.getClass();
			listaeReturn = StatusLocalAvailableHelperZZZ.searchEnumMappedListInherited(objClass,bScanInterfaceImmidiate);
		
			}//end main:
	return listaeReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListInherited(Class<?> cls)  throws ExceptionZZZ {
		return searchEnumMappedListInherited_(cls, true);
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListInherited(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedListInherited_(cls, bScanInterfaceImmidiate);
	}
	
	
	//private static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable_(Class cls, boolean bLocal, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListInherited_(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		//ArrayList<Collection<? extends Enum<?>>> listaeReturn = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert		
		Enum[] enuma = searchEnum_(cls,bScanInterfaceImmidiate);
		
		ArrayList<IEnumSetMappedStatusZZZ>listaeByDirect=null;
		if(!ArrayUtilZZZ.isEmpty(enuma)) {	
			listaeByDirect = new ArrayList<IEnumSetMappedStatusZZZ>();
			for(Enum objEnum : enuma) {				
				IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) objEnum;				
				if(!listaeByDirect.contains(e)) {
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
					listaeByDirect.add(e);
				}
			}			
		}
		
				
		//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
		//          Es fehlen hier die Interfaces extends Interface, usw.
		
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<IEnumSetMappedStatusZZZ>listaeByInterface=null;
		if(bScanInterfaceImmidiate) {
			ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);
			for(Class<?> objclsByInterface : listaInterfaceSuper) {
						
				Enum[] enumaByInterface = searchEnum_(objclsByInterface,bScanInterfaceImmidiate);
				
				ArrayList<IEnumSetMappedStatusZZZ>listaeByInterfaceTemp=null; 
				if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {			
					listaeByInterfaceTemp = new ArrayList<IEnumSetMappedStatusZZZ>();
										
					for(Enum objEnum : enumaByInterface) {						
						IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) objEnum;
						if(!listaeByInterfaceTemp.contains(e)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listaeByInterfaceTemp.add(e);
						}
					}
				}
				if(!ArrayListZZZ.isEmpty(listaeByInterfaceTemp)) {
					if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusZZZ>();				
					listaeByInterface = ArrayListZZZ.join(listaeByInterface, listaeByInterfaceTemp);
				}
			}
		}
		
		//3. Hole die Elternklassen.
		ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		ArrayList<IEnumSetMappedStatusZZZ>listaeByClassSuper=null;
		for(Class<?> objclsSuper : listaClassSuper) {
			//Rekursion durchfuehren !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//ArrayList<Collection<? extends Enum<?>>> listaStatusByClassSuper = new ArrayList<Collection<? extends Enum<?>>>();
			ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuperTemp = StatusLocalAvailableHelperZZZ.searchEnumMappedListInherited(objclsSuper,bScanInterfaceImmidiate);
			listaeByClassSuper = ArrayListZZZ.join(listaeByClassSuper, listaeByClassSuperTemp);
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
	
	//+++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++
	public static ArrayList<String> searchList(Class<?> cls) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchList_(cls, true);
	}
	
	public static ArrayList<String> searchList(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchList_(cls, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<String> searchList_(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		ArrayList<String> listasByDirect = StatusLocalAvailableHelperZZZ.searchListDirect(cls,bScanInterfaceImmidiate);
				
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<String> listasByInterface = null;
		if(bScanInterfaceImmidiate) {
			ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
			for(Class<?> objclsByInterface : listaClassInterface) {
				Enum[] enumaByInterface = searchEnum_(objclsByInterface, false);//false, weil ja die Interfaces eh betrachtet werde sollen
				ArrayList<String>listasByInterfaceTemp=null;
				if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {	
					listasByInterfaceTemp = new ArrayList<String>();
					for(Enum objEnum : enumaByInterface) {
						String sEnum = objEnum.name();
						if(!listasByInterfaceTemp.contains(sEnum)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listasByInterfaceTemp.add(sEnum);
						}
					}			
				}
				if(!ArrayListZZZ.isEmpty(listasByInterfaceTemp)) {
					if(ArrayListZZZ.isEmpty(listasByInterface)) listasByInterface = new ArrayList<String>();				
					listasByInterface.addAll(listasByInterfaceTemp);
				}
			}
		}
		
		//3. von den Elternklassen der Klasse implementiert
		ArrayList<String> listasByParent = new ArrayList<String>();		
		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
		for(Class objcls : listaobjClass) {
			//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
			//Darum: Von der Vererbungshierarchie des Interface implementiert.
			ArrayList<String> listasByParentTemp = StatusLocalAvailableHelperZZZ.searchListInherited(objcls, bScanInterfaceImmidiate);
			if(!ArrayListZZZ.isEmpty(listasByParentTemp)) {
				if(ArrayListZZZ.isEmpty(listasByParent)) listasByParent = new ArrayList<String>();				
				listasByParent.addAll(listasByParentTemp);
			}			
		}
				
		if(!ArrayListZZZ.isEmpty(listasByDirect)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByDirect);
		}
		
		if(!ArrayListZZZ.isEmpty(listasByInterface)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByInterface);
		}
		
		if(!ArrayListZZZ.isEmpty(listasByParent)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByParent);
		}
		
		listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
	}//end main:
	return listasReturn;
	}
	
	public static String[] search(Class<?> cls) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}

		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.searchList(cls);
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] searchDirect(Class<?> cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.searchListDirect(cls);			
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls)  throws ExceptionZZZ {
	public static String[] searchInherited(Class<?> cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.searchListInherited(cls);					
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] searchEnumInherited(Class<?> cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.searchListInherited(cls);					
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
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
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList(Class<?> cls, int iGroupId) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.searchForGroupList_(cls, iGroupId, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.searchForGroupList_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> searchForGroupList_(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
		main:{
			if(iGroupId<=-1) break main;
			
			//1. von der Classe selbst implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaeByDirect = StatusLocalAvailableHelperZZZ.searchForGroupListDirect(cls, iGroupId, bScanInterfaceImmidiate);
					
			//2. allen Interfaces der Klasse, auch den erbenden implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaeByInterface = null;
			if(bScanInterfaceImmidiate) {
				ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
				for(Class<?> objclsByInterface : listaClassInterface) {
					IEnumSetMappedStatusZZZ[] enumaByInterface = searchEnumMapped(objclsByInterface, false);//false, weil ja eh schon nach Interfaces-Klassen gesucht wird
					if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {
						ArrayList<IEnumSetMappedStatusZZZ> listaeByInterfaceTemp = new ArrayList<IEnumSetMappedStatusZZZ>();
						for(IEnumSetMappedStatusZZZ objEnum : enumaByInterface) {
							int iGroupIdByEnum = objEnum.getStatusGroupId();
							if(iGroupIdByEnum == iGroupId) {
								if(!listaeByInterfaceTemp.contains(objEnum)) {
									//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
									listaeByInterfaceTemp.add(objEnum);
								}
							}
						}
						if(!ArrayListZZZ.isEmpty(listaeByInterfaceTemp)) {
							if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusZZZ>();				
							listaeByInterface.addAll(listaeByInterfaceTemp);
						}
					}
				}
			}
			
			//3. von den Elternklassen der Klasse implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaeByParent = null;		
			ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
			for(Class objcls : listaobjClass) {
				//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.				
				//Darum: Von der Vererbungshierarchie des Interface implementiert.
				ArrayList<IEnumSetMappedStatusZZZ> listaeByParentTemp = StatusLocalAvailableHelperZZZ.searchForGroupListInherited(objcls, iGroupId, bScanInterfaceImmidiate);
				if(!ArrayListZZZ.isEmpty(listaeByParentTemp)) {
					if(ArrayListZZZ.isEmpty(listaeByParent)) listaeByParent = new ArrayList<IEnumSetMappedStatusZZZ>();				
					listaeByParent.addAll(listaeByParentTemp);
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
			
			if(!ArrayListZZZ.isEmpty(listaeByParent)) {
				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
				listaeReturn.addAll(listaeByParent);
			}

			//listasReturn = (ArrayList<Collection<? extends Enum<?>>>) ArrayListZZZ.unique(listasReturn);
			listaeReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListZZZ.unique(listaeReturn);
		}//end main:
		return listaeReturn;
	}
	
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
			IEnumSetMappedStatusZZZ[] enumaMappedStatus = StatusLocalAvailableHelperZZZ.searchEnumMappedInherited(classToCheck, bScanInterfaceImmidiate);
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
	 public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedInheritedList(Class<?> cls) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMappedInheritedList_(cls, true);
	 }
	 
	 public static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedInheritedList(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMappedInheritedList_(cls,bScanInterfaceImmidiate);
	 }

	
	
	/** s. https://stackoverflow.com/questions/31104584/how-to-save-enum-class-in-a-hashmap
	 * aber wenn ich ArrayList<Collection<? extends Enum<?>>> verwende, 
	 * kommt es beim Hinzufügen des Enum - Objekts trotzdem zu einem TypeCast Fehler
	 * Darum alles auf IEnumSetMappedStatusZZZ umgestellt.
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 */
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumList(Class cls) throws ExceptionZZZ {
	private static ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedInheritedList_(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		//ArrayList<Collection<? extends Enum<?>>> listasReturn = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		//ArrayList<String> listasDirect = StatusLocalHelperZZZ.getStatusLocalListDirectAvailable(cls);
		//ArrayList<Collection<? extends Enum<?>>> listaeDirect = StatusLocalHelperZZZ.getStatusLocalEnumListDirectAvailable(cls);
		ArrayList<IEnumSetMappedStatusZZZ> listaeByDirect = StatusLocalAvailableHelperZZZ.searchEnumMappedListDirect(cls, bScanInterfaceImmidiate);
		
		ArrayList<IEnumSetMappedStatusZZZ> listaeByInterface = null;
		if(bScanInterfaceImmidiate) {
			//2. allen Interfaces der Klasse, auch den erbenden implementiert
			//ArrayList<String> listasInterface = new ArrayList<String>();
			//kann nicht instanziiert werden... ArrayList<? extends Enum<?>> listasInterface = new ArrayList<? extends Enum<?>>();
			//ArrayList<Collection<? extends Enum<?>>> listasInterface = new ArrayList<Collection<? extends Enum<?>>>();			
			ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
			for(Class<?> objclsByInterface : listaClassInterface) {			
				Enum[] enumaByInterface = searchEnum_(objclsByInterface, bScanInterfaceImmidiate);
				if(!ArrayUtilZZZ.isEmpty(enumaByInterface)){
					for(Enum objEnum : enumaByInterface) {
						if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusZZZ>();
						IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) objEnum;
						listaeByInterface.add(e);
					}
				}
			}
		}
			
		//3. von den Elternklassen der Klasse implementiert
		//ArrayList<Collection<? extends Enum<?>>> listaeParent = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<IEnumSetMappedStatusZZZ> listaeByParent = null;
		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
		for(Class objcls : listaobjClass) {
			//Rekursion der interited Klassen und Interfaces einleiten !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!		
			ArrayList<IEnumSetMappedStatusZZZ> listaeTemp = StatusLocalAvailableHelperZZZ.searchEnumMappedListInherited(objcls,bScanInterfaceImmidiate);
			if(!ArrayListZZZ.isEmpty(listaeTemp)) {
				for(IEnumSetMappedStatusZZZ objEnum : listaeTemp) {
					if(ArrayListZZZ.isEmpty(listaeByParent)) listaeByParent = new ArrayList<IEnumSetMappedStatusZZZ>();
					listaeByParent.add(objEnum);
				}
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
		
		if(!ArrayListZZZ.isEmpty(listaeByParent)) {
			if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
			listaeReturn.addAll(listaeByParent);
		}

		//listasReturn = (ArrayList<Collection<? extends Enum<?>>>) ArrayListZZZ.unique(listasReturn);
		listaeReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListZZZ.unique(listaeReturn);
	}//end main:
	return listaeReturn;
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
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
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
	public static <E extends IEnumSetMappedStatusZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, bScanInterfaceImmidiate);
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E searchEnumMapped_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			if(classToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			//+++++++++++++++++++++++++++++++++++
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			if(!ArrayListZZZ.isEmpty(listaClass)) {				
				for(Class objClass : listaClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumFlagZName)) {
						IEnumSetMappedStatusZZZ e = EnumHelperZZZ.getEnumAsField(objClass, sEnumName);	
						if(e!=null) {
							enumReturn = (E) e;										
							break main;
						}
					 }
				}
			}
			
			//+++++++++++++++++++++++++++++++++++
			if(bScanInterfaceImmidiate) {
				ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(classToCheck);			
				for(Class objClass : listaInterface) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumFlagZName)) {
						IEnumSetMappedStatusZZZ e = EnumHelperZZZ.getEnumAsField(objClass, sEnumName);	
						if(e!=null) {
							enumReturn = (E) e;										
							break main;
						}
					 }
				}
				
			}
		}//end main:
		return enumReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ[] searchEnumMapped(IStatusLocalMessageUserZZZ objToCheck) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMapped_(objToCheck, true);
	 }

	public static IEnumSetMappedStatusZZZ[] searchEnumMapped(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.searchEnumMapped_(objToCheck, bScanInterfaceImmidiate);
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
	private static IEnumSetMappedStatusZZZ[] searchEnumMapped_(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ[] objaReturn = null;
		main:{
			if(objToCheck==null)break main;
			
			//Irgendwie ein Problem der Namensgebung für die Methoden wenn nur eine anders benannte Methode aufgerufen wird
			Class<?> objClassToCheck = objToCheck.getClass();
			objaReturn = StatusLocalAvailableHelperZZZ.searchEnumMapped(objClassToCheck, bScanInterfaceImmidiate);
		}
		return objaReturn;
	}

	//++++++++++++++++++++++++
	//++++++++++++++++++++++++
	public static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMapped(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMapped(Class<?> classToCheck, boolean bScannInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, bScannInterfaceImmidiate);
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E[] searchEnumMapped_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			if(classToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			//Suche nun nach einem Wert in den Eingebetteten Klassen, der per Innere Klasse eingebunden wird.			
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";			
			
			//+++++++++++++++++++++++++++++++++
			//Holle alle eingebetteten Klassen aus Klassen
			E[] enumaReturnByClass = null;
			//if(classToCheck.isInterface()) {
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				//z.B. das enum selbst gibt es so:
				//Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;))
		    	//Object[] objEnum = objClassFromInterface.getEnumConstants();
		    	//IEnumSetMappedStatusZZZ[] enumaByInterface3 = (IEnumSetMappedStatusZZZ[])objEnum; 
		  		
				Object[] objEnum = classToCheck.getEnumConstants();
				enumaReturnByClass = (E[])objEnum; 		    	
			}else {
				ArrayList<Class<?>> listaClassByClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
				if(!ArrayListZZZ.isEmpty(listaClassByClass)) {				
					ArrayList<E> listaeByClass = new ArrayList<E>();
					for(Class objClass : listaClassByClass) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
							Object[] obja = objClass.getEnumConstants();
							for(Object obj : obja) {
								IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) obj;
								listaeByClass.add((E) e);
							}
						 }
					}
					enumaReturnByClass = ArrayListZZZ.toEnumMappedStatusArray(listaeByClass);
				}
			}
			
			E[] enumaReturnByInterface = null;
			if(bScanInterfaceImmidiate) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces	
				//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);								
				ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
				
				ArrayList<E> listaeByInterface = null;
				for(Class objClassInterface : listaInterfaceByClass) {
					ArrayList<E> listaeByInterfaceTemp = new ArrayList<E>();
					
					//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
					ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
					for(Class objClass : listaClassByInterface) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
							Object[] obja = objClass.getEnumConstants();
							for(Object obj : obja) {
								IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) obj;
								listaeByInterfaceTemp.add((E) e);
							}
						 }
					}//end for		
					if(!ArrayListZZZ.isEmpty(listaeByInterfaceTemp)) {
						if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<E>();				
						listaeByInterface.addAll(listaeByInterfaceTemp);
					}
				}//end for
				enumaReturnByInterface = ArrayListZZZ.toEnumMappedStatusArray(listaeByInterface);			
			}
			
			//Verbinde beides
			enumaReturn = ArrayUtilZZZ.join(enumaReturnByClass, enumaReturnByInterface);
		}
		return enumaReturn;
	}
							
	//++++++++++++++++++++++++++++++
	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnum_(classToCheck, true);
	}
	
	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.searchEnum_(classToCheck, bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> E[] searchEnum_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			//String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			String sEnumName = StatusLocalAvailableHelperZZZ.sENUM_NAME;
			enumaReturn = EnumAvailableHelperZZZ.searchEnum(classToCheck, sEnumName, bScanInterfaceImmidiate);
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
			
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
						
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
				 ExceptionZZZ ez = new ExceptionZZZ( "Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
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
				 ExceptionZZZ ez = new ExceptionZZZ( "Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
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
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
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
			listaReturn = ArrayListZZZ.join(listaeReturnByClass, listaeReturnByInterface);
			
		}//end main:
		return listaReturn;
	}
}
