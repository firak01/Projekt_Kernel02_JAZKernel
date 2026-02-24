package custom.zKernel;

import basic.zBasic.ExceptionZZZ;

public interface ILogStringComputerZZZ {
	//Früher gab es diese Methoden nur static, nun wurde die static Methode um den FormatManager erweitert.
	//Diese public Methoden nutzen den ggfs. hinterlegten Format-Manager.
	public String computeLine(Object object, String sLog1) throws ExceptionZZZ;

}
