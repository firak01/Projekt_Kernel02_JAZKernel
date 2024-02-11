package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalReactUserZZZ extends ISenderObjectStatusBasicUserZZZ{
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalReactZZZ objEventSender);	
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
}
