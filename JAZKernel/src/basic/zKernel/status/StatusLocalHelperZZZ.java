package basic.zKernel.status;

import java.lang.reflect.Field;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.enums.EnumHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class StatusLocalHelperZZZ implements IConstantZZZ{
	public static boolean proofStatusLocalExists(Class cls, String sFlagName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sFlagName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "FlagString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
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
				if(StringArrayZZZ.containsIgnoreCase(saFlagAvailable, sFlagName)) {
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
