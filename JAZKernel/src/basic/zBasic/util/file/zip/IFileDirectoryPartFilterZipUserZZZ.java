package basic.zBasic.util.file.zip;

import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;

public interface IFileDirectoryPartFilterZipUserZZZ extends ZipEntryFilter{
	public void setDirectoryPartFilter(IFileDirectoryFilterZipZZZ objDirectoryFilterZip);
	public IFileDirectoryFilterZipZZZ getDirectoryPartFilter() throws ExceptionZZZ;
	
	public void setDirectoryPartFilterWithContent(IFileDirectoryWithContentFilterZipZZZ objDirectoryFilterZip);
	public IFileDirectoryWithContentFilterZipZZZ getDirectoryPartFilterWithConent() throws ExceptionZZZ;	
}
