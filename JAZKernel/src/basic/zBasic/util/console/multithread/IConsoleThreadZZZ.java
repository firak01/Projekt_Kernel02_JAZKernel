package basic.zBasic.util.console.multithread;

public interface IConsoleThreadZZZ extends IKeyPressThreadZZZ{
	public IConsoleUserZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleUserZZZ objConsoleUser) ;	
	
	public IKeyPressThreadZZZ getKeyPressThread();
}
