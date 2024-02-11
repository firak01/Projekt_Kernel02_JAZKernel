package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalUserZZZ extends ISenderObjectStatusBasicUserZZZ{	
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalZZZ objEventSender);	
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
}
