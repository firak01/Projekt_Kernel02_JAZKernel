package debug.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.util.console.multithread.AbstractConsoleUserZZZ;
import basic.zBasic.util.console.multithread.IConsoleUserZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;

public class DummyConsoleUserCryptZZZ extends AbstractConsoleUserZZZ {
	public DummyConsoleUserCryptZZZ() throws ExceptionZZZ {
		super();
	}
	
	public DummyConsoleUserCryptZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}

	private int iCounter = 0;
	
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.isStopped()) break main;

			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
			//       Darum muss man alles in dem KeyPressThread erledigen
			//Warten auf die fertige Eingabe.
			if(!this.getConsole().isInputFinished()) break main;
		
			this.iCounter++;
			System.out.println("Zähler crypt: " + iCounter);
			
			//TODOGOON: Nun die eingegebenen Variablen über eine HashMap aus der Console für die Steuereung der Verschlüsselung nutzen. 

			
			
			
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
