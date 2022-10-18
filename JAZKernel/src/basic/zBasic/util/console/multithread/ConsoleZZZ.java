package basic.zBasic.util.console.multithread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basic.zBasic.ObjectZZZ;

/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 * 
 * @author Fritz Lindhauer, 16.10.2022, 08:01:04
 * 
 */
public class ConsoleZZZ extends AbstractConsoleZZZ {
	
	/**Konstruktor ist private, wg. Singleton
	 */
	private ConsoleZZZ() {		
		super();		
	}

	public static IConsoleZZZ getInstance() {
		{
			if(objConsole==null){
				objConsole = new ConsoleZZZ();
			}
			return objConsole;		
		}
	}
		
}
