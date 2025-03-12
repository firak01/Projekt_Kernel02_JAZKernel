package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface ISolveUserZZZ extends IKernelSolveEnabledZZZ{		
	public boolean isSolve(String sExpression) throws ExceptionZZZ;
		
	public void updateValueSolveCalled() throws ExceptionZZZ;
	public void updateValueSolveCalled(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSolveCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void updateValueSolveCalled(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueSolved() throws ExceptionZZZ;
	public void updateValueSolved(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSolved(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void updateValueSolved(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	public void updateValueSolvedChanged() throws ExceptionZZZ;
	public void updateValueSolvedChanged(boolean bIsSolveCalled) throws ExceptionZZZ;
	public void updateValueSolvedChanged(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ;
	public void updateValueSolvedChanged(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolveCalled) throws ExceptionZZZ;
	
	//Merke: Auf dieser Ebene der Vererbung gibt es keine Kernel Flags, also geht nicht
//	public enum FLAGZ{
//		USEEXPRESSION
//	}
//	
//  ..... getFlag(...), .....	
	
}
