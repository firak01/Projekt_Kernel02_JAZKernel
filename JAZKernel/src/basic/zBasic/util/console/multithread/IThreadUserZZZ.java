package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;

public interface IThreadUserZZZ {
	public boolean start() throws ExceptionZZZ;
	
	public boolean isStopped();
	public void isStopped(boolean bStop);
	public void requestStop();
}
