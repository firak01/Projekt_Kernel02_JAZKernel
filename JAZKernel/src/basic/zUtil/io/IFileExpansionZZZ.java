package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IFlagZZZ;
import custom.zUtil.io.FileZZZ;

public interface IFileExpansionZZZ extends IFileExpansionConstZZZ,IFlagZZZ {
	
	public FileZZZ getFileBase();
	public void setFileBase(FileZZZ objFile);
	
	public int getExpansionLength();
	public void setExpansionLength(int iExpansionLength);
	
	public String getExpansionFilling();
	public void setExpansionFilling(char cExpansionFilling);
	public void setExpansionFilling(String sExpansionFillingCharacter) throws ExceptionZZZ;
	
	public String getExpansionCurrent() throws ExceptionZZZ;
	public String getExpansionCurrent(int iExpansionLength) throws ExceptionZZZ;
	public String getExpansionFirst() throws ExceptionZZZ;
	public String getExpansionFirst(int iExpansionLength) throws ExceptionZZZ;
	public String getExpansionNext() throws ExceptionZZZ;
	public String getExpansionNext(int iExpansionLength) throws ExceptionZZZ;
	
	public String computeExpansion(int iExpansionValue);
	public String computeExpansion(String sFilling, int iExpansionValue);
	public String computeExpansion(String sFilling, int iExpansionValue, int iExpansionLength);
}
