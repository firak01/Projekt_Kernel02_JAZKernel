package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractConsoleUserZZZ extends AbstractObjectZZZ implements IConsoleUserZZZ {	
	private IConsoleZZZ objConsole=null;
	private int iCounter = 0;
	
	public AbstractConsoleUserZZZ()  throws ExceptionZZZ {
		super();
	}
	public AbstractConsoleUserZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super();
		AbstractConsoleUserNew_(objConsole,null);
	}
	public AbstractConsoleUserZZZ(IConsoleZZZ objConsole,String sFlag) throws ExceptionZZZ {
		super();
		String[]saFlag=new String[1];
		saFlag[0]=sFlag;
		AbstractConsoleUserNew_(objConsole,saFlag);
	}
	public AbstractConsoleUserZZZ(IConsoleZZZ objConsole,String[] saFlag) throws ExceptionZZZ {
		super();
		AbstractConsoleUserNew_(objConsole,saFlag);
	}
	
	private boolean AbstractConsoleUserNew_(IConsoleZZZ objConsole, String[]saFlagControlIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objConsole==null) {
				ExceptionZZZ ez = new ExceptionZZZ("No Console Object provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.setConsole(objConsole);
 		
 			//setzen der übergebenen Flags	
			if(saFlagControlIn != null){
				 String stemp; boolean btemp; String sLog;
				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
					stemp = saFlagControlIn[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){
						 String sKey = stemp;
						 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
						 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
						//							  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
						// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
						// throw ez;		 
					}
				}
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
			}
			
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
	public synchronized boolean isInputAllFinished() {
		return this.getConsole().isInputAllFinished();
	}
	
	@Override
	public synchronized void isInputAllFinished(boolean bInputFinished) {
		this.getConsole().isInputAllFinished(bInputFinished);
	}
	
	@Override
	public synchronized boolean isOutputAllFinished() {
		return this.getConsole().isOutputAllFinished();
	}
	
	@Override
	public synchronized void isOutputAllFinished(boolean bOutputFinished) {
		this.getConsole().isOutputAllFinished(bOutputFinished);
	}
	

	@Override
	public void requestStop() {
		this.isStopped(true);
	}
	
	@Override
	public boolean isStopped() {
		return this.getConsole().isStopped();
	}
	
	@Override
	public void isStopped(boolean bStop) {
		this.getConsole().isStopped(bStop);
	}
	 
	@Override
	public synchronized IConsoleZZZ getConsole() {
		return this.objConsole;
	}
	
	@Override
	public synchronized void setConsole(IConsoleZZZ objConsole) {
		this.objConsole = objConsole;
	}
}
