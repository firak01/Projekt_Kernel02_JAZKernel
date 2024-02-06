package basic.zKernel.status;

import basic.zBasic.ExceptionZZZ;

public interface ISenderObjectStatusLocalSetUserZZZ extends ISenderObjectStatusBasicUserZZZ{	
	//public abstract ISenderObjectStatusLocalSetZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
	public abstract void setSenderStatusLocalUsed(ISenderObjectStatusLocalSetZZZ objEventSender);
	
	public abstract ISenderObjectStatusBasicZZZ getSenderStatusLocalUsed() throws ExceptionZZZ;
	//public abstract void setSenderStatusLocalUsed(ISenderObjectStatusBasicZZZ objEventSender);
}
