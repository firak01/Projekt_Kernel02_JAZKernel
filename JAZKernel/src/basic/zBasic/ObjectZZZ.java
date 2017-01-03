package basic.zBasic;

import static java.lang.System.out;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ObjectZZZ <T> implements IConstantZZZ, IObjectZZZ, IFunctionZZZ, IFlagZZZ{
	//Flags, die alle Z-Objekte haben
//	private boolean bFlagDebug;
//	private boolean bFlagInit;
		
	/**20130721: Eweitert um HashMap und die Enum-Flags, Compiler auf 1.7 ge�ndert
	 * 
	 */
	public enum FLAGZ{
		DEBUG, INIT;
	}

	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	
	protected ExceptionZZZ objException = null;    // diese Exception hat jedes Objekt
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu k�nnen.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public ObjectZZZ(){
	}
	public ObjectZZZ(String sFlag){
		if(!StringZZZ.isEmpty(sFlag))	this.setFlag(sFlag, true);
	}
	public ObjectZZZ(String[] saFlag){
		if(saFlag!=null){
			if(saFlag.length>=1){
				for(int icount =0; icount <= saFlag.length-1; icount++){
					this.setFlag(saFlag[icount], true);
				}
			}
		}
	}
	
	
	//### FLAGS ####################
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - �ber Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	public boolean setFlagZ(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			

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

	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - �ber Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
	 * @param name 
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	public boolean proofFlagZExists(String sFlagName){
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;			
				
				Class objClass4Enum = this.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
				String sFilterName = objClass4Enum.getSimpleName();
				
				ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
				if(listEmbedded == null) break main;
				
				for(Class objClass : listEmbedded){
					
					Field[] fields = objClass.getDeclaredFields();
					for(Field field : fields){
						if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zur�ckgegeben.
							out.format("%s# Field...%s%n", ReflectCodeZZZ.getMethodCurrentName(), field.getName());
							if(sFlagName.equalsIgnoreCase(field.getName())){
								bReturn = true;
								break main;
							}
						}				
				}
			}//end if objaEnumConstant!=null
						
		}//end main:
		return bReturn;
	}

public static boolean proofFlagZExists(IFlagZZZ objcp, String sFlagName) {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName))break main;
		if(objcp==null)break main;
					
		Class objClass4Enum = objcp.getClassFlagZ();	//Aufgrund des Interfaces IFlagZZZ wird vorausgesetzt, dass diese Methode vorhanden ist.
		String sFilterName = objClass4Enum.getSimpleName();
		
		//TODO: Nachfolgender Code ist Redundant, bleibt aber drin, solange "FLAGZ" nicht irgendwo als Konstante definiert ist.
		ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(objcp.getClass(), sFilterName);
		if(listEmbedded == null) break main;
		
		for(Class objClass : listEmbedded){
			
			Field[] fields = objClass.getDeclaredFields();
			for(Field field : fields){
				if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zur�ckgegeben.
					out.format("%s# Field...%s%n", ReflectCodeZZZ.getMethodCurrentName(), field.getName());
					if(sFlagName.equalsIgnoreCase(field.getName())){
						bReturn = true;
						break main;
					}
				}				
			}		
		}
	}//End main:
	if(bReturn){
		System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# VORHANDEN Flag='" + sFlagName + "'");
	}else{
		System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# NICHT DA Flag='" + sFlagName + "'");
	}
	return bReturn;
}

public static boolean proofFlagZExists(Class objcp, String sFlagName) {
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
				if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zur�ckgegeben.
					out.format("%s# Field...%s%n", ReflectCodeZZZ.getMethodCurrentName(), field.getName());
					if(sFlagName.equalsIgnoreCase(field.getName())){
						bReturn = true;
						break main;
					}
				}				
			}		
		}
	}//End main:
	if(bReturn){
		System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# VORHANDEN Flag='" + sFlagName + "'");
	}else{
		System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# NICHT DA Flag='" + sFlagName + "'");
	}
	return bReturn;
}

public static boolean proofFlagZExists(String sClassName, String sFlagName){
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFlagName))break main;
		if(StringZZZ.isEmpty(sClassName))break main;
		try {
			
			//Existiert in der Elternklasse oder in der aktuellen Klasse das Flag?
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# ObjektInstanz erzeugen f�r '" + sClassName + "'");
			Class objClass = Class.forName(sClassName);		
			
			//!!! f�r abstrakte Klassen gilt: Es kann per Reflection keine neue Objektinstanz geholt werden.
			if(!ReflectClassZZZ.isAbstract(objClass)){
			
				IFlagZZZ objcp = (IFlagZZZ)objClass.newInstance();  //Aus der Objektinstanz kann dann gut die Enumeration FLAGZ ausgelesen werden.				
				if(objcp==null){
				}else{
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# ObjektInstanz f�r '" + objcp.getClass().getName() + "' erfolgreich erzeugt. Nun daraus Enum Klasse holen... .");
					bReturn = ObjectZZZ.proofFlagZExists(objcp, sFlagName);
				}
			}else{
				System.out.println("Abstrakte Klasse, weiter zur Elternklasse.");
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
	}//end main:
	return bReturn;
}
	
	public HashMap<String, Boolean>getHashMapFlagZ(){
		return this.hmFlag;
	} 
	public Class getClassFlagZ(){
		return FLAGZ.class;
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
	@Override
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
