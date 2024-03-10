package basic.zKernel.status;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class StatusLocalHelperZZZ implements IConstantZZZ{	
	public static boolean proofStatusLocalChanged(HashMap<String,Boolean>hmStatusLocal, String sStatusName, boolean bValue) throws ExceptionZZZ {
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
	
	
	public static boolean proofStatusLocalExists(Class cls, String sStatusName) throws ExceptionZZZ {
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
//			String[] saFlagAvailable = FlagZHelperZZZ.getFlagsZLocal(cls);
//			if(saFlagAvailable!=null) {
//				if(StringArrayZZZ.contains(saFlagAvailable, sFlagName)) {
//					bReturn = true;	
//					break main;
//				}
//			}
			
			//Die Klasse selbst und alle Elternklassen, sowie deren Interface. Achtung, Rekursion wird darin verwendet!
			String[] saFlagAvailable = StatusLocalHelperZZZ.getStatusLocalInheritedAvailable(cls);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
			if(saFlagAvailable!=null) {
				if(StringArrayZZZ.containsIgnoreCase(saFlagAvailable, sStatusName)) {
					bReturn = true;	
					break main;
				}
			}												
		}//end main:
		return bReturn;
	}
	
	
	
	public static boolean proofStatusLocalDirectExists(Class cls, String sFlagName) throws ExceptionZZZ {
		return proofStatusLocalDirectExists_(cls, sFlagName, true);
	}
		
	private static boolean proofStatusLocalDirectExists_(Class cls, String sStatusName, boolean bLocal) throws ExceptionZZZ {
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
			String[] saStatusAvailable = null;
			if(bLocal) {
			    saStatusAvailable = StatusLocalHelperZZZ.getStatusLocalDirectAvailable(cls);
			}else {
				//saFlagAvailable = StatusLocalHelperZZZ.getStatusDirectAvailable(cls);
			}
			if(saStatusAvailable!=null) {
				if(StringArrayZZZ.contains(saStatusAvailable, sStatusName)) {
					bReturn = true;	
					break main;
				}
			}						
		}//end main:
		return bReturn;
	}
	
	public static ArrayList<String> getStatusLocalListAvailable(Class cls)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		ArrayList<String> listasDirect = StatusLocalHelperZZZ.getStatusLocalListDirectAvailable(cls);
				
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
//		ArrayList<String> listasInterface = new ArrayList<String>();
//		ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
//		ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
//		for(Class<?> objclsByInterface : listaClassInterface) {
//			Enum[] enumaByInterface = getEnumFlagZLocal(objclsByInterface);
//			if(enumaByInterface!=null) {			
//				for(Enum objEnum : enumaByInterface) {
//					String sEnum = objEnum.name();
//					if(!listasInterface.contains(sEnum)) {
//						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//						listasInterface.add(sEnum);
//					}
//				}			
//			}
//		}
//		
//		//3. von den Elternklassen der Klasse implementiert
//		ArrayList<String> listasParent = new ArrayList<String>();		
//		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
//		for(Class objcls : listaobjClass) {
//			//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
//			//ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListDirectAvailable(objcls);
//			
//			//Von der Vererbungshierarchie des Interface implementiert.
//			ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZLocalListInheritedAvailable(objcls);
//			
//			listasParent.addAll(listasTemp);
//		}
		
		listasReturn.addAll(listasDirect);
//		listasReturn.addAll(listasParent);
//		listasReturn.addAll(listasInterface);
		listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);							
		
	}//end main:
	return listasReturn;
	}
	
	
	public static ArrayList<String> getStatusLocalListDirectAvailable(Class cls)  throws ExceptionZZZ {
		return getStatusLocalListDirectAvailable_(cls, true);
	}
	
	private static ArrayList<String> getStatusLocalListDirectAvailable_(Class cls, boolean bLocal)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = null;
		if(bLocal) {
			enuma = getEnumStatusLocal(cls);
		}else {
			//enuma = getEnumFlagZ(cls);
		}
		if(enuma!=null) {			
			for(Enum objEnum : enuma) {
				String sEnum = objEnum.name();
				if(!listasReturn.contains(sEnum)) {
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
					listasReturn.add(sEnum);
				}
			}			
		}
		
		//2. von den Interfaces der Klasse DIREKT implementiert
		Class[] objclsaByInterface = cls.getInterfaces();
		for(Class objclsByInterface : objclsaByInterface) {
			Enum[] enumaByInterface = null;
			if(bLocal) {
				enumaByInterface = getEnumStatusLocal(objclsByInterface);
			}else {
			//	enumaByInterface = getEnumFlagZ(objclsByInterface);
			}
			if(enumaByInterface!=null) {			
				for(Enum objEnum : enumaByInterface) {
					String sEnum = objEnum.name();
					if(!listasReturn.contains(sEnum)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listasReturn.add(sEnum);
					}
				}			
			}
		}
		
	}//end main:
	return listasReturn;
	}
	
	public static ArrayList<IEnumSetMappedStatusZZZ> getStatusLocalListForGroupInheritedAvailable(Class cls, int iGroupId)  throws ExceptionZZZ {
		return getStatusLocalListForGroupInheritedAvailable_(cls, iGroupId, true);
	}
	
	private static ArrayList<IEnumSetMappedStatusZZZ> getStatusLocalListForGroupInheritedAvailable_(Class cls, int iGroupId, boolean bLocal)  throws ExceptionZZZ {
		ArrayList<IEnumSetMappedStatusZZZ> listaReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		if(iGroupId<=-1)break main;
		
		//1. von der Classe selbst implementiert
		
		IEnumSetMappedStatusZZZ[] enuma = null;
		if(bLocal) {
			enuma = getEnumStatusLocalMapped(cls);
		}else {
			//enuma = getEnumFlagZ(cls);
		}
		if(enuma!=null) {			
			for(IEnumSetMappedStatusZZZ objEnum : enuma) {
				int iGroupIdByEnum = objEnum.getStatusGroupId();
				if(iGroupIdByEnum == iGroupId) {
					if(!listaReturn.contains(objEnum)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listaReturn.add(objEnum);
					}
				}
			}			
		}
				
		//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
		//          Es fehlen hier die Interfaces extends Interface, usw.
		
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
		ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);
		for(Class<?> objclsByInterface : listaInterfaceSuper) {
					
			IEnumSetMappedStatusZZZ[] enumaByInterface = null;
			if(bLocal) {
				enumaByInterface = getEnumStatusLocalMapped(objclsByInterface);
			}else {
				//enumaByInterface = getEnumFlagZ(objclsByInterface);
			}
			if(enumaByInterface!=null) {			
				for(IEnumSetMappedStatusZZZ objEnum : enumaByInterface) {					
					if(!listaReturn.contains(objEnum)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listaReturn.add(objEnum);
					}
				}			
			}
		}
		
		//3. Hole die Elternklassen.
		ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		for(Class<?> objclsSuper : listaClassSuper) {
			//!!!Rekursion
			ArrayList<IEnumSetMappedStatusZZZ> listaFlagByClassSuper = null;
			if(bLocal) {
				listaFlagByClassSuper=StatusLocalHelperZZZ.getStatusLocalListForGroupInheritedAvailable(objclsSuper, iGroupId);
			}else {
				//listaFlagByClassSuper=StatusLocalHelperZZZ.getFlagsZListInheritedAvailable(objclsSuper);
			}
			for(IEnumSetMappedStatusZZZ objEnum : listaFlagByClassSuper) {
				if(!listaReturn.contains(objEnum)) {
					listaReturn.add(objEnum);
				}
			}
		}				
	}//end main:
	return listaReturn;
	}
	
	public static ArrayList<String> getStatusLocalListInheritedAvailable(Class cls)  throws ExceptionZZZ {
		return getStatusLocalListInheritedAvailable_(cls, true);
	}
	
	private static ArrayList<String> getStatusLocalListInheritedAvailable_(Class cls, boolean bLocal)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		
		Enum[] enuma = null;
		if(bLocal) {
			enuma = getEnumStatusLocal(cls);
		}else {
			//enuma = getEnumFlagZ(cls);
		}
		if(enuma!=null) {			
			for(Enum objEnum : enuma) {
				String sEnum = objEnum.name();
				if(!listasReturn.contains(sEnum)) {
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
					listasReturn.add(sEnum);
				}
			}			
		}
				
		//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
		//          Es fehlen hier die Interfaces extends Interface, usw.
		
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
		ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);
		for(Class<?> objclsByInterface : listaInterfaceSuper) {
					
			Enum[] enumaByInterface = null;
			if(bLocal) {
				enumaByInterface = getEnumStatusLocal(objclsByInterface);
			}else {
				//enumaByInterface = getEnumFlagZ(objclsByInterface);
			}
			if(enumaByInterface!=null) {			
				for(Enum objEnum : enumaByInterface) {
					String sEnum = objEnum.name();
					if(!listasReturn.contains(sEnum)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listasReturn.add(sEnum);
					}
				}			
			}
		}
		
		//3. Hole die Elternklassen.
		ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		for(Class<?> objclsSuper : listaClassSuper) {
			//!!!Rekursion
			ArrayList<String> listaFlagByClassSuper = null;
			if(bLocal) {
				listaFlagByClassSuper=StatusLocalHelperZZZ.getStatusLocalListInheritedAvailable(objclsSuper);
			}else {
				//listaFlagByClassSuper=StatusLocalHelperZZZ.getFlagsZListInheritedAvailable(objclsSuper);
			}
			for(String sEnum : listaFlagByClassSuper) {
				if(!listasReturn.contains(sEnum)) {
					listasReturn.add(sEnum);
				}
			}
		}				
	}//end main:
	return listasReturn;
	}
	
	
	
	public static ArrayList<String> getStatusLocalList(Class cls) throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		ArrayList<String> listasDirect = StatusLocalHelperZZZ.getStatusLocalListDirectAvailable(cls);
				
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<String> listasInterface = new ArrayList<String>();
		ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
		ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
		for(Class<?> objclsByInterface : listaClassInterface) {
			Enum[] enumaByInterface = getEnumStatusLocal(objclsByInterface);
			if(enumaByInterface!=null) {			
				for(Enum objEnum : enumaByInterface) {
					String sEnum = objEnum.name();
					if(!listasInterface.contains(sEnum)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listasInterface.add(sEnum);
					}
				}			
			}
		}
		
		//3. von den Elternklassen der Klasse implementiert
		ArrayList<String> listasParent = new ArrayList<String>();		
		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
		for(Class objcls : listaobjClass) {
			//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
			//ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListDirectAvailable(objcls);
			
			//Von der Vererbungshierarchie des Interface implementiert.
			ArrayList<String> listasTemp = StatusLocalHelperZZZ.getStatusLocalListInheritedAvailable(objcls);
			
			listasParent.addAll(listasTemp);
		}
		
		listasReturn.addAll(listasDirect);
		listasReturn.addAll(listasParent);
		listasReturn.addAll(listasInterface);
		listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
	}//end main:
	return listasReturn;
	}
	
	public static String[] getStatusLocal(Class cls) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}

		ArrayList<String> listas = getStatusLocalList(cls);
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] getStatusLocalDirectAvailable(Class cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalHelperZZZ.getStatusLocalListDirectAvailable(cls);			
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	
	public static String[] getStatusLocalInheritedAvailable(Class cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = StatusLocalHelperZZZ.getStatusLocalListInheritedAvailable(cls);					
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	//+++++++++++++++++++++++++++++
	//+++ Hole die Status-Strings fuer eine bestimmte StatusGroupId
	//+++++++++++++++++++++++++++++
	public static ArrayList<IEnumSetMappedStatusZZZ> getStatusLocalListForGroupDirectAvailabel(Class cls, int iGroupId) throws ExceptionZZZ{
		ArrayList<IEnumSetMappedStatusZZZ> listaReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
		main:{
			if(iGroupId<=-1)break main;
			
			boolean bLocal = true;						
			IEnumSetMappedStatusZZZ[] enuma = null;
			if(bLocal) {
				enuma = getEnumStatusLocalMapped(cls);
			}else {
				//enuma = getEnumFlagZ(cls);
			}
					
			if(enuma!=null) {			
				for(IEnumSetMappedStatusZZZ objEnum : enuma) {					
					int iEnumGroupId = objEnum.getStatusGroupId();
					if(iEnumGroupId==iGroupId) {					
						if(!listaReturn.contains(objEnum)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listaReturn.add(objEnum);
						}
					}
				}			
			}		
		}//end main:
		return listaReturn;
	}	

	
	
	public static ArrayList<IEnumSetMappedStatusZZZ> getStatusLocalListForGroup(Class cls, int iGroupId) throws ExceptionZZZ{
		ArrayList<IEnumSetMappedStatusZZZ> listaReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
		main:{
			if(iGroupId<=-1) break main;
			
			//1. von der Classe selbst implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaDirect = StatusLocalHelperZZZ.getStatusLocalListForGroupDirectAvailabel(cls, iGroupId);
					
			//2. allen Interfaces der Klasse, auch den erbenden implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaInterface = new ArrayList<IEnumSetMappedStatusZZZ>();
			ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
			for(Class<?> objclsByInterface : listaClassInterface) {
				IEnumSetMappedStatusZZZ[] enumaByInterface = getEnumStatusLocalMapped(objclsByInterface);
				if(enumaByInterface!=null) {			
					for(IEnumSetMappedStatusZZZ objEnum : enumaByInterface) {
						int iGroupIdByEnum = objEnum.getStatusGroupId();
						if(iGroupIdByEnum == iGroupId) {
							if(!listaInterface.contains(objEnum)) {
								//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
								listaInterface.add(objEnum);
							}
						}
					}			
				}
			}
			
			//3. von den Elternklassen der Klasse implementiert
			ArrayList<IEnumSetMappedStatusZZZ> listaParent = new ArrayList<IEnumSetMappedStatusZZZ>();		
			ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
			for(Class objcls : listaobjClass) {
				//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
				//ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListDirectAvailable(objcls);
				
				//Von der Vererbungshierarchie des Interface implementiert.
				ArrayList<IEnumSetMappedStatusZZZ> listaTemp = StatusLocalHelperZZZ.getStatusLocalListForGroupInheritedAvailable(objcls, iGroupId);
				
				listaParent.addAll(listaTemp);
			}
			
			listaReturn.addAll(listaDirect);
			listaReturn.addAll(listaParent);
			listaReturn.addAll(listaInterface);
			listaReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListZZZ.unique(listaReturn);
		}//end main:
		return listaReturn;
	}
	
	/** s. https://stackoverflow.com/questions/31104584/how-to-save-enum-class-in-a-hashmap
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IStatusBooleanMessageZZZ> getHashMapStatusBooleanMessageZZZ(Class<?> classToCheck) throws ExceptionZZZ {
		HashMap<String, IStatusBooleanMessageZZZ> hmReturn = new HashMap<String, IStatusBooleanMessageZZZ>();
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumStatusLocalName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			ArrayList<? extends Enum<?>> listae = new ArrayList();
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumStatusLocalName)) {
					Object[] obja = objClass.getEnumConstants();
									 }
			}
			TODOGOON;
			//enumaReturn = ArrayListZZZ.toEnumArray(listae);
		}
		return hmReturn;
	}
	
	
	public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumList(Class cls) throws ExceptionZZZ {
		ArrayList<Collection<? extends Enum<?>>> listasReturn = new ArrayList<Collection<? extends Enum<?>>>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		ArrayList<String> listasDirect = StatusLocalHelperZZZ.getStatusLocalListDirectAvailable(cls);
				
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		//ArrayList<String> listasInterface = new ArrayList<String>();
		//kann nicht instanziiert werden... ArrayList<? extends Enum<?>> listasInterface = new ArrayList<? extends Enum<?>>();
		ArrayList<Collection<? extends Enum<?>>> listasInterface = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
		ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
		for(Class<?> objclsByInterface : listaClassInterface) {
//			Enum[] enumaByInterface = getEnumStatusLocal(objclsByInterface);
//			if(enumaByInterface!=null) {			
//				for(Enum objEnum : enumaByInterface) {
//					String sEnum = objEnum.name();
//					if(!listasInterface.contains(sEnum)) {
//						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//						listasInterface.add(sEnum);
//					}									
//				}			
//			}
			
			Enum[] enumaByInterface = getEnumStatusLocal(objclsByInterface);
			for(Enum objEnum : enumaByInterface) {
				listasInterface.add((Collection<? extends Enum<?>>) objEnum);
			}
		}
		TODOGOON;
		
		
		//3. von den Elternklassen der Klasse implementiert
		ArrayList<String> listasParent = new ArrayList<String>();		
		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
		for(Class objcls : listaobjClass) {
			//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
			//ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListDirectAvailable(objcls);
			
			//Von der Vererbungshierarchie des Interface implementiert.
			ArrayList<String> listasTemp = StatusLocalHelperZZZ.getStatusLocalListInheritedAvailable(objcls);
			
			listasParent.addAll(listasTemp);
		}
		
		listasReturn.addAll(listasDirect);
		listasReturn.addAll(listasParent);
		listasReturn.addAll(listasInterface);
		listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
	}//end main:
	return listasReturn;
	}


	public static String[] getStatusLocalForGroup(Class cls, int iGroupId) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			if(iGroupId<=-1)break main;
	
			ArrayList<IEnumSetMappedStatusZZZ> lista = getStatusLocalListForGroup(cls, iGroupId);
			ArrayList<String>listasReturn = new ArrayList<String>();
			for(IEnumSetMappedStatusZZZ objEnum : lista) {
				if(objEnum!=null) {
					String sEnum = objEnum.getName();
					listasReturn.add(sEnum);
				}
			}
			listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
			
			saReturn = ArrayListZZZ.toStringArray(listasReturn);
		}//end main:
		return saReturn;
	}
	
	
	private static <E extends IEnumSetMappedStatusZZZ> E getEnumStatusLocalMapped(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
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
		}//end main:
		return enumReturn;
	}
	
	private static <E extends IEnumSetMappedStatusZZZ> E[] getEnumStatusLocalMapped(Class<?> classToCheck) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumStatusLocalName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			ArrayList<E> listae = new ArrayList<E>();
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumStatusLocalName)) {
					Object[] obja = objClass.getEnumConstants();
					for(Object obj : obja) {
						IEnumSetMappedStatusZZZ e = (IEnumSetMappedStatusZZZ) obj;
						listae.add((E) e);
					}
				 }
			}
			enumaReturn = ArrayListZZZ.toEnumForGroupArray(listae);
		}
		return enumaReturn;
	}
	
	
	
		
	//++++++++++++++++++++++++++++++						
	//++++++++++++++++++++++++++++++
	private static <E extends Enum> E[] getEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumStatusLocalName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			ArrayList<E> listae = new ArrayList<E>();
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumStatusLocalName)) {
					Object[] obja = objClass.getEnumConstants();
					for(Object obj : obja) {
						Enum e = (Enum) obj;
						listae.add((E) e);
					}
				 }
			}
			enumaReturn = ArrayListZZZ.toEnumArray(listae);
		}
		return enumaReturn;
	}
	
	private static <E extends Enum> E getEnumStatusLocal(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumFlagZName)) {
					Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumName);	
					if(e!=null) {
						enumReturn = (E) e;										
						break main;
					}
				 }
			}
		}//end main:
		return enumReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	

}
