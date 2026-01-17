package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.log.IEnumSetMappedLogStringFormatZZZ;

import static basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ.sENUMNAME;

public class EnumMappedStatusAvailableHelperZZZ  implements IConstantZZZ{
	//+++++++++++++++++++++++++
	//+++++++++++++++++++++++++
	//Hier wird in einer beliebige (!!!!) Klasse nach dem Enum mit dem Namen gesucht. 
	//Darum ist der Returnwert vorgegeben, aber die Klasse ist <?>
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, true, true);
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, true);
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}
	
	private static <E extends Enum<E> & IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ searchEnumMapped_(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			String sEnumInnerName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
			
			//+++++++++++++++++++++++++++++++++++
			//1. Direkte die Klasse abfragen
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			if(!ArrayListUtilZZZ.isEmpty(listaClass)) {				
				for(Class objClass : listaClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumInnerName)) {
						IEnumSetMappedZZZ e = EnumHelperZZZ.getEnumAsField(objClass, sEnumInnerName);	
						if(e!=null) {
							if(e.getName().equals(sEnumPropertyName)) {
								enumReturn = (E) e;										
								break main;
							}
						}
					 }
				}
			}
			
			//+++++++++++++++++++++++++++++++++++
			//2. Hole die Interfaces
			if(bScanInterfaceImmidiate) {
				//Achtung, das holt alle moeglichen Interfaces, aber nicht die Interfaces aus denen Interface erbt.
				//ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(classToCheck);
								
				ArrayList<Class<?>> listaInterfaceByClass=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(classToCheck, listaInterfaceByClass);
				
				for(Class objClass : listaInterfaceByClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumInnerName)) {
						IEnumSetMappedZZZ e = EnumHelperZZZ.getEnumAsField(objClass, sEnumInnerName);	
						if(e!=null) {
							if(e.getName().equals(sEnumPropertyName)) {
								enumReturn = (E) e;										
								break main;
							}
						}
					 }
				}
				
			}
			
		
			//+++++++++++++++++++++++++++++++++++
			//3. Hole die Elternklassen.
			if(bScanParentClassImmidiate) {
				ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(classToCheck);
				for(Class<?> objclsSuper : listaClassSuper) {
					//Rekursion durchfuehren !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					enumReturn = (E) EnumMappedAvailableHelperZZZ.searchEnumMapped(objclsSuper,sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
					if(enumReturn!=null) break main;
				}
			}
			
		}//end main:
		return enumReturn;
	}


	//+++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++
	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Class objClass, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedByName_(objClass, sEnumName, sEnumPropertyName, true);		
	}

	
	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Class objClass, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedByName_(objClass, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate);		
	}

	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Object objForClass, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sEnumName, sEnumPropertyName, true);
	}

	public static IEnumSetMappedStatusZZZ searchEnumMappedByName(Object objForClass, String sEnumName,  String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate);
	}
	
	private static IEnumSetMappedStatusZZZ searchEnumMappedByName_(Class objClass, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			if(objClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			HashMap<String,IEnumSetMappedStatusZZZ> hmStatus = EnumMappedStatusAvailableHelperZZZ.searchHashMapEnumMapped(objClass, sEnumName, bScanInterfaceImmidiate);
			if(hmStatus==null)break main;
			
			IEnumSetMappedStatusZZZ objBooleanMessage = hmStatus.get(sEnumPropertyName);
			if(objBooleanMessage==null) break main;
			
			objReturn = (IEnumSetMappedStatusZZZ) objBooleanMessage; //objBooleanMessage.getEnumObject();
		
		}//end main:
		return objReturn;		
	}

	private static IEnumSetMappedStatusZZZ searchEnumMappedByName_(Object objForClass, String sEnumName,  String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "Enum String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumPropertyName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumProperty String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class<?> objClass = objForClass.getClass();
			ArrayList<IEnumSetMappedStatusZZZ> listae = EnumMappedStatusAvailableHelperZZZ.searchEnumMappedList(objClass, sEnumName, bScanInterfaceImmidiate);
			if(ArrayListUtilZZZ.isEmpty(listae)) break main;
			
			for(IEnumSetMappedStatusZZZ obje : listae) {
				String sName = obje.getName();
				if(sEnumPropertyName.equals(sName)) {
					objReturn = obje;
					break main;
				}
			}
		
		}//end main:
		return objReturn;		
	}

	//####################################################################
	//####################################################################
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Object objForClass)  throws ExceptionZZZ {
		Class<?> cls = objForClass.getClass();
		String sEnumName = sENUMNAME;
		E[] enumaReturn = searchEnumMappedArray_(cls, sEnumName, false, false);
		return enumaReturn;
	}
	 
	//++++++++++++++++++++++
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Class<?> cls)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		E[] enumaReturn = searchEnumMappedArray_(cls, sEnumName, false, false);
		return enumaReturn;
	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		E[] enumaReturn = searchEnumMappedArray_(cls, sEnumName, bScanInterfaceImmidiate, false);
		return enumaReturn;
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Class<?> cls, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		E[] enumaReturn = searchEnumMappedArray_(cls, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);
		return enumaReturn;
	}
	
	//+++++++++++++++++++++++
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Object objForClass, String sEnumName)  throws ExceptionZZZ {
		Class<?> cls = objForClass.getClass();
		E[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, false, false);
		return enumaReturn;
	}
	 
	//++++++++++++++++++++++++
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		E[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, false, false);
		return enumaReturn;
	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		E[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, bScanInterfaceImmidiate, false);
		return enumaReturn;
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate)  throws ExceptionZZZ {
		E[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);
		return enumaReturn;
	}
	
	private static <E extends Enum<E> & IEnumSetMappedStatusZZZ> E[] searchEnumMappedArray_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate) throws ExceptionZZZ{
		E[] enumaReturn = null;
		main:{
			if(classToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			ArrayList<E> listae = EnumAvailableHelperZZZ.searchEnumList(classToCheck, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);					
			enumaReturn = ArrayListUtilZZZ.toEnumArray(listae);
		}//end main:
		return enumaReturn;
	}

	
	//####################################################################
	//####################################################################
	//Aber wir durchsuchen nun einmal beliebige Klassen
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(Class<?> cls)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, true, true);
	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, true, true);
	}

	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	//public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, bScanInterfaceImmidiate, true);
	}

	//public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate)  throws ExceptionZZZ {
	//public static <E extends IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate)  throws ExceptionZZZ {
	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate)  throws ExceptionZZZ {
		return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}

	//private static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
			//private static <E extends IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
			//Das ? ist unspezifisch, ohne ? funktioniert wenigstens der Compiler, ob es dann auch zur Laufzeit funktioniert????
			static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
				ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
				main:{
				if(cls==null) {
					 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					 throw ez;
				}
				
	//			//1. von der Classe selbst implementiert		
				Enum[] enuma = EnumAvailableHelperZZZ.searchEnumArray(cls,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
				listaeReturn = EnumUtilZZZ.toArrayListMapped(enuma);
				
				//ArrayList<IEnumSetMappedZZZ>listaeByDirect=null;
				ArrayList<IEnumSetMappedStatusZZZ>listaeByDirect=null;
				if(!ArrayUtilZZZ.isNull(enuma)) {	
					//listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
					for(Enum objEnum : enuma) {				
						//IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;				
						E e = (E) objEnum;
						if(!listaeReturn.contains(e)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listaeReturn.add(e);
						}
					}			
				}
				
						
				//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
				//          Es fehlen hier die Interfaces extends Interface, usw.
				
				//2. allen Interfaces der Klasse, auch den "Elterninerfaces"
				//In der scanInterfacesSuper-Methode meiner ReflectClass-Klasse wird folgendes beachtet.https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				//ArrayList<IEnumSetMappedZZZ>listaeByInterface=null;
				ArrayList<IEnumSetMappedStatusZZZ>listaeByInterface=null;
				if(bScanInterfaceImmediate) {
					ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
					ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);
					for(Class<?> objclsByInterface : listaInterfaceSuper) {
								
						Enum[] enumaByInterface = EnumAvailableHelperZZZ.searchEnumArray(objclsByInterface, sEnumName,bScanInterfaceImmediate, false);
						
						//ArrayList<IEnumSetMappedZZZ>listaeByInterfaceTemp=null; 
						ArrayList<IEnumSetMappedStatusZZZ>listaeByInterfaceTemp=null;
						if(!ArrayUtilZZZ.isNull(enumaByInterface)) {			
							//listaeByInterfaceTemp = new ArrayList<IEnumSetMappedZZZ>();
							listaeByInterfaceTemp = new ArrayList<IEnumSetMappedStatusZZZ>();
							for(Enum objEnum : enumaByInterface) {						
								//IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;
								E e = (E) objEnum;
								if(!listaeByInterfaceTemp.contains(e)) {
									//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
									listaeByInterfaceTemp.add(e);
								}
							}
						}
						if(!ArrayListUtilZZZ.isEmpty(listaeByInterfaceTemp)) {
							//if(ArrayListUtilZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedZZZ>();				
							if(ArrayListUtilZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusZZZ>();
							//listaeByInterface = (ArrayList<IEnumSetMappedZZZ>) ArrayListUtilZZZ.join(listaeByInterface, listaeByInterfaceTemp);
							listaeByInterface = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListUtilZZZ.join(listaeByInterface, listaeByInterfaceTemp);
						}
					}
					
					
					
				}
				
				//3. Hole die Elternklassen.
				//ArrayList<IEnumSetMappedZZZ>listaeByClassSuper=null;
				ArrayList<IEnumSetMappedStatusZZZ>listaeByClassSuper=null;
				if(bScanSuperclassImmediate) {
					ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
				
					for(Class<?> objclsSuper : listaClassSuper) {
						//Rekursion durchfuehren !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						//z.B. ArrayList<Collection<? extends Enum<?>>> listaStatusByClassSuper = new ArrayList<Collection<? extends Enum<?>>>();
						
						//s. ChatGPT vom 20260110
						//ArrayList<IEnumSetMappedZZZ> listaeByClassSuperTemp = EnumAvailableHelperZZZ.searchEnumMappedList(objclsSuper,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
						//ArrayList<E> listaeByClassSuperTemp = EnumAvailableHelperZZZ.searchEnumMappedList(objclsSuper,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
						ArrayList<IEnumSetMappedStatusZZZ> listaeByClassSuperTemp = searchEnumMappedList(cls, sEnumName, bScanInterfaceImmediate);
						//listaeByClassSuper = (ArrayList<IEnumSetMappedZZZ>) ArrayListUtilZZZ.join(listaeByClassSuper, listaeByClassSuperTemp);
						listaeByClassSuper = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListUtilZZZ.join(listaeByClassSuper, (ArrayList<E>) listaeByClassSuperTemp);
					}
				}
				
				if(!ArrayListUtilZZZ.isEmpty(listaeByDirect)) {		
					//if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
					listaeReturn.addAll(listaeByDirect);			
				}
				
				if(!ArrayListUtilZZZ.isEmpty(listaeByInterface)) {
					//if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
					listaeReturn.addAll(listaeByInterface);
				}
				
				if(!ArrayListUtilZZZ.isEmpty(listaeByClassSuper)) {
					//if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
					listaeReturn.addAll(listaeByClassSuper);
				}
				
				//listaeReturn = (ArrayList<IEnumSetMappedZZZ>) ArrayListUtilZZZ.unique(listaeReturn);
				listaeReturn = (ArrayList<IEnumSetMappedStatusZZZ>) ArrayListUtilZZZ.unique(listaeReturn);
			}//end main:
			return listaeReturn;
			}

			public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect(Class<E> cls, String sEnumName)  throws ExceptionZZZ {
				return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedListDirect_(cls, sEnumName, true);
			}

			public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedStatusListDirect(Class<E> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
				return EnumMappedStatusAvailableHelperZZZ.searchEnumMappedListDirect_(cls, sEnumName, bScanInterfaceImmidiate);
			}

			static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> searchEnumMappedListDirect_(Class<E> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {		
				ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = searchEnumMappedList(cls,sEnumName,bScanInterfaceImmidiate, false); 		
				return listaeReturn;
			}

			/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IEnmSetMappdedStatusZZZ>. 
			 * @param classToCheck
			 * @throws ExceptionZZZ
			 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
			 * @param E 
			 */
			//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
			//public static HashMap<String,Collection<? extends IEnumSetMappedZZZ>> getHashMapEnumStatusLocal(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
			//Das ? ist unspezifisch, ohne ? funktioniert wenigstens der Compiler, ob es dann auch zur Laufzeit funktioniert????
			public static HashMap<String, IEnumSetMappedStatusZZZ> searchHashMapEnumMapped(Class classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {		
				HashMap<String, IEnumSetMappedStatusZZZ> hmReturn = null;
				main:{
					
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//						IEnumSetMappedZZZ[] enumaMappedStatus= EnumAvailableHelperZZZ.searchEnumMapped(classToCheck, sEnumName, bScanInterfaceImmidiate);
//						if(enumaMappedStatus==null) break main;
					
					//s. ChatGPT vom 20260110
					//ArrayList<IEnumSetMappedZZZ>listaEnumaMappedStatus= EnumAvailableHelperZZZ.searchEnumMappedList(classToCheck, sEnumName, bScanInterfaceImmidiate);
					List<IEnumSetMappedStatusZZZ> listaEnumaMappedStatus = EnumMappedStatusAvailableHelperZZZ.searchEnumMappedList(classToCheck, sEnumName, bScanInterfaceImmidiate);
					
					if(listaEnumaMappedStatus==null) break main;

					hmReturn = new HashMap<String, IEnumSetMappedStatusZZZ>();			
					for(IEnumSetMappedStatusZZZ objEnumMapped : listaEnumaMappedStatus) {
						String sEnum = objEnumMapped.getName();
						hmReturn.put(sEnum, objEnumMapped);
					}		
				}//end main:
				return hmReturn;
			}
}
