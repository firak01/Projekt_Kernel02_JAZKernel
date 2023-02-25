package basic.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.console.multithread.AbstractKeyPressThreadZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;
import basic.zBasic.util.console.multithread.IKeyPressConstantZZZ;
import basic.zBasic.util.console.multithread.KeyPressUtilZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.character.ICharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractKeyPressThreadCryptZZZ extends AbstractKeyPressThreadZZZ implements IKeyPressThreadCryptConstantsZZZ{
	public AbstractKeyPressThreadCryptZZZ(IConsoleZZZ objConsole) {
    	super(objConsole);
    }
    public AbstractKeyPressThreadCryptZZZ(IConsoleZZZ objConsole, long lSleepTime) {
    	super(objConsole, lSleepTime);
    }
    
	protected boolean printTableASCII(HashMapExtendedZZZ hmVariable) throws ExceptionZZZ {
		//Ausgabe der ASCII-Zeichen auf dem aktuellen System
		boolean bReturn = true;
		main:{
			KeyPressCryptUtilZZZ.printTableAscii();
			this.isCurrentMenue(true);//das Menue erneut aufbauen
    		this.isCurrentInputFinished(true);
    		this.isInputAllFinished(true);//das beendet diesen Menuelauf
    		this.isOutputAllFinished(true);//das bewirkt, das kein anderer Thread eine Ausgabe macht.
    		
			System.out.println("Weiter mit der Menueeingabe....");
		}//end main:
		return bReturn;						
	}
	
	protected void questionNumericKey(HashMapExtendedZZZ hmVariable) throws ExceptionZZZ {
		//######################################################################
    	//### Frage nach dem Numeric-Key (um den dann die Rotation stattfindet
    	if(!this.isCurrentInputFinished()) {
        	String sInput = KeyPressUtilZZZ.makeInputNumericCancel(this.getInputReader(), "Bitte geben Sie den nummerischen Schluessel ein.");				                						                				    	                			                				                					                		
    		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){					                		
    			this.cancelToMenue(hmVariable);				                
        	}else {
        		this.isCurrentInputValid(true);	
        		if(hmVariable!=null) hmVariable.put(KeyPressThreadEncryptZZZ.sINPUT_KEY_NUMERIC, sInput);					
        	}	
    	}
	}
	
	protected void questionAlphabetKey(HashMapExtendedZZZ hmVariable) throws ExceptionZZZ {
		//######################################################################
    	//### Frage nach dem Alphabet-Key (um dessen Zahlenwert den dann die Rotation stattfindet
    	if(!this.isCurrentInputFinished()) {
        	String sInput = KeyPressUtilZZZ.makeInputAlphabetCancel(this.getInputReader(), "Bitte geben Sie das Schluesselwort bestehend aus Zeichen des Alphabets ein.");				                						                				    	                			                				                					                		
    		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){					                		
    			this.cancelToMenue(hmVariable);				                
        	}else {
        		this.isCurrentInputValid(true);	
        		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_KEY_STRING, sInput);					
        	}	
    	}
	}
	
	protected void questionUseAdditional(HashMapExtendedZZZ hmVariable, String sCharacterPool) throws ExceptionZZZ {
		
		//#####################################################################
		//### Frage nach Sonderzeichen, bzw. Zusatzzeichen        	
    	if(!this.isCurrentInputFinished()) {        		
    		boolean bCharacterPoolContainsAdditionalOnly=false;//nur nach Sonderzeichen fragen, wenn der characterPool nicht eh aus Sonderzeichen besteht.
    		if(!StringZZZ.isEmpty(sCharacterPool)) {
    			bCharacterPoolContainsAdditionalOnly=StringZZZ.containsOnly(sCharacterPool, ICharacterExtendedZZZ.sCHARACTER_ADDITIONAL);
    		}
    		        		
    		if(!bCharacterPoolContainsAdditionalOnly) {
        		String sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Pool ergaenzend mit diesen Standard-Zusatzbuchstaben '" + ICharacterExtendedZZZ.sCHARACTER_ADDITIONAL + "' verwenden?");
        		this.isCurrentInputValid(true); 
        		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
        			this.cancelToMenue(hmVariable);
        		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {		        				                		
        			System.out.println("Geben Sie den gewuenschten Standard-Zusatzbuchstaben Zeichenvorrat als String ein, oder keine Zusatzbuchstaben.");		                	
                	sInput = this.getInputReader().nextLine();                        	
                	if(StringZZZ.isEmpty(sInput)) {
                		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_USE_STRATEGY_CHARACTERPOOL, false);
                		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_CHARACTER_ADDITIONAL, false);
                		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_CHARACTERPOOL_ADDITIONAL, "");
                	}else {
                		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_USE_STRATEGY_CHARACTERPOOL, true);
                		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_CHARACTER_ADDITIONAL, true);
                		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_CHARACTERPOOL_ADDITIONAL, sInput);
                	}	                        		                        		                                            		        				                			                		                		                			                	              
            	}else{	            		
            		//STANDARD-FALL	            		
            		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_USE_STRATEGY_CHARACTERPOOL, true);
            		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_CHARACTER_ADDITIONAL, true);
            		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_CHARACTERPOOL_ADDITIONAL, CharacterExtendedZZZ.sCHARACTER_ADDITIONAL);        					            			      
            	}	        		
    		}//end if(!bCharacterPoolContainsAdditionalOnly) {	
    	}
	}
	
	protected void questionUseBlank(HashMapExtendedZZZ hmVariable, String sCharacterPool) throws ExceptionZZZ {
		//#####################################################################
		//### Frage nach Zahlen        	
    	if(!this.isCurrentInputFinished()) {

    		boolean bCharacterPoolContainsNumericOnly=false;//nur nach Zahlen fragen, wenn der characterPool nicht eh aus Zahlen besteht.
    		if(StringZZZ.isEmpty(sCharacterPool)) {
    			bCharacterPoolContainsNumericOnly=StringZZZ.containsNumericAndBlankOnly(sCharacterPool);
    		}
    		        		
    		if(!bCharacterPoolContainsNumericOnly) {
        		String sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Pool ergaenzend mit dem 'Leerzeichen' verwenden?");	        		
        		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
        			this.cancelToMenue(hmVariable);
        		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
        			this.isCurrentInputValid(true);	
                	if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_CHARACTER_BLANK, BooleanZZZ.stringToBoolean(sInput));
            	}else {
            		this.isCurrentInputValid(true);	
            		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_CHARACTER_BLANK, BooleanZZZ.stringToBoolean(sInput));
            		if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_FLAG_USE_STRATEGY_CHARACTERPOOL, BooleanZZZ.stringToBoolean(sInput));
            	}	        		
    		}
    	}
	}
	
	protected void questionUseNumeric(HashMapExtendedZZZ hmVariable, String sCharacterPool) throws ExceptionZZZ {
		//#####################################################################
		//### Frage nach Zahlen        	
    	if(!this.isCurrentInputFinished()) {

    		boolean bCharacterPoolContainsNumericOnly=false;//nur nach Zahlen fragen, wenn der characterPool nicht eh aus Zahlen besteht.
    		if(StringZZZ.isEmpty(sCharacterPool)) {
    			bCharacterPoolContainsNumericOnly=StringZZZ.containsNumericAndBlankOnly(sCharacterPool);
    		}
    		        		
    		if(!bCharacterPoolContainsNumericOnly) {
        		String sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie den Pool ergaenzend mit nummerischen Werte (0-9) verwenden?");	        		
        		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
        			this.cancelToMenue(hmVariable);
        		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
        			this.isCurrentInputValid(true);	
                	if(hmVariable!=null) hmVariable.put(KeyPressThreadEncryptZZZ.sINPUT_FLAG_CHARACTER_NUMERIC, BooleanZZZ.stringToBoolean(sInput));
            	}else {
            		this.isCurrentInputValid(true);	
            		if(hmVariable!=null) hmVariable.put(KeyPressThreadEncryptZZZ.sINPUT_FLAG_CHARACTER_NUMERIC, BooleanZZZ.stringToBoolean(sInput));
            		if(hmVariable!=null) hmVariable.put(KeyPressThreadEncryptZZZ.sINPUT_FLAG_USE_STRATEGY_CHARACTERPOOL, BooleanZZZ.stringToBoolean(sInput));
            	}	        		
    		}
    	}
	}
	
	protected void questionUseStrategy_CaseChange(HashMapExtendedZZZ hmVariable, String sCharacterPool) throws ExceptionZZZ {
		//#####################################################################
		//### Frage nach der Stragegy "CaseChange"        	
    	if(!this.isCurrentInputFinished()) {
    		String sInput = KeyPressUtilZZZ.makeQuestionYesNoCancel(this.getInputReader(), "Wollen Sie als Strategie den Austausch zwischen den Typen Gross-/Kleinbuchstaben und Numerischen Zeichen verwenden (y) oder soll der Austausch innerhalb des Typs bleiben (N)?");	        		
    		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)){
    			this.cancelToMenue(hmVariable);
    		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {
    			this.isCurrentInputValid(true);	
    			if(hmVariable!=null) hmVariable.put(KeyPressThreadEncryptZZZ.sINPUT_FLAG_USE_STRATEGY_CASECHANGE, BooleanZZZ.stringToBoolean(sInput));
        	}else {
        		this.isCurrentInputValid(true);	            		
        		if(hmVariable!=null) hmVariable.put(KeyPressThreadEncryptZZZ.sINPUT_FLAG_USE_STRATEGY_CASECHANGE, BooleanZZZ.stringToBoolean(sInput));
        		
        		//###############################################################
        		this.questionUseBlank(hmVariable,null);
        	}	        		
		}
	}
	
}
