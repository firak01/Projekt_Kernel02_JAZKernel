package tryout.zBasic.util.console.multithread.extended;

public interface IConsoleZZZ {
	public IConsoleUserZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleUserZZZ objConsoleUser) ;	
	
	public KeyPressThreadZZZ getKeyPressThread();
	public ConsoleThreadZZZ getConsoleThread();
}
