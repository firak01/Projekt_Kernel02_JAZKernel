package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.StatusLocalEventHelperZZZ;

public abstract class AbstractObjectWithFlagOnFlagListeningZZZ <T> extends AbstractObjectWithFlagZZZ<Object> implements IListenerObjectFlagZsetZZZ {
	private static final long serialVersionUID = 1L;
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithFlagOnFlagListeningZZZ() {	
		super();
	}
	public AbstractObjectWithFlagOnFlagListeningZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractObjectWithFlagOnFlagListeningNew_();
	}
	public AbstractObjectWithFlagOnFlagListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractObjectWithFlagOnFlagListeningNew_();
	}
	public AbstractObjectWithFlagOnFlagListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
		AbstractObjectWithFlagOnFlagListeningNew_();
	}
		
	private boolean AbstractObjectWithFlagOnFlagListeningNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.getFlag("init")) break main;
			
			//Das Programm sollte sich ggfs. am eigenen ObjectBroker registrieren.
			//Ansonsten bleibt nur die reaction4Action-Methode.
			if(this.getFlag(IListenerObjectFlagZsetZZZ.FLAGZ.REGISTER_SELF_FOR_EVENT)) {
				this.getSenderFlagUsed().addListenerObjectFlagZset(this);
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	/* (non-Javadoc)
	 * @see basic.zKernel.flag.IEventBrokerFlagZsetUserZZZ#registerForFlagEvent(basic.zKernel.flag.IListenerObjectFlagZsetZZZ)
	 */
	@Override
	public void registerForFlagEvent(IListenerObjectFlagZsetZZZ objEventListener) throws ExceptionZZZ {
		//Das Programm sollte sich ggfs. am eigenen ObjectBroker registrieren.
		//Ansonsten bleibt nur die reaction4Action-Methode, die es aktuell nur bei STATUS_LOCAL gibt
		if(this.getFlag(IListenerObjectFlagZsetZZZ.FLAGZ.REGISTER_SELF_FOR_EVENT)) {
			this.getSenderFlagUsed().addListenerObjectFlagZset(this);
		}
		
		//this.getSenderFlagUsed().addListenerObjectFlagZset(objEventListener);
		super.registerForFlagEvent(objEventListener);
	}
	
	//######################################################################
	//### FLAGZ: aus IListenerObjectFlagZsetZZZ                 #########
	//######################################################################
	@Override
	public boolean getFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IListenerObjectFlagZsetZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}		
}


