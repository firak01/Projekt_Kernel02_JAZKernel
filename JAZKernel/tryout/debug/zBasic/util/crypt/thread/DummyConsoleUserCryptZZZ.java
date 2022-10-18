package debug.zBasic.util.crypt.thread;

import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.console.multithread.AbstractConsoleUserZZZ;
import basic.zBasic.util.console.multithread.IConsoleUserZZZ;

public class DummyConsoleUserCryptZZZ extends AbstractConsoleUserZZZ {
	private int iCounter = 0;
	
	public boolean start() {
		boolean bReturn = false;
		main:{
			if(this.isStopped()) break main;
			
			this.iCounter++;
			System.out.println("Zähler crypt: " + iCounter);
			 try {				 
				 Thread.sleep(900);
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
