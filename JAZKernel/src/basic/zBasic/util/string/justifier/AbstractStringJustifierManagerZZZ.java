package basic.zBasic.util.string.justifier;

import java.util.LinkedHashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class AbstractStringJustifierManagerZZZ extends AbstractObjectWithFlagZZZ implements IStringJustifierManagerZZZ{
	private static final long serialVersionUID = 7094594085787231868L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	private static final boolean INITIALIZED = true;// Trick, um Mehrfachinstanzen zu verhindern (optional)
	
	
	// --- Globale Objekte ---
	//Zum Buendig machen
	//Idee: Bei mehreren Instanzen per Default auf das Singleton zugreifen
	//      Aber ggfs. ueberschreibend auf einen hinterlegten zugreifen.
	//Merke: XML-Strings werden nicht buendig gemacht. 
	//       Darum wird der StringJustifier nicht mehr in dieser Elternklasse im Konstruktor an den "Formater" uebergeben.
	private volatile ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = null;
	
	//Die liste der verwendeten Justifier, nach der Filterung
	private volatile ArrayListUniqueZZZ<IStringJustifierZZZ> listaStringJustifierUsed = null;

	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractStringJustifierManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	//############################################################################
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
			listaReturn = StringJustifierManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumFormatLogString);
		}//end main:
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringJustifierManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumaFormatLogString);
		}//end main:
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringJustifierManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, hm);
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

	//###################################################
	//### FLAG: ILogStringFormatManagerZZZ
	//###################################################
	@Override
	public boolean getFlag(IStringJustifierManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IStringJustifierManagerZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IStringJustifierManagerZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStringJustifierManagerZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IStringJustifierManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IStringJustifierManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
