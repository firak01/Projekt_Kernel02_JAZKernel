package basic.zBasic.util.console.multithread;

import java.util.Scanner;


	 
	public class KeyPressThreadDefaultZZZ extends AbstractKeyPressThreadZZZ {


        //Method that gets called when the object is instantiated
        public KeyPressThreadDefaultZZZ(long lSleepTime) {
        	super(lSleepTime);
        }
       
        public boolean start(){
        	boolean bReturn = false;
        	main:{
	        	System.out.println("Eingaben: [ ] oder Q");
	            while(true){
	            	long lSleepTime = this.getSleepTime();
		        	 try {
		             	System.out.println("Warte auf Eingabe...");                 	
						Thread.sleep(500);                 	
					} catch (InterruptedException e) {
						System.out.println("KeyPressThread: 1. Wait Error");
						e.printStackTrace();
					}
	
	                String input = inputReader.next();
	                System.out.println(input);
	                if (input.equals("[")) {
	                	lSleepTime+=100;
	                	this.setSleepTime(lSleepTime);
	                    System.out.println("Pressed [");
	                }
	                if (input.equals("]")) {
	                	lSleepTime-=100;
	                	this.setSleepTime(lSleepTime);
	                    System.out.println("Pressed ]");
	                }
	                if (input.equalsIgnoreCase("Q")) {
	                    this.requestStop();
	                	break; // stop KeyPressThread
	                }
	
	                try {
	                	System.out.println("Nach der Eingabe.");
	                	Thread.sleep(500);
	                	bReturn = true;
					} catch (InterruptedException e) {
						System.out.println("KeyPressThread: 2. Wait Error");
						e.printStackTrace();
					}
	            }//end while
	    	}//end main:
	    	return bReturn;
        }
    }

