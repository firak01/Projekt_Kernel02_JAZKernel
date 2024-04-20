package basic.zKernel.flag.event;

import basic.zBasic.ExceptionZZZ;

/** Interface muss von den Objekten implementiert werden, die den Event-Broker verwenden wollen, um einen Event abzufeuern.
 *  Der Event-Broker verwaltet, dann die Objekte die auf den abgefeuerten Event hoeren.
 *  
 *  //ALSO: Die 2. Zeile ist nicht mehr notwendig, wenn das Registrieren (hier des FileIniZZZ Objects am Flag-Event klappt:
 *  //objKernelFGL.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION, false);
	//objKernelFGL.getFileConfigKernelIni().setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION, false);	
 *  
 *  
 *  Merke1: Die Objekte, die lediglich auf den Event "hoeren" brauchen dieses Interface nicht !!!
 *  Merke2: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; ) 
 * @author lindhaueradmin
 *
 */
public interface ISenderObjectFlagZsetUserZZZ {
	public abstract ISenderObjectFlagZsetZZZ getSenderFlagUsed() throws ExceptionZZZ;
	public abstract void setSenderFlagUsed(ISenderObjectFlagZsetZZZ objEventSender);
}

