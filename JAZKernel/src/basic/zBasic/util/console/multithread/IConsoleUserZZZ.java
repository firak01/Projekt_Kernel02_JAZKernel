package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;

public interface IConsoleUserZZZ extends IThreadUserZZZ{
	public IConsoleZZZ getConsole();
	public void setConsole(IConsoleZZZ objConsole);
	
	//Mehr als nur Start
	public boolean start(HashMapExtendedZZZ<String, Object> hmVariable) throws ExceptionZZZ;
}
