package basic.zBasic.component;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;

public abstract class AbstractProgramWithStatusOnStatusListeningRunnableZZZ extends AbstractProgramWithStatusOnStatusListeningZZZ implements IProgramRunnableZZZ{
	private static final long serialVersionUID = 6586079955658760005L;		
		
	public AbstractProgramWithStatusOnStatusListeningRunnableZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramWithStatusOnStatusListeningRunnableZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);		
	}
	
	public AbstractProgramWithStatusOnStatusListeningRunnableZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);	
		AbstractProgramRunnableWithStatusListeningNew_();
	}
	
	private boolean AbstractProgramRunnableWithStatusListeningNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(this.getFlag("init")) break main;
						
			
		}//end main:
		return bReturn;
	}
	
	//#### METHODEN
	//### aus IProgramRunnableZZZ
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
				Thread objThread = new Thread(this);
				objThread.start();//Damit wird run() aufgerufen, was dann die definierte Methode "startCustom()" aufruft.
				
				bReturn = true;								
		}//end main:
		return bReturn;
	}
		
	@Override
	abstract public boolean startCustom() throws ExceptionZZZ;	
	
	@Override
	abstract public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ;
	
	@Override
	public boolean queryReactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean bQueryOnStatusLocalEvent = super.queryReactOnStatusLocalEvent(eventStatusLocal);
			if(!bQueryOnStatusLocalEvent)break main;
			
			String sLog;
		
			//Falls das REQUEST_STOP Flag gesetzt ist, nicht weiter reagieren...
			if(this.getFlag(IProgramRunnableZZZ.FLAGZ.REQUEST_STOP)) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + "Flag '" + IProgramRunnableZZZ.FLAGZ.REQUEST_STOP.name() + "' gesetzt. Keine weitere Verarbeitung von Events. Breche ab.";
				this.logProtocolString(sLog);
				break main;
			}
			
		}//end main:
		return bReturn;
	}
	
	//##########################################
	//### FLAG HANDLING IProgramRunnableZZZ
	//##########################################
	@Override
	public boolean getFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IProgramRunnableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isEmpty(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IProgramRunnableZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}
	
	@Override
	public boolean proofFlagSetBefore(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
}
