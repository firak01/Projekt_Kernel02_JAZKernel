package basic.zKernel.flag;

import java.lang.reflect.Field;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zUtil.io.KernelFileZZZ.FLAGZ;

public class FlagZHelperZZZ implements IConstantZZZ{
	public static boolean proofFlagZExists(Class cls, String sFlagName) throws ExceptionZZZ {
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
			
			//Die Klasse selbst
			String[] saFlagAvailable = FlagZHelperZZZ.getFlagsZInheritedAvailable(cls);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
			if(saFlagAvailable!=null) {
				if(StringArrayZZZ.contains(saFlagAvailable, sFlagName)) {
					bReturn = true;	
					break main;
				}
			}
			
			//Alle Elternklassen
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getSuperClasses(cls);
			for(Class<?>objClass : listaClass) {
				String[] saFlagAvailableInherited = FlagZHelperZZZ.getFlagsZInheritedAvailable(objClass); //20210406 das reicht nicht saFlagAvailable = FlagZHelperZZZ.getFlagsZDirectAvailable(objClass);
				if(saFlagAvailableInherited!=null) {
					if(StringArrayZZZ.contains(saFlagAvailableInherited, sFlagName)) {
						bReturn = true;	
						break main;
					}
				}
			}
/*
			//+++++ ZUM DEBUGGEN / EXPERIMENTEIEREN
			//TODOGONN 20200404; //Es fehlen die Elternklassen der Interfaces.
			//ICH SUCHE 
			//interface IPanelCascadedZZZ extends IComponentCascadedUserZZZ
			//beachte https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				
			//Sammler für die Strings
			ArrayList<String> listas = new ArrayList<String>();
			
			Class[] objaclsaByInterface = cls.getInterfaces();
			for(Class objclsByInterface : objaclsaByInterface) {
				Enum[] enumaByInterface = getEnumFlagZ(objclsByInterface);
				if(enumaByInterface!=null) {			
					for(Enum objEnum : enumaByInterface) {
						String sEnum = objEnum.name();
						listas.add(sEnum);
					}			
				}
				
				//Alle Elternklassen DES INTERFACE
				//Aber beachte: https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				ArrayList<Class<?>> listaClassInterfaceInherited = ReflectClassZZZ.getSuperClasses(objclsByInterface);
				for(Class<?>objClassInterfaceInherited : listaClassInterfaceInherited) {
					
					Enum[] enumaByInterfaceInherited = getEnumFlagZ(objClassInterfaceInherited);
					if(enumaByInterfaceInherited!=null) {			
						for(Enum objEnum : enumaByInterfaceInherited) {
							String sEnum = objEnum.name();
							listas.add(sEnum);
						}			
					}
				}
				
				//ALLE INTERFACES DES INTERFACE
				//Beachte: https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				ArrayList<Class<?>> listaClassInterfaceInterface = ReflectClassZZZ.getInterfaces(objclsByInterface);
				for(Class<?>objClassInterfaceInterface : listaClassInterfaceInterface) {
					
					Enum[] enumaByInterfaceInterface = getEnumFlagZ(objClassInterfaceInterface);
					if(enumaByInterfaceInterface!=null) {			
						for(Enum objEnum : enumaByInterfaceInterface) {
							String sEnum = objEnum.name();
							listas.add(sEnum);
							System.out.println("1. " + sEnum);
						}			
					}
				}
				
			}
			
			
			//Hole die Elternklassen 
			ArrayList<Class<?>> listaClass2 = ReflectClassZZZ.getSuperClasses(cls);
			for(Class<?>objClass : listaClass2) {
				
				
				Class[] objaclsaByInterface2 = objClass.getInterfaces();
				for(Class objclsByInterface : objaclsaByInterface2) {
					Enum[] enumaByInterface = getEnumFlagZ(objclsByInterface);
					if(enumaByInterface!=null) {			
						for(Enum objEnum : enumaByInterface) {
							String sEnum = objEnum.name();
							listas.add(sEnum);
							System.out.println("2. " + sEnum);
						}			
					}
					
					//Alle Elternklassen DES INTERFACE
					ArrayList<Class<?>> listaClassInterfaceInherited = ReflectClassZZZ.getSuperClasses(objclsByInterface);
					for(Class<?>objClassInterfaceInherited : listaClassInterfaceInherited) {
						
						Enum[] enumaByInterfaceInherited = getEnumFlagZ(objClassInterfaceInherited);
						if(enumaByInterfaceInherited!=null) {			
							for(Enum objEnum : enumaByInterfaceInherited) {
								String sEnum = objEnum.name();
								listas.add(sEnum);
								System.out.println("3. " + sEnum);
							}			
						}
					}
					
					//ALLE INTERFACES DES INTERFACE
					//Beachte: https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
					ArrayList<Class<?>> listaClassInterfaceInterface = ReflectClassZZZ.getInterfaces(objclsByInterface);
					for(Class<?>objClassInterfaceInterface : listaClassInterfaceInterface) {
						
						Enum[] enumaByInterfaceInterface = getEnumFlagZ(objClassInterfaceInterface);
						if(enumaByInterfaceInterface!=null) {			
							for(Enum objEnum : enumaByInterfaceInterface) {
								String sEnum = objEnum.name();
								listas.add(sEnum);
								System.out.println("4. " + sEnum);
							}			
						}
					}
				}
			}
			*/
		}//end main:
		return bReturn;
	}
	
	public static ArrayList<String> getFlagsZListDirectAvailable(Class cls)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = getEnumFlagZ(cls);
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
			Enum[] enumaByInterface = getEnumFlagZ(objclsByInterface);
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
	
	public static ArrayList<String> getFlagsZListInheritedAvailable(Class cls)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = getEnumFlagZ(cls);
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
			Enum[] enumaByInterface = getEnumFlagZ(objclsByInterface);
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
	
	public static ArrayList<String> getFlagsZList(Class cls) throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		ArrayList<String> listasDirect = FlagZHelperZZZ.getFlagsZListDirectAvailable(cls);
				
		//2. von den Elternklassen der Klasse implementiert
		ArrayList<String> listasParent = new ArrayList<String>();		
		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
		for(Class objcls : listaobjClass) {
			//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
			//ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListDirectAvailable(objcls);
			
			//Von der Vererbungshierarchie des Interface implementiert.
			ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListInheritedAvailable(objcls);
			
			listasParent.addAll(listasTemp);
		}
		
		listasReturn.addAll(listasDirect);
		listasReturn.addAll(listasParent);
	}//end main:
	return listasReturn;
	}
	
	public static String[] getFlagsZ(Class cls) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}

		ArrayList<String> listas = getFlagsZList(cls);
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	
	public static String[] getFlagsZDirectAvailable(Class cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = FlagZHelperZZZ.getFlagsZListDirectAvailable(cls);			
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
		
		public static String[] getFlagsZInheritedAvailable(Class cls)  throws ExceptionZZZ {
			String[] saReturn = null;
			main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			ArrayList<String> listas = FlagZHelperZZZ.getFlagsZListInheritedAvailable(cls);					
			saReturn = ArrayListZZZ.toStringArray(listas);
		}//end main:
		return saReturn;
		}
	
	
	
	
	private static <E extends Enum> E[] getEnumFlagZ(Class<?> classToCheck) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "FLAGZ";
			
			ArrayList<E> listae = new ArrayList<E>();
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumFlagZName)) {
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
	
	private static <E extends Enum> E getEnumFlagZ(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "FLAGZ";
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumFlagZName)) {
					Enum e = getEnumAsField(objClass, sEnumName);	
					if(e!=null) {
						enumReturn = (E) e;										
						break main;
					}
				 }
			}
		}//end main:
		return enumReturn;
	}
	
	//private static <E extends Enum> E[] getEnumValues(Class<E> enumClass, String sFieldname) throws ExceptionZZZ {
	private static <E extends Enum> E getEnumAsField(Class<E> enumClass, String sFieldname) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			try {
				//Auflisten aller Felder
//				Field[] fa = enumClass.getDeclaredFields();
//				for(Field f : fa) {
//					 System.out.println(f);
//					 System.out.println(f.getName());
//				     System.out.println(Modifier.toString(f.getModifiers()));
//				}
				
				//Zugriff auf ein konkretes Enum
			    Field f = enumClass.getDeclaredField(sFieldname);
//		        System.out.println(f);
//		        System.out.println(Modifier.toString(f.getModifiers()));
		        f.setAccessible(true);
		        Object o = f.get(null);
		        enumReturn = (E) o;
		        //return (E[]) o;
			 }catch (NoSuchFieldException nsfe) {
				 //Keine Exception werfen, dann ist das Ergebnis einfach NULL
//				ExceptionZZZ ez = new ExceptionZZZ("NoSuchFieldException", iERROR_RUNTIME, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName(), nsfe);
//		    	throw ez;
		    } catch (IllegalAccessException iae) {
		    	ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException", iERROR_RUNTIME, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName(), iae);
		    	throw ez;
		    }			
		}
		return enumReturn;
	}

}
