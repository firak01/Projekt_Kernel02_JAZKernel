package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;

	public class ConsoleThreadZZZ implements Runnable,IConsoleThreadZZZ, IConsoleUserZZZ {
		private IConsoleZZZ objConsole = null;		
		private boolean bStop = false;
				
        Scanner inputReader = new Scanner(System.in);

        //Method that gets called when the object is instantiated
        public ConsoleThreadZZZ(IConsoleZZZ objConsole) {
        	this.setConsole(objConsole);
        }
     
		public void run() 
        {
        	try {
				this.start();
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		public boolean start() throws ExceptionZZZ {
			boolean bReturn=false;
			main:{				
				try {    
		           while(!this.isStopped()) {
		                long lSleepTime = this.getConsole().getSleepTime();
		                //System.out.println("ConsoleThread.sleep: " + lSleepTime);
		                Thread.sleep(lSleepTime);
		                
		                IConsoleUserZZZ objUser = this.getConsole().getConsoleUserObject();
		                if(objUser!=null) {
		                	boolean bStop = this.getConsole().isStopped(); 
			                if(bStop) {
			                	this.getConsole().getConsoleUserObject().requestStop();
			                	this.requestStop();
			                }else {
			                	this.getConsole().getConsoleUserObject().start();  
			                	this.getConsole().isConsoleUserThreadFinished(true);
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
		@Override
		public IConsoleZZZ getConsole() {
			return this.objConsole;
		}
		@Override
		public void setConsole(IConsoleZZZ objConsole) {
			this.objConsole = objConsole;
		}				
    }

