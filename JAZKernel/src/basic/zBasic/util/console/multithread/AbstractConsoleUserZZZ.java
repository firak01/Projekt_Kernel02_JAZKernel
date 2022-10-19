package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractConsoleUserZZZ extends ObjectZZZ implements IConsoleUserZZZ {	
	private IConsoleZZZ objConsole=null;
	private int iCounter = 0;
	private boolean bStop = false;
	
	public AbstractConsoleUserZZZ()  throws ExceptionZZZ {
		super();
	}
	public AbstractConsoleUserZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super();
		AbstractConsoleUserNew_(objConsole);
	}
	
	private boolean AbstractConsoleUserNew_(IConsoleZZZ objConsole) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objConsole==null) {
				ExceptionZZZ ez = new ExceptionZZZ("No Console Object provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.setConsole(objConsole);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	@Override
	public abstract boolean start() throws ExceptionZZZ;
		
	
	public int getcounter() {
		return this.iCounter;
	}

	@Override
	public void requestStop() {
		this.isStopped(true);
	}
	
	@Override
	public boolean isStopped() {
		return this.bStop;
	}
	
	@Override
	public void isStopped(boolean bStop) {
		this.bStop = bStop;
	}
	 
	@Override
	public IConsoleZZZ getConsole() {
		return this.objConsole;
	}
	
	@Override
	public void setConsole(IConsoleZZZ objConsole) {
		this.objConsole = objConsole;
	}
}
