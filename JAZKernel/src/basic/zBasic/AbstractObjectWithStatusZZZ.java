package basic.zBasic;

import static java.lang.System.out;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import base.util.abstractArray.ArrayUtil;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarEasyZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.KernelLogZZZ;
import basic.zKernel.flag.EventObjectFlagZsetZZZ;
import basic.zKernel.flag.IEventBrokerFlagZsetUserZZZ;
import basic.zKernel.flag.IEventObjectFlagZsetZZZ;
import basic.zKernel.flag.IFlagZLocalUserZZZ;
import basic.zKernel.flag.IFlagZUserZZZ;
import basic.zKernel.flag.IListenerObjectFlagZsetZZZ;
import basic.zKernel.flag.ISenderObjectFlagZsetZZZ;
import basic.zKernel.flag.KernelSenderObjectFlagZsetZZZ;
import basic.zKernel.flag.json.FlagZHelperZZZ;
import basic.zKernel.flag.json.IFlagZZZ;
import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalSetZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalSetUserZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalSetZZZ;
import custom.zKernel.LogZZZ;

public abstract class AbstractObjectWithStatusZZZ <T> extends ObjectZZZ implements ISenderObjectStatusLocalSetZZZ, IListenerObjectStatusLocalSetZZZ{
	private static final long serialVersionUID = 1L;

	protected HashMap<String, Boolean>hmStatusLocal = new HashMap<String, Boolean>(); //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	//private ISenderObjectStatusLocalSetZZZ objEventStatusLocalBroker=null;//Das Broker Objekt, an dem sich andere Objekte regristrieren können, um ueber Aenderung eines StatusLocal per Event informiert zu werden.
	
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public AbstractObjectWithStatusZZZ() {	
		super();
	}
	public AbstractObjectWithStatusZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	public AbstractObjectWithStatusZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}
	public AbstractObjectWithStatusZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
	}
	
	//##### aus IListenerObjectStatusLocalSetZZZ
	@Override
	public boolean statusLocalChanged(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ {
		boolean bReturn = false;
		
		main:{
			//Falls nicht zuständig, mache nix
		    boolean bProof = this.isStatusLocalRelevant(eventStatusLocalSet);
			if(!bProof) break main;
			
			//Lies den Status (geworfen vom Backend aus)
			String sStatus = eventStatusLocalSet.getStatusText();
			
			//übernimm den Status
			//this.setStatusString(sStatus);
			
		}//end main:
		return bReturn;
	}
	
	//### aus ISenderObjectStatusLocalSetUserOVPN
//	@Override
//	public ISenderObjectStatusLocalSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
//		return this.objEventStatusLocalBroker;
//	}
		
//	@Override
//	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalSetZZZ objEventSender) {
//		this.objEventStatusLocalBroker=objEventSender;
//	}
	
	@Override
	public void fireEvent(IEventObjectStatusLocalSetZZZ event) {
		
	}
	
	@Override
	public void removeListenerObjectStatusLocalSet(IListenerObjectStatusLocalSetZZZ objEventListener) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addListenerObjectStatusLocalSet(IListenerObjectStatusLocalSetZZZ objEventListener) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.ISenderObjectStatusLocalSetZZZ#getListenerRegisteredAll()
	 */
	@Override
	public ArrayList<IListenerObjectStatusLocalSetZZZ> getListenerRegisteredAll() throws ExceptionZZZ {
		return null;
	}
	
	@Override
	public IEventObjectStatusLocalSetZZZ getEventPrevious() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEventPrevious(IEventObjectStatusLocalSetZZZ event) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isStatusLocalRelevant(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
}
