package basic.zKernel;

import java.util.EnumSet;

import basic.zKernel.config.IEnumSetKernelConfigDefaultEntryZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ;

public interface IKernelConfigConstantZZZ {
	public enum MODULEPROPERTY{
		Path, File ; //20220814: EIGENSCHAFTEN, DIE ZUR Definition der ini-Datei Einträge fuer Module genutzt werden können
	}
	
	//Zum Auslesen der Konfigurationseinträge in der INI-Datei
	//Merke: Dies wird in KernelConfigDefaultEntryZZZ, in der Enumeration ggfs. verwendet.
	public final static String sDIRECTORY_CONFIG_DEFAULT="c:\\fglkernel\\kernelconfig";
	public final static String sFILENAME_CONFIG_DEFAULT="ZKernelConfigKernel.ini";
	
	public final static String sMODULE_PREFIX="KernelConfig";
	
	//Scheinbar manchmal Probleme diese Konstanten "Auszurechnen". Verwende dann die Methode KernelKernelZZZ.getModuleDirectoryPrefix()
	public final static String sMODULE_DIRECTORY_PREFIX=sMODULE_PREFIX+MODULEPROPERTY.Path.name();
	//Scheinbar manchmal Probleme diese Konstanten "Auszurechnen". Verwende dann die Methode KernelKernelZZZ.getModuleFilenamePrefix()
	public final static String sMODULE_FILENAME_PREFIX=sMODULE_PREFIX+MODULEPROPERTY.File.name();
	
	//#######################################################
	//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
	//### int Key, String shorttext, String longtext, String description
	//#######################################################
	public enum EnumConfigDefaultEntryZZZ implements IEnumSetKernelConfigDefaultEntryZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		
		//Merke1: Die Defaultwerte in der Kernel-Konfiguration richten sich nach den in der INI-Konfiguration verwedendeten Werten.
		//        z.B. KernelExpressionIni_NullZZZ liefert <z:Null> als Tag.
		//Merke2: In den Testklassen wird aber als Pfad verwendet: test
	    //@IFieldDescription(description = "DTXT01 TEXTVALUES")
	   	//T01(1,"KernelConfigPath","<z:Null/>","The default path of the configuration"),
		T01(1,KernelConfigDefaultEntryZZZ.sMODULE_DIRECTORY_PREFIX ,"<z:Null/>","The default path of the configuration"),
		//@IFieldDescription(description = "DTXT02 TEXTVALUES") 
		//T02(2,"KernelConfigFile",KernelConfigDefaultEntryZZZ.sFILENAME_CONFIG_DEFAULT, "The default filename of the configuration");
		T02(2,KernelConfigDefaultEntryZZZ.sMODULE_FILENAME_PREFIX,KernelConfigDefaultEntryZZZ.sFILENAME_CONFIG_DEFAULT, "The default filename of the configuration");
   	   	
		private int iId;
		private String sProperty;
		private String sValueDefault;
		private String sDescription;

		//#############################################
		//#### Konstruktoren
	   //Merke: Enums haben keinen public Konstruktor, können also nicht instantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.  ( basic/zBasic/util/abstractEnum/EnumSetMappedUtilZZZ.java ).
		   EnumConfigDefaultEntryZZZ(){	
		   }
		   
	   EnumConfigDefaultEntryZZZ(int iId, String sProperty, String sValueDefault, String sDescription){
	       this.iId = iId;
	       this.sProperty = sProperty;
	       this.sValueDefault = sValueDefault;
	       this.sDescription = sDescription;
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
//		public Long getThiskey() {
//			return this.lKey;
//		}
	
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
   
 //+++ Das könnte auch in einer Utility-Klasse sein.
   // the valueOfMethod <--- Translating from DB
   public static EnumConfigDefaultEntryZZZ fromShorttext(String s) {
       for (EnumConfigDefaultEntryZZZ state : values()) {
           if (s.equals(state.getConfigProperty()))
               return state;
       }
       throw new IllegalArgumentException("Not a correct shorttext: " + s);
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

	   @Override
	   public int getIndex() {
	   	return ordinal();
	   }
	   
	   //### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
	   @Override
	   public int getPosition() {
	   		return getIndex() + 1;
	   }
   }//End inner classs			
}
