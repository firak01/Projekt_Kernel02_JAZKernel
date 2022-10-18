package basic.zBasic.util.console.multithread;

import basic.zBasic.ObjectZZZ;

public class DummyConsoleUserZZZ extends AbstractConsoleUserZZZ {
	private int iCounter = 0;

	@Override
	public boolean start() {
		boolean bReturn = false;
		main:{
			if(this.isStopped()) break main;
			
			this.iCounter++;
			System.out.println("Zähler: " + iCounter);
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
