package basic.zBasic.util.console.multithread;

import basic.zBasic.ObjectZZZ;

public abstract class AbstractConsoleUserZZZ extends ObjectZZZ implements IConsoleUserZZZ {
	private int iCounter = 0;
	private boolean bStop = false;
	
	@Override
	public abstract boolean start();
		
	
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

		

}
