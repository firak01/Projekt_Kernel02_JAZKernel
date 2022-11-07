package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;

public interface IKeyPressThreadZZZ extends IThreadUserZZZ{
	public long getSleepTime();
	public void setSleepTime(long lSleepTime);

	public Scanner getInputReader();//Das Scanner Objekt
	public void setInputReader(Scanner objScanner);
	
	public boolean isKeyPressThreadFinished(); //Informiert die Konsole, das der Eingabethread fertig ist
	public void isKeyPressThreadFinished(boolean bFinished);
			
	public boolean isCurrentInputFinished(); //Flag, das darüber informiert, dass keine weiteren Eingaben gemacht werden sollen. 
    public void isCurrentInputFinished(boolean bCurrentInput);
    
    public boolean isCurrentInputValid();    //Gedacht für eine WHILE Schleife, z.B. im ersten Menue: Solange die Eingabe abfragen, bis was gueltiges ausgewaehlt wird.
    public void isCurrentInputValid(boolean bCurrentInput) ;
    
    public boolean isCurrentMenue();         //falls true, dann soll wieder das Menue mit den Eingabemoeglichkeiten angezeigt werden
    public void isCurrentMenue(boolean bMakeMenue);
    
    public boolean isInputAllFinished();
    public void isInputAllFinished(boolean bInputAllFinished) ;
    
    public void makeMenueMain() throws InterruptedException,ExceptionZZZ;//zu überschreiben...Das Hauptmenue ausgeben
    public boolean processMenueMainInput(String sInput, HashMapExtendedZZZ hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit
}
