package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;

public interface ILogStringContainerZZZ {
	public IEnumSetMappedLogStringFormatZZZ getEnumAsKey() throws ExceptionZZZ;
	public String getLog() throws ExceptionZZZ;
	public void setLogString(IEnumSetMappedLogStringFormatZZZ objEnum, String sLog) throws ExceptionZZZ;	
}
