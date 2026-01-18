package basic.zBasic.util.log;

import java.util.LinkedHashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.IStringJustifierZZZ;
import basic.zBasic.util.datatype.string.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.datatype.string.Separator01StringJustifierZZZ;
import basic.zBasic.util.datatype.string.Separator02StringJustifierZZZ;
import basic.zBasic.util.datatype.string.Separator03StringJustifierZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractLogStringFormatManagerZZZ extends AbstractObjectWithFlagZZZ implements ILogStringFormatManagerZZZ, ILogStringFormatManagerJaggedZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein
	
	
	// --- Globale Objekte ---
	//Zum Buendig machen
	//Idee: Bei mehreren Instanzen per Default auf das Singleton zugreifen
	//      Aber ggfs. ueberschreibend auf einen hinterlegten zugreifen.
	//Merke: XML-Strings werden nicht buendig gemacht. 
	//       Darum wird der StringJustifier nicht mehr in dieser Elternklasse im Konstruktor an den "Formater" uebergeben.
	private volatile ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = null;
	
	private static final boolean INITIALIZED = true;// Trick, um Mehrfachinstanzen zu verhindern (optional)
		
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractLogStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}

	
	
	//##########################################################	
	//### GETTER / SETTER
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ>getStringJustifierListDefault() throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn = new ArrayListZZZ<IStringJustifierZZZ>();
		
		TODOGOON20260102: Hier muss die Reihenfolge irgendwie aus dem uebergebenen Formatanweisungs-Array ermittelt werden.
		Separator01StringJustifierZZZ objJustifier01 = (Separator01StringJustifierZZZ) Separator01StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier01);

		Separator02StringJustifierZZZ objJustifier02 = (Separator02StringJustifierZZZ) Separator02StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier02);

		Separator03StringJustifierZZZ objJustifier03 = (Separator03StringJustifierZZZ) Separator03StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier03);
		
		SeparatorMessageStringJustifierZZZ objJustifierMessage = (SeparatorMessageStringJustifierZZZ) SeparatorMessageStringJustifierZZZ.getInstance();
		listaReturn.add(objJustifierMessage);
		
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ>getStringJustifierList() throws ExceptionZZZ{
		if(this.listaStringJustifier==null) {
			this.listaStringJustifier = this.getStringJustifierListDefault();			
		}
		return this.listaStringJustifier;
	}
	
	@Override
	public void setStringJustifierList(ArrayListZZZ listaStringJustifier) throws ExceptionZZZ{
		this.listaStringJustifier = listaStringJustifier;
	}
	
	//+++++++++++++++++++++++++
	//Die optimale Reihenfolge der Justifier orientiert sich an der Reihenfolge der Formatanweisungen oder der Reihenfolge der Zeichen im String
	//Bei einer nicht optimalen Reihenfolge sind die vorderen Spalten ggfs. zu breit.
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumaFormatLogString, listaStringJustifierDefault);
		}//end main:
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = LogStringFormatManagerUtilZZZ.getStringJustifierList(hm, listaStringJustifierDefault);
		}//end main:
		return listaReturn;
	}
	
	
	//#############################################
	@Override
	public IStringJustifierZZZ getStringJustifierDefault() throws ExceptionZZZ{
		//Verwende als default das Singleton vom MessageStringJutifier
		return SeparatorMessageStringJustifierZZZ.getInstance();
	}
	
	@Override
	public IStringJustifierZZZ getStringJustifier(int iIndex) throws ExceptionZZZ {
		if(!this.hasStringJustifier(iIndex)) {
			//Das Problem ist, so wird fuer jeden Indexwert immer etwas erstellt, das darf nicht sein... eigentlich eine Endlosschleife
			//Verwende als default das Singleton
			//IStringJustifierZZZ objJustifier = this.getStringJustifierDefault();
			//this.addStringJustifier(objJustifier);			
			return null;
		}else {
			//Verwende als "manual override" den einmal hinterlegten StringJustifier.
			return this.getStringJustifierList().get(iIndex);
		}
	}

	@Override
	public void addStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ {
		this.getStringJustifierList().add(objStringJustifier);
	}

	//##########################################################
	//### METHODEN ##########
	@Override
	public boolean hasStringJustifier(int iIndex) throws ExceptionZZZ{
		if(this.listaStringJustifier==null) {
			return false;
		}else {
			if(this.listaStringJustifier.size()>=iIndex+1) {
				return true;
			}else {
				return false;
			}
		}		
	}
	
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(this.listaStringJustifier==null) {
				bReturn=false;
			}else {
				this.listaStringJustifier.clear();
				bReturn = true;
			}
		}//end main:
		return bReturn;
	}
	
	
	//#################################################################
	//### COMPUTE
	//#################################################################
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {		
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(null, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(obj, sLogs);
	}

	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.compute(classObj, sLogs);
	}
	
	//######################
	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(ienumFormatLogString);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumFormatLogString, listaStringJustifierDefault);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(obj, ienumFormatLogString);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumFormatLogString, listaStringJustifierDefault);						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(ienumaFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			IStringJustifierZZZ[] aStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierArray(ienumaFormatLogString, listaStringJustifierDefault);						
			for(int icount=0; icount<=aStringJustifier.length-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;	
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumFormatLogString, listaStringJustifierDefault);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumaFormatLogString, listaStringJustifierDefault);						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumFormatLogString, listaStringJustifierDefault);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumaFormatLogString, listaStringJustifierDefault);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(classObj, ienumFormatLogString);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(ienumFormatLogString, listaStringJustifierDefault);
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(hm);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(hm, listaStringJustifierDefault);
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(obj, hm);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(hm, listaStringJustifierDefault);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(classObj, hm);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(hm, listaStringJustifierDefault);						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(obj, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(objFormater, listaStringJustifierDefault);						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(classObj, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = LogStringFormatManagerUtilZZZ.getStringJustifierList(objFormater, listaStringJustifierDefault);									
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}
		
	//#####################################################
	//### Aus ILogStringFormat Manager JAGGED
	//#####################################################
	@Override
	public String computeJagged(String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(sLogs);
	}

	
	@Override
	public String computeJagged(Object obj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, (IEnumSetMappedLogStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public String computeJagged(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(hm);
	}

	@Override
	public String computeJagged(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, hm);
	}

	@Override
	public String computeJagged(Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, hmLog);
	}

	@Override
	public String computeJagged(IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ILogStringFormaterZZZ objFormater = new LogStringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(hm);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, hm);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, hm);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, Class classObj, String... sLogs)	throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, sLogs);
	}

	@Override
	public String computeJagged(ILogStringFormaterZZZ objFormater, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(ienumFormatLogString);
	}
	
	//###################################################
	//### Merke: COMPUTE JUSTIFIED gibt es nicht bei XML-Format Manager, darum die entsprechenden Methoden direkt in FormatManager-Klasse
	//###################################################

	
	
	//###################################################
	//### FLAG: ILogStringFormatManagerZZZ
	//###################################################
	@Override
	public boolean getFlag(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(ILogStringFormatManagerZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(ILogStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
