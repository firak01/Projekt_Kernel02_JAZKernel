package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;

public interface IFileExpansionUserZZZ {
	public enum FLAGZ{
		USE_FILE_EXPANSION; //Merke: DEBUG und INIT über IFlagZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von anderer Objektklasse geerbt.
	}
	
	public IFileExpansionZZZ getFileExpansionObject();
	public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion);	
}
