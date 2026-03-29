package basic.zBasic.util.console.multithread.extended;

public interface IConsoleZZZ {
	public IConsoleEnabledZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleEnabledZZZ objConsoleUser) ;	
	
	public KeyPressThreadZZZ getKeyPressThread();
	public ConsoleThreadZZZ getConsoleThread();
}
