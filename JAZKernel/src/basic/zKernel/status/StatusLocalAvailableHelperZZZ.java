package basic.zKernel.status;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.scripting.bsh.BshScriptUtils.BshExecutionException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectInterfaceZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class StatusLocalAvailableHelperZZZ implements IConstantZZZ{	
	
	public static IEnumSetMappedStatusZZZ getEnumMappedByName(Object objForClass, String sStatusName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumMappedByName_(objForClass, sStatusName, true);
	}

	public static IEnumSetMappedStatusZZZ getEnumMappedByName(Object objForClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumMappedByName_(objForClass, sStatusName, bScanInterfaceImmidiate);
	}

	private static IEnumSetMappedStatusZZZ getEnumMappedByName_(Object objForClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
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
			objReturn = StatusLocalAvailableHelperZZZ.getEnumMappedByName(objClass, sStatusName, bScanInterfaceImmidiate);
		
		}//end main:
		return objReturn;		
	}
	
	//+++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ getEnumMappedByName(Class objClass, String sStatusName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumMappedByName_(objClass, sStatusName, true);		
	}
	
	public static IEnumSetMappedStatusZZZ getEnumMappedByName(Class objClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumMappedByName_(objClass, sStatusName, bScanInterfaceImmidiate);		
	}
	
	private static IEnumSetMappedStatusZZZ getEnumMappedByName_(Class objClass, String sStatusName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
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
			
			HashMap<String,IStatusBooleanMessageZZZ> hmStatus = StatusLocalAvailableHelperZZZ.createHashMapBooleanMessageZZZ(objClass, bScanInterfaceImmidiate);
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
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			if(hmStatusLocal==null)break main;
						
			//1. existiert der Statuswert schon darin
			Boolean objValue = hmStatusLocal.get(sStatusName.toUpperCase());
			if(objValue==null) {
				bReturn = true;
				break main;
			}
			
			//2. Ist der Statuswert identisch
			if(objValue.booleanValue()!=bValue) {
				bReturn = true;
				break main;
			}				
		}//end main:
		return bReturn;
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
			String[] saFlagAvailable = StatusLocalAvailableHelperZZZ.getInherited(cls);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
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
			String[] saStatusAvailable = StatusLocalAvailableHelperZZZ.getDirect(cls);			
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
	public static ArrayList<String> getListDirect(Class cls)  throws ExceptionZZZ {
		return getListDirect_(cls, true);
	}
	
	public static ArrayList<String> getListDirect(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getListDirect_(cls, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<String> getListDirect_(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = getEnum_(cls, bScanInterfaceImmidiate);
		
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
					Enum[] enumaByInterface = getEnum_(objclsByInterface, false);//false, weil ja die Interfaces eh betrachtet werde sollen

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
	public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListDirect(Class cls)  throws ExceptionZZZ {
		return getEnumMappedListDirect_(cls, true);
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListDirectAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListDirect(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getEnumMappedListDirect_(cls, bScanInterfaceImmidiate);
	}
	
	//private static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListDirectAvailable_(Class cls, boolean bLocal, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	private static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListDirect_(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		//ArrayList<Collection<? extends Enum<?>>> listaeReturn = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;

		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = getEnum_(cls, bScanInterfaceImmidiate);

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
					Enum[] enumaByInterface = getEnum_(objclsByInterface, bScanInterfaceImmidiate);

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
	
	public static ArrayList<IEnumSetMappedStatusZZZ> getListForGroupInherited(Class cls, int iGroupId)  throws ExceptionZZZ {
		return getListForGroupInherited_(cls, iGroupId, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> getListForGroupInherited(Class cls, int iGroupId, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getListForGroupInherited_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> getListForGroupInherited_(Class cls, int iGroupId, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		if(iGroupId<=-1)break main;
		
		//1. von der Classe selbst implementiert		
		IEnumSetMappedStatusZZZ[] enuma = getEnumMapped(cls,bScanInterfaceImmidiate);

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
				enumaByInterface = getEnumMapped(objclsByInterface, bScanInterfaceImmidiate);
				
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
			ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuperTemp=StatusLocalAvailableHelperZZZ.getListForGroupInherited(objclsSuper, iGroupId, bScanInterfaceImmidiate);			
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
	
	public static ArrayList<String> getListInherited(Class cls)  throws ExceptionZZZ {
		return getListInherited_(cls, true);
	}
	
	public static ArrayList<String> getListInherited(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getListInherited_(cls, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<String> getListInherited_(Class cls, boolean bScanInterfaceImmiediate)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert		
		Enum[] enuma = getEnum_(cls, bScanInterfaceImmiediate);
		
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
				enumaByInterface = getEnum(objclsByInterface);
								
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
			ArrayList<String> listasByClassSuperTemp=StatusLocalAvailableHelperZZZ.getListInherited(objclsSuper);
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
	public static IEnumSetMappedStatusZZZ[] getEnumMappedInherited(IStatusLocalMessageUserZZZ objForClass)  throws ExceptionZZZ {
		return getEnumMappedInherited_(objForClass, true);
	}
	
	public static IEnumSetMappedStatusZZZ[] getEnumMappedInherited(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getEnumMappedInherited_(objForClass, bScanInterfaceImmidiate);
	}
	
	private static IEnumSetMappedStatusZZZ[] getEnumMappedInherited_(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {	
		IEnumSetMappedStatusZZZ[] objaeReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class objClass = objForClass.getClass();
			objaeReturn = StatusLocalAvailableHelperZZZ.getEnumMappedInherited(objClass);
		
		}//end main:
	return objaeReturn;
	}


	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ[] getEnumMappedInherited(Class<?> cls)  throws ExceptionZZZ {
		return getEnumMappedInherited_(cls, true);
	}
	
	public static IEnumSetMappedStatusZZZ[] getEnumMappedInherited(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getEnumMappedInherited_(cls, bScanInterfaceImmidiate);
	}
		
	private static IEnumSetMappedStatusZZZ[] getEnumMappedInherited_(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {	
		IEnumSetMappedStatusZZZ[] objaeReturn = null;
		main:{
		
		ArrayList<IEnumSetMappedStatusZZZ>listaeReturn=StatusLocalAvailableHelperZZZ.getEnumMappedListInherited(cls, bScanInterfaceImmidiate);
		if(listaeReturn==null)break main;
		
		objaeReturn = ArrayListZZZ.toEnumForGroupArray(listaeReturn);
		
	}//end main:
	return objaeReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++	
	public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListInherited(IStatusLocalMessageUserZZZ objForClass)  throws ExceptionZZZ {
		return getEnumMappedListInherited_(objForClass, true);
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListInherited(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getEnumMappedListInherited_(objForClass, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListInherited_(IStatusLocalMessageUserZZZ objForClass, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {		
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class objClass = objForClass.getClass();
			listaeReturn = StatusLocalAvailableHelperZZZ.getEnumMappedListInherited(objClass,bScanInterfaceImmidiate);
		
			}//end main:
	return listaeReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListInherited(Class<?> cls)  throws ExceptionZZZ {
		return getEnumMappedListInherited_(cls, true);
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListInherited(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return getEnumMappedListInherited_(cls, bScanInterfaceImmidiate);
	}
	
	
	//private static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable_(Class cls, boolean bLocal, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	private static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedListInherited_(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		//ArrayList<Collection<? extends Enum<?>>> listaeReturn = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert		
		Enum[] enuma = getEnum_(cls,bScanInterfaceImmidiate);
		
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
						
				Enum[] enumaByInterface = getEnum_(objclsByInterface,bScanInterfaceImmidiate);
				
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
			ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuperTemp = StatusLocalAvailableHelperZZZ.getEnumMappedListInherited(objclsSuper,bScanInterfaceImmidiate);
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
	public static ArrayList<String> getList(Class<?> cls) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getList_(cls, true);
	}
	
	public static ArrayList<String> getList(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getList_(cls, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<String> getList_(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		ArrayList<String> listasByDirect = StatusLocalAvailableHelperZZZ.getListDirect(cls,bScanInterfaceImmidiate);
				
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<String> listasByInterface = null;
		if(bScanInterfaceImmidiate) {
			ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
			for(Class<?> objclsByInterface : listaClassInterface) {
				Enum[] enumaByInterface = getEnum_(objclsByInterface, false);//false, weil ja die Interfaces eh betrachtet werde sollen
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
			ArrayList<String> listasByParentTemp = StatusLocalAvailableHelperZZZ.getListInherited(objcls, bScanInterfaceImmidiate);
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
	
	public static String[] get(Class<?> cls) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}

		ArrayList<String> listas = getList(cls);
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] getDirect(Class<?> cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.getListDirect(cls);			
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls)  throws ExceptionZZZ {
	public static String[] getInherited(Class<?> cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.getListInherited(cls);					
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] getEnumInherited(Class<?> cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalAvailableHelperZZZ.getListInherited(cls);					
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	//+++++++++++++++++++++++++++++
	//+++ Hole die Status-Strings fuer eine bestimmte StatusGroupId
	//+++++++++++++++++++++++++++++
	public static ArrayList<IEnumSetMappedStatusZZZ> getListForGroupDirect(Class<?> cls, int iGroupId) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.getListForGroupDirect_(cls, iGroupId, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> getListForGroupDirect(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.getListForGroupDirect_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> getListForGroupDirect_(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
		main:{
			if(iGroupId<=-1)break main;
			
			boolean bLocal = true;						
			IEnumSetMappedStatusZZZ[] enuma = null;
			if(bLocal) {
				enuma = getEnumMapped(cls, bScanInterfaceImmidiate);
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
					IEnumSetMappedStatusZZZ[] enumaByInterface = getEnumMapped(objclsByInterface, false);//false, weil ja eh schon nach Interfaces-Klassen gesucht wird
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
	public static ArrayList<IEnumSetMappedStatusZZZ> getListForGroup(Class<?> cls, int iGroupId) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.getListForGroup_(cls, iGroupId, true);
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> getListForGroup(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.getListForGroup_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> getListForGroup_(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
		main:{
			if(iGroupId<=-1) break main;
			
			//1. von der Classe selbst implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaeByDirect = StatusLocalAvailableHelperZZZ.getListForGroupDirect(cls, iGroupId, bScanInterfaceImmidiate);
					
			//2. allen Interfaces der Klasse, auch den erbenden implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaeByInterface = null;
			if(bScanInterfaceImmidiate) {
				ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
				for(Class<?> objclsByInterface : listaClassInterface) {
					IEnumSetMappedStatusZZZ[] enumaByInterface = getEnumMapped(objclsByInterface, false);//false, weil ja eh schon nach Interfaces-Klassen gesucht wird
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
				ArrayList<IEnumSetMappedStatusZZZ> listaeByParentTemp = StatusLocalAvailableHelperZZZ.getListForGroupInherited(objcls, iGroupId, bScanInterfaceImmidiate);
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
	public static HashMap<String, IEnumSetMappedStatusZZZ> getHashMapEnumMapped(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getHashMapEnumMapped(classToCheck, true);
	}
		
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IEnmSetMappdedStatusZZZ>. 
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IEnumSetMappedStatusZZZ> getHashMapEnumMapped(Class<?> classToCheck, boolean bScanInterface) throws ExceptionZZZ {
		HashMap<String, IEnumSetMappedStatusZZZ> hmReturn = null;
		main:{
			IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalAvailableHelperZZZ.getEnumMapped(classToCheck, bScanInterface);
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
		public static HashMap<String, IStatusBooleanMessageZZZ> createHashMapBooleanMessageZZZ(Class<?> classToCheck) throws ExceptionZZZ {
			return StatusLocalAvailableHelperZZZ.createHashMapBooleanMessageZZZ_(classToCheck, true);
		}
		
		public static HashMap<String, IStatusBooleanMessageZZZ> createHashMapBooleanMessageZZZ(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
			return StatusLocalAvailableHelperZZZ.createHashMapBooleanMessageZZZ_(classToCheck, bScanInterfaceImmidiate);
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
	private static HashMap<String, IStatusBooleanMessageZZZ> createHashMapBooleanMessageZZZ_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		HashMap<String, IStatusBooleanMessageZZZ> hmReturn = null;
		main:{
			//Das hat nur direkte Klassen und Interfaces
			//IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalHelperZZZ.getEnumStatusLocalMapped(classToCheck, bScanInterfaceImmidiate);
			
			//IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalHelperZZZ.getStatusLocalEnumMapped(classToCheck, bScanInterfaceImmidiate);			


			//Hier werden auch Vererbte Klassen und Interfaces betrachtet.
			IEnumSetMappedStatusZZZ[] enumaMappedStatus = StatusLocalAvailableHelperZZZ.getEnumMappedInherited(classToCheck, bScanInterfaceImmidiate);
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
	public static HashMap<String, IStatusBooleanMessageZZZ> getHashMapBooleanMessage(IStatusLocalMessageUserZZZ objToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getHashMapBooleanMessage(objToCheck, true);
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
	public static HashMap<String, IStatusBooleanMessageZZZ> getHashMapBooleanMessage(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterface) throws ExceptionZZZ {
		HashMap<String, IStatusBooleanMessageZZZ> hmReturn = null;
		main:{
			if(objToCheck==null)break main;
			
			Class<?> objClassToCheck = objToCheck.getClass();
			IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalAvailableHelperZZZ.getEnumMapped(objClassToCheck, bScanInterface);
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
	 public static ArrayList<IEnumSetMappedStatusZZZ> createListEnumMappedInherited(Class<?> cls) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.createListEnumMappedInherited_(cls, true);
	 }
	 
	 public static ArrayList<IEnumSetMappedStatusZZZ> createListEnumMappedInherited(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.createListEnumMappedInherited_(cls,bScanInterfaceImmidiate);
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
	private static ArrayList<IEnumSetMappedStatusZZZ> createListEnumMappedInherited_(Class<?> cls, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
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
		ArrayList<IEnumSetMappedStatusZZZ> listaeByDirect = StatusLocalAvailableHelperZZZ.getEnumMappedListDirect(cls, bScanInterfaceImmidiate);
		
		ArrayList<IEnumSetMappedStatusZZZ> listaeByInterface = null;
		if(bScanInterfaceImmidiate) {
			//2. allen Interfaces der Klasse, auch den erbenden implementiert
			//ArrayList<String> listasInterface = new ArrayList<String>();
			//kann nicht instanziiert werden... ArrayList<? extends Enum<?>> listasInterface = new ArrayList<? extends Enum<?>>();
			//ArrayList<Collection<? extends Enum<?>>> listasInterface = new ArrayList<Collection<? extends Enum<?>>>();			
			ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
			for(Class<?> objclsByInterface : listaClassInterface) {			
				Enum[] enumaByInterface = getEnum_(objclsByInterface, bScanInterfaceImmidiate);
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
			ArrayList<IEnumSetMappedStatusZZZ> listaeTemp = StatusLocalAvailableHelperZZZ.getEnumMappedListInherited(objcls,bScanInterfaceImmidiate);
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
	 public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedList(IStatusLocalMessageUserZZZ objToCheck) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.getEnumMappedList_(objToCheck, true);
	 }
	 
	 /** Rueckgabewert ist eine ArrayList in Form <IStatusBooleanMessageZZZ>.
		 *  Merke1: Die Werte der BooleanMessage stammen aus dem Objekt selbst.
		 *  Merke2: Trotzdem sind das ALLE moeglichen/definierten Statuswerte.
		 * @param classToCheck
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
		 * @param E 
		 */
	 public static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedList(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.getEnumMappedList_(objToCheck,bScanInterfaceImmidiate);
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
	private static ArrayList<IEnumSetMappedStatusZZZ> getEnumMappedList_(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaReturn = null;
		main:{
			if(objToCheck==null)break main;
			
			Class<?> objClassToCheck = objToCheck.getClass();
			IEnumSetMappedStatusZZZ[] enumaMappedStatus= StatusLocalAvailableHelperZZZ.getEnumMapped(objClassToCheck, bScanInterfaceImmidiate);
			if(enumaMappedStatus==null) break main;
			
			listaReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayUtilZZZ.toArrayList(enumaMappedStatus);
		}
		return listaReturn;
	}


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String[] getForGroup(Class<?> cls, int iGroupId) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.getForGroup_(cls, iGroupId, true);
	}
	
	public static String[] getForGroup(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		return StatusLocalAvailableHelperZZZ.getForGroup_(cls, iGroupId, bScanInterfaceImmidiate);
	}
	
	private static String[] getForGroup_(Class<?> cls, int iGroupId, boolean bScanInterfaceImmidiate) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			if(iGroupId<=-1)break main;
	
			ArrayList<IEnumSetMappedStatusZZZ> lista = getListForGroup(cls, iGroupId, bScanInterfaceImmidiate);
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
	public static <E extends IEnumSetMappedStatusZZZ> E getEnumMapped(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumlMapped_(classToCheck, sEnumName, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E getEnumlMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumlMapped_(classToCheck, sEnumName, bScanInterfaceImmidiate);
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E getEnumlMapped_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
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
	public static IEnumSetMappedStatusZZZ[] getEnumMapped(IStatusLocalMessageUserZZZ objToCheck) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.getEnumMapped_(objToCheck, true);
	 }

	public static IEnumSetMappedStatusZZZ[] getEnumMapped(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		 return StatusLocalAvailableHelperZZZ.getEnumMapped_(objToCheck, bScanInterfaceImmidiate);
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
	private static IEnumSetMappedStatusZZZ[] getEnumMapped_(IStatusLocalMessageUserZZZ objToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ[] objaReturn = null;
		main:{
			if(objToCheck==null)break main;
			
			//Irgendwie ein Problem der Namensgebung für die Methoden wenn nur eine anders benannte Methode aufgerufen wird
			Class<?> objClassToCheck = objToCheck.getClass();
			objaReturn = StatusLocalAvailableHelperZZZ.getEnumMapped(objClassToCheck, bScanInterfaceImmidiate);
		}
		return objaReturn;
	}

	//++++++++++++++++++++++++
	//++++++++++++++++++++++++
	public static <E extends IEnumSetMappedStatusZZZ> E[] getEnumMapped(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumMapped_(classToCheck, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E[] getEnumMapped(Class<?> classToCheck, boolean bScannInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumMapped_(classToCheck, bScannInterfaceImmidiate);
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E[] getEnumMapped_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
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
					enumaReturnByClass = ArrayListZZZ.toEnumForGroupArray(listaeByClass);
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
				enumaReturnByInterface = ArrayListZZZ.toEnumForGroupArray(listaeByInterface);			
			}
			
			//Verbinde beides
			enumaReturn = ArrayUtilZZZ.join(enumaReturnByClass, enumaReturnByInterface);
		}
		return enumaReturn;
	}
							
	//++++++++++++++++++++++++++++++
	public static <E extends Enum> E[] getEnum(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnum_(classToCheck, true);
	}
	
	public static <E extends Enum> E[] getEnum(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnum_(classToCheck, bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> E[] getEnum_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			//++++++++++++++++++++++++++++++
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
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
				
				ArrayList<E> listaeByClass = new ArrayList<E>();
				for(Class objClass : listaClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
						Object[] obja = objClass.getEnumConstants();
						for(Object obj : obja) {
							Enum e = (Enum) obj;
							listaeByClass.add((E) e);
						}
					 }
				}
				enumaReturnByClass = ArrayListZZZ.toEnumArray(listaeByClass);
			}
			
			
			E[] enumaReturnByInterface = null;
			if(bScanInterfaceImmidiate) {
			//+++++++++++++++++++++++++++++++++
			//Holle alle eingebetteten Klassen sofort aus Interfaces, alternativ mir Rekursion der Elternklasse arbeiten.	
			//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);
				ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
							
				ArrayList<E> listaeByInterface = new ArrayList<E>();
				for(Class objClassInterface : listaInterfaceByClass) {
					
					//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
					ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
					for(Class objClass : listaClassByInterface) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumStatusLocalNameInner)) {
							Object[] obja = objClass.getEnumConstants();
							for(Object obj : obja) {
								IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) obj;
								listaeByInterface.add((E) e);
							}
						 }
					}//end for												
				}//end for
				
				enumaReturnByInterface = ArrayListZZZ.toEnumArray(listaeByInterface);
			}//end if
			
			//Verbinde beides
			enumaReturn = ArrayUtilZZZ.join(enumaReturnByClass, enumaReturnByInterface);
		}//end main:
		return enumaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++	
	public static <E extends Enum> E getEnum(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnum_(classToCheck, sEnumName, true);
	}
	
	public static <E extends Enum> E getEnum(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnum_(classToCheck, sEnumName, bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> E getEnum_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
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
	public static <E extends Enum> ArrayList<E> getEnumList(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumList_(classToCheck,true);
	}

	public static <E extends Enum> ArrayList<E> getEnumList(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return StatusLocalAvailableHelperZZZ.getEnumList_(classToCheck,bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> ArrayList<E> getEnumList_(Class<?> classToCheck, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
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
