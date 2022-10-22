package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;


	 
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
		IConsoleZZZ objConsole = null; //Darüber werden die Variablen und auch die Eingaben ausgetauscht
		
		@Override
		 public long getSleepTime() {
	     	return this.lSleepTime;
	     }
		
		 @Override
		 public void setSleepTime(long lSleepTime) {
			 this.lSleepTime = lSleepTime;
		 }
		 
		 @Override
			public boolean isInputFinished() {
				return this.getConsole().isInputFinished();
			}
			@Override
			public void isInputFinished(boolean bFinished) {
				this.getConsole().isInputFinished(bFinished);
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
        	try {
        		HashMapExtendedZZZ<String,Object> hmVariable = this.getConsole().getVariableHashMap();
				this.start(hmVariable);
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}
        }
        
        @Override
        public boolean isStopped() {
    		return this.getConsole().isStopped();
    	}
        
        @Override
    	public void isStopped(boolean bStop) {
    		this.getConsole().isStopped(bStop);
    	}
        
        @Override
    	public void requestStop() {
    		this.isStopped(true);
    	}
    	    	
    	@Override
    	public abstract boolean start() throws ExceptionZZZ;
    }

