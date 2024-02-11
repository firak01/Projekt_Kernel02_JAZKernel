package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalMessageUserZZZ {//extends ISenderObjectStatusBasicUserZZZ {	
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageZZZ objEventSender);	
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
}
