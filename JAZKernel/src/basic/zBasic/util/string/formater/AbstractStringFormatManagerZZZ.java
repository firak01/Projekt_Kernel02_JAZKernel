package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierManagerZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator01StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator02StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator03StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator04StringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorFilePositionJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerUtilZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/** Abstrakte Klasse der FormatManager.
 *  
 *  Ein FormatManager ruft den Formater auf, der aus einer Formatierungsanweisung einen String baut.
 *  Dabei enthält die Formatierungsanweisung neben ggfs. konkreten Texten
 *  auch dynamische Bestandteil, wie z.B. das Datum 
 *  oder auch Bestandteile aus den Reflections, wie z.B. die aktuelle Positon im Java Code.
 *  
 *   Zudem werden Trenner definiert, die es dann ermöglichen diese Anageben im Ergebnis buendig zu sehen.
 *   
 *   
 *  Der FormatManager ruft nach dem erstellen des "unbuendigen / jagged" Textes 
 *  ggfs. einen JustifierManager auf, der eben dieses "Buendig Machen" durchfuehrt.
 *  
 *  Merke: Der FormatManager fuer XML strukturierten Text verzichtet auf das "Buendig Machen",
 *         da die Tag-Struktur doch sehr breite Spalten erzeugt.
 *  
 * @author Fritz Lindhauer, 08.02.2026, 09:03:41
 * 
 */
public abstract class AbstractStringFormatManagerZZZ extends AbstractObjectWithFlagZZZ implements IStringFormatManagerZZZ, IStringFormatManagerJaggedZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein
	private static final boolean INITIALIZED = true;// Trick, um Mehrfachinstanzen zu verhindern (optional)
	
	
	// --- Globale Objekte ---
	//Der LogString-Index - also von den reinen String ( nicht ggfs. hinzugefuegte XML Strings wie von ReflectCodeZZZ.getPositionCurrent() )
	//Hier als Array aller schon benutzter "einfacher" Strings. Das wird gemacht, damit die XML Strings auch weiterhin bei jeder Operation beruecksichtigt werden können.
	protected volatile ArrayListUniqueZZZ<Integer>listaintStringIndexRead=null;
	
	

	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}

	
	
	//##########################################################	
	//### GETTER / SETTER

	
	//##########################################################################
	//### aus IStringFormatComputerZZZ
	@Override 
	public boolean resetStringIndexRead() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(this.getStringIndexReadList().size()>=1) {
				bReturn = true;
			}
			this.getStringIndexReadList().clear();	
		}//end main:
		return bReturn;
	}
	
	@Override 
	public ArrayListUniqueZZZ<Integer> getStringIndexReadList() throws ExceptionZZZ{
		if(this.listaintStringIndexRead==null) {
			this.listaintStringIndexRead = new ArrayListUniqueZZZ<Integer>();
		}
		return this.listaintStringIndexRead;
	}
	
	@Override
	public void setStringIndexRead(ArrayListUniqueZZZ<Integer> listaintStringIndexRead) throws ExceptionZZZ{
		this.listaintStringIndexRead = listaintStringIndexRead;
	}
	
	@Override
	public boolean reset() throws ExceptionZZZ {
	//	boolean bReturn = this.resetStringIndexRead();
		return false;
	}
	
	
	//#################################################################
	//### COMPUTE
	//#################################################################
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.compute(null, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, sLogs);
	}

	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, sLogs);
	}
	
	//######################
	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged(ienumFormatLogString);

			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged(obj, ienumFormatLogString);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged(ienumaFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumaFormatLogString);			
		}//end main:
		return sReturn;	
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
						
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumaFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			//TODOGOON20260210;//zunächst einmal muss der objFormater die Formatanweisungen "normieren".
            //d.h. seine bisherigen Formatanweisungen mit den neuen Formatanweisungen vergleichen
			sReturn = objFormater.computeJagged(classObj, ienumaFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumaFormatLogString);			
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			//... die konkrete Formatanweisung wurde schon aus einem Array der "normierten" Formatanweisungen geholt.
			sReturn = objFormater.computeJagged(classObj, ienumFormatLogString);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged(hm);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, hm);			
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged(obj, hm);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, hm);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged(classObj, hm);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, hm);		
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged(obj, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn);
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged(classObj, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn);			
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
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(obj, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public String computeJagged(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(hm);
	}

	@Override
	public String computeJagged(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(obj, hm);
	}

	@Override
	public String computeJagged(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(classObj, hmLog);
	}

	@Override
	public String computeJagged(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public String computeJagged(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(obj, ienumFormatLogString);
	}
	
	@Override
	public String computeJagged(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
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
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJaggedArrayList(sLogs);
	}

	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(obj, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(obj, hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(classObj, hmLog);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(ienumFormatLogString);
	}
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(obj, ienumFormatLogString);
	}
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(classObj, ienumFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {	
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
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
