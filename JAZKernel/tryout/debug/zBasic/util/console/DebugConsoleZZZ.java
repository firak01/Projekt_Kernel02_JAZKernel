package debug.zBasic.util.console;

import basic.zBasic.util.console.ConsoleZZZ;
import basic.zBasic.util.console.DummyConsoleUserZZZ;
import basic.zBasic.util.console.IConsoleUserZZZ;
import basic.zBasic.util.console.IConsoleZZZ;

public class DebugConsoleZZZ {

	public static void main(String[] args) {
		IConsoleUserZZZ objConsoleUser = new DummyConsoleUserZZZ();
				
		ConsoleZZZ objConsole = ConsoleZZZ.getInstance();				
		objConsole.setConsoleUserObject(objConsoleUser);
		objConsole.start();
		
	}

}
