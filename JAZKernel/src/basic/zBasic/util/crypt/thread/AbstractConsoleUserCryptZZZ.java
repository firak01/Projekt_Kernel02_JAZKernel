package basic.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.multithread.AbstractConsoleUserZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;

public abstract class AbstractConsoleUserCryptZZZ extends AbstractConsoleUserZZZ{
	public AbstractConsoleUserCryptZZZ()  throws ExceptionZZZ {
		super();
	}
	public AbstractConsoleUserCryptZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);		
	}
	public AbstractConsoleUserCryptZZZ(IConsoleZZZ objConsole,String sFlag) throws ExceptionZZZ {
		super(objConsole, sFlag);		
	}
	public AbstractConsoleUserCryptZZZ(IConsoleZZZ objConsole,String[] saFlag) throws ExceptionZZZ {
		super(objConsole, saFlag);	
	}
}
