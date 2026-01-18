package basic.zBasic.component;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;

public interface IProgramMonitorZZZ extends IProgramZZZ, IModuleUserZZZ {
	public ArrayList<IProgramZZZ> getProgramList();
	public void setProgramList(ArrayList<IProgramZZZ> listaRunnables);
	
	//Die Programme /runnable oder einfache) in die ArrayList der Programme aufnehmen. 
	//Merke: Dabei werden sie auch sofort am Monitor registriert, d.h. wenn der Monitor einen Event wirft, dann koennen auch "seine" Programme darauf reagieren.
	public void addProgram(IProgramZZZ objProgram) throws ExceptionZZZ;
	
	//Die lauffähigen Programme aus der ArrayList starten.
	public boolean startProgramAll() throws ExceptionZZZ;
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
		
	//#######################################################################################
	// STATUS	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Die StatusId für Stati, aus dieser Klasse selbst. Nicht die Stati der anderen Klassen.
	//Sollte dann irgendwie einzigartig sein.
	public static int iSTATUSLOCAL_GROUPID=1;
		
	//++++++++++++++++++++++++
	//ALIAS("Uniquename","Statusmeldung","Beschreibung, wird nicht genutzt....",)
	public enum STATUSLOCAL implements IEnumSetMappedStatusLocalZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		ISSTARTNEW(iSTATUSLOCAL_GROUPID,"isstartnew","ZZZ: ProgramMonitor nicht gestartet",""),
		ISSTARTING(iSTATUSLOCAL_GROUPID,"isstarting","ZZZ: ProgramMonitor startet...",""),		
		ISSTARTED(iSTATUSLOCAL_GROUPID,"isstarted","ZZZ: ProgramMonitor gestartet",""),
		ISSTARTNO(iSTATUSLOCAL_GROUPID,"isstartno","ZZZ: ProgramMonitor nicht gestartet",""),

		//Hier dann für beobachtete events einen eigenen Namen vergeben, um diesen weiterzuleiten
		//Beispiele
		//HASPROCESSSTARTNEW(iSTATUSLOCAL_GROUPID,"hasprocessnew","OVPN: Prozess nicht gestartet",""),
		//HASPROCESSSTARTING(iSTATUSLOCAL_GROUPID,"hasprocessstarting","OVPN: Prozess startet",""),
		//HASPROCESSSTARTED(iSTATUSLOCAL_GROUPID,"hasprocessstarted","OVPN: Prozess gestartet",""),
		//HASPROCESSOUTPUT(iSTATUSLOCAL_GROUPID,"hasprocessoutput","OVPN: Prozess mit Ausgabe",""),
		//HASPROCESSCONNECTION(iSTATUSLOCAL_GROUPID,"hasprocessconnection","OVPN: Process verbunden",""),
		
		//HASPROCESSERROR(iSTATUSLOCAL_GROUPID,"hasprocesserror","OVPN: Process meldet fehler",""),
		//HASPROCESSSTOPPED(iSTATUSLOCAL_GROUPID,"hasprocessstopped","OVPN: Process gestoppt",""),
		
		ISSTOPPED(iSTATUSLOCAL_GROUPID,"isstopped","ZZZ: ProgramMonitor beendet",""),			
		HASERROR(iSTATUSLOCAL_GROUPID,"haserror","ZZZ: ProgramMonitor Fehler","");		
		
		private int iStatusGroupId;
		private String sAbbreviation,sStatusMessage,sDescription;
	
		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.
		STATUSLOCAL(int iStatusGroupId, String sAbbreviation, String sStatusMessage, String sDescription) {
			this.iStatusGroupId = iStatusGroupId;
		    this.sAbbreviation = sAbbreviation;
		    this.sStatusMessage = sStatusMessage;
		    this.sDescription = sDescription;
		}
	
		public int getStatusGroupId() {
			return this.iStatusGroupId;
		}
		
		public String getAbbreviation() {
		 return this.sAbbreviation;
		}
		
		public String getStatusMessage() {
			 return this.sStatusMessage;
		}
		
		public EnumSet<?>getEnumSetUsed(){
			return STATUSLOCAL.getEnumSet();
		}
	
		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		@SuppressWarnings("rawtypes")
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<STATUSLOCAL> enumClass = STATUSLOCAL.class;
			EnumSet<STATUSLOCAL> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
			
			 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			 for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((STATUSLOCAL) obj);
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
		//the valueOfMethod <--- Translating from DB
		public static STATUSLOCAL fromAbbreviation(String s) {
		for (STATUSLOCAL state : values()) {
		   if (s.equals(state.getAbbreviation()))
		       return state;
		}
		throw new IllegalArgumentException("Not a correct abbreviation: " + s);
		}
	
		//##################################################
		//#### Folgende Methoden bring Enumeration von Hause aus mit. 
				//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
		@Override
		public String getName() {	
			return super.name();
		}
	
		@Override
		public String toString() {//Mehrere Werte mit # abtennen
		    return this.sAbbreviation+"="+this.sDescription;
		}
	
		@Override
		public int getIndex() {
			return ordinal();
		}
	
		//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
		@Override
		public int getPosition() {
			return getIndex()+1; 
		}
	
		@Override
		public String getDescription() {
			return this.sDescription;
		}
		//+++++++++++++++++++++++++
	}//End internal Class
}
