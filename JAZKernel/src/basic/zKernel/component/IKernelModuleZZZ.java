package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public interface IKernelModuleZZZ extends IFlagUserZZZ {
	public enum FLAGZ{
		ISKERNELMODULE;
	}	
	public abstract String getModuleName() throws ExceptionZZZ;    //Z.B. der Name des uebergeordneten JFrames. Ist der Package und Klassenname
}
