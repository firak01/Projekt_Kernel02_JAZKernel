package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.crypt.thread.ConsoleUserEncryptZZZ;

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
        
		@Override
		public boolean isInputAllFinished() {
			return this.getConsole().isInputAllFinished();
		}

		@Override
		public void isInputAllFinished(boolean bInputFinished) {
			this.getConsole().isInputAllFinished(bInputFinished);
		}

		@Override
		public boolean isOutputAllFinished() {
			return this.getConsole().isOutputAllFinished();
		}

		@Override
		public void isOutputAllFinished(boolean bOutputFinished) {
			this.getConsole().isOutputAllFinished(bOutputFinished);
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
		                if(this.getConsole().isInputAllFinished()){
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
					                		this.getConsole().isOutputAllFinished(false); //Bereit für neue Ausgabe...
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

