package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IStatusBooleanZZZ;
import basic.zKernel.status.IStatusLocalMapForCascadingStatusLocalUserZZZ;
import basic.zKernel.status.IStatusLocalUserZZZ;
import basic.zKernel.status.StatusBooleanZZZ;
import basic.zKernel.status.StatusLocalHelperZZZ;

public abstract class AbstractObjectWithStatusListeningZZZ <T> extends AbstractObjectWithStatusZZZ implements IStatusLocalMapForCascadingStatusLocalUserZZZ{
	private static final long serialVersionUID = 1L;
	protected HashMap<IEnumSetMappedZZZ,IEnumSetMappedZZZ> hmEnumSet =null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.

	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithStatusListeningZZZ() {	
		super();
	}
	public AbstractObjectWithStatusListeningZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	public AbstractObjectWithStatusListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}
	public AbstractObjectWithStatusListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
	}
	
	
	//### aus IStatusLocalUserZZZ
	@Override
	abstract public boolean isStatusLocalRelevant(IEnumSetMappedZZZ objEnumStatusIn) throws ExceptionZZZ;


	//### aus IListenerObjectStatusLocalMapForEventUserZZZ	
	@Override
	public HashMap<IEnumSetMappedZZZ, IEnumSetMappedZZZ> getHashMapEnumSetForCascadingStatusLocal() {
		if(this.hmEnumSet==null) {
			this.hmEnumSet = this.createHashMapEnumSetForCascadingStatusLocalCustom();
		}
		return this.hmEnumSet;
	}

	@Override
	public void setHashMapEnumSetForCascadingStatusLocal(HashMap<IEnumSetMappedZZZ, IEnumSetMappedZZZ> hmEnumSet) {
		this.hmEnumSet = hmEnumSet;
	}
	
	@Override
	public abstract HashMap<IEnumSetMappedZZZ, IEnumSetMappedZZZ> createHashMapEnumSetForCascadingStatusLocalCustom();
	
}
