package basic.zBasic.util.console.multithread;

public interface IConsoleUserZZZ extends IThreadEnabledZZZ{
	public IConsoleZZZ getConsole();
	public void setConsole(IConsoleZZZ objConsole);
	
	boolean isInputAllFinished();
	void isInputAllFinished(boolean bInputFinished);
	boolean isOutputAllFinished();
	void isOutputAllFinished(boolean bOutputFinished);	
}
