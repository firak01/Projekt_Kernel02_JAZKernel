package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator01StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator02StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator03StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator04StringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorFilePositionJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractStringFormatManagerZZZ extends AbstractObjectWithFlagZZZ implements IStringFormatManagerZZZ, IStringFormatManagerJaggedZZZ{
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
	
	//Die liste der verwendeten Justifier, nach der Filterung
	private volatile ArrayListUniqueZZZ<IStringJustifierZZZ> listaStringJustifierUsed = null;
	
	
	private static final boolean INITIALIZED = true;// Trick, um Mehrfachinstanzen zu verhindern (optional)
		
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}

	
	
	//##########################################################	
	//### GETTER / SETTER
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ>getStringJustifierListDefault() throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn = new ArrayListZZZ<IStringJustifierZZZ>();
		
		
		Separator01StringJustifierZZZ objJustifier01 = (Separator01StringJustifierZZZ) Separator01StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier01);

		Separator02StringJustifierZZZ objJustifier02 = (Separator02StringJustifierZZZ) Separator02StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier02);

		Separator03StringJustifierZZZ objJustifier03 = (Separator03StringJustifierZZZ) Separator03StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier03);
		
		Separator04StringJustifierZZZ objJustifier04 = (Separator04StringJustifierZZZ) Separator04StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier04);
		
		SeparatorFilePositionJustifierZZZ objJustifierFilePosition = (SeparatorFilePositionJustifierZZZ) SeparatorFilePositionJustifierZZZ.getInstance();
		listaReturn.add(objJustifierFilePosition);
				
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
	
	
	@Override
	public ArrayListUniqueZZZ<IStringJustifierZZZ>getStringJustifierListUsed() throws ExceptionZZZ{
		if(this.listaStringJustifierUsed==null) {
			this.listaStringJustifierUsed = new ArrayListUniqueZZZ<IStringJustifierZZZ>();
		}
		return this.listaStringJustifierUsed;
	}
	
	@Override
	public void setStringJustifierListUsed(ArrayListUniqueZZZ<IStringJustifierZZZ> listaStringJustifier) throws ExceptionZZZ{
		this.listaStringJustifierUsed = listaStringJustifier;
	}
	
	//+++++++++++++++++++++++++
	//Die optimale Reihenfolge der Justifier orientiert sich an der Reihenfolge der Formatanweisungen oder der Reihenfolge der Zeichen im String
	//Bei einer nicht optimalen Reihenfolge sind die vorderen Spalten ggfs. zu breit.
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringFormatManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumFormatLogString);
		}//end main:
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringFormatManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumaFormatLogString);
		}//end main:
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, hm);
		}//end main:
		return listaReturn;
	}
	
	
	//#############################################
	@Override
	public IStringJustifierZZZ getStringJustifierDefault() throws ExceptionZZZ{
		//Verwende als default das Singleton vom MessageStringJustifier
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
	public String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {		
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {		
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(null, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(obj, sLogs);
	}

	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.compute(classObj, sLogs);
	}
	
	//######################
	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(ienumFormatLogString);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, ienumFormatLogString);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(obj, ienumFormatLogString);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, ienumFormatLogString);						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(ienumaFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			IStringJustifierZZZ[] aStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierArray(listaStringJustifierDefault, ienumaFormatLogString);						
			for(int icount=0; icount<=aStringJustifier.length-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;	
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, ienumFormatLogString);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumaFormatLogString);						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, ienumFormatLogString);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumaFormatLogString);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(classObj, ienumFormatLogString);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, ienumFormatLogString);
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.computeJagged(hm);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
			
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, hm);
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(obj, hm);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, hm);			
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(classObj, hm);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, hm);						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(obj, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, objFormater.getFormatPositionsMapped());						
			for(int icount=0; icount<=listaStringJustifier.size();icount++) {
				IStringJustifierZZZ objJustifier = this.getStringJustifier(icount);
				sReturn = objJustifier.justifyInfoPart(sReturn);
			}
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = objFormater.compute(classObj, sLogs);
			
			//Das wuerde die Defaultreihenfolge holen.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierList();
						
			//Ziel ist es aber die Reihenfolge des "Buendigmachens" per Justifier an der Reihenfolge der Formatanweisungen und der Separatoren auszurichten.
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = StringFormatManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, objFormater.getFormatPositionsMapped());									
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
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(sLogs);
	}

	
	@Override
	public String computeJagged(Object obj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(obj, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public String computeJagged(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(hm);
	}

	@Override
	public String computeJagged(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(obj, hm);
	}

	@Override
	public String computeJagged(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(classObj, hmLog);
	}

	@Override
	public String computeJagged(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public String computeJagged(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(hm);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, hm);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, hm);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged(obj, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, Class classObj, String... sLogs)	throws ExceptionZZZ {
		return objFormater.computeJagged(classObj, sLogs);
	}

	@Override
	public String computeJagged(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged(ienumFormatLogString);
	}
	
	
	//###########################################################
	//### Jede Methode auch als ....ArrayList
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(sLogs);
	}

	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(obj, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(obj, hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(classObj, hmLog);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(ienumFormatLogString);
	}
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(obj, ienumFormatLogString);
	}
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(classObj, ienumFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(obj, ienumFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(classObj, ienumFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(obj, hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(classObj, hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(obj, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, Class classObj, String... sLogs)	throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(classObj, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList(ienumFormatLogString);
	}
	
	//###################################################
	//### Merke: COMPUTE JUSTIFIED gibt es nicht bei XML-Format Manager, darum die entsprechenden Methoden direkt in FormatManager-Klasse
	//###################################################

	
	
	//###################################################
	//### FLAG: ILogStringFormatManagerZZZ
	//###################################################
	@Override
	public boolean getFlag(IStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IStringFormatManagerZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IStringFormatManagerZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStringFormatManagerZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IStringFormatManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
