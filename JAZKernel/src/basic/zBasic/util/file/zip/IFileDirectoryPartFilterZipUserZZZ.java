package basic.zBasic.util.file.zip;

import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;

public interface IFileDirectoryPartFilterZipUserZZZ extends ZipEntryFilter{
	public void setDirectoryPartFilter(IFileDirectoryPartFilterZipZZZ objDirectoryFilterZip);
	public IFileDirectoryPartFilterZipZZZ getDirectoryPartFilter() throws ExceptionZZZ;
	
	public void setDirectoryPartFilterWithContent(IFileDirectoryWithContentPartFilterZipZZZ objDirectoryFilterZip);
	public IFileDirectoryWithContentPartFilterZipZZZ getDirectoryPartFilterWithConent() throws ExceptionZZZ;	
	
	public void setDirectoryPartFilterEmpty(IFileDirectoryEmptyPartFilterZipZZZ objDirectoryFilterZip);
	public IFileDirectoryEmptyPartFilterZipZZZ getDirectoryPartFilterEmpty() throws ExceptionZZZ;
}
