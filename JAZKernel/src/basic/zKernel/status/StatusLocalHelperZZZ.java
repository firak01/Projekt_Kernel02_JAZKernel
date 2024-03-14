package basic.zKernel.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
	
	public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListDirectAvailable(Class cls)  throws ExceptionZZZ {
		return getStatusLocalEnumListDirectAvailable_(cls, true);
	}
	
	private static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListDirectAvailable_(Class cls, boolean bLocal)  throws ExceptionZZZ {
		ArrayList<Collection<? extends Enum<?>>> listasReturn = new ArrayList<Collection<? extends Enum<?>>>();
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
//			for(Enum objEnum : enuma) {
//				String sEnum = objEnum.name();
//				if(!listasReturn.contains(sEnum)) {
//					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//					listasReturn.add(sEnum);
//				}
//			}	
			
			for(Enum objEnum : enuma) {
				listasReturn.add((Collection<? extends Enum<?>>) objEnum);
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
//				for(Enum objEnum : enumaByInterface) {
//					String sEnum = objEnum.name();
//					if(!listasReturn.contains(sEnum)) {
//						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//						listasReturn.add(sEnum);
//					}
//				}
				
				for(Enum objEnum : enuma) {
					listasReturn.add((Collection<? extends Enum<?>>) objEnum);
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
	
	public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls)  throws ExceptionZZZ {
		return getStatusLocalEnumListInheritedAvailable_(cls, true);
	}
	
	private static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable_(Class cls, boolean bLocal)  throws ExceptionZZZ {
		ArrayList<Collection<? extends Enum<?>>> listasReturn = new ArrayList<Collection<? extends Enum<?>>>();
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
//			for(Enum objEnum : enuma) {
//				String sEnum = objEnum.name();
//				if(!listasReturn.contains(sEnum)) {
//					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//					listasReturn.add(sEnum);
//				}
//			}	
								
			for(Enum objEnum : enuma) {
				listasReturn.add((Collection<? extends Enum<?>>) objEnum);
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
//				for(Enum objEnum : enumaByInterface) {
//					String sEnum = objEnum.name();
//					if(!listasReturn.contains(sEnum)) {
//						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//						listasReturn.add(sEnum);
//					}
//				}
				
				for(Enum objEnum : enumaByInterface) {
					listasReturn.add((Collection<? extends Enum<?>>) objEnum);
				}
			}
		}
		
		//3. Hole die Elternklassen.
		ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		for(Class<?> objclsSuper : listaClassSuper) {
			//!!!Rekursion
//			ArrayList<String> listaFlagByClassSuper = null;
//			if(bLocal) {
//				listaFlagByClassSuper=StatusLocalHelperZZZ.getStatusLocalListInheritedAvailable(objclsSuper);
//			}else {
//				//listaFlagByClassSuper=StatusLocalHelperZZZ.getFlagsZListInheritedAvailable(objclsSuper);
//			}
//			for(String sEnum : listaFlagByClassSuper) {
//				if(!listasReturn.contains(sEnum)) {
//					listasReturn.add(sEnum);
//				}
//			}
			
			ArrayList<Collection<? extends Enum<?>>> listaFlagByClassSuper = null;
			if(bLocal) {
				listaFlagByClassSuper=StatusLocalHelperZZZ.getStatusLocalEnumListInheritedAvailable(objclsSuper);
			}else {
				//listaFlagByClassSuper=StatusLocalHelperZZZ.getFlagsZListInheritedAvailable(objclsSuper);
			}
//			for(String sEnum : listaFlagByClassSuper) {
//				if(!listasReturn.contains(sEnum)) {
//					listasReturn.add(sEnum);
//				}
//			}
			
			for(Collection<? extends Enum<?>> objEnum : listaFlagByClassSuper) {
				if(!listasReturn.contains(objEnum)) {
					listasReturn.add(objEnum);
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
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls)  throws ExceptionZZZ {
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
	
	public static String[] getEnumStatusLocalInheritedAvailable(Class cls)  throws ExceptionZZZ {
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
			//TODOGOON20240310;
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
		//ArrayList<String> listasDirect = StatusLocalHelperZZZ.getStatusLocalListDirectAvailable(cls);
		ArrayList<Collection<? extends Enum<?>>> listasDirect = StatusLocalHelperZZZ.getStatusLocalEnumListDirectAvailable(cls);
		
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		//ArrayList<String> listasInterface = new ArrayList<String>();
		//kann nicht instanziiert werden... ArrayList<? extends Enum<?>> listasInterface = new ArrayList<? extends Enum<?>>();
		//ArrayList<Collection<? extends Enum<?>>> listasInterface = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<? extends Enum<?>> listasInterface = new ArrayList();
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
			
			
		}
		//TODOGOON20240310;
		
		
		//3. von den Elternklassen der Klasse implementiert
		//ArrayList<String> listasParent = new ArrayList<String>();
		ArrayList<Collection<? extends Enum<?>>> listasParent = new ArrayList<Collection<? extends Enum<?>>>();
		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
		for(Class objcls : listaobjClass) {
			//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
			//ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListDirectAvailable(objcls);
			
			//Von der Vererbungshierarchie des Interface implementiert.
			//ArrayList<String> listasTemp = StatusLocalHelperZZZ.getStatusLocalListInheritedAvailable(objcls);
			//listasParent.addAll(listasTemp);
			
			ArrayList<Collection<? extends Enum<?>>> listasTemp = StatusLocalHelperZZZ.getStatusLocalEnumListInheritedAvailable(objcls);
			for(Collection<? extends Enum<?>> objEnum : listasTemp) {
				listasParent.add(objEnum);
			}
		}
		
		listasReturn.addAll(listasDirect);
		listasReturn.addAll(listasParent);
		//listasReturn.addAll(listasInterface);
		listasReturn = (ArrayList<Collection<? extends Enum<?>>>) ArrayListZZZ.unique(listasReturn);
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
		return StatusLocalHelperZZZ.getEnumStatusLocalMapped(classToCheck, sEnumName, true);
	}
	
	
	private static <E extends IEnumSetMappedStatusZZZ> E getEnumStatusLocalMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterface) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			//+++++++++++++++++++++++++++++++++++
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);			
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
			
			//+++++++++++++++++++++++++++++++++++
			if(bScanInterface) {
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
	
	public static <E extends IEnumSetMappedStatusZZZ> E[] getEnumStatusLocalMapped(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalHelperZZZ.getEnumStatusLocalMapped(classToCheck, true);
	}
	
	public static <E extends IEnumSetMappedStatusZZZ> E[] getEnumStatusLocalMapped(Class<?> classToCheck, boolean bScanInterface) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			//Suche nun nach einem Wert in den Eingebetteten Klassen, der per Innere Klasse eingebunden wird.			
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";			
			
			//+++++++++++++++++++++++++++++++++
			//Holle alle eingebetteten Klassen aus Klassen
			E[] enumaReturnByClass = null;
			//if(classToCheck.isInterface()) {
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				Object[] objEnum = classToCheck.getEnumConstants();
				enumaReturnByClass = (E[])objEnum; 		    	
			}else {
				ArrayList<Class<?>> listaClassByClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
								
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
			
			if(bScanInterface) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces	
				//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);								
				ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
				
				E[] enumaReturnByInterface = null;
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
				enumaReturnByInterface = ArrayListZZZ.toEnumForGroupArray(listaeByInterface);
				
				//Verbinde beides
				enumaReturn = ArrayUtilZZZ.join(enumaReturnByClass, enumaReturnByInterface);
			}else {
				enumaReturn = enumaReturnByClass;
			}
		}
		return enumaReturn;
	}
							
	//++++++++++++++++++++++++++++++
	private static <E extends Enum> E[] getEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalHelperZZZ.getEnumStatusLocal(classToCheck, true);
	}
	
	
	private static <E extends Enum> E[] getEnumStatusLocal(Class<?> classToCheck, boolean bScanInterface) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			//++++++++++++++++++++++++++++++
			E[] enumaReturnByClass = null;
			//if(classToCheck.isInterface()) {
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
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
			
			
			
			if(bScanInterface) {
			//+++++++++++++++++++++++++++++++++
			//Holle alle eingebetteten Klassen aus Interfaces	
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
				
				E[] enumaReturnByInterface = ArrayListZZZ.toEnumArray(listaeByInterface);
				
				//Verbinde beides
				enumaReturn = ArrayUtilZZZ.join(enumaReturnByClass, enumaReturnByInterface);
				
			}else {
				enumaReturn = enumaReturnByClass;
				
			}//end if
		}//end main:
		return enumaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++	
	private static <E extends Enum> E getEnumStatusLocal(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return StatusLocalHelperZZZ.getEnumStatusLocal(classToCheck, sEnumName, true);
	}
	
	private static <E extends Enum> E getEnumStatusLocal(Class<?> classToCheck, String sEnumName, boolean bScanInterface) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
						
			//if(classToCheck.isInterface()) {
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
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
			if(bScanInterface) {
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
	private static <E extends Enum> ArrayList<E> getEnumListStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
		return StatusLocalHelperZZZ.getEnumListStatusLocal(classToCheck,true);
	}

	private static <E extends Enum> ArrayList<E> getEnumListStatusLocal(Class<?> classToCheck, boolean bScanInterface) throws ExceptionZZZ {
		ArrayList<E> listaReturn = new ArrayList<E>();
		main:{
			String sEnumStatusLocalNameInner = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "STATUSLOCAL";
			
			ArrayList<E> listaeReturnByClass = new ArrayList<E>();
			//if(classToCheck.isInterface()) {
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				Object[] objaEnum = classToCheck.getEnumConstants();
				for(Object obj : objaEnum) {
					Enum e = (Enum) obj;
					listaeReturnByClass.add((E) e);
				} 
			}else {
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
								
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
			
			
			if(bScanInterface) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces	
				//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);
				ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
			
				ArrayList<E> listaeReturnByInterface = new ArrayList<E>();
				for(Class objClassInterface : listaInterfaceByClass) {
					
					//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
					ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
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
				}//end for
				
				//E[] enumaReturnByInterface = ArrayListZZZ.toEnumArray(listaeReturnByInterface);
			
				//Verbinde beides
				listaReturn = ArrayListZZZ.join(listaeReturnByClass, listaeReturnByInterface);
				
			}else {
				listaReturn = listaeReturnByClass;
				
			}//end if
			
		}//end main:
		return listaReturn;
	}
}
