package basic.zKernel.status;

import java.util.HashMap;

public interface IStatusBooleanContainerZZZ {
	public abstract HashMap<String, Boolean>getHmStatus();
	public abstract void setHmStatus(HashMap<String, Boolean>hmFlag);
	public abstract HashMap<String, Boolean>getHashMap();
	public abstract void setHashMap(HashMap<String, Boolean>hmFlag);
	public abstract boolean getFlag(String sFlag);
	public abstract void setFlag(String sFlag, boolean bValue);	
}
