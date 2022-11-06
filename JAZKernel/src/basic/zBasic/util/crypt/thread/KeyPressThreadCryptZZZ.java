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
		boolean bCurrentInputFinished=false;
		boolean bMakeMenue=true;//true, damit die erste Anzeige generiert wird
		
        //Method that gets called when the object is instantiated
		public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole) {
        	super(objConsole);
        }
        public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	super(objConsole, lSleepTime);
        }
        public boolean isCurrentInputFinished() {
        	return this.bCurrentInputFinished;
        }
        public void isCurrentInputFinished(boolean bCurrentInput) {
        	this.bCurrentInputFinished = bCurrentInput;
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
			        	    this.isCurrentInputFinished(false);
				        	if(bSkipArguments) {
				        		System.out.println("KeyPressThread: bSkipArguments=true");
				        	}else {				        		
				        		do {
				        			this.isCurrentInputValid(false);
				        			this.isCurrentInputFinished(false);
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
					                	this.quit();					                						                
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
					                	//### Frage nach dem Numeric-Key (um den dann die Rotation stattfindet
					                	if(!this.isCurrentInputFinished()) {
						                	sInput = KeyPressUtilZZZ.makeInputNumericCancel(inputReader, "Bitte geben Sie den nummerischen Schluessel ein.");				                						                				    	                			                				                					                		
					                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){					                		
					                			this.cancelToMenue(hmVariable);				                
						                	}else {
						                		this.isCurrentInputValid(true);	
						                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_KEY_NUMERIC, sInput);					
						                	}	
					                	}
					                	
					                	//######################################################################
					                	//### Frage nach Characterpool
					                	if(!this.isCurrentInputFinished()) {
					                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie den Standard-Characterpool '" + ROTnnZZZ.sCHARACTER_POOL_DEFAULT + "' als Ausgangsstring verwenden?");				                						                				    	                			                				                					                		
					                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){					                		
					                			this.cancelToMenue(hmVariable);
					                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
					                			this.isCurrentInputValid(true);	
					                			System.out.println("Geben Sie den Charakterpool als String ein.");		                	
							                	sInput = inputReader.nextLine();
							                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL, sInput);
							                	if(StringZZZ.isEmpty(sInput)) {
							                		this.isCurrentInputValid(false); //wieder zurück zum Menue
							                		this.isCurrentMenue(true);
							                		this.isCurrentInputFinished(true);	
							                	}							                								                	
						                	}else {
						                		this.isCurrentInputValid(true);	
						                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL, ROTnnZZZ.sCHARACTER_POOL_DEFAULT);			
						                	}	
					                	}
				                		
				                		//#####################################################################
				                		//### Frage nach Grossbuchstaben
					                	if(!this.isCurrentInputFinished()) {
					                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie den Pool ergaenzend mit Großbuchstaben verwenden?");				                						                				    	                			                				                					                		
					                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){					                		
					                			this.cancelToMenue(hmVariable);
					                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
					                			this.isCurrentInputValid(true);	
							                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_UPPERCASE, BooleanZZZ.stringToBoolean(sInput));
						                	}else {
						                		this.isCurrentInputValid(true);	
						                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_UPPERCASE, BooleanZZZ.stringToBoolean(sInput));
					
						                	}
					                	}
				                		
				                		//#####################################################################
				                		//### Frage nach Kleinbuchstaben
					                	if(!this.isCurrentInputFinished()) {
					                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie den Pool ergaenzend mit Kleinbuchstaben verwenden?");				                						                				    	                			                				                					                		
					                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){					                			
					                			this.cancelToMenue(hmVariable);
					                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
					                			this.isCurrentInputValid(true);	
							                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_LOWERCASE, BooleanZZZ.stringToBoolean(sInput));
						                	}else {
						                		this.isCurrentInputValid(true);	
						                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_LOWERCASE, BooleanZZZ.stringToBoolean(sInput));			
						                	}
					                	}
				                						                		
				                		//#####################################################################
				                		//### Frage nach Zahlen
					                	if(!this.isCurrentInputFinished()) {
					                		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(inputReader, "Wollen Sie den Pool ergaenzend mit nummerischen Werte (0-9) verwenden?");				                						                				    	                			                				                					                		
					                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
					                			this.cancelToMenue(hmVariable);
					                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
					                			this.isCurrentInputValid(true);	
							                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_NUMERIC, BooleanZZZ.stringToBoolean(sInput));
						                	}else {
						                		this.isCurrentInputValid(true);	
						                		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_FLAG_CHARACTER_NUMERIC, BooleanZZZ.stringToBoolean(sInput));			
						                	}
					                	}
				                					                		
					                	break;
					                default:
					                	System.out.println("ungueltige Eingabe");
					                	this.isCurrentMenue(false);//Neue Eingabe OHNE erneut das Menue aufzubauen.
					                	this.isCurrentInputValid(false);					                	
					                	break;
					                }		 
			                
				        		}while(!this.isCurrentInputValid());	                
				        	}//end if bSkipArguments
				        	
				        	//######################################################################
				        	//### Eingabe des zu verschlüsselnden Textes
				        	//Beispieltexte zum Rauskopieren, enthalten alles relevante...
				        	//abcdefgHIJK1234abcdefg
				        	//Das ist das 4711 Haus der Riesenmaus 0815
				        	if(!this.isCurrentInputFinished()) {
					        	System.out.println("Geben Sie den zu verschluesselnden Text als String ein");
			                	sInput = this.getInputReader().nextLine();
			                	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_TEXT_UNCRYPTED, sInput);
			                	if(StringZZZ.isEmpty(sInput)) {
			                		this.cancelToMenue(hmVariable);
			                	}
				        	}
				        	
		                	//######################################################################
		                	//### Frage nach Mehrfacheingabe
				        	if(!this.isCurrentInputFinished()) {
		                		sInput = KeyPressUtilZZZ.makeQuestionYesNoQuit(this.getInputReader(), "Wollen Sie danach zurueck zum Menue oder mit den akuellen Menueangaben weiteren Text verschluesseln?");		                		                			                			    	                			                				               
		                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
//		                			this.isCurrentInputValid(true);	
//			                		System.out.println("Abbruch dieses Laufs");
//			                		this.requestStop();	
		                			this.quit();
			                	}else {		               		                		
			                		boolean bMenue = BooleanZZZ.stringToBoolean(sInput);
			                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü			                		
			                			this.cancelToMenue(hmVariable);
			                		}else {
			                			this.isCurrentInputValid(true);	
			                			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyYes));		                			
					                	this.isCurrentInputFinished(true);
			                			this.isCurrentMenue(false);	
			                		}		                		
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
		
		public void cancelToMenue(HashMapExtendedZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
			if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyNo));//wieder so als würde das Menü nicht übersprungen.
			this.cancelToMenue();
		}
		public void cancelToMenue() {			
			System.out.println("Abbruch. Zurueck zum Menue");
			this.isCurrentInputValid(false);					
    		this.isCurrentMenue(true);
    		this.isCurrentInputFinished(true);
		}
		
		public void quit() {
			System.out.println("Beenden");		                					                    
            this.isCurrentInputValid(false);
            this.isCurrentInputFinished(true);
            this.isKeyPressThreadFinished(true);
            this.requestStop(); //stop KeyPressThread über die gesetzte STOP Variable
		}
		
    }


    