package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class StringFormatManagerZZZ_BACKUP20260208 extends AbstractStringFormatManagerZZZ implements IStringFormatManagerJustifiedZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static IStringFormatManagerZZZ objFormatManagerINSTANCE=null;

	//##########################################################
	//Trick, um Mehrfachinstanzen zu verhindern (optional)
	//Warum das funktioniert:
	//initialized ist static → nur einmal pro ClassLoader
	//Wird beim ersten Konstruktoraufruf gesetzt
	//Jeder weitere Versuch (Reflection!) schlägt fehl
    private static boolean INITIALIZED = false;
    
    //Reflection-Schutz ist eine Hürde, kein Sicherheitsmechanismus.
    //Denn:
    //Field f = AbstractService.class.getDeclaredField("initialized");
    //f.setAccessible(true);
    //f.set(null, false);
    //Danach kann man wieder instanziieren.
	//##########################################################
	    
	//als private deklariert, damit man es nicht so instanzieren kann, sondern die Methode .getInstance() verwenden muss
	private StringFormatManagerZZZ_BACKUP20260208() throws ExceptionZZZ{
		super();
	}
	
	public static IStringFormatManagerJustifiedZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(StringFormatManagerZZZ_BACKUP20260208.class) {
			if(objFormatManagerINSTANCE == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objFormatManagerINSTANCE = getNewInstance();
				INITIALIZED=true;
			}
		}
		return (IStringFormatManagerJustifiedZZZ) objFormatManagerINSTANCE;
	}
	
	public static StringFormatManagerZZZ_BACKUP20260208 getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objFormatManagerINSTANCE = new StringFormatManagerZZZ_BACKUP20260208();
		return (StringFormatManagerZZZ_BACKUP20260208)objFormatManagerINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objFormatManagerINSTANCE = null;
	}
	
	 // Trick, um Mehrfachinstanzen zu verhindern (optional)
    private static class HolderAlreadyInitializedHolder {
        private static final boolean INITIALIZED = true;
    }
    
	//################################################
	//### Methoden
   
    @Override
	public synchronized String compute(String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(sLogs);
		return sReturn;
	}
    
    @Override
	public synchronized String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, sLogs);
		return sReturn;
	}
    
    @Override
	public synchronized String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(classObj, sLogs);
		return sReturn;
	}
	
    
    //######################
    @Override
   	public synchronized String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
   		String sReturn = this.computeJustified(ienumFormatLogString);	
   		return sReturn;
   	}
       		       
    @Override
	public synchronized String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		String sReturn = this.computeJustified(ienumaFormatLogString, sLogs);
		return sReturn;
	}

	@Override
	public synchronized String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(obj, ienumaFormatLogString, sLogs);	
		return sReturn;
	}
	
	
	@Override
	public synchronized String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(classObj, ienumaFormatLogString, sLogs);	
		return sReturn;
	}
	
	
	
	
	
	//######################
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, sLogs);
		return sReturn;
	}
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, ienumFormatLogString);
		return sReturn;
	}
	
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, ienumaFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumFormatLogString);
		return sReturn;
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, obj, ienumaFormatLogString, sLogs);
		return sReturn;
	}
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumFormatLogString, sLogs);				
		return sReturn;
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumaFormatLogString, sLogs);		
		return sReturn;
	}
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, ienumFormatLogString);	
		return sReturn;
	}
		
	

	
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = this.computeJustified(objFormater, classObj, sLogs);	
		return sReturn;
	}

	
	//###################################################################
		@Override
		public synchronized String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
			String sReturn = super.computeJagged(hm);
			sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG2: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.

			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
				sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
			}
				
			return sReturn;
		}

		@Override
		public synchronized String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
			return this.computeJustified(obj, hm);
		}

		@Override
		public synchronized String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
			String sReturn = super.computeJagged(classObj, hm);	
			sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);			
				sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
			}
			return sReturn;
		}
		
		@Override
		public synchronized String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
			String sReturn = this.computeJustified(objFormater, hm);
			return sReturn;
		}
		
		
		@Override
		public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
			String sReturn = this.computeJustified(objFormater, obj, hm);
			return sReturn;
		}
		
		@Override
		public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
			String sReturn = this.computeJustified(objFormater, classObj, hm);
			return sReturn;
		}

		

	
		//#########################################################################################
	///##################################################################
  
	@Override
	public synchronized String computeJustified(String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp = null;
			
			//#######################
			//Hole das Format, aus einem Default.
			IStringFormaterZZZ objFormater = new StringFormaterZZZ();
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = objFormater.getFormatPositionsMapped();
	
		
		
			//#######################
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
						
			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(this, ienumaFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}
	
	
	
	@Override
	public synchronized String computeJustified(Object objIn, String... sLogs) throws ExceptionZZZ {
		
		Object obj = null;
		if(objIn==null) {
			obj = this;
		}else {
			obj = objIn;
		}
		Class objClass = obj.getClass();
		
		String sReturn = this.computeJustified(objClass, sLogs);
		return sReturn;
	}
	
	
	
	@Override
	public synchronized String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ {				
		String sReturn = null;
		main:{
			String stemp=null;
		
			//#######################
			//Hole das Format, aus einem Default.
			IStringFormaterZZZ objFormater = new StringFormaterZZZ();
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = objFormater.getFormatPositionsMapped();
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
			
			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;	
	}
	
	
	
	//############################################################
	
	@Override
	public synchronized String computeJustified(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		Object obj = null;
		if(objIn==null) {
			obj = this;
		}else {
			obj = objIn;
		}
		Class objClass = obj.getClass();
		
		String sReturn = this.computeJustified(objClass, ienumaFormatLogString, sLogs);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogString==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
						
			//#######################
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
			
			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;													
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, listasReturn);//true ist default... Fasse ggf. 1. Zeile und 2. Zeile zusammen beim Normieren.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}

	
	@Override
	public synchronized String computeJustified(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp;
		
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogString==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//#######################
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
						
			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(this, ienumaFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}
	
	
	
	//####################################################
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		String sReturn=null;
		main:{
			String stemp = null;
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = objFormater.getFormatPositionsMapped();
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
			
			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(this.getClass(), ienumFormatLogString);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}


	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object objIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		Object obj = null;
		if(objIn==null) {
			obj = this;
		}else {
			obj = objIn;
		}
		Class objClass = obj.getClass();
		
		String sReturn = this.computeJustified(objFormater, objClass, ienumFormatLogString);
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			String stemp = null;
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = objFormater.getFormatPositionsMapped();
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
			
			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(classObj, ienumaFormatLogString);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}
	

	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn=null;
		main:{
			String stemp=null;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogString==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//################################################################
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
	
			
			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(this.getClass(), ienumaFormatLogString, sLogs);
			
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}
	

	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp = null;
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
			this.setStringJustifierList(listaStringJustifier);

			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(obj, ienumFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;	
	}

	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			String stemp = null;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogString==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//################################################
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);

			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(obj, ienumaFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}

	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			String stemp = null;
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
			this.setStringJustifierList(listaStringJustifier);

			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(classObj, ienumFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}
	
	
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			String stemp = null;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogString==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//Hole nicht eine (per CRLF zusammengefasste) Zeile, sondern jede Zeile einzeln
			//... Liste der Justifier ausserhalb der Schleife holen
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);

			//... Zeilen holen
			ArrayListZZZ<String>listasJagged = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			ArrayListZZZ<String>listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				listasReturn = StringFormaterUtilZZZ.justifyInfoPartArrayList(objJustifier, true, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.									
			}
			
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}

	//#######################################################################################################
	//##################################################################################
	//### HashMap mit den "Daten"
	//TODOGOON20260127: HashMap übergeben, aber: Das müsste dann auch irgendwie umstrukturiert werden hin zur ArrayList-Methodik
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}
	
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, classObj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(objFormater, obj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		//OHNE FORMATANWEISUNGSSTRING DIE DEFAULTREIHENFOLGE DER FILTER NEHMEN
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListDefault();
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}
	
	
		
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = super.computeJagged(objFormater, classObj, sLogs);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = objFormater.getFormatPositionsMapped();
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);		
		}
		return sReturn;
	}

	@Override
	public synchronized String computeJustified(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}	
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
	    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
		//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
		
		ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
		this.setStringJustifierList(listaStringJustifier);
		for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
			IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);		
			this.getStringJustifierListUsed().add(objJustifier);
			sReturn = StringFormaterUtilZZZ.justifyInfoPart(objJustifier, sReturn);
		}		
		return sReturn;
	}

	
}
