package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;

public interface IConsoleUserZZZ extends IThreadUserZZZ{
	public IConsoleZZZ getConsole();
	public void setConsole(IConsoleZZZ objConsole);	
}
