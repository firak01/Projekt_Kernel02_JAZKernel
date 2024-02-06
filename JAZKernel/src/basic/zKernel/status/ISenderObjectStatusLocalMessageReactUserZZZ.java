package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalMessageReactUserZZZ extends ISenderObjectStatusBasicUserZZZ {	
//	public abstract ISenderObjectStatusLocalMessageReactZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageReactZZZ objEventSender);
	
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
//	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusBasicZZZ objEventSender);
}
