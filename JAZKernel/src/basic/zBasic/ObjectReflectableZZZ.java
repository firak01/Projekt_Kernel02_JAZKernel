package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class ObjectReflectableZZZ <T> extends ObjectZZZ implements IObjectReflectableZZZ{
	//private String sClassMethodCalling=null;		
	private HashMap<String,String> hmClassMethodCalling = new HashMap<String,String>();
	
	public ObjectReflectableZZZ(){
		super();
		try{
			this.initClassMethodCallingString();
		}catch(ExceptionZZZ ez){
			System.out.println("ExceptionZZZ:" + ez.getDetailAllLast());
		}
	}
	@Override
	public String getClassMethodCallingString() throws ExceptionZZZ {
		TODO GOON: HIER EINE HASHMAP ALLER KONTRUKTOREN/KLASSEN UND DER SIE AUFRUFENDEN METHODE AUSLESEN.
		//Basierend auf dem aktuellen Klassennamen...
		
		return this.sClassMethodCalling;
	}

	@Override
	public void setClassMethodCallingString(String sClassMethodCalling) throws ExceptionZZZ {
		main:{
		//Nur setzen, wenn nicht schon gesetzt wurde.
			//if(this.sClassMethodCalling!=null) break main;
			
			 //Prüfe auf Korrektheit des Wertes
			if(StringZZZ.isEmpty(sClassMethodCalling)){
				ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: String Classenname.Methodenname erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!StringZZZ.contains(sClassMethodCalling, ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR)){
				ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: String Classenname.Methodenname erwartet. Es fehlt der Punkt!", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			TODO GOON HIER EINE HASHMAP ALLER KONTRUKTOREN/KLASSEN UND DER SIE AUFRUFENDEN METHODE FÜLLEN.
			
			this.sClassMethodCalling=sClassMethodCalling; 
		}		
	}
	@Override
	public void initClassMethodCallingString() throws ExceptionZZZ {
		String sClassMethodCalling = ReflectCodeZZZ.getClassMethodCallingString(2);
		this.setClassMethodCallingString(sClassMethodCalling);
	}
	
	
//	@Override
//	public void setClassMethodInitialisedName(Class<?>objClass, String sMethod) throws ExceptionZZZ{
//		if(objClass==null){
//			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: Classenobject erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//		if(StringZZZ.isEmpty(sMethod)){
//			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: String Methodenname erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//				
//		String sClassname = objClass.getName();
//		this.sClassMethodInitialisedName = sClassname + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + sMethod;		
//	}			

}
