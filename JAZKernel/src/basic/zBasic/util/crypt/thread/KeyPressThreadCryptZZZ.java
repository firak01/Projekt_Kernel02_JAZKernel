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
        //Method that gets called when the object is instantiated
		public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole) {
        	super(objConsole);
        }
        public KeyPressThreadCryptZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	super(objConsole, lSleepTime);
        }
		@Override
		public void makeMenueMain() throws InterruptedException {
			System.out.println("Eingaben: + - zur Console-Threadgeschwindigkeit | Q zum Abbruch");
			System.out.println("Bitte wählen Sie den Algorithmus:");
			System.out.println("1: Rot13");
			System.out.println("2: RotNn");
         	System.out.println("Warte auf Eingabe Crypt...");                 	
			Thread.sleep(this.getSleepTime()); 
		}
		
		@Override
		public boolean processMenueMainArgumentInput(String sInput, HashMapExtendedZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn = true;
			main:{
			//In the JDK 7 release, you can use a String object in the expression of a switch statement:
            //Das keine lowercase Methode oder eine Fallunterscheidung in den CASE eingebaut werden kann, 
            //vorher lowercase
            this.isCurrentMenue(true);
            String input = sInput.toLowerCase();			                
            switch(input) {
            case "+":
            	this.isCurrentInputValid(true);					                	
            	this.setSleepTime(this.getSleepTime()+100);
            	this.getConsole().setSleepTime(this.getSleepTime());			                	
            	break;
            case "-":
            	this.isCurrentInputValid(true);
            	this.setSleepTime(this.getSleepTime()-100);
            	this.getConsole().setSleepTime(this.getSleepTime());			                	
            	break;
            case "q":
            	this.quit();
            	bReturn=false;
            	break main; 
            case "1":
            	this.isCurrentInputValid(true);
            	if(hmVariable!=null) {
            		String sCipher = CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROT13.getAbbreviation();
            		hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CIPHER, sCipher);
            	}
            						                						                						                					                		               
            	break;
            case "2":
            	this.isCurrentInputValid(true);
            	this.processROTnn(hmVariable);        					                	
            	break;
            default:
            	System.out.println("ungueltige Eingabe");
            	this.isCurrentMenue(false);//Neue Eingabe OHNE erneut das Menue aufzubauen.
            	this.isCurrentInputValid(false);					                	
            	break;
            }		 		
		}//end main:
		return bReturn;
	}
		
		public boolean processMenuePostArgumentInput(HashMapExtendedZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn = true;
			main:{

        		//######################################################################
	        	//### Eingabe des zu verschlüsselnden Textes
	        	//Beispieltexte zum Rauskopieren, enthalten alles relevante...
	        	//abcdefgHIJK1234abcdefg
	        	//Das ist das 4711 Haus der Riesenmaus 0815
	        	
        		
        		//TODOGOON20221107; //Fehler abfangen: Exception in thread "Thread-1" java.lang.IllegalArgumentException: Illegal character 'ß'
                  //Ausserdem muss das in KeyPassThreadCryptZZZ
	        	System.out.println("Geben Sie den zu verschluesselnden Text als String ein");
            	String sInput = this.getInputReader().nextLine();
            	if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_TEXT_UNCRYPTED, sInput);
            	if(StringZZZ.isEmpty(sInput)) {
            		this.cancelToMenue(hmVariable);
            	}
				
			}//end main:
			return bReturn;
		}
		
		private void processROTnn(HashMapExtendedZZZ hmVariable) throws ExceptionZZZ{
			
			String sInput;
			if(hmVariable!=null) {
        		String sCipher = CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROTnn.getAbbreviation();
        		hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CIPHER, sCipher);
        	}	
        	
        	//######################################################################
        	//### Frage nach dem Numeric-Key (um den dann die Rotation stattfindet
        	if(!this.isCurrentInputFinished()) {
            	sInput = KeyPressUtilZZZ.makeInputNumericCancel(this.getInputReader(), "Bitte geben Sie den nummerischen Schluessel ein.");				                						                				    	                			                				                					                		
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
        		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Standard-Zeichenvorrat '" + ROTnnZZZ.sCHARACTER_POOL_DEFAULT + "' als Ausgangsstring verwenden?");				                						                				    	                			                				                					                		
        		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){					                		
        			this.cancelToMenue(hmVariable);
        		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
        			this.isCurrentInputValid(true);	
        			System.out.println("Geben Sie den Zeichenvorrat als String ein.");		                	
                	sInput = this.getInputReader().nextLine();
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
        	String sCharacterPool = (String) hmVariable.get(KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL);
    		
    		//#####################################################################
    		//### Frage nach Grossbuchstaben
        	if(!this.isCurrentInputFinished()) {
        		if(!StringZZZ.containsUppercaseOnly(sCharacterPool)) {
	        		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Pool ergaenzend mit Grossbuchstaben verwenden?");				                						                				    	                			                				                					                		
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
        	}
    		
    		//#####################################################################
    		//### Frage nach Kleinbuchstaben
        	if(!this.isCurrentInputFinished()) {
        		if(!StringZZZ.containsLowercaseOnly(sCharacterPool)) {
	        		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Pool ergaenzend mit Kleinbuchstaben verwenden?");				                						                				    	                			                				                					                		
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
        	}
    						                		
    		//#####################################################################
    		//### Frage nach Zahlen        	
        	if(!this.isCurrentInputFinished()) {
        		if(!StringZZZ.containsNumericOnly(sCharacterPool)) {
	        		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Pool ergaenzend mit nummerischen Werte (0-9) verwenden?");
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
        	}
        	
        	//#####################################################################
    		//### Frage nach Leerzeichen
        	if(!this.isCurrentInputFinished()) {
        		if(!StringZZZ.containsBlankAny(sCharacterPool)) {
	        		sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Pool ergaenzend mit dem Leerzeichen ' ' verwenden?");				                						                				    	                			                				                					                		
	        		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
	        			this.cancelToMenue(hmVariable);
	        		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
	        			this.isCurrentInputValid(true);	
	                	//nix weiter
	            	}else {
	            		this.isCurrentInputValid(true);
	            		if(hmVariable!=null) hmVariable.put(KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL, (" " + sCharacterPool));	            				
	            	}
        		}
        	}
        	
        	
        	
    		
        	
		}
}


    