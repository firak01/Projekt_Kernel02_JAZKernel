package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zKernel.status.IEventObjectStatusLocalSetZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalSetZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalSetZZZ;

public abstract class AbstractObjectWithStatusZZZ <T> extends AbstractObjectZZZ implements ISenderObjectStatusLocalSetZZZ, IListenerObjectStatusLocalSetZZZ{
	private static final long serialVersionUID = 1L;

	protected HashMap<String, Boolean>hmStatusLocal = new HashMap<String, Boolean>(); //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	
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
			
			//Nur so als Beispiel, muss ueberschrieben werden:
			//Lies den Status (geworfen vom Backend aus)
			String sStatus = eventStatusLocalSet.getStatusText();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Methode muss ueberschrieben werden.");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sStatus='"+sStatus+"'");
		}//end main:
		return bReturn;
	}
	

	@Override
	abstract public void fireEvent(IEventObjectStatusLocalSetZZZ event);
	
	@Override
	abstract public void removeListenerObjectStatusLocalSet(IListenerObjectStatusLocalSetZZZ objEventListener) throws ExceptionZZZ;
	
	@Override
	abstract public void addListenerObjectStatusLocalSet(IListenerObjectStatusLocalSetZZZ objEventListener) throws ExceptionZZZ;

	@Override
	abstract public ArrayList<IListenerObjectStatusLocalSetZZZ> getListenerRegisteredAll() throws ExceptionZZZ;
	
	@Override
	abstract public IEventObjectStatusLocalSetZZZ getEventPrevious();

	@Override
	abstract public void setEventPrevious(IEventObjectStatusLocalSetZZZ event);
	
	@Override
	abstract public boolean isStatusLocalRelevant(IEventObjectStatusLocalSetZZZ eventStatusLocalSet) throws ExceptionZZZ;
}
