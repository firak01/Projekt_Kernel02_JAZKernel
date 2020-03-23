package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;

public interface IFileExpansionUserZZZ {
	public IFileExpansionZZZ getFileExpansionObject();
	public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion);
	
	public int getExpansionLength() throws ExceptionZZZ;
	public void setExpansionLength(int iExpansionLength) throws ExceptionZZZ;
	
	public String getExpansionFilling() throws ExceptionZZZ;
	public void setExpansionFilling(String sExpansionCharacter) throws ExceptionZZZ;
	public String getExpansionNext() throws ExceptionZZZ;
	public String getExpansionCurrent() throws ExceptionZZZ;
	public String getExpansionFirst() throws ExceptionZZZ;
	
}
