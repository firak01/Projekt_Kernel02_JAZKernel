package debug.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.multithread.ConsoleZZZ;
import basic.zBasic.util.console.multithread.IConsoleUserZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadCryptZZZ;

public class DebugCryptConsoleMainZZZ {

	public static void main(String[] args) {
		try {
			IConsoleZZZ objConsole = ConsoleZZZ.getInstance();
			
			//Merke: Ziel ist es, das was in DebugRot13ZZZ (oder ähnlichen) gemacht wird in einer Endlosschleife durchzuführen.
			//Der ConsoleUser und die Eingabe so eng miteinander verknüpft, dass man hier den KeyPressCryptThreadZZZ
			//übergeben wird, der das Verhalten des ...UserCrypt... steuert.
			KeyPressThreadCryptZZZ objKeyPressThread = new KeyPressThreadCryptZZZ(objConsole);
			objConsole.setKeyPressThread(objKeyPressThread);
						
			//Merke: Ziel ist, dass der ConsoleUser-Thread und der KeyPressThread "Daten" miteinander austauschen können. 
			IConsoleUserZZZ objConsoleUser = new ConsoleUserCryptZZZ(objConsole);
			objConsole.setConsoleUserObject(objConsoleUser);
			objConsole.start();
			
			//TODO 20230127: Weitere-Threads anbinden können, d.h. objConsole.setConsoleUserObejects(Array von IConsoleUser) 
			//               Aber: Zu bedenken ist, das wohl immer nur 1 Thread per Scanner - Klasse auf die Eingaben hören kann. 
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
