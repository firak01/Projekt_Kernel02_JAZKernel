package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalMessageSetUserZZZ {//extends ISenderObjectStatusBasicUserZZZ {	
//	public abstract ISenderObjectStatusLocalMessageSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageSetZZZ objEventSender);
	
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
//	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusBasicZZZ objEventSender);
}
