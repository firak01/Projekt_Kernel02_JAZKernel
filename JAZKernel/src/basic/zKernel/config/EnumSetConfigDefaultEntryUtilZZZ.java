package basic.zKernel.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

public class EnumSetConfigDefaultEntryUtilZZZ extends EnumSetUtilZZZ{
	private EnumSet<?> enumSetCurrent=null;
	private IEnumSetFactoryZZZ objEnumSetFactory=null;
	
	public EnumSetConfigDefaultEntryUtilZZZ(){		
	}
	public EnumSetConfigDefaultEntryUtilZZZ(EnumSet<?>enumSetUsed){
		this.setEnumSetCurrent(enumSetUsed);
	}
	public EnumSetConfigDefaultEntryUtilZZZ(Class<?>objClass)throws ExceptionZZZ{
		super(objClass);		
	}
	public EnumSetConfigDefaultEntryUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory, Class<?> objClass) throws ExceptionZZZ{
		super(objEnumSetFactory, objClass);
	}
	public EnumSetConfigDefaultEntryUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory){
		super(objEnumSetFactory);
	}
	
						
	public boolean startsWithAnyDescription(String sToFind){
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyDescription(sToFind, drivers);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyDescription(String sToFind, EnumSet<?> setEnumCurrent){
		boolean bReturn = false;
		main:{
			Set<IEnumSetKernelConfigDefaultEntryZZZ> drivers = (Set<IEnumSetKernelConfigDefaultEntryZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetKernelConfigDefaultEntryZZZ driver : drivers) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
						
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
		public static String readEnumConstant_DescriptionValue(Class<IEnumSetKernelConfigDefaultEntryZZZ> clazz, String name) {
		//public static <E extends Enum<E>> String getEnumConstant_DescriptionValue(Class<E> clazz, String name) {
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetKernelConfigDefaultEntryZZZ[] enumaSetMapped = (IEnumSetKernelConfigDefaultEntryZZZ[]) clazz.getEnumConstants();
		    if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt
		    
			for(IEnumSetKernelConfigDefaultEntryZZZ driver : enumaSetMapped) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			
				if(!StringZZZ.isEmpty(driver.getDescription())){
				  if(driver.getName().equals(name)){
					  sReturn = driver.getDescription();
					  break main;
				  }
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}
		
		

		
		
}


