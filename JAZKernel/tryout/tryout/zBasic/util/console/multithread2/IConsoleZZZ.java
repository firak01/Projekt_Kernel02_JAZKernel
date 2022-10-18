package tryout.zBasic.util.console.multithread2;

public interface IConsoleZZZ {
	public IConsoleUserZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleUserZZZ objConsoleUser) ;	
	
	public KeyPressThreadZZZ getKeyPressThread();
	public ConsoleThreadZZZ getConsoleThread();
}
