package basic.zBasic.util.crypt.thread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.console.multithread.AbstractKeyPressThreadZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;
import basic.zBasic.util.console.multithread.IKeyPressConstantZZZ;
import basic.zBasic.util.console.multithread.KeyPressUtilZZZ;
import basic.zBasic.util.crypt.CryptCipherAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.ROTnnZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


	 
	public class KeyPressThreadCryptZZZ extends AbstractKeyPressThreadZZZ implements IKeyPressThreadCryptConstantsZZZ{
		boolean bCurrentInputValid=false;
		boolean bMakeMenue=true;//true, damit die erste Anzeige generiert wird
		
        //Method that gets called when the object is instantiated
		public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole) {
        	super(objConsole);
        }
        public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	super(objConsole, lSleepTime);
        }
               
        public boolean isCurrentInputValid() {
        	return this.bCurrentInputValid;
        }
        public void isCurrentInputValid(boolean bCurrentInput) {
        	this.bCurrentInputValid = bCurrentInput;
        }
        public boolean isCurrentMenue() {
        	return this.bMakeMenue;
        }
        public void isCurrentMenue(boolean bMakeMenue) {
        	this.bMakeMenue = bMakeMenue;
        }
        public synchronized boolean isInputAllFinished() {
        	return this.getConsole().isInputAllFinished();
        }
        public synchronized void isInputAllFinished(boolean bInputAllFinished) {
        	this.getConsole().isInputAllFinished(bInputAllFinished);
        }
		@Override
		public boolean start() throws ExceptionZZZ {
			boolean bReturn = true;
        	main:{
    			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
    			//       Darum muss man alle Eingaben in diesem KeyPressThread erledigen				
				this.getConsole().isKeyPressThreadRunning(true);
				
				HashMapExtendedZZZ hmVariable = this.getConsole().getVariableHashMap();								
	            while(!this.isStopped()){
	            	
	            	long lSleepTime = this.getSleepTime();
	            	//synchronized(this) {
	            	input:{	            		
	            		String sInput = null; boolean bSkipArguments=false;	String sCipher=null;
		            		            		            			            	
		            	//while(!this.getConsole().isKeyPressThreadFinished()) {
		            	if(!this.isInputAllFinished()) {
			        	    if(hmVariable!=null) {
			        	    	sInput = (String) hmVariable.get(KeyPressThreadCryptZZZ.sINPUT_SKIP_ARGUMENTS);
			        	        if(!StringZZZ.isEmpty(sInput)) {
			        	        	if(sInput.equalsIgnoreCase("Y")) {
			        	        		bSkipArguments=true;
			        	        	}
			        	        }
			        	    }
				        	   
			        	    //########################################################
			        	    //#### Eingabe der Argumente
				        	if(bSkipArguments) {
				        		System.out.println("KeyPressThread: bSkipArguments=true");
				        	}else {
				        		do {
				        			this.isCurrentInputValid(false);
						        	 try {
						        		if(this.isCurrentMenue()) {				        			
							        		System.out.println("Eingaben: + - zur Console-Threadgeschwindigkeit | Q zum Abbruch");
											System.out.println("Bitte wählen Sie den Algorithmus:");
											System.out.println("1: Rot13");
											System.out.println("2: RotNn");
							             	System.out.println("Warte auf Eingabe Crypt...");                 	
											Thread.sleep(lSleepTime);   									
						        		}
									} catch (InterruptedException e) {
										System.out.println("KeyPressThread: 1. Wait Error");
										e.printStackTrace();
									}
			
					                //das holt wohl wort fuer wort von der Konsole: String sInput = inputReader.next();
						        	Scanner inputReader = this.getInputReader();				      
						        	sInput = inputReader.nextLine();
					                System.out.println("Pressed Crypt:" + sInput);
					                if(sInput==null) break main;
					                
					                //In the JDK 7 release, you can use a String object in the expression of a switch statement:
					                //Das keine lowercase Methode oder eine Fallunterscheidung in den CASE eingebaut werden kann, 
					                //vorher lowercase
					                this.isCurrentMenue(true);
					                String input = sInput.toLowerCase();			                
					                switch(input) {
					                case "+":
					                	this.isCurrentInputValid(true);					                	
					                	lSleepTime+=100;
					                	this.getConsole().setSleepTime(lSleepTime);			                	
					                	break;
					                case "-":
					                	this.isCurrentInputValid(true);
					                	lSleepTime-=100;
					                	this.getConsole().setSleepTime(lSleepTime);			                	
					                	break;
					                case "q":
					                	System.out.println("Beenden");		                					                    
					                    this.isCurrentInputValid(true);
					                    this.isKeyPressThreadFinished(true);
					                    this.requestStop(); //stop KeyPressThread über die gesetzte STOP Variable
					                	break main; 
					                case "1":
					                	this.isCurrentInputValid(true);
					                	if(hmVariable!=null) {
					                		sCipher = CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROT13.getAbbreviation();
					                		hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CIPHER, sCipher);
					                	}
					                						                						                						                					                		               
					                	break;
					                case "2":
					                	this.isCurrentInputValid(true);
					                	if(hmVariable!=null) {
					                		sCipher = CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROTnn.getAbbreviation();
					                		hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CIPHER, sCipher);
					                	}	
					                	
					                	//######################################################################
					                	//### Frage nach Characterpool
				                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie den Standard-Characterpool '" + KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL + "' verwenden?");				                						                				    	                			                				                					                		
				                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
				                			System.out.println("Abbruch. Zurück zum Menue");
				                			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_SKIP_ARGUMENTS, IKeyPressConstantZZZ.cKeyNo);//wieder so als würde das Menü nicht übersprungen.
					                		this.isCurrentMenue(true);
					                		this.isCurrentInputValid(false);
				                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
				                			System.out.println("Geben Sie den Charakterpool als String ein.");		                	
						                	sInput = inputReader.nextLine();			                	
						                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL, sInput);
					                	}else {
					                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL, ROTnnZZZ.sCHARACTER_POOL_DEFAULT);
				
					                	}		
				                		
				                		//#####################################################################
				                		//### Frage nach Grossbuchstaben
				                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie Großbuchstaben verwenden?");				                						                				    	                			                				                					                		
				                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
				                			System.out.println("Abbruch. Zurück zum Menue");
				                			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_UPPERCASE, IKeyPressConstantZZZ.cKeyNo);//wieder so als würde das Menü nicht übersprungen.
					                		this.isCurrentMenue(true);
					                		this.isCurrentInputValid(false);
				                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                					                	
						                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_UPPERCASE, BooleanZZZ.stringToBoolean(sInput));
					                	}else {
					                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_UPPERCASE, BooleanZZZ.stringToBoolean(sInput));
				
					                	}
				                		
				                		//#####################################################################
				                		//### Frage nach Kleinbuchstaben
				                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie Kleinbuchstaben verwenden?");				                						                				    	                			                				                					                		
				                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
				                			System.out.println("Abbruch. Zurück zum Menue");
				                			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_LOWERCASE, IKeyPressConstantZZZ.cKeyNo);//wieder so als würde das Menü nicht übersprungen.
					                		this.isCurrentMenue(true);
					                		this.isCurrentInputValid(false);
				                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                					                	
						                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_LOWERCASE, BooleanZZZ.stringToBoolean(sInput));
					                	}else {
					                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_LOWERCASE, BooleanZZZ.stringToBoolean(sInput));			
					                	}
				                		
				                		TODOGOON20221104; //Fehler beim Verschlüsseln/Entschlüsseln
				                		//#####################################################################
				                		//### Frage nach Zahlen
				                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie numerische Werte (0-9) verwenden?");				                						                				    	                			                				                					                		
				                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
				                			System.out.println("Abbruch. Zurück zum Menue");
				                			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_NUMERIC, IKeyPressConstantZZZ.cKeyNo);//wieder so als würde das Menü nicht übersprungen.
					                		this.isCurrentMenue(true);
					                		this.isCurrentInputValid(false);
				                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                					                	
						                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_NUMERIC, BooleanZZZ.stringToBoolean(sInput));
					                	}else {
					                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_NUMERIC, BooleanZZZ.stringToBoolean(sInput));			
					                	}
				                					                		
					                	break;
					                default:
					                	System.out.println("ungültige Eingabe");
					                	this.isCurrentMenue(false);//Neue Eingabe OHNE erneut das Menue aufzubauen.
					                	this.isCurrentInputValid(false);					                	
					                	break;
					                }		 
			                
				        		}while(!this.isCurrentInputValid());	                
				        	}//end if bSkipArguments
				        	
				        	//######################################################################
				        	//### Eingabe des zu verschlüsselnden Textes
				        	
				        	System.out.println("Geben Sie den zu verschlüsselnden Text als String ein");
		                	sInput = this.getInputReader().nextLine();
		                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_TEXT_UNCRYPTED, sInput);
		                	
		                	//######################################################################
		                	//### Frage nach Mehrfacheingabe
		                	this.isCurrentInputValid(false);
	                		sInput = KeyPressUtilZZZ.makeQuestionYesNoQuit(this.getInputReader(), "Wollen Sie danach zurück zum Menue oder mit den akuellen Menueangaben weiteren Text verschluesseln?");
	                		this.isCurrentInputValid(true);	                			                			    	                			                				                
	                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
		                		System.out.println("Abbruch dieses Laufs");
		                		this.requestStop();		   	                			
		                	}else {		               		                		
		                		
		                		
		                		boolean bMenue = BooleanZZZ.stringToBoolean(sInput);
		                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü	                			
		                			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_SKIP_ARGUMENTS, "N");
		                			System.out.println("Zurück zum Menue");		                		
			                		this.isCurrentMenue(true);
		                		}else {
		                			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_SKIP_ARGUMENTS, "Y");
		                			this.isCurrentMenue(false);	
		                		}
		                		
		                	}
	                		
	                		
				        	//#########################################################################
			                try {
			                	System.out.println("Nach der Eingabe.");
			                	Thread.sleep(lSleepTime);			                	
							} catch (InterruptedException e) {
								System.out.println("KeyPressThread: 2. Wait Error");
								e.printStackTrace();
							}
			                this.isInputAllFinished(true);
	            		}//end if inputAllFinished
	            	}//end input:
	            	//}//End synchro
	            	
	            	while(!this.getConsole().isOutputAllFinished() && !this.getConsole().isConsoleUserThreadFinished() && !this.getConsole().isStopped()) {
			        	 try {
			             	System.out.println("Warte auf Ergebnis des Cryptlaufs...");                 	
							Thread.sleep(lSleepTime);  		
							//this.isInputAllFinished(false);//Bereit für neue Eingaben, hier und nicht nach der Schleife!!!
						} catch (InterruptedException e) {
							System.out.println("KeyPressThread: 2. Wait Error");
							e.printStackTrace();
						}
	            	}//end while		            	
	            		
	            	
	            }//end while isStopped
	    	}//end main:
			this.getConsole().isKeyPressThreadFinished(true);
	    	return bReturn;
		}
    }


    