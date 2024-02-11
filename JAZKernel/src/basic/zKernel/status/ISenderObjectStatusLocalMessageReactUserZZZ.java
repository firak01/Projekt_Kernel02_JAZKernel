package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalMessageReactUserZZZ extends ISenderObjectStatusBasicUserZZZ {	
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalMessageReactZZZ objEventSender);
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
}
