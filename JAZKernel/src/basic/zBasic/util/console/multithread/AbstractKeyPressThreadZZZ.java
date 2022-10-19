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
	public abstract class AbstractKeyPressThreadZZZ implements Runnable,IConsoleUserZZZ,IKeyPressThreadZZZ {
		private long lSleepTime=1000;//default
		private boolean bStop = false;
		IConsoleZZZ objConsole = null; //Darüber werden die Variablen und auch die Eingaben ausgetauscht
		
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
        public AbstractKeyPressThreadZZZ(IConsoleZZZ objConsole) {
        	this.setConsole(objConsole);
        }
        public AbstractKeyPressThreadZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	this.setConsole(objConsole);
        	this.setSleepTime(lSleepTime);
        }
       
        @Override
		public IConsoleZZZ getConsole() {
			return this.objConsole;
		}
		@Override
		public void setConsole(IConsoleZZZ objConsole) {
			this.objConsole = objConsole;
		}
		
		@Override
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

