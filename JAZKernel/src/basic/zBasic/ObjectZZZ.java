package basic.zBasic;

import static java.lang.System.out;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyZZZ;
import basic.zKernel.IKernelConfigZZZ;

public class ObjectZZZ <T> implements Serializable, IObjectZZZ, IFlagZZZ{
	private static final long serialVersionUID = 1L;

	/**20130721: Erweitert um HashMap und die Enum-Flags, Compiler auf 1.6 geändert
	 * 
	 */
//Flags, die alle Z-Objekte haben
//	public enum FLAGZ{
//		DEBUG, INIT; //Verschoben nach IFlagZZZ, weil nicht alle Klassen von ObjectZZZ erben können (weil sie schon von einer anderen Klasse erben).
//	}
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public ObjectZZZ() {	
		//Darf so nicht definiert werden, da dieser Konstruktor implizit immer aufgerufen wird. 
		//wenn dieser Defaultkonstruktor nicht explizit definiert ist in der Kindklasse 
		//this.setFlag("init", true);
		//
		//Lösung dies trotzdem zu setzten:
		//rufe im Default Konstuktor der Kindklasse auf:
		//super("init");		
	}
	public ObjectZZZ(String sFlag) {
		if(!StringZZZ.isEmpty(sFlag)) this.setFlag(sFlag, true);
	}
	public ObjectZZZ(String[] saFlag) {
		if(saFlag!=null){
			if(saFlag.length>=1){
				for(int icount =0; icount <= saFlag.length-1; icount++){
					if(!StringZZZ.isEmpty(saFlag[icount])){						
						this.setFlag(saFlag[icount], true);
					}
				}
			}
		}
	}
		
	
	//### FlagMethods ##########################		
	public Class getClassFlagZ(){
		return FLAGZ.class;
	}

		public HashMap<String, Boolean>getHashMapFlagZ(){
			return this.hmFlag;
		} 
		
		/**Gibt alle möglichen FlagZ Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ() throws ExceptionZZZ{
			String[] saReturn = null;
			main:{				
					Class objClass4Enum = this.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
					String sFilterName = objClass4Enum.getSimpleName();
					
					ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
					if(listEmbedded == null) break main;
					//out.format("%s# ListEmbeddedClasses.size()...%s%n", ReflectCodeZZZ.getPositionCurrent(), listEmbedded.size());
					
					ArrayList <String> listasTemp = new ArrayList<String>();
					for(Class objClass : listEmbedded){
						//out.format("%s# Class...%s%n", ReflectCodeZZZ.getPositionCurrent(), objClass.getName());
						Field[] fields = objClass.getDeclaredFields();
						for(Field field : fields){
							if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zurückgegeben.
								//out.format("%s# Field...%s%n", ReflectCodeZZZ.getPositionCurrent(), field.getName());
								listasTemp.add(field.getName());
							}				
					}//end for
				}//end for
					
				//20170307: Durch das Verschieben von FLAGZ mit den Werten DEBUG und INIT in das IObjectZZZ Interface, muss man explizit auch dort nachsehen.
			   //                Merke: Das Verschieben ist deshlab notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.
			
												
					//+++ Nun die aktuelle Klasse 
					Class<FLAGZ> enumClass = FLAGZ.class;								
					for(Object obj : FLAGZ.class.getEnumConstants()){
						//System.out.println(obj + "; "+obj.getClass().getName());
						listasTemp.add(obj.toString());
					}
					saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
	
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ(boolean bValueToSearchFor) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				
				ArrayList<String>listasTemp=new ArrayList<String>();
				
				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
				if(bValueToSearchFor==true){
					HashMap<String,Boolean>hmFlag=this.getHashMapFlagZ();
					if(hmFlag==null) break main;
					
					Set<String> setKey = hmFlag.keySet();
					for(String sKey : setKey){
						boolean btemp = hmFlag.get(sKey);
						if(btemp==bValueToSearchFor){
							listasTemp.add(sKey);
						}
					}
				}else{
					String[]saFlagZ = this.getFlagZ();
					for(String sFlagZ : saFlagZ){
						boolean btemp = this.getFlagZ(sFlagZ);
						if(btemp==bValueToSearchFor ){ //also 'false'
							listasTemp.add(sFlagZ);
						}
					}
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
		 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
		 *    
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZZZ objUsingFlagZ) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
				String[] saFlag = this.getFlagZ(bValueToSearchFor);
				
				//2. Hole alle FlagZ der Zielklasse
				String[] saFlagTarget = objUsingFlagZ.getFlagZ();
				
				//ArrayList<String>listasFlagPassable=new ArrayList<String>();
				//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
				
				saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
			}//end main:
			return saReturn;
		}
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 */
//		public String[] getFlagZ_passable(boolean bValueToSearchFor, String sTargetClassnameForThePass){
//			String[] saReturn = null;
//			main:{
//				
//				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
//				String[] saFlag = this.getFlagZ(bValueToSearchFor);
//				
//				//2. Hole alle FlagZ der Zielklasse
//				
//				ArrayList<String>listasFlagPassable=new ArrayList<String>();
//				
//				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
//				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
//				if(bValueToSearchFor==true){
//					HashMap<String,Boolean>hmFlag=this.getHashMapFlagZ();
//					if(hmFlag==null) break main;
//					
//					Set<String> setKey = hmFlag.keySet();
//					for(String sKey : setKey){
//						boolean btemp = hmFlag.get(sKey);
//						if(btemp==bValueToSearchFor){
//							listasTemp.add(sKey);
//						}
//					}
//				}else{
//					String[]saFlagZ = this.getFlagZ();
//					for(String sFlagZ : saFlagZ){
//						boolean btemp = this.getFlagZ(sFlagZ);
//						if(btemp==bValueToSearchFor ){ //also 'false'
//							listasTemp.add(sFlagZ);
//						}
//					}
//				}
//				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
//			}//end main:
//			return saReturn;
//		}
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
		 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
		 *    
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ_passable(IFlagZZZ objUsingFlagZ) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
				String[] saFlag = this.getFlagZ();
				
				//2. Hole alle FlagZ der Zielklasse
				String[] saFlagTarget = objUsingFlagZ.getFlagZ();
				
				ArrayList<String>listasFlagPassable=new ArrayList<String>();
				//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
				
				saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
			}//end main:
			return saReturn;
		}
		
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) {
				bFunction = true;
				break main;
			}
			

			bFunction = this.proofFlagZExists(sFlagName);												
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}

	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
	 * @param name 
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public boolean proofFlagZExists(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
				//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sFlagName = " + sFlagName);
				
				Class objClass4Enum = this.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
				String sFilterName = objClass4Enum.getSimpleName();
				
				ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
				if(listEmbedded == null) break main;
				//out.format("%s# ListEmbeddedClasses.size()...%s%n", ReflectCodeZZZ.getPositionCurrent(), listEmbedded.size());
				
				for(Class objClass : listEmbedded){
					//out.format("%s# Class...%s%n", ReflectCodeZZZ.getPositionCurrent(), objClass.getName());
					Field[] fields = objClass.getDeclaredFields();
					for(Field field : fields){
						if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zurückgegeben.
							//out.format("%s# Field...%s%n", ReflectCodeZZZ.getPositionCurrent(), field.getName());
							if(sFlagName.equalsIgnoreCase(field.getName())){
								bReturn = true;
								break main;
							}
						}				
				}//end for
			}//end for
				
				/* Zugriff auf die Interfaces einer Klasse. Diese müssen auch auf die Flag geprüft werden.
				  static void printInterfaceNames(Object o) {
      Class c = o.getClass();
      Class[] theInterfaces = c.getInterfaces();
      for (int i = 0; i < theInterfaces.length; i++) {
         String interfaceName = theInterfaces[i].getName();
         System.out.println(interfaceName);
      }
   }
				 */
				
			//20170307: Durch das Verschieben von FLAGZ mit den Werten DEBUG und INIT in das IObjectZZZ Interface, muss man explizit auch dort nachsehen.
		   //                Merke: Das Verschieben ist deshlab notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.
		
							
				//TODO GOON: 
				//Merke: bReturn = set.contains(sFlagName.toUpperCase());
				//          Weil das nicht funktioniert meine Util-Klasse erstellen, die dann den String tatsächlich prüfen kann
				
				Class<FLAGZ> enumClass = FLAGZ.class;
				//EnumSet<FLAGZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
				
				for(Object obj : FLAGZ.class.getEnumConstants()){
					//System.out.println(ReflectCodeZZZ.getPositionCurrent()+": "+ obj + "; "+obj.getClass().getName());
					if(sFlagName.equalsIgnoreCase(obj.toString())) {
						bReturn = true;
						break main;
					}
					//set.add((FLAGZ) obj);
				}				
		}//end main:
		return bReturn;
	}

public static boolean proofFlagZExists(IFlagZZZ objcp, String sFlagName) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName))break main;
		if(objcp==null)break main;
					
		Class objClass4Enum = objcp.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
		String sFilterName = objClass4Enum.getSimpleName();
		
		//Merke: Nachfolgender Code ist Redundant, bleibt aber drin, solange "FLAGZ" nicht irgendwo als Konstante definiert ist.
		ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(objcp.getClass(), sFilterName);
		if(listEmbedded == null) break main;
		
		for(Class objClass : listEmbedded){
			
			Field[] fields = objClass.getDeclaredFields();
			for(Field field : fields){
				if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zur�ckgegeben.
					//out.format("%s# Field...%s%n", ReflectCodeZZZ.getPositionCurrent(), field.getName());
					if(sFlagName.equalsIgnoreCase(field.getName())){
						bReturn = true;
						break main;
					}
				}				
			}		
		}
	}//End main:
	if(bReturn){
		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# VORHANDEN Flag='" + sFlagName + "'");
	}else{
		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# NICHT DA Flag='" + sFlagName + "'");
	}
	return bReturn;
}

public static boolean proofFlagZExists(Class objcp, String sFlagName) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName))break main;
		if(objcp==null)break main;
							
		String sFilterName = "FLAGZ"; //TODO: Diesen Namen als Konstante definieren, dann kann auch in diesen static methoden redundanter Code entfernt werden.
		
		ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(objcp.getClass(), sFilterName);
		if(listEmbedded == null) break main;
		
		for(Class objClass : listEmbedded){
			
			Field[] fields = objClass.getDeclaredFields();
			for(Field field : fields){
				if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zurückgegeben.
					// out.format("%s# Field...%s%n", ReflectCodeZZZ.getMethodCurrentName(), field.getName());
					if(sFlagName.equalsIgnoreCase(field.getName())){
						bReturn = true;
						break main;
					}
				}				
			}		
		}
		
		//TODOGOON; //20210130: In den Interfaces sind die ENUM-FLAGZ also auf diese Interfaces zugreifen. 
		/* Zugriff auf die Interfaces einer Klasse. Diese müssen auch auf die Flag geprüft werden.
		  static void printInterfaceNames(Object o) {
Class c = o.getClass();
Class[] theInterfaces = c.getInterfaces();
for (int i = 0; i < theInterfaces.length; i++) {
String interfaceName = theInterfaces[i].getName();
System.out.println(interfaceName);
}
}
		 */
		Class[] objaInterfaces = objcp.getInterfaces();
		for (int i = 0; i < objaInterfaces.length; i++) {
			String interfaceName = objaInterfaces[i].getName();
			System.out.println(interfaceName);
		}
		
		
		
		//20170307: Durch das Verschieben von FLAGZ mit den Werten DEBUG und INIT in das IObjectZZZ Interface, muss man explizit auch dort nachsehen.
		   //                Merke: Das Verschieben ist deshlab notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.				
		Class<FLAGZ> enumClass = FLAGZ.class;				
		for(Object obj : FLAGZ.class.getEnumConstants()){
			//System.out.println(obj + "; "+obj.getClass().getName());
			if(sFlagName.equalsIgnoreCase(obj.toString())) {
				bReturn = true;
				break main;
			}
		}			
				
		//20170308: Merke: Wenn immer noch kein FLAGZ gefunden wurde, dieses Flag aus der aufrufenden Klasse selbst holen.
		
	}//End main:
	if(bReturn){
		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# VORHANDEN Flag='" + sFlagName + "'");
	}else{
		//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# NICHT VORHANDEN Flag='" + sFlagName + "'");
	}
	return bReturn;
}

/* Voraussetzungen:
 * - Public Default Konstruktor, damit die Klasse instanziiert werden kann.
 * - Innere Klassen müssen auch public deklariert werden.
 */
public static boolean proofFlagZExists(String sClassName, String sFlagName) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName))break main;
		if(StringZZZ.isEmpty(sClassName))break main;
		try {
			
			//Existiert in der Elternklasse oder in der aktuellen Klasse das Flag?
			//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# ObjektInstanz erzeugen für '" + sClassName + "'");
			Class<?> objClass = Class.forName(sClassName);		
			
			//!!! für abstrakte Klassen gilt: Es kann per Reflection keine neue Objektinstanz geholt werden.
			if(!ReflectClassZZZ.isAbstract(objClass)){
				if(ReflectClassZZZ.isInner(objClass)){
					//Bei inneren Klassen anders eine neue Instanz erzeugen.
				    //http://stackoverflow.com/questions/17485297/how-to-instantiate-inner-class-with-reflection-in-java
					Class<?> objClassEnclosing = ReflectClassZZZ.getEnclosingClass(objClass);
					Object objClassEnclosingInstance = objClassEnclosing.newInstance();
					
					try {
						Constructor<?> ctor= objClass.getDeclaredConstructor(objClassEnclosing);
						Object objInnerInstance = ctor.newInstance(objClassEnclosingInstance);
						IFlagZZZ objcp = (IFlagZZZ) objInnerInstance;
						if(objcp==null){
						}else{
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# INNERE ObjektInstanz für '" + objcp.getClass().getName() + "' erfolgreich erzeugt. Nun daraus Enum Klasse holen... .");
							bReturn = ObjectZZZ.proofFlagZExists(objcp, sFlagName);
						}
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					IFlagZZZ objcp = (IFlagZZZ)objClass.newInstance();  //Aus der Objektinstanz kann dann gut die Enumeration FLAGZ ausgelesen werden.				
					if(objcp==null){
					}else{
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# ObjektInstanz für '" + objcp.getClass().getName() + "' erfolgreich erzeugt. Nun daraus Enum Klasse holen... .");
						bReturn = ObjectZZZ.proofFlagZExists(objcp, sFlagName);
					}
				}//isInner(...)
			}else{
				//System.out.println("Abstrakte Klasse, weiter zur Elternklasse.");
				Class objcp2 = objClass.getSuperclass();
				if(objcp2!=null){
					bReturn = ObjectZZZ.proofFlagZExists(objcp2.getName(), sFlagName);
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//20170307: Durch das Verschieben von FLAGZ mit den Werten DEBUG und INIT in das IObjectZZZ Interface, muss man explizit auch dort nachsehen.
		   //                Merke: Das Verschieben ist deshlab notwenig, weil nicht alle Klassen direkt von ObjectZZZ erben können, sondern das Interface implementieren müsssen.						
		Class<FLAGZ> enumClass = FLAGZ.class;				
		for(Object obj : FLAGZ.class.getEnumConstants()){
			//System.out.println(obj + "; "+obj.getClass().getName());
			if(sFlagName.equalsIgnoreCase(obj.toString())) {
				bReturn = true;
				break main;
			}
		}			
		
		
		//20170308: Merke: Wenn immer noch kein FLAGZ gefunden wurde, dieses Flag aus der aufrufenden Klasse selbst holen.
		
	}//end main:
	return bReturn;
}
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#getExceptionObject()
	 */
	public ExceptionZZZ getExceptionObject() {
		return this.objException;
	}
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetObjectZZZ#setExceptionObject(zzzKernel.custom.ExceptionZZZ)
	 */
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
	 */
	public boolean getFlagZ(String sFlagName) {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlagZ();
			Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
	@Override
	public boolean getFlag(String sFlagName) {
//		boolean bFunction = false;
//	main:{
//		if(StringZZZ.isEmpty(sFlagName)) break main;
//		
//		// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//		// bFunction = super.getFlag(sFlagName);
//		// if(bFunction == true) break main;
//		
//		// Die Flags dieser Klasse setzen
//		String stemp = sFlagName.toLowerCase();
//		if(stemp.equals("debug")){
//			bFunction = this.bFlagDebug;
//			break main;
//		}else if(stemp.equals("init")){
//			bFunction = this.bFlagInit;
//			break main;
//		}else{
//			bFunction = false;
//		}		
//	}	// end main:
//	
//	return bFunction;	
		return this.getFlagZ(sFlagName);
	}
	@Override
	public boolean setFlag(String sFlagName, boolean bFlagValue) {
		//Version Vor Java 1.6
//		boolean bFunction = true;
//		main:{
//			if(StringZZZ.isEmpty(sFlagName)) break main;
//			
//			// hier keine Superclass aufrufen, ist ja schon ObjectZZZ
//			// bFunction = super.setFlag(sFlagName, bFlagValue);
//			// if(bFunction == true) break main;
//			
//			// Die Flags dieser Klasse setzen
//			String stemp = sFlagName.toLowerCase();
//			if(stemp.equals("debug")){
//				this.bFlagDebug = bFlagValue;
//				bFunction = true;                            //durch diesen return wert kann man "reflexiv" ermitteln, ob es in dem ganzen hierarchie-strang das flag �berhaupt gibt !!!
//				break main;
//			}else if(stemp.equals("init")){
//				this.bFlagInit = bFlagValue;
//				bFunction = true;
//				break main;
//			}else{
//				bFunction = false;
//			}	
//			
//		}	// end main:
//		
//		return bFunction;	
		try {
			return this.setFlagZ(sFlagName, bFlagValue);
		} catch (ExceptionZZZ e) {
			System.out.println("ExceptionZZZ (aus compatibilitaetgruenden mit Version vor Java 6 nicht weitergereicht) : " + e.getDetailAllLast());
			return false;
		}
	}
}
