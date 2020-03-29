package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import custom.zUtil.io.FileZZZ;

public interface IKernelLogZZZ {
	public String getFilename();
	public void setFilename(String sFilename);
	
	public String getDirectory();
	public void setDirectory(String sDirectorypath) throws ExceptionZZZ;
	
	public FileZZZ getFileObject() throws ExceptionZZZ;
	public void setFileObject(FileZZZ objFile);
	
	public FileTextWriterZZZ getFileTextWriterObject() throws ExceptionZZZ;
	
	public boolean Write(String sLog);//wird dann als Synchronized implementiert.
	public boolean WriteLine(String sLog);//wird dann als Synchronized implementiert.
	public boolean WriteLineDate(String sLog);//wird dann als Synchronized implementiert.
	
}
