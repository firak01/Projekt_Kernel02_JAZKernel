package tryout.zBasic.util.console.multithread2;

public class DebugConsoleZZZ {

	public static void main(String[] args) {
		IConsoleUserZZZ objConsoleUser = new DummyConsoleUserZZZ();
				
		ConsoleZZZ objConsole = ConsoleZZZ.getInstance();				
		objConsole.setConsoleUserObject(objConsoleUser);
		objConsole.start();
		
	}

}
