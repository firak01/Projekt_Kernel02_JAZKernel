package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.xml.tagsimple.ISubstituteEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface ISubstituteUserZZZ extends ISubstituteEnabledZZZ{//, IConvertEnabledZZZ{		
	public boolean isSubstitute(String sExpression) throws ExceptionZZZ;
		
	
	public void addHistorySubstituteCalled() throws ExceptionZZZ;
	public void addHistorySubstituteCalled(String sTagName) throws ExceptionZZZ;
	public void addHistorySubstituteCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void addHistorySubstituteCalled(IKernelConfigSectionEntryZZZ objEntry, String sTagName) throws ExceptionZZZ;
	
	
	public void updateValueSubstituteCalled() throws ExceptionZZZ;
	public void updateValueSubstituteCalled(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValuePathSubstituteCalled() throws ExceptionZZZ;
	public void updateValuePathSubstituteCalled(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValuePathSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValuePathSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueVariableSubstituteCalled() throws ExceptionZZZ;
	public void updateValueVariableSubstituteCalled(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueVariableSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueVariableSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	
	public void updateValueSubstituteCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, String sExpression) throws ExceptionZZZ;
	
	public void updateValueSubstituted() throws ExceptionZZZ;
	public void updateValueSubstituted(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValuePathSubstituted() throws ExceptionZZZ;
	public void updateValuePathSubstituted(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValuePathSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValuePathSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueVariableSubstituted() throws ExceptionZZZ;
	public void updateValueVariableSubstituted(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueVariableSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueVariableSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	
	public void updateValueSubstitutedChanged() throws ExceptionZZZ;
	public void updateValueSubstitutedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValuePathSubstitutedChanged() throws ExceptionZZZ;
	public void updateValuePathSubstitutedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValuePathSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValuePathSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueVariableSubstitutedChanged() throws ExceptionZZZ;
	public void updateValueVariableSubstitutedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueVariableSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueVariableSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	
	//Merke: Auf dieser Ebene der Vererbung gibt es keine Kernel Flags, also geht nicht
//	public enum FLAGZ{
//		USEEXPRESSION
//	}
//	
//  ..... getFlag(...), .....	
	
}
