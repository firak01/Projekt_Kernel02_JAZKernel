package debug.zBasic.util.crypt.thread;

import basic.zBasic.util.console.multithread.ConsoleZZZ;
import basic.zBasic.util.console.multithread.IConsoleUserZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;

public class DebugCryptConsoleZZZ {

	public static void main(String[] args) {
		IConsoleUserZZZ objConsoleUser = new DummyConsoleUserCryptZZZ();
		
		//Merke: Ziel ist es, das was in DebugRot13ZZZ (oder ähnlichen) gemacht wird in einer Endlosschleife durchzuführen.
		TODOGOON20221018; //Eigentlich ist der ConsoleUSer und die Eingabe so eng miteinander verknüpft, dass man hier den KeyPassCryptThreadZZZ
		                  //rein übergeben müsste, der das Verhalten des ...UserCrypt... steuert.
		
		IConsoleZZZ objConsole = ConsoleZZZ.getInstance();				
		objConsole.setConsoleUserObject(objConsoleUser);
		objConsole.start();
		
	}

}
