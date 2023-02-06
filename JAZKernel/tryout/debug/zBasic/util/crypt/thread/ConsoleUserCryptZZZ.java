package debug.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.console.multithread.AbstractConsoleUserZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmFactoryZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMaintypeZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolUserZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.IVigenereNnZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadCryptZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagUserZZZ;

public class ConsoleUserCryptZZZ extends AbstractConsoleUserZZZ {
	private static final long serialVersionUID = 1L;

	public ConsoleUserCryptZZZ() throws ExceptionZZZ {
		super();
	}
	
	public ConsoleUserCryptZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}
	public ConsoleUserCryptZZZ(IConsoleZZZ objConsole, String sFlag) throws ExceptionZZZ {
		super(objConsole, sFlag);
	}
	public ConsoleUserCryptZZZ(IConsoleZZZ objConsole, String[] saFlag) throws ExceptionZZZ {
		super(objConsole, saFlag);
	}
	
	private int iCounter = 0;
		
	public int getcounter() {
		return this.iCounter;
	}
	
	@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		try {
		main:{
			this.getConsole().isConsoleUserThreadRunning(true);
			//Merke: Diesen Teil nicht als Schleife ausführen... viel zu kompliziert... es gibt schon genug andere Threads
			//while(!this.isStopped()) {
			if(this.isStopped()) break main;
			if(this.isOutputAllFinished()) break main; //wenn Z.B. schon ein Menuepunkt ausgefuehrt worden ist. Z.B. eine einfache ASCII-Tabelle ausgegeben wurde.
			if(!this.isInputAllFinished()) break main; 
			String sInput = null;
			
			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
			//       Darum muss man alles in dem KeyPressThread erledigen
			//Warten auf die fertige Eingabe.			
			//if(!this.getConsole().isKeyPressThreadFinished()) break main;
			if(this.getFlag(IFlagUserZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread START: WARTE AUF FERTIGE KONSOLENEINGABE ######");				
			do {
				 try {				 
					 Thread.sleep(200);
					 //System.out.println("CryptThread wartet auf fertige Konsoleneingabe");
				} catch (InterruptedException e) {
					System.out.println("KeyPressThread: Wait Error");
					e.printStackTrace();
				}				 
			}while(!this.getConsole().isInputAllFinished());
			if(this.getFlag(IFlagUserZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread ENDE: WARTE AUF FERTIGE KONSOLENEINGABE ######");
			
			
			//this.isOutputAllFinished(false);
			
			
			this.iCounter++;
			if(this.getFlag(IFlagUserZZZ.FLAGZ.DEBUG)) System.out.println("Zähler crypt: " + iCounter);

			HashMapExtendedZZZ<String,Object>hmVariable=this.getConsole().getVariableHashMap();
			if(hmVariable!=null) {
				String sDebug = hmVariable.debugString("|","<BR>");
				System.out.println(sDebug);
			}
			
			if(hmVariable!=null) {
				//Ausgabewerte zurücksetzen
				hmVariable.remove(KeyPressThreadCryptZZZ.sOUTPUT_TEXT_CRYPTED);
				hmVariable.remove(KeyPressThreadCryptZZZ.sOUTPUT_TEXT_UNCRYPTED);
			}
					
			//Die eingegebenen Variablen über eine HashMap aus der Console für die Steuereung der Verschlüsselung nutzen. 			
			//String sCipher = (String) hmVariable.get(CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROT13.getAbbreviation());
			String sCipher = (String) hmVariable.get(KeyPressThreadCryptZZZ.sINPUT_CIPHER);
			if(!StringZZZ.isEmpty(sCipher)) {
				ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.getInstance().createAlgorithmType(sCipher);
								
				//+++ Zusätzlich gesetzte Argumente				
				if(objCrypt.getSubtype()==CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal()) {
					//A) Für ROT-Algorithmen
					//0) NumericKey
					sInput = (String) hmVariable.get(KeyPressThreadCryptZZZ.sINPUT_KEY_NUMERIC);
					if(!StringZZZ.isEmpty(sInput)) {
						Integer intCryptKey = new Integer(sInput);
						int iCryptKey = intCryptKey.intValue();
						objCrypt.setCryptNumber(iCryptKey);
					}

				}else if(objCrypt.getSubtype()==CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal()) {
					//B) Für Vigenere-Algorithmen
					//0) StringKey
					sInput = (String) hmVariable.get(KeyPressThreadCryptZZZ.sINPUT_KEY_STRING);
					if(!StringZZZ.isEmpty(sInput)) {
						String sCryptKey = sInput;						
						objCrypt.setCryptKey(sCryptKey);
					}
										
				}else {
					System.out.println("Der Algorithmus-Subtyp wird nicht behandelt.");
					bReturn = false;
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++				
				//a) alle Flags setzen und ggfs. auswerten
				String[] saFlags = hmVariable.getKeysAsStringStartingWith(KeyPressThreadCryptZZZ.sINPUT_FLAG);
				if(saFlags!=null) {
					for(String stemp : saFlags) {
						String sFlagName = StringZZZ.right(stemp,KeyPressThreadCryptZZZ.sINPUT_FLAG);
						Boolean bValue = (Boolean) hmVariable.get(stemp);
						objCrypt.setFlag(sFlagName, bValue);
					}
				}
				
				//b) CharacterPool setzen, wenn verwendet
				if(objCrypt.getFlag(ICharacterPoolUserZZZ.FLAGZ.USECHARACTERPOOL.name())) {
					sInput = (String) hmVariable.get(KeyPressThreadCryptZZZ.sINPUT_CHARACTERPOOL);
					if(!StringZZZ.isEmpty(sInput)) {
						objCrypt.setCharacterPoolBase(sInput);
						
						//... und dann auch das fehlende Zeichen setzen
						CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ('¶');//Mal was anderes verwenden als den "DefaultBuchstaben" aus ICharacterPoolUserConstantZZZ
						objCrypt.setCharacterMissingReplacement(objCharacterMissingReplacement);
					}
					
					//ba) 
					//TODOGOON20230206;//Zusaetzlichen Zeichenpool setzen, wenn verwendet
					
				}						
				//+++++++++++++++++++++++++++++++++++++++++++++++++
								
				sInput = (String) hmVariable.get(KeyPressThreadCryptZZZ.sINPUT_TEXT_UNCRYPTED);				
				try {
					String sOutput = objCrypt.encrypt(sInput);
					hmVariable.put(KeyPressThreadCryptZZZ.sOUTPUT_TEXT_CRYPTED, sOutput);
					
					System.out.println("Verschluesselter Wert:\n"+sOutput);
					String sOutput2 = objCrypt.decrypt(sOutput);
					hmVariable.put(KeyPressThreadCryptZZZ.sOUTPUT_TEXT_UNCRYPTED, sOutput2);
					System.out.println("Wieder entschluesselter Wert:\n"+sOutput2);
					
					bReturn = true;
				}catch( IllegalArgumentException e) {
					String sError=e.getMessage();
					System.out.println("Fehler bei der Eingabe.\nText enthaelt fuer die Argumentkombination ungueltige Werte.\nFehler: "+sError +"\nbei Eingabe: "+sInput);
					bReturn=false;
				}
				
			}else {
				System.out.println("noch kein Schluesselalgorithmus festgelegt.");
				bReturn = false;
			}
			if(this.getFlag(IFlagUserZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread START: DUMMYWARTEN ALS TEST ######");
			 try {				 
				 Thread.sleep(4500);
			} catch (InterruptedException e) {
				System.out.println("KeyPressThread: Wait Error");
				e.printStackTrace();
			}
			 if(this.getFlag(IFlagUserZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread ENDE: DUMMYWARTEN ALS TEST ######");			 
			 this.isOutputAllFinished(true);			
			//}//end while !isStopped
		}//end main:
		}catch(ExceptionZZZ ez) {
			ez.printStackTrace();
		}
		this.getConsole().isConsoleUserThreadFinished(true);
		return bReturn;
	}
}
