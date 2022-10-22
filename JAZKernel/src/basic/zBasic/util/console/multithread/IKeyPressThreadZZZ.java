package basic.zBasic.util.console.multithread;

public interface IKeyPressThreadZZZ extends IThreadUserZZZ{
	public long getSleepTime();
	public void setSleepTime(long lSleepTime);
	
	public boolean isInputFinished();
	public void isInputFinished(boolean bFinished);
}
