package basic.zBasic.util.console.multithread;

import java.util.Scanner;

public interface IKeyPressThreadZZZ extends IThreadUserZZZ{
	public long getSleepTime();
	public void setSleepTime(long lSleepTime);
	
	public boolean isInputFinished();
	public void isInputFinished(boolean bFinished);
		
	public Scanner getInputReader();
	public void setInputReader(Scanner objScanner);
}
