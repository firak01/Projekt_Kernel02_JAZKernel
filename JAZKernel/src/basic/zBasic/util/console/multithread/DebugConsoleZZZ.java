package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;

public class DebugConsoleZZZ {

	public static void main(String[] args) {
		try {						
			IConsoleZZZ objConsole = ConsoleZZZ.getInstance();	
			IConsoleUserZZZ objConsoleUser = new DummyConsoleUserZZZ(objConsole);
			
			//TODOGOON20221019; //Den "individuellen" KeyPathThread hier definieren und übergeben
			//objConsole.setKeyPressThread(objKeyPressThread);//ohne den speziellen KeyPassThread wird ein Default genommen.
			objConsole.setConsoleUserObject(objConsoleUser);
			objConsole.start();
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
