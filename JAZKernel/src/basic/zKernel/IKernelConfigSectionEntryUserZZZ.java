package basic.zKernel;

import basic.zBasic.ExceptionZZZ;

public interface IKernelConfigSectionEntryUserZZZ {
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getEntryNew() throws ExceptionZZZ; //wichtig für Objekte, die mehrmals aufgerufen werden. Z.B KernelFileIni. Beim 2ten Suchlauf duerfen keine alten Werte drin sein.
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public boolean adoptEntryValuesMissing(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ; //Damit holt man die Werte aus einem Reference-Entry in das eigene interne Entry.
	public boolean resetEntry() throws ExceptionZZZ;
}
