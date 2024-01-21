package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalMessageSetUserZZZ {	
	public abstract ISenderObjectStatusLocalMessageSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageSetZZZ objEventSender);
}
