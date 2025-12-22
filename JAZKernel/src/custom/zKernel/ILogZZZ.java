package custom.zKernel;

import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_NAME_DEFAULT;
import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_DIRECTORY_DEFAULT;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import basic.zKernel.IKernelConfigUserZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import custom.zUtil.io.FileZZZ;

public interface ILogZZZ extends IFileExpansionEnabledZZZ, IKernelConfigUserZZZ{
//	public enum FLAGZ{
//		USE_FILE_EXPANSION; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
//	}
	
	public String getFilename() throws ExceptionZZZ;
	public void setFilename(String sFilename) throws ExceptionZZZ;
	
	public String getFilenameExpanded() throws ExceptionZZZ;
	
	public String getDirectory() throws ExceptionZZZ;
	public void setDirectory(String sDirectorypath) throws ExceptionZZZ;
	
	public FileZZZ getFileObject() throws ExceptionZZZ;
	public void setFileObject(FileZZZ objFile) throws ExceptionZZZ;
	
	public FileTextWriterZZZ getFileTextWriterObject() throws ExceptionZZZ;
	
	public boolean Write(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean WriteLine(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean WriteLineDate(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean WriteLineDate(String... sLogs) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	
	public boolean WriteLineDate(Object obj, String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	
	public boolean WriteLineDateWithPosition(String sLog) throws ExceptionZZZ;
	
	public boolean WriteLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ;	
	public boolean WriteLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ;
	public boolean WriteLineDateWithPosition(Object obj, int iStackTraceOffset, String sLog) throws ExceptionZZZ;
	
	public boolean WriteLineDateWithPositionXml(Object obj, String sLog) throws ExceptionZZZ;
}
