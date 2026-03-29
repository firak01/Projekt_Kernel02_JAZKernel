package basic.zKernel.status;

import java.util.HashMap;

import basic.zKernel.flag.json.IFlagContainerZZZ;

public class StatusBooleanContainerZZZ  implements IStatusBooleanContainerZZZ{
	//???? !!! NUR PUBLIC PROPERTIES KÖNNEN ÜBETRAGEN WERDEN PER JSON !!!
	public HashMap<String, Boolean>HmStatus; // = new HashMap<String, Boolean>(); //Neu 20130721

	public StatusBooleanContainerZZZ() {		
	}
	
	//??? Nur Variablen, die auch in einem Konstruktor übergeben werden können per JSON übertragen werden.
	public StatusBooleanContainerZZZ(HashMap<String, Boolean>hmStatus) {
		this.HmStatus = hmStatus;
	}
	
	//??? nur wenn der Getter und Setter so heisst wie die interne Variable ist eine Übetragung per JSON möglich
	public HashMap<String, Boolean> getHmStatus(){
		return HmStatus;
	}
	public void setHmStatus(HashMap<String, Boolean>hmStatus) {
		this.HmStatus = hmStatus;
	}
	
	@Override
	public HashMap<String, Boolean> getHashMap() {	
		if(this.HmStatus==null) {
			this.HmStatus=new HashMap<String,Boolean>();
		}
		return HmStatus;
	}
	
	@Override
	public void setHashMap(HashMap<String,Boolean>hmStatus) {
		this.HmStatus=hmStatus;
	}

	@Override
	public boolean getFlag(String sFlag) {
		return this.getHashMap().get(sFlag);
	}

	@Override
	public void setFlag(String sFlag, boolean bValue) {
		this.getHashMap().put(sFlag, bValue);
		
	}
}

