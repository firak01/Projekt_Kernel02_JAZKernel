package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;

public class DummyConsoleUserZZZ extends AbstractConsoleUserZZZ {
	public DummyConsoleUserZZZ() throws ExceptionZZZ {
		super();
	}
	public DummyConsoleUserZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}

	private int iCounter = 0;

	@Override
	public boolean start() {
		boolean bReturn = false;
		main:{
			if(this.isStopped()) break main;
			
			this.iCounter++;
			System.out.println("Zähler: " + iCounter);
			 try {				 
				 Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("KeyPressThread: Wait Error");
				e.printStackTrace();
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public int getcounter() {
		return this.iCounter;
	}
}
