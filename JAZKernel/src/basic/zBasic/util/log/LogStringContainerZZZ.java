package basic.zBasic.util.log;

import basic.zBasic.ExceptionZZZ;

public class LogStringContainerZZZ implements ILogStringContainerZZZ{
	private IEnumSetMappedLogStringFormatZZZ enumLogStringFormat =null;//siehe ILogStringZZZ
	private String sLog=null;                               //der mit dem enum-iFactor... verbundene Log-Eintrag.
	
	public LogStringContainerZZZ() throws ExceptionZZZ{	
	}
	
	
	public LogStringContainerZZZ(IEnumSetMappedLogStringFormatZZZ enumLogStringFormat, String sLog) throws ExceptionZZZ{
		this.setLogString(enumLogStringFormat, sLog);
	}


	@Override
	public IEnumSetMappedLogStringFormatZZZ getEnumAsKey() throws ExceptionZZZ {
		return this.enumLogStringFormat;
	}


	@Override
	public String getLog() throws ExceptionZZZ {
		return this.sLog;
	}


	@Override
	public void setLogString(IEnumSetMappedLogStringFormatZZZ enumLogStringFormat, String sLog) throws ExceptionZZZ {
		this.enumLogStringFormat = enumLogStringFormat;
		this.sLog = sLog;
	}
}
