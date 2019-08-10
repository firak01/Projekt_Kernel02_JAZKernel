package basic.zBasic;

/** Klasse, wird in den Testklassen aufgerufen.
 *   Sie ist Ausgangsposition für den Aufruf weiterer Testklassen, in denen dann die zu testenden Reflect-Methoden ausgeführt werden.
 *  
 * @author Fritz Lindhauer, 09.08.2019, 08:43:27
 * 
 */
public class ReflectTHECodeKernelObject4TestingZZZ extends ObjectReflectableZZZ {
	String[] saCallingStack = null;
	
	public ReflectTHECodeKernelObject4TestingZZZ(){
		super();
	}
	
	public String[] getCallingStackComputed(){
		return this.saCallingStack;
	}

	private void setCallingStackComputed(String[] sa){
		this.saCallingStack = sa;
	}
		
	//############
	public boolean startAsMain() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			bReturn = this.startAsSub();
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSub() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			bReturn = this.startAsSubA();
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubA() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			bReturn = this.startAsSubAA();
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubAA() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			String[] saReturn = ReflectCodeKernelZZZ.getCallingStack();
			this.setCallingStackComputed(saReturn);
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubInitialiseObject() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			String[] saReturn = ReflectCodeKernelZZZ.getCallingStack();
			//this.setCallingStackComputed(saReturn);
			
			ReflectTHECodeKernelObject4TestingSubZZZ objTest = new ReflectTHECodeKernelObject4TestingSubZZZ();
						
		}//end main:
		return bReturn;
	}
}
