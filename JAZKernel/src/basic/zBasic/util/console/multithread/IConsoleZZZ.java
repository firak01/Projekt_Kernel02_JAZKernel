package basic.zBasic.util.console.multithread;

public interface IConsoleZZZ extends IThreadUserZZZ {
	public long getSleepTime();
	public void setSleepTime(long lSleepTime);
	
	public IConsoleThreadZZZ getConsoleThread();

	public IConsoleUserZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleUserZZZ objConsoleUser) ;	
	
	public IKeyPressThreadZZZ getKeyPressThread();
	public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread);
}
