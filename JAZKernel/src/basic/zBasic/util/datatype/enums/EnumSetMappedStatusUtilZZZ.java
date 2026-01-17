package basic.zBasic.util.datatype.enums;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/**Merksatz (wichtig!)(von ChatGPT, 20260110)
 *
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 * 
 * @author Fritz Lindhauer, 10.01.2026, 08:27:05
 */
public class EnumSetMappedStatusUtilZZZ extends EnumSetUtilZZZ{
	private static final long serialVersionUID = 9011362468839990162L;

	public EnumSetMappedStatusUtilZZZ(){		
	}
	public EnumSetMappedStatusUtilZZZ(EnumSet<?>enumSetUsed){
		super(enumSetUsed);
	}
	public EnumSetMappedStatusUtilZZZ(Class<?>objClass)throws ExceptionZZZ{
		super(objClass);
	}
	public EnumSetMappedStatusUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory, Class<?> objClass) throws ExceptionZZZ{
		super(objEnumSetFactory, objClass);
	}
	public EnumSetMappedStatusUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory){
		this.setEnumFactoryCurrent(objEnumSetFactory);
	}
	
	//###############
	public boolean startsWithAnyAlias(String sToFind) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedStatusUtilZZZ.startsWithAnyAlias(sToFind, drivers);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyAlias(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			@SuppressWarnings("unchecked")
			Set<IEnumSetMappedStatusZZZ> drivers = (Set<IEnumSetMappedStatusZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedStatusZZZ driver : drivers) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				
				if(!StringZZZ.isEmpty(driver.name())){
					String sName = driver.name();
					if(StringZZZ.startsWithIgnoreCase(sName,sToFind)){
					  bReturn = true;
					  break;
				  }
			  }
			}
		}
		return bReturn;
	}
	
	public IEnumSetMappedStatusZZZ  startsWithAnyAlias_EnumMappedObject(String sToFind) throws ExceptionZZZ{
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			objReturn = EnumSetMappedStatusUtilZZZ.startsWithAnyAlias_EnumMappedObject(sToFind, drivers);
		}
		return objReturn;
	}
	public static IEnumSetMappedStatusZZZ startsWithAnyAlias_EnumMappedObject(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		IEnumSetMappedStatusZZZ objReturn = null;
		main:{
			@SuppressWarnings("unchecked")
			Set<IEnumSetMappedStatusZZZ> drivers = (Set<IEnumSetMappedStatusZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedStatusZZZ driver : drivers) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				
				if(!StringZZZ.isEmpty(driver.name())){
					if(StringZZZ.startsWithIgnoreCase(sToFind, driver.name())){
					  objReturn = driver;
					  break;
				  }
			  }
			}
		}
		return objReturn;
	}
	
	public boolean startsWithAnyAbbreviation(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedStatusUtilZZZ.startsWithAnyAbbreviation(sToFind, drivers);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyAbbreviation(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			Set<IEnumSetMappedStatusZZZ> drivers = (Set<IEnumSetMappedStatusZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedStatusZZZ driver : drivers) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			String sAbbreviation = driver.getAbbreviation();
				if(!StringZZZ.isEmpty(sAbbreviation)){
				  if(sAbbreviation.startsWith(sToFind)){  //!!! hier auch die Groß-/Kleinschreibung unterscheiden.
					  bReturn = true;
					  break;
				  }
			  }
		
			}//end for
		}//end main
		return bReturn;
	}
		
	
	public boolean startsWithAnyDescription(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedStatusUtilZZZ.startsWithAnyDescription(sToFind, drivers);
		}
		return bReturn;
	}
		public static boolean startsWithAnyDescription(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				Set<IEnumSetMappedStatusZZZ> drivers = (Set<IEnumSetMappedStatusZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
				for(IEnumSetMappedStatusZZZ driver : drivers) {
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
							
					if(!StringZZZ.isEmpty(driver.getDescription())){
					  if(driver.getDescription().startsWith(sToFind)){  //Groß-/Kleinschreibung beachten.
						  bReturn = true;
						  break;
					  }
				  }				
				}//end for
			}//end main:
			return bReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_NameValue(Class<?> clazz, String name) {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).name();
		    return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_StringValue(Class<?> clazz, String name) throws ExceptionZZZ {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).toString();//ACHTUNG: Das ist der Name ggfs. mit einem Gleichheitszeichen darin z.B. meinstatus=
		    sReturn = StringZZZ.left(sReturn+"=", "=");
		    return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_toString(Class<?> clazz, String name) {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).toString();//ACHTUNG: Das ist der Name ggfs. mit einem Gleichheitszeichen darin z.B. meinstatus=
		    return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Integer getEnumConstant_OrdinalValue(Class<?> clazz, String name) {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    int iReturn = Enum.valueOf((Class<Enum>)clazz, name).ordinal();
		    Integer intReturn = new Integer(iReturn);
		    return intReturn;
		}
		
		//getAbbreviation();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_AbbreviationValue(Class<IEnumSetMappedStatusZZZ> clazz, String name) throws ExceptionZZZ {
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStatusZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			
				if(!StringZZZ.isEmpty(driver.getAbbreviation())){
				  if(driver.name().equals(name)){
					  sReturn = driver.getAbbreviation();
					  break main;
				  }
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		//public static String getEnumConstant_DescriptionValue(Class<IEnumSetMappedZZZ> clazz, String name) {
		public static String getEnumConstant_DescriptionValue(Class<IEnumSetMappedStatusZZZ> clazz, String name) {				
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStatusZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  sReturn = driver.getDescription();
				  break main;
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}		
			
			
		@SuppressWarnings({ "unchecked", "rawtypes" })			
		public static String getEnumConstant_StatusMessageValue(Class<IEnumSetMappedStatusZZZ> clazz, String name) {				
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStatusZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  sReturn = driver.getStatusMessage();
				  break main;
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}		
			
			
		@SuppressWarnings({ "unchecked", "rawtypes" })			
		public static int getEnumConstant_StatusGroupIdValue(Class<IEnumSetMappedStatusZZZ> clazz, String name) {				
			int iReturn = -1;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStatusZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  iReturn = driver.getStatusGroupId();
				  break main;
			  }
		
			}//end for
			}//end main:
			return iReturn;
		}		
				
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Integer getEnumConstant_PositionValue(Class<IEnumSetMappedStatusZZZ> clazz, String name) {
			Integer intValue = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				  if(driver.name().equals(name)){
					 int iValue = driver.getPosition();
					intValue = new Integer(iValue);
				  }
		
			}//end for
			}//end main:
			return intValue;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Integer getEnumConstant_IndexValue(Class<IEnumSetMappedStatusZZZ> clazz, String name) {
			Integer intValue = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStatusZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				  if(driver.name().equals(name)){
					 int iValue = driver.getIndex();
					intValue = new Integer(iValue);
				  }
		
			}//end for
			}//end main:
			return intValue;
		}
		
		//public static ArrayList<Collection<? extends Enum<?>>> toEnum(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
		//<E extends Enum<E> & IEnumSetMappedZZZ>
		//public static ArrayList<E extends Enum<E> & IEnumSetMappedStatusZZZ>toEnum(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ {
		public static ArrayList<Collection<? extends Enum<?>>> toEnum(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
			ArrayList<Collection<? extends Enum<?>>> listaEnum = null;
			main:{
				if(ArrayListUtilZZZ.isEmpty(listae)) break main;
				
				for(IEnumSetMappedZZZ objMapped : listae) {
					if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
					listaEnum.add((Collection<? extends Enum<?>>) objMapped);					
				}				
			}
			return listaEnum;
		}
		
		public static <E extends Enum> E[] toEnumArray(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
			E[] objaeReturn = null;
			main:{
				if(ArrayListUtilZZZ.isEmpty(listae)) break main;

				//Hier ein Arra<ListZZZ.getInstanceOf() verwenden und nach der Klasse die Fallunterscheidung machen.
				//Das spart 1x dieses durchsehen der Liste....
				ArrayList<Class<?>> listaInstance = ArrayListUtilZZZ.getInstanceOfList(listae);
				if(listaInstance.contains(IEnumSetMappedZZZ.class)) {
					objaeReturn=EnumSetMappedStatusUtilZZZ.toEnumArrayByMapped(listae);
				}else if(listaInstance.contains(IEnumSetMappedStatusZZZ.class)) {
					objaeReturn=EnumSetMappedStatusUtilZZZ.toEnumArrayByMapped((ArrayList<IEnumSetMappedStatusZZZ>) listae);
				}else {
					ExceptionZZZ ez = new ExceptionZZZ("The type of ArrayList is not from IEnumSetMappdeZZZ", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getPositionCurrent());
					throw ez;
				}
					
				//obige Lösung ersetzt das mehrmalige interne ausrechnen der Liste.
//				if(ArrayListZZZ.isInstanceOf(listae, IEnumSetMappedZZZ.class)) {
//					objaeReturn=EnumSetMappedUtilZZZ.toEnumArrayByMapped((ArrayList<IEnumSetMappedZZZ>) listae);
//				}else if(ArrayListZZZ.isInstanceOf(listae, IEnumSetMappedStatusZZZ.class)) {
//					objaeReturn=EnumSetMappedUtilZZZ.toEnumArrayByMappedStatus((ArrayList<IEnumSetMappedStatusZZZ>) listae);
//				}else {
//					ExceptionZZZ ez = new ExceptionZZZ("The type of ArrayList is not from IEnumSetMappdeZZZ", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getPositionCurrent());
//					throw ez;
//				}
			}//end main:	
			return objaeReturn;
		}
		
		public static <E extends Enum> E[] toEnumArrayByMapped(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
			E[] objaeReturn = null;
			main:{
				if(ArrayListUtilZZZ.isEmpty(listae)) break main;
				
				//1. Versuch: Direkt per Cast.
				//Exception:  objaeReturn = (E[]) EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
				
				//2. Versuch:
				//Exception: objaeReturn = (E[]) ArrayListZZZ.toEnumMappedArray(listae);
				
				
				//3. Versuch: Per Mappen der Einzelwerte
				//Das Klappt....IEnumSetMappedZZZ[] objaeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
								
				//EXCEPTION: ArrayList<Collection<? extends Enum<?>>> listaeTemp = new ArrayList<Collection<? extends Enum<?>>>();
				ArrayList<E> listaeTemp = new ArrayList<E>();
				
				for(IEnumSetMappedZZZ objMapped : listae) {
					//if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
					//3. aber Excepteion: listaeTemp.add((Collection<? extends Enum<?>>) objMapped);
					listaeTemp.add((E) objMapped); 
				}
				
				//3.
				objaeReturn = ArrayListUtilZZZ.toEnumArray(listaeTemp);
			}
			
			return objaeReturn;
		}
				
		
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN 
		public static IEnumSetMappedStatusZZZ[] toEnumMappedArray(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
			IEnumSetMappedStatusZZZ[] objaReturn = null;
			main:{
				objaReturn = (IEnumSetMappedStatusZZZ[]) ArrayListUtilZZZ.toArray(listae);
			}
			return objaReturn;
		}
				
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN
		public static <E extends IEnumSetMappedStatusZZZ> E[] toEnumMappedArray(Enum[] enuma) throws ExceptionZZZ{
			E[] enumaReturn = null;
			main:{
				if(ArrayUtilZZZ.isNull(enuma)) break main;
				
				ArrayList<IEnumSetMappedStatusZZZ> listeReturnTemp = EnumSetMappedStatusUtilZZZ.toEnumMappedArrayList(enuma);
				if(ArrayListUtilZZZ.isEmpty(listeReturnTemp)) break main;
				
				enumaReturn = (E[]) listeReturnTemp.toArray(new IEnumSetMappedStatusZZZ[listeReturnTemp.size()]);
			}//end main:
			return enumaReturn;	
		}

		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN
		public static <E extends IEnumSetMappedStatusZZZ> E[] toEnumMappedArray(List<E> listae){
			E[] enumaReturn = null;
			main:{
				if(listae==null) break main;
				if(listae.size()==0) break main;
											
				enumaReturn = (E[]) listae.toArray(new IEnumSetMappedStatusZZZ[listae.size()]);
			}//end main:
			return enumaReturn;	
		}
						
		public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> toEnumMappedArrayList(List<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
			//ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
			ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
			main:{
				if(listae==null) break main;
				//if(!(enuma instanceof IEnumSetMappedStatusZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
				for(IEnumSetMappedStatusZZZ objenum : listae) {				
					listaeReturn.add(objenum);
				}
			}//end main:
			return listaeReturn;	
		}
		
		
		public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<IEnumSetMappedStatusZZZ> toEnumMappedArrayList(Enum[] enuma) throws ExceptionZZZ{
			ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;			
			main:{
				if(enuma==null) break main;
				//if(!(enuma instanceof IEnumSetMappedStatusZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
				for(Enum objEnum : enuma) {
					IEnumSetMappedStatusZZZ objEnumMapped = (IEnumSetMappedStatusZZZ) objEnum;
					listaeReturn.add(objEnumMapped);
				}
			}//end main:
			return listaeReturn;	
		}
		
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//		public static <E extends IEnumSetMappedStatusZZZ> E[] toEnumMappedStatusArray(Enum[] enuma){
//			E[] enumaReturn = null;
//			main:{
//				if(ArrayUtilZZZ.isNull(enuma)) break main;
//				
//				ArrayList<IEnumSetMappedStatusZZZ> listeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedStatusArrayList(enuma);
//				if(ArrayListUtilZZZ.isEmpty(listeReturnTemp)) break main;
//				
//				enumaReturn = (E[]) listeReturnTemp.toArray(new IEnumSetMappedStatusZZZ[listeReturnTemp.size()]);
//			}//end main:
//			return enumaReturn;	
//		}

//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//		public static <E extends IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ[] toEnumMappedStatusArray(ArrayList<E> listae) throws ExceptionZZZ{
//			IEnumSetMappedStatusZZZ[] enumaReturn = null;
//			main:{
//				if(listae==null) break main;
//				if(listae.size()==0) break main;
//				
//				//das geht so nicht:
//				//s. https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
//				//if(!(listae instanceof IEnumSetMappedStatusZZZ)) break main;
//				
//				//Also eigener, heuristischer Ansatz
//				boolean bInstanceOfMappedStatus = ArrayListUtilZZZ.isInstanceOf(listae, IEnumSetMappedStatusZZZ.class);
//				if(!bInstanceOfMappedStatus)break main;
//				
//				enumaReturn = listae.toArray(new IEnumSetMappedStatusZZZ[listae.size()]);
//			}//end main:
//			return enumaReturn;	
//		}	
		
//		public static ArrayList<IEnumSetMappedStatusZZZ> toEnumMappedStatusArrayList(ArrayList<IEnumSetMappedZZZ> listae) throws ExceptionZZZ{
//			ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
//			main:{
//				if(listae==null) break main;
//				
//				listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//				if(listae.size()==0) break main;
//				
//				for(IEnumSetMappedZZZ obj : listae) {
//					IEnumSetMappedStatusZZZ objStatus = (IEnumSetMappedStatusZZZ) obj;
//					listaeReturn.add(objStatus);
//				}								
//			}//end main:
//			return listaeReturn;	
//		}	
		
			
		/* TODO GOON: Weitere Ideen für die Utitlity Klasse
		 * hier kombineren von zwei Enumerations:
		 * If I have an Enum, I can create an EnumSet using the handy EnumSet class

enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
EnumSet<Suit> reds = EnumSet.of(Suit.HEARTS, Suit.DIAMONDS);
EnumSet<Suit> blacks = EnumSet.of(Suit.CLUBS, Suit.SPADES);

Give two EnumSets, how can I create a new EnumSet which contains the union of both of those sets?

		 *EnumSet<Suit> redAndBlack = EnumSet.copyOf(reds);
redAndBlack.addAll(blacks);
		 * 
		 */

}
