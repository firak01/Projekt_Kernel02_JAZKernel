package tryout.zBasic.util.console.multithread.extended;

public class DebugScannerConsoleExtendedMainZZZ {

	public static void main(String[] args) {
		IConsoleUserZZZ objConsoleUser = new DummyConsoleUserZZZ();
				
		ConsoleZZZ objConsole = ConsoleZZZ.getInstance();				
		objConsole.setConsoleUserObject(objConsoleUser);
		objConsole.start();
		
	}

}
