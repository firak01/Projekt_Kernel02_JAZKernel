package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import debug.zBasic.util.crypt.thread.ConsoleUserCryptZZZ;

	public class ConsoleThreadZZZ implements Runnable,IConsoleThreadZZZ, IConsoleUserZZZ {
		private IConsoleZZZ objConsole = null;		

        //Method that gets called when the object is instantiated
        public ConsoleThreadZZZ(IConsoleZZZ objConsole) {
        	this.setConsole(objConsole);
        }
     
		public void run() 
        {
        	try {        		
				this.start();
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
				this.requestStop();
			}
        }
        
        public boolean isStopped() {
    		return this.getConsole().isStopped();
    	}
    	public void isStopped(boolean bStop) {
    		this.getConsole().isStopped(bStop);
    	}
    	public void requestStop() {
    		this.isStopped(true);
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
		public boolean start() throws ExceptionZZZ {
			boolean bReturn=false;
			main:{		
				try {    				
		           while(!this.isStopped()) {
		                long lSleepTime = this.getConsole().getSleepTime();
		                //System.out.println("ConsoleThread.sleep: " + lSleepTime);
		                Thread.sleep(lSleepTime);			                		               
		                if(this.getConsole().isInputAllFinished()&& this.getConsole().isOutputAllFinished()){
			                IConsoleUserZZZ objUser = this.getConsole().getConsoleUserObject();
			                if(objUser!=null) {
			                	boolean bStop = this.getConsole().isStopped(); 
				                if(bStop) {
				                	this.getConsole().getConsoleUserObject().requestStop();
				                	this.requestStop();
				                }else {				                			                		
			                		if(!this.getConsole().isConsoleUserThreadRunning()) { //den Thread nicht mehrmals starten
					                		boolean bResult = this.getConsole().getConsoleUserObject().start();					                		
					                		this.getConsole().isInputAllFinished(false); //Bereit für neue Eingabe...					                				                			
			                		}//end if isConsoleUserThreadRunning()
				                }			                				               
			                }else {
			                	this.requestStop();
			                }
		                }
	                }//end while	               
		            bReturn = true;
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
			}//end main
			return bReturn;
		}				
    }

