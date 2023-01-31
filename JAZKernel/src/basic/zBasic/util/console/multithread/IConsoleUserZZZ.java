package basic.zBasic.util.console.multithread;

public interface IConsoleUserZZZ extends IThreadUserZZZ{
	public IConsoleZZZ getConsole();
	public void setConsole(IConsoleZZZ objConsole);
	
	boolean isInputAllFinished();
	void isInputAllFinished(boolean bInputFinished);
	boolean isOutputAllFinished();
	void isOutputAllFinished(boolean bOutputFinished);	
}
