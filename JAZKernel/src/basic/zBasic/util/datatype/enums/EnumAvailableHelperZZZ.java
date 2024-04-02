package basic.zBasic.util.datatype.enums;

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
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.json.FlagZHelperZZZ;
import basic.zKernel.status.IStatusBooleanMessageZZZ;
import basic.zKernel.status.IStatusLocalMessageUserZZZ;
import basic.zKernel.status.StatusBooleanMessageZZZ;
import basic.zKernel.status.StatusLocalAvailableHelperZZZ;

/**Siehe auch StatuslLocalAvailableHelperZZZ
 *  
 * 
 * @author Fritz Lindhauer, 27.03.2024, 19:44:32
 * 
 */
public class EnumAvailableHelperZZZ implements IConstantZZZ{	
		
	public static IEnumSetMappedZZZ searchEnumMappedByName(Object objForClass, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sEnumName, sEnumPropertyName, true);
	}

	public static IEnumSetMappedZZZ searchEnumMappedByName(Object objForClass, String sEnumName,  String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate);
	}
	
	private static IEnumSetMappedZZZ searchEnumMappedByName_(Object objForClass, String sEnumName,  String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedZZZ objReturn = null;
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
			
			Class objClass = objForClass.getClass();
			ArrayList<IEnumSetMappedZZZ> listae = EnumAvailableHelperZZZ.searchEnumMappedList(objClass, sEnumName, bScanInterfaceImmidiate);
			if(ArrayListZZZ.isEmpty(listae)) break main;
			
			for(IEnumSetMappedZZZ obje : listae) {
				String sName = obje.getName();
				if(sEnumPropertyName.equals(sName)) {
					objReturn = obje;
					break main;
				}
			}
		
		}//end main:
		return objReturn;		
	}
	
	//+++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++
	public static IEnumSetMappedZZZ searchEnumMappedByName(Class objClass, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMappedByName_(objClass, sEnumName, sEnumPropertyName, true);		
	}
	
	public static IEnumSetMappedZZZ searchEnumMappedByName(Class objClass, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMappedByName_(objClass, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate);		
	}
	
	private static IEnumSetMappedZZZ searchEnumMappedByName_(Class objClass, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedZZZ objReturn = null;
		main:{
			if(objClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			HashMap<String,IEnumSetMappedZZZ> hmStatus = EnumAvailableHelperZZZ.searchHashMapEnumMapped(objClass, sEnumName, bScanInterfaceImmidiate);
			if(hmStatus==null)break main;
			
			IEnumSetMappedZZZ objBooleanMessage = hmStatus.get(sEnumPropertyName);
			if(objBooleanMessage==null) break main;
			
			objReturn = (IEnumSetMappedZZZ) objBooleanMessage; //objBooleanMessage.getEnumObject();
		
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
						
			//1. existiert der Statuswert schon in der HashMap
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
	
	
	public static boolean proofExists(Class<?> cls, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumPropertyName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumPropertyName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
					
			//Die Klasse selbst und alle Elternklassen, sowie deren Interface. Achtung, Rekursion wird darin verwendet!
			String[] saAvailable = EnumAvailableHelperZZZ.searchInherited(cls, sEnumName);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
			if(saAvailable!=null) {
				if(StringArrayZZZ.containsIgnoreCase(saAvailable, sEnumPropertyName)) {
					bReturn = true;	
					break main;
				}
			}												
		}//end main:
		return bReturn;
	}
			
	public static boolean proofDirectExists(Class<?> cls, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumPropertyName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumPropertyName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			//NUR die Klasse selbst
			String[] saStatusAvailable = EnumAvailableHelperZZZ.searchDirect(cls, sEnumName);			
			if(saStatusAvailable!=null) {
				if(StringArrayZZZ.contains(saStatusAvailable, sEnumPropertyName)) {
					bReturn = true;	
					break main;
				}
			}						
		}//end main:
		return bReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++
	public static ArrayList<String> searchListDirect(Class cls, String sEnumName)  throws ExceptionZZZ {
		return searchListDirect_(cls, sEnumName, true, true);
	}
	
	public static ArrayList<String> searchListDirect(Class cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchListDirect_(cls, sEnumName, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<String> searchListDirect(Class cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		return searchListDirect_(cls, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	}
	
	private static ArrayList<String> searchListDirect_(Class cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		ArrayList<String>listasReturn = null;
		main:{
			Enum[]enuma = EnumAvailableHelperZZZ.searchEnum(cls, sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
			if(ArrayUtilZZZ.isEmpty(enuma))break main;
			
			String[] saReturn = EnumUtilZZZ.toString(enuma);
			listasReturn = StringArrayZZZ.toArrayList(saReturn);
			
		}//end main:
		return listasReturn;
	}
	
	//++++++++++++++++++++++++++
	//++++++++++++++++++++++++++	
	public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedListDirect(Class cls, String sEnumName)  throws ExceptionZZZ {
		return searchEnumMappedListDirect_(cls, sEnumName, true);
	}
	
	public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedListDirect(Class cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedListDirect_(cls, sEnumName, bScanInterfaceImmidiate);
	}
	
	private static ArrayList<IEnumSetMappedZZZ> searchEnumMappedListDirect_(Class cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {		
		ArrayList<IEnumSetMappedZZZ> listaeReturn = EnumAvailableHelperZZZ.searchEnumMappedList(cls,sEnumName,bScanInterfaceImmidiate, false); 
		return listaeReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
//	public static IEnumSetMappedZZZ[] searchEnumMappedInherited(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
//		return searchEnumMappedInherited_(cls, sEnumName, true, true);
//	}
//	
//	public static IEnumSetMappedZZZ[] searchEnumMappedInherited(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
//		return searchEnumMappedInherited_(cls, sEnumName, bScanInterfaceImmidiate, true);
//	}
//
//	public static IEnumSetMappedZZZ[] searchEnumMappedInherited(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImidiate)  throws ExceptionZZZ {
//		return searchEnumMappedInherited_(cls, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImidiate);
//	}
//	
//	private static IEnumSetMappedZZZ[] searchEnumMappedInherited_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImidiate)  throws ExceptionZZZ {	
//		IEnumSetMappedZZZ[] objaeReturn = null;
//		main:{
//		
//		ArrayList<IEnumSetMappedZZZ>listaeReturn=EnumAvailableHelperZZZ.searchEnumMappedListInherited(cls, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImidiate);
//		if(listaeReturn==null)break main;
//		
//		objaeReturn = ArrayListZZZ.toEnumMappedArray(listaeReturn);
//		
//	}//end main:
//	return objaeReturn;
//	}
//			
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++
	public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		return searchEnumMappedList_(cls, sEnumName, true, true);
	}
	
	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedList_(cls, sEnumName, bScanInterfaceImmidiate, true);
	}
	

	public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate)  throws ExceptionZZZ {
		return searchEnumMappedList_(cls, sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}	
	
	private static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		ArrayList<IEnumSetMappedZZZ> listaeReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
//		//1. von der Classe selbst implementiert		
		Enum[] enuma = searchEnum(cls,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
		listaeReturn = EnumUtilZZZ.toArrayListMapped(enuma);
		
		ArrayList<IEnumSetMappedZZZ>listaeByDirect=null;
		if(!ArrayUtilZZZ.isEmpty(enuma)) {	
			listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
			for(Enum objEnum : enuma) {				
				IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;				
				if(!listaeReturn.contains(e)) {
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
					listaeReturn.add(e);
				}
			}			
		}
		
				
		//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
		//          Es fehlen hier die Interfaces extends Interface, usw.
		
		//2. allen Interfaces der Klasse, auch den "Elterninerfaces"
		ArrayList<IEnumSetMappedZZZ>listaeByInterface=null;
		if(bScanInterfaceImmediate) {
			ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);
			for(Class<?> objclsByInterface : listaInterfaceSuper) {
						
				Enum[] enumaByInterface = searchEnum(objclsByInterface, sEnumName,bScanInterfaceImmediate, false);
				
				ArrayList<IEnumSetMappedZZZ>listaeByInterfaceTemp=null; 
				if(!ArrayUtilZZZ.isEmpty(enumaByInterface)) {			
					listaeByInterfaceTemp = new ArrayList<IEnumSetMappedZZZ>();
										
					for(Enum objEnum : enumaByInterface) {						
						IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;
						if(!listaeByInterfaceTemp.contains(e)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listaeByInterfaceTemp.add(e);
						}
					}
				}
				if(!ArrayListZZZ.isEmpty(listaeByInterfaceTemp)) {
					if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedZZZ>();				
					listaeByInterface = ArrayListZZZ.join(listaeByInterface, listaeByInterfaceTemp);
				}
			}
			
			
			
		}
		
		//3. Hole die Elternklassen.
		ArrayList<IEnumSetMappedZZZ>listaeByClassSuper=null;
		if(bScanSuperclassImmediate) {
			ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
		
			for(Class<?> objclsSuper : listaClassSuper) {
				//Rekursion durchfuehren !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				//ArrayList<Collection<? extends Enum<?>>> listaStatusByClassSuper = new ArrayList<Collection<? extends Enum<?>>>();
				ArrayList<IEnumSetMappedZZZ> listaeByClassSuperTemp = EnumAvailableHelperZZZ.searchEnumMappedList(objclsSuper,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
				listaeByClassSuper = ArrayListZZZ.join(listaeByClassSuper, listaeByClassSuperTemp);
			}
		}
		
		if(!ArrayListZZZ.isEmpty(listaeByDirect)) {		
			if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
			listaeReturn.addAll(listaeByDirect);
		}
		
		if(!ArrayListZZZ.isEmpty(listaeByInterface)) {
			if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
			listaeReturn.addAll(listaeByInterface);
		}
		
		if(!ArrayListZZZ.isEmpty(listaeByClassSuper)) {
			if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
			listaeReturn.addAll(listaeByClassSuper);
		}
		
		listaeReturn = (ArrayList<IEnumSetMappedZZZ>) ArrayListZZZ.unique(listaeReturn);
	}//end main:
	return listaeReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++
	public static ArrayList<String> searchList(Class<?> cls, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchList_(cls, sEnumName, true, true);		
	}
	
	public static ArrayList<String> searchList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchList_(cls, sEnumName, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<String> searchList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchList_(cls, sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}
	
	private static ArrayList<String> searchList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		ArrayList<String> listasByDirect = EnumAvailableHelperZZZ.searchListDirect(cls,sEnumName,bScanInterfaceImmidiate);
				
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<String> listasByInterface = null;
		if(bScanInterfaceImmidiate) {
			//Achtung, das holt alle moeglichen Interfaces, aber nicht die Interfaces aus denen Interface erbt.
			//ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(classToCheck);
			
			ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
			for(Class<?> objclsByInterface : listaClassInterface) {
				Enum[] enumaByInterface = searchEnum(objclsByInterface, sEnumName, false, false);//false, weil ja die Interfaces eh betrachtet werde sollen
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
		//!!! Rekursion
		ArrayList<String> listasByParent = new ArrayList<String>();	
		if(bScanParentClassImmidiate) {
			ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
			for(Class objcls : listaobjClass) {
				//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
				//Darum: Von der Vererbungshierarchie des Interface implementiert.
				ArrayList<String> listasByParentTemp = EnumAvailableHelperZZZ.searchList(objcls, sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
				if(!ArrayListZZZ.isEmpty(listasByParentTemp)) {
					if(ArrayListZZZ.isEmpty(listasByParent)) listasByParent = new ArrayList<String>();				
					listasByParent.addAll(listasByParentTemp);
				}			
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
	
	public static String[] search(Class<?> cls, String sEnumName) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		ArrayList<String> listas = searchList(cls, sEnumName);
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] searchDirect(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = EnumAvailableHelperZZZ.searchListDirect(cls, sEnumName);			
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
		
	public static String[] searchInherited(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = EnumAvailableHelperZZZ.searchList(cls, sEnumName);					
		saReturn = ArrayListZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
			
			
	//++++++++++++++++++++++++++++++++++++++++
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IEnmSetMappdedStatusZZZ>. 
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IEnumSetMappedZZZ> searchHashMapEnumMapped(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchHashMapEnumMapped(classToCheck, sEnumName, true);
	}
		
	/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IEnmSetMappdedStatusZZZ>. 
	 * @param classToCheck
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
	 * @param E 
	 */
	//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
	public static HashMap<String, IEnumSetMappedZZZ> searchHashMapEnumMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		HashMap<String, IEnumSetMappedZZZ> hmReturn = null;
		main:{
			IEnumSetMappedZZZ[] enumaMappedStatus= EnumAvailableHelperZZZ.searchEnumMapped(classToCheck, sEnumName, bScanInterfaceImmidiate);
			if(enumaMappedStatus==null) break main;
			
			hmReturn = new HashMap<String, IEnumSetMappedZZZ>();
			if(ArrayUtilZZZ.isEmpty(enumaMappedStatus)) break main;
			
			for(IEnumSetMappedZZZ objEnumMapped : enumaMappedStatus) {
				String sEnum = objEnumMapped.getName();
				hmReturn.put(sEnum, objEnumMapped);
			}		
		}//end main:
		return hmReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++
//	 public static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList(Class<?> cls, String sEnumName) throws ExceptionZZZ {
//		 return EnumAvailableHelperZZZ.searchListEnumMappedList_(cls, sEnumName, true, true);
//	 }
//	 
//	 public static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
//		 return EnumAvailableHelperZZZ.searchListEnumMappedList_(cls,sEnumName,bScanInterfaceImmidiate, true);
//	 }
//	
//	 public static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
//		 return EnumAvailableHelperZZZ.searchListEnumMappedList_(cls,sEnumName,bScanInterfaceImmidiate, bScanParentClassImmidiate);
//	 }
//	
//	
//	/** s. https://stackoverflow.com/questions/31104584/how-to-save-enum-class-in-a-hashmap
//	 * aber wenn ich ArrayList<Collection<? extends Enum<?>>> verwende, 
//	 * kommt es beim Hinzufügen des Enum - Objekts trotzdem zu einem TypeCast Fehler
//	 * Darum alles auf IEnumSetMappedZZZ umgestellt.
//	 * @param classToCheck
//	 * @throws ExceptionZZZ
//	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
//	 */
//	private static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
//		ArrayList<IEnumSetMappedZZZ> listaeReturn = null;
//		main:{
//			if(cls==null) {
//				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
//				 throw ez;
//			}
//		
//			//+++++++++++++++++++++++++++++++++++++++++++++++
//			//1. von der Classe selbst implementiert
//			ArrayList<IEnumSetMappedZZZ> listaeByDirect = EnumAvailableHelperZZZ.searchEnumMappedListDirect(cls, sEnumName, bScanInterfaceImmidiate);
//		
//		
//			//++++++++++++++++++++++++++++++++++++++++++++++++
//			//2. von den Interfaces implementiert
//			//20240331: Hier fehlen die erbenden interfacss
//			ArrayList<IEnumSetMappedZZZ> listaeByInterface = null;
//			if(bScanInterfaceImmidiate) {
//				//2. allen Interfaces der Klasse, auch den erbenden implementiert
//				//ArrayList<String> listasInterface = new ArrayList<String>();
//				//kann nicht instanziiert werden... ArrayList<? extends Enum<?>> listasInterface = new ArrayList<? extends Enum<?>>();
//				//ArrayList<Collection<? extends Enum<?>>> listasInterface = new ArrayList<Collection<? extends Enum<?>>>();			
//				ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
//				ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
//				for(Class<?> objclsByInterface : listaClassInterface) {					
//					Enum[] enumaByInterface = EnumAvailableHelperZZZ.searchEnum(objclsByInterface, sEnumName, false, false);
//					if(!ArrayUtilZZZ.isEmpty(enumaByInterface)){
//						for(Enum objEnum : enumaByInterface) {
//							if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedZZZ>();
//							IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;
//							listaeByInterface.add(e);
//						}
//					}
//				}
//			}
//		
//			//+++++++++++++++++++++++++++++++++++++++++++++++
//			//3. von den Elternklassen der Klasse implementiert	
//			//!!! Rekursion
//			ArrayList<IEnumSetMappedZZZ> listaeBySuperclass = null;
//			if(bScanSuperclassImidiate) {
//				ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
//				for(Class<?> objcls : listaobjClass) {
//					//Rekursion der interited Klassen und Interfaces einleiten !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!		
//					ArrayList<IEnumSetMappedZZZ> listaeBySuperclassTemp = EnumAvailableHelperZZZ.searchEnumMappedList(objcls,sEnumName,bScanInterfaceImmidiate, bScanSuperclassImidiate);
//					listaeBySuperclass = ArrayListZZZ.join(listaeBySuperclass, listaeBySuperclassTemp);
////					if(!ArrayListZZZ.isEmpty(listaeTemp)) {
////						for(IEnumSetMappedZZZ objEnum : listaeTemp) {
////							if(ArrayListZZZ.isEmpty(listaeByParent)) listaeByParent = new ArrayList<IEnumSetMappedZZZ>();
////							listaeByParent.add(objEnum);
////						}
////					}
//				}
//			}
//		
//			if(!ArrayListZZZ.isEmpty(listaeByDirect)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
//				listaeReturn.addAll(listaeByDirect);
//			}
//			
//			if(!ArrayListZZZ.isEmpty(listaeByInterface)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
//				listaeReturn.addAll(listaeByInterface);
//			}
//			
//			if(!ArrayListZZZ.isEmpty(listaeBySuperclass)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
//				listaeReturn.addAll(listaeBySuperclass);
//			}
//		
//			//listasReturn = (ArrayList<Collection<? extends Enum<?>>>) ArrayListZZZ.unique(listasReturn);
//			listaeReturn = (ArrayList<IEnumSetMappedZZZ>) ArrayListZZZ.unique(listaeReturn);
//		}//end main:
//		return listaeReturn;
//	}
	
	//+++++++++++++++++++++++++
	//+++++++++++++++++++++++++
	public static <E extends IEnumSetMappedZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, true, true);
	}
	
	public static <E extends IEnumSetMappedZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, true);
	}
	
	public static <E extends IEnumSetMappedZZZ> E searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}
	
	private static <E extends IEnumSetMappedZZZ> E searchEnumMapped_(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			String sEnumInnerName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
			
			//+++++++++++++++++++++++++++++++++++
			//1. Direkte die Klasse abfragen
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			if(!ArrayListZZZ.isEmpty(listaClass)) {				
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
					enumReturn = EnumAvailableHelperZZZ.searchEnumMapped(objclsSuper,sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
					if(enumReturn!=null) break main;
				}
			}
			
		}//end main:
		return enumReturn;
	}
	
	
	//++++++++++++++++++++++++
	//++++++++++++++++++++++++
	public static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck,sEnumName, true, true);
	}
	
	public static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck,sEnumName, bScanInterfaceImmidiate, true);
	}
	
	public static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck,sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}
	
	private static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			
			ArrayList<IEnumSetMappedZZZ> listaMappedReturn = EnumAvailableHelperZZZ.searchEnumMappedList(classToCheck, sEnumName,bScanInterfaceImmidiate,bScanParentClassImmidiate);
			if(ArrayListZZZ.isEmpty(listaMappedReturn))break main;
			
			enumaReturn = ArrayListZZZ.toEnumMappedArrayByMapped(listaMappedReturn);	
		}//end main
		return enumaReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
//	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
//		return EnumAvailableHelperZZZ.searchEnum_(classToCheck,sEnumName,true);
//	}
	
//	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
//		return EnumAvailableHelperZZZ.searchEnum_(classToCheck,sEnumName,bScanInterfaceImmidiate);
//	}
	
//	private static <E extends Enum> E[] searchEnum_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
//		E[] objaeReturn = null;
//		main:{
//			if(classToCheck==null) {
//				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
//				 throw ez;
//			}
//			
//			ArrayList<IEnumSetMappedZZZ> listaeDirect = EnumAvailableHelperZZZ.searchEnumMappedListDirect(classToCheck, sEnumName);
//			objaeReturn = ArrayListZZZ.toEnumArrayByMapped(listaeDirect);
//		}//end main:
//		return objaeReturn;
//	}
		
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++
	public static <E extends Enum> E[] searchEnum(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		E[] enumaReturn = searchEnum_(cls,sEnumName, false, false);
		return enumaReturn;
	}
	
	public static <E extends Enum> E[] searchEnum(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		E[] enumaReturn = searchEnum_(cls,sEnumName, bScanInterfaceImmidiate, false);
		return enumaReturn;
	}

	public static <E extends Enum> E[] searchEnum(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate)  throws ExceptionZZZ {
		E[] enumaReturn = searchEnum_(cls,sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);
		return enumaReturn;
	}
	
	
	private static <E extends Enum> E[] searchEnum_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate) throws ExceptionZZZ{
		E[] enumaReturn = null;
		main:{
			if(classToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			ArrayList<E> listae = EnumAvailableHelperZZZ.searchEnumList(classToCheck, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);					
			enumaReturn = ArrayListZZZ.toEnumArray(listae);
		}//end main:
		return enumaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, sEnumPropertyName, true, true);
	}
	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImidiate, true);
	}
	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImidiate, bScanSuperclassImidiate);
	}
	
	private static <E extends Enum> E searchEnum_(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			String sEnumInnerName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
						
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				//z.B. das enum selbst gibt es so:
				//Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;))
		    	//Object[] objEnum = objClassFromInterface.getEnumConstants();
		    	//IEnumSetMappedZZZ[] enumaByInterface3 = (IEnumSetMappedZZZ[])objEnum; 
		  		
				Object[] objaEnum = classToCheck.getEnumConstants();
				for(Object objEnum : objaEnum) {
					Enum e = (E) objEnum;
					if(e.name().equals(sEnumPropertyName)) {
						enumReturn = (E) e;								
						break main;
					}					
				} 	
				
			}else {
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);			
				for(Class objClass : listaClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumInnerName)) {
						Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumPropertyName);	
						if(e!=null) {
							enumReturn = (E) e;										
							break main;
						}
					 }
				}
			}
			
			//Falls noch nix gefunden wurde... ggfs. die Interfaces scannen
			if(bScanInterfaceImidiate) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces	
				//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);

				//Achtung: Das holt alles sonstigen Interfaces, nicht die Interfaces, aus denen geerbt wird.
				//ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
				
				ArrayList<Class<?>> listaInterfaceByClass=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(classToCheck, listaInterfaceByClass);
				
				for(Class objClassInterface : listaInterfaceByClass) {
					
					//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
					ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
					for(Class objClass : listaClassByInterface) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumInnerName)) {
							Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumPropertyName);	
							if(e!=null) {
								enumReturn = (E) e;										
								break main;
							}
						 }
					}//end for												
				}//end for	
			}//end if

			//++++++++++++++++++++++++++++++++++++++++++++++
			//3. Hole die Elternklassen.
			if( bScanSuperclassImidiate) {
				ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(classToCheck);
				for(Class<?> objclsSuper : listaClassSuper) {
					//!!!Rekursion
					enumReturn=EnumAvailableHelperZZZ.searchEnum(objclsSuper, sEnumName,sEnumPropertyName,bScanInterfaceImidiate,bScanSuperclassImidiate);
					if(enumReturn!=null)break main;
				}			
			}			
		}//end main:
		return enumReturn;
	}
	
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
	public static <E extends Enum> ArrayList<E> searchEnumListDirect(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumListDirect_(classToCheck,sEnumName,true);
	}
	
	public static <E extends Enum> ArrayList<E> searchEnumListDirect(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumListDirect_(classToCheck,sEnumName,bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> ArrayList<E> searchEnumListDirect_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<E> listaReturn = EnumAvailableHelperZZZ.searchEnumList(classToCheck, sEnumName, bScanInterfaceImmidiate, false);			
		return listaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,true,true);
	}
	
	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,bScanInterfaceImidiate, true);
	}
	
	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,bScanInterfaceImidiate, bScanSuperclassImmidiate);
	}
	
	private static <E extends Enum> ArrayList<E> searchEnumList_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
		ArrayList<E> listaeReturn = null;
		main:{
			String sEnumInnerName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
			
			ArrayList<E> listaeByClass = null;
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				//z.B. das enum selbst gibt es so:
				//Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;))
		    	//Object[] objEnum = objClassFromInterface.getEnumConstants();
		    	//IEnumSetMappedZZZ[] enumaByInterface3 = (IEnumSetMappedZZZ[])objEnum; 
		  		    	 				
				Object[] objaEnum = classToCheck.getEnumConstants();
				if(!ArrayUtilZZZ.isEmpty(objaEnum)) {
					listaeByClass = new ArrayList<E>();
					for(Object obj : objaEnum) {
						Enum e = (Enum) obj;
						listaeByClass.add((E) e);
					} 
				}
			}else {
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
				if(!ArrayListZZZ.isEmpty(listaClass)) {
					for(Class objClass : listaClass) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumInnerName)) {
							Object[] obja = objClass.getEnumConstants();
							
							for(Object obj : obja) {
								if(ArrayListZZZ.isEmpty(listaeByClass)) listaeByClass = new ArrayList<E>();
								Enum e = (Enum) obj;
								listaeByClass.add((E) e);
							}
						}
					}	
				}
			}
						
			ArrayList<E>listaeByInterface=null;
			if(bScanInterfaceImidiate) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces
				//!!! Achtung, das holt nicht die Vererbung der Interfaces, sondern alle sonstigen Interfaces:
				//ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
				
				ArrayList<Class<?>> listaInterfaceByClass=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(classToCheck, listaInterfaceByClass);
				
				if(!ArrayListZZZ.isEmpty(listaInterfaceByClass)) {					
					for(Class objClassInterface : listaInterfaceByClass) {
						
						//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
						ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
						if(!ArrayListZZZ.isEmpty(listaClassByInterface)) {
							for(Class objClass : listaClassByInterface) {
								String sEnumClass = objClass.getName();				
								if(sEnumClass.endsWith(sEnumInnerName)) {
									Object[] obja = objClass.getEnumConstants();
									for(Object obj : obja) {
										if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<E>();
										IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) obj;
										listaeByInterface.add((E) e);
									}
								 }
							}//end for	
						}
					}//end for					
				}	
			}//end if
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++
			//3. Hole die Elternklassen.
			ArrayList<E>listaeBySuperclass=null;
			if( bScanSuperclassImidiate) {
				ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(classToCheck);
				for(Class<?> objclsSuper : listaClassSuper) {
					//!!!Rekursion
					ArrayList<E> listaeBySuperclassTemp=EnumAvailableHelperZZZ.searchEnumList(objclsSuper, sEnumName,bScanInterfaceImidiate,bScanSuperclassImidiate);
					listaeBySuperclass = ArrayListZZZ.join(listaeBySuperclass, listaeBySuperclassTemp);			
				}			
			}
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++
			if(!ArrayListZZZ.isEmpty(listaeByClass)) {		
				if(listaeReturn==null) listaeReturn = new ArrayList<E>();
				listaeReturn.addAll(listaeByClass);
			}
		
			if(!ArrayListZZZ.isEmpty(listaeByInterface)) {
				if(listaeReturn==null) listaeReturn = new ArrayList<E>();
				listaeReturn.addAll(listaeByInterface);
			}
			
			if(!ArrayListZZZ.isEmpty(listaeBySuperclass)) {
				if(listaeReturn==null) listaeReturn = new ArrayList<E>();
				listaeReturn.addAll(listaeBySuperclass);
			}
			
		
			listaeReturn = (ArrayList<E>) ArrayListZZZ.unique(listaeReturn);
		}//end main:
		return listaeReturn;
	}
}

