package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;

public abstract class AbstractProgramWithStatusOnStatusListeningMonitoredRunnableZZZ extends AbstractProgramWithStatusOnStatusListeningRunnableZZZ implements IStatusLocalMapForMonitoringStatusLocalUserZZZ {
	private static final long serialVersionUID = -1445384815158662362L;
	protected volatile HashMap<IEnumSetMappedStatusZZZ, IEnumSetMappedStatusZZZ> hmEnumSet =null;
	
	
	public AbstractProgramWithStatusOnStatusListeningMonitoredRunnableZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramWithStatusOnStatusListeningMonitoredRunnableZZZ(String[] saFlag) throws ExceptionZZZ {
		super();	
		AbstractProgramRunnableWithStatusListeningCascadedNew_(saFlag);
	}
	
	private boolean AbstractProgramRunnableWithStatusListeningCascadedNew_(String[] saFlagControl) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(saFlagControl != null){
				String stemp; boolean btemp;
				for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
					stemp = saFlagControl[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 						
						   throw ez;		 
					}
				}
				if(this.getFlag("init")) break main;
			}
						
			
		}//end main:
		return bReturn;
	}

	//#### GETTER / SETTER
	
	
	
	//#### METHODEN
	@Override
	public void run() {		
		try {
			this.startCustom();
		} catch (ExceptionZZZ ez) {
			try {
				this.logProtocolString(ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}
		}
	}//END run
	
	@Override 
	public boolean start() throws ExceptionZZZ {
		return this.startAsThread(); //Merke: Anders als ein einfaches Program wird ein runnable Program in seinem eigenen Thread gestarted.
	}
	
	@Override
	public boolean startAsThread() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
				Thread objThreadMonitor = new Thread(this);
				objThreadMonitor.start();//Damit wird run() aufgerufen, was wiederum start_() als private Methode aufruft
				
				bReturn = true;								
		}//end main:
		return bReturn;
	}
		
	@Override
	abstract public boolean startCustom() throws ExceptionZZZ;
	
	
}
