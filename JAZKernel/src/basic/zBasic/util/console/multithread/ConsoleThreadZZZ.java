package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;

	public class ConsoleThreadZZZ implements Runnable,IConsoleThreadZZZ, IConsoleUserZZZ {
		private IConsoleZZZ objConsole = null;		
				
        Scanner inputReader = new Scanner(System.in);

        //Method that gets called when the object is instantiated
        public ConsoleThreadZZZ(IConsoleZZZ objConsole) {
        	this.setConsole(objConsole);
        }
     
		public void run() 
        {
        	try {
        		HashMapExtendedZZZ<String,Object> hmVariable = this.getConsole().getVariableHashMap();
				this.start(hmVariable);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		public boolean start() throws ExceptionZZZ {
			return this.start(null);
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
		public boolean start(HashMapExtendedZZZ<String, Object> hmVariable) throws ExceptionZZZ {
			boolean bReturn=false;
			main:{		
				boolean bResult = false;
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
			                	if(hmVariable==null) {
			                		bResult = this.getConsole().getConsoleUserObject().start();
			                	}else {
			                	    bResult = this.getConsole().getConsoleUserObject().start(hmVariable);  			                		
			                	}
			                	if(bResult) {
			                		this.getConsole().isConsoleUserThreadFinished(true);
			                	}
			                }
		                }else {
		                	this.requestStop();
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

