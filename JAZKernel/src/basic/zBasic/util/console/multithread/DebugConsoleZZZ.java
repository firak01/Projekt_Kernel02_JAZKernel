package basic.zBasic.util.console.multithread;

public class DebugConsoleZZZ {

	public static void main(String[] args) {
		IConsoleUserZZZ objConsoleUser = new DummyConsoleUserZZZ();
				
		IConsoleZZZ objConsole = ConsoleZZZ.getInstance();				
		objConsole.setConsoleUserObject(objConsoleUser);
		objConsole.start();
		
	}

}
