package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierManagerZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class StringFormatManagerZZZ extends AbstractStringFormatManagerZZZ implements IStringFormatManagerJustifiedZZZ{
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
	private StringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	public static IStringFormatManagerJustifiedZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(StringFormatManagerZZZ.class) {
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
	
	public static StringFormatManagerZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objFormatManagerINSTANCE = new StringFormatManagerZZZ();
		return (StringFormatManagerZZZ)objFormatManagerINSTANCE;
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
		return this.computeJustified(sLogs);		
	}
    
    @Override
	public synchronized String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(obj, sLogs);		
	}
    
    @Override
	public synchronized String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(classObj, sLogs);		
	}
	
    
    //######################
    @Override
   	public synchronized String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
   		return this.computeJustified(ienumFormatLogString);	
   	}
       		       
    @Override
	public synchronized String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		return this.computeJustified(ienumaFormatLogString, sLogs);		
	}

	@Override
	public synchronized String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(obj, ienumaFormatLogString, sLogs);			
	}
	
	
	@Override
	public synchronized String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(classObj, ienumaFormatLogString, sLogs);	
	}
	

	//######################
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(objFormater, obj, sLogs);		
	}
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.computeJustified(objFormater, ienumFormatLogString);
	}
	
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(objFormater, ienumaFormatLogString, sLogs);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.computeJustified(objFormater, obj, ienumFormatLogString);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(objFormater, obj, ienumFormatLogString, sLogs);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(objFormater, obj, ienumaFormatLogString, sLogs);
	}
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(objFormater, classObj, ienumFormatLogString, sLogs);				
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(objFormater, classObj, ienumaFormatLogString, sLogs);		
	}
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return this.computeJustified(objFormater, classObj, ienumFormatLogString);	
	}
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified(objFormater, classObj, sLogs);	
	}

	
	
	//#########################################################################################
	///##################################################################
  
	@Override
	public synchronized String computeJustified(String... sLogs) throws ExceptionZZZ {
		return computeJustified_(this.getClass(), sLogs);	
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
		
		return computeJustified_(objClass, sLogs);
	}
	

	@Override
	public synchronized String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ {				
		return computeJustified_(classObj, sLogs);
	}
	
	private String computeJustified_(Class classObj, String... sLogs) throws ExceptionZZZ {				
		String sReturn = null;
		main:{
			String stemp=null;
		
			//#######################
			//Hole das Format, aus einem Default.
			//Will man keinen Default-Format-Style haben, dann soll man halt die Methode mit Formatanweisung nutzen
			IStringFormaterZZZ objFormater = new StringFormaterZZZ();
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn = objFormater.getFormatPositionsMapped();
						 
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
						
			sReturn = computeJustified_(classObj, ienumaFormatLogStringIn, sLogs);
		}//end main:
		return sReturn;	
	}
	
	
	
	//############################################################
	@Override
	public synchronized String computeJustified(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified_(this.getClass(), ienumaFormatLogStringIn, sLogs);
	}	
	
	
	@Override
	public synchronized String computeJustified(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		Object obj = null;
		if(objIn==null) {
			obj = this;
		}else {
			obj = objIn;
		}
		Class objClass = obj.getClass();
		
		return this.computeJustified_(objClass, ienumaFormatLogStringIn, sLogs);
	}
	
	@Override
	public synchronized String computeJustified(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		return computeJustified_(classObj, ienumaFormatLogStringIn, sLogs);
	}
	
	private String computeJustified_(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
						
			//#######################
						
			//1. Teile Formatierung an den Zeilentrennern auf.
			List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
						
			//3. Alle Zeilen unbündig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
				listasJaggedTemp = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
				listasJaggedReturn.addAll(listasJaggedTemp);
			}
			
			//4. Zeilen bündig machen
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
				listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn, ienumaFormatLogString);
				listasJustifiedReturn.addAll(listasJustifiedTemp);
			}
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}

	
	
	
	
	

	
	
	
	//####################################################
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn) throws ExceptionZZZ {		
		return this.computeJustified_(objFormater, this.getClass(), ienumFormatLogStringIn);					
	}

	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object objIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn=null;
		main:{							
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = this.computeJustified_(objFormater, objClass, ienumFormatLogString);
		}//end main:
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn) throws ExceptionZZZ {
		return computeJustified_(objFormater, classObj, ienumFormatLogStringIn);
	}
	
	private String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			String stemp;
			
			//#######################						
			//1. Teile Formatierung an den Zeilentrennern auf.
			//Nein, entfaellt, da nur 1 Formatanweisung
			//List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
			
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			
			//Nein hier wurde explizit nur 1 Formatanweisung übergeben. Dies dann zum Ausrechnen des Zeileninhalts nutzen
//			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
//				listasJaggedTemp = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
//				listasJaggedReturn.addAll(listasJaggedTemp);
//			}
						
			//Das ist hier nur 1 Zeile, darum keine Schleife
			listasJaggedTemp = super.computeJaggedArrayList(objFormater, classObj, ienumFormatLogStringIn);
			listasJaggedReturn.addAll(listasJaggedTemp);
			
			//4. Zeilen buendig machen
			//Das ist hier zwar nur 1 Zeile, wir muessen aber alle Justifier ansetzen, um an die passende Stelle zu ruecken
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
					
			//Wir brauchen blos keine Formatanweisungen für die Separatoren zu uebergeben.
			listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn);
			listasJustifiedReturn.addAll(listasJustifiedTemp);
			
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());						
		}//end main:
		return sReturn;
	}


	//########################################################
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object objIn, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn=null;
		main:{							
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = this.computeJustified_(objFormater, objClass, ienumFormatLogStringIn, sLogs);
		}//end main:
		return sReturn;		
	}

	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		return computeJustified_(objFormater, classObj, ienumFormatLogStringIn, sLogs);
	}
	
	private String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			String stemp = null;
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn = objFormater.getFormatPositionsMapped();
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//#######################						
			//1. Teile Formatierung an den Zeilentrennern auf.
			//Nein, entfaellt, da nur 1 Formatanweisung
			//List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
			
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			
			//Nein hier wurde explizit nur 1 Formatanweisung übergeben. Dies dann zum Ausrechnen des Zeileninhalts nutzen
//			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
//				listasJaggedTemp = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
//				listasJaggedReturn.addAll(listasJaggedTemp);
//			}
						
			//Das ist hier nur 1 Formatanweisung, darum keine Schleife
			listasJaggedTemp = super.computeJaggedArrayList(objFormater, classObj, ienumFormatLogStringIn, sLogs);
			listasJaggedReturn.addAll(listasJaggedTemp);
			
			//4. Zeilen buendig machen
			//Das ist hier zwar nur 1 Zeile, wir muessen aber alle Justifier ansetzen, um an die passende Stelle zu ruecken
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
					
			//Wir brauchen blos keine Formatanweisungen für die Separatoren zu uebergeben.
			listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn);
			listasJustifiedReturn.addAll(listasJustifiedTemp);
			
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());									
		}//end main:
		return sReturn;
	}
	
	//####################################################################
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		return computeJustified_(objFormater, this.getClass(), ienumaFormatLogStringIn, sLogs);
	}
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = computeJustified_(objFormater, objClass, ienumaFormatLogStringIn, sLogs);	
		}//end main:
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		return computeJustified_(objFormater, classObj, ienumaFormatLogStringIn, sLogs);
	}
	
	private String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			String stemp = null;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//#######################						
			//1. Teile Formatierung an den Zeilentrennern auf.
			List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
						
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
				listasJaggedTemp = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
				listasJaggedReturn.addAll(listasJaggedTemp);
			}
			
			//4. Zeilen buendig machen
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
				listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn, ienumaFormatLogString);
				listasJustifiedReturn.addAll(listasJustifiedTemp);
			}
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());						
		}//end main:
		return sReturn;
	}
	
	//##################################################################
	//### Hole ohne Angabe der Formatanweisungen als Array, diese aus dem objFormater
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object objIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = computeJustified_(objFormater, objClass, sLogs);	
		}//end main:
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		return computeJustified_(objFormater, classObj, sLogs);
	}
	
	private String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			//1. Teile Formatierung an den Zeilentrennern auf.
			//Nein, entfaellt, da nur 1 Formatanweisung
			//List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
	
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
			//Ohne Formatanweisungen muss das wohl in der im folgenden aufgerufenen super - Methode passieren
			
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();

			
			listasJaggedTemp = super.computeJaggedArrayList(objFormater, classObj, sLogs);
			listasJaggedReturn.addAll(listasJaggedTemp);
			
			//4. Zeilen buendig machen
			//Das ist hier zwar nur 1 Zeile, wir muessen aber alle Justifier ansetzen, um an die passende Stelle zu ruecken
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
					
			//Wir brauchen blos keine Formatanweisungen für die Separatoren zu uebergeben.
			listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn);
			listasJustifiedReturn.addAll(listasJustifiedTemp);
			
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}

	//#######################################################################################################
	//##################################################################################
	//### HashMap mit den "Daten"
	//TODOGOON20260127: HashMap übergeben, aber: Das müsste dann auch irgendwie umstrukturiert werden hin zur ArrayList-Methodik
	
	//###################################################################
	@Override
	public synchronized String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}

	@Override
	public synchronized String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.computeJustified(obj, hm);
	}

	@Override
	public synchronized String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(classObj, hm);	
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.computeJustified(objFormater, hm);	
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

	
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
				
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}

	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}
	
	
	@Override
	public synchronized String computeJustified(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged(objFormater, classObj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}


	@Override
	public synchronized String computeJustified(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}
	
	@Override
	public synchronized String computeJustified(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged(obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}

	
}
