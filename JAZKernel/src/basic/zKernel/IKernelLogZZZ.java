package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import basic.zUtil.io.IFileExpansionUserZZZ;
import custom.zUtil.io.FileZZZ;

public interface IKernelLogZZZ extends IFileExpansionUserZZZ{
//	public enum FLAGZ{
//		USE_FILE_EXPANSION; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
//	}
	
	public String getFilename();
	public void setFilename(String sFilename);
	
	public String getDirectory();
	public void setDirectory(String sDirectorypath) throws ExceptionZZZ;
	
	public FileZZZ getFileObject() throws ExceptionZZZ;
	public void setFileObject(FileZZZ objFile);
	
	public FileTextWriterZZZ getFileTextWriterObject() throws ExceptionZZZ;
	
	public boolean Write(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean WriteLine(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean WriteLineDate(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	
}
