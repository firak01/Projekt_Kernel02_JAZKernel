package basic.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.console.multithread.AbstractKeyPressThreadZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;
import basic.zBasic.util.crypt.CryptCipherAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.ROTnnZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


	 
	public class KeyPressThreadCryptZZZ extends AbstractKeyPressThreadZZZ implements IKeyPressThreadCryptConstantsZZZ{
		boolean bCurrentInput=false;
		boolean bMakeMenue=true;//true, damit die erste Anzeige generiert wird
		
        //Method that gets called when the object is instantiated
		public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole) {
        	super(objConsole);
        }
        public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	super(objConsole, lSleepTime);
        }
       
        public boolean start() throws ExceptionZZZ{
        	return this.start(null);
        }
        
        public boolean isCurrentInput() {
        	return this.bCurrentInput;
        }
        public void isCurrentInput(boolean bCurrentInput) {
        	this.bCurrentInput = bCurrentInput;
        }
        public boolean isCurrentMenue() {
        	return this.bMakeMenue;
        }
        public void isCurrentMenue(boolean bMakeMenue) {
        	this.bMakeMenue = bMakeMenue;
        }
		@Override
		public boolean start(HashMapExtendedZZZ<String, Object> hmVariable) throws ExceptionZZZ {
			boolean bReturn = false;
        	main:{
    			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
    			//       Darum muss man alle Eingaben in diesem KeyPressThread erledigen
	        	
				
	            while(!this.isStopped()){
	            	long lSleepTime = this.getSleepTime();
	            	input:{
		            	this.isInputFinished(false);		            		            			            	
		            	while(!this.getConsole().isInputFinished()) {
				        	 try {
				        		if(this.bMakeMenue) {
					        		System.out.println("Eingaben: + - zur Console-Threadgeschwindigkeit | Q zum Abbruch");
									System.out.println("Bitte wählen Sie den Algorithmus:");
									System.out.println("1: Rot13");
									System.out.println("2: RotNn");
					             	System.out.println("Warte auf Eingabe Crypt...");                 	
									Thread.sleep(lSleepTime);   
									this.isCurrentInput(false);
				        		}
							} catch (InterruptedException e) {
								System.out.println("KeyPressThread: 1. Wait Error");
								e.printStackTrace();
							}
			
			                //das holt wohl wort fuer wort von der Konsole: String sInput = inputReader.next();
				        	String sInput = inputReader.nextLine();
			                System.out.println("Pressed Crypt:" + sInput);
			                if(sInput==null) break main;
			                
			                //In the JDK 7 release, you can use a String object in the expression of a switch statement:
			                //Das keine lowercase Methode oder eine Fallunterscheidung in den CASE eingebaut werden kann, 
			                //vorher lowercase
			                String input = sInput.toLowerCase();
			                String sCipher=null;
			                switch(input) {
			                case "+":
			                	lSleepTime+=100;
			                	this.getConsole().setSleepTime(lSleepTime);
			                	this.isCurrentInput(true);
			                	break;
			                case "-":
			                	lSleepTime-=100;
			                	this.getConsole().setSleepTime(lSleepTime);
			                	this.isCurrentInput(true);
			                	break;
			                case "q":
			                    this.requestStop();
			                    this.getConsole().isInputFinished(true);
			                    this.isCurrentInput(true);
			                	break; // stop KeyPressThread über die gesetzte STOP Variable
			                case "1":
			                	
			                	if(hmVariable!=null) {
			                		sCipher = CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROT13.getAbbreviation();
			                		hmVariable.put(this.sINPUT_CIPHER, sCipher);
			                	}
			                	
			                	
			                	//ggfs. weitere Eingaben abfragen
			                	System.out.println("Geben Sie den zu verschlüsselnden Text als String ein");
			                	
			                	 //das holt wohl wort fuer wort von der Konsole: sInput = inputReader.next();
					        	sInput = inputReader.nextLine();
			                	
			                	if(hmVariable!=null) hmVariable.put(this.sINPUT_TEXT_UNCRYPTED, sInput);
			                	this.isCurrentInput(true);
			                	this.isInputFinished(true);		                	
			                	break;
			                case "2":
			                	
			                	if(hmVariable!=null) {
			                		sCipher = CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROTnn.getAbbreviation();
			                		hmVariable.put(this.sINPUT_CIPHER, sCipher);
			                	}
			                	
			                	boolean bGoon = false;
			                	do {
				                	System.out.println("Wollen Sie den Standard-Characterpool verwenden [Y] ('" + ROTnnZZZ.sCHARACTER_POOL_DEFAULT + "') oder einen eigenen eingeben [N], Abbrechen mit [Q]");		                	
				                	sInput = inputReader.nextLine();
				                	if(StringZZZ.isEmpty(sInput)){
				                		System.out.println("ungültige Eingabe");
				                		bGoon = false;
				                	}else if(sInput.equalsIgnoreCase("Y")) {
				                		if(hmVariable!=null) hmVariable.put("INPUT_CHARACTERPOOL", ROTnnZZZ.sCHARACTER_POOL_DEFAULT);
				                		bGoon = true;
				                	}else if(sInput.equalsIgnoreCase("N")) {
				                		System.out.println("Geben Sie den Charakterpool als String ein.");		                	
					                	sInput = inputReader.nextLine();			                	
					                	if(hmVariable!=null) hmVariable.put("INPUT_CHARACTERPOOL", sInput);
					                	bGoon = true;
				                	}else if(sInput.equalsIgnoreCase("Q")) {
				                		System.out.println("Abbruch");
				                		this.isCurrentInput(true);
				                		this.isCurrentMenue(true);
				                	}else {
				                		System.out.println("ungültige Eingabe");			                		
					                	bGoon=false;				                	
				                	}
			                	}while(!bGoon);
			                	//ggfs. weitere Eingaben
			                	
			                	
			                	System.out.println("Geben Sie den zu verschlüsselnden Text als String ein");
			                	sInput = inputReader.nextLine();
			                	if(hmVariable!=null) hmVariable.put("INPUT_TEXT_UNCRYPTED", sInput);
			                	
			                	this.isCurrentInput(true);
			                	this.isInputFinished(true);	
			                	break;
			                default:
			                	System.out.println("ungültige Eingabe");
			                	this.isCurrentInput(true);
			                	this.isInputFinished(false);	
			                }		                	            	
			                try {
			                	//System.out.println("Nach der Eingabe.");
			                	Thread.sleep(lSleepTime);
			                	bReturn = true;
							} catch (InterruptedException e) {
								System.out.println("KeyPressThread: 2. Wait Error");
								e.printStackTrace();
							}
	            		}//end input:
	            	}//end while
	            	
	            	if(this.getConsole().isInputFinished()==true) {
		            	while(!this.getConsole().isConsoleUserThreadFinished()) {
				        	 try {
				             	System.out.println("Warte auf Ergebnis des Cryptlaufs...");                 	
								Thread.sleep(lSleepTime);                 	
							} catch (InterruptedException e) {
								System.out.println("KeyPressThread: 2. Wait Error");
								e.printStackTrace();
							}
		            	}//end while
	            	}
	            }//end while
	    	}//end main:
	    	return bReturn;
		}
    }

