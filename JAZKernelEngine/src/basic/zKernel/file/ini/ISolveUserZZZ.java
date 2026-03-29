package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface ISolveUserZZZ extends IKernelSolveZZZ{		
	public boolean isSolve(String sExpression) throws ExceptionZZZ;
	public void updateValueSolveCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, String sExpression) throws ExceptionZZZ;	
	
	public void addHistorySolveCalled() throws ExceptionZZZ;
	public void addHistorySolveCalled(String sTagName) throws ExceptionZZZ;
	public void addHistorySolveCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void addHistorySolveCalled(IKernelConfigSectionEntryZZZ objEntry, String sTagName) throws ExceptionZZZ;
	
	public void updateValueSolveCalled() throws ExceptionZZZ;
	public void updateValueSolveCalled(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueSolved() throws ExceptionZZZ;
	public void updateValueSolved(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueSolvedChanged() throws ExceptionZZZ;
	public void updateValueSolvedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	//Merke: Auf dieser Ebene der Vererbung gibt es keine Kernel Flags, also geht nicht
//	public enum FLAGZ{
//		USEEXPRESSION
//	}
//	
//  ..... getFlag(...), .....	
	
}
