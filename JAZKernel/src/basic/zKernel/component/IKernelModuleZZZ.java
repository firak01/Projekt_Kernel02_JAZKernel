package basic.zKernel.component;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZZZ;

public interface IKernelModuleZZZ extends IFlagZZZ {
	public enum FLAGZ{
		ISKERNELMODULE;
	}	
	public abstract String getModuleName() throws ExceptionZZZ;    //Z.B. der Name des uebergeordneten JFrames. Ist der Package und Klassenname
}
