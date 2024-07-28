package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.AbstractObjectWithExpressionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagexpression.AbstractTagWithExpressionBasicZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

//DIES IST DER FLAG - WEG
//ALSO: ALLES WAS IN IniTagBasicZZZ und dessen Elternklasse implementiert ist hierein kopieren
public abstract class AbstractIniTagWithExpressionBasicZZZ<T> extends AbstractTagWithExpressionBasicZZZ<T> implements IIniTagWithExpressionZZZ{
	private static final long serialVersionUID = 4049221887081114236L;
	
	//Fuer Interface IKernelConfigSectionEntryUserZZZ
	protected volatile IKernelConfigSectionEntryZZZ objEntry = null; //Vereinfachung, ich speichere alles hier ab, hier werden auch die Statusergebnisse der Formelaufloesungsschritte verwaltet.	
	
	public AbstractIniTagWithExpressionBasicZZZ() throws ExceptionZZZ{
		super();
		AbstractIniTagWithExprssionBasicNew_();
	}
	
	public AbstractIniTagWithExpressionBasicZZZ(String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);
		AbstractIniTagWithExprssionBasicNew_();
	}
	
	public AbstractIniTagWithExpressionBasicZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
		AbstractIniTagWithExprssionBasicNew_();
	}
	
	private boolean AbstractIniTagWithExprssionBasicNew_() throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{			 
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
				
				
		 	}//end main:
			return bReturn;
		 }//end function AbstractObjectWithFormulaNew_

	
	//######## Getter / Setter #################
	
	//### aus IKernelConfigSectionEntryUserZZZ 	
	@Override
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ{
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ<T>();			
		}
		return this.objEntry;
	}
	
	@Override
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
	

	// Die Methoden aus AbstractObjectWithExpressionZZZ muessen nur auf den Entry umgebogen werden.
	//### Aus IValueBufferedUserZZZ
	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() throws ExceptionZZZ {
		return this.getEntry().getValueVector();
	}
	
	@Override
	public String getValue() throws ExceptionZZZ {
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) throws ExceptionZZZ {
		this.getEntry().setValue(sValue);
	}
	
	@Override
	public boolean hasAnyValue() throws ExceptionZZZ {
		return this.getEntry().hasAnyValue();
	}	
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasAnyValue(boolean bAnyValue) throws ExceptionZZZ {
		this.getEntry().hasAnyValue(bAnyValue);
	}
	
	@Override
	public boolean hasNullValue() {
		return this.bNullValue;
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar, 
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasNullValue(boolean bNullValue) {
		this.bNullValue=bNullValue;
	}
	
	//####################################################
    //### aus IValueComputedBufferedUserZZZ
	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() throws ExceptionZZZ {
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() throws ExceptionZZZ {
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) throws ExceptionZZZ {
		this.getEntry().setValue(sRaw);
	}
	
	
	
	
	//##############################
	//### aus IValueArrayUserZZZ
	//Merke: Arrays erst in ini-Tag behandeln, da es dafuer Separatorn in der Zeile geben muss
	@Override	
	public ArrayList<String> getValueArrayList() throws ExceptionZZZ{
		return this.getEntry().getValueArrayList();
	}

	@Override
	public void setValue(ArrayList<String> listasValue) throws ExceptionZZZ{
		this.getEntry().setValue(listasValue);
	}
	
	@Override
	public boolean isArrayValue() throws ExceptionZZZ {
		return this.getEntry().isArrayValue();
	}

	@Override
	public void isArrayValue(boolean bIsArrayValue) throws ExceptionZZZ {
		this.getEntry().isArrayValue(bIsArrayValue);
	}
	
	//### Merke: IValueSolvedUserZZZ hier eingebunden, da es eine Expression ist
	//           Die MEthoden aus AbstractObjectWithExpressionZZZ muessen nur auf den Entry umgebogen werden.
	//### aus IValueSolvedUserZZZ


	
}
