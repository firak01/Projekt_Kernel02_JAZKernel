package basic.zBasic;

import java.util.Vector;

import basic.zBasic.util.abstractList.VectorExtendedDifferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;

public abstract class AbstractObjectWithFormulaZZZ extends AbstractObjectWithFlagZZZ implements IObjectWithFormulaZZZ{
	protected IKernelConfigSectionEntryZZZ objEntry = null; //Vereinfachung, ich speichere alles hier ab, hier werden auch die Statusergebnisse der Formelaufloesungsschritte verwaltet.
	
	
	//### aus IKernelConfigSectionEntryZZZ 
	public IKernelConfigSectionEntryZZZ getEntry() {
		if(this.objEntry==null) {
			this.objEntry = new KernelConfigSectionEntryZZZ();			
		}
		return this.objEntry;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) {
		this.objEntry = objEntry;
	}
	
	//### aus IValueSolvedUserZZZ

	@Override
	public VectorExtendedDifferenceZZZ<String> getValueVector() {
		return this.getEntry().getValueVector();
	}

	@Override
	public String getValue() {
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) {
		this.getEntry().setValue(sValue);
	}

	@Override
	public VectorExtendedDifferenceZZZ<String> getRawVector() {
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() {
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) {
		this.getEntry().setValue(sRaw);
	}
	//##############################
	
}
