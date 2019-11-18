package basic.zKernel.config;

import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class KernelConfigDefaultEntryZZZ<IEnumSetConfigKernelConfigDefaultEntryZZZ>{
	private int iId;
	private String sProperty;
	private String sValueDefault;
	
	//+++ Konstruktor
	public KernelConfigDefaultEntryZZZ() {		
	}
	
	//+++ Gespeicherte Werte
	public int getiId() {
		return iId;
	}

	public void setId(int iId) {
		this.iId = iId;
	}

	public String getProperty() {
		return sProperty;
	}

	public void setProperty(String sProperty) {
		this.sProperty = sProperty;
	}

	public String getValueDefault() {
		return sValueDefault;
	}

	public void setValueDefault(String sValueDefault) {
		this.sValueDefault = sValueDefault;
	}
	
	//### Statische Methode (um einfacher darauf zugreifen zu können)
    public static Class getThiskeyEnumClassStatic(){
    	try{
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Diese Methode muss in den daraus erbenden Klassen überschrieben werden.");
    	}catch(ExceptionZZZ ez){
			String sError = "ExceptionZZZ: " + ez.getMessageLast() + "+\n ThreadID:" + Thread.currentThread().getId() +"\n";			
			System.out.println(sError);
		}
    	return EnumConfigDefaultEntryZZZ.class;    	
    }

	//#######################################################
	//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
	//### int Key, String shorttext, String longtext, String description
	//#######################################################
	public enum EnumConfigDefaultEntryZZZ implements IEnumSetKernelConfigDefaultEntryZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		
		//Merke1: Die Defaultwerte in der Kernel-Konfiguration richten sich nach den in der INI-Konfiguration verwedendeten Werten.
		//        z.B. KernelExpressionIni_NullZZZ liefert <z:Null> als Tag.
		//Merke2: In den Testklassen wird aber als Pfad verwendet: test
	    //@IFieldDescription(description = "DTXT01 TEXTVALUES") 
	   	T01(1,"KernelConfigPath","<z:Null/>","The default path of the configuration"),
	   	
		//@IFieldDescription(description = "DTXT02 TEXTVALUES") 
		T02(2,"KernelConfigFile","ZKernelConfigKernel_test.ini", "The default filename of the configuration");
   	   	
		private int iId;
		private String sProperty;
		private String sValueDefault;
		private String sDescription;

		//#############################################
		//#### Konstruktoren
	   //Merke: Enums haben keinen public Konstruktor, können also nicht instantiiert werden, z.B. durch Java-Reflektion.
	   EnumConfigDefaultEntryZZZ(int iId, String sProperty, String sValueDefault, String sDescription){
	       this.iId = iId;
	       this.sProperty = sProperty;
	       this.sValueDefault = sValueDefault;
	       this.sDescription = sDescription;
	   }
	   	   
	   //Merke: Enums haben keinen public Konstruktor, können also nicht instantiiert werden, z.B. durch Java-Reflektion.
	   //           In der Util-Klasse habe ich aber einen Workaround gefunden ( basic/zBasic/util/abstractEnum/EnumSetMappedUtilZZZ.java ).
	   EnumConfigDefaultEntryZZZ(){	
	   }

   //##################################################
   //#### Folgende Methoden holen die definierten Werte.
   @Override
   public int getType() {   	
   	return this.iId;
   }

   @Override
   public String getConfigProperty() {   	
   	return this.sProperty;
   }

   @Override
   public String getValueDefault() {
   	return this.sValueDefault;
   }
   

	@Override
	public String getDescription() {
	 return this.sDescription;
	}
   
   //#### Methode aus IKeyProviderZZZ
//	public Long getThiskey() {
//		return this.lKey;
//	}

	
   // the valueOfMethod <--- Translating from DB
   public static EnumConfigDefaultEntryZZZ fromShorttext(String s) {
       for (EnumConfigDefaultEntryZZZ state : values()) {
           if (s.equals(state.getConfigProperty()))
               return state;
       }
       throw new IllegalArgumentException("Not a correct shorttext: " + s);
   }

   @Override
   public EnumSet<?>getEnumSetUsed(){
   	return EnumConfigDefaultEntryZZZ.getEnumSet();
   }
      
   public static EnumSet<?> getEnumSet() {
	   //Merke: Das wird anders behandelt als FLAGZ Enumeration.
	   	//String sFilterName = "FLAGZ"; /
	   	//...
	   	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
	   	
	   	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
	   	Class<EnumConfigDefaultEntryZZZ> enumClass = EnumConfigDefaultEntryZZZ.class;
	   	EnumSet<EnumConfigDefaultEntryZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
	   	
	   	for(Object obj : EnumConfigDefaultEntryZZZ.class.getEnumConstants()){
	   		//System.out.println(obj + "; "+obj.getClass().getName());
	   		set.add((EnumConfigDefaultEntryZZZ) obj);
	   	}
	   	return set;
	}
 

   //TODO: Mal ausprobieren was das bringt
   //Convert Enumeration to a Set/List
   private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
   	  EnumSet<E> set=EnumSet.noneOf(enumClass);
   	  long mask=1;
   	  for (  E e : enumClass.getEnumConstants()) {
   	    if ((mask & vector) == mask) {
   	      set.add(e);
   	    }
   	    mask<<=1;
   	  }
   	  return set;
   	}

   	  //##################################################
	  //#### Folgende Methoden bring Enumeration von Hause aus mit. 
   			//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
	   public String getName(){
		   return super.name();
	   }
	   
	   @Override
	   public String toString() {
	       return this.sProperty+"="+this.sValueDefault+"#"+this.iId + " "+ this.sDescription;
	   }

	   public int getIndex() {
	   	return ordinal();
	   }
	   
	   //### Folgende Methoden sind zum komfortablen arbeiten gedacht.
	   public int getPosition() {
	   	return getIndex() + 1;
	   }
   }//End inner classs
	
}//end class
