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
public abstract class AbstractConsoleZZZ extends ObjectZZZ implements IConsoleZZZ {
	protected static IConsoleZZZ objConsole = null;  //muss static sein, wg. getInstance()!!!
	
	private IKeyPressThreadZZZ objThreadKeyPress=null;
	private IConsoleUserZZZ objConsoleUser = null;
	private IConsoleThreadZZZ objThreadConsole = null;
	
	//Variablen zur Steuerung des internen Threads
	private long lSleepTime=1000;
	private boolean bStop = false;
	
	/**Konstruktor ist private, wg. Singleton
	 */
	protected AbstractConsoleZZZ() {		
		super();
		ConsoleMain_();
	}
	//Merke: das geht static nicht abstract public abstract IConsoleZZZ getInstance();
	
	
	
	private boolean ConsoleMain_() {
		boolean bReturn = false;
		main:{
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean start() {
		boolean bReturn = false;
		main:{			
	        try {	        	
	        	final IKeyPressThreadZZZ objThreadKeyPress = this.getKeyPressThread();
	            Thread t1 = new Thread((Runnable) objThreadKeyPress);
	            t1.start();

	            final IConsoleThreadZZZ objThreadConsole = this.getConsoleThread();	          
		        Thread t2 = new Thread((Runnable) objThreadConsole);
		        t2.start();
	         
	        } catch (Exception e)        {
	            e.printStackTrace();
	        }
			
		}//end main:
		return bReturn;		
	}
	
	public boolean isStopped() {
		return this.bStop;
	}
	public void isStopped(boolean bStop) {
		this.bStop = bStop;
	}
	public void requestStop() {
		this.isStopped(true);
	}
	
	public long getSleepTime() {
		return this.lSleepTime;
	}
	public void setSleepTime(long lSleepTime) {
		this.lSleepTime = lSleepTime;
	}
	
	@Override
	public IConsoleZZZ getConsole() {
		return this.objConsole;
	}
	
	@Override
	public void setConsole(IConsoleZZZ objConsole) {
		this.objConsole = objConsole;
	}

	@Override
	public IConsoleUserZZZ getConsoleUserObject() {
		return this.objConsoleUser;
	}

	@Override
	public void setConsoleUserObject(IConsoleUserZZZ objConsoleUser) {
		this.objConsoleUser = objConsoleUser;
	}

	@Override
	public IKeyPressThreadZZZ getKeyPressThread() {
		if(this.objThreadKeyPress==null) {
			long lSleepTime = this.getSleepTime();
			this.objThreadKeyPress = new KeyPressThreadDefaultZZZ(lSleepTime);		
		}
		return this.objThreadKeyPress;
	}

	private void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread) {
		this.objThreadKeyPress = objKeyPressThread;
	}
	
	@Override
	public IConsoleThreadZZZ getConsoleThread() {
		if(this.objThreadConsole==null) {
			IKeyPressThreadZZZ objKeyPressThread = this.getKeyPressThread();
			if(objKeyPressThread!=null) {
			
				IConsoleUserZZZ objConsoleUser = this.getConsoleUserObject();
				if(objConsoleUser!=null) {
					long lSleepTime = this.getSleepTime();
					this.objThreadConsole = new ConsoleThreadZZZ(lSleepTime, objKeyPressThread);
			        this.objThreadConsole.setConsoleUserObject(this.getConsoleUserObject());
				}
			}
		}
		return this.objThreadConsole;    
	}
	
	private void setConsoleThread(ConsoleThreadZZZ objThreadConsole) {
		this.objThreadConsole = objThreadConsole;
	}
	
	
	
	

	
	
	
}
