package basic.zBasic.util.console.multithread;

import basic.zBasic.util.abstractList.HashMapExtendedZZZ;

public interface IConsoleZZZ extends IThreadUserZZZ {
	public long getSleepTime();
	public void setSleepTime(long lSleepTime);
	
	public IConsoleThreadZZZ getConsoleThread();

	public IConsoleUserZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleUserZZZ objConsoleUser) ;	
	
	public boolean isInputAllFinished();
	public void isInputAllFinished(boolean bInputFinished);
	
	public boolean isOutputAllFinished();
	public void isOutputAllFinished(boolean bOutputFinished);
	
	public IKeyPressThreadZZZ getKeyPressThread();
	public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread);
	
	public boolean isKeyPressThreadFinished();
	public void isKeyPressThreadFinished(boolean bInputThreadFinished); //setzen, wenn die Eingabe im KeyPressThread vorerst abgeschlossen ist.
	
	public boolean isKeyPressThreadRunning();
	public void isKeyPressThreadRunning(boolean bInputThreadRunning); //setzen, wenn die Eingabe im KeyPressThread vorerst abgeschlossen ist.	
	
	public boolean isConsoleUserThreadRunning();
	public void isConsoleUserThreadRunning(boolean bConsoleUserThreadRunning); //setzen, wenn der gestartete ConsolenUserThread beendet wurde. Dann kann eine neue Eingabe gestartet werden.
		
	public boolean isConsoleUserThreadFinished();
	public void isConsoleUserThreadFinished(boolean bConsoleUserThreadFinished); //setzen, wenn der gestartete ConsolenUserThread beendet wurde. Dann kann eine neue Eingabe gestartet werden.
	
	public HashMapExtendedZZZ<String, Object> getVariableHashMap();
	public void setVariableHashMap(HashMapExtendedZZZ<String, Object> hmVariable);
}
