package use.zBasic.util.console;

import java.util.Scanner;


	 
	public class KeyPressThread implements Runnable {
		private long rand;
		 public long getRand() {
	     	return this.rand;
	     }
		 public void setRand(long rand) {
			 this.rand = rand;
		 }
		
        Scanner inputReader = new Scanner(System.in);

        //Method that gets called when the object is instantiated
        public KeyPressThread(long rand2) {
        	setRand(rand2);
        }
       

        public void run() 
        {
            while(true) 
            {
                if (inputReader.hasNext())
                {
                    String input = inputReader.next();
                    if (input.equals("["))
                    {
                        rand+=100;
                        System.out.println("Pressed [");
                    }
                    if (input.equals("]"))
                    {
                       rand-=100;
                       System.out.println("Pressed ]");
                    }
                    if (input.equalsIgnoreCase("Q"))
                    {
                        break; // stop KeyPressThread
                    }
                }
            }
        }

    }

