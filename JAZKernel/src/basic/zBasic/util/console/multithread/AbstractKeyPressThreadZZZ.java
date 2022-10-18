package basic.zBasic.util.console.multithread;

import java.util.Scanner;


	 
	/** Der KeypressThread bestimmt die Eingabemöglichkeiten
	 *  und was damit getan werden soll.
	 *  Darum gibt es zu Demonstrationszwecken den KeyPressThreadDefaultZZZ
	 *  
	 * 
	 * @author Fritz Lindhauer, 18.10.2022, 09:15:40
	 * 
	 */
	public abstract class AbstractKeyPressThreadZZZ implements Runnable,IKeyPressThreadZZZ {
		private long lSleepTime;
		private boolean bStop = false;
		
		@Override
		 public long getSleepTime() {
	     	return this.lSleepTime;
	     }
		
		 @Override
		 public void setSleepTime(long lSleepTime) {
			 this.lSleepTime = lSleepTime;
		 }
		
        protected Scanner inputReader = new Scanner(System.in);

        //Method that gets called when the object is instantiated
        public AbstractKeyPressThreadZZZ(long lSleepTime) {
        	setSleepTime(lSleepTime);
        }
       

        public void run() 
        {
        	this.start();
        }
        
        @Override
        public boolean isStopped() {
    		return this.bStop;
    	}
        
        @Override
    	public void isStopped(boolean bStop) {
    		this.bStop = bStop;
    	}
        
        @Override
    	public void requestStop() {
    		this.isStopped(true);
    	}
    	
    	@Override
    	public abstract boolean start(); 
    }

