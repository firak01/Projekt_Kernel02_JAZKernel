package basic.zKernel.config;

import java.util.EnumSet;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class EnumSetConfigDefaultEntryUtilZZZ extends EnumSetUtilZZZ{	
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
	
	//#########################################################################
	//Zugriffe auf spezielle Eigenschaften dieses Enum-Konstrukts, siehe: IEnumSetKernelConfigDefaultEntryZZZ
	//#########################################################################
	
	//#################
	//+++ Zugriffe auf den Enum-Wert der ConfigDescription					
	public boolean startsWithAnyDescription(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			EnumSet<?> myEnumSet = this.getEnumSetCurrent();
			bReturn = EnumSetConfigDefaultEntryUtilZZZ.startsWithAnyDescription(myEnumSet, sToFind);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyDescription(EnumSet<?> myEnumSet, String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Was ist besser? Erst das ganze Set typecasten, oder Einzelwerte im Set typecasten?
			Set<IEnumSetKernelConfigDefaultEntryZZZ> drivers = (Set<IEnumSetKernelConfigDefaultEntryZZZ>) myEnumSet;//..allOf(JdbcDriverClassTypeZZZ.class);
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
	
	public Integer readEnumConstant_FirstIndexValueByDescription(String sDescription) throws ExceptionZZZ {
		Integer intReturn = null;
		main:{
			if(sDescription==null || sDescription.isEmpty()) break main;			
			intReturn = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_FirstIndexValueByDescription(this.getEnumSetCurrent(), sDescription);
		}//end main:
		return intReturn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Integer readEnumConstant_FirstIndexValueByDescription(EnumSet<?> myEnumSet, String sDescription) throws ExceptionZZZ {					
		Integer intReturn = null;
		main:{
	    if (myEnumSet==null || sDescription==null || sDescription.isEmpty()) break main;
	    
	    //Was ist besser? Erst das ganze Set typecasten, oder Einzelwerte im Set typecasten?
	    //Set<IEnumSetKernelConfigDefaultEntryZZZ> drivers = (Set<IEnumSetKernelConfigDefaultEntryZZZ>) myEnumSet;
		for(Enum<?> myEnum : myEnumSet) {
			IEnumSetKernelConfigDefaultEntryZZZ myEnumtype = (IEnumSetKernelConfigDefaultEntryZZZ) myEnum;
			if(myEnumtype.getDescription().equals(sDescription)) {
				int iValue = myEnumtype.getIndex();
				intReturn = new Integer(iValue);
			  }
			}//end for
		}//end main:
		return intReturn;
	}
						
		
	//#############################		
	//+++ Zugriffe auf den Enum-Wert der ConfigDefaultValue
	public Integer readEnumConstant_FirstIndexValueByDefaultValue(String sValue) throws ExceptionZZZ {
		Integer intReturn = null;
		main:{
			if(sValue==null || sValue.isEmpty()) break main;			
			intReturn = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_FirstIndexValueByDefaultValue(this.getEnumSetCurrent(), sValue);
		}//end main:
		return intReturn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Integer readEnumConstant_FirstIndexValueByDefaultValue(EnumSet<?> myEnumSet, String sValue) throws ExceptionZZZ {					
		Integer intReturn = null;
		main:{
	    if (myEnumSet==null || sValue==null || sValue.isEmpty()) break main;
	    
	    //Was ist besser? Erst das ganze Set typecasten, oder Einzelwerte im Set typecasten?
	    //Set<IEnumSetKernelConfigDefaultEntryZZZ> drivers = (Set<IEnumSetKernelConfigDefaultEntryZZZ>) myEnumSet;
		for(Enum<?> myEnum : myEnumSet) {
			IEnumSetKernelConfigDefaultEntryZZZ myEnumtype = (IEnumSetKernelConfigDefaultEntryZZZ) myEnum;
			if(myEnumtype.getValueDefault().equals(sValue)) {
				int iValue = myEnumtype.getIndex();
				intReturn = new Integer(iValue);
			  }
			}//end for
		}//end main:
		return intReturn;
	}	

	//#############################	
	//+++ Zugriffe auf den Enum-Wert des ConfigProperty
	public Integer readEnumConstant_FirstIndexValueByProperty(String sProperty) throws ExceptionZZZ {
		Integer intReturn = null;
		main:{
			if(sProperty==null || sProperty.isEmpty()) break main;			
			intReturn = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_FirstIndexValueByProperty(this.getEnumSetCurrent(), sProperty);
		}//end main:
		return intReturn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Integer readEnumConstant_FirstIndexValueByProperty(EnumSet<?> myEnumSet, String sProperty) throws ExceptionZZZ {					
		Integer intReturn = null;
		main:{
	    if (myEnumSet==null || sProperty==null || sProperty.isEmpty()) break main;
	    
	    //Was ist besser? Erst das ganze Set typecasten, oder Einzelwerte im Set typecasten?
	    //Set<IEnumSetKernelConfigDefaultEntryZZZ> drivers = (Set<IEnumSetKernelConfigDefaultEntryZZZ>) myEnumSet;
		for(Enum<?> myEnum : myEnumSet) {
			IEnumSetKernelConfigDefaultEntryZZZ myEnumtype = (IEnumSetKernelConfigDefaultEntryZZZ) myEnum;
			if(myEnumtype.getConfigProperty().equals(sProperty)) {
				int iValue = myEnumtype.getIndex();
				intReturn = new Integer(iValue);
			  }
			}//end for
		}//end main:
		return intReturn;
	}	
	//#############################	
	//+++ Zugriffe auf den Emum-Wert des ConfigValueDefault ausgehend von der ConfigProperty
	public String readEnumConstant_DefaultValueByProperty(String sProperty) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(sProperty==null || sProperty.isEmpty()) break main;			
			sReturn = EnumSetConfigDefaultEntryUtilZZZ.readEnumConstant_FirstDefaultValueByProperty(this.getEnumSetCurrent(), sProperty);
		}//end main:
		return sReturn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String readEnumConstant_FirstDefaultValueByProperty(EnumSet<?> myEnumSet, String sProperty) throws ExceptionZZZ {					
		String sReturn = null;
		main:{
	    if (myEnumSet==null || sProperty==null || sProperty.isEmpty()) break main;
	    
	    //Was ist besser? Erst das ganze Set typecasten, oder Einzelwerte im Set typecasten?
	    //Set<IEnumSetKernelConfigDefaultEntryZZZ> drivers = (Set<IEnumSetKernelConfigDefaultEntryZZZ>) myEnumSet;
		for(Enum<?> myEnum : myEnumSet) {
			IEnumSetKernelConfigDefaultEntryZZZ myEnumtype = (IEnumSetKernelConfigDefaultEntryZZZ) myEnum;
			if(myEnumtype.getConfigProperty().equals(sProperty)) {
				sReturn = myEnumtype.getValueDefault();
			  }
			}//end for
		}//end main:
		return sReturn;
	}
		
}


