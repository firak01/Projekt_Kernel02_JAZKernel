package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IKernelProgramZZZ  extends IFlagUserZZZ{ 
	public enum FLAGZ{
		ISKERNELPROGRAM; 
	}
	public abstract String getProgramName() throws ExceptionZZZ;   //Z.B. ein Button, Ist der Package und Klassenname		
	public abstract String getProgramAlias() throws ExceptionZZZ;  //Der Alias wird auf Modulebenen definiert. Package und Klassenname = Alias. Speicher den Alias in einer Variablen.
}
