package basic.zBasic.util.console.multithread;

import java.util.Scanner;

	public class ConsoleThreadZZZ implements Runnable,IConsoleThreadZZZ {
		private IConsoleUserZZZ objConsoleUser = null;
		private IKeyPressThreadZZZ objKeyPressThread = null;
		private long lSleepTime = 1000;
		private boolean bStop = false;
		
		 public long getSleepTime() {		
	     	return this.lSleepTime;
	     }
		 public void setSleepTime(long lSleepTime) {
			 if(lSleepTime<0){
				 lSleepTime=0;
			 }
			 this.lSleepTime = lSleepTime;
		 }
		
        Scanner inputReader = new Scanner(System.in);

        //Method that gets called when the object is instantiated
        public ConsoleThreadZZZ(long lSleepTime, IKeyPressThreadZZZ objKeyPressThread) {
        	this.setSleepTime(lSleepTime);
        	this.setKeyPressThread(objKeyPressThread);
        }
     
		public void run() 
        {
        	this.start();
        }
        
        public boolean isStopped() {
    		return this.bStop;
    	}
    	public void isStopped(boolean bStop) {
    		this.bStop = bStop;
    	}
    	public void requestStop() {
    		this.isStopped(true);
    	}
		@Override
		public IConsoleUserZZZ getConsoleUserObject() {
			return this.objConsoleUser;
		}
		@Override
		public void setConsoleUserObject(IConsoleUserZZZ objConsoleUser) {
			this.objConsoleUser = objConsoleUser;
		}
		@Override
		public IKeyPressThreadZZZ getKeyPressThread() {
			return this.objKeyPressThread;
		}		
		 private void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread) {
				this.objKeyPressThread = objKeyPressThread;
			}
		 
		@Override
		public boolean start() {
			boolean bReturn=false;
			main:{				
				try {    
		           while(!this.isStopped()) {
		                long lSleepTime = this.getKeyPressThread().getSleepTime();
		                this.setSleepTime(lSleepTime);
		                lSleepTime = this.getSleepTime();
		                System.out.println("ConsoleThread.sleep: " + lSleepTime);
		                Thread.sleep(lSleepTime);
		                
		                IConsoleUserZZZ objUser = this.getConsoleUserObject();
		                if(objUser!=null) {
		                	boolean bStop = this.getKeyPressThread().isStopped(); 
			                if(bStop) {
			                	objUser.requestStop();
			                	this.requestStop();
			                }else {
			                	 objUser.start();                     
			                }
		                }else {
		                	this.requestStop();
		                }
	                }
	               
		           bReturn = true;
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
			}//end main
			return bReturn;
		}				
    }

