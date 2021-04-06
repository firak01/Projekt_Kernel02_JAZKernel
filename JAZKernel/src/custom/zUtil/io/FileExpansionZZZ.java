package custom.zUtil.io;

import basic.zBasic.ExceptionZZZ;
import basic.zUtil.io.KernelFileExpansionZZZ;

public class FileExpansionZZZ extends KernelFileExpansionZZZ{
	public FileExpansionZZZ() throws ExceptionZZZ {
		super();
	}
	public FileExpansionZZZ(char cFilling, int iExpansionLength) {
		super(cFilling, iExpansionLength);
	}
	public FileExpansionZZZ(FileZZZ objFileBase) {
		super(objFileBase);
	}
	public FileExpansionZZZ(FileZZZ objFileBase, int iExpansionLength) {
		super(objFileBase, iExpansionLength);
	}
	
}
