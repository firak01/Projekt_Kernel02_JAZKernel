package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;

public class DummyConsoleUserZZZ extends AbstractConsoleUserZZZ {
	public DummyConsoleUserZZZ() throws ExceptionZZZ {
		super();
	}
	public DummyConsoleUserZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}

	private int iCounter = 0;

	@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.start(null);
		}//end main:
		return bReturn;
	}
	
	public int getcounter() {
		return this.iCounter;
	}
	
	@Override
	public boolean start(HashMapExtendedZZZ<String, Object> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.isStopped()) break main;
			
			this.iCounter++;
			System.out.println("Zähler: " + iCounter);
			 try {				 
				Thread.sleep(100);
				bReturn = true;
			} catch (InterruptedException e) {
				System.out.println("KeyPressThread: Wait Error");
				e.printStackTrace();
			}
		}//end main:
		return bReturn;	
	}
}
