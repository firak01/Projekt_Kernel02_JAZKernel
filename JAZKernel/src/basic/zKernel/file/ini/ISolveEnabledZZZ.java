package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;

public interface ISolveEnabledZZZ{
	boolean isSolverEnabledGeneral() throws ExceptionZZZ;////prueft intern ab, ob das Flag Solver zu nutzen gesetzt ist.
	boolean isSolverEnabledThis() throws ExceptionZZZ; //prueft intern ab, ob das Flag fuer den konkreten Solver gesetzt ist.
	boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ; //darin werden nur die "Haupttags" berücksichtigt hinsichtlich des getFlag(... USE ...), also CALL aber nicht JAVA_CALL 
	boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ; //darin können auch die "KindTags" beruecksichtigt werden, also auch deren getFlag(... USE ...) Werte.
		
	boolean isSolverEnabledEveryRelevant() throws ExceptionZZZ; //prueft intern ab ab solverEnabledGeneral und solverEnabledEveryRelevantThis auf true sind.
	boolean isSolverEnabledAnyRelevant() throws ExceptionZZZ; //prueft intern ab ab solverEnabledGeneral oder solverEnabledThis auf true sind.	
}
