package basic.zBasic;

public class ReflectEnvironmentZZZ {
	public static final String sJAVA4 = "1.4";
	public static final String sJAVA5 = "1.5";
	public static final String sJAVA6 = "1.6";
	public static String getJavaVersionCurrent(){
		    return System.getProperty("java.runtime.version");
	}
	public static boolean isJava6() {
		String sVersionString = ReflectEnvironmentZZZ.getJavaVersionCurrent();
		String sVersionMain = ReflectEnvironmentZZZ.getJavaVersionMain(sVersionString);
		if(sVersionMain.equals(ReflectEnvironmentZZZ.sJAVA6)){
			return true;
		}else{
			return false;
		}			
	}
	
	public static boolean isJavaVersionMainCurrentEqualOrNewerThan(String sVersionString){
		boolean bReturn=false;
		main:{
		if(sVersionString==null) break main;
		if(sVersionString.trim().equals("")) break main;
		
		String sVersionMainCurrent = ReflectEnvironmentZZZ.getJavaVersionMain(ReflectEnvironmentZZZ.getJavaVersionCurrent());
		String sVersionMain = ReflectEnvironmentZZZ.getJavaVersionMain(sVersionString);

		//	Test waspassiert bei einer angenommenen JavaVersion der Umgebung
		//sVersionMainCurrent = "1.7";
		
		//Vergleich von Strings 
		if(sVersionMainCurrent.compareTo(sVersionMain)>=1){
			bReturn= true; //Also 1.7 ist neur als 1.6
		}else if (sVersionMainCurrent.compareTo(sVersionMain)==0){
			bReturn= true; //Also der Gleichheitsfall
		}else{
			bReturn = false;  //Also 1.4 ist älter als 1.6
		}
		}//end main
		return bReturn;
	}
	
	
	public static String  getJavaVersionMainCurrent(){
		String sVersionString = ReflectEnvironmentZZZ.getJavaVersionCurrent();
		String sVersionMain = ReflectEnvironmentZZZ.getJavaVersionMain(sVersionString);
		return sVersionMain;
	}	
		
	public static String getJavaVersionMain(String sVersionString){
		String sReturn = new String("");
		main:{
			if(sVersionString==null) break main;
			if(sVersionString.trim().equals("")) break main;
			
			sReturn = sVersionString.substring(0, 3);
		}//end main:
		return sReturn;
	}
}
